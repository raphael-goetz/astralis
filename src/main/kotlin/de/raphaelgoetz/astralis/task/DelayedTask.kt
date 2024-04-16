package de.raphaelgoetz.astralis.task

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

inline fun doLater(
    delay: Long,
    function: () -> Unit = {}
) = DelayedTaskBuilder(false, delay).apply { function() }.execute()


inline fun doLaterAsync(
    delay: Long,
    function: () -> Unit = {}
) = DelayedTaskBuilder(true, delay).apply { function() }.execute()

data class DelayedTaskBuilder(
    private val async: Boolean,
    private val delay: Long,
    private val function: () -> Unit = {}
) {

    private val timer = Timer()

    fun execute() = timer.schedule(createTask(async, function), delay)

}

private fun createTask(
    async: Boolean,
    function: () -> Unit
): TimerTask {

    return object : TimerTask() {

        override fun run() {

            if (async) {
                CoroutineScope(Dispatchers.Default).launch { function() }
            } else {
                function()
            }
        }
    }
}
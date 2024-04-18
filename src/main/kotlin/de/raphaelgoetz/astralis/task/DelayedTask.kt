package de.raphaelgoetz.astralis.task

import de.raphaelgoetz.astralis.annotations.InternalUse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

/**
 * @param delay is the time after which the given function gets executed
 * @param function is the function that gets called
 * The given function will be called synchronous
 */
inline fun doLater(
    delay: Long,
    function: () -> Unit = {}
) = DelayedTaskBuilder(false, delay).apply { function() }.execute()

/**
 * @param delay is the time after which the given function gets executed
 * @param function is the function that gets called
 * The given function will be called asynchronous
 */
inline fun doLaterAsync(
    delay: Long,
    function: () -> Unit = {}
) = DelayedTaskBuilder(true, delay).apply { function() }.execute()

@InternalUse
data class DelayedTaskBuilder(
    private val async: Boolean,
    private val delay: Long,
    private val function: () -> Unit = {}
) {

    private val timer = Timer()

    fun execute() = timer.schedule(createTask(async, function), delay)

}

@InternalUse
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
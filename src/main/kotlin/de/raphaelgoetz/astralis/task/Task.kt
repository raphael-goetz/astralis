package de.raphaelgoetz.astralis.task

import kotlinx.coroutines.*

inline fun doNow(
    function: () -> Unit = {}
) = TaskBuilder(false).apply { function() }.execute()

inline fun doNowAsync(
    function: () -> Unit = {}
) = TaskBuilder(true).apply { function() }.execute()

class TaskBuilder(
    private val async: Boolean = false,
    private val function: () -> Unit = {}
) {

    fun execute() {

        try {

            if (async) {
                CoroutineScope(Dispatchers.Default).launch {
                    this@TaskBuilder.function
                }

            } else this@TaskBuilder.function

        } catch (exception: Exception) {
            println(exception)
        }
    }
}
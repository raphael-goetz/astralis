package de.raphaelgoetz.astralis.schedule

import de.raphaelgoetz.astralis.annotations.InternalUse
import kotlinx.coroutines.*

/**
 * @param function is the function that gets called.
 * The given function will be called synchronous
 */
inline fun doNow(
    function: () -> Unit = {}
) = TaskBuilder(false).apply { function() }.execute()

/**
 * @param function is the function that gets called.
 * The given function will be called asynchronous
 */
inline fun doNowAsync(
    function: () -> Unit = {}
) = TaskBuilder(true).apply { function() }.execute()

@InternalUse
class TaskBuilder(
    private val async: Boolean = false,
    private val function: () -> Unit = {}
) {

    fun execute() {

        try {

            //TODO: OVERLOOK COROUTINE
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
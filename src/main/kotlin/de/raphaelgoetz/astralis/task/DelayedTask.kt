package de.raphaelgoetz.astralis.task

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import kotlin.concurrent.schedule

/**
 * @param delay is the time after which the given function gets executed
 * @param function is the function that gets called
 * The given function will be called synchronous
 */
inline fun doLater(
    delay: Long,
    crossinline function: () -> Unit = {}
) = Timer(false).schedule(delay, ) {
    function.invoke()
}

/**
 * @param delay is the time after which the given function gets executed
 * @param function is the function that gets called
 * The given function will be called asynchronous
 */
inline fun doLaterAsync(
    delay: Long,
    crossinline function: () -> Unit = {}
) = Timer().schedule(delay) {
    CoroutineScope(Dispatchers.Default).launch { function.invoke() }
}
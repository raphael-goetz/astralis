package de.raphaelgoetz.astralis.schedule

import de.raphaelgoetz.astralis.schedule.time.TaskTimeTypes
import de.raphaelgoetz.astralis.schedule.time.toMilliseconds
import java.util.*
import kotlin.concurrent.schedule

/**
 * @param delay is the time after which the given function gets executed.
 * @param taskTimeTypes is the time converter. Output in milliseconds.
 * @param function is the function that gets called.
 * The given function will be called synchronous.
 */
inline fun doLater(
    delay: Long,
    taskTimeTypes: TaskTimeTypes = TaskTimeTypes.SECONDS,
    crossinline function: () -> Unit = {}
) = Timer(false).schedule(taskTimeTypes.toMilliseconds(delay)) {
    doNow {
        function.invoke()
    }
}

/**
 * @param delay is the time after which the given function gets executed.
 * @param taskTimeTypes is the time converter. Output in milliseconds.
 * @param function is the function that gets called.
 * The given function will be called asynchronous.
 */
inline fun doLaterAsync(
    delay: Long,
    taskTimeTypes: TaskTimeTypes = TaskTimeTypes.SECONDS,
    crossinline function: () -> Unit = {}
) = Timer().schedule(taskTimeTypes.toMilliseconds(delay)) {
    doNowAsync {
        function.invoke()
    }
}
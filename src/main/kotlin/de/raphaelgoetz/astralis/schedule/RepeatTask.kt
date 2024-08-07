package de.raphaelgoetz.astralis.schedule

import de.raphaelgoetz.astralis.annotations.InternalUse
import de.raphaelgoetz.astralis.schedule.time.TaskTimeTypes
import de.raphaelgoetz.astralis.schedule.time.toMilliseconds
import java.util.*

private val repeatingTasks: HashMap<UUID, Timer> = hashMapOf()

/**
 * @param delay is the time after which the given function gets executed.
 * @param period is the time between each function call.
 * @param taskTimeTypes is the time converter. Output in milliseconds.
 * @param function is the function that gets called.
 * It contains an integer that will be incremented after every execution.
 * It's main purpose is to count how many times the given function has been called.
 * The given function will be called synchronous.
 */
inline fun doAgain(
    delay: Long = 0,
    period: Long,
    taskTimeTypes: TaskTimeTypes = TaskTimeTypes.SECONDS,
    noinline function: RepeatTaskBuilder.(Long) -> Unit = {},
) = RepeatTaskBuilder(
    false,
    taskTimeTypes.toMilliseconds(delay),
    taskTimeTypes.toMilliseconds(period),
    function
).start()

/**
 * @param delay is the time after which the given function gets executed.
 * @param period is the time between each function call.
 * @param taskTimeTypes is the time converter. Output in milliseconds.
 * @param function is the function that gets called.
 * It contains an integer that will be incremented after every execution.
 * It's main purpose is to count how many times the given function has been called.
 * The given function will be called asynchronous.
 */
inline fun doAgainAsync(
    delay: Long = 0,
    period: Long,
    taskTimeTypes: TaskTimeTypes = TaskTimeTypes.SECONDS,
    noinline function: RepeatTaskBuilder.(Long) -> Unit = {},
) = RepeatTaskBuilder(true, taskTimeTypes.toMilliseconds(delay), taskTimeTypes.toMilliseconds(period), function).start()

/**
 * Stops an repeating task.
 * @param uuid of the task that will be stopped.
 */
fun stopRepeatingTask(uuid: UUID) {
    repeatingTasks.remove(uuid)
}

/**
 * Builder for RepeatingTasks.
 * @param async when true, function will be executed async.
 * @param delay of the task (time to wait util the task starts).
 * @param period of the task (time after the task gets repeated).
 * @param function that will be executed. Contains an integer which tells the current iteration.
 */
@InternalUse
data class RepeatTaskBuilder(
    private val async: Boolean,
    private val delay: Long,
    private val period: Long,
    private val function: RepeatTaskBuilder.(Long) -> Unit = {},
) {

    private val timer = Timer()

    private var iteration: Long = 0
    private var stopped = false

    fun start(): UUID {

        val uuid = UUID.randomUUID()
        repeatingTasks.putIfAbsent(uuid, timer)

        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {

                if (stopped) return

                if (async) doNowAsync {
                    function(iteration)
                } else doNow {
                    function(iteration)
                }

                iteration++

            }
        }, delay, period)

        return uuid
    }

    fun stop() {
        timer.cancel()
    }
}
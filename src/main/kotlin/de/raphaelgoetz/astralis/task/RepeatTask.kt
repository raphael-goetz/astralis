package de.raphaelgoetz.astralis.task

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.HashMap

private val dispatcher: HashMap<UUID, Timer> = hashMapOf()

inline fun doAgain(
    delay: Long,
    period: Long,
    noinline function: RepeatTaskBuilder.(Int) -> Unit = {},
) = RepeatTaskBuilder(false, delay, period, function).start()

inline fun doAgainAsync(
    delay: Long,
    period: Long,
    noinline function: RepeatTaskBuilder.(Int) -> Unit = {},
) = RepeatTaskBuilder(true, delay, period, function).start()

data class RepeatTaskBuilder(
    private val async: Boolean,
    private val delay: Long,
    private val period: Long,
    private val function: RepeatTaskBuilder.(Int) -> Unit = {},
) {

    private val timer = Timer()
    private var iteration: Int = 0

    fun start(): UUID {

        val uuid = UUID.randomUUID()
        dispatcher.putIfAbsent(uuid, timer)

        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {

                if (async) {
                    CoroutineScope(Dispatchers.Default).launch { function(iteration) }
                } else {
                    function(iteration)
                }

                iteration++

            }
        }, delay, period * 100)

        return uuid
    }
}

fun stopTask(uuid: UUID) {
    dispatcher.remove(uuid)
}


package de.raphaelgoetz.astralis.schedule

import de.raphaelgoetz.astralis.AstralisInstance
import de.raphaelgoetz.astralis.annotations.InternalUse
import org.bukkit.Bukkit

/**
 * @param function is the function that gets called.
 * The given function will be called synchronous.
 */
inline fun doNow(
    noinline function: () -> Unit = {}
) = doTask(false, function)

/**
 * @param function is the function that gets called.
 * The given function will be called asynchronous.
 */
inline fun doNowAsync(
    noinline function: () -> Unit = {}
) = doTask(true, function)

/**
 * Builder for creating sync/async tasks.
 * @param async if true, the function will be called async.
 * @param function that will be executed once.
 */
@InternalUse
inline fun doTask(
    async: Boolean = false,
    noinline function: () -> Unit = {}
) {
    if (async) {
        Bukkit.getScheduler().runTaskAsynchronously(AstralisInstance, function)
    } else {
        Bukkit.getScheduler().runTask(AstralisInstance, function)
    }
}
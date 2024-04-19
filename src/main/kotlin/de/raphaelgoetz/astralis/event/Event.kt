package de.raphaelgoetz.astralis.event

import org.bukkit.entity.Player
import org.bukkit.event.*
import org.bukkit.event.player.PlayerEvent
import org.bukkit.plugin.java.JavaPlugin

fun Listener.unregister() = HandlerList.unregisterAll(this)

inline fun <reified T : Event> listen(
    plugin: JavaPlugin,
    priority: EventPriority = EventPriority.NORMAL,
    crossinline onEvent: (event: T) -> Unit,
): Listener {

    val wrapper = object : WrapperListener<T>(priority) {
        override fun onEvent(event: T) = onEvent.invoke(event)
    }

    wrapper.subscribe(plugin)
    return wrapper
}

inline fun <reified T : PlayerEvent> Player.listen(
    plugin: JavaPlugin,
    priority: EventPriority = EventPriority.NORMAL,
    crossinline onEvent: (event: T) -> Unit,
): Listener {

    val wrapper = object : WrapperListener<T>(priority) {
        override fun onEvent(event: T) = onEvent.invoke(event)
    }

    wrapper.subscribeForPlayer(plugin, this)
    return wrapper
}

inline fun <reified T : Event> listenCancelled(plugin: JavaPlugin) : Listener =
    listen<T>(plugin, EventPriority.LOWEST) { event ->
        if (event is Cancellable) event.isCancelled = true
    }


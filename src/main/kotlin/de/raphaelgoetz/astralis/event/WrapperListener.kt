package de.raphaelgoetz.astralis.event

import de.raphaelgoetz.astralis.annotations.InternalUse
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.Event
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerEvent
import org.bukkit.plugin.java.JavaPlugin

/**
 * A wrapper that contains the event and the listener.
 * @returns the created listener.
 */
@InternalUse
abstract class WrapperListener<T: Event> (
    val priority: EventPriority
) : Listener{
    abstract fun onEvent(event: T)
}

/**
 * Registers the event from the given WrapperListener.
 */
@InternalUse
inline fun <reified T: Event> WrapperListener<T>.subscribe(javaPlugin: JavaPlugin) =
    Bukkit.getPluginManager().registerEvent(T::class.java, this, priority, { _, event ->
        if (event is T) {
            onEvent(event)
        }
    }, javaPlugin)

/**
 * Registers the player-event only for a specific player from the given WrapperListener.
 */
@InternalUse
inline fun <reified T: PlayerEvent> WrapperListener<T>.subscribeForPlayer(javaPlugin: JavaPlugin, player: Player) =
    Bukkit.getPluginManager().registerEvent(T::class.java, this, priority, { _, event ->
        if (event is T && event.player.uniqueId == player.uniqueId) {
            onEvent(event)
        }
    }, javaPlugin)
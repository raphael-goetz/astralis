package de.raphaelgoetz.astralis.event

import org.bukkit.entity.Player
import org.bukkit.event.*
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.player.PlayerEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack

/**
 * Unregisters the given listener.
 */
fun Listener.unregister() = HandlerList.unregisterAll(this)

/**
 * Will automatically listen to the created event.
 * @param T as an Event.
 * @param priority of the listener. Default = Normal,
 * @param onEvent is the action that gets called.
 * @return the created listener.
 */
inline fun <reified T : Event> listen(
    priority: EventPriority = EventPriority.NORMAL,
    crossinline onEvent: (event: T) -> Unit,
): Listener {

    val wrapper = object : WrapperListener<T>(priority) {
        override fun onEvent(event: T) = onEvent.invoke(event)
    }

    wrapper.subscribe()
    return wrapper
}

/**
 * Will automatically listen to the created event only for the player.
 * @param T as an PlayerEvent.
 * @param priority of the listener. Default = Normal,
 * @param onEvent is the action that gets called.
 * @return the created listener.
 */
inline fun <reified T : PlayerEvent> Player.listen(
    priority: EventPriority = EventPriority.NORMAL,
    crossinline onEvent: (event: T) -> Unit,
): Listener {

    val wrapper = object : WrapperListener<T>(priority) {
        override fun onEvent(event: T) = onEvent.invoke(event)
    }

    wrapper.subscribeForPlayer(this)
    return wrapper
}

/**
 * Will automatically listen to the created event & cancel it.
 * @param T as an Event.
 * @param eventPriority of the listener. Default = Lowest,
 * @return the created listener.
 */
inline fun <reified T : Event> listenCancelled(eventPriority: EventPriority = EventPriority.LOWEST): Listener =
    listen<T>(eventPriority) { event ->
        if (event is Cancellable) event.isCancelled = true
    }

/**
 * Will automatically listen to the item that gets clicked.
 * @param priority of the listener. Default = Normal,
 * @param onEvent is the action that gets called.
 * @return the created listener.
 */
inline fun ItemStack.listenClick(
    priority: EventPriority = EventPriority.NORMAL,
    crossinline onEvent: (event: InventoryClickEvent) -> Unit,
) = listen<InventoryClickEvent>(priority) { event ->
    if (event.clickedInventory == this) onEvent.invoke(event)
}

/**
 * Will automatically listen to the item that gets interacted with.
 * @param priority of the listener. Default = Normal,
 * @param onEvent is the action that gets called.
 * @return the created listener.
 */
inline fun ItemStack.listenInteract(
    priority: EventPriority = EventPriority.NORMAL,
    crossinline onEvent: (event: PlayerInteractEvent) -> Unit
) = listen<PlayerInteractEvent>(priority) { event ->
    if (event.item == this) onEvent.invoke(event)
}
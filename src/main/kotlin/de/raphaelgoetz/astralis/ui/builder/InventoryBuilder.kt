package de.raphaelgoetz.astralis.ui.builder

import de.raphaelgoetz.astralis.event.listen
import de.raphaelgoetz.astralis.items.builder.SmartItem
import de.raphaelgoetz.astralis.ui.data.InventoryRows
import de.raphaelgoetz.astralis.ui.data.InventorySlots
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.Inventory
import org.bukkit.plugin.java.JavaPlugin

import java.util.function.Consumer
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

/**
 * Builder for a default inventory.
 * @param title of the inventory.
 * @param holder of the current inventory.
 * @param rows of the inventory. Used InventoryRows as easier way then doing 5*9 or something.
 */
open class InventoryBuilder(
    title: Component,
    private val holder: Player,
    rows: InventoryRows = InventoryRows.ROW6,
) {

    private val clickEvents: HashMap<Int, SmartClick> = HashMap()
    private val closeEvents: ArrayList<Runnable> = arrayListOf()
    private val inventory: Inventory = Bukkit.createInventory(holder, rows.value, title)

    /** Clears the current inventory */
    fun clear() {
        clickEvents.clear()
        inventory.clear()
    }

    /** Opens the inventory */
    fun open() = holder.openInventory(inventory)

    /**
     * Binds the smart-item to the inside a smart-click to a slot.
     * @param slot that the smart-click gets bind to by int.
     * @param item of the smart-click.
     * @param action of the smart-click.
     */
    fun setSlot(slot: Int, item: SmartItem, action: Consumer<InventoryClickEvent>?) {
        if (action != null) this.clickEvents[slot] = SmartClick(item, action)
        inventory.setItem(slot, item.itemStack)
    }

    fun setSlot(slot: InventorySlots, item: SmartItem, action: Consumer<InventoryClickEvent>?) = setSlot(slot.value, item, action)
    /**
     * Binds the smart-item to the inside a smart-click to a slot.
     * @param slot that the smart-click gets bind to by InventorySlots.
     * @param item of the smart-click.
     * @param action of the smart-click.
     */

    /**
     * Binds the smart-item to the inside a smart-click to a slot.
     * Without an action.
     * @param slot that the smart-click gets bind to by InventorySlots.
     * @param item of the smart-click.
     */
    fun setSlot(slot: InventorySlots, item: SmartItem) = setSlot(slot, item, null)

    /**
     * Binds the smart-item to the inside a smart-click to a slot.
     * The inventory-click event automatically gets cancelled.
     * @param slot that the smart-click gets bind to by InventorySlots.
     * @param item of the smart-click.
     * @param action of the smart-click.
     */
    fun setBlockedSlot(slot: InventorySlots, item: SmartItem, action: Consumer<InventoryClickEvent>?) {
        setSlot(slot, item) { event ->
            event.isCancelled = true
            action?.accept(event)
        }
    }

    /**
     * Binds the smart-item to the inside a smart-click to a slot.
     * The inventory-click event automatically gets cancelled.
     * Without an action.
     * @param slot that the smart-click gets bind to by InventorySlots.
     * @param item of the smart-click.
     */
    fun setBlockedSlot(slot: InventorySlots, item: SmartItem) = setBlockedSlot(slot, item, null)

    /**
     * Binds the smart-item to the inside a smart-click to a slot.
     * @param item of the smart-click.
     * @param action of the smart-click.
     */
    fun addSlot(item: SmartItem, action: Consumer<InventoryClickEvent>?) {
        inventory.addItem(item.itemStack)
        val slot = inventory.first(item.itemStack)
        if (action != null) this.clickEvents[slot] = SmartClick(item, action)
    }

    /**
     * Binds the smart-item to the inside a smart-click to a slot.
     * Without an action.
     * @param item of the smart-click.
     */
    fun addSlot(item: SmartItem) = addSlot(item, null)

    /**
     * Binds the smart-item to the inside a smart-click to a slot.
     * The inventory-click event automatically gets cancelled.
     * @param item of the smart-click.
     * @param action of the smart-click.
     */
    fun addBlockedSlot(item: SmartItem, action: Consumer<InventoryClickEvent>?) {
        addSlot(item) { event ->
            event.isCancelled = true
            action?.accept(event)
        }
    }

    /**
     * Removes the item on the given InventorySlots.
     * @param slot of the item that will be removed.
     */
    fun removeSlot(slot: InventorySlots) {
        inventory.clear(slot.value)
        clickEvents.remove(slot.value)
    }

    init {
        listen<InventoryClickEvent> { inventoryClickEvent ->
            if (inventoryClickEvent.whoClicked !is Player) return@listen
            if (inventoryClickEvent.clickedInventory == null) return@listen
            if (inventoryClickEvent.currentItem == null) return@listen

            val slot: Int = inventoryClickEvent.slot
            if (inventoryClickEvent.clickedInventory!! != inventory) return@listen

            val smartClick = clickEvents[slot] ?: return@listen
            smartClick.action.accept(inventoryClickEvent)
            holder.playSound(holder, smartClick.item.interactionType.sound, 1f, 1f)
        }
    }
}
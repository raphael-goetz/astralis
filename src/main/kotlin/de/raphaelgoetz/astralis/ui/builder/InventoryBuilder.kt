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

open class InventoryBuilder(
    javaPlugin: JavaPlugin,
    title: Component,
    private val holder: Player,
    rows: InventoryRows = InventoryRows.ROW6,
) {

    private val clickEvents: HashMap<Int, SmartClick> = HashMap()
    private val closeEvents: ArrayList<Runnable> = arrayListOf()
    private val inventory: Inventory = Bukkit.createInventory(holder, rows.value, title)

    fun clear() {
        clickEvents.clear()
        inventory.clear()
    }

    fun open() = holder.openInventory(inventory)

    fun setSlot(slot: Int, item: SmartItem, action: Consumer<InventoryClickEvent>?) {
        if (action != null) this.clickEvents[slot] = SmartClick(item, action)
        inventory.setItem(slot, item.itemStack)
    }

    fun setSlot(slot: InventorySlots, item: SmartItem, action: Consumer<InventoryClickEvent>?) = setSlot(slot.value, item, action)

    fun setSlot(slot: InventorySlots, item: SmartItem) = setSlot(slot, item, null)

    fun setBlockedSlot(slot: InventorySlots, item: SmartItem, action: Consumer<InventoryClickEvent>?) {
        setSlot(slot, item) { event ->
            event.isCancelled = true
            action?.accept(event)
        }
    }

    fun setBlockedSlot(slot: InventorySlots, item: SmartItem) = setBlockedSlot(slot, item, null)

    fun addSlot(item: SmartItem, action: Consumer<InventoryClickEvent>?) {
        inventory.addItem(item.itemStack)
        val slot = inventory.first(item.itemStack)
        if (action != null) this.clickEvents[slot] = SmartClick(item, action)
    }

    fun addSlot(item: SmartItem) = addSlot(item, null)

    fun addBlockedSlot(item: SmartItem, action: Consumer<InventoryClickEvent>?) {
        addSlot(item) { event ->
            event.isCancelled = true
            action?.accept(event)
        }
    }

    fun removeSlot(slot: InventorySlots) {
        inventory.clear(slot.value)
        clickEvents.remove(slot.value)
    }

    init {
        listen<InventoryClickEvent>(javaPlugin) { inventoryClickEvent ->
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
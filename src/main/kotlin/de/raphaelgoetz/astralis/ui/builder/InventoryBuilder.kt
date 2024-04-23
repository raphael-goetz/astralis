package de.raphaelgoetz.astralis.ui.builder

import de.raphaelgoetz.astralis.event.listen
import de.raphaelgoetz.astralis.ui.data.InventoryRows
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.Inventory
import org.bukkit.plugin.java.JavaPlugin
import java.util.function.Consumer

class InventoryBuilder(
    javaPlugin: JavaPlugin,
    title: Component,
    holder: Player,
    rows: InventoryRows = InventoryRows.ROW6,
) {

    private val clickEvents: HashMap<Int, Consumer<InventoryClickEvent>> = HashMap()
    private val closeEvents: ArrayList<Runnable> = arrayListOf()
    private val inventory: Inventory = Bukkit.createInventory(holder, rows.value, title)

    init {
        listen<InventoryClickEvent>(javaPlugin) { inventoryClickEvent ->
            if (inventoryClickEvent.whoClicked !is Player) return@listen
            if (inventoryClickEvent.clickedInventory == null) return@listen
            if (inventoryClickEvent.currentItem == null) return@listen

            val slot: Int = inventoryClickEvent.slot
            if (inventoryClickEvent.clickedInventory!! != inventory) return@listen

            clickEvents[slot]?.accept(inventoryClickEvent)
            holder.playSound(holder, Sound.UI_BUTTON_CLICK, 1f, 1f)
        }
    }
}
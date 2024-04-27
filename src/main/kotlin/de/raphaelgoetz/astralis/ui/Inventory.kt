package de.raphaelgoetz.astralis.ui

import de.raphaelgoetz.astralis.ui.builder.DisplayInventoryBuilder
import de.raphaelgoetz.astralis.ui.builder.InventoryBuilder
import de.raphaelgoetz.astralis.ui.builder.SmartClick
import de.raphaelgoetz.astralis.ui.data.InventoryRows
import de.raphaelgoetz.astralis.ui.data.InventorySlots
import net.kyori.adventure.text.Component
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

/**
 * Creates an inventory for a specific player.
 * @param title of the inventory.
 * @param rows of the inventory. Used InventoryRows as easier way then doing 5*9 or something.
 * @param builder for the inventory properties. Contains methods for setting/adding/removing items.
 */
inline fun Player.createInventory(
    javaPlugin: JavaPlugin,
    title: Component,
    rows: InventoryRows = InventoryRows.ROW6,
    crossinline builder: InventoryBuilder.() -> Unit = {}
) = InventoryBuilder(javaPlugin, title, this, rows).apply(builder)

/**
 * Creates an inventory for a specific player and automatically opens it.
 * @param title of the inventory.
 * @param rows of the inventory. Used InventoryRows as easier way then doing 5*9 or something.
 * @param builder for the inventory properties. Contains methods for setting/adding/removing items.
 */
inline fun Player.openInventory(
    javaPlugin: JavaPlugin,
    title: Component,
    rows: InventoryRows = InventoryRows.ROW6,
    crossinline builder: InventoryBuilder.() -> Unit = {}
) = createInventory(javaPlugin, title, rows, builder).open()

/**
 * Creates a page-inventory for a specific player.
 * @param title of the inventory.
 * @param rows of the inventory. Used InventoryRows as easier way then doing 5*9 or something.
 * @param list of the items to display.
 * @param from (starting point) to display the items inside the list.
 * @param to (ending point, inclusive) to display the items inside the list.
 * @param builder for the inventory properties. Contains methods for setting/adding/removing items.
 */
inline fun Player.createPageInventory(
    javaPlugin: JavaPlugin,
    title: Component,
    rows: InventoryRows = InventoryRows.ROW6,
    list: List<SmartClick> = emptyList(),
    from: InventorySlots = InventorySlots.SLOT1ROW1,
    to: InventorySlots = InventorySlots.SLOT9ROW6,
    crossinline builder: DisplayInventoryBuilder.() -> Unit = {}
) = DisplayInventoryBuilder(javaPlugin, title, this, rows, list, from, to).apply(builder)

/**
 * Creates a page-inventory for a specific player and automatically opens it.
 * @param title of the inventory.
 * @param rows of the inventory. Used InventoryRows as easier way then doing 5*9 or something.
 * @param list of the items to display.
 * @param from (starting point) to display the items inside the list.
 * @param to (ending point, inclusive) to display the items inside the list.
 * @param builder for the inventory properties. Contains methods for setting/adding/removing items.
 */
inline fun Player.openPageInventory(
    javaPlugin: JavaPlugin,
    title: Component,
    rows: InventoryRows = InventoryRows.ROW6,
    list: List<SmartClick> = emptyList(),
    from: InventorySlots = InventorySlots.SLOT1ROW1,
    to: InventorySlots = InventorySlots.SLOT9ROW6,
    crossinline builder: DisplayInventoryBuilder.() -> Unit = {}
) = DisplayInventoryBuilder(javaPlugin, title, this, rows, list, from, to).apply(builder).open()
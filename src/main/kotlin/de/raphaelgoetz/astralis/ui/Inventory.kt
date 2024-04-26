package de.raphaelgoetz.astralis.ui

import de.raphaelgoetz.astralis.ui.builder.DisplayInventoryBuilder
import de.raphaelgoetz.astralis.ui.builder.InventoryBuilder
import de.raphaelgoetz.astralis.ui.builder.SmartClick
import de.raphaelgoetz.astralis.ui.data.InventoryRows
import de.raphaelgoetz.astralis.ui.data.InventorySlots
import net.kyori.adventure.text.Component
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

inline fun Player.createInventory(
    javaPlugin: JavaPlugin,
    title: Component,
    rows: InventoryRows = InventoryRows.ROW6,
    crossinline builder: InventoryBuilder.() -> Unit = {}
) = InventoryBuilder(javaPlugin, title, this, rows).apply(builder)

inline fun Player.openInventory(
    javaPlugin: JavaPlugin,
    title: Component,
    rows: InventoryRows = InventoryRows.ROW6,
    crossinline builder: InventoryBuilder.() -> Unit = {}
) = createInventory(javaPlugin, title, rows, builder).open()

inline fun Player.createPageInventory(
    javaPlugin: JavaPlugin,
    title: Component,
    rows: InventoryRows = InventoryRows.ROW6,
    list: List<SmartClick> = emptyList(),
    from: InventorySlots = InventorySlots.SLOT1ROW1,
    to: InventorySlots = InventorySlots.SLOT9ROW6,
    crossinline builder: DisplayInventoryBuilder.() -> Unit = {}
) = DisplayInventoryBuilder(javaPlugin, title, this, rows, list, from, to).apply(builder)

inline fun Player.openPageInventory(
    javaPlugin: JavaPlugin,
    title: Component,
    rows: InventoryRows = InventoryRows.ROW6,
    list: List<SmartClick> = emptyList(),
    from: InventorySlots = InventorySlots.SLOT1ROW1,
    to: InventorySlots = InventorySlots.SLOT9ROW6,
    crossinline builder: DisplayInventoryBuilder.() -> Unit = {}
) = DisplayInventoryBuilder(javaPlugin, title, this, rows, list, from, to).apply(builder).open()
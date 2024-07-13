package de.raphaelgoetz.astralis.ui

import de.raphaelgoetz.astralis.text.components.adventureText
import de.raphaelgoetz.astralis.text.translation.getValue
import de.raphaelgoetz.astralis.ux.color.Colorization
import de.raphaelgoetz.astralis.ui.builder.DisplayInventoryBuilder
import de.raphaelgoetz.astralis.ui.builder.InventoryBuilder
import de.raphaelgoetz.astralis.ui.builder.SmartClick
import de.raphaelgoetz.astralis.ui.data.InventoryRows
import de.raphaelgoetz.astralis.ui.data.InventorySlots

import net.kyori.adventure.text.Component
import org.bukkit.entity.Player

inline fun Player.createTransInventory(
    key: String,
    fallback: String = "",
    rows: InventoryRows = InventoryRows.ROW6,
    crossinline builder: InventoryBuilder.() -> Unit = {}
) = InventoryBuilder(this.translateTitle(key, fallback), this, rows).apply(builder)

inline fun Player.openTransInventory(
    key: String,
    fallback: String = "",
    rows: InventoryRows = InventoryRows.ROW6,
    crossinline builder: InventoryBuilder.() -> Unit = {}
) = createInventory(this.translateTitle(key, fallback), rows, builder).open()

inline fun Player.createTransPageInventory(
    key: String,
    fallback: String = "",    rows: InventoryRows = InventoryRows.ROW6,
    list: List<SmartClick> = emptyList(),
    from: InventorySlots = InventorySlots.SLOT1ROW1,
    to: InventorySlots = InventorySlots.SLOT9ROW6,
    crossinline builder: DisplayInventoryBuilder.() -> Unit = {}
) = DisplayInventoryBuilder(this.translateTitle(key, fallback), this, rows, list, from, to).apply(builder)

inline fun Player.openTransPageInventory(
    key: String,
    fallback: String = "",
    rows: InventoryRows = InventoryRows.ROW6,
    list: List<SmartClick> = emptyList(),
    from: InventorySlots = InventorySlots.SLOT1ROW1,
    to: InventorySlots = InventorySlots.SLOT9ROW6,
    crossinline builder: DisplayInventoryBuilder.() -> Unit = {}
) = DisplayInventoryBuilder(this.translateTitle(key, fallback), this, rows, list, from, to).apply(builder).open()

fun Player.translateTitle(key: String, fallback: String): Component {
    val text = this.locale().getValue(key, fallback)
    return adventureText(text) {
        color = Colorization.GRAY
        underlined(true)
    }
}
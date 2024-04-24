package de.raphaelgoetz.astralis.ui.builder

import de.raphaelgoetz.astralis.items.builder.SmartItem
import org.bukkit.event.inventory.InventoryClickEvent
import java.util.function.Consumer

data class SmartClick(
    val item: SmartItem,
    val action: Consumer<InventoryClickEvent>
)
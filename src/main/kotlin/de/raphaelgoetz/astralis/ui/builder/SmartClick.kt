package de.raphaelgoetz.astralis.ui.builder

import de.raphaelgoetz.astralis.items.builder.SmartItem
import org.bukkit.event.inventory.InventoryClickEvent
import java.util.function.Consumer

/**
 * Wrapper class to contain an SmartItem and a consumer.
 * @param item that contains an ItemStack and a InteractionType.
 * @param action is the consumer of an inventory-click-event that gets called when the item gets clicked.
 */
data class SmartClick(
    val item: SmartItem,
    val action: Consumer<InventoryClickEvent>
)
package de.raphaelgoetz.astralis.ui.builder

import com.google.common.collect.Lists
import de.raphaelgoetz.astralis.items.builder.SmartItem
import de.raphaelgoetz.astralis.items.data.InteractionType
import de.raphaelgoetz.astralis.ui.data.InventoryRows
import de.raphaelgoetz.astralis.ui.data.InventorySlots
import net.kyori.adventure.text.Component
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import java.util.function.Consumer

/**
 * Builder for the page-inventory.
 * @param title of the inventory.
 * @param holder of the current inventory.
 * @param rows of the inventory. Used InventoryRows as easier way then doing 5*9 or something.
 * @param list of the items to display.
 * @param from (starting point) to display the items inside the list.
 * @param to (ending point, inclusive) to display the items inside the list.
 */

/**
 * Inventory that supports paged item display with automatic
 * deactivated page buttons when no page is available.
 */
class DisplayInventoryBuilder(
    title: Component,
    holder: Player,
    rows: InventoryRows = InventoryRows.ROW6,
    private val list: List<SmartClick>,
    private val from: InventorySlots,
    private val to: InventorySlots
) : InventoryBuilder(title, holder, rows) {

    private val maxItems: Int = (to.value + 1) - from.value
    private val pages: List<List<SmartClick>> = if (list.isEmpty()) listOf() else Lists.partition(list, maxItems)
    private val maxPage: Int = if (pages.isEmpty()) 1 else pages.count()
    private var currentPageIndex = 0

    // Navigation button data
    private var leftSlot: InventorySlots? = null
    private var rightSlot: InventorySlots? = null
    private var leftActiveItem: SmartItem? = null
    private var rightActiveItem: SmartItem? = null
    private var leftInactiveItem: SmartItem? = null
    private var rightInactiveItem: SmartItem? = null

    init {
        applyPage()
    }

    /** Updates inventory display for the current page. */
    private fun applyPage() {
        // Clear relevant slots
        for (slotIndex in from.value..to.value) {
            removeSlot(InventorySlots.values().first { it.value == slotIndex })
        }

        if (pages.isEmpty()) return

        val currentPage = pages[currentPageIndex]

        // Fill the inventory range with current page items
        for ((i, slotIndex) in (from.value..to.value).withIndex()) {
            if (i >= currentPage.size) break
            val currentItem = currentPage[i]
            setSlot(slotIndex, currentItem.item, currentItem.action)
        }

        // Handle left page button
        leftSlot?.let { slot ->
            if (currentPageIndex > 0) {
                leftActiveItem?.let {
                    setBlockedSlot(slot, it, Consumer { _ ->
                        currentPageIndex--
                        applyPage()
                    })
                }
            } else {
                leftInactiveItem?.let { setBlockedSlot(slot, it) }
            }
        }

        // Handle right page button
        rightSlot?.let { slot ->
            if (currentPageIndex < maxPage - 1) {
                rightActiveItem?.let {
                    setBlockedSlot(slot, it, Consumer { _ ->
                        currentPageIndex++
                        applyPage()
                    })
                }
            } else {
                rightInactiveItem?.let { setBlockedSlot(slot, it) }
            }
        }
    }

    /**
     * Sets the left (previous page) navigation button.
     *
     * @param slot inventory slot to place the button
     * @param activeDisplay item shown when a previous page exists
     * @param inactiveDisplay item shown when on the first page
     */
    fun pageLeft(slot: InventorySlots, activeDisplay: ItemStack, inactiveDisplay: ItemStack) {
        leftSlot = slot
        leftActiveItem = SmartItem(activeDisplay, InteractionType.PAGE_TURN)
        leftInactiveItem = SmartItem(inactiveDisplay, InteractionType.ERROR)
        applyPage()
    }

    /**
     * Sets the right (next page) navigation button.
     *
     * @param slot inventory slot to place the button
     * @param activeDisplay item shown when a next page exists
     * @param inactiveDisplay item shown when on the last page
     */
    fun pageRight(slot: InventorySlots, activeDisplay: ItemStack, inactiveDisplay: ItemStack) {
        rightSlot = slot
        rightActiveItem = SmartItem(activeDisplay, InteractionType.PAGE_TURN)
        rightInactiveItem = SmartItem(inactiveDisplay, InteractionType.ERROR)
        applyPage()
    }

    /**
     * Shows a display item when there are no items at all.
     */
    fun emptyPage(slot: InventorySlots, display: ItemStack) {
        if (list.isEmpty()) {
            setBlockedSlot(slot, SmartItem(display, InteractionType.ERROR))
        }
    }
}


package de.raphaelgoetz.astralis.ui.builder

import com.google.common.collect.Lists
import de.raphaelgoetz.astralis.items.builder.SmartItem
import de.raphaelgoetz.astralis.items.data.InteractionType
import de.raphaelgoetz.astralis.ui.data.InventoryRows
import de.raphaelgoetz.astralis.ui.data.InventorySlots
import net.kyori.adventure.text.Component
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

/**
 * Builder for the page-inventory.
 * @param title of the inventory.
 * @param holder of the current inventory.
 * @param rows of the inventory. Used InventoryRows as easier way then doing 5*9 or something.
 * @param list of the items to display.
 * @param from (starting point) to display the items inside the list.
 * @param to (ending point, inclusive) to display the items inside the list.
 */
class DisplayInventoryBuilder(
    title: Component,
    holder: Player,
    rows: InventoryRows = InventoryRows.ROW6,
    list: List<SmartClick>,
    private val from: InventorySlots,
    private val to: InventorySlots
) : InventoryBuilder(title, holder, rows) {

    //to should be inclusive. That's why to + 1
    private val maxItems: Int = (to.value + 1) - from.value
    private val pages = Lists.partition(list, maxItems)
    private val maxPage = pages.count()

    private var currentPageIndex = 0

    init {
        applyPage()
    }

    private fun applyPage() {

        val currentPage = pages[currentPageIndex]

        for (index in from.value until (to.value + 1)) {
            if (index >= currentPage.size) break
            val currentItem = currentPage[index]
            setSlot(index, currentItem.item, currentItem.action)
        }
    }

    /**
     * Sets the item to change the page to the left.
     * @param slot where the item gets set.
     * @param display of the item for page-changing.
     */
    fun pageLeft(slot: InventorySlots, display: ItemStack) {
        this.setBlockedSlot(slot, SmartItem(display, InteractionType.PAGE_TURN)) {
            if (currentPageIndex - 1 < 0) {
                currentPageIndex = (maxPage - 1)
                applyPage()
                return@setBlockedSlot
            }
            currentPageIndex--
            applyPage()
        }
    }

    /**
     * Sets the item to change the page to the right.
     * @param slot where the item gets set.
     * @param display of the item for page-changing.
     */
    fun pageRight(slot: InventorySlots, display: ItemStack) {
        this.setBlockedSlot(slot, SmartItem(display, InteractionType.PAGE_TURN)) {
            if (currentPageIndex + 1 >= maxPage) {
                currentPageIndex = 0
                applyPage()
                return@setBlockedSlot
            }
            currentPageIndex++
            applyPage()
        }
    }
}
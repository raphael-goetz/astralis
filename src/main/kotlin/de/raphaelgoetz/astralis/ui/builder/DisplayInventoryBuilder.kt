package de.raphaelgoetz.astralis.ui.builder

import com.google.common.collect.Lists
import de.raphaelgoetz.astralis.items.builder.SmartItem
import de.raphaelgoetz.astralis.ui.data.InventoryRows
import de.raphaelgoetz.astralis.ui.data.InventorySlots
import net.kyori.adventure.text.Component
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

class DisplayInventoryBuilder(
    javaPlugin: JavaPlugin,
    title: Component,
    holder: Player,
    rows: InventoryRows = InventoryRows.ROW6,
    list: List<SmartClick>,
    private val from: InventorySlots,
    private val to: InventorySlots
) : InventoryBuilder(javaPlugin, title, holder, rows) {

    private val maxItems = from.value - to.value
    private val pages = Lists.partition(list, maxItems)
    private val maxPage = pages.count()

    private var currentPageIndex = 0

    init {
        applyPage()
    }

    private fun applyPage() {

        val currentPage = pages[currentPageIndex]
        var currentItemIndex = 0

        for (index in from.value until to.value) {
            val currentItem = currentPage[currentPageIndex]
            setSlot(index, currentItem.item, currentItem.action)
            currentItemIndex++
        }
    }

    fun pageLeft(slot: InventorySlots, smartItem: SmartItem) {
        this.setBlockedSlot(slot, smartItem) {
            if (currentPageIndex - 1 <= 0) return@setBlockedSlot
            currentPageIndex--
            applyPage()
        }
    }

    fun pageRight(slot: InventorySlots, smartItem: SmartItem) {
        this.setBlockedSlot(slot, smartItem) {
            if (currentPageIndex + 1 >= maxPage) return@setBlockedSlot
            currentPageIndex++
            applyPage()
        }
    }
}
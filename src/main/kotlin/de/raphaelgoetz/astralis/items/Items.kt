package de.raphaelgoetz.astralis.items

import de.raphaelgoetz.astralis.items.builder.SmartItem
import de.raphaelgoetz.astralis.items.builder.applyMeta
import de.raphaelgoetz.astralis.items.data.InteractionType
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta

/**
 * Will create a SmartItem used for building an inventory.
 * @param name is the String which gets used for the display-name.
 * @param material is the Material of the resulting ItemStack.
 * @param description is the String which gets used for the lore.
 * @param tagResolver are possible resolvers for placeholder.
 * @param interactionType is the container of styling & sound.
 * @param builder contains the itemMeta properties which are getting applied.
 * @see SmartItem
 */
inline fun <reified T : ItemMeta> createSmartItem(
    name: String,
    material: Material,
    description: String = "",
    tagResolver: List<TagResolver> = emptyList(),
    interactionType: InteractionType = InteractionType.CLICK,
    crossinline builder: T.() -> Unit = {}
): SmartItem {
    val itemStack = ItemStack(material)
    itemStack.applyMeta<T>(name, description, tagResolver, interactionType, builder)
    return SmartItem(itemStack, interactionType)
}

/**
 * Will create a SmartItem used for building an inventory. Without defining a custom meta!
 * @param name is the String which gets used for the display-name.
 * @param material is the Material of the resulting ItemStack.
 * @param description is the String which gets used for the lore.
 * @param tagResolver are possible resolvers for placeholder.
 * @param interactionType is the container of styling & sound.
 * @param builder contains the itemMeta properties which are getting applied.
 * @see SmartItem
 */
inline fun smartItemWithoutMeta(
    name: String,
    material: Material,
    description: String = "",
    tagResolver: List<TagResolver> = emptyList(),
    interactionType: InteractionType = InteractionType.CLICK,
    crossinline builder: ItemMeta.() -> Unit = {}
): SmartItem = createSmartItem<ItemMeta>(name, material, description, tagResolver, interactionType, builder)

/**
 * Will create a ItemStack.
 * @param material is the Material of the resulting ItemStack.
 * @param builder contains the itemMeta properties which will be applied.
 */
inline fun <reified T : ItemMeta> basicItem(
    material: Material,
    crossinline builder: T.() -> Unit = {}
): ItemStack {
    val itemStack = ItemStack(material)
    val meta = itemStack.itemMeta as T
    meta.apply(builder)
    itemStack.itemMeta = meta
    return itemStack
}

/**
 * Will create a ItemStack. Without defining a custom meta!
 * @param material is the Material of the resulting ItemStack.
 * @param builder contains the itemMeta properties which will be applied.
 */
inline fun basicItemWithoutMeta(
    material: Material,
    crossinline builder: ItemMeta.() -> Unit = {}
): ItemStack = basicItem<ItemMeta>(material, builder)
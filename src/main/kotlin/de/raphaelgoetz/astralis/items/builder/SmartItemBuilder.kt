package de.raphaelgoetz.astralis.items.builder

import de.raphaelgoetz.astralis.annotations.InternalUse
import de.raphaelgoetz.astralis.items.data.InteractionType
import de.raphaelgoetz.astralis.text.components.adventureText
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta

/**
 * Wrapper that contains the user-feedback and the corresponding item.
 * @param itemStack is the item which gets styled after the InteractionType.
 * @param interactionType contains styling and the sound.
 */
data class SmartItem(
    val itemStack: ItemStack,
    val interactionType: InteractionType
)

/**
 * Applies the given itemMeta to the given ItemStack.
 * @param name is the String which gets used for the display-name.
 * @param description is the String which gets used for the lore.
 * @param tagResolver are possible resolvers for placeholder.
 * @param interactionType is the container of styling & sound.
 * @param builder contains the itemMeta properties which are getting applied.
 */
@InternalUse
inline fun <reified T : ItemMeta> ItemStack.applyMeta(
    name: String,
    description: String,
    tagResolver: List<TagResolver>,
    interactionType: InteractionType,
    builder: T.() -> Unit = {}
) {
    val meta = this.itemMeta as T
    meta.apply { builder(this) }
    val title = adventureText(name) {

        if (interactionType == InteractionType.DISPLAY_CLICK) {
            underlined(true)
        }

        italic(false)
        color = interactionType.color
        resolver = tagResolver.toTypedArray()
    }

    meta.setDisplayName(LegacyComponentSerializer.legacySection().serialize(title))

    val currentDescription = smartestLoreBuilder(description)
    if (currentDescription.isNotEmpty()) {
        val stringLore = currentDescription.map { LegacyComponentSerializer.legacySection().serialize(it) }
        meta.lore = stringLore
    }
    this.itemMeta = meta
}
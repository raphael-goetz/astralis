package de.raphaelgoetz.astralis.items.builder

import com.google.common.collect.Lists
import de.raphaelgoetz.astralis.annotations.InternalUse
import de.raphaelgoetz.astralis.text.communication.CommunicationType
import de.raphaelgoetz.astralis.text.components.ComponentBuilder
import de.raphaelgoetz.astralis.text.components.adventureText
import net.kyori.adventure.text.Component

/**
 * @param description is a possible lists of Components, if the lore of the current item should be edited.
 */
class SmartLoreBuilder(
    private val description: MutableList<Component> = mutableListOf()
) {

    fun removeLast() = description.removeLast()

    /**
     * Inserts a component at the start.
     * @param component that will be inserted.
     */
    fun insertFirst(component: Component) = description.addFirst(component)

    /**
     * Inserts a component at the end.
     * @param component that will be inserted.
     */
    fun insertLast(component: Component) = description.addLast(component)

    /**
     * Adds a component at the description.
     * @param component that will be added.
     */
    fun addLore(component: Component) = description.add(component)

    /**
     * Adds a component at the description.
     * @param text that will be used for component-creation.
     * @param builder contains the component properties.
     */
    fun addLore(
        text: String,
        builder: ComponentBuilder.() -> Unit
    ) = description.add(ComponentBuilder(text).apply(builder).build().component)

    /**
     * Removes a specific lore by index.
     * @param index that will be removed.
     */
    fun removeLore(index: Int) = description.removeAt(index)

    fun build(): List<Component> = description

}

/**
 * Returns stylized & formated component-list as a lore
 * @param input is the text that will be converted
 */
fun smartestLoreBuilder(input: String): List<Component> {
    if (input.isEmpty()) return emptyList()

    val strings = input.split(" ")
    if (strings.size <= 5) return listOf(input.stringToStyledLore())
    val subList = Lists.partition(strings, 4)
    val result = mutableListOf<Component>()

    subList.forEach { list ->
        val alreadyAString = buildString { list.forEach { append("$it ") } }
        result.add(alreadyAString.stringToStyledLore())
    }

    return result
}

/**
 * Styles a string into a component
 */
@InternalUse
private fun String.stringToStyledLore(): Component {
    return adventureText("| $this") {
        type = CommunicationType.NONE
    }
}
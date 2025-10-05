package de.raphaelgoetz.astralis.text.components

import de.raphaelgoetz.astralis.annotations.InternalUse
import de.raphaelgoetz.astralis.text.communication.CommunicationType
import de.raphaelgoetz.astralis.ux.color.Colorization

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.event.ClickEvent
import net.kyori.adventure.text.event.HoverEvent
import net.kyori.adventure.text.format.TextDecoration
import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver

/**
 * based on https://docs.advntr.dev/text.html
 */

/**
 * @param text is the default text that the component is build on.
 * @param builder is the builder to style the component.
 * @see ComponentBuilder
 * @return an adventure component.
 */
inline fun adventureText(
    text: String,
    builder: ComponentBuilder.() -> Unit = {}
) = adventureMessage(text, builder).component

/**
 * Creates a AdventureMessage (wrapper that contains a component & communication-type)
 * @param text of the message.
 * @param builder that contains the properties of the message.
 * @return the created AdventureMessage.
 */
inline fun adventureMessage(
    text: String,
    builder: ComponentBuilder.() -> Unit = {}
) = ComponentBuilder(text).apply(builder).build()

/**
 * @param input is the default text that the component is build on.
 */
@InternalUse
class ComponentBuilder(private val input: String) {

    var currentStyles = mutableSetOf<TextDecoration>()
    private var removeStyles = mutableSetOf<TextDecoration>()

    /*
     * Events
     */
    var clickEvent: ClickEvent? = null
    var hoverEvent: HoverEvent<*>? = null

    /*
     * Type, color, mode
     */
    var type: CommunicationType? = null
    var color: Colorization? = null
    var resolver: Array<out TagResolver>? = null
    var renderMode: RenderMode = RenderMode.DEFAULT

    /*
     * Style helpers
     */
    fun italic(enabled: Boolean) = toggleDecoration(TextDecoration.ITALIC, enabled)
    fun bold(enabled: Boolean) = toggleDecoration(TextDecoration.BOLD, enabled)
    fun strikethrough(enabled: Boolean) = toggleDecoration(TextDecoration.STRIKETHROUGH, enabled)
    fun underlined(enabled: Boolean) = toggleDecoration(TextDecoration.UNDERLINED, enabled)
    fun obfuscated(enabled: Boolean) = toggleDecoration(TextDecoration.OBFUSCATED, enabled)

    private fun toggleDecoration(decoration: TextDecoration, enabled: Boolean) {
        if (enabled) currentStyles.add(decoration) else removeStyles.add(decoration)
    }

    /*
     * Click / Hover event helpers
     */
    fun onOpenURL(url: String) = applyClickEvent(ClickEvent.openUrl(url))
    fun onOpenFile(file: String) = applyClickEvent(ClickEvent.openFile(file))
    fun onRunCommand(command: String) = applyClickEvent(ClickEvent.runCommand(command))
    fun onSuggestCommand(command: String) = applyClickEvent(ClickEvent.suggestCommand(command))
    fun onCopyClipboard(text: String) = applyClickEvent(ClickEvent.copyToClipboard(text))
    private fun applyClickEvent(event: ClickEvent) { this.clickEvent = event }
    fun onHoverText(text: Component) { this.hoverEvent = HoverEvent.showText(text) }

    /*
     * Builder core
     */
    fun build(): AdventureMessage {
        var textComponent =
            if (resolver == null) MiniMessage.miniMessage().deserialize(input)
            else MiniMessage.miniMessage().deserialize(input, *resolver!!)

        var styleBuilder = textComponent.style()

        // Apply decorations
        currentStyles.forEach { styleBuilder = styleBuilder.decoration(it, true) }
        removeStyles.forEach { styleBuilder = styleBuilder.decoration(it, false) }

        // Apply events
        clickEvent?.let { styleBuilder = styleBuilder.clickEvent(it) }
        hoverEvent?.let { styleBuilder = styleBuilder.hoverEvent(hoverEvent) }
        color?.let { styleBuilder = styleBuilder.color(it.rgbLike) }

        // 🔹 Apply lore-specific rules
        if (renderMode == RenderMode.LORE) {
            styleBuilder = styleBuilder
                .decoration(TextDecoration.ITALIC, false) // disable italics
            if (color == null && type != null) {
                styleBuilder = styleBuilder.color(type!!.color.rgbLike)
            }
        }

        // Apply communication type
        type?.let {
            styleBuilder = styleBuilder.color(it.color.rgbLike)
            textComponent = Component.text(it.icon).append(textComponent)
        }


        return AdventureMessage(textComponent.style(styleBuilder).asComponent(), type?.sound)
    }
}

enum class RenderMode {
    DEFAULT, // normal behavior (chat, messages, etc.)
    LORE     // special mode for item descriptions
}
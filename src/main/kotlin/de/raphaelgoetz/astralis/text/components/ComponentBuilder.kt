package de.raphaelgoetz.astralis.text.components

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
 * @param text is the default text that the component is build on
 * @param builder is the builder to style the component
 * @see ComponentBuilder
 * @return an adventure component
 */
inline fun adventureText(
    text: String,
    builder: ComponentBuilder.() -> Unit = {}
) = ComponentBuilder(text).apply(builder).build()

/**
 * @param input is the default text that the component is build on
 */
class ComponentBuilder(private val input: String) {

    var currentStyles = mutableSetOf<TextDecoration>()

    /**
     * @see TextDecoration for available text decorations
     * possible are: italic, bold, strikethrough, underlined, obfuscated
     * @param enabled turns the decoration on/off
     */
    fun italic(enabled: Boolean) {
        if (enabled) addStyle(TextDecoration.ITALIC)
    }

    fun bold(enabled: Boolean) {
        if (enabled) addStyle(TextDecoration.BOLD)
    }

    fun strikethrough(enabled: Boolean) {
        if (enabled) addStyle(TextDecoration.STRIKETHROUGH)
    }

    fun underlined(enabled: Boolean) {
        if (enabled) addStyle(TextDecoration.UNDERLINED)
    }

    fun obfuscated(enabled: Boolean) {
        if (enabled) addStyle(TextDecoration.OBFUSCATED)
    }

    private fun addStyle(textDecoration: TextDecoration) {
        currentStyles.add(textDecoration)
    }

    /*
        Events
     */
    var clickEvent: ClickEvent? = null
    var hoverEvent: HoverEvent<*>? = null

    fun onOpenURL(url: String) = applyClickEvent(ClickEvent.openUrl(url))
    fun onOpenFile(file: String) = applyClickEvent(ClickEvent.openFile(file))
    fun onRunCommand(command: String) = applyClickEvent(ClickEvent.runCommand(command))
    fun onSuggestCommand(command: String) = applyClickEvent(ClickEvent.suggestCommand(command))
    fun onCopyClipboard(text: String) = applyClickEvent(ClickEvent.copyToClipboard(text))

    private fun applyClickEvent(event: ClickEvent) {
        this.clickEvent = event
    }

    fun onHoverText(text: Component) {
        this.hoverEvent = HoverEvent.showText(text)
    }

    /*
        Types
     */
    var type: CommunicationType? = null
    var color: Colorization? = null

    var resolver: Array<out TagResolver>? = null

    fun build(): Component {

        var textComponent =
            if (resolver == null) MiniMessage.miniMessage().deserialize(input)
            else MiniMessage.miniMessage().deserialize(input, *resolver!!)

        var styleBuilder = textComponent.style()

        currentStyles.forEach { styleBuilder = styleBuilder.decoration(it, true) }
        clickEvent?.let { styleBuilder = styleBuilder.clickEvent(it) }
        hoverEvent?.let { styleBuilder = styleBuilder.hoverEvent(hoverEvent) }
        color?.let { styleBuilder = styleBuilder.color(it.rgbLike) }

        type?.let {
            styleBuilder = styleBuilder.color(it.color.rgbLike)
            textComponent = Component.text(it.icon).append(textComponent)
        }

        return textComponent.style(styleBuilder).asComponent()
    }
}
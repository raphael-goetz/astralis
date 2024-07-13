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

    /**
     * @see TextDecoration for available text decorations.
     * possible are: italic, bold, strikethrough, underlined, obfuscated.
     * @param enabled turns the decoration on/off.
     */
    fun italic(enabled: Boolean) {
        if (enabled) addStyle(TextDecoration.ITALIC)
        else removeStyle(TextDecoration.ITALIC)
    }

    fun bold(enabled: Boolean) {
        if (enabled) addStyle(TextDecoration.BOLD)
        else removeStyle(TextDecoration.BOLD)
    }

    fun strikethrough(enabled: Boolean) {
        if (enabled) addStyle(TextDecoration.STRIKETHROUGH)
        else removeStyle(TextDecoration.STRIKETHROUGH)
    }

    fun underlined(enabled: Boolean) {
        if (enabled) addStyle(TextDecoration.UNDERLINED)
        else removeStyle(TextDecoration.UNDERLINED)
    }

    fun obfuscated(enabled: Boolean) {
        if (enabled) addStyle(TextDecoration.OBFUSCATED)
        else removeStyle(TextDecoration.OBFUSCATED)
    }

    private fun addStyle(textDecoration: TextDecoration) {
        currentStyles.add(textDecoration)
    }

    private fun removeStyle(textDecoration: TextDecoration) {
        removeStyles.add(textDecoration)
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

    fun build(): AdventureMessage {

        var textComponent =
            if (resolver == null) MiniMessage.miniMessage().deserialize(input)
            else MiniMessage.miniMessage().deserialize(input, *resolver!!)

        var styleBuilder = textComponent.style()

        currentStyles.forEach { styleBuilder = styleBuilder.decoration(it, true) }
        removeStyles.forEach { styleBuilder = styleBuilder.decoration(it, true) }

        clickEvent?.let { styleBuilder = styleBuilder.clickEvent(it) }
        hoverEvent?.let { styleBuilder = styleBuilder.hoverEvent(hoverEvent) }
        color?.let { styleBuilder = styleBuilder.color(it.rgbLike) }

        type?.let {
            styleBuilder = styleBuilder.color(it.color.rgbLike)
            textComponent = Component.text(it.icon).append(textComponent)
        }

        return AdventureMessage(textComponent.style(styleBuilder).asComponent(), type?.sound)
    }
}
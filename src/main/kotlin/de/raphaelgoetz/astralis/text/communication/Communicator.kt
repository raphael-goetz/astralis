package de.raphaelgoetz.astralis.text.communication

import de.raphaelgoetz.astralis.text.components.AdventureMessage
import de.raphaelgoetz.astralis.text.components.ComponentBuilder
import de.raphaelgoetz.astralis.text.components.adventureMessage
import de.raphaelgoetz.astralis.text.title.TitleBuilder

import org.bukkit.entity.Player

/**
 * Sends the player a component & plays a sound at the same time
 * @param text is the text that will be converted to a component that the player will receive
 * @param builder is the ComponentBuilder which contains the sound and the styling
 */
fun Player.sendText(
    text: String = "",
    builder: ComponentBuilder.() -> Unit = {}
) = this.sendText(adventureMessage(text, builder))

/**
 * Sends the player a component & plays a sound at the same time
 * @param adventureMessage is the component wrapper that contains the styled component and the message
 */
fun Player.sendText(adventureMessage: AdventureMessage) {
    this.sendMessage(adventureMessage.component)
    adventureMessage.sound?.let { sound -> this.playSound(this, sound, 1f, 1f) }
}

fun Player.sendTitle(
    title: String,
    subTitle: String,
    builder: TitleBuilder.() -> Unit = {}
) {
    val titleBuilder = TitleBuilder(title, subTitle)
    titleBuilder.builder()
    val message = titleBuilder.build()
    this.showTitle(message)
    titleBuilder.type?.sound?.let { this.playSound(this, it, 1f, 1f) }
}

fun Player.sendTitle(
    title: String,
    builder: TitleBuilder.() -> Unit = {}
) = this.sendTitle(title, "", builder)
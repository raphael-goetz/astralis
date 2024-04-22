package de.raphaelgoetz.astralis.text.communication

import de.raphaelgoetz.astralis.text.components.AdventureMessage
import de.raphaelgoetz.astralis.text.components.ComponentBuilder
import de.raphaelgoetz.astralis.text.components.adventureMessage
import de.raphaelgoetz.astralis.text.title.AdventureTitle
import de.raphaelgoetz.astralis.text.title.TitleBuilder
import de.raphaelgoetz.astralis.text.title.adventureTitle

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
 * @param adventureMessage is the component wrapper that contains the styled component and the sound
 */
fun Player.sendText(adventureMessage: AdventureMessage) {
    this.sendMessage(adventureMessage.component)
    adventureMessage.sound?.let { sound -> this.playSound(this, sound, 1f, 1f) }
}

/**
 * Sends the player a title & plays a sound at the same time
 * @param adventureTitle is the title wrapper that contains the styled title and the sound
 */
fun Player.sendTitle(adventureTitle: AdventureTitle) {
    this.showTitle(adventureTitle.title)
    adventureTitle.sound?.let { sound -> this.playSound(this, sound, 1f, 1f) }
}

/**
 * Sends the player a title & plays a sound at the same time
 * @param title is the content of the upper title
 * @param subTitle is the content of the lower title
 * @param builder is the TitleBuilder which contains the sound and the styling
 */
fun Player.sendTitle(
    title: String,
    subTitle: String = "",
    builder: TitleBuilder.() -> Unit = {}
) = this.sendTitle(adventureTitle(title, subTitle, builder))
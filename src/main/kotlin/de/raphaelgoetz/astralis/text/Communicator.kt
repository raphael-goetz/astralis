package de.raphaelgoetz.astralis.text

import de.raphaelgoetz.astralis.schedule.doAgain
import de.raphaelgoetz.astralis.schedule.doAgainAsync
import de.raphaelgoetz.astralis.text.components.AdventureMessage
import de.raphaelgoetz.astralis.text.components.ComponentBuilder
import de.raphaelgoetz.astralis.text.components.adventureMessage
import de.raphaelgoetz.astralis.text.components.adventureText
import de.raphaelgoetz.astralis.text.title.AdventureTitle
import de.raphaelgoetz.astralis.text.title.TitleBuilder
import de.raphaelgoetz.astralis.text.title.adventureTitle
import net.kyori.adventure.text.Component

import org.bukkit.entity.Player

/**
 * Sends the player a component & plays a sound at the same time.
 * @param text is the text that will be converted to a component that the player will receive.
 * @param builder is the ComponentBuilder which contains the sound and the styling.
 */
fun Player.sendText(
    text: String = "",
    builder: ComponentBuilder.() -> Unit = {}
) = this.sendText(adventureMessage(text, builder))

/**
 * Sends the player a component & plays a sound at the same time.
 * @param adventureMessage is the component wrapper that contains the styled component and the sound.
 */
fun Player.sendText(adventureMessage: AdventureMessage) {
    this.sendMessage(adventureMessage.component)
    adventureMessage.sound?.let { sound -> this.playSound(this, sound, 1f, 1f) }
}

/**
 * Sends the player a title & plays a sound at the same time.
 * @param adventureTitle is the title wrapper that contains the styled title and the sound.
 */
fun Player.sendTitle(adventureTitle: AdventureTitle) {
    this.showTitle(adventureTitle.title)
    adventureTitle.sound?.let { sound -> this.playSound(this, sound, 1f, 1f) }
}

/**
 * Sends the player a title & plays a sound at the same time.
 * @param title is the content of the upper title.
 * @param subTitle is the content of the lower title.
 * @param builder is the TitleBuilder which contains the sound and the styling.
 */
fun Player.sendTitle(
    title: String,
    subTitle: String = "",
    builder: TitleBuilder.() -> Unit = {}
) = this.sendTitle(adventureTitle(title, subTitle, builder))

/**
 * Sends an actionbar infinite. Therefore, creating a task to repeat sending the actionbar.
 * @param component that will be sent as action-bar.
 */
fun Player.showActionbar(component: Component) {
    doAgainAsync(0, 3) {
        this@showActionbar.sendActionBar(component)
    }
}

/**
 * Sends an actionbar infinite. Therefore, creating a task to repeat sending the actionbar.
 * @param text that will be sent as action-bar.
 * @param builder that will style the text by the given properties.
 */
inline fun Player.showActionbar(
    text: String,
    crossinline builder: ComponentBuilder.() -> Unit = {}
) = this.showActionbar(adventureText(text, builder))

/**
 * Sends an actionbar infinite. Therefore, creating a task to repeat sending the actionbar.
 * @param textList list of components that will be switched after one sending.
 */
fun Player.showActionBars(textList: List<Component>) {
    var textIndex = 0

    doAgain(0, 3) {
        if (textIndex >= textList.size) textIndex = 0
        this@showActionBars.sendActionBar(textList[textIndex])
        textIndex++
    }
}

/**
 * Sends an actionbar infinite. Therefore, creating a task to repeat sending the actionbar.
 * @param textList list of strings that will be switched after one sending.
 * @param builder that will style the text by the given properties.
 */
inline fun Player.sendActionBars(
    textList: List<String>,
    crossinline builder: ComponentBuilder.() -> Unit = {}
) {
    val componentList = textList.map { ComponentBuilder(it).apply(builder).build().component }
    this.showActionBars(componentList)
}
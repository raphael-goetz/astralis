package de.raphaelgoetz.astralis.text.communication

import de.raphaelgoetz.astralis.text.components.ComponentBuilder
import de.raphaelgoetz.astralis.text.components.adventureText
import de.raphaelgoetz.astralis.text.title.TitleBuilder

import org.bukkit.entity.Player

fun Player.sendText(
    text: String = "",
    builder: ComponentBuilder.() -> Unit = {}
) {
    val componentBuilder = ComponentBuilder(text)
    componentBuilder.builder()
    componentBuilder.type?.sound?.let { this.playSound(this, it, 1f, 1f) }
    this.sendMessage(adventureText(text, builder))
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
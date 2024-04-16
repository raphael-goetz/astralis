package de.raphaelgoetz.astralis.text.title

import de.raphaelgoetz.astralis.text.communication.CommunicationType
import de.raphaelgoetz.astralis.text.components.adventureText
import de.raphaelgoetz.astralis.text.title.configuration.TitleAppearAnimation
import de.raphaelgoetz.astralis.text.title.configuration.TitleType
import net.kyori.adventure.text.format.TextDecoration
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver
import net.kyori.adventure.title.Title
import net.kyori.adventure.title.Title.Times
import java.time.Duration

inline fun adventureTitle(
    title: String,
    subtitle: String,
    builder: TitleBuilder.() -> Unit
) = TitleBuilder(title, subtitle).apply(builder).build()

class TitleBuilder(
    private val title: String,
    private val subTitle: String
) {

    private val currentStyles = mutableMapOf<TitleType, MutableSet<TextDecoration>>()

    /*
        Styling components
     */
    fun italic(titleType: TitleType, enabled: Boolean = true) {
        if (enabled) addStyle(titleType, TextDecoration.ITALIC)
    }

    fun bold(titleType: TitleType, enabled: Boolean = true) {
        if (enabled) addStyle(titleType, TextDecoration.BOLD)
    }

    fun strikethrough(titleType: TitleType, enabled: Boolean = true) {
        if (enabled) addStyle(titleType, TextDecoration.STRIKETHROUGH)
    }

    fun underlined(titleType: TitleType, enabled: Boolean = true) {
        if (enabled) addStyle(titleType, TextDecoration.UNDERLINED)
    }

    fun obfuscated(titleType: TitleType, enabled: Boolean = true) {
        if (enabled) addStyle(titleType, TextDecoration.OBFUSCATED)
    }

    private fun addStyle(titleType: TitleType, textDecoration: TextDecoration) {
        when (titleType) {
            TitleType.UP -> currentStyles.computeIfAbsent(TitleType.UP) { mutableSetOf() }.add(textDecoration)
            TitleType.DOWN -> currentStyles.computeIfAbsent(TitleType.DOWN) { mutableSetOf() }.add(textDecoration)
            TitleType.BOTH -> {
                currentStyles.computeIfAbsent(TitleType.UP) { mutableSetOf() }.add(textDecoration)
                currentStyles.computeIfAbsent(TitleType.DOWN) { mutableSetOf() }.add(textDecoration)
            }
        }
    }

    var animation: TitleAppearAnimation? = null
    var tagResolver: Array<TagResolver>? = null
    var type: CommunicationType? = null

    var duration: Long = 3

    fun build(): Title {
        val titleComponent = adventureText(title) {
            type = this@TitleBuilder.type
            if (this@TitleBuilder.currentStyles[TitleType.UP].isNullOrEmpty()) return@adventureText
            currentStyles = this@TitleBuilder.currentStyles[TitleType.UP]!!
        }

        val subtitleComponent = adventureText(subTitle) {
            type = CommunicationType.NONE
            if (this@TitleBuilder.currentStyles[TitleType.DOWN].isNullOrEmpty()) return@adventureText
            currentStyles = this@TitleBuilder.currentStyles[TitleType.DOWN]!!
        }

        val time = Times.times(Duration.ZERO, Duration.ofSeconds(duration), Duration.ZERO)
        return Title.title(titleComponent, subtitleComponent, time)
    }

}
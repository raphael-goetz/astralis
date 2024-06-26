package de.raphaelgoetz.astralis.text.title

import de.raphaelgoetz.astralis.annotations.InternalUse
import de.raphaelgoetz.astralis.text.communication.CommunicationType
import de.raphaelgoetz.astralis.text.components.adventureText
import de.raphaelgoetz.astralis.text.title.configuration.TitleType

import net.kyori.adventure.text.format.TextDecoration
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver
import net.kyori.adventure.title.Title
import net.kyori.adventure.title.Title.Times
import java.time.Duration

/**
 * Will create a styled adventure Title.
 * @param title is the upper text of the title.
 * @param subtitle is the lower text of the title.
 * @param builder is the builder to style the title.
 * @return an adventure Title
 */
inline fun adventureTitle(
    title: String,
    subtitle: String = "",
    builder: TitleBuilder.() -> Unit
) = TitleBuilder(title, subtitle).apply(builder).build()

/**
 * @param title is the upper text of the title
 * @param subTitle is the lower text of the title
 */
@InternalUse
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

    var resolver: Array<TagResolver>? = null
    var type: CommunicationType? = null

    var duration: Long = 3

    fun build(): AdventureTitle {
        val titleComponent = adventureText(title) {
            resolver = this@TitleBuilder.resolver
            type = this@TitleBuilder.type
            if (this@TitleBuilder.currentStyles[TitleType.UP].isNullOrEmpty()) return@adventureText
            currentStyles = this@TitleBuilder.currentStyles[TitleType.UP]!!
        }

        val subtitleComponent = adventureText(subTitle) {
            resolver = this@TitleBuilder.resolver
            type = CommunicationType.NONE
            if (this@TitleBuilder.currentStyles[TitleType.DOWN].isNullOrEmpty()) return@adventureText
            currentStyles = this@TitleBuilder.currentStyles[TitleType.DOWN]!!
        }

        val time = Times.times(Duration.ZERO, Duration.ofSeconds(duration), Duration.ZERO)
        val title = Title.title(titleComponent, subtitleComponent, time)
        return AdventureTitle(title, type?.sound)
    }

}
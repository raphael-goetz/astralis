package de.raphaelgoetz.astralis.text.title

import net.kyori.adventure.title.Title
import org.bukkit.Sound

/**
 * Wrapper type for titles
 * @param title is the contained title inside the wrapper
 * @param sound is the contained sound inside the wrapper
 */
data class AdventureTitle(
    val title: Title,
    val sound: Sound?
)
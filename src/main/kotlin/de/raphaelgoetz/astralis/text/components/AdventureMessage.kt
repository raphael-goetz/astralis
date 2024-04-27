package de.raphaelgoetz.astralis.text.components

import net.kyori.adventure.text.Component
import org.bukkit.Sound

/**
 * Wrapper type for components.
 * @param component is the contained component inside the wrapper.
 * @param sound is the contained sound inside the wrapper.
 */
data class AdventureMessage(
    val component: Component,
    val sound: Sound?
)
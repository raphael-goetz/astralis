package de.raphaelgoetz.astralis.text.components

import net.kyori.adventure.text.Component
import org.bukkit.Sound

data class AdventureMessage(
    val component: Component,
    val sound: Sound?
)
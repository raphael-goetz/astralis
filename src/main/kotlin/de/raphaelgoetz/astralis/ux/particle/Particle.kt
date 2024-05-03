package de.raphaelgoetz.astralis.ux.particle

import de.raphaelgoetz.astralis.ux.color.Colorization
import de.raphaelgoetz.astralis.ux.color.asColor
import org.bukkit.Location
import org.bukkit.Particle
import org.bukkit.entity.Player

fun Player.showParticle(particle: Particle, location: Location) {
    this.spawnParticle(particle, location, 1)
}

fun Player.showColorParticle(colorization: Colorization, location: Location) {
    val particle = Particle.DustOptions(colorization.asColor(), 1f)
    this.spawnParticle(Particle.DUST, location.x, location.y, location.z, 0, 0.0, 0.0, 0.0, particle)
}
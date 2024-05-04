package de.raphaelgoetz.astralis.ux.particle

import de.raphaelgoetz.astralis.ux.color.Colorization
import de.raphaelgoetz.astralis.ux.color.asColor
import org.bukkit.Location
import org.bukkit.Particle
import org.bukkit.entity.Player

/**
 * Spawns a particle for a player.
 * @param particle type.
 * @param location of the spawned particle.
 */
fun Player.showParticle(particle: Particle, location: Location) {
    this.spawnParticle(particle, location, 1)
}

/**
 * Spawns a colorized particle for a player.
 * @param colorization of the particle.
 * @param location of the spawned particle.
 */
fun Player.showColorParticle(colorization: Colorization, location: Location) {
    val particle = Particle.DustOptions(colorization.asColor(), 1f)
    this.spawnParticle(Particle.DUST, location.x, location.y, location.z, 0, 0.0, 0.0, 0.0, particle)
}
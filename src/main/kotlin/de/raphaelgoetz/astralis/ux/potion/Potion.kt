package de.raphaelgoetz.astralis.ux.potion

import org.bukkit.entity.Player
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffect.INFINITE_DURATION
import org.bukkit.potion.PotionEffectType

/**
 * Function for easier potion adding.
 * @param potionEffectType is the type of the potion-effect.
 * @param infinite if true the potion-duration will be infinite.
 * @param duration is the duration of the potion-effect.
 * @param amplifier for the potion-effect.
 * @param ambient for the potion-effect.
 * @param particle toggles weather particles are spawned when having the effect.
 * @param icon toggles weather an icon for the effect shows up.
 */
fun Player.addEffect(
    potionEffectType: PotionEffectType,
    infinite: Boolean = true,
    duration: Int = 300,
    amplifier: Int = 1,
    ambient: Boolean = false,
    particle: Boolean = false,
    icon: Boolean = false
) {
    if (infinite) this.addPotionEffect(
        PotionEffect(
            potionEffectType,
            INFINITE_DURATION,
            amplifier,
            ambient,
            particle,
            icon
        )
    )
    else this.addPotionEffect(PotionEffect(potionEffectType, duration, amplifier, ambient, particle, icon))
}

/**
 * Toggle for potion-effects. Returns true if added, false if removed.
 * @param potionEffectType is the potion that will be removed/added.
 */
fun Player.toggleEffect(potionEffectType: PotionEffectType): Boolean {

    if (this.hasPotionEffect(potionEffectType)) {
        this.removePotionEffect(potionEffectType)
        return false
    }

    this.addEffect(potionEffectType)
    return true
}
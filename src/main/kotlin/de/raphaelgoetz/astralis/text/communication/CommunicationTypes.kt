package de.raphaelgoetz.astralis.text.communication

import de.raphaelgoetz.astralis.ux.color.Colorization
import org.bukkit.Sound

/**
 * For general and consistent styling & sound design.
 * @param color is the Colorization (color) for the styled text.
 * @param icon is an icon that will be put before the main message.
 * @param sound is the sound matching the information type.
 */
enum class CommunicationType(val color: Colorization, val icon: String, val sound: Sound) {
    NONE(Colorization.GRAY, "", Sound.BLOCK_ANVIL_BREAK),
    WARNING(Colorization.ORANGE, "⚠ ", Sound.ENTITY_ENDERMAN_STARE),
    ERROR(Colorization.RED, "❌ ", Sound.BLOCK_ANVIL_BREAK),
    SUCCESS(Colorization.LIME, "✔ ", Sound.ENTITY_PLAYER_LEVELUP),
    INFO(Colorization.YELLOW, "ℹ ", Sound.BLOCK_NOTE_BLOCK_PLING),
    DEBUG(Colorization.GRAY, "🔍 ", Sound.ENTITY_SNOWBALL_THROW),
    ALERT(Colorization.RED, "🚨 ", Sound.ENTITY_GHAST_WARN),
    REMINDER(Colorization.YELLOW, "🔔 ", Sound.BLOCK_NOTE_BLOCK_BELL),
    UPDATE(Colorization.LIME, "🔄 ", Sound.ENTITY_EXPERIENCE_ORB_PICKUP),
}
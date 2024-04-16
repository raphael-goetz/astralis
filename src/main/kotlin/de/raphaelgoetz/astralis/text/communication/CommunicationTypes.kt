package de.raphaelgoetz.astralis.text.communication

import de.raphaelgoetz.librarytesting.ux.color.Colorization
import org.bukkit.Sound

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
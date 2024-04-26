package de.raphaelgoetz.astralis.items.data

import de.raphaelgoetz.astralis.ux.color.Colorization
import org.bukkit.Sound

/**
 * Container for styling and sound. Used for better ux
 * @param color is the color the display-name gets colored in
 * @param sound is the sound the user will get when clicking
 */
enum class InteractionType(val color: Colorization, val sound: Sound) {
    SUCCESS(Colorization.LIME, Sound.ENTITY_EXPERIENCE_ORB_PICKUP),
    ERROR(Colorization.RED, Sound.BLOCK_ANVIL_BREAK),
    ENABLED(Colorization.LIME, Sound.ENTITY_VILLAGER_YES),
    DISABLED(Colorization.RED, Sound.ENTITY_VILLAGER_NO),
    CLICK(Colorization.GRAY, Sound.UI_LOOM_SELECT_PATTERN),
    PAGE_TURN(Colorization.GRAY, Sound.UI_LOOM_TAKE_RESULT)
}
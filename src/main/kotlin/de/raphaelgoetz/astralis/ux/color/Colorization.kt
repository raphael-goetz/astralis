package de.raphaelgoetz.astralis.ux.color

import net.kyori.adventure.bossbar.BossBar
import net.kyori.adventure.text.format.TextColor
import org.bukkit.Color
import org.bukkit.DyeColor

/**
 * Enum to map minecraft-color variants to generic color type.
 * @param rgbLike is the TextColor defined through rgb-color codes.
 */
enum class Colorization(val rgbLike: TextColor) {
    WHITE(TextColor.color(255, 255, 255)),
    LIGHT_GRAY(TextColor.color(211, 211, 211)),
    GRAY(TextColor.color(128, 128, 128)),
    BLACK(TextColor.color(0, 0, 0)),
    BROWN(TextColor.color(165, 42, 42)),
    RED(TextColor.color(255, 0, 0)),
    ORANGE(TextColor.color(255, 165, 0)),
    YELLOW(TextColor.color(255, 255, 0)),
    LIME(TextColor.color(0, 255, 0)),
    GREEN(TextColor.color(0, 128, 0)),
    CYAN(TextColor.color(0, 255, 255)),
    LIGHT_BLUE(TextColor.color(173, 216, 230)),
    BLUE(TextColor.color(0, 0, 255)),
    PURPLE(TextColor.color(128, 0, 128)),
    MAGENTA(TextColor.color(255, 0, 255)),
    PINK(TextColor.color(255, 192, 203))
}

/**
 * @return the TextColor of the Colorization type.
 * @see TextColor
 */
fun Colorization.asTextColor(): TextColor {
    return this.rgbLike
}

/**
 * @return the DyeColor of the Colorization type.
 * Used for Minecraft-DyeColor, banner, wool...
 * @see DyeColor
 */
fun Colorization.asDyeColor(): DyeColor {
    return when (this) {
        Colorization.WHITE -> DyeColor.WHITE
        Colorization.LIGHT_GRAY -> DyeColor.LIGHT_GRAY
        Colorization.GRAY -> DyeColor.GRAY
        Colorization.BLACK -> DyeColor.BLACK
        Colorization.BROWN -> DyeColor.BROWN
        Colorization.RED -> DyeColor.RED
        Colorization.ORANGE -> DyeColor.ORANGE
        Colorization.YELLOW -> DyeColor.YELLOW
        Colorization.LIME -> DyeColor.LIME
        Colorization.GREEN -> DyeColor.GREEN
        Colorization.CYAN -> DyeColor.CYAN
        Colorization.LIGHT_BLUE -> DyeColor.LIGHT_BLUE
        Colorization.BLUE -> DyeColor.BLUE
        Colorization.PURPLE -> DyeColor.PURPLE
        Colorization.MAGENTA -> DyeColor.MAGENTA
        Colorization.PINK -> DyeColor.PINK
    }
}

/**
 * @return the bukkit color.
 * @see Color
 */
fun Colorization.asColor(): Color {
    return Color.fromRGB(
        this.asTextColor().red(),
        this.asTextColor().green(),
        this.asTextColor().blue()
    )
}

/**
 * @return the BossBarColor of the Colorization type.
 * Used for Minecraft-BossBar color.
 * @throws Exception when no matching color has been found.
 * @see BossBar.Color
 */
fun Colorization.asBossBarColor(): BossBar.Color {
    return when (this) {
        Colorization.WHITE -> BossBar.Color.WHITE
        Colorization.RED -> BossBar.Color.RED
        Colorization.YELLOW -> BossBar.Color.YELLOW
        Colorization.GREEN -> BossBar.Color.GREEN
        Colorization.BLUE -> BossBar.Color.BLUE
        Colorization.PURPLE -> BossBar.Color.PURPLE
        Colorization.PINK -> BossBar.Color.PINK
        else -> throw Exception("$this has no boss bar color")
    }
}
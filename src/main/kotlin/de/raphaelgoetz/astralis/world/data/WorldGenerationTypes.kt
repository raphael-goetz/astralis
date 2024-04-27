package de.raphaelgoetz.astralis.world.data

import de.raphaelgoetz.astralis.world.generator.VoidGenerator
import org.bukkit.WorldCreator
import org.bukkit.WorldType

/**
 * Generation type wrapper to contain the void generation option.
 */
enum class WorldGenerationTypes {
    VOID,
    FLAT,
    NORMAL,
    LARGE_BIOMES,
    AMPLIFIED
}

/**
 * Modifies the given world-creator to generate the given type to accomplish a void generator.
 * @param worldGenerationTypes is the type of the world-generator.
 * @return the modified WorldCreator that was given.
 */
fun WorldCreator.modifyGeneration(worldGenerationTypes: WorldGenerationTypes): WorldCreator {
    return when (worldGenerationTypes) {
        WorldGenerationTypes.VOID -> this.generator(VoidGenerator())
        WorldGenerationTypes.FLAT -> this.type(WorldType.FLAT)
        WorldGenerationTypes.NORMAL -> this.type(WorldType.NORMAL)
        WorldGenerationTypes.LARGE_BIOMES -> this.type(WorldType.LARGE_BIOMES)
        WorldGenerationTypes.AMPLIFIED -> this.type(WorldType.AMPLIFIED)
    }
}
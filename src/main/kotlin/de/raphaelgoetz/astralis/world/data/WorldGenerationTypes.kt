package de.raphaelgoetz.astralis.world.data

import de.raphaelgoetz.astralis.world.generator.VoidGenerator
import org.bukkit.WorldCreator
import org.bukkit.WorldType

enum class WorldGenerationTypes {
    VOID,
    FLAT,
    NORMAL,
    LARGE_BIOMES,
    AMPLIFIED
}

fun WorldCreator.modifyGeneration(worldGenerationTypes: WorldGenerationTypes): WorldCreator {
    return when (worldGenerationTypes) {
        WorldGenerationTypes.VOID -> this.generator(VoidGenerator())
        WorldGenerationTypes.FLAT -> this.type(WorldType.FLAT)
        WorldGenerationTypes.NORMAL -> this.type(WorldType.NORMAL)
        WorldGenerationTypes.LARGE_BIOMES -> this.type(WorldType.LARGE_BIOMES)
        WorldGenerationTypes.AMPLIFIED -> this.type(WorldType.AMPLIFIED)
    }
}
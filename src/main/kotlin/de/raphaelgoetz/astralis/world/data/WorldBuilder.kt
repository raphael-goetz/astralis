package de.raphaelgoetz.astralis.world.data

import de.raphaelgoetz.astralis.world.generator.VoidGenerator
import org.bukkit.World
import org.bukkit.WorldCreator

data class WorldBuilder(
    val name: String,
    val generationTypes: WorldGenerationTypes,
    val environment: World.Environment,
    val isBuildingWorld: Boolean
) {

    fun build(): World {
        val worldGenerator = WorldCreator(name)

        worldGenerator.environment(environment)
        worldGenerator.modifyGeneration(generationTypes)

        if (isBuildingWorld) {
            worldGenerator.generateStructures(false)
            worldGenerator.generator(VoidGenerator())
        }

        //TODO: CREATE LISTENER THAT REGISTERS WORLD --> THEN ADD GAME RULES --> UNREGISTER LISTENER
        return worldGenerator.createWorld()!!
    }
}
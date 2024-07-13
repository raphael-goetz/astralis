package de.raphaelgoetz.astralis.world.data

import de.raphaelgoetz.astralis.event.listen
import de.raphaelgoetz.astralis.event.unregister
import de.raphaelgoetz.astralis.schedule.doLater
import de.raphaelgoetz.astralis.world.generator.VoidGenerator
import org.bukkit.Difficulty
import org.bukkit.GameRule
import org.bukkit.World
import org.bukkit.WorldCreator
import org.bukkit.event.world.WorldLoadEvent

/**
 * Builder for creating setup worlds. Specifically worlds to build in.
 * @param name is the Name of the world.
 * @param generationTypes is the type of world-generator (includes vanilla and void).
 * @param environment is the environment of the world like nether or end.
 * @param isBuildingWorld
 */
data class WorldBuilder(
    val name: String,
    val generationTypes: WorldGenerationTypes,
    val environment: World.Environment,
    val isBuildingWorld: Boolean
) {

    fun build(): World? {
        val worldGenerator = WorldCreator(name)

        worldGenerator.environment(environment)
        worldGenerator.modifyGeneration(generationTypes)

        if (isBuildingWorld) {
            worldGenerator.generateStructures(false)
            worldGenerator.generator(VoidGenerator())
            applyGameRules(name)
        }

        return worldGenerator.createWorld()
    }
}

/**
 * Applies predefined game-rules and world settings for a building-world.
 * @param name is the name of the world that the rules will be applied on.
 */
private fun applyGameRules(name: String) {
    val loadEvent = listen<WorldLoadEvent> { event ->
        if (event.world.name != name) return@listen
        val world = event.world

        world.difficulty = Difficulty.PEACEFUL

        world.setGameRule(GameRule.RANDOM_TICK_SPEED, 0)
        world.setGameRule(GameRule.DISABLE_RAIDS, true)
        world.setGameRule(GameRule.DO_FIRE_TICK, false)
        world.setGameRule(GameRule.DO_WEATHER_CYCLE, false)
        world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false)
        world.setGameRule(GameRule.MOB_GRIEFING, false)
        world.setGameRule(GameRule.DO_WARDEN_SPAWNING, false)
        world.setGameRule(GameRule.DO_TRADER_SPAWNING, false)
        world.setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS, false)
        world.setGameRule(GameRule.DO_ENTITY_DROPS, false)
        world.setGameRule(GameRule.DO_ENTITY_DROPS, false)
        world.setGameRule(GameRule.KEEP_INVENTORY, true)
        world.setGameRule(GameRule.SPAWN_CHUNK_RADIUS, 0)
    }

    doLater(2000) {
        loadEvent.unregister()
    }
}
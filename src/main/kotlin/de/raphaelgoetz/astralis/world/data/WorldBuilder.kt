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
import org.bukkit.plugin.java.JavaPlugin

data class WorldBuilder(
    val javaPlugin: JavaPlugin,
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
            applyGameRules(javaPlugin, name)
        }

        return worldGenerator.createWorld()
    }
}

private fun applyGameRules(javaPlugin: JavaPlugin, name: String) {
    val loadEvent = listen<WorldLoadEvent>(javaPlugin) { event ->
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
    }

    doLater(2000) {
        loadEvent.unregister()
    }
}
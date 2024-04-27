package de.raphaelgoetz.astralis.world

import de.raphaelgoetz.astralis.world.data.WorldBuilder
import de.raphaelgoetz.astralis.world.data.WorldGenerationTypes
import org.bukkit.Bukkit
import org.bukkit.World
import org.bukkit.WorldCreator
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

/**
 * Creates a normal world.
 * @param name is the name of the world which will be created.
 * @param generationTypes is the generation of the world.
 * @param environment is the environment of the created world. Possible are Normal, Nether, End.
 * @return the world object.
 */
fun createWorld(
    javaPlugin: JavaPlugin,
    name: String,
    generationTypes: WorldGenerationTypes = WorldGenerationTypes.NORMAL,
    environment: World.Environment = World.Environment.NORMAL,
) = WorldBuilder(javaPlugin, name, generationTypes, environment, false).build()

/**
 * Creates a world that's specifically for building. (Game rules may be differed from normal. Is always a void gen)
 * @param name is the name of the world which will be created.
 * @return the world object.
 */
fun createBuildingWorld(
    javaPlugin: JavaPlugin,
    name: String,
) = WorldBuilder(javaPlugin, name, WorldGenerationTypes.VOID, World.Environment.NORMAL, true).build()

/**
 * @return true if the given file is a world folder. False if not.
 */
fun File.isWorldContainer(): Boolean {
    val lockFile = File(this, "session.lock")
    return lockFile.exists()
}

/**
 * @return true if the given string is a world folder. False if not.
 */
fun String.isWorldContainer(): Boolean {
    val possibleWorldContainers = Bukkit.getWorldContainer().listFiles() ?: return false

    for (file in possibleWorldContainers) {
        if (!file.isWorldContainer()) continue
        if (file.name != this) continue
        return true
    }

    return false
}

/**
 * @return a Set of all world names. Also contains unloaded worlds.
 */
val existingWorlds: Set<String>
    get() {
        val result = mutableSetOf<String>()
        val possibleWorldContainers = Bukkit.getWorldContainer().listFiles() ?: return emptySet()

        for (file in possibleWorldContainers) {
            if (!file.isWorldContainer()) continue
            if (result.contains(file.name)) continue
            result.add(file.name)
        }

        return result
    }

/**
 * Will load the world by the given string.
 * @return a nullable world.
 */
fun String.loadAsWorld(): World? {
    if (this.isWorldContainer()) return null
    return Bukkit.getWorld(this) ?: Bukkit.createWorld(WorldCreator(this))
}
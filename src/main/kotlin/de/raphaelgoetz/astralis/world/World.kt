package de.raphaelgoetz.astralis.world

import de.raphaelgoetz.astralis.world.data.WorldBuilder
import de.raphaelgoetz.astralis.world.data.WorldGenerationTypes
import org.bukkit.Bukkit
import org.bukkit.World
import java.io.File

/**
 * Creates a normal world
 * @param name is the name of the world
 * @param generationTypes is the generation of the world.
 * @param environment is the environment of the created world. Possible are Normal, Nether, End
 * @return the world object
 */
fun createWorld(
    name: String,
    generationTypes: WorldGenerationTypes = WorldGenerationTypes.NORMAL,
    environment: World.Environment = World.Environment.NORMAL,
) = WorldBuilder(name, generationTypes, environment, false).build()

/**
 * Creates a world that's specifically for building. (Game rules may be differed from normal. Is always a void gen)
 * @param name is the name of the world
 * @return the world object
 */
fun createBuildingWorld(
    name: String,
) = WorldBuilder(name, WorldGenerationTypes.VOID, World.Environment.NORMAL, true).build()


/**
 * @return true if the given file is a world folder. False if not.
 */
fun File.isWorldContainer(): Boolean {
    val lockFile = File(this, "session.lock")
    return lockFile.exists()
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
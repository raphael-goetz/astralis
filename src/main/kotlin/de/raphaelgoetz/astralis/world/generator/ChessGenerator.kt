package de.raphaelgoetz.astralis.world.generator

import org.bukkit.Material
import org.bukkit.generator.ChunkGenerator
import org.bukkit.generator.WorldInfo
import java.util.Random

/**
 * A simple chessboard-generator that will create a chess pattern
 */
class ChessGenerator : ChunkGenerator() {

    override fun generateBedrock(worldInfo: WorldInfo, random: Random, chunkX: Int, chunkZ: Int, chunkData: ChunkData) {
        // Determine color based on chunk coordinates
        val isEven = (chunkX + chunkZ) % 2 == 0
        val topBlock = if (isEven) Material.LIGHT_GRAY_CONCRETE else Material.GRAY_CONCRETE

        // Fill from y=0 to y=63 with stone
        for (x in 0 until 16) {
            for (z in 0 until 16) {
                for (y in 0..63) {
                    chunkData.setBlock(x, y, z, Material.STONE)
                }
                // Place the top layer at y=64
                chunkData.setBlock(x, 64, z, topBlock)
            }
        }
    }
}
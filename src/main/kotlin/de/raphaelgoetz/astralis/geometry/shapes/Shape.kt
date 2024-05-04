package de.raphaelgoetz.astralis.geometry.shapes

import com.google.common.collect.Lists
import de.raphaelgoetz.astralis.schedule.doAgain
import org.bukkit.Material
import org.bukkit.World
import org.bukkit.block.data.BlockData
import org.bukkit.util.Vector

/**
 * Template for creating any shape.
 * @param world of the shape.
 */
abstract class Shape(
    open val world: World,
) {

    /** List of all the locations inside the shape */
    abstract fun getShapeOutline(): List<Vector>

    /** List of all the locations of the outline */
    abstract fun getShapeFilament(): List<Vector>

    /**
     * Fills any part instant.
     * @param material of the filament.
     * @param filament (list of locations) that get filled.
     * @param builder to configure the block-data.
     */
    inline fun <reified T : BlockData> fillInstant(
        material: Material,
        filament: List<Vector> = getShapeFilament(),
        crossinline builder: T.() -> Unit = {}
    ) {

        filament.forEach { vector ->
            val block = world.getBlockAt(vector.blockX, vector.blockY, vector.blockZ)
            block.type = material

            val data = block.blockData as T
            data.apply(builder)

            block.blockData = data
        }
    }

    /**
     * Fills any part instant.
     * @param material of the filament.
     * @param filament (list of locations) that get filled.
     */
    fun fillInstant(
        material: Material,
        filament: List<Vector> = getShapeFilament()
    ) = fillInstant<BlockData>(material)

    /**
     * Fills any part at a specific rate.
     * @param rate how many blocks get placed at a second.
     * @param material of the filament.
     * @param filament (list of locations) that get filled.
     * @param builder to configure the block-data.
     */
    inline fun <reified T : BlockData> fillAtRate(
        rate: Int,
        material: Material,
        filament: List<Vector> = getShapeFilament(),
        crossinline builder: T.() -> Unit = {}
    ) {

        val fills = Lists.partition(filament, rate)

        doAgain(period = 1) { iteration ->
            if (iteration >= fills.size) stop()
            fillInstant(material, fills[iteration.toInt()], builder)
        }

    }

    /**
     * Fills any part at a specific rate.
     * @param rate how many blocks get placed at a second.
     * @param material of the filament.
     * @param filament (list of locations) that get filled.
     */
    fun fillAtRage(rate: Int, material: Material, filament: List<Vector> = getShapeFilament()) =
        fillAtRate<BlockData>(rate, material, filament)
}
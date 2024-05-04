package de.raphaelgoetz.astralis.geometry.shapes

import com.google.common.collect.Lists
import de.raphaelgoetz.astralis.schedule.doAgain
import org.bukkit.Material
import org.bukkit.World
import org.bukkit.block.data.BlockData
import org.bukkit.util.Vector

abstract class Shape(
    open val world: World,
) {
    abstract fun getShapeOutline(): List<Vector>
    abstract fun getShapeFilament(): List<Vector>

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

    fun fillInstant(material: Material, filament: List<Vector> = getShapeFilament()) = fillInstant<BlockData>(material)

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

    fun fillAtRage(rate: Int, material: Material, filament: List<Vector> = getShapeFilament()) =
        fillAtRate<BlockData>(rate, material, filament)
}
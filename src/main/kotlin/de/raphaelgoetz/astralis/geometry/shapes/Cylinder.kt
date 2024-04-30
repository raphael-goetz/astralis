package de.raphaelgoetz.astralis.geometry.shapes

import org.bukkit.World
import org.bukkit.util.Vector

class Cylinder(
    override val world: World,
    val middle: Vector,
    val height: Int,
    val radius: Int,
) : Shape(world) {

    private val from = Vector(middle.blockX - radius, middle.blockY, middle.blockZ - radius)
    private val to = Vector(middle.blockX + radius, middle.blockY + height, middle.blockZ + radius)

    override fun getOutline(): List<Vector> {
        TODO("Not yet implemented")
    }

    override fun getFilament(): List<Vector> {
        TODO("Not yet implemented")
    }
}
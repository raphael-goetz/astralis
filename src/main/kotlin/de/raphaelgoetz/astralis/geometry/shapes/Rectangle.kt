package de.raphaelgoetz.astralis.geometry.shapes

import org.bukkit.World
import org.bukkit.util.Vector
import kotlin.math.max
import kotlin.math.min

/**
 * Creates a rectangle.
 * @param world of the shape.
 * @param from starting point of the location.
 * @param to ending point of the location.
 */
class Rectangle(
    override val world: World,
    private val from: Vector,
    private val to: Vector,
) : Shape(world) {

    /** List of all the locations inside the shape */
    val filament: List<Vector>
        get() = getShapeFilament()

    /** List of all the locations of the outline */
    val outline: List<Vector>
        get() = getShapeOutline()

    private val minX = min(from.x, to.x).toInt()
    private val minY = min(from.y, to.y).toInt()
    private val minZ = min(from.z, to.z).toInt()

    private val maxX = max(from.x, to.x).toInt()
    private val maxY = max(from.y, to.y).toInt()
    private val maxZ = max(from.z, to.z).toInt()

    override fun getShapeFilament(): MutableList<Vector> {
        val result = mutableListOf<Vector>()

        for (x in minX..maxX) {
            for (y in minY..maxY) {
                for (z in minZ..maxZ) {
                    result.add(Vector(x, y, z))
                }
            }
        }

        return result
    }

    override fun getShapeOutline(): MutableList<Vector> {
        val result = mutableListOf<Vector>()

        //adding all points in the x-y-layer
        for (x in minX..maxX) {
            for (y in minY..maxY) {
                result.add(Vector(x, y, from.z.toInt()))
            }
        }

        //adding all points in the x-z-layer
        for (x in minX..maxX) {
            for (z in minZ..maxZ) {
                result.add(Vector(x, from.y.toInt(), z))
            }
        }

        //adding all points in the y-z-layer
        for (y in minY..maxY) {
            for (z in minZ..maxZ) {
                result.add(Vector(from.x.toInt(), y, z))
            }
        }

        return result
    }

}
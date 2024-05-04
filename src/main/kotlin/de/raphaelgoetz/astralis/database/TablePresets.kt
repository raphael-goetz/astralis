package de.raphaelgoetz.astralis.database

import de.raphaelgoetz.astralis.annotations.InternalUse
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.jodatime.CurrentDateTime
import org.jetbrains.exposed.sql.jodatime.datetime

/**
 * Creates a database table for players (uuid as primary-key).
 */
open class PlayerTable : Table() {
    val uuid = uuid("uuid")
    val createdAt = datetime("created_at").defaultExpression(CurrentDateTime)

    override val primaryKey = PrimaryKey(uuid)
}

/**
 * Creates a database table (integer as primary-key).
 */
open class DataTable : Table() {
    val id = integer("id").autoIncrement()
    val createdAt = datetime("created_at").defaultExpression(CurrentDateTime)

    override val primaryKey = PrimaryKey(id)
}

/**
 * Creates a database table for locations bound to players (uuid as primary-key).
 */
open class PlayerLocationTable : LocationTable() {
    val uuid = uuid("uuid")
    val createdAt = datetime("created_at").defaultExpression(CurrentDateTime)

    override val primaryKey = PrimaryKey(uuid)
}

/**
 * Creates a database table for locations bound to integers (integer as primary-key).
 */
open class DataLocationTable : LocationTable() {
    val id = integer("id").autoIncrement()
    val createdAt = datetime("created_at").defaultExpression(CurrentDateTime)

    override val primaryKey = PrimaryKey(id)
}

/**
 * Creates a database table for locations.
 */
@InternalUse
open class LocationTable : Table() {
    val world = short("world")
    val x = double("x")
    val y = double("y")
    val z = double("z")
    val pitch = float("pitch")
    val yaw = float("yaw")
}
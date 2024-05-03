package de.raphaelgoetz.astralis.database

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.jodatime.CurrentDateTime
import org.jetbrains.exposed.sql.jodatime.datetime

open class PlayerTable : Table() {
    val uuid = uuid("uuid")
    val createdAt = datetime("created_at").defaultExpression(CurrentDateTime)

    override val primaryKey = PrimaryKey(uuid)
}

open class DataTable : Table() {
    val id = integer("id").autoIncrement()
    val createdAt = datetime("created_at").defaultExpression(CurrentDateTime)

    override val primaryKey = PrimaryKey(id)
}

open class PlayerLocationTable : LocationTable() {
    val uuid = uuid("uuid")
    val createdAt = datetime("created_at").defaultExpression(CurrentDateTime)

    override val primaryKey = PrimaryKey(uuid)
}

open class DataLocationTable : LocationTable() {
    val id = integer("id").autoIncrement()
    val createdAt = datetime("created_at").defaultExpression(CurrentDateTime)

    override val primaryKey = PrimaryKey(id)
}

open class LocationTable : Table() {
    val world = short("world")
    val x = double("x")
    val y = double("y")
    val z = double("z")
    val pitch = float("pitch")
    val yaw = float("yaw")
}
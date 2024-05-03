package de.raphaelgoetz.astralis.database

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.jodatime.CurrentDateTime
import org.jetbrains.exposed.sql.jodatime.datetime

class PlayerTable : Table() {
    val uuid = uuid("uuid")
    val createdAt = datetime("created_at").defaultExpression(CurrentDateTime)

    override val primaryKey = PrimaryKey(uuid)
}

class DataTable : Table() {
    val id = integer("id").autoIncrement()
    val createdAt = datetime("created_at").defaultExpression(CurrentDateTime)

    override val primaryKey = PrimaryKey(id)
}
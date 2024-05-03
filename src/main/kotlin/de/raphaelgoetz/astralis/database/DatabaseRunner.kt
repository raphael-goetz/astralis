package de.raphaelgoetz.astralis.database

import org.bukkit.Bukkit
import org.jetbrains.exposed.sql.Database
import java.io.File

fun createConnection(name: String): Database {
    val dbFile = File("${Bukkit.getPluginsFolder().path}/data/$name.db")

    dbFile.parentFile.mkdirs()
    if (!dbFile.exists()) dbFile.createNewFile()

    return Database.connect("jdbc:sqlite:$dbFile", "org.sqlite.JDBC")
}
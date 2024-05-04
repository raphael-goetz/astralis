package de.raphaelgoetz.astralis.database

import org.bukkit.Bukkit
import org.jetbrains.exposed.sql.Database
import java.io.File

/**
 * Creates a database connection & file if not exists.
 *
 * Found by the plugins/data path.
 *
 * @param name of the database & file.
 * @return Database of the created connection.
 */
fun createConnection(name: String): Database {
    val dbFile = File("${Bukkit.getPluginsFolder().path}/data/$name.db")

    dbFile.parentFile.mkdirs()
    if (!dbFile.exists()) dbFile.createNewFile()

    return Database.connect("jdbc:sqlite:$dbFile", "org.sqlite.JDBC")
}
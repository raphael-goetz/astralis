package de.raphaelgoetz.astralis.command

import de.raphaelgoetz.astralis.command.data.BasicCommand
import org.bukkit.Bukkit

/**
 * Automatically register a command. No other method of registration is needed!
 */
fun BasicCommand.register() {
    Bukkit.getServer().commandMap.register(this.commandDescription, this)
}
package de.raphaelgoetz.astralis.command.placeholder

import org.bukkit.command.Command
import org.bukkit.command.CommandSender

/**
 * A placeholder command. Not any usage right now.
 */
class PlaceholderCommand(label: String) : Command(label) {

    override fun execute(p0: CommandSender, p1: String, p2: Array<out String>?): Boolean {
        return false
    }

}
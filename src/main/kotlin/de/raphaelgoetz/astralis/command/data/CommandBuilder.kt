package de.raphaelgoetz.astralis.command.data

import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player

/**
 * Creates a registered command & tabCompleter. No other registration is needed (not even plugin.yml)
 * @param commandName is the name also known as label of the command
 * @param commandDescription is the description of the command. Will be shown if a user uses '/help'
 * @param commandUsage shows the user how he should use the command
 * @param commandAliases are the aliases of the command
 * @param body is the main code-block that will be executed. Contains the player that has been executed the command.
 */
data class BasicCommand(
    val commandName: String,
    val commandDescription: String,
    val commandUsage: String = "/$commandName",
    val commandAliases: MutableList<String> = mutableListOf(),
    inline val body: (executor: Player) -> Unit
): Command(commandName, commandDescription, commandUsage, commandAliases), TabCompleter {

    //TODO: ALSO REGISTER TAB_COMPLETER
    override fun onTabComplete(
        p0: CommandSender,
        p1: Command,
        p2: String,
        p3: Array<out String>?
    ): MutableList<String> {
        return mutableListOf()
    }

    override fun execute(sender: CommandSender, label: String, p2: Array<out String>?): Boolean {
        if (sender !is Player) return true
        body.invoke(sender)
        return false
    }
}
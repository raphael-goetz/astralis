package de.raphaelgoetz.astralis.command.data

import org.bukkit.command.Command
import org.bukkit.command.CommandSender
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
    val paths: MutableList<ValueCommandPath<Any>> = arrayListOf(),
    val body: (executor: Player) -> Unit
) : Command(commandName) {

    override fun tabComplete(sender: CommandSender, alias: String, args: Array<out String>?): MutableList<String> {
        return super.tabComplete(sender, alias, args)
    }

    override fun execute(sender: CommandSender, label: String, args: Array<out String>?): Boolean {
        if (sender !is Player) return true
        if (paths.isNotEmpty() && args == null) return true
        if (label != commandName) return true

        for (currentPath in paths) {
            val pathList = currentPath.path.split(" ")
            val argumentList = args!!.toList()
            if (!(argumentList.containsAll(pathList) && pathList.containsAll(argumentList))) continue

            //TODO:
            //currentPath.onExecute.invoke(sender)
            return true
        }

        body.invoke(sender)
        return false
    }
}
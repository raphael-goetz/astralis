package de.raphaelgoetz.astralis.command

import com.destroystokyo.paper.brigadier.BukkitBrigadierCommandSource
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.builder.LiteralArgumentBuilder.literal
import com.mojang.brigadier.builder.RequiredArgumentBuilder
import com.mojang.brigadier.tree.LiteralCommandNode
import de.raphaelgoetz.astralis.command.placeholder.PlaceholderCommand
import org.bukkit.Bukkit

val astralisCommandMap: HashMap<String, LiteralCommandNode<BukkitBrigadierCommandSource>> = hashMapOf()

inline fun registerCommand(
    label: String,
    crossinline builder: LiteralCommand.() -> Unit = {}
) = LiteralCommand(label).apply(builder).build()

class LiteralCommand(
   val label: String,
) {

    private var literal = literal<BukkitBrigadierCommandSource>(label)

    val subCommands: MutableList<LiteralArgumentBuilder<BukkitBrigadierCommandSource>> = mutableListOf()

    private val argumentTree: MutableList<RequiredArgumentBuilder<BukkitBrigadierCommandSource, *>> = mutableListOf()

    fun addArgument(node: RequiredArgumentBuilder<BukkitBrigadierCommandSource, *>) = argumentTree.add(node)

    inline fun addSubCommand(
        label: String,
        crossinline builder: LiteralBuilder.() -> Unit
    ) = subCommands.add(registerLiteral(label, builder))

    fun build() {

        for (subCommand in subCommands) {
            literal.then(subCommand)
        }

        for (argument in argumentTree) {
            literal.then(argument)
        }

        Bukkit.getServer().commandMap.register(label, PlaceholderCommand(label))
        astralisCommandMap[label] = literal.build()
    }

}
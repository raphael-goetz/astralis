package de.raphaelgoetz.astralis.command

import com.destroystokyo.paper.brigadier.BukkitBrigadierCommandSource
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.builder.LiteralArgumentBuilder.literal
import com.mojang.brigadier.tree.LiteralCommandNode
import de.raphaelgoetz.astralis.command.placeholder.PlaceholderCommand
import org.bukkit.Bukkit

val astralisCommandMap: HashMap<String, LiteralCommandNode<BukkitBrigadierCommandSource>> = hashMapOf()

inline fun command(
    label: String,
    crossinline builder: LiteralArgumentBuilder<BukkitBrigadierCommandSource>.() -> Unit
) {
    astralisCommandMap[label] = literal<BukkitBrigadierCommandSource>(label).apply(builder).build()
    val placeholder = PlaceholderCommand(label)
    Bukkit.getCommandMap().register(label, placeholder)
}

inline fun subCommand(
    label: String,
    crossinline builder: LiteralArgumentBuilder<BukkitBrigadierCommandSource>.() -> Unit
) = literal<BukkitBrigadierCommandSource>(label).apply(builder)
package de.raphaelgoetz.astralis.command

import com.destroystokyo.paper.brigadier.BukkitBrigadierCommandSource
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.builder.LiteralArgumentBuilder.literal
import com.mojang.brigadier.tree.LiteralCommandNode
import de.raphaelgoetz.astralis.annotations.InternalUse
import de.raphaelgoetz.astralis.command.placeholder.PlaceholderCommand
import org.bukkit.Bukkit
import java.util.*

/**
 * Internal map for command registering.
 */
@InternalUse
val astralisCommandMap: HashMap<String, LiteralCommandNode<BukkitBrigadierCommandSource>> = hashMapOf()

/**
 * Automatically registers the given command.
 *
 * NEVER REGISTER IN plugin.yml OR ANYTHING.
 *
 * @param label of the command.
 * @param builder for the command creation.
 */
inline fun command(
    label: String,
    crossinline builder: LiteralArgumentBuilder<BukkitBrigadierCommandSource>.() -> Unit
) {
    astralisCommandMap[label] = literal<BukkitBrigadierCommandSource>(label).apply(builder).build()
    val placeholder = PlaceholderCommand(label)
    Bukkit.getCommandMap().register(label, placeholder)
}

/**
 * Adds a subcommand to the existing command.
 * @param label of the command.
 * @param builder for the subcommand creation.
 */
inline fun subCommand(
    label: String,
    crossinline builder: LiteralArgumentBuilder<BukkitBrigadierCommandSource>.() -> Unit
) = literal<BukkitBrigadierCommandSource>(label).apply(builder)
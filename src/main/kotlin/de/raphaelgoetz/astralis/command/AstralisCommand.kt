package de.raphaelgoetz.astralis.command

import com.mojang.brigadier.tree.LiteralCommandNode
import io.papermc.paper.command.brigadier.CommandSourceStack

data class AstralisCommand(
    val command: LiteralCommandNode<CommandSourceStack>,
    val description: String,
    val aliases: List<String>,
)
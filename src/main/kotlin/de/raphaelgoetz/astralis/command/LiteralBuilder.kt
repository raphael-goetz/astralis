package de.raphaelgoetz.astralis.command

import com.destroystokyo.paper.brigadier.BukkitBrigadierCommandSource
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.builder.LiteralArgumentBuilder.literal
import com.mojang.brigadier.builder.RequiredArgumentBuilder


inline fun registerLiteral(
    label: String,
    crossinline builder: LiteralBuilder.() -> Unit
) = LiteralBuilder(label).apply(builder).build()

class LiteralBuilder(
    private val label: String,
) {

    var literal = literal<BukkitBrigadierCommandSource>(label)

    val argumentTree: MutableList<RequiredArgumentBuilder<BukkitBrigadierCommandSource, *>> = mutableListOf()

    fun <T>addArgument(node: RequiredArgumentBuilder<BukkitBrigadierCommandSource, T>) {
        literal.then(node)
    }

    fun build(): LiteralArgumentBuilder<BukkitBrigadierCommandSource> {

        if (argumentTree.isEmpty()) return literal

        for (node in argumentTree) {
            literal.then(node)
        }

        return literal
    }
}
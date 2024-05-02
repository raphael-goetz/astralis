package de.raphaelgoetz.astralis.command

import com.mojang.brigadier.LiteralMessage
import com.mojang.brigadier.builder.RequiredArgumentBuilder

fun <S, T> RequiredArgumentBuilder<S, T>.suggest(suggestions: List<String>): RequiredArgumentBuilder<S, T> {
    this.suggests { _, builder ->

        for (suggestion in suggestions) {
            if (!suggestion.lowercase().startsWith(builder.remainingLowerCase)) continue
            builder.suggest(suggestion)
        }

        return@suggests builder.buildFuture()

    }

    return this
}

fun <S, T> RequiredArgumentBuilder<S, T>.suggest(suggestions: Map<String, String>): RequiredArgumentBuilder<S, T> {
    this.suggests { _, builder ->

        for ((suggestion, tooltip) in suggestions) {
            if (!suggestion.lowercase().startsWith(builder.remainingLowerCase)) continue
            builder.suggest(suggestion, LiteralMessage(tooltip))
        }

        return@suggests builder.buildFuture()

    }

    return this
}
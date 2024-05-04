package de.raphaelgoetz.astralis.command

import com.destroystokyo.paper.brigadier.BukkitBrigadierCommandSource
import com.mojang.brigadier.arguments.ArgumentType
import com.mojang.brigadier.arguments.BoolArgumentType.bool
import com.mojang.brigadier.arguments.DoubleArgumentType.doubleArg
import com.mojang.brigadier.arguments.FloatArgumentType.floatArg
import com.mojang.brigadier.arguments.IntegerArgumentType.integer
import com.mojang.brigadier.arguments.LongArgumentType.longArg
import com.mojang.brigadier.arguments.StringArgumentType.string
import com.mojang.brigadier.builder.RequiredArgumentBuilder
import com.mojang.brigadier.builder.RequiredArgumentBuilder.argument
import com.mojang.brigadier.context.CommandContext

inline fun <reified T> createArgument(
    name: String,
    type: ArgumentType<T>,
    suggestions: Map<T, String> = emptyMap(),
    crossinline onExecute: (CommandContext<BukkitBrigadierCommandSource>, T) -> Int
): RequiredArgumentBuilder<BukkitBrigadierCommandSource, T> {
    val correctSuggestions = suggestions.map { it.key.toString() to it.value }.toMap()
    return argument<BukkitBrigadierCommandSource, T>(name, type).executes { context ->
        onExecute(context, context.getArgument(name, T::class.java))
    }.suggest(correctSuggestions)
}

inline fun <reified T> createArgument(
    name: String,
    type: ArgumentType<T>,
    suggestions: List<T> = emptyList(),
    crossinline onExecute: (CommandContext<BukkitBrigadierCommandSource>, T) -> Int
): RequiredArgumentBuilder<BukkitBrigadierCommandSource, T> {
    val correctSuggestions = suggestions.map { suggestion -> suggestion.toString() }

    return argument<BukkitBrigadierCommandSource, T>(name, type).executes { context ->
        onExecute(context, context.getArgument(name, T::class.java))
    }.suggest(correctSuggestions)
}

inline fun createStringArgument(
    name: String,
    suggestions: Map<String, String>,
    crossinline onExecute: (CommandContext<BukkitBrigadierCommandSource>, String) -> Int
) = createArgument<String>(name, string(), suggestions, onExecute)

inline fun createStringArgument(
    name: String,
    suggestions: List<String>,
    crossinline onExecute: (CommandContext<BukkitBrigadierCommandSource>, String) -> Int
) = createArgument<String>(name, string(), suggestions, onExecute)

inline fun createIntArgument(
    name: String,
    from: Int = Int.MIN_VALUE,
    to: Int = Int.MAX_VALUE,
    suggestions: List<Int> = IntRange(from, to + 1).toList(),
    crossinline onExecute: (CommandContext<BukkitBrigadierCommandSource>, Int) -> Int
) = createArgument<Int>(name, integer(from, to), suggestions, onExecute)

inline fun createIntArgument(
    name: String,
    from: Int = Int.MIN_VALUE,
    to: Int = Int.MAX_VALUE,
    suggestions: HashMap<Int, String>,
    crossinline onExecute: (CommandContext<BukkitBrigadierCommandSource>, Int) -> Int
) = createArgument<Int>(name, integer(from, to), suggestions, onExecute)

inline fun createLongArgument(
    name: String,
    from: Long = Long.MIN_VALUE,
    to: Long = Long.MAX_VALUE,
    suggestions: List<Long> = LongRange(from, to).toList(),
    crossinline onExecute: (CommandContext<BukkitBrigadierCommandSource>, Long) -> Int
) = createArgument<Long>(name, longArg(from, to), suggestions, onExecute)

inline fun createLongArgument(
    name: String,
    from: Long = Long.MIN_VALUE,
    to: Long = Long.MAX_VALUE,
    suggestions: HashMap<Long, String> = hashMapOf(),
    crossinline onExecute: (CommandContext<BukkitBrigadierCommandSource>, Long) -> Int
) = createArgument<Long>(name, longArg(from, to), suggestions, onExecute)

inline fun createFloatArgument(
    name: String,
    floats: List<Float>,
    suggestions: List<Float> = floats,
    crossinline onExecute: (CommandContext<BukkitBrigadierCommandSource>, Float) -> Int
) = createArgument<Float>(name, floatArg(), suggestions, onExecute)

inline fun createFloatArgument(
    name: String,
    floats: List<Float>,
    suggestions: HashMap<Float, String>,
    crossinline onExecute: (CommandContext<BukkitBrigadierCommandSource>, Float) -> Int
) = createArgument<Float>(name, floatArg(), suggestions, onExecute)

inline fun createDoubleArgument(
    name: String,
    doubles: List<Double>,
    suggestions: List<Double>,
    crossinline onExecute: (CommandContext<BukkitBrigadierCommandSource>, Double) -> Int
) = createArgument<Double>(name, doubleArg(), suggestions, onExecute)

inline fun createDoubleArgument(
    name: String,
    doubles: List<Double>,
    suggestions: HashMap<Double, String>,
    crossinline onExecute: (CommandContext<BukkitBrigadierCommandSource>, Double) -> Int
) = createArgument<Double>(name, doubleArg(), suggestions, onExecute)

inline fun createBooleanArgument(
    name: String,
    falseSuggestion: String = "",
    trueSuggestion: String = "",
    crossinline onExecute: (CommandContext<BukkitBrigadierCommandSource>, Boolean) -> Int
) = createArgument<Boolean>(name, bool(), mapOf(Pair(false, falseSuggestion), Pair(true, trueSuggestion)), onExecute)

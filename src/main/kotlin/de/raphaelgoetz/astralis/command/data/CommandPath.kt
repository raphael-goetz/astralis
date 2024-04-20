package de.raphaelgoetz.astralis.command.data

import org.bukkit.entity.Player
import java.util.stream.IntStream
import java.util.stream.LongStream
import kotlin.streams.toList

abstract class CommandPath (
    open val path: String
)

abstract class ValueCommandPath<T> (
    override val path: String,
    open val completionValues: List<T>,
    open val onExecute: (player: Player, value: T?) -> Unit
) : CommandPath(path)

class StringCommandPath (
    override val path: String,
    override val completionValues: List<String>,
    override val onExecute: (player: Player, value: String?) -> Unit
) : ValueCommandPath<String>(path, completionValues, onExecute)

class IntCommandPath (
    override val path: String,
    from: Int,
    to: Int,
    override val onExecute: (player: Player, value: Int?) -> Unit
) : ValueCommandPath<Int>(path, IntStream.range(from, to + 1).toList(), onExecute)

class LongCommandPath (
    override val path: String,
    from: Long,
    to: Long,
    override val onExecute: (player: Player, value: Long?) -> Unit
) : ValueCommandPath<Long>(path, LongStream.range(from, to + 1).toList(), onExecute)

class BooleanCommandPath (
    override val path: String,
    override val onExecute: (player: Player, value: Boolean?) -> Unit
) : ValueCommandPath<Boolean>(path, arrayListOf(false, true), onExecute)

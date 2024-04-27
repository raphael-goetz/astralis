package de.raphaelgoetz.astralis.task

import org.bukkit.entity.Player
import java.util.UUID

/**
 * Interface for tasks. For example a player specific task.
 * No-Clip is an use-case example.
 */
interface Task {

    /** UUID of the task */
    val uuid: UUID
        get() = UUID.randomUUID()

    fun getTaskHolder(): Player

    fun getTask(): Runnable

    fun run()

    fun stop()

}
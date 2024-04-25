package de.raphaelgoetz.astralis.task

import org.bukkit.entity.Player
import java.util.UUID

interface Task {

    val uuid: UUID
        get() = UUID.randomUUID()

    fun getTaskHolder(): Player

    fun getTask(): Runnable

    fun run()

    fun stop()

}
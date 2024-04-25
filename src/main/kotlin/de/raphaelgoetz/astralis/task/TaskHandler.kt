package de.raphaelgoetz.astralis.task

import java.util.UUID

private val taskRegistry: MutableMap<UUID, Task> = mutableMapOf()

fun Task.register() {
    taskRegistry[this.uuid] = this
}

fun stopTask(uuid: UUID) {
    taskRegistry[uuid]?.stop()
    taskRegistry.remove(uuid)
}

fun runTask(uuid: UUID) {
    taskRegistry[uuid]?.run()
}
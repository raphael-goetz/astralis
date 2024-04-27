package de.raphaelgoetz.astralis.task

import java.util.UUID

private val taskRegistry: MutableMap<UUID, Task> = mutableMapOf()

/**
 * Registers a new task by its uuid.
 */
fun Task.register() {
    taskRegistry[this.uuid] = this
}

/**
 * Stop & registration of the task.
 * @param uuid of the given task
 */
fun unregisterTask(uuid: UUID) {
    taskRegistry[uuid]?.stop()
    taskRegistry.remove(uuid)
}

/**
 * Stop & registration of the task.
 */
fun Task.unregister() {
    taskRegistry[uuid]?.stop()
    taskRegistry.remove(uuid)
}

/**
 * Runs a task.
 * @param uuid of the task.
 */
fun runTask(uuid: UUID) {
    taskRegistry[uuid]?.run()
}
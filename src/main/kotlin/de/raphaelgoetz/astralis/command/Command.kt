package de.raphaelgoetz.astralis.command

import de.raphaelgoetz.astralis.annotations.InternalUse

/**
 * Internal map for command registering.
 */
@InternalUse
val astralisCommandList: MutableList<AstralisCommand> = mutableListOf()

/**
 * Automatically registers the given command.
 *
 * NEVER REGISTER IN plugin.yml OR ANYTHING.
 */
fun registerCommand(command: AstralisCommand) {
    astralisCommandList.add(command)
}
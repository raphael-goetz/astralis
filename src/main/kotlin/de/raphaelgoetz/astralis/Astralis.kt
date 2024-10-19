package de.raphaelgoetz.astralis

import de.raphaelgoetz.astralis.command.astralisCommandList
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents
import org.bukkit.plugin.java.JavaPlugin

/** Instance of the main-plugin */
lateinit var AstralisInstance: Astralis
    private set

abstract class Astralis : JavaPlugin() {

    /** Called on load */
    open fun load() {}

    /** Called on enable */
    open fun enable() {}

    /** Called on disable */
    open fun disable() {}

    override fun onLoad() {
        load()
    }

    override fun onEnable() {
        AstralisInstance = this
        enable()
        registerCommands()
    }

    override fun onDisable() {
        disable()
    }

    private fun registerCommands() {

        val manager = this.lifecycleManager

        manager.registerEventHandler(LifecycleEvents.COMMANDS) { event ->
            val commands = event.registrar()

            astralisCommandList.forEach { command ->
                commands.register(
                    command.command,
                    command.description,
                    command.aliases
                )
            }
        }
    }
}
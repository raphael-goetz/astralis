package de.raphaelgoetz.astralis

import com.destroystokyo.paper.brigadier.BukkitBrigadierCommandSource
import com.destroystokyo.paper.event.brigadier.CommandRegisteredEvent
import de.raphaelgoetz.astralis.command.astralisCommandMap
import de.raphaelgoetz.astralis.event.listen
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

        /** Registers all custom commands */
        listen<CommandRegisteredEvent<BukkitBrigadierCommandSource>> { event ->
            astralisCommandMap[event.commandLabel].let { event.literal = it }
        }

    }

}
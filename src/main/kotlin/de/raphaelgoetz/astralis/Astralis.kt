package de.raphaelgoetz.astralis

import com.destroystokyo.paper.brigadier.BukkitBrigadierCommandSource
import com.destroystokyo.paper.event.brigadier.CommandRegisteredEvent
import de.raphaelgoetz.astralis.command.astralisCommandMap
import de.raphaelgoetz.astralis.event.listen
import org.bukkit.plugin.java.JavaPlugin

object Astralis : JavaPlugin() {

    fun load() {}

    fun enable() {}

    fun disable() {}

    override fun onLoad() {
        load()
    }

    override fun onEnable() {

        listen<CommandRegisteredEvent<BukkitBrigadierCommandSource>> { event ->
            astralisCommandMap[event.commandLabel]?.let { event.literal = it }
        }

        enable()

    }

    override fun onDisable() {
        disable()
    }

}
package de.raphaelgoetz.astralis.text.interrogate

import de.raphaelgoetz.astralis.event.listen
import de.raphaelgoetz.astralis.event.unregister
import de.raphaelgoetz.astralis.task.doLater
import de.raphaelgoetz.astralis.text.communication.CommunicationType
import de.raphaelgoetz.astralis.text.components.adventureText

import io.papermc.paper.event.player.AsyncChatEvent
import net.kyori.adventure.text.Component
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

inline fun Player.interrogate(
    javaPlugin: JavaPlugin,
    questionMessage: Component = adventureText("Please type your answer in the chat!") {
        type = CommunicationType.INFO
    },
    timeoutMessage: Component = adventureText("You didn't answer!") { type = CommunicationType.ALERT },
    timeout: Long = 10,
    crossinline onAnswerReceived: (player: Player, answered: Boolean, chatEvent: AsyncChatEvent?) -> Unit
) {

    //TODO: Players should get an sound when res. a message. like in player.sendText & do inner sync

    var message: Component? = null

    this.sendMessage(questionMessage)
    val event = this.listen<AsyncChatEvent>(javaPlugin) { asyncChatEvent ->
        message = asyncChatEvent.message()
        onAnswerReceived.invoke(this@interrogate, true, asyncChatEvent)
    }

    doLater(timeout) {
        event.unregister()
        if (message != null) return@doLater
        this.sendMessage(timeoutMessage)
        onAnswerReceived.invoke(this@interrogate, false, null)
    }
}
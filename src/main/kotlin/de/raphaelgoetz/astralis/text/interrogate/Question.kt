package de.raphaelgoetz.astralis.text.interrogate

import de.raphaelgoetz.astralis.event.listen
import de.raphaelgoetz.astralis.event.unregister
import de.raphaelgoetz.astralis.schedule.doLater
import de.raphaelgoetz.astralis.schedule.time.TaskTimeTypes
import de.raphaelgoetz.astralis.schedule.time.toMilliseconds
import de.raphaelgoetz.astralis.text.communication.CommunicationType
import de.raphaelgoetz.astralis.text.sendText
import de.raphaelgoetz.astralis.text.components.AdventureMessage
import de.raphaelgoetz.astralis.text.components.adventureMessage

import io.papermc.paper.event.player.AsyncChatEvent
import net.kyori.adventure.text.Component
import org.bukkit.entity.Player

/**
 * @param questionMessage is the message the player will receive when the function is called.
 * @param timeoutMessage is the message that the player will receive when the time is over.
 * @param timeout is the time in milliseconds, that the player has time to answer.
 * @param onAnswerReceived is the function that will be executed if the player answers or the timeout will be called.
 * It contains a boolean which correspond to the player having send an answer or not.
 */
inline fun Player.interrogate(
    questionMessage: AdventureMessage = adventureMessage("Please type your answer in the chat!") {
        type = CommunicationType.INFO
    },
    timeoutMessage: AdventureMessage = adventureMessage("You didn't answer!") {
        type = CommunicationType.ALERT
    },
    timeout: Long = 60,
    timeTypes: TaskTimeTypes = TaskTimeTypes.SECONDS,
    crossinline onAnswerReceived: (player: Player, answered: Boolean, chatEvent: AsyncChatEvent?) -> Unit
) {

    var message: Component? = null

    this.sendText(questionMessage)
    val event = this.listen<AsyncChatEvent> { asyncChatEvent ->
        message = asyncChatEvent.message()
        onAnswerReceived.invoke(this@interrogate, true, asyncChatEvent)
    }

    doLater(timeTypes.toMilliseconds(timeout)) {
        event.unregister()
        if (message != null) return@doLater
        this.sendText(timeoutMessage)
        onAnswerReceived.invoke(this@interrogate, false, null)
    }
}
package com.example.listeners
import com.example.models.Category
import com.example.models.Console
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.decodeFromString

suspend fun makeCall(urlToCall: String): String {
    val client = HttpClient(CIO)
    val response: HttpResponse = client.get(urlToCall)
    return response.body()
}

class EventListener : ListenerAdapter() {

    override fun onMessageReceived(event: MessageReceivedEvent) {
        super.onMessageReceived(event)
        var message = event.message.contentRaw
        println("Message: " + event.message.contentRaw)

        GlobalScope.launch {
            if (message == "Categories") {
                var messageToDecode = makeCall("http://0.0.0.0:8080/category")
                println(messageToDecode)
                val categories = Json.decodeFromString<List<Category>>(messageToDecode)
                var messageFinal = ""
                for (category in categories) {
                    messageFinal += category.name + "\n"
                }
                event.channel.sendMessage(messageFinal).queue()
            } else if (message.contains("Category: ")) {
                var categoriesString = makeCall("http://0.0.0.0:8080/category")
                val categories = Json.decodeFromString<List<Category>>(categoriesString)

                var messageSplit = message.split(": ")

                var idForCall: String = ""
                for (category in categories) {
                    if (category.name == messageSplit[1]) {
                        idForCall = category.id
                        break
                    }
                }
                if (idForCall == "") {
                    event.channel.sendMessage("No category with this name.").queue()
                }

                var messageToDecode = makeCall("http://0.0.0.0:8080/category/" + idForCall)
                val consoles = Json.decodeFromString<List<Console>>(messageToDecode)
                var messageFinal = ""
                for (console in consoles) {
                    messageFinal += console.name + "\n"
                }
                event.channel.sendMessage(messageFinal).queue()
            }
        }
    }
}
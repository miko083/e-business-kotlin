package com.example

import com.example.listeners.EventListener
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.example.plugins.*
import net.dv8tion.jda.api.OnlineStatus
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.requests.GatewayIntent
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder
import net.dv8tion.jda.api.sharding.ShardManager

lateinit var shardManager: ShardManager

fun buildBot(){
    val builder = DefaultShardManagerBuilder.createDefault(System.getenv("DISCORD_TOKEN")).enableIntents(GatewayIntent.MESSAGE_CONTENT)
    builder.setStatus(OnlineStatus.ONLINE)
    builder.setActivity(Activity.watching("Kotlin TV"))
    shardManager = builder.build()
    val eventListener: EventListener = EventListener()
    shardManager.addEventListener(eventListener)

}

fun main() {
    /*
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        configureRouting()
    }.start(wait = true)
    */
    buildBot()
    println("Hello, it's Chatbot!")
}

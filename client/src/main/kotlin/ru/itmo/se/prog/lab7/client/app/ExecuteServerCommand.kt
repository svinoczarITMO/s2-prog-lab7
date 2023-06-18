package ru.itmo.se.prog.lab7.client.app

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class ExecuteServerCommand {
    fun run (commandName: String, args: MutableMap<String, String>, flag: String = "main"): String {
        val paramList = mutableListOf(commandName, args, flag)
        val data = MyApp.guiClientValidator.validate(paramList)
        val dataStr = Json.encodeToString(data)
        return MyApp.di.clientApp.request(dataStr)
    }
}
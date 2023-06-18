package ru.itmo.se.prog.lab7.client

import ConnectDi
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.GlobalContext.startKoin
import ru.itmo.se.prog.lab7.client.app.MyApp
import ru.itmo.se.prog.lab7.client.di.notKoinModule
import ru.itmo.se.prog.lab7.client.utils.*
import ru.itmo.se.prog.lab7.client.utils.io.PrinterManager
import ru.itmo.se.prog.lab7.client.utils.io.ReaderManager
import ru.itmo.se.prog.lab7.client.utils.managers.CommandManager
import ru.itmo.se.prog.lab7.client.utils.validation.ConsoleClientValidator
import ru.itmo.se.prog.lab7.client.view.MainView
import ru.itmo.se.prog.lab7.common.data.Messages
import ru.itmo.se.prog.lab7.common.data.types.ArgType
import ru.itmo.se.prog.lab7.common.data.types.LocationType
import ru.itmo.se.prog.lab7.common.data.types.StatusType
import tornadofx.launch


fun main() {
    startKoin {
        modules(notKoinModule)
    }
    val di = ConnectDi()
    val commandManager = di.commandManager
    val consoleClientValidator = ConsoleClientValidator()
    val write = di.write
    val read = di.read
    val message = di.message
    val clientApp = di.clientApp
    val commandPackage = "ru.itmo.se.prog.lab7.client.commands"
    val kotlinIsBetterThanJava = true

    launch<MyApp>()
    var guiIsWorking = true

    if (guiIsWorking) {
        val guiFlag = true
        while (kotlinIsBetterThanJava) {
            val flag = ::main.name
//            while (!clientApp.authorized) {
//                val command = commandManager.getCommand(commandPackage, readFromConsole[0], "Command")
//                if (command != null && command.arg == ArgType.TOKEN || command?.location == LocationType.CLIENT) {
//                    val data = consoleClientValidator.validate(readFromConsole)
//                    val dataStr = Json.encodeToString(data)
//                    clientApp.request(dataStr)
//                }
//            }

            write.inConsole("> ")
            val readFromConsole = (readln().lowercase()).split(" ").toMutableList()
            readFromConsole.add(flag)
            val command = commandManager.getCommand(commandPackage, readFromConsole[0], "Command")
            if (command != null && command.arg != ArgType.TOKEN) {
                if (command.status == StatusType.USER || (command.status == StatusType.ADMIN && !clientApp.user.isAdmin)) {
                    val data = consoleClientValidator.validate(readFromConsole)
                    val dataStr = Json.encodeToString(data)
                    clientApp.request(dataStr)
                } else if (command.status == StatusType.ADMIN && clientApp.user.isAdmin) {
                    val data = consoleClientValidator.validate(readFromConsole)
                    val dataStr = Json.encodeToString(data)
                    clientApp.request(dataStr)
                } else {
                    write.linesInConsole(message.getMessage("permission_upgrade"))
                }
            } else {
                write.linesInConsole(message.getMessage("weird_command"))
            }
        }
    }
    else {
        val guiFlag = false
        while (kotlinIsBetterThanJava) {
            val flag = ::main.name
            while (!clientApp.authorized) {
                write.linesInConsole(message.getMessage("login_info"))
                write.inConsole("> ")
                val readFromConsole = (read.fromConsole().lowercase()).split(" ").toMutableList()
                readFromConsole.add(flag)
                val command = commandManager.getCommand(commandPackage, readFromConsole[0], "Command")
                if (command != null && command.arg == ArgType.TOKEN || command?.location == LocationType.CLIENT) {
                    val data = consoleClientValidator.validate(readFromConsole)
                    val dataStr = Json.encodeToString(data)
                    clientApp.request(dataStr)
                }
            }

            write.inConsole("> ")
            val readFromConsole = (readln().lowercase()).split(" ").toMutableList()
            readFromConsole.add(flag)
            val command = commandManager.getCommand(commandPackage, readFromConsole[0], "Command")
            if (command != null && command.arg != ArgType.TOKEN) {
                if (command.status == StatusType.USER || (command.status == StatusType.ADMIN && !clientApp.user.isAdmin)) {
                    val data = consoleClientValidator.validate(readFromConsole)
                    val dataStr = Json.encodeToString(data)
                    clientApp.request(dataStr)
                } else if (command.status == StatusType.ADMIN && clientApp.user.isAdmin) {
                    val data = consoleClientValidator.validate(readFromConsole)
                    val dataStr = Json.encodeToString(data)
                    clientApp.request(dataStr)
                } else {
                    write.linesInConsole(message.getMessage("permission_upgrade"))
                }
            } else {
                write.linesInConsole(message.getMessage("weird_command"))
            }
        }
    }
}

//DONE: 1) Хэширование на клиенте (для защиты от ~воровства~ пакетов)
//DONE: 2) Хэширование на сервере (для безопасного хранения в бд)
//DONE: 3) Создание токена на сервере (хэш + соль), отправка токена клиенту
//DONE: 4) Сделать команду logout
//DONE: 5) Пофиксить команду info (она увеличивает person collection в N раз) UPD: Пофикшен ServerApp и загрузка `Person` и `Users`, а не info
//DONE: 7) Редактирование команд типа add / update / remove_by_id с учетом owner_id
//DONE????????????????????????: 8) МногопотОЧКА (понять... (простить(?)...))
//DONE: Проверить команду Upgrade To Admin
//DONE: Сделать админские команды активными для админа
//DONE: Сделать невозможную регистрацию для двух одинаковых логинов
//DONE??: Настроить хранение - работу токена / с токеном
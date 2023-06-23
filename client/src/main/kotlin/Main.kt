import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.GlobalContext.startKoin
import ru.itmo.se.prog.lab7.client.ClientApp
import ru.itmo.se.prog.lab7.client.di.notKoinModule
import ru.itmo.se.prog.lab7.client.utils.*
import ru.itmo.se.prog.lab7.client.utils.io.PrinterManager
import ru.itmo.se.prog.lab7.client.utils.io.ReaderManager
import ru.itmo.se.prog.lab7.client.utils.managers.CommandManager
import ru.itmo.se.prog.lab7.client.utils.validation.ClientValidator
import ru.itmo.se.prog.lab7.common.data.Messages
import ru.itmo.se.prog.lab7.common.data.types.ArgType
import ru.itmo.se.prog.lab7.common.data.types.LocationType
import ru.itmo.se.prog.lab7.common.data.types.StatusType


fun main() {
    startKoin {
        modules(notKoinModule)
    }
    val di = ConnectDi()
    val commandManager = di.commandManager
    val clientValidator = ClientValidator()
    val write = di.write
    val read = di.read
    val message = di.message
    val clientApp = di.clientApp
    val commandPackage = "ru.itmo.se.prog.lab7.client.commands"
    val kotlinIsBetterThanJava = true

    while (kotlinIsBetterThanJava) {
        val flag = ::main.name
        while (!clientApp.authorized) {
            write.linesInConsole(message.getMessage("login_info"))
            write.inConsole("> ")
            val readFromConsole = (read.fromConsole().lowercase()).split(" ").toMutableList()
            readFromConsole.add(flag)
            val command = commandManager.getCommand(commandPackage, readFromConsole[0], "Command")
            if (command != null && command.arg == ArgType.TOKEN || command?.location == LocationType.CLIENT) {
                val data = clientValidator.validate(readFromConsole)
                val dataStr = Json.encodeToString(data)
                clientApp.request(dataStr)
            }
        }

        write.inConsole("> ")
        val readFromConsole = (readln().lowercase()).split(" ").toMutableList()
        readFromConsole.add(flag)
        println(readFromConsole)
        val command = commandManager.getCommand(commandPackage, readFromConsole[0], "Command")
        if (command != null && command.arg != ArgType.TOKEN) {
            if (command.status == StatusType.USER || (command.status == StatusType.ADMIN && !clientApp.user.isAdmin)) {
                val data = clientValidator.validate(readFromConsole)
                val dataStr = Json.encodeToString(data)
                clientApp.request(dataStr)
            } else if (command.status == StatusType.ADMIN && clientApp.user.isAdmin) {
                val data = clientValidator.validate(readFromConsole)
                val dataStr = Json.encodeToString(data)
                clientApp.request(dataStr)
            }
            else {
                write.linesInConsole(message.getMessage("permission_upgrade"))
            }
        } else {
            write.linesInConsole(message.getMessage("weird_command"))
        }
    }
}

class ConnectDi: KoinComponent {
    val commandManager: CommandManager by inject()
    val write: PrinterManager by inject()
    val read: ReaderManager by inject()
    val message: Messages by inject()
    val serializer: Serializer by inject()
    val clientApp: ClientApp by inject()
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
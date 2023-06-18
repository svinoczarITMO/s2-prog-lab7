package ru.itmo.se.prog.lab7.client.utils.validation

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.itmo.se.prog.lab7.client.ClientApp
import ru.itmo.se.prog.lab7.client.utils.AddPersonFields
import ru.itmo.se.prog.lab7.client.utils.Hash
import ru.itmo.se.prog.lab7.client.utils.managers.CommandManager
import ru.itmo.se.prog.lab7.client.utils.io.PrinterManager
import ru.itmo.se.prog.lab7.client.utils.io.ReaderManager
import ru.itmo.se.prog.lab7.common.data.*
import ru.itmo.se.prog.lab7.common.data.types.ArgType
import ru.itmo.se.prog.lab7.common.data.types.LocationType
import ru.itmo.se.prog.lab7.common.data.types.StatusType
import ru.itmo.se.prog.lab7.common.exceptions.WrongPasswordException
import java.util.*

class ConsoleClientValidator: KoinComponent {
    private val commandManager: CommandManager by inject()
    private val message: Messages by inject()
    private val write: PrinterManager by inject()
    private val read: ReaderManager by inject()
    private val clientApp: ClientApp by inject()
    private val commandPackage = "ru.itmo.se.prog.lab7.client.commands"
    private val addPersonFields = AddPersonFields()
    private val hash = Hash()
    private var user = User(0, "LERA", "naponb")
    private var params = arrayListOf("null parameter", "null parameter", "null parameter", "null parameter", "null parameter",
        "null parameter", "null parameter", "null parameter", "null parameter", "null parameter")
    private val dataObj = Data("command", "none",
        Person(0,"Nikita", Coordinates(1.4f, 8.8f), Date(),180, 68, Color.YELLOW, Country.VATICAN, Location(1,2,3), -1), "",
        User(0,"login", "password"),
        "main", ArgType.NO_ARG, StatusType.USER, LocationType.CLIENT, "")

    fun validate (data: MutableList<String>): Data {
        val commandName = data[0]
        val oneArg = data[1]
        val placeFlag = data.last()
        val command = commandManager.getCommand(commandPackage, commandName, "Command")
        var isExecuteScript = false

        dataObj.name = commandName
        dataObj.placeFlag = placeFlag
        dataObj.argType = command?.arg!!
        dataObj.statusType = command.status
        dataObj.locationType = command.location
        dataObj.token = clientApp.token
        dataObj.user = clientApp.user

        if (commandName == "execute_script") {
            isExecuteScript = true
        }

        if (command.location == LocationType.SERVER) {
            if (!isExecuteScript) {
                when (command.arg) {
                     ArgType.NO_ARG -> {
                         dataObj.oneArg = ""
                     }

                    ArgType.ONE_ARG -> {
                        dataObj.oneArg = oneArg
                    }

                    ArgType.OBJECT -> {
                        val obj = makeAnObject(placeFlag)
                        dataObj.obj = obj
                    }

                    ArgType.OBJECT_PLUS -> {
                        val obj = makeAnObject(placeFlag)
                        dataObj.obj = obj
                        dataObj.oneArg = oneArg
                    }

                    ArgType.TOKEN -> {
                        val typeOfToken = dataObj.name
                        user = signing(placeFlag, typeOfToken)
                        dataObj.user = user
                    }
                }

            }
            return dataObj
        } else {
            write.linesInConsole(command.execute(dataObj))
            return dataObj
        }
    }

    private fun signing (placeFlag: String, type: String): User {
        if (type == "reg") {
            write.inConsole(message.getMessage("enter_login"))
            user.login = hash.encrypt(read.fromConsole())
            user.password = hash.encrypt(regPassword() as String)
        } else if (type == "login") {
            write.inConsole(message.getMessage("enter_login"))
            user.login = hash.encrypt(read.fromConsole())
            write.inConsole(message.getMessage("enter_password"))
            user.password = hash.encrypt(read.fromConsole())
        }
        return user
    }

    private fun makeAnObject (placeFlag: String): Person {
        return Person(0,
            addPersonFields.name(params[0], placeFlag),
            Coordinates(
                addPersonFields.coordinateX(params[1], placeFlag),
                addPersonFields.coordinateY(params[2], placeFlag)),
            Date(),
            addPersonFields.height(params[3], placeFlag),
            addPersonFields.weight(params[4], placeFlag),
            addPersonFields.hairColor(params[5], placeFlag),
            addPersonFields.nationality(params[6], placeFlag),
            Location(
                addPersonFields.locationX(params[7], placeFlag),
                addPersonFields.locationY(params[8], placeFlag),
                addPersonFields.locationZ(params[9], placeFlag)
            ),
            -1
        )
    }

    private fun regPassword(): Any {
        var password = ""
        try {
            write.inConsole(message.getMessage("enter_password"))
            val checkPassword = read.fromConsole()
            write.inConsole(message.getMessage("repeat_password"))
            val repeatPassword = read.fromConsole()
            if (checkPassword != repeatPassword) {
                throw WrongPasswordException()
            } else {
                password = checkPassword
            }
        } catch (e: WrongPasswordException) {
            write.linesInConsole(message.getMessage("no_match_password"))
            return regPassword()
        }
        return password
    }

//    private fun preValidation (path: String): ArrayList<Array<String>> {
//        val commands = arrayListOf<Array<String>>()
//        val errorArray = arrayListOf<Array<String>>(arrayOf("ERROR"))
//        val strings = File(path).readStrings()
//        val params = arrayListOf<String>()
//
//        if (strings.isNotEmpty()) {
//            for (index in 0 until strings.size) {
//                val arr = arrayListOf<String>()
//                val currentLine = strings[index].split(" ")
//                val currentCommand = commandManager.getCommand(commandPackage, currentLine[0], "Command")
//                if (currentCommand != null) {
//                    if (currentLine[0] == "execute_script" && currentLine.last() == path) {
//                        return errorArray
//                    }
//                    if (currentCommand.arg == ArgType.OBJECT || currentCommand.arg == ArgType.OBJECT_PLUS) {
//                        for (n in 1..10) {
//                            val param = strings.getOrElse(index + n) { "" }.trim().lowercase()
//                            if (param.isNotEmpty()) {
//                                params[n - 1] = param
//                            }
//                        }
//                        //команда, список полей
//                        currentLine.forEach { arr.add(it) }
//                        params.forEach { arr.add(it) }
//                        commands.add(arr.toTypedArray())
//                    } else {
//                        //команда, список аргументов
//                        currentLine.forEach { arr.add(it) }
//                        commands.add(arr.toTypedArray())
//                    }
//                }
//            }
//        }
//        return commands
//    }
}
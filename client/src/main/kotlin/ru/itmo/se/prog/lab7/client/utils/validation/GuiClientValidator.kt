package ru.itmo.se.prog.lab7.client.utils.validation

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.itmo.se.prog.lab7.client.ClientApp
import ru.itmo.se.prog.lab7.client.commands.Command
import ru.itmo.se.prog.lab7.client.utils.AddPersonFields
import ru.itmo.se.prog.lab7.client.utils.Hash
import ru.itmo.se.prog.lab7.client.utils.io.PrinterManager
import ru.itmo.se.prog.lab7.client.utils.io.ReaderManager
import ru.itmo.se.prog.lab7.client.utils.managers.CommandManager
import ru.itmo.se.prog.lab7.common.data.*
import ru.itmo.se.prog.lab7.common.data.types.ArgType
import ru.itmo.se.prog.lab7.common.data.types.LocationType
import ru.itmo.se.prog.lab7.common.data.types.StatusType
import ru.itmo.se.prog.lab7.common.exceptions.WrongPasswordException
import java.util.*

class GuiClientValidator: KoinComponent {
    private val commandManager: CommandManager by inject()
    private val message: Messages by inject()
    private val write: PrinterManager by inject()
    private val read: ReaderManager by inject()
    private val clientApp: ClientApp by inject()
    private val commandPackage = "ru.itmo.se.prog.lab7.client.commands"
    private val addPersonFields = AddPersonFields()
    private val hash = Hash()
    private var user = User(0, "LERA", "naponb")
    var params = arrayListOf("null parameter", "null parameter", "null parameter", "null parameter", "null parameter",
        "null parameter", "null parameter", "null parameter", "null parameter", "null parameter")
    private val dataObj = Data("command", "none",
        Person(0,"Nikita", Coordinates(1.4f, 8.8f), Date(),180, 68, Color.YELLOW, Country.VATICAN, Location(1,2,3), -1), "",
        User(0,"login", "password"),
        "main", ArgType.NO_ARG, StatusType.USER, LocationType.CLIENT, "")

    fun validate (data: MutableList<*>): Data {
        val commandName = data[0] as String
        val placeFlag = data.last() as String
        val args = data[1] as MutableMap<String, String>
        var oneArg = ""
        val command = commandManager.getCommand(commandPackage, commandName, "Command")
        var isExecuteScript = false

        dataObj.name = commandName
        dataObj.placeFlag = placeFlag
        dataObj.argType = command?.arg!!
        dataObj.statusType = command.status
        dataObj.locationType = command.location
        dataObj.token = clientApp.token
        dataObj.user = clientApp.user

        if (dataObj.argType == ArgType.ONE_ARG) {
            oneArg = args.get("oneArg") as String
        }

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
                        user = signing(placeFlag, typeOfToken, args)
                        if (user.login != message.getMessage("no_match_passwords")) {
                            dataObj.answerStr = ""
                            dataObj.user = user
                        } else {
                            dataObj.answerStr = message.getMessage("no_match_passwords")
                        }
                    }
                }

            }
            return dataObj
        } else {
            val result = command.execute(dataObj)
            dataObj.answerStr = result
            write.linesInConsole(result)
            return dataObj
        }
    }

    private fun signing (placeFlag: String, type: String, params: MutableMap<String, String>): User {
        if (type == "reg") {
            val login: String by params
            val password: String by params
            val repeatedPassword: String by params
            user.login = hash.encrypt(login)
            val pass = regPassword(password, repeatedPassword) as String
            if (pass == message.getMessage("no_match_passwords")) {
                user.login = message.getMessage("no_match_passwords") as String
                return user
            }
            user.password = hash.encrypt(pass)
        } else if (type == "login") {
            val login: String by params
            val password: String by params
            user.login = hash.encrypt(login)
            user.password = hash.encrypt(password)
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

    private fun regPassword(checkPassword: String, repeatPassword: String): Any {
        var password = ""
        try {
            if (checkPassword != repeatPassword) {
                throw WrongPasswordException()
            } else {
                password = checkPassword
            }
        } catch (e: WrongPasswordException) {
            write.linesInConsole(message.getMessage("no_match_passwords"))
            return message.getMessage("no_match_passwords") as String
        }
        return password
    }
}
package ru.itmo.se.prog.lab7.server.commands.server

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.itmo.se.prog.lab7.common.data.Data
import ru.itmo.se.prog.lab7.common.data.types.*
import ru.itmo.se.prog.lab7.server.ServerApp
import ru.itmo.se.prog.lab7.server.commands.Command
import ru.itmo.se.prog.lab7.server.utils.SignManager
import ru.itmo.se.prog.lab7.server.utils.managers.DataBaseManager
import ru.itmo.se.prog.lab7.server.utils.Hash


class Login: Command(ArgType.TOKEN, StatusType.USER, LocationType.SERVER), KoinComponent {
    private val signManager = SignManager()
    private val hash = Hash()
    private val dbmanager: DataBaseManager by inject()
    private val serverApp: ServerApp by inject()

    override fun getName(): String {
        return "login"
    }

    override fun getDescription(): String {
        return " - входит в учетную запись."
    }

    override fun execute(data: Data): Data {
        var result = ""

        val login = signManager.logLogin(hash.encryptLogin(data.user.login)) as String
        if (login == message.getMessage("invalid_login1")) {
            data.answerStr = login
            return data
        } else {
            data.user.login = login
        }
        val id = dbmanager.connect().prepareStatement("select id from users where users.login = '$login'").executeQuery()
        while (id.next()) {
            data.user.id = id.getInt("id")
        }
        val password = signManager.logPassword(hash.encryptPassword(data.user.password)) as String
        if (password == message.getMessage("invalid_password")) {
            data.answerStr = password
            return data
        } else {
            data.user.password = password
        }
        val isAdmin = dbmanager.connect().prepareStatement("select is_admin from users where users.login = '$login'").executeQuery()
        while (isAdmin.next()) {
            data.user.isAdmin = isAdmin.getBoolean("is_admin")
        }
        dbmanager.listOfUsers.forEach {

            println("login: $login")
            println("it.login: ${it.login}")
            println("password: $password")
            println("it.password: ${it.password}")

            if (login == it.login && password == it.password) {
                result = message.getMessage("successful_login")!!
                val token = (hash.encryptLogin(login) + hash.encryptPassword(password) + hash.generateSalt(login)).drop(92)
                data.token = token
                data.answerStr = result
                dbmanager.updateToken(data.user.id, data.token)
                serverApp.tokens.add(token)
                serverApp.serverSessionUsers.add(data.user)

                println("token: ${data.token}")
                return data
            } else {
                result = message.getMessage("wrong_login")!!
            }
        }
        data.answerStr = result
        return data
    }
}
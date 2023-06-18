package ru.itmo.se.prog.lab7.client.commands.server

import ru.itmo.se.prog.lab7.client.commands.Command
import ru.itmo.se.prog.lab7.common.data.Data
import ru.itmo.se.prog.lab7.common.data.Messages
import ru.itmo.se.prog.lab7.common.data.types.ArgType
import ru.itmo.se.prog.lab7.common.data.types.LocationType
import ru.itmo.se.prog.lab7.common.data.types.StatusType

class Login: Command(ArgType.TOKEN, StatusType.USER, LocationType.SERVER) {
    override fun getName(): String {
        return "login"
    }

    override fun getDescription(): String {
        return " - входит в учетную запись.\n"
    }

    override fun execute(data: Data): String? {
        return "// will not implement"
    }
}
package ru.itmo.se.prog.lab7.client.commands.server

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.itmo.se.prog.lab7.client.ClientApp
import ru.itmo.se.prog.lab7.client.commands.Command
import ru.itmo.se.prog.lab7.common.data.Data
import ru.itmo.se.prog.lab7.common.data.Messages
import ru.itmo.se.prog.lab7.common.data.types.ArgType
import ru.itmo.se.prog.lab7.common.data.types.LocationType
import ru.itmo.se.prog.lab7.common.data.types.StatusType
import java.io.File

class UpgradeToAdmin: Command(ArgType.ONE_ARG, StatusType.USER, LocationType.SERVER), KoinComponent {
    private val clientApp: ClientApp by inject()
    private val secretKey = File("D:\\ITMO\\2nd-semester\\prog-labs\\s2-prog-lab7\\common\\src\\main\\kotlin\\ru\\itmo\\se\\prog\\lab7\\common\\data\\.admin-key").readText()

    override fun getName(): String {
        return "uta"
    }

    override fun getDescription(): String {
        return " --key - делает вашу учетную запись админской\n"
    }

    override fun execute(data: Data): String? {
        if (data.oneArg == secretKey) {
            clientApp.user.isAdmin = true

        }
        return ""
    }
}
package ru.itmo.se.prog.lab7.server.commands.server

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.itmo.se.prog.lab7.common.data.Data
import ru.itmo.se.prog.lab7.common.data.Messages
import ru.itmo.se.prog.lab7.common.data.types.ArgType
import ru.itmo.se.prog.lab7.common.data.types.LocationType
import ru.itmo.se.prog.lab7.common.data.types.StatusType
import ru.itmo.se.prog.lab7.server.commands.Command
import ru.itmo.se.prog.lab7.server.utils.managers.DataBaseManager
import java.io.File

class UpgradeToAdmin: Command(ArgType.ONE_ARG, StatusType.USER, LocationType.SERVER), KoinComponent {
    private val secretKey = File("D:\\ITMO\\2nd-semester\\prog-labs\\s2-prog-lab8\\common\\src\\main\\kotlin\\ru\\itmo\\se\\prog\\lab7\\common\\data\\.admin-key").readText()
    private val dbmanager: DataBaseManager by inject ()

    override fun getName(): String {
        return "uta"
    }

    override fun getDescription(): String {
        return " ${Messages.cyanText}--key${Messages.resetColor} - делает вашу учетную запись админской\n"
    }

    override fun execute(data: Data): Data {
        if (data.oneArg == secretKey) {
            dbmanager.updateIsAdminQuery.setInt(1, data.user.id)
            dbmanager.updateIsAdminQuery.executeUpdate()
            data.answerStr = message.getMessage("correct_key")
        } else {
            data.answerStr = message.getMessage("wrong_key")
        }
        return data
    }
}
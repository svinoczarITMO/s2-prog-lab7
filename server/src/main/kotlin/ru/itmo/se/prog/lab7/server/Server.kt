package ru.itmo.se.prog.lab7.server

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.GlobalContext.startKoin
import ru.itmo.se.prog.lab7.server.di.serverKoinModule
import ru.itmo.se.prog.lab7.server.utils.Loader
import ru.itmo.se.prog.lab7.server.utils.managers.CommandManager
import ru.itmo.se.prog.lab7.server.utils.managers.DataBaseManager
import java.io.File
import java.util.concurrent.Executors
import kotlin.concurrent.thread

fun main(args: Array<String>) {
    startKoin {
        modules(serverKoinModule)
    }
    val di = ConnectDi()
    di.dbmanager.uploadAllUsers()
    di.dbmanager.uploadAllPersons()

    val historyFile = File("D:\\ITMO\\2nd-semester\\prog-labs\\s2-prog-lab8\\server\\src\\main\\kotlin\\ru\\itmo\\se\\prog\\lab7\\server\\data\\history.log")
    historyFile.writeText("")
    val loader = Loader()
    val server = ServerApp()

    Thread {
        server.start()
    }.start()
}

class ConnectDi: KoinComponent {
    val dbmanager: DataBaseManager by inject()
}
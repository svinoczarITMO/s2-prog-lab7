package ru.itmo.se.prog.lab7.client.di

import org.koin.dsl.module
import ru.itmo.se.prog.lab7.client.ClientApp
import ru.itmo.se.prog.lab7.client.utils.*
import ru.itmo.se.prog.lab7.client.utils.io.PrinterManager
import ru.itmo.se.prog.lab7.client.utils.io.ReaderManager
import ru.itmo.se.prog.lab7.client.utils.managers.CollectionManager
import ru.itmo.se.prog.lab7.client.utils.managers.CommandManager
import ru.itmo.se.prog.lab7.client.view.MainView
import ru.itmo.se.prog.lab7.common.data.Messages

/**
 * Koin Module with all needed objects.
 *
 * @author svinoczar
 * @since 1.0.0
 */
val notKoinModule = module {
    single {
        PrinterManager()
    }

    single {
        ReaderManager()
    }

    single {
        Messages()
    }

    single {
        CommandManager()
    }

    single {
        CollectionManager()
    }

    single {
        Serializer()
    }

    single {
        ClientApp()
    }

    single {
        MainView()
    }
}
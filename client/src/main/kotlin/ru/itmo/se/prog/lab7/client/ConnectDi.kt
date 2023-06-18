import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.itmo.se.prog.lab7.client.ClientApp
import ru.itmo.se.prog.lab7.client.utils.Serializer
import ru.itmo.se.prog.lab7.client.utils.io.PrinterManager
import ru.itmo.se.prog.lab7.client.utils.io.ReaderManager
import ru.itmo.se.prog.lab7.client.utils.managers.CommandManager
import ru.itmo.se.prog.lab7.client.view.MainView
import ru.itmo.se.prog.lab7.common.data.Messages

class ConnectDi: KoinComponent {
    val commandManager: CommandManager by inject()
    val write: PrinterManager by inject()
    val read: ReaderManager by inject()
    val message: Messages by inject()
    val serializer: Serializer by inject()
    val clientApp: ClientApp by inject()
    val mainView: MainView by inject()
}
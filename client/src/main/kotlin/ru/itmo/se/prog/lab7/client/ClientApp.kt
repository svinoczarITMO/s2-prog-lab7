package ru.itmo.se.prog.lab7.client

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.itmo.se.prog.lab7.client.utils.io.PrinterManager
import ru.itmo.se.prog.lab7.client.utils.Serializer
import ru.itmo.se.prog.lab7.common.data.Data
import ru.itmo.se.prog.lab7.common.data.Messages
import ru.itmo.se.prog.lab7.common.data.User
import java.io.*
import java.net.InetSocketAddress
import java.nio.channels.SocketChannel

class ClientApp (): KoinComponent {
    private val ip = "localhost"
    private val port = 8844
    val serializer = Serializer()
    val write = PrinterManager()
    var authorized = false
    var token = ""
    var user = User(-999, "", "", false)
    val message: Messages by inject()

    private fun connection(): SocketChannel {
        return try {
            val clientSocketChannel = SocketChannel.open()
            clientSocketChannel.socket().connect(InetSocketAddress(ip, port))
            clientSocketChannel
        } catch (e: RuntimeException) {
            println("Ошибка подключения.")
            SocketChannel.open(InetSocketAddress(ip, port))
            throw e
        }
    }

    fun request(obj: String): String {
        var result = ""
        try {
            val clientSocketChannel = connection()
            if (clientSocketChannel.isConnected) {
                val output = PrintWriter(clientSocketChannel.socket().getOutputStream())
                output.write(obj)
                output.flush()
                clientSocketChannel.shutdownOutput()
                result = response(clientSocketChannel)!!
                return result
            }
        } catch (e: Exception) {
            println("Ошибка запроса.")
        }
        return result
    }

    private fun response(clientSocketChannel: SocketChannel): String? {
        try {
            val input: InputStream = clientSocketChannel.socket().getInputStream()
            val bufferedReader = BufferedReader(InputStreamReader(input))
            val dataStr = bufferedReader.readLine()?.trim()!!
            val inputData: Data = serializer.deserializeData(dataStr)
            val result = inputData.answerStr
            if (result == message.getMessage("successful_login")) {
                authorized = true
                token = inputData.token
                user = inputData.user
            }
            if (result != null) {
                write.linesInConsole(result)
            }
            clientSocketChannel.socket().close()
            return result
        } catch (e: Exception) {
            write.linesInConsole("Ошибка при получении ответа от сервера.")
        }
        return "Ошибка при получении ответа от сервера."
    }

}
package ru.itmo.se.prog.lab7.client.view

import javafx.beans.property.SimpleStringProperty
import javafx.geometry.Pos
import javafx.scene.text.FontWeight
import ru.itmo.se.prog.lab7.client.app.MyApp
import ru.itmo.se.prog.lab7.client.commands.Command
import tornadofx.*

class HelpView: View() {
    private val resultText= SimpleStringProperty("")

    override val root= vbox {
        setPrefSize(1000.0, 400.0)
        val flag = "main"
        val commandName = "help"
        val paramList = mutableListOf(commandName, mutableMapOf<String, String>(), flag)
        MyApp.guiClientValidator.validate(paramList)
        var result = ""
        val classes = MyApp.di.commandManager.parsePackage("ru.itmo.se.prog.lab7.client.commands", "Command")
            .filter { klass -> !klass.simpleName.equals("FastAdd") && !klass.simpleName.equals("PrintCollection") && !klass.simpleName.equals("GetElement") }
        for (klass in classes) {
            val command = klass.getConstructor().newInstance() as Command
            result += (command.getName() + command.getDescription())
        }
        resultText.set(result)

        label(resultText).style {
            fontSize = 14.px
            textFill = c("#000000")
            fontFamily = "Arial"
            setAlignment(Pos.TOP_CENTER)
            padding = box(30.px, 20.px)
        }
    }
}
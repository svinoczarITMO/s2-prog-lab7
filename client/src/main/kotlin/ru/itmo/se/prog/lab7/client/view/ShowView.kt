package ru.itmo.se.prog.lab7.client.view

import javafx.beans.property.SimpleStringProperty
import javafx.geometry.Pos
import ru.itmo.se.prog.lab7.client.app.MyApp
import tornadofx.*

class ShowView: View() {
    private val resultText= SimpleStringProperty("")


    override val root= vbox {
        setPrefSize(350.0, 450.0)
        val result = MyApp.executeServerCommand.run("show", mutableMapOf())
        resultText.set(result)
        label(resultText).style {
            setAlignment(Pos.TOP_CENTER)
            padding = box(5.px, 5.px)
            fontSize = 15.px
            textFill = c("#000000")
            fontFamily = "Arial"
        }
    }
}

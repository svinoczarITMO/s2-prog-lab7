package ru.itmo.se.prog.lab7.client.app

import ConnectDi
import javafx.beans.property.SimpleStringProperty
import javafx.scene.Scene
import org.koin.core.component.KoinComponent
import ru.itmo.se.prog.lab7.client.ClientApp
import ru.itmo.se.prog.lab7.client.utils.validation.GuiClientValidator
import ru.itmo.se.prog.lab7.client.view.MainView
import tornadofx.*
import tornadofx.FX.Companion.primaryStage
import java.nio.charset.Charset
import java.util.*


class MyApp: App(MainView::class), KoinComponent{
    override fun createPrimaryScene(view: UIComponent): Scene {
        val scene = Scene(view.root)
        primaryStage.width = 960.0
        primaryStage.height = 540.0
        return scene
    }
    companion object {
        val di = ConnectDi()
        val guiClientValidator = GuiClientValidator()
        val executeServerCommand = ExecuteServerCommand()
        var bundle = ResourceBundle.getBundle("messages", Locale("ru"), ResourceBundle.Control.getNoFallbackControl(ResourceBundle.Control.FORMAT_PROPERTIES))
        var setBundle: ResourceBundle
            get() = bundle
            set(value) {
                bundle = value
            }
        var login = SimpleStringProperty()
    }
}
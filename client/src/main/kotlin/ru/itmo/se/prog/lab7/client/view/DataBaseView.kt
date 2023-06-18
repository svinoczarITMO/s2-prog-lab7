package ru.itmo.se.prog.lab7.client.view

import javafx.collections.ObservableList
import javafx.geometry.Pos
import javafx.scene.paint.Color
import ru.itmo.se.prog.lab7.client.app.MyApp
import ru.itmo.se.prog.lab7.common.data.Person
import tornadofx.*

class DataBaseView() : View() {
    private val tableData: ObservableList<Person> = mutableListOf<Person>().asObservable()

    override val root = tabpane {
        tab("Таблица") {
            borderpane() {
                primaryStage.isResizable = true
                right {
                    scrollpane {
                        style {
                            fitToHeight = true
                            fitToWidth = true
                        }
                        tableview(tableData) {
                            setPrefSize(1500.0, 900.0)
                            style {
                            }
                            isEditable = false
                            column(MyApp.bundle.getString("id"), Person::id)
                            column(MyApp.bundle.getString("name"), Person::name)
                            column(MyApp.bundle.getString("coordinate_x"), Person::coordinates).cellFormat {
                                text = it.x.toString()
                            }
                            column(MyApp.bundle.getString("coordinate_y"), Person::coordinates).cellFormat {
                                text = it.y.toString()
                            }
                            column(MyApp.bundle.getString("hair_color"), Person::hairColor)
                            column(MyApp.bundle.getString("nationality"), Person::nationality)
                            column(MyApp.bundle.getString("location_x"), Person::location).cellFormat {
                                text = it.x.toString()
                            }
                            column(MyApp.bundle.getString("location_y"), Person::location).cellFormat {
                                text = it.y.toString()
                            }
                            column(MyApp.bundle.getString("location_z"), Person::location).cellFormat {
                                text = it.z.toString()
                            }
                            column(MyApp.bundle.getString("owner_id"), Person::ownerId)
                        }

                    }

                }
                left {
                    maxWidth = 240.0
                    vbox {
                        label() {
//                            textProperty().bind(MyApp.login)
                        }.style {
                            setAlignment(Pos.TOP_CENTER)
                            fontFamily = "Small capital"
                            fontSize = 20.px
                            padding = box(10.px, 20.px)
                        }
                        style {
                            setAlignment(Pos.TOP_LEFT)
                            padding = box(30.px, 20.px)
                            setAlignment(Pos.TOP_CENTER)
                        }
                        spacing = 10.0

                        button(MyApp.bundle.getString("help")) {
                            style {
                                textFill = Color.WHITE
                                backgroundColor += Color.BLACK
                                padding = box(10.px, 5.px)
                            }
                            minWidth = 100.0
                            minHeight = 50.0
                            action {
                                openInternalWindow(HelpView::class)
                            }
                        }

                        button(MyApp.bundle.getString("info")) {
                            style {
                                textFill = Color.WHITE
                                backgroundColor += Color.BLACK
                                padding = box(10.px, 5.px)
                            }
                            minWidth = 100.0
                            minHeight = 50.0
                            action {
                                openInternalWindow(InfoView::class)
                            }
                        }
                        button(MyApp.bundle.getString("show")) {
                            style {
                                textFill = Color.WHITE
                                backgroundColor += Color.BLACK
                            }
                            minWidth = 100.0
                            minHeight = 50.0
                            action {
                                openInternalWindow(ShowView::class)
                            }
                        }

                        button(MyApp.bundle.getString("clear")) {
                            style {
                                textFill = Color.WHITE
                                backgroundColor += Color.BLACK
                                padding = box(10.px, 5.px)
                            }
                            minWidth = 100.0
                            minHeight = 50.0
                            action {
                                MyApp.executeServerCommand.run("clear", mutableMapOf())
                            }
                        }

                        button(MyApp.bundle.getString("add")) {
                            style {
                                textFill = Color.WHITE
                                backgroundColor += Color.BLACK
                                padding = box(10.px, 5.px)
                            }
                            minWidth = 100.0
                            minHeight = 50.0
                            action {
                                openInternalWindow(AddView::class)
                            }
                        }
//                        button(MyApp.bundle.getString("Remove\ngreater_key")) {
//                            style {
//                                textFill = Color.WHITE
//                                backgroundColor += Color.BLACK
//                                padding = box(10.px, 5.px)
//                            }
//                            minWidth = 100.0
//                            minHeight = 50.0
//                            action {
//                                openInternalWindow(RemoveByGreaterKeyScreen::class)
//                            }
//                        }
//                        button(MyApp.bundle.getString("Remove\nlower_key")) {
//                            style {
//                                textFill = Color.WHITE
//                                backgroundColor += Color.BLACK
//                                padding = box(10.px, 5.px)
//                            }
//                            minWidth = 100.0
//                            minHeight = 50.0
//                            action {
//                                openInternalWindow(RemoveByLowerKeyScreen::class)
//                            }
//                        }
//                        button(MyApp.bundle.getString("Count_less\nthan_group\nadmin")) {
//                            style {
//                                textFill = Color.WHITE
//                                backgroundColor += Color.BLACK
//                                padding = box(10.px, 5.px)
//                            }
//                            minWidth = 100.0
//                            minHeight = 70.0
//                            action {
//                                openInternalWindow(CountLessThenAdmin::class)
//                            }
//                        }
//                        button(MyApp.bundle.getString("Remove")) {
//                            style {
//                                textFill = Color.WHITE
//                                backgroundColor += Color.BLACK
//                                padding = box(10.px, 5.px)
//                            }
//                            minWidth = 100.0
//                            minHeight = 50.0
//                            action {
//                                openInternalWindow(RemoveScreen::class)
//                            }
//                        }
//                        button(MyApp.bundle.getString("Update_id")) {
//                            style {
//                                textFill = Color.WHITE
//                                backgroundColor += Color.BLACK
//                                padding = box(10.px, 5.px)
//                            }
//                            minWidth = 100.0
//                            minHeight = 50.0
//                            action {
//                                openInternalWindow(UpdateIdScreen::class)
//                            }
//                        }

//                        button(MyApp.bundle.getString("Log_out")) {
//                            style {
//                                textFill = Color.WHITE
//                                backgroundColor += Color.BLACK
//                                padding = box(10.px, 5.px)
//                            }
//                            minWidth = 100.0
//                            minHeight = 50.0
//                            action {
//                                MyApp.readerOfCommands.readCommand("log_out")
//                                replaceWith<LoginScreen>(sizeToScene = true)
//                            }
//                        }
//                        button(MyApp.bundle.getString("Exit")) {
//                            style {
//                                textFill = Color.WHITE
//                                backgroundColor += Color.BLACK
//                                padding = box(10.px, 5.px)
//                            }
//                            minWidth = 100.0
//                            minHeight = 50.0
//                            action {
//                                MyApp.readerOfCommands.readCommand("exit")
//                            }
//                        }
//                    }
//                }
//                onRefresh()
//            }
//        }
//        tab("Карта") {
//            pane {
//                val imageView = imageview("/map1.png") {
//                    isPreserveRatio = true
//                }
//                imageView.fitHeight = 900.0
//                imageView.fitWidth = 1800.0
//                val circle1 = circle(radius = 10.0) {
//                    layoutX = 790.0
//                    layoutY = 335.0
//                    tooltip() {
//                        textProperty().bind(spainC)
//                    }
//                    visibleProperty().bind(spainC.isNotEmpty)
//                }
//                val arrow1 = polygon(0.0, 0.0, 20.0, 10.0, 0.0, 20.0) {
//                    layoutX = circle1.layoutX + 5
//                    layoutY = circle1.layoutY - 37
//                    isVisible = false
//                }
//                circle1.setOnMouseEntered {
//                    arrow1.isVisible = true
//                }
//
//                circle1.setOnMouseExited {
//                    arrow1.isVisible = false
//                }
//                val circle2 = circle(radius = 10.0) {
//                    layoutX = 835.0
//                    layoutY = 287.0
//                    tooltip() {
//                        textProperty().bind(germanC)
//                    }
//                    visibleProperty().bind(germanC.isNotEmpty)
//                }
//                val arrow2 = polygon(0.0, 0.0, 20.0, 10.0, 0.0, 20.0) {
//                    layoutX = circle2.layoutX + 5
//                    layoutY = circle2.layoutY - 37
//                    isVisible = false
//                }
//                circle2.setOnMouseEntered {
//                    arrow2.isVisible = true
//                }
//
//                circle2.setOnMouseExited {
//                    arrow2.isVisible = false
//                }
//                val circle3 = circle(radius = 10.0) {
//                    layoutX = 1200.0
//                    layoutY = 400.0
//                    tooltip {
//                        textProperty().bind(indiaC)
//                    }
//                    visibleProperty().bind(indiaC.isNotEmpty)
//                }
//                val arrow3 = polygon(0.0, 0.0, 20.0, 10.0, 0.0, 20.0) {
//                    layoutX = circle3.layoutX + 5
//                    layoutY = circle3.layoutY - 37
//                    isVisible = false
//                }
//                circle3.setOnMouseEntered {
//                    arrow3.isVisible = true
//                }
//
//                circle3.setOnMouseExited {
//                    arrow3.isVisible = false
//                }
                    }
                }
            }
        }
    }
}
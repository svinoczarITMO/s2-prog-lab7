package ru.itmo.se.prog.lab7.client.utils

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.itmo.se.prog.lab7.client.utils.io.PrinterManager
import ru.itmo.se.prog.lab7.client.utils.io.ReaderManager
import ru.itmo.se.prog.lab7.common.data.Color
import ru.itmo.se.prog.lab7.common.data.Country
import ru.itmo.se.prog.lab7.common.data.Messages
import ru.itmo.se.prog.lab7.common.exceptions.ElementAmountException

/**
 * Handles inputed data for Person constructor fields.
 *
 * @author svinoczar
 * @since 1.0.0
 */
class AddPersonFields: KoinComponent {
    private val message: Messages by inject()
    private val write: PrinterManager by inject()
    private val read: ReaderManager by inject()
    /**
     * Sets inputed name of current Person element.
     *
     * @return name as String
     */
    fun name (arg: String?, flag: String): String {
        val name: String = if (flag == "main") {
            write.linesInConsole(message.getMessage("enter_name"))
            read.fromConsole()
        } else {
            arg as String
        }

        try {
            if (name.isBlank()) throw ElementAmountException()
            return name
        } catch (e: ElementAmountException) {
            write.linesInConsole("Строка не может быть пустой! Введите имя ещё раз.")
        }
        return name(arg, flag)
    }

    /**
     * Sets inputed coordinate "x" of current Person element.
     *
     * @return Float
     */
    fun coordinateX (arg: String?, flag: String): Float {
        val coordinateX: String = if (flag == "main") {
            write.linesInConsole(message.getMessage("enter_coordinateX"))
            read.fromConsole()
        } else {
            arg as String
        }
        try {
            if (coordinateX.toFloat() <= 214) {
                return coordinateX.toFloat()
            } else {
                write.linesInConsole("Координата \"x\" должна быть не больше 214!")
                if (flag == "main") {
                    return coordinateX(arg, flag)
                } else {return coordinateX as Float}
            }
        } catch (e: NumberFormatException) {
            write.linesInConsole(message.getMessage("NumberFormatException"))
            println("x")
        }
        if (flag == "main") {
            return coordinateX(arg, flag)
        } else {return coordinateX as Float}
    }

    /**
     * Sets inputed coordinate "x" of current Person element.
     *
     * @return Float
     */
    fun coordinateY (arg: String?, flag: String): Float {
        val coordinateY: String = if (flag == "main") {
            write.linesInConsole(message.getMessage("enter_coordinateY"))
            read.fromConsole()
        } else {
            arg as String
        }
        try {
            if (coordinateY.toFloat() <= 794) {
                return coordinateY.toFloat()
            } else {
                write.linesInConsole("Координата \"y\" должна быть не больше 794!")
                if (flag == "main") {
                    return coordinateX(arg, flag)
                } else {return coordinateY as Float}
            }
        } catch (e: NumberFormatException) {
            write.linesInConsole(message.getMessage("NumberFormatException"))
            println("y")
        }
        if (flag == "main") {
            return coordinateX(arg, flag)
        } else {return coordinateY as Float}
    }

    /**
     * Sets inputed height of current Person element.
     *
     * @return Int
     */
    fun height (arg: String?, flag: String): Int {
        val height: String = if (flag == "main") {
            write.linesInConsole(message.getMessage("enter_height"))
            read.fromConsole()
        } else {
            arg as String
        }
        try {
            if (height.toInt() > 0) {
                return height.toInt()
            } else {
                write.linesInConsole("Рост должен быть больше нуля!")
                if (flag == "main") {
                    return height(arg, flag)
                } else {return height as Int}
            }
        } catch (e: NumberFormatException) {
            write.linesInConsole(message.getMessage("NumberFormatException"))
            println("height")
        }
        if (flag == "main") {
            return height(arg, flag)
        } else {return height as Int}
    }

    /**
     * Sets inputed weight of current Person element.
     *
     * @return Long
     */
    fun weight (arg: String?, flag: String): Long {
        val weight: String = if (flag == "main") {
            write.linesInConsole(message.getMessage("enter_weight"))
            read.fromConsole()
        } else {
            arg as String
        }
        try {
            if (weight.toLong() > 0) {
                return weight.toLong()
            } else {
                write.linesInConsole("Вес должен быть больше нуля!")
                if (flag == "main") {
                    return weight(arg, flag)
                } else {return weight as Long}
            }
        } catch (e: NumberFormatException) {
            write.linesInConsole(message.getMessage("NumberFormatException"))
            println("weight")
        }
        if (flag == "main") {
            return weight(arg, flag)
        } else {return weight as Long}
    }

    /**
     * Sets inputed color of hair of current Person element.
     *
     * @return Color
     */
    fun hairColor (arg: String?, flag: String): Color {
        val hairColor: String = if (flag == "main") {
            write.linesInConsole(message.getMessage("enter_hairColor"))
            for (value in 0 until Color.values().size) {
                write.inConsole("${Color.values()[value]}")
                if (value < Color.values().size-1) {
                    write.inConsole(", ")
                }
            }
            write.linesInConsole("")
            read.fromConsole().uppercase()
        } else {
            arg?.uppercase() as String
        }
        try {
            return Color.valueOf(hairColor)
        } catch (e: IllegalArgumentException) {
            write.linesInConsole(message.getMessage("IllegalColor"))
        }
        if (flag == "main") {
            return hairColor(arg, flag)
        } else {return hairColor as Color
        }
    }

    /**
     * Sets inputed nationality of current Person element.
     *
     * @return Country
     */
    fun nationality (arg: String?, flag: String): Country {

        write.linesInConsole("")
        val nationality: String = if (flag == "main") {
            write.linesInConsole(message.getMessage("enter_nationality"))
            for (value in 0 until Country.values().size) {
                write.inConsole("${Country.values()[value]}")
                if (value < Country.values().size-1) {
                    write.inConsole(",")
                }
            }
            write.linesInConsole("")
            read.fromConsole().uppercase()
        } else {
            arg?.uppercase() as String
        }
        try {
            return Country.valueOf(nationality)
        } catch (e: IllegalArgumentException) {
            write.linesInConsole(message.getMessage("IllegalCountry"))
        }
        if (flag == "main") {
            return nationality(arg, flag)
        } else {return nationality as Country
        }
    }

    /**
     * Sets inputed location "x" of current Person element.
     *
     * @return Int
     */
    fun locationX (arg: String?, flag: String): Int {
        val locationX: String = if (flag == "main") {
            write.linesInConsole(message.getMessage("enter_locationX"))
            read.fromConsole()
        } else {
            arg as String
        }
        try {
            return locationX.toInt()
        } catch (e: NumberFormatException) {
            write.linesInConsole(message.getMessage("NumberFormatException"))
            println("X")
        }
        if (flag == "main") {
            return locationX(arg, flag)
        } else {return locationX as Int}
    }

    /**
     * Sets inputed location "y" of current Person element.
     *
     * @return Long
     */
    fun locationY (arg: String?, flag: String): Long? {
        val locationY: String = if (flag == "main") {
            write.linesInConsole(message.getMessage("enter_locationY"))
            read.fromConsole()
        } else {
            arg as String
        }
        try {
            return locationY.toLong()
        } catch (e: NumberFormatException) {
            write.linesInConsole(message.getMessage("NumberFormatException"))
            println("Y")
        }
        if (flag == "main") {
            return locationY(arg, flag)
        } else {return locationY as Long}
    }

    /**
     * Sets inputed location "z" of current Person element.
     *
     * @return Int
     */
    fun locationZ (arg: String?, flag: String): Int {
        val locationZ: String = if (flag == "main") {
            write.linesInConsole(message.getMessage("enter_locationZ"))
            read.fromConsole()
        } else {
            arg as String
        }
        try {
            return locationZ.toInt()
        } catch (e: NumberFormatException) {
            write.linesInConsole(message.getMessage("NumberFormatException"))
            println("Z")
        }
        if (flag == "main") {
            return locationZ(arg, flag)
        } else {return locationZ as Int}
    }
}
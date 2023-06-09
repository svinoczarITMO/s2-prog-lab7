package ru.itmo.se.prog.lab7.server.commands.server

import ru.itmo.se.prog.lab7.common.data.Color
import ru.itmo.se.prog.lab7.common.data.Data
import ru.itmo.se.prog.lab7.common.data.Messages
import ru.itmo.se.prog.lab7.common.data.types.ArgType
import ru.itmo.se.prog.lab7.common.data.types.LocationType
import ru.itmo.se.prog.lab7.common.data.types.StatusType
import ru.itmo.se.prog.lab7.server.commands.Command

/**
 * Counts elements by hair color.
 *
 * @author svinoczar
 * @since 1.0.0
 */
class CountByHairColor: Command(ArgType.ONE_ARG, StatusType.USER, LocationType.SERVER) {
    override fun getName(): String {
        return "count_by_hair_color"
    }

    override fun getDescription(): String {
        return "${Messages.cyanText} --hairColor ${Messages.resetColor}- выводит количество элементов, значение поля hairColor которых равно заданному\n"
    }

    override fun execute(data: Data): Data {
        var result: String? = ""
        try {
            val color = Color.valueOf(data.oneArg.uppercase())
            val copyVector = collectionManager.collection
            var counter = 0
            for (element in copyVector) {
                if (element.hairColor == color) {
                    counter += 1
                }
            }
            result = ("Количество людей с цветом волос \"${color.toString().capitalize()}\": $counter")
        } catch (e: IllegalArgumentException) {
            result = (message.getMessage("IllegalColor"))
            data.answerStr = result
            return data
        }
        data.answerStr = result
        return data
    }
}
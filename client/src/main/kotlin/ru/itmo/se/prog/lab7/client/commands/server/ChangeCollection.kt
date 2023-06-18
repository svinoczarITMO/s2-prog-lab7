package ru.itmo.se.prog.lab7.client.commands.server

import ru.itmo.se.prog.lab7.client.commands.Command
import ru.itmo.se.prog.lab7.common.data.Data
import ru.itmo.se.prog.lab7.common.data.Messages
import ru.itmo.se.prog.lab7.common.data.types.ArgType
import ru.itmo.se.prog.lab7.common.data.types.LocationType
import ru.itmo.se.prog.lab7.common.data.types.StatusType


/**
 * Changes the collection type.
 *
 * @author svinoczar
 * @since 1.0.0
 */
class ChangeCollection: Command(ArgType.ONE_ARG, StatusType.USER, LocationType.SERVER) {
    override fun getName(): String {
        return "change_collection"
    }

    override fun getDescription(): String {
        return " --type_of_collection - изменяет тип коллекции.)\n"
    }

    override fun execute(data: Data): String? {
        val type = data.oneArg
        var result: String? = ""
        try {
            collectionManager.changeType(type.toString())
        } catch (e: NullPointerException) {
            result = (message.getMessage("NoSuchType"))
            result += printSupportedTypes()
            return result
        }
        result = message.getMessage("type_changed") + collectionManager.getType()
        return result
    }

    /**
     * Outputs the supported collection types.
     *
     * @author svinoczar
     * @since 1.0.0
     */
    private fun printSupportedTypes() : String? {
        var output = message.getMessage("supported_types")
        for (type in collectionManager.getSupportedCollectionTypes()) {
            output += type.key + "\n"
        }
        return output
    }
}
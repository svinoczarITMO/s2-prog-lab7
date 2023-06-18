//package ru.itmo.se.prog.lab7.client.commands.server
//
//
//import org.jetbrains.kotlin.konan.file.File
//import ru.itmo.se.prog.lab7.client.commands.Command
//import ru.itmo.se.prog.lab7.common.data.Data
//import ru.itmo.se.prog.lab7.common.data.Messages
//import ru.itmo.se.prog.lab7.common.data.types.ArgType
//import ru.itmo.se.prog.lab7.common.data.types.LocationType
//import ru.itmo.se.prog.lab7.common.data.types.StatusType
//
//
///**
// * Executes the script according to the file path entered.
// *
// * @author svinoczar
// * @since 1.0.0
// */
//class ExecuteScript: Command(ArgType.ONE_ARG, StatusType.USER, LocationType.SERVER) {
//    private var scriptFile = File("")
//
//    override fun getName(): String {
//        return "execute_script"
//    }
//
//    override fun getDescription(): String {
//        return " ${Messages.cyanText}--file_name${Messages.resetColor} - считывает и исполняет скрипт из указанного файла\n"
//    }
//
//    override fun execute(data: Data): String? {
//        var result: String? = ""
//        // глубина рекурсии
//        val maxDepth = 8
//        val depth = 0
//        var actDepth = depth
//        val flag = ::execute.name
//        val path = data.oneArg
//        var arguments: ArrayList<Any?> = arrayListOf()
//        if (File(path).exists) {
//            scriptFile = File(path)
////            validator.explorer(path)
//        } else {
//            result = message.getMessage("NoSuchFile")
//            return result
//        }
//        try {
//            if (actDepth <= maxDepth) {
//                val strings = scriptFile.readStrings()
//                write.linesInConsole(message.getMessage("script_start"))
//                for (string in strings) {
//                    val newArgs = string.split(" ").toMutableList()
//                    val commandName = newArgs[0]
//                    if (commandName == "execute_script") {
//                        actDepth += 1
//                        newArgs.add(flag)
//                        newArgs.add(path)
//                        newArgs.add(actDepth.toString())
////                        validator.validate(newArgs.toTypedArray())
//                    } else {
//                        newArgs.add(flag)
//                        newArgs.add(path)
////                        validator.validate(newArgs.toTypedArray())
//                    }
//                }
//            } else {
//                write.linesInConsole(message.getMessage("recurision"))
//            }
//        } catch (e: NoSuchFileException) {
//            result = (message.getMessage("NoSuchFile"))
//            return result
//        }
//        actDepth -= 1
//        if (actDepth == 0) {
//            result = (message.getMessage("script_end"))
//        }
//        return result
//    }
//}
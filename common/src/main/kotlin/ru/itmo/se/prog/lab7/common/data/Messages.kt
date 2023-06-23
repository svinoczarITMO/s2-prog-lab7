package ru.itmo.se.prog.lab7.common.data

/**
 * Contains messages for output.
 *
 * @author svinoczar
 * @since 1.0.0
 */
class Messages {

    companion object {
        const val redText = "\u001B[31m"
        const val cyanText = "\u001B[1;36m"
        const val resetColor = "\u001B[0m"
    }

    private val messages = mapOf(
        //reading data
        "enter_name" to "Введите имя: ",
        "enter_coordinateX" to "Введите координату X места рождения: ",
        "enter_coordinateY" to "Введите координату Y места рождения: ",
        "enter_height" to "Введите рост: ",
        "enter_weight" to "Введите вес: ",
        "enter_hairColor" to "Выберите цвет волос из предложенных: ",
        "enter_nationality" to "Выберите страну рождения из предложенных: ",
        "enter_locationX" to "Введите координату X примерного текущего местоположения: ",
        "enter_locationY" to "Введите координату Y примерного текущего местоположения: ",
        "enter_locationZ" to "Введите координату Z примерного текущего местоположения: ",
        "enter_login" to "Введите имя пользователя (логин): ",
        "enter_password" to "Введите пароль: ",
        "repeat_password" to "Повторите пароль: ",

        //successes
        "added" to "Эллемент успешно добавлен.",
        "updated" to "Эллемент успешно обновлён.",
        "clear" to "Все элементы, принадлежащие вам, успешно удалены!",
        "clear_all" to "Все элементы успешно удалены! :D",
        "removed" to " успешно удалён!",
        "saved" to "Коллекция успешно сохранена.",
        "reordered" to "Коллекция успешно отсортирована в обратном порядке.",
        "type_changed" to "Тип коллекции успешно сменен на ",
        "successful_registration" to "Регистрация прошла успешно!",
        "successful_login" to "Авторизация прошла успешно! Добро пожаловать!",

        //warnings
        "recursion" to "Скрипт содержит рекурсию! Выполнение невозможно!",
        "not_enough_args" to "Не хватает параметров в скрипте для добавления новой команды!\n",
        "weird_command" to "Введена неверная команда. Используйте help для вывода списка команд.\n",
        "invalid_id" to "Объект с указанным id не найден.\n",
        "blank_string" to "Строка не может быть пустой! Введите имя ещё раз.",
        "invalid_login1" to "Такого логина не существует.",
        "invalid_login2" to "Введенное имя пользователя уже существует.\nПридумайте другое: ",
        "invalid_password" to "Пароль неверный.",
        "no_match_passwords" to "Пароли не совпадают.",
        "access_denied" to "Недостаточно прав для выполнения данной команды.",
        "attempts_are_over" to "Вы трижды ввели неверный пароль. Попробуйте войти еще раз (комманда login).",
        "wrong_login" to "Ошибка входа. Попробуйте ещё раз.",
        "wrong_registration" to "Ошибка регистрации. Попробуйте ещё раз.",
        "not_owner" to "Вы можете изменять только элементы коллекции, добавленные вами.",

        //info
        "script_start" to "Скрипт выполняется...",
        "script_end" to "Выполнение скрипта завершено...",
        "last_commands" to "Последние 7 комманд: ",
        "min_weight_id" to "Id объекта коллекции с минимальным весом: ",
        "weight" to "Вес: ",
        "first_element" to "Первый элемент коллекции ",
        "by_id" to "Элемент с id = ",
        "clean_collection" to "В коллекции не содержится элементов.",
        "supported_types" to "Вы можете использовать следующие типы коллекции:\n",
        "login_info" to "Для дальнейшей работы вам необходимо войти в свой аккаунт (комманда login)\n" +
                "или зарегистрироваться (команда reg)",
        "permission_upgrade" to "Для повышения прав данного аккаунта введите команду uta --key (где \"key\" - ключ, выдаваемый администратором).",
        "log_out" to "Выход из аккаунта...\n",
        "goodbye" to "До свидания!",
        "correct_key" to "Введенный ключ подходит. Права аккаунта повышены до администратора.",
        "wrong_key" to "Введенный ключ не подходит.",


        //extra
        "u=" to "=========================================================================================================================================\n",
        "d=" to "=========================================================================================================================================",
        "welcome" to "Добро пожаловать, ",

        //exceptions
        "NumberFormatException" to "Введенное значение должно быть числом! Попробуйте ещё раз.",
        "IndexOutOfRange" to "Введенное число выходит за диапозон допустимых значений! Попробуйте ещё раз.",
        "IllegalColor" to "Введенный цвет не соответствует ни одному из предложенных! Попробуйте ещё раз.",
        "IllegalCountry" to "Введенная страна не соответствует ни одной из предложенных! Попробуйте ещё раз.",
        "InvalidArgument" to "Введенно недопустимое значение аргумента.",
        "NoSuchFile" to "Указанный файл не найден.",
        "NoSuchType" to "Указанный тип коллекции не найден.",
        "InvalidCommand" to "Такой команды не существует. Напишите help для просмотра списка команд."
    )

    /**
     * Returns message from map of messages by inputed key.
     *
     * @param key key of message
     * @return message as String?
     */
    fun getMessage (key: String): String? {
        return messages[key]
    }
}
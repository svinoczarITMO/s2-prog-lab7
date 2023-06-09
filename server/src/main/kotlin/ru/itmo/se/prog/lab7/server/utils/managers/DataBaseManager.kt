package ru.itmo.se.prog.lab7.server.utils.managers

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.itmo.se.prog.lab7.common.data.*
import ru.itmo.se.prog.lab7.common.data.Person
import ru.itmo.se.prog.lab7.server.utils.io.PrinterManager
import java.io.File
import java.sql.*
import java.util.Date
import java.sql.Date as sqlDate


class DataBaseManager: KoinComponent {
    private val user = "postgres"
    private val password = File("D:\\ITMO\\2nd-semester\\prog-labs\\s2-prog-lab7\\server\\src\\main\\kotlin\\ru\\itmo\\se\\prog\\lab7\\server\\utils\\.psw").readText()
    private val url = "jdbc:postgresql://localhost:5433/prog-lab-7"
    private val collectionManager: CollectionManager by inject ()
    private val write: PrinterManager by inject()
    private val connectionBD = connect()
    val listOfUsers = mutableListOf<User>()

    // person table queries
    private val insertPersonQuery = connectionBD.prepareStatement(
        "insert into public.person " +
                "(id, name, coordinate_x , coordinate_y, creation_date, height, weight, hair_color, nationality, location_x, location_y, location_z, owner_id)" +
                "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);")
    var selectPersonQuery = connectionBD.prepareStatement("select * from person order by id;")
    private val deletePersonQuery = connectionBD.prepareStatement("delete from person where person.id = ?;")
    private val clearPersonQuery = connectionBD.prepareStatement("delete from person;")

    // users table queries
    private val insertUsersQuery = connectionBD.prepareStatement(
        "insert into public.users " +
        "(id, login, password, is_admin)" +
        "values (?, ?, ?, ?);")
    private val selectUsersQuery = connectionBD.prepareStatement("select * from users order by id")
    private val clearUsersQuery = connectionBD.prepareStatement("delete from users")
    private val updateTokenQuery = connectionBD.prepareStatement("update users set token = ? where id = ?;")
    val updateIsAdminQuery = connectionBD.prepareStatement("update users set is_admin = true where id = ?;")

    fun connect(): Connection {
        try {
            val connection = DriverManager.getConnection(url, user, password)
            return connection
        } catch (e: SQLException) {
            throw e
        }
    }

    fun insertPerson(person: Person, ownerId: Int) {
        insertPerson(person.id, person.name, person.coordinates.x, person.coordinates.y, person.creationDate, person.height, person.weight, person.hairColor,
            person.nationality, person.location.x, person.location.y!!, person.location.z, ownerId)
    }

    private fun insertPerson (id: Int, name: String, coordinateX: Float, coordinateY: Float, creationDate: Date,
                              height: Int, weight: Long, hairColor: Color, nationality: Country, locationX: Int,
                              locationY: Long, locationZ: Int, ownerId: Int) {
        connect()
        try {
            val sqlDate = sqlDate(creationDate.time)
            val sqlHairColor = hairColor.toString().lowercase()
            val sqlNationality = nationality.toString().lowercase()
            insertPersonQuery.setInt(1, id)
            insertPersonQuery.setString(2, name)
            insertPersonQuery.setFloat(3, coordinateX)
            insertPersonQuery.setFloat(4, coordinateY)
            insertPersonQuery.setDate(5, sqlDate)
            insertPersonQuery.setInt(6, height)
            insertPersonQuery.setLong(7, weight)
            insertPersonQuery.setString(8, sqlHairColor)
            insertPersonQuery.setString(9, sqlNationality)
            insertPersonQuery.setInt(10, locationX)
            insertPersonQuery.setLong(11, locationY)
            insertPersonQuery.setInt(12, locationZ)
            insertPersonQuery.setInt(13, ownerId)

            val result = insertPersonQuery.executeUpdate()
            if (result == 0) {
                throw SQLException()
            }
        } catch (e: SQLException) {
            connect().close()
            write.linesInConsole(e.message)
            write.linesInConsole("Wrong insert-person query")
        }
        connect().close()
    }

    fun deletePerson (id: Int) {
        connect()
        try {
            deletePersonQuery.setInt(1, id)
            deletePersonQuery.executeUpdate()
        } catch (e: SQLException) {
            write.linesInConsole(e.message)
            write.linesInConsole("Wrong delete-person query")
            connect().close()
        }
        connect().close()
    }

    fun clearPerson () {
        connect()
        try {
            clearPersonQuery.executeUpdate()
        } catch (e: SQLException) {
            write.linesInConsole(e.message)
            write.linesInConsole("Wrong clear-person query")
            connect().close()
        }
        connect().close()
    }

    fun updatePerson (id: Int, name: String, coordinateX: Float, coordinateY: Float, creationDate: Date,
                      height: Int, weight: Long, hairColor: Color, nationality: Country, locationX: Int,
                      locationY: Long, locationZ: Int, ownerId: Int) {
        connect()
        deletePerson(id)
        insertPerson(id, name, coordinateX, coordinateY, creationDate, height, weight, hairColor, nationality, locationX, locationY, locationZ, ownerId)
        connect().close()
    }

    fun uploadAllPersons () {
        connect()
        try {
            val persons = selectPersonQuery.executeQuery()
            while (persons.next()) {
                val coordinates = Coordinates(persons.getFloat("coordinate_x"), persons.getFloat("coordinate_y"))
                val location = Location(persons.getInt("location_x"), persons.getLong("location_y"), persons.getInt("location_z"))

                val personToAdd = Person(
                    persons.getInt("id"),
                    persons.getString("name"),
                    coordinates,
                    persons.getDate("creation_date"),
                    persons.getInt("height"),
                    persons.getLong("weight"),
                    Color.valueOf(persons.getString("hair_color").uppercase()),
                    Country.valueOf(persons.getString("nationality").uppercase()),
                    location,
                    persons.getInt("owner_id")
                )
                collectionManager.collection.add(personToAdd)
            }

        } catch (e: SQLException) {
            write.linesInConsole(e.message)
            write.linesInConsole("Wrong upload-all-persons query")
            connect().close()
        }
        connect().close()
    }

    fun selectOwnerId (id: Int): Int {
        val person = selectPersonQuery.executeQuery()
        while (person.next()) {
            if (id == person.getInt("id")) {
                return person.getInt("owner_id")
            }
        }
        return person.getInt("owner_id")
    }

    fun insertUsers (id: Int, login: String, password: String, isAdmin: Boolean) {
        connect()
        try {
            insertUsersQuery.setInt(1, id)
            insertUsersQuery.setString(2, login)
            insertUsersQuery.setString(3, password)
            insertUsersQuery.setBoolean(4, isAdmin)

            val result = insertUsersQuery.executeUpdate()
            if (result == 0) {
                throw SQLException()
            }
        } catch (e: SQLException) {
            write.linesInConsole(e.message)
            write.linesInConsole("Wrong insert-users query")
            connect().close()
        }
        connect().close()
    }

    fun clearUsers () {
        connect()
        try {
            clearUsersQuery.executeUpdate()
        } catch (e: SQLException) {
            write.linesInConsole(e.message)
            write.linesInConsole("Wrong clear-users query")
            connect().close()
        }
        connect().close()
    }

    fun uploadAllUsers () {
        connect()
        try {
            val users = selectUsersQuery.executeQuery()
            while (users.next()) {
                val userToAdd = User(
                    users.getInt("id"),
                    users.getString("login"),
                    users.getString("password"),
                    users.getBoolean("is_admin")
                )
                listOfUsers.add(userToAdd)
            }

        } catch (e: SQLException) {
            write.linesInConsole(e.message)
            write.linesInConsole("Wrong upload-all-users query")
            connect().close()
        }
        connect().close()
    }

    fun updateToken (id: Int, token: String) {
        connect()
        try {
            updateTokenQuery.setString(1, token)
            updateTokenQuery.setInt(2, id)

            val result = updateTokenQuery.executeUpdate()
            if (result == 0) {
                throw SQLException()
            }
        } catch (e: SQLException) {
            write.linesInConsole(e.message)
            write.linesInConsole("Wrong update-token query")
            connect().close()
        }
        connect().close()
    }

}
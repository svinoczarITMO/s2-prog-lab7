package ru.itmo.se.prog.lab7.common.data

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import ru.itmo.se.prog.lab7.common.DateAsStringSerializer
import java.util.*

/**
 * Model of Person. Main model of the program.
 *
 * @constructor id: Int, name: String, coordinates: Coordinates,
 * creationDate: Date, height: Int, weight: Long,
 * hairColor: Color, nationality: Country, location: Location
 * @author svinoczar
 * @since 1.0.0
 */

@Serializable
data class Person(
    var id: Int,
    var name: String,
    var coordinates: Coordinates,
    @Contextual
    @Serializable(DateAsStringSerializer::class)
    var creationDate: Date,
    var height: Int,
    var weight: Long,
    var hairColor: Color,
    var nationality: Country,
    var location: Location,
    var ownerId: Int
)
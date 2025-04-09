package org.mathieu.cleanrmapi.data.local.objects

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.mathieu.cleanrmapi.data.local.RMDatabase
import org.mathieu.cleanrmapi.data.remote.responses.LocationResponse
import org.mathieu.cleanrmapi.data.validators.annotations.MustBeCommaSeparatedIds
import org.mathieu.cleanrmapi.data.extensions.extractIdsFromUrls
import org.mathieu.cleanrmapi.domain.location.models.Location
import org.mathieu.cleanrmapi.domain.character.models.Character

/**
 * SQLite entity representing a location cached locally.
 *
 * @property id The unique identifier of the location.
 * @property name The name of the location.
 * @property type The category or nature of the location.
 * @property dimension The dimension in which this location exists.
 * @property residentsIds Comma-separated list of character IDs living in this location.
 */
@Entity(tableName = RMDatabase.LOCATION_TABLE)
class LocationObject(
    @PrimaryKey
    val id: Int,
    val name: String,
    val type: String,
    val dimension: String,
    @MustBeCommaSeparatedIds
    val residentsIds: String
)

internal fun LocationResponse.toDBObject(): LocationObject =
LocationObject(
    id = id,
    name = name,
    type = type,
    dimension = dimension,
    residentsIds = residents.extractIdsFromUrls()
)


internal fun LocationObject.toModel(residents: List<Character>) = Location(
    id = id,
    name = name,
    type = type,
    dimension = dimension,
    residents = residents
)
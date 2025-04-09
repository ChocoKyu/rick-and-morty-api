package org.mathieu.cleanrmapi.data.repositories

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.mathieu.cleanrmapi.data.local.LocationDAO
import org.mathieu.cleanrmapi.data.local.CharacterDAO
import org.mathieu.cleanrmapi.data.local.objects.LocationObject
import org.mathieu.cleanrmapi.data.local.objects.toModel
import org.mathieu.cleanrmapi.data.remote.LocationApi
import org.mathieu.cleanrmapi.domain.location.LocationRepository
import org.mathieu.cleanrmapi.domain.location.models.Location
import org.mathieu.cleanrmapi.data.local.objects.toDBObject


internal class LocationRepositoryImpl : LocationRepository, KoinComponent {

    private val locationApi: LocationApi by inject()
    private val locationDao: LocationDAO by inject()
    private val characterDao: CharacterDAO by inject()

    override suspend fun getLocation(id: Int): Location {
        val local = GetLocationObjectIfExists(id)

        val residents = local.residentsIds
            .split(",")
            .mapNotNull { it.toIntOrNull() }
            .mapNotNull { characterDao.getCharacter(it)?.toModel() }

        return local.toModel(residents)
    }

}

private object GetLocationObjectIfExists : KoinComponent {

    private val locationApi: LocationApi by inject()
    private val locationDao: LocationDAO by inject()

    suspend operator fun invoke(id: Int) =
        tryToGetLocationLocally(id)
            .fetchRemotelyIfNotFound(id)
            .throwIfWeCannotFindIt()

    private suspend fun tryToGetLocationLocally(id: Int) =
        locationDao.getById(id)

    private suspend fun LocationObject?.fetchRemotelyIfNotFound(id: Int): LocationObject? {
        if (this != null) return this

        return locationApi.getLocation(id)
            ?.toDBObject()
            ?.also { locationDao.insert(it) }
    }

    private fun LocationObject?.throwIfWeCannotFindIt(): LocationObject {
        if (this != null) return this
        throw Exception("Could not find Location locally or remotely.")
    }
}

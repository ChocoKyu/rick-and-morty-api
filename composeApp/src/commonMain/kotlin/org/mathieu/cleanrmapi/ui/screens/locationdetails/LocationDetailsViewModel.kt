package org.mathieu.cleanrmapi.ui.screens.locationdetails

import org.koin.core.component.inject
import org.mathieu.cleanrmapi.domain.location.LocationRepository
import org.mathieu.cleanrmapi.ui.core.Destination
import org.mathieu.cleanrmapi.ui.core.ViewModel
import org.mathieu.cleanrmapi.ui.screens.characterdetails.CharacterDetailsAction

class LocationDetailsViewModel : ViewModel<LocationDetailsState>(LocationDetailsState.Loading) {

    private val repository: LocationRepository by inject()

    fun init(locationId: Int) {
        fetchData(
            source = { repository.getLocation(locationId) }
        ) {
            onSuccess { location ->
                updateState {
                    LocationDetailsState.Loaded(
                        location = location,
                        residents = location.residents
                    )
                }
            }

            onFailure {
                updateState {
                    LocationDetailsState.Error(it.message ?: "Unknown error")
                }
            }
        }
    }

    fun handleAction(action: LocationDetailsAction) {
        when(action) {
            is LocationDetailsAction.SelectedCharacter ->
                sendEvent(Destination.CharacterDetails(action.characterId.toString()))
        }
    }
}

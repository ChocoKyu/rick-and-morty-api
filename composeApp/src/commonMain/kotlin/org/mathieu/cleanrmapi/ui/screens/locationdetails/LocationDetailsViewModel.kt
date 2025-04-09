package org.mathieu.cleanrmapi.ui.screens.locationdetails

import org.koin.core.component.inject
import org.mathieu.cleanrmapi.domain.location.LocationRepository
import org.mathieu.cleanrmapi.ui.core.Destination
import org.mathieu.cleanrmapi.ui.core.ViewModel
import org.mathieu.cleanrmapi.ui.screens.characterdetails.CharacterDetailsAction

class LocationDetailsViewModel : ViewModel<LocationDetailsState>(LocationDetailsState.Loading) {

    // On récupère le repository via Koin pour accéder aux données de localisation
    private val repository: LocationRepository by inject()

    // Appelé au chargement de l'écran pour récupérer les données
    fun init(locationId: Int) {
        fetchData(
            source = { repository.getLocation(locationId) }
        ) {
            onSuccess { location ->
                // Si c'est bon, on met à jour l'état avec les infos reçues
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
                // Redirige vers la page de détails du personnage
                sendEvent(Destination.CharacterDetails(action.characterId.toString()))
        }
    }
}

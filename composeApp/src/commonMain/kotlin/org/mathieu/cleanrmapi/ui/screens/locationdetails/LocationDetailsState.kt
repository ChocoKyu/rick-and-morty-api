package org.mathieu.cleanrmapi.ui.screens.locationdetails

import org.mathieu.cleanrmapi.domain.character.models.Character
import org.mathieu.cleanrmapi.domain.location.models.Location

sealed interface LocationDetailsState {
    object Loading : LocationDetailsState
    data class Error(val message: String) : LocationDetailsState
    data class Loaded(
        val location: Location,
        val residents: List<Character>
    ) : LocationDetailsState
}

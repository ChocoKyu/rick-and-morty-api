package org.mathieu.cleanrmapi.ui.screens.characterdetails

import org.mathieu.cleanrmapi.domain.character.models.CharacterGender
import org.mathieu.cleanrmapi.domain.character.models.CharacterStatus
import org.mathieu.cleanrmapi.domain.episode.models.Episode
import org.mathieu.cleanrmapi.domain.location.models.LocationPreview


sealed interface CharacterDetailsState {
    object Loading : CharacterDetailsState

    data class Error(val message: String) : CharacterDetailsState

    data class Loaded(
        val name: String,
        val avatarUrl: String,
        val episodes: List<Episode>,
        val status: CharacterStatus,
        val gender: CharacterGender,
        val origin: LocationPreview,
        val location: LocationPreview,
    ) : CharacterDetailsState

}

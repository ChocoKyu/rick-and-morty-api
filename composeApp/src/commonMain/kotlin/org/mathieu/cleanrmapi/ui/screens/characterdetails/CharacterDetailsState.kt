package org.mathieu.cleanrmapi.ui.screens.characterdetails

import org.mathieu.cleanrmapi.domain.character.models.CharacterGender
import org.mathieu.cleanrmapi.domain.character.models.CharacterStatus
import org.mathieu.cleanrmapi.domain.episode.models.Episode


sealed interface `CharacterDetailsState.kt` {
    object Loading : `CharacterDetailsState.kt`

    data class Error(val message: String) : `CharacterDetailsState.kt`

    data class Loaded(
        val name: String,
        val avatarUrl: String,
        val episodes: List<Episode>,
        val status: CharacterStatus,
        val gender: CharacterGender,
        val origin: String,
        val location: String,
    ) : `CharacterDetailsState.kt`

}
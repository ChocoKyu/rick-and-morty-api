package org.mathieu.cleanrmapi.ui.screens.characterdetails

import org.mathieu.cleanrmapi.domain.episode.models.Episode

sealed interface CharacterDetailsAction {
    data class SelectedEpisode(val episode: Episode): CharacterDetailsAction
    data class SelectedLocation(val locationId: Int): CharacterDetailsAction
}
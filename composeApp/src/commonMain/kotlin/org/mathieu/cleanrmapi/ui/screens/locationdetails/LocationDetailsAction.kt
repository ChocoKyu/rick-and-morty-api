package org.mathieu.cleanrmapi.ui.screens.locationdetails

sealed interface LocationDetailsAction {
    data class SelectedCharacter(val characterId: Int): LocationDetailsAction
}
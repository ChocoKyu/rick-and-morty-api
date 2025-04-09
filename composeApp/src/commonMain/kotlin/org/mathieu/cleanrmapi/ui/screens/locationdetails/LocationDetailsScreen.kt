package org.mathieu.cleanrmapi.ui.screens.locationdetails

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.mathieu.cleanrmapi.ui.core.composables.BackArrow
import org.mathieu.cleanrmapi.ui.core.composables.CharacterCard
import org.mathieu.cleanrmapi.ui.core.composables.ErrorView
import org.mathieu.cleanrmapi.ui.core.composables.LoadingView
import org.mathieu.cleanrmapi.ui.core.composables.PreviewContent
import org.mathieu.cleanrmapi.ui.core.composables.Screen
import org.mathieu.cleanrmapi.ui.core.theme.PrimaryColor
import org.mathieu.cleanrmapi.ui.core.theme.SurfaceColor

@Composable
fun LocationDetailsScreen(
    navController: NavController,
    id: Int
) {
    Screen(
        viewModel = viewModel { LocationDetailsViewModel() },
        navController = navController
    ) { state, viewModel ->

        LaunchedEffect(Unit) { viewModel.init(id) }

        Content(
            state = state,
            onClickBack = navController::popBackStack,
            onAction = viewModel::handleAction
        )
    }
}

@Composable
private fun Content(
    state: LocationDetailsState,
    onAction: (LocationDetailsAction) -> Unit = { },
    onClickBack: () -> Unit = {},
) = Box(
    modifier = Modifier
        .fillMaxSize()
        .padding(),
    contentAlignment = Alignment.Center
) {
    BackArrow(
        modifier = Modifier
            .align(Alignment.TopStart)
            .zIndex(1f),
        onClick = onClickBack
    )

    Crossfade(targetState = state, label = "") {
        when (it) {
            is LocationDetailsState.Error -> ErrorView(it.message)
            is LocationDetailsState.Loading -> LoadingView()
            is LocationDetailsState.Loaded -> LocationDetailsContent(it, onAction = onAction)

        }
    }
}

private object LocationDetailsContent {

    @Composable
    operator fun invoke(
        state: LocationDetailsState.Loaded,
        onAction: (LocationDetailsAction) -> Unit
    ) {

        var offsetY by remember { mutableFloatStateOf(0f) }

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Header(
                name = state.location.name,
                type = state.location.type,
                dimension = state.location.dimension,
                offsetY = offsetY
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                itemsIndexed(state.residents) { index, character ->
                    if (index == 0) {
                        Box(modifier = Modifier.onGloballyPositioned {
                            offsetY = it.positionInParent().y
                        })
                    }

                    CharacterCard(
                        modifier = Modifier
                            .padding(12.dp)
                            .shadow(1.dp, spotColor = PrimaryColor)
                            .background(SurfaceColor)
                            .fillMaxWidth()
                            .clickable {
                                onAction(LocationDetailsAction.SelectedCharacter(character.id))
                            },
                        character = character
                    )
                }
            }
        }
    }

    @Composable
    private fun Header(
        name: String,
        type: String,
        dimension: String,
        offsetY: Float
    ) {
        val density = LocalDensity.current
        val additionalHeight: Dp = with(density) { offsetY.toDp() }
        val animatedHeight by animateDpAsState(targetValue = 200.dp + additionalHeight)

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(animatedHeight)
        ) {
            AsyncImage(
                model = "https://images.wallpapersden.com/image/download/rick-and-morty_am5mZmeUmZqaraWkpJRobWllrWdma2U.jpg",
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.matchParentSize()
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(SurfaceColor.copy(alpha = 0.3f))
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ) {
                Box(
                    modifier = Modifier
                        .background(SurfaceColor, RoundedCornerShape(6.dp))
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = name,
                            fontSize = 21.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Serif
                        )
                        Text(text = "Type : $type", fontSize = 14.sp)
                        Text(text = "Dimension : $dimension", fontSize = 14.sp)
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun LocationDetailsPreview() = PreviewContent {
    Content(state = LocationDetailsState.Loading)
}

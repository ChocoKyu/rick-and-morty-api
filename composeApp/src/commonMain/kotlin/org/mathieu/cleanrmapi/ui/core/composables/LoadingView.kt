package org.mathieu.cleanrmapi.ui.core.composables

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LoadingView() {
    Text(
        modifier = Modifier.padding(16.dp),
        text = "Chargement...",
        fontSize = 20.sp
    )
}

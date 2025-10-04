
package com.fyyadi.scan.ui

import android.util.Log
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.fyyadi.domain.model.PlantLabel

@Composable
fun ResultScanScreen(
    modifier: Modifier = Modifier,
    plantLabels: List<PlantLabel>,
    onBackClick: () -> Unit
) {
    LaunchedEffect(Unit) {
        Log.e("ResultScanScreen", "plantLabels: $plantLabels")
    }
}

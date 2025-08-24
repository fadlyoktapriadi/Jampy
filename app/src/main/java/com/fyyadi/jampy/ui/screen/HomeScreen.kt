package com.fyyadi.jampy.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fyyadi.core.domain.model.Plant
import com.fyyadi.jampy.ui.theme.*
import com.fyyadi.jampy.R
import com.fyyadi.jampy.ui.components.PlantCard
import com.fyyadi.jampy.ui.components.WeatherCard
import com.fyyadi.jampy.utils.plantHomeDummy

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier
) {
    Scaffold { paddingValues ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(BackgroundGreen)
                .padding(paddingValues)
        ) {
            TopSection()
            BottomContentSheet(modifier = Modifier.align(Alignment.BottomCenter))
        }
    }
}

@Composable
fun TopSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(380.dp)
            .background(BackgroundGreen)
    ) {
        Image(
            painter = painterResource(id = R.drawable.jampy),
            contentDescription = "Jampy Logo",
            modifier = Modifier
                .padding(start = 24.dp, top = 24.dp)
                .size(48.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))

        WeatherCard()
        Spacer(modifier = Modifier.weight(1f))

        Image(
            painter = painterResource(id = R.drawable.illuplant_home),
            contentDescription = "Plant Illustration",
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp),
            contentScale = ContentScale.FillWidth
        )
    }
}


@Composable
fun BottomContentSheet(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(0.49f),
        color = Color.White,
        shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 16.dp)
        ) {
            Box(
                modifier = Modifier
                    .width(40.dp)
                    .height(4.dp)
                    .clip(CircleShape)
                    .background(Color.LightGray)
                    .align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(24.dp))
            PopularPlantsSection()
        }
    }
}

@Composable
fun PopularPlantsSection() {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(R.string.plant_populer),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = PrimaryGreen
            )
            Text(
                text = stringResource(R.string.see_all),
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = OrangePrimary
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        LazyRow(
            contentPadding = PaddingValues(horizontal = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(plantHomeDummy) { plant ->
                PlantCard(plant)
            }
        }
    }
}


@Preview(showBackground = true, device = "id:pixel_5")
@Composable
fun HomeScreenPreview() {
    JampyTheme {
        HomeScreen()
    }
}
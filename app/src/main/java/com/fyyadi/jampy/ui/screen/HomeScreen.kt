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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fyyadi.jampy.ui.theme.*
import com.fyyadi.jampy.R

// Data class untuk tanaman populer
data class Plant(
    val name: String,
    val species: String,
    val description: String,
    val imageRes: Int
)

// Data dummy
val popularPlants = listOf(
    Plant("Lidah Buaya", "Aloe Vera", "Soothing, Healing, Hydrating", R.drawable.lidahbuaya),
    Plant("Daun Sirih", "Piper betle", "Antiseptic, Analgesic, Astringent", R.drawable.sirih),
    Plant(
        "Jahe",
        "Zingiber",
        "Warming, Anti-inflammatory",
        R.drawable.lidahbuaya
    ) // Ganti dengan gambar jahe
)

@Composable
fun HomeScreen() {
    // Scaffold menyediakan struktur dasar Material Design
    Scaffold(
        bottomBar = { BottomNavigationBar() }
    ) { paddingValues ->
        // Box digunakan untuk menumpuk elemen
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(paddingValues)
        ) {
            // Bagian atas dengan latar belakang hijau
            TopSection()

            // Bagian bawah (sheet putih) yang menumpuk di atas
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
            .background(bgGreen)
    ) {
        // Header dengan logo
        Image(
            painter = painterResource(id = R.drawable.jampy),
            contentDescription = "Jampy Logo",
            modifier = Modifier
                .padding(start = 24.dp, top = 24.dp)
                .size(48.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Kartu cuaca dan sapaan
        WeatherCard()
        Spacer(modifier = Modifier.weight(1f))

        // Ilustrasi tanaman
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
fun WeatherCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = cardWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Kolom cuaca
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Outlined.LocationOn,
                        contentDescription = "Location",
                        tint = OrangePrimary,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "Pandeglang", fontSize = 12.sp, color = Color.Black)
                }
                Text(
                    text = "28°C",
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Bold,
                    color = OrangePrimary,
                    lineHeight = 50.sp
                )
            }
            // Kolom sapaan
            Column(horizontalAlignment = Alignment.End) {
                Text(text = "Hai,", fontSize = 16.sp, color = Color.Black)
                Text(
                    text = "Fadly Oktapriadi",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = primaryGreen
                )
            }
        }
    }
}

@Composable
fun BottomContentSheet(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(0.65f), // Mengisi 65% bagian bawah layar
        color = Color.White,
        shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 16.dp)
        ) {
            // Handle (garis abu-abu di atas)
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
        // Header "Plant Populer"
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Plant Populer",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = primaryGreen
            )
            Text(
                text = "See All",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = OrangePrimary
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Daftar tanaman horizontal
        LazyRow(
            contentPadding = PaddingValues(horizontal = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(popularPlants) { plant ->
                PlantCard(plant)
            }
        }
    }
}

@Composable
fun PlantCard(plant: Plant) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .width(160.dp)
                .padding(12.dp)
        ) {
            Image(
                painter = painterResource(id = plant.imageRes),
                contentDescription = plant.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = plant.name, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.Black)
            Text(text = plant.species, fontSize = 12.sp, color = Color.Black)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = plant.description,
                fontSize = 12.sp,
                color = primaryGreen,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun BottomNavigationBar() {
    var selectedIndex by remember { mutableStateOf(0) }
    val items = listOf(
        BottomNavItem("Home", R.drawable.home_filled, R.drawable.home_gray),
        BottomNavItem("Search", R.drawable.search_filled, R.drawable.search_grey),
        BottomNavItem("Scan", R.drawable.scan_orange, R.drawable.scan_grey),
        BottomNavItem("Bookmark", R.drawable.bookmark_filled, R.drawable.bookmark_grey),
        BottomNavItem("Profile", R.drawable.profile_filled, R.drawable.profile_grey)
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 8.dp),
        shape = RoundedCornerShape(28.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
        colors = CardDefaults.cardColors(containerColor = cardWhite)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            items.forEachIndexed { index, item ->
                val isSelected = selectedIndex == index
                val isScanButton = index == 2

                IconButton(onClick = { selectedIndex = index }) {
                    Box(
                        modifier = if (isScanButton) Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(OrangePrimary)
                        else Modifier.size(28.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(id = if (isSelected) item.selectedIcon else item.unselectedIcon),
                            contentDescription = item.title,
                            modifier = Modifier.size(if (isScanButton) 28.dp else 24.dp),
                            tint = when {
                                isScanButton -> Color.White
                                isSelected -> primaryGreen
                                else -> OrangePrimary
                            }
                        )
                    }
                }
            }
        }
    }
}

data class BottomNavItem(
    val title: String,
    val selectedIcon: Int,
    val unselectedIcon: Int
)

@Preview(showBackground = true, device = "id:pixel_5")
@Composable
fun HomeScreenPreview() {
    JampyTheme {
        HomeScreen()
    }
}
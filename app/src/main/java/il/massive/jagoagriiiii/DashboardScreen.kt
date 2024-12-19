package il.massive.jagoagriiiii.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun DashboardScreen(navController: NavController) {
    Scaffold(
        bottomBar = { BottomNavigationBar(navController) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text("Selamat Datang!", fontSize = 20.sp, color = Color.Gray)
                    Text("Amin", fontSize = 28.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                }
                Icon(Icons.Default.AccountCircle, contentDescription = "Profile", modifier = Modifier.size(50.dp))
            }
            Spacer(modifier = Modifier.height(16.dp))

            // Cuaca Hari Ini
            Card(
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF007A49), contentColor = Color.White)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Kota Banyumas, 22 September 2024", fontSize = 14.sp, color = Color.White)
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column {
                            Text("24°C", fontSize = 36.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                            Text("Feels like: 23°C", fontSize = 14.sp)
                        }
                        Icon(Icons.Default.WbSunny, contentDescription = "Cloudy", modifier = Modifier.size(48.dp))
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Cloudy | Wind: 10 km/h | Hum: 54%", fontSize = 14.sp)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Menu Utama
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                MenuItem("Kalender", Icons.Default.CalendarToday) {
                    navController.navigate("calendar")
                }
                MenuItem("JagoEdu", Icons.Default.School) {
                    navController.navigate("education")
                }
                MenuItem("Diagnosis", Icons.Default.MedicalServices) {
                    navController.navigate("diagnosis")
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Informasi tentang Tanah
            Text("Informasi tentang tanah", fontSize = 18.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
            Card(
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF007A49), contentColor = Color.White)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Soil Moisture Content", fontSize = 20.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Current Moisture: 30%", fontSize = 16.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("The soil moisture content is adequate. Ensure regular irrigation to maintain this level.", fontSize = 14.sp)
                }
            }
        }
    }
}

@Composable
fun MenuItem(label: String, icon: androidx.compose.ui.graphics.vector.ImageVector, onClick: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        IconButton(onClick = onClick) {
            Icon(icon, contentDescription = label, tint = Color(0xFF2E7D32), modifier = Modifier.size(48.dp))
        }
        Text(label, fontSize = 14.sp, textAlign = TextAlign.Center)
    }
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    NavigationBar(containerColor = Color.White) {
        NavigationBarItem(
            icon = { Icon(Icons.Default.Forum, contentDescription = "Forum") },
            label = { Text("Forum") },
            selected = false,
            onClick = { navController.navigate("forum") }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Cloud, contentDescription = "Cuaca") },
            label = { Text("Cuaca") },
            selected = false,
            onClick = { navController.navigate("weather") }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Beranda") },
            label = { Text("Beranda") },
            selected = true,
            onClick = { /* Stay here */ }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Settings, contentDescription = "Pengaturan") },
            label = { Text("Pengaturan") },
            selected = false,
            onClick = { navController.navigate("settings") }
        )
    }
}

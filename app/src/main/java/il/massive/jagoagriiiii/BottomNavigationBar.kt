package il.massive.jagoagriiiii.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import il.massive.jagoagriiiii.R

@Composable
fun BottomNavigationBar(navController: NavController, currentRoute: String) {
    NavigationBar(containerColor = Color.White) {
        NavigationBarItem(
            icon = { Icon(painterResource(id = R.drawable.jagoagri_logo), contentDescription = "Forum") },
            label = { Text("Forum") },
            selected = currentRoute == "forum",
            onClick = { navController.navigate("forum") }
        )
        NavigationBarItem(
            icon = { Icon(painterResource(id = R.drawable.jagoagri_logo), contentDescription = "Cuaca") },
            label = { Text("Cuaca") },
            selected = currentRoute == "weather",
            onClick = { navController.navigate("weather") }
        )
        NavigationBarItem(
            icon = { Icon(painterResource(id = R.drawable.jagoagri_logo), contentDescription = "Beranda") },
            label = { Text("Beranda") },
            selected = currentRoute == "dashboard",
            onClick = { navController.navigate("dashboard") }
        )
        NavigationBarItem(
            icon = { Icon(painterResource(id = R.drawable.jagoagri_logo), contentDescription = "Dompet") },
            label = { Text("Dompet") },
            selected = currentRoute == "wallet",
            onClick = { navController.navigate("wallet") }
        )
        NavigationBarItem(
            icon = { Icon(painterResource(id = R.drawable.jagoagri_logo), contentDescription = "Pengaturan") },
            label = { Text("Pengaturan") },
            selected = currentRoute == "settings",
            onClick = { navController.navigate("settings") }
        )
    }
}

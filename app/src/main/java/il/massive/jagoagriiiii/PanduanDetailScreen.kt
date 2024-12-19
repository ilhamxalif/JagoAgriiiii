package il.massive.jagoagriiiii.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import androidx.navigation.NavBackStackEntry
import androidx.navigation.compose.rememberNavController
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import il.massive.jagoagriiiii.R
import il.massive.jagoagriiiii.components.BottomNavigationBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PanduanDetailScreen(navController: NavController, backStackEntry: NavBackStackEntry) {
    val topic = backStackEntry.arguments?.getString("topic") ?: "Topik Tidak Ditemukan"

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(topic, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Kembali")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Text(
                text = "Panduan: $topic",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Text(
                text = "Deskripsi detail mengenai $topic akan ditampilkan di sini. Anda dapat mengedit dan menambahkan informasi yang relevan untuk panduan ini.",
                style = MaterialTheme.typography.bodyLarge
            )

        }
    }
}

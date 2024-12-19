package il.massive.jagoagriiiii.screens

import android.os.Handler
import android.os.Looper
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import il.massive.jagoagriiiii.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.jagoagri_logo), // Pastikan gambar ada di res/drawable
                contentDescription = "Logo",
                modifier = Modifier.size(230.dp)
            )
        }
    }

    // Menambahkan delay untuk pindah ke layar berikutnya
    LaunchedEffect(Unit) {
        delay(2000)  // Splash screen tampil selama 2 detik
        navController.navigate("onboarding") // Pindah ke layar onboarding
    }
}

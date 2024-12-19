package il.massive.jagoagriiiii.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import il.massive.jagoagriiiii.R

@Composable
fun OnboardingScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp), // Padding untuk seluruh konten
        horizontalAlignment = Alignment.CenterHorizontally, // Align center secara horizontal
        verticalArrangement = Arrangement.Center // Vertikal di tengah
    ) {
        // Gambar Logo JagoAgri dengan ukuran sesuai gambar
        Image(
            painter = painterResource(id = R.drawable.jagoagri_logo), // Ganti dengan logo Anda
            contentDescription = "Logo",
            modifier = Modifier.size(230.dp) // Ukuran logo sesuai gambar
        )
        Spacer(modifier = Modifier.height(32.dp)) // Memberi jarak vertikal antara logo dan teks

        // Teks "Selamat Datang di JagoAgri!"
        Text(
            text = "Selamat Datang di JagoAgri!",
            style = MaterialTheme.typography.headlineLarge.copy(color = androidx.compose.ui.graphics.Color(0xFF007A49)),
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp)) // Memberi jarak vertikal

        // Teks deskripsi yang lebih kecil
        Text(
            text = "Solusi Digital untuk Petani Jagung Indonesia Tingkatkan Hasil Panen Anda Bersama JagoAgri",
            style = MaterialTheme.typography.bodyLarge.copy(color = androidx.compose.ui.graphics.Color(0xFF007A49)),
            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp) // Padding kiri kanan agar tidak terlalu rapat
        )
        Spacer(modifier = Modifier.height(32.dp)) // Memberi jarak vertikal antara teks dan tombol

        // Tombol Mulai
        Button(
            onClick = { navController.navigate("login") }, // Navigasi ke login setelah klik Mulai
            modifier = Modifier.fillMaxWidth() // Tombol mengisi lebar layar
        ) {
            Text("Mulai")
        }
    }
}

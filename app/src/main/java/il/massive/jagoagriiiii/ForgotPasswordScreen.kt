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
import kotlinx.coroutines.delay
import il.massive.jagoagriiiii.R

@Composable
fun ForgotPasswordScreen(navController: NavController) {
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordResetMessage by remember { mutableStateOf<String?>(null) } // Untuk menyimpan pesan
    var isMessageVisible by remember { mutableStateOf(false) } // Untuk mengatur visibilitas pesan

    // Handle the message display duration (4 seconds)
    LaunchedEffect(passwordResetMessage) {
        if (passwordResetMessage != null) {
            isMessageVisible = true
            delay(1500) // Delay selama 4 detik
            navController.navigate("login") // Navigasi ke login setelah delay
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Gambar Logo JagoAgri
        Image(
            painter = painterResource(id = R.drawable.jagoagri_logo), // Ganti dengan logo Anda
            contentDescription = "Logo",
            modifier = Modifier.size(150.dp)
        )
        Spacer(modifier = Modifier.height(32.dp))

        // Title for reset password
        Text(
            text = "Reset Password",
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.height(24.dp))

        // New Password Field
        OutlinedTextField(
            value = newPassword,
            onValueChange = { newPassword = it },
            label = { Text("New Password") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Confirm New Password Field
        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Confirm New Password") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(32.dp))

        // Confirm Button
        Button(
            onClick = {
                // Handle password reset logic
                if (newPassword == confirmPassword) {
                    passwordResetMessage = "Password berhasil diubah!"
                } else {
                    passwordResetMessage = "Password tidak cocok!"
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Confirm")
        }

        // Menampilkan pesan setelah password berhasil diubah atau ada kesalahan
        if (isMessageVisible) {
            Spacer(modifier = Modifier.height(32.dp))
            Text(text = passwordResetMessage ?: "", style = MaterialTheme.typography.bodyLarge)
        }
    }
}

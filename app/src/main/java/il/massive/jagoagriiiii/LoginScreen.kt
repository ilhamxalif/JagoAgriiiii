package il.massive.jagoagriiiii.screens

import android.widget.Toast
import android.os.Handler
import android.os.Looper
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

@Composable
fun LoginScreen(navController: NavController) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    // Fungsi untuk login
    fun login(username: String, password: String) {
        val client = OkHttpClient()
        val url = "http://10.0.2.2/jagoagri-api/login.php"

        val formBody = FormBody.Builder()
            .add("username", username)
            .add("password", password)
            .build()

        val request = Request.Builder()
            .url(url)
            .post(formBody)
            .build()

        isLoading = true

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                isLoading = false
                Handler(Looper.getMainLooper()).post {
                    Toast.makeText(navController.context, "Login failed: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                isLoading = false
                val responseData = response.body?.string()
                Handler(Looper.getMainLooper()).post {
                    try {
                        if (response.isSuccessful && responseData != null) {
                            val jsonResponse = JSONObject(responseData)
                            val status = jsonResponse.getString("status")
                            val message = jsonResponse.getString("message")

                            if (status == "success") {
                                Toast.makeText(navController.context, "Login Berhasil: $message", Toast.LENGTH_SHORT).show()
                                navController.navigate("dashboard") {
                                    popUpTo("login") { inclusive = true }
                                }
                            } else {
                                Toast.makeText(navController.context, "Login Gagal: $message", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            Toast.makeText(navController.context, "Server error: ${response.message}", Toast.LENGTH_SHORT).show()
                        }
                    } catch (e: Exception) {
                        Toast.makeText(navController.context, "Error parsing response", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }

    // UI Layout
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Teks Selamat Datang
        Text(
            text = "Selamat Datang Kembali di JagoAgri!",
            style = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Teks Deskripsi
        Text(
            text = "Solusi Digital untuk Petani Jagung Indonesia Tingkatkan Hasil Panen Anda Bersama JagoAgri",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(32.dp))

        // Username Field
        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Password Field
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Log In Button
        Button(
            onClick = {
                if (username.isNotEmpty() && password.isNotEmpty()) {
                    // Call login function with username and password
                    login(username, password)
                } else {
                    Toast.makeText(navController.context, "Please fill in both fields", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !isLoading
        ) {
            Text("Log In")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Forgot Password
        TextButton(onClick = { navController.navigate("forgot_password") }) {
            Text("Forgot password?")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Sign Up
        TextButton(onClick = { navController.navigate("signup") }) {
            Text("Don't have an account? Sign Up")
        }

        // Loading Indicator
        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.padding(top = 16.dp))
        }
    }
}

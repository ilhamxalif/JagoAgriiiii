package il.massive.jagoagriiiii.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import il.massive.jagoagriiiii.R
import kotlinx.coroutines.launch
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

@Composable
fun SignUpScreen(navController: NavController) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    val client = OkHttpClient()
    val coroutineScope = rememberCoroutineScope()

    // Fungsi untuk Sign Up
    fun signUp(username: String, password: String, confirmPassword: String) {
        val url = "http://10.0.2.2/jagoagri-api/signup.php" // Endpoint PHP

        val formBody = FormBody.Builder()
            .add("username", username)
            .add("password", password)
            .add("confirm_password", confirmPassword)
            .build()

        val request = Request.Builder()
            .url(url)
            .post(formBody)
            .build()

        // Set loading state
        isLoading = true

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                // Update loading state
                coroutineScope.launch {
                    isLoading = false
                    Toast.makeText(
                        navController.context,
                        "Network Error: ${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                coroutineScope.launch {
                    isLoading = false
                    val responseBody = response.body?.string()
                    val jsonResponse = responseBody?.let { JSONObject(it) }

                    if (response.isSuccessful && jsonResponse != null) {
                        val status = jsonResponse.getString("status")
                        val message = jsonResponse.getString("message")

                        // Handle success or error response
                        if (status == "success") {
                            Toast.makeText(
                                navController.context,
                                "Sign Up Successful: $message",
                                Toast.LENGTH_SHORT
                            ).show()
                            // Navigate to login screen after success
                            navController.navigate("login")
                        } else {
                            Toast.makeText(
                                navController.context,
                                "Error: $message",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        Toast.makeText(
                            navController.context,
                            "Server Error: ${response.message}",
                            Toast.LENGTH_SHORT
                        ).show()
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
        // Logo
        Image(
            painter = painterResource(id = R.drawable.jagoagri_logo),
            contentDescription = "Logo",
            modifier = Modifier.size(150.dp)
        )
        Spacer(modifier = Modifier.height(32.dp))

        Text("Sign Up", style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(24.dp))

        // Username Input
        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Password Input
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Confirm Password Input
        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Confirm Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(32.dp))

        // Sign Up Button
        Button(
            onClick = {
                if (username.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()) {
                    if (password == confirmPassword) {
                        signUp(username, password, confirmPassword)
                    } else {
                        Toast.makeText(
                            navController.context,
                            "Passwords do not match!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Toast.makeText(
                        navController.context,
                        "All fields are required!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !isLoading
        ) {
            Text("Sign Up")
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(onClick = { navController.navigate("login") }) {
            Text("Already have an account? Log In")
        }

        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.padding(top = 16.dp))
        }
    }
}

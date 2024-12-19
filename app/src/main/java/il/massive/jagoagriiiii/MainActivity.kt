package il.massive.jagoagriiiii

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import il.massive.jagoagriiiii.screens.*
import il.massive.jagoagriiiii.ui.theme.JagoAgriTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JagoAgriTheme {
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = "splash_screen") {
                    // Splash Screen
                    composable("splash_screen") { SplashScreen(navController) }

                    // Onboarding Screen
                    composable("onboarding") { OnboardingScreen(navController) }

                    // Login & SignUp Screens
                    composable("login") { LoginScreen(navController) }
                    composable("signup") { SignUpScreen(navController) }
                    composable("forgot_password") { ForgotPasswordScreen(navController) }

                    // Dashboard (Beranda)
                    composable("dashboard") { DashboardScreen(navController) }

                    // Weather
                    composable("weather") { WeatherScreen(navController) }

                    // Calendar
                    composable("calendar") { CalendarScreen(navController) }

                    // Education
                    composable("education") { JagoEduScreen(navController) }

                    // Diagnosis
                    composable("diagnosis") { DiagnosisScreen(navController) }

                    // Monitoring Soil
                    composable("monitoring_soil") { MonitoringSoilScreen(navController) }

                    // Forum
                    composable("forum") { ForumScreen(navController) }

                    // Post Detail
                    composable("post_detail") { PostDetailScreen(navController) }

                    // Create Post
                    composable("create_post") { CreatePostScreen(navController) }
                    composable("settings") { PengaturanScreen(navController) }
                    composable("panduan") { PanduanScreen(navController) }
                    composable("panduan_detail/{topic}") { backStackEntry ->
                        PanduanDetailScreen(navController, backStackEntry)
                    }
                }
            }
        }
    }
}

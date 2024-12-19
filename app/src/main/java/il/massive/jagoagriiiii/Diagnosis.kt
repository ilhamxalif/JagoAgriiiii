package il.massive.jagoagriiiii.screens

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import kotlin.random.Random

@Composable
fun DiagnosisScreen(navController: NavController) {
    val context = LocalContext.current
    var hasCameraPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_GRANTED
        )
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted -> hasCameraPermission = isGranted }

    if (!hasCameraPermission) {
        LaunchedEffect(Unit) {
            permissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    if (hasCameraPermission) {
        DiagnosisContent(navController)
    } else {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Izin kamera diperlukan untuk menggunakan fitur ini.", color = Color.Red)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiagnosisContent(navController: NavController) {
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }
    var selectedTab by remember { mutableStateOf("Tentang Penyakit") }
    var selectedDisease by remember { mutableStateOf<DiagnosisData?>(null) }

    // Data Penyakit
    val diagnosisData = listOf(
        DiagnosisData(
            title = "Karat Daun",
            latinName = "Puccinia Polysora",
            description = "Penyakit ini menyebabkan bercak oranye di daun tanaman jagung.",
            controlMethods = listOf(
                "Pengendalian Kultur Teknis",
                "Penggunaan Fungisida",
                "Sanitasi Lahan",
                "Penggunaan Varietas Resisten"
            )
        ),
        DiagnosisData(
            title = "Busuk Batang",
            latinName = "Fusarium moniliforme",
            description = "Penyakit ini menyebabkan batang tanaman menjadi busuk.",
            controlMethods = listOf(
                "Rotasi tanaman",
                "Pemberian pupuk yang seimbang",
                "Pengendalian gulma",
                "Penggunaan fungisida sistemik"
            )
        ),
        DiagnosisData(
            title = "Hawar Daun",
            latinName = "Helminthosporium turcicum",
            description = "Penyakit ini ditandai dengan munculnya bercak coklat pada daun.",
            controlMethods = listOf(
                "Pemantauan rutin",
                "Penggunaan varietas tahan penyakit",
                "Pengendalian gulma",
                "Penyemprotan fungisida"
            )
        ),
        DiagnosisData(
            title = "Busuk Akar",
            latinName = "Pythium spp.",
            description = "Penyakit ini menyerang akar tanaman dan menghambat pertumbuhan.",
            controlMethods = listOf(
                "Drainase lahan yang baik",
                "Rotasi tanaman",
                "Pengolahan tanah yang tepat",
                "Penggunaan varietas tahan penyakit"
            )
        ),
        DiagnosisData(
            title = "Bercak Daun",
            latinName = "Cercospora zeae-maydis",
            description = "Penyakit ini menyebabkan bercak kecil berwarna coklat pada daun.",
            controlMethods = listOf(
                "Pemangkasan daun yang terinfeksi",
                "Sanitasi kebun",
                "Penggunaan fungisida",
                "Pemupukan yang tepat"
            )
        )
    )

    // Kamera Launcher
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { resultBitmap ->
        bitmap = resultBitmap
        selectedDisease = diagnosisData[Random.nextInt(diagnosisData.size)] // Pilih penyakit secara acak
    }

    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(Color(0xFF007A49), Color(0xFF007A49))
                        )
                    )
                    .padding(top = 40.dp, bottom = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                IconButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier.align(Alignment.TopStart).padding(start = 8.dp)
                ) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Kembali", tint = Color.White)
                }
                Text(
                    text = "Diagnosis",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )
            }
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .background(Color.LightGray, RoundedCornerShape(12.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    if (bitmap != null) {
                        Image(
                            bitmap = bitmap!!.asImageBitmap(),
                            contentDescription = "Foto",
                            modifier = Modifier.fillMaxSize()
                        )
                    } else {
                        Text("Klik tombol kamera untuk mengambil gambar")
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { cameraLauncher.launch(null) },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF007A49))
                ) {
                    Icon(Icons.Filled.CameraAlt, contentDescription = "Kamera", tint = Color.White)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Ambil Foto", color = Color.White)
                }

                Spacer(modifier = Modifier.height(16.dp))

                selectedDisease?.let { disease ->
                    // Informasi Penyakit
                    if (selectedTab == "Tentang Penyakit") {
                        DiagnosisInfo(disease)
                    } else {
                        DiagnosisControl(disease)
                    }

                    // Tab untuk switch konten
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        DiagnosisTab("Tentang Penyakit", selectedTab == "Tentang Penyakit") {
                            selectedTab = "Tentang Penyakit"
                        }
                        DiagnosisTab("Cara Pengendalian", selectedTab == "Cara Pengendalian") {
                            selectedTab = "Cara Pengendalian"
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun DiagnosisTab(title: String, selected: Boolean, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (selected) Color(0xFF007A49) else Color.LightGray
        )
    ) {
        Text(title, color = Color.White)
    }
}

@Composable
fun DiagnosisInfo(disease: DiagnosisData) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF007A49))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(disease.title, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Text("Nama Latin: ${disease.latinName}", color = Color.Gray)
            Spacer(modifier = Modifier.height(8.dp))
            Text(disease.description)
        }
    }
}

@Composable
fun DiagnosisControl(disease: DiagnosisData) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF007A49))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Cara Pengendalian:", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            disease.controlMethods.forEachIndexed { index, method ->
                Text("${index + 1}. $method", modifier = Modifier.padding(vertical = 4.dp))
            }
        }
    }
}

data class DiagnosisData(
    val title: String,
    val latinName: String,
    val description: String,
    val controlMethods: List<String>
)

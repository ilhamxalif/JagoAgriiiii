package il.massive.jagoagriiiii.screens

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import il.massive.jagoagriiiii.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreatePostScreen(navController: NavController) {
    var postContent by remember { mutableStateOf(TextFieldValue("")) }
    var selectedUri by remember { mutableStateOf<Uri?>(null) } // URI untuk gambar/video

    // Launcher untuk memilih file
    val filePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.data?.let { uri ->
                selectedUri = uri // Simpan URI file yang dipilih
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Buat Postingan", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Kembali")
                    }
                }
            )
        },
        bottomBar = {
            // Tombol Posting
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF007A49)),
                contentAlignment = Alignment.Center
            ) {
                TextButton(
                    onClick = {
                        // Logika untuk menyimpan postingan
                        println("Postingan: ${postContent.text}, File: $selectedUri")
                        navController.popBackStack()
                    }
                ) {
                    Text(
                        text = "Posting",
                        fontSize = 16.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            // Header Profil
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.ic_profile),
                    contentDescription = "User Avatar",
                    modifier = Modifier
                        .size(48.dp)
                        .background(Color.Gray, CircleShape),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text("Amin", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Text("Metro", fontSize = 12.sp, color = Color.Gray)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Tambah Gambar/Video
            Text(text = "Tambahkan gambar/video", fontSize = 14.sp, color = Color.Black)
            Spacer(modifier = Modifier.height(8.dp))

            Box(
                modifier = Modifier
                    .size(64.dp)
                    .background(Color(0xFFE0E0E0), RoundedCornerShape(8.dp))
                    .clickable {
                        // Intent untuk memilih gambar atau video
                        val intent = Intent(Intent.ACTION_GET_CONTENT)
                        intent.type = "image/* video/*" // Hanya izinkan gambar dan video
                        filePickerLauncher.launch(intent)
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.Add, contentDescription = "Tambah Gambar", tint = Color.Gray)
            }

            // Pratinjau Gambar/Video
            selectedUri?.let { uri ->
                Spacer(modifier = Modifier.height(16.dp))
                Text("Pratinjau File:", fontSize = 14.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                Image(
                    painter = rememberAsyncImagePainter(uri),
                    contentDescription = "Preview Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .background(Color.Gray, RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Text Field untuk Menulis Postingan
            OutlinedTextField(
                value = postContent,
                onValueChange = { postContent = it },
                placeholder = { Text("Ketik sesuatu disini") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
            )
        }
    }
}

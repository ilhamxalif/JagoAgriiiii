package il.massive.jagoagriiiii.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostDetailScreen(navController: NavController) {
    // Data Komentar Dummy
    val comments = remember {
        listOf(
            Comment(username = "galih", time = "7 hari", content = "Mantap"),
            Comment(username = "petani konoha", time = "2 jam", content = "hujan terus YTTA"),
            Comment(username = "petani sunagakure", time = "2 minggu", content = "informasi yang sangat menarik"),
            Comment(username = "petani mamat", time = "2 jam", content = "terimakasih informasinya"),
            Comment(username = "petani kaya", time = "1 jam", content = "rekom varietas benih yang bagus bang")
        )
    }

    var newComment by remember { mutableStateOf(TextFieldValue("")) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Forum", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Kembali")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Detail Postingan
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF007A49)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Tatang Irawan",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                    Text(
                        text = "20 September 2024",
                        color = Color.LightGray,
                        fontSize = 12.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Halo semua, saya ingin berbagi beberapa tips untuk menanam jagung:\n" +
                                "- Pilih varietas benih yang sesuai dengan iklim daerah.\n" +
                                "- Pastikan tanah sudah dibersihkan dari gulma sebelum menanam.\n" +
                                "- Siram secara teratur, terutama saat musim kemarau.",
                        color = Color.White,
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        Icon(Icons.Default.FavoriteBorder, contentDescription = "Like", tint = Color.White)
                        Icon(Icons.Default.Share, contentDescription = "Share", tint = Color.White)
                    }
                }
            }

            // Judul Komentar
            Text(
                text = "Komentar",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )

            // List Komentar
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(comments) { comment ->
                    CommentItem(comment)
                }
            }

            // Input Komentar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = newComment,
                    onValueChange = { newComment = it },
                    placeholder = { Text("Tulis komentar...") },
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp)
                )
                Button(onClick = {
                    // Logic untuk mengirim komentar
                    // Untuk sementara tampilkan komentar di log
                    println("Komentar terkirim: ${newComment.text}")
                    newComment = TextFieldValue("") // Reset input
                }) {
                    Text("Kirim")
                }
            }
        }
    }
}

// Komponen untuk Menampilkan Item Komentar
@Composable
fun CommentItem(comment: Comment) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = comment.username,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
        )
        Text(
            text = comment.time,
            fontSize = 12.sp,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = comment.content,
            fontSize = 14.sp
        )
        Divider(modifier = Modifier.padding(vertical = 8.dp))
    }
}

// Data Class untuk Komentar
data class Comment(
    val username: String,
    val time: String,
    val content: String
)

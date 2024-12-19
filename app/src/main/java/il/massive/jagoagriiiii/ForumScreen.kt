package il.massive.jagoagriiiii.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import il.massive.jagoagriiiii.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForumScreen(navController: NavController) {
    // Data Postingan Forum
    val posts = remember {
        listOf(
            ForumPost(
                username = "Tatang Irawan",
                content = "Halo semua, saya ingin berbagi beberapa tips untuk menanam jagung:\n- Pilih varietas benih yang sesuai dengan iklim daerah.\n- Pastikan tanah sudah dibersihkan dari gulma sebelum menanam.",
                timestamp = "25 September 2024"
            ),
            ForumPost(
                username = "Iman",
                content = "Selamat pagi, teman-teman! Saya mengalami masalah dengan hama ulat pada tanaman jagung saya. Apa ada yang punya rekomendasi pestisida alami?",
                timestamp = "25 September 2024"
            ),
            ForumPost(
                username = "Maman",
                content = "Saya ingin berdiskusi mengenai jadwal tanam jagung di daerah kita. Kapan biasanya teman-teman mulai menanam?",
                timestamp = "24 September 2024"
            )
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Forum", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("create_post") },
                containerColor = Color(0xFF007A49)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Tambah Postingan")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Search Bar
            OutlinedTextField(
                value = "",
                onValueChange = {},
                placeholder = { Text("Search") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search Icon") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .background(Color(0xFF007A49), RoundedCornerShape(16.dp))
            )

            // List of Posts
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                items(posts) { post ->
                    ForumPostItem(post, onClick = { navController.navigate("post_detail") })
                }
            }
        }
    }
}

// Komponen untuk Menampilkan Item Postingan
@Composable
fun ForumPostItem(post: ForumPost, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF007A49))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Avatar Pengguna
            Image(
                painter = painterResource(id = R.drawable.ic_profile), // Tambahkan placeholder avatar
                contentDescription = "User Avatar",
                modifier = Modifier
                    .size(48.dp)
                    .background(Color.White, CircleShape),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(8.dp))

            // Konten Postingan
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = post.username,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = post.content,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 14.sp,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = post.timestamp,
                    fontSize = 12.sp,
                    color = Color.LightGray
                )
            }

            // Ikon Like dan Share
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                IconButton(onClick = { /* Like action */ }) {
                    Icon(Icons.Default.FavoriteBorder, contentDescription = "Like", tint = Color.White)
                }
                IconButton(onClick = { /* Share action */ }) {
                    Icon(Icons.Default.Share, contentDescription = "Share", tint = Color.White)
                }
            }
        }
    }
}

// Data Class untuk Forum Post
data class ForumPost(
    val username: String,
    val content: String,
    val timestamp: String
)

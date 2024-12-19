package il.massive.jagoagriiiii.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarScreen(navController: NavController) {
    var selectedDate by remember { mutableStateOf<LocalDate?>(null) }
    var showForm by remember { mutableStateOf(false) }
    val events = remember { mutableStateMapOf<LocalDate, String>() }
    val currentMonth = remember { mutableStateOf(LocalDate.now()) }
    var activityName by remember { mutableStateOf("") }
    var notificationEnabled by remember { mutableStateOf(false) }

    Scaffold(
        topBar = { CalendarHeader(currentMonth, navController) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showForm = true }, // Show form when "Add" is clicked
                containerColor = Color(0xFF007A49),
                shape = CircleShape
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Tambah Event", tint = Color.White)
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            // Calendar Grid
            CalendarGrid(
                currentMonth = currentMonth.value,
                selectedDate = selectedDate,
                onDateSelected = { selectedDate = it },
                events = events
            )

            // Display selected event details
            Spacer(modifier = Modifier.height(16.dp))
            selectedDate?.let { date ->
                Text(
                    text = "${date.format(DateTimeFormatter.ofPattern("dd MMMM yyyy"))}: ${events[date] ?: "Tidak ada kegiatan"}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }

            // Show Event Form below calendar if a date is selected
            if (showForm && selectedDate != null) {
                EventFormDialog(
                    selectedDate = selectedDate!!,
                    onSave = { eventName ->
                        events[selectedDate!!] = eventName
                        showForm = false // Hide the form after saving
                    },
                    activityName = activityName,
                    onActivityNameChange = { activityName = it },
                    notificationEnabled = notificationEnabled,
                    onNotificationChange = { notificationEnabled = !notificationEnabled }
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarHeader(currentMonth: MutableState<LocalDate>, navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { currentMonth.value = currentMonth.value.minusMonths(1) }) {
            Icon(Icons.Filled.ArrowBack, contentDescription = "Previous Month")
        }
        Text(
            text = currentMonth.value.format(DateTimeFormatter.ofPattern("MMMM yyyy")),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        IconButton(onClick = { currentMonth.value = currentMonth.value.plusMonths(1) }) {
            Icon(Icons.Filled.ArrowForward, contentDescription = "Next Month")
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarGrid(
    currentMonth: LocalDate,
    selectedDate: LocalDate?,
    onDateSelected: (LocalDate) -> Unit,
    events: Map<LocalDate, String>
) {
    val daysInMonth = YearMonth.from(currentMonth).lengthOfMonth()
    val firstDayOfMonth = currentMonth.withDayOfMonth(1).dayOfWeek.value % 7
    val days = (1..firstDayOfMonth).map { null } + (1..daysInMonth).map { it }

    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        listOf("Sen", "Sel", "Rab", "Kam", "Jum", "Sab", "Min").forEach { day ->
            Text(day, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center, modifier = Modifier.weight(1f))
        }
    }

    LazyVerticalGrid(columns = GridCells.Fixed(7), modifier = Modifier.padding(top = 8.dp)) {
        items(days.size) { index ->
            val day = days[index]
            val date = day?.let { currentMonth.withDayOfMonth(it) }

            Box(
                modifier = Modifier
                    .size(40.dp)
                    .padding(4.dp)
                    .background(
                        color = when {
                            date == selectedDate -> Color(0xFF007A49)
                            date in events.keys -> Color(0xFFFFD700)
                            else -> Color.Transparent
                        },
                        shape = RoundedCornerShape(8.dp)
                    )
                    .clickable { date?.let { onDateSelected(it) } },
                contentAlignment = Alignment.Center
            ) {
                if (day != null) {
                    Text(
                        text = day.toString(),
                        color = if (date == selectedDate) Color.White else Color.Black
                    )
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EventFormDialog(
    selectedDate: LocalDate,
    onSave: (String) -> Unit,
    activityName: String,
    onActivityNameChange: (String) -> Unit,
    notificationEnabled: Boolean,
    onNotificationChange: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        // Display selected date
        Text(
            text = "Tanggal: ${selectedDate.format(DateTimeFormatter.ofPattern("dd MMMM yyyy"))}",
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Text field for activity name
        OutlinedTextField(
            value = activityName,
            onValueChange = onActivityNameChange,
            label = { Text("Nama Kegiatan") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
        )

        // Button for toggling notification status
        Text(
            text = "Notifikasi",
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Button(
            onClick = onNotificationChange,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                contentColor = Color(0xFF006400)
            ),
            border = ButtonDefaults.outlinedButtonBorder.copy(width = 1.dp)
        ) {
            Text(text = if (notificationEnabled) "Aktif" else "Sembunyikan")
        }

        // Save button
        Button(
            onClick = {
                if (activityName.isNotBlank()) {
                    onSave(activityName)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF007A49)),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text("Simpan", color = Color.White)
        }
    }
}

package dz.cosider.suivitrx

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Green = Color(0xFF087F5B)
private val Dark = Color(0xFF17211F)
private val Background = Color(0xFFF6F7F4)

data class Chantier(
    val code: String,
    val name: String,
    val location: String,
    val progress: Int
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme(
                colorScheme = lightColorScheme(
                    primary = Green,
                    background = Background
                )
            ) {
                App()
            }
        }
    }
}

@Composable
fun App() {
    var loggedIn by remember { mutableStateOf(false) }

    if (!loggedIn) {
        LoginScreen {
            loggedIn = true
        }
    } else {
        Dashboard {
            loggedIn = false
        }
    }
}

@Composable
fun LoginScreen(onLogin: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(28.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    "COSIDER AGRICO",
                    color = Green,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )

                Text(
                    "SUIVI-TRX",
                    color = Dark,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 34.sp
                )

                Text(
                    "Suivi des travaux & contrats",
                    color = Color.Gray
                )

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Email") },
                    singleLine = true
                )

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Mot de passe") },
                    singleLine = true
                )

                Button(
                    onClick = onLogin,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text("Se connecter")
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Dashboard(onLogout: () -> Unit) {
    val chantiers = listOf(
        Chantier("CTR-001", "Travaux routiers", "Adrar", 72),
        Chantier("CTR-002", "Ouvrage hydraulique", "Alger", 48),
        Chantier("CTR-003", "Aménagement VRD", "Tizi Ouzou", 91)
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            "SUIVI-TRX",
                            fontWeight = FontWeight.ExtraBold
                        )
                        Text(
                            "COSIDER AGRICO • UEV",
                            fontSize = 11.sp,
                            color = Color.Gray
                        )
                    }
                },
                actions = {
                    IconButton(onClick = onLogout) {
                        Icon(Icons.Default.Logout, "Déconnexion")
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = true,
                    onClick = {},
                    icon = { Icon(Icons.Default.Home, null) },
                    label = { Text("Accueil") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = {},
                    icon = { Icon(Icons.Default.Build, null) },
                    label = { Text("Chantiers") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = {},
                    icon = { Icon(Icons.Default.Description, null) },
                    label = { Text("Rapports") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = {},
                    icon = { Icon(Icons.Default.Person, null) },
                    label = { Text("Profil") }
                )
            }
        }
    ) { padding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {

            item {
                Text(
                    "Bonjour 👋",
                    color = Color.Gray
                )

                Text(
                    "Tableau de bord",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    StatCard(
                        "Chantiers",
                        "12",
                        Modifier.weight(1f)
                    )

                    StatCard(
                        "En cours",
                        "8",
                        Modifier.weight(1f)
                    )
                }
            }

            item {
                Text(
                    "Mes chantiers",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            items(chantiers) { chantier ->
                ChantierCard(chantier)
            }
        }
    }
}

@Composable
fun StatCard(
    title: String,
    value: String,
    modifier: Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                value,
                fontSize = 28.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Green
            )

            Text(
                title,
                color = Color.Gray
            )
        }
    }
}

@Composable
fun ChantierCard(chantier: Chantier) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(
            modifier = Modifier.padding(18.dp)
        ) {
            Text(
                chantier.code,
                color = Green,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                chantier.name,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                chantier.location,
                color = Color.Gray
            )

            Spacer(Modifier.height(12.dp))

            LinearProgressIndicator(
                progress = { chantier.progress / 100f },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
            )

            Spacer(Modifier.height(8.dp))

            Text(
                "${chantier.progress}% d'avancement",
                color = Green,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

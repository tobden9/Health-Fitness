package com.example.health__fitness

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.health__fitness.ui.theme.HealthFitnessTheme
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.PasswordVisualTransformation // Import for masking password
import androidx.compose.ui.text.input.VisualTransformation // Import for text masking

// --- Navigation Route Constants ---
sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Details : Screen("details")
    object Profile : Screen("profile")
    object Settings : Screen("settings")
}

// --- Main Activity ---
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HealthFitnessTheme {
                MyApp()
            }
        }
    }
}

// --- App Navigation Host (Starts at Details) ---
@Composable
fun MyApp() {
    val navController = rememberNavController()

    // START DESTINATION IS NOW DETAILS
    NavHost(navController = navController, startDestination = Screen.Details.route) {
        composable(Screen.Home.route) { HomeScreen(navController) }
        composable(Screen.Details.route) { DetailsScreen(navController) }
        composable(Screen.Profile.route) { ProfileScreen(navController) }
        composable(Screen.Settings.route) { SettingsScreen(navController) }
    }
}

// --- 1. Home Screen (Main Content) ---
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, modifier: Modifier = Modifier) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("App Dashboard") }) }
    ) { padding ->
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // Column 1: Dice Images (Placeholder)
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(100.dp)
                    .background(Color.Yellow),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                repeat(image_ids.size) { index ->
                    Image(
                        painter = painterResource(image_ids[index]),
                        contentDescription = "Dice ${index + 1}",
                        modifier = Modifier.width(60.dp)
                    )
                }
            }

            // Column 2: Navigation Buttons
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(120.dp)
                    .background(Color.Gray),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(onClick = { navController.navigate(Screen.Details.route) }) {
                    Text("Details")
                }
                Button(onClick = { navController.navigate(Screen.Profile.route) }) {
                    Text("Profile")
                }
                Button(onClick = { navController.navigate(Screen.Settings.route) }) {
                    Text("Settings")
                }
            }

            // Column 3: Placeholder
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
                    .background(Color.Green),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Main Content Area", color = Color.Black)
            }
        }
    }
}

// --- 2. Details Screen (Login Screen with Password) ---
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(navController: NavController) {
    var userInput by remember { mutableStateOf("") }
    var passwordInput by remember { mutableStateOf("") } // New state for password

    Scaffold(
        topBar = { TopAppBar(title = { Text("Welcome / Login") }) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(32.dp), // Increased padding for better look
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Log in to your account",
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Username/Name TextField
            TextField(
                value = userInput,
                onValueChange = { userInput = it },
                label = { Text("Username") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Password TextField
            TextField(
                value = passwordInput,
                onValueChange = { passwordInput = it },
                label = { Text("Password") },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(), // Masks the input (dots)
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(30.dp))

            Button(onClick = {
                // Simple validation check: ensure fields are not empty
                if (userInput.isNotBlank() && passwordInput.isNotBlank()) {
                    // Navigate to the main app screen and clear the login screen from the back stack
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Details.route) { inclusive = true }
                    }
                }
            },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Log In")
            }

            Spacer(modifier = Modifier.height(20.dp))

            if (userInput.isNotEmpty()) {
                Text(text = "Welcome, $userInput!", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}

// --- 3. Profile Screen ---
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavController) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Profile") }) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("This is the Profile Screen")
            Spacer(modifier = Modifier.height(20.dp))
            Button(onClick = { navController.popBackStack() }) {
                Text("Go Back")
            }
        }
    }
}

// --- 4. Settings Screen ---
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(navController: NavController) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Settings") }) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("This is the Settings Screen")
            Spacer(modifier = Modifier.height(20.dp))
            Button(onClick = { navController.popBackStack() }) {
                Text("Go Back")
            }
        }
    }
}


// --- Preview and Resources ---

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    HealthFitnessTheme {
        // Preview the Login Screen
        DetailsScreen(navController = rememberNavController())
    }
}

// Resource IDs (Requires dice_1 to dice_6 drawables in res/drawable)
private val image_ids = listOf(
    R.drawable.dice_1,
    R.drawable.dice_2,
    R.drawable.dice_3,
    R.drawable.dice_4,
    R.drawable.dice_5,
    R.drawable.dice_6
)
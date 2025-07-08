package cz.cvut.fel.zan_kramkvol.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import cz.cvut.fel.zan_kramkvol.datastore.ThemePreferenceManager
import cz.cvut.fel.zan_kramkvol.ui.components.BottomNavBar
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(navController: NavController) {
    val context = LocalContext.current
    val themeManager = remember { ThemePreferenceManager(context) }
    val scope = rememberCoroutineScope()

    val isDarkMode by themeManager.themeFlow.collectAsState(initial = false)

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Settings") })
        },
        bottomBar = {
            BottomNavBar(currentRoute = "settings") { route ->
                navController.navigate(route)
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Dark Mode")
                Switch(
                    checked = isDarkMode,
                    onCheckedChange = {
                        scope.launch {
                            themeManager.saveTheme(it)
                        }
                    }
                )
            }
        }
    }
}

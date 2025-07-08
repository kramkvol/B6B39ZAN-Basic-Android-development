package cz.cvut.fel.zan_kramkvol

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.*
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.rememberNavController
import cz.cvut.fel.zan_kramkvol.datastore.ThemePreferenceManager
import cz.cvut.fel.zan_kramkvol.notifications.scheduleSimpleReminder
import cz.cvut.fel.zan_kramkvol.navigation.NavGraph
import cz.cvut.fel.zan_kramkvol.ui.theme.ZAN_kramkvolTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        if (Build.VERSION.SDK_INT >= 33) {
            requestPermissions(arrayOf(android.Manifest.permission.POST_NOTIFICATIONS), 0)
        }
        createNotificationChannel()
        scheduleSimpleReminder(this)

        val themeManager = ThemePreferenceManager(applicationContext)

        setContent {
            val isDarkTheme by themeManager.themeFlow.collectAsState(initial = false)

            ZAN_kramkvolTheme(darkTheme = isDarkTheme) {
                Surface(color = MaterialTheme.colorScheme.background) {
                    val navController = rememberNavController()
                    NavGraph(navController = navController)
                }
            }
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "reminder_channel",
                "Reading Reminder",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
    }

}

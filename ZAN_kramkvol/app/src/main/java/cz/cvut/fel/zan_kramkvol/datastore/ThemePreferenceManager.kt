package cz.cvut.fel.zan_kramkvol.datastore

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = "settings")

class ThemePreferenceManager(private val context: Context) {
    private val THEME_KEY = booleanPreferencesKey("dark_mode")

    val themeFlow: Flow<Boolean> = context.dataStore.data.map {
        it[THEME_KEY] ?: false
    }

    suspend fun saveTheme(isDark: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[THEME_KEY] = isDark
        }
    }
}

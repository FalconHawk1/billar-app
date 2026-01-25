package co.hitech.billar_app.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import co.hitech.billar_app.utils.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "billiard_preferences")

/**
 * Manager for storing and retrieving preferences using DataStore
 */
class PreferencesManager(private val context: Context) {
    
    companion object {
        val TABLE_ID = stringPreferencesKey("table_id")
        val CAMERA_URL = stringPreferencesKey("camera_url")
        val PRICE_PER_MINUTE = doublePreferencesKey("price_per_minute")
        val API_BASE_URL = stringPreferencesKey("api_base_url")
        val TABLE_NAME = stringPreferencesKey("table_name")
    }
    
    /**
     * Get table ID
     */
    val tableId: Flow<String> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            preferences[TABLE_ID] ?: Constants.DEFAULT_TABLE_ID
        }
    
    /**
     * Get camera URL
     */
    val cameraUrl: Flow<String> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            preferences[CAMERA_URL] ?: Constants.DEFAULT_CAMERA_URL
        }
    
    /**
     * Get price per minute
     */
    val pricePerMinute: Flow<Double> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            preferences[PRICE_PER_MINUTE] ?: Constants.DEFAULT_PRICE_PER_MINUTE
        }
    
    /**
     * Get API base URL
     */
    val apiBaseUrl: Flow<String> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            preferences[API_BASE_URL] ?: Constants.DEFAULT_API_BASE_URL
        }
    
    /**
     * Get table name
     */
    val tableName: Flow<String> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            preferences[TABLE_NAME] ?: "Mesa 1"
        }
    
    /**
     * Save table ID
     */
    suspend fun saveTableId(tableId: String) {
        context.dataStore.edit { preferences ->
            preferences[TABLE_ID] = tableId
        }
    }
    
    /**
     * Save camera URL
     */
    suspend fun saveCameraUrl(url: String) {
        context.dataStore.edit { preferences ->
            preferences[CAMERA_URL] = url
        }
    }
    
    /**
     * Save price per minute
     */
    suspend fun savePricePerMinute(price: Double) {
        context.dataStore.edit { preferences ->
            preferences[PRICE_PER_MINUTE] = price
        }
    }
    
    /**
     * Save API base URL
     */
    suspend fun saveApiBaseUrl(url: String) {
        context.dataStore.edit { preferences ->
            preferences[API_BASE_URL] = url
        }
    }
    
    /**
     * Save table name
     */
    suspend fun saveTableName(name: String) {
        context.dataStore.edit { preferences ->
            preferences[TABLE_NAME] = name
        }
    }
}

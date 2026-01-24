package co.hitech.billar_app.data.local

import android.content.Context
import co.hitech.billar_app.data.model.TableConfig
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

/**
 * Manager for handling table configuration
 */
class ConfigurationManager(private val preferencesManager: PreferencesManager) {
    
    /**
     * Get complete table configuration as a Flow
     */
    fun getTableConfig(): Flow<TableConfig> {
        return combine(
            preferencesManager.tableId,
            preferencesManager.cameraUrl,
            preferencesManager.pricePerMinute,
            preferencesManager.tableName
        ) { tableId, cameraUrl, pricePerMinute, tableName ->
            TableConfig(
                tableId = tableId,
                cameraUrl = cameraUrl,
                pricePerMinute = pricePerMinute,
                tableName = tableName,
                isActive = true
            )
        }
    }
    
    /**
     * Save complete table configuration
     */
    suspend fun saveTableConfig(config: TableConfig) {
        preferencesManager.saveTableId(config.tableId)
        preferencesManager.saveCameraUrl(config.cameraUrl)
        preferencesManager.savePricePerMinute(config.pricePerMinute)
        preferencesManager.saveTableName(config.tableName)
    }
    
    companion object {
        @Volatile
        private var instance: ConfigurationManager? = null
        
        fun getInstance(context: Context): ConfigurationManager {
            return instance ?: synchronized(this) {
                instance ?: ConfigurationManager(
                    PreferencesManager(context.applicationContext)
                ).also { instance = it }
            }
        }
    }
}

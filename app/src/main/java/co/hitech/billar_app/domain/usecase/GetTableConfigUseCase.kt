package co.hitech.billar_app.domain.usecase

import co.hitech.billar_app.data.model.TableConfig
import co.hitech.billar_app.data.repository.TableRepository

/**
 * Use case for getting table configuration
 */
class GetTableConfigUseCase(
    private val repository: TableRepository
) {
    suspend operator fun invoke(tableId: String): Result<TableConfig> {
        return repository.getTableConfig(tableId)
    }
}

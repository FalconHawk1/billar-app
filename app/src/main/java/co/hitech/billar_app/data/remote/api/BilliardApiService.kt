package co.hitech.billar_app.data.remote.api

import co.hitech.billar_app.data.remote.dto.*
import retrofit2.Response
import retrofit2.http.*

/**
 * Retrofit API service for billiard app backend
 */
interface BilliardApiService {
    
    /**
     * Start a new game session
     */
    @POST("api/sessions/start")
    suspend fun startSession(
        @Body request: SessionRequest
    ): Response<SessionResponse>
    
    /**
     * Update an existing session
     */
    @PUT("api/sessions/update")
    suspend fun updateSession(
        @Body request: UpdateSessionRequest
    ): Response<SessionResponse>
    
    /**
     * End a game session
     */
    @POST("api/sessions/end")
    suspend fun endSession(
        @Body request: UpdateSessionRequest
    ): Response<SessionResponse>
    
    /**
     * Update player score
     */
    @POST("api/players/score")
    suspend fun updatePlayerScore(
        @Body request: PlayerScoreRequest
    ): Response<SessionResponse>
    
    /**
     * Get table configuration
     */
    @GET("api/tables/{tableId}/config")
    suspend fun getTableConfig(
        @Path("tableId") tableId: String
    ): Response<ConfigResponse>
}

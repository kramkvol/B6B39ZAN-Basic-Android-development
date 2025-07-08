package cz.cvut.fel.zan_kramkvol.api

import retrofit2.http.GET
import retrofit2.http.Query

interface BooksApi {
    @GET("volumes")
    suspend fun searchBooks(
        @Query("q") query: String
    ): BooksResponse
}

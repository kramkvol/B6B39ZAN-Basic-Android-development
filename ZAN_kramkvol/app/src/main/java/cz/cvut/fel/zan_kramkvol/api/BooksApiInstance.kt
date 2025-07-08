package cz.cvut.fel.zan_kramkvol.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object BooksApiInstance {
    private const val BASE_URL = "https://www.googleapis.com/books/v1/"

    val retrofitService: BooksApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BooksApi::class.java)
    }
}

package cz.cvut.fel.zan_kramkvol.api

/**
 * Wrapper for the entire response from the Google Books API.
 * The API returns a list of book items inside the "items" field.
 */
data class BooksResponse(
    val items: List<GoogleBookItem>?
)

/**
 * Each item from the "items" list in the Google Books API response.
 * It contains the volume information for a single book.
 */
data class GoogleBookItem(
    val volumeInfo: BookItem
)

/**
 * Detailed information about a specific book.
 * Includes title, list of authors, published date, and description.
 */
data class BookItem(
    val title: String,
    val authors: List<String>?,
    val publishedDate: String?,
    val description: String?
)

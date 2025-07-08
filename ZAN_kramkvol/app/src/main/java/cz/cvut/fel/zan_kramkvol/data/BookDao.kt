package cz.cvut.fel.zan_kramkvol.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

// Define a DAO (Data Access Object) interface to interact with the books table in Room database
@Dao
interface BookDao {
    @Query("SELECT * FROM books ORDER BY id DESC")
    fun getAllBooks(): Flow<List<Book>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(book: Book)

    @Delete
    suspend fun delete(book: Book)

    @Query("DELETE FROM books")
    suspend fun deleteAll()

    @Query("DELETE FROM books WHERE id = :bookId")
    suspend fun deleteById(bookId: Int)

    @Query("SELECT * FROM books WHERE id = :id")
    fun getBookById(id: Int): Flow<Book?>

    @Query("SELECT * FROM books")
    suspend fun getAllBooksOnce(): List<Book>

    @Update
    suspend fun update(book: Book)

    @Query("SELECT * FROM books WHERE title = :title AND author = :author LIMIT 1")
    suspend fun findByTitleAndAuthor(title: String, author: String): Book?

}

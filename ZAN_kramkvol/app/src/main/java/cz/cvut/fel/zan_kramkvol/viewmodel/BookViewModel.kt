package cz.cvut.fel.zan_kramkvol.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.cvut.fel.zan_kramkvol.data.Book
import cz.cvut.fel.zan_kramkvol.data.BookDao
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class BookViewModel(private val bookDao: BookDao) : ViewModel() {

    val books = bookDao.getAllBooks()
        .map { it.sortedBy { book -> book.startDate } }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun addBook(book: Book) {
        viewModelScope.launch {
            bookDao.insert(book)
        }
    }

    fun deleteBookById(id: Int) {
        viewModelScope.launch {
            bookDao.deleteById(id)
        }
    }
    fun getBookById(id: Int): Flow<Book?> {
        return bookDao.getBookById(id)
    }

    fun updateBook(book: Book) {
        viewModelScope.launch {
            bookDao.update(book)
        }
    }
}

package cz.cvut.fel.zan_kramkvol.api

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ImportViewModel : ViewModel() {

    private val _results = MutableStateFlow<List<BookItem>>(emptyList())
    val results: StateFlow<List<BookItem>> = _results

    private val _isLoading = MutableStateFlow(false)

    fun searchBooks(query: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = BooksApiInstance.retrofitService.searchBooks(query)
                val items = response.items?.map { it.volumeInfo } ?: emptyList()
                _results.value = items
            } catch (e: Exception) {
                _results.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }
}

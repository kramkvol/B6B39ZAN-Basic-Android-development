package cz.cvut.fel.zan_kramkvol.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import cz.cvut.fel.zan_kramkvol.data.AppDatabase
import cz.cvut.fel.zan_kramkvol.ui.components.BookForm
import cz.cvut.fel.zan_kramkvol.ui.components.BottomNavBar
import cz.cvut.fel.zan_kramkvol.viewmodel.BookViewModel
import cz.cvut.fel.zan_kramkvol.viewmodel.BookViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditBookScreen(navController: NavController, bookId: Int) {
    val context = LocalContext.current
    val dao = AppDatabase.getInstance(context).bookDao()
    val viewModel: BookViewModel = viewModel(factory = BookViewModelFactory(dao))

    val book by viewModel.getBookById(bookId).collectAsState(initial = null)

    var title by rememberSaveable { mutableStateOf("") }
    var author by rememberSaveable { mutableStateOf("") }
    var startDate by rememberSaveable { mutableStateOf("") }
    var endDate by rememberSaveable { mutableStateOf("") }

    var note by rememberSaveable { mutableStateOf("") }
    var category by rememberSaveable { mutableStateOf("Planning to read") }

    LaunchedEffect(book) {
        book?.let {
            title = it.title
            author = it.author
            startDate = it.startDate
            endDate = it.endDate
            note = it.note
            category = it.category
        }
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Edit Book") }) },
        bottomBar = {
            BottomNavBar(currentRoute = "home") { route ->
                navController.navigate(route)
            }
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).padding(16.dp)) {
            BookForm(
                title = title,
                onTitleChange = { title = it },
                author = author,
                onAuthorChange = { author = it },
                startDate = startDate,
                onStartDateChange = { startDate = it },
                endDate = endDate,
                onEndDateChange = { endDate = it },
                category = category,
                onCategoryChange = { category = it },
                note = note,
                onNoteChange = { note = it },
                onSubmit = {
                    book?.let {
                        viewModel.updateBook(
                            it.copy(
                                title = title,
                                author = author,
                                startDate = startDate,
                                endDate = endDate,
                                category = category,
                                note = note
                            )
                        )
                        navController.navigateUp()
                    }
                },
                submitText = "Update Book"
            )
        }
    }
}

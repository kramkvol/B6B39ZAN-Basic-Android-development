package cz.cvut.fel.zan_kramkvol.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import cz.cvut.fel.zan_kramkvol.data.Book
import androidx.navigation.NavController
import cz.cvut.fel.zan_kramkvol.api.ImportViewModel
import cz.cvut.fel.zan_kramkvol.data.AppDatabase
import cz.cvut.fel.zan_kramkvol.ui.components.BottomNavBar
import cz.cvut.fel.zan_kramkvol.viewmodel.BookViewModel
import cz.cvut.fel.zan_kramkvol.viewmodel.BookViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImportBookScreen(
    navController: NavController,
    viewModel: ImportViewModel = viewModel()
) {
    val context = LocalContext.current
    val dao = AppDatabase.getInstance(context).bookDao()
    val bookViewModel: BookViewModel = viewModel(factory = BookViewModelFactory(dao))

    var query by rememberSaveable { mutableStateOf("") }
    val books by viewModel.results.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Import Book") })
        },
        bottomBar = {
            BottomNavBar(currentRoute = "search") { route ->
                navController.navigate(route)
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = query,
                onValueChange = { query = it },
                label = { Text("Search book") },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = { viewModel.searchBooks(query) },
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Text("Search in Google Books")
            }

            LazyColumn(modifier = Modifier.padding(top = 16.dp)) {
                items(books) { bookInfo ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        Column(Modifier.padding(16.dp)) {
                            Text(text = bookInfo.title, style = MaterialTheme.typography.titleMedium)
                            Text(text = "By: ${bookInfo.authors?.joinToString() ?: "Unknown"}")
                            Text(text = "Published: ${bookInfo.publishedDate ?: "?"}")

                            Button(
                                onClick = {
                                    val book = Book(
                                        title = bookInfo.title,
                                        author = bookInfo.authors?.joinToString() ?: "Unknown",
                                        startDate = "",
                                        endDate = "",
                                        category = "Planning to read",
                                        note = bookInfo.description ?: ""
                                    )
                                    bookViewModel.addBook(book)
                                    navController.navigate("home")
                                },
                                modifier = Modifier.padding(top = 8.dp)
                            ) {
                                Text("Add to My Books")
                            }
                        }
                    }
                }
            }
        }
    }
}

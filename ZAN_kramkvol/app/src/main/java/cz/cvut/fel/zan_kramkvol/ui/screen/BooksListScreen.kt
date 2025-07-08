package cz.cvut.fel.zan_kramkvol.ui.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.compose.foundation.combinedClickable
import androidx.compose.runtime.saveable.rememberSaveable
import cz.cvut.fel.zan_kramkvol.data.AppDatabase
import cz.cvut.fel.zan_kramkvol.ui.components.BottomNavBar
import cz.cvut.fel.zan_kramkvol.viewmodel.BookViewModel
import cz.cvut.fel.zan_kramkvol.viewmodel.BookViewModelFactory

@SuppressLint("AutoboxingStateCreation")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun MainScreen(navController: NavController) {
    val context = LocalContext.current
    val dao = AppDatabase.getInstance(context).bookDao()
    val factory = BookViewModelFactory(dao)
    val viewModel: BookViewModel = viewModel(factory = factory)

    val books by viewModel.books.collectAsState(initial = emptyList())
    val selectedBooks = remember { mutableStateListOf<Int>() }
    var selectionMode by remember { mutableStateOf(false) }

    val categories = listOf("All", "Reading now", "Planning to read", "Already read")
    var selectedCategoryIndex by rememberSaveable { mutableStateOf(0) }

    val selectedCategory = categories[selectedCategoryIndex]
    val filteredBooks = books.filter { book ->
        selectedCategory == "All" || book.category == selectedCategory
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    if (selectionMode) Text("Selected: ${selectedBooks.size}")
                    else Text("Books List")
                },
                actions = {
                    if (selectionMode) {
                        IconButton(onClick = {
                            selectedBooks.forEach { id ->
                                viewModel.deleteBookById(id)
                            }
                            selectedBooks.clear()
                            selectionMode = false
                        }) {
                            Icon(Icons.Default.Delete, contentDescription = "Delete")
                        }
                    }
                }
            )
        },
        bottomBar = {
            BottomNavBar(currentRoute = "home") { route ->
                navController.navigate(route)
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Category Tabs
            TabRow(
                selectedTabIndex = selectedCategoryIndex,
                modifier = Modifier.fillMaxWidth()
            ) {
                categories.forEachIndexed { index, category ->
                    Tab(
                        selected = selectedCategoryIndex == index,
                        onClick = { selectedCategoryIndex = index },
                        text = { Text(category) }
                    )
                }
            }

            // Book List
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                items(filteredBooks) { book ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .combinedClickable(
                                onClick = {
                                    if (selectionMode) {
                                        if (selectedBooks.contains(book.id)) {
                                            selectedBooks.remove(book.id)
                                            if (selectedBooks.isEmpty()) selectionMode = false
                                        } else {
                                            selectedBooks.add(book.id)
                                        }
                                    } else {
                                        navController.navigate("edit/${book.id}")
                                    }
                                },
                                onLongClick = {
                                    if (!selectionMode) {
                                        selectionMode = true
                                        selectedBooks.add(book.id)
                                    }
                                }
                            )
                            .border(
                                width = 1.dp,
                                color = MaterialTheme.colorScheme.outline,
                                shape = MaterialTheme.shapes.medium
                            ),
                        colors = CardDefaults.cardColors(
                            containerColor = if (selectedBooks.contains(book.id))
                                MaterialTheme.colorScheme.secondaryContainer
                            else
                                MaterialTheme.colorScheme.surface
                        )
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(text = book.title, style = MaterialTheme.typography.titleMedium)
                            Text(text = "by ${book.author}", style = MaterialTheme.typography.bodyMedium)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "${book.startDate} - ${book.endDate}",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            }
        }
    }
}


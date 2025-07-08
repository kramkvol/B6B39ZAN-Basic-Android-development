package cz.cvut.fel.zan_kramkvol.data

import androidx.room.Entity
import androidx.room.PrimaryKey

/*Define a data class that represents a Book entity in the Room database
 Annotates this class as a database table called "books"
 Each book gets a unique ID automatically */
@Entity(tableName = "books")
data class Book(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val author: String,
    val title: String,
    val startDate: String = "",
    val endDate: String = "",
    val category: String = "",
    val note: String = ""
)

package cz.cvut.fel.zan_kramkvol.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/*
This is a Room database.
It uses Book as the only table (entity).

The bookDao() function provides access to the data operations for books (insert, delete, update, etc.).

The getInstance() function ensures the database is created only once and reused afterward (singleton pattern).

*/
@Database(entities = [Book::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun bookDao(): BookDao

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,      
                    AppDatabase::class.java,          
                    "book_database"                   
                ).build()
                INSTANCE = instance
                instance 
            }
        }
    }
}
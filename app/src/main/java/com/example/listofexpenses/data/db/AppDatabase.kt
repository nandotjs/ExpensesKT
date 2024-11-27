package com.example.listofexpenses.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.listofexpenses.data.dao.ExpenseDao
import com.example.listofexpenses.data.dao.UserDao
import com.example.listofexpenses.data.model.Expense
import com.example.listofexpenses.data.model.User

@Database(entities = [User::class, Expense::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    // Define os DAOs
    abstract fun userDao(): UserDao
    abstract fun expenseDao(): ExpenseDao

    companion object {
        // Instância do banco de dados
        @Volatile
        private var INSTANCE: AppDatabase? = null

        // Método para obter a instância do banco de dados
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                )
                .fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}

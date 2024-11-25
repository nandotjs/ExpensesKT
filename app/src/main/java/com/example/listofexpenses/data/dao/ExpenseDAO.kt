package com.example.listofexpenses.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Delete
import com.example.listofexpenses.data.model.Expense

@Dao
interface ExpenseDao {
    @Insert
    suspend fun insertExpense(expense: Expense)

    @Query("SELECT * FROM expense WHERE userId = :userId")
    suspend fun getExpensesByUserId(userId: Int): List<Expense>

    @Delete
    suspend fun deleteExpense(expense: Expense)
}

package com.example.listofexpenses.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.listofexpenses.data.model.Expense

@Dao
interface ExpenseDao {
    @Insert
    suspend fun insertExpense(expense: Expense)

    @Query("SELECT * FROM expense")
    suspend fun getAllExpenses(): List<Expense>
}

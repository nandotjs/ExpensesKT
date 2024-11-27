package com.example.listofexpenses.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Delete
import com.example.listofexpenses.data.model.Expense

@Dao
interface ExpenseDao {
    // Query de inserir despesa no banco de dados
    @Insert
    suspend fun insertExpense(expense: Expense)

    // Query de buscar todas as despesas do usuário
    @Query("SELECT * FROM expense WHERE userId = :userId")
    suspend fun getExpensesByUserId(userId: Int): List<Expense>

    // Query de deletar uma despesa
    @Delete
    suspend fun deleteExpense(expense: Expense)
}

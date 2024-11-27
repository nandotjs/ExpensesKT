package com.example.listofexpenses.repository

import com.example.listofexpenses.data.dao.ExpenseDao
import com.example.listofexpenses.data.model.Expense

class ExpenseRepository(private val expenseDao: ExpenseDao) {
    // Adicionar despesa ao banco de dados
    suspend fun addExpense(expense: Expense) {
        expenseDao.insertExpense(expense)
    }

    // Obter todas as despesas do usuario banco de dados
    suspend fun getExpensesByUserId(userId: Int): List<Expense> {
        return expenseDao.getExpensesByUserId(userId)
    }

    // Deletar despesa do banco de dados
    suspend fun deleteExpense(expense: Expense) {
        expenseDao.deleteExpense(expense)
    }
}

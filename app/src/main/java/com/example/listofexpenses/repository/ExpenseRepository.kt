package com.example.listofexpenses.repository

import com.example.listofexpenses.data.dao.ExpenseDao
import com.example.listofexpenses.data.model.Expense

class ExpenseRepository(private val expenseDao: ExpenseDao) {
    suspend fun addExpense(expense: Expense) {
        expenseDao.insertExpense(expense)
    }

    suspend fun getExpensesByUserId(userId: Int): List<Expense> {
        return expenseDao.getExpensesByUserId(userId)
    }

    suspend fun deleteExpense(expense: Expense) {
        expenseDao.deleteExpense(expense)
    }
}

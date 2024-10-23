package com.example.listofexpenses.repository

import com.example.listofexpenses.data.dao.ExpenseDao
import com.example.listofexpenses.data.model.Expense

class ExpenseRepository(private val expenseDao: ExpenseDao) {
    suspend fun addExpense(expense: Expense) {
        expenseDao.insertExpense(expense)
    }

    suspend fun getExpenses(): List<Expense> {
        return expenseDao.getAllExpenses()
    }
}

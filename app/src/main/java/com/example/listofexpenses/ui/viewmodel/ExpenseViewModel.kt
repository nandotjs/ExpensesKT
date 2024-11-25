package com.example.listofexpenses.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.listofexpenses.data.model.Expense
import com.example.listofexpenses.repository.ExpenseRepository
import kotlinx.coroutines.launch

class ExpenseViewModel(private val expenseRepository: ExpenseRepository) : ViewModel() {
    private var currentUserId: Int = -1

    fun setCurrentUser(userId: Int) {
        currentUserId = userId
    }

    fun addExpense(expense: Expense, onSuccess: () -> Unit) {
        if (currentUserId == -1) return
        val expenseWithUserId = expense.copy(userId = currentUserId)
        viewModelScope.launch {
            expenseRepository.addExpense(expenseWithUserId)
            onSuccess()
        }
    }

    fun getExpenses(onSuccess: (List<Expense>) -> Unit) {
        if (currentUserId == -1) return
        viewModelScope.launch {
            val expenses = expenseRepository.getExpensesByUserId(currentUserId)
            onSuccess(expenses)
        }
    }

    fun deleteExpense(expense: Expense) {
        if (expense.userId != currentUserId) return
        viewModelScope.launch {
            expenseRepository.deleteExpense(expense)
        }
    }
}

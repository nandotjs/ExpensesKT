package com.example.listofexpenses.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.listofexpenses.data.model.Expense
import com.example.listofexpenses.repository.ExpenseRepository
import kotlinx.coroutines.launch

class ExpenseViewModel(private val expenseRepository: ExpenseRepository) : ViewModel() {
    fun addExpense(expense: Expense, onSuccess: () -> Unit) {
        viewModelScope.launch {
            expenseRepository.addExpense(expense)
            onSuccess()
        }
    }

    fun getExpenses(onSuccess: (List<Expense>) -> Unit) {
        viewModelScope.launch {
            val expenses = expenseRepository.getExpenses()
            onSuccess(expenses)
        }
    }

    fun deleteExpense(expense: Expense) {
        viewModelScope.launch {
            expenseRepository.deleteExpense(expense)
        }
    }
}

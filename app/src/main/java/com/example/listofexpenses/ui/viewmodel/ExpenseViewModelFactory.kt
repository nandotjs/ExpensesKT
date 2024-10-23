package com.example.listofexpenses.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.listofexpenses.repository.ExpenseRepository

class ExpenseViewModelFactory(private val expenseRepository: ExpenseRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ExpenseViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ExpenseViewModel(expenseRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

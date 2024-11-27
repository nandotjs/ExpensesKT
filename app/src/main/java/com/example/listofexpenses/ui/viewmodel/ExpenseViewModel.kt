package com.example.listofexpenses.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.listofexpenses.data.model.Expense
import com.example.listofexpenses.repository.ExpenseRepository
import kotlinx.coroutines.launch

class ExpenseViewModel(private val expenseRepository: ExpenseRepository) : ViewModel() {
    private var currentUserId: Int = -1

    // Função para definir o usuário atual
    fun setCurrentUser(userId: Int) {
        currentUserId = userId
    }

    // Função para adicionar um novo despesa
    fun addExpense(expense: Expense, onSuccess: () -> Unit) {
        if (currentUserId == -1) return
        val expenseWithUserId = expense.copy(userId = currentUserId)
        viewModelScope.launch {
            expenseRepository.addExpense(expenseWithUserId)
            onSuccess()
        }
    }

    // Função para obter todas as despesas do usuário atual
    fun getExpenses(onSuccess: (List<Expense>) -> Unit) {
        if (currentUserId == -1) return
        viewModelScope.launch {
            val expenses = expenseRepository.getExpensesByUserId(currentUserId)
            onSuccess(expenses)
        }
    }

    // Função para deletar uma despesa
    fun deleteExpense(expense: Expense) {
        if (expense.userId != currentUserId) return
        viewModelScope.launch {
            expenseRepository.deleteExpense(expense)
        }
    }
}

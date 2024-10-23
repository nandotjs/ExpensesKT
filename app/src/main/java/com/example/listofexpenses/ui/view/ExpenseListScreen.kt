package com.example.listofexpenses.ui.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.navigation.NavController
import com.example.listofexpenses.data.model.Expense
import com.example.listofexpenses.ui.viewmodel.ExpenseViewModel

@Composable
fun ExpenseListScreen(
    navController: NavController,
    expenseViewModel: ExpenseViewModel
) {
    var expenses by remember { mutableStateOf<List<Expense>>(emptyList()) }

    // Function to fetch expenses and update the list
    fun fetchExpenses() {
        expenseViewModel.getExpenses { fetchedExpenses ->
            expenses = fetchedExpenses
        }
    }

    // Initial fetch of expenses
    LaunchedEffect(Unit) {
        fetchExpenses()
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        // Add a title to the screen
        Text(
            text = "Expense List",
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.align(Alignment.CenterHorizontally).padding(bottom = 16.dp)
        )

        LazyColumn(modifier = Modifier.weight(1f)) {
            items(expenses) { expense ->
                Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = "Category: ${expense.category}")
                            Text(text = "Amount: ${expense.amount}")
                            Text(text = "Date: ${expense.date}")
                            Text(text = "Description: ${expense.description}")
                        }
                        IconButton(onClick = { 
                            expenseViewModel.deleteExpense(expense)
                            fetchExpenses() // Update the list after deletion
                        }) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete Expense"
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Add a button to navigate to the Add Expense screen
        Button(
            onClick = { navController.navigate("add_expense") },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Add Expense")
        }
    }
}

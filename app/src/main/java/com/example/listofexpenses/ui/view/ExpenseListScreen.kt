package com.example.listofexpenses.ui.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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

    LaunchedEffect(Unit) {
        expenseViewModel.getExpenses { fetchedExpenses ->
            expenses = fetchedExpenses
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            items(expenses) { expense ->
                Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = "Category: ${expense.category}")
                        Text(text = "Amount: ${expense.amount}")
                        Text(text = "Date: ${expense.date}")
                        Text(text = "Description: ${expense.description}")
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { navController.navigate("add_expense") },
            modifier = Modifier.align(Alignment.CenterHorizontally).padding(16.dp)
        ) {
            Text("Add Expense")
        }
    }
}

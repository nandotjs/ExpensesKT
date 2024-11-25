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
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun ExpenseListScreen(
    navController: NavController,
    expenseViewModel: ExpenseViewModel
) {
    var expenses by remember { mutableStateOf<List<Expense>>(emptyList()) }
    var dollarRate by remember { mutableStateOf(0.0) }

    // Function to fetch expenses and update the list
    fun fetchExpenses() {
        expenseViewModel.getExpenses { fetchedExpenses ->
            expenses = fetchedExpenses
        }
    }

    // Function to fetch the dollar rate
    suspend fun fetchDollarRate() {
        withContext(Dispatchers.IO) {
            try {
                val client = OkHttpClient()
                val request = Request.Builder()
                    .url("https://economia.awesomeapi.com.br/json/last/USD-BRL")
                    .build()

                client.newCall(request).execute().use { response ->
                    if (response.isSuccessful) {
                        val jsonResponse = JSONObject(response.body?.string() ?: "")
                        val usdBrl = jsonResponse.getJSONObject("USDBRL")
                        dollarRate = usdBrl.getString("bid").toDouble()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Initial fetch of expenses and dollar rate
    LaunchedEffect(Unit) {
        fetchExpenses()
        fetchDollarRate()
    }

    // Calculate total expenses
    val totalAmount = expenses.sumOf { it.amount }
    val totalInDollars = if (dollarRate > 0) totalAmount / dollarRate else 0.0

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        // Add a title to the screen
        Text(
            text = "Expense List",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.align(Alignment.CenterHorizontally).padding(bottom = 8.dp)
        )

        // Display total expenses
        Text(
            text = "Total: $totalAmount BRL / ${"%.2f".format(totalInDollars)} USD",
            style = MaterialTheme.typography.bodyMedium,
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

        Row(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Add a button to navigate to the Add Expense screen
            Button(
                onClick = { navController.navigate("add_expense") }
            ) {
                Text("Add Expense")
            }

            // Add a button to navigate back to the Login screen
            Button(
                onClick = { navController.navigate("login") }
            ) {
                Text("Logout")
            }
        }
    }
}

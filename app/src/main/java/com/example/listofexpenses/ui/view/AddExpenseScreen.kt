package com.example.listofexpenses.ui.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.navigation.NavController
import com.example.listofexpenses.data.model.Expense
import com.example.listofexpenses.ui.viewmodel.ExpenseViewModel

@Composable
fun AddExpenseScreen(
    navController: NavController,
    expenseViewModel: ExpenseViewModel
) {
    var amount by remember { mutableStateOf(TextFieldValue()) }
    var category by remember { mutableStateOf(TextFieldValue()) }
    var date by remember { mutableStateOf(TextFieldValue()) }
    var description by remember { mutableStateOf(TextFieldValue()) }
    var errorMessage by remember { mutableStateOf("") }
    var hasError by remember { mutableStateOf(false) }

    // Validação dos campos
    fun validateFields(): Boolean {
        val errors = mutableListOf<String>()
        
        if (amount.text.isEmpty()) {
            errors.add("amount")
        } else {
            try {
                amount.text.toDouble()
            } catch (e: NumberFormatException) {
                errors.add("amount")
            }
        }

        if (category.text.isEmpty()) {
            errors.add("category")
        }

        if (date.text.isEmpty()) {
            errors.add("date")
        } else if (!date.text.matches(Regex("\\d{2}/\\d{2}/\\d{4}"))) {
            errors.add("date")
        }

        errorMessage = when {
            errors.contains("amount") && amount.text.isEmpty() -> "Amount is required"
            errors.contains("amount") -> "Please enter a valid amount"
            errors.contains("category") -> "Category is required"
            errors.contains("date") && date.text.isEmpty() -> "Date is required"
            errors.contains("date") -> "Please enter a valid date (DD/MM/YYYY)"
            else -> ""
        }

        hasError = errors.isNotEmpty()
        return !hasError
    }

    // Função para formatar a data automaticamente
    fun formatDate(input: String): String {
        val digitsOnly = input.filter { it.isDigit() }
        return when {
            digitsOnly.length <= 2 -> digitsOnly
            digitsOnly.length <= 4 -> "${digitsOnly.substring(0,2)}/${digitsOnly.substring(2)}"
            else -> "${digitsOnly.substring(0,2)}/${digitsOnly.substring(2,4)}/${digitsOnly.substring(4, minOf(8, digitsOnly.length))}"
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Add Expense",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        TextField(
            value = amount,
            onValueChange = { 
                amount = it
                if (hasError) validateFields()
            },
            label = { Text("Amount", style = MaterialTheme.typography.bodyLarge) },
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp),
            textStyle = MaterialTheme.typography.bodyLarge,
            isError = hasError && (amount.text.isEmpty() || try { amount.text.toDouble(); false } catch (e: NumberFormatException) { true })
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = category,
            onValueChange = { 
                category = it
                if (hasError) validateFields()
            },
            label = { Text("Category", style = MaterialTheme.typography.bodyLarge) },
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp),
            textStyle = MaterialTheme.typography.bodyLarge,
            isError = hasError && category.text.isEmpty()
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = date,
            onValueChange = { newValue ->
                // Apenas permite números e /
                val filtered = newValue.text.filter { it.isDigit() || it == '/' }
                // Formata a data automaticamente
                val formatted = formatDate(filtered)
                date = TextFieldValue(formatted)
                if (hasError) validateFields()
            },
            label = { Text("Date (DD/MM/YYYY)", style = MaterialTheme.typography.bodyLarge) },
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp),
            textStyle = MaterialTheme.typography.bodyLarge,
            isError = hasError && (date.text.isEmpty() || !date.text.matches(Regex("\\d{2}/\\d{2}/\\d{4}")))
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description (Optional)", style = MaterialTheme.typography.bodyLarge) },
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp),
            textStyle = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(24.dp))

        if (errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = { navController.navigateUp() },
                modifier = Modifier
                    .weight(1f)
                    .height(56.dp)
                    .padding(end = 8.dp)
            ) {
                Text(
                    "Cancel",
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            Button(
                onClick = {
                    if (validateFields()) {
                        try {
                            val expense = Expense(
                                amount = amount.text.toDouble(),
                                category = category.text,
                                date = date.text,
                                description = description.text
                            )
                            expenseViewModel.addExpense(expense) {
                                navController.navigateUp()
                            }
                        } catch (e: Exception) {
                            errorMessage = "Error adding expense. Please try again."
                            hasError = true
                        }
                    }
                },
                modifier = Modifier
                    .weight(1f)
                    .height(56.dp)
                    .padding(start = 8.dp)
            ) {
                Text(
                    "Save",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

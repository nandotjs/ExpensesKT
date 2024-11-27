package com.example.listofexpenses.ui.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.listofexpenses.ui.viewmodel.UserViewModel
import com.example.listofexpenses.ui.viewmodel.ExpenseViewModel

@Composable
fun LoginScreen(
    navController: NavController,
    userViewModel: UserViewModel,
    expenseViewModel: ExpenseViewModel
) {
    // Variáveis de estado
    var email by remember { mutableStateOf(TextFieldValue()) }
    var password by remember { mutableStateOf(TextFieldValue()) }
    var errorMessage by remember { mutableStateOf("") }
    var hasError by remember { mutableStateOf(false) }

    // Validação dos campos
    fun validateFields(): Boolean {
        val errors = mutableListOf<String>()
        
        // Validação do email
        if (email.text.isEmpty()) {
            errors.add("email_empty")
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email.text).matches()) {
            errors.add("email_invalid")
        }

        // Validação da senha
        if (password.text.isEmpty()) {
            errors.add("password_empty")
        } else if (password.text.length < 6) {
            errors.add("password_short")
        }

        errorMessage = when {
            errors.contains("email_empty") -> "Email is required"
            errors.contains("email_invalid") -> "Please enter a valid email"
            errors.contains("password_empty") -> "Password is required"
            errors.contains("password_short") -> "Password must be at least 6 characters"
            else -> ""
        }

        hasError = errors.isNotEmpty()
        return !hasError
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Login",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        TextField(
            value = email,
            onValueChange = { 
                email = it
                if (hasError) validateFields()
            },
            label = { Text("Email", style = MaterialTheme.typography.bodyLarge) },
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp),
            textStyle = MaterialTheme.typography.bodyLarge,
            isError = hasError && (email.text.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email.text).matches())
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = password,
            onValueChange = { 
                password = it
                if (hasError) validateFields()
            },
            label = { Text("Password", style = MaterialTheme.typography.bodyLarge) },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp),
            textStyle = MaterialTheme.typography.bodyLarge,
            isError = hasError && (password.text.isEmpty() || password.text.length < 6)
        )

        Spacer(modifier = Modifier.height(8.dp))

        if (errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (validateFields()) {
                    userViewModel.login(
                        email.text,
                        password.text,
                        onSuccess = { user ->
                            expenseViewModel.setCurrentUser(user.id)
                            navController.navigate("expense_list")
                        },
                        onError = {
                            errorMessage = "Invalid email or password"
                            hasError = true
                        }
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Text(
                "Login",
                style = MaterialTheme.typography.bodyLarge
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(onClick = { navController.navigate("register") }) {
            Text(
                "Don't have an account? Register",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

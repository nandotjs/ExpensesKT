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
import com.example.listofexpenses.data.model.User
import com.example.listofexpenses.ui.viewmodel.UserViewModel

@Composable
fun RegisterScreen(
    navController: NavController,
    userViewModel: UserViewModel
) {
    // Variáveis de estado
    var name by remember { mutableStateOf(TextFieldValue()) }
    var email by remember { mutableStateOf(TextFieldValue()) }
    var password by remember { mutableStateOf(TextFieldValue()) }
    var confirmPassword by remember { mutableStateOf(TextFieldValue()) }
    var errorMessage by remember { mutableStateOf("") }
    var hasError by remember { mutableStateOf(false) }

    // Validação dos campos
    fun validateFields(): Boolean {
        val errors = mutableListOf<String>()
        
        // Validação do nome
        if (name.text.isEmpty()) {
            errors.add("name_empty")
        } else if (name.text.length < 3) {
            errors.add("name_short")
        }

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

        // Validação da confirmação de senha
        if (confirmPassword.text.isEmpty()) {
            errors.add("confirm_password_empty")
        } else if (password.text != confirmPassword.text) {
            errors.add("passwords_dont_match")
        }

        errorMessage = when {
            errors.contains("name_empty") -> "Name is required"
            errors.contains("name_short") -> "Name must be at least 3 characters"
            errors.contains("email_empty") -> "Email is required"
            errors.contains("email_invalid") -> "Please enter a valid email"
            errors.contains("password_empty") -> "Password is required"
            errors.contains("password_short") -> "Password must be at least 6 characters"
            errors.contains("confirm_password_empty") -> "Please confirm your password"
            errors.contains("passwords_dont_match") -> "Passwords don't match"
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
            text = "Register",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        TextField(
            value = name,
            onValueChange = { 
                name = it
                if (hasError) validateFields()
            },
            label = { Text("Name", style = MaterialTheme.typography.bodyLarge) },
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp),
            textStyle = MaterialTheme.typography.bodyLarge,
            isError = hasError && (name.text.isEmpty() || name.text.length < 3)
        )

        Spacer(modifier = Modifier.height(16.dp))

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

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = confirmPassword,
            onValueChange = { 
                confirmPassword = it
                if (hasError) validateFields()
            },
            label = { Text("Confirm Password", style = MaterialTheme.typography.bodyLarge) },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp),
            textStyle = MaterialTheme.typography.bodyLarge,
            isError = hasError && (confirmPassword.text.isEmpty() || password.text != confirmPassword.text)
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
                    val user = User(name = name.text, email = email.text, password = password.text)
                    userViewModel.register(
                        user,
                        onSuccess = {
                            navController.navigate("login")
                        },
                        onError = {
                            errorMessage = "Registration failed. Please try again."
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
                "Register",
                style = MaterialTheme.typography.bodyLarge
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(onClick = { navController.navigate("login") }) {
            Text(
                "Already have an account? Login",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

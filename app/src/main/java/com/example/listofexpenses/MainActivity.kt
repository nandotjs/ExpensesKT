package com.example.listofexpenses

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.listofexpenses.ui.view.*
import com.example.listofexpenses.ui.viewmodel.ExpenseViewModel
import com.example.listofexpenses.ui.viewmodel.ExpenseViewModelFactory
import com.example.listofexpenses.ui.viewmodel.UserViewModel
import com.example.listofexpenses.ui.viewmodel.UserViewModelFactory
import com.example.listofexpenses.repository.UserRepository
import com.example.listofexpenses.repository.ExpenseRepository
import com.example.listofexpenses.data.dao.UserDao
import com.example.listofexpenses.data.dao.ExpenseDao
import com.example.listofexpenses.data.db.AppDatabase
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF9FA8DA),      // Light indigo
    secondary = Color(0xFF90CAF9),    // Light blue
    tertiary = Color(0xFFA5D6A7),     // Light green
    background = Color(0xFF121212),   // Dark background
    surface = Color(0xFF1E1E1E),      // Slightly lighter dark
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color.White,
    onSurface = Color.White,
)

@Composable
fun AppTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = Typography(),
        content = content
    )
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                val navController = rememberNavController()

                // Initialize UserRepository with UserDao
                val userDao: UserDao = AppDatabase.getDatabase(applicationContext).userDao()
                val userRepository = UserRepository(userDao)

                // Create UserViewModel using UserViewModelFactory
                val userViewModel: UserViewModel = ViewModelProvider(
                    this,
                    UserViewModelFactory(userRepository)
                ).get(UserViewModel::class.java)

                // Initialize ExpenseRepository with ExpenseDao
                val expenseDao: ExpenseDao = AppDatabase.getDatabase(applicationContext).expenseDao()
                val expenseRepository = ExpenseRepository(expenseDao)

                // Create ExpenseViewModel using ExpenseViewModelFactory
                val expenseViewModel: ExpenseViewModel = ViewModelProvider(
                    this,
                    ExpenseViewModelFactory(expenseRepository)
                ).get(ExpenseViewModel::class.java)

                Surface(color = MaterialTheme.colorScheme.background) {
                    NavHost(navController, startDestination = "login") {
                        composable("login") { LoginScreen(navController, userViewModel) }
                        composable("register") { RegisterScreen(navController, userViewModel) }
                        composable("expense_list") { ExpenseListScreen(navController, expenseViewModel) }
                        composable("add_expense") { AddExpenseScreen(navController, expenseViewModel) }
                    }
                }
            }
        }
    }
}

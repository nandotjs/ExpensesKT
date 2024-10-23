package com.example.listofexpenses

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.listofexpenses.ui.view.*
import com.example.listofexpenses.ui.viewmodel.ExpenseViewModel
import com.example.listofexpenses.ui.viewmodel.UserViewModel
import com.example.listofexpenses.ui.viewmodel.UserViewModelFactory
import com.example.listofexpenses.repository.UserRepository
import com.example.listofexpenses.data.dao.UserDao
import com.example.listofexpenses.data.db.AppDatabase

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            // Initialize UserRepository with UserDao
            val userDao: UserDao = AppDatabase.getDatabase(applicationContext).userDao()
            val userRepository = UserRepository(userDao)

            // Create UserViewModel using UserViewModelFactory
            val userViewModel: UserViewModel = ViewModelProvider(
                this,
                UserViewModelFactory(userRepository)
            ).get(UserViewModel::class.java)

            // Assuming ExpenseViewModel is created similarly
            val expenseViewModel: ExpenseViewModel by viewModels()

            NavHost(navController, startDestination = "login") {
                composable("login") { LoginScreen(navController, userViewModel) }
                composable("register") { RegisterScreen(navController, userViewModel) }
                composable("expense_list") { ExpenseListScreen(navController, expenseViewModel) }
                composable("add_expense") { AddExpenseScreen(navController, expenseViewModel) }
            }
        }
    }
}

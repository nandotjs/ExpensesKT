package com.example.listofexpenses.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.listofexpenses.data.model.User
import com.example.listofexpenses.repository.UserRepository
import kotlinx.coroutines.launch

class UserViewModel(private val userRepository: UserRepository) : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    fun login(email: String, password: String, onSuccess: (User) -> Unit, onError: () -> Unit) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val user = userRepository.login(email, password)
                if (user != null) {
                    onSuccess(user)
                } else {
                    onError()
                }
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun register(user: User, onSuccess: () -> Unit, onError: () -> Unit) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                userRepository.register(user)
                onSuccess()
            } catch (e: Exception) {
                onError()
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Function to delete a user
    fun deleteUser(user: User, onSuccess: () -> Unit, onError: () -> Unit) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                userRepository.deleteUser(user)
                onSuccess()
            } catch (e: Exception) {
                onError()
            } finally {
                _isLoading.value = false
            }
        }
    }
}

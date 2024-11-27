package com.example.listofexpenses.repository

import com.example.listofexpenses.data.dao.UserDao
import com.example.listofexpenses.data.model.User

class UserRepository(private val userDao: UserDao) {
    // Função para fazer login
    suspend fun login(email: String, password: String): User? {
        return userDao.login(email, password)
    }

    // Função para registrar um novo usuário
    suspend fun register(user: User) {
        userDao.insertUser(user)
    }

    // Função para deletar um usuário
    suspend fun deleteUser(user: User) {
        userDao.deleteUser(user)
    }
}

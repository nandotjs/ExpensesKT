package com.example.listofexpenses.repository

import com.example.listofexpenses.data.dao.UserDao
import com.example.listofexpenses.data.model.User

class UserRepository(private val userDao: UserDao) {
    suspend fun login(email: String, password: String): User? {
        return userDao.login(email, password)
    }

    suspend fun register(user: User) {
        userDao.insertUser(user)
    }
}

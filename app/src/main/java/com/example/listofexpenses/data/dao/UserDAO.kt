package com.example.listofexpenses.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.listofexpenses.data.model.User

@Dao
interface UserDao {
    @Insert
    suspend fun insertUser(user: User)

    @Query("SELECT * FROM user WHERE email = :email AND password = :password")
    suspend fun login(email: String, password: String): User?
}



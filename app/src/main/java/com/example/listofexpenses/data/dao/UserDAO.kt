package com.example.listofexpenses.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Delete
import com.example.listofexpenses.data.model.User

@Dao
interface UserDao {
    // Query de inserir usuário no banco de dados
    @Insert
    suspend fun insertUser(user: User)

    // Query de buscar usuário pelo email
    @Query("SELECT * FROM user WHERE email = :email AND password = :password")
    suspend fun login(email: String, password: String): User?

    // Query de deletar usuário
    @Delete
    suspend fun deleteUser(user: User)
}



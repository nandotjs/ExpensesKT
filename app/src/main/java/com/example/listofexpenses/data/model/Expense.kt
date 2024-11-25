package com.example.listofexpenses.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "expense")
data class Expense(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: Int,
    val amount: Double,
    val category: String,
    val date: String,
    val description: String,
)


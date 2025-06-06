package com.example.quizapp

import androidx.room.TypeConverter

class Converters {
    @TypeConverter fun fromList(list: List<String>) = list.joinToString("||")
    @TypeConverter
    fun toList(data: String) = if (data.isEmpty()) emptyList() else data.split("||")
}
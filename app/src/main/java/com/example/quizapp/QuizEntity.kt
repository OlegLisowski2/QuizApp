package com.example.quizapp

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "questions")
data class QuizEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val quizName: String,
    val quizTopic: String,
    val questionText: String,
    val answerA: String,
    val answerB: String,
    val answerC: String,
    val answerD: String,
    val correctAnswer: String,
    val timeLimit: Int
)

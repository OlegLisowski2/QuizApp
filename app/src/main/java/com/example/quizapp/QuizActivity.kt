package com.example.quizapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class QuizActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        // Hier kannst du den Quiz-Logik-Code einfügen.
        val topic = intent.getStringExtra("QUIZ_TOPIC")
        // Verwende "topic" entsprechend
    }
}

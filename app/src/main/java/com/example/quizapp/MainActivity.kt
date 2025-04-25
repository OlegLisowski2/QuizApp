package com.example.quizapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.homepage)

        val createButton: Button = findViewById(R.id.btnQuizErstellen)
        createButton.setOnClickListener {
            val intent = Intent(this, QuizCreateActivity::class.java)
            startActivity(intent)
        }
    }
}
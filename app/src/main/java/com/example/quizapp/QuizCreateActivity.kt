package com.example.quizapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class QuizCreateActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.quiz_create_page)

        val createButton: Button = findViewById(R.id.btnStartEditing)
        createButton.setOnClickListener {
            val intent = Intent(this, QuestionCreateActivity::class.java)
            startActivity(intent)
        }
    }
}
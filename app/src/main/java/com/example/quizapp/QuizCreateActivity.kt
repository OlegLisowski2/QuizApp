package com.example.quizapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class QuizCreateActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.quiz_create_page)

        val btn: MaterialButton = findViewById(R.id.btnStartEditing)
        btn.setOnClickListener {
            val intent = Intent(this, QuestionCreateActivity::class.java)
            startActivity(intent)
        }
    }
}
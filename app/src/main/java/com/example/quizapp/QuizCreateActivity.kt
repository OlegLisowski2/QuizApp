package com.example.quizapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity

class QuizCreateActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.quiz_create_page)

        val btn: Button = findViewById(R.id.btnStartEditing)
        btn.setOnClickListener {
            val intent = Intent(this, QuizPagerActivity::class.java)
            startActivity(intent)
        }
    }
}
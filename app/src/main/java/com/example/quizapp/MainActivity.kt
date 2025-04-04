package com.example.quizapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.startseite)

        val createButton: Button = findViewById(R.id.btnQuizErstellen)
        createButton.setOnClickListener {
            val intent = Intent(this, QuestionCreateActivity::class.java)
            startActivity(intent)
        }
    }
}
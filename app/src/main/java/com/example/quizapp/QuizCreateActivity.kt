package com.example.quizapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.ComponentActivity

class QuizCreateActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.quiz_create_page)

        window.decorView.apply {
            // allow your layout to draw under status and nav bars
            systemUiVisibility = (
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    )
        }

        val btn: Button = findViewById(R.id.btnBackToQuiz)
        btn.setOnClickListener {
            val intent = Intent(this, QuizPagerActivity::class.java)
            startActivity(intent)
        }

        val btnHP: Button = findViewById(R.id.btnHomepage)
        btnHP.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }
}
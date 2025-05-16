package com.example.quizapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.ComponentActivity
import com.example.quizapp.QuizQuestionFragment.Companion.KEY_ANSWER_A
import com.example.quizapp.QuizQuestionFragment.Companion.KEY_ANSWER_B
import com.example.quizapp.QuizQuestionFragment.Companion.KEY_QUESTION


class QuizCreateActivity : ComponentActivity() {
    companion object {
        private const val KEY_NAME      = "key_name"
        private const val KEY_TOPIC     = "key_topic"
        private const val KEY_TIME      = "key_time"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.quiz_create_page)

        val quizName: EditText = findViewById(R.id.etQuizName)
        val quizTopic: EditText = findViewById(R.id.etQuizTopic)
        val questionTime: EditText = findViewById(R.id.etQuestionTime)

        val btnBToQ: Button = findViewById(R.id.btnBackToQuiz)
        btnBToQ.setOnClickListener {
            val prefs = getSharedPreferences("quiz_prefs", Context.MODE_PRIVATE)
            val lastIndex = prefs.getInt("last_page_index", 0)  // default to 0

            Intent(this, QuizPagerActivity::class.java)
                .putExtra(QuizPagerActivity.EXTRA_START_INDEX, lastIndex)
                .also { startActivity(it) }
        }

        val btnHP: Button = findViewById(R.id.btnHomepage)
        btnHP.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val questionNumber: EditText = findViewById(R.id.etQuestionNumber)
        val btnEQ: Button = findViewById(R.id.btnEditQuestion)


        btnEQ.setOnClickListener {
            val numText = questionNumber.text.toString().trim()
            val target  = numText.toIntOrNull()
            // assume QuizPagerActivity.QUESTION_INDEX extra key
            if (target == null || target < 1) {
                questionNumber.error = "Enter a valid question number"
            } else {
                val intent = Intent(this, QuizPagerActivity::class.java)
                intent.putExtra(QuizPagerActivity.EXTRA_START_INDEX, target - 1)
                startActivity(intent)
            }
        }

    }

    getKey(KEY_QUESTION)

    fun saveQuizData() {
        prefs.edit()
            .putString(getKey(KEY_QUESTION), etQuestion.text.toString())
            .putString(getKey(KEY_ANSWER_A),  etAnswerA.text.toString())
            .putString(getKey(KEY_ANSWER_B),  etAnswerB.text.toString())
            .apply()
    }
}



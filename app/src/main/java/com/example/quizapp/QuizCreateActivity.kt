package com.example.quizapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.ComponentActivity

class QuizCreateActivity : ComponentActivity() {

    companion object {
        private const val PREFS_NAME = "quiz_prefs"
        private const val KEY_LAST_INDEX = "last_page_index"
        private const val KEY_QUIZ_NAME = "quiz_name"
        private const val KEY_QUIZ_TOPIC = "quiz_topic"
        private const val KEY_QUESTION_TIME = "question_time"
    }

    private lateinit var questionNumber: EditText
    private lateinit var quizName: EditText
    private lateinit var quizTopic: EditText
    private lateinit var timeLimit: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.quiz_create_page)

        questionNumber = findViewById(R.id.etQuestionNumber)
        quizName       = findViewById(R.id.etQuizName)
        quizTopic      = findViewById(R.id.etQuizTopic)
        timeLimit      = findViewById(R.id.etQuestionTime)

        loadPreferences()

        val btnBToQ: Button = findViewById(R.id.btnBackToQuiz)
        btnBToQ.setOnClickListener {
            savePreferences()
            val prefs = getSharedPreferences("quiz_prefs", Context.MODE_PRIVATE)
            val lastIndex = prefs.getInt("last_page_index", 0)  // default to 0

            Intent(this, QuizPagerActivity::class.java)
                .putExtra(QuizPagerActivity.EXTRA_START_INDEX, lastIndex)
                .also { startActivity(it) }
        }

        val btnHP: Button = findViewById(R.id.btnHomepage)
        btnHP.setOnClickListener {
            savePreferences()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val btnEQ: Button = findViewById(R.id.btnEditQuestion)
        btnEQ.setOnClickListener {
            savePreferences()
            val numText = questionNumber.text.toString().trim()
            val target = numText.toIntOrNull()
            // assume QuizPagerActivity.QUESTION_INDEX extra key
            if (target == null || target < 1) {
                questionNumber.error = "Enter a valid question number"
            } else {
                val intent = Intent(this, QuizPagerActivity::class.java)
                intent.putExtra(QuizPagerActivity.EXTRA_START_INDEX, target - 1)
                startActivity(intent)
            }
        }

        val btnSQ: Button = findViewById(R.id.btnSaveQuiz)
        btnSQ.setOnClickListener {

            val quizName = quizName.text.toString()
            val quizTopic = quizTopic.text.toString()
            val questionText = questionTextEditText.text.toString()
            val answerA = etAnswerA.text.toString()
            val answerB = etAnswerB.text.toString()
            val answerC = etAnswerC.text.toString()
            val answerD = etAnswerD.text.toString()
            val correctAnswer = correctAnswer.text.toString()
            val timeLimit = timeLimit.text.toString().toIntOrNull() ?: 0

            val question = QuizEntity(
                quizName = quizName,
                quizTopic = quizTopic,
                questionText = questionText,
                answerA = answerA,
                answerB = answerB,
                answerC = answerC,
                answerD = answerD,
                correctAnswer = correctAnswer,
                timeLimit = timeLimit
            )

            // Insert into database
            lifecycleScope.launch(Dispatchers.IO) {
                val db = QuizDatabase.getDatabase(applicationContext)
                db.questionDao().insertQuestion(question)
            }
        }
    }

    private fun savePreferences() {
        val prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        with(prefs.edit()) {
            putString(KEY_QUIZ_NAME, quizName.text.toString().trim())
            putString(KEY_QUIZ_TOPIC, quizTopic.text.toString().trim())
            putString(KEY_QUESTION_TIME, timeLimit.text.toString().trim())
            // last_index if needed elsewhere
            // putInt(KEY_LAST_INDEX, ...)
            apply()
        }
    }

    private fun loadPreferences() {
        val prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        quizName.setText(prefs.getString(KEY_QUIZ_NAME, ""))
        quizTopic.setText(prefs.getString(KEY_QUIZ_TOPIC, ""))
        timeLimit.setText(prefs.getString(KEY_QUESTION_TIME, ""))
        // Optionally load last index into questionNumber
        val last = prefs.getInt(KEY_LAST_INDEX, 0)
        if (last >= 0) questionNumber.setText((last + 1).toString())
    }
}
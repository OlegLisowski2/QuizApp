package com.example.quizapp

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.quizapp.QuizQuestionFragment.Companion.KEY_ANSWER_A
import com.example.quizapp.QuizQuestionFragment.Companion.KEY_ANSWER_B
import com.example.quizapp.QuizQuestionFragment.Companion.KEY_ANSWER_C
import com.example.quizapp.QuizQuestionFragment.Companion.KEY_ANSWER_D
import com.example.quizapp.QuizQuestionFragment.Companion.KEY_CB_A
import com.example.quizapp.QuizQuestionFragment.Companion.KEY_CB_B
import com.example.quizapp.QuizQuestionFragment.Companion.KEY_CB_C
import com.example.quizapp.QuizQuestionFragment.Companion.KEY_CB_D
import com.example.quizapp.QuizQuestionFragment.Companion.KEY_QUESTION

class QuizPagerActivity : AppCompatActivity() {
    companion object {
        private const val PREFS_NAME = "quiz_input_prefs"
        private const val KEY_QUESTION_COUNT = "key_question_count"
        private const val DEFAULT_QUESTION_COUNT = 1
        private const val MAX_QUESTIONS = 40
    }

    lateinit var viewPager: ViewPager2
    private lateinit var pagerAdapter: QuizPagerAdapter
    private lateinit var prefs: SharedPreferences
    var questionCount: Int = DEFAULT_QUESTION_COUNT
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_pager)

        // Load persisted question count
        prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        loadQuestionCount()



        viewPager = findViewById(R.id.viewPager)
        pagerAdapter = QuizPagerAdapter(this, questionCount)
        viewPager.adapter = pagerAdapter

    }
    /** Fügt eine neue Frage-Seite hinzu, falls unter dem Limit */
    fun addPage() {
        if (questionCount < MAX_QUESTIONS) {
            questionCount++
            // persist the new count
            prefs.edit().putInt(KEY_QUESTION_COUNT, questionCount).apply()
            // update adapter
            pagerAdapter.questionCount = questionCount
            pagerAdapter.notifyItemInserted(questionCount - 1)
        }
    }

    override fun onResume() {
        super.onResume()
        // if questionCount changed (e.g. after returning), refresh adapter
        val persisted = prefs.getInt(KEY_QUESTION_COUNT, DEFAULT_QUESTION_COUNT)
        if (persisted != questionCount) {
            questionCount = persisted
            pagerAdapter.questionCount = questionCount
            pagerAdapter.notifyDataSetChanged()
        }
    }

    /** Löscht Seite und verschiebt nachfolgende Fragen */
    fun deletePage(delIndex: Int) {
        if (questionCount <= 1) return
        // Verschiebe alle Daten von i+1 nach i
        val editor = prefs.edit()
        for (i in delIndex until questionCount) {
            moveQuestionData(i + 1, i, editor)
        }
        // Entferne letzte Frage
        clearQuestionData(questionCount, editor)
        questionCount--
        editor.putInt(KEY_QUESTION_COUNT, questionCount)
        editor.apply()

        // Update Adapter
        pagerAdapter.questionCount = questionCount
        pagerAdapter.notifyItemRemoved(delIndex - 1)
        // Navigiere
        viewPager.post {
            val newPos = (delIndex - 2).coerceAtLeast(0)
            viewPager.currentItem = newPos
        }
    }

    // Kopiert Daten von oldIdx -> newIdx
    private fun moveQuestionData(oldIdx: Int, newIdx: Int, editor: SharedPreferences.Editor) {
        val keys = listOf(
            KEY_QUESTION, KEY_ANSWER_A, KEY_ANSWER_B, KEY_ANSWER_C, KEY_ANSWER_D,
            KEY_CB_A, KEY_CB_B, KEY_CB_C, KEY_CB_D
        )
        for (base in keys) {
            val oldKey = "${base}_$oldIdx"
            val newKey = "${base}_$newIdx"
            if (prefs.contains(oldKey)) {
                when (val v = prefs.all[oldKey]) {
                    is String -> editor.putString(newKey, v)
                    is Boolean -> editor.putBoolean(newKey, v)
                }
            } else {
                editor.remove(newKey)
            }
        }
    }

    private fun loadQuestionCount() {
        questionCount = prefs.getInt(KEY_QUESTION_COUNT, DEFAULT_QUESTION_COUNT)
    }

    // Löscht alle Daten für idx
    private fun clearQuestionData(idx: Int, editor: SharedPreferences.Editor) {
        val keys = listOf(
            KEY_QUESTION, KEY_ANSWER_A, KEY_ANSWER_B, KEY_ANSWER_C, KEY_ANSWER_D,
            KEY_CB_A, KEY_CB_B, KEY_CB_C, KEY_CB_D
        )
        for (base in keys) {
            editor.remove("${base}_$idx")
        }
    }

}
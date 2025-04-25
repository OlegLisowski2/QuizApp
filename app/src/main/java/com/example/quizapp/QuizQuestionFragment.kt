package com.example.quizapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2

class QuizQuestionFragment : Fragment(R.layout.questions_answers_create) {
    companion object {
        private const val ARG_INDEX       = "arg_index"
        private const val PREFS_NAME      = "quiz_input_prefs"
        private const val MAX_QUESTIONS   = 40

        // Base keys; real key = base + "_" + index
        internal const val KEY_QUESTION    = "key_question"
        internal const val KEY_ANSWER_A    = "key_answer_a"
        internal const val KEY_ANSWER_B    = "key_answer_b"
        internal const val KEY_ANSWER_C    = "key_answer_c"
        internal const val KEY_ANSWER_D    = "key_answer_d"
        internal const val KEY_CB_A        = "key_cb_a"
        internal const val KEY_CB_B        = "key_cb_b"
        internal const val KEY_CB_C        = "key_cb_c"
        internal const val KEY_CB_D        = "key_cb_d"

        fun newInstance(index: Int): QuizQuestionFragment = QuizQuestionFragment().apply {
            arguments = Bundle().apply { putInt(ARG_INDEX, index) }
        }
    }

    private val index: Int by lazy { requireArguments().getInt(ARG_INDEX) }
    private lateinit var prefs: SharedPreferences

    private lateinit var etQuestion: EditText
    private lateinit var etAnswerA:  EditText
    private lateinit var etAnswerB:  EditText
    private lateinit var etAnswerC:  EditText
    private lateinit var etAnswerD:  EditText

    private lateinit var cbAnswerA:  CheckBox
    private lateinit var cbAnswerB:  CheckBox
    private lateinit var cbAnswerC:  CheckBox
    private lateinit var cbAnswerD:  CheckBox

    private lateinit var btnMainCreatePage: Button
    private lateinit var btnPrevQuestion: Button
    private lateinit var btnNextQuestion: Button
    private lateinit var btnDelete: Button

    private lateinit var tvCounter: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // First bind views, then initialize preferences and load data
        bindViews(view)
        prefs = requireContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        loadSavedInput()

        // set the counter text: "Frage X von Y"
        val total = (requireActivity() as QuizPagerActivity).questionCount
        tvCounter.text = getString(R.string.question_counter, index, total)

        btnMainCreatePage.setOnClickListener {
            val intent = Intent(requireContext(), QuizCreateActivity::class.java)
            startActivity(intent)
        }

        btnPrevQuestion.setOnClickListener {
            val pager = requireActivity()
                .findViewById<ViewPager2>(R.id.viewPager)
            pager.currentItem = (index - 2).coerceAtLeast(0)
        }

        btnNextQuestion.setOnClickListener {
            val activity = requireActivity() as QuizPagerActivity
            if (index == activity.questionCount && activity.questionCount < MAX_QUESTIONS) {
                activity.addPage()
            }
            activity.viewPager.currentItem = index
        }

        // Delete current question
        btnDelete.setOnClickListener {
            (requireActivity() as QuizPagerActivity).deletePage(index)
        }

    }

    override fun onPause() {
        super.onPause()
        saveInput()
    }

    private fun bindViews(v: View) {
        etQuestion = v.findViewById(R.id.etQuestion)
        etAnswerA  = v.findViewById(R.id.etAnswerA)
        etAnswerB  = v.findViewById(R.id.etAnswerB)
        etAnswerC  = v.findViewById(R.id.etAnswerC)
        etAnswerD  = v.findViewById(R.id.etAnswerD)

        cbAnswerA  = v.findViewById(R.id.cbAnswerA)
        cbAnswerB  = v.findViewById(R.id.cbAnswerB)
        cbAnswerC  = v.findViewById(R.id.cbAnswerC)
        cbAnswerD  = v.findViewById(R.id.cbAnswerD)

        btnMainCreatePage = v.findViewById(R.id.btnMainCreatePage)
        btnPrevQuestion = v.findViewById(R.id.btnPrevQuestion)
        btnNextQuestion = v.findViewById(R.id.btnNextQuestion)
        btnDelete = v.findViewById(R.id.btnDeleteQuestion)

        tvCounter  = v.findViewById(R.id.tvQuestionCounter)
    }

    private fun getKey(base: String): String = "${base}_$index"

    private fun saveInput() {
        prefs.edit()
            .putString(getKey(KEY_QUESTION), etQuestion.text.toString())
            .putString(getKey(KEY_ANSWER_A),  etAnswerA.text.toString())
            .putString(getKey(KEY_ANSWER_B),  etAnswerB.text.toString())
            .putString(getKey(KEY_ANSWER_C),  etAnswerC.text.toString())
            .putString(getKey(KEY_ANSWER_D),  etAnswerD.text.toString())
            .putBoolean(getKey(KEY_CB_A), cbAnswerA.isChecked)
            .putBoolean(getKey(KEY_CB_B), cbAnswerB.isChecked)
            .putBoolean(getKey(KEY_CB_C), cbAnswerC.isChecked)
            .putBoolean(getKey(KEY_CB_D), cbAnswerD.isChecked)
            .apply()
    }

    private fun loadSavedInput() {
        etQuestion.setText(prefs.getString(getKey(KEY_QUESTION), ""))
        etAnswerA .setText(prefs.getString(getKey(KEY_ANSWER_A),  ""))
        etAnswerB .setText(prefs.getString(getKey(KEY_ANSWER_B),  ""))
        etAnswerC .setText(prefs.getString(getKey(KEY_ANSWER_C),  ""))
        etAnswerD .setText(prefs.getString(getKey(KEY_ANSWER_D),  ""))

        cbAnswerA.isChecked = prefs.getBoolean(getKey(KEY_CB_A), false)
        cbAnswerB.isChecked = prefs.getBoolean(getKey(KEY_CB_B), false)
        cbAnswerC.isChecked = prefs.getBoolean(getKey(KEY_CB_C), false)
        cbAnswerD.isChecked = prefs.getBoolean(getKey(KEY_CB_D), false)
    }
}

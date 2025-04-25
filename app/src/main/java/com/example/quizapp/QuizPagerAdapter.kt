package com.example.quizapp

import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class QuizPagerAdapter(fa: FragmentActivity, var questionCount: Int)
    : FragmentStateAdapter(fa) {

    override fun getItemCount(): Int = questionCount

    override fun createFragment(position: Int) =
        QuizQuestionFragment.newInstance(position + 1)
}

package com.example.quizapp

import androidx.room.*

@Dao
interface QuizDao {

    @Transaction
    @Insert fun insertQuiz(q: QuizEntity): Long

    @Insert fun insertQuestions(qs: List<QuizEntity>)

    // Convenience method
    @Transaction
    fun saveFullQuiz(quiz: QuizEntity, questions: List<QuizEntity>) {
        val id = insertQuiz(quiz)
        questions.forEach { it.quizOwnerId = id }
        insertQuestions(questions)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuestion(question: QuizEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuestions(questions: List<QuizEntity>)

    @Query("SELECT * FROM questions")
    suspend fun getAllQuestions(): List<QuizEntity>

    @Query("SELECT * FROM questions WHERE id = :id")
    suspend fun getQuestionById(id: Int): QuizEntity?

    @Update
    suspend fun updateQuestion(question: QuizEntity)

    @Delete
    suspend fun deleteQuestion(question: QuizEntity)
}

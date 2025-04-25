package com.example.quizapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context)
    : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME     = "quiz.db"
        private const val DATABASE_VERSION  = 1
        private const val TABLE_QUESTIONS   = "questions"
        private const val COL_ID            = "id"
        private const val COL_QUESTION      = "question"
        private const val COL_ANSWER        = "answer"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = """
            CREATE TABLE $TABLE_QUESTIONS (
                $COL_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COL_QUESTION TEXT NOT NULL,
                $COL_ANSWER   TEXT NOT NULL
            )
        """.trimIndent()
        db.execSQL(createTable)  // executes the SQL to create the table :contentReference[oaicite:5]{index=5}
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_QUESTIONS")
        onCreate(db)            // simple upgrade strategy: drop & recreate :contentReference[oaicite:6]{index=6}
    }

    /** Inserts a question–answer pair and returns the new row’s ID */
    fun addQuestion(question: String, answer: String): Long {
        val db     = writableDatabase
        val values = ContentValues().apply {
            put(COL_QUESTION, question)
            put(COL_ANSWER, answer)
        }
        return db.insert(TABLE_QUESTIONS, null, values)  // inserts and returns row ID :contentReference[oaicite:7]{index=7}
    }

    /** Reads all stored questions as a list of (question, answer) pairs */
    fun getAllQuestions(): List<Pair<String, String>> {
        val list   = mutableListOf<Pair<String, String>>()
        val db     = readableDatabase
        val cursor = db.rawQuery(
            "SELECT $COL_QUESTION, $COL_ANSWER FROM $TABLE_QUESTIONS", null
        )
        if (cursor.moveToFirst()) {
            do {
                val q = cursor.getString(0)
                val a = cursor.getString(1)
                list.add(q to a)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return list
    }
}

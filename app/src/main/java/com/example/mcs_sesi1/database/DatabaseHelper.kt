package com.example.mcs_sesi1.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, "user_db", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = """
            CREATE TABLE users (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                email TEXT UNIQUE NOT NULL,
                username TEXT UNIQUE NOT NULL,
                password TEXT NOT NULL
            );
        """.trimIndent()
        db.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS users")
        onCreate(db)
    }

    fun createAccount(email: String, username: String, password: String): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("email", email)
            put("username", username)
            put("password", password)
        }

        val result = db.insert("users", null, values)
        db.close()

        return result != -1L
    }

    fun deleteAccount(username: String): Boolean {
        val db = writableDatabase
        val rowsDeleted = db.delete("users", "username = ?", arrayOf(username))
        db.close()

        return rowsDeleted > 0
    }

    fun validateAccount(username: String, password: String): Boolean {
        val db = readableDatabase
        val query = "SELECT * FROM users WHERE username = ? AND password = ?"
        val cursor: Cursor = db.rawQuery(query, arrayOf(username, password))

        val isValid = cursor.count > 0
        cursor.close()
        db.close()
        return isValid
    }

    fun findEmail(email: String): Boolean {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM users WHERE email = ?", arrayOf(email))
        val exists = cursor.count > 0
        cursor.close()
        db.close()
        return exists
    }

    fun updatePassword(email: String, newPassword: String): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("password", newPassword)
        }
        val rowsUpdated = db.update("users", values, "email = ?", arrayOf(email))
        db.close()
        return rowsUpdated > 0
    }

}

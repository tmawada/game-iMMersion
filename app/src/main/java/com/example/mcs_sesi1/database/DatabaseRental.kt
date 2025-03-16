package com.example.mcs_sesi1.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.mcs_sesi1.models.RentalRoom

class DatabaseRental(context: Context) : SQLiteOpenHelper(context, "rental_db", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = """
            CREATE TABLE rooms (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                roomId TEXT UNIQUE NOT NULL,
                console TEXT NOT NULL,
                price INTEGER NOT NULL,
                status INTEGER NOT NULL
            );
        """.trimIndent()
        db.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS rooms")
        onCreate(db)
    }

    fun addRoom(room: RentalRoom): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("roomId", room.roomId)
            put("console", room.consoleType)
            put("price", room.rentalPrice)
            put("status", if (room.status) 1 else 0)
        }

        val result = db.insertWithOnConflict("rooms", null, values, SQLiteDatabase.CONFLICT_IGNORE)
        db.close()
        return result != -1L
    }

    fun getAllRooms(): List<RentalRoom> {
        val db = readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM rooms", null)

        val roomList = mutableListOf<RentalRoom>()
        if (cursor.moveToFirst()) {
            do {
                val room = RentalRoom(
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getInt(3),
                    cursor.getInt(4) == 1
                )
                roomList.add(room)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return roomList
    }

    fun clearRooms() {
        val db = writableDatabase
        db.execSQL("DELETE FROM rooms")
        db.close()
    }
}

package com.example.crm.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ClientsDbHelper(context: Context) :
    SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            """
            CREATE TABLE $TABLE_CLIENTS(
                $COL_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COL_NAME TEXT NOT NULL,
                $COL_EMAIL TEXT NOT NULL,
                $COL_PHONE TEXT NOT NULL
            );
            """.trimIndent()
        )
        db.execSQL("CREATE INDEX idx_clients_name ON $TABLE_CLIENTS($COL_NAME);")
        db.execSQL("CREATE INDEX idx_clients_email ON $TABLE_CLIENTS($COL_EMAIL);")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_CLIENTS")
        onCreate(db)
    }

    companion object {
        const val DB_NAME = "crm.db"
        const val DB_VERSION = 1

        const val TABLE_CLIENTS = "clients"
        const val COL_ID = "id"
        const val COL_NAME = "name"
        const val COL_EMAIL = "email"
        const val COL_PHONE = "phone"
    }
}

package com.example.crm.data

import android.content.ContentValues
import android.content.Context

class ClientRepository(context: Context) {

    private val helper = ClientsDbHelper(context)

    fun insert(client: Client): Long {
        val db = helper.writableDatabase
        val cv = ContentValues().apply {
            put(ClientsDbHelper.COL_NAME, client.name)
            put(ClientsDbHelper.COL_EMAIL, client.email)
            put(ClientsDbHelper.COL_PHONE, client.phone)
        }
        val id = db.insert(ClientsDbHelper.TABLE_CLIENTS, null, cv)
        db.close()
        return id
    }

    fun update(client: Client): Int {
        requireNotNull(client.id)
        val db = helper.writableDatabase
        val cv = ContentValues().apply {
            put(ClientsDbHelper.COL_NAME, client.name)
            put(ClientsDbHelper.COL_EMAIL, client.email)
            put(ClientsDbHelper.COL_PHONE, client.phone)
        }
        val rows = db.update(
            ClientsDbHelper.TABLE_CLIENTS,
            cv,
            "${ClientsDbHelper.COL_ID}=?",
            arrayOf(client.id.toString())
        )
        db.close()
        return rows
    }

    fun delete(id: Long): Int {
        val db = helper.writableDatabase
        val rows = db.delete(
            ClientsDbHelper.TABLE_CLIENTS,
            "${ClientsDbHelper.COL_ID}=?",
            arrayOf(id.toString())
        )
        db.close()
        return rows
    }

    fun getAll(): MutableList<Client> {
        val db = helper.readableDatabase
        val list = mutableListOf<Client>()
        val c = db.query(
            ClientsDbHelper.TABLE_CLIENTS,
            arrayOf(
                ClientsDbHelper.COL_ID,
                ClientsDbHelper.COL_NAME,
                ClientsDbHelper.COL_EMAIL,
                ClientsDbHelper.COL_PHONE
            ),
            null, null, null, null,
            "${ClientsDbHelper.COL_NAME} COLLATE NOCASE ASC"
        )
        c.use {
            while (it.moveToNext()) {
                list.add(
                    Client(
                        id = it.getLong(0),
                        name = it.getString(1),
                        email = it.getString(2),
                        phone = it.getString(3)
                    )
                )
            }
        }
        db.close()
        return list
    }

    fun search(query: String): MutableList<Client> {
        val db = helper.readableDatabase
        val list = mutableListOf<Client>()
        val like = "%${query.trim()}%"
        val c = db.query(
            ClientsDbHelper.TABLE_CLIENTS,
            arrayOf(
                ClientsDbHelper.COL_ID,
                ClientsDbHelper.COL_NAME,
                ClientsDbHelper.COL_EMAIL,
                ClientsDbHelper.COL_PHONE
            ),
            "${ClientsDbHelper.COL_NAME} LIKE ? OR ${ClientsDbHelper.COL_EMAIL} LIKE ?",
            arrayOf(like, like),
            null, null,
            "${ClientsDbHelper.COL_NAME} COLLATE NOCASE ASC"
        )
        c.use {
            while (it.moveToNext()) {
                list.add(
                    Client(
                        id = it.getLong(0),
                        name = it.getString(1),
                        email = it.getString(2),
                        phone = it.getString(3)
                    )
                )
            }
        }
        db.close()
        return list
    }

    fun count(): Int {
        val db = helper.readableDatabase
        val c = db.rawQuery("SELECT COUNT(*) FROM ${ClientsDbHelper.TABLE_CLIENTS}", null)
        var count = 0
        c.use { if (it.moveToFirst()) count = it.getInt(0) }
        db.close()
        return count
    }
}

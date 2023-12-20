package com.example.uas_waroengujang.util

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.uas_waroengujang.model.WaroengDatabase

val DB_NAME = "newwaroengdb"

fun buildDb(context: Context): WaroengDatabase {
    val db = Room.databaseBuilder(
        context,
        WaroengDatabase::class.java,
        DB_NAME
    )
        .addMigrations(MIGRATION_1_2)
        .build()

    return db
}

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            "ALTER TABLE orders ADD COLUMN id INTEGER NOT NULL DEFAULT 0"
        )
        database.execSQL(
            "ALTER TABLE orders ADD COLUMN nomor_table STRING"
        )
        database.execSQL(
            "ALTER TABLE orders ADD COLUMN total_harga INTEGER NOT NULL DEFAULT 0"
        )
    }
}
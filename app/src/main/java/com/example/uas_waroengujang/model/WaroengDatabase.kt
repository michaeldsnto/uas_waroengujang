package com.example.uas_waroengujang.model
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Waitress::class, Menu::class, Cart::class, Orders::class], version = 1, exportSchema = false)
abstract class WaroengDatabase : RoomDatabase() {

    abstract fun waroengDao(): WaroengDao

    companion object {
        @Volatile
        private var instance: WaroengDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context): WaroengDatabase {
            return instance ?: synchronized(LOCK) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): WaroengDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                WaroengDatabase::class.java,
                "waroeng_database"
            ).build()
        }
    }
}

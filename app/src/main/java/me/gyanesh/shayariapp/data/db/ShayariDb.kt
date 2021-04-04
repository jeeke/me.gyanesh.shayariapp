package me.gyanesh.shayariapp.data.db

import android.content.Context
import androidx.databinding.adapters.Converters
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import me.gyanesh.shayariapp.data.model.Category
import me.gyanesh.shayariapp.data.model.Shayari

@Database(
    entities = [Shayari::class, Category::class],
    version = 4,
    exportSchema = false
)
abstract class ShayariDb : RoomDatabase() {

    companion object {
        fun create(context: Context): ShayariDb {
            val databaseBuilder = Room.databaseBuilder(context, ShayariDb::class.java, "shayari.db")
            return databaseBuilder.fallbackToDestructiveMigration().build()
        }
    }

    abstract fun shayariDao(): ShayariDao
    abstract fun categoryDao(): CategoryDao
}
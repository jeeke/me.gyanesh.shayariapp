package me.gyanesh.shayariapp.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import me.gyanesh.shayariapp.data.model.Category

@Dao
interface CategoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(cats: List<Category>)

    @Query("SELECT * FROM Category")
    fun getCategories(): LiveData<List<Category>>

    @Query("DELETE FROM Category")
    fun clear()

}
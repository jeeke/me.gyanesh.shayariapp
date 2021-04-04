package me.gyanesh.shayariapp.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import me.gyanesh.shayariapp.data.PreferenceProvider
import me.gyanesh.shayariapp.data.model.Shayari

@Dao
interface ShayariDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(shayaris: List<Shayari>)

    @Query("SELECT * FROM Shayari")
    fun shayaris(): LiveData<List<Shayari>>

    @Query("SELECT * FROM Shayari WHERE category = :category")
    fun getShayariByCategory(category: String): LiveData<List<Shayari>>

    @Query("SELECT * FROM Shayari WHERE roomId IN(:ids)")
    fun getSavedShayaris(ids: List<String>): LiveData<List<Shayari>>

    @Query("SELECT COUNT(roomId) FROM Shayari WHERE category = :category")
    fun getShayariCountCategory(category: String): Int

    @Query("SELECT DISTINCT category FROM SHAYARI")
    fun getCategories(): LiveData<List<String>>

    @Query("DELETE FROM SHAYARI")
    fun clear()

}
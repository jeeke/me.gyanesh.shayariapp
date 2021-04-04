package me.gyanesh.shayariapp.data

import androidx.lifecycle.LiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.GlobalScope
import me.gyanesh.shayariapp.data.db.ShayariDb
import me.gyanesh.shayariapp.data.model.Shayari
import me.gyanesh.shayariapp.util.io

class FirebaseDataProvider(private val db: ShayariDb) {

    fun fetchAllShayaris() {
        if (RemoteConfigProvider.shouldFetchShayaris) {
            FirebaseDatabase.getInstance().getReference("/shayaris")
                .addListenerForSingleValueEvent(
                    object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            val shayaris = snapshot.children.mapNotNull {
                                it.getValue(Shayari::class.java)
                            }.toList()
                            GlobalScope.io {
                                db.shayariDao().clear()
                                db.shayariDao().insert(shayaris)
                                RemoteConfigProvider.onLatestVersionFetched()
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {
                            TODO("Not yet implemented")
                        }

                    }
                )
        }
    }

    fun getAllSavedShayaris(): LiveData<List<Shayari>> {
        return db.shayariDao().getSavedShayaris(PreferenceProvider.getAllSavedShayaris())
    }

    fun getAllShayaris(): LiveData<List<Shayari>> {
        return db.shayariDao().shayaris()
    }

    fun getShayariByCategory(category: String): LiveData<List<Shayari>> {
        return db.shayariDao().getShayariByCategory(category)
    }

    fun getShayariCountCategory(category: String): Int {
        return db.shayariDao().getShayariCountCategory(category)
    }

    fun getAllCategories(): LiveData<List<String>> {
        return db.shayariDao().getCategories()
    }

}
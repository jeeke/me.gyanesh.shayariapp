package me.gyanesh.shayariapp.data

import androidx.lifecycle.LiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.GlobalScope
import me.gyanesh.shayariapp.data.db.ShayariDb
import me.gyanesh.shayariapp.data.model.Category
import me.gyanesh.shayariapp.data.model.Shayari
import me.gyanesh.shayariapp.util.io
import me.gyanesh.shayariapp.util.ioThenMain

class FirebaseDataProvider(private val db: ShayariDb) {

    fun fetchAllShayaris(onCompleteListener: () -> Unit = {}) {
        FirebaseDatabase.getInstance().reference
            .addListenerForSingleValueEvent(
                object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {

                        val shayaris = snapshot.child("shayaris").children.mapNotNull {
                            it.getValue(Shayari::class.java)?.apply {
                                category = category.toLowerCase()
                            }
                        }.toList()

                        val cats = snapshot.child("categories").children.mapNotNull {
                            it.getValue(Category::class.java)
                        }.toList()

                        GlobalScope.ioThenMain(
                            {
                                db.shayariDao().clear()
                                db.shayariDao().insert(shayaris)

                                db.categoryDao().clear()
                                db.categoryDao().insert(cats)

                                RemoteConfigProvider.onLatestVersionFetched()
                            },
                            {
                                onCompleteListener()
                            }
                        )
                    }

                    override fun onCancelled(error: DatabaseError) {}

                }
            )
    }

    fun getAllSavedShayaris(): LiveData<List<Shayari>> {
        return db.shayariDao().getSavedShayaris(PreferenceProvider.getAllSavedShayaris())
    }

    fun getShayariByCategory(category: String): LiveData<List<Shayari>> {
        return db.shayariDao().getShayariByCategory(category)
    }

    fun getShayariCountCategory(category: String): Int {
        return db.shayariDao().getShayariCountCategory(category)
    }

    fun getAllCategories(): LiveData<List<Category>> {
        return db.categoryDao().getCategories()
    }

}
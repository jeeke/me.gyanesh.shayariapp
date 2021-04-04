package me.gyanesh.shayariapp.data.model

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*
import kotlin.collections.ArrayList

@Entity
@Keep
data class Shayari(
    @PrimaryKey(autoGenerate = true) var roomId: Int? = null,
    var id: String = UUID.randomUUID().toString(),
    var category: String = "",
    var content: String = ""
) {
    companion object {

    }
}
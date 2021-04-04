package me.gyanesh.shayariapp.data.model

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import me.gyanesh.shayariapp.ui.ChooseLanguageActivity
import java.io.Serializable

@Keep
@Entity
data class Category(
    @PrimaryKey(autoGenerate = true) var roomId: Int? = null,
    var id: String? = null,
    var title: String? = null,
    var title_hi: String? = null,
    var icon: String? = null
) : Serializable {
    fun getLangTitle() : String? {
        return if(ChooseLanguageActivity.lang == "hi") title_hi else title
    }
}
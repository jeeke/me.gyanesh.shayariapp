package me.gyanesh.shayariapp.data.model

import androidx.annotation.Keep
import java.io.Serializable
import kotlin.random.Random

@Keep
data class Category(
    var id: Int = Random.nextInt(),
    var category_name: String? = null,
    var category_icon: String? = null
) : Serializable
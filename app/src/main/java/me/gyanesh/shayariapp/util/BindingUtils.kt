package me.gyanesh.shayariapp.util

import android.graphics.drawable.ColorDrawable
import android.text.method.LinkMovementMethod
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import me.gyanesh.shayariapp.R
import me.gyanesh.shayariapp.ShayariApp

@BindingAdapter("app:image_url")
fun loadImage(view: ImageView, url: String?) {
    val d = ColorDrawable(ShayariApp.getInstance().resources.getColor(R.color.color_divider))
    try {
        Glide.with(view)
            .load(url)
            .placeholder(d)
            .into(view)
    } catch (e: Exception) {
        view.setImageDrawable(d)
    }
}

@BindingAdapter("app:avatar")
fun loadAvatar(view: ImageView, url: String?) {
    try {
        Glide.with(view)
            .load(url)
            .placeholder(R.drawable.ic_person_placeholder)
            .error(R.drawable.ic_person_placeholder)
            .circleCrop()
            .into(view)
    } catch (e: Exception) {
        view.setImageResource(R.drawable.ic_person_placeholder)
    }
}

@BindingAdapter("app:badge_count")
fun setBadgeCount(view: TextView, c: Int?) {
    val count = c ?: 0
    if (count > 0) {
        view.show()
        view.text = if (count > 9) "9+" else count.toString()
    } else view.gone()
}

//@BindingAdapter("app:badge_count")
//fun setBadgeCount(imageView: ImageView, c: Int?) {
//    val count = c ?: 0
//    val animatable = imageView.drawable as Animatable
//    if (count > 0) {
//        AnimatedVectorDrawableCompat.registerAnimationCallback(
//            imageView.drawable,
//            object : Animatable2Compat.AnimationCallback() {
//                override fun onAnimationEnd(drawable: Drawable) {
//                    super.onAnimationEnd(drawable)
//                    animatable.start()
//                }
//            })
//        animatable.start()
//    } else animatable.stop()
//}

@BindingAdapter("app:visibility")
fun setVisibility(view: View, v: Boolean?) {
    view.show(v == true)
}
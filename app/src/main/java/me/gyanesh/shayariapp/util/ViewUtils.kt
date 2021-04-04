package me.gyanesh.shayariapp.util

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.*
import android.graphics.drawable.GradientDrawable
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getColor
import androidx.core.content.FileProvider
import androidx.core.widget.ImageViewCompat
import androidx.fragment.app.Fragment
import com.github.dhaval2404.imagepicker.ImagePicker
import com.nblik.app.util.dpToPx
import com.nblik.app.util.tag
import es.dmoral.toasty.Toasty
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

val colors = arrayListOf(
    "#ab8fda",
    "#87c1f5",
    "#f89d81",
    "#80d5be",
    "#e993b6",
    "#73b1f4",
    "#fc8370",
    "#fcd277",
    "#b3a5ef",
    "#62ddbd",
    "#F7A48C"
)
private val gradients = arrayListOf(
    intArrayOf(
        Color.parseColor("#ab8fda"),
        Color.parseColor("#ab8fda"),
    ),
    intArrayOf(
        Color.parseColor("#87c1f5"),
        Color.parseColor("#87c1f5"),
    ),
    intArrayOf(
        Color.parseColor("#f89d81"),
        Color.parseColor("#f89d81"),
    ),
    intArrayOf(
        Color.parseColor("#80d5be"),
        Color.parseColor("#80d5be"),
    ),
    intArrayOf(
        Color.parseColor("#e993b6"),
        Color.parseColor("#e993b6"),
    ),
    intArrayOf(
        Color.parseColor("#73b1f4"),
        Color.parseColor("#73b1f4"),
    ),
    intArrayOf(
        Color.parseColor("#fc8370"),
        Color.parseColor("#fc8370"),
    ),
    intArrayOf(
        Color.parseColor("#fcd277"),
        Color.parseColor("#fcd277"),
    ),
    intArrayOf(
        Color.parseColor("#b3a5ef"),
        Color.parseColor("#b3a5ef"),
    ),
    intArrayOf(
        Color.parseColor("#62ddbd"),
        Color.parseColor("#62ddbd"),
    ),
    intArrayOf(
        Color.parseColor("#F7A48C"),
        Color.parseColor("#F7A48C"),
    ),
//            intArrayOf(
//                Color.parseColor("#0396ff"),
//                Color.parseColor("#abdcff")
//            ),
//            intArrayOf(
//                Color.parseColor("#ea5455"),
//                Color.parseColor("#feb692")
//            ),
//            intArrayOf(
//                Color.parseColor("#7367f0"),
//                Color.parseColor("#ce9ffc")
//            ),
//            intArrayOf(
//                Color.parseColor("#F8D800"),
//                Color.parseColor("#FDEB71")
//            ),
//            intArrayOf(
//                Color.parseColor("#32CCBC"),
//                Color.parseColor("#90F7EC")
//            ),
//            intArrayOf(
//                Color.parseColor("#28C76F"),
//                Color.parseColor("#81FBB8")
//            ),
//            intArrayOf(
//                Color.parseColor("#FA742B"),
//                Color.parseColor("#FFE985")
//            ),
)

private val directions = arrayListOf(
//            GradientDrawable.Orientation.TOP_BOTTOM,
    GradientDrawable.Orientation.TL_BR,
    GradientDrawable.Orientation.RIGHT_LEFT,
    GradientDrawable.Orientation.BR_TL,
//            GradientDrawable.Orientation.BOTTOM_TOP,
    GradientDrawable.Orientation.BL_TR,
    GradientDrawable.Orientation.LEFT_RIGHT,
    GradientDrawable.Orientation.TL_BR
)

var seed = 0

fun getRandomColor() = Color.parseColor(colors[seed++ % colors.size])

fun getRandomGradientDrawable(): GradientDrawable {
    val gd = GradientDrawable(
        directions.random(),
        gradients[seed++ % gradients.size]
    )
    gd.cornerRadius = dpToPx(5).toFloat()
    return gd
}

fun View.screenShot(): Bitmap {
    val bitmap = Bitmap.createBitmap(
        width,
        height, Bitmap.Config.ARGB_8888
    )
    val canvas = Canvas(bitmap)
    draw(canvas)
    return bitmap
}

fun Context.storeBitmap(filePath: String, bitmap: Bitmap) {
    try {
        val cachePath = File(cacheDir, "images")
        cachePath.mkdirs() // don't forget to make the directory
        // overwrites this image every time
        val stream = FileOutputStream("$cachePath/${filePath}")
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        stream.close()
    } catch (e: IOException) {
        e.printStackTrace()
    }
}

fun Context.shareImage(filePath: String) {
    val imagePath = File(cacheDir, "images")
    val newFile = File(imagePath, filePath)
    val contentUri = FileProvider.getUriForFile(this, "me.gyanesh.shayariapp.fileprovider", newFile)

    if (contentUri != null) {
        val shareIntent = Intent()
        shareIntent.action = Intent.ACTION_SEND
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION) // temp permission for receiving app to read this file
        shareIntent.setDataAndType(contentUri, contentResolver.getType(contentUri))
        shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri)
        shareIntent.putExtra(
            Intent.EXTRA_TEXT,
            "I found this Shayari on this amazing app ${getPlayStoreLink()}"
        )
        startActivity(Intent.createChooser(shareIntent, "Choose an app"))
    }
}

fun Context?.toastSuccess(message: String?, duration: Int = Toast.LENGTH_SHORT) {
    if (this != null && message != null) {
        Toasty.success(this, message, duration, true).show()
    }
}

fun Context?.toastError(message: String?, duration: Int = Toast.LENGTH_SHORT) {
    if (this != null && message != null) {
        Toasty.error(this, message, duration, true).show()
    }
}

fun Context?.toastInfo(message: String?, duration: Int = Toast.LENGTH_SHORT) {
    if (this != null && message != null) {
        Toasty.info(this, message, duration, true).show()
    }
}

fun Context?.toastWarning(message: String?, duration: Int = Toast.LENGTH_SHORT) {
    if (this != null && message != null) {
        Toasty.info(this, message, duration, true).show()
    }
}

fun Fragment.selectImage(
    squareOnly: Boolean = false
//    cameraOnly: Boolean = false,
//    galleryOnly: Boolean = true
) {
    try {
        val builder = ImagePicker.with(this)
        if (squareOnly) builder.cropSquare() else builder.crop()
//    if (cameraOnly) builder.cameraOnly()
//    else if (galleryOnly) builder.galleryOnly()
        builder.galleryOnly()
            .compress(1000)
            //Final image size will be less than 1 MB(Optional)
//        .maxResultSize(
//            1080,
//            1080
//        )
            .start()
    } catch (e: Exception) {
        Log.e(tag(), "Line No: 82")
    }
}

fun Fragment.selectImage(
    width: Int,
    height: Int
) {
    try {
        val builder = ImagePicker.with(this).crop(width.toFloat(), height.toFloat())
        builder.galleryOnly()
            .compress(1000)
            .start()
    } catch (e: Exception) {
        Log.e(tag(), "Line No: 82")
    }
}

fun Context.getActivity(): Activity? {
    var context = this
    while (context is ContextWrapper) {
        if (context is Activity) {
            return context
        }
        context = context.baseContext
    }
    return null
}

//fun View.snackbar(message: String?) {
//    if (message?.isNotBlank() == true) {
//        Snackbar.make(this, message, Snackbar.LENGTH_SHORT).show()
//    }
//}
//
//fun View.snackbar(message: String?, retry: () -> Unit) {
//    if (message?.isNotBlank() == true) {
//        Snackbar.make(this, message, Snackbar.LENGTH_SHORT)
//            .setAction(
//                "Retry"
//            ) {
//                retry()
//            }.show()
//    }
//}

fun View?.show() {
    this?.let {
        visibility = View.VISIBLE
    }
}

fun View?.gone() {
    this?.let {
        visibility = View.GONE
    }
}

fun View?.hide() {
    this?.let {
        visibility = View.INVISIBLE
    }
}

fun View?.show(show: Boolean) {
    this?.let {
        visibility = if (show) View.VISIBLE else View.GONE
    }
}

//here edittext is the input field to be focused
fun EditText.showKeyBoard() {
    this.requestFocus()
    this.post {
        val keyboard: InputMethodManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        keyboard.showSoftInput(this, 0)
    }
}

fun View.showKeyBoard() {
    this.requestFocus()
    this.post {
        val keyboard: InputMethodManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        keyboard.showSoftInput(this, 0)
    }
}

fun ImageView.setTint(@ColorRes color: Int) {
    val tint = ContextCompat.getColor(context, color)
    ImageViewCompat.setImageTintList(this, ColorStateList.valueOf(tint))
}

fun TextView.setDrawableColor(@ColorRes color: Int) {
    compoundDrawables.filterNotNull().forEach {
        it.colorFilter = PorterDuffColorFilter(getColor(context, color), PorterDuff.Mode.SRC_IN)
    }
}

//here edittext is the focused edittext
fun EditText.hideKeyboard() {
    val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(this.windowToken, 0)
}

fun View.hideKeyboard() {
    val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(this.windowToken, 0)
}

//fun Activity.tourGuideApp(viewsList: List<ViewInfoHolder>) {
//    val config = ShowcaseConfig().apply {
//        delay = 300L
//    }
//
//    val viewsSequence = MaterialShowcaseSequence(this)
//    viewsSequence.setConfig(config)
//
//    viewsList.forEach {
//        val view = this.showcaseViewBuilder(it)
//        viewsSequence.addSequenceItem(view)
//    }
//    viewsSequence.start()
//}
//
//fun Activity.showcaseViewBuilder(viewDetails: ViewInfoHolder): MaterialShowcaseView {
//    return MaterialShowcaseView.Builder(this)
//        .setTarget(viewDetails.view)
//        .setContentText(viewDetails.information)
//        .setDismissText("GOT IT")
//        .setSkipText("SKIP")
//        .setDelay(200)
//        .build()
//}
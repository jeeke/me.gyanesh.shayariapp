package me.gyanesh.shayariapp.ui

import android.app.ProgressDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein

abstract class BaseActivity : AppCompatActivity(), KodeinAware {
    override val kodein by kodein()
    lateinit var progressBar: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        progressBar = ProgressDialog(this)
//        currentLanguage = getCurrentLanguage().language
    }

//    override fun onBackPressed() {
//        val navController = findNavController(R.id.nav_host_fragment)
//        return if (navController.previousBackStackEntry == null) {
//            MaterialDialog.Builder(this)
//                .setAnimation(R.raw.sad)
//                .setMessage("Are you sure you want to exit the app?")
//                .setNegativeButton("Exit") { _: DialogInterface, _: Int ->
//                    finishAndRemoveTask()
//                }
//                .setPositiveButton("Stay") { dialog: DialogInterface, _: Int ->
//                    dialog.dismiss()
//                }
//                .build()
//                .apply {
//                    val params = animationView.layoutParams as RelativeLayout.LayoutParams
//                    params.width = ViewGroup.LayoutParams.WRAP_CONTENT
//                    params.addRule(RelativeLayout.CENTER_HORIZONTAL)
//                    val m = dpToPx(120)
//                    animationView.setPadding(m, m, m, m)
//                }
//                .show()
//        } else super.onBackPressed()
//    }

//    fun changeLanguage(lgCode: String?) {
//        if (lgCode == "hi") setLanguage(lgCode)
//        else setLanguage(Locale.ENGLISH)
//    }
//
//    override fun onAfterLocaleChanged() {
//        super.onAfterLocaleChanged()
//        currentLanguage = getCurrentLanguage().language
//    }

    companion object {
        lateinit var currentLanguage: String
    }
}
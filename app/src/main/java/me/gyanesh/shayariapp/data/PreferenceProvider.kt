package me.gyanesh.shayariapp.data

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import me.gyanesh.shayariapp.ShayariApp


object PreferenceProvider {

    private const val FIRST_TIME = "first_time"
    private const val NIGHT_MODE = "night_mode"
    private const val HAS_RATED = "has_rated"
    private const val LAST_SHAYARI_DATA_VERSION = "shayari_version"
    private const val LAST_RATING_PAYOUT_ID = "last_rating_payout_id"
    private const val LATEST_PAYOUT_ID = "latest_payout_id"
    private const val SAVED_SHAYARI_IDS = "saved_ids"

    fun getAllSavedShayaris(): List<String> {
        val p = preference.getString(SAVED_SHAYARI_IDS, "") ?: ""
        return p.split(":")
    }

    fun addToSavedShayaris(id: String) {
        val p = (preference.getString(SAVED_SHAYARI_IDS, "") ?: "").split(":").toMutableList()
        p.add(id)
        val new = p.filter { it.isNotBlank() }.toSet().toList().joinToString(":")
        preference.edit().putString(SAVED_SHAYARI_IDS, new).apply()
    }

    fun deleteFromSavedShayaris(id: String) {
        val new = (getAllSavedShayaris().filterNot { it == id }).joinToString(":")
        preference.edit().putString(SAVED_SHAYARI_IDS, new).apply()
    }

    private val nightMode: Int
        get() = preference.getInt(
            NIGHT_MODE,
            AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
        )

    fun setCurrentTheme() {
        AppCompatDelegate.setDefaultNightMode(nightMode)
    }

    fun changeAppTheme(theme: String) {
        val nightMode = when (theme) {
            "light" -> AppCompatDelegate.MODE_NIGHT_NO
            "dark" -> AppCompatDelegate.MODE_NIGHT_YES
            else -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
        }
        preference.edit().putInt(NIGHT_MODE, nightMode).apply()
        AppCompatDelegate.setDefaultNightMode(nightMode)
    }

    fun onOnboardingComplete() {
        preference.edit().putBoolean(
            FIRST_TIME,
            false
        ).apply()
    }

    fun isFirstTime(): Boolean = preference.getBoolean(FIRST_TIME, true)
    fun lastShayariVersion(): Int = preference.getInt(LAST_SHAYARI_DATA_VERSION, 0)
    fun setShayariDataVersion(version: Int) {
        preference.edit().putInt(LAST_SHAYARI_DATA_VERSION, version).apply()
    }

    private val preference: SharedPreferences
        get() = ShayariApp.getInstance().getSharedPreferences(
            "shayari-preferences",
            Context.MODE_PRIVATE
        )

    fun canShowRatingPopup(): Boolean {
        val hasRated = preference.getBoolean(HAS_RATED, false)
        val latestPayoutId = preference.getInt(LATEST_PAYOUT_ID, -1)
        val lastRatingPayoutId = preference.getInt(LAST_RATING_PAYOUT_ID, -1)

        return !hasRated && latestPayoutId != -1 && latestPayoutId != lastRatingPayoutId
    }

    fun onRatingPopupDismissed(hasRated: Boolean) {
        val editor = preference.edit()
        editor.putBoolean(HAS_RATED, hasRated)
        editor.putInt(LAST_RATING_PAYOUT_ID, preference.getInt(LATEST_PAYOUT_ID, -1))
        editor.apply()
    }

}
package me.gyanesh.shayariapp.data

import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import me.gyanesh.shayariapp.BuildConfig

object RemoteConfigProvider {
    private const val LAST_SUPPORTED_VERSION = "last_supported_version"
    private const val LATEST_VERSION = "latest_version"
    private const val LATEST_SHAYARI_VERSION = "latest_shayari_version"
    val hasUpdate by lazy {
        BuildConfig.VERSION_CODE < Firebase.remoteConfig.getLong(LATEST_VERSION)
    }
    val hasMandatoryUpdate by lazy {
        BuildConfig.VERSION_CODE < Firebase.remoteConfig.getLong(LAST_SUPPORTED_VERSION)
    }
    val shouldFetchShayaris by lazy {
        val latestVersion = Firebase.remoteConfig.getLong(LATEST_SHAYARI_VERSION)
        return@lazy latestVersion > PreferenceProvider.lastShayariVersion()
    }

    fun onLatestVersionFetched() {
        val latestVersion = Firebase.remoteConfig.getLong(LATEST_SHAYARI_VERSION)
        PreferenceProvider.setShayariDataVersion(latestVersion.toInt())
    }

    init {
        if (BuildConfig.DEBUG) {
            Firebase.remoteConfig.setConfigSettingsAsync(
                remoteConfigSettings {
                    minimumFetchIntervalInSeconds = 3600
                }
            )
        }
        Firebase.remoteConfig.setDefaultsAsync(
            mapOf(
                LAST_SUPPORTED_VERSION to BuildConfig.VERSION_CODE,
                LATEST_VERSION to BuildConfig.VERSION_CODE,
                LATEST_SHAYARI_VERSION to 1,
//                NBLIK_OFFICIAL_ACCOUNT_NAME to ,
//                NBLIK_OFFICIAL_ACCOUNT_USERNAME to ,
//                NBLIK_OFFICIAL_PROFILE_PIC to

            )
        )
        Firebase.remoteConfig.fetchAndActivate()
    }
}
package me.gyanesh.shayariapp.util

import me.gyanesh.shayariapp.BuildConfig


val BASE_URL =
    if (BuildConfig.DEBUG)
        "https://dev-api.nblik.com/api/v1/"
//        "https://dev-nblik-api.herokuapp.com/api/v1/"
    else "https://api.nblik.com/api/v1/"

val WEBSITE_URL =
    if (BuildConfig.DEBUG) "https://dev-nblik-react.herokuapp.com/" else "https://www.nblik.com/"

const val APP_ICON =
    "https://firebasestorage.googleapis.com/v0/b/nblikk.appspot.com/o/nblik_logo.png?alt=media&token=0918b4b7-ac7a-4637-8d6c-a099366d8cb2"

fun getPlayStoreLink(): String {
    val baseUrl = "https://play.google.com/store/apps/details?id=me.gyanesh.shayariapp"
//    NblikApp.currentUser()?.id?.toString(36)?.let {
//        return "$baseUrl&referrer=$it"
//    }
    return baseUrl
}

const val DEFAULT_PROFILE_PIC =
    "https://firebasestorage.googleapis.com/v0/b/nblikk.appspot.com/o/profile.png?alt=media&token=a6cf71c0-d044-4bb8-948f-f8225ce1cbc7"

val ALGOLIA_APP_ID = if (BuildConfig.DEBUG) "QI65G4PKYZ" else "Y12FJ8IEX8"

val ALGOLIA_API_KEY =
    if (BuildConfig.DEBUG) "9b52c2a989471e120c360b18145991ba" else "0a156d102d35f21534a326aa0ec60492"

const val AVERAGE_READING_SPEED = 150


val GOOGLE_CLIENT_ID =
    if (BuildConfig.DEBUG)
        "1018892805469-9pos7as8m56dao395dg91d3c634i1mar.apps.googleusercontent.com"
    else "962951941377-lfg8rnhvbddfefc7rtaqkkafq5bd3sk7.apps.googleusercontent.com"

const val PEXELS_AUTH_TOKEN = "563492ad6f91700001000001cadbf823bcf04cc393a184e98da1673b"
const val PIXABAY_AUTH_TOKEN = "14926136-7e051c0dde9767c4bde697d49"
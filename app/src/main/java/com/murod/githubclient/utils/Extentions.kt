package com.murod.githubclient.utils

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.util.Log
import android.view.View
import com.google.android.material.snackbar.Snackbar

fun View.visible() {
    try {
        this.visibility = View.VISIBLE
    } catch (e: Exception) {
        logError(e.message)
    }
}

fun View.gone() {
    try {
        this.visibility = View.GONE
    } catch (e: Exception) {
        logError(e.message)
    }
}

fun Activity.openBrowser(url: String) {
    try {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    } catch (e: ActivityNotFoundException) {
        logError(e.message)
    }
}

fun Activity.navigateTo(destinationActivity: Class<*>) {
    try {
        val intent = Intent(this, destinationActivity)
        startActivity(intent)
    } catch (e: ActivityNotFoundException) {
        logError(e.message)
    }
}


fun logError(message: Any?) {
    Log.e("Error", message.toString())
}

fun Activity.isInternetAvailable(): Boolean {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
    return connectivityManager?.run {
        val network = activeNetwork ?: return false
        val networkCapabilities = getNetworkCapabilities(network) ?: return false
        return networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    } ?: false
}

fun Activity.showMessage(message: String) {
    val rootView: View = findViewById(android.R.id.content)
    Snackbar.make(rootView, message, Snackbar.LENGTH_SHORT).show()
}



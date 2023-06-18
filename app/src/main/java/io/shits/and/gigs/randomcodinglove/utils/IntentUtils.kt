package io.shits.and.gigs.randomcodinglove.utils

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.view.View
import com.google.android.material.snackbar.Snackbar

object IntentUtils {

    fun openUri(view: View, uri: Uri) {
        runCatching {
            val context = view.context
            val intent = Intent(Intent.ACTION_VIEW, uri)
            context.startActivity(intent)
        }.onFailure { exception ->
            if (exception is ActivityNotFoundException) {
                // Handle ActivityNotFoundException here
                // For example, show an error message or take alternative action
            } else {
                // Handle other exceptions if needed
                if (exception is ActivityNotFoundException) {
                    val snackbar = Snackbar.make(view, "No apps available to share... Guess you are not allowed to see this", Snackbar.LENGTH_LONG)
                    snackbar.show()
                } else {
                    val snackbar = Snackbar.make(view, "Oops, something went wrong while sharing. Better luck next time!", Snackbar.LENGTH_LONG)
                    snackbar.show()
                }
            }
        }
    }

    fun shareText(view: View, text: String) {
        val context = view.context
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_TEXT, text)

        runCatching {
            val packageManager = context.packageManager
            val activities = packageManager.queryIntentActivities(shareIntent, 0)
            if (activities.isNotEmpty()) {
                context.startActivity(Intent.createChooser(shareIntent, "Share via"))
            } else {
                throw ActivityNotFoundException("No apps available to handle the share action")
            }
        }.onFailure { exception ->
            if (exception is ActivityNotFoundException) {
                val snackbar = Snackbar.make(view, "No apps available to share... Guess you'll have to keep it to yourself!", Snackbar.LENGTH_LONG)
                snackbar.show()
            } else {
                val snackbar = Snackbar.make(view, "Oops, something went wrong while sharing. Better luck next time!", Snackbar.LENGTH_LONG)
                snackbar.show()
            }
        }
    }
}

package com.savemykeys.views.utils

import android.content.Context
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar

class AppUtils {
    companion object {

        fun showToastMessage(
            context: Context,
            message: String,
            duration: Int = Toast.LENGTH_SHORT
        ) {
            Toast.makeText(context, message, duration).show()

        }

        fun showToastMessageById(
            context: Context,
            id: Int,
            duration: Int = Toast.LENGTH_SHORT
        ) {
            Toast.makeText(context, context.getString(id), duration).show()

        }

        fun showSnackBarMessage(view: View,
            message: String,
            duration: Int = Snackbar.LENGTH_SHORT
        ) {
            Snackbar.make(view,message, duration).show()

        }

        fun showSnackBarMessageById(
            context: Context,view: View,
            id: Int,
            duration: Int = Snackbar.LENGTH_SHORT
        ) {
            Snackbar.make(view, context.getString(id), duration).show()

        }
    }

}
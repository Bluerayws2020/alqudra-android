package com.blueray.alqudra.helpers

import android.annotation.SuppressLint
import android.app.Activity
import android.util.Log.e
import android.view.View
import android.widget.EditText
import androidx.core.text.isDigitsOnly
import androidx.fragment.app.Fragment
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

object ViewUtils {

    fun View.hide() {
        visibility = View.GONE
    }

    fun View.show() {
        visibility = View.VISIBLE
    }

    fun View.inVisible() {
        visibility = View.INVISIBLE
    }

    fun EditText.isInputEmpty(): Boolean {
        return text.toString().trim().isEmpty()
    }



}
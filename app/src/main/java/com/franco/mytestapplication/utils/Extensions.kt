package com.franco.mytestapplication.utils

import android.app.Activity
import android.util.DisplayMetrics
import androidx.fragment.app.Fragment

/**
 * This stuff works...
 *
 * @author Franco Omar Castillo Bello
 * Created 25/09/21 at 4:55 p.m.
 */


fun Activity.getWindowHeight(): Int {
    val displayMetrics = DisplayMetrics()
    windowManager.defaultDisplay.getMetrics(displayMetrics)
    return displayMetrics.heightPixels
}

fun Fragment.getWindowHeight() = requireActivity().getWindowHeight()

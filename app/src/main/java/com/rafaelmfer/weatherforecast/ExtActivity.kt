package com.rafaelmfer.weatherforecast

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

inline fun <T : ViewBinding> AppCompatActivity.viewBinding(
    crossinline bindingInflater: (LayoutInflater) -> T
) =
    lazy(LazyThreadSafetyMode.NONE) {
        bindingInflater.invoke(layoutInflater)
    }

fun Activity.hideKeyboard() {
    if (currentFocus == null) View(this) else currentFocus?.let { hideKeyboard(it) }
}
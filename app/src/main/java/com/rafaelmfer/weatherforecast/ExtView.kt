package com.rafaelmfer.weatherforecast

import android.os.SystemClock
import android.view.View
import androidx.annotation.ColorRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

@OptIn(DelicateCoroutinesApi::class)
fun View.onSingleClick(
    delayTime: Long = 1000L,
    context: CoroutineContext = Dispatchers.Main,
    handler: suspend CoroutineScope.(v: View) -> Unit
) {
    var lastClickTime = 0L

    setOnClickListener { v ->
        if (SystemClock.elapsedRealtime() - lastClickTime >= delayTime) {
            lastClickTime = SystemClock.elapsedRealtime()

            GlobalScope.launch(context, CoroutineStart.DEFAULT) {
                handler(v)
            }
        }
    }
}

val View.visible: View
    get() {
        visibility = View.VISIBLE
        return this
    }

val View.invisible: View
    get() {
        visibility = View.INVISIBLE
        return this
    }

val View.gone: View
    get() {
        visibility = View.GONE
        return this
    }

fun View.addRippleEffect(@ColorRes colorOfBackground: Int = 0) {
    AppCompatResources.getDrawable(
        context,
        R.drawable.ripple_shape_rect
    )?.also { drawable ->
        val wrappedDrawable = DrawableCompat.wrap(drawable)
        if (colorOfBackground != 0) {
            DrawableCompat.setTint(wrappedDrawable, ContextCompat.getColor(context, colorOfBackground))
        }
        background = wrappedDrawable
    }
}
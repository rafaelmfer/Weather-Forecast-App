package com.rafaelmfer.weatherforecast

import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan
import java.util.Locale

fun String.toSpannableStringBuilder() = SpannableStringBuilder(this)

fun SpannableStringBuilder.sectionTextBold(vararg args: String?): SpannableStringBuilder {
    return this.sectionText(*args) { spannable, startingIndex, endingIndex ->
        spannable.setSpan(StyleSpan(Typeface.BOLD), startingIndex, endingIndex, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
    }
}

private fun SpannableStringBuilder.sectionText(vararg args: String?, apply: (SpannableStringBuilder, Int, Int) -> Unit): SpannableStringBuilder {
    args.forEach { argTextToBold ->
        if (argTextToBold != null) {
            if (argTextToBold.isNotEmpty() && argTextToBold.trim { it <= ' ' } != "") {
                val text = this.toString().lowercase(Locale.ROOT)
                val textToBold = argTextToBold.lowercase(Locale.ROOT)
                val startingIndex = text.indexOf(textToBold)
                val endingIndex = startingIndex + textToBold.length

                if (startingIndex >= 0 && endingIndex >= 0) {
                    apply(this, startingIndex, endingIndex)
                }
            }
        }
    }
    return this
}
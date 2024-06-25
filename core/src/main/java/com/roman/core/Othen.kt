package com.roman.core

import android.app.Activity
import android.content.Context
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.roman.entity.RecommendationsDirection

val recommendations = listOf(
    RecommendationsDirection(1, R.drawable.stambul, "Стамбул"),
    RecommendationsDirection(1, R.drawable.sochi, "Сочи"),
    RecommendationsDirection(1, R.drawable.phuket, "Пхукет"),
)


object MyKeyListener {
    fun setListener(context: Context, view: EditText, listener: () -> Unit): View.OnKeyListener {
        return View.OnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP
            ) {
                val inputMethodManager =
                    context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager

                inputMethodManager.hideSoftInputFromWindow(
                    view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS
                )
                listener()

                return@OnKeyListener true
            }
            false
        }
    }

}
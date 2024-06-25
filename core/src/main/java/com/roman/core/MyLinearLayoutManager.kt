package com.roman.core

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager

class MyLinearLayoutManager(context: Context): LinearLayoutManager(context) {
    override fun canScrollVertically(): Boolean {
        return false
    }
}
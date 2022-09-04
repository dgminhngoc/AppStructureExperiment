package com.example.myapplication.ui

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

sealed class UIString {
    data class StaticString(val value: String): UIString()
    class DynamicString(
        @StringRes val resId: Int,
        vararg val args: Any
    ): UIString()

    @Composable
    fun asString(): String {
        return when(this) {
            is StaticString -> value
            is DynamicString -> stringResource(id = resId, *args)
        }
    }

    fun asString(context: Context): String {
        return when(this) {
            is StaticString -> value
            is DynamicString -> context.getString(resId, *args)
        }
    }
}
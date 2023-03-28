package com.fbk

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.viewbinding.ViewBinding

class BaseActivity {
    inline fun <T : ViewBinding> ViewGroup.viewBinding(
        @LayoutRes layoutRes: Int,
        crossinline bindingInflater: (LayoutInflater, ViewGroup, Boolean) -> T
    ): T {
        return bindingInflater(LayoutInflater.from(context), this, false)
    }
}
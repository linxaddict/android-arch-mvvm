package com.machineinsight_it.androidarch.mvvm.ui.base.binding

import android.databinding.BindingAdapter
import android.support.design.widget.TextInputLayout
import android.view.View

object BindingAdapters {
    @JvmStatic
    @BindingAdapter("visibleIf")
    fun changeViewVisibility(view: View, bool: Boolean) {
        view.visibility = if (bool) View.VISIBLE else View.GONE
    }

    @JvmStatic
    @BindingAdapter("error")
    fun setError(view: TextInputLayout, resId: Int?) {
        if (resId != null) {
            view.error = view.resources.getString(resId)
        } else {
            view.error = null
        }
    }
}
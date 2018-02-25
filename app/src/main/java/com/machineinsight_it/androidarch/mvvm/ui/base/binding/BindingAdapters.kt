package com.machineinsight_it.androidarch.mvvm.ui.base.binding

import android.databinding.BindingAdapter
import android.support.design.widget.TextInputLayout
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.google.common.base.Strings

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

    @JvmStatic
    @BindingAdapter("imageUrl")
    fun imageUrl(view: ImageView, url: String) {
        if (!Strings.isNullOrEmpty(url)) {
            Glide.clear(view)
            Glide.with(view.context)
                    .load(url)
                    .into(view)
        }
    }
}
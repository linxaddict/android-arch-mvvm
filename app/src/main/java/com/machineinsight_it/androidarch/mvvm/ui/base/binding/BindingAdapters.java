package com.machineinsight_it.androidarch.mvvm.ui.base.binding;

import android.databinding.BindingAdapter;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.view.View;

public class BindingAdapters {
    private BindingAdapters() {
    }

    @BindingAdapter("visibleIf")
    public static void changeViewVisibility(@NonNull View view, @NonNull Boolean bool) {
        view.setVisibility(bool ? View.VISIBLE : View.GONE);
    }

    @BindingAdapter("error")
    public static void setError(@NonNull TextInputLayout view, @Nullable Integer resId) {
        if (resId != null) {
            view.setError(view.getResources().getString(resId));
        } else {
            view.setError(null);
        }
    }
}
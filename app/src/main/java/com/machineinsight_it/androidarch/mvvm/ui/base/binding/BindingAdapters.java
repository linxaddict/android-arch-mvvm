package com.machineinsight_it.androidarch.mvvm.ui.base.binding;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.databinding.BindingAdapter;
import android.databinding.InverseBindingListener;
import android.databinding.InverseBindingMethod;
import android.databinding.InverseBindingMethods;
import android.graphics.drawable.Drawable;
import android.support.annotation.ArrayRes;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

/**
 * @author Marcin Przepiórkowski
 * @since 16.09.2016
 */
@InverseBindingMethods({
        @InverseBindingMethod(type = Spinner.class, attribute = "selectedItem")
})
public class BindingAdapters {
    public static final float MIN_ALPHA = 0.55f;
    public static final float MAX_ALPHA = 1.0f;

    private static final int BLUR_RADIUS = 25;

    private BindingAdapters() {
    }

    @BindingAdapter("visibleIf")
    public static void changeViewVisibility(@NonNull View view, @NonNull Boolean bool) {
        view.setVisibility(bool ? View.VISIBLE : View.GONE);
    }

    @BindingAdapter(value = {"enabledIf", "alpha"}, requireAll = false)
    public static void enabledIf(@NonNull View view, @Nullable Boolean bool, Float alpha) {
        if (alpha == null) {
            alpha = MIN_ALPHA;
        }

        if (bool == null || !bool) {
            view.setEnabled(false);
            view.animate().alpha(alpha);
        } else {
            view.animate().alpha(MAX_ALPHA).withEndAction(() -> view.setEnabled(true));
        }
    }

    @BindingAdapter("animateVisibleIf")
    public static void changeViewVisibilityWithAlphaTransition(@NonNull View view, @NonNull Boolean bool) {
        if (bool) {
            view.setVisibility(View.VISIBLE);
            view.animate().alpha(1.0f);
        } else {
            view.animate().alpha(0.0f).withEndAction(() -> view.setVisibility(View.GONE));
        }
    }

    @BindingAdapter("invisibleIf")
    public static void viewInvisibleIf(@NonNull View view, @NonNull Boolean bool) {
        view.setVisibility(bool ? View.INVISIBLE : View.VISIBLE);
    }

    @BindingAdapter("transparentIf")
    public static void changeViewAlpha(@NonNull View view, @NonNull Boolean bool) {
        view.setAlpha(bool ? 0f : 1f);
    }

    @BindingAdapter("fadeOutIf")
    public static void fadeOutIf(@NonNull View view, @NonNull Boolean bool) {
        if (bool) {
            view.animate().alpha(MIN_ALPHA);
        }
    }

    @BindingAdapter("srcColor")
    public static void setSrcColor(@NonNull ImageView view, @ColorRes int color) {
        Drawable drawable = view.getDrawable();
        drawable.mutate();

        DrawableCompat.setTint(drawable, ResourcesCompat.getColor(view.getResources(), color, null));
        view.setImageDrawable(drawable);
    }

    @BindingAdapter("drawableColor")
    public static void setDrawableColor(@NonNull TextView textView, @ColorRes int color) {
        Drawable[] drawables = textView.getCompoundDrawables();
        for (Drawable drawable : drawables) {
            if (drawable != null) {
                drawable.mutate();
                DrawableCompat.setTint(drawable, ResourcesCompat.getColor(textView.getResources(), color, null));
            }
        }
    }

    @BindingAdapter({"alphaCondition", "alphaValue", "enableAnimation"})
    public static void animateAlpha(@NonNull View view, boolean alphaCondition, float alphaValue, boolean enableAnimation) {
        if (alphaCondition) {
            if (enableAnimation) {
                view.animate().alpha(alphaValue);
            } else {
                view.setAlpha(alphaValue);
            }
        } else {
            if (enableAnimation) {
                view.animate().alpha(1.0f);
            } else {
                view.setAlpha(1.0f);
            }
        }
    }

    @BindingAdapter("items")
    public static void spinnerItems(@NonNull Spinner spinner, @Nullable List<String> items) {
        if (items == null || items.isEmpty()) {
            return;
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(spinner.getContext(),
                android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    @BindingAdapter("itemsRes")
    public static void spinnerItems(@NonNull Spinner spinner, @ArrayRes int arrayResId) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(spinner.getContext(),
                arrayResId, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    @BindingAdapter("selectedItemAttrChanged")
    public static void setSelectedItemListener(@NonNull Spinner view, @Nullable InverseBindingListener itemChange) {
        if (itemChange == null) {
            view.setOnItemSelectedListener(null);
        } else {
            view.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    itemChange.onChange();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        }
    }

    @BindingAdapter("reveal")
    public static void reveal(@NonNull View view, boolean condition) {
        if (!view.isAttachedToWindow()) {
            view.setVisibility(condition ? View.VISIBLE : View.INVISIBLE);
            return;
        }

        int cx = view.getMeasuredWidth() / 2;
        int cy = view.getMeasuredHeight() / 2;

        if (condition) {
            int finalRadius = Math.max(view.getWidth(), view.getHeight()) / 2;

            Animator anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, 0, finalRadius);

            view.setVisibility(View.VISIBLE);
            anim.start();
        } else {
            int initialRadius = view.getWidth() / 2;
            Animator anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, initialRadius, 0);

            anim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    view.setVisibility(View.INVISIBLE);
                }
            });

            anim.start();
        }
    }
}
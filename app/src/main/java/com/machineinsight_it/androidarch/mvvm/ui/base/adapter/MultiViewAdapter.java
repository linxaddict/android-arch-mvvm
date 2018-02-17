package com.machineinsight_it.androidarch.mvvm.ui.base.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.databinding.library.baseAdapters.BR;
import com.machineinsight_it.androidarch.mvvm.ui.base.model.RowViewModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Marcin Przepiórkowski
 * @since 18.03.2017
 * @version 1.1
 *
 * This class extends recycler view adapter and provides simple api for registering views that
 * can be used in the recycler. It assumes that the client-programmer uses MVVM architectural
 * pattern along with android data binding library.
 *
 * Usage:
 * <pre><code>
 *      MultiViewAdapter adapter = new MultiViewAdapter.Builder(model.mBills)
 *                .register(R.layout.item_header, HeaderRowViewModel.class)
 *                .register(R.layout.item_dashboard_bill, DashboardBillRowViewModel.class)
 *                .build();
 * </code></pre>
 */
public class MultiViewAdapter extends RecyclerView.Adapter<MultiViewAdapter.BaseViewHolder> {
    public static class Builder {
        protected Map<Integer, ViewHolderCreator> mViewHolderCreators = new HashMap<>();
        protected Map<Class<?>, Integer> mClassToTypeMap = new HashMap<>();
        protected int mViewTypeCounter = 1;
        protected final List<? extends RowViewModel> mModels;

        public Builder(@NonNull List<? extends RowViewModel> models) {
            mModels = checkNotNull(models);
        }

        /**
         * Registers a view model that will be used in this adapter. Note: this version of the
         * register method takes a binding class as input argument in order to use it in a binding
         * callback.
         *
         * @param layout        view layout for the model
         * @param modelCls      class of the model
         * @param bindingCls    binding class that defines what type of typing will be accessible in
         *                      the binding callback
         * @param callback      binding callback that lets setting custom bindings
         * @param <T>           the type (class) of the model
         * @param <B>           the type (class) of the binding
         *
         * @return              builder instance
         */
        public @NonNull <T extends RowViewModel, B extends ViewDataBinding> Builder register(
                @LayoutRes int layout, @NonNull Class<T> modelCls, @NonNull Class<B> bindingCls,
                @Nullable BindingCallback<T, B> callback) {
            if (mClassToTypeMap.containsKey(checkNotNull(modelCls))) {
                throw new IllegalArgumentException("cls already added");
            }

            mViewHolderCreators.put(mViewTypeCounter, (inflater, parent) -> {
                B binding = DataBindingUtil.inflate(inflater, layout, parent, false);

                return new BaseViewHolder<T>(binding.getRoot()) {
                    @Override
                    public void bind(@NonNull T model) {
                        binding.setVariable(BR.model, model);

                        if (callback != null) {
                            callback.onBind(model, binding);
                        }

                        binding.executePendingBindings();
                    }
                };
            });
            mClassToTypeMap.put(modelCls, mViewTypeCounter++);

            return this;
        }

        /**
         * Registers a view model that will be used in this adapter.
         *
         * @param layout        view layout for the model
         * @param modelCls      class of the model
         * @param <T>           the type (class) of the model
         *
         * @return              builder instance
         */
        public @NonNull <T extends RowViewModel> Builder register(@LayoutRes int layout,
                                                                  @NonNull Class<T> modelCls) {
            return register(layout, modelCls, ViewDataBinding.class, null);
        }

        /**
         * Registers a view model that will be used in this adapter.
         *
         * @param layout        view layout for the model
         * @param modelCls      class of the mode
         * @param callback      binding callback that lets setting custom bindings
         * @param <T>           the type (class) of the model
         *
         * @return              builder instance
         */
        public @NonNull <T extends RowViewModel> Builder register(
                @LayoutRes int layout, @NonNull Class<T> modelCls,
                @Nullable BindingCallback<T, ViewDataBinding> callback) {
            return register(layout, modelCls, ViewDataBinding.class, callback);
        }

        /**
         * Creates an instance of MultiViewAdapter.
         *
         * @return  MultiViewAdapter instance
         */
        public @NonNull MultiViewAdapter build() {
            return new MultiViewAdapter(mModels, mViewHolderCreators, mClassToTypeMap);
        }
    }

    public interface BindingCallback<Model extends RowViewModel, Binding> {
        void onBind(@NonNull Model model, @NonNull Binding binding);
    }

    protected interface ViewHolderCreator<T extends RowViewModel> {
        @NonNull BaseViewHolder<T> create(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent);
    }

    protected static abstract class BaseViewHolder<T extends RowViewModel> extends RecyclerView.ViewHolder {
        public BaseViewHolder(View itemView) {
            super(itemView);
        }

        public abstract void bind(final @NonNull T model);
    }

    protected MultiViewAdapter(@NonNull List<? extends RowViewModel> models,
                               @NonNull Map<Integer, ViewHolderCreator> viewHolderCreators,
                               @NonNull Map<Class<?>, Integer> classToTypeMap) {
        mModels = checkNotNull(models);
        mViewHolderCreators = checkNotNull(viewHolderCreators);
        mClassToTypeMap = checkNotNull(classToTypeMap);
    }

    protected final Map<Integer, ViewHolderCreator> mViewHolderCreators;
    protected final Map<Class<?>, Integer> mClassToTypeMap;
    protected final List<? extends RowViewModel> mModels;

    @Override
    public @NonNull BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolderCreator creator = mViewHolderCreators.get(viewType);
        return creator.create(LayoutInflater.from(parent.getContext()), parent);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        //noinspection unchecked
        holder.bind(mModels.get(position));
    }

    @Override
    public int getItemCount() {
        return mModels.size();
    }

    @Override
    public int getItemViewType(int position) {
        RowViewModel model = mModels.get(position);
        Integer type = mClassToTypeMap.get(model.getClass());
        if (type == null) {
            /* check if model is a subclass of a known class */
            Class<?> modelClass = model.getClass();

            for (Map.Entry<Class<?>, Integer> entry : mClassToTypeMap.entrySet()) {
                Class<?> cls = entry.getKey();
                if (cls.isAssignableFrom(modelClass)) {
                    mClassToTypeMap.put(modelClass, type = entry.getValue());
                    break;
                }
            }

            if (type == null) {
                throw new IllegalArgumentException("unknown view model type, did you forget to register it?");
            }
        }

        return type;
    }
}
package com.machineinsight_it.androidarch.mvvm.ui.main

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import com.machineinsight_it.androidarch.mvvm.R
import com.machineinsight_it.androidarch.mvvm.databinding.ActivityMainBinding
import com.machineinsight_it.androidarch.mvvm.ui.base.adapter.MultiViewAdapter
import dagger.android.AndroidInjection
import org.jetbrains.anko.design.snackbar
import javax.inject.Inject


class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val binding by lazy {
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
    }

    private val viewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(MainViewModel::class.java)
    }

    val adapter by lazy {
        MultiViewAdapter.Builder(viewModel.users)
                .register(R.layout.row_user, UserViewModel::class.java)
                .build()
    }

    private fun subscribeForSnackBarEvent() {
        viewModel.showErrorMessage.observe(this, Observer {
            if (it != null) {
                snackbar(binding.root, it)
            }
        })
    }

    private fun subscribeForDataSetClearedEvent() {
        viewModel.dataSetCleared.observe(this, Observer {
            if (it != null) {
                adapter.notifyItemRangeRemoved(it.first, it.second)
            }
        })
    }

    private fun subscribeForDataSetUpdatedEvent() {
        viewModel.dataSetUpdated.observe(this, Observer {
            if (it != null) {
                adapter.notifyDataSetChanged()
            }
        })
    }

    private fun setUpUsersList() {
        val layoutManager = LinearLayoutManager(this)
        val dividerItemDecoration = DividerItemDecoration(this,
                layoutManager.orientation)

        binding.usersList.layoutManager = layoutManager
        binding.usersList.addItemDecoration(dividerItemDecoration)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AndroidInjection.inject(this)

        binding.model = viewModel
        binding.view = this

        setUpUsersList()

        subscribeForSnackBarEvent()
        subscribeForDataSetClearedEvent()
        subscribeForDataSetUpdatedEvent()

        viewModel.fetchUsers()
    }
}

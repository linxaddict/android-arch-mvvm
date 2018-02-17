package com.machineinsight_it.androidarch.mvvm.ui.login

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import com.machineinsight_it.androidarch.mvvm.R
import com.machineinsight_it.androidarch.mvvm.databinding.ActivityLoginBinding
import com.machineinsight_it.androidarch.mvvm.ui.main.MainActivity
import dagger.android.AndroidInjection
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.startActivity
import javax.inject.Inject

class LoginActivity : AppCompatActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val binding by lazy {
        DataBindingUtil.setContentView<ActivityLoginBinding>(this, R.layout.activity_login)
    }

    private val viewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(LoginViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AndroidInjection.inject(this)

        binding.model = viewModel

        viewModel.openMainScreen.observe(this, Observer {
            startActivity<MainActivity>()
        })

        viewModel.showErrorMessage.observe(this, Observer {
            if (it != null) {
                snackbar(binding.root, it)
            }
        })
    }
}
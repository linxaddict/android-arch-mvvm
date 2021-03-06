package com.machineinsight_it.androidarch.mvvm.ui.login

import android.databinding.ObservableField
import com.machineinsight_it.androidarch.mvvm.R
import com.machineinsight_it.androidarch.mvvm.api.service.ArchApi
import com.machineinsight_it.androidarch.mvvm.ui.base.events.NavigationEvent
import com.machineinsight_it.androidarch.mvvm.ui.base.events.TextMessageEvent
import com.machineinsight_it.androidarch.mvvm.ui.base.model.BaseViewModel
import com.machineinsight_it.androidarch.mvvm.validation.EmailValidator
import com.machineinsight_it.androidarch.mvvm.validation.PasswordLengthValidator
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class LoginViewModel @Inject constructor() : BaseViewModel() {
    @Inject
    lateinit var emailValidator: EmailValidator

    @Inject
    lateinit var passwordLengthValidator: PasswordLengthValidator

    @Inject
    lateinit var archApiService: ArchApi

    val email = ObservableField<String>()
    val emailError = ObservableField<Int>()
    val password = ObservableField<String>()
    val passwordError = ObservableField<Int>()

    val loginInProgress = ObservableField<Boolean>()

    val openMainScreen = NavigationEvent()
    val showErrorMessage = TextMessageEvent()

    private fun validateEmail(): Boolean {
        var valid = false

        if (email.get().isNullOrEmpty()) {
            emailError.set(R.string.error_field_required)
        } else if (!emailValidator.isValid(email.get())) {
            emailError.set(R.string.error_invalid_email)
        } else {
            emailError.set(null)
            valid = true
        }

        return valid
    }

    private fun validatePassword(): Boolean {
        var valid = false

        if (password.get().isNullOrEmpty()) {
            passwordError.set(R.string.error_field_required)
        } else if (!passwordLengthValidator.isValid(password.get())) {
            passwordError.set(R.string.error_password_too_short)
        } else {
            passwordError.set(null)
            valid = true
        }

        return valid
    }

    private fun handleLoginError() = showErrorMessage.call(R.string.error_unable_to_login)

    private fun handleLoginSuccessful() = openMainScreen.call()

    private fun disableFetchInProgress() = loginInProgress.set(false)

    private fun enableFetchInProgress() = loginInProgress.set(true)

    fun login() {
        var inputValid = validateEmail()
        inputValid = validatePassword() && inputValid

        if (inputValid) {
            val disposable = archApiService.login(email.get(), password.get())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe { enableFetchInProgress() }
                    .doFinally { disableFetchInProgress() }
                    .subscribe(
                            { handleLoginSuccessful() },
                            { handleLoginError() }
                    )

            registerDisposable(disposable)
        }
    }
}
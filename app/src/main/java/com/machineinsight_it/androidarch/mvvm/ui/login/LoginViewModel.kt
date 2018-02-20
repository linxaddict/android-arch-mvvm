package com.machineinsight_it.androidarch.mvvm.ui.login

import android.databinding.ObservableField
import com.machineinsight_it.androidarch.mvvm.R
import com.machineinsight_it.androidarch.mvvm.api.service.ArchApi
import com.machineinsight_it.androidarch.mvvm.ui.base.events.NavigationEvent
import com.machineinsight_it.androidarch.mvvm.ui.base.events.SnackBarEvent
import com.machineinsight_it.androidarch.mvvm.ui.base.model.BaseViewModel
import com.machineinsight_it.androidarch.mvvm.validation.EmailValidator
import com.machineinsight_it.androidarch.mvvm.validation.PasswordLengthValidator
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

    val openMainScreen = NavigationEvent()
    val showErrorMessage = SnackBarEvent()

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

    fun login() {
        var inputValid = validateEmail()
        inputValid = validatePassword() && inputValid

        if (inputValid) {
            archApiService.login(email.get(), password.get())
                    .subscribe(
                            {
                                openMainScreen.call()
                            },
                            {
                                showErrorMessage.call(R.string.error_unable_to_login)
                            }
                    )
        }
    }
}
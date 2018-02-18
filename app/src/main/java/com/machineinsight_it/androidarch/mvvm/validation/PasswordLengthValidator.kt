package com.machineinsight_it.androidarch.mvvm.validation

class PasswordLengthValidator : Validator {
    private val MIN_LENGTH = 6

    override fun isValid(text: String?) = !text.isNullOrEmpty() && text?.length ?: 0 >= MIN_LENGTH
}
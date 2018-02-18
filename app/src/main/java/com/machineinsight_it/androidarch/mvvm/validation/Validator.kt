package com.machineinsight_it.androidarch.mvvm.validation

interface Validator {
    fun isValid(text: String?): Boolean
}
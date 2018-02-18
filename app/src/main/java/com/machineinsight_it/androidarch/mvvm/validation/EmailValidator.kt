package com.machineinsight_it.androidarch.mvvm.validation

import java.util.regex.Pattern
import javax.inject.Inject

class EmailValidator @Inject constructor() : Validator {
    private val EMAIL_ADDRESS_PATTERN: Pattern = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
    )

    override fun isValid(text: String?) = !text.isNullOrEmpty()
            && EMAIL_ADDRESS_PATTERN.matcher(text).matches()
}
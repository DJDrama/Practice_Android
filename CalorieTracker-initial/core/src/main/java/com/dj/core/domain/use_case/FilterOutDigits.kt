package com.dj.core.domain.use_case

class FilterOutDigits {

    operator fun invoke(
        text: String
    ): String {
        return text.filter { it.isDigit() }
    }
}
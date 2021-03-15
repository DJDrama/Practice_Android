package com.dj.testing

object RegistrationUtils {
    private val existingUsers = listOf("Peter", "Kevin")

    /**
     * input is not valid if
     * ...the username / password is empty,
     * ...the username is already taken
     * ...the confirmed password is not equal to the password
     * ...the password contains less than 2 digits
     */
    fun validateRegistrationInput(
        userName: String,
        password: String,
        confirmedPassword: String
    ): Boolean {
        if (userName.isEmpty() || password.isEmpty())
            return false
        if (userName in existingUsers)
            return false
        if (password != confirmedPassword)
            return false
        if (password.count { it.isDigit() } < 2)
            return false
        return true
    }
}
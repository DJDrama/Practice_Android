package com.dj.testing

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class RegistrationUtilsTest {

    @Test
    fun `empty username returns false`() {
        val result = RegistrationUtils.validateRegistrationInput(
            userName = "",
            password = "123",
            confirmedPassword = "123"
        )
        assertThat(result).isFalse()
    }

    @Test
    fun `valid username and correctly repeated password returns true`() {
        val result = RegistrationUtils.validateRegistrationInput(
            userName = "Tom",
            password = "123",
            confirmedPassword = "123"
        )
        assertThat(result).isTrue()
    }

    @Test
    fun `username already exists returns false`() {
        val result = RegistrationUtils.validateRegistrationInput(
            userName = "Kevin",
            password = "123",
            confirmedPassword = "123"
        )
        assertThat(result).isFalse()
    }

    @Test
    fun `password empty returns false`() {
        val result = RegistrationUtils.validateRegistrationInput(
            userName = "Kevin",
            password = "",
            confirmedPassword = ""
        )
        assertThat(result).isFalse()
    }

    @Test
    fun `password repeated incorrectly returns false`() {
        val result = RegistrationUtils.validateRegistrationInput(
            userName = "Kevin",
            password = "123",
            confirmedPassword = "1234"
        )
        assertThat(result).isFalse()
    }

    @Test
    fun `password contains less than 2 digits returns false`() {
        val result = RegistrationUtils.validateRegistrationInput(
            userName = "Kevin",
            password = "1",
            confirmedPassword = "1"
        )
        assertThat(result).isFalse()
    }
}
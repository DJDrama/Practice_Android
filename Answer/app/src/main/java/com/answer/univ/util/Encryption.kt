package com.answer.univ.util

import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

fun String.md5(): String {
    val md5 = "MD5"
    try {
        // Create MD5 Hash
        val digest = MessageDigest
            .getInstance(md5)
        digest.update(toByteArray())
        val messageDigest = digest.digest()

        // Create Hex String
        val hexString = StringBuilder()
        for (aMessageDigest in messageDigest) {
            var h = Integer.toHexString(0xFF and aMessageDigest.toInt())
            while (h.length < 2) h = "0$h"
            hexString.append(h)
        }
        return hexString.toString()
    } catch (e: NoSuchAlgorithmException) {
        e.printStackTrace()
    }
    return ""
}
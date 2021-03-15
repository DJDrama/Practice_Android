package com.dj.testing

import com.dj.testing.Sample.fib
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class SampleTest{
    @Test
    fun `zero and one returns own value`(){
        var n=0
        var result = Sample.fib(n = n)
        assertThat(result).isEqualTo(n)
        n=1
        result = Sample.fib(n = n)
        assertThat(result).isEqualTo(n)
    }
    @Test
    fun `value of fib has right value`(){
        var n=4
        val result = Sample.fib(n=n)
        assertThat(result).isEqualTo(3)
    }
}


/**
 * Returns the n-th fibonacci number
 * They are defined like this:
 * fib(0) = 0
 * fib(1) = 1
 * fib(n) = fib(n-2) + fib(n-1)
 */
//fun fib(n: Int): Long {
//    if(n == 0 || n == 1) {
//        return n.toLong()
//    }
//    var a = 0L
//    var b = 1L
//    var c = 1L
//    (1..n-2).forEach { i ->
//        c = a + b
//        a = b
//        b = c
//    }
//    return c
//}
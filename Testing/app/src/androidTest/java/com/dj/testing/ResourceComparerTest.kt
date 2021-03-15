package com.dj.testing

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.google.common.truth.Truth.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test

class ResourceComparerTest {
    private lateinit var resourceComparer: ResourceComparer

    @Before // setup "before" other tests run
    fun setup(){
        resourceComparer = ResourceComparer()
    }
    @After
    fun teardown(){
        // clear (ex. close room database, etc)
    }

    @Test
    fun isStringResourceSameAsTheGivenString_returnsTrue(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        val result = resourceComparer.isEqual(context, R.string.app_name, "Testing")
        assertThat(result).isTrue()
    }

    @Test
    fun isStringResourceDifferentAsTheGivenString_returnsTrue(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        val result = resourceComparer.isEqual(context, R.string.app_name, "Random String")
        assertThat(result).isFalse()
    }
}
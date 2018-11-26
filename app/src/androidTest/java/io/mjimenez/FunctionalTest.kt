package io.mjimenez

import android.app.Activity
import android.content.Intent
import androidx.test.rule.ActivityTestRule
import org.junit.Rule
import kotlin.reflect.KClass

abstract class FunctionalTest(activityClass: KClass<out Activity>) {
    @Rule @JvmField val activityRule = ActivityTestRule(activityClass.java, false, false)

    fun startActivity(intent: Intent? = null) {
        activityRule.launchActivity(intent)
    }
}
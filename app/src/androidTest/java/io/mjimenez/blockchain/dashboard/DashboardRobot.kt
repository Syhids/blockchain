package io.mjimenez.blockchain.dashboard

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import io.mjimenez.blockchain.R
import org.hamcrest.CoreMatchers.not

fun onDashboardScreen(func: DashboardRobot.() -> Unit) = DashboardRobot().apply(func)

class DashboardRobot {
    fun assertTitleIs(text: String) {
        onView(withId(R.id.textViewTitle))
            .check(matches(withText(text)))
    }

    fun assertChartIsShown(shown: Boolean) {
        onView(withId(R.id.chart))
            .check(matches(if (shown) isDisplayed() else not(isDisplayed())))
    }

    fun assertSnackbarHasText(text: String) {
        //TODO: SnackbarViewMatcher?
        onView(withText(text)).check(matches(isDisplayed()))
    }

    fun clickSnackbarRetry() {
        onView(withText("RETRY"))
            .perform(click())
    }
}
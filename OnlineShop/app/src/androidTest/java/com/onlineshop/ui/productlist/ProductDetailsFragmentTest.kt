package com.onlineshop.ui.productlist

import android.view.View
import android.view.ViewManager
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.onlineshop.R
import com.onlineshop.ui.MainActivity
import junit.framework.TestCase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.lang.Thread.sleep

@RunWith(AndroidJUnit4ClassRunner::class)
class ProductDetailsFragmentTest : TestCase() {
    @Before
    fun before() {
        ActivityScenario.launch(MainActivity::class.java)
        sleep(1000)
        onView(withId(R.id.products_recycler_view))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<ProductsListAdapter.ProductViewHolder>(
                    1,
                    click()
                )
            )
    }

    @Test
    fun areAllItemsVisible() {
        onView(withId(R.id.button_back)).check(matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.product_image)).check(matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.product_name)).check(matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.product_price)).check(matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.product_rating)).check(matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.product_description)).check(matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.add_to_cart_button)).check(matches(ViewMatchers.isDisplayed()))
    }
}
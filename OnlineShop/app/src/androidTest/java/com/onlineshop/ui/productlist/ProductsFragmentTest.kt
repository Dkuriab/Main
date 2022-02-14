package com.onlineshop.ui.productlist

import android.view.View
import android.widget.Button
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.onlineshop.R
import com.onlineshop.ui.MainActivity
import junit.framework.TestCase
import org.hamcrest.Matcher
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.lang.Thread.sleep

@RunWith(AndroidJUnit4ClassRunner::class)
class ProductsFragmentTest : TestCase() {

    @Before
    fun before() {
        ActivityScenario.launch(MainActivity::class.java)
        sleep(10000)
    }

    @Test
    fun isProductListScrollable() {
        onView(withId(R.id.products_recycler_view))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<ProductsListAdapter.ProductViewHolder>(
                    1,
                    swipeUp()
                )
            )
        sleep(1000)
    }

    @Test
    fun openProductDetailsWorksCorrectly() {

        onView(withId(R.id.products_recycler_view))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<ProductsListAdapter.ProductViewHolder>(
                    1,
                    click()
                )
            )

        sleep(1000)

        onView(withId(R.id.button_back))
            .perform(click())

        sleep(20000)
    }

    @Test
    fun addToCartButtonWorks() {
        onView(withId(R.id.products_recycler_view))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<ProductsListAdapter.ProductViewHolder>(
                    1,
                    object : ViewAction {
                        override fun getConstraints(): Matcher<View>? {
                            return null
                        }

                        override fun getDescription(): String {
                            return ""
                        }

                        override fun perform(uiController: UiController?, view: View?) {
                            val addToCartButton =
                                view?.findViewById<Button>(R.id.add_to_cart_button)
                            addToCartButton?.performClick()

                        }

                    }
                )
            )
    }

    @Test
    fun isSearchBarWorksCorrectly() {
        onView(withId(R.id.search_bar))
            .perform(
                typeText("test text")
            )

        onView(withId(R.id.search_bar))
            .check(matches(isCompletelyDisplayed()))

        sleep(1000)
    }
}
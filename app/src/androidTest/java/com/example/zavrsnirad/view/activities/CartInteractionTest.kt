package com.example.zavrsnirad.view.activities

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.zavrsnirad.R
import com.example.zavrsnirad.model.ProductModel
import org.hamcrest.Matchers.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CartInteractionTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setup() {
        MainActivity.cartList.clear()
    }

    @Test
    fun dodavanje_proizvoda_u_kosaricu_i_provjera_u_kosarici() {
        onView(withText("Računalo LINKS Office O125IW")).perform(click())
        onView(withId(R.id.btnAddToCart)).perform(click())
        onView(withId(R.id.ivCart)).perform(click())
        onView(withText("Računalo LINKS Office O125IW")).check(matches(isDisplayed()))
        onView(withId(R.id.tvTotal)).check(matches(withText("€749")))
    }

    @Test
    fun uklanjanje_proizvoda_iz_kosarice_i_provjera_prazne_kosarice() {
        MainActivity.cartList.add(ProductModel(2, "Laptop ACER Nitro V 15", 1599, "Opis Laptopa", 0))
        onView(withId(R.id.ivCart)).perform(click())
        onView(withText("Laptop ACER Nitro V 15")).perform(click())
        onView(withId(R.id.btnAddToCart)).check(matches(withText("Ukloni iz košarice"))).perform(click())
        onView(withId(R.id.llBottom)).check(matches(not(isDisplayed())))
    }
}
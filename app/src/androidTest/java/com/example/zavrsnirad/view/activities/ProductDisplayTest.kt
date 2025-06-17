package com.example.zavrsnirad.view.activities

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.zavrsnirad.R
import com.example.zavrsnirad.model.ProductModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ProductDisplayTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setup() {
        MainActivity.cartList.clear()
    }

    @Test
    fun glavni_ekran_ispravno_prikazuje_listu_proizvoda() {
        onView(withId(R.id.rvItems)).check(matches(isDisplayed()))
        onView(withText("Računalo LINKS Office O125IW")).check(matches(isDisplayed()))
        onView(withText("€749")).check(matches(isDisplayed()))
    }

    @Test
    fun klik_na_proizvod_otvara_ispravne_detalje() {
        onView(withText("Računalo LINKS Office O125IW")).perform(click())
        onView(withId(R.id.tvName)).check(matches(withText("Naziv: Računalo LINKS Office O125IW")))
        onView(withId(R.id.tvPrice)).check(matches(withText("Cijena: €749")))
        onView(withId(R.id.ivImage)).check(matches(isDisplayed()))
    }

    @Test
    fun ikona_kosarice_otvara_kosaricu_i_prikazuje_sadrzaj() {
        MainActivity.cartList.add(ProductModel(1, "Test Proizvod", 100, "Opis", 0))
        onView(withId(R.id.ivCart)).perform(click())
        onView(withId(R.id.llBottom)).check(matches(isDisplayed()))
        onView(withId(R.id.btnCheckout)).check(matches(isDisplayed()))
    }
}
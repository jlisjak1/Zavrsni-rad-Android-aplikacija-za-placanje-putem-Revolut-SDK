package com.example.zavrsnirad.view.activities

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.zavrsnirad.R
import com.example.zavrsnirad.view.adapter.ItemsAdapter
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PrePaymentFlowTest {

    @get:Rule
    val activityRule = IntentsTestRule(MainActivity::class.java)


    @Test
    fun test_toka_aplikacije_do_poziva_revolut_forme() {
        onView(withId(R.id.rvItems)).perform(
            RecyclerViewActions.actionOnItemAtPosition<ItemsAdapter.ViewHolder>(0, click())
        )
        onView(withId(R.id.btnAddToCart)).perform(click())
        onView(withId(R.id.ivCart)).perform(click())
        onView(withId(R.id.btnCheckout)).perform(click())
        onView(withId(R.id.etName)).perform(typeText("Pero Peric"), closeSoftKeyboard())
        onView(withId(R.id.etEmail)).perform(typeText("pero.peric@test.com"), closeSoftKeyboard())
        onView(withId(R.id.etPhone)).perform(typeText("1234567890"), closeSoftKeyboard())
        onView(withId(R.id.etDeliveryAddress)).perform(typeText("Ulica Lije lihvarova 1"), closeSoftKeyboard())

        onView(withId(R.id.btnConfirm)).perform(click())

        Thread.sleep(10000)
        Intents.intended(hasComponent("com.revolut.cardpayments.cardpayment.CardPaymentActivity"))
    }
}
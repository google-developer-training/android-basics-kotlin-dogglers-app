/*
* Copyright (C) 2021 The Android Open Source Project.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package com.example.dogglers

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@MediumTest
class ButtonTests {
    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity>
        = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun `vertical_list_button_is_displayed`() {
        onView(withId(R.id.vertical_btn)).check(matches(isDisplayed()))
    }

    @Test
    fun `horizontal_list_button_is_displayed`() {
        onView(withId(R.id.horizontal_btn)).check(matches(isDisplayed()))
    }

    @Test
    fun `grid_list_button_is_displayed`() {
        onView(withId(R.id.grid_btn)).check(matches(isDisplayed()))
    }

    @Test
    fun `clicking_vertical_list_button_displays_vertical_list`() {
        onView(withId(R.id.vertical_btn)).perform(click())
        onView(withId(R.id.vertical_recycler_view)).check(matches(isDisplayed()))
    }

    @Test
    fun `clicking_horizontal_list_button_displays_horizontal_list`() {
        onView(withId(R.id.horizontal_btn)).perform(click())
        onView(withId(R.id.horizontal_recycler_view)).check(matches(isDisplayed()))
    }

    @Test
    fun `clicking_grid_list_button_displays_grid_list`() {
        onView(withId(R.id.grid_btn)).perform(click())
        onView(withId(R.id.grid_recycler_view)).check(matches(isDisplayed()))
    }
}

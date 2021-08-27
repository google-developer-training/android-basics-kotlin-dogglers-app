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

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.swipeUp
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.dogglers.BaseTest.DrawableMatcher.hasItemCount
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class VerticalListTests : BaseTest() {

    @get:Rule
    var activityRule: ActivityScenarioRule<VerticalListActivity>
            = ActivityScenarioRule(VerticalListActivity::class.java)

    @Test
    fun `vertical_scroll_content_at_first_position`() {
        checkFirstPosition()
    }

    @Test
    fun `vertical_scroll_content_at_last_position`() {
        onView(withId(R.id.vertical_recycler_view))
                .perform(scrollToPosition<RecyclerView.ViewHolder>(lastPosition))
        onView(withText("Bella")).check(matches(isDisplayed()))
    }

    @Test
    fun `vertical_scrolling`() {
        onView(withId(R.id.vertical_recycler_view))
                .perform(swipeUp())
        onView(withText("Faye")).check(matches(isDisplayed()))
    }

    @Test
    fun `recycler_view_item_count`() {
        onView(withId(R.id.vertical_recycler_view)).check(hasItemCount(6))
    }
}

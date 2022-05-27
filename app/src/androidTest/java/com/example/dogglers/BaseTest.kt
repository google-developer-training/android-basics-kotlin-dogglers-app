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

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.dogglers.BaseTest.DrawableMatcher.withDrawable
import com.example.dogglers.data.DataSource
import org.hamcrest.CoreMatchers
import org.hamcrest.Description
import org.hamcrest.Matcher
import java.lang.IllegalStateException

open class BaseTest {

    val lastPosition = DataSource.dogs.size - 1

    /**
     * Check the content of a card
     *
     * @param name
     * @param age The full age string as it appears on the screen
     * @param hobbies The full hobbies string as it appears on the screen
     * @param imageResource The image resource Id
     */
    private fun hasListItemContent(name: String, age: String, hobbies: String, imageResource: Int) {
        onView(withText(name))
            .check(matches(isDisplayed()))
        onView(withText(age))
            .check(matches(isDisplayed()))
        onView(withText(hobbies))
            .check(matches(isDisplayed()))
        onView(withDrawable(imageResource))
            .check(matches(isDisplayed()))
    }

    /**
     * Check the content of the first card
     */
    fun checkFirstPosition() {
        hasListItemContent("Tzeitel", "7", "sunbathing",
            R.drawable.tzeitel)
    }

    /**
     * Custom matcher to find drawable.
     */
    object DrawableMatcher {

        /**
         * Invokes the [RecyclerViewAssertion] to check the RecyclerView has the correct count
         *
         * @param count The expected number of items in the RecyclerView adapter
         */
        fun hasItemCount(count: Int): ViewAssertion {
            return RecyclerViewAssertion(count)
        }

        fun withDrawable(@DrawableRes resourceId: Int): Matcher<View> {
            return object : BoundedMatcher<View, ImageView>(ImageView::class.java) {
                override fun describeTo(description: Description?) {
                    description!!.appendText("has drawable resource $resourceId")
                }

                override fun matchesSafely(imageView: ImageView): Boolean {
                    return isSameBitmap(imageView, imageView.drawable, resourceId)
                }
            }
        }

        private fun isSameBitmap(item: View, drawable: Drawable?, expectedResId: Int): Boolean {
            val image = item as ImageView
            if (expectedResId < 0) {
                return image.drawable == null
            }
            val expectedDrawable: Drawable? = ContextCompat.getDrawable(item.context, expectedResId)
            if (drawable == null || expectedDrawable == null) {
                return false
            }

            // In the case we are not checking a vector drawable, we can get the bitmap directly
            if (drawable is BitmapDrawable && expectedDrawable is BitmapDrawable) {
                val found = drawable.bitmap
                val expected = expectedDrawable.bitmap
                return found.sameAs(expected)
            }
            // Make tint consistent just in case they differ
            drawable.setTint(android.R.color.black)
            expectedDrawable.setTint(android.R.color.black)
            val bitmap = getBitmap(drawable)
            val expectedBitmap = getBitmap(expectedDrawable)
            return bitmap.sameAs(expectedBitmap)
        }

        /**
         * Convert vector drawable to bitmap
         * @param drawable vector drawable
         */
        private fun getBitmap(drawable: Drawable): Bitmap {
            val bitmap = Bitmap.createBitmap(
                    drawable.intrinsicWidth,
                    drawable.intrinsicHeight,
                    Bitmap.Config.ARGB_8888
            )
            val canvas = Canvas(bitmap)
            drawable.setBounds(0, 0, canvas.width, canvas.height)
            drawable.draw(canvas)
            return bitmap
        }

        /**
         * Custom view assertion to check:
         * The RecyclerView exists
         * The RecyclerView has an adapter
         * The adapter contains the expected number of items
         *
         * @param count The expected number of adapter items
         */
        private class RecyclerViewAssertion(private val count: Int) : ViewAssertion {
            override fun check(view: View?, noViewFoundException: NoMatchingViewException?) {
                if (noViewFoundException != null) {
                    throw noViewFoundException
                }

                if (view !is RecyclerView) {
                    throw IllegalStateException("The view is not a RecyclerView")
                }

                if (view.adapter == null) {
                    throw IllegalStateException("No adapter assigned to RecyclerView")
                }

                // Check item count
                ViewMatchers.assertThat(
                    "RecyclerView item count",
                    view.adapter?.itemCount,
                    CoreMatchers.equalTo(count)
                )
            }
        }
    }
}

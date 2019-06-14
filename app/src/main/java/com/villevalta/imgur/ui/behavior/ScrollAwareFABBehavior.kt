/*
 * Copyright 2015 The Android Open Source Project
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
package com.villevalta.imgur.ui.behavior

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import androidx.core.view.isVisible
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.villevalta.imgur.utils.invisible

class ScrollAwareFABBehavior(context: Context, attrs: AttributeSet) :
  FloatingActionButton.Behavior() {

  override fun onStartNestedScroll(
    coordinatorLayout: CoordinatorLayout,
    child: FloatingActionButton,
    directTargetChild: View,
    target: View,
    nestedScrollAxes: Int,
    @ViewCompat.NestedScrollType type: Int
  ): Boolean {
    return ViewCompat.SCROLL_AXIS_VERTICAL == nestedScrollAxes
        || super.onStartNestedScroll(
      coordinatorLayout,
      child,
      directTargetChild,
      target,
      nestedScrollAxes,
      type
    )
  }

  override fun onNestedScroll(
    coordinatorLayout: CoordinatorLayout,
    child: FloatingActionButton,
    target: View,
    dxConsumed: Int,
    dyConsumed: Int,
    dxUnconsumed: Int,
    dyUnconsumed: Int,
    @ViewCompat.NestedScrollType type: Int
  ) {
    super.onNestedScroll(
      coordinatorLayout,
      child,
      target,
      dxConsumed,
      dyConsumed,
      dxUnconsumed,
      dyUnconsumed,
      type
    )

    if (dyConsumed > 0 && child.isVisible) {
      child.hide(object : FloatingActionButton.OnVisibilityChangedListener() {
        override fun onHidden(fab: FloatingActionButton?) {
          super.onHidden(fab)
          fab?.invisible()
        }
      })
    } else if (dyConsumed < 0 && !child.isVisible) {
      child.show()
    }
  }
}
/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.focus.browser

import android.os.Build
import android.os.CancellationSignal
import android.view.View
import android.view.Window
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.graphics.Insets
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsAnimationControlListenerCompat
import androidx.core.view.WindowInsetsAnimationControllerCompat
import androidx.core.view.WindowInsetsCompat.Type.statusBars
import androidx.core.view.isVisible
import mozilla.components.browser.toolbar.BrowserToolbar
import mozilla.components.ui.widgets.behavior.EngineViewScrollingGesturesBehavior
import kotlin.math.roundToInt

internal class StatusBarScrollController(
    window: Window,
    view: View,
) {
    private val insetsController = WindowCompat.getInsetsController(window, view)
    private var animationController: WindowInsetsAnimationControllerCompat? = null
    private var cancellationSignal: CancellationSignal? = null
    private var controlRequestId = 0
    private var controlRequestPending = false
    private var enabled = false
    private var topInset = 0
    private var toolbarTranslationY = Float.NaN

    fun synchronize(toolbar: BrowserToolbar, topInset: Int) {
        val behavior = (toolbar.layoutParams as? CoordinatorLayout.LayoutParams)?.behavior
        if (!toolbar.isVisible || behavior !is EngineViewScrollingGesturesBehavior) {
            enabled = false
            cancel()
            return
        }

        val stateChanged = !enabled || this.topInset != topInset || toolbarTranslationY != toolbar.translationY
        enabled = true
        this.topInset = topInset
        toolbarTranslationY = toolbar.translationY

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R || topInset <= 0) {
            return
        }

        if (!stateChanged) {
            return
        }

        if (animationController == null && !controlRequestPending) {
            requestControl()
        } else {
            applyTranslation()
        }
    }

    fun finishShown() {
        controlRequestId++
        controlRequestPending = false
        enabled = false
        val signal = cancellationSignal
        cancellationSignal = null
        val controller = animationController
        animationController = null
        if (controller?.isReady == true) {
            controller.finish(true)
        } else {
            signal?.cancel()
        }
    }

    private fun requestControl() {
        val requestId = ++controlRequestId
        val signal = CancellationSignal()
        cancellationSignal = signal
        controlRequestPending = true

        insetsController.controlWindowInsetsAnimation(
            statusBars(),
            -1L,
            null,
            signal,
            object : WindowInsetsAnimationControlListenerCompat {
                override fun onReady(controller: WindowInsetsAnimationControllerCompat, types: Int) {
                    if (requestId != controlRequestId) {
                        if (controller.isReady) {
                            controller.finish(toolbarTranslationY >= 0f)
                        }
                        return
                    }

                    controlRequestPending = false
                    animationController = controller
                    applyTranslation()
                }

                override fun onFinished(controller: WindowInsetsAnimationControllerCompat) {
                    if (requestId == controlRequestId) {
                        controlRequestPending = false
                        cancellationSignal = null
                        animationController = null
                    }
                }

                override fun onCancelled(controller: WindowInsetsAnimationControllerCompat?) {
                    if (requestId == controlRequestId) {
                        controlRequestPending = false
                        cancellationSignal = null
                        animationController = null
                    }
                }
            },
        )
    }

    private fun applyTranslation() {
        val controller = animationController?.takeIf { it.isReady } ?: return
        val shownInsets = controller.shownStateInsets
        val hiddenInsets = controller.hiddenStateInsets
        val statusBarHeight = shownInsets.top - hiddenInsets.top
        if (statusBarHeight <= 0) {
            return
        }

        val visibleFraction =
            ((statusBarHeight + toolbarTranslationY) / statusBarHeight).coerceIn(0f, 1f)
        controller.setInsetsAndAlpha(
            interpolate(hiddenInsets, shownInsets, visibleFraction),
            1f,
            visibleFraction,
        )
    }

    private fun cancel() {
        if (!controlRequestPending && animationController == null) {
            return
        }

        controlRequestId++
        controlRequestPending = false
        cancellationSignal?.cancel()
        cancellationSignal = null
        animationController = null
    }

    private fun interpolate(start: Insets, end: Insets, fraction: Float): Insets =
        Insets.of(
            interpolate(start.left, end.left, fraction),
            interpolate(start.top, end.top, fraction),
            interpolate(start.right, end.right, fraction),
            interpolate(start.bottom, end.bottom, fraction),
        )

    private fun interpolate(start: Int, end: Int, fraction: Float): Int =
        (start + ((end - start) * fraction)).roundToInt()
}

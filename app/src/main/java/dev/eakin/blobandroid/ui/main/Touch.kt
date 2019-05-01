/*
 * Copyright 2019 Greg Eakin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at:
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dev.eakin.blobandroid.ui.main

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.MotionEvent

class Touch(private val collective: BlobCollective, val id: Int) {
    private val paint = Paint()

    init {
        paint.color = Color.RED
        paint.style = Paint.Style.STROKE
        paint.textSize = 30.0f
    }

    var x: Float = 0.0f
    var y: Float = 0.0f
    var p: Float = 0.0f
    private var action: Int = MotionEvent.ACTION_UP
    var number: Int = -1
    var blob: Blob? = null

    val radius: Float
        get() = 100.0f * p

    fun draw(canvas: Canvas) {
        if (action == MotionEvent.ACTION_UP) return

        when {
            blob?.selected == null -> paint.color = Color.BLACK
            blob?.selected === this -> paint.color = Color.GREEN
            else -> paint.color = Color.RED
        }

        // paint.strokeWidth = 1.0f
        // canvas.drawText("$number", x + 100.0f, y + 100.0f, paint);

        paint.strokeWidth = 2.0f
        canvas.drawCircle(x, y, radius, paint)

        val currentBlob = blob ?: return
        canvas.drawLine(x, y, currentBlob.x, currentBlob.y, paint)

//        val aXbX = x - currentBlob.x
//        val aYbY = y - currentBlob.y
//        val distance = aXbX * aXbX + aYbY * aYbY
//        canvas.drawText("$distance", x + 120.0f, y + 120.0f, paint);
    }

    fun update(action: Int, number: Int, x: Float, y: Float, p: Float) {
        this.action = action
        this.number = number
        this.x = x
        this.y = y
        this.p = p

        val closetBlob = collective.findClosest(x, y, radius)
        blob = closetBlob
        closetBlob?.selected = this
    }

    fun zero() {
        action = MotionEvent.ACTION_UP
        val currentBlob = blob
        blob = null
        currentBlob?.selected = null
    }
}
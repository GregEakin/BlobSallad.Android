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

package dev.eakin.blob.ui.main

interface Environment {
    fun collision(curPos: Vector, prePos: Vector): Boolean
    fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int)
    // fun draw(canvas: Int, scaleFactor: Double)
    var w : Float
    var h : Float
}

class EnvironmentImpl(val minX: Float, val minY: Float, override var w: Float, override var h: Float) : Environment {
    init {
        if (w < 0.0f)
            throw Exception("Can't have negative width.")
        if (h < 0.0f)
            throw Exception("Can't have negative height.")
    }

    override fun collision(curPos: Vector, prePos: Vector): Boolean {
        var outOfBounds = false
        if (curPos.x < minX) {
            curPos.x = minX
            outOfBounds = true
        } else if (curPos.x > minX + w) {
            curPos.x = minX + w
            outOfBounds = true
        }

        if (curPos.y < minY) {
            curPos.y = minY
            outOfBounds = true
        } else if (curPos.y > minY + h) {
            curPos.y = minY + h
            outOfBounds = true
        }

        return outOfBounds
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        if (w < 0.0f)
            throw Exception("Can't have negative width.")
        if (h < 0.0f)
            throw Exception("Can't have negative height.")

        this.w = w.toFloat()
        this.h = h.toFloat()
    }

//    fun draw(canvas: Int, scaleFactor: Double) {}
}
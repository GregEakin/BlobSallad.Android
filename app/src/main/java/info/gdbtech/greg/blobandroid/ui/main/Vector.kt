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

package info.gdbtech.greg.blobandroid.ui.main

import java.lang.Math.sqrt

class Vector(var x: Float, var y: Float) {
    val length: Float
            get() = sqrt(x.toDouble() * x + y * y).toFloat()

    fun setValue(that: Vector) {
        x = that.x
        y = that.y
    }

    fun add(x1: Float, y1: Float) {
        x += x1
        y += y1
    }

    fun scale(factor: Float) {
        x *= factor
        y *= factor
    }

    override fun toString(): String = "(X: $x, Y: $y)"

    operator fun plusAssign(that: Vector): Unit {
        x += that.x
        y += that.y
    }

    operator fun minus(that: Vector): Vector = Vector(x - that.x, y - that.y)

    operator fun minusAssign(that: Vector): Unit {
        x -= that.x
        y -= that.y
    }

    // dot product: a * b = ‖a‖ ‖b‖ cos(θ)
    operator fun times(that: Vector): Float {
        return x * that.x + y * that.y
    }
}
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
            get() = sqrt(this.x.toDouble() * this.x + this.y * this.y).toFloat()

    fun set(that: Vector) {
        this.x = that.x
        this.y = that.y
    }

    fun add(x: Float, y: Float) {
        this.x += x
        this.y += y
    }

    fun add(that: Vector) {
        this.x += that.x
        this.y += that.y
    }

    fun sub(that: Vector) {
        this.x -= that.x
        this.y -= that.y
    }

    fun scale(factor: Float) {
        this.x *= factor
        this.y *= factor
    }

    fun dotProd(that: Vector): Float = this.x * that.x + this.y * that.y

    override fun toString(): String = "(X: $x, Y: $y)"

    operator fun minus(that: Vector): Vector = Vector(this.x - that.x, this.y - that.y)
}
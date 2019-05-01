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

class PointMass(cx: Float, cy: Float, val mass: Float) {
    val pos = Vector(cx, cy)
    val prev = Vector(cx, cy)

    val xPos: Float
        get() = pos.x
    val yPos: Float
        get() = pos.y

    val xPrev: Float
        get() = prev.x
    val yPrev: Float
        get() = prev.y

    val velocity: Float
        get() {
            val cXpX = pos.x - prev.x
            val cYpY = pos.y - prev.y
            return cXpX * cXpX + cYpY * cYpY
        }

    var friction: Float = 0.01f

    private val _force = Vector(0.0f, 0.0f)
    var force: Vector
        get() = _force
        set(value) = _force.setValue(value)

    fun addForce(force: Vector) {
        _force += force
    }

    /// https://en.wikipedia.org/wiki/Verlet_integration
    fun move(dt: Float) {
        val dt2 = dt * dt

        val currX = pos.x
        val prevX = prev.x
        val accX = _force.x / mass
        prev.x = currX
        pos.x = (2.0f - friction) * currX - (1.0f - friction) * prevX + accX * dt2

        val prevY = prev.y
        val currY = pos.y
        val accY = _force.y / mass
        prev.y = currY
        pos.y = (2.0f - friction) * currY - (1.0f - friction) * prevY + accY * dt2
    }

//    fun draw() {
//        val radius = 4f
//        val x = pos.x
//        val y = pos.y
//        // val circle = EllipseGeometry(Point(x, y), radius, radius);
//    }
}
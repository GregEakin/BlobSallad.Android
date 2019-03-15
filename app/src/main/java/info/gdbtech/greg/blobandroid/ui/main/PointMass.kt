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
        set(value) = _force.set(value)

    fun addForce(force: Vector) =
        _force.add(force)

    /// https://en.wikipedia.org/wiki/Verlet_integration
    fun move(dt: Float) {
        val dt2 = dt * dt

        val ax = _force.x / mass
        val cx = pos.x
        val px = prev.x
        val tx = (2.0f - friction) * cx - (1.0f - friction) * px + ax * dt2
        prev.x = cx
        pos.x = tx

        val ay = _force.y / mass
        val cy = pos.y
        val py = prev.y
        val ty = (2.0f - friction) * cy - (1.0f - friction) * py + ay * dt2
        prev.y = cy
        pos.y = ty

        // val velocity = cx - px
    }

//    fun draw() {
//        val radius = 4f
//        val x = pos.x
//        val y = pos.y
//        // val circle = EllipseGeometry(Point(x, y), radius, radius);
//    }
}
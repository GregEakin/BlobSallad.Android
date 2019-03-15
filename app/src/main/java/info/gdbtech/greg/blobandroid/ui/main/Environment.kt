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

class Environment(val x: Float, val y: Float, val w: Float, val h: Float) {
    init {
        if (w < 0.0f)
            throw Exception("Can't have negative width.")
        if (h < 0.0f)
            throw Exception("Can't have negative height.")
    }

    fun collision(curPos: Vector, prePos: Vector): Boolean {
        var outOfBounds = false
        if (curPos.x < x) {
            curPos.x = x
            outOfBounds = true
        } else if (curPos.x > x + w) {
            curPos.x = x + w
            outOfBounds = true
        }

        if (curPos.y < y) {
            curPos.y = y
            outOfBounds = true
        } else if (curPos.y > y + h) {
            curPos.y = y + h
            outOfBounds = true
        }

        return outOfBounds
    }

//    fun draw(canvas: Int, scaleFactor: Double) {}
}
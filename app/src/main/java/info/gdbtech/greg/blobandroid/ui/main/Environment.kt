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

class Environment(val x: Float, val y: Float, w: Float, h: Float) {
    val left: Float = x
    var right: Float = x + w
    val top: Float = y
    var bottom: Float = y + h
    var width: Float = w
        set(value) {
            right = left + value
            field = value
        }
    var height: Float = h
        set(value) {
            bottom = top + value
            field = value
        }

    fun collision(curPos: Vector, prePos: Vector): Boolean {
        if (curPos.x < left) {
            curPos.x = left
            return true
        }

        if (curPos.x > right) {
            curPos.x = right
            return true
        }

        if (curPos.y < top) {
            curPos.y = top
            return true
        }

        if (curPos.y > bottom) {
            curPos.y = bottom
            return true
        }

        return false
    }

//    fun draw(canvas: Int, scaleFactor: Double) {}
}
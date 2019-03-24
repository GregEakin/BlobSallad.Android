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

class Bones(pointMassA: PointMass, pointMassB: PointMass, shortFactor: Float, longFactor: Float) :
    Force(pointMassA, pointMassB) {

    var shortLimit: Float = (pointMassB.pos - pointMassA.pos).length * shortFactor

    var longLimit: Float = (pointMassB.pos - pointMassA.pos).length * longFactor

    private val slSquared: Float
        get() = shortLimit * shortLimit

    private val llSquared: Float
        get() = longLimit * longLimit

    override fun scale(scaleFactor: Float) {
        shortLimit *= scaleFactor
        longLimit *= scaleFactor
    }

    override fun sc() {
        val delta = pointMassB.pos - pointMassA.pos
        val dp = delta * delta
        if (dp < slSquared) {
            val scaleFactor = slSquared / (dp + slSquared) - 0.5f
            delta.scale(scaleFactor)
            pointMassA.pos -= delta
            pointMassB.pos += delta
        }
        if (dp > llSquared) {
            val scaleFactor = llSquared / (dp + llSquared) - 0.5f
            delta.scale(scaleFactor)
            pointMassA.pos -= delta
            pointMassB.pos += delta
        }
    }
}
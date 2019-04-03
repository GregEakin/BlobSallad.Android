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

class Bone(pointMassA: PointMass, pointMassB: PointMass, shortFactor: Float, longFactor: Float) :
    Force(pointMassA, pointMassB) {

    init{
        if (shortFactor < 0)
            throw Exception("Short Factor needs to be greater than zero.")
        if (longFactor < 0)
            throw Exception("Long Factor needs to be greater than zero.")
        if (shortFactor >= longFactor)
            throw Exception("Short Factor needs to be less than Long Factor.")
    }

    var shortLimit: Float = (pointMassB.pos - pointMassA.pos).length * shortFactor

    var longLimit: Float = (pointMassB.pos - pointMassA.pos).length * longFactor

    val slSquared: Float
        get() = shortLimit * shortLimit

    val llSquared: Float
        get() = longLimit * longLimit

    override fun scale(scaleFactor: Float) {
        shortLimit *= scaleFactor
        longLimit *= scaleFactor
    }

    override fun sc() {
        val delta = pointMassB.pos - pointMassA.pos
        val dist = delta * delta
        if (dist < slSquared) {
            val scaleFactor = slSquared / (dist + slSquared) - 0.5f
            delta.scale(scaleFactor)
            pointMassA.pos -= delta
            pointMassB.pos += delta
        }
        else if (dist > llSquared) {
            val scaleFactor = llSquared / (dist + llSquared) - 0.5f
            delta.scale(scaleFactor)
            pointMassA.pos -= delta
            pointMassB.pos += delta
        }
    }
}
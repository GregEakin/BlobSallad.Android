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

class Neighbor(pointMassA: PointMass, pointMassB: PointMass, var limit: Float) :
    Connection(pointMassA, pointMassB) {

    val limitSquared: Float
        get() = limit * limit

    override fun scale(scaleFactor: Float) {
        limit *= scaleFactor
    }

    override fun sc() {
        val delta = pointMassB.pos - pointMassA.pos
        val distance = delta * delta
        if (distance >= limitSquared) return
        val scaleFactor = limitSquared / (distance + limitSquared) - 0.5f
        delta.scale(scaleFactor)
        pointMassA.pos -= delta
        pointMassB.pos += delta
    }
}
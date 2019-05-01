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

class BlobFace(x: Float, y: Float, radius: Float, numPoints: Int) : Blob(x, y, radius, numPoints) {

    constructor(mother: BlobFace) : this(mother.x, mother.y, mother.radius, mother.numPoints)

    enum class Eye {
        Open,
        Closed,
        Crossed
    }

    enum class Face {
        Smile,
        Open,
        Ooh
    }

    var drawEyeStyle: Eye = Eye.Open
    var drawFaceStyle: Face = Face.Smile

    fun drawFace() {
        if (drawFaceStyle == Face.Smile && Math.random() < 0.05) {
            drawFaceStyle = Face.Open
        } else if (drawFaceStyle == Face.Open && Math.random() < 0.1) {
            drawFaceStyle = Face.Smile
        }

        if (drawEyeStyle == Eye.Open && Math.random() < 0.025) {
            drawEyeStyle = Eye.Closed
        } else if (drawEyeStyle == Eye.Closed && Math.random() < 0.3) {
            drawEyeStyle = Eye.Open
        }
    }
}
package info.gdbtech.greg.myapplicationii.ui.main

class BlobFace(x: Double, y: Double, radius: Double, numPoints: Int) : Blob(x, y, radius, numPoints) {

    constructor(mother: BlobFace) : this(mother.x, mother.y, mother.radius, mother.numPoints) {
    }

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

    fun DrawFace() {
        if (drawFaceStyle == Face.Smile && Math.random() < 0.05) {
            drawFaceStyle = Face.Open;
        } else if (drawFaceStyle == Face.Open && Math.random() < 0.1) {
            drawFaceStyle = Face.Smile;
        }

        if (drawEyeStyle == Eye.Open && Math.random() < 0.025) {
            drawEyeStyle = Eye.Closed;
        } else if (drawEyeStyle == Eye.Closed && Math.random() < 0.3) {
            drawEyeStyle = Eye.Open;
        }
    }
}
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
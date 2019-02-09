package info.gdbtech.greg.myapplicationii.ui.main

class Environment(val x: Float, val y: Float, val w: Float, val h: Float) {
    val left: Float = x
    var right: Float = x + w
    val top: Float = y
    var bottom: Float = y + h
    var width: Float = w
        set(value: Float) {
            right = left + value
            field = value
        }
    var height: Float = h
        set(value: Float) {
            bottom = top + value
            field = value
        }

    fun Collision(curPos: Vector, prePos: Vector): Boolean {
        if (curPos.x < left) {
            curPos.x = left
            return true;
        }

        if (curPos.x > right) {
            curPos.x = right
            return true;
        }

        if (curPos.y < top) {
            curPos.y = top
            return true;
        }

        if (curPos.y > bottom) {
            curPos.y = bottom
            return true;
        }

        return false;
    }

    fun Draw(canvas: Int, scaleFactor: Double) {}
}
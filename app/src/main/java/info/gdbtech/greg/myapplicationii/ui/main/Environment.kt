package info.gdbtech.greg.myapplicationii.ui.main

class Environment(val x: Double, val y: Double, val w: Double, val h: Double) {
    val left: Double = x
    var right: Double = x + w
    val top: Double = y
    var bottom: Double = y + h
    var width: Double = w
        set(value: Double) {
            right = left + value
            field = value
        }
    var height: Double = h
        set(value: Double) {
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
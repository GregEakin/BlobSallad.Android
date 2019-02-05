package info.gdbtech.greg.myapplicationii.ui.main

public class PointMass(cx: Double, cy: Double, val mass: Double) {
    val pos = Vector(cx, cy)
    val prev = Vector(cx, cy)

    val xPos: Double
        get() = pos.x
    val yPos: Double
        get() = pos.y

    val xPrev: Double
        get() = prev.x
    val yPrev: Double
        get() = prev.y

    val velocity: Double
        get() {
            val cXpX = pos.x - prev.x
            val cYpY = pos.y - prev.y
            return cXpX * cXpX + cYpY * cYpY
        }

    var friction: Double = 0.01

    var force: Vector =
        Vector(0.0, 0.0)

    fun addForce(force: Vector) = this.force.add(force)

    fun move(dt: Double) {
        val dt2 = dt * dt

        val ax = force.x / mass
        val cx = pos.x
        val px = prev.x
        val tx = (2.0 - friction) * cx - (1.0 - friction) * px + ax * dt2
        prev.x = cx
        pos.x = tx

        val ay = force.y / mass
        val cy = pos.y
        val py = prev.y
        val ty = (2.0 - friction) * cy - (1.0 - friction) * py + ay * dt2
        prev.y = cy
        pos.y = ty
    }

    fun draw(scaleFactor: Double) {
        val radius = 4
        val x = pos.x * scaleFactor
        val y = pos.y * scaleFactor
        // val circle = EllipseGeometry(Point(x, y), radius, radius);
    }
}
package info.gdbtech.greg.myapplicationii.ui.main

class PointMass(cx: Float, cy: Float, val mass: Float) {
    val pos = Vector(cx, cy)
    val prev = Vector(cx, cy)

    val xPos: Float
        get() = pos.x
    val yPos: Float
        get() = pos.y

    val xPrev: Float
        get() = prev.x
    val yPrev: Float
        get() = prev.y

    val velocity: Float
        get() {
            val cXpX = pos.x - prev.x
            val cYpY = pos.y - prev.y
            return cXpX * cXpX + cYpY * cYpY
        }

    var friction: Float = 0.01f

    private val _force = Vector(0.0f, 0.0f)
    var force: Vector
        get() {
            return _force
        }
        set(value) {
            _force.set(value)
        }

    fun addForce(force: Vector) =
        _force.add(force)

    fun move(dt: Float) {
        //velocity += acceleration * deltaTime;
        //position += velocity * deltaTime - 0.5 * acceleration * deltaTime * deltaTime;

        val dt2 = dt * dt

        val ax = _force.x / mass
        val cx = pos.x
        val px = prev.x
        val tx = (2.0f - friction) * cx - (1.0f - friction) * px + ax * dt2
        prev.x = cx
        pos.x = tx

        val ay = _force.y / mass
        val cy = pos.y
        val py = prev.y
        val ty = (2.0f - friction) * cy - (1.0f - friction) * py + ay * dt2
        prev.y = cy
        pos.y = ty
    }

    fun draw() {
        val radius = 4f
        val x = pos.x
        val y = pos.y
        // val circle = EllipseGeometry(Point(x, y), radius, radius);
    }
}
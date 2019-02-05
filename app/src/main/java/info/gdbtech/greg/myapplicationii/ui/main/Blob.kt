package info.gdbtech.greg.myapplicationii.ui.main


open class Blob(private val x1: Double, private val y1: Double, var radius: Double, val numPoints: Int) {
    init {
        if (radius <= 0.0)
            throw Exception("Can't have a negative radius.")

        if (numPoints < 0)
            throw Exception("Not enough points.")
    }

    constructor(mother: Blob) : this(mother.x1, mother.y1, mother.radius, mother.numPoints) {
    }

    val middle: PointMass = PointMass(x1, y1, 1.0)

    val points: List<PointMass> = pointsInit()
    private fun pointsInit(): List<PointMass> {
        val list = mutableListOf<PointMass>()
        for (i in 0 until numPoints) {
            val theta = i * 2.0 * Math.PI / numPoints
            val cx = Math.cos(theta) * radius + x1
            val cy = Math.sin(theta) * radius + y1
            val mass = if (i < 2) 4.0 else 1.0
            val pt = PointMass(cx.toDouble(), cy.toDouble(), mass)
            list.add(pt)
        }
        return list
    }

    val skins: List<Skin> = skinInit()
    private fun skinInit(): List<Skin> {
        val list = mutableListOf<Skin>()
        for (i in 0 until numPoints) {
            val pointMassA = points[i]
            val index = PointMassIndex(i + 1)
            val pointMassB = points[index]
            val skin = Skin(pointMassA, pointMassB)
            list.add(skin)
        }
        return list
    }

    val bones: List<Bones> = bonesInit()
    private fun bonesInit(): List<Bones> {
        val list = mutableListOf<Bones>()
        for (i in 0 until numPoints) {
            val crossShort = 0.95;
            val crossLong = 1.05;
            val middleShort = crossLong * 0.9;
            val middleLong = crossShort * 1.1;
            val pointMassA = points[i];

            val index = PointMassIndex(i + numPoints / 2 + 1);
            val pointMassB = points[index];
            val bone1 = Bones(pointMassA, pointMassB, crossShort, crossLong);
            list.add(bone1);

            val bone2 = Bones(pointMassA, middle, middleShort, middleLong);
            list.add(bone2);
        }
        return list
    }

    var selected: Boolean = false

    val x = middle.xPos
    val y = middle.yPos
    val mass = middle.mass

    fun PointMassIndex(x: Int): Int {
        val m = numPoints
        return (x % m + m) % m
    }

    val collisions: MutableList<Collision> = mutableListOf()

    fun linkBlob(blob: Blob) {
        val dist = radius + blob.radius
        val collision = Collision(middle, blob.middle, dist * 0.95)
        collisions.add(collision)
    }

    fun unlinkBlob(blob: Blob) {
        for (collision in collisions) {
            if (collision.pointMassB != blob.middle)
                continue

            collisions.remove(collision)
            break
        }
    }

    fun scale(scaleFactor: Double) {
        for (skin in skins)
            skin.scale(scaleFactor)

        for (bone in bones)
            bone.scale(scaleFactor)

        for (collision in collisions)
            collision.scale(scaleFactor)

        radius *= scaleFactor
    }

    fun move(dt: Double) {
        for (point in points)
            point.move(dt)

        middle.move(dt)
    }

    fun Sc(env: Environment) {
        for (j in 0 until 4) {
            for (point in points) {
                val collision = env.Collision(point.pos, point.prev)
                point.friction = if (collision) 0.75 else 0.01
            }

            for (skin in skins)
                skin.sc(env)

            for (bone in bones)
                bone.sc(env)

            for (collision in collisions)
                collision.sc(env)
        }
    }

    var force: Vector
        get() {
            return middle.force
        }
        set(value: Vector) {
            for (point in points)
                point.force = value
            middle.force = value
        }

    fun AddForce(force: Vector) {
        middle.addForce(force);
        for (point in points)
            point.addForce(force);

        if (!points.any())
            return;

        // put a spin on the blob
        var pointMass = points[0];
        pointMass.addForce(force);
        pointMass.addForce(force);
        pointMass.addForce(force);
        pointMass.addForce(force);
    }

    fun MoveTo(x2: Double, y2: Double) {
        val blobPos1 = middle.pos;
        val x4 = x2 - blobPos1.x;
        val y4 = y2 - blobPos1.y;

        for (point in points) {
            val blobPos = point.pos;
            blobPos.addX(x4);
            blobPos.addY(y4);
        }

        val blobPos = middle.pos;
        blobPos.addX(x4);
        blobPos.addY(y4);
    }
}
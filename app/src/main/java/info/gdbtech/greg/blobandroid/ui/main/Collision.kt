package info.gdbtech.greg.blobandroid.ui.main

class Collision(pointMassA: PointMass, pointMassB: PointMass, var shortLimit: Float) :
    Force(pointMassA, pointMassB) {

    private val slSquared: Float
        get() {
            return shortLimit * shortLimit
        }

    override fun scale(scaleFactor: Float) {
        shortLimit *= scaleFactor
    }

    override fun sc(env: Environment) {
        val delta = pointMassB.pos - pointMassA.pos
        val dp = delta.dotProd(delta)
        if (dp < slSquared) {
            val scaleFactor = slSquared / (dp + slSquared) - 0.5f
            delta.scale(scaleFactor)
            pointMassA.pos.sub(delta)
            pointMassB.pos.add(delta)
        }
    }
}
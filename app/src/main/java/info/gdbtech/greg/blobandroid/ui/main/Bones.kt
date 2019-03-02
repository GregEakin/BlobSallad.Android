package info.gdbtech.greg.blobandroid.ui.main

class Bones(pointMassA: PointMass, pointMassB: PointMass, shortFactor: Float, longFactor: Float) :
    Force(pointMassA, pointMassB) {

    var shortLimit: Float = (pointMassB.pos - pointMassA.pos).length * shortFactor

    var longLimit: Float = (pointMassB.pos - pointMassA.pos).length * longFactor

    private val slSquared: Float
        get() {
            return shortLimit * shortLimit
        }

    private val llSquared: Float
        get() {
            return longLimit * longLimit
        }

    override fun scale(scaleFactor: Float) {
        shortLimit *= scaleFactor
        longLimit *= scaleFactor
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
        if (dp > llSquared) {
            val scaleFactor = llSquared / (dp + llSquared) - 0.5f
            delta.scale(scaleFactor)
            pointMassA.pos.sub(delta)
            pointMassB.pos.add(delta)
        }
    }
}
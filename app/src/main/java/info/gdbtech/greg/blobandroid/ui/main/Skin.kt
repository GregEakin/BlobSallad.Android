package info.gdbtech.greg.blobandroid.ui.main

class Skin(pointMassA: PointMass, pointMassB: PointMass) :
    Force(pointMassA, pointMassB) {

    var lengthSquared: Float
    var length: Float

    init {
        val aXbX = pointMassA.xPos - pointMassB.xPos
        val aYbY = pointMassA.yPos - pointMassB.yPos
        lengthSquared = aXbX * aXbX + aYbY * aYbY
        length = Math.sqrt(lengthSquared.toDouble()).toFloat()
    }

    override fun scale(scaleFactor: Float) {
        length *= scaleFactor
        lengthSquared = length * length
    }

    override fun sc(env: Environment) {
        val delta = pointMassB.pos - pointMassA.pos
        val dotProd = delta.dotProd(delta)
        val scaleFactor = lengthSquared / (dotProd + lengthSquared) - 0.5f
        delta.scale(scaleFactor)
        pointMassA.pos.sub(delta)
        pointMassB.pos.add(delta)
    }
}
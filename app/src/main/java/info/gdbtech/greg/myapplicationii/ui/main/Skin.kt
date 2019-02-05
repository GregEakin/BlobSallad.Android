package info.gdbtech.greg.myapplicationii.ui.main

import android.graphics.Canvas
import android.graphics.ColorFilter

class Skin(pointMassA: PointMass, pointMassB: PointMass) :
    Force(pointMassA, pointMassB) {

    private val aXbX = pointMassA.xPos - pointMassB.xPos
    private val aYbY = pointMassA.yPos - pointMassB.yPos
    var lengthSquared: Double = aXbX * aXbX + aYbY * aYbY
    var length: Double = Math.sqrt(lengthSquared)

    override fun scale(scaleFactor: Double) {
        length += scaleFactor
        lengthSquared = length * length
    }

    override fun sc(env: Environment) {
        val delta = pointMassB.pos - pointMassA.pos
        val dotProd = delta.dotProd(delta)
        val scaleFactor = lengthSquared / (dotProd + lengthSquared) - 0.5
        delta.scale(scaleFactor)
        pointMassA.pos.sub(delta)
        pointMassB.pos.sub(delta)
    }

    override fun draw(canvas: Canvas) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setAlpha(alpha: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getOpacity(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
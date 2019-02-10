package info.gdbtech.greg.myapplicationii.ui.main

import android.graphics.Canvas
import android.graphics.ColorFilter

class Skin(pointMassA: PointMass, pointMassB: PointMass) :
    Force(pointMassA, pointMassB) {

    private val aXbX = pointMassA.xPos - pointMassB.xPos
    private val aYbY = pointMassA.yPos - pointMassB.yPos
    var lengthSquared: Float = aXbX * aXbX + aYbY * aYbY
    var length: Float = Math.sqrt(lengthSquared.toDouble()).toFloat()

    override fun scale(scaleFactor: Float) {
        length += scaleFactor
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
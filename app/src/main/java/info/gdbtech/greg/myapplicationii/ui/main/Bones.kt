package info.gdbtech.greg.myapplicationii.ui.main

import android.graphics.Canvas
import android.graphics.ColorFilter

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

    override fun draw(canvas: Canvas) {
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
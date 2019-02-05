package info.gdbtech.greg.myapplicationii.ui.main

import android.graphics.Canvas
import android.graphics.ColorFilter

class Bones(pointMassA: PointMass, pointMassB: PointMass, shortFactor: Double, longFactor: Double) :
    Force(pointMassA, pointMassB) {

    private var delta = pointMassB.pos - pointMassA.pos

    var shortLimit: Double = delta.length * shortFactor

    var longLimit: Double = delta.length * longFactor

    private var slSquared = shortLimit * shortLimit

    private var llSquared = longLimit * longLimit

    override fun scale(scaleFactor: Double) {
        shortLimit *= scaleFactor
        longLimit *= scaleFactor
    }

    override fun sc(env: Environment) {
        val dp = delta.dotProd(delta)
        if (dp < slSquared) {
            val scaleFactor = slSquared / (dp + slSquared) - 0.5
            delta.scale(scaleFactor)
            pointMassA.pos.sub(delta)
            pointMassB.pos.sub(delta)
        }
        if (dp > llSquared) {
            val scaleFactor = llSquared / (dp + llSquared) - 0.5
            delta.scale(scaleFactor)
            pointMassA.pos.sub(delta)
            pointMassB.pos.sub(delta)
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
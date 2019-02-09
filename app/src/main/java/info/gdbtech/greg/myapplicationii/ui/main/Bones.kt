package info.gdbtech.greg.myapplicationii.ui.main

import android.graphics.Canvas
import android.graphics.ColorFilter

class Bones(pointMassA: PointMass, pointMassB: PointMass, shortFactor: Float, longFactor: Float) :
    Force(pointMassA, pointMassB) {

    private var delta = pointMassB.pos - pointMassA.pos

    var shortLimit: Float = delta.length * shortFactor

    var longLimit: Float = delta.length * longFactor

    private var slSquared = shortLimit * shortLimit

    private var llSquared = longLimit * longLimit

    override fun scale(scaleFactor: Float) {
        shortLimit *= scaleFactor
        longLimit *= scaleFactor
    }

    override fun sc(env: Environment) {
        val dp = delta.dotProd(delta)
        if (dp < slSquared) {
            val scaleFactor = slSquared / (dp + slSquared) - 0.5f
            delta.scale(scaleFactor)
            pointMassA.pos.sub(delta)
            pointMassB.pos.sub(delta)
        }
        if (dp > llSquared) {
            val scaleFactor = llSquared / (dp + llSquared) - 0.5f
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
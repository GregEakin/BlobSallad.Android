package info.gdbtech.greg.myapplicationii.ui.main

import android.graphics.drawable.Drawable

abstract class Force(val pointMassA: PointMass, val pointMassB: PointMass) : Drawable() {
    abstract fun scale(scaleFactor: Float)
    abstract fun sc(env: Environment)
    // abstract fun draw()
}
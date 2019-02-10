package info.gdbtech.greg.myapplicationii.ui.main

abstract class Force(val pointMassA: PointMass, val pointMassB: PointMass) {
    abstract fun scale(scaleFactor: Float)
    abstract fun sc(env: Environment)
}
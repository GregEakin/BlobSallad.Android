package info.gdbtech.greg.myapplicationii.ui.main

import java.lang.Math.sqrt

class Vector(var x: Double, var y: Double) {
    val length: Double = sqrt(this.x * this.x + this.y * this.y)
    fun addX(x: Double) {
        this.x += x
    }

    fun addY(y: Double) {
        this.y += y
    }

    fun set(that: Vector) {
        this.x = that.x
        this.y = that.y
    }

    fun add(that: Vector) {
        this.x += that.x
        this.y += that.y
    }

    fun sub(that: Vector) {
        this.x -= that.x
        this.y -= that.y
    }

    fun scale(factor: Double) {
        this.x *= factor
        this.y *= factor
    }

    fun dotProd(that: Vector): Double = this.x * that.x + this.y * that.y

    override fun toString(): String = "(X: $x, Y: $y)"

    operator fun minus(that: Vector): Vector = Vector(this.x - that.x, this.y - that.y)
}
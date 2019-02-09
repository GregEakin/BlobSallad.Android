package info.gdbtech.greg.myapplicationii.ui.main

import java.lang.Math.sqrt

class Vector(var x: Float, var y: Float) {
    val length: Float = sqrt(this.x.toDouble() * this.x + this.y * this.y).toFloat()
    fun addX(x: Float) {
        this.x += x
    }

    fun addY(y: Float) {
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

    fun scale(factor: Float) {
        this.x *= factor
        this.y *= factor
    }

    fun dotProd(that: Vector): Float = this.x * that.x + this.y * that.y

    override fun toString(): String = "(X: $x, Y: $y)"

    operator fun minus(that: Vector): Vector = Vector(this.x - that.x, this.y - that.y)
}
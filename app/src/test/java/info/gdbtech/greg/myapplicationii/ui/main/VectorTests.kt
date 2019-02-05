package info.gdbtech.greg.myapplicationii.ui.main;

import org.junit.Assert.assertEquals
import org.junit.Test

public class VectorTests {
    @Test
    fun ctor_test() {
        val vector = Vector(71.0, 67.0)
        assertEquals(71.0, vector.x, 0.001)
        assertEquals(67.0, vector.y, 0.001)
    }

    @Test
    fun addX_test() {
        val vector = Vector(71.0, 67.0)
        vector.addX(100.0)
        assertEquals(171.0, vector.x, 0.001)
        assertEquals(67.0, vector.y, 0.001)
    }

    @Test
    fun addY_test() {
        val vector = Vector(71.0, 67.0)
        vector.addY(100.0)
        assertEquals(71.0, vector.x, 0.001)
        assertEquals(167.0, vector.y, 0.001)
    }

    @Test
    fun set_test() {
        val vector = Vector(71.0, 67.0)
        val setter = Vector(61.0, 59.0)
        vector.set(setter)
        assertEquals(61.0, vector.x, 0.001)
        assertEquals(59.0, vector.y, 0.001)
    }

    @Test
    fun add_test() {
        val vector = Vector(71.0, 67.0)
        val setter = Vector(13.0, 11.0)
        vector.add(setter)
        assertEquals(84.0, vector.x, 0.001)
        assertEquals(78.0, vector.y, 0.001)
    }

    @Test
    fun sub_test() {
        val vector = Vector(71.0, 67.0)
        val setter = Vector(13.0, 11.0)
        vector.sub(setter)
        assertEquals(58.0, vector.x, 0.001)
        assertEquals(56.0, vector.y, 0.001)
    }

    @Test
    fun operator_subtract_test() {
        val vector = Vector(71.0, 67.0)
        val setter = Vector(13.0, 11.0)
        val b = vector - setter
        assertEquals(58.0, b.x, 0.001)
        assertEquals(56.0, b.y, 0.001)
    }

    @Test
    fun dot_test() {
        val vector = Vector(71.0, 67.0)
        val setter = Vector(13.0, 11.0)
        val dot = vector.dotProd(setter)
        assertEquals(1660.0, dot, 0.001)
    }

    @Test
    fun length_test() {
        val vector = Vector(71.0, 67.0)
        val length = vector.length
        assertEquals(97.621, length, 0.001)
    }

    @Test
    fun scale_test() {
        val vector = Vector(71.0, 67.0)
        vector.scale(2.0)
        assertEquals(142.0, vector.x, 0.001)
        assertEquals(134.0, vector.y, 0.001)
    }

    @Test
    fun string_test() {
        val vector = Vector(71.0, 67.0)
        assertEquals("(X: 71.0, Y: 67.0)", vector.toString())
    }

    @Test
    fun setX_test() {
        val vector = Vector(71.0, 67.0)
        vector.x = 99.0
        assertEquals(99.0, vector.x, 0.001)
    }

    @Test
    fun setY_test() {
        val vector = Vector(71.0, 67.0)
        vector.y = 99.0
        assertEquals(99.0, vector.y, 0.001)
    }
}



package info.gdbtech.greg.myapplicationii.ui.main;

import org.junit.Assert.assertEquals
import org.junit.Test

public class VectorTests {
    @Test
    fun ctor_test() {
        val vector = Vector(71.0f, 67.0f)
        assertEquals(71.0f, vector.x, 0.001f)
        assertEquals(67.0f, vector.y, 0.001f)
    }

    @Test
    fun addX_test() {
        val vector = Vector(71.0f, 67.0f)
        vector.addX(100.0f)
        assertEquals(171.0f, vector.x, 0.001f)
        assertEquals(67.0f, vector.y, 0.001f)
    }

    @Test
    fun addY_test() {
        val vector = Vector(71.0f, 67.0f)
        vector.addY(100.0f)
        assertEquals(71.0f, vector.x, 0.001f)
        assertEquals(167.0f, vector.y, 0.001f)
    }

    @Test
    fun set_test() {
        val vector = Vector(71.0f, 67.0f)
        val setter = Vector(61.0f, 59.0f)
        vector.set(setter)
        assertEquals(61.0f, vector.x, 0.001f)
        assertEquals(59.0f, vector.y, 0.001f)
    }

    @Test
    fun add_test() {
        val vector = Vector(71.0f, 67.0f)
        val setter = Vector(13.0f, 11.0f)
        vector.add(setter)
        assertEquals(84.0f, vector.x, 0.001f)
        assertEquals(78.0f, vector.y, 0.001f)
    }

    @Test
    fun sub_test() {
        val vector = Vector(71.0f, 67.0f)
        val setter = Vector(13.0f, 11.0f)
        vector.sub(setter)
        assertEquals(58.0f, vector.x, 0.001f)
        assertEquals(56.0f, vector.y, 0.001f)
    }

    @Test
    fun operator_subtract_test() {
        val vector = Vector(71.0f, 67.0f)
        val setter = Vector(13.0f, 11.0f)
        val b = vector - setter
        assertEquals(58.0f, b.x, 0.001f)
        assertEquals(56.0f, b.y, 0.001f)
    }

    @Test
    fun dot_test() {
        val vector = Vector(71.0f, 67.0f)
        val setter = Vector(13.0f, 11.0f)
        val dot = vector.dotProd(setter)
        assertEquals(1660.0f, dot, 0.001f)
    }

    @Test
    fun length_test() {
        val vector = Vector(71.0f, 67.0f)
        val length = vector.length
        assertEquals(97.621f, length, 0.001f)
    }

    @Test
    fun scale_test() {
        val vector = Vector(71.0f, 67.0f)
        vector.scale(2.0f)
        assertEquals(142.0f, vector.x, 0.001f)
        assertEquals(134.0f, vector.y, 0.001f)
    }

    @Test
    fun string_test() {
        val vector = Vector(71.0f, 67.0f)
        assertEquals("(X: 71.0, Y: 67.0)", vector.toString())
    }

    @Test
    fun setX_test() {
        val vector = Vector(71.0f, 67.0f)
        vector.x = 99.0f
        assertEquals(99.0f, vector.x, 0.001f)
    }

    @Test
    fun setY_test() {
        val vector = Vector(71.0f, 67.0f)
        vector.y = 99.0f
        assertEquals(99.0f, vector.y, 0.001f)
    }
}



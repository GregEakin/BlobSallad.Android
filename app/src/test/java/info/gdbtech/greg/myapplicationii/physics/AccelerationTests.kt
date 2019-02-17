package info.gdbtech.greg.myapplicationii.physics

import org.junit.Test


class AccelerationTests {
    @Test
    fun example() {
        val dt = 1.0 / 30.0
        val dp0 = 0.0
        val dp1 = 10.0
        val p0 = 0.0
        val p1 = 25.0
        val ddp = (dp1 * dp1 - dp0 * dp0) / (2.0 * (p1 - p0))

        var i = 0
        var p = p0
        var dp = dp0
        while (p <= p1) {
            System.out.print("{$i}.")
            System.out.print(" p: ${round(p)}")
            System.out.print(", dp: ${round(dp)}")
            System.out.println(", ddp: ${round(ddp)}")

            ++i
            p += dp * dt
            dp += ddp * dt
        }
    }

    private fun round(p: Double) = Math.floor((p * 1000.0)) / 1000.0
}
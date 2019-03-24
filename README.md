# Blob Sallad for Android
I found this little blob engine code, and converted it run on [Android](https://www.android.com/) with [Kotlin](https://kotlinlang.org/)

## From the website: [Blob Sallad](https://blobsallad.se/)
![Blob Sallad](Blob.PNG)

Original version by: [Björn Lindberg](mailto:bjoern.lindberg@gmail.com)

## See the writeup at Dev.Opera
[Blob Sallad — Canvas Tag and JavaScript Physics Simulation Experiment](https://dev.opera.com/articles/blob-sallad-canvas-tag-and-javascript/)

## Sample code
Here's the code that calculates the [Verlet Integration](https://en.wikipedia.org/wiki/Verlet_integration) to integrate Newton's equations of motion, as used from the Störmer method.
```kotlin
fun move(dt: Float) {
    val dt2 = dt * dt

    val currX = pos.x
    val prevX = prev.x
    val accX = _force.x / mass
    prev.x = currX
    pos.x = (2.0f - friction) * currX - (1.0f - friction) * prevX + accX * dt2

    val currY = pos.y
    val prevY = prev.y
    val accY = _force.y / mass
    prev.y = currY
    pos.y = (2.0f - friction) * currY - (1.0f - friction) * prevY + accY * dt2
}
```

## Author
:fire: [Greg Eakin](https://www.linkedin.com/in/gregeakin)

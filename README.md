# Blob Sallad for Android
I found this little blob engine code, and converted it run on [Android](https://www.android.com/) with [Kotlin](https://kotlinlang.org/)

## From the website: [Blob Sallad](https://blobsallad.se/)
![Blob Sallad](Blob.PNG)

Original version by: [Björn Lindberg](mailto:bjoern.lindberg@gmail.com)

## See his writeup at Dev.Opera
[Blob Sallad — Canvas Tag and JavaScript Physics Simulation Experiment](https://dev.opera.com/articles/blob-sallad-canvas-tag-and-javascript/)

## Sample code
Here's the code that calculates the [Verlet integration](https://en.wikipedia.org/wiki/Verlet_integration) for each time step.
```kotlin
fun move(dt: Float) {
    val dt2 = dt * dt

    val ax = _force.x / mass
    val cx = pos.x
    val px = prev.x
    val tx = (2.0f - friction) * cx - (1.0f - friction) * px + ax * dt2
    prev.x = cx
    pos.x = tx

    val ay = _force.y / mass
    val cy = pos.y
    val py = prev.y
    val ty = (2.0f - friction) * cy - (1.0f - friction) * py + ay * dt2
    prev.y = cy
    pos.y = ty
}
```

## Author
:fire: [Greg Eakin](https://www.linkedin.com/in/gregeakin)

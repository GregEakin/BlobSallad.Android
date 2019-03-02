package info.gdbtech.greg.blobandroid.ui.demo

import android.app.Activity
import android.os.Bundle
import android.view.View

open class GraphicsActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun setContentView(view: View) {
        var view = view
        if (TEST_PICTURE) {
            val vg = PictureLayout(this)
            vg.addView(view)
            view = vg
        }
        super.setContentView(view)
    }

    companion object {
        // set to true to test Picture
        private val TEST_PICTURE = false
    }
}
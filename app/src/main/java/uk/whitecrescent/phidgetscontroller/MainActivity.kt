@file:Suppress("NOTHING_TO_INLINE")

package uk.whitecrescent.phidgetscontroller

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.KeyEvent
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import uk.whitecrescent.phidgetscontroller.fragments.MainFragment

class MainActivity : AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
                .add(fragmentContainer_frameLayout.id, MainFragment(), "MainFragment")
                .commit()
    }

    // Use this to circumnavigate errors when touching Views from a thread that isn't it's own
    inline fun post(crossinline block: () -> Unit) {
        fragmentContainer_frameLayout.post { block() }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        shortSnackbar("event: $event")
        return super.onTouchEvent(event)
    }

    override fun onGenericMotionEvent(event: MotionEvent?): Boolean {
        shortSnackbar("event: $event")
        return super.onGenericMotionEvent(event)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        shortSnackbar("keyCode: $keyCode event: $event")
        return super.onKeyDown(keyCode, event)
    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        shortSnackbar("keyCode: $keyCode event: $event")
        return super.onKeyUp(keyCode, event)
    }

    inline fun shortSnackbar(text: Any?) {
        Snackbar.make(fragmentContainer_frameLayout, text.toString(), Snackbar.LENGTH_SHORT).show()
    }
}

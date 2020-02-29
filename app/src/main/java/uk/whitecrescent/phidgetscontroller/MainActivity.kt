@file:Suppress("NOTHING_TO_INLINE")

package uk.whitecrescent.phidgetscontroller

import android.content.Context
import android.content.pm.ActivityInfo
import android.hardware.usb.UsbDevice
import android.hardware.usb.UsbManager
import android.os.Bundle
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.view.animation.TranslateAnimation
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.configuration
import org.jetbrains.anko.landscape
import uk.whitecrescent.phidgetscontroller.fragments.MainFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (supportFragmentManager.fragments.isEmpty()) {
            supportFragmentManager.beginTransaction()
                    .add(fragmentContainer_frameLayout.id, MainFragment(), "MainFragment")
                    .commit()
        }
    }

    // Use this to circumnavigate errors when touching Views from a thread that isn't it's own
    inline fun post(crossinline block: () -> Unit) {
        fragmentContainer_frameLayout.post { block() }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        //shortSnackbar("event: $event")
        return super.onTouchEvent(event)
    }

    override fun onGenericMotionEvent(event: MotionEvent?): Boolean {
        //shortSnackbar("event: $event")
        return super.onGenericMotionEvent(event)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        //shortSnackbar("keyCode: $keyCode event: $event")
        return super.onKeyDown(keyCode, event)
    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        //shortSnackbar("keyCode: $keyCode event: $event")
        return super.onKeyUp(keyCode, event)
    }

    inline fun shortSnackbar(text: Any?) {
        Snackbar.make(fragmentContainer_frameLayout, text.toString(), Snackbar.LENGTH_SHORT).show()
    }

    inline fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
                .replace(fragmentContainer_frameLayout.id, fragment, null)
                .addToBackStack(null)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit()
    }

    inline var appBarText: String
        set(value) {
            appBar.textView.text = value
        }
        get() = appBar.textView.text.toString()

    inline val allUsbDevices: List<UsbDevice>
        get() = (getSystemService(Context.USB_SERVICE) as? UsbManager)
                ?.deviceList?.values?.toList() ?: emptyList()

    inline val connectedPhidgets: List<UsbDevice>
        get() = allUsbDevices.filter { it.vendorId == 1730 }

    inline val isAllPhidgetsConnected: Boolean
        get() = connectedPhidgets.filter { it.productId == 69 || it.productId == 51 }.size == 3

    inline var landscape: Boolean
        set(value) {
            requestedOrientation = if (value) ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            else ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        }
        get() = configuration.landscape

    inline fun setFullScreen(fullScreen: Boolean) {
        window.decorView.systemUiVisibility =
                if (fullScreen)
                    (View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                            or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            or View.SYSTEM_UI_FLAG_FULLSCREEN)
                else View.SYSTEM_UI_FLAG_VISIBLE
    }

    inline fun hideAppBar(delay: Long) {
        appBar?.doInBackgroundDelayed(delay) {
            startAnimation(TranslateAnimation(x, x, y, -height.F).also {
                it.duration = 500L
                it.fillAfter = true
            })
        }
    }

    inline fun showAppBar(delay: Long) {
        appBar?.doInBackgroundDelayed(delay) {
            startAnimation(TranslateAnimation(x, x, -height.F, 0F).also {
                it.duration = 500L
                it.fillAfter = true
            })
        }
    }
}

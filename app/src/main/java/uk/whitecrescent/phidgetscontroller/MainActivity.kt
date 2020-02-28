@file:Suppress("NOTHING_TO_INLINE")

package uk.whitecrescent.phidgetscontroller

import android.annotation.SuppressLint
import android.content.Context
import android.hardware.usb.UsbDevice
import android.hardware.usb.UsbManager
import android.os.Bundle
import android.view.KeyEvent
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
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

    inline var actionBarText: String
        set(value) {
            supportActionBar?.title = value
        }
        get() = supportActionBar?.title.toString()

    inline val allUsbDevices: List<UsbDevice>
        get() = (getSystemService(Context.USB_SERVICE) as? UsbManager)
                ?.deviceList?.values?.toList() ?: emptyList()

    inline val connectedPhidgets: List<UsbDevice>
        get() = allUsbDevices.filter { it.vendorId == 1730 }

    inline val isAllPhidgetsConnected: Boolean
        get() = connectedPhidgets.filter { it.productId == 69 || it.productId == 51 }.size == 3
}

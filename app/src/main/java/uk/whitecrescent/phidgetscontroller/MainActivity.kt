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
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.snackbar.Snackbar
import com.phidgets.usb.Manager
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.attr
import org.jetbrains.anko.configuration
import org.jetbrains.anko.landscape
import uk.whitecrescent.phidgetscontroller.fragments.BaseFragment
import uk.whitecrescent.phidgetscontroller.fragments.ControllerViewFragment
import uk.whitecrescent.phidgetscontroller.fragments.GameFragment
import uk.whitecrescent.phidgetscontroller.fragments.SensorDataFragment

class MainActivity : AppCompatActivity() {

    var currentFragmentTag: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme)
        setContentView(R.layout.activity_main)

        Manager.Initialize(this)

        if (supportFragmentManager.fragments.isEmpty()) {
            val fragment = SensorDataFragment()
            supportFragmentManager.beginTransaction()
                    .add(fragmentContainer_frameLayout.id, fragment, FRAGMENT_SENSOR_DATA)
                    .runOnCommit {
                        fragment.onShow()
                        currentFragmentTag = FRAGMENT_SENSOR_DATA
                    }
                    .commit()
        }

        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.sensorData_menuItem -> showFragment(FRAGMENT_SENSOR_DATA)
                R.id.controllerView_menuItem -> showFragment(FRAGMENT_CONTROLLER_VIEW)
                R.id.demoGame_menuItem -> showFragment(FRAGMENT_GAME)
            }
            true
        }

        window.statusBarColor = getColorCompat(R.color.colorPrimary)
        window.navigationBarColor = attr(R.attr.colorSurface).data

    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        currentFragmentTag = savedInstanceState.getString("currentFragmentTag")
                ?: FRAGMENT_SENSOR_DATA

        showFragment(currentFragmentTag)
        currentFragment.onShow()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putString("currentFragmentTag", this.currentFragmentTag)
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

    inline fun showFragment(name: String) {
        (supportFragmentManager.findFragmentByTag(name) as? BaseFragment).let {
            if (it == null) {
                val fragment: BaseFragment = when (name) {
                    FRAGMENT_SENSOR_DATA -> SensorDataFragment()
                    FRAGMENT_CONTROLLER_VIEW -> ControllerViewFragment()
                    FRAGMENT_GAME -> GameFragment()
                    else -> throw IllegalArgumentException("No Such Fragment with name $name")
                }
                supportFragmentManager.beginTransaction()
                        .hide(currentFragment)
                        .add(fragmentContainer_frameLayout.id, fragment, name)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .runOnCommit {
                            currentFragment.onHide()
                            fragment.onShow()
                        }
                        .commit()
            } else if (name != currentFragmentTag) {
                supportFragmentManager.beginTransaction()
                        .hide(currentFragment)
                        .show(it)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .runOnCommit {
                            currentFragment.onHide()
                            it.onShow()
                        }
                        .commit()
            }
            currentFragmentTag = name
        }
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

    inline val currentFragment: BaseFragment
        get() = supportFragmentManager.findFragmentByTag(currentFragmentTag) as? BaseFragment
                ?: throw IllegalArgumentException("No Fragment with name $currentFragmentTag")

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

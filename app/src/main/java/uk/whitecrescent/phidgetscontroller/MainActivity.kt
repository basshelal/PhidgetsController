@file:Suppress("NOTHING_TO_INLINE")

package uk.whitecrescent.phidgetscontroller

import android.annotation.SuppressLint
import android.content.Context
import android.hardware.usb.UsbDevice
import android.hardware.usb.UsbManager
import android.inputmethodservice.InputMethodService
import android.os.Bundle
import android.util.Log
import android.view.InputDevice
import androidx.appcompat.app.AppCompatActivity
import com.phidgets.PhidgetException
import com.phidgets.usb.Manager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Manager.Initialize(this)

        run {
            leftX_textView.text = "No Phidgets!"
            leftY_textView.text = "No Phidgets!"
            rightX_textView.text = "No Phidgets!"
            rightY_textView.text = "No Phidgets!"
        }

        try {
            initializePhidgets()
        } catch (e: PhidgetException) {
            Log.e("ERROR", e.errorNumber.toString())
            Log.e("ERROR", e.description)
            run {
                leftX_textView.text = "Error Loading Phidgets Library!"
                leftY_textView.text = "Error Loading Phidgets Library!"
                rightX_textView.text = "Error Loading Phidgets Library!"
                rightY_textView.text = "Error Loading Phidgets Library!"
            }
        }


        // Check if the controller is connected through USB
        // read here https://developer.android.com/guide/topics/connectivity/usb/host

        // Android game controller shit is here https://developer.android.com/training/game-controllers

        /*
         * Android does not detect us an InputDevice but instead as 3 USB devices, the same 3
         * components found in the Controller object.
         * Fine, but how can we do Android MotionEvent and KeyEvent mapping to the system??
         * Look into InputMethod and InputMethodService
         * https://developer.android.com/guide/topics/text/creating-input-method
         *
         */

        val allInputDevicesString = allInputDevices.joinToString(separator = ", \n") { it.toString() }

        val allUsbDevicesString = allUsbDevices.joinToString(separator = ", \n") { it.toString() }


        loggingInfo_textView.text = allUsbDevicesString


        InputMethodService()
    }

    val allInputDevices: List<InputDevice>
        get() = InputDevice.getDeviceIds().map { InputDevice.getDevice(it) }

    val gameControllers: List<InputDevice>
        get() = allInputDevices.filter {
            it.sources and InputDevice.SOURCE_GAMEPAD == InputDevice.SOURCE_GAMEPAD
                    || it.sources and InputDevice.SOURCE_JOYSTICK == InputDevice.SOURCE_JOYSTICK
        }

    val allUsbDevices: List<UsbDevice>
        get() = (getSystemService(Context.USB_SERVICE) as UsbManager).deviceList.values.toList()

    @SuppressLint("SetTextI18n")
    private inline fun initializePhidgets() {
        Controller.initialize()

        Controller.doOnReady = {
            run {
                leftX_textView.text = "Ready!"
                leftY_textView.text = "Ready!"
                rightX_textView.text = "Ready!"
                rightY_textView.text = "Ready!"
            }
        }

        Controller.Sensors.LEFT_JOYSTICK_X.onChange = {
            run { leftX_textView.text = "x: $it" }
        }
        Controller.Sensors.LEFT_JOYSTICK_Y.onChange = {
            run { leftY_textView.text = "y: $it" }
        }
        Controller.Sensors.RIGHT_JOYSTICK_X.onChange = {
            run { rightX_textView.text = "x: $it" }
        }
        Controller.Sensors.RIGHT_JOYSTICK_Y.onChange = {
            run { rightY_textView.text = "y: $it" }
        }
    }

    // Use this to circumnavigate errors when touching Views from a thread that isn't it's own
    private inline fun run(crossinline block: () -> Unit) {
        root_constraintLayout.post { block() }
    }
}

@file:Suppress("NOTHING_TO_INLINE")

package uk.whitecrescent.phidgetscontroller.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.hardware.usb.UsbDevice
import android.hardware.usb.UsbManager
import android.os.Bundle
import android.util.Log
import android.view.InputDevice
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.phidgets.PhidgetException
import com.phidgets.usb.Manager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_main.*
import uk.whitecrescent.phidgetscontroller.Controller
import uk.whitecrescent.phidgetscontroller.MainActivity
import uk.whitecrescent.phidgetscontroller.R

class MainFragment : Fragment() {

    inline val mainActivity: MainActivity get() = activity as MainActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        Manager.Initialize(context)

        try {
            initializePhidgets()
        } catch (e: PhidgetException) {
            Log.e("ERROR", e.errorNumber.toString())
            Log.e("ERROR", e.description)
        }

        val allInputDevicesString = allInputDevices.joinToString(separator = ", \n") { it.toString() }

        val allUsbDevicesString = allUsbDevices.joinToString(separator = ", \n") { it.toString() }


        controllerView_button.setOnClickListener {
            mainActivity.supportFragmentManager.beginTransaction()
                    .replace(mainActivity.fragmentContainer_frameLayout.id, ControllerViewFragment(), "")
                    .addToBackStack("")
                    .commit()
        }
    }

    inline val allInputDevices: List<InputDevice>
        get() = InputDevice.getDeviceIds().map { InputDevice.getDevice(it) }

    inline val gameControllers: List<InputDevice>
        get() = allInputDevices.filter {
            it.sources and InputDevice.SOURCE_GAMEPAD == InputDevice.SOURCE_GAMEPAD
                    || it.sources and InputDevice.SOURCE_JOYSTICK == InputDevice.SOURCE_JOYSTICK
        }

    inline val allUsbDevices: List<UsbDevice>
        get() = (context?.getSystemService(Context.USB_SERVICE) as? UsbManager)
                ?.deviceList?.values?.toList() ?: emptyList()


    @SuppressLint("SetTextI18n")
    private inline fun initializePhidgets() {
        Controller.initialize()

        Controller.doOnReady = {
            post {
                leftX_textView.text = "Ready!"
                leftY_textView.text = "Ready!"
                rightX_textView.text = "Ready!"
                rightY_textView.text = "Ready!"
            }
        }

        Controller.Sensors.LEFT_JOYSTICK_X.onChange = {
            post { leftX_textView.text = "x: $it" }
        }
        Controller.Sensors.LEFT_JOYSTICK_Y.onChange = {
            post { leftY_textView.text = "y: $it" }
        }
        Controller.Sensors.RIGHT_JOYSTICK_X.onChange = {
            post { rightX_textView.text = "x: $it" }
        }
        Controller.Sensors.RIGHT_JOYSTICK_Y.onChange = {
            post { rightY_textView.text = "y: $it" }
        }
    }

    inline fun post(crossinline block: () -> Unit) = mainActivity.post(block)

}
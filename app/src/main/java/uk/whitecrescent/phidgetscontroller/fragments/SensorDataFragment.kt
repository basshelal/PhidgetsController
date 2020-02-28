@file:Suppress("NOTHING_TO_INLINE")

package uk.whitecrescent.phidgetscontroller.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.phidgets.PhidgetException
import com.phidgets.usb.Manager
import kotlinx.android.synthetic.main.fragment_sensor_data.*
import uk.whitecrescent.phidgetscontroller.Controller
import uk.whitecrescent.phidgetscontroller.R

class SensorDataFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_sensor_data, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        Manager.Initialize(context)

        mainActivity.actionBarText = "Sensor Data"

        title_textView.text = if (mainActivity.isAllPhidgetsConnected) "Phidgets Ready!"
        else "No Phidgets Connected!"

        try {
            initializePhidgets()
        } catch (e: PhidgetException) {
            Log.e("ERROR", e.errorNumber.toString())
            Log.e("ERROR", e.description)
        }
    }


    @SuppressLint("SetTextI18n")
    private inline fun initializePhidgets() {
        Controller.initialize()

        Controller.doOnReady = {
            post {
                //  title_textView.text = "Phidgets Ready!"
            }
        }

        Controller.Sensors.LEFT_JOYSTICK_X.onChange = {
            post { leftX_textView.text = "left x: $it" }
        }
        Controller.Sensors.LEFT_JOYSTICK_Y.onChange = {
            post { leftY_textView.text = "left y: $it" }
        }
        Controller.Sensors.RIGHT_JOYSTICK_X.onChange = {
            post { rightX_textView.text = "right x: $it" }
        }
        Controller.Sensors.RIGHT_JOYSTICK_Y.onChange = {
            post { rightY_textView.text = "right y: $it" }
        }
    }

    inline fun post(crossinline block: () -> Unit) = mainActivity.post(block)

}

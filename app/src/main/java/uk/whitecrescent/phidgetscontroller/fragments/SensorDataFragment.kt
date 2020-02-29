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

        mainActivity.appBarText = "Sensor Data"

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

        Controller.doOnReady = { post { } }

        // JoySticks
        Controller.Sensors.LEFT_JOYSTICK_X.onChange = { post { leftX_sensorInfoView.valueText = "$it" } }
        Controller.Sensors.LEFT_JOYSTICK_Y.onChange = { post { leftY_sensorInfoView.valueText = "$it" } }
        Controller.Sensors.RIGHT_JOYSTICK_X.onChange = { post { rightX_sensorInfoView.valueText = "$it" } }
        Controller.Sensors.RIGHT_JOYSTICK_Y.onChange = { post { rightY_sensorInfoView.valueText = "$it" } }
        // Buttons
        Controller.Sensors.Y_BUTTON.onChange = { post { yButton_sensorInfoView.valueText = "$it" } }
        Controller.Sensors.B_BUTTON.onChange = { post { bButton_sensorInfoView.valueText = "$it" } }
        Controller.Sensors.A_BUTTON.onChange = { post { aButton_sensorInfoView.valueText = "$it" } }
        Controller.Sensors.X_BUTTON.onChange = { post { xButton_sensorInfoView.valueText = "$it" } }
        // DPad
        Controller.Sensors.UP_DPAD.onChange = { post { upButton_sensorInfoView.valueText = "$it" } }
        Controller.Sensors.RIGHT_DPAD.onChange = { post { rightButton_sensorInfoView.valueText = "$it" } }
        Controller.Sensors.DOWN_DPAD.onChange = { post { downButton_sensorInfoView.valueText = "$it" } }
        Controller.Sensors.LEFT_DPAD.onChange = { post { leftButton_sensorInfoView.valueText = "$it" } }
        // Triggers
        Controller.Sensors.RIGHT_TRIGGER.onChange = { post { rightTrigger_sensorInfoView.valueText = "$it" } }
        Controller.Sensors.LEFT_TRIGGER.onChange = { post { leftTrigger_sensorInfoView.valueText = "$it" } }
        // Extras
        Controller.Sensors.VOLUME_KNOB.onChange = { post { volumeKnob_sensorInfoView.valueText = "$it" } }
        Controller.Sensors.EXTRA.onChange = { post { extra_sensorInfoView.valueText = "$it" } }
    }

    inline fun post(crossinline block: () -> Unit) = mainActivity.post(block)

}

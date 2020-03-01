@file:Suppress("NOTHING_TO_INLINE")

package uk.whitecrescent.phidgetscontroller.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.phidgets.PhidgetException
import kotlinx.android.synthetic.main.fragment_sensor_data.*
import uk.whitecrescent.phidgetscontroller.Controller
import uk.whitecrescent.phidgetscontroller.R

class SensorDataFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_sensor_data, container, false)
    }

    override fun onShow() {
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

    override fun onHide() {
        Controller.Sensors.ALL.forEach { it.onChange = {} }
    }

    @SuppressLint("SetTextI18n")
    private inline fun initializePhidgets() {
        Controller.initialize()

        Controller.doOnReady = {
            title_textView.text = if (mainActivity.isAllPhidgetsConnected) "Phidgets Ready!"
            else "No Phidgets Connected!"
        }

        // JoySticks
        Controller.Sensors.LEFT_JOYSTICK_X.onChange = { leftX_sensorInfoView.valueText = "$it" }
        Controller.Sensors.LEFT_JOYSTICK_Y.onChange = { leftY_sensorInfoView.valueText = "$it" }
        Controller.Sensors.RIGHT_JOYSTICK_X.onChange = { rightX_sensorInfoView.valueText = "$it" }
        Controller.Sensors.RIGHT_JOYSTICK_Y.onChange = { rightY_sensorInfoView.valueText = "$it" }
        // Buttons
        Controller.Sensors.Y_BUTTON.onChange = { yButton_sensorInfoView.valueText = "$it" }
        Controller.Sensors.B_BUTTON.onChange = { bButton_sensorInfoView.valueText = "$it" }
        Controller.Sensors.A_BUTTON.onChange = { aButton_sensorInfoView.valueText = "$it" }
        Controller.Sensors.X_BUTTON.onChange = { xButton_sensorInfoView.valueText = "$it" }
        // DPad
        Controller.Sensors.UP_DPAD.onChange = { upButton_sensorInfoView.valueText = "$it" }
        Controller.Sensors.RIGHT_DPAD.onChange = { rightButton_sensorInfoView.valueText = "$it" }
        Controller.Sensors.DOWN_DPAD.onChange = { downButton_sensorInfoView.valueText = "$it" }
        Controller.Sensors.LEFT_DPAD.onChange = { leftButton_sensorInfoView.valueText = "$it" }
        // Triggers
        Controller.Sensors.RIGHT_TRIGGER.onChange = { rightTrigger_sensorInfoView.valueText = "$it" }
        Controller.Sensors.LEFT_TRIGGER.onChange = { leftTrigger_sensorInfoView.valueText = "$it" }
        // Extras
        Controller.Sensors.VOLUME_KNOB.onChange = { volumeKnob_sensorInfoView.valueText = "$it" }
        Controller.Sensors.EXTRA.onChange = { extra_sensorInfoView.valueText = "$it" }
    }

}

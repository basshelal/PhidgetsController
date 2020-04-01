@file:Suppress("NOTHING_TO_INLINE")

package uk.whitecrescent.phidgetscontroller.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.phidgets.PhidgetException
import kotlinx.android.synthetic.main.fragment_controller_view.*
import uk.whitecrescent.phidgetscontroller.Controller
import uk.whitecrescent.phidgetscontroller.F
import uk.whitecrescent.phidgetscontroller.R

class ControllerViewFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_controller_view, container, false)
    }

    override fun onShow() {
        mainActivity.appBarText = "Controller View"

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

    private inline fun initializePhidgets() {
        Controller.initialize()

        Controller.doOnReady = {
            title_textView.text =
                    if (mainActivity.isAllPhidgetsConnected) "All Phidgets Ready!"
                    else when (mainActivity.phidgetsConnections) {
                        1 -> "1/3 Phidgets Connected"
                        2 -> "2/3 Phidgets Connected"
                        else -> "No Phidgets Connected!"
                    }
        }

        // JoySticks
        Controller.Sensors.LEFT_JOYSTICK_X.onChange = { leftJoystick_joystickView.setPosition(x = it) }
        Controller.Sensors.LEFT_JOYSTICK_Y.onChange = { leftJoystick_joystickView.setPosition(y = it) }
        Controller.Sensors.RIGHT_JOYSTICK_X.onChange = { rightJoystick_joystickView.setPosition(x = it) }
        Controller.Sensors.RIGHT_JOYSTICK_Y.onChange = { rightJoystick_joystickView.setPosition(y = it) }
        // Buttons
        Controller.Sensors.Y_BUTTON.onChange = { yButton_circleButton.intensity = it.F / 1000F }
        Controller.Sensors.B_BUTTON.onChange = { bButton_circleButton.intensity = it.F / 1000F }
        Controller.Sensors.A_BUTTON.onChange = { aButton_circleButton.intensity = it.F / 1000F }
        Controller.Sensors.X_BUTTON.onChange = { xButton_circleButton.intensity = it.F / 1000F }
        // DPad
        Controller.Sensors.UP_DPAD.onChange = { upButton_dpadButton.intensity = it.F / 1000F }
        Controller.Sensors.RIGHT_DPAD.onChange = { rightButton_dpadButton.intensity = it.F / 1000F }
        Controller.Sensors.DOWN_DPAD.onChange = { downButton_dpadButton.intensity = it.F / 1000F }
        Controller.Sensors.LEFT_DPAD.onChange = { leftButton_dpadButton.intensity = it.F / 1000F }
        // Triggers
        Controller.Sensors.RIGHT_TRIGGER.onChange = { rightTrigger_triggerButton.intensity = it.F / 1000F }
        Controller.Sensors.LEFT_TRIGGER.onChange = { leftTrigger_triggerButton.intensity = it.F / 1000F }
        // Extras
        Controller.Sensors.VOLUME_KNOB.onChange = { volume_circleButton.intensity = it.F / 1000F }
        Controller.Sensors.OPTIONS.onChange = { options_circleButton.intensity = it.F / 1000F }
    }

}

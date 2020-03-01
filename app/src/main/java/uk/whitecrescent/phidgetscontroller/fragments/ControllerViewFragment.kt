package uk.whitecrescent.phidgetscontroller.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_controller_view.*
import uk.whitecrescent.phidgetscontroller.Controller
import uk.whitecrescent.phidgetscontroller.R

class ControllerViewFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_controller_view, container, false)
    }

    override fun onShow() {
        mainActivity.appBarText = "Controller View"

        Controller.initialize()

        // JoySticks
        Controller.Sensors.LEFT_JOYSTICK_X.onChange = {
            leftJoystick_joystickView.setPosition(x = it)
        }
        Controller.Sensors.LEFT_JOYSTICK_Y.onChange = {
            leftJoystick_joystickView.setPosition(y = it)
        }
        Controller.Sensors.RIGHT_JOYSTICK_X.onChange = {
            rightJoystick_joystickView.setPosition(x = it)
        }
        Controller.Sensors.RIGHT_JOYSTICK_Y.onChange = {
            rightJoystick_joystickView.setPosition(y = it)
        }
    }

    override fun onHide() {
        Controller.Sensors.ALL.forEach { it.onChange = {} }
    }

}

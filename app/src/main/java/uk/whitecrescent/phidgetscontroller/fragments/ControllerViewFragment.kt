package uk.whitecrescent.phidgetscontroller.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import uk.whitecrescent.phidgetscontroller.R

class ControllerViewFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_controller_view, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mainActivity.appBarText = "Controller View"
        /*val xArray = Array(100) { it }
        var current = 0
        timer(period = 150L) {
            mainActivity.shortSnackbar("x: ${xArray[current]}")
            leftJoystick_joystickView.setPosition(50, xArray[current])
            rightJoystick_joystickView.setPosition(50, xArray[current])
            if (current != xArray.lastIndex) current++ else current = 0
        }*/
    }

}

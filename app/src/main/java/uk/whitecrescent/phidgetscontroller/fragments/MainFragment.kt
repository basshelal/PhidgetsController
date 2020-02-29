@file:Suppress("NOTHING_TO_INLINE")

package uk.whitecrescent.phidgetscontroller.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_main.*
import uk.whitecrescent.phidgetscontroller.R

class MainFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val allUsbDevicesString = mainActivity.allUsbDevices.joinToString(separator = ", \n") { it.toString() }

        mainActivity.appBarText = "Phidgets Controller"

        mainActivity.landscape = false
        mainActivity.setFullScreen(false)

        controllerView_button.setOnClickListener {
            mainActivity.landscape = true
            mainActivity.showFragment(ControllerViewFragment())
        }

        sensorDataView_button.setOnClickListener {
            mainActivity.showFragment(SensorDataFragment())
        }

        demoGame_button.setOnClickListener {
            mainActivity.landscape = true
            mainActivity.showFragment(GameFragment())
        }
    }


}
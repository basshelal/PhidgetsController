package uk.whitecrescent.phidgetscontroller

import com.phidgets.InterfaceKitPhidget
import com.phidgets.SpatialPhidget

// Represents a Sensor
class Sensor(val index: Int) {
    var onChange: (value: Int) -> Unit = { }
    var value: Int = 0
        set(value) {
            field = value
            onChange(value)
        }
}

object Controller {

    // The sensors attached to the Controller
    // Ensure that the indexes are the same ones on the device!!!
    object Sensors {
        val LEFT_JOYSTICK_X = Sensor(0)
        val LEFT_JOYSTICK_Y = Sensor(1)
        val RIGHT_JOYSTICK_X = Sensor(2)
        val RIGHT_JOYSTICK_Y = Sensor(3)
        val FORCE_1 = Sensor(4) // Y Button
        val FORCE_2 = Sensor(5) // B Button
        val FORCE_3 = Sensor(6) // A Button
        val FORCE_4 = Sensor(7) // X Button
        val TOUCH_1 = Sensor(8) // DPad Up
        val TOUCH_2 = Sensor(9) // DPad Right
        val TOUCH_3 = Sensor(10) // DPad Down
        val TOUCH_4 = Sensor(11) // DPad Left
        val ROTATION = Sensor(12) // Volume Knob
        val TRIGGER_L = Sensor(13) // Left Trigger
        val TRIGGER_R = Sensor(14) // Right Trigger
        val EXTRA = Sensor(15) // We have space for one more sensor!

        val ALL = listOf(
                LEFT_JOYSTICK_X,
                LEFT_JOYSTICK_Y,
                RIGHT_JOYSTICK_X,
                RIGHT_JOYSTICK_Y,
                FORCE_1,
                FORCE_2,
                FORCE_3,
                FORCE_4,
                TOUCH_1,
                TOUCH_2,
                TOUCH_3,
                TOUCH_4,
                ROTATION,
                TRIGGER_L,
                TRIGGER_R,
                EXTRA
        ).sortedBy { it.index }
    }

    object Serials {
        const val INTERFACE_KIT_LARGE = 30406
        const val INTERFACE_KIT_SMALL = 443313
        const val SPATIAL = 296234
    }

    // There are 3 independent components, InterfaceKits contain the sensors
    val smallInterfaceKit = InterfaceKitPhidget()
    val largeInterfaceKit = InterfaceKitPhidget()
    val spatial = SpatialPhidget()
    // TODO: 24-Feb-20 Make a nice abstraction for the Spatial

    inline val allSensorData: List<Int> get() = Sensors.ALL.map { it.value }

    var doOnReady: () -> Unit = {}
    private val componentsReady = mutableListOf(false, false, false)
    private var onReadyInvoked = false

    fun initialize() {
        largeInterfaceKit.open(Serials.INTERFACE_KIT_LARGE)
        smallInterfaceKit.open(Serials.INTERFACE_KIT_SMALL)
        spatial.open(Serials.SPATIAL)

        largeInterfaceKit.addSensorChangeListener {

            Sensors.ALL[it.index].value = it.value
        }
        smallInterfaceKit.addSensorChangeListener {
            Sensors.ALL[it.index + 7].value = it.value
        }

        fun invokeReady() {
            if (componentsReady.all { true } && !onReadyInvoked) {
                onReadyInvoked = true
                doOnReady()
            }
        }

        largeInterfaceKit.addAttachListener {
            componentsReady[0] = true
            invokeReady()
        }
        smallInterfaceKit.addAttachListener {
            componentsReady[1] = true
            invokeReady()
        }
        spatial.addAttachListener {
            componentsReady[2] = true
            invokeReady()
        }
    }

    fun finalize() {
        smallInterfaceKit.close()
        largeInterfaceKit.close()
        spatial.close()
    }
}

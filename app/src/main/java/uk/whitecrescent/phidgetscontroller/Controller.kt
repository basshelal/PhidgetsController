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
        val Y_BUTTON = Sensor(4)
        val B_BUTTON = Sensor(5)
        val A_BUTTON = Sensor(6)
        val X_BUTTON = Sensor(7)
        val UP_DPAD = Sensor(8)
        val RIGHT_DPAD = Sensor(9)
        val DOWN_DPAD = Sensor(10)
        val LEFT_DPAD = Sensor(11)
        val LEFT_TRIGGER = Sensor(12)
        val RIGHT_TRIGGER = Sensor(13)
        val VOLUME_KNOB = Sensor(14)
        val EXTRA = Sensor(15) // Unsure but this should probably be a menu button?

        val ALL = listOf(
                LEFT_JOYSTICK_X,
                LEFT_JOYSTICK_Y,
                RIGHT_JOYSTICK_X,
                RIGHT_JOYSTICK_Y,
                Y_BUTTON,
                B_BUTTON,
                A_BUTTON,
                X_BUTTON,
                UP_DPAD,
                RIGHT_DPAD,
                DOWN_DPAD,
                LEFT_DPAD,
                LEFT_TRIGGER,
                RIGHT_TRIGGER,
                VOLUME_KNOB,
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
            Sensors.ALL[it.index + 8].value = it.value
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

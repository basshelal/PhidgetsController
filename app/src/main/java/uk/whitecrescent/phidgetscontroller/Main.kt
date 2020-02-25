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
        val FORCE_1 = Sensor(4)
        val FORCE_2 = Sensor(5)
        val FORCE_3 = Sensor(6)
        val FORCE_4 = Sensor(7)
        val TOUCH_1 = Sensor(8)
        val TOUCH_2 = Sensor(9)
        val TOUCH_3 = Sensor(10)
        val TOUCH_4 = Sensor(11)
        val ROTATION_1 = Sensor(12)
        val ROTATION_2 = Sensor(13)
        val SLIDER = Sensor(14)

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
                ROTATION_1,
                ROTATION_2,
                SLIDER
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

    fun finalize_() {
        smallInterfaceKit.close()
        largeInterfaceKit.close()
        spatial.close()
    }
}

// For testing shit on JVM not on Android!
fun main() {

    Controller.initialize()

    Controller.doOnReady = {

    }

    Controller.Sensors.LEFT_JOYSTICK_X.onChange = {
        println("x: $it")
    }
    Controller.Sensors.LEFT_JOYSTICK_Y.onChange = {
        println("y: $it")
    }

    // Better than while(true) ^_^
    Thread.sleep(Long.MAX_VALUE)
}
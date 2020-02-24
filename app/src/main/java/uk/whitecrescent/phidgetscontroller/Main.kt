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
    object Sensors {
        val LEFT_JOYSTICK_X = Sensor(0)
        val LEFT_JOYSTICK_Y = Sensor(0)
        val RIGHT_JOYSTICK_X = Sensor(0)
        val RIGHT_JOYSTICK_Y = Sensor(0)
        val FORCE_1 = Sensor(0)
        val FORCE_2 = Sensor(0)
        val FORCE_3 = Sensor(0)
        val FORCE_4 = Sensor(0)
        val TOUCH_1 = Sensor(0)
        val TOUCH_2 = Sensor(0)
        val TOUCH_3 = Sensor(0)
        val TOUCH_4 = Sensor(0)
        val ROTATION_1 = Sensor(0)
        val ROTATION_2 = Sensor(0)
        val SLIDER = Sensor(0)

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
        )
    }

    object Serials {
        const val INTERFACE_KIT_LARGE = 30406
        const val INTERFACE_KIT_SMALL = 443313
        const val SPATIAL = 296234
    }

    val smallInterfaceKit = InterfaceKitPhidget()
    val largeInterfaceKit = InterfaceKitPhidget()
    val spatial = SpatialPhidget()
    // TODO: 24-Feb-20 Make a nice abstraction for the Spatial

    val allSensorData: List<Int> get() = Sensors.ALL.map { it.value }

    var doOnReady: () -> Unit = {}
    private val componentsReady = mutableListOf(false, false, false)
    private val onReadyInvoked = false

    fun initialize() {
        smallInterfaceKit.open(Serials.INTERFACE_KIT_SMALL)
        largeInterfaceKit.open(Serials.INTERFACE_KIT_LARGE)
        spatial.open(Serials.SPATIAL)

        smallInterfaceKit.addSensorChangeListener {
            Sensors.ALL[it.index].value = it.value
        }
        largeInterfaceKit.addSensorChangeListener {
            Sensors.ALL[it.index + 7].value = it.value
        }

        smallInterfaceKit.addAttachListener {
            componentsReady[0] = true
            if (componentsReady.all { true } && !onReadyInvoked) doOnReady()
        }
        largeInterfaceKit.addAttachListener {
            componentsReady[1] = true
            if (componentsReady.all { true } && !onReadyInvoked) doOnReady()
        }
        spatial.addAttachListener {
            componentsReady[2] = true
            if (componentsReady.all { true } && !onReadyInvoked) doOnReady()
        }
    }

    fun finalize_() {
        smallInterfaceKit.close()
        largeInterfaceKit.close()
        spatial.close()
    }
}

fun main() {

    val interfaceKitSmall = InterfaceKitPhidget()
    val interfaceKitLarge = InterfaceKitPhidget()
    val spatial = SpatialPhidget()

    interfaceKitSmall.open(Controller.Serials.INTERFACE_KIT_SMALL)
    interfaceKitLarge.open(Controller.Serials.INTERFACE_KIT_LARGE)

    spatial.open(Controller.Serials.SPATIAL)

    Thread.sleep(500)

    spatial.dataRate = 500

    spatial.addSpatialDataListener {
        println(it.data.first().timeSeconds)
    }

    interfaceKitSmall.addSensorChangeListener {
        println("${it.source} ${it.index} ${it.value}")
    }
    interfaceKitLarge.addSensorChangeListener {
        println("${it.source} ${it.index} ${it.value}")
    }

    // Better than while(true) ^_^
    Thread.sleep(Long.MAX_VALUE)
}
package uk.whitecrescent.phidgetscontroller

import com.phidgets.InterfaceKitPhidget

// Serials
const val INTERFACE_KIT_LARGE = 30406
const val INTERFACE_KIT_SMALL = 443313

fun main() {

    val interfaceKitSmall = InterfaceKitPhidget()
    val interfaceKitLarge = InterfaceKitPhidget()

    // TODO: 24-Feb-20 We need to hardcode the sensor indexes because Phidgets cannot know the type
    //  of sensor we are using, so we have to set them out and agree on them before hand

    interfaceKitSmall.open(INTERFACE_KIT_SMALL)
    interfaceKitLarge.open(INTERFACE_KIT_LARGE)
    interfaceKitSmall.waitForAttachment()
    interfaceKitLarge.waitForAttachment()

    interfaceKitSmall.addSensorChangeListener {
        println("${it.source} ${it.index} ${it.value}")
    }
    interfaceKitLarge.addSensorChangeListener {
        println("${it.source} ${it.index} ${it.value}")
    }


    Thread.sleep(Long.MAX_VALUE)
}
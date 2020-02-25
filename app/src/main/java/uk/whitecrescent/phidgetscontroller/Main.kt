package uk.whitecrescent.phidgetscontroller

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
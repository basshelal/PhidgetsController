package uk.whitecrescent.phidgetscontroller

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.phidgets.PhidgetException
import com.phidgets.usb.Manager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Manager.Initialize(this)

        run { x_textView.text = "No Phidgets!" }
        run { y_textView.text = "No Phidgets!" }

        try {

            Controller.initialize()

            Controller.doOnReady = {
                run { x_textView.text = "Ready!" }
                run { y_textView.text = "Ready!" }
            }

            Controller.Sensors.LEFT_JOYSTICK_X.onChange = {
                run { x_textView.text = "x: $it" }
            }
            Controller.Sensors.LEFT_JOYSTICK_Y.onChange = {
                run { y_textView.text = "y: $it" }
            }
        } catch (e: PhidgetException) {
            run { x_textView.text = "Error Loading Phidgets Library!" }
            run { y_textView.text = "Error Loading Phidgets Library!" }
            Log.e("ERROR", e.errorNumber.toString())
            Log.e("ERROR", e.description)
        }


        // Check if the controller is connected through USB
        // read here https://developer.android.com/guide/topics/connectivity/usb/host

        // Android game controller shit is here https://developer.android.com/training/game-controllers
    }

    // Use this to circumnavigate errors when touching Views from a thread that isn't it's own
    inline fun run(crossinline block: () -> Unit) {
        root_constraintLayout.post { block() }
    }
}

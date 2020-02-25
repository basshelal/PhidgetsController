package uk.whitecrescent.phidgetscontroller

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.phidgets.PhidgetException
import com.phidgets.usb.Manager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Manager.Initialize(this)

        try {

            Controller.initialize()

            Controller.doOnReady = {


                x_textView.post {
                    x_textView.text = "HEWLOO"
                }

            }

            Controller.Sensors.LEFT_JOYSTICK_X.onChange = {
                x_textView.post {
                    x_textView.text = "x: $it"
                }
            }
            Controller.Sensors.LEFT_JOYSTICK_Y.onChange = {
                y_textView.post {
                    y_textView.text = "y: $it"
                }
            }
        } catch (e: PhidgetException) {
            Log.e("ASS", e.errorNumber.toString())
            Log.e("ASS", e.description.toString())
        }


        // Check if the controller is connected through USB
        // read here https://developer.android.com/guide/topics/connectivity/usb/host

        // Android game controller shit is here https://developer.android.com/training/game-controllers
    }
}

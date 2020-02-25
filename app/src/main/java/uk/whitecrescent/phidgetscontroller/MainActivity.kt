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

        run {
            leftX_textView.text = "No Phidgets!"
            leftY_textView.text = "No Phidgets!"
            rightX_textView.text = "No Phidgets!"
            rightY_textView.text = "No Phidgets!"
        }

        try {

            Controller.initialize()

            Controller.doOnReady = {
                run {
                    leftX_textView.text = "Ready!"
                    leftY_textView.text = "Ready!"
                    rightX_textView.text = "Ready!"
                    rightY_textView.text = "Ready!"
                }
            }

            Controller.Sensors.LEFT_JOYSTICK_X.onChange = {
                run {
                    leftX_textView.text = "x: $it"
                }
            }
            Controller.Sensors.LEFT_JOYSTICK_Y.onChange = {
                run { leftY_textView.text = "y: $it" }
            }
            Controller.Sensors.RIGHT_JOYSTICK_X.onChange = {
                run { rightX_textView.text = "x: $it" }
            }
            Controller.Sensors.RIGHT_JOYSTICK_Y.onChange = {
                run { rightY_textView.text = "y: $it" }
            }
        } catch (e: PhidgetException) {
            run {
                leftX_textView.text = "Error Loading Phidgets Library!"
                leftY_textView.text = "Error Loading Phidgets Library!"
                rightX_textView.text = "Error Loading Phidgets Library!"
                rightY_textView.text = "Error Loading Phidgets Library!"
            }
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

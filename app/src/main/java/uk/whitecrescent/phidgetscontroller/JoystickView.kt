package uk.whitecrescent.phidgetscontroller

import android.content.Context
import android.util.AttributeSet
import me.caibou.rockerview.JoystickView

class JoystickView
@JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : JoystickView(context, attrs, defStyleAttr) {

    fun setX(x: Int) {

    }

    fun setY(y: Int) {

    }

    fun setPosition(x: Int, y: Int) {
        setX(x)
        setY(y)
    }

    fun calculateAngle(dx: Float, dy: Float): Double {
        val degrees = Math.toDegrees(Math.atan2(dy.toDouble(), dx.toDouble()))
        return if (degrees < 0) Math.floor(degrees + 360) else degrees
    }

}
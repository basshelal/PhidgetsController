package uk.whitecrescent.phidgetscontroller.views

import android.content.Context
import android.util.AttributeSet
import me.caibou.rockerview.JoystickView
import uk.whitecrescent.phidgetscontroller.D
import uk.whitecrescent.phidgetscontroller.F

class JoystickView
@JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : JoystickView(context, attrs, defStyleAttr) {

    fun setPosition(x: Int, y: Int) {
        val x_ = x.F * (this.width.F / 100.F)
        val y_ = y.F * (this.height.F / 100.F)
        val angle = calculateAngle((x_ - centerPoint().x).F, (y_ - centerPoint().y).F)
        actionMove(x_, y_, angle)
    }

    fun calculateAngle(dx: Float, dy: Float): Double {
        val degrees = Math.toDegrees(Math.atan2(dy.D, dx.D))
        return if (degrees < 0) Math.floor(degrees + 360) else degrees
    }

}
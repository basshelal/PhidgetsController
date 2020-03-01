package uk.whitecrescent.phidgetscontroller.views

import android.content.Context
import android.util.AttributeSet
import androidx.annotation.IntRange
import me.caibou.rockerview.JoystickView
import uk.whitecrescent.phidgetscontroller.D
import uk.whitecrescent.phidgetscontroller.F

class JoystickView
@JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : JoystickView(context, attrs, defStyleAttr) {

    private var lastX: Int = 0
    private var lastY: Int = 0

    fun setPosition(@IntRange(from = 0, to = 1000) x: Int = lastX,
                    @IntRange(from = 0, to = 1000) y: Int = lastY) {
        val x_ = x.F * (this.width.F / 1000.F)
        val y_ = (y.F * (this.height.F / -1000.F)) + this.height.F
        val angle = calculateAngle((x_ - centerPoint().x).F, (y_ - centerPoint().y).F)
        actionMove(x_, y_, angle)
        lastX = x
        lastY = y
    }

    private fun calculateAngle(dx: Float, dy: Float): Double {
        val degrees = Math.toDegrees(Math.atan2(dy.D, dx.D))
        return if (degrees < 0) Math.floor(degrees + 360) else degrees
    }

}
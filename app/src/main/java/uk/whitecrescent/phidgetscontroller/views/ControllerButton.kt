package uk.whitecrescent.phidgetscontroller.views

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ShapeDrawable
import android.util.AttributeSet
import android.view.View
import androidx.annotation.FloatRange
import org.jetbrains.anko.backgroundColor
import uk.whitecrescent.phidgetscontroller.R
import uk.whitecrescent.phidgetscontroller.getColorCompat
import kotlin.concurrent.timer
import kotlin.math.roundToInt

// TODO: 29-Feb-20 Button needs an outline that is always active even if the intensity is 0
open class ControllerButton
@JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    val shapeDrawable = ShapeDrawable()

    init {
        background = shapeDrawable
        backgroundColor = context.getColorCompat(R.color.colorPrimary)
        setIntensity(1F)
    }

    fun startSimulation() {
        val array = Array(100) { it }
        var current = 0
        timer(period = 32L) {
            setIntensity(array[current] / 100F)
            if (current != array.lastIndex) current++ else current = 0
        }
    }

    fun setIntensity(@FloatRange(from = 0.0, to = 1.0) intensity: Float) {
        shapeDrawable.alpha = (intensity * 255F).roundToInt()
    }

    override fun setBackgroundColor(color: Int) {
        super.setBackgroundColor(Color.TRANSPARENT)
        background = shapeDrawable.apply {
            setTint(color)
            elevation = 16F
        }
    }
}
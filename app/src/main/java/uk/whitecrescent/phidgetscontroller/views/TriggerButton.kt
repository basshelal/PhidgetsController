package uk.whitecrescent.phidgetscontroller.views

import android.content.Context
import android.graphics.drawable.shapes.RoundRectShape
import android.util.AttributeSet

class TriggerButton
@JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ControllerButton(context, attrs, defStyleAttr) {

    init {
        val outer = FloatArray(8) { 32F }
        shapeDrawable.shape = RoundRectShape(outer, null, null)
    }
}
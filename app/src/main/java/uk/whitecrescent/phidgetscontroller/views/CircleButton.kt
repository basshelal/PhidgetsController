package uk.whitecrescent.phidgetscontroller.views

import android.content.Context
import android.graphics.drawable.shapes.OvalShape
import android.util.AttributeSet

class CircleButton
@JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ControllerButton(context, attrs, defStyleAttr) {

    init {
        shapeDrawable.shape = OvalShape()
    }
}
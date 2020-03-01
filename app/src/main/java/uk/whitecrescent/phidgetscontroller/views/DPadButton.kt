package uk.whitecrescent.phidgetscontroller.views

import android.content.Context
import android.graphics.drawable.shapes.RectShape
import android.util.AttributeSet

class DPadButton
@JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ControllerButton(context, attrs, defStyleAttr) {

    init {
        shapeDrawable.shape = RectShape()
    }
}

package uk.whitecrescent.phidgetscontroller.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.github.florent37.shapeofview.shapes.RoundRectView
import kotlinx.android.synthetic.main.view_dpad_button.view.*
import uk.whitecrescent.phidgetscontroller.R

class DPadButton
@JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RoundRectView(context, attrs, defStyleAttr) {

    inline var intensity: Float
        set(value) {
            button_colorView.alpha = value
        }
        get() = button_colorView.alpha

    init {
        View.inflate(context, R.layout.view_dpad_button, this)
        this.elevation = 8F
        this.intensity = 0F
    }
}

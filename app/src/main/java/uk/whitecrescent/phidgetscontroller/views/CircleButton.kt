package uk.whitecrescent.phidgetscontroller.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.github.florent37.shapeofview.shapes.CircleView
import kotlinx.android.synthetic.main.view_circle_button.view.*
import uk.whitecrescent.phidgetscontroller.R

class CircleButton
@JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : CircleView(context, attrs, defStyleAttr) {

    inline var intensity: Float
        set(value) {
            button_colorView.alpha = value
        }
        get() = button_colorView.alpha

    init {
        View.inflate(context, R.layout.view_circle_button, this)
        this.elevation = 8F
    }
}
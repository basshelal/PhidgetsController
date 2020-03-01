@file:Suppress("NOTHING_TO_INLINE", "OVERRIDE_BY_INLINE")

package uk.whitecrescent.waqti.frontend.customview

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapeAppearanceModel
import kotlinx.android.synthetic.main.view_appbar.view.*
import org.jetbrains.anko.textColor
import uk.whitecrescent.phidgetscontroller.R
import uk.whitecrescent.phidgetscontroller.convertDpToPx
import uk.whitecrescent.phidgetscontroller.getColorCompat

class AppBar
@JvmOverloads constructor(
        context: Context,
        attributeSet: AttributeSet? = null,
        defStyle: Int = 0
) : FrameLayout(context, attributeSet, defStyle) {

    companion object {
        const val DEFAULT_ELEVATION = 32F
        const val CORNER_RADIUS = 12
    }

    private val materialShapeDrawable = MaterialShapeDrawable(context, attributeSet, defStyle, 0)

    inline val textView: TextView get() = appBar_textView

    init {
        View.inflate(context, R.layout.view_appbar, this)

        elevation = DEFAULT_ELEVATION

        setBackgroundColor(context.getColorCompat(R.color.colorPrimary))

        textView.textColor = Color.WHITE

        roundedCorners()
    }


    private inline fun roundedCorners() {
        materialShapeDrawable.apply {
            shapeAppearanceModel = ShapeAppearanceModel.Builder()
                    .setBottomLeftCorner(CornerFamily.ROUNDED, context.convertDpToPx(CORNER_RADIUS))
                    .setBottomRightCorner(CornerFamily.ROUNDED, context.convertDpToPx(CORNER_RADIUS))
                    .build()
        }
    }

    override fun setBackgroundColor(color: Int) {
        super.setBackgroundColor(Color.TRANSPARENT)
        background = materialShapeDrawable.apply {
            setTint(color)
            elevation = DEFAULT_ELEVATION
        }
    }

}
package uk.whitecrescent.phidgetscontroller.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import org.jetbrains.anko.backgroundColor
import uk.whitecrescent.phidgetscontroller.R
import uk.whitecrescent.phidgetscontroller.getColorCompat

class ColorView
@JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    init {
        backgroundColor = context.getColorCompat(R.color.colorPrimary)
    }

}
package uk.whitecrescent.phidgetscontroller

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import kotlinx.android.synthetic.main.view_sensor_info.view.*

class SensorInfoView
@JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    inline var labelText: String
        set(value) {
            sensorLabel_textView.text = value
        }
        get() = sensorLabel_textView.text.toString()

    inline var valueText: String
        set(value) {
            sensorValue_textView.text = value
        }
        get() = sensorValue_textView.text.toString()

    init {
        View.inflate(context, R.layout.view_sensor_info, this)

        context.obtainStyledAttributes(attrs, R.styleable.SensorInfoView).apply {
            labelText = getString(R.styleable.SensorInfoView_labelText) ?: ""
            valueText = getString(R.styleable.SensorInfoView_valueText) ?: ""
        }.recycle()

        valueText = "0000"

    }
}
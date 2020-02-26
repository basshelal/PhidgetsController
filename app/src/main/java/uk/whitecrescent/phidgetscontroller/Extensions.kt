@file:Suppress("NOTHING_TO_INLINE")

package uk.whitecrescent.phidgetscontroller

import android.util.Log

// Number extensions
inline val Number.I: Int get() = this.toInt()
inline val Number.D: Double get() = this.toDouble()
inline val Number.F: Float get() = this.toFloat()
inline val Number.L: Long get() = this.toLong()

// Logging
inline fun logE(any: Any?) = Log.e("DEFAULT", any.toString())
package uk.whitecrescent.phidgetscontroller.fragments

import androidx.fragment.app.Fragment
import uk.whitecrescent.phidgetscontroller.MainActivity

abstract class BaseFragment : Fragment() {
    inline val mainActivity: MainActivity get() = activity as MainActivity

    abstract fun onHide()

    abstract fun onShow()
}
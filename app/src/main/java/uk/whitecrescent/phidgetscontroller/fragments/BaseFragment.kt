package uk.whitecrescent.phidgetscontroller.fragments

import androidx.fragment.app.Fragment
import uk.whitecrescent.phidgetscontroller.MainActivity

open class BaseFragment : Fragment() {
    inline val mainActivity: MainActivity get() = activity as MainActivity
}
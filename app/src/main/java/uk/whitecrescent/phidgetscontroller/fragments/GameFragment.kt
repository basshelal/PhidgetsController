package uk.whitecrescent.phidgetscontroller.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import uk.whitecrescent.phidgetscontroller.R

class GameFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_game, container, false)
    }

    override fun onShow() {
        mainActivity.appBarText = "Demo Game"

        //mainActivity.setFullScreen(true)
    }

    override fun onHide() {

    }

}

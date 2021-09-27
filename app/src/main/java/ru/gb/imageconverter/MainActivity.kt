package ru.gb.imageconverter

import android.os.Bundle
import com.github.terrakok.cicerone.androidx.AppNavigator
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter
import java.io.File

class MainActivity : MvpAppCompatActivity(), MainView {

    val navigator = AppNavigator(this, R.id.container)

    private val mainPresenter by moxyPresenter { MainPresenter (CiceroneObject.router, Screens()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        CiceroneObject.navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        CiceroneObject.navigatorHolder.removeNavigator()
    }
}
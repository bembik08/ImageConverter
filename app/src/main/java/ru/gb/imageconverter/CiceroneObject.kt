package ru.gb.imageconverter

import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Router

object CiceroneObject {
    private val cicerone: Cicerone<Router> by lazy { Cicerone.create() }

    val navigatorHolder get() = cicerone.getNavigatorHolder()
    val router get() = cicerone.router
}
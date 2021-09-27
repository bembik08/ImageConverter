package ru.gb.imageconverter

import com.github.terrakok.cicerone.Screen
import com.github.terrakok.cicerone.androidx.FragmentScreen

class Screens: Screen {
    fun convertScreen() = FragmentScreen { ConvertFragment.newInstance() }
}
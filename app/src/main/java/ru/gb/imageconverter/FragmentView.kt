package ru.gb.imageconverter

import android.net.Uri
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface FragmentView : MvpView {
    fun setOriginalImage(selectedFile: Uri?)
    fun setConvertedToPNGImage(newImage: Uri)
    fun setProgressBar()
    fun setCancelButton()
    fun setOnErrorReturn(throwable: Throwable)
}
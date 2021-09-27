package ru.gb.imageconverter

import moxy.MvpPresenter
import android.graphics.Bitmap
import android.net.Uri
import io.reactivex.rxjava3.disposables.CompositeDisposable

class ConvertFragmentPresenter : MvpPresenter<FragmentView>() {

    val disposable = CompositeDisposable()

    override fun onDestroy() {
        disposable.dispose()
    }
}
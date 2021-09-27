package ru.gb.imageconverter

import android.graphics.Bitmap
import android.net.Uri
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import moxy.MvpPresenter

class ConvertFragmentPresenter : MvpPresenter<FragmentView>() {

    val disposable = CompositeDisposable()

    fun setPickedImage(selectedFile: Uri?) {
        viewState.setOriginalImage(selectedFile)
    }

    fun convertAndSaveNewImage(image: Bitmap, path: String?) {
        disposable.addAll(
            Converter.convertImage(image, path)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ convertedImage ->
                    viewState.setConvertedImage(Uri.parse(convertedImage.absolutePath))
                }, { error ->
                    println(error.message)
                })
        )
    }

    override fun onDestroy() {
        disposable.dispose()
    }
}
package ru.gb.imageconverter

import android.graphics.Bitmap
import android.net.Uri
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.CompositeDisposable
import java.io.File
import moxy.MvpPresenter

class ConvertFragmentPresenter(
    private val ioScheduler: Scheduler,
    private val mainThread: Scheduler
) : MvpPresenter<FragmentView>() {

    private val disposable = CompositeDisposable()

    fun setPickedImage(selectedFile: Uri?) {
        viewState.setOriginalImage(selectedFile)
    }

    fun convertAndSaveNewImage(image: Bitmap, path: String?) {
        setProgressBarAndCancelButton()
        disposable.addAll(
            Converter.convertImage(image, path)
                .subscribeOn(ioScheduler)
                .observeOn(mainThread)
                .subscribe(
                    { setConvertedImage(it) },
                    { onReturnError(it) }
                )
        )
    }

    fun onCancelButtonCLicked(){
        Converter.cancel()
        setProgressBarAndCancelButton()
    }

    private fun setConvertedImage (image : File) {
        viewState.setConvertedToPNGImage(Uri.parse(image.absolutePath))
        setProgressBarAndCancelButton()
    }

    private fun onReturnError(throwable: Throwable) {
        viewState.setOnErrorReturn(throwable)
    }

    private fun setProgressBarAndCancelButton(){
        viewState.setProgressBar()
        viewState.setCancelButton()
    }

    override fun onDestroy() {
        disposable.dispose()
    }
}
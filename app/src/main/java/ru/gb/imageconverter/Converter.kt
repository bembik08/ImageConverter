package ru.gb.imageconverter

import android.graphics.Bitmap
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.core.SingleOnSubscribe
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

object Converter {

    private var counter = 1

    private lateinit var stream: OutputStream

    fun convertImage(image: Bitmap, path: String?): Single<File> {
        return Single.create(SingleOnSubscribe {
            if (it.isDisposed) return@SingleOnSubscribe
            val newImage = File(path, "NewImage${counter}.png")
            stream = FileOutputStream(newImage)
            if (image.compress(Bitmap.CompressFormat.PNG, 100, stream)) {
                it.onSuccess(newImage)
                counter++
            } else
                it.onError(Exception("Conversion problem"))
            stream.flush()
            stream.close()
        })
    }

    fun cancel () {
        stream.flush()
        stream.close()
    }
}
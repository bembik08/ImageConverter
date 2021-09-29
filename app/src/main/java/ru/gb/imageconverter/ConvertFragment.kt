package ru.gb.imageconverter

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.Intent.ACTION_GET_CONTENT
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import by.kirich1409.viewbindingdelegate.viewBinding
import androidx.fragment.app.Fragment
import ru.gb.imageconverter.databinding.FragmentConvertBinding
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_convert.*
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter

class ConvertFragment : MvpAppCompatFragment(R.layout.fragment_convert), FragmentView {
    companion object {
        fun newInstance(): Fragment = ConvertFragment()
        var selectedFile: Uri? = null
        var path: String? = null
    }

    private val presenter by moxyPresenter {
        ConvertFragmentPresenter(
            Schedulers.io(),
            AndroidSchedulers.mainThread()
        )
    }

    private val viewBinding: FragmentConvertBinding by viewBinding()
    private var progressBarOn = false
    private var cancelButtonActive = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.convertButton.setOnClickListener {
            try {
                val bitmap = (imageView.drawable as BitmapDrawable).bitmap
                presenter.convertAndSaveNewImage(bitmap, path)
                Toast.makeText(activity, "Wait a bit: converting new image...", Toast.LENGTH_SHORT)
                    .show()
            } catch (e : NullPointerException) {
                Toast.makeText(activity, "Wrong type of file", Toast.LENGTH_SHORT).show()
            }
        }

        viewBinding.cancelButton.setOnClickListener {
            presenter.onCancelButtonCLicked()
        }

        viewBinding.selectButton.setOnClickListener {
            val intent = Intent(ACTION_GET_CONTENT)
                .setType("image/*")
            startActivityForResult(intent, 111)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 111 && resultCode == RESULT_OK) {
            selectedFile = data?.data
            path = context?.filesDir?.absolutePath
            presenter.setPickedImage(selectedFile)
        }
    }

    override fun setOriginalImage(selectedFile: Uri?) {
        viewBinding.imageView.setImageURI(selectedFile)
        Toast.makeText(context, "New image was set from presenter", Toast.LENGTH_SHORT).show()
    }

    override fun setConvertedToPNGImage(newImage: Uri) {
        imageView2.setImageURI(newImage)
        Toast.makeText(context, "Image was converted to PNG and saved at $path", Toast.LENGTH_LONG)
            .show()
    }

    override fun setProgressBar() {
        if (!progressBarOn) {
            viewBinding.progressBar.visibility = View.VISIBLE
            progressBarOn = true
        } else {
            viewBinding.progressBar.visibility = View.INVISIBLE
            progressBarOn = false
        }
    }

    override fun setCancelButton() {
        if (!cancelButtonActive) {
            viewBinding.cancelButton.visibility = View.VISIBLE
            cancelButtonActive = true
        } else {
            viewBinding.cancelButton.visibility = View.INVISIBLE
            cancelButtonActive = false
        }
    }

    override fun setOnErrorReturn(throwable: Throwable) {
        Toast.makeText(context, "Converting was canceled due to $throwable", Toast.LENGTH_LONG)
            .show()
    }
}
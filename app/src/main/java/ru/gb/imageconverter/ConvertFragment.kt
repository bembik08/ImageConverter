package ru.gb.imageconverter

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_convert.*
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter

class ConvertFragment : MvpAppCompatFragment(), FragmentView {
    companion object {
        fun newInstance() = ConvertFragment()
        var selectedFile: Uri? = null
        var path: String? = null
    }

    private val presenter by moxyPresenter { ConvertFragmentPresenter() }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_convert, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        convertButton.setOnClickListener {
            try {
                val bitmap = (imageView.drawable as BitmapDrawable).bitmap
                presenter.convertAndSaveNewImage(bitmap, path)
                Toast.makeText(activity, "Wait a bit: converting new image...", Toast.LENGTH_SHORT)
                    .show()
            } catch (e : NullPointerException) {
                Toast.makeText(activity, "Wrong type of file", Toast.LENGTH_SHORT).show()
            }
        }

        selectButton.setOnClickListener {
            val intent = Intent()
                .setType("*/*")
                .setAction(Intent.ACTION_GET_CONTENT)
            startActivityForResult(Intent.createChooser(intent, "Select a file"), 111)
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
        imageView.setImageURI(selectedFile)
        Toast.makeText(context, "New image was set from presenter", Toast.LENGTH_SHORT).show()
    }

    override fun setConvertedImage(newImage: Uri) {
        imageView2.setImageURI(newImage)
        Toast.makeText(context, "Image was converted to PNG and saved at $path", Toast.LENGTH_LONG)
            .show()
    }
}
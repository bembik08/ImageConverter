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

class ConvertFragment : MvpAppCompatFragment (), FragmentView {

    companion object {
        fun newInstance() = ConvertFragment()
        var selectedFile : Uri? = null
        var path : String? = null
    }

    private val presenter by moxyPresenter { ConvertFragmentPresenter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_convert, container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        convertButton.setOnClickListener {}

        selectButton.setOnClickListener {}
    }


    override fun setOriginalImage(selectedFile: Uri?) {}

    override fun setConvertedImage(newImage: Uri) {}

}
package jmapps.thenamesof.ui.other

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import jmapps.thenamesof.R
import jmapps.thenamesof.databinding.BottomSheetAboutUsBinding

class AboutUsBottomSheet : BottomSheetDialogFragment() {

    override fun getTheme() = R.style.CustomBottomSheetDialog

    private lateinit var binding: BottomSheetAboutUsBinding

    companion object {
        const val keyAboutUs = "key_about_us"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.bottom_sheet_about_us, container, false)

        return binding.root
    }
}
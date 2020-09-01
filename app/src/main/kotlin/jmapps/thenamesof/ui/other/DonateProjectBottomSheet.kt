package jmapps.thenamesof.ui.other

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import jmapps.thenamesof.R
import jmapps.thenamesof.databinding.BottomSheetDonateProjectBinding

class DonateProjectBottomSheet : BottomSheetDialogFragment() {

    override fun getTheme() = R.style.CustomBottomSheetDialog

    private lateinit var binding: BottomSheetDonateProjectBinding

    companion object {
        const val keyDonateProject = "key_donate_project"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.bottom_sheet_donate_project, container, false)

        return binding.root
    }
}
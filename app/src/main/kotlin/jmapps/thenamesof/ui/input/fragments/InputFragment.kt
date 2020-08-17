package jmapps.thenamesof.ui.input.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import jmapps.thenamesof.R
import jmapps.thenamesof.databinding.FragmentInputBinding
import jmapps.thenamesof.ui.input.activities.InputActivity

class InputFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentInputBinding

    companion object {
        const val keyInputCategoryState = "key_input_category_state"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_input, container, false)

        binding.btnInputArabicCategory.setOnClickListener(this)
        binding.btnInputRussianCategory.setOnClickListener(this)

        return binding.root
    }

    override fun onClick(v: View?) {
        when (v?.id) {

            R.id.btnInputArabicCategory -> {
                toInputActivity(true)
            }

            R.id.btnInputRussianCategory -> {
                toInputActivity(false)
            }
        }
    }

    private fun toInputActivity(categoryState: Boolean) {
        val toArabicCategory = Intent(requireContext(), InputActivity::class.java)
        toArabicCategory.putExtra(keyInputCategoryState, categoryState)
        startActivity(toArabicCategory)
    }
}
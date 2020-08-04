package jmapps.thenamesof.ui.flip

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import jmapps.thenamesof.R
import jmapps.thenamesof.databinding.FragmentFlipBinding

class FlipFragment : Fragment() {

    private lateinit var binding: FragmentFlipBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_flip, container, false)

        return binding.root
    }
}
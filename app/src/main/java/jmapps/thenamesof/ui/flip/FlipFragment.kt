package jmapps.thenamesof.ui.flip

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import jmapps.thenamesof.R
import jmapps.thenamesof.databinding.FragmentContentBinding
import jmapps.thenamesof.databinding.FragmentFlipBinding
import jmapps.thenamesof.ui.content.ContentViewModel

class FlipFragment : Fragment() {

    private lateinit var flipViewModel: FlipViewModel
    private lateinit var binding: FragmentFlipBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        flipViewModel = ViewModelProvider(this).get(FlipViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_flip, container, false)

        flipViewModel.text.observe(viewLifecycleOwner, Observer {

        })

        return binding.root
    }
}
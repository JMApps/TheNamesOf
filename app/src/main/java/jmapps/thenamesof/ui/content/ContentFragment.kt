package jmapps.thenamesof.ui.content

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

class ContentFragment : Fragment() {

    private lateinit var contentViewModel: ContentViewModel
    private lateinit var binding: FragmentContentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        contentViewModel = ViewModelProvider(this).get(ContentViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_content, container, false)

        contentViewModel.text.observe(viewLifecycleOwner, Observer {
            binding.mainTextView.text = it
        })

        return binding.root
    }
}
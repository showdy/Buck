package com.buck.ui.live

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.buck.R

class LiveFragment : Fragment() {

    companion object {

        val TAG=LiveFragment.javaClass.simpleName

        fun newInstance() = LiveFragment()
    }

    private lateinit var viewModel: LiveViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.live_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(LiveViewModel::class.java)
        // TODO: Use the ViewModel
    }

}

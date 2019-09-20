package com.buck.ui.video

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.buck.R
import kotlinx.android.synthetic.main.video_fragment.*

class VideoFragment : Fragment() {

    companion object {
        val TAG: String=VideoFragment.javaClass.simpleName

        fun newInstance() = VideoFragment()
    }

    private lateinit var viewModel: VideoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.video_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(VideoViewModel::class.java)
        // TODO: Use the ViewModel



    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        video_view.setVideoPath("http://video.aohxc.com:8603/4rh/RH715JDR/RH715JDR.m3u8")
        video_view.start()
    }

}

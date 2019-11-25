package com.buck.ui.video

import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager


import com.buck.R
import com.buck.data.db.VideoEntity
import com.buck.data.model.VideoSection
import com.buck.internal.GlideImageLoader
import com.buck.ui.base.BaseFragment
import com.buck.ui.video.play.VideoPlayActivity
import com.buck.ui.video.play.VideoPlayActivity.Companion.KEY_VIDEO
import com.youth.banner.Banner
import com.youth.banner.loader.ImageLoader
import kotlinx.android.synthetic.main.banner_view.*
import kotlinx.android.synthetic.main.video_fragment.*


class VideoFragment : BaseFragment() {


    companion object {
        val TAG: String = VideoFragment::class.java.simpleName

        fun newInstance() = VideoFragment()
    }

    private val viewModel: VideoViewModel by lazy {
        ViewModelProviders.of(this).get(VideoViewModel::class.java)
    }

    private val videoAdapter by lazy {
        VideoAdapter()
    }

    private lateinit var errorView: View
    private lateinit var emptyView: View
    private lateinit var banner: Banner


    override fun getLayoutResId(): Int {
        return R.layout.video_fragment
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.layoutManager = LinearLayoutManager(context)
        emptyView = LayoutInflater.from(context)
            .inflate(R.layout.empty_view, recyclerView.parent as ViewGroup, false)
        errorView = LayoutInflater.from(context)
            .inflate(R.layout.error_view, recyclerView.parent as ViewGroup, false)
        val loadingView = LayoutInflater.from(context)
            .inflate(R.layout.loading_view, recyclerView.parent as ViewGroup, false)

        emptyView.setOnClickListener {
            viewModel.getAllVideos()
        }
        errorView.setOnClickListener {
            viewModel.getAllVideos()
        }

        //初始化banner
        initBanner()
        //进度条进行中
        videoAdapter.emptyView = loadingView
        videoAdapter.openLoadAnimation()
        recyclerView.adapter = videoAdapter

        //下拉刷新
        refreshLayout.setOnRefreshListener {
            viewModel.getAllVideos()
        }
        videoAdapter.setOnItemClickListener { adapter, view, position ->
            val entity = adapter.data[position] as VideoEntity
            Log.d("VideoFragment",entity.toString())
            val intent = Intent(context, VideoPlayActivity::class.java)
            intent.putExtra(KEY_VIDEO, entity)
            startActivity(intent)
        }
        //加载数据
        loadAllVideos()
    }

    private fun initBanner() {
        //banner--header
        val header = LayoutInflater.from(context)
            .inflate(R.layout.banner_view, recyclerView.parent as ViewGroup, false)
        banner = header.findViewById(R.id.bannerView)
        banner.setImageLoader(GlideImageLoader())
        banner.isAutoPlay(true)
        videoAdapter.addHeaderView(header)
    }

    private fun loadAllVideos() {

        viewModel.getAllVideos()

        viewModel.allVideos.observe(this, Observer {
            refreshLayout.finishRefresh()
            Log.d("VideoEntity", "entity: $it")
            when {
                it.isSuccess() -> {
                    if (it.data!!.isNullOrEmpty()) {
                        videoAdapter.emptyView = emptyView
                    } else {
                        Log.d("VideoEntity", "entity: ${it.data}")
                        val images = it.data.map { videoEntity -> videoEntity.coverImgUrl }
                        banner.setImages(images)
                        val titles = it.data.map { videoEntity -> videoEntity.name }
                        banner.setBannerTitles(titles)
                        banner.start()

                        videoAdapter.replaceData(it.data)
                    }
                }
                it.isError() -> videoAdapter.emptyView = errorView
            }
        })
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


    }


}

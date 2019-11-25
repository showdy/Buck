package com.buck.ui.video.play

import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import com.buck.R
import com.buck.data.db.VideoEntity
import com.buck.internal.StatusBarUtils
import com.buck.ui.base.BaseActivity
import com.bumptech.glide.Glide
import com.shuyu.gsyvideoplayer.GSYVideoADManager
import com.shuyu.gsyvideoplayer.utils.OrientationUtils
import kotlinx.android.synthetic.main.video_fragment.*
import kotlinx.android.synthetic.main.video_play_activity.*
import kotlin.properties.Delegates

class VideoPlayActivity : BaseActivity() {

    private var orientationUtils by Delegates.notNull<OrientationUtils>()

    companion object {
        const val KEY_VIDEO = "VIDEO"
    }

    override fun getLayoutResId(): Int {
        return R.layout.video_play_activity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        intent.getParcelableExtra<VideoEntity>(KEY_VIDEO)


        val entity = intent.getParcelableExtra<VideoEntity>(KEY_VIDEO)

        Log.d("VideoPlay",entity.toString())

        val url = "http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4"
        videoPlay.setUp(entity.url, true, "测试视频")
        val imageView = ImageView(this)
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        Glide.with(this).load(entity.coverImgUrl).into(imageView)
        videoPlay.thumbImageView = imageView

        videoPlay.titleTextView.visibility = View.VISIBLE
        videoPlay.backButton.visibility = View.VISIBLE

        orientationUtils = OrientationUtils(this, videoPlay)

        videoPlay.fullscreenButton.setOnClickListener {
            orientationUtils.resolveByClick()
        }
        videoPlay.setIsTouchWiget(true)
        videoPlay.backButton.setOnClickListener {
            onBackPressed()
        }
        videoPlay.startPlayLogic()

    }

    override fun onPause() {
        super.onPause()
        videoPlay.onVideoPause()
    }

    override fun onResume() {
        super.onResume()
        videoPlay.onVideoResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        GSYVideoADManager.releaseAllVideos()
        orientationUtils.releaseListener()
    }


    override fun onBackPressed() {
        if (orientationUtils.screenType == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            videoPlay.fullscreenButton.performClick()
            return
        }
        videoPlay.setVideoAllCallBack(null)
        super.onBackPressed()
    }


}

package com.buck.ui.video

import android.widget.ImageView
import android.widget.TextView

import com.buck.R
import com.buck.data.db.VideoEntity
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

class VideoAdapter(
    layoutResId: Int = R.layout.item_section_content
) : BaseQuickAdapter<VideoEntity, BaseViewHolder>(layoutResId) {
    override fun convert(helper: BaseViewHolder, item: VideoEntity) {
        val imageCover = helper.itemView.findViewById<ImageView>(R.id.imageCover)
        val textTitle = helper.itemView.findViewById<TextView>(R.id.textTitle)
        Glide.with(mContext).load(item.coverImgUrl).into(imageCover)
        textTitle.text = item.name
    }

}

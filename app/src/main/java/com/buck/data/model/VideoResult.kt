package com.buck.data.model

import com.buck.data.db.VideoEntity

data class VideoResult(
    val data: List<VideoEntity>,
    val message: String,
    val code: Int = 0
)
package com.buck.data.repository

import android.content.Context
import android.util.Log
import androidx.annotation.RawRes
import com.buck.data.db.VideoEntity
import com.buck.data.model.RequestResult
import com.buck.data.model.VideoResult
import com.buck.data.model.VideoSection
import com.google.gson.Gson
import java.io.IOException

class VideoRepository {

    suspend fun readLocal(context: Context, @RawRes resId: Int): RequestResult<List<VideoEntity>> {
        return try {
            val jsonString =
                context.resources.openRawResource(resId).bufferedReader().use { it.readText() }
            Log.d("video list:", jsonString)
            val videoResult = Gson().fromJson<VideoResult>(jsonString, VideoResult::class.java)
            RequestResult.success(videoResult.data)
        } catch (e: Exception) {
            RequestResult.error("load error")
        }
    }
}
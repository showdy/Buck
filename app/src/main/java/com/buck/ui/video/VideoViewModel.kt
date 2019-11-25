package com.buck.ui.video

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.buck.R
import com.buck.data.db.VideoEntity
import com.buck.data.model.RequestResult
import com.buck.data.model.VideoSection
import com.buck.data.repository.VideoRepository
import com.medisana.tender.ui.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class VideoViewModel(application: Application) : BaseViewModel(application) {

    val videoRepository = VideoRepository()

    private val _allVideos = MutableLiveData<RequestResult<List<VideoEntity>>>()

    val allVideos: LiveData<RequestResult<List<VideoEntity>>> = _allVideos


    fun getAllVideos() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _allVideos.postValue(videoRepository.readLocal(getApplication(), R.raw.video))
            }
        }
    }
}

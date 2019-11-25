package com.buck.data.model;

import com.buck.data.db.VideoEntity;
import com.chad.library.adapter.base.entity.SectionEntity;

public class VideoSection extends SectionEntity<VideoEntity> {
    public VideoSection(boolean isHeader, String header) {
        super(isHeader, header);
    }

    public VideoSection(VideoEntity videoEntity) {
        super(videoEntity);
    }
}

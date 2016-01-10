package com.tomishi.sampletvapp.data;

import com.tomishi.sampletvapp.model.Video;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class VideoProvider {
    private static List<Video> sVideos = Collections.unmodifiableList(
            new LinkedList<Video>() {
                {
                    for (int i = 0; i < 10; i++) {
                        Video video = new Video();
                        video.setTitle("title" + i);
                        video.setStudio("studio" + i);
                        video.setDescription("description" + i);
                        add(video);
                    }
                }
            });


    public static List<Video> getDummpyVideos() {
        return sVideos;
    }

}

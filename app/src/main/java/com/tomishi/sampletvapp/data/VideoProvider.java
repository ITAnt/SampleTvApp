package com.tomishi.sampletvapp.data;

import com.tomishi.sampletvapp.R;
import com.tomishi.sampletvapp.model.Video;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class VideoProvider {
    private static List<Video> sDummyVideos = Collections.unmodifiableList(
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
            }
    );

    private static List<Video> sVideos = Collections.unmodifiableList(
            new LinkedList<Video>() {
                {
                    Video video = new Video();
                    video.setId(0);
                    video.setResourceId(R.raw.video1);
                    video.setTitle("Giraffe");
                    video.setStudio("free video");
                    video.setDescription("");
                    add(video);

                    video = new Video();
                    video.setId(1);
                    video.setResourceId(R.raw.video2);
                    video.setTitle("Dinosaur");
                    video.setStudio("free video");
                    video.setDescription("");
                    add(video);
                }
            }
    );

    public static List<Video> getDummpyVideos() {
        return sDummyVideos;
    }

    public static List<Video> getVideos() {
        return sVideos;
    }
}

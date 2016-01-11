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
                    video.setTitle("キリン　夕日を背景に");
                    video.setStudio("ＮＨＫクリエイティブ・ライブラリ");
                    video.setDescription("アフリカ南部、ナミビアにあるナミブ砂漠。夕日が沈もうとしている。夕日を背景に、親子のキリンが移動中。薄暮の時間は、気温も下がり、つかの間の平和なひととき。２００４年１１月から１２月に撮影。");
                    add(video);

                    video = new Video();
                    video.setId(1);
                    video.setResourceId(R.raw.video2);
                    video.setTitle("ティラノサウルスとデイノスクスに追い詰められたクリトサウルス");
                    video.setStudio("ＮＨＫクリエイティブ・ライブラリ");
                    video.setDescription("中生代白亜紀の水辺で展開されていた恐竜やワニの祖先の捕食の様子を再現したＣＧ。クリトサウルスという中型の植物食恐竜が大形肉食恐竜ティラノサウルスに水辺まで追い詰められている。突然、背後の水中から出現した大型のワニであるデイノスクスがクリトサウルスを捕食してしまう様子。デイノスクスは、同時期の北アメリカに生息していたた史上最大級のワニ。");
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

package com.movtalent.app.util;


import android.util.Log;
import android.util.SparseArray;
import com.movtalent.app.model.VideoVo;

import java.util.ArrayList;

/**
 * @author huangyong
 * createTime 2019-08-24
 */
public class PlayUrlUtils {
    public static ArrayList<VideoVo> convertPlayList(String vodName, String vodPlayUrl) {
        //电影播放地址集合
        ArrayList<VideoVo> videoVos = new ArrayList<>();
        //先通过$$$将在线和云播分开
        if (vodPlayUrl.contains("m3u8")) {
            String replace = vodPlayUrl.replace("$$$", "bbb");
            String[] tmp = replace.split("bbb");
            for (int j = 0; j < tmp.length; j++) {
                if (tmp[j].contains("m3u8")) {
                    //进一步处理,如果是包含#，应该是电视剧
                    if (tmp[j].contains("#")) {
                        String[] playurls = tmp[j].split("#");
                        for (int i = 0; i < playurls.length; i++) {
                            if (playurls[i].endsWith("m3u8")) {
                                String singleUrl = playurls[i];
                                if (singleUrl.contains("$")) {
                                    singleUrl = singleUrl.substring(singleUrl.indexOf("$") + 1);

                                    VideoVo videoVo = new VideoVo();
                                    if (!singleUrl.endsWith(".m3u8")) {
                                        int prefix = singleUrl.indexOf(".m3u8");
                                        singleUrl = singleUrl.substring(0, prefix + 5);
                                    }
                                    videoVo.setPlayUrl(singleUrl);
                                    Log.e("getplayurl",""+singleUrl);
                                    videoVo.setTitle(vodName);
                                    videoVos.add(videoVo);
                                }
                            }
                        }
                    } else {
                        //应该是电影，只有一集
                        if (tmp[j].endsWith("m3u8")) {
                            String singleUrl = tmp[j];
                            if (singleUrl.contains("$")) {
                                singleUrl = singleUrl.substring(singleUrl.indexOf("$") + 1);
                                if (!singleUrl.endsWith(".m3u8")) {
                                    int prefix = singleUrl.indexOf(".m3u8");
                                    singleUrl = singleUrl.substring(0, prefix + 5);
                                }
                                VideoVo videoVo = new VideoVo();
                                videoVo.setPlayUrl(singleUrl);
                                Log.e("getplayurl","--"+singleUrl);
                                videoVo.setTitle(vodName);
                                videoVos.add(videoVo);
                            }
                        }

                    }
                }
            }
        } else if (vodPlayUrl.contains(".html")) {

            //官方地址需要解析
            //进一步处理,如果是包含#，应该是电视剧
            if (vodPlayUrl.contains("#")) {
                String[] playurls = vodPlayUrl.split("#");
                for (int i = 0; i < playurls.length; i++) {

                    if (playurls[i].endsWith(".html")) {
                        String singleUrl = playurls[i];
                        if (singleUrl.contains("$")) {
                            singleUrl = singleUrl.substring(singleUrl.indexOf("$") + 1);

                            VideoVo videoVo = new VideoVo();
                            if (!singleUrl.endsWith(".html")) {
                                int prefix = singleUrl.indexOf(".html");
                                singleUrl = singleUrl.substring(0, prefix + 5);
                            }
                            videoVo.setPlayUrl(singleUrl);
                            videoVo.setTitle(vodName);
                            videoVos.add(videoVo);
                        }
                    }
                }
            } else {
                //应该是电影，只有一集
                if (vodPlayUrl.endsWith(".html")) {
                    String singleUrl = vodPlayUrl;
                    if (singleUrl.contains("$")) {
                        singleUrl = singleUrl.substring(singleUrl.indexOf("$") + 1);
                        if (!singleUrl.endsWith(".html")) {
                            int prefix = singleUrl.indexOf(".html");
                            singleUrl = singleUrl.substring(0, prefix + 5);
                        }
                        VideoVo videoVo = new VideoVo();
                        videoVo.setPlayUrl(singleUrl);
                        videoVo.setTitle(vodName);
                        videoVos.add(videoVo);
                    }
                }

            }
        }
        return videoVos;
    }

    public static SparseArray<ArrayList<VideoVo>> convertGroupPlayList(String vodPlayUrl) {
        SparseArray<ArrayList<VideoVo>> groupMap = new SparseArray<>();

        //先通过$$$将播放组分出来
        String[] vodGroup = vodPlayUrl.split("[$][$][$]");
        //每个播放组都有自己的提取规则，比如m3u8,云播，html的那种是官方播放地址
        for (int a = 0; a < vodGroup.length; a++) {

            //电影播放地址集合
            ArrayList<VideoVo> videoVos = new ArrayList<>();
            //每一次循环，生成一个播放组，每个播放组有且只有一种形式，所以直接穷举就行了
            String vodJson = vodGroup[a];
            if (vodJson.contains("m3u8")) {
                //多集
                if (vodJson.contains("#")) {
                    String[] playurls = vodJson.split("#");
                    for (int i = 0; i < playurls.length; i++) {
                        if (playurls[i].endsWith("m3u8")) {
                            String singleUrl = playurls[i];
                            if (singleUrl.contains("$")) {
                                String[] vodData = singleUrl.split("[$]");
                                if (vodData.length == 2) {
                                    VideoVo videoVo = new VideoVo();
                                    videoVo.setPlayUrl(vodData[1]);
                                    videoVo.setTitle(vodData[0]);
                                    videoVos.add(videoVo);
                                }
                            }
                        }
                    }
                } else {
                    //应该是电影，只有一集
                    if (vodJson.contains("$")) {
                        String[] vodData = vodJson.split("[$]");
                        if (vodData.length == 2) {
                            VideoVo videoVo = new VideoVo();
                            videoVo.setPlayUrl(vodData[1]);
                            videoVo.setTitle(vodData[0]);
                            videoVos.add(videoVo);
                        }
                    }

                }

            } else if (vodJson.contains("html")) {
                //官方播放地址
                //多集
                if (vodJson.contains("#")) {
                    String[] playurls = vodJson.split("#");
                    for (int i = 0; i < playurls.length; i++) {
                        String singleUrl = playurls[i];
                        if (singleUrl.contains("$")) {
                            String[] vodData = singleUrl.split("[$]");
                            if (vodData.length == 2) {
                                VideoVo videoVo = new VideoVo();
                                videoVo.setPlayUrl(vodData[1]);
                                videoVo.setTitle(vodData[0]);
                                videoVos.add(videoVo);
                            }
                        }
                    }
                } else {
                    //应该是电影，只有一集
                    if (vodJson.contains("$")) {
                        String[] vodData = vodJson.split("[$]");
                        if (vodData.length == 2) {
                            VideoVo videoVo = new VideoVo();
                            videoVo.setPlayUrl(vodData[1]);
                            videoVo.setTitle(vodData[0]);
                            videoVos.add(videoVo);
                        }
                    }

                }


            } else {
                //这种就是云播了，h5播放那种
                //多集
                if (vodJson.contains("#")) {
                    String[] playurls = vodJson.split("#");
                    for (int i = 0; i < playurls.length; i++) {
                        String singleUrl = playurls[i];
                        if (singleUrl.contains("$")) {
                            String[] vodData = singleUrl.replace("$", "bbb").split("bbb");
                            if (vodData.length == 2) {
                                VideoVo videoVo = new VideoVo();
                                videoVo.setPlayUrl(vodData[1]);
                                videoVo.setTitle(vodData[0]);
                                videoVos.add(videoVo);
                            }
                        }
                    }
                } else {
                    //应该是电影，只有一集
                    if (vodJson.contains("$")) {
                        String[] vodData = vodJson.replace("$", "bbb").split("bbb");
                        if (vodData.length == 2) {
                            VideoVo videoVo = new VideoVo();
                            videoVo.setPlayUrl(vodData[1]);
                            videoVo.setTitle(vodData[0]);
                            videoVos.add(videoVo);
                        }
                    }

                }
            }
            groupMap.append(a, videoVos);
        }
        return groupMap;
    }
}

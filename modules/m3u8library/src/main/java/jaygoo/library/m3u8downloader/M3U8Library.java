package jaygoo.library.m3u8downloader;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * @author huangyong
 * createTime 2019-09-27
 */
public class M3U8Library {

    public static final String EVENT_REFRESH = "acton_refresh";
    public static String dirPath = Environment.getExternalStorageDirectory().getPath() + File.separator + "M3u8Cache";

    public static Context getContext() {
        return context;
    }

    public static Context context;

    public static void init(Context contexts) {

        context = contexts;

        File file = new File(dirPath);
        if (!file.exists()){
            file.mkdirs();
        }

        M3U8DownloaderConfig
                .build(contexts)
                .setSaveDir(dirPath)
                .setDebugMode(true);
    }
}

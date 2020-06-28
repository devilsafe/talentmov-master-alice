package jaygoo.library.m3u8downloader;

import android.text.TextUtils;
import jaygoo.library.m3u8downloader.bean.M3U8Task;

import java.util.HashMap;
import java.util.Map;

/**
 * @author huangyong
 * createTime 2019-09-28
 */
public class DownloadManager {
    private String DEFAULT_FILE_DIR;//默认下载目录
    private final Map<String, M3U8DownloadTask> mDownloadTasks;//文件下载任务索引，String为url,用来唯一区别并操作下载的文件
    private static DownloadManager mInstance;

    /**
     * 下载文件
     */
    public void download(String... urls) {
        for (int i = 0, length = urls.length; i < length; i++) {
            String url = urls[i];
            if (mDownloadTasks.containsKey(url)) {
                mDownloadTasks.get(url).restart();
            }
        }
    }

    /**
     * 通过url获取下载文件的名称
     */
    public String getFileName(String url) {
        return url.substring(url.lastIndexOf("/") + 1);
    }

    /**
     * 暂停
     */
    public void pause(String... urls) {
        for (int i = 0, length = urls.length; i < length; i++) {
            String url = urls[i];
            if (mDownloadTasks.containsKey(url)) {
                mDownloadTasks.get(url).stop();
            }
        }
    }

    /**
     * 取消下载
     */
    public void cancel(String... urls) {
        for (int i = 0, length = urls.length; i < length; i++) {
            String url = urls[i];
            if (mDownloadTasks.containsKey(url)) {
                mDownloadTasks.get(url).stop();
            }
        }
    }


    /**
     * 添加下载任务
     */
    public void add(String url, M3U8Task task) {
        add(url, task, null);
    }

    /**
     * 添加下载任务
     */
    public void add(String url, M3U8Task task, OnTaskDownloadListener l) {
        M3U8DownloadTask m3U8DownloadTask = new M3U8DownloadTask();
        m3U8DownloadTask.download(task, url, l);
        mDownloadTasks.put(url, m3U8DownloadTask);
    }

    /**
     * 获取默认下载目录
     *
     * @return
     */
    private String getDefaultDirectory() {
        if (TextUtils.isEmpty(DEFAULT_FILE_DIR)) {
            DEFAULT_FILE_DIR = M3U8Library.dirPath;
        }
        return DEFAULT_FILE_DIR;
    }

    /**
     * 是否正在下载
     *
     * @param urls
     * @return boolean
     */
    public boolean isDownloading(String... urls) {
        boolean result = false;
        for (int i = 0, length = urls.length; i < length; i++) {
            String url = urls[i];
            if (mDownloadTasks.containsKey(url)) {
                result = mDownloadTasks.get(url).isRunning();
            }
        }
        return result;
    }

    public static DownloadManager getInstance() {
        if (mInstance == null) {
            synchronized (DownloadManager.class) {
                if (mInstance == null) {
                    mInstance = new DownloadManager();
                }
            }
        }
        return mInstance;
    }

    /**
     * 初始化下载管理器
     */
    private DownloadManager() {
        mDownloadTasks = new HashMap<>();
    }
}

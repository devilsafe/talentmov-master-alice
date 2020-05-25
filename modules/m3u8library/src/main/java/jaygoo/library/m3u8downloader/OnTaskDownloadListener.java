package jaygoo.library.m3u8downloader;


import jaygoo.library.m3u8downloader.bean.M3U8;
import jaygoo.library.m3u8downloader.bean.M3U8Task;

/**
 * ================================================
 * 作    者：JayGoo
 * 版    本：
 * 创建日期：2017/11/17
 * 描    述: 单独M3U8下载任务下载监听
 * ================================================
 */
public interface OnTaskDownloadListener extends BaseListener {

    /**
     *
     * @param totalTs ts总数
     * @param curTs 当前下载完成的ts个数
     */
    void onStartDownload(int totalTs, int curTs);

    /**
     * 下载m3u8文件.
     * 注意：这个方法是异步的（子线程中执行），所以不能在此方法中回调，其他方法为主线程中回调
     * @param task
     * @param totalFileSize
     * @param itemFileSize 单个文件的大小
     * @param totalTs      ts总数
     * @param curTs        当前下载完成的ts个数
     * @param url
     */
    void onDownloading(M3U8Task task, long totalFileSize, long itemFileSize, int totalTs, int curTs, String url);

    /**
     * 下载成功
     */
    void onSuccess(M3U8 m3U8);

    /**
     * 当前已经下载的文件大小
     *
     * @param curLength
     */
    void onProgress(M3U8Task curLength);

    /**
     * 开始的时候回调
     */
    void onStart();


    /**
     * 错误的时候回调
     * 线程环境无法保证，不可以直接在UI线程调用
     * @param errorMsg
     */
    void onError(Throwable errorMsg);

    /**
     * 暂停任务，其实就是停止任务
     * @param task
     */
    void onPause(M3U8Task task);

    /**
     * 开始监听
     */
    void onListen();
}

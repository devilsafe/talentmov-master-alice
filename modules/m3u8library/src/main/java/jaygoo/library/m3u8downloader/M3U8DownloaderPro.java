package jaygoo.library.m3u8downloader;

import android.content.Context;
import androidx.annotation.Nullable;
import android.text.TextUtils;
import android.widget.Toast;
import jaygoo.library.m3u8downloader.bean.M3U8Task;
import jaygoo.library.m3u8downloader.bean.M3U8TaskState;
import jaygoo.library.m3u8downloader.control.DownloadPresenter;
import jaygoo.library.m3u8downloader.utils.M3U8Log;
import jaygoo.library.m3u8downloader.utils.MUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * ================================================
 * 作    者：JayGoo
 * 版    本：
 * 创建日期：2017/11/17
 * 描    述: M3U8下载器
 * ================================================
 */
public class M3U8DownloaderPro {

    private long currentTime;
    private final DownloadTaskQueue downLoadQueue;
    private final List<M3U8DownloadTask> taskStack;

    private M3U8DownloaderPro() {
        downLoadQueue = new DownloadTaskQueue();
        taskStack = new ArrayList<>();
    }

    public void addTaskListener(String taskUrl, OnTaskDownloadListener onTaskDownloadListener) {
        if (downLoadQueue != null && downLoadQueue.size() > 0) {
            if (downLoadQueue.getTask(taskUrl) != null) {
                downLoadQueue.getTask(taskUrl).addTaskListener(onTaskDownloadListener);
            }
        }
    }

    private static class SingletonHolder {
        static M3U8DownloaderPro instance = new M3U8DownloaderPro();
    }

    public static M3U8DownloaderPro getInstance() {
        return SingletonHolder.instance;
    }


    /**
     * 防止快速点击引起ThreadPoolExecutor频繁创建销毁引起crash
     *
     * @return
     */
    private boolean isQuicklyClick() {
        boolean result = false;
        if (System.currentTimeMillis() - currentTime <= 100) {
            result = true;
            M3U8Log.d("is too quickly click!");
        }
        currentTime = System.currentTimeMillis();
        return result;
    }


    /**
     * 下载下一个任务，直到任务全部完成
     */
    private void downloadNextTask() {
//        M3U8DownloadTask poll = downLoadQueue.poll();
//        if (poll != null && !poll.isRunning()) {
//            startDownloadTask(poll.getTask(), null);
//        }
    }

    private void pendingTask(M3U8Task task) {
        task.setState(M3U8TaskState.PENDING);
    }


    /**
     * 点击item 对任务进行操作
     * 如果当前任务在下载列表中则认为是暂停
     * 否则入队等候下载
     *
     * @param url
     * @param onTaskDownloadListener
     */
    public void download(String url, String name, String posterUrl, OnTaskDownloadListener onTaskDownloadListener) {
        if (TextUtils.isEmpty(url) || isQuicklyClick()) {
            return;
        }
        M3U8Task task = new M3U8Task(url);
        task.setName(name);
        task.setImgPoster(posterUrl);

        if (downLoadQueue.getTask(url) != null) {
            if (!downLoadQueue.getTask(url).isRunning()) {
                downLoadQueue.remove(downLoadQueue.getTask(url));
                M3U8DownloadTask m3U8DownloadTask = startDownloadTask(task, onTaskDownloadListener);
                downLoadQueue.offer(m3U8DownloadTask);
            } else {
                pause(url);
            }
        } else {
            M3U8DownloadTask m3U8DownloadTask = startDownloadTask(task, onTaskDownloadListener);
            downLoadQueue.offer(m3U8DownloadTask);
        }
    }

    /**
     * 下载任务，跨页面启动一个任务，任务追加到队列里
     * 如果当前任务在下载列表中则认为是暂停
     * 否则入队等候下载
     *
     * @param context
     * @param url
     * @param name
     * @param imgUrl
     */
    public void addTask(Context context, String url, String name, String imgUrl) {

        if (TextUtils.isEmpty(url) || isQuicklyClick()) return;
        M3U8Task task = new M3U8Task(url);
        task.setImgPoster(imgUrl);
        task.setName(name);

        if (downLoadQueue.getTask(url) != null) {
            Toast.makeText(context, "任务已存在", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "任务已添加", Toast.LENGTH_SHORT).show();
            downLoadQueue.offer(startDownloadTask(task, null));
            //添加数据库记录
            DownloadPresenter.saveItemToDB(task);
        }
    }

    /**
     * 暂停，如果此任务正在下载则暂停，否则无反应
     * 只支持单一任务暂停，多任务暂停请使用{@link #pause(List)}
     *
     * @param url
     */
    public void pause(String url) {
        if (TextUtils.isEmpty(url)) return;
        M3U8DownloadTask task = downLoadQueue.getTask(url);
        if (task != null && task.isRunning()) {
            task.stop();
            downLoadQueue.remove(task);
//            downloadNextTask();
        }
    }

    /**
     * 批量暂停
     *
     * @param urls
     */
    public void pause(List<String> urls) {
        if (urls == null || urls.size() == 0) return;
        for (String url : urls) {
            if (downLoadQueue.getTask(url) != null && downLoadQueue.getTask(url).isRunning()) {
                downLoadQueue.getTask(url).stop();
            }
        }
    }

    /**
     * 检查m3u8文件是否存在
     *
     * @param url
     * @return
     */
    public boolean checkM3U8IsExist(String url) {
        try {
            return false;
        } catch (Exception e) {
            M3U8Log.e(e.getMessage());
        }
        return false;
    }

    /**
     * 得到m3u8文件路径
     *
     * @param url
     * @return
     */
    public String getM3U8Path(String url) {
        M3U8Task task = downLoadQueue.getTask(url).getTask();
        return task.getM3U8().getM3u8FilePath();
    }


    private M3U8DownloadTask startDownloadTask(M3U8Task task, OnTaskDownloadListener onTaskDownloadListener) {
        try {
            M3U8Log.d("====== start downloading ===== " + task.getUrl());
            M3U8DownloadTask m3U8DownLoadTask = new M3U8DownloadTask();
            m3U8DownLoadTask.download(task, task.getUrl(), onTaskDownloadListener);
            m3U8DownLoadTask.addQueneLisener(queneListener);
            return m3U8DownLoadTask;
        } catch (Exception e) {
            M3U8Log.e("startDownloadTask Error:" + e.getMessage());
        }
        return null;
    }


    /**
     * 取消任务
     *
     * @param url
     */
    public void cancel(String url) {
        pause(url);
    }

    /**
     * 批量取消任务
     *
     * @param urls
     */
    public void cancel(List<String> urls) {
        pause(urls);
    }

    /**
     * 取消任务,删除缓存
     *
     * @param url
     */
    public void cancelAndDelete(final String url, @Nullable final OnDeleteTaskListener listener) {
        pause(url);
        if (listener != null) {
            listener.onStart();
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean isDelete = MUtils.clearDir(new File(MUtils.getSaveFileDir(url)));
                if (listener != null) {
                    if (isDelete) {
                        listener.onSuccess();
                    } else {
                        listener.onFail();
                    }
                }
            }
        }).start();
    }

    /**
     * 取消任务,删除缓存
     *
     * @param url
     */
    public void deleteLocal(final String url, @Nullable final OnDeleteTaskListener listener) {
        if (listener != null) {
            listener.onStart();
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean isDelete = MUtils.clearDir(new File(MUtils.getSaveFileDir(url)));
                if (listener != null) {
                    if (isDelete) {
                        listener.onSuccess();
                    } else {
                        listener.onFail();
                    }
                }
            }
        }).start();
    }

    /**
     * 批量取消任务,删除缓存
     *
     * @param urls
     * @param listener
     */
    public void cancelAndDelete(final List<String> urls, @Nullable final OnDeleteTaskListener listener) {
        pause(urls);
        if (listener != null) {
            listener.onStart();
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean isDelete = true;
                for (String url : urls) {
                    isDelete = isDelete && MUtils.clearDir(new File(MUtils.getSaveFileDir(url)));
                }
                if (listener != null) {
                    if (isDelete) {
                        listener.onSuccess();
                    } else {
                        listener.onFail();
                    }
                }
            }
        }).start();
    }

    OnQueneListener queneListener = new OnQueneListener() {
        @Override
        public void onPoll(String taskUrl) {
            if (downLoadQueue != null && downLoadQueue.getTask(taskUrl) != null) {
                downLoadQueue.remove(downLoadQueue.getTask(taskUrl));
            }
        }
    };

    public interface OnQueneListener {
        //出队
        void onPoll(String taskUrl);
    }
}

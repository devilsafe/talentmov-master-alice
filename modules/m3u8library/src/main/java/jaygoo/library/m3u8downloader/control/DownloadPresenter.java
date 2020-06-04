package jaygoo.library.m3u8downloader.control;


import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.Toast;
import jaygoo.library.m3u8downloader.M3U8DownloaderPro;
import jaygoo.library.m3u8downloader.M3U8Library;
import jaygoo.library.m3u8downloader.bean.M3U8Task;
import jaygoo.library.m3u8downloader.db.M3U8dbManager;
import jaygoo.library.m3u8downloader.db.dao.DoneDao;
import jaygoo.library.m3u8downloader.db.dao.DowningDao;
import jaygoo.library.m3u8downloader.db.table.M3u8DoneInfo;
import jaygoo.library.m3u8downloader.db.table.M3u8DownloadingInfo;
import jaygoo.library.m3u8downloader.utils.MD5Utils;
import jaygoo.library.m3u8downloader.utils.MUtils;

import java.io.File;
import java.util.List;

/**
 * @author huangyong
 * createTime 2019-09-27
 * 管理入口类
 */
public class DownloadPresenter {


    public static void addM3u8Task(Context context, String url, String name, String imgUrl) {
        if (TextUtils.isEmpty(url) || !url.startsWith("https")) {
            Toast.makeText(context, "url不合法，无法添加缓存", Toast.LENGTH_SHORT).show();
            return;
        }

        if (checkInLocalHistory(url)) {
            Toast.makeText(context, "当前任务已存在", Toast.LENGTH_SHORT).show();
        } else {
            M3U8DownloaderPro.getInstance().addTask(context, url, name, imgUrl);
        }
    }

    /**
     * 检查是否本地已经下载过或在下载中
     *
     * @param url
     */
    private static boolean checkInLocalHistory(String url) {
        DoneDao doneDao = M3U8dbManager.getInstance(M3U8Library.getContext()).doneDao();
        DowningDao downingDao = M3U8dbManager.getInstance(M3U8Library.getContext()).downingDao();
        String taskId = MD5Utils.encode(url);
        List<M3u8DoneInfo> taskDone = doneDao.getById(taskId);
        List<M3u8DownloadingInfo> taskIng = downingDao.getById(taskId);
        return taskDone != null && taskDone.size() > 0 || taskIng != null && taskIng.size() > 0;
    }

    public static void saveItemToDB(M3U8Task task) {
        DowningDao downingDao = M3U8dbManager.getInstance(M3U8Library.getContext()).downingDao();
        String taskUrl = task.getUrl();
        String taskId = MD5Utils.encode(taskUrl);
        M3u8DownloadingInfo info = new M3u8DownloadingInfo();
        info.setTaskData(taskUrl);
        info.setTaskId(taskId);
        info.setTaskName(task.getName());
        info.setTaskPoster(task.getImgPoster());
        downingDao.insert(info);
    }

    public static String getTaskById(String taskId) {
        DowningDao downingDao = M3U8dbManager.getInstance(M3U8Library.getContext()).downingDao();
        List<M3u8DownloadingInfo> downingItem = downingDao.getById(taskId);
        if (downingItem != null && downingItem.size() > 0) {
            String taskUrl = downingItem.get(0).getTaskData();
            return taskUrl;
        }
        return null;
    }


    public static void clearAll() {
        DowningDao dao = M3U8dbManager.getInstance(M3U8Library.getContext()).downingDao();
        DoneDao dao2 = M3U8dbManager.getInstance(M3U8Library.getContext()).doneDao();
        List<M3u8DownloadingInfo> all = dao.getAll();
        if (all != null && all.size() > 0) {
            for (int i = 0; i < all.size(); i++) {
                M3U8DownloaderPro.getInstance().cancel(all.get(i).getTaskData());
                dao.delete(all.get(i));
            }
        }
        List<M3u8DoneInfo> u8DoneInfos = dao2.getAll();
        if (u8DoneInfos != null && u8DoneInfos.size() > 0) {
            for (int i = 0; i < u8DoneInfos.size(); i++) {
                dao2.delete(u8DoneInfos.get(i));
            }
        }
        MUtils.clearDir(new File(M3U8Library.dirPath));
        Intent intent = new Intent();
        intent.setAction(M3U8Library.EVENT_REFRESH);
        M3U8Library.getContext().sendBroadcast(intent);
    }

    public static void MoveToDoneDb(String taskId) {
        DowningDao downingDao = M3U8dbManager.getInstance(M3U8Library.getContext()).downingDao();
        DoneDao doneDao = M3U8dbManager.getInstance(M3U8Library.getContext()).doneDao();
        List<M3u8DownloadingInfo> infos = downingDao.getById(taskId);
        if (infos != null && infos.size() > 0) {

            M3u8DoneInfo doneInfo = new M3u8DoneInfo();
            doneInfo.setTaskData(infos.get(0).getTaskData());
            doneInfo.setTaskId(infos.get(0).getTaskId());
            doneInfo.setTaskName(infos.get(0).getTaskName());
            doneInfo.setTaskPoster(infos.get(0).getTaskPoster());
            doneDao.insert(doneInfo);
            downingDao.delete(infos.get(0));
        }
    }

    public static void saveProgress2DB(M3U8Task task) {
        DowningDao downingDao = M3U8dbManager.getInstance(M3U8Library.getContext()).downingDao();
        List<M3u8DownloadingInfo> infos = downingDao.getById(MD5Utils.encode(task.getUrl()));
        if (infos != null && infos.size() > 0) {
            M3u8DownloadingInfo info = infos.get(0);
            info.setTaskState(task.getState());
            info.setTaskSize((int) (task.getProgress() * 100));
            downingDao.update(info);

        }
    }

    /**
     * 通知列表更新
     */
    public static void notifyGlobal() {
        Intent intent = new Intent();
        intent.setAction(M3U8Library.EVENT_REFRESH);
        M3U8Library.getContext().sendBroadcast(intent);
    }


    public static void DeleteTask(String taskId) {
        DowningDao downingDao = M3U8dbManager.getInstance(M3U8Library.getContext()).downingDao();
        List<M3u8DownloadingInfo> infos = downingDao.getById(taskId);
        if (infos != null && infos.size() > 0){
            downingDao.delete(infos.get(0));
        }
    }

    public static void DeleteDoneTask(String taskId) {
        DoneDao doneDao = M3U8dbManager.getInstance(M3U8Library.getContext()).doneDao();
        List<M3u8DoneInfo> infos = doneDao.getById(taskId);
        if (infos != null && infos.size() > 0){
            doneDao.delete(infos.get(0));
        }
    }
}

package jaygoo.library.m3u8downloader.view.item;

import jaygoo.library.m3u8downloader.db.table.M3u8DoneInfo;

/**
 * @author huangyong
 * createTime 2019-09-28
 */
public class M3u8DoneItem {
    M3u8DoneInfo m3u8DoneInfo;
    M3u8DoneItemViewBinder.OnItemListener clickListener;

    public M3u8DoneInfo getM3u8DoneInfo() {
        return m3u8DoneInfo;
    }

    public M3u8DoneItem(M3u8DoneInfo m3u8DoneInfo, M3u8DoneItemViewBinder.OnItemListener clickListener) {
        this.clickListener = clickListener;
        this.m3u8DoneInfo = m3u8DoneInfo;
    }
}
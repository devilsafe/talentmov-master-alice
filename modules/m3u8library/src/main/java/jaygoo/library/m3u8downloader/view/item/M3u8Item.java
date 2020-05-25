package jaygoo.library.m3u8downloader.view.item;


/**
 * @author huangyong
 * createTime 2019-09-27
 */
public class M3u8Item {

    private String imgUrl;
    private String name;

    public String getImgUrl() {
        return imgUrl;
    }

    public String getName() {
        return name;
    }

    public String getTaskId() {
        return taskId;
    }

    private String taskId;


    public M3u8ItemViewBinder.OnItemListener getItemListener() {
        return itemListener;
    }

    private M3u8ItemViewBinder.OnItemListener itemListener;

    public M3u8Item(M3u8ItemViewBinder.OnItemListener itemListener,String taskId,String name,String imgUrl) {
        this.name = name;
        this.imgUrl = imgUrl;
        this.itemListener = itemListener;
        this.taskId = taskId;
    }
}
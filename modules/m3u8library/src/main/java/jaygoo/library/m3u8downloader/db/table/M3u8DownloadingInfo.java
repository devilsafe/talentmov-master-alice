package jaygoo.library.m3u8downloader.db.table;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = M3u8DownloadingInfo.TABLE_NAME, indices = {@Index("task_id")})
public class M3u8DownloadingInfo {

    public static final String TABLE_NAME = "t_m3u8_downing";

    @ColumnInfo(name = "task_id")
    private String taskId;

    @ColumnInfo(name = "task_name")
    private String taskName;

    @ColumnInfo(name = "task_poster")
    private String taskPoster;

    @ColumnInfo(name = "task_state")
    private int taskState;

    @ColumnInfo(name = "task_size")
    private int taskSize;

    public int getTaskState() {
        return taskState;
    }

    public void setTaskState(int taskState) {
        this.taskState = taskState;
    }

    public int getTaskSize() {
        return taskSize;
    }

    public void setTaskSize(int taskSize) {
        this.taskSize = taskSize;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskPoster() {
        return taskPoster;
    }

    public void setTaskPoster(String taskPoster) {
        this.taskPoster = taskPoster;
    }


    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskData() {
        return taskData;
    }

    public void setTaskData(String taskData) {
        this.taskData = taskData;
    }

    @ColumnInfo(name = "task_data")
    private String taskData;


    @PrimaryKey(autoGenerate = true)
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

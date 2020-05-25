package jaygoo.library.m3u8downloader.db.table;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = M3u8DoneInfo.TABLE_NAME, indices = {@Index("task_id")})
public class M3u8DoneInfo {

    public static final String TABLE_NAME = "t_m3u8_done";

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "task_id")
    private String taskId;
    @ColumnInfo(name = "task_data")
    private String taskData;
    @ColumnInfo(name = "task_name")
    private String taskName;

    @ColumnInfo(name = "task_poster")
    private String taskPoster;

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
    public String getTaskData() {
        return taskData;
    }

    public void setTaskData(String taskData) {
        this.taskData = taskData;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

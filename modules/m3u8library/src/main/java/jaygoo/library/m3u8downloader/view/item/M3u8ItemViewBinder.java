package jaygoo.library.m3u8downloader.view.item;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import jaygoo.library.m3u8downloader.OnTaskDownloadListener;
import jaygoo.library.m3u8downloader.R;
import jaygoo.library.m3u8downloader.bean.M3U8;
import jaygoo.library.m3u8downloader.bean.M3U8Task;
import jaygoo.library.m3u8downloader.bean.M3U8TaskState;
import jaygoo.library.m3u8downloader.control.DownloadPresenter;
import me.drakeet.multitype.ItemViewBinder;

/**
 * @author huangyong
 * createTime 2019-09-27
 */
public class M3u8ItemViewBinder extends ItemViewBinder<M3u8Item, M3u8ItemViewBinder.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_m3u8_item, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull final ViewHolder holder, @NonNull final M3u8Item m3u8Item) {

        if (m3u8Item.getItemListener() != null) {
            Glide.with(holder.itemView.getContext()).load(m3u8Item.getImgUrl()).into(holder.itemIcon);
            holder.itemTitle.setText(TextUtils.isEmpty(m3u8Item.getName()) ? "未知电影" : m3u8Item.getName());
            holder.itemState.setText("已暂停");
            holder.itemSpeed.setVisibility(View.INVISIBLE);
            holder.itemProgress.setVisibility(View.INVISIBLE);
            m3u8Item.getItemListener().onListenerInit(getOnTaskDownloadListener(holder, m3u8Item), m3u8Item);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    OnTaskDownloadListener onTaskDownloadListener = getOnTaskDownloadListener(holder, m3u8Item);
                    m3u8Item.getItemListener().onClick(m3u8Item.getTaskId(), m3u8Item.getName(), m3u8Item.getImgUrl(), onTaskDownloadListener);
                    holder.itemState.setText("连接中...");
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    m3u8Item.getItemListener().onLongClick(m3u8Item);
                    return true;
                }
            });
        }


    }

    private OnTaskDownloadListener getOnTaskDownloadListener(@NonNull final ViewHolder holder, final M3u8Item m3u8Item) {
        return new OnTaskDownloadListener() {

            @Override
            public void onStartDownload(int totalTs, int curTs) {
            }

            @Override
            public void onDownloading(M3U8Task task, long totalFileSize, long itemFileSize, int totalTs, int curTs, String url) {
                float progress = 1.0f * curTs / totalTs;
                if (task.getState() == M3U8TaskState.DOWNLOADING) {
                    holder.itemProgress.setVisibility(View.VISIBLE);
                    holder.itemState.setText("下载中：" + String.format("%.1f ", progress * 100) + "%");
                    holder.itemProgress.setProgress((int) (progress * 100));
                    holder.itemSpeed.setText(task.getFormatSpeed() + "");
                    holder.itemSpeed.setVisibility(View.VISIBLE);
                    DownloadPresenter.saveProgress2DB(task);
                }
            }

            @Override
            public void onSuccess(final M3U8 m3U8) {

                holder.itemState.setText("下载完成 100%");
                holder.itemView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        DownloadPresenter.MoveToDoneDb(m3u8Item.getTaskId());
                        DownloadPresenter.notifyGlobal();
                        getAdapter().getItems().remove(m3u8Item);
                        getAdapter().notifyItemRemoved(getAdapter().getItems().indexOf(m3u8Item));
                    }
                }, 200);


            }

            @Override
            public void onProgress(M3U8Task curLength) {

            }

            @Override
            public void onStart() {

            }

            @Override
            public void onError(Throwable errorMsg) {
                holder.itemState.setText("下载出错，请重试");
            }

            @Override
            public void onPause(final M3U8Task task) {
                holder.itemState.setText("已暂停");
                holder.itemSpeed.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onListen() {
                holder.itemState.setText("进度获取中...");
            }
        };
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView itemContorl;
        TextView itemState;
        TextView itemTitle;
        ImageView itemIcon;
        TextView itemSpeed;
        android.widget.ProgressBar itemProgress;

        ViewHolder(View itemView) {
            super(itemView);
            itemContorl = itemView.findViewById(R.id.item_control);
            itemProgress = itemView.findViewById(R.id.update_progress);
            itemSpeed = itemView.findViewById(R.id.state_speed);
            itemState = itemView.findViewById(R.id.state_tv);
            itemTitle = itemView.findViewById(R.id.m3u8_title);
            itemIcon = itemView.findViewById(R.id.item_icon);
        }
    }

    public interface OnItemListener {
        void onListenerInit(OnTaskDownloadListener onTaskDownloadListener, M3u8Item m3u8Item);

        void onClick(String taskId, String name, String posterUrl, OnTaskDownloadListener onTaskDownloadListener);

        void onLongClick(M3u8Item m3u8Item);
    }
}

package jaygoo.library.m3u8downloader.view.item;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.media.playerlib.PlayMainActivity;
import com.media.playerlib.model.DataInter;
import jaygoo.library.m3u8downloader.M3U8Downloader;
import jaygoo.library.m3u8downloader.M3U8Library;
import jaygoo.library.m3u8downloader.R;
import me.drakeet.multitype.ItemViewBinder;

/**
 * @author huangyong
 * createTime 2019-09-28
 */
public class M3u8DoneItemViewBinder extends ItemViewBinder<M3u8DoneItem, M3u8DoneItemViewBinder.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_m3u8_done_item, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull final ViewHolder holder, @NonNull final M3u8DoneItem m3u8DoneItem) {
        holder.itemTitle.setText(m3u8DoneItem.getM3u8DoneInfo().getTaskName());
        holder.itemState.setText("已完成");
        Glide.with(holder.itemView.getContext()).load(m3u8DoneItem.getM3u8DoneInfo().getTaskPoster()).into(holder.icon);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (M3U8Downloader.getInstance().checkM3U8IsExist(m3u8DoneItem.getM3u8DoneInfo().getTaskData())) {
                    Intent intent = new Intent(holder.itemView.getContext(), PlayMainActivity.class);
                    intent.putExtra(DataInter.Key.KEY_CURRENTPLAY_URL, M3U8Downloader.getInstance().getM3U8Path(m3u8DoneItem.getM3u8DoneInfo().getTaskData()));
                    intent.putExtra(DataInter.Key.KEY_CURRENTPLAY_TITLE, m3u8DoneItem.getM3u8DoneInfo().getTaskName());
                    holder.itemView.getContext().startActivity(intent);
                } else {
                    Toast.makeText(M3U8Library.getContext(), "未发现播放文件，删除了？", Toast.LENGTH_SHORT).show();
                }
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (m3u8DoneItem.clickListener!=null){
                    m3u8DoneItem.clickListener.onLongClick(m3u8DoneItem,getAdapter().getItems().indexOf(m3u8DoneItem));
                }
                return true;
            }
        });
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemState;
        TextView itemTitle;
        ImageView icon;

        ViewHolder(View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.m3u8_item_icon);
            itemTitle = itemView.findViewById(R.id.m3u8_title);
            itemState = itemView.findViewById(R.id.m3u8_state);
        }
    }

    public interface OnItemListener{
        void onLongClick(M3u8DoneItem m3u8DoneItem, int doneItem);
    }
}

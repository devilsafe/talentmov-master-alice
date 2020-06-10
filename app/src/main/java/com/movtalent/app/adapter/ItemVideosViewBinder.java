package com.movtalent.app.adapter;

import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.lib.common.util.DataInter;
import com.movtalent.app.R;
import com.movtalent.app.model.dto.VideoListDto;
import com.movtalent.app.view.OnlineDetailPageActivity;
import me.drakeet.multitype.ItemViewBinder;

/**
 * @author huangyong
 * createTime 2019-09-14
 * 每个section含有6个item，为了保证顺序，这样来搞
 */
public class ItemVideosViewBinder extends ItemViewBinder<VideoListDto.DataBean, ItemVideosViewBinder.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_video, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull VideoListDto.DataBean itemVideo) {
        Glide.with(holder.itemView.getContext()).load(itemVideo.getVod_pic()).into(holder.cover);
        holder.title.setText(itemVideo.getVod_name() + "");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), OnlineDetailPageActivity.class);
                intent.putExtra(DataInter.KEY.VOD_ID, Integer.parseInt(itemVideo.getVod_id()));
                holder.itemView.getContext().startActivity(intent);
            }
        });
        holder.uptime.setText(itemVideo.getVod_content().trim());
        holder.upRemark.setText(itemVideo.getVod_remarks());
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @NonNull
        ImageView cover;
        @NonNull
        TextView title;

        TextView uptime;
        TextView upRemark;

        ViewHolder(View itemView) {
            super(itemView);
            cover = itemView.findViewById(R.id.post_img);
            title = itemView.findViewById(R.id.post_title);
            uptime = itemView.findViewById(R.id.up_time);
            upRemark = itemView.findViewById(R.id.update_seri);
            itemView.setOnClickListener(v -> Toast.makeText(v.getContext(), String.valueOf(getAdapterPosition()), Toast.LENGTH_SHORT).show());
        }

    }
}

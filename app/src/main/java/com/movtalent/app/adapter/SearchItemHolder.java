package com.movtalent.app.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.movtalent.app.R;


/**
 * @author huangyong
 * createTime 2019/6/22
 */
public class SearchItemHolder extends RecyclerView.ViewHolder {

    public TextView movName;
    public TextView movRemark;
    public TextView vodYear;
    public TextView vodDesc;
    public ImageView itemPoster;

    public SearchItemHolder(@NonNull View itemView) {
        super(itemView);
        movName = itemView.findViewById(R.id.post_title);
        itemPoster = itemView.findViewById(R.id.post_img);
        movRemark = itemView.findViewById(R.id.update_seri);
        vodYear = itemView.findViewById(R.id.up_time);
        vodDesc = itemView.findViewById(R.id.vod_desc);
    }
}

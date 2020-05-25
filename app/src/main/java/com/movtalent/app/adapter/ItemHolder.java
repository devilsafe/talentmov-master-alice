package com.movtalent.app.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.movtalent.app.R;



/**
 * @author huangyong
 * createTime 2019/6/22
 */
public class ItemHolder extends RecyclerView.ViewHolder {

    public TextView movName;
    public TextView movRemark;
    public TextView vodYear;
    public ImageView itemPoster;

    public ItemHolder(@NonNull View itemView) {
        super(itemView);
        movName = itemView.findViewById(R.id.post_title);
        itemPoster = itemView.findViewById(R.id.post_img);
        movRemark = itemView.findViewById(R.id.update_seri);
        vodYear = itemView.findViewById(R.id.up_time);
    }
}

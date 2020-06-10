package com.movtalent.app.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.movtalent.app.R;


/**
 * @author huangyong
 * createTime 2019/6/23
 */
public class PlayItemHolder extends RecyclerView.ViewHolder {


    public TextView index;

    public PlayItemHolder(@NonNull View itemView) {
        super(itemView);
        index = itemView.findViewById(R.id.play_squre_index);
    }
}

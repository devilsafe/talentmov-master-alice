package com.movtalent.app.category.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.movtalent.app.R;


/**
 * @author huangyong
 * createTime 2019/6/14
 */
public class CateHolder extends RecyclerView.ViewHolder {

    public TextView catTab;

    public CateHolder(@NonNull View itemView) {
        super(itemView);
        catTab = itemView.findViewById(R.id.tab_item_tv);
    }
}

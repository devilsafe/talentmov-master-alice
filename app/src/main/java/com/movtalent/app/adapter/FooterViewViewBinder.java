package com.movtalent.app.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.movtalent.app.R;
import com.movtalent.app.model.FooterView;
import me.drakeet.multitype.ItemViewBinder;

/**
 * @author huangyong
 * createTime 2019-09-15
 */
public class FooterViewViewBinder extends ItemViewBinder<FooterView, FooterViewViewBinder.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_footer_entity, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull FooterView footerView) {
        holder.nomore.setText(footerView.getCatName());
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView nomore;

        ViewHolder(View itemView) {
            super(itemView);
            nomore = itemView.findViewById(R.id.no_more);
        }
    }
}

package com.movtalent.app.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.movtalent.app.R;
import com.movtalent.app.model.Category;
import me.drakeet.multitype.ItemViewBinder;

/**
 * @author huangyong
 * createTime 2019-09-14
 */
public class CategoryViewBinder extends ItemViewBinder<Category, CategoryViewBinder.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_category, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull Category category) {
        holder.seeMore.setOnClickListener(v -> {
            if (category.getClickListener() != null) {
                category.getClickListener().onClick(v);
            }
        });
        holder.secName.setText(category.getCatName());

    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView seeMore;
        TextView secName;

        ViewHolder(View itemView) {
            super(itemView);
            seeMore = itemView.findViewById(R.id.see_more);
            secName = itemView.findViewById(R.id.section_name);
        }
    }
}

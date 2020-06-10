package com.movtalent.app.category;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.movtalent.app.R;
import me.drakeet.multitype.ItemViewBinder;

/**
 * @author huangyong
 * createTime 2019-10-08
 */
public class SelectSectionTabViewBinder extends ItemViewBinder<SelectSectionTab, SelectSectionTabViewBinder.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_select_section_tab, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull SelectSectionTab selectSectionTab) {

    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ViewHolder(View itemView) {
            super(itemView);
        }
    }
}

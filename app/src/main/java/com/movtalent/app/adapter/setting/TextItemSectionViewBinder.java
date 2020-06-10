package com.movtalent.app.adapter.setting;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.movtalent.app.R;
import me.drakeet.multitype.ItemViewBinder;

/**
 * @author huangyong
 * createTime 2019-09-18
 */
public class TextItemSectionViewBinder extends ItemViewBinder<TextItemSection, TextItemSectionViewBinder.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_text_item_section, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull TextItemSection textItemSection) {

        holder.itemView.setOnClickListener(v -> {
            if (textItemSection.getClickListenr() != null) {
                textItemSection.getClickListenr().clickItem();
            }
        });
        holder.title.setText(textItemSection.getItemText());

    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;

        ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
        }
    }

    public interface OnItemClickListenr {
        void clickItem();
    }
}

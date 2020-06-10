package com.movtalent.app.adapter.icon;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.movtalent.app.App_Config;
import com.movtalent.app.R;
import me.drakeet.multitype.ItemViewBinder;

/**
 * @author huangyong
 * createTime 2019-10-10
 */
public class IconSectionViewBinder extends ItemViewBinder<IconSection, IconSectionViewBinder.ViewHolder> {


    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_icon_section, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull IconSection iconSection) {
        Glide.with(holder.itemView.getContext()).load(App_Config.ICON_GROUP[iconSection.iconIndex]).into(holder.icon);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iconSection.clickListener.onClick(iconSection.iconIndex);
                iconSection.setChosed(true);
            }
        });
        if (iconSection.isChosed()) {
            holder.confirm.setVisibility(View.VISIBLE);
        }else {
            holder.confirm.setVisibility(View.INVISIBLE);
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView icon;
        ImageView confirm;

        ViewHolder(View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.icon);
            confirm = itemView.findViewById(R.id.chose_confirm);
        }
    }

    public interface OnItemClickListener {
        void onClick(int index);
    }
}

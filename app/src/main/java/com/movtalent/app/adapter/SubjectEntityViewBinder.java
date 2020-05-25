package com.movtalent.app.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.movtalent.app.App_Config;
import com.movtalent.app.R;
import com.movtalent.app.view.AllSubjectActivity;
import me.drakeet.multitype.ItemViewBinder;

/**
 * @author huangyong
 * createTime 2019-09-15
 */
public class SubjectEntityViewBinder extends ItemViewBinder<SubjectEntity, SubjectEntityViewBinder.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_subject_entity, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull SubjectEntity subjectEntity) {
        holder.name.setText(subjectEntity.getSubJectName());
        holder.sub.setText(subjectEntity.getSubJectNameSub());
        Glide.with(holder.itemView.getContext()).load(App_Config.BASE_URL+subjectEntity.getPosterUrl()).into(holder.poster);
        holder.itemView.setOnClickListener(v -> AllSubjectActivity.startTo(holder.itemView.getContext(),subjectEntity.getTopId()));
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView sub;
        ImageView poster;

        ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.subject_name);
            sub = itemView.findViewById(R.id.subject_sub);
            poster = itemView.findViewById(R.id.subject_pster);

        }
    }
}

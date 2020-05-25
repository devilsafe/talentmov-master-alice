package com.movtalent.app.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.movtalent.app.R;
import com.movtalent.app.model.vo.CommonVideoVo;
import me.drakeet.multitype.ItemViewBinder;

import java.util.ArrayList;

/**
 * @author huangyong
 * createTime 2019-09-15
 */
public class DetailRecmmendSectionViewBinder extends ItemViewBinder<DetailRecmmendSection, DetailRecmmendSectionViewBinder.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_detail_recmmend_section, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull DetailRecmmendSection detailRecmmendSection) {
        holder.setData(holder.itemView.getContext(), detailRecmmendSection.getVideoVos());
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        RecyclerView recList;

        ViewHolder(View itemView) {
            super(itemView);
            recList = itemView.findViewById(R.id.rec_list);
        }

        public void setData(Context context, ArrayList<CommonVideoVo> videoVos) {
            OnlineCategoryAdapter categoryAdapter = new OnlineCategoryAdapter(context, videoVos);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            recList.setLayoutManager(linearLayoutManager);
            recList.setAdapter(categoryAdapter);
        }
    }
}

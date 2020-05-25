package com.movtalent.app.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.movtalent.app.R;
import com.movtalent.app.model.vo.CommonVideoVo;
import com.ms.banner.Banner;
import com.ms.banner.BannerConfig;
import com.ms.banner.Transformer;
import me.drakeet.multitype.ItemViewBinder;

import java.util.ArrayList;

/**
 * @author huangyong
 * createTime 2019-09-14
 */
public class BannerEntityViewBinder extends ItemViewBinder<BannerEntity, BannerEntityViewBinder.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_banner_entity, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull BannerEntity bannerEntity) {

        ArrayList<CommonVideoVo> videoVos = bannerEntity.getVideoVos();
        if (videoVos!=null&&videoVos.size()>0){
            holder.bgaBanner.setAutoPlay(true)
                    .setPages(videoVos, new CustomViewHolder())
                    .setBannerStyle(BannerConfig.NOT_INDICATOR)
                    .setBannerAnimation(Transformer.Accordion)
                    .setDelayTime(3000)
                    .start();
        }

    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        Banner bgaBanner;

        ViewHolder(View itemView) {
            super(itemView);
            bgaBanner = itemView.findViewById(R.id.banner);
        }
    }
}

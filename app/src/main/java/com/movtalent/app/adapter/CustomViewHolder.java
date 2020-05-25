package com.movtalent.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.lib.common.util.DataInter;
import com.movtalent.app.R;
import com.movtalent.app.model.vo.CommonVideoVo;
import com.movtalent.app.view.OnlineDetailPageActivity;
import com.ms.banner.holder.BannerViewHolder;


/**
 * @author huangyong
 * createTime 2019-09-20
 */
public class CustomViewHolder implements BannerViewHolder<CommonVideoVo> {

    private ImageView bannerImg;
    private TextView bannerTv;

    @Override
    public View createView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.banner_layout, null);
        bannerImg = view.findViewById(R.id.banner_img);
        bannerTv = view.findViewById(R.id.mov_title);
        return view;
    }

    @Override
    public void onBind(Context context, int position, CommonVideoVo data) {
        // 数据绑定
        Glide.with(context).load(data.getMovPoster()).into(bannerImg);
        bannerImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, OnlineDetailPageActivity.class);
                intent.putExtra(DataInter.KEY.VOD_ID, Integer.parseInt(data.getMovId()));
                context.startActivity(intent);
            }
        });
        bannerTv.setText(data.getMovName());
    }
}

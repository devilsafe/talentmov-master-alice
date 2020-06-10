package com.movtalent.app.adapter;

import android.content.Context;
import android.content.Intent;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.lib.common.util.DataInter;
import com.lib.common.util.tool.StringUtil;
import com.movtalent.app.R;
import com.movtalent.app.model.vo.CommonVideoVo;
import com.movtalent.app.view.OnlineDetailPageActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


/**
 * Created by huangyong on 2018/2/11.
 */

public class OnlineSearchAdapter extends RecyclerView.Adapter {
    private final Context context;
    private final ArrayList<CommonVideoVo> datas;
    private int isMV;
    private final String time;
    private final Gson gson;


    public OnlineSearchAdapter(Context context, ArrayList<CommonVideoVo> datas) {
        this.context = context;
        this.datas = datas;
        gson = new Gson();
        SimpleDateFormat format0 = new SimpleDateFormat("yyyy-MM-dd");
        Date ss = new Date();
        time = format0.format(ss.getTime());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_search_video, parent, false);
        return new SearchItemHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        String imgUrl = datas.get(position).getMovPoster();
        String name = datas.get(position).getMovTypeName();


        ((SearchItemHolder) holder).movRemark.setText(datas.get(position).getMovRemark());

        if (TextUtils.isEmpty(datas.get(position).getMovRemark())) {
            ((SearchItemHolder) holder).movRemark.setVisibility(View.INVISIBLE);
        } else {
            ((SearchItemHolder) holder).movRemark.setVisibility(View.VISIBLE);
        }

        StringBuilder builder = new StringBuilder();
        builder.append("地区：" + (TextUtils.isEmpty(datas.get(position).getMovArea()) ? "" : datas.get(position).getMovArea()) + "\n");
        builder.append("简介：" + datas.get(position).getMovDesc());

        ((SearchItemHolder) holder).vodDesc.setText(builder.toString());

        RoundedCorners roundedCorners = new RoundedCorners(10);
        RequestOptions requestOptions = RequestOptions.bitmapTransform(roundedCorners);
        Glide.with(context).load(imgUrl).transition(DrawableTransitionOptions.withCrossFade(300)).apply(requestOptions).into(((SearchItemHolder) holder).itemPoster);

        ((SearchItemHolder) holder).movName.setText(datas.get(position).getMovName());
        if (!TextUtils.isEmpty(datas.get(position).getMovUpdateTime())) {
            ((SearchItemHolder) holder).vodYear.setText(StringUtil.getDateStringFromTimestamp(Long.parseLong(datas.get(position).getMovUpdateTime())));
            ((SearchItemHolder) holder).vodYear.setVisibility(View.VISIBLE);
        }


        ((SearchItemHolder) holder).itemView.setOnClickListener(view -> {
            try {
                toDetailPage(datas.get(position).getMovId());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void toDetailPage(String vodId) {
        Intent intent = new Intent(context, OnlineDetailPageActivity.class);
        intent.putExtra(DataInter.KEY.VOD_ID, Integer.parseInt(vodId));
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }
}

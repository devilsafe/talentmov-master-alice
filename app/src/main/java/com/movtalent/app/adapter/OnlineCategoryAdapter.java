package com.movtalent.app.adapter;

import android.content.Context;
import android.content.Intent;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.lib.common.util.DataInter;
import com.movtalent.app.R;
import com.movtalent.app.model.vo.CommonVideoVo;
import com.movtalent.app.view.OnlineDetailPageActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


/**
 * Created by huangyong on 2018/2/11.
 */

public class OnlineCategoryAdapter extends RecyclerView.Adapter {
    private final Context context;
    private final ArrayList<CommonVideoVo> datas;
    private int isMV;
    private final String time;
    private final Gson gson;


    public OnlineCategoryAdapter(Context context, ArrayList<CommonVideoVo> datas) {
        this.context = context;
        this.datas = datas;
        gson = new Gson();
        SimpleDateFormat format0 = new SimpleDateFormat("yyyy-MM-dd");
        Date ss = new Date();
        time = format0.format(ss.getTime());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_video, parent, false);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        String imgUrl = datas.get(position).getMovPoster();
        String name = datas.get(position).getMovTypeName();


        ((ItemHolder) holder).movRemark.setText(datas.get(position).getMovRemark());

        if (TextUtils.isEmpty(datas.get(position).getMovRemark())) {
            ((ItemHolder) holder).movRemark.setVisibility(View.INVISIBLE);
        } else {
            ((ItemHolder) holder).movRemark.setVisibility(View.VISIBLE);
        }

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.tupian);
        Glide.with(context).load(imgUrl).transition(DrawableTransitionOptions.withCrossFade(300)).apply(requestOptions).into(((ItemHolder) holder).itemPoster);

        ((ItemHolder) holder).movName.setText(datas.get(position).getMovName());
        ((ItemHolder) holder).vodYear.setText(datas.get(position).getMovDesc().trim());

        ((ItemHolder) holder).itemView.setOnClickListener(view -> {
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
        return datas == null ? 0 : datas.size();
    }
}

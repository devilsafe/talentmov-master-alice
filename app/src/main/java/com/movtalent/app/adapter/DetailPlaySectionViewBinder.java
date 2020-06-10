package com.movtalent.app.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.impl.CenterListPopupView;
import com.lxj.xpopup.interfaces.OnSelectListener;
import com.media.playerlib.widget.GlobalDATA;
import com.movtalent.app.R;
import com.movtalent.app.adapter.event.OnSeriClickListener;
import com.movtalent.app.model.VideoVo;
import me.drakeet.multitype.ItemViewBinder;

import java.util.ArrayList;

/**
 * @author huangyong
 * createTime 2019-09-15
 */
public class DetailPlaySectionViewBinder extends ItemViewBinder<DetailPlaySection, DetailPlaySectionViewBinder.ViewHolder> {
    private int index; //用来判断数组是否越界
    private String TAG="DetailPlaySectionViewBinder";
    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_detail_play_section, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull DetailPlaySection detailPlaySection) {
        holder.setData(holder.itemView.getContext(), detailPlaySection.getCommonVideoVo().getMovPlayUrlList().get(0), detailPlaySection.getClickListener(),detailPlaySection.getGroupPlay());
        holder.seeMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (detailPlaySection.getClickListener() != null) {
                    detailPlaySection.getClickListener().showAllSeri(detailPlaySection.getCommonVideoVo());
                }
            }
        });


        SparseArray<ArrayList<VideoVo>> movPlayUrlList = detailPlaySection.getCommonVideoVo().getMovPlayUrlList();
        String vodPlayFrom = detailPlaySection.getCommonVideoVo().getVodPlayFrom();
        String[] from = vodPlayFrom.split("[$][$][$]");
        holder.playRes.setText("切换线路："+from[detailPlaySection.getGroupPlay()]);
        holder.playRes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CenterListPopupView popupView = new XPopup.Builder(holder.itemView.getContext()).asCenterList("选择播放线路", from, null, 0,
                        new OnSelectListener() {
                            @Override
                            public void onSelect(int position, String text) {
                                holder.playRes.setText("切换线路："+from[position]);
                                //如果链接的数量比当前播放链接的位置 小，赋小值
                                holder.setData(holder.itemView.getContext(), movPlayUrlList.get(position), detailPlaySection.getClickListener(), detailPlaySection.getGroupPlay());
                                //播放监听
                                if (movPlayUrlList.size()<GlobalDATA.PLAY_INDEX){
                                    detailPlaySection.getClickListener().switchPlay(movPlayUrlList.get(position).get(0).getPlayUrl(), GlobalDATA.PLAY_INDEX,position);
                                   // Log.d(TAG, "onSelect: "+movPlayUrlList.get(position).get(0).getPlayUrl());
                                }
                                    detailPlaySection.getClickListener().switchPlay(movPlayUrlList.get(position).get(GlobalDATA.PLAY_INDEX).getPlayUrl(), GlobalDATA.PLAY_INDEX,position);
                                   // Log.d(TAG, "onSelect: "+movPlayUrlList.get(position).get(GlobalDATA.PLAY_INDEX).getPlayUrl());

                                detailPlaySection.setGroupPlay(position);

                            }
                        });
                popupView.setCheckedPosition(detailPlaySection.getGroupPlay());
                popupView.show();

            }
        });
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        RecyclerView playList;
        TextView seeMore;
        TextView playRes;
        private String TAG = "选集";


        ViewHolder(View itemView) {
            super(itemView);
            playList = itemView.findViewById(R.id.play_list);
            seeMore = itemView.findViewById(R.id.see_all);
            playRes = itemView.findViewById(R.id.play_res);
        }

        public void setData(Context context, ArrayList<VideoVo> videoVos, OnSeriClickListener clickListener, int groupPlay) {

            PlayListAdapter playListAdapter = new PlayListAdapter(videoVos, clickListener,groupPlay);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            playList.setLayoutManager(linearLayoutManager);
            playList.setAdapter(playListAdapter);
            playListAdapter.notifyDataSetChanged();
        }
    }

}

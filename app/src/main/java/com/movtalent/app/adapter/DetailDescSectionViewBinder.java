package com.movtalent.app.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.movtalent.app.R;
import me.drakeet.multitype.ItemViewBinder;

/**
 * @author huangyong
 * createTime 2019-09-15
 */
public class DetailDescSectionViewBinder extends ItemViewBinder<DetailDescSection, DetailDescSectionViewBinder.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_detail_video_section, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull DetailDescSection detailVideoSection) {

        holder.vodName.setText(detailVideoSection.getCommonVideoVo().getMovName());
        holder.vodScore.setText(detailVideoSection.getCommonVideoVo().getMovScore() + "分");
        holder.vodDesc.setOnClickListener(v -> {
            if (detailVideoSection.getClickListener()!=null){
                detailVideoSection.getClickListener().clickDesc(detailVideoSection.getCommonVideoVo());
            }
        });

        holder.vodReport.setOnClickListener(v -> {
            if (detailVideoSection.getClickListener()!=null){
                detailVideoSection.getClickListener().clickReport(detailVideoSection.getCommonVideoVo().getMovId());
            }
        });

        holder.vodShare.setOnClickListener(v -> {
            if (detailVideoSection.getClickListener()!=null){
                detailVideoSection.getClickListener().clickShare(detailVideoSection.getCommonVideoVo().getMovId());
            }
        });

        holder.vodRemark.setText(detailVideoSection.getCommonVideoVo().getMovRemark());


    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView vodName;
        TextView vodRemark;
        TextView vodDesc;
        TextView vodReport;
        TextView vodScore;
        TextView vodShare;

        ViewHolder(View itemView) {
            super(itemView);
            vodName = itemView.findViewById(R.id.vod_name);
            vodRemark = itemView.findViewById(R.id.vod_remark);
            vodDesc = itemView.findViewById(R.id.vod_more_desc);
            vodReport = itemView.findViewById(R.id.vod_report);
            vodScore = itemView.findViewById(R.id.vod_score);
            vodShare = itemView.findViewById(R.id.vod_share);
        }
    }

}

package com.movtalent.app.adapter.user;

import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.lib.common.util.DataInter;
import com.lib.common.util.utils.DataCleanManager;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.movtalent.app.App_Config;
import com.movtalent.app.R;
import com.movtalent.app.util.UserUtil;
import com.movtalent.app.view.*;
import me.drakeet.multitype.ItemViewBinder;

/**
 * @author huangyong
 * createTime 2019-09-16
 */
public class SelfBodyViewViewBinder extends ItemViewBinder<SelfBodyView, SelfBodyViewViewBinder.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_self_body_view, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull SelfBodyView selfBodyView) {

        holder.selfFavor.setOnClickListener(v -> {
            if (UserUtil.isLogin()) {
                AllFavorActivity.startTo(v.getContext());
            } else {
                LoginActivity.start(v.getContext());
            }
        });
        holder.selfHistory.setOnClickListener(v -> AllHistoryActivity.startTo(v.getContext()));
        holder.selfDown.setOnClickListener(v -> AllDownLoadActivity.startTo(v.getContext()));
//        holder.selfReport.setOnClickListener(v -> ReportActivitys.start(v.getContext()));
        holder.selfCearCache.setOnClickListener(v -> {
          new XPopup.Builder(holder.itemView.getContext()).asConfirm("提示！", "清空缓存后，图片缓存及登录状态将被清除，下载和浏览记录不会被清除。", new OnConfirmListener() {
              @Override
              public void onConfirm() {
                  DataCleanManager.cleanInternalCache(holder.itemView.getContext());
                  UserUtil.exitLogin(holder.itemView.getContext());
                  holder.itemView.getContext().sendBroadcast(new Intent(DataInter.KEY.ACTION_REFRESH_COIN));
              }
          }).show();
        });
//        holder.selfQQ.setOnClickListener(v -> new XPopup.Builder(v.getContext()).asConfirm("提示", "欢迎加入qq群： 682499902", () -> {
//        }).show());
//        holder.selfShare.setOnClickListener(v -> {
//            final Bitmap thumbBmp = ((BitmapDrawable) v.getContext().getResources().getDrawable(R.drawable.share)).getBitmap();
//            ShareContent mShareContent = new ShareContentPic(thumbBmp);
//            new XPopup.Builder(v.getContext()).asCustom(new BottomShareView(v.getContext(), mShareContent)).show();
//        });

        //预留1点击
        holder.selfJump1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebDetailActivity.start(v.getContext(), App_Config.JUMP_URL_1);
            }
        });
        //预留2点击
        /*holder.selfJump2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebDetailActivity.start(v.getContext(), App_Config.JUMP_URL_2);
            }
        });
        //预留3点击
        holder.selfJump3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebDetailActivity.start(v.getContext(), App_Config.JUMP_URL_3);
            }
        });*/
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView selfFavor;
        TextView selfHistory;
        TextView selfDown;
        TextView selfReport;
        TextView selfCearCache;
        TextView selfQQ;
        TextView selfShare;
        TextView selfJump1;
//        TextView selfJump2;
//        TextView selfJump3;

        ViewHolder(View itemView) {
            super(itemView);

            selfFavor = itemView.findViewById(R.id.self_favor);
            selfHistory = itemView.findViewById(R.id.self_his);
            selfDown = itemView.findViewById(R.id.self_down);
//            selfReport = itemView.findViewById(R.id.self_report);
            selfCearCache = itemView.findViewById(R.id.self_clear_cache);
//            selfQQ = itemView.findViewById(R.id.self_qqgroup);
//            selfShare = itemView.findViewById(R.id.self_share);
            selfJump1 = itemView.findViewById(R.id.self_jump1);
//            selfJump2 = itemView.findViewById(R.id.self_jump2);
//            selfJump3 = itemView.findViewById(R.id.self_jump3);
        }

    }
}

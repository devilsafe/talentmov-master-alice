package com.movtalent.app.adapter.shop;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.lib.common.util.tool.StringUtil;
import com.movtalent.app.App_Config;
import com.movtalent.app.R;
import me.drakeet.multitype.ItemViewBinder;

/**
 * @author huangyong
 * createTime 2019-09-25
 */
public class PayLogSectionViewBinder extends ItemViewBinder<PayLogSection, PayLogSectionViewBinder.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_pay_log_section, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull PayLogSection payLogSection) {
        if (payLogSection!=null){
            holder.coinNum.setText(payLogSection.getCoinNum()+"金币");

            if (Integer.parseInt(payLogSection.getCoinNum())==10){
                holder.payContent.setText(App_Config.VIP_1);
            }else if (Integer.parseInt(payLogSection.getCoinNum())==70){
                holder.payContent.setText(App_Config.VIP_2);
            }else if (Integer.parseInt(payLogSection.getCoinNum())==300){
                holder.payContent.setText(App_Config.VIP_3);
            }else {
                holder.payContent.setText(App_Config.VIP_4);
            }

            holder.payTime.setText("日期:"+StringUtil.getYmdhmsTimeString(Long.parseLong(payLogSection.getPayTime()+"000")));

        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView payTime;
        TextView payContent;
        TextView coinNum;
        ViewHolder(View itemView) {
            super(itemView);
            coinNum = itemView.findViewById(R.id.vip_coin_num);
            payContent = itemView.findViewById(R.id.vip_pay_content);
            payTime = itemView.findViewById(R.id.vip_time_pay);
        }
    }
}

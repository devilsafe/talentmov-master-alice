package com.movtalent.app.adapter.vip;

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
 * createTime 2019-09-18
 */
public class VipHeaderSectionViewBinder extends ItemViewBinder<VipHeaderSection, VipHeaderSectionViewBinder.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_vip_header_section, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull VipHeaderSection vipHeaderSection) {

        if (vipHeaderSection.getClickListener() != null) {
            holder.vipCenter.setOnClickListener(v -> vipHeaderSection.getClickListener().toCenter());
            holder.vipInv.setOnClickListener(v -> vipHeaderSection.getClickListener().toShop());
            holder.vipCoinNum.setText(vipHeaderSection.getCoinNum());
            vipHeaderSection.getClickListener().initDataListener(dataBean -> {
                holder.vipCoinNum.setText(dataBean);
            });
        }

    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView vipCoinNum;
        TextView vipInv;
        TextView vipCenter;

        ViewHolder(View itemView) {
            super(itemView);
            vipInv = itemView.findViewById(R.id.vip_inv);
            vipCoinNum = itemView.findViewById(R.id.vip_coin_num);
            vipCenter = itemView.findViewById(R.id.vip_center);

        }
    }

    public interface OnVipClickListener {
        void toCenter();

        void toShop();

        void initDataListener(OnDataChangeListener onDataChangeListener);
    }

    public interface OnDataChangeListener{
        void onChange(String coinNum);
    }
}

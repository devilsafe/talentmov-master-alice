package com.movtalent.app.adapter.shop;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.movtalent.app.R;
import me.drakeet.multitype.ItemViewBinder;

/**
 * @author huangyong
 * createTime 2019-09-26
 */
public class VipBrandSectionViewBinder extends ItemViewBinder<VipBrandSection, VipBrandSectionViewBinder.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_vip_brand_section, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull VipBrandSection vipBrandSection) {
        holder.vipTitle.setText(vipBrandSection.getItemTitle());
        holder.vipCoin.setText(vipBrandSection.getPayDesc());
        holder.vipPayText.setText(vipBrandSection.getPayText());
        Glide.with(holder.itemView.getContext()).load(vipBrandSection.getItemIcon()).into(holder.vipIcon);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (vipBrandSection.getClickItemPayListener()!=null){
                    vipBrandSection.getClickItemPayListener().onItemClick();
                }
            }
        });
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView vipTitle;
        TextView vipCoin;
        TextView vipPayText;
        TextView vipToPay;
        ImageView vipIcon;
        ViewHolder(View itemView) {
            super(itemView);
            vipTitle= itemView.findViewById(R.id.vip_title);
            vipCoin= itemView.findViewById(R.id.vip_coin_desc);
            vipPayText= itemView.findViewById(R.id.vip_pay_desc);
            vipToPay= itemView.findViewById(R.id.vip_topay);
            vipIcon= itemView.findViewById(R.id.vip_icon);
        }
    }

    public interface OnClickItemPayListener{
        void onItemClick();
    }
}

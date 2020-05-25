package com.movtalent.app.adapter.user;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.lib.common.util.tool.StringUtil;
import com.movtalent.app.R;
import com.movtalent.app.model.dto.LoginDto;
import com.movtalent.app.util.UserUtil;
import com.movtalent.app.view.LoginActivity;
import com.movtalent.app.view.UserProfileActivity;
import me.drakeet.multitype.ItemViewBinder;

/**
 * @author huangyong
 * createTime 2019-09-16
 */
public class SelfHeadViewViewBinder extends ItemViewBinder<SelfHeadView, SelfHeadViewViewBinder.ViewHolder> {


    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_self_head_view, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull SelfHeadView selfHeadView) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.ic_user_avator);

        if (UserUtil.isLogin()) {
            if (TextUtils.isEmpty(selfHeadView.userIcon)) {
                Glide.with(holder.itemView.getContext()).load(selfHeadView.getUserIcon()).transition(DrawableTransitionOptions.withCrossFade(300)).apply(requestOptions).into(holder.userIcon);
            } else {
                Glide.with(holder.itemView.getContext()).load(Integer.parseInt(selfHeadView.getUserIcon())).transition(DrawableTransitionOptions.withCrossFade(300)).apply(requestOptions).into(holder.userIcon);
            }
            holder.userName.setText(UserUtil.getUserName());
            holder.userCoin.setText("金币：" + UserUtil.getUserCoin());
            holder.userCoin.setVisibility(View.VISIBLE);
            holder.endTime.setVisibility(View.VISIBLE);
            holder.endTime.setText("会员到期：" + StringUtil.getYmdhmsTimeStringv(Long.parseLong(UserUtil.getUserVipEndTime() + "000")));
            holder.btLogin.setVisibility(View.INVISIBLE);
        } else {
            Glide.with(holder.itemView.getContext()).load(R.drawable.ic_user_avator).transition(DrawableTransitionOptions.withCrossFade(300)).apply(requestOptions).into(holder.userIcon);
            holder.endTime.setVisibility(View.INVISIBLE);
            holder.btLogin.setVisibility(View.VISIBLE);
            holder.userCoin.setVisibility(View.INVISIBLE);

            holder.userName.setVisibility(View.INVISIBLE);
            holder.btLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LoginActivity.start(v.getContext());
                }
            });
        }

        holder.userCoin.setText("金币：" + selfHeadView.getUserCoins());
        selfHeadView.getOnloginListener().statuLogin(new OnLoginStatusChanged() {
            @Override
            public void onLine(LoginDto.DataBean dataBean) {
                holder.userName.setVisibility(View.VISIBLE);
                holder.userCoin.setVisibility(View.VISIBLE);
                holder.btLogin.setVisibility(View.INVISIBLE);
                holder.userName.setText(dataBean.getUser_name());
                holder.userCoin.setText("金币：" + dataBean.getUser_points());
            }

            @Override
            public void offLine() {
                Glide.with(holder.itemView.getContext()).load(R.drawable.ic_user_avator).transition(DrawableTransitionOptions.withCrossFade(300)).apply(requestOptions).into(holder.userIcon);
                holder.userName.setVisibility(View.INVISIBLE);
                holder.btLogin.setVisibility(View.VISIBLE);
                holder.endTime.setVisibility(View.INVISIBLE);
                holder.userCoin.setVisibility(View.INVISIBLE);
                holder.btLogin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LoginActivity.start(v.getContext());
                    }
                });
            }

            @Override
            public void updateCoin(String coin) {
                holder.userCoin.setText("金币：" + coin);
            }
        });
        holder.getCoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selfHeadView.getOnloginListener().goToSharePage();
            }
        });
        holder.userIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UserUtil.isLogin()) {
                    UserProfileActivity.start(v.getContext());
                }
            }
        });
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView userIcon;
        TextView userName;
        TextView userCoin;
        TextView btLogin;
        TextView getCoin;
        TextView endTime;

        ViewHolder(View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.user_name);
            userCoin = itemView.findViewById(R.id.user_coin);
            userIcon = itemView.findViewById(R.id.user_icon);
            btLogin = itemView.findViewById(R.id.bt_login);
            getCoin = itemView.findViewById(R.id.get_coin);
            endTime = itemView.findViewById(R.id.user_end_time);
        }
    }

    public interface OnloginListener {
        void onLogin();

        void statuLogin(OnLoginStatusChanged isLogin);

        void onExit();

        void goToSharePage();
    }

    public interface OnLoginStatusChanged {
        void onLine(LoginDto.DataBean dataBean);

        void offLine();

        void updateCoin(String coin);
    }
}

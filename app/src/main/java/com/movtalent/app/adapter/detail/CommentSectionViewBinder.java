package com.movtalent.app.adapter.detail;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.lib.common.util.tool.StringUtil;
import com.movtalent.app.App_Config;
import com.movtalent.app.R;
import com.movtalent.app.db.DiggDBHelper;
import com.movtalent.app.util.UserUtil;
import com.movtalent.app.view.LoginActivity;
import me.drakeet.multitype.ItemViewBinder;

/**
 * @author huangyong
 * createTime 2019-09-21
 */
public class CommentSectionViewBinder extends ItemViewBinder<CommentSection, CommentSectionViewBinder.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_comment_section, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull CommentSection commentSection) {

        if (UserUtil.isLogin()) {
            int userId = Integer.parseInt(UserUtil.getUserId());
            //楼主
            if (commentSection.getCommentVo().getUserId() == userId) {
                holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        if (commentSection.getReplyListener() != null) {
                            commentSection.getReplyListener().onDeleteRoot();
                        }

                        return true;
                    }
                });

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (commentSection.getReplyListener() != null) {
                            commentSection.getReplyListener().onDeleteRoot();
                        }
                    }
                });

            } else {
                //非楼主
                holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        if (commentSection.getReplyListener() != null) {
                            commentSection.getReplyListener().onReport();
                        }
                        return true;
                    }
                });


                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //点击别人，可以快速回复
                        if (commentSection.getReplyListener() != null) {
                            commentSection.getReplyListener().onReply();
                        }
                    }
                });
            }


        } else {
            //没有登录的用户，长按直接去登录
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    LoginActivity.start(v.getContext());
                    return true;
                }
            });
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LoginActivity.start(v.getContext());
                }
            });
        }

        if (commentSection.getCommentVo().getReplyVo() != null) {
            holder.replyContainer.setVisibility(View.VISIBLE);
            holder.rUserName.setText(commentSection.getCommentVo().getReplyVo().getUserName());
            holder.rContent.setText(commentSection.getCommentVo().getReplyVo().getCommentContent());
        }
        holder.cContent.setText(commentSection.getCommentVo().getCommentContent());
        holder.cUserName.setText(commentSection.getCommentVo().getUserName());
        holder.commentTime.setText(StringUtil.gettimeAcurrate(StringUtil.getYmdhmsTimeString(Long.parseLong(commentSection.getCommentVo().getCommentTime())*1000)));

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.ic_user_avator);
        if (TextUtils.isEmpty(commentSection.getCommentVo().getUserIcon())) {
            Glide.with(holder.itemView.getContext()).load(commentSection.getCommentVo().getUserIcon()).transition(DrawableTransitionOptions.withCrossFade(300)).apply(requestOptions).into(holder.avator);
        } else {
            Glide.with(holder.itemView.getContext()).load(App_Config.ICON_GROUP[Integer.parseInt(commentSection.getCommentVo().getUserIcon())]).transition(DrawableTransitionOptions.withCrossFade(300)).apply(requestOptions).into(holder.avator);
        }


        if (commentSection.getCommentVo().getCommentUp()>0){
            holder.commentDigg.setText(commentSection.getCommentVo().getCommentUp()+"");
        }
        holder.commentDigg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!DiggDBHelper.dealDigg(commentSection.getCommentVo())){
                    holder.commentDigg.setText(commentSection.getCommentVo().getCommentUp()+1+"");
                    commentSection.getCommentVo().setCommentUp(commentSection.getCommentVo().getCommentUp()+1);
                }else {
                    holder.commentDigg.setText(commentSection.getCommentVo().getCommentUp()-1==0?"":commentSection.getCommentVo().getCommentUp()-1+"");
                    commentSection.getCommentVo().setCommentUp(commentSection.getCommentVo().getCommentUp()-1);
                }
                showDiggAnim();
            }
        });
    }

    private void showDiggAnim() {

    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout replyContainer;
        ImageView avator;
        TextView commentDigg;
        TextView cUserName;
        TextView cContent;
        TextView rContent;
        TextView rUserName;
        TextView commentTime;

        ViewHolder(View itemView) {
            super(itemView);

            avator = itemView.findViewById(R.id.avator);
            commentDigg = itemView.findViewById(R.id.comment_digg);
            cUserName = itemView.findViewById(R.id.comment_user_name);
            cContent = itemView.findViewById(R.id.comment_content);
            rContent = itemView.findViewById(R.id.reply_content);
            rUserName = itemView.findViewById(R.id.reply_user_name);
            replyContainer = itemView.findViewById(R.id.comment_reply);
            commentTime = itemView.findViewById(R.id.comment_time);

        }
    }

    public interface OnReplyListener {
        void onReply();

        void onDeleteRoot();

        void onReport();

    }
}

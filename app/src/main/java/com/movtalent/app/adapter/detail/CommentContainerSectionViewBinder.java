package com.movtalent.app.adapter.detail;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.movtalent.app.R;
import com.movtalent.app.model.vo.CommentVo;
import com.movtalent.app.util.UserUtil;
import me.drakeet.multitype.ItemViewBinder;
import me.drakeet.multitype.MultiTypeAdapter;

import java.util.ArrayList;

/**
 * @author huangyong
 * createTime 2019-09-21
 */
public class CommentContainerSectionViewBinder extends ItemViewBinder<CommentContainerSection, CommentContainerSectionViewBinder.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_comment_container_section, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull CommentContainerSection commentContainerSection) {

        holder.setCommentData(commentContainerSection.getVos(), commentContainerSection.getCommentLoadListener());
        if (commentContainerSection.getCommentLoadListener() != null) {
            commentContainerSection.getCommentLoadListener().setOnLoadMoreListener(new OnLoadMoreListener() {
                @Override
                public void loadMore(ArrayList<CommentVo> commentvos) {
                    holder.loadMore(commentvos, commentContainerSection.getCommentLoadListener());
                }

                @Override
                public void addComment(CommentVo commentVo) {
                    holder.addComment(commentVo, commentContainerSection.getCommentLoadListener());
                }

                @Override
                public void removeItem(CommentVo commentVo) {
                    holder.removeItem(commentVo);
                }
            });
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        RecyclerView cList;
        MultiTypeAdapter multiTypeAdapter;
        ArrayList<Object> items;
        CommentHeaderSectionViewBinder.OnShowListener showListener;

        ViewHolder(View itemView) {
            super(itemView);
            cList = itemView.findViewById(R.id.comment_list);
            items = new ArrayList<>();
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(itemView.getContext());
            cList.setLayoutManager(linearLayoutManager);
            multiTypeAdapter = new MultiTypeAdapter();
            multiTypeAdapter.register(CommentSection.class, new CommentSectionViewBinder());
            multiTypeAdapter.register(CommentHeaderSection.class, new CommentHeaderSectionViewBinder());
            multiTypeAdapter.setItems(items);
            items.add(0, new CommentHeaderSection(new CommentHeaderSectionViewBinder.OnHeadListener() {
                @Override
                public void onHeadInit(CommentHeaderSectionViewBinder.OnShowListener onShowListener) {
                    showListener = onShowListener;
                }
            }));
            cList.setAdapter(multiTypeAdapter);
        }

        public void setCommentData(ArrayList<CommentVo> vos, OnCommentLoadListener commentLoadListener) {
            for (CommentVo v : vos) {
                CommentSection section = getCommentSection(commentLoadListener, v);
                items.add(section);
            }
        }

        public void loadMore(ArrayList<CommentVo> commentvos, OnCommentLoadListener commentLoadListener) {
            for (CommentVo v : commentvos) {
                CommentSection section = getCommentSection(commentLoadListener, v);
                items.add(section);
            }
            refreshHeadVisible();
            multiTypeAdapter.notifyDataSetChanged();
        }

        public void addComment(CommentVo commentVo, OnCommentLoadListener commentLoadListener) {
            CommentSection section = getCommentSection(commentLoadListener, commentVo);
            section.getCommentVo().setUserIcon(UserUtil.getUserIcon());
            items.add(1, section);
            refreshHeadVisible();
            multiTypeAdapter.notifyDataSetChanged();
        }

        public void removeItem(CommentVo commentVo) {
            for (int i = 0; i < items.size(); i++) {
                Object o = items.get(i);
                if (o instanceof CommentSection && ((CommentSection) o).getCommentVo().getCommentId()==commentVo.getCommentId()){
                    items.remove(o);
                    multiTypeAdapter.notifyItemRemoved(i);
                    break;
                }
            }
            refreshHeadVisible();
        }

        private void refreshHeadVisible() {
            if (showListener != null) {
                if (items.size() > 1) {
                    showListener.onHide();
                } else {
                    showListener.onShow();
                }
            }
        }
    }

    private static CommentSection getCommentSection(OnCommentLoadListener commentLoadListener, CommentVo v) {
        return new CommentSection(v, new CommentSectionViewBinder.OnReplyListener() {
            @Override
            public void onReply() {
                commentLoadListener.onReply(v);
            }

            @Override
            public void onDeleteRoot() {
                commentLoadListener.onDelete(v);
            }

            @Override
            public void onReport() {
                commentLoadListener.onReport(v);
            }
        });
    }


    public interface OnCommentLoadListener {
        void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener);

        void onReply(CommentVo commentVo);

        void onDelete(CommentVo commentVo);

        void onReport(CommentVo commentVo);
    }

    public interface OnLoadMoreListener {
        void loadMore(ArrayList<CommentVo> commentvos);

        void addComment(CommentVo commentVo);

        void removeItem(CommentVo commentVo);
    }
}

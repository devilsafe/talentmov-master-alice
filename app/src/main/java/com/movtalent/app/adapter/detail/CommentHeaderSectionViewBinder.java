package com.movtalent.app.adapter.detail;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.movtalent.app.R;
import me.drakeet.multitype.ItemViewBinder;

/**
 * @author huangyong
 * createTime 2019-09-21
 */
public class CommentHeaderSectionViewBinder extends ItemViewBinder<CommentHeaderSection, CommentHeaderSectionViewBinder.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_comment_header_section, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull CommentHeaderSection commentHeaderSection) {
        if (commentHeaderSection.getHeadListener()!=null){
            commentHeaderSection.getHeadListener().onHeadInit(new OnShowListener() {
                @Override
                public void onShow() {

                    holder.sofa.post(new Runnable() {
                        @Override
                        public void run() {
                            holder.sofa.setVisibility(View.VISIBLE);
                        }
                    });
                }

                @Override
                public void onHide() {

                    holder.sofa.post(new Runnable() {
                        @Override
                        public void run() {
                            holder.sofa.setVisibility(View.GONE);
                        }
                    });

                }
            });
        }

    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ViewGroup sofa;
        ViewHolder(View itemView) {
            super(itemView);
           sofa =  itemView.findViewById(R.id.sofa_view);
        }
    }

    public interface OnHeadListener{
        void onHeadInit(OnShowListener onShowListener);
    }

    public interface OnShowListener{
        void onShow();
        void onHide();
    }
}

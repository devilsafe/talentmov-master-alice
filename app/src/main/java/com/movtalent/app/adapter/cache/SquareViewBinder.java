package com.movtalent.app.adapter.cache;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.movtalent.app.R;
import com.movtalent.app.util.ToastUtil;
import me.drakeet.multitype.ItemViewBinder;

import java.util.Set;

import static java.lang.String.valueOf;

/**
 * @author drakeet
 */
public class SquareViewBinder extends ItemViewBinder<Square, SquareViewBinder.ViewHolder> {

    private final @NonNull
    Set<Integer> selectedSet;


    public SquareViewBinder(@NonNull Set<Integer> selectedSet) {
        this.selectedSet = selectedSet;
    }


    @Override
    protected @NonNull
    ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_square, parent, false);
        return new ViewHolder(root);
    }


    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull Square square) {
        holder.square = square;
        holder.squareView.setText(valueOf(square.number));
        if (square.isSelected) {
            holder.statuTag.setImageResource(R.drawable.ic_cache_down);
            holder.statuTag.setVisibility(View.VISIBLE);
        }
        if (square.finished){
            holder.statuTag.setImageResource(R.drawable.ic_succeed);
            holder.statuTag.setVisibility(View.VISIBLE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (square.clickListener!=null && !square.finished && !square.isSelected){
                    square.clickListener.onClick(v);
                    holder.square.isSelected= true;
                    holder.statuTag.setVisibility(View.VISIBLE);
                    holder.statuTag.setImageResource(R.drawable.ic_cache_down);
                }else {
                    ToastUtil.showMessage("当前节目已在缓存列表");
                }

            }
        });
    }


    public @NonNull
    Set<Integer> getSelectedSet() {
        return selectedSet;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView squareView;
        private Square square;
        ImageView statuTag;

        ViewHolder(final View itemView) {
            super(itemView);
            squareView = itemView.findViewById(R.id.square);
            statuTag = itemView.findViewById(R.id.status_tag);
        }
    }
}

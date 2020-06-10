package com.movtalent.app.view;

import android.content.Context;
import androidx.annotation.NonNull;
import android.widget.TextView;
import com.lxj.xpopup.core.CenterPopupView;
import com.movtalent.app.R;
import com.movtalent.app.model.dto.PostDto;

/**
 * @author huangyong
 * createTime 2019-10-16
 */
public class PostPop extends CenterPopupView {


    private TextView postTitle;
    private TextView content;
    private final PostDto dto;

    public PostPop(@NonNull Context context, PostDto dto) {
        super(context);
        this.dto = dto;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.post_layout;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        content = findViewById(R.id.content);
        postTitle = findViewById(R.id.post_title);
        content.setText(dto.getData().getContent());
        postTitle.setText(dto.getData().getTitle());

    }
}

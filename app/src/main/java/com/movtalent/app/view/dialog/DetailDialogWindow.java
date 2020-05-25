package com.movtalent.app.view.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.lxj.xpopup.core.BottomPopupView;
import com.movtalent.app.R;
import com.movtalent.app.model.vo.CommonVideoVo;


/**
 * @author huangyong
 * createTime 2019-09-21
 */
public class DetailDialogWindow extends BottomPopupView {

    private CommonVideoVo commonVideoVo;

    public DetailDialogWindow(@NonNull Context context, CommonVideoVo commonVideoVo) {
        super(context);
        this.commonVideoVo = commonVideoVo;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.detail_all_layout;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        TextView textView = findViewById(R.id.detail_desc);
        StringBuilder builder = new StringBuilder();
        builder.append("导演：" + (TextUtils.isEmpty(commonVideoVo.getMovDirector()) ? "" : commonVideoVo.getMovDirector()) + "\n");
        builder.append("演员：" + (TextUtils.isEmpty(commonVideoVo.getMovActor()) ? "" : commonVideoVo.getMovActor()) + "\n");
        builder.append("分类：" + (TextUtils.isEmpty(commonVideoVo.getMovTypeName()) ? "" : commonVideoVo.getMovTypeName()) + "\n");
        builder.append("年代：" + commonVideoVo.getMovYear() + "\n");
        builder.append("地区：" + commonVideoVo.getMovArea() + "\n");
        builder.append("简介：" + commonVideoVo.getMovDesc().trim() + "\n");

        textView.setText(builder.toString());
        ImageView close = findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }


}

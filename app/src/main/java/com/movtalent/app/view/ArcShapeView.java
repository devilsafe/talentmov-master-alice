package com.movtalent.app.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import com.lib.common.util.tool.StringUtil;
import com.movtalent.app.R;

/**
 * @author huangyong
 * createTime 2019-09-18
 */
public class ArcShapeView extends View {


    private Paint paint;
    private RectF oval2;
    private RectF rectF;
    private Rect bg;

    public ArcShapeView(Context context) {
        this(context,null);
    }

    public ArcShapeView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ArcShapeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);

        paint.setColor(context.getResources().getColor(R.color.colorPrimary));
        oval2 = new RectF(0,0,0,0);
        bg = new Rect(0,0,0,0);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        // 设置个新的长方形，扫描测量
        oval2.set(-StringUtil.dip2px(getContext(),100),0, getMeasuredWidth()+StringUtil.dip2px(getContext(),100), StringUtil.dip2px(getContext(),130));
        bg.set(0,0,getMeasuredWidth(),StringUtil.dip2px(getContext(),100));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (bg!=null){
            canvas.drawRect(bg, paint);
        }
        if (oval2!=null){
            canvas.drawArc(oval2, 0, 180, true, paint);
        }
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);

    }
}

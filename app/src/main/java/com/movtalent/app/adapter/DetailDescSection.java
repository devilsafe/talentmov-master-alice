package com.movtalent.app.adapter;

import com.movtalent.app.adapter.event.OnDetailClickListener;
import com.movtalent.app.model.vo.CommonVideoVo;

/**
 * @author huangyong
 * createTime 2019-09-15
 */
public class DetailDescSection {

    public CommonVideoVo getCommonVideoVo() {
        return commonVideoVo;
    }

    public OnDetailClickListener getClickListener() {
        return clickListener;
    }

    private OnDetailClickListener clickListener;
    private CommonVideoVo commonVideoVo;

    public DetailDescSection(CommonVideoVo commonVideoVo,OnDetailClickListener clickListener) {
        this.clickListener = clickListener;
        this.commonVideoVo = commonVideoVo;
    }
}
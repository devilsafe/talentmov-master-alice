package com.movtalent.app.adapter.event;

import com.movtalent.app.model.vo.CommonVideoVo;

/**
 * @author huangyong
 * createTime 2019-09-15
 */
public interface OnDetailClickListener {
    void clickReport(String vodId);

    void clickShare(String vodId);

    void clickDesc(CommonVideoVo videoVo);
}

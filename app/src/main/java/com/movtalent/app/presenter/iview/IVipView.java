package com.movtalent.app.presenter.iview;

import com.movtalent.app.model.dto.PayLogDto;
import com.movtalent.app.model.vo.VipVo;

/**
 * @author huangyong
 * createTime 2019-09-25
 */
public interface IVipView extends IBase {
    void payDone(VipVo from);

    void loadLogDone(PayLogDto data);
}

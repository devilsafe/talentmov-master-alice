package com.movtalent.app.presenter.iview;

import com.movtalent.app.model.dto.UpdateDto;

/**
 * @author huangyong
 * createTime 2019-10-09
 */
public interface IUpdate {
    void loadDone(UpdateDto dto);
    void loadEmpty();
}

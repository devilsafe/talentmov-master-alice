package com.movtalent.app.presenter.iview;

import com.movtalent.app.model.dto.LoginDto;

/**
 * @author huangyong
 * createTime 2019-09-17
 */
public interface IUserView extends IBase{
    void loadDone(LoginDto dto);
}

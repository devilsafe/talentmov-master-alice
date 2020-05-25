package com.movtalent.app.presenter.iview;

import com.movtalent.app.model.dto.SearchWdDto;
import com.movtalent.app.model.vo.CommonVideoVo;

import java.util.ArrayList;

/**
 * @author huangyong
 * createTime 2019-09-15
 */
public interface ISearch extends IBase{
    void loadDone(ArrayList<CommonVideoVo> videoVos);
    void loadWdDone(SearchWdDto wordDto);
}

package com.movtalent.app.presenter;

import com.movtalent.app.http.ApiService;
import com.movtalent.app.http.BaseApi;
import com.movtalent.app.model.dto.PostDto;

/**
 * @author huangyong
 * createTime 2019-10-16
 */
public class PublishPresenter {


    private IPost iPost;

    public PublishPresenter(IPost iPost) {
        this.iPost = iPost;
    }

    public void getPost() {
        BaseApi.request(BaseApi.createApi(ApiService.class)
                        .getPost(), new BaseApi.IResponseListener<PostDto>() {
                    @Override
                    public void onSuccess(PostDto data) {
                        iPost.loadPost(data);
                    }

                    @Override
                    public void onFail() {
                    }
                }
        );
    }

    public void getAd() {

    }

    public interface IPost {
        void loadPost(PostDto dto);
    }
}

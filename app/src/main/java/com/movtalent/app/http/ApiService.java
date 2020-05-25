package com.movtalent.app.http;


import com.media.playerlib.model.AdConfigDto;
import com.movtalent.app.model.dto.*;
import io.reactivex.Observable;
import retrofit2.http.*;

/**
 * Created by Huangyong on 2017/10/24.
 */

public interface ApiService {


    @GET(UrlConfig.getVodType)
    Observable<TypeListDto> getTypeList();

    @GET(UrlConfig.getMovieList)
    Observable<VideoListDto> getTypeMovList(@Query("type_id") int typeId, @Query("page") int page, @Query("limit") int limit);

    @GET(UrlConfig.getVodDetail)
    Observable<VideoListDto> getMovDetail(@Query("vodid") int vodId);

    @GET(UrlConfig.getHomeLevel)
    Observable<HomeLevelDto> getHomeLevel();

    @GET(UrlConfig.getHomeLevelAll)
    Observable<VideoListDto> getHomeLevelAll(@Query("level") int typeId, @Query("page") int page, @Query("limit") int limit);

    @GET(UrlConfig.getRecmend)
    Observable<VideoListDto> getRecommend(@Query("type") int typeId);

    @GET(UrlConfig.getTopicRoot)
    Observable<TopicRDto> getTopicRoot(@Query("page") int page, @Query("limit") int limit);

    @GET(UrlConfig.getTopicList)
    Observable<VideoListDto> getTopicList(@Query("topic_id") int topicId);

    @GET(UrlConfig.getSearch)
    Observable<VideoListDto> getMovSearch(@Query("key") String word);

    @GET(UrlConfig.getSearchRecWd)
    Observable<SearchWdDto> getMovRecSearchWord();

    @GET(UrlConfig.login)
    Observable<LoginDto> login(@Query("user") String username, @Query("pass") String pwd);

    @GET(UrlConfig.regist)
    Observable<LoginDto> regist(@Query("user") String username, @Query("pass") String pwd, @Query("mail") String mail);

    @FormUrlEncoded
    @POST(UrlConfig.addUserCoin)
    Observable<PointDto> addUserCoin(@Field("utoken") String utoken);

    @FormUrlEncoded
    @POST(UrlConfig.getUserCoin)
    Observable<PointDto> getUserCoin(@Field("utoken") String utoken);

    @FormUrlEncoded
    @POST(UrlConfig.GET_HAVE_FAVOR)
    Observable<FavorDto> getUserFavorOrNot(@Field("uid") String userId, @Field("vod_id") int vodId);

    @FormUrlEncoded
    @POST(UrlConfig.GET_FAVOR)
    Observable<VideoListDto> getUserFavor(@Field("uid") String userId);

    @FormUrlEncoded
    @POST(UrlConfig.CANCEL_FAVOR)
    Observable<FavorDto> cancelFavor(@Field("uid") String userId, @Field("vod_id") int vodId);

    @FormUrlEncoded
    @POST(UrlConfig.ADD_FAVOR)
    Observable<FavorDto> doFavor(@Field("uid") String userId, @Field("vod_id") int vodId);


    @FormUrlEncoded
    @POST(UrlConfig.NEW_COMMENT)
    Observable<CommentDto> newComment(@Field("uid") String userId, @Field("vod_uname") String vodUname, @Field("vod_comment_content") String commentContent,
                                      @Field("vod_id") int vodId,  @Field("vod_comment_pid") int commentPid);

    @FormUrlEncoded
    @POST(UrlConfig.DELETE_COMMENT)
    Observable<CommentDDto> deleteComment(@Field("uid") String userId, @Field("vod_comment_id") int commentId);

    @FormUrlEncoded
    @POST(UrlConfig.GET_COMMENT)
    Observable<CommentGDto> getComment(@Field("vod_id") int vodId, @Field("page") int page, @Field("limit") int limit);

    @FormUrlEncoded
    @POST(UrlConfig.DIGG_COMMENT)
    Observable<Object> diggComment(@Field("vod_comment_id") int commentId);

    @FormUrlEncoded
    @POST(UrlConfig.CANCEL_DIGG_COMMENT)
    Observable<Object> cancelDiggComment(@Field("vod_comment_id") int commentId);

    @FormUrlEncoded
    @POST(UrlConfig.BUY_VIP)
    Observable<BuyVipDto> buyVip(@Field("uid") String userId, @Field("vip_type") int vipTyp);

    @FormUrlEncoded
    @POST(UrlConfig.GET_PAY_LOG)
    Observable<PayLogDto> getPayLog(@Field("uid") String userId);

    @FormUrlEncoded
    @POST(UrlConfig.changeIcon)
    Observable<IconDto> changeIcon(@Field("uid") String userId, @Field("index") String index);

    @GET(UrlConfig.getUpdate)
    Observable<UpdateDto> getUpdate();

    @GET(UrlConfig.getPublish)
    Observable<PostDto> getPost();

    @GET(UrlConfig.getCategoryList)
    Observable<VideoListDto> getCategory(@Query("type") int typeId,
                                        @Query("area") String area,
                                        @Query("year") int year,
                                        @Query("page") int page,
                                        @Query("limit") int limit);
    @GET(UrlConfig.getADConfig)
    Observable<AdConfigDto> getAd();
}

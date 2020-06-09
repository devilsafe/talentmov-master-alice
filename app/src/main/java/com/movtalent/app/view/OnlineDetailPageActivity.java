package com.movtalent.app.view;

import android.Manifest;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.google.gson.Gson;
import com.lib.common.util.DataInter;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.media.playerlib.manager.ParsePlayUtils;
import com.media.playerlib.manager.PlayerPresenter;
import com.media.playerlib.model.VideoPlayVo;
import com.media.playerlib.widget.GlobalDATA;
import com.movtalent.app.R;
import com.movtalent.app.adapter.*;
import com.movtalent.app.adapter.cache.Square;
import com.movtalent.app.adapter.cache.SquareViewBinder;
import com.movtalent.app.adapter.detail.CommentContainerSection;
import com.movtalent.app.adapter.detail.CommentContainerSectionViewBinder;
import com.movtalent.app.adapter.event.OnDetailClickListener;
import com.movtalent.app.adapter.event.OnSeriClickListener;
import com.movtalent.app.db.HistoryDBhelper;
import com.movtalent.app.model.FooterView;
import com.movtalent.app.model.VideoVo;
import com.movtalent.app.model.dto.LoginDto;
import com.movtalent.app.model.vo.CommentVo;
import com.movtalent.app.model.vo.CommonVideoVo;
import com.movtalent.app.presenter.CommentPresenter;
import com.movtalent.app.presenter.DetailPresenter;
import com.movtalent.app.presenter.DetailRecommendPresenter;
import com.movtalent.app.presenter.FavorPresenter;
import com.movtalent.app.presenter.iview.ICView;
import com.movtalent.app.presenter.iview.IDetailView;
import com.movtalent.app.presenter.iview.IFavor;
import com.movtalent.app.presenter.iview.IRecView;
import com.movtalent.app.util.ToastUtil;
import com.movtalent.app.util.UserUtil;
import com.movtalent.app.view.dialog.BottomInputSheet;
import com.movtalent.app.view.dialog.BottomShareView;
import com.movtalent.app.view.dialog.DetailDialogWindow;
import com.tbruyelle.rxpermissions.RxPermissions;
import jaygoo.library.m3u8downloader.M3U8Library;
import jaygoo.library.m3u8downloader.control.DownloadPresenter;
import jaygoo.library.m3u8downloader.db.M3U8dbManager;
import jaygoo.library.m3u8downloader.db.dao.DoneDao;
import jaygoo.library.m3u8downloader.db.dao.DowningDao;
import jaygoo.library.m3u8downloader.db.table.M3u8DoneInfo;
import jaygoo.library.m3u8downloader.db.table.M3u8DownloadingInfo;
import jaygoo.library.m3u8downloader.utils.MD5Utils;
import kale.sharelogin.content.ShareContent;
import kale.sharelogin.content.ShareContentPic;
import me.drakeet.multitype.MultiTypeAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

/**
 * @author huangyong
 * createTime 2019-09-14
 */
public class OnlineDetailPageActivity extends AppCompatActivity implements IDetailView, IRecView, View.OnClickListener {
    private VideoPlayVo videoPlayVo;
    private String PathUrl ="";
    private int groupPlay = 0;
    @BindView(R.id.video_container)
    FrameLayout videoContainer;
    @BindView(R.id.detail_content)
    RecyclerView detailContent;
    @BindView(R.id.full_container)
    FrameLayout fullContainer;
    @BindView(R.id.edit_comment)
    TextView editComment;
    @BindView(R.id.favor)
    ImageView favor;
    @BindView(R.id.download)
    ImageView download;
    private MultiTypeAdapter detailAdapter;
    private DetailPresenter detailPresenter;
    private FrameLayout frameLayout;
    @VisibleForTesting
    List<Object> items;
    private PlayerPresenter playerPresenter;
    private FavorPresenter favorPresenter;
    private int vodId;
    private CommentPresenter commentPresenter;
    private int index;
    private CommonVideoVo videoVo;
    private ArrayList<String> urls = new ArrayList<>();
    ;
    private CommonVideoVo globalVideoVo;
    private String TAG = "播放页";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail_layout);
        ButterKnife.bind(this);
        frameLayout = (FrameLayout) ViewGroup.inflate(OnlineDetailPageActivity.this, R.layout.item_detail_ad_entity, videoContainer);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        vodId = getIntent().getIntExtra(DataInter.KEY.VOD_ID, 0);

        if (vodId == 0) {
            ToastUtil.showMessage("数据有误");
            return;
        }
        detailAdapter = new MultiTypeAdapter();

        LinearLayoutManager manager = new LinearLayoutManager(this);

        detailContent.setLayoutManager(manager);
        detailContent.setAdapter(detailAdapter);

        detailAdapter.register(DetailDescSection.class, new DetailDescSectionViewBinder());
        detailAdapter.register(DetailPlaySection.class, new DetailPlaySectionViewBinder());
        detailAdapter.register(DetailRecmmendSection.class, new DetailRecmmendSectionViewBinder());
        detailAdapter.register(FooterView.class, new FooterViewViewBinder());
        detailAdapter.register(CommentContainerSection.class, new CommentContainerSectionViewBinder());

        detailPresenter = new DetailPresenter(this);
        detailPresenter.getDetail(vodId);


        items = new ArrayList<>();

        playerPresenter = new PlayerPresenter();

        detailAdapter.setItems(items);


        initFavorAbout();
        initCommentAbout();
        initLisener();

    }


    private void initCommentAbout() {
        index = 1;
        commentPresenter = new CommentPresenter(icView);

    }

    private void initFavorAbout() {
        favorPresenter = new FavorPresenter(iFavor);
        String userInfo = UserUtil.getUserInfo(this);
        if (TextUtils.isEmpty(userInfo)) {
            return;
        }
        LoginDto.DataBean dataBean = new Gson().fromJson(userInfo, LoginDto.DataBean.class);
        favorPresenter.getHaveFavor(dataBean.getUser_id(), vodId);

    }

    private void initLisener() {
        editComment.setOnClickListener(this);
        favor.setOnClickListener(this);
        download.setOnClickListener(this);
    }

    //第一次请求数据
    @Override
    public void loadDone(CommonVideoVo commonVideoVo) {
        this.videoVo = commonVideoVo;
        loadPlay(commonVideoVo);
        loadDetail(commonVideoVo);
        loadSeri(commonVideoVo);
        loadRecommend(commonVideoVo);
        //第一次请求评论
        CommentContainerSection commentContainerSection = new CommentContainerSection(new ArrayList<>(), onloadListener);
        items.add(commentContainerSection);
        detailAdapter.notifyDataSetChanged();

    }

    /**
     * //第一次请求数据
     * 如果是电视剧，会显示分集
     *
     * @param commonVideoVo
     */
    private void loadSeri(CommonVideoVo commonVideoVo) {
        DetailPlaySection section = new DetailPlaySection(groupPlay, commonVideoVo, new OnSeriClickListener() {
            @Override
            public void switchPlay(String url, int index, int groupIndex) {
//                String add = "http://194.58.119.183/apiplayer/?url=";
//                url = String.format("%s%s",url);
                Log.d(TAG, "loadSeri: " + url);
                playerPresenter.switchPlayFirst(OnlineDetailPageActivity.this, videoContainer,url,index);
                groupPlay = groupIndex;

            }

            //查看全部
            @Override
            public void showAllSeri(CommonVideoVo commonVideoVo) {
                showPlaySheetDialog(commonVideoVo.getMovPlayUrlList().get(groupPlay));

            }
        });
        items.add(section);
    }

    private void loadRecommend(CommonVideoVo commonVideoVo) {
        DetailRecommendPresenter recommendPresenter = new DetailRecommendPresenter(this);
        recommendPresenter.getRecommend(commonVideoVo.getMovTypeId());
    }

    private void loadDetail(CommonVideoVo commonVideoVo) {

        DetailDescSection detailDescSection = new DetailDescSection(commonVideoVo, new OnDetailClickListener() {
            @Override
            public void clickReport(String vodId) {
                //上报异常
                ReportActivitys.start(OnlineDetailPageActivity.this);
            }

            @Override
            public void clickShare(String vodId) {
                //分享
                final Bitmap thumbBmp = ((BitmapDrawable) getResources().getDrawable(R.drawable.share)).getBitmap();
                ShareContent mShareContent = new ShareContentPic(thumbBmp);
                new XPopup.Builder(OnlineDetailPageActivity.this).asCustom(new BottomShareView(OnlineDetailPageActivity.this, mShareContent)).show();
            }

            @Override
            public void clickDesc(CommonVideoVo videoVo) {
                //查看详情
                showDetailSheetDialog(videoVo);
            }
        });
        items.add(detailDescSection);
        detailAdapter.notifyItemChanged(0);
    }

    private void loadPlay(CommonVideoVo commonVideoVo) {


        if (commonVideoVo.getMovPlayUrlList() == null || commonVideoVo.getMovPlayUrlList().size() == 0) {
            ToastUtil.showMessage("当前影片没有可播放地址");
            return;
        }

        globalVideoVo = commonVideoVo;
        videoPlayVo = new VideoPlayVo();
        playerPresenter.initView(OnlineDetailPageActivity.this, videoContainer, fullContainer);
        videoPlayVo.setVodId(Integer.parseInt(commonVideoVo.getMovId()));
        videoPlayVo.setTitle(commonVideoVo.getMovName());
        for (int i = 0; i < commonVideoVo.getMovPlayUrlList().get(groupPlay).size(); i++) {
            urls.add(commonVideoVo.getMovPlayUrlList().get(groupPlay).get(i).getPlayUrl());
        }
        videoPlayVo.setPlayUrl(commonVideoVo.getMovPlayUrlList().get(groupPlay).get(0).getPlayUrl());
        videoPlayVo.setSeriUrls(urls);
        playerPresenter.initData(videoPlayVo);
        playerPresenter.configOrientationSensor(OnlineDetailPageActivity.this);
        playerPresenter.setPlayListener(playListener);

        if (commonVideoVo.getMovPlayUrlList().get(groupPlay).get(0).getPlayUrl().endsWith(".html")) {
            ParsePlayUtils.getInstance().toParsePlay(OnlineDetailPageActivity.this, commonVideoVo.getMovPlayUrlList().get(groupPlay).get(0).getPlayUrl(),
                    videoContainer, new ParsePlayUtils.OnPlayUrlFindListener() {
                        @Override
                        public void onFindUrl(String url) {
                            playerPresenter.switchPlay(url,0);
                        }

                        @Override
                        public void onError() {

                        }
                    }
            );

        }

        HistoryDBhelper.checkHistoryAndPlay(vodId, playSwitchListener);
    }


    HistoryDBhelper.OnPlaySwitchListener playSwitchListener = new HistoryDBhelper.OnPlaySwitchListener() {
        @Override
        public void onPlay(int i) {
            if (urls.size() > i) {
                playerPresenter.switchPlay(urls.get(i), i);
                GlobalDATA.PLAY_INDEX = i;
            }
        }
    };

    @Override
    public void loadError() {
    }

    @Override
    public void loadEmpty() {
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (playerPresenter != null) {
            playerPresenter.pause();
        }
    }

    @Override
    protected void onDestroy() {
        if (playerPresenter != null) {
            playerPresenter.destroy();
        }
        super.onDestroy();
    }

    PlayerPresenter.OnPlayListener playListener = new PlayerPresenter.OnPlayListener() {
        @Override
        public void onExit(int currentPosition, int duration, int currentIndex) {
            HistoryDBhelper.saveHistory(videoVo, currentPosition, duration, currentIndex);
        }

        @Override
        public void toVipPay() {
            if (UserUtil.isLogin()) {
                CoinShopActivity.start(OnlineDetailPageActivity.this);
            } else {
                LoginActivity.start(OnlineDetailPageActivity.this);
            }

        }
    };


  @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        playerPresenter.onConfigurationChange(newConfig);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (playerPresenter != null) {
            playerPresenter.resume();
        }
    }

    //查看全部
    private void showPlaySheetDialog(ArrayList<VideoVo> playUrlList) {
        // Set up BottomSheetDialog
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View view = LayoutInflater.from(this).inflate(R.layout.play_all_list_layout, null);
        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.getWindow().findViewById(R.id.design_bottom_sheet).setBackground(new ColorDrawable(Color.TRANSPARENT));
        PlayListAdapter adapter2 = new PlayListAdapter(playUrlList, new OnSeriClickListener() {

            @Override
            public void switchPlay(String url, int index, int groupIndex) {
                Toast.makeText(OnlineDetailPageActivity.this, "即将播放第" + (index + 1) + "集", Toast.LENGTH_SHORT).show();
                playerPresenter.switchPlayFirst(OnlineDetailPageActivity.this, frameLayout,url,index);
              //  Log.d(TAG, "switchPlay: " + url);
            }

            @Override
            public void showAllSeri(CommonVideoVo commonVideoVo) {
            }
        }, groupPlay);
        //adapter2.notifyDataSetChanged();
        RecyclerView allList = view.findViewById(R.id.all_list);
        TextView title = view.findViewById(R.id.title);
        String[] arrName = videoVo.getVodPlayFrom().split("[$][$][$]");
        title.setText("所有剧集（" + arrName[groupPlay] + ")");
        ImageView close = view.findViewById(R.id.close);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 5);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        allList.addItemDecoration(new GridItemDecoration(this, R.drawable.grid_item_decor));
        allList.setLayoutManager(gridLayoutManager);
        allList.setAdapter(adapter2);
        bottomSheetDialog.show();
        close.setOnClickListener(v -> bottomSheetDialog.dismiss());
    }

    private void startCache() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View view = LayoutInflater.from(this).inflate(R.layout.cache_all_list_layout, null);
        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.getWindow().findViewById(R.id.design_bottom_sheet).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        if (globalVideoVo == null || globalVideoVo.getMovPlayUrlList() == null || globalVideoVo.getMovPlayUrlList().size() == 0) {
            ToastUtil.showMessage("正在请求数据，请稍后");
            return;
        }

        DowningDao downingDao = M3U8dbManager.getInstance(M3U8Library.getContext()).downingDao();
        DoneDao doneDao = M3U8dbManager.getInstance(M3U8Library.getContext()).doneDao();

        ArrayList<Square> cacheItem = new ArrayList<>();

        for (VideoVo v : globalVideoVo.getMovPlayUrlList().get(groupPlay)) {

            Square square = new Square(globalVideoVo.getMovPlayUrlList().get(groupPlay).indexOf(v) + 1, new View.OnClickListener() {
                @Override
                public void onClick(View vs) {
                    String downloadTitle = "";
                    if (globalVideoVo.getMovPlayUrlList().size() > 1) {
                        downloadTitle = globalVideoVo.getMovName() + "第" + (globalVideoVo.getMovPlayUrlList().get(groupPlay).indexOf(v) + 1) + "集";
                    } else {
                        downloadTitle = globalVideoVo.getMovName();
                    }
                    Toast.makeText(OnlineDetailPageActivity.this, "开始缓存第" + (globalVideoVo.getMovPlayUrlList().get(groupPlay).indexOf(v) + 1) + "集", Toast.LENGTH_SHORT).show();

                    DownloadPresenter.addM3u8Task(OnlineDetailPageActivity.this, v.getPlayUrl(), downloadTitle, globalVideoVo.getMovPoster());
                }
            });
            square.isSelected = false;
            square.finished = false;

            List<M3u8DownloadingInfo> infos = downingDao.getById(MD5Utils.encode(v.getPlayUrl()));
            if (infos != null && infos.size() > 0) {
                //正在下载中
                square.isSelected = true;
                square.finished = false;
            }
            List<M3u8DoneInfo> doneInfos = doneDao.getById(MD5Utils.encode(v.getPlayUrl()));
            if (doneInfos != null && doneInfos.size() > 0) {
                //已下载完成
                square.isSelected = false;
                square.finished = true;
            }

            cacheItem.add(square);
        }

        TreeSet<Integer> selectedSet = new TreeSet<>();
        MultiTypeAdapter multiTypeAdapter = new MultiTypeAdapter();
        multiTypeAdapter.register(Square.class, new SquareViewBinder(selectedSet));
        ArrayList<Object> cacheItems = new ArrayList<>();
        cacheItems.addAll(cacheItem);
        multiTypeAdapter.setItems(cacheItems);


        RecyclerView allList = view.findViewById(R.id.all_list);
        TextView title = view.findViewById(R.id.title);
        TextView downCenter = view.findViewById(R.id.down_center);
        downCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AllDownLoadActivity.startTo(OnlineDetailPageActivity.this);
            }
        });
        title.setText("缓存剧集");
        ImageView close = view.findViewById(R.id.close);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 5);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        allList.addItemDecoration(new GridItemDecoration(this, R.drawable.grid_item_decor));
        allList.setLayoutManager(gridLayoutManager);
        allList.setAdapter(multiTypeAdapter);
        bottomSheetDialog.show();
        close.setOnClickListener(v -> bottomSheetDialog.dismiss());
    }

    static final String[] PERMISSIONS = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
    };

    private void showDetailSheetDialog(CommonVideoVo commonVideoVo) {
        new XPopup.Builder(this).asCustom(new DetailDialogWindow(this, commonVideoVo)).show();
    }

    @Override
    public void loadRecmmedDone(ArrayList<CommonVideoVo> videoVos) {
        items.add(items.size() - 1, new DetailRecmmendSection(videoVos));
        detailAdapter.notifyItemChanged(2);
        commentPresenter.getCommentByVodId(vodId, index, 18);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.favor:
                if (UserUtil.isLogin()) {
                    favorPresenter.toggleFavor(favor, vodId, favor.isSelected());
                } else {
                    LoginActivity.start(this);
                }
                break;
            case R.id.download:
                showDownloadWindow();
                break;
            case R.id.edit_comment:
                if (UserUtil.isLogin()) {
                    showCommentDialog(null, 0);
                } else {
                    LoginActivity.start(this);
                }
                break;
            default:
                break;
        }
    }

    private void showDownloadWindow() {
        getPermission();
    }


    IFavor iFavor = new IFavor() {
        @Override
        public void favorDone() {
            ToastUtil.showMessage("已添加收藏");
        }

        @Override
        public void loadDone(ArrayList<CommonVideoVo> videoListDto) {

        }

        @Override
        public void cancelDone() {
            ToastUtil.showMessage("已取消收藏");
        }

        @Override
        public void loadHaveDone(boolean haveFavor) {
            favor.setSelected(haveFavor);
        }

        @Override
        public void loadError() {

        }

        @Override
        public void loadEmpty() {

        }

    };

    private void showCommentDialog(CommentVo commentVo, int pid) {
        new XPopup.Builder(this).asCustom(new BottomInputSheet(this, commentVo, new BottomInputSheet.OnPublishListener() {
            @Override
            public void onSend(String conntent) {
                commentPresenter.publishComment(conntent, vodId, commentVo, pid);
            }
        })).show();
    }

    ICView icView = new ICView() {


        @Override
        public void loadAllDone(ArrayList<CommentVo> commentVos) {
            if (loadListener != null) {
                loadListener.loadMore(commentVos);
            }
        }

        @Override
        public void publishDone(CommentVo commentVo) {
            if (loadListener != null) {
                loadListener.addComment(commentVo);
            }
        }

        @Override
        public void loadMoreDone(ArrayList<CommentVo> commentVos) {
            if (loadListener != null) {
                loadListener.loadMore(commentVos);
            }
        }

        @Override
        public void loadError() {

        }

        @Override
        public void loadEmpty() {

        }


    };
    private CommentContainerSectionViewBinder.OnLoadMoreListener loadListener;
    CommentContainerSectionViewBinder.OnCommentLoadListener onloadListener = new CommentContainerSectionViewBinder.OnCommentLoadListener() {
        @Override
        public void setOnLoadMoreListener(CommentContainerSectionViewBinder.OnLoadMoreListener onLoadMoreListener) {
            loadListener = onLoadMoreListener;

        }

        @Override
        public void onReply(CommentVo commentVo) {
            showCommentDialog(commentVo, commentVo.getCommentId());

        }

        @Override
        public void onDelete(CommentVo commentVo) {
            new XPopup.Builder(OnlineDetailPageActivity.this).asConfirm("提示：", "确定删除该条评论？", new OnConfirmListener() {
                @Override
                public void onConfirm() {
                    commentPresenter.deleteComment(commentVo.getCommentId());
                    loadListener.removeItem(commentVo);
                }
            }).show();
        }

        @Override
        public void onReport(CommentVo commentVo) {

        }
    };

    public void getPermission() {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(WRITE_EXTERNAL_STORAGE)
                .subscribe(aBoolean -> {
                    if (aBoolean) {
                        startCache();
                    } else {
                        Toast.makeText(OnlineDetailPageActivity.this, "你没有授权读写文件权限，将无法下载影片", Toast.LENGTH_SHORT).show();
                    }
                });

    }


    @Override
    public void onBackPressed() {

        if (playerPresenter != null && playerPresenter.getIsLandscape()) {
            playerPresenter.onBackPress();
        } else {
            super.onBackPressed();
        }
    }
}

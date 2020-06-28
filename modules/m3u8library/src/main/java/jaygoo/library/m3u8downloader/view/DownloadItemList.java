package jaygoo.library.m3u8downloader.view;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.impl.LoadingPopupView;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import jaygoo.library.m3u8downloader.M3U8DownloaderPro;
import jaygoo.library.m3u8downloader.OnDeleteTaskListener;
import jaygoo.library.m3u8downloader.R;
import jaygoo.library.m3u8downloader.control.DownloadPresenter;
import jaygoo.library.m3u8downloader.db.M3U8dbManager;
import jaygoo.library.m3u8downloader.db.dao.DoneDao;
import jaygoo.library.m3u8downloader.db.table.M3u8DoneInfo;
import jaygoo.library.m3u8downloader.view.item.M3u8DoneItem;
import jaygoo.library.m3u8downloader.view.item.M3u8DoneItemViewBinder;
import me.drakeet.multitype.MultiTypeAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author huangyong
 * createTime 2019-09-27
 */
public class DownloadItemList extends Fragment {

    private ArrayList<Object> items;
    private RecyclerView recyclerView;
    private MultiTypeAdapter multiTypeAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_list_layout, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        //读取数据库，获取已经完成的任务

        recyclerView = view.findViewById(R.id.down_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        multiTypeAdapter = new MultiTypeAdapter();
        multiTypeAdapter.register(M3u8DoneItem.class, new M3u8DoneItemViewBinder());
        items = new ArrayList<>();
        multiTypeAdapter.setItems(items);
        recyclerView.setAdapter(multiTypeAdapter);


        initData();
    }

    private void initData() {
        DoneDao downingDao = M3U8dbManager.getInstance(getContext()).doneDao();
        List<M3u8DoneInfo> daoAll = downingDao.getAll();
        if (daoAll != null && daoAll.size() > 0) {
            for (final M3u8DoneInfo info : daoAll) {
                M3u8DoneItem doneItem = new M3u8DoneItem(info, new M3u8DoneItemViewBinder.OnItemListener() {
                    @Override
                    public void onLongClick(final M3u8DoneItem m3u8DoneItem, final int index) {
                        final LoadingPopupView loadingPopupView = new XPopup.Builder(getContext()).asLoading("正在处理...");
                        new XPopup.Builder(getActivity()).asConfirm("提示！", "确定删除？", new OnConfirmListener() {
                            @Override
                            public void onConfirm() {

                                M3U8DownloaderPro.getInstance().deleteLocal(m3u8DoneItem.getM3u8DoneInfo().getTaskData(), new OnDeleteTaskListener() {
                                    @Override
                                    public void onStart() {
                                        loadingPopupView.show();
                                    }

                                    @Override
                                    public void onSuccess() {

                                        loadingPopupView.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                DownloadPresenter.DeleteDoneTask(m3u8DoneItem.getM3u8DoneInfo().getTaskId());
                                                items.remove(m3u8DoneItem);
                                                loadingPopupView.dismiss();
                                                multiTypeAdapter.notifyItemRemoved(index);
                                            }
                                        });


                                    }

                                    @Override
                                    public void onFail() {
                                        loadingPopupView.dismiss();
                                    }

                                    @Override
                                    public void onError(Throwable errorMsg) {
                                        loadingPopupView.dismiss();
                                    }
                                });
                            }
                        }).show();
                    }
                });
                items.add(doneItem);
            }
            multiTypeAdapter.notifyDataSetChanged();
        }
    }


    public void refreshList() {
        if (multiTypeAdapter != null && items != null) {
            recyclerView.post(new Runnable() {
                @Override
                public void run() {
                    items.clear();
                    initData();
                    multiTypeAdapter.notifyDataSetChanged();
                }
            });

        }
    }
}

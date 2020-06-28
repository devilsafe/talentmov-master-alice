package jaygoo.library.m3u8downloader.view;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.impl.LoadingPopupView;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import jaygoo.library.m3u8downloader.M3U8DownloaderPro;
import jaygoo.library.m3u8downloader.OnDeleteTaskListener;
import jaygoo.library.m3u8downloader.OnTaskDownloadListener;
import jaygoo.library.m3u8downloader.R;
import jaygoo.library.m3u8downloader.control.DownloadPresenter;
import jaygoo.library.m3u8downloader.db.M3U8dbManager;
import jaygoo.library.m3u8downloader.db.dao.DowningDao;
import jaygoo.library.m3u8downloader.db.table.M3u8DownloadingInfo;
import jaygoo.library.m3u8downloader.view.item.M3u8Item;
import jaygoo.library.m3u8downloader.view.item.M3u8ItemViewBinder;
import me.drakeet.multitype.MultiTypeAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author huangyong
 * createTime 2019-09-27
 */
public class DownloadingItemList extends Fragment {

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
        //读取数据库，获取正在进行的任务

        recyclerView = view.findViewById(R.id.down_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        multiTypeAdapter = new MultiTypeAdapter();
        multiTypeAdapter.register(M3u8Item.class, new M3u8ItemViewBinder());
        items = new ArrayList<>();
        multiTypeAdapter.setItems(items);
        recyclerView.setAdapter(multiTypeAdapter);

        initData();

    }

    private void initData() {
        DowningDao downingDao = M3U8dbManager.getInstance(getContext()).downingDao();
        List<M3u8DownloadingInfo> daoAll = downingDao.getAll();
        if (daoAll != null && daoAll.size() > 0) {
            for (final M3u8DownloadingInfo info : daoAll) {
                M3u8Item m3u8Item = new M3u8Item(new M3u8ItemViewBinder.OnItemListener() {
                    @Override
                    public void onListenerInit(OnTaskDownloadListener taskDownloadListener, M3u8Item m3u8Item) {
                        String taskUrl = DownloadPresenter.getTaskById(m3u8Item.getTaskId());

                        M3U8DownloaderPro.getInstance().addTaskListener(taskUrl, taskDownloadListener);
                    }

                    @Override
                    public void onClick(String taskId, String name, String posterUrl, OnTaskDownloadListener onTaskDownloadListener) {
                        String taskUrl = DownloadPresenter.getTaskById(taskId);

                        if (!TextUtils.isEmpty(taskUrl)) {
                            M3U8DownloaderPro.getInstance().download(taskUrl, name, posterUrl, onTaskDownloadListener);
                        } else {
                            Toast.makeText(getContext(), "重启失败，稍后重试", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onLongClick(final M3u8Item m3u8Item) {
                        final LoadingPopupView loadingPopupView = new XPopup.Builder(getContext()).asLoading("正在处理...");
                        String taskUrl = DownloadPresenter.getTaskById(m3u8Item.getTaskId());
                        if (TextUtils.isEmpty(taskUrl)){
                            return;
                        }
                        new XPopup.Builder(getContext()).asConfirm("提示！", "确定删除当前下载任务？", new OnConfirmListener() {
                            @Override
                            public void onConfirm() {
                                M3U8DownloaderPro.getInstance().cancelAndDelete(DownloadPresenter.getTaskById(m3u8Item.getTaskId()), new OnDeleteTaskListener() {
                                    @Override
                                    public void onStart() {
                                        loadingPopupView.show();
                                    }

                                    @Override
                                    public void onSuccess() {
                                        items.remove(m3u8Item);
                                        DownloadPresenter.DeleteTask(m3u8Item.getTaskId());
                                        loadingPopupView.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(getContext(), "任务和本地文件已删除", Toast.LENGTH_SHORT).show();
                                                loadingPopupView.dismiss();
                                                multiTypeAdapter.notifyDataSetChanged();
                                            }
                                        });

                                    }

                                    @Override
                                    public void onFail() {
                                        loadingPopupView.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(getContext(), "删除失败，请手动删除", Toast.LENGTH_SHORT).show();
                                                loadingPopupView.dismiss();
                                            }
                                        });

                                    }

                                    @Override
                                    public void onError(Throwable errorMsg) {
                                        loadingPopupView.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(getContext(), "删除出错，请手动删除", Toast.LENGTH_SHORT).show();
                                                loadingPopupView.dismiss();
                                            }
                                        });

                                    }
                                });
                            }
                        }).show();
                    }
                }, info.getTaskId(), info.getTaskName(), info.getTaskPoster());
                items.add(m3u8Item);

            }
            multiTypeAdapter.notifyDataSetChanged();

        }
    }


    public void refreshList() {

        if (multiTypeAdapter != null && items != null) {
            items.clear();
            initData();
        }
    }
}

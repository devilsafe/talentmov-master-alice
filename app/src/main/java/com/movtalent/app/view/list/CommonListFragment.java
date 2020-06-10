package com.movtalent.app.view.list;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.movtalent.app.R;


/**
 * @author huangyong
 * createTime 2019/6/18
 */
public class CommonListFragment extends Fragment {

    @BindView(R.id.mov_rv)
    RecyclerView movRv;
    Unbinder unbinder;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.common_list_layout, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }


    private void initView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        movRv.setLayoutManager(linearLayoutManager);

    }

    public void refreshData(RecyclerView.Adapter adapter) {
        if (movRv != null) {
            movRv.setAdapter(adapter);
        }

    }

    public static CommonListFragment newInstance() {
        CommonListFragment movListFragment = new CommonListFragment();
        return movListFragment;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}

package com.movtalent.app.category;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.media.playerlib.model.DataInter;
import com.movtalent.app.R;

/**
 * @author huangyong
 * createTime 2019-10-08
 */
public class SelectTabFragment extends Fragment {

    @BindView(R.id.tab_name)
    TextView tabName;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.select_tab_layout, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        Bundle bundle = getArguments();
        if (bundle == null) {
            return;
        }
        String tabTitle = bundle.getString(DataInter.Key.TAB_NAME);
        if (TextUtils.isEmpty(tabTitle)) {
            return;
        }

        tabName.setText(tabTitle);
    }


    public static SelectTabFragment getInstance(String name) {
        SelectTabFragment tabFragment = new SelectTabFragment();
        Bundle bundle = new Bundle();
        bundle.putString(DataInter.Key.TAB_NAME, name);
        tabFragment.setArguments(bundle);
        return tabFragment;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}

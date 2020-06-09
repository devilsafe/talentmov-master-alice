package com.movtalent.app.view;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.lib.common.util.DataInter;
import com.movtalent.app.R;
import com.movtalent.app.adapter.icon.IconSection;
import com.movtalent.app.adapter.icon.IconSectionViewBinder;
import com.movtalent.app.model.dto.IconDto;
import com.movtalent.app.util.UserUtil;
import com.movtalent.app.viewmodel.IconViewModel;
import me.drakeet.multitype.MultiTypeAdapter;

import java.util.ArrayList;

public class UserProfileActivity extends AppCompatActivity {

    @BindView(R.id.backup)
    ImageView backup;
    @BindView(R.id.center_tv)
    TextView centerTv;
    @BindView(R.id.right_view)
    FrameLayout rightView;
    @BindView(R.id.toolbar_layout)
    Toolbar toolbarLayout;
    @BindView(R.id.icon_grid)
    RecyclerView iconGrid;
    private ArrayList<Object> items;
    private IconViewModel viewModel;
    private int iconIndex = -1;
    private MultiTypeAdapter multiTypeAdapter;

    public static void start(Context context) {
        Intent intent = new Intent(context, UserProfileActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        ButterKnife.bind(this);
        centerTv.setText("修改头像");


        TextView manage = new TextView(this);
        manage.setText("确定");
        manage.setTextColor(Color.WHITE);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;
        manage.setLayoutParams(params);
        rightView.addView(manage);


        multiTypeAdapter = new MultiTypeAdapter();
        multiTypeAdapter.register(IconSection.class, new IconSectionViewBinder());
        iconGrid.setLayoutManager(new GridLayoutManager(this, 4));
        items = new ArrayList<>();

        for (int i = 0; i < 8; i++) {
            items.add(new IconSection(i, clickListener));
        }
        multiTypeAdapter.setItems(items);

        iconGrid.setAdapter(multiTypeAdapter);

        viewModel = ViewModelProviders.of(this).get(IconViewModel.class);

        viewModel.getIconResult().observe(this, new Observer<IconDto>() {
            @Override
            public void onChanged(@Nullable IconDto iconDto) {
                Toast.makeText(UserProfileActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                if (iconDto!=null){
                    UserUtil.updateUserIcon(iconDto.getData().getUser_portrait_thumb());
                    sendBroadcast(new Intent(DataInter.KEY.ACTION_REFRESH_ICON));
                }
            }
        });


        manage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (iconIndex == -1) {
                    return;
                }
                viewModel.changeIcon(iconIndex);
            }
        });
        backup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    IconSectionViewBinder.OnItemClickListener clickListener = new IconSectionViewBinder.OnItemClickListener() {
        @Override
        public void onClick(int index) {
            iconIndex = index;
            for (int i = 0; i < items.size(); i++) {
                Object sect = items.get(i);
                IconSection iconSection = (IconSection) sect;
                iconSection.setChosed(false);
                if (i==index){
                    iconSection.setChosed(true);
                }
            }
            multiTypeAdapter.notifyDataSetChanged();
        }
    };
}

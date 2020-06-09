package com.movtalent.app.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.media.playerlib.model.DataInter;
import com.movtalent.app.R;
import com.movtalent.app.view.list.LevelFragment;

/**
 * @author huangyong
 * createTime 2019-09-14
 */
public class AllLevelActivity extends AppCompatActivity {


    @BindView(R.id.backup)
    ImageView backup;
    @BindView(R.id.center_tv)
    TextView centerTv;
    @BindView(R.id.right_view)
    FrameLayout rightView;
    @BindView(R.id.mov_content)
    FrameLayout movCentent;

    public static void startTo(Context context, int levelId) {
        Intent intent = new Intent(context,AllLevelActivity.class);
        intent.putExtra(DataInter.Key.LEVEL_ID,levelId);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_level_layout);
        ButterKnife.bind(this);

        int levelId = getIntent().getIntExtra(DataInter.Key.LEVEL_ID, 0);

        backup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        centerTv.setText("全部内容");

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.mov_content, LevelFragment.getInstance(levelId));
        fragmentTransaction.commitAllowingStateLoss();
    }
}

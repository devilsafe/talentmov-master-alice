package com.movtalent.app.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.just.agentweb.AgentWeb;
import com.lib.common.util.DataInter;
import com.movtalent.app.R;

/**
 * @author huangyong
 * createTime 2019-09-24
 */
public class WebDetailActivity extends AppCompatActivity {

    @BindView(R.id.backup)
    ImageView backup;
    @BindView(R.id.center_tv)
    TextView centerTv;
    @BindView(R.id.right_view)
    FrameLayout rightView;
    @BindView(R.id.toolbar_layout)
    Toolbar toolbarLayout;
    @BindView(R.id.web_root)
    FrameLayout webRoot;
    private AgentWeb agentWeb;

    public static void start(Context context, String url) {
        Intent intent = new Intent(context,WebDetailActivity.class);
        intent.putExtra(DataInter.KEY.WEB_URL,url);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_layout);
        ButterKnife.bind(this);
        String adUrl = getIntent().getStringExtra(DataInter.KEY.WEB_URL);
        centerTv.setText("详情");
        agentWeb = AgentWeb.with(this)
                .setAgentWebParent(webRoot, webRoot.getLayoutParams())
                .useDefaultIndicator()
                .setWebViewClient(new WebViewClient())
                .createAgentWeb()
                .ready()
                .go(adUrl);
        backup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (agentWeb.handleKeyEvent(keyCode, event)) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onPause() {
        agentWeb.getWebLifeCycle().onPause();
        super.onPause();

    }

    @Override
    protected void onResume() {
        agentWeb.getWebLifeCycle().onResume();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        agentWeb.getWebLifeCycle().onDestroy();
        super.onDestroy();
    }
}

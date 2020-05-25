package com.movtalent.app.view;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.*;
import butterknife.BindView;
import com.movtalent.app.R;
import com.movtalent.app.presenter.ReportPresenter;
import com.movtalent.app.presenter.iview.IReport;

/**
 * @author huangyong
 * createTime 2019-07-09
 */
public class ReportActivitys extends BaseActivity implements IReport {


    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.head_bar)
    RelativeLayout headBar;
    @BindView(R.id.content)
    EditText content;
    @BindView(R.id.submit)
    TextView submit;
    @BindView(R.id.title)
    EditText title;
    @BindView(R.id.app_bug)
    RadioButton appBug;
    @BindView(R.id.page_error)
    RadioButton pageError;
    @BindView(R.id.res_advice)
    RadioButton resAdvice;
    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;
    @BindView(R.id.contact)
    EditText contact;
    private int rpTpye=0;
    private ReportPresenter reportPresenter;
    private String repTitle;
    private String repContent;
    private String repContact;

    public static void start(Context context) {
        Intent intent = new Intent(context, ReportActivitys.class);
        context.startActivity(intent);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        back.setOnClickListener(v -> finish());
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.app_bug:
                    rpTpye = 0;
                    break;
                case R.id.page_error:
                    rpTpye = 1;
                    break;
                case R.id.res_advice:
                    rpTpye = 2;
                    break;
                default:
                    break;
            }
        });
        contact.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                repContact = s.toString();
            }
        });
        content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                repContent = s.toString();
            }
        });
        title.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                repTitle = s.toString();
            }
        });

        reportPresenter = new ReportPresenter(this, this);
        submit.setOnClickListener(v -> {
            if (checkInput()) {
                reportPresenter.reportMsg(repContent, repContact, repTitle,rpTpye);
            }
        });
    }

    private boolean checkInput() {

        if (TextUtils.isEmpty(repContact)) {
            Toast.makeText(this, "联系方式不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(repContent)) {
            Toast.makeText(this, "内容不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(repTitle)) {
            Toast.makeText(this, "标题不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.report_layout;
    }

    @Override
    public void loadDone() {
        title.postDelayed(() -> {
            finish();
            Toast.makeText(ReportActivitys.this, "提交成功", Toast.LENGTH_SHORT).show();
        },1000);
    }

    @Override
    public void loadError(String msg) {
        Toast.makeText(ReportActivitys.this, "提交失败", Toast.LENGTH_SHORT).show();
    }
}

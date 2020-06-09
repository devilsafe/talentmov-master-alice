package com.movtalent.app.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.movtalent.app.R;

/**
 * @author huangyong
 * createTime 2019-09-19
 */
public class ReQuestFilmActivity extends AppCompatActivity {


    public static void startTo(Context context) {
        Intent intent = new Intent(context, ReQuestFilmActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.request_film_layout);
    }
}

package com.movtalent.app.util;

import android.app.Activity;
import android.widget.Toast;
import com.movtalent.app.App;


public class ToastUtil {

	public static void showMessage(final String msg) {
		Toast.makeText(App.getContext(), msg, Toast.LENGTH_SHORT).show();
	}

	public static void showMessage(Activity activity, final String msg) {
		Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
	}

}

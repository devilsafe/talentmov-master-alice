package com.lib.common.util.tool;

import android.content.Context;
import android.util.Log;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by H19 on 2018/6/11 0011.
 */

public class StringUtil {
    public static String getCenterText(String text, int start, int length) {
        return text.substring(start, length);
    }

    public static String getCenterText(String text, String left, String right) {
        if (left == null || right == null || left.isEmpty() || right.isEmpty()) {
            return null;
        }
        int let = text.indexOf(left);
        if (let == -1) return null;

        let = let + left.length();
        int rit = text.indexOf(right, let);
        if (rit == -1) return null;
        Log.i("eee-left", "" + let + "|" + rit);
        return text.substring(let, rit);
    }

    public static String getCenterText(String text, String left, String right, int start) {
        String result = "";
        int zLen;
        if (left == null || left.isEmpty()) {
            zLen = 0;
        } else {
            zLen = text.indexOf(left, start);
            if (zLen > -1) {
                zLen += left.length();
            } else {
                zLen = 0;
            }
        }
        int yLen = text.indexOf(right, zLen);
        if (yLen < 0 || right == null || right.isEmpty()) {
            yLen = text.length();
        }
        result = text.substring(zLen, yLen);
        return result;
    }

    public static String getCenterText(String text, int start, String right) {
        String result = "";
        int i = text.indexOf(right, start);
        if (i > -1) {
            result = text.substring(start, i);
        } else {
            result = "";
        }

        return result;
    }

    public static String getLeftText(String text, String t1) {
        int ri;
        if (text == null) return null;
        if (t1 == null) return null;
        ri = text.indexOf(t1);
        if (ri < 1) {
            return null;
        }
        String result = text.substring(0, ri);
        return result;
    }

    public static String getLeftText(String text, String t1, String t2) {
        int ri;
        ri = text.indexOf(t1);
        String result = text.substring(0, ri) + t2;
        return result;
    }

    public static String getLeftText(String text, int length) {
        if (text.length() < length) return "";
        if (text.length() > length) {
            return text.substring(0, length);
        } else {
            return text;
        }
    }

    public static String getLeftText(String text, int length, String t2) {
        if (text.length() > length + 1) {
            return text.substring(0, length) + t2;
        } else {
            return text;
        }
    }

    public static String getTextRight(String text, int length) {
        return text.substring(text.length() - length);
    }

    public static String getTextRight(String text, String t1) {
        int ri;
        ri = text.lastIndexOf(t1);
        if (ri == -1) return null;
        String result = text.substring(ri + 1);
        return result;
    }

    public static List<String> getCenterTextList(String text, String left, String right) {
        List<String> l1 = new ArrayList<String>();
        int s = 0;
        int i1 = 0;
        int i2 = 0;
        i1 = text.indexOf(left, s);
        i2 = text.indexOf(right, i1);
        while (i2 > 0) {
            l1.add(text.substring(i1 + left.length(), i2));
            i1 = text.indexOf(left, i2 + right.length());
            if (i1 != -1) {
                i2 = text.indexOf(right, i1 + left.length());
            } else {
                i2 = -1;
            }
        }
        return l1;

    }


    public static String getMagnet(String Text) {
        Pattern p = Pattern.compile("([0-9a-zA-Z]{40})");
        Matcher m = p.matcher(Text);
        String t = "";
        if (m.find()) {
            t = "magnet:?xt=urn:btih:" + m.group(1);
        }
        return t;
    }


    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    public static String getDateStringFromTimestamp(long timestamp) {
        try {
            Calendar calendar = Calendar.getInstance();
            TimeZone tz = TimeZone.getDefault();
            calendar.setTimeInMillis(timestamp * 1000);
            calendar.add(Calendar.MILLISECOND, tz.getOffset(calendar.getTimeInMillis()));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            Date currentTimeZone = calendar.getTime();
            return sdf.format(currentTimeZone);
        } catch (Exception e) {
        }
        return "";
    }

    /**
     * 将时间戳转化为 yyyy-MM-dd HH:mm:ss
     *
     * @param timestamp 时间戳
     * @return
     */
    public static String getYmdhmsTimeString(long timestamp) {
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        return formatDate.format(new Date(timestamp));
    }

    /**
     * 将时间戳转化为 yyyy-MM-dd HH:mm:ss
     *
     * @param timestamp 时间戳
     * @return
     */
    public static String getYmdhmsTimeStringv(long timestamp) {
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        return formatDate.format(new Date(timestamp));
    }


    public static String gettimeAcurrate(String inputtimestring) {
        String result = "很久以前";
        Calendar cal = Calendar.getInstance();
        Date nowTime = cal.getTime();
        Date inputTime = gettime(inputtimestring);
        if (inputTime == null)
            return result;
        long difsec = (nowTime.getTime() - inputTime.getTime()) / (1000);
        if (difsec < 60)
            result = "刚刚";
        else if (difsec < 60 * 60) {
            String minutedif = String.valueOf(difsec / 60);
            result = minutedif + "分钟前";
        } else if (difsec < 60 * 60 * 24) {
            String hourdif = String.valueOf(difsec / (60 * 60));
            result = hourdif + "小时前";
        } else if (difsec < 60 * 60 * 24 * 30) {
            String daydif = String.valueOf(difsec / (60 * 60 * 24));
            result = daydif + "天前";
        } else if (difsec < 60 * 60 * 24 * 30 * 12) {
            String monthdif = String.valueOf(nowTime.getMonth()
                    - inputTime.getMonth()
                    + (nowTime.getYear() - inputTime.getYear()) * 12);
            result = monthdif + "个月前";
        }else {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            result = simpleDateFormat.format(inputTime);
        }
        return result;
    }

    public static Date gettime(String inputtimestring) {
        Date resultdate = null;
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            if (inputtimestring == null)
                inputtimestring = "2011-03-01 00:00:00";
            resultdate = df.parse(inputtimestring);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return resultdate;
    }

    public static String stringToMD5(String plainText) {
        byte[] secretBytes = null;
        try {
            secretBytes = MessageDigest.getInstance("md5").digest(
                    plainText.getBytes());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("没有这个md5算法！");
        }
        String md5code = new BigInteger(1, secretBytes).toString(16);
        for (int i = 0; i < 32 - md5code.length(); i++) {
            md5code = "0" + md5code;
        }
        return md5code;
    }

    /**
     * 获取时间的字符串格式，将秒单位的时间转为如 "00:00:00"这样的格式
     *
     * @param time 时间，单位 秒
     */
    public static String toTimeString(long time) {
        long seconds = time % 60;
        long minutes = (time / 60) % 60;
        long hours = time / 3600;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}


package com.media.playerlib.manager;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by H19 on 2018/6/11 0011.
 */

public class StringUtil {
    public static String getCenterText(String text, int start, int length){
        return text.substring(start,length);
    }
    public static String getCenterText(String text, String left, String right){
        if (left == null || right == null || left.isEmpty() || right.isEmpty()){
            return null;
        }
        int let = text.indexOf(left);
        if (let == -1) return null;

        let = let + left.length();
        int rit = text.indexOf(right,let);
        if (rit == -1) return null;
        Log.i("eee-left","" + let + "|" + rit);
        return text.substring(let, rit);
    }
    public static String getCenterText(String text, String left, String right, int start){
        String result = "";
        int zLen;
        if (left == null || left.isEmpty()) {
            zLen = 0;
        } else {
            zLen = text.indexOf(left,start);
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
    public static String getCenterText(String text, int start, String right){
        String result = "";
        int i = text.indexOf(right,start);
        if (i > -1){
            result = text.substring(start,i);
        }else {
            result = "";
        }

        return result;
    }

    public static String getLeftText(String text, String t1){
        int ri;
        if (text == null) return null;
        if (t1 == null) return null;
        ri = text.indexOf(t1);
        if (ri < 1){
            return null;
        }
        String result = text.substring(0, ri);;
        return result;
    }
    public static String getLeftText(String text, String t1, String t2){
        int ri;
        ri = text.indexOf(t1);
        String result = text.substring(0, ri) + t2;;
        return result;
    }
    public static String getLeftText(String text, int length){
        if (text.length() < length) return "";
        if (text.length() > length){
            return text.substring(0,length);
        }else {
            return text;
        }
    }
    public static String getLeftText(String text, int length, String t2){
        if (text.length() > length + 1){
            return text.substring(0,length) + t2;
        }else {
            return text;
        }
    }

    public static String getTextRight(String text, int length){
        return text.substring(text.length() - length,text.length());
    }
    public static String getTextRight(String text, String t1){
        int ri;
        ri = text.lastIndexOf(t1);
        if (ri == -1) return null;
        String result = text.substring(ri + 1, text.length());
        return result;
    }
    public static List<String> getCenterTextList(String text, String left, String right){
        List<String> l1 = new ArrayList<String>();
        int s = 0;
        int i1 = 0;
        int i2 = 0;
        i1 = text.indexOf(left,s);
        i2 = text.indexOf(right,i1);
        while (i2 > 0){
            l1.add(text.substring(i1 + left.length(),i2));
            i1 = text.indexOf(left,i2 + right.length());
            if (i1 != -1){
                i2 = text.indexOf(right,i1 + left.length());
            }else {
                i2 = -1;
            }
        }
        return l1;

    }
    public static List<String> get中间文本集合_正则(String text, String left, String right) {
        List<String> results = new ArrayList<String>();
        Pattern p = Pattern.compile(left + "(.*?)" + right);
        Matcher m = p.matcher(text);
        while (!m.hitEnd() && m.find()) {
            results.add(m.group(1));
        }
        return results;
    }

    public static String getMagnet(String Text){
        Pattern p = Pattern.compile("([0-9a-zA-Z]{40})");
        Matcher m = p.matcher(Text);
        String t = "";
        if (m.find()){
            t = "magnet:?xt=urn:btih:" + m.group(1);
        }
        return t;
    }

    // 删除html 数据,限制长度
    public static String deleteHtml标识符号(String input, int length){

        if (input == null || input.trim().equals("")) {
            return "";
        }
        // 去掉所有html元素,
        String str = input.replaceAll("\\&[a-zA-Z]{1,10};", "").replaceAll(
                "<[^>]*>", "");
        str = str.replaceAll("[(/>)<]", "");
        str.replace("\n","");// 不要换行
        str.replace("  "," "); // 把多空格改成一个
        int len = str.length();
        if (len <= length) {
            return str;
        } else {
            str = str.substring(0, length);
            str += "......";
        }
        return str;

    }

    public static String 替换(String t,String 需替换文本,String 替换成){
        String tt;
        try {
            tt = t.replace(需替换文本,替换成);
        }catch (Exception e){
            tt = t;
        }
        return tt;
    }
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}

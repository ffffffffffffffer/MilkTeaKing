package com.milkteaking.core.util.date;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.LinearLayout;

import com.milkteaking.core.fragments.MilkTeaFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * @author TanJJ
 * @time 2018/5/27 14:49
 * @des 日期对话框工具
 */

public class DateDialogUtil {

    private IDateListener mDateListener;

    public void setDateListener(IDateListener listener) {
        mDateListener = listener;
    }

    public void showDateDialog(MilkTeaFragment fragment) {
        // 定义日期选择器
        DatePicker datePicker = new DatePicker(fragment.getContext());
        // 设置layout的参数
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        datePicker.setLayoutParams(layoutParams);
        // 初始化日期选择器
        datePicker.init(1990, 1, 1, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // 创建日历类
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, monthOfYear, dayOfMonth);
                // 建立日期格式,使用默认的Locale区域
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日", Locale.getDefault());
                String date = simpleDateFormat.format(calendar.getTime());
                if (mDateListener != null) {
                    mDateListener.onDateChange(date);
                }
            }
        });
        // 创建一个linearLayout布局
        LinearLayout linearLayout = new LinearLayout(fragment.getContext());
        // 将datePicker加入布局中
        linearLayout.addView(datePicker);
        // 创建对话框
        new AlertDialog.Builder(fragment.getContext())
                .setTitle("选择日期")
                .setView(linearLayout)// 将LinearLayout显示在Dialog里(和自定义Dialog一样)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();
    }

    public interface IDateListener {
        void onDateChange(String date);
    }

}

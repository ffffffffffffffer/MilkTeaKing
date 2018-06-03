package com.milkteaking.core.ui.scanner;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.milkteaking.core.fragments.MilkTeaFragment;

import me.dm7.barcodescanner.zbar.Result;
import me.dm7.barcodescanner.zbar.ZBarScannerView;

/**
 * @author TanJJ
 * @time 2018/6/3 9:30
 * @des 扫描器Fragment
 */

public class ScannerFragment extends MilkTeaFragment implements ZBarScannerView.ResultHandler {

    private ScannerView mScannerView;

    @Override
    public Object getLayout() {
        return mScannerView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mScannerView == null) {
            mScannerView = new ScannerView(getContext());
        }
        // 设置自动对焦
        mScannerView.setAutoFocus(true);
    }

    @Override
    public void onBindView(Bundle savedInstanceState, View view) {

    }

    @Override
    public void onResume() {
        super.onResume();
        if (mScannerView != null) {
            // 设置数据扫描结果回调
            mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
            mScannerView.startCamera();          // Start camera on resume
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mScannerView != null) {
            mScannerView.stopCameraPreview();
            mScannerView.stopCamera();           // Stop camera on pause
        }
    }

    @Override
    public void handleResult(Result result) {
        new AlertDialog.Builder(getContext())
                .setTitle("Scan Result")
                .setMessage("Result:" + result.getContents())
                .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .show();
        // 结束当前fragment
        getSupportDelegate().pop();
    }
}

package com.milkteaking.core.ui.scanner;

import android.content.Context;
import android.util.AttributeSet;

import me.dm7.barcodescanner.core.IViewFinder;
import me.dm7.barcodescanner.zbar.ZBarScannerView;

/**
 * @author TanJJ
 * @time 2018/6/3 19:35
 * @des 根据Scanner库提供的View来实现扩展
 */

public class ScannerView extends ZBarScannerView {
    public ScannerView(Context context) {
        super(context);
    }

    public ScannerView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override
    protected IViewFinder createViewFinderView(Context context) {
        return new FindView(context);
    }
}

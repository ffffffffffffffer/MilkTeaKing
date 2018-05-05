package com.milkteaking.core.ui.loader;

/**
 * @author TanJJ
 * @time 2018/5/5 16:15
 * @ProjectName MilkTeaKing
 * @PackageName com.milkteaking.core.ui.loader
 * @des Loader的类型(通过拼接类名来反射获得对象)
 */

public enum LoaderStyle {
    BallPulseIndicator,
    BallGridPulseIndicator,
    BallClipRotateIndicator,
    BallClipRotatePulseIndicator,

    SquareSpinIndicator,
    BallClipRotateMultipleIndicator,
    BallPulseRiseIndicator,
    BallRotateIndicator,

    CubeTransitionIndicator,
    BallZigZagIndicator,
    BallZigZagDeflectIndicator,
    BallTrianglePathIndicator,

    BallScaleIndicator,
    LineScaleIndicator,
    LineScalePartyIndicator,
    BallScaleMultipleIndicator,

    BallPulseSyncIndicator,
    BallBeatIndicator,
    LineScalePulseOutIndicator,
    LineScalePulseOutRapidIndicator,

    BallScaleRippleIndicator,
    BallScaleRippleMultipleIndicator,
    BallSpinFadeLoaderIndicator,
    LineSpinFadeLoaderIndicator,

    TriangleSkewSpinIndicator,
    PacmanIndicator,
    BallGridBeatIndicator,
    SemiCircleSpinIndicator
}

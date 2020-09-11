package com.example.tbaoproductdetaildemo;

import android.content.Context;
import android.view.View;

import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.lxj.xpopup.enums.PopupAnimation;

public class PublicPopControl {
    private static PopViewCallBack mpopViewCallBack;

    /**
     * 从中间弹出pop
     * @param context
     * @param layout_id
     * @param popViewCallBack
     */
    public static void general_pop(Context context, int layout_id, PopViewCallBack popViewCallBack) {
        mpopViewCallBack = popViewCallBack;
        new XPopup.Builder(context)
                .autoOpenSoftInput(true)
                .popupAnimation(PopupAnimation.ScaleAlphaFromCenter)
                .asCustom(new CenterPopupView(context, layout_id, new ViewCallBack() {
                    @Override
                    public void onReturnView(View view, BasePopupView popup) {
                        mpopViewCallBack.return_view(view, popup);
                    }
                })).show();

    }



    /**
     * 从下方弹出的pop
     * @param context
     * @param layout_id
     * @param popViewCallBack
     */
    public static void bottom_pop(Context context, int layout_id, PopViewCallBack popViewCallBack) {
        mpopViewCallBack = popViewCallBack;
        new XPopup.Builder(context)
                .autoOpenSoftInput(false)
                .asCustom(new BottomPopView(context, layout_id, new ViewCallBack() {
                    @Override
                    public void onReturnView(View view, BasePopupView popup) {
                        mpopViewCallBack.return_view(view, popup);
                    }
                })).show();

    }



}

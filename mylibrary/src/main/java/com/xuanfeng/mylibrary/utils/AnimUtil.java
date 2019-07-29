package com.xuanfeng.mylibrary.utils;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

/**
 * Created by xuanfengwuxiang on 2018/3/13.
 */

public class AnimUtil {
    private AnimUtil() {
    }

    //LayoutAnimation，用于viewgroup的孩子做动画
    public static void runLayoutAnimation(RecyclerView recyclerView, int animResId) {
        final Context context = recyclerView.getContext();
        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(context, animResId);

        recyclerView.setLayoutAnimation(controller);
        recyclerView.getAdapter().notifyDataSetChanged();
        recyclerView.scheduleLayoutAnimation();
    }
}

package com.xuanfeng.xflibrary.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import androidx.recyclerview.widget.RecyclerView;

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

    public static void startActivityCommon(Activity activity, Class clazz) {
        Intent intent = new Intent(activity, clazz);
        activity.startActivity(intent);
//        activity.overridePendingTransition(R.anim.push_right_in, 0);

    }
}

package com.didichuxing.doraemonkit.kit.leakcanary;

import android.content.Context;
import android.content.Intent;

import com.didichuxing.doraemonkit.kit.Category;
import com.didichuxing.doraemonkit.kit.IKit;
import com.squareup.leakcanary.R;
import com.squareup.leakcanary.internal.DisplayLeakActivity;

/**
 * ================================================
 * 作    者：jint（金台）
 * 版    本：1.0
 * 创建日期：2019-09-24-17:05
 * 描    述：内存泄漏leakCanary功能入口
 * 修订历史：
 * ================================================
 */
public class LeakCanaryKit implements IKit {
    @Override
    public int getCategory() {
        return Category.PERFORMANCE;
    }

    @Override
    public int getName() {
        return R.string.dk_frameinfo_leakcanary;
    }

    @Override
    public int getIcon() {
        return R.drawable.leak_canary_icon;
    }

    @Override
    public void onClick(Context context) {
        Intent intent = new Intent(context, DisplayLeakActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onAppInit(Context context) {

    }
}

package com.didichuxing.doraemonkit.kit.largepicture;

import android.content.Context;

import com.blankj.utilcode.util.ToastUtils;
import com.didichuxing.doraemonkit.R;
import com.didichuxing.doraemonkit.constant.DokitConstant;
import com.didichuxing.doraemonkit.constant.FragmentIndex;
import com.didichuxing.doraemonkit.kit.AbstractKit;
import com.didichuxing.doraemonkit.kit.Category;

/**
 * ================================================
 * 作    者：jint（金台）
 * 版    本：1.0
 * 创建日期：2019-09-24-17:05
 * 描    述：网络大图检测功能入口
 * 修订历史：
 * ================================================
 */
public class LargePictureKit extends AbstractKit {
    @Override
    public int getCategory() {
        return Category.PERFORMANCE;
    }

    @Override
    public int getName() {
        return R.string.dk_frameinfo_big_img;
    }

    @Override
    public int getIcon() {
        return R.drawable.dk_performance_large_picture;
    }

    @Override
    public void onClick(Context context) {
        if (!DokitConstant.IS_HOOK) {
            ToastUtils.showShort("需要引入doraemonkit-plugin插件以后才能使用该功能");
            return;
        }
        startUniversalActivity(context,FragmentIndex.FRAGMENT_LARGE_PICTURE);
    }

    @Override
    public void onAppInit(Context context) {

    }

    @Override
    public boolean isInnerKit() {
        return true;
    }

    @Override
    public String innerKitId() {
        return "dokit_sdk_performance_ck_img";
    }
}

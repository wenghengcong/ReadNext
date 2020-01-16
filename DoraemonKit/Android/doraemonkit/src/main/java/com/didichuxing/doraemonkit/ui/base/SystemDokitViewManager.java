package com.didichuxing.doraemonkit.ui.base;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.WindowManager;

import com.didichuxing.doraemonkit.constant.DokitConstant;
import com.didichuxing.doraemonkit.util.LogHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by wanglikun on 2018/10/23.
 * 系统悬浮窗管理类
 */

class SystemDokitViewManager implements DokitViewManagerInterface {
    private static final String TAG = "FloatPageManager";
    /**
     * 参考:
     * https://blog.csdn.net/awenyini/article/details/78265284
     * https://yuqirong.me/2017/09/28/Window%E6%BA%90%E7%A0%81%E8%A7%A3%E6%9E%90(%E4%B8%80)%EF%BC%9A%E4%B8%8EDecorView%E7%9A%84%E9%82%A3%E4%BA%9B%E4%BA%8B/
     */
    private WindowManager mWindowManager = DokitViewManager.getInstance().getWindowManager();
    private Context mContext;
    private List<AbsDokitView> mDokitViews = new ArrayList<>();

    private List<DokitViewManager.DokitViewAttachedListener> mListeners = new ArrayList<>();

    /**
     * 获取页面上所有的dokitViews
     *
     * @return
     */
    @Override
    public Map<String, AbsDokitView> getDokitViews(Activity activity) {
        Map<String, AbsDokitView> dokitViewMaps = new HashMap<>();
        for (AbsDokitView dokitView : mDokitViews) {
            dokitViewMaps.put(dokitView.getTag(), dokitView);
        }
        return dokitViewMaps;
    }


    /**
     * 当app进入后台时调用
     */
    @Override
    public void notifyBackground() {
        for (AbsDokitView dokitView : mDokitViews) {
            dokitView.onEnterBackground();
        }
    }

    /**
     * 当app进入前台时调用
     */
    @Override
    public void notifyForeground() {
        for (AbsDokitView page : mDokitViews) {
            page.onEnterForeground();
        }
    }

    public SystemDokitViewManager(Context context) {
        this.mContext = context.getApplicationContext();
        //获取WindowService
    }

    /**
     * 添加悬浮窗
     *
     * @param pageIntent
     */
    @Override
    public void attach(DokitIntent pageIntent) {
        try {
            if (pageIntent.targetClass == null) {
                return;
            }
            if (pageIntent.mode == DokitIntent.MODE_SINGLE_INSTANCE) {
                for (AbsDokitView dokitView : mDokitViews) {
                    //如果当前page对象已经存在 则直接返回
                    if (pageIntent.targetClass.isInstance(dokitView)) {
                        return;
                    }
                }
            }
            //通过newInstance方式创建floatPage
            AbsDokitView dokitView = pageIntent.targetClass.newInstance();
            dokitView.setBundle(pageIntent.bundle);
            //page.setTag(pageIntent.tag);
            //添加进page列表
            mDokitViews.add(dokitView);

            dokitView.performCreate(mContext);
            //在window上显示floatIcon
            //WindowManagerImpl具体实现
            mWindowManager.addView(dokitView.getRootView(),
                    dokitView.getSystemLayoutParams());
            dokitView.onResume();

            if (!DokitConstant.IS_NORMAL_FLOAT_MODE) {
                for (DokitViewManager.DokitViewAttachedListener listener : mListeners) {
                    listener.onDokitViewAdd(dokitView);
                }
            }
        } catch (Exception e) {
            LogHelper.e(TAG, e.toString());
        }
    }

    @Override
    public void detach(String tag) {
        if (TextUtils.isEmpty(tag) || mWindowManager == null) {
            return;
        }

        for (Iterator<AbsDokitView> it = mDokitViews.iterator(); it.hasNext(); ) {
            AbsDokitView dokitView = it.next();
            if (tag.equals(dokitView.getTag())) {
                mWindowManager.removeView(dokitView.getRootView());
                dokitView.performDestroy();
                it.remove();
                return;
            }
        }


    }

    @Override
    public void detach(AbsDokitView dokitView) {
        detach(dokitView.getClass().getSimpleName());
    }

    @Override
    public void detach(Class<? extends AbsDokitView> dokitViewClass) {
        detach(dokitViewClass.getSimpleName());
    }


    @Override
    public void detachAll() {
        for (Iterator<AbsDokitView> it = mDokitViews.iterator(); it.hasNext(); ) {
            AbsDokitView dokitView = it.next();
            mWindowManager.removeView(dokitView.getRootView());
            dokitView.performDestroy();
            it.remove();
        }
    }

    @Override
    public AbsDokitView getDokitView(Activity activity, String tag) {
        if (TextUtils.isEmpty(tag)) {
            return null;
        }
        for (AbsDokitView dokitView : mDokitViews) {
            if (tag.equals(dokitView.getTag())) {
                return dokitView;
            }
        }
        return null;
    }

    /**
     * Activity销毁时调用 不需要实现 为了统一api
     */
    @Override
    public void onActivityDestroy(Activity activity) {

    }

    /**
     * 不需要实现
     *
     * @param activity
     */
    @Override
    public void resumeAndAttachDokitViews(Activity activity) {

    }



    /**
     * 在每一个float page创建时 添加监听器
     *
     * @param listener
     */
    void addListener(DokitViewManager.DokitViewAttachedListener listener) {
        mListeners.add(listener);
    }

    void removeListener(DokitViewManager.DokitViewAttachedListener listener) {
        mListeners.remove(listener);
    }


}

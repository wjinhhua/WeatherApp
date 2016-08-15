package com.myapp.weatherapp.view;

import android.content.Context;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.myapp.weatherapp.R;
import com.myapp.weatherapp.util.ConstantsUtil;
import com.myapp.weatherapp.util.ThreadUtil;
import com.myapp.weatherapp.util.UiUtils;

/**
 * 版权: ft626 版权所有(c) 2016
 * 作者: wjh
 * 版本: 1.0
 * 创建日期: 2016/6/20.20:35
 * 描述:View加载控制器
 **/
public abstract class LoadingView extends FrameLayout {

    public int state = ConstantsUtil.LOADINGVIEW_STATE_UNKOWN;

    private View view_loading;
    private View view_error;
    private View view_empty;
    private View view_success;

    public LoadingView(Context context) {
        super(context);
        init();
    }

    public LoadingView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public LoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        view_loading = createLoadingView();
        if (view_loading != null) {
            this.addView(view_loading, new FrameLayout.LayoutParams(
                    LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        }
        view_error = createErrorView();
        if (view_error != null) {
            this.addView(view_error, new FrameLayout.LayoutParams(
                    LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        }
        view_empty = createEmptyView();
        if (view_empty != null) {
            this.addView(view_empty, new FrameLayout.LayoutParams(
                    LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        }
        showPage();
    }

    // 根据不同的状态显示不同的界面
    private void showPage() {
        if (view_loading != null) {
            view_loading.setVisibility(state == ConstantsUtil.LOADINGVIEW_STATE_UNKOWN
                    || state == ConstantsUtil.LOADINGVIEW_STATE_LOADING ? View.VISIBLE : View.INVISIBLE);
        }
        if (view_error != null) {
            view_error.setVisibility(state == ConstantsUtil.LOADINGVIEW_STATE_ERROR ? View.VISIBLE
                    : View.INVISIBLE);
        }
        if (view_empty != null) {
            view_empty.setVisibility(state == ConstantsUtil.LOADINGVIEW_STATE_EMPTY ? View.VISIBLE
                    : View.INVISIBLE);
        }
        if (state == ConstantsUtil.LOADINGVIEW_STATE_SUCCESS) {
            if (view_success == null) {
                view_success = createSuccessView();
                this.addView(view_success, new FrameLayout.LayoutParams(
                        LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            }
            view_success.setVisibility(View.VISIBLE);
        } else {
            if (view_success != null) {
                view_success.setVisibility(View.INVISIBLE);
            }
        }
    }

    /* 创建了空的界面 */
    private View createEmptyView() {
        View view = View.inflate(UiUtils.getContext(), R.layout.loadingview_empty,
                null);
        return view;
    }

    /* 创建了错误界面 */
    private View createErrorView() {
        View view = View.inflate(UiUtils.getContext(), R.layout.loadingview_error,
                null);
        Button bt_loadingerror = (Button) view.findViewById(R.id.bt_loadingerror);
        bt_loadingerror.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                show();
            }
        });
        return view;
    }

    /* 创建加载中的界面 */
    private View createLoadingView() {
        View view = View.inflate(UiUtils.getContext(),
                R.layout.loadingview_loading, null);
        return view;
    }

    public enum LoadResult {
        error(2), empty(3), success(4);

        int value;

        LoadResult(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

    }

    // 根据服务器的数据 切换状态
    public void show() {
        if (state == ConstantsUtil.LOADINGVIEW_STATE_ERROR || state == ConstantsUtil.LOADINGVIEW_STATE_EMPTY) {
            state = ConstantsUtil.LOADINGVIEW_STATE_LOADING;
        }
        // 请求服务器 获取服务器上数据 进行判断
        // 请求服务器 返回一个结果
        final LoadResult result = load();

        ThreadUtil.getInstance().createLongPool().execute(new Runnable() {

            @Override
            public void run() {
                SystemClock.sleep(500);
                final LoadResult result = load();
                UiUtils.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        if (result != null) {
                            state = result.getValue();
                            showPage(); // 状态改变了,重新判断当前应该显示哪个界面
                        }
                    }
                });
            }
        });

    }

    /***
     * 创建成功的界面
     *
     * @return
     */
    public abstract View createSuccessView();

    /**
     * 请求服务器
     *
     * @return
     */
    protected abstract LoadResult load();
}

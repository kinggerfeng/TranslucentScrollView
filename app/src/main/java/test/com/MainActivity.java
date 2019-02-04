package test.com;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import test.com.base.BaseActivity;
import test.com.impl.ActionBarClickListener;
import test.com.utils.SizeUtils;
import test.com.widget.TranslucentActionBar;
import test.com.widget.TranslucentScrollView;

/**
 * Created by Administrator on 2016/12/16.
 * email:303767416@qq.com
 */

public class MainActivity extends BaseActivity implements ActionBarClickListener, TranslucentScrollView.TranslucentChangedListener {

    private TranslucentScrollView translucentScrollView;
    private TranslucentActionBar actionBar;
    private View zoomView;
    private RelativeLayout rl;
    //    private float[] scrollTopOrBottom = {0f, 0f};
    private boolean isDoing = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // Android 5.0 以上 全透明
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            // 状态栏（以上几行代码必须，参考setStatusBarColor|setNavigationBarColor方法源码）
            window.setStatusBarColor(Color.TRANSPARENT);
            // 虚拟导航键
//            window.setNavigationBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // Android 4.4 以上 半透明
            Window window = getWindow();
            // 状态栏
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 虚拟导航键
//            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }

        init();
    }

    private void init() {
        actionBar = (TranslucentActionBar) findViewById(R.id.actionbar);
        //初始actionBar
        actionBar.setData("测试", R.mipmap.ic_left_light, "返回", R.mipmap.ic_right_gray, "惨淡", this);
        //开启渐变
        actionBar.setNeedTranslucent();
        //设置状态栏高度
        actionBar.setStatusBarHeight(getStatusBarHeight());

        translucentScrollView = (TranslucentScrollView) findViewById(R.id.pullzoom_scrollview);
        //设置透明度变化监听
        translucentScrollView.setTranslucentChangedListener(this);
        //关联需要渐变的视图
        translucentScrollView.setTransView(actionBar);
        //设置ActionBar键渐变颜色
        translucentScrollView.setTransColor(getResources().getColor(R.color.orange_dft));

        zoomView = findViewById(R.id.lay_header);
        //关联伸缩的视图
        translucentScrollView.setPullZoomView(zoomView);
    }

    @Override
    public void onLeftClick() {
        Toast.makeText(MainActivity.this, "onLeftClick", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRightClick() {
        Toast.makeText(MainActivity.this, "onRightClick", Toast.LENGTH_SHORT).show();

    }

    public void refresh() {
        isDoing = true;
        Log.i("eee", "refresh");

    }

    @Override
    public void onTranslucentPullChanged(int CurrentHeight, int defaultHeight, int pullDistance) {


        if (pullDistance > 80 && !isDoing) {
            refresh();
        }

    }

    @Override
    public void onTranslucentChanged(int transAlpha, float scrollY) {
        //todo: 渐变显示
        rl = findViewById(R.id.bar);
//        actionBar.tvTitle.setVisibility(transAlpha > 48 ? View.VISIBLE : View.GONE);
//        actionBar.tvTitle.setVisibility(View.VISIBLE);
//        Log.i("scrollY", "1234");
        TextView tv = findViewById(R.id.tv_name);
        tv.setText("scrollY: " + scrollY);
//不设置出现效果2：字体一直保持不变
        //效果1：（字体透明切换效果不好）
//        if (transAlpha > 0) {
//            rl.setAlpha(transAlpha / 255f);
//        } else {
//            rl.setAlpha(1); //todo： 实现渐变
//
//
//        }

        //            rl.animate().alpha(0f).setDuration(1000)
//                    .setListener(new AnimatorListenerAdapter() {
//                        @Override
//                        public void onAnimationEnd(Animator animation) {
//                            rl.setAlpha(1);
//                        }
//
//                    });

    }

}

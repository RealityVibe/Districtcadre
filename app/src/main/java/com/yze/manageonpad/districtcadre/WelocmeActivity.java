package com.yze.manageonpad.districtcadre;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yze.manageonpad.districtcadre.model.ActivityManager;
import com.yze.manageonpad.districtcadre.model.Apartment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.yze.manageonpad.districtcadre.MainActivity.NavigationBarStatusBar;

public class WelocmeActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.welcome_county) private Button countyBtn;
    @BindView(R.id.welcome_direct) private Button directBtn;
    @BindView(R.id.welcome_backup) private Button backupBtn;
    @BindView(R.id.welcome_researcher) private Button investBtn;
    @BindView(R.id.welcome_bdg) private ImageView imgView;
    @BindView(R.id.welcome_ll) private LinearLayout welcome_ll;
    @BindView(R.id.gesture_ll) private LinearLayout gesture_ll;
    @BindView(R.id.tv_state) private TextView tv_state;
    @BindView(R.id.reset_rb) private CheckBox reset_rb;
    private long exitTime = 0;
    private static Dialog dialog;
    //    private GestureLockViewGroup mGestureLockViewGroup;
    private boolean resetPswd = false;//是否修改密码
    private final static int FAULT_PASSWORD_PAUSE = 1;
    public static ActivityManager activityManager = new ActivityManager();
/*    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case FAULT_PASSWORD_PAUSE:
                    tv_state.setTextColor(Color.WHITE);
                    tv_state.setText("请输入手势密码");
                    //设置密码错误后的时间
                    mGestureLockViewGroup.setRetryTimes(5);
                    break;

            }
        }
    };*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        activityManager.addActivity(this);
        setContentView(R.layout.activity_welocme);
        NavigationBarStatusBar(this, true);

        //添加点击事件
        countyBtn.setOnClickListener(this);
        directBtn.setOnClickListener(this);
        backupBtn.setOnClickListener(this);
        investBtn.setOnClickListener(this);
//        initGesture();
//        dialog = createLoadDialog(WelocmeActivity.this);
    }

    //横屏锁定
    @Override
    protected void onResume() {
        /**
         * 设置为横屏
         */
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        super.onResume();
    }

    @Override
    public void onClick(View view) {
        final Intent intent = new Intent(this, MainActivity.class);
        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                startActivity(intent);
            }
        };

        switch (view.getId()) {
            case R.id.welcome_county:
                intent.putExtra("viewtype", "镇街领导");
                handler.post(runnable);
                break;
            case R.id.welcome_direct:
                intent.putExtra("viewtype", "区级机关");
                handler.post(runnable);
                break;
            case R.id.welcome_backup:
                intent.putExtra("viewtype", "后备干部");
                handler.post(runnable);
                break;
            case R.id.welcome_researcher:
                intent.putExtra("viewtype", "调研员");
                handler.post(runnable);
                break;
            default:
                break;

        }

    }

    // 重写Home键返回事件
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            // 判断间隔时间 小于2秒就退出应用
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                // 应用名
                String applicationName = getResources().getString(
                        R.string.app_name);
                String msg = "再按一次返回键退出" + applicationName;
                Toast.makeText(WelocmeActivity.this, msg, Toast.LENGTH_SHORT).show();
                // 计算两次返回键按下的时间差
                exitTime = System.currentTimeMillis();
            } else {
                // 关闭应用程序
                activityManager.finishAll();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
/*

    private void initGesture() {
        mGestureLockViewGroup = (GestureLockViewGroup) findViewById(R.id.gesturelock);
        gestureEventListener();
        gesturePasswordSettingListener();
        gestureRetryLimitListener();
    }

    //手势密码设置
    private void gesturePasswordSettingListener() {
        mGestureLockViewGroup.setGesturePasswordSettingListener(new GesturePasswordSettingListener() {
            @Override
            public boolean onFirstInputComplete(int len) {
                if (len > 3) {
                    tv_state.setTextColor(Color.WHITE);
                    tv_state.setText("再次绘制手势密码");
                    return true;
                } else {
                    tv_state.setTextColor(Color.RED);
                    tv_state.setText("最少连接4个点，请重新输入!");
                    return false;
                }
            }

            @Override
            public void onSuccess() {
                tv_state.setTextColor(Color.WHITE);
                Toast.makeText(WelocmeActivity.this, "密码设置成功!", Toast.LENGTH_SHORT).show();

                tv_state.setText("请输入手势密码解锁!");
            }

            @Override
            public void onFail() {
                tv_state.setTextColor(Color.RED);
                tv_state.setText("与上一次绘制不一致，请重新绘制");
            }
        });
    }

    //设置手势密码监听事件
    private void gestureEventListener() {
        mGestureLockViewGroup.setGestureEventListener(new GestureEventListener() {
            @Override
            public void onGestureEvent(boolean matched) {
                if (reset_rb.isChecked())
                    resetPswd = true;
//                mylog.d("onGestureEvent matched: " + matched);
                if (!matched) {
                    tv_state.setTextColor(Color.parseColor("#ff0000"));
                    tv_state.setText("手势密码错误");
                    mGestureLockViewGroup.resetView();

                } else {
                    tv_state.setTextColor(Color.WHITE);
                    tv_state.setText("手势密码正确");
                    mGestureLockViewGroup.resetView();
                }
                if (resetPswd) {
                    mGestureLockViewGroup.removePassword();
                    resetPswd = false;
                    reset_rb.setChecked(false);
                    initGesture();
                } else {
                    gesture_ll.setVisibility(View.GONE);
                    welcome_ll.setVisibility(View.VISIBLE);
                }

            }
    });
}

    //重试次数超过限制
    private void gestureRetryLimitListener() {
        mGestureLockViewGroup.setGestureUnmatchedExceedListener(5, new GestureUnmatchedExceedListener() {
            @Override
            public void onUnmatchedExceedBoundary() {
                tv_state.setTextColor(Color.parseColor("#ff0000"));
                tv_state.setText("错误次数过多，请稍后再试!");

//                mGestureLockViewGroup.setRetryTimes(5);
                new resetTvThread().start();
            }
        });
    }

class resetTvThread extends Thread {
    @Override
    public void run() {
        try {
            sleep(5000);
            Message message = handler.obtainMessage(FAULT_PASSWORD_PAUSE);
            handler.sendMessage(message);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
*/

    //在onCreate方法外面定义静态方法
    public static void closeProgressDialog() {
//        dialog.dismiss();
    }
}

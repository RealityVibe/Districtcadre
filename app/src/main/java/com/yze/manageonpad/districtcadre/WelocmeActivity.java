package com.yze.manageonpad.districtcadre;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;
import com.andrognito.patternlockview.utils.ResourceUtils;
import com.yze.manageonpad.districtcadre.model.ActivityManager;

import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.yze.manageonpad.districtcadre.MainActivity.NavigationBarStatusBar;

public class WelocmeActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.welcome_county)
    Button countyBtn;
    @BindView(R.id.welcome_direct)
    Button directBtn;
    @BindView(R.id.welcome_backup)
    Button backupBtn;
    @BindView(R.id.welcome_researcher)
    Button investBtn;
    @BindView(R.id.welcome_ll)
    LinearLayout welcome_ll;
    @BindView(R.id.gesture_ll)
    LinearLayout gesture_ll;
    @BindView(R.id.reset_rb)
    CheckBox reset_rb;
    private long exitTime = 0;
    private static Dialog dialog;
    //    private GestureLockViewGroup mGestureLockViewGroup;
    String mLocalKey;

    @BindView(R.id.patter_lock_view)
    PatternLockView mPatternLockView;

    @BindView(R.id.tv_state)
    TextView mTVTips;

    private String mCreatePattern;

    private int mCheckTimes = 3;
    private boolean resetPswd = false;//是否修改密码
    private boolean correctPswd = false;//是否成功输入密码
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
        setContentView(R.layout.activity_welocme);
        ButterKnife.bind(this);

        activityManager.addActivity(this);
        NavigationBarStatusBar(this, true);

        //添加点击事件
        countyBtn.setOnClickListener(this);
        directBtn.setOnClickListener(this);
        backupBtn.setOnClickListener(this);
        investBtn.setOnClickListener(this);
        welcome_ll.setVisibility(View.GONE);
        initGesture();
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


    private void initGesture() {
        SharedPreferences read = getSharedPreferences("lock", MODE_PRIVATE);
        //步骤2：获取文件中的值
        mLocalKey = read.getString("gesture_password", "");
        mPatternLockView = (PatternLockView) findViewById(R.id.patter_lock_view);
        mPatternLockView.setDotCount(3);
        mPatternLockView.setDotNormalSize((int) ResourceUtils.getDimensionInPx(this, R.dimen.pattern_lock_dot_size));
        mPatternLockView.setDotSelectedSize((int) ResourceUtils.getDimensionInPx(this, R.dimen.pattern_lock_dot_selected_size));
        mPatternLockView.setPathWidth((int) ResourceUtils.getDimensionInPx(this, R.dimen.pattern_lock_path_width));
        mPatternLockView.setAspectRatioEnabled(true);
        mPatternLockView.setAspectRatio(PatternLockView.AspectRatio.ASPECT_RATIO_HEIGHT_BIAS);
        mPatternLockView.setViewMode(PatternLockView.PatternViewMode.CORRECT);
        mPatternLockView.setDotAnimationDuration(150);
        mPatternLockView.setPathEndAnimationDuration(100);
        mPatternLockView.setCorrectStateColor(ResourceUtils.getColor(this, R.color.white));
        mPatternLockView.setInStealthMode(false);
        mPatternLockView.setTactileFeedbackEnabled(true);
        mPatternLockView.setInputEnabled(true);
        mPatternLockView.addPatternLockListener(mPatternLockViewListener);
        mPatternLockView.setEnableHapticFeedback(false);
    }


    private PatternLockViewListener mPatternLockViewListener = new PatternLockViewListener() {
        @Override
        public void onStarted() {
            Log.d(getClass().getName(), "Pattern drawing started");
            mTVTips.setText("完成后请抬起手指");
        }

        @Override
        public void onProgress(List<PatternLockView.Dot> progressPattern) {
            Log.d(getClass().getName(), "Pattern progress: " +
                    PatternLockUtils.patternToString(mPatternLockView, progressPattern));
        }

        @Override
        public void onComplete(List<PatternLockView.Dot> pattern) {
            Log.d(getClass().getName(), "Pattern complete: " +
                    PatternLockUtils.patternToString(mPatternLockView, pattern));
            if (reset_rb.isChecked()) {
                resetPswd = true;
            }
            if (correctPswd) {
                createPattern(pattern);
            } else if (checkPattern(pattern)) {
                if (!resetPswd) {
                    gesture_ll.setVisibility(View.GONE);
                    welcome_ll.setVisibility(View.VISIBLE);
                } else {
                    correctPswd = true;
                    mTVTips.setText("请绘制新的解锁手势");
                }
            }

        }

        @Override
        public void onCleared() {
            Log.d(getClass().getName(), "Pattern has been cleared");
        }
    };

    private boolean checkPattern(List<PatternLockView.Dot> pattern) {
        String patternStr = PatternLockUtils.patternToString(mPatternLockView, pattern);

        mCheckTimes--;

        if (mCheckTimes == 0) {
            Toast.makeText(this, "请重新登录", Toast.LENGTH_SHORT).show();
            finish();
            return false;
        }

        if (patternStr.equals(mLocalKey)) {
            mTVTips.setText("输入正确");
            return true;
        } else {
            mTVTips.setText("你还有" + mCheckTimes + "次机会");
            mPatternLockView.setViewMode(PatternLockView.PatternViewMode.WRONG);
            mPatternLockView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mPatternLockView.clearPattern();
                }
            }, 1500);
        }
        return false;
    }

    private void createPattern(List<PatternLockView.Dot> pattern) {
        if (pattern.size() < 4) {
            mTVTips.setText("至少需要4个点，请重试");
            mPatternLockView.setViewMode(PatternLockView.PatternViewMode.WRONG);
            mPatternLockView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mPatternLockView.clearPattern();
                    mTVTips.setText("请绘制新的解锁手势");
                }
            }, 1500);
            return;
        }

        String patternStr = PatternLockUtils.patternToString(mPatternLockView, pattern);

        if (TextUtils.isEmpty(mCreatePattern)) {
            mCreatePattern = patternStr;
            boolean[][] selected = new boolean[3][3];
            for (PatternLockView.Dot dot : pattern) {
                selected[dot.getRow()][dot.getColumn()] = true;
            }
//            mPatternPreview.setSelectedDots(selected);
            mPatternLockView.clearPattern();
            mTVTips.setText("重绘手势以确认");
        } else {
            if (patternStr.equals(mCreatePattern)) {
//                SPUtils.savePattern(getApplicationContext(), patternStr);
                mTVTips.setText("设置手势成功");
                SharedPreferences.Editor editor = getSharedPreferences("lock", MODE_PRIVATE).edit();
                //步骤2-2：将获取过来的值放入文件
                editor.putString("gesture_password", mCreatePattern);
                //步骤3：提交
                editor.commit();
                mPatternLockView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                }, 800);
            } else {
                mCreatePattern = "";
                mTVTips.setText("两次图案不一致，请重新绘制");
                mPatternLockView.setViewMode(PatternLockView.PatternViewMode.WRONG);

                mPatternLockView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPatternLockView.clearPattern();
//                        mPatternPreview.clearPattern();
                        mTVTips.setText("请绘制新的解锁手势");
                    }
                }, 1500);


            }
        }
    }


    //在onCreate方法外面定义静态方法
    public static void closeProgressDialog() {
//        dialog.dismiss();
    }
}

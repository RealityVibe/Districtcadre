package com.yze.manageonpad.districtcadre;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import com.yze.manageonpad.districtcadre.core.enums.CadreType;
import com.yze.manageonpad.districtcadre.core.fragments.BackFragment;
import com.yze.manageonpad.districtcadre.core.fragments.CountyFragment;
import com.yze.manageonpad.districtcadre.core.fragments.DirectFragment;
import com.yze.manageonpad.districtcadre.core.fragments.ResearcherFragment;
import com.yze.manageonpad.districtcadre.core.subview.DetailView;
import com.yze.manageonpad.districtcadre.model.ActivityManager;
import com.yze.manageonpad.districtcadre.model.Apartment;
import com.yze.manageonpad.districtcadre.model.Cadre;
import com.yze.manageonpad.districtcadre.model.CadresParams;
import com.yze.manageonpad.districtcadre.utils.JSONUtils;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindArray;
import butterknife.BindView;
import butterknife.ButterKnife;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yze
 * <p>
 * 2019/3/1.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int EVENT_TIME_TO_CHANGE_IMAGE = 100;
    private static final int EVENT_TIME_TO_INIT = 1;
    private static final int COUNTY = 1, DIRECT = 2, BACKUP = 3, RESEARCHER = 4;
    /*
     * 文件未找到
     * */
    private static final int FILE_NOT_FOUND = 2;
    private static final int CEHCK_COUNTY = 4;
    /*
     * 开启进度条
     * */
    private static final int PROGRESS_START = 5;
    /*
     * 关闭进度条
     * */
    private static final int PROGRESS_END = 6;
    public List<Apartment> apartmentsList = new ArrayList<Apartment>();
    public int loadApartNum = 0;
    public Map<String, Cadre> cadresMap = new HashMap<String, Cadre>();
    /**
     * index：部门编号
     * value：部门人数
     */
    public int presentNum[];
    public int apartmentsNum = 0;
    public String cadresMatrix[][];
    /**
     * 镇街干部清单
     */
    public static List<Apartment> countyApartmentsList = new ArrayList<Apartment>();
    /*
     * 区级干部
     */
    public static List<Apartment> directApartmentsList = new ArrayList<Apartment>();
    /*
     * 后备干部
     */
    public static List<Cadre> backCadresList = new ArrayList<Cadre>();
    private static ProgressDialog progressDialog;
    private Bundle bundle = new Bundle();
    private Fragment mContent;//当前的fragment
    //    private String[] textStrings = new String[]{"CountyFragment", "DirectFragment", "BackupFragment", "ResearcherFragment"};

    @BindArray(R.array.fragmentList)
    private String[] fragmentNames;
    @BindView(R.id.type_group)
    private RadioGroup typeGroup;
    @BindView(R.id.search_text)
    private EditText editText;
    @BindView(R.id.search_button)
    private ImageView searchImage;

    /**
     * activity管理器
     */
    public final static ActivityManager activityManager = new ActivityManager();

    private int cadreType = 1;

    public static final int MAX_CADRE_NUM = 102;
    /*
     * 镇街干部视图
     * */
    private Fragment countyFragment = new CountyFragment();

    /*
     * 区级干部视图
     * */
    private Fragment directFragment = new DirectFragment();

    /*
     * 后备干部视图
     * */
    private Fragment backupFragment = new BackFragment();

    /*
     * 调研员视图
     * */
    private Fragment researcherFragment = new ResearcherFragment();

    private CadresParams param;
    /*
     * 現在的碎片
     * */
    private Fragment curFragment = directFragment;
    private List<Cadre> cadresList = new ArrayList<Cadre>();
    @BindView(R.id.main_content)
    private LinearLayout mainContent;
    private long exitTime = 0;
    private Bundle mSavedInstanceState;
    // handler 事件管理
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case EVENT_TIME_TO_CHANGE_IMAGE:
                    WelocmeActivity.closeProgressDialog();
                    break;
                /* 大表加载完成 */
                case EVENT_TIME_TO_INIT:
                    progressDialog.dismiss();
                    break;
                /* 源文件异常 */
                case FILE_NOT_FOUND: // 数据文件异常
                    Toast.makeText(MainActivity.this, "未发现源数据文件或数据异常", Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                    break;
                case CEHCK_COUNTY:
                  /*  LinearLayout loadLayout = (LinearLayout)findViewById(R.id.layout_load);
                    loadLayout.setVisibility(View.GONE);*/
                    typeGroup.check(R.id.area_button);
                    break;
                case PROGRESS_START:
                    progressDialogTip();
                    break;
                case PROGRESS_END:
                    progressDialog.dismiss();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        activityManager.addActivity(this);
        mSavedInstanceState = savedInstanceState;

        NavigationBarStatusBar(this, true);

        // 欢迎界面
//        mainContent.setOnClickListener((View.OnClickListener) this);

        // 开始装载数据
        new Thread(new InitThread()).start();

        editText.setCursorVisible(false);
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                boolean hasFocus = true;
                if (hasFocus) {
                    editText.requestFocus(); //获取焦点,光标出现
                    editText.setFocusableInTouchMode(true);
                    editText.setFocusable(true);
                } else {
                    editText.clearFocus();
                }
            }
        });

        //  搜索按钮
        initButton();
        progressDialogTip();
    }

    //监听输入法事件
    private View.OnKeyListener onKeyListener = new View.OnKeyListener() {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if (event.getAction() == KeyEvent.ACTION_DOWN) {
                /*隐藏软键盘*/
                /*Toast.makeText(MainActivity.this, "键盘出来啦", Toast.LENGTH_SHORT).show();*/
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (inputMethodManager.isActive()) {
                    inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
                }
                NavigationBarStatusBar(MainActivity.this, true);
                return true;
            }
            return false;

        }
    };

    // 沉浸式状态栏
    public static void NavigationBarStatusBar(Activity activity, boolean hasFocus) {
        if (hasFocus && Build.VERSION.SDK_INT >= 16) {
            View decorView = activity.getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION //视图延伸至导航栏区域，导航栏上浮于视图之上
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION  //隐藏导航栏
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN //状态栏隐藏
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE  //保持整个View稳定, 常和控制System UI悬浮, 隐藏的Flags共用, 使View不会因为System UI的变化而重新layout
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            decorView.setSystemUiVisibility(option);
            activity.getWindow().setNavigationBarColor(Color.TRANSPARENT);
            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    public static void dismissProgress() {
        progressDialog.dismiss();
    }

    private void buildDataCollections() throws Exception {
        //从json获取数据
        countyApartmentsList = JSONUtils.parseApartmentsFromJSON("sourcedata.json", this, "1");
        directApartmentsList = JSONUtils.parseApartmentsFromJSON("sourcedata.json", this, "2");
        if (countyApartmentsList != null && directApartmentsList != null) {
            apartmentsList.addAll(countyApartmentsList);
            apartmentsList.addAll(directApartmentsList);
            backCadresList = JSONUtils.parseBackupFromJson("sourcedata.json", this, "3");
            apartmentsNum = countyApartmentsList.size() + directApartmentsList.size();

            // 设置各单位最大人数为102
            cadresMatrix = new String[apartmentsNum + 3][MAX_CADRE_NUM];
            presentNum = new int[apartmentsNum + 1];
            for (int i = 0; i < apartmentsNum + 1; ++i) {
                presentNum[i] = 0;
            }
        } else {
            Toast.makeText(this, "请检查数据文件是否正确", Toast.LENGTH_LONG).show();
        }
        param = new CadresParams(directApartmentsList, countyApartmentsList, cadresMatrix, presentNum, cadresMap);
        ((DirectFragment) directFragment).setCadresParams(param);
        ((CountyFragment) directFragment).setCadresParams(param);
        ((BackFragment) directFragment).setCadresParams(param);
        ((ResearcherFragment) directFragment).setCadresParams(param);
    }

    private void initFragmentsData() {
        cadresList = JSONUtils.parseCadresFromJSON("sourcedata.json", this);
        for (Cadre c : cadresList) {
            int tmp_bmbh = Integer.valueOf(c.getBmbh());
            cadresMatrix[tmp_bmbh][presentNum[tmp_bmbh]] = c.getXm();
            presentNum[tmp_bmbh] = presentNum[tmp_bmbh] + 1; //该部门人数+1
            cadresMap.put(String.valueOf(tmp_bmbh) + c.getXm(), c);//Cadre对象放Map
        }
    }

    //    初始化搜索按钮
    private void initButton() {
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                /*判断是否是“SEARCH”键*/
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    /*隐藏软键盘*/
                    InputMethodManager imm = (InputMethodManager) v
                            .getContext().getSystemService(
                                    Context.INPUT_METHOD_SERVICE);
                    if (imm.isActive()) {
                        imm.hideSoftInputFromWindow(
                                v.getApplicationWindowToken(), 0);
                    }
                    NavigationBarStatusBar(MainActivity.this, true);
                    search_func();
                    return true;
                }
                return false;
            }
        });
//        searchImage = (ImageView) findViewById(R.id.search_button);
        searchImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //调用搜索方法
                search_func();
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_content:
                NavigationBarStatusBar(MainActivity.this, true);
                break;
            default:
                break;
        }
    }

    //  搜索方法
    private void search_func() {
        if (cadreType == BACKUP) {
            Intent intent = new Intent("showPro");
            intent.putExtra("search_condition", editText.getText().toString());
            LocalBroadcastManager.getInstance(this)
                    .sendBroadcast(intent);
        } else if (cadreType == RESEARCHER) {
            Intent intent = new Intent("researcherDoc");
            intent.putExtra("search_condition", editText.getText().toString());
            LocalBroadcastManager.getInstance(this)
                    .sendBroadcast(intent);
        } else if (editText.getText().toString().equals("") || editText.getText().toString() == null) {
            AlertReport("输入异常", "请输入搜索条件", MainActivity.this);
        } else {
            boolean isApartment = false;
            for (Apartment a : apartmentsList) {
                if (a.getBmmz().equals(editText.getText().toString())) {
                    Intent intent = new Intent(MainActivity.this, DetailView.class);
                    //将获取到的干部对象传递给detailView
                    intent.putExtra("intent_type", "search_by_apartment");
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("cadresParams", new CadresParams(directApartmentsList, countyApartmentsList, cadresMatrix, presentNum, cadresMap));
                    intent.putExtras(bundle);
                    intent.putExtra("bmbh", String.valueOf(a.getBmbh()));
                    intent.putExtra("bmmz", a.getBmmz());
                    intent.putExtra("params", param);
                    startActivity(intent);
                    isApartment = true;
                    break;
                }
            }
            if (!isApartment) {
                Intent intent = new Intent(MainActivity.this, DetailView.class);
                intent.putExtra("intent_type", "search_result");
                intent.putExtra("search_condition", editText.getText().toString());
                startActivity(intent);
            }

        }
    }

    public void switchContent_keep(Fragment from, Fragment to, int i) {
        if (from != to) {
            /*加载数据提示*/
            Message message = mHandler.obtainMessage(PROGRESS_START);
            mHandler.sendMessage(message);
            mContent = to;
            FragmentTransaction transaction = getSupportFragmentManager()
                    .beginTransaction();
            // 先判断是否被add过
            if (!to.isAdded()) {
                bundle.putString("text", fragmentNames[i]);
                to.setArguments(bundle);
                // 隐藏当前的fragment，add下一个fragment到Activity中
                transaction.hide(from).add(R.id.cadres_table, to).commit();
            } else {
                // 隐藏当前的fragment，显示下一个fragment
                transaction.hide(from).show(to).commit();
                // to.onResume();该命令可注释，若希望fragment切换的过程中，被显示的fragment执行onResume方法，则解注；
            }
            /*提示消除*/
            Message message2 = mHandler.obtainMessage(PROGRESS_END);
            mHandler.sendMessage(message2);
        }
    }

    //部门选择初始化函数
    private void initTypeSelector(Bundle savedInstanceState) {
        typeGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = (RadioButton) group.findViewById(checkedId);
                String type = radioButton.getText().toString();
                if (type.equals("区级机关")) {
                    if (cadreType != DIRECT) {
                        cadreType = DIRECT;
                        switchContent_keep(curFragment, directFragment, 1);
                        curFragment = directFragment;
                    }
                } else if (type.equals("镇街领导")) {
                    if (cadreType != COUNTY) {
                        cadreType = COUNTY;
                        switchContent_keep(curFragment, countyFragment, 0);
                        curFragment = countyFragment;
                    }
                } else if (type.equals("后备干部")) {
                    if (cadreType != BACKUP) {
                        cadreType = BACKUP;
                        switchContent_keep(curFragment, backupFragment, 2);
                        curFragment = backupFragment;
                    }
                } else if (type.equals("调研员")) {
                    if (cadreType != RESEARCHER) {
                        cadreType = RESEARCHER;
                        switchContent_keep(curFragment, researcherFragment, 3);
                        curFragment = researcherFragment;
                    }
                }
            }
        });

        try {
            if (savedInstanceState == null) {
                String tmptype = getIntent().getStringExtra("viewtype");

                if (tmptype.equals("区级机关")) {
                    bundle.putString("text", fragmentNames[1]);
                    directFragment.setArguments(bundle);
                    mContent = directFragment;
                    getSupportFragmentManager().beginTransaction().add(R.id.cadres_table, directFragment).commit();
//                  typeGroup.check(R.id.area_button);
                    cadreType = DIRECT;
                    curFragment = directFragment;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            typeGroup.check(R.id.area_button);
                        }
                    });
                } else if (tmptype.equals("镇街领导")) {
                    bundle.putString("text", fragmentNames[0]);
                    cadreType = COUNTY;
                    countyFragment.setArguments(bundle);
                    mContent = countyFragment;
                    curFragment = countyFragment;
                    getSupportFragmentManager().beginTransaction().add(R.id.cadres_table, countyFragment).commit();
                    typeGroup.check(R.id.county_button);
                } else if (tmptype.equals("后备干部")) {
                    bundle.putString("text", fragmentNames[2]);
                    backupFragment.setArguments(bundle);
                    mContent = backupFragment;
                    curFragment = backupFragment;
                    getSupportFragmentManager().beginTransaction().add(R.id.cadres_table, backupFragment).commit();
                    cadreType = BACKUP;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            typeGroup.check(R.id.backup_button);
                        }
                    });
                } else if (CadreType.RESEARCHER.getName().equals(tmptype)) {
                    bundle.putString("text", fragmentNames[3]);
                    researcherFragment.setArguments(bundle);
                    mContent = researcherFragment;
                    curFragment = researcherFragment;
                    getSupportFragmentManager().beginTransaction().add(R.id.cadres_table, researcherFragment).commit();
                    cadreType = RESEARCHER;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            typeGroup.check(R.id.researcher_button);
                        }
                    });
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Alert方法
    public void AlertReport(String title, String msg, Context context) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        if (!title.equals(""))
            dialog.setTitle(title);
        dialog.setMessage(msg);
        dialog.setCancelable(true);

        //OK按钮
        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                NavigationBarStatusBar(MainActivity.this, true);
            }
        });
        dialog.show();
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
                //String msg1 = "再按一次返回键回到桌面";
                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
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
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            Message message = mHandler.obtainMessage(EVENT_TIME_TO_CHANGE_IMAGE);
            mHandler.sendMessage(message);
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    /*
    数据加载进度
    * */
    public void progressDialogTip() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("正在加载数据，请稍等...");
        progressDialog.show();
    }

    class InitThread extends Thread {
        @Override
        public void run() {
            try {
                buildDataCollections();
                initFragmentsData();
                //初始化部门类型
                initTypeSelector(mSavedInstanceState);
                Message message = mHandler.obtainMessage(EVENT_TIME_TO_INIT);
                mHandler.sendMessage(message);
            } catch (Exception e) {
                /*加载数据提示*/
                Message message = mHandler.obtainMessage(FILE_NOT_FOUND);
                mHandler.sendMessage(message);
                e.printStackTrace();
            }
        }
    }
}

package com.yze.manageonpad.districtcadre.core.subview;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yze.manageonpad.districtcadre.BuildConfig;
import com.yze.manageonpad.districtcadre.R;
import com.yze.manageonpad.districtcadre.core.adapter.ResultAdapter;
import com.yze.manageonpad.districtcadre.model.Cadre;
import com.yze.manageonpad.districtcadre.utils.CadreUtils;
import static com.yze.manageonpad.districtcadre.MainActivity.param;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.support.v4.content.FileProvider.getUriForFile;
import static com.yze.manageonpad.districtcadre.MainActivity.NavigationBarStatusBar;

/**
 * @author yze
 *
 * 2019/2/27.
 */
public class DetailView extends AppCompatActivity {
    private TextView backbtn;
    private RecyclerView resultView;
    private TextView numText;
    private FragmentTabHost mTabHost;
    private LayoutInflater mInflater;
    private List<Cadre> cadreList = new ArrayList<Cadre>();
    private int listNum = 0;
    private ResultAdapter resultAdapter;
    @BindView(R.id.search_title) LinearLayout searchTitle;
    @BindView(R.id.result_row) LinearLayout resultTitle;
    private float x1 = 0;
    private float x2 = 0;
    private float y1 = 0;
    private float y2 = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_view);
        ButterKnife.bind(this);

        NavigationBarStatusBar(this, true);
        LinearLayout mainContent = (LinearLayout) findViewById(R.id.detail_content);
        mainContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavigationBarStatusBar(DetailView.this, true);
            }
        });
        //  设置标题栏高度
        searchTitle.getLayoutParams().height = 110;
        resultTitle.getLayoutParams().height = 100;
        ((LinearLayout.LayoutParams) resultTitle.getLayoutParams()).topMargin = 10;

        //  加载表中数据
        initResultView();
        //从intent获取上个Activity的数据
        getMsgFromIntent(getIntent());

        //  返回按钮
        backbtn = (TextView) findViewById(R.id.backtn);
        backbtn.getLayoutParams().height = 60;
        backbtn.getLayoutParams().width = 150;
        backbtn.setPadding(15, 0, 15, 0);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }

    private void initResultView() {
        numText = (TextView) findViewById(R.id.search_num);
        resultView = (RecyclerView) findViewById(R.id.result_recycler_view);
        resultAdapter = new ResultAdapter(cadreList, param, DetailView.this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        resultView.setLayoutManager(layoutManager);
        resultView.setAdapter(resultAdapter);
    }

    public  Intent getWordFileIntent(Context context, File file) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            Uri uri = getUriForFile(context,
                    BuildConfig.APPLICATION_ID+ ".fileprovider",
                    file);
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(uri, "application/msword");
        } else{
            Uri uri = Uri.fromFile(file);
            intent.setDataAndType(uri, "application/msword");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        return intent;
    }

    //读取文件
    public static String ReadDayDayString(Context context, String fileName) {
        InputStream is = null;
        String msg = null;
        try {
            is = context.getResources().getAssets().open(fileName);
            byte[] bytes = new byte[is.available()];
            is.read(bytes);
            msg = new String(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return msg;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //继承了Activity的onTouchEvent方法，直接监听点击事件
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            //当手指按下的时候
            x1 = event.getX();
            y1 = event.getY();
        }
        if(event.getAction() == MotionEvent.ACTION_UP) {
            //当手指离开的时候
            x2 = event.getX();
            y2 = event.getY();
            Log.d("move length", "onTouchEvent: x2" + String.valueOf(x2) + "  / x1"  + String.valueOf(x1));
            if(x2 - x1 > 50) {
                DetailView.this.finish();
            }
        }
        return super.onTouchEvent(event);
    }

    private void getMsgFromIntent(Intent intent) {
        String search_condition;
        TextView apartmentText;
        switch (intent.getStringExtra("intent_type")) {//点击单个干部
            case "single_cadre":
                Cadre tmp_cadre = (Cadre) intent.getSerializableExtra("cadre_object");
                apartmentText = (TextView) findViewById(R.id.search_item);
                apartmentText.setText(tmp_cadre.getXm());
                cadreList.clear();
                cadreList.add(tmp_cadre);
                resultAdapter.notifyDataSetChanged();
                numText.setVisibility(View.GONE);
                break;
//                搜索视图
            case "search_result": //初始化recyclerView
                search_condition = intent.getStringExtra("search_condition");
                apartmentText = (TextView) findViewById(R.id.search_item);
                //更新条件值
                apartmentText.setText("搜索 \"" + search_condition + "\" 的结果");
                List<Cadre> tmpCadreList = CadreUtils.getCadresListByName(search_condition, param.getCadreMap());
                for (Cadre c : tmpCadreList)
                    cadreList.add(c);
                numText.setText("共" + String.valueOf(cadreList.size()) + "条记录");
                resultAdapter.notifyDataSetChanged();
                break;
//                部门搜索视图
            case "search_by_apartment":
                search_condition = intent.getStringExtra("bmbh");
                apartmentText = (TextView) findViewById(R.id.search_item);
                //更新条件值
                apartmentText.setText(intent.getStringExtra("bmmz").toString());
                List<Cadre> tmpCadreList2 = CadreUtils.getCadreListByApartment(param, search_condition);
                for (Cadre c : tmpCadreList2)
                    cadreList.add(c);
                numText.setText("共" + String.valueOf(cadreList.size()) + "条记录");
                resultAdapter.notifyDataSetChanged();
                break;
            default:
                break;
        }
    }

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
}

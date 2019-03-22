package com.yze.manageonpad.districtcadre.core.subview;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yze.manageonpad.districtcadre.R;
import com.yze.manageonpad.districtcadre.core.adapter.CardAdapter;
import com.yze.manageonpad.districtcadre.core.adapter.ResultAdapter;
import com.yze.manageonpad.districtcadre.core.enums.NumEnum;
import com.yze.manageonpad.districtcadre.model.Cadre;
import com.yze.manageonpad.districtcadre.utils.CadreUtils;

import static com.yze.manageonpad.districtcadre.MainActivity.param;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.yze.manageonpad.districtcadre.MainActivity.NavigationBarStatusBar;

/**
 * @author yze
 * <p>
 * 2019/2/27.
 */
public class DetailView extends AppCompatActivity {
    private List<Cadre> cadreList = new ArrayList<Cadre>();

    @BindView(R.id.result_recycler_view)
    RecyclerView resultView;

    @BindView(R.id.result_row)
    LinearLayout resultTitle;

    @BindView(R.id.back_btn)
    TextView backBtn;

    @BindView(R.id.change_button)
    TextView changeBtn;

    @BindView(R.id.search_num)
    TextView numText;

    /**
     * 卡片视图
     */
    private final static int CARD_VIEW = 0;

    /**
     * 表格视图
     */
    private final static int BOOK_VIEW = 1;

    private int curViewType = 0;
    private RecyclerView.Adapter adapter;
    private float x1 = 0, x2 = 0, y1 = 0, y2 = 0;

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

        //  加载表中数据
        initResultView(CARD_VIEW);
        //从intent获取上个Activity的数据
        getDataFromIntent(getIntent());

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        changeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                curViewType = curViewType == CARD_VIEW ? BOOK_VIEW : CARD_VIEW;
                initResultView(curViewType);
            }
        });

    }

    /**
     * 初始化RecyclerView && 切换视图时更新
     *
     * @param type 视图类型
     */
    private void initResultView(int type) {
        if (type == CARD_VIEW) {
            resultTitle.setVisibility(View.GONE);
            CardAdapter cardAdapter;
            cardAdapter = new CardAdapter(R.layout.card_item, cadreList);
            cardAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_RIGHT);
            StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(NumEnum.NUM_3.getValue(), StaggeredGridLayoutManager.VERTICAL);
            resultView.setLayoutManager(layoutManager);
            resultView.setAdapter(cardAdapter);
            adapter = cardAdapter;
        } else {
            resultTitle.setVisibility(View.VISIBLE);
            ResultAdapter resultAdapter;
            resultAdapter = new ResultAdapter(cadreList, param, DetailView.this);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            resultView.setLayoutManager(layoutManager);
            resultView.setAdapter(resultAdapter);
            adapter = resultAdapter;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //继承了Activity的onTouchEvent方法，直接监听点击事件
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            //当手指按下的时候
            x1 = event.getX();
            y1 = event.getY();
        }
        if (event.getAction() == MotionEvent.ACTION_UP) {
            //当手指离开的时候
            x2 = event.getX();
            y2 = event.getY();
            Log.d("move length", "onTouchEvent: x2" + String.valueOf(x2) + "  / x1" + String.valueOf(x1));
            if (x2 - x1 > 50) {
                DetailView.this.finish();
            }
        }
        return super.onTouchEvent(event);
    }

    /**
     * 从intent获取数据
     *
     * @param intent
     */
    private void getDataFromIntent(Intent intent) {
        String search_condition;
        TextView apartmentText;
        switch (intent.getStringExtra("intent_type")) {//点击单个干部
            case "single_cadre":
                Cadre tmp_cadre = (Cadre) intent.getSerializableExtra("cadre_object");
                apartmentText = (TextView) findViewById(R.id.search_item);
                apartmentText.setText(tmp_cadre.getXm());
                cadreList.clear();
                cadreList.add(tmp_cadre);
                adapter.notifyDataSetChanged();
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
                adapter.notifyDataSetChanged();
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
                adapter.notifyDataSetChanged();
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

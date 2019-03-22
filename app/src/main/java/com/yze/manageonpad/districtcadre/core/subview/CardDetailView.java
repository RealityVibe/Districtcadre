package com.yze.manageonpad.districtcadre.core.subview;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yze.manageonpad.districtcadre.MainActivity;
import com.yze.manageonpad.districtcadre.R;
import com.yze.manageonpad.districtcadre.core.adapter.CardAdapter;
import com.yze.manageonpad.districtcadre.core.enums.NumEnum;
import com.yze.manageonpad.districtcadre.model.Cadre;
import com.yze.manageonpad.districtcadre.utils.CadreUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.yze.manageonpad.districtcadre.MainActivity.param;

public class CardDetailView extends AppCompatActivity {

    @BindView(R.id.result_recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.search_num)
    TextView numText;

    @BindView(R.id.back_btn)
    TextView backBtn;

    private List<Cadre> cadreList = new ArrayList<Cadre>();
    private CardAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carddetail_view);
        ButterKnife.bind(this);

        adapter = new CardAdapter(R.layout.card_item, cadreList);
        recyclerView.setAdapter(adapter);
        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_RIGHT);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(NumEnum.NUM_3.getValue(), StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        getMsgFromIntent(getIntent());
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void getMsgFromIntent(Intent intent) {
        String search_condition;
        switch (intent.getStringExtra("intent_type")) {//点击单个干部
            case "single_cadre":
                Cadre tmp_cadre = (Cadre) intent.getSerializableExtra("cadre_object");
                TextView apartmentText = (TextView) findViewById(R.id.search_item);
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

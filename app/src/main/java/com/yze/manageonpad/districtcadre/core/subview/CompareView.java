package com.yze.manageonpad.districtcadre.core.subview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yze.manageonpad.districtcadre.R;
import com.yze.manageonpad.districtcadre.core.adapter.CompareAdapter;
import com.yze.manageonpad.districtcadre.model.Cadre;
import com.yze.manageonpad.districtcadre.model.CompareRow;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.yze.manageonpad.districtcadre.MainActivity.leftName;
import static com.yze.manageonpad.districtcadre.MainActivity.param;
import static com.yze.manageonpad.districtcadre.MainActivity.rightName;

public class CompareView extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.back_btn)
    TextView backBtn;

    @BindView(R.id.compare_recycler_view)
    RecyclerView compareView;

    @BindView(R.id.left_name)
    TextView leftNameTextView;

    @BindView(R.id.right_name)
    TextView rightNameTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare_view);
        ButterKnife.bind(this);

        // 初始化按钮事件
        initButton();
        // 渲染对比项
        renderRecyclerView();
    }

    public void renderRecyclerView(){
        List<CompareRow> compareRows = new ArrayList<CompareRow>();
        Map<String, Cadre> cadreMap = param.getCadreMap();
        int count = 0;
        Cadre left = new Cadre();
        Cadre right= new Cadre();
        for(Map.Entry<String, Cadre> cadreEntry: cadreMap.entrySet()){
            if(leftName.equals(cadreEntry.getValue().getXm())){
                left = cadreEntry.getValue();
                count++;
            }
            if(rightName.equals(cadreEntry.getValue().getXm())){
                right = cadreEntry.getValue();
                count++;
            }
            if(count == 2){
                break;
            }

        }
        leftNameTextView.setText("姓名："+left.getXm()+" 职务："+left.getXrzw());
        rightNameTextView.setText("姓名："+right.getXm()+" 职务："+right.getXrzw());
        compareRows.add(new CompareRow("分管", left.getFg(), right.getFg()));
        compareRows.add(new CompareRow("籍贯", left.getJg(), right.getJg()));
        compareRows.add(new CompareRow("出生年月", left.getCsny(), right.getCsny()));
        compareRows.add(new CompareRow("全日制学历", left.getQrzxw(), right.getQrzxw()));
        compareRows.add(new CompareRow("毕业院校", left.getXl(), right.getXl()));
        compareRows.add(new CompareRow("最高学历", left.getXw(), right.getXw()));
        compareRows.add(new CompareRow("参加工作时间", left.getCjgzsj(), right.getCjgzsj()));
        compareRows.add(new CompareRow("入党时间", left.getRdsj(), right.getRdsj()));
        compareRows.add(new CompareRow("任现职时间", left.getRxzsj(), right.getRxzsj()));
        CompareAdapter adapter = new CompareAdapter(R.layout.compare_row_item, compareRows);
        adapter.openLoadAnimation();
        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        compareView.setAdapter(adapter);
        compareView.setLayoutManager(layoutManager);
    }

    public void initButton() {
        backBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_btn:
                this.finish();
                break;
            default:
                break;
        }
    }
}

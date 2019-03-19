package com.yze.manageonpad.districtcadre.core.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yze.manageonpad.districtcadre.MainActivity;
import com.yze.manageonpad.districtcadre.R;
import com.yze.manageonpad.districtcadre.core.adapter.NewRvAdapter;
import com.yze.manageonpad.districtcadre.core.enums.CadreType;
import com.yze.manageonpad.districtcadre.model.CadresParams;
import static com.yze.manageonpad.districtcadre.MainActivity.param;

/**
 * @author yze
 * <p>
 * 2019/2/27.
 */
public class CountyFragment extends Fragment {
    private View view;
    private RecyclerView mRecyclerView;
    private NewRvAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflate, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = inflate.inflate(R.layout.county_fragment, container, false);

        initView();
        return view;
    }

    //初始化布局View
    public void initView() {
        // 初始化recyclerview
        mRecyclerView = (RecyclerView) view.findViewById(R.id.county_fragment_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new NewRvAdapter(this.getContext(), param, CadreType.COUNTY);
        mRecyclerView.setAdapter(mAdapter);
    }

    public String getName() {
        return "CountyFragment";
    }

}

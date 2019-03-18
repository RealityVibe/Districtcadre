package com.yze.manageonpad.districtcadre.core.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.yze.manageonpad.districtcadre.R;
import com.yze.manageonpad.districtcadre.core.adapter.NewRvAdapter;
import com.yze.manageonpad.districtcadre.model.Apartment;
import com.yze.manageonpad.districtcadre.model.CadresParams;

import java.util.ArrayList;
import java.util.List;

import static com.yze.manageonpad.districtcadre.MainActivity.directApartmentsList;

/**
 * @author yze
 * <p>
 * 2019/2/27.
 */
public class DirectFragment extends Fragment {

    private View view;
    private RecyclerView mRecyclerView;
    private List<Apartment> apartments = new ArrayList<Apartment>();
    private CadresParams cadresParams;
    @Override
    public View onCreateView(LayoutInflater inflate, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = inflate.inflate(R.layout.direct_fragment, container, false);

        initView();
        return view;
    }

    //初始化布局View
    public void initView() {
        apartments = directApartmentsList;
        mRecyclerView = (RecyclerView) view.findViewById(R.id.direct_fragment_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        NewRvAdapter mAdapter = new NewRvAdapter(this.getContext(), cadresParams);
        mRecyclerView.setAdapter(mAdapter);

    }


    public String getName() {
        return "DirectFragment";
    }

    public CadresParams getCadresParams() {
        return cadresParams;
    }

    public void setCadresParams(CadresParams cadresParams) {
        this.cadresParams = cadresParams;
    }
}

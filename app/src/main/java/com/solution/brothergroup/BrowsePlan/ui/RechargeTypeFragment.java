package com.solution.brothergroup.BrowsePlan.ui;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.solution.brothergroup.BrowsePlan.dto.PlanDataDetails;
import com.solution.brothergroup.R;

import java.util.ArrayList;

public class RechargeTypeFragment extends Fragment {

    RecyclerView recycler_view;
    TextView noData;


    ArrayList<PlanDataDetails> operatorDetailshow = new ArrayList<>();
    // String response = "";
    RechargeTypeAdapter mAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_recharge_plan, container, false);

        try {
            operatorDetailshow = (ArrayList<PlanDataDetails>) getArguments().getSerializable("response");

            recycler_view = v.findViewById(R.id.recycler_view);
            noData = v.findViewById(R.id.noData);


            if (operatorDetailshow != null && operatorDetailshow.size() > 0) {
                noData.setVisibility(View.GONE);
                mAdapter = new RechargeTypeAdapter(operatorDetailshow, getActivity());
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                recycler_view.setLayoutManager(mLayoutManager);
                recycler_view.setItemAnimator(new DefaultItemAnimator());
                recycler_view.setAdapter(mAdapter);

            } else {
                noData.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return v;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }


}

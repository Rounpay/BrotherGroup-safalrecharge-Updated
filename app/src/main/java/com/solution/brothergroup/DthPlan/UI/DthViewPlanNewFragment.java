package com.solution.brothergroup.DthPlan.UI;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.solution.brothergroup.DthPlan.dto.DthPlanLanguages;
import com.solution.brothergroup.DthPlan.dto.PlanInfoPlan;
import com.solution.brothergroup.DthPlan.dto.PlanRPResponse;
import com.solution.brothergroup.R;
import com.solution.brothergroup.Util.UtilMethods;

import java.util.ArrayList;


public class DthViewPlanNewFragment extends Fragment {

    RecyclerView recycler_view;
    ArrayList<PlanInfoPlan> operatorDetailshow = new ArrayList<PlanInfoPlan>();
    ArrayList<PlanInfoPlan> operatorDetailPAshow = new ArrayList<PlanInfoPlan>();
    ArrayList<PlanRPResponse> operatorDetailRPshow = new ArrayList<>();
    ArrayList<PlanRPResponse> operatorDetailRPNewshow = new ArrayList<>();
    ArrayList<DthPlanLanguages> operatorDetailRPNewLanguages = new ArrayList<>();


    EditText searchEt;
    ImageView clearIcon;
    private View clearView;
    private DthPlanListNewAdapter mAdapter;
    private DthPlanListRPNewAdapter mRPAdapter;
    private DthPlanListLanguageAdapter mLanguageAdapter;
    private boolean isPlanServiceUpdated;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_recharge_plan_new, container, false);

        try {
            String opId = getArguments().getString("OpID");
            isPlanServiceUpdated = getArguments().getBoolean("IsPlanServiceUpdated", false);
            operatorDetailshow = (ArrayList<PlanInfoPlan>) getArguments().getSerializable("response");
            operatorDetailRPshow = (ArrayList<PlanRPResponse>) getArguments().getSerializable("responseRP");
            operatorDetailRPNewshow = (ArrayList<PlanRPResponse>) getArguments().getSerializable("responseRPNew");
            operatorDetailRPNewLanguages = (ArrayList<DthPlanLanguages>) getArguments().getSerializable("responseRPNewLanguages");
            operatorDetailPAshow = (ArrayList<PlanInfoPlan>) getArguments().getSerializable("responsePA");
            recycler_view = v.findViewById(R.id.recycler_view);
            recycler_view.setLayoutManager(new LinearLayoutManager(getActivity()));
            searchEt = v.findViewById(R.id.searchEt);
            clearIcon = v.findViewById(R.id.clearIcon);
            clearView = v.findViewById(R.id.clearView);


            if (operatorDetailshow != null && operatorDetailshow.size() > 0) {
                mAdapter = new DthPlanListNewAdapter(isPlanServiceUpdated, opId, operatorDetailshow, getActivity());
                recycler_view.setAdapter(mAdapter);
            } else if (operatorDetailPAshow != null && operatorDetailPAshow.size() > 0) {
                mAdapter = new DthPlanListNewAdapter(isPlanServiceUpdated, opId, operatorDetailPAshow, getActivity());
                recycler_view.setAdapter(mAdapter);
            } else if (operatorDetailRPshow != null && operatorDetailRPshow.size() > 0) {
                mRPAdapter = new DthPlanListRPNewAdapter(isPlanServiceUpdated, operatorDetailRPshow, getActivity(), opId);
                recycler_view.setAdapter(mRPAdapter);
            } else if (operatorDetailRPNewshow != null && operatorDetailRPNewshow.size() > 0) {
                mRPAdapter = new DthPlanListRPNewAdapter(isPlanServiceUpdated, operatorDetailRPNewshow, getActivity(), opId);
                recycler_view.setAdapter(mRPAdapter);
            } else if (operatorDetailRPNewLanguages != null && operatorDetailRPNewLanguages.size() > 0) {
                mLanguageAdapter = new DthPlanListLanguageAdapter(operatorDetailRPNewLanguages, getActivity());
                recycler_view.setAdapter(mLanguageAdapter);
            } else {
                UtilMethods.INSTANCE.Error(getActivity(),"Data is not available");
            }


            clearIcon.setOnClickListener(v1 -> searchEt.setText(""));

            searchEt.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (s.length() > 0) {
                        clearView.setVisibility(View.VISIBLE);
                    } else {
                        clearView.setVisibility(View.GONE);
                    }

                    if (mAdapter != null) {
                        mAdapter.getFilter().filter(s);
                    }
                    if (mRPAdapter != null) {
                        mRPAdapter.getFilter().filter(s);
                    }
                    if (mLanguageAdapter != null) {
                        mLanguageAdapter.getFilter().filter(s);
                    }

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        return v;
    }


}

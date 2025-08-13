package com.solution.brothergroup.DthPlan.UI;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.solution.brothergroup.BrowsePlan.dto.PlanDataRecords;
import com.solution.brothergroup.BrowsePlan.dto.ResponsePlan;
import com.solution.brothergroup.DthPlan.dto.DthPlanLanguages;
import com.solution.brothergroup.DthPlan.dto.PlanInfoPlan;
import com.solution.brothergroup.DthPlan.dto.PlanInfoRPData;
import com.solution.brothergroup.DthPlan.dto.PlanInfoRPWithPackage;
import com.solution.brothergroup.DthPlan.dto.PlanInfoRecords;
import com.solution.brothergroup.DthPlan.dto.PlanRPResponse;
import com.solution.brothergroup.R;

import java.util.ArrayList;


public class DthViewPlanFragment extends Fragment {

    RecyclerView recycler_view;
    TextView noData;

    ResponsePlan responsePlan = new ResponsePlan();
    PlanDataRecords records;
    ArrayList<PlanInfoPlan> operatorDetailshow = new ArrayList<PlanInfoPlan>();
    ArrayList<PlanRPResponse> operatorDetailRPshow = new ArrayList<>();
    ArrayList<DthPlanLanguages> operatorDetailRPLanguageshow = new ArrayList<>();
    PlanInfoRecords response;
    PlanInfoRPData responseRP;
    PlanInfoRecords responsePA;
    DthPlanListAdapter mAdapter;
    private PlanInfoRPWithPackage responseRPPackage;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_recharge_plan, container, false);

        try {
            String re_type = getArguments().getString("type");
            response = (PlanInfoRecords) getArguments().getSerializable("response");
            responseRP = (PlanInfoRPData) getArguments().getSerializable("responseRP");
            responseRPPackage = (PlanInfoRPWithPackage) getArguments().getSerializable("responseRPPackage");
            responsePA = (PlanInfoRecords) getArguments().getSerializable("responsePA");
            recycler_view = v.findViewById(R.id.recycler_view);
            noData = v.findViewById(R.id.noData);

            if (response != null) {
                if (re_type.equalsIgnoreCase("Plan")) {
                    operatorDetailshow.addAll(response.getPlan());
                } else {
                    operatorDetailshow.addAll(response.getAddOnPack());
                }
            } else if (responseRP != null) {
                if (responseRP.getResponse() != null && responseRP.getResponse().size() > 0) {
                    for (int i = 0; i < responseRP.getResponse().size(); i++) {
                        if (re_type.equalsIgnoreCase(responseRP.getResponse().get(i).getRechargeType())) {
                            operatorDetailRPshow.add(responseRP.getResponse().get(i));
                        }
                    }

                }
                /*if (re_type.equalsIgnoreCase("Plan")) {
                    operatorDetailRPshow.addAll(responseRP.getResponse());
                }*/
            } else if (responseRPPackage != null) {
                if (responseRPPackage.getResponse() != null && responseRPPackage.getResponse().size() > 0) {
                    for (int i = 0; i < responseRPPackage.getResponse().size(); i++) {
                        if (re_type.equalsIgnoreCase(responseRPPackage.getResponse().get(i).getRechargeType())) {
                            operatorDetailRPshow.add(responseRPPackage.getResponse().get(i));
                        }
                    }

                }
                if (responseRPPackage.getPackageList() != null && responseRPPackage.getPackageList().size() > 0) {
                    for (int i = 0; i < responseRPPackage.getPackageList().size(); i++) {
                        if (re_type.equalsIgnoreCase(responseRPPackage.getPackageList().get(i).getRechargeType())) {
                            operatorDetailRPshow.add(responseRPPackage.getPackageList().get(i));
                        }
                    }

                }if (responseRPPackage.getLanguages() != null && responseRPPackage.getLanguages().size() > 0) {
                    if (re_type.equalsIgnoreCase("Language")) {
                        operatorDetailRPLanguageshow.addAll(responseRPPackage.getLanguages());
                    }

                }

            } else if (responsePA != null) {
                if (re_type.equalsIgnoreCase("Plan")) {
                    operatorDetailshow.addAll(responsePA.getPlan());
                } else {
                    operatorDetailshow.addAll(responsePA.getAddOnPack());
                }
            }

            if (operatorDetailshow != null && operatorDetailshow.size() > 0) {
                noData.setVisibility(View.GONE);
                DthPlanListAdapter mAdapter = new DthPlanListAdapter(operatorDetailshow, getActivity());

                recycler_view.setLayoutManager(new LinearLayoutManager(getActivity()));
                recycler_view.setAdapter(mAdapter);

            } else if (operatorDetailRPshow != null && operatorDetailRPshow.size() > 0) {
                noData.setVisibility(View.GONE);
                DthPlanListRPAdapter mAdapter = new DthPlanListRPAdapter(operatorDetailRPshow, getActivity());

                recycler_view.setLayoutManager(new LinearLayoutManager(getActivity()));
                recycler_view.setAdapter(mAdapter);

            } else if (operatorDetailRPLanguageshow!= null && operatorDetailRPLanguageshow.size() > 0) {
                noData.setVisibility(View.GONE);
                DthPlanListLanguageAdapter mAdapter = new DthPlanListLanguageAdapter(operatorDetailRPLanguageshow, getActivity());

                recycler_view.setLayoutManager(new LinearLayoutManager(getActivity()));
                recycler_view.setAdapter(mAdapter);

            } else {
                noData.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return v;
    }


}

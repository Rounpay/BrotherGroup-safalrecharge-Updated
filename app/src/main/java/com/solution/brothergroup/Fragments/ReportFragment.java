package com.solution.brothergroup.Fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.solution.brothergroup.Activities.MoveToBankReportActivity;
import com.solution.brothergroup.Aeps.UI.AEPSReportActivity;
import com.solution.brothergroup.AppUser.Activity.AccountStatementReportActivity;
import com.solution.brothergroup.AppUser.Activity.AppUserListActivity;
import com.solution.brothergroup.Activities.CreateUserActivity;
import com.solution.brothergroup.Activities.DMRReport;
import com.solution.brothergroup.Activities.DisputeReport;
import com.solution.brothergroup.Activities.FundOrderPendingActivity;
import com.solution.brothergroup.Activities.FundRecReport;
import com.solution.brothergroup.Activities.FundReqReport;
import com.solution.brothergroup.Activities.LedgerReport;
import com.solution.brothergroup.Activities.MainActivity;
import com.solution.brothergroup.Activities.PaymentRequest;
import com.solution.brothergroup.Activities.SpecificRechargeReportActivity;
import com.solution.brothergroup.Activities.UserDayBookActivity;
import com.solution.brothergroup.Api.Object.AssignedOpType;
import com.solution.brothergroup.Api.Object.OpTypeRollIdWiseServices;
import com.solution.brothergroup.CommissionSlab.ui.CommissionScreen;
import com.solution.brothergroup.Fragments.Adapter.HomeOptionListAdapter;
import com.solution.brothergroup.Fragments.interfaces.RefreshCallBack;
import com.solution.brothergroup.R;
import com.solution.brothergroup.RECHARGEANDBBPS.UI.RechargeHistory;
import com.solution.brothergroup.Util.OpTypeResponse;
import com.solution.brothergroup.Util.UtilMethods;
import com.solution.brothergroup.W2RRequest.report.W2RHistory;

public class ReportFragment extends Fragment implements RefreshCallBack {
    View view;
    HomeOptionListAdapter mDashboardOptionListAdapter;
    RecyclerView rechargeRecyclerView;
    OpTypeRollIdWiseServices mOpTypeData;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_report, container, false);
        UtilMethods.INSTANCE.setDashboardStatus(getActivity(), false);
        getIds();

        try {
            mOpTypeData = ((MainActivity) getActivity()).mActiveServiceData;
            if (mOpTypeData == null) {
                mOpTypeData = new Gson().fromJson(UtilMethods.INSTANCE.getActiveService(getActivity()), OpTypeRollIdWiseServices.class);
            }
        } catch (NullPointerException npe) {
            mOpTypeData = new Gson().fromJson(UtilMethods.INSTANCE.getActiveService(getActivity()), OpTypeRollIdWiseServices.class);
        } catch (Exception e) {
            mOpTypeData = new Gson().fromJson(UtilMethods.INSTANCE.getActiveService(getActivity()), OpTypeRollIdWiseServices.class);
        }
        setDashboardData(mOpTypeData);

        // setDashboardData(new Gson().fromJson(UtilMethods.INSTANCE.getActiveService(getActivity()), AppUserListResponse.class));
        return view;
    }


    private void getIds() {
        rechargeRecyclerView = view.findViewById(R.id.rechargeRecyclerView);
        rechargeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    private void setDashboardData(OpTypeRollIdWiseServices mActiveServiceData) {
        try {

            if (mActiveServiceData != null && mActiveServiceData.getGetShowableRTReportSerive() != null && mActiveServiceData.getGetShowableRTReportSerive().size() > 0) {

                mDashboardOptionListAdapter = new HomeOptionListAdapter(mActiveServiceData.getGetShowableRTReportSerive(), getActivity(), new HomeOptionListAdapter.ClickView() {

                    @Override
                    public void onClick(AssignedOpType operator) {
                        openNewScreen(operator.getServiceID());
                    }

                    @Override
                    public void onPackageUpgradeClick() {

                    }

                    // mDashboardOptionListAdapter = new HomeOptionListAdapter(mActiveServiceData.getShowableActiveSerive(), getActivity(), new HomeOptionListAdapter.ClickView() {

                }, R.layout.adapter_dashboard_option);
                rechargeRecyclerView.setAdapter(mDashboardOptionListAdapter);
            } else {
                if (UtilMethods.INSTANCE.isNetworkAvialable(getActivity())) {
                    try {
                        /* UtilMethods.INSTANCE.Balancecheck(getActivity(), loader);*/
                        UtilMethods.INSTANCE.GetActiveService(getActivity(), new UtilMethods.ApiActiveServiceCallBack() {
                            @Override
                            public void onSucess(OpTypeResponse mOpTypeResponse, OpTypeRollIdWiseServices mOpTypeRollIdWiseServices) {
                                mOpTypeData=mOpTypeRollIdWiseServices;

                                try {
                                    ((MainActivity) getActivity()).mActiveServiceData = mOpTypeData;
                                    ((MainActivity) getActivity()).isECollectEnable = mOpTypeResponse.getECollectEnable();
                                    ((MainActivity) getActivity()).isUpiQR = mOpTypeResponse.getUPIQR();
                                    ((MainActivity) getActivity()).isAddMoneyEnable =mOpTypeResponse.getAddMoneyEnable();
                                    ((MainActivity) getActivity()).isPaymentGatway = mOpTypeResponse.getPaymentGatway();
                                    ((MainActivity) getActivity()).isUPI = mOpTypeResponse.getUPI();

                                } catch (NullPointerException npe) {

                                }
                                if (mOpTypeRollIdWiseServices != null && mOpTypeRollIdWiseServices.getGetShowableRTReportSerive() != null &&
                                        mOpTypeRollIdWiseServices.getGetShowableRTReportSerive().size() > 0) {

                                    setDashboardData(mOpTypeRollIdWiseServices);
                                }
                            }


                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {
                    UtilMethods.INSTANCE.NetworkError(getActivity(), getResources().getString(R.string.err_msg_network_title),
                            getResources().getString(R.string.err_msg_network));
                }
            }
        } catch (Exception e) {

        }
    }


    void openNewScreen(int id) {

        if (id == 1001) {
            Intent intent = new Intent(getActivity(), RechargeHistory.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        }
        if (id == 102) {
            Intent intent = new Intent(getActivity(), LedgerReport.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        }
        if (id == 103) {
            Intent i = new Intent(getActivity(), FundReqReport.class);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
        }
        if (id == 1004) {
            Intent n = new Intent(getActivity(), DisputeReport.class);
            n.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(n);
        }
        if (id == 105) {
            Intent i = new Intent(getActivity(), DMRReport.class);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);

        }
        if (id == 106) {
            // Fund Transfer Report

        }
        if (id == 107) {
            Intent i = new Intent(getActivity(), FundRecReport.class);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);

        }
        if (id == 108) {
            Intent i = new Intent(getActivity(), UserDayBookActivity.class);
            i.putExtra("Type", "UserDayBook");
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);

        }
        if (id == 120) {
            Intent i = new Intent(getActivity(), AEPSReportActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);

        }
        if (id == 109) {
            Intent i = new Intent(getActivity(), FundOrderPendingActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            getActivity().startActivity(i);
        }
        if (id == 110) {
            Intent i = new Intent(getActivity(), PaymentRequest.class);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            getActivity().startActivity(i);
        }
        if (id == 111) {
            Intent i = new Intent(getActivity(), CreateUserActivity.class);
            i.putExtra("KeyFor","User");
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            getActivity().startActivity(i);
        }
        if (id == 112) {
            Intent i = new Intent(getActivity(), AppUserListActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            getActivity().startActivity(i);
        }
        if (id == 113) {
            Intent n = new Intent(getActivity(), CommissionScreen.class);
            n.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(n);
        }
        if (id == 114) {
            Intent n = new Intent(getActivity(), W2RHistory.class);
            n.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(n);
        }
        if (id == 115) {
            Intent i = new Intent(getActivity(), UserDayBookActivity.class);
            i.putExtra("Type", "UserDayBookDMT");
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);

        }
        if (id == 118) {
            Intent i = new Intent(getActivity(), SpecificRechargeReportActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);

        }
        if (id == 124) {
            // UtilMethods.INSTANCE.GetArealist(getActivity(), loader, loginPrefResponse, null);
            Intent intent = new Intent(getActivity(), AccountStatementReportActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        }
        if (id == 135) {
            Intent i = new Intent(getActivity(), MoveToBankReportActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);

        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((MainActivity) context).mRefreshCallBack = this;
    }

    @Override
    public void onRefresh(Object object) {
        mOpTypeData=(OpTypeRollIdWiseServices) object;
        setDashboardData(mOpTypeData);

        // setDashboardData(new Gson().fromJson(UtilMethods.INSTANCE.getActiveService(getActivity()), AppUserListResponse.class));
    }

   /* @Override
    public void onClick(View v) {
        if (v == ll_RechargeReport) {
            Intent intent = new Intent(getActivity(), RechargeHistory.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        } else if (v == btnLadgerReport) {
            Intent intent = new Intent(getActivity(), LedgerReport.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        } else if (v == btnDisputeReport) {
//            Intent n = new Intent(getActivity(), DisputeReport.class);
//            startActivity(n);
        } else if (v == btnfundRecReport) {
            Intent i = new Intent(getActivity(), FundRecReport.class);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

            startActivity(i);
        } else if (v == btncommisionSlab) {
            Intent n = new Intent(getActivity(), CommissionScreen.class);
            n.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(n);
        } else if (v == btnfundRqReport) {
            Intent i = new Intent(getActivity(), FundReqReport.class);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
        } else if (v == btnDmrReport) {
            Intent i = new Intent(getActivity(), DMRReport.class);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
        }
        else if (v == btnuserDayBook) {
            Intent i = new Intent(getActivity(), UserDayBookActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
            *//*if (UtilMethods.INSTANCE.isNetworkAvialable(getActivity())) {

                UtilMethods.INSTANCE.OpenDaybookDialog(getActivity());

            } else {
                UtilMethods.INSTANCE.dialogOk(getActivity(), getResources().getString(R.string.err_msg_network_title),
                        getResources().getString(R.string.err_msg_network_title), 2);
            }*//*
        }
    }*/
}

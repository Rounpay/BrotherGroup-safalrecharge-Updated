package com.solution.brothergroup.Activities;

import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;
import com.solution.brothergroup.Adapter.RechargeReportAdapter;
import com.solution.brothergroup.Api.Object.RechargeStatus;
import com.solution.brothergroup.Api.Response.RechargeReportResponse;
import com.solution.brothergroup.Authentication.dto.LoginResponse;
import com.solution.brothergroup.R;
import com.solution.brothergroup.Util.ApplicationConstant;
import com.solution.brothergroup.Util.CustomFilterDialogUtils.CustomFilterDialog;
import com.solution.brothergroup.Util.UtilMethods;
import com.solution.brothergroup.usefull.CustomLoader;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class SpecificRechargeReportActivity extends AppCompatActivity {
    EditText et_search_number;
    private CustomFilterDialog mCustomFilterDialog;
    private CustomLoader loader;
    private View noData;
    private LoginResponse mLoginDataResponse;
    private RecyclerView recycler_view;
    private String todatay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_recharge_report);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setTitle("Specific Recharge Report");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_icon);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        et_search_number = findViewById(R.id.et_search_number);
        mCustomFilterDialog = new CustomFilterDialog(this);
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        recycler_view = findViewById(R.id.recycler_view);
        recycler_view.setLayoutManager(new LinearLayoutManager(this));
        noData = findViewById(R.id.noData);
        // OpenRechargeDialog();

        SharedPreferences myPrefs = getSharedPreferences(ApplicationConstant.INSTANCE.prefNameLoginPref, MODE_PRIVATE);
        String getLoginPref = myPrefs.getString(ApplicationConstant.INSTANCE.LoginPref, "");
        mLoginDataResponse = new Gson().fromJson(getLoginPref, LoginResponse.class);

        final Calendar myCalendar = Calendar.getInstance();
        String myFormat = "dd-MMM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        todatay = sdf.format(myCalendar.getTime());


        findViewById(R.id.searchView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_search_number.getText().toString().isEmpty()) {
                    et_search_number.setError("Please Enter Mobile Number");
                    et_search_number.requestFocus();
                    return;
                } else if (et_search_number.getText().length() != 10) {
                    et_search_number.setError("Invalid Mobile Number");
                    et_search_number.requestFocus();
                    return;
                }
                HitApi();
            }
        });
    }

    public void HitApi() {
        if (UtilMethods.INSTANCE.isNetworkAvialable(this)) {
            loader.show();
            loader.setCancelable(false);
            loader.setCanceledOnTouchOutside(false);
            UtilMethods.INSTANCE.RechargeReport(this, "0", "50", 0, todatay, todatay,
                    "", et_search_number.getText().toString(), "", "false",
                    false, loader, new UtilMethods.ApiCallBackTwoMethod() {
                        @Override
                        public void onSucess(Object object) {
                            RechargeReportResponse mRechargeReportResponse = (RechargeReportResponse) object;
                            dataParse(mRechargeReportResponse);
                        }

                        @Override
                        public void onError(Object object) {

                        }
                    });
        } else {
            UtilMethods.INSTANCE.NetworkError(this, getResources().getString(R.string.err_msg_network_title),
                    getResources().getString(R.string.err_msg_network));
        }
    }

    public void dataParse(RechargeReportResponse transactions) {


        ArrayList<RechargeStatus> transactionsObjects = transactions.getRechargeReport();

        if (transactionsObjects.size() > 0) {
            noData.setVisibility(View.GONE);
            recycler_view.setVisibility(View.VISIBLE);
            RechargeReportAdapter mAdapter = new RechargeReportAdapter(transactionsObjects, this, false, mLoginDataResponse.getData().getRoleID());
            recycler_view.setAdapter(mAdapter);

        } else {
            noData.setVisibility(View.VISIBLE);
            UtilMethods.INSTANCE.Error(this, "Record Not Found.");
        }
    }
}

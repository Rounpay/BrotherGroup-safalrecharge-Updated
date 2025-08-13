package com.solution.brothergroup.DMTWithPipe.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.TextView;



import com.solution.brothergroup.Api.Response.BenisObject;
import com.solution.brothergroup.Api.Response.RechargeReportResponse;
import com.solution.brothergroup.DMTWithPipe.Adapter.BeneficiaryAdapterNew;
import com.solution.brothergroup.DMTWithPipe.networkAPI.UtilsMethodDMTPipe;
import com.solution.brothergroup.R;
import com.solution.brothergroup.Util.ActivityActivityMessage;
import com.solution.brothergroup.Util.ApplicationConstant;
import com.solution.brothergroup.Util.GlobalBus;
import com.solution.brothergroup.Util.UtilMethods;
import com.solution.brothergroup.usefull.CustomLoader;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

public class BeneficiaryListScreenNew extends AppCompatActivity {

    RecyclerView recycler_view;
    TextView noData;

    RechargeReportResponse beneResponse;

    ArrayList<BenisObject> operator;
    private TextView tvView;
    private String oidIntent, sidIntent;
    private CustomLoader loader;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beneficiary_list_screen);
        oidIntent = getIntent().getStringExtra("OID");
        sidIntent = getIntent().getStringExtra("SID");
         prefs = getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, Context.MODE_PRIVATE);
        GetId();
    }

    private void GetId() {
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Beneficiary List");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_icon);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        recycler_view = (RecyclerView) findViewById(R.id.recycler_view);
        recycler_view.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        noData = (TextView) findViewById(R.id.noData);

        getBeneficiaryList();


    }

    public void getBeneficiaryList() {

        if (UtilMethods.INSTANCE.isNetworkAvialable(this)) {
            loader.show();
            loader.setCancelable(false);
            loader.setCanceledOnTouchOutside(false);
            UtilsMethodDMTPipe.INSTANCE.GetBeneficiaryNew(this, oidIntent, prefs.getString(ApplicationConstant.INSTANCE.senderNumberPref, ""), loader, new UtilsMethodDMTPipe.ApiCallBack() {
                @Override
                public void onSucess(Object object) {
                    RechargeReportResponse beneResponse = (RechargeReportResponse) object;
                    if (beneResponse.getBenis() != null && beneResponse.getBenis().size() > 0) {
                        operator = beneResponse.getBenis();
                        recycler_view.setVisibility(View.VISIBLE);
                        if (operator != null && operator.size() > 0) {
                            noData.setVisibility(View.GONE);
                            recycler_view.setAdapter(new BeneficiaryAdapterNew(operator, BeneficiaryListScreenNew.this, oidIntent, sidIntent));
                        } else {
                            noData.setVisibility(View.VISIBLE);
                        }
                    } else {
                        recycler_view.setVisibility(View.GONE);
                        noData.setVisibility(View.VISIBLE);
                        UtilMethods.INSTANCE.Error(BeneficiaryListScreenNew.this, "No Beneficiary found ! please Add Beneficiary");
                    }
                }
            });


        } else {
            UtilMethods.INSTANCE.NetworkError(this);
        }


        /*SharedPreferences prefs = getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String response = prefs.getString(ApplicationConstant.INSTANCE.beneficiaryListPref, "");
        Gson gson = new Gson();
        beneResponse = gson.fromJson(response, RechargeReportResponse.class);
        if (beneResponse.getBenis() != null && beneResponse.getBenis().size() > 0) {
            operator = beneResponse.getBenis();
            recycler_view.setVisibility(View.VISIBLE);
        } else {
            recycler_view.setVisibility(View.GONE);
            UtilMethods.INSTANCE.Error(this, "No Beneficiary found ! please Add Beneficiary");
        }*/

    }

    @Subscribe
    public void onActivityActivityMessage(ActivityActivityMessage activityFragmentMessage) {
        if (activityFragmentMessage.getMessage().equalsIgnoreCase("transferDoneDialog")) {
            SharedPreferences prefs = getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
            String senderNumber = prefs.getString(ApplicationConstant.INSTANCE.senderNumberPref, null);

            if (UtilMethods.INSTANCE.isNetworkAvialable(this)) {

//                UtilMethods.INSTANCE.GetSender(this, senderNumber, null);

            } else {
                UtilMethods.INSTANCE.NetworkError(this);
            }
            finish();
        } else if (activityFragmentMessage.getFrom().equalsIgnoreCase("beneDeleted")) {
            onBackPressed();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            GlobalBus.getBus().register(this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Unregister the registered event.
        GlobalBus.getBus().unregister(this);
    }

    public void refresh() {
        getBeneficiaryList();
    }
}

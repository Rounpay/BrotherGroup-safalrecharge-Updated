package com.solution.brothergroup.DMRNEW.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.solution.brothergroup.Api.Response.BenisObject;
import com.solution.brothergroup.Api.Response.RechargeReportResponse;
import com.solution.brothergroup.R;
import com.solution.brothergroup.Util.ActivityActivityMessage;
import com.solution.brothergroup.Util.ApplicationConstant;
import com.solution.brothergroup.Util.GlobalBus;
import com.solution.brothergroup.Util.UtilMethods;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

public class BeneficiaryListScreen extends AppCompatActivity {

    RecyclerView recycler_view;
    TextView noData;
    BeneficiaryAdapter mAdapter;
    RechargeReportResponse beneResponse;

    ArrayList<BenisObject> operator;
    private TextView tvView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowInsetsControllerCompat controller = new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        controller.setAppearanceLightStatusBars(true);
        controller.setAppearanceLightNavigationBars(true);
        setContentView(R.layout.activity_beneficiary_list_screen);
        GetId();
    }

    private void GetId() {
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
        noData = (TextView) findViewById(R.id.noData);

        getBeneficiaryList();

        if (operator != null && operator.size() > 0) {
            noData.setVisibility(View.GONE);

            mAdapter = new BeneficiaryAdapter(operator, BeneficiaryListScreen.this);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recycler_view.setLayoutManager(mLayoutManager);
            recycler_view.setItemAnimator(new DefaultItemAnimator());
            recycler_view.setAdapter(mAdapter);

        } else {
            noData.setVisibility(View.VISIBLE);
        }

    }

    public void getBeneficiaryList() {
        SharedPreferences prefs = getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String response = prefs.getString(ApplicationConstant.INSTANCE.beneficiaryListPref, "");
        Gson gson = new Gson();
        beneResponse = gson.fromJson(response, RechargeReportResponse.class);
        if (beneResponse.getBenis() != null && beneResponse.getBenis().size() > 0) {
            operator = beneResponse.getBenis();
            recycler_view.setVisibility(View.VISIBLE);
        } else {
            recycler_view.setVisibility(View.GONE);
            UtilMethods.INSTANCE.Error(this, "No Beneficiary found ! please Add Beneficiary");
        }

    }

    @Subscribe
    public void onActivityActivityMessage(ActivityActivityMessage activityFragmentMessage) {
        if (activityFragmentMessage.getMessage().equalsIgnoreCase("beneUpdate")) {
            mAdapter.deleteDone();
            getBeneficiaryList();
            if (operator != null && operator.size() > 0) {
                noData.setVisibility(View.GONE);
                mAdapter = new BeneficiaryAdapter(operator, BeneficiaryListScreen.this);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                recycler_view.setLayoutManager(mLayoutManager);
                recycler_view.setItemAnimator(new DefaultItemAnimator());
                recycler_view.setAdapter(mAdapter);
            } else {
                noData.setVisibility(View.VISIBLE);
            }
        } else if (activityFragmentMessage.getMessage().equalsIgnoreCase("transferDoneDialog")) {
            SharedPreferences prefs = getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
            String senderNumber = prefs.getString(ApplicationConstant.INSTANCE.senderNumberPref, null);

            if (UtilMethods.INSTANCE.isNetworkAvialable(this)) {

//                UtilMethods.INSTANCE.GetSender(this, senderNumber, null);

            } else {
                UtilMethods.INSTANCE.NetworkError(this, getResources().getString(R.string.network_error_title),
                        getResources().getString(R.string.network_error_message));
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

}

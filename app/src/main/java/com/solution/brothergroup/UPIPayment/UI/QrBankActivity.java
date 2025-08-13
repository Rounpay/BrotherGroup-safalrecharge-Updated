package com.solution.brothergroup.UPIPayment.UI;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.solution.brothergroup.Api.Object.Bank;
import com.solution.brothergroup.Api.Request.FundRequestToUsers;
import com.solution.brothergroup.Api.Response.FundreqToResponse;
import com.solution.brothergroup.Api.Response.GetBankAndPaymentModeResponse;
import com.solution.brothergroup.R;
import com.solution.brothergroup.Util.UtilMethods;
import com.solution.brothergroup.usefull.CustomLoader;

import java.util.ArrayList;
import java.util.List;

public class QrBankActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    QrBankAdapter mBankDetailAdapter;
    private CustomLoader loader;
    View banRoleView;
    Spinner bankRole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowInsetsControllerCompat controller = new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        controller.setAppearanceLightStatusBars(true);
        controller.setAppearanceLightNavigationBars(true);
        setContentView(R.layout.activity_qr_bank);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setTitle("Scan & Pay");
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        banRoleView = findViewById(R.id.banRoleView);
        bankRole = findViewById(R.id.bankRole);
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        HitApi();
    }

    private void HitApi() {
        if (UtilMethods.INSTANCE.isNetworkAvialable(this)) {

            loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);
            loader.show();
            loader.setCancelable(false);
            loader.setCanceledOnTouchOutside(false);
            UtilMethods.INSTANCE.FundRequestTo(this, loader, new UtilMethods.ApiCallBack() {
                @Override
                public void onSucess(Object object) {
                    FundreqToResponse mFundreqToResponse = (FundreqToResponse) object;
                    selectRoleData(mFundreqToResponse);
                }
            });
        } else {
            UtilMethods.INSTANCE.dialogOk(this, getResources().getString(R.string.network_error_title),
                    getResources().getString(R.string.network_error_message), 2);
        }
    }


    void selectRoleData(FundreqToResponse fundreqToResponse) {

        final ArrayList<FundRequestToUsers> fundRequestToUsers = fundreqToResponse.getFundRequestToUsers();

        if (fundRequestToUsers.size() == 1) {
            banRoleView.setVisibility(View.GONE);
            GetBank(fundRequestToUsers.get(0).getParentID());
        } else {
            banRoleView.setVisibility(View.VISIBLE);
        }
        ArrayList<String> arrayList = new ArrayList<String>();
        for (int i = 0; i < fundRequestToUsers.size(); i++) {
            arrayList.add(fundRequestToUsers.get(i).getParentName());
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bankRole.setAdapter(arrayAdapter);
        bankRole.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                GetBank(fundRequestToUsers.get(position).getParentID());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void GetBank(final int parentId) {
        try {
            loader.show();
            UtilMethods.INSTANCE.GetBankAndPaymentMode(QrBankActivity.this, parentId + "", loader, new UtilMethods.ApiCallBack() {
                @Override
                public void onSucess(Object object) {
                    GetBankAndPaymentModeResponse getBankAndPaymentModeResponse = (GetBankAndPaymentModeResponse) object;
                    List<Bank> qrBank = new ArrayList<>();
                    for (int i = 0; i < getBankAndPaymentModeResponse.getBanks().size(); i++) {
                        if (getBankAndPaymentModeResponse.getBanks().get(i).getIsqrenable()) {
                            qrBank.add(getBankAndPaymentModeResponse.getBanks().get(i));
                        }
                    }

                    mBankDetailAdapter = new QrBankAdapter(qrBank, QrBankActivity.this, parentId);
                    mRecyclerView.setAdapter(mBankDetailAdapter);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
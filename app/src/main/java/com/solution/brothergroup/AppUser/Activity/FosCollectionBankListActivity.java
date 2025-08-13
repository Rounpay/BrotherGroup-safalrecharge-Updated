package com.solution.brothergroup.AppUser.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.solution.brothergroup.Adapter.BankListScreenAdapter;
import com.solution.brothergroup.Api.Object.BankListObject;
import com.solution.brothergroup.Api.Response.BankListResponse;
import com.solution.brothergroup.Authentication.dto.LoginResponse;
import com.solution.brothergroup.R;
import com.solution.brothergroup.Util.UtilMethods;
import com.solution.brothergroup.usefull.CustomLoader;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FosCollectionBankListActivity extends AppCompatActivity {
    RecyclerView recycler_view;
    View noDataView;
    BankListScreenAdapter mAdapter;
    ArrayList<BankListObject> mBankListObjects = new ArrayList<>();
    ArrayList<BankListObject> mSearchBankListObjects = new ArrayList<>();
    EditText search_all;
    TextView bank_collection;
    private CustomLoader loader;
    private LoginResponse mLoginDataResponse;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowInsetsControllerCompat controller = new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        controller.setAppearanceLightStatusBars(true);
        controller.setAppearanceLightNavigationBars(true);
        setContentView(R.layout.activity_fos_bank_list_screen);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setTitle("Bank List");
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_icon);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);

        mLoginDataResponse = new Gson().fromJson(UtilMethods.INSTANCE.getLoginPref(this), LoginResponse.class);

        search_all = findViewById(R.id.search_all);
        findViewById(R.id.clearIcon).setOnClickListener(v -> search_all.setText(""));


        recycler_view = findViewById(R.id.recycler_view);
        recycler_view.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new BankListScreenAdapter(mSearchBankListObjects, FosCollectionBankListActivity.this);
        recycler_view.setAdapter(mAdapter);

        noDataView = findViewById(R.id.noData);

        BankListResponse mBankListResponse = new Gson().fromJson(UtilMethods.INSTANCE.getFosBankList(this), BankListResponse.class);
         getOperatorList(mBankListResponse);


        search_all.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mSearchBankListObjects.clear();
                String newText = s.toString().trim().toLowerCase();
                if (newText.length() > 0) {
                    for (BankListObject op : mBankListObjects) {
                        if (op.getBankName().toLowerCase().contains(newText)) {
                            mSearchBankListObjects.add(op);
                        }
                    }
                } else {
                    mSearchBankListObjects.addAll(mBankListObjects);
                }
                if (mAdapter != null) {
                    mAdapter.filter(mSearchBankListObjects);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
/*
        findViewById(R.id.retryBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HitApi();
            }
        });*/
    }

    public void getOperatorList(BankListResponse bankListResponse) {
        if (bankListResponse != null && bankListResponse.getBankMasters() != null && bankListResponse.getBankMasters().size() > 0) {
            noDataView.setVisibility(View.GONE);
            mBankListObjects.clear();

            mSearchBankListObjects.clear();
            mBankListObjects.addAll(bankListResponse.getBankMasters());

            mSearchBankListObjects.addAll(mBankListObjects);
            mAdapter.notifyDataSetChanged();
        } else {

                HitApi();

        }

    }

    private void HitApi() {
        if (UtilMethods.INSTANCE.isNetworkAvialable(this)) {
            loader.show();
            noDataView.setVisibility(View.GONE);
            UtilMethods.INSTANCE.GetASCollectBank(this, loader, mLoginDataResponse,
                    new UtilMethods.ApiCallBack() {
                        @Override
                        public void onSucess(Object object) {
                            BankListResponse mBankListResponse = (BankListResponse) object;
                            if (mBankListResponse != null && mBankListResponse.getBankMasters() != null && mBankListResponse.getBankMasters().size() > 0) {
                                getOperatorList((BankListResponse) object);
                            }
                        }

                    });

        } else {
            noDataView.setVisibility(View.GONE);
            UtilMethods.INSTANCE.NetworkError(this);
        }
    }

    public void ItemClick(BankListObject operator) {
        Intent clickIntent = new Intent();
        clickIntent.putExtra("bankName", operator.getBankName());
        clickIntent.putExtra("bankId", operator.getBankID());
        setResult(RESULT_OK, clickIntent);
        finish();

    }

}

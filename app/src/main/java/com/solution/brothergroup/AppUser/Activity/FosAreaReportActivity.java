package com.solution.brothergroup.AppUser.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.google.gson.Gson;
import com.solution.brothergroup.Api.Object.AreaMaster;
import com.solution.brothergroup.Api.Response.AppGetAMResponse;
import com.solution.brothergroup.AppUser.Adapter.FosReportAreaListScreenAdapter;
import com.solution.brothergroup.Authentication.dto.LoginResponse;
import com.solution.brothergroup.R;
import com.solution.brothergroup.Util.UtilMethods;
import com.solution.brothergroup.usefull.CustomLoader;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FosAreaReportActivity extends AppCompatActivity {
    RecyclerView recycler_view_area;
    FosReportAreaListScreenAdapter fosReportAreaListScreenAdapter;

    EditText search_all;
    private CustomLoader loader;

    ArrayList<AreaMaster> mAreaListObjects = new ArrayList<>();
    ArrayList<AreaMaster> mSearchAreamaster = new ArrayList<>();

    private LoginResponse mLoginDataResponse;
    LinearLayoutManager manager;
    private boolean isFromFOS;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fos_area_report);

        isFromFOS = getIntent().getBooleanExtra("ISFromFOS", false);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setTitle("Area List");
        setSupportActionBar(toolbar);


        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_icon);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);

        mLoginDataResponse = new Gson().fromJson(UtilMethods.INSTANCE.getLoginPref(this), LoginResponse.class);


        search_all = findViewById(R.id.search_all);
        findViewById(R.id.clearIcon).setOnClickListener(v -> search_all.setText(""));

        recycler_view_area = findViewById(R.id.recycler_view_area);


        recycler_view_area.setLayoutManager(new LinearLayoutManager(this));
        fosReportAreaListScreenAdapter = new FosReportAreaListScreenAdapter(mSearchAreamaster, FosAreaReportActivity.this);
        recycler_view_area.setAdapter(fosReportAreaListScreenAdapter);

        AppGetAMResponse appGetAMResponse = new Gson().fromJson(UtilMethods.INSTANCE.getAreaListPref(this), AppGetAMResponse.class);
        getOperatorList(appGetAMResponse);


        search_all.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mSearchAreamaster.clear();
                String newText = s.toString().trim().toLowerCase();
                if (newText.length() > 0) {
                    for (AreaMaster op : mAreaListObjects) {
                        if (op.getArea().toLowerCase().contains(newText)) {
                            mSearchAreamaster.add(op);
                        }
                    }
                } else {
                    mSearchAreamaster.addAll(mAreaListObjects);
                }
                if (fosReportAreaListScreenAdapter != null) {
                    fosReportAreaListScreenAdapter.filter(mSearchAreamaster);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    private void getOperatorList(AppGetAMResponse areaListResponse) {

        if (areaListResponse != null && areaListResponse.getAreaMaster() != null && areaListResponse.getAreaMaster().size() > 0) {

            mAreaListObjects.clear();

            mSearchAreamaster.clear();
            mAreaListObjects.addAll(areaListResponse.getAreaMaster());

            mSearchAreamaster.addAll(mAreaListObjects);
            fosReportAreaListScreenAdapter.notifyDataSetChanged();
        } else {


            HitApi();

        }


    }

    private void HitApi() {
        if (UtilMethods.INSTANCE.isNetworkAvialable(this)) {
            loader.show();
            UtilMethods.INSTANCE.GetArealist(this, loader, mLoginDataResponse, new UtilMethods.ApiCallBack() {
                @Override
                public void onSucess(Object object) {
                    AppGetAMResponse mAppGetAMResponse = (AppGetAMResponse) object;
                    if (mAppGetAMResponse != null && mAppGetAMResponse.getAreaMaster() != null && mAppGetAMResponse.getAreaMaster().size() > 0) {
                        getOperatorList(mAppGetAMResponse);
                    }
                }
            });


        } else {

            UtilMethods.INSTANCE.NetworkError(this);
        }
    }


    public void setArea(AreaMaster operator) {
        if (isFromFOS) {
            Intent i = new Intent(this, FOSAccStmtAndCollActivity.class);
            i.putExtra("areaID", operator.getAreaID());
            startActivity(i);
        } else {
            Intent intent = new Intent();
            intent.putExtra("areaID", operator.getAreaID());
            intent.putExtra("area", operator.getArea());
            setResult(RESULT_OK, intent);
            finish();
        }
    }
}

package com.solution.brothergroup.AppUser.Activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.solution.brothergroup.Api.Object.BalanceData;
import com.solution.brothergroup.Api.Request.FosAppUserListRequest;
import com.solution.brothergroup.Api.Response.AppUserListResponse;
import com.solution.brothergroup.Api.Response.BalanceResponse;
import com.solution.brothergroup.Authentication.dto.LoginResponse;
import com.solution.brothergroup.BuildConfig;
import com.solution.brothergroup.Api.Object.UserReport;
import com.solution.brothergroup.AppUser.Adapter.FosUserListAdapter;
import com.solution.brothergroup.R;
import com.solution.brothergroup.Util.ApiClient;
import com.solution.brothergroup.Util.ApplicationConstant;
import com.solution.brothergroup.Util.EndPointInterface;
import com.solution.brothergroup.Util.UtilMethods;
import com.solution.brothergroup.usefull.CustomLoader;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;

public class FosUserListActivity extends AppCompatActivity {
    RecyclerView mRecyclerView;
    String rollId;
    TextView errorMsg;
    EditText searchEt;
    ImageView clearIcon;
    View loader;

    private ArrayList<UserReport> mUserLists = new ArrayList<>();
   FosUserListAdapter mAppUserListAdapter;
    private BalanceData mBalanceData;
    private LoginResponse LoginDataResponse;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fos_user_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("FOS User List");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mRecyclerView = findViewById(R.id.recyclerView);
        loader = findViewById(R.id.loader);
        searchEt = findViewById(R.id.searchEt);
        clearIcon =findViewById(R.id.clearIcon);

        errorMsg = findViewById(R.id.errorMsg);
        String LoginResponse = UtilMethods.INSTANCE.getLoginPref(this);
         LoginDataResponse = new Gson().fromJson(LoginResponse,  LoginResponse.class);

        SharedPreferences myPreferences = getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String balanceResponse = myPreferences.getString(ApplicationConstant.INSTANCE.balancePref, "");
        BalanceResponse balanceCheckResponse = new Gson().fromJson(balanceResponse, BalanceResponse.class);
        mBalanceData= balanceCheckResponse.getBalanceData();
        if(mUserLists!=null)
        {
            errorMsg.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            mAppUserListAdapter = new FosUserListAdapter(this, mUserLists,mBalanceData,
                    LoginDataResponse.isAccountStatement(),new FosUserListAdapter.FundTransferCallBAck() {
                @Override
                public void onSucessFund() {
                    appUserListApi();
                }

                @Override
                public void onSucessEdit() {
                    appUserListApi();
                }
            },new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar),LoginDataResponse);
            mRecyclerView.setAdapter(mAppUserListAdapter);
        }
        else
        {
            mRecyclerView.setVisibility(View.GONE);
            errorMsg.setVisibility(View.VISIBLE);
            errorMsg.setText("Fos User list not found.");
        }


        //rollId = getArguments().getString("Id");
        clearIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchEt.setText("");
            }
        });
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
                    clearIcon.setVisibility(View.VISIBLE);
                } else {
                    clearIcon.setVisibility(View.GONE);
                }

                mAppUserListAdapter.getFilter().filter(s);
            }
        });

        appUserListApi();


    }




    public void appUserListApi() {
        try {
            loader.setVisibility(View.VISIBLE);
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<AppUserListResponse> call = git.FOSRetailerList(new FosAppUserListRequest(1000,"","",rollId, LoginDataResponse.getData().getUserID(), LoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID,
                    UtilMethods.INSTANCE.getIMEI(this),
                    "", BuildConfig.VERSION_NAME, UtilMethods.INSTANCE.getSerialNo(this), LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));
            call.enqueue(new Callback<AppUserListResponse>() {

                @Override
                public void onResponse(Call<AppUserListResponse> call, retrofit2.Response<AppUserListResponse> response) {
//                     //Log.e("Payment response", "hello response : " + new Gson().toJson(response.body()).toString());

                    AppUserListResponse data = response.body();
                   // errorMsg.setVisibility(View.GONE);
                    if (data != null) {
                        if (data.getStatuscode() == 1) {
                            if (data.getFosList().getUserReports() != null && data.getFosList().getUserReports().size() > 0) {
                                errorMsg.setVisibility(View.GONE);
                                mUserLists.clear();
                                mUserLists.addAll(data.getFosList().getUserReports());
                                mAppUserListAdapter.notifyDataSetChanged();
                            } else {
                                errorMsg.setVisibility(View.VISIBLE);
                            }


                        } else if (response.body().getStatuscode() == -1) {
                            UtilMethods.INSTANCE.Error(FosUserListActivity.this, data.getMsg() + "");
                        } else if (response.body().getStatuscode() == 0) {
                            UtilMethods.INSTANCE.Error(FosUserListActivity.this, data.getMsg() + "");
                        } else {
                            UtilMethods.INSTANCE.Error(FosUserListActivity.this, data.getMsg() + "");
                        }

                    } else {
                        UtilMethods.INSTANCE.Error(FosUserListActivity.this, getString(R.string.some_thing_error));
                    }
                    loader.setVisibility(View.GONE);
                }

                @Override
                public void onFailure(Call<AppUserListResponse> call, Throwable t) {
                    //Log.e("response", "error ");
                    loader.setVisibility(View.GONE);
                    try {

                        if (t.getMessage() != null && !t.getMessage().isEmpty()) {

                            if (t.getMessage().contains("No address associated with hostname")) {
                                UtilMethods.INSTANCE.Error(FosUserListActivity.this, getString(R.string.err_msg_network));


                            } else {
                                UtilMethods.INSTANCE.Error(FosUserListActivity.this, t.getMessage());


                            }

                        } else {
                            UtilMethods.INSTANCE.Error(FosUserListActivity.this, getString(R.string.some_thing_error));
                        }
                    } catch (IllegalStateException ise) {
                        loader.setVisibility(View.GONE);
                        UtilMethods.INSTANCE.Error(FosUserListActivity.this, getString(R.string.some_thing_error));
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            loader.setVisibility(View.GONE);
            UtilMethods.INSTANCE.Error(FosUserListActivity.this, getString(R.string.some_thing_error));
        }

    }


}
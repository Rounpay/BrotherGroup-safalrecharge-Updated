 package com.solution.brothergroup.AppUser.Activity;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

import com.solution.brothergroup.Api.Object.BalanceData;
import com.solution.brothergroup.Api.Request.AppUserListRequest;
import com.solution.brothergroup.Api.Request.FundTransferRequest;
import com.solution.brothergroup.Api.Response.AppUserListResponse;
import com.solution.brothergroup.Api.Response.BalanceResponse;
import com.solution.brothergroup.Authentication.ChildRole;
import com.solution.brothergroup.Authentication.dto.LoginResponse;
import com.solution.brothergroup.BuildConfig;
import com.solution.brothergroup.Fragments.Adapter.FragmentViewPagerAdapter;
import com.solution.brothergroup.AppUser.AppUserListFragment;
import com.solution.brothergroup.R;
import com.solution.brothergroup.Util.ApiClient;
import com.solution.brothergroup.Util.ApplicationConstant;
import com.solution.brothergroup.Util.AutoBillingUpdateAppRequest;
import com.solution.brothergroup.Util.AutoBillingUpdateAppResponse;
import com.solution.brothergroup.Util.EndPointInterface;
import com.solution.brothergroup.Util.UtilMethods;
import com.solution.brothergroup.usefull.CustomLoader;

public class AppUserListActivity extends AppCompatActivity {

    TabLayout sliding_tabs;
    ViewPager viewPager;
    FragmentViewPagerAdapter mFragmentViewPagerAdapter;
    List<ChildRole> mchildRolesList = new ArrayList<>();
    CustomLoader loader;
    TextView errorMsg;
    private LoginResponse LoginDataResponse;
    private BalanceData mBalanceData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_user_list);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("App User List");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);
        loader.setCancelable(false);
        loader.setCanceledOnTouchOutside(false);
        errorMsg = findViewById(R.id.errorMsg);
        sliding_tabs = findViewById(R.id.sliding_tabs);
        viewPager = findViewById(R.id.viewpager);
        String LoginResponseStr = UtilMethods.INSTANCE.getLoginPref(this);
        LoginDataResponse = new Gson().fromJson(LoginResponseStr, LoginResponse.class);

        SharedPreferences myPreferences = getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String balanceResponse = myPreferences.getString(ApplicationConstant.INSTANCE.balancePref, "");
        BalanceResponse balanceCheckResponse = new Gson().fromJson(balanceResponse, BalanceResponse.class);
        mBalanceData= balanceCheckResponse.getBalanceData();
        mFragmentViewPagerAdapter = new FragmentViewPagerAdapter(getSupportFragmentManager());
        appUserChildRolesApi();

    }

    private void setDataInUI() {

        mFragmentViewPagerAdapter.removeFragment();

        for (int i = 0; i < mchildRolesList.size(); i++) {

            AppUserListFragment mAppUserListFragment = new AppUserListFragment();
            Bundle arg = new Bundle();
            arg.putString("Id", mchildRolesList.get(i).getId() + "");
            arg.putString("Role", mchildRolesList.get(i).getRole());
            arg.putInt("Ind", mchildRolesList.get(i).getInd());
            arg.putSerializable("BalanceData", mBalanceData);
            arg.putBoolean("isAccountStatement", LoginDataResponse.isAccountStatement());

            mAppUserListFragment.setArguments(arg);

            mFragmentViewPagerAdapter.addFragment(mAppUserListFragment, mchildRolesList.get(i).getRole());

        }
        viewPager.setAdapter(mFragmentViewPagerAdapter);
        viewPager.setOffscreenPageLimit(mchildRolesList.size());
        sliding_tabs.setupWithViewPager(viewPager);

    }


    public void appUserChildRolesApi() {
        try {
            loader.show();
           EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<AppUserListResponse> call = git.AppUserChildRoles(new AppUserListRequest("", LoginDataResponse.getData().getUserID(), LoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID,
                    UtilMethods.INSTANCE.getIMEI(this),
                    "", BuildConfig.VERSION_NAME, UtilMethods.INSTANCE.getSerialNo(this), LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));
            call.enqueue(new Callback<AppUserListResponse>() {

                @Override
                public void onResponse(Call<AppUserListResponse> call, retrofit2.Response<AppUserListResponse> response) {
//                     // Log.e("Payment response", "hello response : " + new Gson().toJson(response.body()).toString());
                    if (loader.isShowing())
                        loader.dismiss();
                    AppUserListResponse data = response.body();
                    if (data != null) {
                        if (data.getStatuscode() == 1) {
                            if (data.getChildRoles() != null && data.getChildRoles().size() > 0) {
                                errorMsg.setVisibility(View.GONE);
                                mchildRolesList.clear();
                                mchildRolesList.addAll(data.getChildRoles());
                            } else {
                                errorMsg.setVisibility(View.VISIBLE);
                                errorMsg.setText("Data not found");
                            }
                            setDataInUI();

                        } else if (response.body().getStatuscode() == -1) {
                            errorMsg.setVisibility(View.VISIBLE);
                            errorMsg.setText(data.getMsg());
                            UtilMethods.INSTANCE.Error(AppUserListActivity.this, data.getMsg() + "");
                        } else if (response.body().getStatuscode() == 0) {
                            errorMsg.setVisibility(View.VISIBLE);
                            errorMsg.setText(data.getMsg());
                            UtilMethods.INSTANCE.Error(AppUserListActivity.this, data.getMsg() + "");
                        } else {
                            errorMsg.setVisibility(View.VISIBLE);
                            errorMsg.setText(data.getMsg());
                            UtilMethods.INSTANCE.Error(AppUserListActivity.this, data.getMsg() + "");
                        }

                    } else {
                        errorMsg.setVisibility(View.VISIBLE);
                        errorMsg.setText(getString(R.string.some_thing_error));
                        UtilMethods.INSTANCE.Error(AppUserListActivity.this, getString(R.string.some_thing_error));
                    }
                }

                @Override
                public void onFailure(Call<AppUserListResponse> call, Throwable t) {

                    try {
                        if (loader.isShowing()) {
                            loader.dismiss();
                        }
                        if (t.getMessage() != null && !t.getMessage().isEmpty()) {

                            if (t.getMessage().contains("No address associated with hostname")) {
                                errorMsg.setVisibility(View.VISIBLE);
                                errorMsg.setText(getString(R.string.err_msg_network));
                                UtilMethods.INSTANCE.Error(AppUserListActivity.this, getString(R.string.err_msg_network));
                            } else {
                                errorMsg.setVisibility(View.VISIBLE);
                                errorMsg.setText(t.getMessage());
                                UtilMethods.INSTANCE.Error(AppUserListActivity.this, t.getMessage());
                            }

                        } else {
                            errorMsg.setVisibility(View.VISIBLE);
                            errorMsg.setText(getString(R.string.some_thing_error));
                            UtilMethods.INSTANCE.Error(AppUserListActivity.this, getString(R.string.some_thing_error));
                        }
                    } catch (IllegalStateException ise) {
                        loader.dismiss();
                        errorMsg.setVisibility(View.VISIBLE);
                        errorMsg.setText(getString(R.string.some_thing_error));
                        UtilMethods.INSTANCE.Error(AppUserListActivity.this, getString(R.string.some_thing_error));
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            errorMsg.setVisibility(View.VISIBLE);
            errorMsg.setText(getString(R.string.some_thing_error));
            UtilMethods.INSTANCE.Error(AppUserListActivity.this, getString(R.string.some_thing_error));
        }
    }

    public void fundTransferApi(boolean isMarkCredit,String securityKey, boolean oType, int uid, String remark, int walletType, String amount,
                                final String userName, final AlertDialog alertDialogFundTransfer,
                                final FundTransferCallBAck mFundTransferCallBAck) {
        try {
            loader.show();
            String LoginResponse = UtilMethods.INSTANCE.getLoginPref(this);
            LoginResponse LoginDataResponse = new Gson().fromJson(LoginResponse, LoginResponse.class);
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<AppUserListResponse> call = git.AppFundTransfer(new FundTransferRequest(isMarkCredit,securityKey, oType, uid, remark, walletType, 0, amount, LoginDataResponse.getData().getUserID(), LoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID,
                    UtilMethods.INSTANCE.getIMEI(this),
                    "", BuildConfig.VERSION_NAME, UtilMethods.INSTANCE.getSerialNo(this), LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));
            call.enqueue(new Callback<AppUserListResponse>() {

                @Override
                public void onResponse(Call<AppUserListResponse> call, retrofit2.Response<AppUserListResponse> response) {

                    if (loader.isShowing())
                        loader.dismiss();
                    AppUserListResponse data = response.body();
                    if (data != null) {
                        if (data.getStatuscode() == 1) {
                            if (mFundTransferCallBAck != null) {
                                mFundTransferCallBAck.onSucess();
                            }
                            alertDialogFundTransfer.dismiss();
                            UtilMethods.INSTANCE.Successful(AppUserListActivity.this, data.getMsg().replace("{User}", userName));
                        } else if (response.body().getStatuscode() == -1) {
                            UtilMethods.INSTANCE.Error(AppUserListActivity.this, data.getMsg().replace("{User}", userName));
                        } else if (response.body().getStatuscode() == 0) {

                            UtilMethods.INSTANCE.Error(AppUserListActivity.this, data.getMsg().replace("{User}", userName));
                        } else {
                            UtilMethods.INSTANCE.Error(AppUserListActivity.this, data.getMsg().replace("{User}", userName));
                        }

                    } else {
                        UtilMethods.INSTANCE.Error(AppUserListActivity.this, getString(R.string.some_thing_error));
                    }
                }

                @Override
                public void onFailure(Call<AppUserListResponse> call, Throwable t) {

                    try {
                        if (loader.isShowing()) {
                            loader.dismiss();
                        }
                        if (t.getMessage() != null && !t.getMessage().isEmpty()) {

                            if (t.getMessage().contains("No address associated with hostname")) {
                                UtilMethods.INSTANCE.NetworkError(AppUserListActivity.this, getString(R.string.network_error_title), getString(R.string.err_msg_network));
                            } else {
                                UtilMethods.INSTANCE.Error(AppUserListActivity.this, t.getMessage());
                            }

                        } else {
                            UtilMethods.INSTANCE.Error(AppUserListActivity.this, getString(R.string.some_thing_error));
                        }
                    } catch (IllegalStateException ise) {
                        loader.dismiss();
                        UtilMethods.INSTANCE.Error(AppUserListActivity.this, getString(R.string.some_thing_error));
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            UtilMethods.INSTANCE.Error(AppUserListActivity.this, getString(R.string.some_thing_error));
        }

    }


    public interface FundTransferCallBAck {
        void onSucess();
    }


    ///////////////////////////////////////
    public void autobillingsubmitAPi(Boolean autoBilling,int uid,String maxBillingCountAB, String balanceForAB, String maxCreditLimitAB, String maxTransferLimitAB,
                                     final AlertDialog alertDialogAutoBilling, final autobillingsubmitAPiCallBAck mautobillingsubmitAPiCallBAck) {
        try {
            loader.show();
            String LoginResponse = UtilMethods.INSTANCE.getLoginPref(this);
            LoginResponse LoginDataResponse = new Gson().fromJson(LoginResponse, LoginResponse.class);
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<AutoBillingUpdateAppResponse> call = git.AutoBillingUpdateApp(new AutoBillingUpdateAppRequest(
                    uid
                    , autoBilling
                    ,maxBillingCountAB
                    ,balanceForAB
                    , false
                    , maxCreditLimitAB
                    , maxTransferLimitAB
                    , LoginDataResponse.getData().getUserID()
                    , LoginDataResponse.getData().getSessionID()
                    , LoginDataResponse.getData().getSession()
                    , ApplicationConstant.INSTANCE.APP_ID
                    , UtilMethods.INSTANCE.getIMEI(this)
                    , UtilMethods.getFCMRegKey(this)
                    ,  BuildConfig.VERSION_NAME
                    , UtilMethods.INSTANCE.getSerialNo(this)
                    , LoginDataResponse.getData().getLoginTypeID()));

                    call.enqueue(new Callback<AutoBillingUpdateAppResponse>() {

                        @Override
                        public void onResponse(Call<AutoBillingUpdateAppResponse> call, retrofit2.Response<AutoBillingUpdateAppResponse> response) {

                            if (loader.isShowing())
                                loader.dismiss();
                            AutoBillingUpdateAppResponse data = response.body();
                            if (data != null) {
                                if (data.getStatuscode() == "1") {
                                    if (mautobillingsubmitAPiCallBAck != null) {
                                        mautobillingsubmitAPiCallBAck.onSucess();
                                    }
                                   alertDialogAutoBilling.dismiss();

                                    Toast.makeText(AppUserListActivity.this, "555", Toast.LENGTH_SHORT).show();

                                   UtilMethods.INSTANCE.Successful(AppUserListActivity.this, data.getMsg());
                                } else if (response.body().getStatuscode() == "-1") {
                                 //   UtilMethods.INSTANCE.Error(AppUserListActivity.this, data.getMsg().replace("{User}", userName));
                                } else if (response.body().getStatuscode() == "0") {

                                  //  UtilMethods.INSTANCE.Error(AppUserListActivity.this, data.getMsg().replace("{User}", userName));
                                } else {
                                 //   UtilMethods.INSTANCE.Error(AppUserListActivity.this, data.getMsg().replace("{User}", userName));
                                }

                            } else {
                                UtilMethods.INSTANCE.Error(AppUserListActivity.this, getString(R.string.some_thing_error));
                            }
                        }

                        @Override
                        public void onFailure(Call<AutoBillingUpdateAppResponse> call, Throwable t) {

                            try {
                                if (loader.isShowing()) {
                                    loader.dismiss();
                                }
                                if (t.getMessage() != null && !t.getMessage().isEmpty()) {

                                    if (t.getMessage().contains("No address associated with hostname")) {
                                        UtilMethods.INSTANCE.NetworkError(AppUserListActivity.this, getString(R.string.network_error_title), getString(R.string.err_msg_network));
                                    } else {
                                        UtilMethods.INSTANCE.Error(AppUserListActivity.this, t.getMessage());
                                    }

                                } else {
                                    UtilMethods.INSTANCE.Error(AppUserListActivity.this, getString(R.string.some_thing_error));
                                }
                            } catch (IllegalStateException ise) {
                                loader.dismiss();
                                UtilMethods.INSTANCE.Error(AppUserListActivity.this, getString(R.string.some_thing_error));
                            }
                        }
                    });

        } catch (Exception e) {
            e.printStackTrace();
            UtilMethods.INSTANCE.Error(AppUserListActivity.this, getString(R.string.some_thing_error));
        }

    }


    public interface autobillingsubmitAPiCallBAck {
        void onSucess();
    }


    ///////////////////////////////////////

}

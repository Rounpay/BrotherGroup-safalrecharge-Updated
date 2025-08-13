package com.solution.brothergroup.DthPlan.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.solution.brothergroup.Authentication.dto.LoginResponse;
import com.solution.brothergroup.BuildConfig;
import com.solution.brothergroup.DthPlan.dto.DthPlanInfoAllData;
import com.solution.brothergroup.DthPlan.dto.DthPlanInfoAllResponse;
import com.solution.brothergroup.DthPlan.dto.DthPlanInfoResponse;
import com.solution.brothergroup.DthPlan.dto.DthPlanLanguageWiseRequest;
import com.solution.brothergroup.DthPlan.dto.DthPlanLanguages;
import com.solution.brothergroup.DthPlan.dto.PlanInfoPlan;
import com.solution.brothergroup.DthPlan.dto.PlanInfoRPData;
import com.solution.brothergroup.DthPlan.dto.PlanInfoRPWithPackage;
import com.solution.brothergroup.DthPlan.dto.PlanInfoRecords;
import com.solution.brothergroup.DthPlan.dto.PlanRPResponse;
import com.solution.brothergroup.R;
import com.solution.brothergroup.ROffer.dto.ROfferRequest;
import com.solution.brothergroup.Util.ApiClient;
import com.solution.brothergroup.Util.ApplicationConstant;
import com.solution.brothergroup.Util.EndPointInterface;
import com.solution.brothergroup.Util.UtilMethods;
import com.solution.brothergroup.usefull.CustomLoader;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import retrofit2.Call;
import retrofit2.Callback;

public class DthPlanInfoNewActivity extends AppCompatActivity {


    ArrayList<String> rechargeType = new ArrayList<>();
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Toolbar toolbar;
    private CustomLoader loader;
    TextView  tv_note;
    private ViewPagerAdapter adapter;
    String intentOpId;
    boolean intentFromLanguage;
    private String intentLanguage;
    private String intentNumber;
    private String intentDeviceId;
    private String intentDeviceSerialNum;
    private int INTENT_VIEW_PLAN = 634;
    private boolean isPlanServiceUpdated;
    private LoginResponse mLoginDataResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowInsetsControllerCompat controller = new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        controller.setAppearanceLightStatusBars(true);
        controller.setAppearanceLightNavigationBars(true);
        setContentView(R.layout.activity_dth_plan_info_new);
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);
        String LoginResponse = UtilMethods.INSTANCE.getLoginPref(this);
        mLoginDataResponse = new Gson().fromJson(LoginResponse, LoginResponse.class);
        intentOpId = getIntent().getStringExtra("OperatorSelectedId");
        intentNumber = getIntent().getStringExtra("Number");
        intentLanguage = getIntent().getStringExtra("Language");
        intentDeviceId =  UtilMethods.INSTANCE.getIMEI(this);
        intentDeviceSerialNum =  UtilMethods.INSTANCE.getSerialNo(this);
        isPlanServiceUpdated = getIntent().getBooleanExtra("IsPlanServiceUpdated", false);
        intentFromLanguage = getIntent().getBooleanExtra("IsFromLanguage", false);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Plans" + (intentFromLanguage ? " (" + intentLanguage + ")" : ""));
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_icon);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
       
        tv_note = findViewById(R.id.tv_note);
      
        tabLayout = findViewById(R.id.tabs);
        tabLayout.setVisibility(View.GONE);
        viewPager = findViewById(R.id.viewpager);

        adapter = new ViewPagerAdapter(getSupportFragmentManager());

        HitApi();


    }


    public void HitApi() {
        if (UtilMethods.INSTANCE.isNetworkAvialable(this)) {
            try {
                loader.show();
                EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);

                if (isPlanServiceUpdated) {
                    Call<DthPlanInfoAllResponse> call;
                    if (intentFromLanguage) {

                        call = git.GetDTHPlanByLang(new DthPlanLanguageWiseRequest(intentOpId,
                                intentLanguage, intentNumber,
                                ApplicationConstant.INSTANCE.APP_ID,
                                intentDeviceId,
                                "", BuildConfig.VERSION_NAME,
                                intentDeviceSerialNum,mLoginDataResponse.getData().getUserID()
                                ,mLoginDataResponse.getData().getLoginTypeID(),
                                mLoginDataResponse.getData().getSessionID(),
                                mLoginDataResponse.getData().getSession()));


                    } else {

                        call = git.GetDTHSimplePlanInfo(new ROfferRequest(intentOpId,
                                intentNumber,
                                ApplicationConstant.INSTANCE.APP_ID,
                                intentDeviceId,
                                "", BuildConfig.VERSION_NAME,
                                intentDeviceSerialNum,mLoginDataResponse.getData().getUserID()
                                ,mLoginDataResponse.getData().getLoginTypeID(),
                                mLoginDataResponse.getData().getSessionID(),
                                mLoginDataResponse.getData().getSession()));


                    }

                    call.enqueue(new Callback<DthPlanInfoAllResponse>() {
                        @Override
                        public void onResponse(Call<DthPlanInfoAllResponse> call, final retrofit2.Response<DthPlanInfoAllResponse> response) {

                            try {
                                if (loader != null && loader.isShowing()) {
                                    loader.dismiss();
                                }
                                if (response.isSuccessful()) {
                                    if (response.body() != null) {
                                        if (response.body().getStatuscode() == 1) {
                                            
                                            if (response.body().getData() != null && response.body().getData().size() > 0) {

                                                dataParseAll(response.body().getData());
                                            } else {
                                                
                                                UtilMethods.INSTANCE.Error(DthPlanInfoNewActivity.this, "Plan not found");
                                            }
                                        } else {
                                            UtilMethods.INSTANCE.Error(DthPlanInfoNewActivity.this, response.body().getMsg() + "");
                                        }


                                    } else {
                                        
                                        UtilMethods.INSTANCE.Error(DthPlanInfoNewActivity.this, "Something went wrong, try after some time.");
                                    }
                                } else {
                                    
                                    UtilMethods.INSTANCE.apiErrorHandle(DthPlanInfoNewActivity.this, response.code(), response.message());
                                }
                            } catch (Exception e) {
                                if (loader != null && loader.isShowing()) {
                                    loader.dismiss();
                                }
                                
                            }

                        }

                        @Override
                        public void onFailure(Call<DthPlanInfoAllResponse> call, Throwable t) {

                            try {
                                if (loader != null && loader.isShowing()) {
                                    loader.dismiss();
                                }

                                if (t instanceof UnknownHostException || t instanceof IOException) {
                                    
                                    UtilMethods.INSTANCE.NetworkError(DthPlanInfoNewActivity.this);
                                } else if (t instanceof SocketTimeoutException || t instanceof TimeoutException) {
                                    
                                    UtilMethods.INSTANCE.ErrorWithTitle(DthPlanInfoNewActivity.this, "TIME OUT ERROR", t.getMessage() + "");
                                } else {
                                    
                                    if (t.getMessage() != null && !t.getMessage().isEmpty()) {
                                        UtilMethods.INSTANCE.ErrorWithTitle(DthPlanInfoNewActivity.this, "FATAL ERROR", t.getMessage() + "");
                                    } else {
                                        UtilMethods.INSTANCE.Error(DthPlanInfoNewActivity.this, getString(R.string.some_thing_error));
                                    }
                                }
                            } catch (IllegalStateException ise) {
                                if (loader != null && loader.isShowing()) {
                                    loader.dismiss();
                                }
                                UtilMethods.INSTANCE.Error(DthPlanInfoNewActivity.this, ise.getMessage());
                                
                            }

                        }
                    });
                } else {

                    Call<DthPlanInfoResponse> call;
                    if (intentFromLanguage) {

                        call = git.GetDTHPlanListByLanguage(new DthPlanLanguageWiseRequest(intentOpId,
                                intentLanguage, intentNumber,
                                ApplicationConstant.INSTANCE.APP_ID,
                                intentDeviceId,
                                "", BuildConfig.VERSION_NAME,
                                intentDeviceSerialNum,mLoginDataResponse.getData().getUserID()
                                ,mLoginDataResponse.getData().getLoginTypeID(),
                                mLoginDataResponse.getData().getSessionID(),
                                mLoginDataResponse.getData().getSession()));


                    } else {

                        call = git.DTHSimplePlanInfo(new ROfferRequest(intentOpId,
                                intentNumber,
                                ApplicationConstant.INSTANCE.APP_ID,
                                intentDeviceId,
                                "", BuildConfig.VERSION_NAME,
                                intentDeviceSerialNum,mLoginDataResponse.getData().getUserID()
                                ,mLoginDataResponse.getData().getLoginTypeID(),
                                mLoginDataResponse.getData().getSessionID(),
                                mLoginDataResponse.getData().getSession()));


                    }

                    call.enqueue(new Callback<DthPlanInfoResponse>() {
                        @Override
                        public void onResponse(Call<DthPlanInfoResponse> call, final retrofit2.Response<DthPlanInfoResponse> response) {

                            try {
                                if (loader != null && loader.isShowing()) {
                                    loader.dismiss();
                                }
                                if (response.isSuccessful()) {
                                    if (response.body() != null) {
                                        if (response.body().getStatuscode() == 1) {
                                            
                                            if (response.body().getData() != null && response.body().getData().getRecords() != null) {

                                                dataParse(response.body().getData().getRecords());
                                            } else if (response.body().getDataRP() != null && response.body().getDataRP().getResponse() != null && response.body().getDataRP().getResponse().size() > 0) {

                                                dataParseRP(response.body().getDataRP());
                                            } else if (response.body().getDataRPDTHWithPackage() != null && response.body().getDataRPDTHWithPackage() != null) {

                                                dataParseRPNew(response.body().getDataRPDTHWithPackage());
                                            } else if (response.body().getDataPA() != null && response.body().getDataPA().getRecords() != null) {

                                                dataParsePA(response.body().getDataPA().getRecords());
                                            } else {
                                                
                                                UtilMethods.INSTANCE.Error(DthPlanInfoNewActivity.this, "Plan not found");
                                            }
                                        } else {
                                            UtilMethods.INSTANCE.Error(DthPlanInfoNewActivity.this, response.body().getMsg() + "");
                                        }


                                    } else {
                                        
                                        UtilMethods.INSTANCE.Error(DthPlanInfoNewActivity.this, "Something went wrong, try after some time.");
                                    }
                                } else {
                                    
                                    UtilMethods.INSTANCE.apiErrorHandle(DthPlanInfoNewActivity.this, response.code(), response.message());
                                }
                            } catch (Exception e) {
                                if (loader != null && loader.isShowing()) {
                                    loader.dismiss();
                                }
                                
                            }

                        }

                        @Override
                        public void onFailure(Call<DthPlanInfoResponse> call, Throwable t) {

                            try {
                                if (loader != null && loader.isShowing()) {
                                    loader.dismiss();
                                }

                                if (t instanceof UnknownHostException || t instanceof IOException) {
                                    
                                    UtilMethods.INSTANCE.NetworkError(DthPlanInfoNewActivity.this);
                                } else if (t instanceof SocketTimeoutException || t instanceof TimeoutException) {
                                    
                                    UtilMethods.INSTANCE.ErrorWithTitle(DthPlanInfoNewActivity.this, "TIME OUT ERROR", t.getMessage() + "");
                                } else {
                                    
                                    if (t.getMessage() != null && !t.getMessage().isEmpty()) {
                                        UtilMethods.INSTANCE.ErrorWithTitle(DthPlanInfoNewActivity.this, "FATAL ERROR", t.getMessage() + "");
                                    } else {
                                        UtilMethods.INSTANCE.Error(DthPlanInfoNewActivity.this, getString(R.string.some_thing_error));
                                    }
                                }
                            } catch (IllegalStateException ise) {
                                if (loader != null && loader.isShowing()) {
                                    loader.dismiss();
                                }
                                UtilMethods.INSTANCE.Error(DthPlanInfoNewActivity.this, ise.getMessage());
                                
                            }

                        }
                    });
                }


            } catch (Exception e) {
                if (loader != null && loader.isShowing()) {
                    loader.dismiss();
                }
                e.printStackTrace();
                
            }
        } else {
            if (loader != null && loader.isShowing()) {
                loader.dismiss();
            }
            
            UtilMethods.INSTANCE.NetworkError(this);
        }
    }


    

    private void dataParse(PlanInfoRecords responsePlan) {
        
        if (responsePlan != null) {

            if (responsePlan.getPlan() != null && responsePlan.getPlan().size() > 0) {
                Bundle arg = new Bundle();
                arg.putSerializable("response", responsePlan.getPlan());
                arg.putSerializable("OpID", intentOpId);
                arg.putSerializable("IsPlanServiceUpdated", isPlanServiceUpdated);
                DthViewPlanNewFragment mFragment = new DthViewPlanNewFragment();
                mFragment.setArguments(arg);
                adapter.addFragment(mFragment, "Plan");
            }
            if (responsePlan.getAddOnPack() != null && responsePlan.getAddOnPack().size() > 0) {
                Bundle arg = new Bundle();
                arg.putSerializable("response", responsePlan.getAddOnPack());
                arg.putSerializable("OpID", intentOpId);
                arg.putSerializable("IsPlanServiceUpdated", isPlanServiceUpdated);
                DthViewPlanNewFragment mFragment = new DthViewPlanNewFragment();
                mFragment.setArguments(arg);
                adapter.addFragment(mFragment, "Add On Pack");
            }

            if (adapter.getCount() > 0) {
                viewPager.setAdapter(adapter);
                viewPager.setOffscreenPageLimit(adapter.getCount());
                tv_note.setVisibility(View.VISIBLE);
                if (adapter.getCount() > 1) {
                    tabLayout.setVisibility(View.VISIBLE);
                }
                tabLayout.setupWithViewPager(viewPager);
            } else {
                
                UtilMethods.INSTANCE.Error(DthPlanInfoNewActivity.this, "Plan not found");
            }
        } else {
            
            UtilMethods.INSTANCE.Error(DthPlanInfoNewActivity.this, "Plan not found");
        }
    }

    private void dataParsePA(PlanInfoRecords responsePAPlan) {
        
        if (responsePAPlan != null) {

            if (responsePAPlan.getPlan() != null && responsePAPlan.getPlan().size() > 0) {
                Bundle arg = new Bundle();
                arg.putSerializable("responsePA", responsePAPlan.getPlan());
                arg.putSerializable("OpID", intentOpId);
                arg.putSerializable("IsPlanServiceUpdated", isPlanServiceUpdated);
                DthViewPlanNewFragment mFragment = new DthViewPlanNewFragment();
                mFragment.setArguments(arg);
                adapter.addFragment(mFragment, "Plan");
            }
            if (responsePAPlan.getAddOnPack() != null && responsePAPlan.getAddOnPack().size() > 0) {
                Bundle arg = new Bundle();
                arg.putSerializable("responsePA", responsePAPlan.getAddOnPack());
                arg.putSerializable("OpID", intentOpId);
                arg.putSerializable("IsPlanServiceUpdated", isPlanServiceUpdated);
                DthViewPlanNewFragment mFragment = new DthViewPlanNewFragment();
                mFragment.setArguments(arg);
                adapter.addFragment(mFragment, "Add On Pack");
            }

            if (adapter.getCount() > 0) {
                viewPager.setAdapter(adapter);
                viewPager.setOffscreenPageLimit(adapter.getCount());
                tv_note.setVisibility(View.VISIBLE);
                if (adapter.getCount() > 1) {
                    tabLayout.setVisibility(View.VISIBLE);
                }
                tabLayout.setupWithViewPager(viewPager);
            } else {
                
                UtilMethods.INSTANCE.Error(DthPlanInfoNewActivity.this, "Plan not found");
            }
        } else {
            
            UtilMethods.INSTANCE.Error(DthPlanInfoNewActivity.this, "Plan not found");
        }
    }

    private void dataParseAll(ArrayList<DthPlanInfoAllData> dthPlanInfoAllData) {
        
        if (dthPlanInfoAllData != null) {

            for (DthPlanInfoAllData mDthPlanInfoAllData : dthPlanInfoAllData) {
                if (mDthPlanInfoAllData.getpType() != null && !mDthPlanInfoAllData.getpType().isEmpty()
                        && mDthPlanInfoAllData.getpDetials() != null && mDthPlanInfoAllData.getpDetials().size() > 0) {
                    Bundle arg = new Bundle();
                    arg.putSerializable("response", mDthPlanInfoAllData.getpDetials());
                    arg.putSerializable("OpID", intentOpId);
                    arg.putSerializable("IsPlanServiceUpdated", isPlanServiceUpdated);
                    DthViewPlanNewFragment mFragment = new DthViewPlanNewFragment();
                    mFragment.setArguments(arg);
                    adapter.addFragment(mFragment, mDthPlanInfoAllData.getpType());
                }
            }


            if (adapter.getCount() > 0) {
                viewPager.setAdapter(adapter);
                viewPager.setOffscreenPageLimit(adapter.getCount());
                tv_note.setVisibility(View.VISIBLE);
                if (adapter.getCount() > 1) {
                    tabLayout.setVisibility(View.VISIBLE);
                }
                tabLayout.setupWithViewPager(viewPager);
            } else {
                
                UtilMethods.INSTANCE.Error(DthPlanInfoNewActivity.this, "Plan not found");
            }
        } else {
            
            UtilMethods.INSTANCE.Error(DthPlanInfoNewActivity.this, "Plan not found");
        }
    }


    private void dataParseRP(PlanInfoRPData responseRPPlan) {
        
        if (responseRPPlan != null) {

            if (responseRPPlan.getResponse() != null && responseRPPlan.getResponse().size() > 0) {
                HashMap<String, ArrayList<PlanRPResponse>> mapList = new HashMap<>();
                String planType = "";
                for (PlanRPResponse item : responseRPPlan.getResponse()) {
                    planType = item.getRechargeType().trim();
                    if (mapList.containsKey(planType)) {
                        mapList.get(planType).add(item);
                    } else {
                        ArrayList<PlanRPResponse> mList = new ArrayList<>();
                        mList.add(item);
                        mapList.put(planType, mList);
                    }

                }

                for (Map.Entry<String, ArrayList<PlanRPResponse>> entry : mapList.entrySet()) {

                    Bundle arg = new Bundle();
                    arg.putSerializable("responseRP", entry.getValue());
                    arg.putSerializable("OpID", intentOpId);
                    arg.putSerializable("IsPlanServiceUpdated", isPlanServiceUpdated);
                    DthViewPlanNewFragment mFragment = new DthViewPlanNewFragment();
                    mFragment.setArguments(arg);
                    adapter.addFragment(mFragment, entry.getKey());
                }

            }

            if (adapter.getCount() > 0) {
                viewPager.setAdapter(adapter);
                viewPager.setOffscreenPageLimit(adapter.getCount());
                tv_note.setVisibility(View.VISIBLE);
                if (adapter.getCount() > 1) {
                    tabLayout.setVisibility(View.VISIBLE);
                }
                tabLayout.setupWithViewPager(viewPager);
            } else {
                
                UtilMethods.INSTANCE.Error(DthPlanInfoNewActivity.this, "Plan not found");
            }
        } else {
            
            UtilMethods.INSTANCE.Error(DthPlanInfoNewActivity.this, "Plan not found");
        }
    }

    private void dataParseRPNew(PlanInfoRPWithPackage responseRPNewPlan) {
        
        if (responseRPNewPlan != null) {
            if (responseRPNewPlan.getPackageList() != null && responseRPNewPlan.getPackageList().size() > 0) {
                Bundle arg = new Bundle();
                arg.putSerializable("responseRPNew", responseRPNewPlan.getPackageList());
                arg.putSerializable("OpID", intentOpId);
                arg.putSerializable("IsPlanServiceUpdated", isPlanServiceUpdated);
                DthViewPlanNewFragment mFragment = new DthViewPlanNewFragment();
                mFragment.setArguments(arg);
                adapter.addFragment(mFragment, "Top Plan");
            }

            if (responseRPNewPlan.getLanguages() != null && responseRPNewPlan.getLanguages().size() > 0) {
                Bundle arg = new Bundle();
                arg.putSerializable("responseRPNewLanguages", responseRPNewPlan.getLanguages());
                arg.putSerializable("OpID", intentOpId);
                arg.putSerializable("IsPlanServiceUpdated", isPlanServiceUpdated);
                DthViewPlanNewFragment mFragment = new DthViewPlanNewFragment();
                mFragment.setArguments(arg);
                adapter.addFragment(mFragment, "Languages");
            }

            if (responseRPNewPlan.getResponse() != null && responseRPNewPlan.getResponse().size() > 0) {
                HashMap<String, ArrayList<PlanRPResponse>> mapList = new HashMap<>();
                String planType = "";
                for (PlanRPResponse item : responseRPNewPlan.getResponse()) {
                    planType = item.getRechargeType().trim();
                    if (mapList.containsKey(planType)) {
                        mapList.get(planType).add(item);
                    } else {
                        ArrayList<PlanRPResponse> mList = new ArrayList<>();
                        mList.add(item);
                        mapList.put(planType, mList);
                    }

                }

                for (Map.Entry<String, ArrayList<PlanRPResponse>> entry : mapList.entrySet()) {

                    Bundle arg = new Bundle();
                    arg.putSerializable("responseRPNew", entry.getValue());
                    arg.putSerializable("OpID", intentOpId);
                    arg.putSerializable("IsPlanServiceUpdated", isPlanServiceUpdated);
                    DthViewPlanNewFragment mFragment = new DthViewPlanNewFragment();
                    mFragment.setArguments(arg);
                    adapter.addFragment(mFragment, entry.getKey());
                }

            }


            if (adapter.getCount() > 0) {
                viewPager.setAdapter(adapter);
                viewPager.setOffscreenPageLimit(adapter.getCount());
                tv_note.setVisibility(View.VISIBLE);
                if (adapter.getCount() > 1) {
                    tabLayout.setVisibility(View.VISIBLE);
                }
                tabLayout.setupWithViewPager(viewPager);
            } else {
                
                UtilMethods.INSTANCE.Error(DthPlanInfoNewActivity.this, "Plan not found");
            }
        } else {
            
            UtilMethods.INSTANCE.Error(DthPlanInfoNewActivity.this, "Plan not found");
        }
    }


   /* private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        for (int i = 0; i < rechargeType.size(); i++) {
            DthViewPlanNewFragment mFragment = new DthViewPlanNewFragment();

            Bundle arg = new Bundle();
            arg.putString("type", rechargeType.get(i));
            arg.putSerializable("response", responsePlan);
            arg.putSerializable("OpID", intentOpId);
            arg.putSerializable("responseRP", responseRPPlan);
            arg.putSerializable("responseRPNew", responseRPNewPlan);
            arg.putSerializable("responsePA", responsePAPlan);
            mFragment.setArguments(arg);
            adapter.addFragment(mFragment, "" + rechargeType.get(i));
        }
        viewPager.setAdapter(adapter);
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.browseplan_menu, menu);
        final MenuItem settingsItem = menu.findItem(R.id.operatorIcon);
        this.invalidateOptionsMenu();
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        return super.onOptionsItemSelected(item);
    }

    public void ItemClick(String amount, String desc) {
        Intent clickIntent = new Intent();
        clickIntent.putExtra("Amount", UtilMethods.INSTANCE.formatedAmount(amount));
        clickIntent.putExtra("desc", desc);
        setResult(RESULT_OK, clickIntent);
        finish();
    }

    public void languageClick(DthPlanLanguages operator) {
        Intent planIntent = new Intent(this, DthPlanInfoNewActivity.class);
        planIntent.putExtra("OperatorSelectedId", intentOpId + "");
        planIntent.putExtra("Number", intentNumber);
        planIntent.putExtra("Language", operator.getLanguage() + "");
        planIntent.putExtra("IsFromLanguage", true);
        planIntent.putExtra("DeviceId", intentDeviceId);
        planIntent.putExtra("IsPlanServiceUpdated", isPlanServiceUpdated);
        planIntent.putExtra("DeviceSerialNum", intentDeviceSerialNum);
        startActivityForResult(planIntent, INTENT_VIEW_PLAN);
    }

    public void languageClick(PlanInfoPlan operator) {
        Intent planIntent = new Intent(this, DthPlanInfoNewActivity.class);
        planIntent.putExtra("OperatorSelectedId", intentOpId + "");
        planIntent.putExtra("Number", intentNumber);
        planIntent.putExtra("Language", operator.getpLangauge() + "");
        planIntent.putExtra("IsFromLanguage", true);
        planIntent.putExtra("DeviceId", intentDeviceId);
        planIntent.putExtra("IsPlanServiceUpdated", isPlanServiceUpdated);
        planIntent.putExtra("DeviceSerialNum", intentDeviceSerialNum);
        startActivityForResult(planIntent, INTENT_VIEW_PLAN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == INTENT_VIEW_PLAN && resultCode == RESULT_OK) {
            if (data != null) {
                ItemClick(data.getStringExtra("Amount"), data.getStringExtra("desc"));
            } else {
                Toast.makeText(this, getString(R.string.some_thing_error), Toast.LENGTH_SHORT).show();
            }
        }
    }
}

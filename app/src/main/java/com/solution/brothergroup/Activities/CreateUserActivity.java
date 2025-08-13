package com.solution.brothergroup.Activities;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.WindowInsetsControllerCompat;

import com.google.gson.Gson;
import com.solution.brothergroup.Api.Object.Role;
import com.solution.brothergroup.Api.Object.Slabs;
import com.solution.brothergroup.Api.Object.UserInfo;
import com.solution.brothergroup.Api.Request.AppUserReffDetailRequest;
import com.solution.brothergroup.Api.Request.AppUserRegisterRequest;
import com.solution.brothergroup.Api.Request.UserCreate;
import com.solution.brothergroup.Api.Response.AppUserReffDetailResponse;
import com.solution.brothergroup.Api.Response.BasicResponse;

import com.solution.brothergroup.Authentication.dto.LoginResponse;
import com.solution.brothergroup.BuildConfig;
import com.solution.brothergroup.R;
import com.solution.brothergroup.Util.ApiClient;
import com.solution.brothergroup.Util.ApplicationConstant;
import com.solution.brothergroup.Util.EndPointInterface;
import com.solution.brothergroup.Util.UtilMethods;
import com.solution.brothergroup.usefull.CustomLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;

public class CreateUserActivity extends AppCompatActivity {

    View pinPassView;
    EditText pinPassEt;
    LoginResponse LoginDataResponse;
    CustomLoader loader;
    ArrayList<String> chooseRoleStrArrayList = new ArrayList<String>();
    ArrayList<String> chooseFosStrArrayList = new ArrayList<String>();
    Map<String, Integer> roleIDMap=new HashMap<>();
    ArrayList<Role> chooseRoleArrayList = new ArrayList<>();
    ArrayList<String> chooseSlabStrArrayList = new ArrayList<String>();
    ArrayList<Slabs> chooseSlabArrayList = new ArrayList<>();
    ArrayAdapter<String> arrayAdapterRole, arrayAdapterSlab;
    int selectedRollId, selectedSlabId;
    String currentRefferalId = "";
    AppUserReffDetailResponse mAppUserReffDetailResponse;
    private EditText referral;
    private AppCompatTextView referralDetailTv;
    private Spinner chooseRoleSpinner;
    private LinearLayout slabView;
    private AppCompatTextView realApiText;
    private LinearLayout realApiSwitchView;
    private Spinner chooseSlabSpinner;
    private SwitchCompat realApiSwitch;
    private LinearLayout websiteView;
    private AppCompatTextView websiteText;
    private LinearLayout websiteSwitchView;
    private EditText website;
    private SwitchCompat websiteSwitch;
    private EditText name;
    private EditText outletName;
    private EditText emailId;
    private EditText mobileNo;
    private EditText pincode;
    private EditText commRate;
    private EditText address;
    private LinearLayout gstSwitchView;
    private SwitchCompat gstSwitch;
    private AppCompatTextView gstText;
    private LinearLayout tdsSwitchView;
    private SwitchCompat tdsSwitch;
    private AppCompatTextView tdsText;
    private Button submitBtn;
    private String keyFor="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowInsetsControllerCompat controller = new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        controller.setAppearanceLightStatusBars(true);
        controller.setAppearanceLightNavigationBars(true);
        setContentView(R.layout.activity_create_user);
        keyFor=getIntent().getExtras().getString("KeyFor");
        findViews();
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);
        loader.setCancelable(false);
        String LoginResponse = UtilMethods.INSTANCE.getLoginPref(this);
        LoginDataResponse = new Gson().fromJson(LoginResponse, LoginResponse.class);

        chooseRoleSpinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (currentRefferalId != null && !currentRefferalId.equalsIgnoreCase(referral.getText().toString())) {
                        AppUserReffDetailApi(referral.getText().toString());
                    }
                }
                return false;
            }
        });

        chooseSlabSpinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (currentRefferalId != null && !currentRefferalId.equalsIgnoreCase(referral.getText().toString())) {
                        AppUserReffDetailApi(referral.getText().toString());
                    }
                }
                return false;
            }
        });
        if(keyFor!=null && !keyFor.isEmpty() && keyFor.equalsIgnoreCase("User"))
        {
            arrayAdapterRole = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, chooseRoleStrArrayList);
        }
        else if(keyFor!=null && !keyFor.isEmpty() && keyFor.equalsIgnoreCase("FOS"))
        {
            arrayAdapterRole = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, chooseFosStrArrayList);
        }
       // arrayAdapterRole = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, chooseRoleStrArrayList);
        arrayAdapterRole.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        chooseRoleSpinner.setAdapter(arrayAdapterRole);
        chooseRoleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //selectedRollId = chooseRoleArrayList.get(position).getId();
                selectedRollId=roleIDMap.get(chooseRoleSpinner.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        arrayAdapterSlab = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, chooseSlabStrArrayList);
        arrayAdapterSlab.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        chooseSlabSpinner.setAdapter(arrayAdapterSlab);
        chooseSlabSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedSlabId = chooseSlabArrayList.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        referral.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (currentRefferalId != null && !currentRefferalId.equalsIgnoreCase(referral.getText().toString())) {
                    AppUserReffDetailApi(referral.getText().toString());
                }
            }
        });

        websiteSwitchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                websiteSwitch.setChecked(websiteSwitch.isChecked() ? false : true);
            }
        });

        realApiSwitchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                realApiSwitch.setChecked(realApiSwitch.isChecked() ? false : true);
            }
        });

        gstSwitchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gstSwitch.setChecked(gstSwitch.isChecked() ? false : true);
            }
        });

        tdsSwitchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tdsSwitch.setChecked(tdsSwitch.isChecked() ? false : true);
            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitData();
            }
        });
        referral.setText(LoginDataResponse.getData().getUserID());
        referral.setSelection(referral.getText().length());
        AppUserReffDetailApi(LoginDataResponse.getData().getUserID());
    }

    private void findViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Create "+keyFor);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        pinPassView = findViewById(R.id.pinPassView);
        pinPassEt = findViewById(R.id.pinPassEt);
        if (UtilMethods.INSTANCE.getDoubleFactorPref(this)) {
            pinPassView.setVisibility(View.VISIBLE);
        } else {
            pinPassView.setVisibility(View.GONE);
        }
        referral = (EditText) findViewById(R.id.referral);
        referralDetailTv = (AppCompatTextView) findViewById(R.id.referralDetailTv);
        chooseRoleSpinner = (Spinner) findViewById(R.id.chooseRoleSpinner);
        slabView = (LinearLayout) findViewById(R.id.slabView);
        realApiText = (AppCompatTextView) findViewById(R.id.realApiText);
        realApiSwitchView = (LinearLayout) findViewById(R.id.realApiSwitchView);
        chooseSlabSpinner = (Spinner) findViewById(R.id.chooseSlabSpinner);
        realApiSwitch = (SwitchCompat) findViewById(R.id.realApiSwitch);
        websiteView = (LinearLayout) findViewById(R.id.websiteView);
        websiteText = (AppCompatTextView) findViewById(R.id.websiteText);
        websiteSwitchView = (LinearLayout) findViewById(R.id.websiteSwitchView);
        website = (EditText) findViewById(R.id.website);
        websiteSwitch = (SwitchCompat) findViewById(R.id.websiteSwitch);
        name = (EditText) findViewById(R.id.name);
        outletName = (EditText) findViewById(R.id.outletName);
        emailId = (EditText) findViewById(R.id.emailId);
        mobileNo = (EditText) findViewById(R.id.mobileNo);
        pincode = (EditText) findViewById(R.id.pincode);
        commRate = (EditText) findViewById(R.id.commRate);
        address = (EditText) findViewById(R.id.address);
        gstSwitchView = (LinearLayout) findViewById(R.id.gstSwitchView);
        gstSwitch = (SwitchCompat) findViewById(R.id.gstSwitch);
        gstText = (AppCompatTextView) findViewById(R.id.gstText);
        tdsSwitchView = (LinearLayout) findViewById(R.id.tdsSwitchView);
        tdsSwitch = (SwitchCompat) findViewById(R.id.tdsSwitch);
        tdsText = (AppCompatTextView) findViewById(R.id.tdsText);
        submitBtn = (Button) findViewById(R.id.submitBtn);

    }


    void submitData() {
        if (mAppUserReffDetailResponse != null && !mAppUserReffDetailResponse.getUserRegModel().getUserInfo().getAdminDefined() && selectedSlabId == 0) {
            UtilMethods.INSTANCE.Error(com.solution.brothergroup.Activities.CreateUserActivity.this, "Please choose a slab");
            return;
        } else if (mAppUserReffDetailResponse != null && !mAppUserReffDetailResponse.getUserRegModel().getUserInfo().getAdminDefined() && websiteSwitch.isChecked() && website.getText().toString().isEmpty()) {
            website.setError(getString(R.string.err_empty_field));
            website.requestFocus();
            return;
        } else if (name.getText().toString().isEmpty()) {
            name.setError(getString(R.string.err_empty_field));
            name.requestFocus();
            return;
        } else if (outletName.getText().toString().isEmpty()) {
            outletName.setError(getString(R.string.err_empty_field));
            outletName.requestFocus();
            return;
        } else if (emailId.getText().toString().isEmpty()) {
            emailId.setError(getString(R.string.err_empty_field));
            emailId.requestFocus();
            return;
        } else if (!emailId.getText().toString().contains(".")) {
            emailId.setError(getString(R.string.err_msg_email));
            emailId.requestFocus();
            return;
        } else if (!emailId.getText().toString().contains("@")) {
            emailId.setError(getString(R.string.err_msg_email));
            emailId.requestFocus();
            return;
        } else if (mobileNo.getText().toString().isEmpty()) {
            mobileNo.setError(getString(R.string.err_empty_field));
            mobileNo.requestFocus();
            return;
        } else if (mobileNo.getText().toString().length() < 10) {
            mobileNo.setError(getString(R.string.mobilenumber_error));
            mobileNo.requestFocus();
            return;
        } else if (pincode.getText().toString().isEmpty()) {
            pincode.setError(getString(R.string.err_empty_field));
            pincode.requestFocus();
            return;
        } else if (pincode.getText().toString().length() < 6) {
            pincode.setError(getString(R.string.pincode_error));
            pincode.requestFocus();
            return;
        } else if (address.getText().toString().isEmpty()) {
            address.setError(getString(R.string.err_empty_field));
            address.requestFocus();
            return;
        } else if (pinPassView.getVisibility() == View.VISIBLE && pinPassEt.getText().toString().isEmpty()) {
            pinPassEt.setError(getString(R.string.err_empty_field));
            pinPassEt.requestFocus();
            return;
        }

        createUserApi();
    }


    private void createUserApi() {
        if (UtilMethods.INSTANCE.isNetworkAvialable(this)) {

            try {
                EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
                loader.show();
                UserCreate userCreate = new UserCreate(address.getText().toString(), "", website.getText().toString(),
                        mAppUserReffDetailResponse.getUserRegModel().getToken(), realApiSwitch.isChecked(), 0.0,
                        mobileNo.getText().toString(), name.getText().toString(), outletName.getText().toString(),
                        emailId.getText().toString(), selectedRollId, selectedSlabId, gstSwitch.isChecked(),
                        tdsSwitch.isChecked(), mAppUserReffDetailResponse.getUserRegModel().getUserInfo().getVirtual(), websiteSwitch.isChecked(),
                        mAppUserReffDetailResponse.getUserRegModel().getUserInfo().getAdminDefined(),
                        pincode.getText().toString());

                Call<BasicResponse> call = git.AppUserRegistraion(new AppUserRegisterRequest(pinPassEt.getText().toString(),
                        LoginDataResponse.getData().getUserID(), LoginDataResponse.getData().getLoginTypeID(),
                        ApplicationConstant.INSTANCE.APP_ID,
                        UtilMethods.INSTANCE.getIMEI(this),
                        "", BuildConfig.VERSION_NAME, UtilMethods.INSTANCE.getSerialNo(this), LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession(), userCreate));
                call.enqueue(new Callback<BasicResponse>() {
                    @Override
                    public void onResponse(Call<BasicResponse> call, final retrofit2.Response<BasicResponse> response) {

                        try {
                            if (loader != null) {
                                if (loader.isShowing())
                                    loader.dismiss();
                            }
                            BasicResponse data = response.body();
                            if (data != null) {

                                if (data.getStatuscode() == 1) {
                                    UtilMethods.INSTANCE.Successfulok(data.getMsg(), com.solution.brothergroup.Activities.CreateUserActivity.this);
                                } else if (data.getStatuscode() == -1) {
                                    if (!data.getVersionValid()) {
                                        UtilMethods.INSTANCE.versionDialog(com.solution.brothergroup.Activities.CreateUserActivity.this);
                                    } else {
                                        UtilMethods.INSTANCE.Error(com.solution.brothergroup.Activities.CreateUserActivity.this, data.getMsg() + "");
                                    }
                                }
                            } else {
                                UtilMethods.INSTANCE.Error(com.solution.brothergroup.Activities.CreateUserActivity.this, getString(R.string.some_thing_error) + "");
                            }
                        } catch (Exception e) {
                            if (loader != null) {
                                if (loader.isShowing())
                                    loader.dismiss();
                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<BasicResponse> call, Throwable t) {

                        try {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                            if (t.getMessage() != null && !t.getMessage().isEmpty()) {

                                if (t.getMessage().contains("No address associated with hostname")) {
                                    UtilMethods.INSTANCE.Error(com.solution.brothergroup.Activities.CreateUserActivity.this, getString(R.string.err_msg_network));


                                } else {
                                    UtilMethods.INSTANCE.Error(com.solution.brothergroup.Activities.CreateUserActivity.this, t.getMessage());


                                }

                            } else {
                                UtilMethods.INSTANCE.Error(com.solution.brothergroup.Activities.CreateUserActivity.this, getString(R.string.some_thing_error));
                            }
                        } catch (IllegalStateException ise) {
                            loader.dismiss();
                            UtilMethods.INSTANCE.Error(com.solution.brothergroup.Activities.CreateUserActivity.this, getString(R.string.some_thing_error));
                        }
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            UtilMethods.INSTANCE.dialogOk(this, getResources().getString(R.string.network_error_title),
                    getResources().getString(R.string.network_error_message), 2);
        }
    }

    private void AppUserReffDetailApi(String referralId) {
        if (UtilMethods.INSTANCE.isNetworkAvialable(this)) {

            try {
                EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
                loader.show();
                Call<AppUserReffDetailResponse> call = git.AppUserReffDetail(new AppUserReffDetailRequest(referralId,
                        LoginDataResponse.getData().getUserID(), LoginDataResponse.getData().getLoginTypeID(),
                        ApplicationConstant.INSTANCE.APP_ID,
                        UtilMethods.INSTANCE.getIMEI(this),
                        "", BuildConfig.VERSION_NAME, UtilMethods.INSTANCE.getSerialNo(this), LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));
                call.enqueue(new Callback<AppUserReffDetailResponse>() {
                    @Override
                    public void onResponse(Call<AppUserReffDetailResponse> call, final retrofit2.Response<AppUserReffDetailResponse> response) {

                        try {
                            if (loader != null) {
                                if (loader.isShowing())
                                    loader.dismiss();
                            }
                            mAppUserReffDetailResponse = response.body();
                            if (mAppUserReffDetailResponse != null && mAppUserReffDetailResponse.getStatuscode() != null) {

                                if (mAppUserReffDetailResponse.getStatuscode() == 1) {
                                    currentRefferalId = referral.getText().toString();
                                    if (mAppUserReffDetailResponse.getUserRegModel() != null) {


                                        UserInfo mUserInfo = mAppUserReffDetailResponse.getUserRegModel().getUserInfo();
                                        if (mUserInfo != null && mUserInfo.getOutletName() != null && mUserInfo.getResultCode() == 1) {
                                            referralDetailTv.setVisibility(View.VISIBLE);
                                            submitBtn.setVisibility(View.VISIBLE);
                                            gstSwitch.setChecked(mUserInfo.getGSTApplicable());
                                            tdsSwitch.setChecked(mUserInfo.getTDSApplicable());
                                            referralDetailTv.setText(mUserInfo.getOutletName() + " " + mUserInfo.getMobileNo());
                                            referralDetailTv.setTextColor(getResources().getColor(R.color.colorAccent));
                                            if (mUserInfo.getAdminDefined()) {
                                                slabView.setVisibility(View.GONE);
                                                websiteView.setVisibility(View.GONE);
                                            } else {
                                                slabView.setVisibility(View.VISIBLE);
                                                websiteView.setVisibility(View.VISIBLE);
                                            }
                                            if (mAppUserReffDetailResponse.getUserRegModel().getRoleSlab() != null && mAppUserReffDetailResponse.getUserRegModel().getRoleSlab().getRoles() != null) {
                                                chooseRoleArrayList.clear();
                                                chooseRoleArrayList.addAll(mAppUserReffDetailResponse.getUserRegModel().getRoleSlab().getRoles());
                                                chooseRoleStrArrayList.clear();
                                                chooseFosStrArrayList.clear();
                                                for (int i = 0; i < chooseRoleArrayList.size(); i++) {
                                                   // chooseRoleStrArrayList.add(chooseRoleArrayList.get(i).getRole());
                                                    if(!chooseRoleArrayList.get(i).getRole().equalsIgnoreCase("User"))
                                                    {
                                                        chooseRoleStrArrayList.add(chooseRoleArrayList.get(i).getRole());
                                                        roleIDMap.put(chooseRoleArrayList.get(i).getRole(),chooseRoleArrayList.get(i).getId());

                                                    }
                                                    else
                                                    {
                                                        chooseFosStrArrayList.add(chooseRoleArrayList.get(i).getRole());
                                                        roleIDMap.put(chooseRoleArrayList.get(i).getRole(),chooseRoleArrayList.get(i).getId());

                                                    }
                                                }

                                                arrayAdapterRole.notifyDataSetChanged();

                                                chooseSlabArrayList.clear();
                                                chooseSlabArrayList.addAll(mAppUserReffDetailResponse.getUserRegModel().getRoleSlab().getSlabs());
                                                chooseSlabStrArrayList.clear();
                                                for (int i = 0; i < chooseSlabArrayList.size(); i++) {
                                                    chooseSlabStrArrayList.add(chooseSlabArrayList.get(i).getSlab());
                                                }

                                                arrayAdapterSlab.notifyDataSetChanged();
                                            }
                                        } else {
                                            chooseRoleArrayList.clear();
                                            chooseRoleStrArrayList.clear();
                                            chooseFosStrArrayList.clear();
                                            chooseSlabArrayList.clear();
                                            chooseSlabStrArrayList.clear();
                                            selectedRollId = 0;
                                            selectedSlabId = 0;
                                            arrayAdapterRole.notifyDataSetChanged();
                                            arrayAdapterSlab.notifyDataSetChanged();
                                            gstSwitch.setChecked(false);
                                            tdsSwitch.setChecked(false);
                                            referralDetailTv.setVisibility(View.VISIBLE);
                                            referralDetailTv.setText(mUserInfo.getMsg());
                                            referralDetailTv.setTextColor(getResources().getColor(R.color.style_color_primary_dark));
                                            submitBtn.setVisibility(View.GONE);
                                        }

                                    } else {
                                        UtilMethods.INSTANCE.Error(com.solution.brothergroup.Activities.CreateUserActivity.this, getString(R.string.some_thing_error) + "");
                                    }
                                } else if (mAppUserReffDetailResponse.getStatuscode() == -1) {
                                    if (mAppUserReffDetailResponse.getVersionValid() != null && !mAppUserReffDetailResponse.getVersionValid()) {
                                        UtilMethods.INSTANCE.versionDialog(com.solution.brothergroup.Activities.CreateUserActivity.this);
                                    } else {
                                        UtilMethods.INSTANCE.Error(com.solution.brothergroup.Activities.CreateUserActivity.this, mAppUserReffDetailResponse.getMsg() + "");
                                    }
                                }
                            } else {
                                UtilMethods.INSTANCE.Error(com.solution.brothergroup.Activities.CreateUserActivity.this, getString(R.string.some_thing_error) + "");
                            }
                        } catch (Exception e) {
                            if (loader != null) {
                                if (loader.isShowing())
                                    loader.dismiss();
                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<AppUserReffDetailResponse> call, Throwable t) {


                        try {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                            if (t.getMessage() != null && !t.getMessage().isEmpty()) {

                                if (t.getMessage().contains("No address associated with hostname")) {
                                    UtilMethods.INSTANCE.Error(com.solution.brothergroup.Activities.CreateUserActivity.this, getString(R.string.err_msg_network));


                                } else {
                                    UtilMethods.INSTANCE.Error(com.solution.brothergroup.Activities.CreateUserActivity.this, t.getMessage());


                                }

                            } else {
                                UtilMethods.INSTANCE.Error(com.solution.brothergroup.Activities.CreateUserActivity.this, getString(R.string.some_thing_error));
                            }
                        } catch (IllegalStateException ise) {
                            loader.dismiss();
                            UtilMethods.INSTANCE.Error(com.solution.brothergroup.Activities.CreateUserActivity.this, getString(R.string.some_thing_error));
                        }
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            UtilMethods.INSTANCE.dialogOk(this, getResources().getString(R.string.network_error_title),
                    getResources().getString(R.string.network_error_message), 2);
        }
    }

}

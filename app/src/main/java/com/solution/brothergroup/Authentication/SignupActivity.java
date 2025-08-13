package com.solution.brothergroup.Authentication;

import android.app.Activity;
import android.os.Bundle;
import com.google.android.material.textfield.TextInputLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowInsetsControllerCompat;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.solution.brothergroup.Api.Object.UserCreateSignup;
import com.solution.brothergroup.Api.Request.SignupRequest;
import com.solution.brothergroup.Authentication.dto.LoginResponse;
import com.solution.brothergroup.BuildConfig;
import com.solution.brothergroup.R;
import com.solution.brothergroup.Util.ApiClient;
import com.solution.brothergroup.Util.ApplicationConstant;
import com.solution.brothergroup.Util.ChildRolen;
import com.solution.brothergroup.Util.CustomFilterDialogUtils.CustomFilterDialog;
import com.solution.brothergroup.Util.EndPointInterface;
import com.solution.brothergroup.Util.GetRoleForReferralResponse;
import com.solution.brothergroup.Util.NunberListRequest;
import com.solution.brothergroup.Util.UtilMethods;
import com.solution.brothergroup.usefull.CustomLoader;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class SignupActivity extends AppCompatActivity {
    private LinearLayout rr1;
    String geratedOtp;
    private LinearLayout llSignup,otplayout;
    private TextInputLayout tilName;
    private AutoCompleteTextView etName;
    private TextInputLayout tilOutletName;
    private AutoCompleteTextView etOutletName;
    private TextInputLayout tilRoll;
    private AutoCompleteTextView etRoll;
    private TextInputLayout tilMobile;
    private AutoCompleteTextView etMobile;
    private TextInputLayout tilEmail;
    private AutoCompleteTextView etEmail;
    private TextInputLayout tilAddress;
    private AutoCompleteTextView etAddress;
    private TextInputLayout tilPincode;
    private AutoCompleteTextView etPincode;
    private Button btLogin;
    private TextView tvLogin;
    CustomLoader loader; String apiCalledReferal;
    private TextInputLayout tilReferral;
    private AutoCompleteTextView etReferral;
    private HashMap<String, Integer> rollsMap = new HashMap<>();
    private String[] rollArray  ;
CustomFilterDialog mCustomFilterDialog;
    private TextInputLayout til_otp;//
    private AutoCompleteTextView et_otp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowInsetsControllerCompat controller = new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        controller.setAppearanceLightStatusBars(true);
        controller.setAppearanceLightNavigationBars(true);
        setContentView(R.layout.activity_signup);
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        mCustomFilterDialog=new CustomFilterDialog(this);
        findViews();
//
//        rollsMap.put("Sub Admin", 5);
//        rollsMap.put("Master Distributor", 6);
//        rollsMap.put("Distributor", 7);
//        rollsMap.put("Retailer", 3);
//        rollsMap.put("API User", 2);

    }

    private void findViews() {
        rr1 = (LinearLayout) findViewById(R.id.rr_1);
        llSignup = (LinearLayout) findViewById(R.id.ll_signup);
        tilName = (TextInputLayout) findViewById(R.id.til_name);
        etName = (AutoCompleteTextView) findViewById(R.id.et_name);
        tilOutletName = (TextInputLayout) findViewById(R.id.til_outletName);
        etOutletName = (AutoCompleteTextView) findViewById(R.id.et_outletName);
        tilRoll = (TextInputLayout) findViewById(R.id.til_rollid);
        etRoll = (AutoCompleteTextView) findViewById(R.id.et_roll);
       // etRoll.setText("Retailer");
        tilReferral = (TextInputLayout) findViewById(R.id.til_referral);
        etReferral = (AutoCompleteTextView) findViewById(R.id.et_referral);
        if (UtilMethods.INSTANCE.getReferrerId(this)!=null && !UtilMethods.INSTANCE.getReferrerId(this).equalsIgnoreCase(""))
        etReferral.setText(UtilMethods.INSTANCE.getReferrerId(this) + "");
        else
        etReferral.setText("1");
        tilMobile = (TextInputLayout) findViewById(R.id.til_mobile);
        etMobile = (AutoCompleteTextView) findViewById(R.id.et_mobile);
        tilEmail = (TextInputLayout) findViewById(R.id.til_email);
        etEmail = (AutoCompleteTextView) findViewById(R.id.et_email);
        tilAddress = (TextInputLayout) findViewById(R.id.til_address);
        etAddress = (AutoCompleteTextView) findViewById(R.id.et_address);
        tilPincode = (TextInputLayout) findViewById(R.id.til_pincode);
        etPincode = (AutoCompleteTextView) findViewById(R.id.et_pincode);
        btLogin = (Button) findViewById(R.id.bt_login);
        tvLogin = (TextView) findViewById(R.id.tv_login);
        til_otp = (TextInputLayout) findViewById(R.id.til_otp);
        et_otp = findViewById(R.id.et_otp);
        otplayout = findViewById(R.id.otplayout);
        til_otp.setVisibility(View.GONE);
        otplayout.setVisibility(View.GONE);
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        etRoll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etReferral.getText().toString().isEmpty()) {
                    tilReferral.setError(getString(R.string.err_empty_field));
                    etReferral.requestFocus();
                    return;
                }
                setRoll();
            }
        });

        etReferral.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (apiCalledReferal != null && !apiCalledReferal.equalsIgnoreCase(s.toString())) {
                    etRoll.setText("");
                }
            }
        });
    }

    void setRoll() {

        if (rollArray != null && rollArray.length > 0) {
            if (apiCalledReferal != null && !apiCalledReferal.equalsIgnoreCase(etReferral.getText().toString())) {
                Role(this);
            } else {
                selectRole();
            }

        } else {
            Role(this);
        }
    }
//    void setRoll(){
//        int selectedIndex = 0;
//        if (etRoll.getText().toString().length() == 0) {
//            selectedIndex = -1;
//        } else {
//            selectedIndex = Arrays.asList(rollArray).indexOf(etRoll.getText().toString());
//        }
//        mCustomFilterDialog.showSingleChoiceAlert(rollArray, selectedIndex, "Date Type", "Choose Date Type", new CustomFilterDialog.SingleChoiceDialogCallBack() {
//            @Override
//            public void onPositiveClick(int index) {
//                etRoll.setText(rollArray[index]);
//            }
//
//            @Override
//            public void onNegativeClick() {
//
//            }
//        });
//    }
void selectRole() {
    int selectedIndex = 0;
    if (etRoll.getText().toString().length() == 0) {
        selectedIndex = -1;
    } else {
        selectedIndex = Arrays.asList(rollArray).indexOf(etRoll.getText().toString());
    }
    mCustomFilterDialog.showSingleChoiceAlert(rollArray, selectedIndex, "Date Type", "Choose Date Type", new CustomFilterDialog.SingleChoiceDialogCallBack() {
        @Override
        public void onPositiveClick(int index) {
          try{ etRoll.setText(rollArray[index]);}catch (Exception e){}

        }

        @Override
        public void onNegativeClick() {

        }
    });
}
    void signup() {
        if (etName.getText().toString().isEmpty()) {
            tilName.setError(getString(R.string.err_empty_field));
            etName.requestFocus();
            return;
        } else if (etOutletName.getText().toString().isEmpty()) {
            tilOutletName.setError(getString(R.string.err_empty_field));
            etOutletName.requestFocus();
            return;
        } else if (etRoll.getText().toString().isEmpty()) {
            tilRoll.setError(getString(R.string.err_empty_field));
            etRoll.requestFocus();
            return;
        } else if (etMobile.getText().toString().isEmpty()) {
            tilMobile.setError(getString(R.string.err_empty_field));
            etMobile.requestFocus();
            return;
        } else if (etMobile.getText().toString().length() != 10) {
            tilMobile.setError(getString(R.string.err_msg_mobile_length));
            etMobile.requestFocus();
            return;
        } else if (etEmail.getText().toString().isEmpty()) {
            tilEmail.setError(getString(R.string.err_empty_field));
            etEmail.requestFocus();
            return;
        } else if (!etEmail.getText().toString().contains("@") || !etEmail.getText().toString().contains(".")) {
            tilEmail.setError(getString(R.string.err_msg_email));
            etEmail.requestFocus();
            return;
        } else if (etAddress.getText().toString().isEmpty()) {
            tilAddress.setError(getString(R.string.err_empty_field));
            etAddress.requestFocus();
            return;
        } else if (etPincode.getText().toString().isEmpty()) {
            tilPincode.setError(getString(R.string.err_empty_field));
            etPincode.requestFocus();
            return;
        } else if (etPincode.getText().toString().length() != 6) {
            tilPincode.setError(getString(R.string.pincode_error));
            etPincode.requestFocus();
            return;
        }

        if (til_otp.getVisibility() == View.VISIBLE) {

            if (et_otp.getText().toString().isEmpty()) {
                til_otp.setError(getString(R.string.err_empty_field));
                et_otp.requestFocus();

            } else if (!et_otp.getText().toString().equalsIgnoreCase(geratedOtp)) {
                til_otp.setError("Otp Does not match");
                et_otp.requestFocus();
            } else {
                Register(this, et_otp.getText().toString().trim(), geratedOtp);//
            }


        } else {

            Register(this, "", "");
        }
    }

    public void Role(final Activity context) {
        try {
            loader.show();
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);


            Call<GetRoleForReferralResponse> call = git.GetRoleForReferral(new NunberListRequest(
                    ApplicationConstant.INSTANCE.APP_ID,
                    UtilMethods.INSTANCE.getIMEI(context),
                    "", BuildConfig.VERSION_NAME, UtilMethods.INSTANCE.getSerialNo(context), etReferral.getText().toString()));
     call.enqueue(new Callback<GetRoleForReferralResponse>() {
                @Override
                public void onResponse(Call<GetRoleForReferralResponse> call, final retrofit2.Response<GetRoleForReferralResponse> response) {
                  // // Log.e("aa","  "+response.body().getChildRoles().get(0).getRole());
                    if (loader != null) {
                        if (loader.isShowing())
                            loader.dismiss();
                    }
                    try {
                        if (response.body() != null && response.body().getStatuscode() != null) {
                            if (response.body().getStatuscode() == 1) {
                                  List<ChildRolen> mChildRolesArray = response.body().getChildRoles();
                                if (mChildRolesArray != null && mChildRolesArray.size() > 0) {
                                    apiCalledReferal = etReferral.getText().toString();
                                    // private String[] rollArray = {"Sub Admin", "Master Distributor", "Distributor", "Retailer", "API User"};
                                    rollArray = new String[mChildRolesArray.size()];
                                    rollsMap.clear();
                                    for (int i = 0; i < mChildRolesArray.size(); i++) {
                                        rollArray[i] = mChildRolesArray.get(i).getRole() + "";
                                        rollsMap.put(mChildRolesArray.get(i).getRole() + "", Integer.valueOf(mChildRolesArray.get(i).getId()));
                                    }
                                    selectRole();
                                }

                            } else if (response.body().getStatuscode() == -1) {
                          /*      if (response.body().isVersionValid() != null && response.body().isVersionValid().equalsIgnoreCase("false")) {
                                    UtilMethods.INSTANCE.versionDialog(context);
                                } else {*/
                                UtilMethods.INSTANCE.Error(context, response.body().getMsg() + "");
                                // }
                            }
                        } else {
                            UtilMethods.INSTANCE.Error(context, getString(R.string.some_thing_error) + "");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }

                    }
                }

                @Override
                public void onFailure(Call<GetRoleForReferralResponse> call, Throwable t) {

                    if (loader != null) {
                        if (loader.isShowing())
                            loader.dismiss();
                    }
                    if (t.getMessage().contains("No address associated with hostname")) {
                        UtilMethods.INSTANCE.Error(context, context.getResources().getString(R.string.network_error));
                    } else {
                        UtilMethods.INSTANCE.Error(context, t.getMessage());
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void Register(final Activity context, String otp,  String genotp) {
        try {
            loader.show();
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            UserCreateSignup mUserCreateSignup = new UserCreateSignup(etAddress.getText().toString(), etMobile.getText().toString(),
                    etName.getText().toString(), etOutletName.getText().toString(), etEmail.getText().toString(), rollsMap.get(etRoll.getText().toString()),
                    etPincode.getText().toString(), etReferral.getText().toString(),
                    otp, genotp);
            Call<LoginResponse> call = git.AppUserSignup(new SignupRequest(
                    ApplicationConstant.INSTANCE.Domain,
                    ApplicationConstant.INSTANCE.APP_ID,
                    UtilMethods.INSTANCE.getIMEI(context),
                    "", BuildConfig.VERSION_NAME, UtilMethods.INSTANCE.getSerialNo(context), mUserCreateSignup));


            call.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, final retrofit2.Response<LoginResponse> response) {

                    if (loader != null) {
                        if (loader.isShowing())
                            loader.dismiss();
                    }
                    try {
                        if (response.body() != null && response.body().getStatuscode() != null) {
                            if (response.body().getStatuscode() == "9") {
                                geratedOtp = response.body().getResOTP();
                                til_otp.setVisibility(View.VISIBLE);
                                otplayout.setVisibility(View.VISIBLE);
                            }
                            if (response.body().getStatuscode().equalsIgnoreCase("1")) {
                                UtilMethods.INSTANCE.SuccessfulWithFinsh(context, response.body().getMsg() + "");


                                // okButton.setEnabled(false);
                                btLogin.setEnabled(false);
                                etAddress.setText("");
                                etMobile.setText("");
                                etName.setText("");
                                etOutletName.setText("");
                                etEmail.setText("");
                                etPincode.setText("");


                            } else if (response.body().getStatuscode().equalsIgnoreCase("-1")) {
                                if (response.body().getIsVersionValid() != null && response.body().getIsVersionValid().equalsIgnoreCase("false")) {
                                    UtilMethods.INSTANCE.versionDialog(context);
                                } else {
                                    UtilMethods.INSTANCE.Error(context, response.body().getMsg() + "");
                                }
                            }
                        }else{
                            UtilMethods.INSTANCE.Error(context, getString(R.string.some_thing_error) + "");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {

                    if (loader != null) {
                        if (loader.isShowing())
                            loader.dismiss();
                    }
                    if (t.getMessage().contains("No address associated with hostname")) {
                        UtilMethods.INSTANCE.Error(context, context.getResources().getString(R.string.network_error));
                    } else {
                        UtilMethods.INSTANCE.Error(context, t.getMessage());
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

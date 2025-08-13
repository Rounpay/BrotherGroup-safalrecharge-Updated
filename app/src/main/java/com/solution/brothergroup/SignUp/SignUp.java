package com.solution.brothergroup.SignUp;

import android.app.DatePickerDialog;
import android.os.Bundle;
import com.google.android.material.textfield.TextInputLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.solution.brothergroup.Authentication.LoginActivity;
import com.solution.brothergroup.R;
import com.solution.brothergroup.Util.ActivityActivityMessage;
import com.solution.brothergroup.Util.GlobalBus;
import com.solution.brothergroup.Util.UtilMethods;
import com.solution.brothergroup.usefull.CustomLoader;
import com.solution.brothergroup.usefull.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;


public class SignUp extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    //Declare Button...
    public Button btSignUp;

    //Declare RelativeLayout...
    public RelativeLayout rlBack;

    //Declare EditText...
    public EditText etDob;
    public EditText etName;
    public EditText etEmail;
    public EditText etMobileNo;
    public EditText etPassword;
    public EditText etCnfPassword;
    public EditText etState;
    public EditText etCity;

    //Declare EditText...
    public TextInputLayout tilName;
    public TextInputLayout tilEmail;
    public TextInputLayout tilMobileNo;
    public TextInputLayout tilPassword;
    public TextInputLayout tilCnfPassword;
    //Declare Spinner...
    Spinner SpinCustomerType;
    Spinner SpinState;
    Spinner SpinCity;
    // Declare CustomLoader.....
    CustomLoader loader;
    //Declare String..
    String sponsor_id = "";
    //Declare SimpleDateFormat...
    private SimpleDateFormat dateFormatter;
    //Declare DatePickerDialog...
    private DatePickerDialog fromDatePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        getId();

    }

    public void getId() {
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        dateFormatter = new SimpleDateFormat("dd MMMM yyyy", Locale.US);

        btSignUp = (Button) findViewById(R.id.bt_signup);
        rlBack = (RelativeLayout) findViewById(R.id.rl_back);

        etDob = (EditText) findViewById(R.id.et_dob);
        etName = (EditText) findViewById(R.id.et_name);
        etEmail = (EditText) findViewById(R.id.et_email);
        etMobileNo = (EditText) findViewById(R.id.et_mobile);
        etPassword = (EditText) findViewById(R.id.et_pass);
        etCnfPassword = (EditText) findViewById(R.id.et_cnf_pass);

        tilName = (TextInputLayout) findViewById(R.id.til_name);
        tilEmail = (TextInputLayout) findViewById(R.id.til_email);
        tilMobileNo = (TextInputLayout) findViewById(R.id.til_mobile);
        tilPassword = (TextInputLayout) findViewById(R.id.til_pass);
        tilCnfPassword = (TextInputLayout) findViewById(R.id.til_cnf_pass);

        // For Customer Type Spinner...

        SpinCustomerType = (Spinner) findViewById(R.id.spn_customer_type);
        SpinCustomerType.setOnItemSelectedListener(this);

        ArrayList<String> array = new ArrayList<String>();
        array.add("Select Type");
        array.add("Customer");
        array.add("Driver");
        array.add("Driver");

        ArrayAdapter<String> mAdapter;
        mAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, array);
        SpinCustomerType.setAdapter(mAdapter);

        ////////////////////////////////////////////////////////////////////////////////

        // For Customer Type Spinner...

        SpinCustomerType = (Spinner) findViewById(R.id.spn_customer_type);
        SpinCustomerType.setOnItemSelectedListener(this);

        ArrayList<String> customer_type = new ArrayList<String>();
        customer_type.add("Select Type");
        customer_type.add("Customer");
        customer_type.add("Driver");
        customer_type.add("Owner");

        ArrayAdapter<String> customerTypeAdapter;
        customerTypeAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, customer_type);
        SpinCustomerType.setAdapter(customerTypeAdapter);

        ////////////////////////////////////////////////////////////////////////////////

        // For State Spinner...

        SpinState = (Spinner) findViewById(R.id.spn_state);
        SpinState.setOnItemSelectedListener(this);

        ArrayList<String> state = new ArrayList<String>();
        state.add("Select State");
        state.add("UttarPradesh");
        state.add("Bihar");
        state.add("Uttarakhand");
        state.add("MadhyaPradesh");
        state.add("Jammu Kashmir");

        ArrayAdapter<String> stateAdapter;
        stateAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, state);
        SpinState.setAdapter(stateAdapter);

        ////////////////////////////////////////////////////////////////////////////////

        // For City Spinner...

        SpinCity = (Spinner) findViewById(R.id.spn_city);
        SpinCity.setOnItemSelectedListener(this);

        ArrayList<String> city = new ArrayList<String>();
        city.add("Select City");
        city.add("Lucknow");
        city.add("Sitapur");
        city.add("Barabanki");
        city.add("Lakhimpur");
        city.add("Bareily");
        city.add("Allahanbad");

        ArrayAdapter<String> cityAdapter;
        cityAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, city);
        SpinCity.setAdapter(cityAdapter);

        //=====================================================Set date Time filter================================================//

        setDateTimeField();
        TextChangeListners_Method();

        setListners();
    }

    private void TextChangeListners_Method() {
        /////////////////////////////////////////////////////
        etName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                /*if (!validateName()) {
                    return;
                }*/
                validateForm();
            }
        });
        /////////////////////////////////////////////////////
        etEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                /*if (!validateEmail()) {
                    return;
                }*/
                validateForm();
            }
        });

        /////////////////////////////////////////////////////
        etMobileNo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                /*if (!validateMobile()) {
                    return;
                }*/
                validateForm();
            }
        });

//        public void Role(final Activity context) {
//            try {
//                loader.show();
//                EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
//
//
//                Call<AppUserListResponse> call = git.GetRoleForReferral(new NunberListRequest(
//                        ApplicationConstant.INSTANCE.APP_ID,
//                        UtilMethods.INSTANCE.getIMEI(context),
//                        "", BuildConfig.VERSION_NAME, UtilMethods.INSTANCE.getSerialNo(context), etReferral.getText().toString()));
//
//
//                call.enqueue(new Callback<AppUserListResponse>() {
//                    @Override
//                    public void onResponse(Call<AppUserListResponse> call, final retrofit2.Response<AppUserListResponse> response) {
//
//                        if (loader != null) {
//                            if (loader.isShowing())
//                                loader.dismiss();
//                        }
//                        try {
//                            if (response.body() != null && response.body().getStatuscode() != null) {
//                                if (response.body().getStatuscode() == 1) {
//                                    ArrayList<ChildRole> mChildRolesArray = response.body().getChildRoles();
//                                    if (mChildRolesArray != null && mChildRolesArray.size() > 0) {
//                                        apiCalledReferal = etReferral.getText().toString();
//                                        // private String[] rollArray = {"Sub Admin", "Master Distributor", "Distributor", "Retailer", "API User"};
//                                        rollArray = new String[mChildRolesArray.size()];
//                                        rollsMap.clear();
//                                        for (int i = 0; i < mChildRolesArray.size(); i++) {
//                                            rollArray[i] = mChildRolesArray.get(i).getRole() + "";
//                                            rollsMap.put(mChildRolesArray.get(i).getRole() + "", Integer.valueOf(mChildRolesArray.get(i).getId()));
//                                        }
//                                        selectRole();
//                                    }
//                                } else if (response.body().getStatuscode() == -1) {
//                          /*      if (response.body().isVersionValid() != null && response.body().isVersionValid().equalsIgnoreCase("false")) {
//                                    UtilMethods.INSTANCE.versionDialog(context);
//                                } else {*/
//                                    UtilMethods.INSTANCE.Error(context, response.body().getMsg() + "");
//                                    // }
//                                }
//                            } else {
//                                UtilMethods.INSTANCE.Error(context, getString(R.string.some_thing_error) + "");
//                            }
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                            if (loader != null) {
//                                if (loader.isShowing())
//                                    loader.dismiss();
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<AppUserListResponse> call, Throwable t) {
//
//                        if (loader != null) {
//                            if (loader.isShowing())
//                                loader.dismiss();
//                        }
//                        if (t.getMessage().contains("No address associated with hostname")) {
//                            UtilMethods.INSTANCE.Error(context, context.getResources().getString(R.string.network_error));
//                        } else {
//                            UtilMethods.INSTANCE.Error(context, t.getMessage());
//                        }
//                    }
//                });
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
        /////////////////////////////////////////////////////
        etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                /*if (!validatePass()) {
                    return;
                }*/
                validateForm();
            }
        });

        /////////////////////////////////////////////////////
        etCnfPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                /*if (!validateCnfPass()) {
                    return;
                }*/
                validateForm();
            }
        });
    }

    private boolean validateName() {
        if (etName.getText().toString().trim().isEmpty()) {
            tilName.setError(getString(R.string.err_msg_name));
            //  etName.requestFocus();
            return false;
        } else {
            //  tilName.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validateEmail() {
        if (etEmail.getText().toString().trim().isEmpty()) {
            tilEmail.setError(getString(R.string.err_msg_email_blank));
            // etEmail.requestFocus();
            return false;
        } else {
            if (!UtilMethods.INSTANCE.isValidEmail(etEmail.getText().toString())) {
                tilEmail.setError(getString(R.string.err_msg_email));
            }

        }
        return true;
    }

    private boolean validateMobile() {
        if (etMobileNo.getText().toString().trim().isEmpty()) {
            tilMobileNo.setError(getString(R.string.err_msg_mobile));
            return false;
        } else {
            if ((etMobileNo.getText() != null && etMobileNo.getText().toString().trim().length() > 0 &&
                    UtilMethods.INSTANCE.isValidMobile(etMobileNo.getText().toString().trim()) &&
                    !(etMobileNo.getText().toString().trim().length() < 10) &&
                    (etMobileNo.getText().toString().trim().charAt(0) == '7' ||
                            etMobileNo.getText().toString().trim().charAt(0) == '8' ||
                            etMobileNo.getText().toString().trim().charAt(0) == '9')
            )) {
                // tilMobileNo.setErrorEnabled(false);
            }
        }
        return true;
    }

    private boolean validatePass() {
        if (etPassword.getText().toString().trim().isEmpty()) {
            tilPassword.setError(getString(R.string.err_msg_pass));
            // etPassword.requestFocus();
            return false;
        } else {
            //  tilPassword.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validateCnfPass() {
        if (etCnfPassword.getText().toString().trim().isEmpty()) {
            tilCnfPassword.setError(getString(R.string.err_msg_cnf_pass));
            // etCnfPassword.requestFocus();
            return false;
        } else if (!etCnfPassword.getText().toString().equals(etPassword.getText().toString())) {
            tilCnfPassword.setError(getString(R.string.err_msg_match_pass));
            etCnfPassword.requestFocus();
            return false;
        } else {
            // tilCnfPassword.setErrorEnabled(false);
        }
        return true;
    }

    public void validateForm() {

        if (!validateName()) {
            return;
        } else if (!validateEmail()) {
            return;
        } else if (!validateMobile()) {
            return;
        } else if (!validatePass()) {
            return;
        } else if (!validateCnfPass()) {
            return;
        } else {
            btSignUp.setEnabled(true);
        }

    }

    public void setListners() {
        btSignUp.setOnClickListener(this);
        rlBack.setOnClickListener(this);
        etDob.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == btSignUp) {
            if (UtilMethods.INSTANCE.isNetworkAvialable(SignUp.this)) {

                loader.show();
                loader.setCancelable(false);
                loader.setCanceledOnTouchOutside(false);


            } else {
                UtilMethods.INSTANCE.NetworkError(SignUp.this, getResources().getString(R.string.err_msg_network_title),
                        getResources().getString(R.string.err_msg_network));
            }
        }
        if (v == rlBack) {
            Utils.goAnotherActivity(this, LoginActivity.class);
        }
        if (v == etDob) {
            fromDatePickerDialog.show();
        }
    }

    @Override
    public void onBackPressed() {
        Utils.goAnotherActivity(this, LoginActivity.class);
    }

    private void setDateTimeField() {

        Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                etDob.setText(dateFormatter.format(newDate.getTime()));
                /*ShowDate=String.valueOf(dateFormatter.format(newDate.getTime()));
                date = String.valueOf(newDate.getTimeInMillis()/1000L);*/
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        fromDatePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

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

    @Subscribe
    public void onActivityActivityMessage(ActivityActivityMessage activityFragmentMessage) {
        if (activityFragmentMessage.getMessage().equalsIgnoreCase("success")) {
            Utils.goAnotherActivity(this, LoginActivity.class);
        }
    }

}



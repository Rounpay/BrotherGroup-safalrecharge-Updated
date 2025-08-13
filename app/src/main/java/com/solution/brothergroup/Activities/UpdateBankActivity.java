package com.solution.brothergroup.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.solution.brothergroup.Api.Object.BankListObject;
import com.solution.brothergroup.Api.Request.EditUser;
import com.solution.brothergroup.Api.Request.UpdateUserRequest;
import com.solution.brothergroup.Api.Response.BankListResponse;
import com.solution.brothergroup.Api.Response.BasicResponse;
import com.solution.brothergroup.Authentication.dto.LoginResponse;
import com.solution.brothergroup.BuildConfig;
import com.solution.brothergroup.Fragments.dto.GetUserResponse;
import com.solution.brothergroup.R;
import com.solution.brothergroup.Util.ApiClient;
import com.solution.brothergroup.Util.ApplicationConstant;
import com.solution.brothergroup.Util.EndPointInterface;
import com.solution.brothergroup.Util.UtilMethods;
import com.solution.brothergroup.usefull.CustomLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;

import retrofit2.Call;
import retrofit2.Callback;

public class UpdateBankActivity extends AppCompatActivity {


    private Spinner spin_Bank;
    private EditText IFSCEt;
    private EditText AccountNumberEt,branchNameEt;
    private EditText AccountNameEt;
    private AppCompatTextView notice;
    private LinearLayout bottomBtnView;
    private View submitBtn;
    private String[] arrayBank;
    GetUserResponse mGetUserResponse;
    private Map<String, Integer> hMapbankId = new HashMap<>();

    private CustomLoader loader;

    private AlertDialog alertDialog;


    private String selectedBank = "", ifsc = "";
    private int selectedBankPos = 0;
    private LoginResponse mLoginDataResponse;
    private Gson gson;
   
    private int bankId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_bank);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setTitle("Update Bank");
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_icon);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        gson = new Gson();

        mLoginDataResponse = gson.fromJson(UtilMethods.INSTANCE.getLoginPref(this), LoginResponse.class);


        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);
        findViews();
        onViewClick();
        if (UtilMethods.INSTANCE.getBankList(this) != null && UtilMethods.INSTANCE.getBankList(this).length() > 0) {
            getBankData(new Gson().fromJson(UtilMethods.INSTANCE.getBankList(this), BankListResponse.class));
        } else {
            HitApi();
        }
        mGetUserResponse = new Gson().fromJson(UtilMethods.INSTANCE.getUserDataPref(this), GetUserResponse.class);
        //mGetUserResponse = (GetUserResponse) getIntent().getSerializableExtra("UserData");
        setUserData();

    }

    private void findViews() {

        spin_Bank =  findViewById(R.id.spin_Bank);
        IFSCEt = (EditText) findViewById(R.id.IFSCEt);branchNameEt = (EditText) findViewById(R.id.branchNameEt);
        AccountNumberEt = (EditText) findViewById(R.id.AccountNumberEt);
        AccountNameEt = (EditText) findViewById(R.id.AccountNameEt);
        notice = (AppCompatTextView) findViewById(R.id.notice);
        bottomBtnView = (LinearLayout) findViewById(R.id.bottomBtnView);
        submitBtn = findViewById(R.id.bt_submit);


    }

    void onViewClick() {




        submitBtn.setOnClickListener(v -> updateBank());

    }


    private void getBankData(BankListResponse bankListResponse) {
        try {
            if (bankListResponse != null && bankListResponse.getBankMasters() != null && bankListResponse.getBankMasters().size() > 0) {

                final ArrayList<BankListObject> Bank = bankListResponse.getBankMasters();
                final ArrayList<String> arrayListBank = new ArrayList<String>();
                final ArrayList<String> BankidID = new ArrayList<String>();

                arrayBank = new String[bankListResponse.getBankMasters().size() + 1];
                arrayBank[0] = "Select Bank";

                for (int i = 0; i < Bank.size(); i++) {
                    arrayBank[i + 1] = Bank.get(i).getBankName();
                    arrayListBank.add(Bank.get(i).getBankName());
                    /*BankidID.add(Bank.get(i).getId() + "_" + Bank.get(i).getAccountHolder() + "_" + Bank.get(i).getAccountNo());*/
                    BankidID.add(Bank.get(i).getId());
                    hMapbankId.put(Bank.get(i).getBankName(), Integer.valueOf(Bank.get(i).getId()));
                }

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayBank);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spin_Bank.setAdapter(arrayAdapter);
                spin_Bank.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        selectedBank = spin_Bank.getSelectedItem().toString();
                        if (selectedBank != null && !selectedBank.equalsIgnoreCase("Select Bank")) {
                            bankId = hMapbankId.get(selectedBank);
                            selectedBank = spin_Bank.getSelectedItem().toString().trim();
                            ifsc = Bank.get(position).getIfsc();
                            IFSCEt.setText(ifsc + "");
                        } else {
                            IFSCEt.setText("");
                            selectedBank = "";
                        }



                   /* bankId[0] = BankidID.get(position);
                    String[] str = bankId[0].split("_");
                    BankID = str[0];
                    txtAccountID.setText(str[1] + "");
                    number.setText(str[2] + "");*/
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        spin_Bank.setSelection(Integer.parseInt(arrayBank[0]));
                    }
                });
            } else {

            }
        } catch (Exception ex) {
            UtilMethods.INSTANCE.Error(this, "Something went wrong!!");
            // Toast.makeText(this, "Bank List is not found,Try again!!", Toast.LENGTH_SHORT).show();

        }


    }


    private void HitApi() {
        if (UtilMethods.INSTANCE.isNetworkAvialable(this)) {
            loader.show();
            loader.setCancelable(false);
            loader.setCanceledOnTouchOutside(false);
            UtilMethods.INSTANCE.GetBanklist(this, loader, new UtilMethods.ApiCallBack() {
                @Override
                public void onSucess(Object object) {
                    getBankData((BankListResponse) object);
                }
            });
        } else {
            UtilMethods.INSTANCE.dialogOk(this, getResources().getString(R.string.network_error_title),
                    getResources().getString(R.string.network_error_message), 2);
        }
    }
   /* void openPhoneBook(int intentRequest, int inentPermisssion) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                startActivityForResult(new Intent(this, PermissionActivity.class),
                        inentPermisssion);

            } else {
                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivityForResult(intent, intentRequest);
            }
        } else {
            Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivityForResult(intent, intentRequest);
        }
    }*/


    void setUserData() {
        if (mGetUserResponse != null && mGetUserResponse.getUserInfo() != null) {


            if (mGetUserResponse.getUserInfo().getIfsc() != null && !mGetUserResponse.getUserInfo().getIfsc().isEmpty()) {
                IFSCEt.setText(mGetUserResponse.getUserInfo().getIfsc());
            }
            if (mGetUserResponse.getUserInfo().getBranchName() != null && !mGetUserResponse.getUserInfo().getBranchName().isEmpty()) {
                branchNameEt.setText(mGetUserResponse.getUserInfo().getBranchName());
            }
            if (mGetUserResponse.getUserInfo().getAccountNumber() != null && !mGetUserResponse.getUserInfo().getAccountNumber().isEmpty()) {
                AccountNumberEt.setText(mGetUserResponse.getUserInfo().getAccountNumber());
            }
            if (mGetUserResponse.getUserInfo().getAccountName() != null && !mGetUserResponse.getUserInfo().getAccountName().isEmpty()) {
                AccountNameEt.setText(mGetUserResponse.getUserInfo().getAccountName());
            }
            if (mGetUserResponse.getUserInfo().getBankName() != null && !mGetUserResponse.getUserInfo().getBankName().isEmpty()) {
                selectedBank = mGetUserResponse.getUserInfo().getBankName();
                ArrayAdapter myAdap = (ArrayAdapter) spin_Bank.getAdapter();
                if (myAdap != null) {
                    int spinnerPosition = myAdap.getPosition(mGetUserResponse.getUserInfo().getBankName());

                    //set the default according to value
                    spin_Bank.setSelection(spinnerPosition);
                }

            }

        }
    }


    void updateBank() {
        if (!spin_Bank.getSelectedItem().toString().isEmpty() && spin_Bank.getSelectedItem().toString().equalsIgnoreCase("Select Bank")) {
            Toast.makeText(this, "Please Select Bank", Toast.LENGTH_SHORT).show();

            /*tv_Bank.setVisibility(View.VISIBLE);
            tv_Bank.setText("Please Select Bank");
            tv_Bank.requestFocus();*/
        }
        /*if (bankTv.getText().length() == 0) {
            bankTv.setError("Please Select Bank");
            bankTv.requestFocus();
        }*/ else if (branchNameEt.getText().toString().isEmpty()) {
            branchNameEt.setError(getString(R.string.err_empty_field));
            branchNameEt.requestFocus();
        } else if (IFSCEt.getText().toString().isEmpty()) {
            IFSCEt.setError(getString(R.string.err_empty_field));
            IFSCEt.requestFocus();
        } else if (AccountNumberEt.getText().toString().isEmpty()) {
            AccountNumberEt.setError(getString(R.string.err_empty_field));
            AccountNumberEt.requestFocus();
        } else if (AccountNameEt.getText().toString().isEmpty()) {
            AccountNameEt.setError(getString(R.string.err_empty_field));
            AccountNameEt.requestFocus();
        } else {
            updateBankApi();
        }
    }


    public void updateBankApi() {
        try {
            loader.show();
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            EditUser editUser = new EditUser(selectedBank, IFSCEt.getText().toString(), AccountNumberEt.getText().toString(),
                    AccountNameEt.getText().toString(),branchNameEt.getText().toString());

            Call<BasicResponse> call = git.UpdateBank(new UpdateUserRequest(
                    mLoginDataResponse.getData().getUserID(), mLoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID, UtilMethods.INSTANCE.getIMEI(this),
                    "", BuildConfig.VERSION_NAME, UtilMethods.INSTANCE.getSerialNo(this),
                    mLoginDataResponse.getData().getSessionID(), mLoginDataResponse.getData().getSession(), editUser));

            call.enqueue(new Callback<BasicResponse>() {

                @Override
                public void onResponse(Call<BasicResponse> call, retrofit2.Response<BasicResponse> response) {

                    try {
                        if (response.isSuccessful()) {
                            BasicResponse data = response.body();
                            if (data != null) {
                                if (data.getStatuscode() == 1) {
                                    UtilMethods.INSTANCE.Successful(UpdateBankActivity.this, data.getMsg() + "");

                                    mGetUserResponse.getUserInfo().setAccountName(AccountNameEt.getText().toString());
                                    mGetUserResponse.getUserInfo().setAccountNumber(AccountNumberEt.getText().toString());
                                    mGetUserResponse.getUserInfo().setIfsc(IFSCEt.getText().toString());
                                    mGetUserResponse.getUserInfo().setBankName(selectedBank);
                                    mGetUserResponse.getUserInfo().setBranchName(branchNameEt.getText().toString());

                                    UtilMethods.INSTANCE.  setUserDataPref(UpdateBankActivity.this, gson.toJson(mGetUserResponse));
                                    setResult(RESULT_OK);
                                } else if (response.body().getStatuscode() == -1) {
                                    setResult(RESULT_OK);
                                    UtilMethods.INSTANCE.Error(UpdateBankActivity.this, data.getMsg() + "");
                                } else if (response.body().getStatuscode() == 0) {

                                    UtilMethods.INSTANCE.Error(UpdateBankActivity.this, data.getMsg() + "");
                                } else {
                                    UtilMethods.INSTANCE.Error(UpdateBankActivity.this, data.getMsg() + "");
                                }

                            } else {
                                UtilMethods.INSTANCE.Error(UpdateBankActivity.this, getString(R.string.some_thing_error));
                            }
                            if (loader != null && loader.isShowing()) {
                                loader.dismiss();
                            }
                        } else {
                            if (loader != null && loader.isShowing()) {
                                loader.dismiss();
                            }
                            UtilMethods.INSTANCE.apiErrorHandle(UpdateBankActivity.this, response.code(), response.message());
                        }
                    } catch (Exception e) {
                        if (loader != null && loader.isShowing()) {
                            loader.dismiss();
                        }
                    }
                }

                @Override
                public void onFailure(Call<BasicResponse> call, Throwable t) {

                    try {

                        if (loader != null && loader.isShowing()) {
                            loader.dismiss();
                        }
                        UtilMethods.INSTANCE.apiFailureError(UpdateBankActivity.this,t);
                    } catch (IllegalStateException ise) {
                        if (loader != null && loader.isShowing()) {
                            loader.dismiss();
                        }
                        UtilMethods.INSTANCE.Error(UpdateBankActivity.this, getString(R.string.some_thing_error));
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            if (loader != null && loader.isShowing()) {
                loader.dismiss();
            }
            UtilMethods.INSTANCE.Error(UpdateBankActivity.this, getString(R.string.some_thing_error));
        }

    }





}
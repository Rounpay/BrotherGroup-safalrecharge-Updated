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
import com.solution.brothergroup.Api.Object.SettlementAccountData;
import com.solution.brothergroup.Api.Request.UpdateSettlementAccountRequest;
import com.solution.brothergroup.Api.Response.BankListResponse;
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

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import retrofit2.Call;
import retrofit2.Callback;

public class UpdateAddSettlementBankActivity extends AppCompatActivity {

    private Spinner spin_Bank;

    private EditText IFSCEt;
    private EditText AccountNumberEt, branchNameEt;
    private EditText AccountNameEt;
    private AppCompatTextView notice;
    private LinearLayout bottomBtnView;
    private View submitBtn;
    private Map<String, Integer> hMapbankId = new HashMap<>();

    private String[] arrayBank;
    SettlementAccountData mSettlementAccountData;


    private CustomLoader loader;

    private AlertDialog alertDialog;


    private String selectedBank = "", ifsc = "";
    private int selectedBankPos = 0;
    private LoginResponse mLoginDataResponse;
    private Gson gson;
    private String deviceId, deviceSerialNum;
    private int updatedId;
    private int bankId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_add_settlement_bank);
        gson = new Gson();

        mLoginDataResponse = gson.fromJson(UtilMethods.INSTANCE.getLoginPref(this), LoginResponse.class);
        deviceId = UtilMethods.INSTANCE.getIMEI(this);
        deviceSerialNum = UtilMethods.INSTANCE.getSerialNo(this);

        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);
        findViews();
        onViewClick();
        if (UtilMethods.INSTANCE.getBankList(this) != null && UtilMethods.INSTANCE.getBankList(this).length() > 0) {
            getBankData(new Gson().fromJson(UtilMethods.INSTANCE.getBankList(this), BankListResponse.class));
        } else {
            HitApi();
        }
        mSettlementAccountData = (SettlementAccountData) getIntent().getSerializableExtra("Data");
        setData();

    }

    private void findViews() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setTitle("Update Account");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_icon);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        spin_Bank =  findViewById(R.id.spin_Bank);
        IFSCEt = (EditText) findViewById(R.id.IFSCEt);
        branchNameEt = (EditText) findViewById(R.id.branchNameEt);
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


    void setData() {
        if (mSettlementAccountData != null) {

            updatedId = mSettlementAccountData.getId();
            bankId = mSettlementAccountData.getBankID();
            if (mSettlementAccountData.getIfsc() != null && !mSettlementAccountData.getIfsc().isEmpty()) {
                IFSCEt.setText(mSettlementAccountData.getIfsc());
            }
            /*if (mSettlementAccountData.getBranchName() != null && !mSettlementAccountData.getBranchName().isEmpty()) {
                branchNameEt.setText(mSettlementAccountData.getBranchName());
            }*/
            if (mSettlementAccountData.getAccountNumber() != null && !mSettlementAccountData.getAccountNumber().isEmpty()) {
                AccountNumberEt.setText(mSettlementAccountData.getAccountNumber());
            }
            if (mSettlementAccountData.getAccountHolder() != null && !mSettlementAccountData.getAccountHolder().isEmpty()) {
                AccountNameEt.setText(mSettlementAccountData.getAccountHolder());
            }

            if (mSettlementAccountData.getBankName() != null && !mSettlementAccountData.getBankName().isEmpty()) {
                selectedBank = mSettlementAccountData.getBankName();
                ArrayAdapter myAdap = (ArrayAdapter) spin_Bank.getAdapter();
                if (myAdap != null) {
                    int spinnerPosition = myAdap.getPosition(mSettlementAccountData.getBankName());

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
        }*/ /*else if (branchNameEt.getText().toString().isEmpty()) {
            branchNameEt.setError(getString(R.string.err_empty_field));
            branchNameEt.requestFocus();
        } */ else if (IFSCEt.getText().toString().isEmpty()) {
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

            Call<BasicResponse> call = git.UpdateSettlementAccount(new UpdateSettlementAccountRequest(
                    mLoginDataResponse.getData().getUserID(), mLoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID, deviceId,
                    "", BuildConfig.VERSION_NAME, deviceSerialNum,
                    mLoginDataResponse.getData().getSessionID(), mLoginDataResponse.getData().getSession(), AccountNameEt.getText().toString(),
                    AccountNumberEt.getText().toString(), bankId, selectedBank, updatedId, IFSCEt.getText().toString()));

            call.enqueue(new Callback<BasicResponse>() {

                @Override
                public void onResponse(Call<BasicResponse> call, retrofit2.Response<BasicResponse> response) {

                    try {
                        if (response.isSuccessful()) {
                            BasicResponse data = response.body();
                            if (data != null) {
                                if (data.getStatuscode() == 1) {
                                    if(data.getData()!=null){
                                        if (data.getData().getStatuscode() == 1) {
                                            UtilMethods.INSTANCE.SuccessfulWithFinish(data.getData().getMsg() + "", com.solution.brothergroup.Activities.UpdateAddSettlementBankActivity.this, false);

                                            setResult(RESULT_OK);
                                        }else {
                                            UtilMethods.INSTANCE.Error(com.solution.brothergroup.Activities.UpdateAddSettlementBankActivity.this, data.getData().getMsg() + "");

                                        }
                                    }else {
                                        UtilMethods.INSTANCE.Successful(com.solution.brothergroup.Activities.UpdateAddSettlementBankActivity.this, data.getMsg() + "");

                                        setResult(RESULT_OK);
                                    }

                                } else {
                                    //setResult(RESULT_OK);
                                    UtilMethods.INSTANCE.Error(com.solution.brothergroup.Activities.UpdateAddSettlementBankActivity.this, data.getMsg() + "");
                                }

                            } else {
                                UtilMethods.INSTANCE.Error(com.solution.brothergroup.Activities.UpdateAddSettlementBankActivity.this, getString(R.string.some_thing_error));
                            }
                            if (loader != null && loader.isShowing()) {
                                loader.dismiss();
                            }
                        } else {
                            if (loader != null && loader.isShowing()) {
                                loader.dismiss();
                            }
                            UtilMethods.INSTANCE.apiErrorHandle(com.solution.brothergroup.Activities.UpdateAddSettlementBankActivity.this, response.code(), response.message());
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
                        UtilMethods.INSTANCE.apiFailureError(com.solution.brothergroup.Activities.UpdateAddSettlementBankActivity.this, t);
                    } catch (IllegalStateException ise) {
                        if (loader != null && loader.isShowing()) {
                            loader.dismiss();
                        }
                        UtilMethods.INSTANCE.Error(com.solution.brothergroup.Activities.UpdateAddSettlementBankActivity.this, getString(R.string.some_thing_error));
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            if (loader != null && loader.isShowing()) {
                loader.dismiss();
            }
            UtilMethods.INSTANCE.Error(com.solution.brothergroup.Activities.UpdateAddSettlementBankActivity.this, getString(R.string.some_thing_error));
        }

    }



}
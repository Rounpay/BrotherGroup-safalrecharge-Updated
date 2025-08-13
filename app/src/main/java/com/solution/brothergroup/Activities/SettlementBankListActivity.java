package com.solution.brothergroup.Activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.solution.brothergroup.Adapter.SettlementAccountAdapter;
import com.solution.brothergroup.Api.Object.SettlementAccountData;
import com.solution.brothergroup.Api.Request.BasicRequest;
import com.solution.brothergroup.Api.Request.UpdateSettlementAccountRequest;
import com.solution.brothergroup.Api.Response.BasicResponse;
import com.solution.brothergroup.Api.Response.SettlementAccountResponse;
import com.solution.brothergroup.Authentication.dto.LoginResponse;
import com.solution.brothergroup.BuildConfig;
import com.solution.brothergroup.R;
import com.solution.brothergroup.Util.ApiClient;
import com.solution.brothergroup.Util.ApplicationConstant;
import com.solution.brothergroup.Util.EndPointInterface;
import com.solution.brothergroup.Util.UtilMethods;
import com.solution.brothergroup.usefull.CustomLoader;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.concurrent.TimeoutException;

import retrofit2.Call;
import retrofit2.Callback;

public class SettlementBankListActivity extends AppCompatActivity {

    private Gson gson;
    RecyclerView recycler_view;

    View addBtn;
    SettlementAccountAdapter mAdapter;
    ArrayList<SettlementAccountData> mBankListObjects = new ArrayList<>();
    /*ArrayList<BankListObject> mTopBankListObjects = new ArrayList<>();*/
    EditText search_all;
    private CustomLoader loader;
    private LoginResponse mLoginDataResponse;
    private String deviceId, deviceSerialNum;
    private Dialog dialog;
    private int INTENT_UPDATE = 2945;
    private AlertDialog alertDialogSendReport;
    public boolean isSattlemntAccountVerify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowInsetsControllerCompat controller = new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        controller.setAppearanceLightStatusBars(true);
        controller.setAppearanceLightNavigationBars(true);
        setContentView(R.layout.activity_settlement_bank_list);
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);
        gson = new Gson();

        mLoginDataResponse = gson.fromJson(UtilMethods.INSTANCE.getLoginPref(this), LoginResponse.class);
        deviceId = UtilMethods.INSTANCE.getIMEI(this);
        deviceSerialNum = UtilMethods.INSTANCE.getSerialNo(this);
        findViews();
        HitApi();
    }

    void findViews() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setTitle("Settlement Accounts");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        search_all = findViewById(R.id.search_all);
        findViewById(R.id.clearIcon).setOnClickListener(v -> search_all.setText(""));

        recycler_view = findViewById(R.id.recycler_view);
        recycler_view.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new SettlementAccountAdapter(isSattlemntAccountVerify, mBankListObjects, this);
        recycler_view.setAdapter(mAdapter);


        addBtn = findViewById(R.id.addBtn);


        search_all.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (mAdapter != null) {
                    mAdapter.getFilter().filter(s);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        addBtn.setOnClickListener(v -> {
            startActivityForResult(new Intent(this, UpdateAddSettlementBankActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP), INTENT_UPDATE);
        });

    }


    private void HitApi() {
        if (UtilMethods.INSTANCE.isNetworkAvialable(this)) {
            loader.show();


            try {
                EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
                Call<SettlementAccountResponse> call = git.GetSettlementAccount(new BasicRequest(mLoginDataResponse.getData().getUserID() + "",
                        mLoginDataResponse.getData().getLoginTypeID(),
                        ApplicationConstant.INSTANCE.APP_ID, deviceId, "", BuildConfig.VERSION_NAME,
                        deviceSerialNum, mLoginDataResponse.getData().getSessionID(), mLoginDataResponse.getData().getSession()));
                call.enqueue(new Callback<SettlementAccountResponse>() {
                    @Override
                    public void onResponse(Call<SettlementAccountResponse> call, final retrofit2.Response<SettlementAccountResponse> response) {

                        try {

                            if (loader != null) {
                                if (loader.isShowing()) {
                                    loader.dismiss();
                                }
                            }
                            if (response.isSuccessful()) {
                                if (response.body() != null) {

                                    if (response.body().getStatuscode() == 1) {
                                        isSattlemntAccountVerify = response.body().isSattlemntAccountVerify();
                                        if (response.body().getData() != null && response.body().getData().size() > 0) {

                                            mBankListObjects.clear();
                                            mBankListObjects.addAll(response.body().getData());
                                            mAdapter.setFlag(isSattlemntAccountVerify);
                                            mAdapter.notifyDataSetChanged();
                                        } else {
                                            UtilMethods.INSTANCE.Error(com.solution.brothergroup.Activities.SettlementBankListActivity.this, "Account not found");
                                        }
                                    } else {
                                        if (!response.body().isVersionValid()) {
                                            UtilMethods.INSTANCE.versionDialog(com.solution.brothergroup.Activities.SettlementBankListActivity.this);
                                        } else {
                                            UtilMethods.INSTANCE.Error(com.solution.brothergroup.Activities.SettlementBankListActivity.this, response.body().getMsg() + "");
                                        }


                                    }
                                }
                            } else {

                                UtilMethods.INSTANCE.apiErrorHandle(com.solution.brothergroup.Activities.SettlementBankListActivity.this, response.code(), response.message());
                            }
                        } catch (
                                Exception e) {
                            if (loader != null) {
                                if (loader.isShowing()) {
                                    loader.dismiss();
                                }
                            }

                        }


                    }

                    @Override
                    public void onFailure(Call<SettlementAccountResponse> call, Throwable t) {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        try {
                            if (t instanceof UnknownHostException || t instanceof IOException) {
                                UtilMethods.INSTANCE.NetworkError(com.solution.brothergroup.Activities.SettlementBankListActivity.this);

                            } else if (t instanceof SocketTimeoutException || t instanceof TimeoutException) {
                                UtilMethods.INSTANCE.ErrorWithTitle(com.solution.brothergroup.Activities.SettlementBankListActivity.this, "TIME OUT ERROR", t.getMessage() + "");

                            } else {

                                if (t.getMessage() != null && !t.getMessage().isEmpty()) {
                                    UtilMethods.INSTANCE.ErrorWithTitle(com.solution.brothergroup.Activities.SettlementBankListActivity.this, "FATAL ERROR", t.getMessage() + "");
                                } else {
                                    UtilMethods.INSTANCE.Error(com.solution.brothergroup.Activities.SettlementBankListActivity.this, getResources().getString(R.string.some_thing_error));
                                }
                            }


                        } catch (IllegalStateException ise) {
                            UtilMethods.INSTANCE.Error(com.solution.brothergroup.Activities.SettlementBankListActivity.this, ise.getMessage());

                        }

                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
                UtilMethods.INSTANCE.Error(com.solution.brothergroup.Activities.SettlementBankListActivity.this, e.getMessage());
                if (loader != null) {
                    if (loader.isShowing()) {
                        loader.dismiss();
                    }
                }

            }
        } else {
            UtilMethods.INSTANCE.NetworkError(this);
        }

    }

    public void confirmationDialog(SettlementAccountData operator) {

        if (dialog != null && dialog.isShowing()) {
            return;
        }
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dialog_beneficiary_delete_confirm, null);

        ((TextView) view.findViewById(R.id.message)).setText("Are you sure you want to delete following Account");
        ((TextView) view.findViewById(R.id.beneName)).setText(operator.getAccountHolder());
        ((TextView) view.findViewById(R.id.beneAccountNumber)).setText(operator.getAccountNumber());
        ((TextView) view.findViewById(R.id.beneBank)).setText(operator.getBankName());
        ((TextView) view.findViewById(R.id.beneIFSC)).setText(operator.getIfsc());
        dialog = new Dialog(this, R.style.alert_dialog_light);
        dialog.setCancelable(false);
        dialog.setContentView(view);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.findViewById(R.id.cancel).setOnClickListener(v -> dialog.dismiss());
        view.findViewById(R.id.ok).setOnClickListener(v -> {
            dialog.dismiss();
            if (UtilMethods.INSTANCE.isNetworkAvialable(this)) {
                loader.show();
                loader.setCancelable(false);
                loader.setCanceledOnTouchOutside(false);
                deleteBankApi(operator.getId());

            } else {
                UtilMethods.INSTANCE.NetworkError(this);
            }
        });
        dialog.show();
    }

    public void update(SettlementAccountData operator) {
        startActivityForResult(new Intent(this, UpdateAddSettlementBankActivity.class)
                .putExtra("Data", operator)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP), INTENT_UPDATE);

    }

    public void setDefault(SettlementAccountData operator) {
        defaultSetBankApi(operator.getId());
    }

    public void VerifyOrUtr(SettlementAccountData operator) {
        if (operator.getVerificationStatus() == 0) {
            verifyUser(operator.getId());
        } else {
            sendUTRDialog(operator);
        }
    }

    public void sendUTRDialog(SettlementAccountData data) {
        try {
            if (alertDialogSendReport != null && alertDialogSendReport.isShowing()) {
                return;
            }


            AlertDialog.Builder dialogBuilder;
            dialogBuilder = new AlertDialog.Builder(this);
            alertDialogSendReport = dialogBuilder.create();
            alertDialogSendReport.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            LayoutInflater inflater = getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.dialog_update_utr, null);
            alertDialogSendReport.setView(dialogView);

            final AppCompatEditText mobileEt = dialogView.findViewById(R.id.mobileEt);


            AppCompatTextView cancelBtn = dialogView.findViewById(R.id.cancelBtn);
            AppCompatTextView sendBtn = dialogView.findViewById(R.id.sendBtn);
            AppCompatImageView closeIv = dialogView.findViewById(R.id.closeIv);
            TextView titleTv = dialogView.findViewById(R.id.titleTv);
            TextView notice = dialogView.findViewById(R.id.notice);


            String changeUtr = data.getUtr() + "";
            int length = changeUtr.length();
            if (length > 4) {
                int midlength = length - 4;
                changeUtr = changeUtr.substring(0, 2) + repeatStr(midlength) + changeUtr.substring(length - 2, length);
            } else {
                changeUtr = repeatStr(length);
            }


            notice.setText("We have sent Rs 1 in Your Bank Account With UTR NO " + changeUtr + ". Please check your Statement and enter complete UTR to Verify your Bank Account\\n\\n\n" +
                    "\n" +
                    "हमने आपके बैंक खाते में यूटीआर नं " + changeUtr + " के साथ 1 रुपये भेजे हैं। कृपया अपना बैंक विवरण जांचें और अपना बैंक खाता सत्यापित करने के लिए पूरा UTR दर्ज करें");
            //mobileEt.setText(data.getUtr() + "");


            closeIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialogSendReport.dismiss();
                }
            });

            cancelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialogSendReport.dismiss();
                }
            });

            sendBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mobileEt.getText().toString().isEmpty()) {
                        mobileEt.setError("Please Enter Valid UTR Number");
                        mobileEt.requestFocus();
                        return;
                    }
                    alertDialogSendReport.dismiss();

                    loader.show();
                    udateUTR(data.getId(), mobileEt.getText().toString());
                }
            });


            alertDialogSendReport.show();

        } catch (IllegalStateException ise) {

        } catch (IllegalArgumentException iae) {

        } catch (WindowManager.BadTokenException bde) {
            bde.printStackTrace();
        } catch (RuntimeException re) {
            re.printStackTrace();
        } catch (Exception e) {

        }
    }

    String repeatStr(int count) {
        String str = "";
        for (int i = 0; i < count; i++) {
            str = str + "*";
        }
        return str;
    }

    public void deleteBankApi(int updatedId) {
        try {
            loader.show();
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);

            Call<BasicResponse> call = git.DeleteSettlementAcount(new UpdateSettlementAccountRequest(
                    mLoginDataResponse.getData().getUserID(), mLoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID, deviceId,
                    "", BuildConfig.VERSION_NAME, deviceSerialNum,
                    mLoginDataResponse.getData().getSessionID(), mLoginDataResponse.getData().getSession(),
                    updatedId));

            call.enqueue(new Callback<BasicResponse>() {

                @Override
                public void onResponse(Call<BasicResponse> call, retrofit2.Response<BasicResponse> response) {

                    try {
                        if (response.isSuccessful()) {
                            BasicResponse data = response.body();
                            if (data != null) {
                                if (data.getStatuscode() == 1) {
                                    if (data.getData() != null) {
                                        if (data.getData().getStatuscode() == 1) {
                                            UtilMethods.INSTANCE.Successful(com.solution.brothergroup.Activities.SettlementBankListActivity.this, data.getData().getMsg() + "");

                                            HitApi();
                                        } else {
                                            UtilMethods.INSTANCE.Error(com.solution.brothergroup.Activities.SettlementBankListActivity.this, data.getData().getMsg() + "");

                                        }
                                    } else {
                                        UtilMethods.INSTANCE.Successful(com.solution.brothergroup.Activities.SettlementBankListActivity.this, data.getMsg() + "");

                                        HitApi();
                                    }

                                } else {
                                    //setResult(RESULT_OK);
                                    UtilMethods.INSTANCE.Error(com.solution.brothergroup.Activities.SettlementBankListActivity.this, data.getMsg() + "");
                                }

                            } else {
                                UtilMethods.INSTANCE.Error(com.solution.brothergroup.Activities.SettlementBankListActivity.this, getString(R.string.some_thing_error));
                            }
                            if (loader != null && loader.isShowing()) {
                                loader.dismiss();
                            }
                        } else {
                            if (loader != null && loader.isShowing()) {
                                loader.dismiss();
                            }
                            UtilMethods.INSTANCE.apiErrorHandle(com.solution.brothergroup.Activities.SettlementBankListActivity.this, response.code(), response.message());
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
                        UtilMethods.INSTANCE.apiFailureError(com.solution.brothergroup.Activities.SettlementBankListActivity.this, t);
                    } catch (IllegalStateException ise) {
                        if (loader != null && loader.isShowing()) {
                            loader.dismiss();
                        }
                        UtilMethods.INSTANCE.Error(com.solution.brothergroup.Activities.SettlementBankListActivity.this, getString(R.string.some_thing_error));
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            if (loader != null && loader.isShowing()) {
                loader.dismiss();
            }
            UtilMethods.INSTANCE.Error(com.solution.brothergroup.Activities.SettlementBankListActivity.this, getString(R.string.some_thing_error));
        }

    }

    public void verifyUser(int updatedId) {
        try {
            loader.show();
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);

            Call<BasicResponse> call = git.VerifySettlementAccountOfUser(new UpdateSettlementAccountRequest(
                    mLoginDataResponse.getData().getUserID(), mLoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID, deviceId,
                    "", BuildConfig.VERSION_NAME, deviceSerialNum,
                    mLoginDataResponse.getData().getSessionID(), mLoginDataResponse.getData().getSession(),
                    updatedId));

            call.enqueue(new Callback<BasicResponse>() {

                @Override
                public void onResponse(Call<BasicResponse> call, retrofit2.Response<BasicResponse> response) {

                    try {
                        if (response.isSuccessful()) {
                            BasicResponse data = response.body();
                            if (data != null) {
                                if (data.getStatuscode() == 1) {
                                    if (data.getData() != null) {
                                        if (data.getData().getStatuscode() == 1) {
                                            UtilMethods.INSTANCE.Processing(com.solution.brothergroup.Activities.SettlementBankListActivity.this, data.getData().getMsg() + "");

                                            HitApi();
                                        } else if (data.getData().getStatuscode() == 2) {
                                            UtilMethods.INSTANCE.Successful(com.solution.brothergroup.Activities.SettlementBankListActivity.this, data.getData().getMsg() + "");

                                            HitApi();
                                        } else {
                                            UtilMethods.INSTANCE.Error(com.solution.brothergroup.Activities.SettlementBankListActivity.this, data.getData().getMsg() + "");

                                        }
                                    } else {
                                        UtilMethods.INSTANCE.Successful(com.solution.brothergroup.Activities.SettlementBankListActivity.this, data.getMsg() + "");

                                        HitApi();
                                    }

                                } else {
                                    //setResult(RESULT_OK);
                                    UtilMethods.INSTANCE.Error(com.solution.brothergroup.Activities.SettlementBankListActivity.this, data.getMsg() + "");
                                }

                            } else {
                                UtilMethods.INSTANCE.Error(com.solution.brothergroup.Activities.SettlementBankListActivity.this, getString(R.string.some_thing_error));
                            }
                            if (loader != null && loader.isShowing()) {
                                loader.dismiss();
                            }
                        } else {
                            if (loader != null && loader.isShowing()) {
                                loader.dismiss();
                            }
                            UtilMethods.INSTANCE.apiErrorHandle(com.solution.brothergroup.Activities.SettlementBankListActivity.this, response.code(), response.message());
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
                        UtilMethods.INSTANCE.apiFailureError(com.solution.brothergroup.Activities.SettlementBankListActivity.this, t);
                    } catch (IllegalStateException ise) {
                        if (loader != null && loader.isShowing()) {
                            loader.dismiss();
                        }
                        UtilMethods.INSTANCE.Error(com.solution.brothergroup.Activities.SettlementBankListActivity.this, getString(R.string.some_thing_error));
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            if (loader != null && loader.isShowing()) {
                loader.dismiss();
            }
            UtilMethods.INSTANCE.Error(com.solution.brothergroup.Activities.SettlementBankListActivity.this, getString(R.string.some_thing_error));
        }

    }

    public void udateUTR(int updatedId, String Utr) {
        try {
            loader.show();
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);

            Call<BasicResponse> call = git.UpdateUTRByUser(new UpdateSettlementAccountRequest(
                    mLoginDataResponse.getData().getUserID(), mLoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID, deviceId,
                    "", BuildConfig.VERSION_NAME, deviceSerialNum,
                    mLoginDataResponse.getData().getSessionID(), mLoginDataResponse.getData().getSession(),
                    updatedId, Utr));

            call.enqueue(new Callback<BasicResponse>() {

                @Override
                public void onResponse(Call<BasicResponse> call, retrofit2.Response<BasicResponse> response) {

                    try {
                        if (response.isSuccessful()) {
                            BasicResponse data = response.body();
                            if (data != null) {
                                if (data.getStatuscode() == 1) {
                                    if (data.getData() != null) {
                                        if (data.getData().getStatuscode() == 1) {
                                            UtilMethods.INSTANCE.Successful(com.solution.brothergroup.Activities.SettlementBankListActivity.this, data.getData().getMsg() + "");

                                            HitApi();
                                        } else {
                                            UtilMethods.INSTANCE.Error(com.solution.brothergroup.Activities.SettlementBankListActivity.this, data.getData().getMsg() + "");

                                        }
                                    } else {
                                        UtilMethods.INSTANCE.Successful(com.solution.brothergroup.Activities.SettlementBankListActivity.this, data.getMsg() + "");

                                        HitApi();
                                    }

                                } else {
                                    //setResult(RESULT_OK);
                                    UtilMethods.INSTANCE.Error(com.solution.brothergroup.Activities.SettlementBankListActivity.this, data.getMsg() + "");
                                }

                            } else {
                                UtilMethods.INSTANCE.Error(com.solution.brothergroup.Activities.SettlementBankListActivity.this, getString(R.string.some_thing_error));
                            }
                            if (loader != null && loader.isShowing()) {
                                loader.dismiss();
                            }
                        } else {
                            if (loader != null && loader.isShowing()) {
                                loader.dismiss();
                            }
                            UtilMethods.INSTANCE.apiErrorHandle(com.solution.brothergroup.Activities.SettlementBankListActivity.this, response.code(), response.message());
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
                        UtilMethods.INSTANCE.apiFailureError(com.solution.brothergroup.Activities.SettlementBankListActivity.this, t);
                    } catch (IllegalStateException ise) {
                        if (loader != null && loader.isShowing()) {
                            loader.dismiss();
                        }
                        UtilMethods.INSTANCE.Error(com.solution.brothergroup.Activities.SettlementBankListActivity.this, getString(R.string.some_thing_error));
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            if (loader != null && loader.isShowing()) {
                loader.dismiss();
            }
            UtilMethods.INSTANCE.Error(com.solution.brothergroup.Activities.SettlementBankListActivity.this, getString(R.string.some_thing_error));
        }

    }

    public void defaultSetBankApi(int updatedId) {
        try {
            loader.show();
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);

            Call<BasicResponse> call = git.ToggleDefaulSettlementAcount(new UpdateSettlementAccountRequest(
                    mLoginDataResponse.getData().getUserID(), mLoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID, deviceId,
                    "", BuildConfig.VERSION_NAME, deviceSerialNum,
                    mLoginDataResponse.getData().getSessionID(), mLoginDataResponse.getData().getSession(),
                    updatedId));

            call.enqueue(new Callback<BasicResponse>() {

                @Override
                public void onResponse(Call<BasicResponse> call, retrofit2.Response<BasicResponse> response) {

                    try {
                        if (response.isSuccessful()) {
                            BasicResponse data = response.body();
                            if (data != null) {
                                if (data.getStatuscode() == 1) {
                                    if (data.getData() != null) {
                                        if (data.getData().getStatuscode() == 1) {
                                            UtilMethods.INSTANCE.Successful(com.solution.brothergroup.Activities.SettlementBankListActivity.this, data.getData().getMsg() + "");
                                            setResult(RESULT_OK);
                                            HitApi();
                                        } else {
                                            UtilMethods.INSTANCE.Error(com.solution.brothergroup.Activities.SettlementBankListActivity.this, data.getData().getMsg() + "");

                                        }
                                    } else {
                                        UtilMethods.INSTANCE.Successful(com.solution.brothergroup.Activities.SettlementBankListActivity.this, data.getMsg() + "");

                                        HitApi();
                                    }

                                } else {
                                    //setResult(RESULT_OK);
                                    UtilMethods.INSTANCE.Error(com.solution.brothergroup.Activities.SettlementBankListActivity.this, data.getMsg() + "");
                                }

                            } else {
                                UtilMethods.INSTANCE.Error(com.solution.brothergroup.Activities.SettlementBankListActivity.this, getString(R.string.some_thing_error));
                            }
                            if (loader != null && loader.isShowing()) {
                                loader.dismiss();
                            }
                        } else {
                            if (loader != null && loader.isShowing()) {
                                loader.dismiss();
                            }
                            UtilMethods.INSTANCE.apiErrorHandle(com.solution.brothergroup.Activities.SettlementBankListActivity.this, response.code(), response.message());
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
                        UtilMethods.INSTANCE.apiFailureError(com.solution.brothergroup.Activities.SettlementBankListActivity.this, t);
                    } catch (IllegalStateException ise) {
                        if (loader != null && loader.isShowing()) {
                            loader.dismiss();
                        }
                        UtilMethods.INSTANCE.Error(com.solution.brothergroup.Activities.SettlementBankListActivity.this, getString(R.string.some_thing_error));
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            if (loader != null && loader.isShowing()) {
                loader.dismiss();
            }
            UtilMethods.INSTANCE.Error(com.solution.brothergroup.Activities.SettlementBankListActivity.this, getString(R.string.some_thing_error));
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == INTENT_UPDATE && resultCode == RESULT_OK) {
            setResult(RESULT_OK);
            HitApi();
        }
    }


}
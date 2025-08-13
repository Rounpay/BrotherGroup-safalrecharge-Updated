package com.solution.brothergroup.QRScan.Activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.solution.brothergroup.Api.Request.ReqSendMoney;
import com.solution.brothergroup.Api.Request.UPIPaymentReq;
import com.solution.brothergroup.Api.Response.BalanceResponse;
import com.solution.brothergroup.Api.Response.RechargeReportResponse;
import com.solution.brothergroup.Authentication.dto.LoginResponse;
import com.solution.brothergroup.BuildConfig;

import com.solution.brothergroup.Fragments.Adapter.WalletBalanceAdapter;
import com.solution.brothergroup.Fragments.dto.BalanceType;
import com.solution.brothergroup.R;
import com.solution.brothergroup.Util.ApiClient;
import com.solution.brothergroup.Util.ApplicationConstant;
import com.solution.brothergroup.Util.EndPointInterface;
import com.solution.brothergroup.Util.UtilMethods;
import com.solution.brothergroup.usefull.CustomLoader;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;

public class UPIPayActivity extends AppCompatActivity implements View.OnClickListener {

    private String vpaVal, nameVal;
    private AppCompatTextView bhimUPIText;

    private EditText vpaEdt, amountEdt, beneNamEdt;
    private Button upiPayBtn;
    private CustomLoader loader;
    private RecyclerView walletBalance;
    private BalanceResponse balanceCheckResponse;
    private ArrayList<BalanceType> mBalanceTypes=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowInsetsControllerCompat controller = new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        controller.setAppearanceLightStatusBars(true);
        controller.setAppearanceLightNavigationBars(true);
        setContentView(R.layout.activity_upi_pay);
        if (getIntent() != null) {
            vpaVal = getIntent().getStringExtra("UPI");
            nameVal = getIntent().getStringExtra("Name");
        }
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);
        bhimUPIText = findViewById(R.id.bhimUPIText);
        upiPayBtn = findViewById(R.id.btn_upiPay);
        walletBalance = (RecyclerView) findViewById(R.id.walletBalance);
        walletBalance.setLayoutManager(new LinearLayoutManager(this));
        vpaEdt = findViewById(R.id.edit_vpa);
        amountEdt = findViewById(R.id.edit_amount);
        beneNamEdt = findViewById(R.id.edit_beneName);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setTitle("UPI Payment");
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_icon);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        if (vpaVal != null && !vpaVal.isEmpty())
            vpaEdt.setText(vpaVal);


        if (nameVal != null && !nameVal.isEmpty())
            beneNamEdt.setText(nameVal);

        upiPayBtn.setOnClickListener(this::onClick);
        balanceCheckResponse = (BalanceResponse) getIntent().getSerializableExtra("BalanceData");
        showWalletListPopupWindow();
    }

    private void showWalletListPopupWindow() {

        if (balanceCheckResponse != null) {
            mBalanceTypes.clear();
            if (balanceCheckResponse.getBalanceData().isBalance() && balanceCheckResponse.getBalanceData().isBalanceFund()) {
                mBalanceTypes.add(new BalanceType("Prepaid Wallet", UtilMethods.INSTANCE.formatedAmount(balanceCheckResponse.getBalanceData().getBalance() + "")));
            }
            if (balanceCheckResponse.getBalanceData().isUBalance() && balanceCheckResponse.getBalanceData().isUBalanceFund()) {
                mBalanceTypes.add(new BalanceType("Utility Wallet", UtilMethods.INSTANCE.formatedAmount(balanceCheckResponse.getBalanceData().getuBalance() + "")));

            }
            if (balanceCheckResponse.getBalanceData().isBBalance() && balanceCheckResponse.getBalanceData().isBBalanceFund()) {
                mBalanceTypes.add(new BalanceType("Bank Wallet", UtilMethods.INSTANCE.formatedAmount(balanceCheckResponse.getBalanceData().getbBalance() + "")));
                //  isBankWalletActive = true;
            }
            if (balanceCheckResponse.getBalanceData().isCBalance() && balanceCheckResponse.getBalanceData().isCBalanceFund()) {
                mBalanceTypes.add(new BalanceType("Card Wallet", UtilMethods.INSTANCE.formatedAmount(balanceCheckResponse.getBalanceData().getcBalance() + "")));
            }
            if (balanceCheckResponse.getBalanceData().isIDBalance() && balanceCheckResponse.getBalanceData().isIDBalanceFund()) {
                mBalanceTypes.add(new BalanceType("Registration Wallet", UtilMethods.INSTANCE.formatedAmount(balanceCheckResponse.getBalanceData().getIdBalnace() + "")));
            }
           /* if (balanceCheckResponse.getBalanceData().isAEPSBalance() && balanceCheckResponse.getBalanceData().isAEPSBalanceFund()) {
                mBalanceTypes.add(new BalanceType("Aeps Wallet", UtilMethods.INSTANCE.formatedAmount(balanceCheckResponse.getBalanceData().getAepsBalnace() + "")));
            }*/
            if (balanceCheckResponse.getBalanceData().isPacakgeBalance() && balanceCheckResponse.getBalanceData().isPacakgeBalanceFund()) {
                mBalanceTypes.add(new BalanceType("Package Wallet", UtilMethods.INSTANCE.formatedAmount(balanceCheckResponse.getBalanceData().getPacakgeBalance() + "")));
            }
            if (mBalanceTypes != null && mBalanceTypes.size() > 0) {
                WalletBalanceAdapter mAdapter = new WalletBalanceAdapter(this, mBalanceTypes);
                walletBalance.setAdapter(mAdapter);
            }
        } else {
            SharedPreferences myPreferences = getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
            String balanceResponse = myPreferences.getString(ApplicationConstant.INSTANCE.balancePref, "");
            balanceCheckResponse = new Gson().fromJson(balanceResponse, BalanceResponse.class);
            if (balanceCheckResponse != null) {
                showWalletListPopupWindow();
            }
            return;
        }

    }

    @Override
    public void onClick(View clickView) {

        if (clickView == upiPayBtn) {
            if (validView()) {
                doUPIPayment(this);
            }
        }
    }


    public void doUPIPayment(final Activity context) {
        try {
            loader.show();
            LoginResponse loginResponse = new Gson().fromJson(UtilMethods.INSTANCE.getLoginPref(this), LoginResponse.class);
            UPIPaymentReq paymentReq = new UPIPaymentReq(new ReqSendMoney(vpaEdt.getText().toString().trim(), amountEdt.getText().toString(), nameVal != null ? nameVal : beneNamEdt.getText().toString()), loginResponse.getData().getUserID() + "", loginResponse.getData().getLoginTypeID() + "",
                    ApplicationConstant.INSTANCE.APP_ID,
                    UtilMethods.INSTANCE.getIMEI(UPIPayActivity.this),
                    "", BuildConfig.VERSION_NAME, UtilMethods.INSTANCE.getSerialNo(UPIPayActivity.this), loginResponse.getData().getSession(), loginResponse.getData().getSessionID());

            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<RechargeReportResponse> call = git.doUPIPayment(paymentReq);

            call.enqueue(new Callback<RechargeReportResponse>() {

                @Override
                public void onResponse(Call<RechargeReportResponse> call, retrofit2.Response<RechargeReportResponse> response) {

                    if (loader != null && loader.isShowing())
                        loader.dismiss();
                    if (response.isSuccessful()) {
                        if (response.body() != null) {

                            if (response.body().getStatuscode().equalsIgnoreCase("1") || response.body().getStatuscode().equalsIgnoreCase("2")) {

                                UtilMethods.INSTANCE.Successfulok(response.body().getMsg(), context);
                                vpaEdt.setText("");
                                amountEdt.setText("");


                            } else if (response.body().getStatuscode().equalsIgnoreCase("3")) {

                                UtilMethods.INSTANCE.Error(context, response.body().getMsg() + "");


                            } else {
                                if (response.body().getIsVersionValid() != null && response.body().getIsVersionValid().equalsIgnoreCase("false")) {
                                    UtilMethods.INSTANCE.versionDialog(context);
                                } else {
                                    UtilMethods.INSTANCE.Error(context, response.body().getMsg() + "");
                                }
                            }

                        }
                    } else {
                        UtilMethods.INSTANCE.apiErrorHandle(context, response.code(), response.message());
                    }
                }

                @Override
                public void onFailure(Call<RechargeReportResponse> call, Throwable t) {

                    if (loader != null && loader.isShowing())
                        loader.dismiss();

                    try {
                        UtilMethods.INSTANCE.apiFailureError(context, t);
                    } catch (IllegalStateException ise) {
                        UtilMethods.INSTANCE.Error(context, ise.getMessage());

                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            if (loader != null && loader.isShowing())
                loader.dismiss();
        }

    }

    private boolean validView() {

        if (vpaEdt.getText().toString().isEmpty()) {
            vpaEdt.setError(getResources().getString(R.string.err_empty_field));
            vpaEdt.requestFocus();
            return false;
        }
        if (beneNamEdt.getText().toString().isEmpty()) {
            beneNamEdt.setError(getResources().getString(R.string.err_empty_field));
            beneNamEdt.requestFocus();
            return false;
        } else if (amountEdt.getText().toString().isEmpty()) {
            amountEdt.setError(getResources().getString(R.string.err_empty_field));
            amountEdt.requestFocus();
            return false;
        }
        vpaEdt.setError(null);
        beneNamEdt.setError(null);
        amountEdt.setError(null);
        return true;
    }

}
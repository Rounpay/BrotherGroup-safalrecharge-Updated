package com.solution.brothergroup.QRScan.Activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

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
import com.solution.brothergroup.Util.CustomAlertDialog;
import com.solution.brothergroup.Util.EndPointInterface;
import com.solution.brothergroup.Util.UtilMethods;
import com.solution.brothergroup.usefull.CustomLoader;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;

public class QRPayActivity extends AppCompatActivity {


    private EditText amountEt, remarkEt;
    TextView amtErr;

    private CustomAlertDialog mCustomAlertDialog;
    private String nameQr, upiQr;

    private CustomLoader loader;

    private LoginResponse LoginDataResponse;
    RecyclerView walletBalance;
    private BalanceResponse balanceCheckResponse;
    private ArrayList<BalanceType> mBalanceTypes = new ArrayList<>();
    TextView nameTv, upiIdTv, shortNameTv, amtWordTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowInsetsControllerCompat controller = new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        controller.setAppearanceLightStatusBars(true);
        controller.setAppearanceLightNavigationBars(true);
        setContentView(R.layout.activity_qr_pay);
        mCustomAlertDialog = new CustomAlertDialog(this, true);
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);
        nameQr = getIntent().getStringExtra("Name");
        upiQr = getIntent().getStringExtra("UPI");

        LoginDataResponse = new Gson().fromJson(UtilMethods.INSTANCE.getLoginPref(this), LoginResponse.class);
        walletBalance = (RecyclerView) findViewById(R.id.walletBalance);
        walletBalance.setLayoutManager(new LinearLayoutManager(this));
        nameTv = findViewById(R.id.name);
        upiIdTv = findViewById(R.id.upiId);
        shortNameTv = findViewById(R.id.shortName);
        amtWordTv = findViewById(R.id.amtWord);


        TextView payBtn = findViewById(R.id.payBtn);
        

        amtErr = findViewById(R.id.amtErr);
        amountEt = findViewById(R.id.amount);
        remarkEt = findViewById(R.id.remark);
        if (nameQr != null && !nameQr.isEmpty()) {
            nameTv.setText(nameQr + "");
            String initials = "";
            String[] nameList = nameQr.trim().split(" ");
            for (String s : nameList) {
                if (initials.length() < 2 && !s.matches("\\s*")) {
                    initials += s.charAt(0);
                } else {
                    break;
                }
            }
            shortNameTv.setText(initials + "");
        } else {
            nameTv.setVisibility(View.GONE);
            shortNameTv.setVisibility(View.GONE);
        }
        upiIdTv.setText(upiQr + "");

      /*  amountEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    amtErr.setVisibility(View.GONE);
                    amtWordTv.setVisibility(View.VISIBLE);
                    try {
                        amtWordTv.setText(NumberToWords(Integer.parseInt(s.toString())));
                    } catch (NumberFormatException nfe) {

                    } catch (Exception e) {

                    }
                } else {
                    amtErr.setVisibility(View.GONE);
                    amtWordTv.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });*/
        findViewById(R.id.closeIv).setOnClickListener(v -> finish());

        payBtn.setOnClickListener(v -> {
            amtErr.setVisibility(View.GONE);
            if (amountEt.getText().toString().trim().isEmpty()) {
                amtErr.setText("Please enter valid amount");
                amtErr.setVisibility(View.VISIBLE);
                amountEt.requestFocus();
                return;
            }
            amountEt.setFocusable(false);
            remarkEt.setFocusable(false);
            doUPIPayment(this);

        });
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
                WalletBalanceAdapter mAdapter = new WalletBalanceAdapter(this,mBalanceTypes);
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

    public String NumberToWords(int number) {
        if (number == 0) {
            return "zero";
        }
        if (number < 0) {
            return "minus " + NumberToWords(Math.abs(number));
        }
        String words = "";
        if ((number / 10000000) > 0) {
            words += NumberToWords(number / 10000000) + " Crore ";
            number %= 10000000;
        }
        if ((number / 100000) > 0) {
            words += NumberToWords(number / 100000) + " Lakh ";
            number %= 100000;
        }
        if ((number / 1000) > 0) {
            words += NumberToWords(number / 1000) + " Thousand ";
            number %= 1000;
        }
        if ((number / 100) > 0) {
            words += NumberToWords(number / 100) + " Hundred ";
            number %= 100;
        }
        if (number > 0) {
            if (words != "") {
                words += ""/*"and "*/;
            }
            String[] unitsMap = new String[]{"Zero", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen", "Eighteen", "Nineteen"};
            String[] tensMap = new String[]{"Zero", "Ten", "Twenty", "Thirty", "Forty", "Fifty", "Sixty", "seventy", "Eighty", "Ninety"};
            if (number < 20) {
                words += unitsMap[number];
            } else {
                words += tensMap[number / 10];
                if ((number % 10) > 0) {
                    words += " "/*"-"*/ + unitsMap[number % 10];
                }
            }
        }
        return words;
    }


    public void doUPIPayment(final Activity context) {
        try {
            loader.show();
            UPIPaymentReq paymentReq = new UPIPaymentReq(new ReqSendMoney(upiIdTv.getText().toString().trim(), amountEt.getText().toString(),
                    nameTv.getText().toString()), LoginDataResponse.getData().getUserID() + "",
                    LoginDataResponse.getData().getLoginTypeID() + "",
                    ApplicationConstant.INSTANCE.APP_ID,
                    UtilMethods.INSTANCE.getIMEI(this),
                    "", BuildConfig.VERSION_NAME, UtilMethods.INSTANCE.getSerialNo(this),
                    LoginDataResponse.getData().getSession(), LoginDataResponse.getData().getSessionID());

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
                                amountEt.setText("");
                                remarkEt.setText("");
                                amountEt.setFocusable(true);
                                remarkEt.setFocusable(true);
                                amountEt.setFocusableInTouchMode(true);
                                remarkEt.setFocusableInTouchMode(true);


                            } else if (response.body().getStatuscode().equalsIgnoreCase("3")) {
                                amountEt.setFocusable(true);
                                remarkEt.setFocusable(true);
                                amountEt.setFocusableInTouchMode(true);
                                remarkEt.setFocusableInTouchMode(true);
                                UtilMethods.INSTANCE.Error(context, response.body().getMsg() + "");


                            } else {
                                amountEt.setFocusable(true);
                                remarkEt.setFocusable(true);
                                amountEt.setFocusableInTouchMode(true);
                                remarkEt.setFocusableInTouchMode(true);
                                if (response.body().getIsVersionValid() != null && response.body().getIsVersionValid().equalsIgnoreCase("false")) {
                                    UtilMethods.INSTANCE.versionDialog(context);
                                } else {
                                    UtilMethods.INSTANCE.Error(context, response.body().getMsg() + "");
                                }
                            }

                        }
                    } else {
                        amountEt.setFocusable(true);
                        remarkEt.setFocusable(true);
                        amountEt.setFocusableInTouchMode(true);
                        remarkEt.setFocusableInTouchMode(true);
                        UtilMethods.INSTANCE.apiErrorHandle(context, response.code(), response.message());
                    }
                }

                @Override
                public void onFailure(Call<RechargeReportResponse> call, Throwable t) {
                    amountEt.setFocusable(true);
                    remarkEt.setFocusable(true);
                    amountEt.setFocusableInTouchMode(true);
                    remarkEt.setFocusableInTouchMode(true);
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
            amountEt.setFocusable(true);
            remarkEt.setFocusable(true);
            amountEt.setFocusableInTouchMode(true);
            remarkEt.setFocusableInTouchMode(true);
            if (loader != null && loader.isShowing())
                loader.dismiss();
        }

    }


}

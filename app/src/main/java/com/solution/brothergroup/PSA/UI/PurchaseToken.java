package com.solution.brothergroup.PSA.UI;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.WindowInsetsControllerCompat;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.solution.brothergroup.Api.Object.OperatorList;
import com.solution.brothergroup.Api.Response.BalanceResponse;
import com.solution.brothergroup.Api.Response.NumberListResponse;
import com.solution.brothergroup.R;
import com.solution.brothergroup.RECHARGEANDBBPS.UI.RechargeHistory;
import com.solution.brothergroup.Util.ApplicationConstant;
import com.solution.brothergroup.Util.UtilMethods;
import com.solution.brothergroup.usefull.CustomLoader;

import java.util.ArrayList;

public class PurchaseToken extends AppCompatActivity {
    RadioGroup radioType;
    TextView tv_url, et_amount, tv_balance;
    EditText et_tokenNo;
    Button purchase, cancle;
    WebView webView;
    CustomLoader loader;
    View tokenphysical, tokendigital;
    TextView tokenphysicalTv, tokendigitalTv;
    RadioButton radioPhysical, radioDigital;
    String oid, PANID;
    int totalToken = 0;
    double amount = 0.0;
    TextView purchaseHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowInsetsControllerCompat controller = new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        controller.setAppearanceLightStatusBars(true);
        controller.setAppearanceLightNavigationBars(true);
        setContentView(R.layout.activity_purchase_token);

        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);

        PANID = getIntent().getExtras().getString("PANID", "");

        radioType = findViewById(R.id.radioType);
        et_amount = findViewById(R.id.et_amount);
        tv_balance = findViewById(R.id.tv_balance);
        et_tokenNo = findViewById(R.id.et_tokenNo);
        purchase = findViewById(R.id.purchase);
        cancle = findViewById(R.id.cancle);
        purchaseHistory = findViewById(R.id.purchaseHistory);
        tokendigital = findViewById(R.id.tokendigital);
        tokenphysical = findViewById(R.id.tokenphysical);
        tokenphysicalTv = findViewById(R.id.tokenphysicalTv);
        tokendigitalTv = findViewById(R.id.tokendigitalTv);
        radioPhysical = findViewById(R.id.radioPhysical);
        radioDigital = findViewById(R.id.radioDigital);
        tv_url = findViewById(R.id.tv_url);


        /*------------ ---------Setting Data ----------- -----------*/
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setTitle("Purchase Token");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_icon);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        SharedPreferences prefs = getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String response = prefs.getString(ApplicationConstant.INSTANCE.numberListPref, "");
        Gson gson1 = new Gson();
        NumberListResponse NumberList = gson1.fromJson(response, NumberListResponse.class);
        final ArrayList<OperatorList> opIdList = new ArrayList<>();
        if (NumberList != null) {
            ArrayList<OperatorList> operatorsList = NumberList.getData().getOperators();
            if (operatorsList != null && operatorsList.size() > 0) {
                for (OperatorList op : operatorsList) {
                    if (op.getOpType()==24) { //For PSA opType is 24
                        opIdList.add(op);
                    }

                }
            }
        }

        for (OperatorList operatorList : opIdList) {
            if (operatorList.getName().equalsIgnoreCase("Digital")) {
                tokendigitalTv.setText("1 Token = " + getResources().getString(R.string.rupiya) + " " + operatorList.getMin() + " (Inclusive of Tax) " + operatorList.getName());
                amount = Double.parseDouble("0" + operatorList.getMin());
                oid = operatorList.getOid()+"";
                tokendigital.setVisibility(View.VISIBLE);
                radioDigital.setVisibility(View.VISIBLE);
                radioDigital.setChecked(true);
            }
            if (operatorList.getName().equalsIgnoreCase("Physical")) {
                tokenphysicalTv.setText("1 Token = " + getResources().getString(R.string.rupiya) + " " + operatorList.getMin() + " (Inclusive of Tax) " + operatorList.getName());
                amount = Double.parseDouble("0" + operatorList.getMin());
                oid = operatorList.getOid()+"";
                tokenphysical.setVisibility(View.VISIBLE);
                radioPhysical.setVisibility(View.VISIBLE);
                radioPhysical.setChecked(true);
            }
        }
        String balanceResponse = prefs.getString(ApplicationConstant.INSTANCE.balancePref, "");
        BalanceResponse balanceCheckResponse = new Gson().fromJson(balanceResponse, BalanceResponse.class);
        if (balanceCheckResponse != null && balanceCheckResponse.getBalanceData() != null) {
            double prepaidWallet = balanceCheckResponse.getBalanceData().getBalance();
            tv_balance.setText(getString(R.string.rupiya) + " " + prepaidWallet);
        }
        tv_url.setText("http://www.psaonline.utiitsl.com/psaonline/");


        /*---------------- Setting Listeners ----------------------------*/


        tv_url.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                webView = new WebView(PurchaseToken.this);
                webView.loadUrl("http://www.psaonline.utiitsl.com/psaonline/");
            }
        });
        purchaseHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PurchaseToken.this, RechargeHistory.class)
                        .putExtra("PSA", true)
                        .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));

            }
        });
        findViewById(R.id.radioPhysical).setSelected(true);

        radioType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioPhysical:
                        for (OperatorList operatorList : opIdList) {
                            if (operatorList.getName().equalsIgnoreCase("Physical")) {
                                oid = operatorList.getOid()+"";
                                amount = Double.parseDouble("0" + operatorList.getMin());
                                et_amount.setText("" + Double.parseDouble("0" + et_tokenNo.getText().toString()) * amount);
                            }
                        }
                        break;

                    case R.id.radioDigital:
                        for (OperatorList operatorList : opIdList) {
                            if (operatorList.getName().equalsIgnoreCase("Digital")) {
                                oid = operatorList.getOid()+"";
                                amount = Double.parseDouble("0" + operatorList.getMin());
                                et_amount.setText("" + Double.parseDouble("0" + et_tokenNo.getText().toString()) * amount);
                            }
                        }
                        break;

                }
            }
        });

        et_tokenNo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                et_amount.setText("" + Double.parseDouble("0" + et_tokenNo.getText().toString()) * amount);
            }
        });

        purchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!et_tokenNo.getText().toString().isEmpty()) {
                    if (UtilMethods.INSTANCE.isNetworkAvialable(PurchaseToken.this)) {
                        loader.show();
                        loader.setCancelable(false);
                        loader.setCanceledOnTouchOutside(false);
                        UtilMethods.INSTANCE.GetAppPurchageToken(PurchaseToken.this, et_tokenNo.getText().toString(), oid, PANID, loader, new UtilMethods.ApiCallBack() {
                            @Override
                            public void onSucess(Object object) {
                                loader.show();
                                UtilMethods.INSTANCE.Balancecheck(PurchaseToken.this, loader, new UtilMethods.ApiCallBack() {
                                    @Override
                                    public void onSucess(Object object) {
                                        BalanceResponse mBalanceResponse = (BalanceResponse) object;
                                        if (mBalanceResponse != null && mBalanceResponse.getBalanceData() != null) {
                                            double prepaidWallet = mBalanceResponse.getBalanceData().getBalance();
                                            tv_balance.setText(getString(R.string.rupiya) + " " + prepaidWallet);
                                        }
                                    }
                                });
                            }
                        });
                    } else {
                        UtilMethods.INSTANCE.NetworkError(PurchaseToken.this, getResources().getString(R.string.err_msg_network_title),
                                getResources().getString(R.string.err_msg_network));
                    }
                } else {
                    et_tokenNo.setError("Enter Tokens");
                }

            }
        });

        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        et_tokenNo.setText("1");

    }

}


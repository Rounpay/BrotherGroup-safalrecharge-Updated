package com.solution.brothergroup.DMTWithPipe.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.WindowInsetsControllerCompat;

import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.google.gson.Gson;
import com.solution.brothergroup.Api.Object.BankListObject;
import com.solution.brothergroup.Api.Response.BankListResponse;
import com.solution.brothergroup.DMTWithPipe.networkAPI.UtilsMethodDMTPipe;
import com.solution.brothergroup.R;
import com.solution.brothergroup.Util.ActivityActivityMessage;
import com.solution.brothergroup.Util.ApplicationConstant;
import com.solution.brothergroup.Util.GlobalBus;
import com.solution.brothergroup.Util.UtilMethods;
import com.solution.brothergroup.usefull.CustomLoader;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

public class MoneyTransferNew extends AppCompatActivity implements View.OnClickListener {

    EditText transferAmount, pinPassEt;
    TextView submitButton, closeButton, charge;
    Toolbar toolbar;
    String name, bank, bankId, bankAccount, beneficiaryCode, beneficiaryMobile, channel = "true", amount, senderNumber, ifsc;

    RelativeLayout neftContainer, impsContainer;
    RadioButton neftRadio, impsRadio;
    CustomLoader loader;
    ArrayList<BankListObject> operator = new ArrayList<>();
    BankListResponse operatorList = new BankListResponse();
    boolean NEFTFlag = false;
    boolean IMPSFlag = false;
    int selectedFlag = 2;
    LinearLayout charge_layout;
    TextView bankname, accountNo, Namebene, total;
    int flag = 1;
    private TextView tvView;
    private String oidIntent, sidIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowInsetsControllerCompat controller = new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        controller.setAppearanceLightStatusBars(true);
        controller.setAppearanceLightNavigationBars(true);
        setContentView(R.layout.activity_money_transfer);

        GetId();
    }

    private void GetId() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Money Transfer");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_icon);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);

        oidIntent = getIntent().getExtras().getString("OID", "");
        sidIntent = getIntent().getExtras().getString("SID", "");
        name = getIntent().getExtras().getString("name", "");
        bank = getIntent().getExtras().getString("bank", "");
        bankId = getIntent().getExtras().getString("bankId", "");
        bankAccount = getIntent().getExtras().getString("bankAccount", "");
        beneficiaryCode = getIntent().getExtras().getString("beneficiaryCode", "");
        beneficiaryMobile = getIntent().getExtras().getString("beneficiaryMobile", "");
        ifsc = getIntent().getExtras().getString("ifsc", "");
        transferType();
        neftContainer = (RelativeLayout) findViewById(R.id.neftContainer);
        impsContainer = (RelativeLayout) findViewById(R.id.impsContainer);
        neftRadio = (RadioButton) findViewById(R.id.neftRadio);
        impsRadio = (RadioButton) findViewById(R.id.impsRadio);
        transferAmount = (EditText) findViewById(R.id.transferAmount);
        submitButton = (TextView) findViewById(R.id.submitButton);
        bankname = findViewById(R.id.bankname);
        bankname.setText(bank);
        pinPassEt = findViewById(R.id.pinPassEt);
        accountNo = findViewById(R.id.accountNo);
        accountNo.setText(bankAccount);
        Namebene = findViewById(R.id.Namebene);
        Namebene.setText(name);
        total = findViewById(R.id.total);
        charge = (TextView) findViewById(R.id.charge);
        closeButton = (TextView) findViewById(R.id.closeButton);
        charge_layout = (LinearLayout) findViewById(R.id.charge_layout);
        SharedPreferences prefs = getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        senderNumber = prefs.getString(ApplicationConstant.INSTANCE.senderNumberPref, null);
        if (flag == 1) {
            submitButton.setText("SEND");
        }
        if (UtilMethods.INSTANCE.getPinRequiredPref(this) || UtilMethods.INSTANCE.getDoubleFactorPref(this)) {
            pinPassEt.setVisibility(View.VISIBLE);
        } else {
            pinPassEt.setVisibility(View.GONE);
        }
        SetListener();
    }

    private void SetListener() {
        neftRadio.setOnClickListener(this);
        impsRadio.setOnClickListener(this);
        neftContainer.setOnClickListener(this);
        impsContainer.setOnClickListener(this);
        submitButton.setOnClickListener(this);
        closeButton.setOnClickListener(this);
    }

    public void transferType() {
        SharedPreferences prefs = getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String response = prefs.getString(ApplicationConstant.INSTANCE.bankListPref, null);

        Gson gson = new Gson();
        operatorList = gson.fromJson(response, BankListResponse.class);
        operator = operatorList.getBankMasters();

        for (BankListObject object : operator) {
            if (object.getBankName().equalsIgnoreCase(bank)) {
                if (object.getIsNEFT().equalsIgnoreCase("1")) {
                    NEFTFlag = true;
                }

                if (object.getIsIMPS().equalsIgnoreCase("1")) {
                    IMPSFlag = true;
                }
            } else {
                NEFTFlag = false;
                IMPSFlag = false;
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v == neftContainer || v == neftRadio) {
            neftRadio.setChecked(true);
            impsRadio.setChecked(false);

            selectedFlag = 1;
            channel = "false";
        }

        if (v == impsContainer || v == impsRadio) {
            neftRadio.setChecked(false);
            impsRadio.setChecked(true);

            selectedFlag = 2;
            channel = "true";
        }

        if (v == closeButton) {
            finish();
        }

        if (v == submitButton) {
            if (validationForm() == 0) {
                if (UtilMethods.INSTANCE.isNetworkAvialable(this)) {

                    loader.show();
                    loader.setCancelable(false);
                    loader.setCanceledOnTouchOutside(false);

                    //  UtilMethods.INSTANCE.GetChargedAmount(this, senderNumber, bankAccount, amount, beneficiaryCode, channel,senderTableInfo.getRemaining(), loader);
                    if (flag == 1) {

                        UtilsMethodDMTPipe.INSTANCE.GetChargedAmountNew(this, oidIntent, amount, beneficiaryCode, senderNumber, ifsc, bankAccount, channel, bank, name, loader, MoneyTransferNew.this);
                    } else if (flag == 2) {
                        UtilsMethodDMTPipe.INSTANCE.SendMoneyNew(this,oidIntent, pinPassEt.getText().toString(), beneficiaryCode, senderNumber,
                                ifsc, bankAccount, amount, channel, bank, bankId, name, beneficiaryMobile, loader,
                                MoneyTransferNew.this, submitButton);
                    }
                } else {
                    UtilMethods.INSTANCE.NetworkError(this);
                }
            }
        }
    }

    public int validationForm() {
        int flag = 0;

        if (selectedFlag != 0 && channel != null && channel.length() > 0) {

        } else {
            UtilMethods.INSTANCE.Error(this, "Please specify transfer type");
            flag++;
        }

        if (transferAmount.getText() != null && transferAmount.getText().toString().trim().length() > 0) {
            amount = transferAmount.getText().toString().trim();
        } else {
            transferAmount.setError("Please enter amount");
            transferAmount.requestFocus();
            flag++;
        }

        if (charge_layout.getVisibility() == View.VISIBLE && pinPassEt.getVisibility() == View.VISIBLE && pinPassEt.getText().toString().isEmpty()) {
            pinPassEt.setError("Please Enter Pin Password");
            pinPassEt.requestFocus();
            flag++;
        }

        return flag;
    }

    @Subscribe
    public void onActivityActivityMessage(ActivityActivityMessage activityActivityMessage) {
        if (activityActivityMessage.getFrom().equalsIgnoreCase("SendMoney")) {
            String[] str = activityActivityMessage.getMessage().split(",");
            charge_layout.setVisibility(View.VISIBLE);
            charge.setText(str[0]);
            total.setText(str[1]);
            flag = 2;
            if (flag == 2) {
                submitButton.setText("Comfirm");
            }

        }
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

}

package com.solution.brothergroup.DMTWithPipe.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.WindowInsetsControllerCompat;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.solution.brothergroup.Activities.BankListScreen;
import com.solution.brothergroup.DMTWithPipe.networkAPI.UtilsMethodDMTPipe;
import com.solution.brothergroup.R;
import com.solution.brothergroup.Util.ActivityActivityMessage;
import com.solution.brothergroup.Util.ApplicationConstant;
import com.solution.brothergroup.Util.GlobalBus;
import com.solution.brothergroup.Util.UtilMethods;
import com.solution.brothergroup.usefull.CustomLoader;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class AddBeneficiaryNew extends AppCompatActivity implements View.OnClickListener {

    static final int PICK_CONTACT = 1;
    EditText beneficiaryName, beneficiaryNumber, bank, accountNumber, ifsc, ifscCode;
    TextView accVerify;
    Button create;
    CustomLoader loader;
    String bankId, isNeft,
            isImps,
            accLmt,
            ekO_BankID;
    String bankName;
    String accVerification;
    String shortCode;
    String verified = "1";
    String currentSenderNumber = "";
    String fullIfscCode = "";
    ImageView ivContact;
    private int opTypeIntent;
    private String oidIntent, sidIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowInsetsControllerCompat controller = new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        controller.setAppearanceLightStatusBars(true);
        controller.setAppearanceLightNavigationBars(true);
        setContentView(R.layout.activity_add_beneficiary);
        opTypeIntent = getIntent().getIntExtra("OpType", 0);
        oidIntent = getIntent().getStringExtra("OID");
        sidIntent = getIntent().getStringExtra("SID");
        GetId();
    }

    private void GetId() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Add Beneficiary");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_icon);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        SharedPreferences prefs = this.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, this.MODE_PRIVATE);
        currentSenderNumber = prefs.getString(ApplicationConstant.INSTANCE.senderNumberPref, null);

        beneficiaryName = (EditText) findViewById(R.id.beneficiaryName);
        beneficiaryNumber = (EditText) findViewById(R.id.beneficiaryNumber);

        beneficiaryNumber.setText(currentSenderNumber);
        bank = (EditText) findViewById(R.id.bank);
        bank.setCompoundDrawablesWithIntrinsicBounds(null, null, AppCompatResources.getDrawable(this, R.drawable.ic_keyboard_arrow_down_black_24dp), null);

        accountNumber = (EditText) findViewById(R.id.accountNumber);
        ifsc = (EditText) findViewById(R.id.ifsc);
        ifscCode = (EditText) findViewById(R.id.ifscCode);
        accVerify = (TextView) findViewById(R.id.accVerify);
        create = (Button) findViewById(R.id.create);
        ivContact = (ImageView) findViewById(R.id.iv_contact);

        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);

        SetListener();
    }

    private void SetListener() {
        accVerify.setOnClickListener(this);
        bank.setOnClickListener(this);
        create.setOnClickListener(this);
        ivContact.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == bank) {
            Intent bankIntent = new Intent(this, BankListScreen.class);
            bankIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivityForResult(bankIntent, 4);
        }

        if (v == accVerify) {
            if (validationAddBeneficiary("accVerif") == 0) {

                if (UtilMethods.INSTANCE.isNetworkAvialable(this)) {
                    SharedPreferences prefs = getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, Context.MODE_PRIVATE);
                    loader.show();
                    loader.setCancelable(false);
                    loader.setCanceledOnTouchOutside(false);

                    UtilsMethodDMTPipe.INSTANCE.VerifyAccountNew(this, oidIntent, prefs.getString(ApplicationConstant.INSTANCE.senderNumberPref, ""), ifsc.getText().toString().trim(),
                            accountNumber.getText().toString().trim(),beneficiaryName.getText().toString(), bankName,bankId, loader);

                } else {
                    UtilMethods.INSTANCE.NetworkError(this);
                }
            }
        }

        if (v == create) {
            if (validationAddBeneficiary("") == 0) {
                SharedPreferences prefs = getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, Context.MODE_PRIVATE);

                if (UtilMethods.INSTANCE.isNetworkAvialable(this)) {
                    loader.show();
                    loader.setCancelable(false);
                    loader.setCanceledOnTouchOutside(false);
                    UtilsMethodDMTPipe.INSTANCE.AddBeneficiaryNew(this, oidIntent, sidIntent, "", prefs.getString(ApplicationConstant.INSTANCE.senderNumberPref, ""), beneficiaryNumber.getText().toString().trim(), beneficiaryName.getText().toString().trim(), ifsc.getText().toString().trim(), accountNumber.getText().toString().trim(), bankName, bankId, loader, AddBeneficiaryNew.this);
                } else {
                    UtilMethods.INSTANCE.NetworkError(this);
                }
            }
        }
        if (v == ivContact) {
            Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivityForResult(intent, PICK_CONTACT);
        }
    }

    public int validationAddBeneficiary(String from) {
        int flag = 0;

        if (accountNumber.getText() != null && accountNumber.getText().toString().trim().length() > 0
                && !(accountNumber.getText().toString().trim().length() < 10)) {
        } else {
            accountNumber.setError(getResources().getString(R.string.bene_acc_error));
            accountNumber.requestFocus();
            flag++;
        }

        if (bank.getText() != null && bank.getText().toString().trim().length() > 0) {
        } else {
            bank.setError(getResources().getString(R.string.bene_bank_error));
            bank.requestFocus();
            flag++;
        }
/*
        if (beneficiaryNumber.getText() != null && beneficiaryNumber.getText().toString().trim().length() > 0 &&
                !(beneficiaryNumber.getText().toString().trim().length() < 10)) {
        } else {
            beneficiaryNumber.setError(getResources().getString(R.string.mobilenumber_error));
            beneficiaryNumber.requestFocus();
            flag++;
        }*/

        return flag;
    }

    @Subscribe
    public void onActivityActivityMessage(ActivityActivityMessage activityFragmentMessage) {
        if (activityFragmentMessage.getMessage().equalsIgnoreCase("AccountVerified")) {

            accVerify.setVisibility(View.GONE);
            beneficiaryName.setText("" + activityFragmentMessage.getFrom());
            verified = "1";
        }
        if (activityFragmentMessage.getFrom().equalsIgnoreCase("beneAdded")) {
            beneficiaryName.setText("");
            beneficiaryNumber.setText(currentSenderNumber);
            accountNumber.setText("");
            ifsc.setText("");
            ifscCode.setText("");
            bank.setText("");
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

    /*@Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        switch (reqCode) {
            case (PICK_CONTACT):
                if (resultCode == Activity.RESULT_OK) {

                    Uri contactData = data.getData();
                    Cursor c = this.getContentResolver().query(contactData, null, null, null, null);
                    if (c.moveToFirst()) {

                        String id = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID));
                        String hasPhone = c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
                        String Number = "";

                        if (hasPhone.equalsIgnoreCase("1")) {
                            Cursor phones = this.getContentResolver().query(
                                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id,
                                    null, null);
                            phones.moveToFirst();
                            // For Clear Old Value Of Number...
                            senderNo.setText("");
                            // <------------------------------------------------------------------>
                            Number = phones.getString(phones.getColumnIndex("data1"));
//                            // Log.e("number is:",Number);
                        }
                        String Name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                        if (!Number.equals("")) {
                            SetNumber(Number);
                        } else {
                            Toast.makeText(this, "Please select a valid number", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                break;
        }
    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == 4) {
            if (requestCode == 4) {
                bankId = data.getExtras().getString("bankId");
                bankName = data.getExtras().getString("bankName");
                accVerification = data.getExtras().getString("accVerification");
                shortCode = data.getExtras().getString("shortCode");
                fullIfscCode = data.getExtras().getString("ifsc");
                isNeft = data.getExtras().getString("neft");
                isImps = data.getExtras().getString("imps");
                accLmt = data.getExtras().getString("accLmt");
                ekO_BankID = data.getExtras().getString("ekO_BankID");

                bank.setText("" + bankName);
                if (fullIfscCode != null && fullIfscCode.length() > 0) {
                    // ifscCode.setText("" + shortCode);
                    ifsc.setText(fullIfscCode);
                    ifscCode.setVisibility(View.GONE);
                } else {
                    ifscCode.setText("");
                    ifscCode.setVisibility(View.GONE);
                }

                if (accVerification.equalsIgnoreCase("true"))
                    accVerify.setVisibility(View.VISIBLE);
                else
                    accVerify.setVisibility(View.GONE);
            }
        }
        if (resultCode == Activity.RESULT_OK) {

            Uri contactData = data.getData();
            Cursor c = this.getContentResolver().query(contactData, null, null, null, null);
            if (c.moveToFirst()) {

                String id = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID));
                String hasPhone = c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
                String Number = "";

                if (hasPhone.equalsIgnoreCase("1")) {
                    Cursor phones = this.getContentResolver().query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id,
                            null, null);
                    phones.moveToFirst();
                    // For Clear Old Value Of Number...
                    beneficiaryNumber.setText("");
                    // <------------------------------------------------------------------>
                    Number = phones.getString(phones.getColumnIndex("data1"));
//                            // Log.e("number is:",Number);
                }
                String Name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                if (!Number.equals("")) {
                    SetNumber(Number);
                } else {
                    Toast.makeText(this, "Please select a valid number", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public void SetNumber(final String Number) {
        String Number1 = Number.replace("+91", "");
        String Number2 = Number1.replace("(", "");
        String Number3 = Number2.replace(")", "");
        String Number4 = Number3.replace(" ", "");
        String Number5 = Number4.replace("-", "");
        String Number6 = Number5.replace("_", "");
        beneficiaryNumber.setText(Number6);
    }
}

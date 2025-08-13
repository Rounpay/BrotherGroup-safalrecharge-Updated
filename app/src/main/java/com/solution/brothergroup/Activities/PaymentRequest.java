package com.solution.brothergroup.Activities;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.safalrecharges.imagepicker.ImagePicker;
import com.safalrecharges.imagepicker.OnImagePickedListener;
import com.solution.brothergroup.Api.Object.Bank;
import com.solution.brothergroup.Api.Request.FundRequestToUsers;
import com.solution.brothergroup.Api.Response.FundreqToResponse;
import com.solution.brothergroup.Api.Response.GetBankAndPaymentModeResponse;
import com.solution.brothergroup.Api.Response.RechargeReportResponse;
import com.solution.brothergroup.Api.Response.WalletTypeResponse;
import com.solution.brothergroup.Authentication.dto.LoginResponse;
import com.solution.brothergroup.BuildConfig;
import com.solution.brothergroup.R;
import com.solution.brothergroup.Util.ApiClient;
import com.solution.brothergroup.Util.ApplicationConstant;
import com.solution.brothergroup.Util.EndPointInterface;
import com.solution.brothergroup.Util.GenerateOrderForUPIRequest;
import com.solution.brothergroup.Util.PaymentMode;
import com.solution.brothergroup.Util.UtilMethods;
import com.solution.brothergroup.usefull.CustomLoader;

import java.io.File;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.Toolbar;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import retrofit2.Call;
import retrofit2.Callback;


public class PaymentRequest extends AppCompatActivity implements View.OnClickListener {

    private ImagePicker imagePicker;
    EditText txtTransactionID, ChecknumberID, txtCardNo, txtBranchName, txtUpiId, txtMobileNo, txtAcHolderName;
    TextView number, txtAccountName, txtSelectImage;
    ImageView image;
    Spinner bankRole, banklist, paymentMode;
    EditText txttranferAmount;
    Button btnPaymentSubmit;
    TextView scanQrCode, UpiBtn;
    String RequestedTo = "";
    RadioButton prepaid, utility;
    CustomLoader loader;
    LinearLayout ll_tranLable, ll_AcHolder, ll_cheque, ll_cardNo, ll_branchName, ll_upiId, ll_Moblie, ll_walletTypeView, ll_acHolderName;
    int BankID;
    int PaymentModeID;
    Bank selectedBank, selectedIntentBank;
    /*-----------------Wallet Type ---------------------------*/
    String walletType = "1";
    Spinner wallet_Type_Spinner;
    LinearLayout WalletType;
    private HashMap<String, Integer> walletTypesMap = new HashMap<>();
    private ArrayList<Integer> walletTypesId = new ArrayList<>();
    private boolean isQrCodeModeSelect;
    private File selectedImageFile;
    private int INTENT_UPI = 638;
    int parentIdIntent;
    private String selectedUpiId;
    private int selectedModeBankId;
    private String upiGenrateOrderId = "0";
    private String orderSecureKey = "";
    private LoginResponse LoginDataResponse;
    private boolean isUPIID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_request);
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);
        String LoginResponse = UtilMethods.INSTANCE.getLoginPref(this);
        LoginDataResponse = new Gson().fromJson(LoginResponse, LoginResponse.class);
        selectedIntentBank = (Bank) getIntent().getSerializableExtra("SelectedBank");
        parentIdIntent = getIntent().getIntExtra("ParentId", 0);
        HitApi();
        getIds();

        LocalBroadcastManager
                .getInstance(this)
                .registerReceiver(mNewNotificationReciver, new IntentFilter("New_UPI_Order_Notification_Detect"));

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("photopath", ImagePicker.currentCameraFileName);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey("photopath") && ImagePicker.currentCameraFileName.length() < 5) {
                ImagePicker.currentCameraFileName = savedInstanceState.getString("photopath");
            }
        }

        super.onRestoreInstanceState(savedInstanceState);
    }

    private void HitApi() {
        if (UtilMethods.INSTANCE.isNetworkAvialable(this)) {


            loader.show();
            UtilMethods.INSTANCE.FundRequestTo(this, loader, new UtilMethods.ApiCallBack() {
                @Override
                public void onSucess(Object object) {
                    FundreqToResponse mFundreqToResponse = (FundreqToResponse) object;
                    selectRoleData(mFundreqToResponse);
                }
            });
        } else {
            UtilMethods.INSTANCE.NetworkError(this);
        }
    }


    void selectRoleData(FundreqToResponse fundreqToResponse) {

        final ArrayList<FundRequestToUsers> fundRequestToUsers = fundreqToResponse.getFundRequestToUsers();
        ArrayList<String> arrayList = new ArrayList<String>();
        int selectedRolePos = 0;
        for (int i = 0; i < fundRequestToUsers.size(); i++) {
            arrayList.add(fundRequestToUsers.get(i).getParentName());
            if (fundRequestToUsers.get(i).getParentID() == parentIdIntent) {
                selectedRolePos = i;
            }
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bankRole.setAdapter(arrayAdapter);
        bankRole.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                loader.show();
                UtilMethods.INSTANCE.GetBankAndPaymentMode(PaymentRequest.this, fundRequestToUsers.get(position).getParentID() + "", loader, new UtilMethods.ApiCallBack() {
                    @Override
                    public void onSucess(Object object) {
                        GetBankAndPaymentModeResponse getBankAndPaymentModeResponse = (GetBankAndPaymentModeResponse) object;
                        // selectBankData(getBankAndPaymentModeResponse);
                        getBankData(getBankAndPaymentModeResponse);
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if (parentIdIntent != 0) {
            bankRole.setSelection(selectedRolePos);
        }
    }


    void getBankData(GetBankAndPaymentModeResponse getBankAndPaymentModeResponse) {
        int selectedBankPos = 0;
        final List<Bank> bankList = getBankAndPaymentModeResponse.getBanks();

        ArrayList<String> arrayListBank = new ArrayList<String>();
        if (bankList != null && bankList.size() > 0) {
            for (int i = 0; i < bankList.size(); i++) {
                arrayListBank.add(bankList.get(i).getBankName());
                if (selectedIntentBank != null && selectedIntentBank.getIsqrenable() && bankList.get(i).getIsqrenable() && bankList.get(i).getId() == selectedIntentBank.getId()) {
                    selectedBankPos = i;
                }
            }
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayListBank);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        banklist.setAdapter(arrayAdapter);
        banklist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                BankID = bankList.get(position).getId();
                if (bankList.get(position).getAccountHolder() != null && !bankList.get(position).getAccountHolder().isEmpty()) {
                    txtAccountName.setText(bankList.get(position).getAccountHolder() + "");
                    ll_AcHolder.setVisibility(View.VISIBLE);
                } else {
                    ll_AcHolder.setVisibility(View.GONE);
                }

                number.setText(bankList.get(position).getAccountNo() + "");
                selectedBank = bankList.get(position);
                selectedUpiId = bankList.get(position).getUpinumber();
                if (selectedBank != null && !selectedBank.getIsqrenable()) {
                    scanQrCode.setVisibility(View.GONE);
                } else if (selectedBank != null && selectedBank.getIsqrenable() && isQrCodeModeSelect) {
                    scanQrCode.setVisibility(View.VISIBLE);
                } else {
                    scanQrCode.setVisibility(View.GONE);
                }

                List<PaymentMode> PaymentMode = bankList.get(position).getMode();
                getPaymentMode(PaymentMode);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        banklist.setSelection(selectedBankPos);

        if (bankList == null || bankList.size() == 0) {
            BankID = 0;
            txtAccountName.setText("");
            number.setText("");
            selectedBank = null;
            scanQrCode.setVisibility(View.GONE);
            getPaymentMode(new ArrayList<PaymentMode>());
        }
    }


    void getPaymentMode(final List<PaymentMode> paymentModeList) {
        int selectedBankModePos = 0;
        ArrayList<String> arrayListPaymentMode = new ArrayList<String>();

        if (paymentModeList != null && paymentModeList.size() > 0) {
            for (int i = 0; i < paymentModeList.size(); i++) {
                arrayListPaymentMode.add(paymentModeList.get(i).getMode());
                if (selectedIntentBank != null && selectedIntentBank.getIsqrenable() && paymentModeList.get(i).getModeID() == 6) {
                    selectedBankModePos = i;
                }
            }
        }

        ArrayAdapter<String> arrayAdapterMode = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayListPaymentMode);
        arrayAdapterMode.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        paymentMode.setAdapter(arrayAdapterMode);
        paymentMode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (paymentModeList.get(position).isTransactionIdAuto()) {
                    ll_tranLable.setVisibility(View.GONE);
                } else {
                    ll_tranLable.setVisibility(View.VISIBLE);
                }
                if (paymentModeList.get(position).isAccountHolderRequired()) {
                    ll_acHolderName.setVisibility(View.VISIBLE);
                } else {
                    ll_acHolderName.setVisibility(View.GONE);
                }
                if (paymentModeList.get(position).isCardNumberRequired()) {
                    ll_cardNo.setVisibility(View.VISIBLE);
                } else {
                    ll_cardNo.setVisibility(View.GONE);
                }

                if (paymentModeList.get(position).isBranchRequired()) {
                    ll_branchName.setVisibility(View.VISIBLE);
                } else {
                    ll_branchName.setVisibility(View.GONE);
                }
                if (paymentModeList.get(position).isUPIID()) {
                    ll_upiId.setVisibility(View.VISIBLE);
                } else {
                    ll_upiId.setVisibility(View.GONE);
                }

                if (paymentModeList.get(position).isChequeNoRequired()) {
                    ll_cheque.setVisibility(View.VISIBLE);
                } else {
                    ll_cheque.setVisibility(View.GONE);
                }
                if (paymentModeList.get(position).isMobileNoRequired()) {
                    ll_Moblie.setVisibility(View.VISIBLE);
                } else {
                    ll_Moblie.setVisibility(View.GONE);
                }

                PaymentModeID = paymentModeList.get(position).getModeID();
                selectedModeBankId = paymentModeList.get(position).getBankID();
                if (selectedBank != null && selectedBank.getIsqrenable() && PaymentModeID == 6) {
                    isQrCodeModeSelect = true;
                    scanQrCode.setVisibility(View.VISIBLE);
                } else {
                    isQrCodeModeSelect = false;
                    scanQrCode.setVisibility(View.GONE);
                }


                if (PaymentModeID == 7) {
                    isUPIID = true;
                    UpiBtn.setVisibility(View.VISIBLE);

                } else {
                    isUPIID = false;
                    UpiBtn.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        paymentMode.setSelection(selectedBankModePos);

        if (paymentModeList == null || paymentModeList.size() == 0) {

            ll_tranLable.setVisibility(View.GONE);
            ll_acHolderName.setVisibility(View.GONE);
            ll_cardNo.setVisibility(View.GONE);
            ll_branchName.setVisibility(View.GONE);
            ll_upiId.setVisibility(View.GONE);
            ll_cheque.setVisibility(View.GONE);
            ll_Moblie.setVisibility(View.GONE);
            PaymentModeID = 0;
            scanQrCode.setVisibility(View.GONE);
            UpiBtn.setVisibility(View.GONE);
            isQrCodeModeSelect = false;
        }
    }

   /* void selectBankData(GetBankAndPaymentModeResponse getBankAndPaymentModeResponse) {
        final String[] bankId = {""};
        final String[] bankModetype = {""};
        int selectedBankPos = 0, selectedBankModePos = 0;
        final List<Bank> Bank = getBankAndPaymentModeResponse.getBanks();
        List<PaymentMode> PaymentMode = getBankAndPaymentModeResponse.getPaymentModes();
        ArrayList<String> arrayListBank = new ArrayList<String>();
        final ArrayList<String> BankidID = new ArrayList<String>();
        ArrayList<String> arrayListPaymentMode = new ArrayList<String>();
        final ArrayList<String> PaymentModebutton = new ArrayList<String>();
        for (int i = 0; i < Bank.size(); i++) {
            arrayListBank.add(Bank.get(i).getBankName());
            BankidID.add(Bank.get(i).getId() + "_" + Bank.get(i).getAccountHolder() + "_" + Bank.get(i).getAccountNo());
            if (selectedIntentBank != null && selectedIntentBank.getIsqrenable() && Bank.get(i).getIsqrenable() && Bank.get(i).getId() == selectedIntentBank.getId()) {
                selectedBankPos = i;
            }
        }
        for (int i = 0; i < PaymentMode.size(); i++) {
            arrayListPaymentMode.add(PaymentMode.get(i).getMode());
            PaymentModebutton.add(
                    PaymentMode.get(i).getIsTransactionIdAuto() + "_" +
                            PaymentMode.get(i).getIsAccountHolderRequired() + "_"
                            + PaymentMode.get(i).getIsCardNumberRequired() + "_"
                            + PaymentMode.get(i).getIsChequeNoRequired() + "_"
                            + PaymentMode.get(i).getIsMobileNoRequired() + "_"
                            + PaymentMode.get(i).getId() + "_"
                            + PaymentMode.get(i).getMode());
            if (selectedIntentBank != null && selectedIntentBank.getIsqrenable() && (PaymentMode.get(i).getMode().contains("QR Code") || PaymentMode.get(i).getMode().toLowerCase().contains("qr code"))) {
                selectedBankModePos = i;
            }
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayListBank);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        banklist.setAdapter(arrayAdapter);
        banklist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                bankId[0] = BankidID.get(position);
                String[] str = bankId[0].split("_");
                BankID = str[0];
                txtAccountName.setText(str[1] + "");
                number.setText(str[2] + "");

                selectedBank = Bank.get(position);
                if (selectedBank != null && !selectedBank.getIsqrenable()) {
                    scanQrCode.setVisibility(View.GONE);
                } else if (selectedBank != null && selectedBank.getIsqrenable() && isQrCodeModeSelect) {
                    scanQrCode.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        banklist.setSelection(selectedBankPos);
        ArrayAdapter<String> arrayAdapterMode = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayListPaymentMode);
        arrayAdapterMode.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        paymentMode.setAdapter(arrayAdapterMode);
        paymentMode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                bankModetype[0] = PaymentModebutton.get(position);
                String[] type = bankModetype[0].split("_");
                if (type[0].equals("false")) {
                    ll_tranLable.setVisibility(View.VISIBLE);
                } else {
                    ll_tranLable.setVisibility(View.GONE);
                }
                if (type[1].equals("false")) {
                    ll_AcHolder.setVisibility(View.GONE);
                } else {
                    ll_AcHolder.setVisibility(View.VISIBLE);
                }
                if (type[2].equals("false")) {
                    ll_cardNo.setVisibility(View.GONE);
                } else {
                    ll_cardNo.setVisibility(View.VISIBLE);
                }
                if (type[3].equals("false")) {
                    ll_cheque.setVisibility(View.GONE);
                } else {
                    ll_cheque.setVisibility(View.VISIBLE);
                }
                if (type[4].equals("false")) {
                    ll_Moblie.setVisibility(View.GONE);
                } else {
                    ll_Moblie.setVisibility(View.VISIBLE);
                }

                PaymentModeID = type[5] + "";

                if (selectedBank != null && selectedBank.getIsqrenable() && (type[6] != null && (type[6].contains("QR Code") || type[6].toLowerCase().contains("qr")))) {
                    isQrCodeModeSelect = true;
                    scanQrCode.setVisibility(View.VISIBLE);
                } else {
                    isQrCodeModeSelect = false;
                    scanQrCode.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        paymentMode.setSelection(selectedBankModePos);
    }*/

    private void getIds() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Fund Request");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);
        txtTransactionID = findViewById(R.id.txtTransactionID);
        txtAccountName = findViewById(R.id.txtAccountName);
        txtSelectImage = findViewById(R.id.txtSelectImage);
        image = findViewById(R.id.image);
        ChecknumberID = findViewById(R.id.ChecknumberID);
        txtAcHolderName = findViewById(R.id.txtacHolderName);
        txtCardNo = findViewById(R.id.txtCardNo);
        txtBranchName = findViewById(R.id.txtbranchName);
        txtUpiId = findViewById(R.id.txtupiId);
        scanQrCode = findViewById(R.id.scanQrCode);
        UpiBtn = findViewById(R.id.UpiBtn);
        txtMobileNo = findViewById(R.id.txtMobileNo);
        txttranferAmount = findViewById(R.id.txttranferAmount);
        txttranferAmount.setCompoundDrawablesWithIntrinsicBounds(
                AppCompatResources.getDrawable(this, R.drawable.ic_rupee_indian),
                null, null, null);
        number = findViewById(R.id.number);
        bankRole = findViewById(R.id.bankRole);
        banklist = findViewById(R.id.banklist);
        paymentMode = findViewById(R.id.paymentMode);
        prepaid = findViewById(R.id.prepaid);
        utility = findViewById(R.id.utility);
        WalletType = (LinearLayout) findViewById(R.id.walletType);
        wallet_Type_Spinner = (Spinner) findViewById(R.id.wallet_Type_Spinner);

        ll_tranLable = (LinearLayout) findViewById(R.id.ll_tranLable);
        ll_AcHolder = (LinearLayout) findViewById(R.id.ll_AcHolder);
        ll_cheque = (LinearLayout) findViewById(R.id.ll_cheque);
        ll_cardNo = (LinearLayout) findViewById(R.id.ll_cardNo);
        ll_branchName = (LinearLayout) findViewById(R.id.ll_branchName);
        ll_upiId = (LinearLayout) findViewById(R.id.ll_upiId);
        ll_walletTypeView = (LinearLayout) findViewById(R.id.walletTypeView);
        ll_acHolderName = (LinearLayout) findViewById(R.id.ll_acHolderName);
        ll_Moblie = (LinearLayout) findViewById(R.id.ll_Moblie);
        btnPaymentSubmit = (Button) findViewById(R.id.btnPaymentSubmit);
        gettingWalletType();
        imagePicker = new ImagePicker(this, null, new OnImagePickedListener() {
            @Override
            public void onImagePicked(Uri imageUri) {
                Uri imgUri = imageUri;
                selectedImageFile = new File(imgUri.getPath());
                // image.setImageURI(imgUri);

                RequestOptions requestOptions = new RequestOptions();
                /*requestOptions.transform(new RoundedCornersTransformation(PaymentRequest.this, (int) getResources().getDimension(R.dimen._5sdp), 0, RoundedCornersTransformation.CornerType.RIGHT));*/

                Glide.with(PaymentRequest.this)
                        .load(selectedImageFile)
                        .apply(requestOptions)
                        .into(image);
                // uploadDocApi(new File(imgUri.getPath()));
            }
        }).setWithImageCrop();
        SetListener();
    }

    private void SetListener() {

        prepaid.setOnClickListener(this);
        utility.setOnClickListener(this);
        btnPaymentSubmit.setOnClickListener(this);
        txtSelectImage.setOnClickListener(this);
        wallet_Type_Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                walletType = "" + walletTypesId.get(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        scanQrCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (selectedBank != null && selectedBank.getIsqrenable()) {
                    startActivity(new Intent(PaymentRequest.this, QrCodeActivity.class)
                            .putExtra("SelectedBank", selectedBank != null ? selectedBank : null)
                            .putExtra("FROMINTENT", 1)
                            .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
                } else {
                    Toast.makeText(PaymentRequest.this, "Select QR Code Supported Bank.", Toast.LENGTH_LONG).show();
                }
            }
        });

        UpiBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txttranferAmount.getText() != null && txttranferAmount.getText().toString().trim().isEmpty()) {
                    txttranferAmount.setError("Please enter valid amount!!");
                    txttranferAmount.requestFocus();
                    return;
                } else if (selectedUpiId == null || selectedUpiId.isEmpty() || !selectedUpiId.contains("@")) {
                    UtilMethods.INSTANCE.Error(PaymentRequest.this, "Invalid UPI Payee Address, Change Bank Or Contact to Admin.");
                    return;
                }
                upiGenrateOrderId = "0";
                orderSecureKey = "";
                OrderForUPI(PaymentRequest.this, txttranferAmount.getText().toString().trim(), selectedUpiId);
                /*openUpiIntent(getUPIString(selectedUpiId, txtAccountName.getText().toString().trim(),
                        getString(R.string.app_name).replaceAll(" ", "") + "UPITransaction", txttranferAmount.getText().toString().trim(),
                        ApplicationConstant.INSTANCE.baseUrl));*/

            }
        });
    }


    @Override
    public void onClick(View v) {

        if (v == txtSelectImage) {
            imagePicker.choosePicture(true);
        } else if (v == btnPaymentSubmit) {
            if (validationForm() == 0)
                if (UtilMethods.INSTANCE.isNetworkAvialable(this)) {

                    loader.show();
                    loader.setCancelable(false);
                    loader.setCanceledOnTouchOutside(false);

                    String checksumValue = orderSecureKey +
                            "|" +
                            upiGenrateOrderId +
                            "|" +
                            txttranferAmount.getText().toString().trim() +
                            "|" +
                            LoginDataResponse.getData().getSessionID() +
                            "|" +
                            LoginDataResponse.getData().getUserID() +
                            "|" +
                            selectedUpiId;
                    String upichecksum = AppEncrypt(checksumValue);
                    UtilMethods.INSTANCE.PaymentRequest(this, isUPIID ? selectedUpiId : "", upiGenrateOrderId, upichecksum, selectedImageFile, BankID,
                            txttranferAmount.getText().toString().trim() + "",
                            number.getText().toString().trim() + "",
                            txtTransactionID.getText().toString().trim() + "",
                            ChecknumberID.getText().toString().trim() + "",
                            txtCardNo.getText().toString().trim() + "",
                            txtMobileNo.getText().toString().trim() + "",
                            ll_acHolderName.getVisibility() == View.VISIBLE ? txtAcHolderName.getText().toString().trim() : txtAccountName.getText().toString().trim(),
                            PaymentModeID, walletType, btnPaymentSubmit, loader, LoginDataResponse, new UtilMethods.ApiCallBack() {
                                @Override
                                public void onSucess(Object object) {
                                    GetBankAndPaymentModeResponse modeResponse = (GetBankAndPaymentModeResponse) object;
                                    txtMobileNo.setText("");
                                    txtTransactionID.setText("");
                                    txttranferAmount.setText("");
                                    upiGenrateOrderId = "0";
                                    orderSecureKey = "";

                                }
                            });

                } else {
                    UtilMethods.INSTANCE.NetworkError(this);
                }

        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        imagePicker.handlePermission(requestCode, grantResults);
    }

    private int validationForm() {
        int flag = 0;
        if (number.getText() != null && number.getText().toString().trim().isEmpty()) {
            number.setError("Please enter valid account number!!");
            number.requestFocus();
            flag++;
        } else if (txttranferAmount.getText() != null && txttranferAmount.getText().toString().trim().isEmpty()) {
            txttranferAmount.setError("Please enter valid amount!!");
            txttranferAmount.requestFocus();
            flag++;
        } else if (ll_acHolderName.getVisibility() == View.VISIBLE && txtAcHolderName.getText() != null && txtAcHolderName.getText().toString().trim().isEmpty()) {

            txtAcHolderName.setError("Please enter valid info!!");
            txtAcHolderName.requestFocus();
            flag++;

        } else if (ll_tranLable.getVisibility() == View.VISIBLE && txtTransactionID.getText() != null && txtTransactionID.getText().toString().trim().isEmpty()) {

            txtTransactionID.setError("Please enter valid info!!");
            txtTransactionID.requestFocus();
            flag++;

        } else if (ll_cheque.getVisibility() == View.VISIBLE && ChecknumberID.getText() != null && ChecknumberID.getText().toString().trim().isEmpty()) {

            ChecknumberID.setError("Please enter valid Check Number!!");
            ChecknumberID.requestFocus();
            flag++;

        } else if (ll_cardNo.getVisibility() == View.VISIBLE && txtCardNo.getText() != null && txtCardNo.getText().toString().trim().isEmpty()) {

            txtCardNo.setError("Please enter valid Card Number!!");
            txtCardNo.requestFocus();
            flag++;

        } else if (ll_branchName.getVisibility() == View.VISIBLE && txtBranchName.getText() != null && txtBranchName.getText().toString().trim().isEmpty()) {

            txtBranchName.setError("Please enter valid Branch Name!!");
            txtBranchName.requestFocus();
            flag++;

        } else if (ll_upiId.getVisibility() == View.VISIBLE && txtUpiId.getText() != null && txtUpiId.getText().toString().trim().isEmpty() && !txtUpiId.getText().toString().trim().contains("@")) {

            txtUpiId.setError("Please enter valid Upi Id!!");
            txtUpiId.requestFocus();
            flag++;

        } else if (ll_Moblie.getVisibility() == View.VISIBLE && !txtMobileNo.getText().toString().trim().isEmpty() && txtMobileNo.getText().length() != 10) {
            txtMobileNo.setError("Please enter valid mobile number!!");
            txtMobileNo.requestFocus();
            flag++;
        }

        return flag;
    }


    private void gettingWalletType() {

        if (UtilMethods.INSTANCE.getWalletType(this) != null && !UtilMethods.INSTANCE.getWalletType(this).isEmpty()) {
            WalletTypeResponse mWalletTypeResponse = new Gson().fromJson(UtilMethods.INSTANCE.getWalletType(this), WalletTypeResponse.class);
            if (mWalletTypeResponse != null && mWalletTypeResponse.getWalletTypes() != null && mWalletTypeResponse.getWalletTypes().size() > 0) {
                ArrayList<String> walletTypesArray = new ArrayList<>();

                for (int i = 0; i < mWalletTypeResponse.getWalletTypes().size(); i++) {
                    if (mWalletTypeResponse.getWalletTypes().get(i).getInFundProcess()) {
                        walletTypesMap.put(mWalletTypeResponse.getWalletTypes().get(i).getName(), mWalletTypeResponse.getWalletTypes().get(i).getId());
                        walletTypesArray.add(mWalletTypeResponse.getWalletTypes().get(i).getName());
                        walletTypesId.add(mWalletTypeResponse.getWalletTypes().get(i).getId());
                    }
                }
                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                        (this, android.R.layout.simple_spinner_item,
                                walletTypesArray.toArray(new String[walletTypesArray.size()])); //selected item will look like a spinner set from XML
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                        .simple_spinner_dropdown_item);
                wallet_Type_Spinner.setAdapter(spinnerArrayAdapter);
                if (walletTypesId.size() > 0) {
                    walletType = "" + walletTypesId.get(0);
                }
                if (walletTypesArray.size() > 1) {
                    ll_walletTypeView.setVisibility(View.VISIBLE);
                } else {
                    ll_walletTypeView.setVisibility(View.GONE);
                }
            } else {
                WalletType.setVisibility(View.GONE);
                UtilMethods.INSTANCE.WalletType(this, null);
            }
        } else {
            WalletType.setVisibility(View.GONE);
            UtilMethods.INSTANCE.WalletType(this, null);
        }
    }

    private Uri getUPIString(String payeeAddress, String payeeName,
                             String trxnNote, String payeeAmount, String refUrl) {
        /* String UPI = "upi://pay?pa=" + payeeAddress + "&pn=" + payeeName + *//*"&mc=" + payeeMCC +*//* *//*"&tid=" + trxnID +*//* *//*"&tr=" + trxnRefId
                +*//* "&tn=" + trxnNote + "&am=" + payeeAmount + "&cu=" + currencyCode
                + "&refUrl=" + refUrl;*/
        // String UPI = "upi://pay?pa=" + payeeAddress + "&pn=" + payeeName + "&tn=" + trxnNote + "&am=" + payeeAmount + "&refUrl=" + refUrl;

        Uri uri =
                new Uri.Builder()
                        .scheme("upi")
                        .authority("pay")
                        .appendQueryParameter("pa", payeeAddress)
                        .appendQueryParameter("pn", payeeName)
                        /* .appendQueryParameter("mc", "your-merchant-code")
                         .appendQueryParameter("tr", "your-transaction-ref-id")*/
                        .appendQueryParameter("tn", trxnNote)
                        .appendQueryParameter("am", payeeAmount)
                        .appendQueryParameter("cu", "INR")
                        .appendQueryParameter("url", refUrl)
                        .build();
        return uri;
    }

    void openUpiIntent(Uri Upi) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Upi);
        Intent chooser = Intent.createChooser(intent, "Pay with...");
        chooser.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            startActivityForResult(chooser, INTENT_UPI, null);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == INTENT_UPI) {

            if (data != null && resultCode == RESULT_OK) {
                String paymentResponse, txnId, status, txnRef, ApprovalRefNo, TrtxnRef, responseCode, bleTxId;
                paymentResponse = data.getStringExtra("response");
                txnId = data.getStringExtra("txnId");
                status = data.getStringExtra("Status");
                txnRef = data.getStringExtra("txnRef");
                ApprovalRefNo = data.getStringExtra("ApprovalRefNo");
                TrtxnRef = data.getStringExtra("TrtxnRef");
                responseCode = data.getStringExtra("responseCode");
                bleTxId = data.getStringExtra("bleTxId");/*TrtxnRef*/


                if ((status == null || status.isEmpty() || status.toLowerCase().contains("null") || status.toLowerCase().contains("undefined"))
                        && paymentResponse != null && !paymentResponse.isEmpty() && !paymentResponse.toLowerCase().equalsIgnoreCase("undefined")
                        && paymentResponse.contains("&") && paymentResponse.contains("=")) {

                    String[] splitArray = TextUtils.split(paymentResponse, "&");

                    for (int i = 0; i < splitArray.length; i++) {
                        if (splitArray[i].contains("txnId=")) {
                            txnId = splitArray[i].replace("txnId=", "").trim();
                        }
                        if (splitArray[i].contains("Status=")) {
                            status = splitArray[i].replace("Status=", "").trim();
                        }
                        if (splitArray[i].contains("txnRef=")) {
                            txnRef = splitArray[i].replace("txnRef=", "").trim();
                        }
                        if (splitArray[i].contains("ApprovalRefNo=")) {
                            ApprovalRefNo = splitArray[i].replace("ApprovalRefNo=", "").trim();
                        }
                        if (splitArray[i].contains("TrtxnRef=")) {
                            TrtxnRef = splitArray[i].replace("TrtxnRef=", "").trim();
                        }
                        if (splitArray[i].contains("responseCode=")) {
                            responseCode = splitArray[i].replace("responseCode=", "").trim();
                        }
                        if (splitArray[i].contains("bleTxId=")) {
                            bleTxId = splitArray[i].replace("bleTxId=", "").trim();
                        }
                    }

                }

                if (status != null) {
                    if (status.toLowerCase().equalsIgnoreCase("success")) {
                        if (txnRef != null && !txnRef.isEmpty() && !txnRef.equalsIgnoreCase("null") && !txnRef.toLowerCase().contains("undefined")) {
                            txtTransactionID.setText(txnRef);
                            onClick(btnPaymentSubmit);
                        } else if (ApprovalRefNo != null && !ApprovalRefNo.isEmpty() && !ApprovalRefNo.equalsIgnoreCase("null") && !ApprovalRefNo.toLowerCase().contains("undefined")) {
                            txtTransactionID.setText(ApprovalRefNo);
                            onClick(btnPaymentSubmit);
                        } else if (TrtxnRef != null && !TrtxnRef.isEmpty() && !TrtxnRef.equalsIgnoreCase("null") && !TrtxnRef.toLowerCase().contains("undefined")) {
                            txtTransactionID.setText(TrtxnRef);
                            onClick(btnPaymentSubmit);
                        } else {
                            UtilMethods.INSTANCE.SuccessfulWithTitle(this, "Success", "Transaction Successful.");
                        }
                    } else if (status.toLowerCase().equalsIgnoreCase("submitted")) {
                        upiGenrateOrderId = "0";
                        orderSecureKey = "";
                        UtilMethods.INSTANCE.ProcessingWithTitle(this, "Submitted", "Transaction Submitted, Please Wait After Confirmation You Can Add Fund Request.");
                    } else {
                        upiGenrateOrderId = "0";
                        orderSecureKey = "";
                        UtilMethods.INSTANCE.ErrorWithTitle(this, "Failed", "Transaction Failed, Please Try After Some Time.");
                    }
                } else {
                    upiGenrateOrderId = "0";
                    orderSecureKey = "";
                }

                /*Toast.makeText(PaymentRequest.this, data.getExtras() + "", Toast.LENGTH_LONG).show();
                 Log.e("UPI",paymentResponse+"");
                   Log.e("UPI",data.getExtras()+"");
                   Log.e("UPI",data.getData()+"");*/

                /*Bundle[{Status=Success, isExternalMerchant=true, txnRef=uDK7591578462474072lpif, TRANSACTION_STATUS=3, response=txnId=Airpay1139Rjc1578462474071&txnRef=uDK7591578462474072lpif&Status=Success&responseCode=00, bleTxId=P2001081118229818376376, txnId=Airpay1139Rjc1578462474071, responseCode=00}]*/
                /*Bundle[{Status=SUCCESS, txnRef=fjD8091578483039469owLn, ApprovalRefNo=, response=txnId=Airpay1297Fyo1578483039469&responseCode=0&Status=SUCCESS&txnRef=fjD8091578483039469owLn, txnId=Airpay1297Fyo1578483039469, responseCode=0, TrtxnRef=fjD8091578483039469owLn}]*/

                /*txnId=AXI17152979abdf4b2b9a9e141083936913&responseCode=&Status=SUBMITTED&txnRef=*/
                /*txnId=SBIfa2b4b8c907a4226bf8829e8769b55f7&responseCode=UP00&Status=SUCCESS&txnRef=000817212057*/
                /*txnId=139Zas1578403940921Ys4T&responseCode=ZD&Status=FAILURE&txnRef=1375qE1578403940921BFMf*/
            } else {
                upiGenrateOrderId = "0";
                orderSecureKey = "";
            }
        } else {
            imagePicker.handleActivityResult(resultCode, requestCode, data);
        }
    }


    private void OrderForUPI(final Activity context, String amt, String upiID) {
        try {
            loader.show();
            String mLoginResponse = UtilMethods.INSTANCE.getLoginPref(context);
            LoginResponse LoginDataResponse = new Gson().fromJson(mLoginResponse, LoginResponse.class);
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<RechargeReportResponse> call = git.AppGenerateOrderForUPI(new GenerateOrderForUPIRequest(amt, upiID,
                    LoginDataResponse.getData().getUserID(),
                    LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession(),
                    ApplicationConstant.INSTANCE.APP_ID,
                    UtilMethods.INSTANCE.getIMEI(context),
                    "", BuildConfig.VERSION_NAME, UtilMethods.INSTANCE.getSerialNo(context), LoginDataResponse.getData().getLoginTypeID()));
            call.enqueue(new Callback<RechargeReportResponse>() {
                @Override
                public void onResponse(Call<RechargeReportResponse> call, final retrofit2.Response<RechargeReportResponse> response) {

                    try {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        if(response.isSuccessful()){
                        if (response.body() != null && response.body().getStatuscode() != null) {
                            if (response.body().getStatuscode().equalsIgnoreCase("1")) {
                                upiGenrateOrderId = String.valueOf(response.body().getOrderID());
                                openUpiIntent(getUPIString(selectedUpiId, txtAccountName.getText().toString().trim(),
                                        getString(R.string.app_name).replaceAll(" ", "") + "UPITransaction", txttranferAmount.getText().toString().trim(),
                                        ApplicationConstant.INSTANCE.baseUrl));
                            } else {
                                if (response.body().getIsVersionValid() != null && response.body().getIsVersionValid().equalsIgnoreCase("false")) {
                                    UtilMethods.INSTANCE.versionDialog(context);
                                } else {
                                    UtilMethods.INSTANCE.Error(context, response.body().getMsg() + "");
                                }
                            }
                        }} else {
                            UtilMethods.INSTANCE.apiErrorHandle(context, response.code(), response.message());
                        }
                    } catch (Exception e) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                    }

                }

                @Override
                public void onFailure(Call<RechargeReportResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            loader.dismiss();
                    }
                    try {
                        UtilMethods.INSTANCE.apiFailureError(context, t);
                    } catch (IllegalStateException ise) {
                        UtilMethods.INSTANCE.Error(context, ise.getMessage());

                    }

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private BroadcastReceiver mNewNotificationReciver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            orderSecureKey = intent.getExtras().getString("ORDER_KEY", "");
        }
    };


    public String md5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            String md5 = number.toString(16);

            while (md5.length() < 32) {
                md5 = "0" + md5;
            }

            return md5;
        } catch (NoSuchAlgorithmException e) {
            return null;
        }

    }

    public String AppEncrypt(String input) {
        return md5(md5(input) + BuildConfig.KEY);
    }




    @Override
    protected void onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mNewNotificationReciver);
        super.onDestroy();
    }
}

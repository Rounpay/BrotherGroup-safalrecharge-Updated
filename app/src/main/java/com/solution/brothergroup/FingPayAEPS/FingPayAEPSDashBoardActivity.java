package com.solution.brothergroup.FingPayAEPS;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.solution.brothergroup.Aeps.dto.SdkDetail;
import com.solution.brothergroup.Api.Object.BankListObject;
import com.solution.brothergroup.Api.Response.BankListResponse;
import com.solution.brothergroup.Authentication.dto.LoginResponse;
import com.solution.brothergroup.FingPayAEPS.dto.AdditionalInfo;
import com.solution.brothergroup.FingPayAEPS.dto.Data;
import com.solution.brothergroup.FingPayAEPS.dto.DeviceInfo;
import com.solution.brothergroup.FingPayAEPS.dto.GenerateDepositOTPResponse;
import com.solution.brothergroup.FingPayAEPS.dto.GetAEPSResponse;
import com.solution.brothergroup.FingPayAEPS.dto.Param;
import com.solution.brothergroup.FingPayAEPS.dto.PidData;
import com.solution.brothergroup.FingPayAEPS.dto.Resp;
import com.solution.brothergroup.FingPayAEPS.dto.Skey;
import com.solution.brothergroup.R;
import com.solution.brothergroup.Util.ApplicationConstant;
import com.solution.brothergroup.Util.DropdownDialog.DropDownDialog;
import com.solution.brothergroup.Util.GetLocation;

import com.solution.brothergroup.Util.RunTimePermissionCheck;
import com.solution.brothergroup.Util.UtilMethods;
import com.solution.brothergroup.usefull.CustomLoader;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.widget.TextViewCompat;

public class FingPayAEPSDashBoardActivity extends AppCompatActivity {

    EditText et_aadhar, et_amount, et_accountNum;
    TextView tv_bank;
    View selectBankView, enterAadharView, enterAccountNoView, deviceView, btnView;
    TextView btn_withdrawl, btn_balance, btn_mini_statement, btn_kyc;
    private CustomLoader loader;
    private int INTENT_BANK = 543;
    private int bankIIN,bankId;
    private int type;
    private int INTENT_READ_DEVICE = 932;
    private SdkDetail intentSdkDetail;
    private View deviceTypeChooserView, transactionTypeChooserView;
    private DropDownDialog mDropDownDialog;
    private int selectedDeviceTypePos = 0;
    private ArrayList<String> arrayListDeviceType = new ArrayList<>();
    private ArrayList<String> arrayListTxnType = new ArrayList<>();
    private TextView deviceType, transactionType;
    private int selectedTxnTypePos = 0;
    private Dialog dialogVerify;
    private RunTimePermissionCheck mRunTimePermissionCheck;
    private GetLocation mGetLocation;
    private int intentInterfaceType;
    private LoginResponse mLoginDataResponse;
    private int INTENT_DEPOSIT_STATUS = 54321;
    private boolean isNotLive;
    private String pidDataApi;
    private String deviceName;
    private BankListResponse mBankListResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fing_pay_aeps_dash_board);
        mRunTimePermissionCheck = new RunTimePermissionCheck();
        SharedPreferences prefs = getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        SharedPreferences myPrefs = getSharedPreferences(ApplicationConstant.INSTANCE.prefNameLoginPref, MODE_PRIVATE);
        String getLoginPref = myPrefs.getString(ApplicationConstant.INSTANCE.LoginPref, "");
        mLoginDataResponse = new Gson().fromJson(getLoginPref, LoginResponse.class);
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);

        mDropDownDialog = new DropDownDialog(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("AEPS");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_icon);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        intentInterfaceType = getIntent().getIntExtra("InterfaceType", 0);
        intentSdkDetail = (SdkDetail) getIntent().getSerializableExtra("SDKDetails");

        selectBankView = findViewById(R.id.selectBankView);
        enterAadharView = findViewById(R.id.enterAadharView);
        enterAccountNoView = findViewById(R.id.enterAccountNoView);
        btnView = findViewById(R.id.btnView);
        deviceView = findViewById(R.id.deviceView);
        deviceTypeChooserView = findViewById(R.id.deviceTypeChooserView);
        transactionTypeChooserView = findViewById(R.id.transactionTypeChooserView);
        deviceType = findViewById(R.id.deviceType);
        transactionType = findViewById(R.id.transactionType);
        et_aadhar = findViewById(R.id.et_aadhar);
        et_amount = findViewById(R.id.et_amount);
        et_accountNum = findViewById(R.id.et_accountNum);
        tv_bank = findViewById(R.id.tv_bank);
        btn_withdrawl = findViewById(R.id.btn_withdrawl);
        btn_balance = findViewById(R.id.btn_balance);
        btn_kyc = findViewById(R.id.btn_kyc);
        btn_mini_statement = findViewById(R.id.btn_mini_statement);


        enterAccountNoView.setVisibility(View.GONE);
        enterAadharView.setVisibility(View.VISIBLE);
        selectBankView.setVisibility(View.VISIBLE);
        btnView.setVisibility(View.VISIBLE);


        btn_kyc.setOnClickListener(v -> {

            startActivity(new Intent(this, EKYCProcessActivity.class)
                    .putExtra("SDKDetail", intentSdkDetail)
                    .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
        });
        btn_withdrawl.setOnClickListener(v -> {

            type = 1;
            fetchDetaill();
        });

        btn_balance.setOnClickListener(v -> {
            type = 2;
            fetchDetaill();
        });
        btn_mini_statement.setOnClickListener(v -> {
            type = 3;
            fetchDetaill();
        });

        if (UtilMethods.INSTANCE.deviceId == null || UtilMethods.INSTANCE.deviceId.isEmpty()) {
            UtilMethods.INSTANCE.deviceId = UtilMethods.INSTANCE.getIMEI(this);
        }

        mGetLocation = new GetLocation(this, loader);
        if (UtilMethods.INSTANCE.getLattitude == 0 || UtilMethods.INSTANCE.getLongitude == 0) {
            mGetLocation.startLocationUpdatesIfSettingEnable((lattitude, longitude) -> {
                UtilMethods.INSTANCE.getLattitude = lattitude;
                UtilMethods.INSTANCE.getLongitude = longitude;
            });
        }

        arrayListDeviceType.add("Mantra");
        arrayListDeviceType.add("Morpho");
        arrayListDeviceType.add("Tatvik");
        arrayListDeviceType.add("Startek");
        arrayListDeviceType.add("Evolute");
       /* arrayListDeviceType.add("Precision");
        arrayListDeviceType.add("Secugen");
        arrayListDeviceType.add("Next Bio");*/

        arrayListTxnType.add("Withdrawal");
        arrayListTxnType.add("Deposit");
        arrayListTxnType.add("AADHAR Pay");
        selectedDeviceTypePos = prefs.getInt(ApplicationConstant.INSTANCE.SELECTED_AEPS_DEVICE_PREF, 0);
        deviceType.setText(arrayListDeviceType.get(selectedDeviceTypePos));
        deviceTypeChooserView.setOnClickListener(v ->
                mDropDownDialog.showDropDownPopup(v, selectedDeviceTypePos, arrayListDeviceType, (clickPosition, value, object) -> {

                            if (selectedDeviceTypePos != clickPosition) {
                                deviceType.setText(value + "");
                                selectedDeviceTypePos = clickPosition;
                                editor.putInt(ApplicationConstant.INSTANCE.SELECTED_AEPS_DEVICE_PREF, clickPosition);
                                editor.commit();
                            }
                        }
                ));
        transactionTypeChooserView.setOnClickListener(v ->
                mDropDownDialog.showDropDownPopup(v, selectedTxnTypePos, arrayListTxnType, (clickPosition, value, object) -> {

                            if (selectedTxnTypePos != clickPosition) {
                                if (clickPosition == 1) {
                                    deviceView.setAlpha(0.4f);
                                    deviceTypeChooserView.setClickable(false);
                                    enterAccountNoView.setVisibility(View.VISIBLE);
                                    enterAadharView.setVisibility(View.GONE);
                                    selectBankView.setVisibility(View.GONE);
                                    btnView.setVisibility(View.GONE);
                                } else {
                                    deviceView.setAlpha(1f);
                                    deviceTypeChooserView.setClickable(true);
                                    enterAccountNoView.setVisibility(View.GONE);
                                    enterAadharView.setVisibility(View.VISIBLE);
                                    selectBankView.setVisibility(View.VISIBLE);
                                    btnView.setVisibility(View.VISIBLE);
                                }
                                transactionType.setText(value + "");
                                selectedTxnTypePos = clickPosition;
                                btn_withdrawl.setText("Continue to " + value);
                            }
                        }
                ));


        findViewById(R.id.bankView).setOnClickListener(v ->
                startActivityForResult(new Intent(this, AEPSBankListScreen.class)
                        .putExtra("BankList", mBankListResponse)
                        .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), INTENT_BANK));


        AEPSWebConnectivity.INSTANCE.GetAEPSBanklist(this, loader,mLoginDataResponse, new UtilMethods.ApiCallBack() {
            @Override
            public void onSucess(Object object) {
                mBankListResponse = (BankListResponse) object;
            }
        });
    }

    void fetchDetaill() {

        if (selectedDeviceTypePos == -1 || deviceType.getText().toString().isEmpty()) {
            NUL(findViewById(R.id.mainView), "Please select device", Color.RED);
            return;
        } else if (selectedTxnTypePos == -1 || transactionType.getText().toString().isEmpty()) {
            NUL(findViewById(R.id.mainView), "Please select transaction type", Color.RED);
            return;
        } else if (selectBankView.getVisibility() == View.VISIBLE && tv_bank.getText().toString().isEmpty()) {
            NUL(findViewById(R.id.mainView), "Please select bank", Color.RED);
            return;
        } else if (enterAadharView.getVisibility() == View.VISIBLE && et_aadhar.getText().toString().isEmpty()) {
            et_aadhar.setError("Please enter valid aadhar number");
            et_aadhar.requestFocus();
            return;
        } else if (enterAadharView.getVisibility() == View.VISIBLE && et_aadhar.getText().toString().length() != 12) {
            et_aadhar.setError("Please enter valid aadhar number");
            et_aadhar.requestFocus();
            return;
        } else if (enterAccountNoView.getVisibility() == View.VISIBLE && et_accountNum.getText().toString().isEmpty()) {
            et_accountNum.setError("Please enter valid account number");
            et_accountNum.requestFocus();
            return;
        } else if (enterAccountNoView.getVisibility() == View.VISIBLE && et_accountNum.getText().toString().length() != 12) {
            et_accountNum.setError("Please enter valid account number");
            et_accountNum.requestFocus();
            return;
        } else if (type == 1 && et_amount.getText().toString().isEmpty()) {
            et_amount.setError("Please enter amount");
            et_amount.requestFocus();
            return;
        }
        if (UtilMethods.INSTANCE.getLattitude != 0 && UtilMethods.INSTANCE.getLongitude != 0) {

            SubmitData();
        } else {
            if (mGetLocation != null) {
                mGetLocation.startLocationUpdates((lattitude, longitude) -> {
                    UtilMethods.INSTANCE.getLattitude = lattitude;
                    UtilMethods.INSTANCE.getLongitude = longitude;
                    SubmitData();
                });
            } else {
                mGetLocation = new GetLocation(this, loader);
                mGetLocation.startLocationUpdates((lattitude, longitude) -> {
                    UtilMethods.INSTANCE.getLattitude = lattitude;
                    UtilMethods.INSTANCE.getLongitude = longitude;
                    SubmitData();
                });
            }
        }

    }

    void SubmitData() {
        loader.show();

        if (selectedTxnTypePos == 1) {
            GenerateDepositOTP(null, null);
        } else {
            if (selectedDeviceTypePos == 0) {
                readDevice("com.mantra.rdservice", "com.mantra.rdservice.RDServiceActivity",
                        "Mantra", null);
            } else if (selectedDeviceTypePos == 1) {
                String fTypeValue= isNotLive ? "0" : "2";

                pidDataApi = "<?xml version=\"1.0\"?> <PidOptions ver=\"1.0\"> <Opts fCount=\"1\" fType=\"" + fTypeValue + "\" iCount=\"0\" pCount=\"0\" format=\"0\" pidVer=\"2.0\" timeout=\"10000\" posh=\"UNKNOWN\" env=\"P\" /> <CustOpts><Param name=\"marphokey\" value=\"\" /></CustOpts> </PidOptions>";

                /* pidData = "<?xml version=\"1.0\"?> <PidOptions ver=\"1.0\"> <Opts fCount=\"1\" fType=\"0\" iCount=\"0\" pCount=\"0\" format=\"0\" pidVer=\"2.0\" timeout=\"10000\" posh=\"UNKNOWN\" env=\"P\" /> <CustOpts><Param name=\"marphokey\" value=\"\" /></CustOpts> </PidOptions>";*/
                readDevice("com.scl.rdservice", "com.scl.rdservice.FingerCaptureActivity",
                        "Marpho", pidDataApi);
            } else if (selectedDeviceTypePos == 2) {
                // tatvik();
                readDevice("com.tatvik.bio.tmf20", "com.tatvik.bio.tmf20.RDMainActivity",
                        "Tatvik", null);
            } else if (selectedDeviceTypePos == 3) {
                // startek();
                readDevice("com.acpl.registersdk", "com.acpl.registersdk.MainActivity",
                        "Startek", null);
            } else if (selectedDeviceTypePos == 4) {
                // evolute();
                readDevice("com.evolute.rdservice", "com.evolute.rdservice.RDserviceActivity",
                        "Evolute", null);
            } else if (selectedDeviceTypePos == 5) {
                // secugen();
                readDevice("com.secugen.rdservice", "com.secugen.rdservice.Capture",
                        "Secugen", null);
            } else if (selectedDeviceTypePos == 6) {
                //  precision();
                Toast.makeText(this, "Coming Soon", Toast.LENGTH_SHORT).show();
                loader.dismiss();
            }
        }
    }


    private void readDevice(String packageName, String serviceName, String name, String piddata) {

        if (NUL(packageName, getPackageManager())) {
            deviceName=name;
            String fTypeValue = isNotLive ? "0" : "2";
            pidDataApi= "<?xml version=\"1.0\"?> <PidOptions ver=\"1.0\"> <Opts fCount=\"1\" fType=\"" + fTypeValue + "\" iCount=\"0\" pCount=\"0\" pgCount=\"2\" format=\"0\" pidVer=\"2.0\" timeout=\"10000\" pTimeout=\"20000\" posh=\"UNKNOWN\" env=\"P\" ></Opts> <CustOpts><Param name=\"" + name + "key\" value=\"\" /></CustOpts> </PidOptions>";
            /*String pidData = "<?xml version=\"1.0\"?> <PidOptions ver=\"1.0\"> <Opts fCount=\"1\" fType="\"0\" iCount=\"0\" pCount=\"0\" pgCount=\"2\" format=\"0\" pidVer=\"2.0\" timeout=\"10000\" pTimeout=\"20000\" posh=\"UNKNOWN\" env=\"P\" ></Opts> <CustOpts><Param name=\"" + name + "key\" value=\"\" /></CustOpts> </PidOptions>";*/
            if (piddata != null && !piddata.isEmpty()) {
                pidDataApi = piddata;
            }
            Intent localIntent = new Intent();
            localIntent.setComponent(new ComponentName(packageName, serviceName));
            localIntent.setAction("in.gov.uidai.rdservice.fp.CAPTURE");
            localIntent.putExtra("PID_OPTIONS", pidDataApi);
            startActivityForResult(localIntent, INTENT_READ_DEVICE);

        } else {
            loader.dismiss();
            Nul(name, packageName);
        }
    }


    void Nul(String name, String packageName) {
        new AlertDialog.Builder(this)
                .setTitle("Get Service")
                .setMessage(name + " RD Services Not Found.Click OK to Download Now.")
                .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                    try {
                        startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=" + packageName)));
                    } catch (Exception localException) {
                        NUL(findViewById(R.id.mainView), "Something went wrong. Please try again later.", Color.RED);
                        localException.printStackTrace();
                    }
                })
                .setNegativeButton(android.R.string.no, null)
                /*.setIcon(android.R.drawable.ic_dialog_alert)*/
                .show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == INTENT_BANK && resultCode == RESULT_OK) {
            if (data != null) {
                BankListObject mBankListObject = (BankListObject) data.getSerializableExtra("Bank");
                tv_bank.setText(mBankListObject.getBankName() + "");
                bankIIN = mBankListObject.getIin();
                bankId = Integer.parseInt(mBankListObject.getId());
                isNotLive = mBankListObject.isNotLive();
            } else {
                NUL(findViewById(R.id.mainView), "Bank not found", Color.RED);
            }
        } else if (requestCode == INTENT_READ_DEVICE) {
            loader.dismiss();
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    String pidData = data.getStringExtra("PID_DATA");
                    pidData(findViewById(R.id.mainView), pidData);
                } else {
                    NUL(findViewById(R.id.mainView), "Didn't receive any data", Color.RED);
                }
            } else {
                NUL(findViewById(R.id.mainView), "Canceled", Color.RED);
            }
        } else {
            if (mGetLocation != null) {
                mGetLocation.onActivityResult(requestCode, resultCode, data);
            }
        }
    }


    public void pidData(View paramRelativeLayout, String paramString) {
        PidData mPidData = new PidData();
        AdditionalInfo mAdditionalInfo = new AdditionalInfo();
        DeviceInfo mDeviceInfo = new DeviceInfo();
        Resp mResp = new Resp();
        Skey mSkey = new Skey();
        Data mData = new Data();
        try {
            if (paramString != null && !paramString.isEmpty() && paramString.contains("<PidData>")) {
                DocumentBuilderFactory localDocumentBuilderFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder localDocumentBuilder = localDocumentBuilderFactory.newDocumentBuilder();
                Document localDocument = localDocumentBuilder.parse(new ByteArrayInputStream(paramString.getBytes("UTF-8")));
                Node localNode = localDocument.getElementsByTagName("PidData").item(0);
                Element localElement1 = (Element) localNode;
                NodeList localNodeList1 = localDocument.getElementsByTagName("Resp");
                Element localElement2 = (Element) localNodeList1.item(0);

                mResp.setErrCode(localElement2.getAttribute("errCode"));
                mResp.setErrInfo(localElement2.getAttribute("errInfo"));
                mResp.setfCount(localElement2.getAttribute("fCount"));
                mResp.setNmPoints(localElement2.getAttribute("nmPoints"));
                mResp.setfType(localElement2.getAttribute("fType"));
                mResp.setqScore(localElement2.getAttribute("qScore"));
                mResp.setiCount(localElement2.getAttribute("iCount"));
                mResp.setiType(localElement2.getAttribute("iType"));
                mResp.setpCount(localElement2.getAttribute("pCount"));
                mResp.setpType(localElement2.getAttribute("pType"));


                if (mResp.getErrCode().equalsIgnoreCase("0") && paramString.contains("<Hmac>")) {
                    NodeList localNodeList2 = localDocument.getElementsByTagName("Skey");
                    Element localElement3 = (Element) localNodeList2.item(0);
                    mSkey.setCi("" + localElement3.getAttribute("ci"));
                    mSkey.setText("" + NUL("Skey", localElement1));

                    NodeList localNodeList3 = localDocument.getElementsByTagName("DeviceInfo");
                    Element localElement4 = (Element) localNodeList3.item(0);
                    mDeviceInfo.setDc("" + localElement4.getAttribute("dc"));
                    mDeviceInfo.setDpId("" + localElement4.getAttribute("dpId"));
                    mDeviceInfo.setMc("" + localElement4.getAttribute("mc"));
                    mDeviceInfo.setMi("" + localElement4.getAttribute("mi"));
                    mDeviceInfo.setRdsId("" + localElement4.getAttribute("rdsId"));
                    mDeviceInfo.setRdsVer("" + localElement4.getAttribute("rdsVer"));

                    NodeList localNodeList4 = ((Element) localNodeList3.item(0)).getElementsByTagName("additional_info");

                    List<Param> mParams = new ArrayList<>();

                    NodeList mNodeList = localNodeList4.item(0).getChildNodes();
                    if (mNodeList != null && mNodeList.getLength() > 0) {
                        for (int i = 0; i < mNodeList.getLength(); i++) {
                            Node node = mNodeList.item(i);
                            if (node instanceof Element) {
                                mParams.add(new Param(((Element) node).getAttribute("name") + "", ((Element) node).getAttribute("value") + ""));
                            }
                        }
                    }

                    mAdditionalInfo.setParam(mParams);
                    mDeviceInfo.setAdditionalInfo(mAdditionalInfo);

                    NodeList localNodeList5 = localDocument.getElementsByTagName("Data");
                    Element localElement6 = (Element) localNodeList5.item(0);
                    mData.setType("" + localElement6.getAttribute("type"));
                    mData.setText("" + NUL("Data", localElement1));


                    mPidData.setHmac("" + NUL("Hmac", localElement1));
                    mPidData.setData(mData);
                    mPidData.setDeviceInfo(mDeviceInfo);
                    mPidData.setResp(mResp);
                    mPidData.setSkey(mSkey);

                    NUL(paramRelativeLayout, "Finger Captured Successfully!", getResources().getColor(R.color.green));
                    if (type == 1) {

                        if (selectedTxnTypePos == 0) {
                            AEPSWebConnectivity.INSTANCE.GetWithdrawlAEPS(this,bankId,deviceName,pidDataApi, paramString,mPidData, et_aadhar.getText().toString(), et_amount.getText().toString(), bankIIN, intentInterfaceType, loader,mLoginDataResponse,UtilMethods.INSTANCE.getLattitude+"", UtilMethods.INSTANCE.getLongitude+"", new UtilMethods.ApiCallBack() {
                                @Override
                                public void onSucess(Object object) {
                                    GetAEPSResponse mGetAEPSResponse = (GetAEPSResponse) object;

                                    Intent intent = new Intent(FingPayAEPSDashBoardActivity.this, AEPSStatusActivity.class);
                                    intent.putExtra("STATUS", mGetAEPSResponse.getStatus());
                                    intent.putExtra("MESSAGE", mGetAEPSResponse.getMsg() + "");
                                    intent.putExtra("LIVE_ID", mGetAEPSResponse.getLiveID() + "");
                                    intent.putExtra("TRANSACTION_ID", mGetAEPSResponse.getTransactionID() + "");
                                    intent.putExtra("OP_NAME", tv_bank.getText().toString());
                                    intent.putExtra("AMOUNT", et_amount.getText().toString());
                                    intent.putExtra("BALANCE", "\u20B9" + UtilMethods.INSTANCE.formatedAmount(mGetAEPSResponse.getBalance() + ""));
                                    intent.putExtra("NUMBER", et_aadhar.getText().toString().replace(et_aadhar.getText().toString().substring(0, 8), "XXXXXXXX"));
                                    intent.putExtra("Device_NAME", deviceType.getText().toString());
                                    startActivity(intent);

                                }
                            });
                        } else if (selectedTxnTypePos == 2) {
                            AEPSWebConnectivity.INSTANCE.GetAadharPay(this,paramString, UtilMethods.INSTANCE.getLattitude+"", UtilMethods.INSTANCE.getLongitude+"", mPidData, et_aadhar.getText().toString(), et_amount.getText().toString(), bankIIN, intentInterfaceType, loader, mLoginDataResponse,new UtilMethods.ApiCallBack() {
                                @Override
                                public void onSucess(Object object) {
                                    GetAEPSResponse mGetAEPSResponse = (GetAEPSResponse) object;

                                    Intent intent = new Intent(FingPayAEPSDashBoardActivity.this, AEPSStatusActivity.class);
                                    intent.putExtra("STATUS", mGetAEPSResponse.getStatus());
                                    intent.putExtra("MESSAGE", mGetAEPSResponse.getMsg() + "");
                                    intent.putExtra("LIVE_ID", mGetAEPSResponse.getLiveID() + "");
                                    intent.putExtra("TRANSACTION_ID", mGetAEPSResponse.getTransactionID() + "");
                                    intent.putExtra("OP_NAME", tv_bank.getText().toString());
                                    intent.putExtra("AMOUNT", et_amount.getText().toString());
                                    intent.putExtra("BALANCE", "\u20B9" + UtilMethods.INSTANCE.formatedAmount(mGetAEPSResponse.getBalance() + ""));
                                    intent.putExtra("NUMBER", et_aadhar.getText().toString().replace(et_aadhar.getText().toString().substring(0, 8), "XXXXXXXX"));
                                    intent.putExtra("Device_NAME", deviceType.getText().toString());

                                    startActivity(intent);

                                }
                            });
                        }

                    }
                    else if (type == 3) {
                        AEPSWebConnectivity.INSTANCE.GetMINIStatementAEPS(this,pidDataApi,deviceName,bankId,paramString, mPidData, et_aadhar.getText().toString(), bankIIN, tv_bank.getText().toString(), intentInterfaceType, loader, mLoginDataResponse,UtilMethods.INSTANCE.getLattitude+"", UtilMethods.INSTANCE.getLongitude+"", new UtilMethods.ApiCallBack() {
                            @Override
                            public void onSucess(Object object) {
                                GetAEPSResponse mGetAEPSResponse = (GetAEPSResponse) object;

                                Intent intent = new Intent(FingPayAEPSDashBoardActivity.this, AEPSMiniStatementActivity.class);
                                intent.putExtra("BALANCE", "\u20B9" + UtilMethods.INSTANCE.formatedAmount(mGetAEPSResponse.getBalance() + ""));
                                intent.putExtra("OP_NAME", tv_bank.getText().toString());
                                intent.putExtra("MINI_STATEMENT", mGetAEPSResponse.getStatements());
                                intent.putExtra("NUMBER", et_aadhar.getText().toString().replace(et_aadhar.getText().toString().substring(0, 8), "XXXXXXXX"));
                                intent.putExtra("Device_NAME", deviceType.getText().toString());
                                startActivity(intent);

                            }
                        });
                    }
                    else {
                        AEPSWebConnectivity.INSTANCE.GetBalanceAEPS(this,pidDataApi,deviceName,bankId, paramString,mPidData, et_aadhar.getText().toString(), bankIIN, intentInterfaceType, loader,mLoginDataResponse,UtilMethods.INSTANCE.getLattitude+"", UtilMethods.INSTANCE.getLongitude+"",  new UtilMethods.ApiCallBack() {
                            @Override
                            public void onSucess(Object object) {
                                GetAEPSResponse mGetAEPSResponse = (GetAEPSResponse) object;
                                NUL("Balance : \u20B9" + UtilMethods.INSTANCE.formatedAmount(mGetAEPSResponse.getBalance() + ""));
                            }
                        });
                    }
                } else {
                    NUL(paramRelativeLayout, "Error Code : " + mResp.getErrCode() + "\n" +
                            "Error Message : " + mResp.getErrInfo() + "", Color.RED);
                }
            } else {
                NUL(paramRelativeLayout, "Didn't receive any data", Color.RED);
            }
        } catch (Exception e) {
            NUL(paramRelativeLayout, e.getMessage() + "", Color.RED);
            e.printStackTrace();
        }

    }

    void GenerateDepositOTP(TextView timerTv, View resendCodeTv) {

        AEPSWebConnectivity.INSTANCE.GenerateDepositOTP(this, UtilMethods.INSTANCE.getLattitude + "",
                UtilMethods.INSTANCE.getLongitude + "", et_accountNum.getText().toString(), et_amount.getText().toString(), 508534, intentInterfaceType, loader, mLoginDataResponse, new UtilMethods.ApiCallBack() {
                    @Override
                    public void onSucess(Object object) {
                        GenerateDepositOTPResponse mGenerateDepositOTPResponse = (GenerateDepositOTPResponse) object;
                        if (mGenerateDepositOTPResponse != null && mGenerateDepositOTPResponse.getStatuscode() == 1) {
                            if (timerTv != null && resendCodeTv != null) {
                                AEPSWebConnectivity.INSTANCE.setTimer(timerTv, resendCodeTv);
                            } else {
                                AEPSWebConnectivity.INSTANCE.openOtpDepositDialog(FingPayAEPSDashBoardActivity.this,
                                        mLoginDataResponse.getData().getMobileNo(), new AEPSWebConnectivity.DialogOTPCallBack() {
                                            @Override
                                            public void onPositiveClick(EditText edMobileOtp, String otpValue, TextView timerTv, View resendCodeTv, Dialog mDialog) {
                                                AEPSWebConnectivity.INSTANCE.VerifyDepositOTP(FingPayAEPSDashBoardActivity.this, UtilMethods.INSTANCE.getLattitude + "", UtilMethods.INSTANCE.getLongitude + "", mGenerateDepositOTPResponse.getReff1(), mGenerateDepositOTPResponse.getReff2(),
                                                        mGenerateDepositOTPResponse.getReff3(), otpValue,
                                                        et_accountNum.getText().toString(), et_amount.getText().toString(),
                                                        508534, intentInterfaceType, loader, mLoginDataResponse, new UtilMethods.ApiCallBack() {
                                                            @Override
                                                            public void onSucess(Object object) {
                                                                mDialog.dismiss();
                                                                GenerateDepositOTPResponse mGenerateDepositVerifyResponse = (GenerateDepositOTPResponse) object;
                                                                openOtpDepositVerifyDialog(FingPayAEPSDashBoardActivity.this, otpValue, et_amount.getText().toString(),
                                                                        et_accountNum.getText().toString().trim(), mGenerateDepositOTPResponse, mGenerateDepositVerifyResponse);
                                                            }
                                                        });
                                            }

                                            @Override
                                            public void onResetCallback(EditText edMobileOtp, String otpValue, TextView timerTv, View resendCodeTv, Dialog mDialog) {
                                                loader.show();
                                                GenerateDepositOTP(timerTv, resendCodeTv);
                                            }
                                        });
                            }


                        } else {

                        }
                    }
                });
    }


    public void openOtpDepositVerifyDialog(final Activity context, String otpValue, String txnAmt, String accountNum,
                                           GenerateDepositOTPResponse genrateResp, GenerateDepositOTPResponse verifyResp) {

        if (dialogVerify != null && dialogVerify.isShowing()) {
            return;
        }
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dialog_deposit_otp_verify, null);

        TextView statusTv = view.findViewById(R.id.statusTv);
        TextView amtTv = view.findViewById(R.id.amtTv);
        TextView nameTv = view.findViewById(R.id.nameTv);
        TextView accountNumTv = view.findViewById(R.id.accountNumTv);
        final ImageView closeIv = view.findViewById(R.id.closeIv);
        final View btnSubmit = view.findViewById(R.id.btnSubmit);
        final View btnCancel = view.findViewById(R.id.btnCancel);


        amtTv.setText(UtilMethods.INSTANCE.formatedAmountWithRupees(txnAmt + ""));
        if (accountNum != null && !accountNum.isEmpty()) {
            accountNumTv.setText(accountNum + "");
        }
        if (verifyResp != null) {
            statusTv.setText(verifyResp.getMsg() + "");
            nameTv.setText(verifyResp.getBeneficiaryName() + "");
        }

        dialogVerify = new Dialog(context, R.style.Theme_AppCompat_Light_Dialog_MinWidth);
        dialogVerify.setCancelable(false);
        dialogVerify.setContentView(view);
        dialogVerify.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        closeIv.setOnClickListener(v -> dialogVerify.dismiss());
        btnCancel.setOnClickListener(v -> dialogVerify.dismiss());


        btnSubmit.setOnClickListener(v -> {
            dialogVerify.dismiss();

            AEPSWebConnectivity.INSTANCE.DepositNow(FingPayAEPSDashBoardActivity.this, UtilMethods.INSTANCE.getLattitude + "", UtilMethods.INSTANCE.getLongitude + "", genrateResp.getReff1(), genrateResp.getReff2(), genrateResp.getReff3(), otpValue, et_accountNum.getText().toString(), et_amount.getText().toString(),
                    508534, intentInterfaceType, loader, mLoginDataResponse, new UtilMethods.ApiCallBack() {
                        @Override
                        public void onSucess(Object object) {
                            GenerateDepositOTPResponse mGenerateDepositResponse = (GenerateDepositOTPResponse) object;

                            if (mGenerateDepositResponse.getBeneficiaryName() == null
                                    && verifyResp.getBeneficiaryName() != null && !verifyResp.getBeneficiaryName().isEmpty()) {
                                mGenerateDepositResponse.setBeneficiaryName(verifyResp.getBeneficiaryName());
                            } else if (mGenerateDepositResponse.getBeneficiaryName().isEmpty()
                                    && verifyResp.getBeneficiaryName() != null && !verifyResp.getBeneficiaryName().isEmpty()) {
                                mGenerateDepositResponse.setBeneficiaryName(verifyResp.getBeneficiaryName());
                            }
                            startActivityForResult(new Intent(FingPayAEPSDashBoardActivity.this, AEPSDepositStatusActivity.class)
                                    .putExtra("data", mGenerateDepositResponse)
                                    .putExtra("txnAmt", et_amount.getText().toString().trim())
                                    .putExtra("accountNum", et_accountNum.getText().toString().trim()), INTENT_DEPOSIT_STATUS);


                        }
                    });

        });


        dialogVerify.show();
    }


    void NUL(String s) {
        new AlertDialog.Builder(this)
                .setTitle("Mini ATM Response")
                .setMessage(s)
                /* .setPositiveButton(android.R.string.yes, (dialog, which) -> finish())*/
                .setNegativeButton(android.R.string.yes, null)
                /*.setIcon(android.R.drawable.ic_dialog_alert)*/
                .show();
    }

    public void NUL(View paramView, String paramString, int paramInt) {
        Snackbar localSnackbar = Snackbar.make(paramView, "" + paramString, Snackbar.LENGTH_LONG);
        View snackBarView = localSnackbar.getView();
        snackBarView.setBackgroundColor(paramInt);
        TextView mainTextView = snackBarView.findViewById(com.google.android.material.R.id.snackbar_text);
        mainTextView.setMaxLines(5);
        TextViewCompat.setTextAppearance(mainTextView, R.style.TextAppearance_AppCompat_Body2);
        localSnackbar.show();

    }

    private String NUL(String paramString, Element paramElement) {
        NodeList localNodeList = paramElement.getElementsByTagName(paramString).item(0).getChildNodes();
        Node localNode = localNodeList.item(0);
        return localNode.getNodeValue();
    }

    public static boolean NUL(String paramString, PackageManager paramPackageManager) {
        try {
            paramPackageManager.getPackageInfo(paramString, 0);
            return true;
        } catch (PackageManager.NameNotFoundException localNameNotFoundException) {
        }
        return false;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == mRunTimePermissionCheck.REQUEST_PERMISSIONS) {
            mRunTimePermissionCheck.onRequestPermissionsResult(this, requestCode, permissions, grantResults, new RunTimePermissionCheck.RequestSucess() {
                @Override
                public void sucess(String key) {
                    UtilMethods.INSTANCE.deviceId = UtilMethods.INSTANCE.getIMEI(FingPayAEPSDashBoardActivity.this);
                    if (mGetLocation != null) {
                        mGetLocation.startLocationUpdates((lattitude, longitude) -> {
                            UtilMethods.INSTANCE.getLattitude = lattitude;
                            UtilMethods.INSTANCE.getLongitude = longitude;
                        });
                    } else {
                        mGetLocation = new GetLocation(FingPayAEPSDashBoardActivity.this, loader);
                        mGetLocation.startLocationUpdates((lattitude, longitude) -> {
                            UtilMethods.INSTANCE.getLattitude = lattitude;
                            UtilMethods.INSTANCE.getLongitude = longitude;
                        });
                    }
                }
            });
        } else {
            if (mGetLocation != null) {
                mGetLocation.onRequestPermissionsResult(requestCode, permissions, grantResults);
            }
        }
    }


    @Override
    protected void onPause() {
        if (mGetLocation != null) {
            mGetLocation.onPause();
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                mRunTimePermissionCheck.checkPhonePermission(this);
            }
        }
    }

}
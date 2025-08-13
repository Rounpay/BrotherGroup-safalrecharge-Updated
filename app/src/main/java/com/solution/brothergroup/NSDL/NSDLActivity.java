package com.solution.brothergroup.NSDL;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.AppBarLayout;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.solution.brothergroup.Aeps.Adapter.ReceiptDetailListAdapter;

import com.solution.brothergroup.Api.Object.ReceiptObject;
import com.solution.brothergroup.Api.Object.RechargeStatus;
import com.solution.brothergroup.Api.Response.NumberListResponse;
import com.solution.brothergroup.Api.Response.OperatorListResponse;

import com.solution.brothergroup.BuildConfig;
import com.solution.brothergroup.Authentication.dto.LoginResponse;
import com.solution.brothergroup.R;
import com.solution.brothergroup.UPIPayment.UI.CompanyProfileResponse;
import com.solution.brothergroup.Util.ApiClient;
import com.solution.brothergroup.Util.ApplicationConstant;
import com.solution.brothergroup.Util.CustomAlertDialog;
import com.solution.brothergroup.Util.EndPointInterface;
import com.solution.brothergroup.Util.UtilMethods;
import com.solution.brothergroup.usefull.CustomLoader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NSDLActivity extends AppCompatActivity {

    EditText email, name, number;
    TextView submitData, panStatus;
    CheckBox checkBox;
    LinearLayout warningBox;
    RelativeLayout operatorChooser, operatorChooser2, genderBtn;
    TextView selectOp, selectOpTwo, gender;
    PopupWindow popup;
    LoginResponse mLoginDataResponse;
    String genderData = "gender";
    String opOneData = "op1";
    String message;
    String order_id;
    String p_order_id;
    String ack_no;
    String date;
    String tid;
    String transactionID;
    String orderId;
    String opTwoData = "op2";
    String opNameData, opMobile, opGender, opEmail, opOid;
    CustomLoader loader;
    CustomAlertDialog customAlertDialog;
    private String deviceId, deviceSerialNum;
    public ArrayList<NsdlUseListModel> OperatorList = new ArrayList<>();
    public ArrayList<String> opName = new ArrayList<>();
    public OperatorListResponse operatorListResponse;
    private ArrayList<RechargeStatus> transactionsObjects = new ArrayList<>();
    private int idOp;
    private final Integer NSDL_INITIATE_REQUEST_CODE=100;
    private NumberListResponse numberListResponse;
    String applicationType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowInsetsControllerCompat controller = new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        controller.setAppearanceLightStatusBars(true);
        controller.setAppearanceLightNavigationBars(true);
        setContentView(R.layout.activity_nsdl);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE |
                        WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        panStatus = findViewById(R.id.panStatus);
        gender = findViewById(R.id.gender);
        genderBtn = findViewById(R.id.genderBtn);
        selectOpTwo = findViewById(R.id.selectOpTwo);
        selectOp = findViewById(R.id.selectOp);
        operatorChooser = findViewById(R.id.oPChooserView);
        operatorChooser2 = findViewById(R.id.oPChooserViewTwo);
        checkBox = findViewById(R.id.checkBox);
        name = findViewById(R.id.et_name);
        number = findViewById(R.id.et_numberData);
        email = findViewById(R.id.et_email);
        warningBox = findViewById(R.id.warningBox);
        Toolbar toolbar = findViewById(R.id.toolbar);
        submitData = findViewById(R.id.submitData);
        Intent intent = getIntent();
        String name = intent.getStringExtra("from");
        idOp = intent.getIntExtra("fromId", 0);
        customAlertDialog = new CustomAlertDialog(this,true);
        warningBox.setBackgroundResource(0);
        //mAppPreferences = UtilMethods.INSTANCE.getAppPreferences(this);
        deviceId = UtilMethods.INSTANCE.getIMEI(this);
        deviceSerialNum = UtilMethods.INSTANCE.getSerialNo(this);
        mLoginDataResponse =  new Gson().fromJson(UtilMethods.INSTANCE.getLoginPref(this), LoginResponse.class);;
        AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams) toolbar.getLayoutParams();
        params.setScrollFlags(0);
        toolbar.setTitle(name);
        toolbar.setTitleTextColor(Color.WHITE);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_icon);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v ->
                onBackPressed());
        checkBox.setTextColor(Color.BLACK);
        checkBox.setHighlightColor(Color.BLACK);
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);
//        NumberListResponse numberListResponse = new NumberListResponse();
        SharedPreferences prefs = this.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String response = prefs.getString(ApplicationConstant.INSTANCE.numberListPref, null);
        Gson gson = new Gson();
        numberListResponse = gson.fromJson(response, NumberListResponse.class);
//        operatorListResponse = new Gson().fromJson(UtilMethods.INSTANCE.getOperatorList(this),OperatorListResponse.class);
        for (int i = 0; i <= numberListResponse.getData().getOperators().size() - 1; i++) {
            if (numberListResponse.getData().getOperators().get(i).getOpType() == idOp) {
                opName.add(numberListResponse.getData().getOperators().get(i).getName());
       /* operatorListResponse = new Gson().fromJson(UtilMethods.INSTANCE.getOperatorList(this),OperatorListResponse.class);
        for (int i = 0; i <= operatorListResponse.getData().getOperators().size() - 1; i++) {
            if (operatorListResponse.getData().getOperators().get(i).getOpType() == idOp) {
                opName.add(operatorListResponse.getData().getOperators().get(i).getName());*/
            }
        }


        submitData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loader.show();
                validionCheck();
            }
        });

        popup = new PopupWindow(this);
        ArrayList<String> op2 = new ArrayList<>();
        op2.add("New");
        op2.add("Modify");

        ArrayList<String> genderList = new ArrayList<>();
        genderList.add("Male");
        genderList.add("Female");
        genderList.add("Other");
        int selectedDeviceTypePos = 0;
        operatorChooser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDropDownPopup(view, selectedDeviceTypePos, opName, (clickPosition, value, object) -> {
                    selectOp.setError(null);
                    selectOp.setText(value);
                    opOneData = value;

                });
            }
        });

        operatorChooser2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDropDownPopup(view, selectedDeviceTypePos, op2, (clickPosition, value, object) -> {
                    selectOpTwo.setError(null);
                    selectOpTwo.setText(value);
                    opTwoData = value;

                });
            }
        });
        genderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDropDownPopup(view, selectedDeviceTypePos, genderList, (clickPosition, value, object) -> {
                    gender.setError(null);
                    gender.setText(value);

                    genderData = value;
                });
            }
        });

        warningBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkBox.isChecked()){
                    checkBox.setChecked(false);
                }else {
                    checkBox.setChecked(true);
                }

            }
        });
    }

    private void openNsdlApp(String authorization) {
        Intent intent = new Intent();
        intent.setClassName( "com.nsdl.panservicedriver",
                "com.nsdl.panservicedriver.MainActivity" );
        intent.putExtra("authorization", authorization);
        intent.putExtra("type", applicationType);
        intent.putExtra("show_receipt", true);
        startActivityForResult(intent, 1001);
    }

    private void validionCheck() {
        loader.dismiss();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        opNameData = name.getText().toString().trim();
        opMobile = number.getText().toString().trim();
        opEmail = email.getText().toString().trim();
        if (opOneData.isEmpty() || opOneData.equalsIgnoreCase("op1")) {
            selectOp.setError("Operator Not Selected..");
            selectOp.requestFocus();
        } else if (opTwoData.isEmpty() || opTwoData.equalsIgnoreCase("op2")) {
            selectOpTwo.setError("Operator Not Selected..");
            selectOpTwo.requestFocus();
        } else if (opNameData.isEmpty()) {
            name.setError("Name Box Empty..");
            name.requestFocus();
        } else if (opMobile.isEmpty()) {
            loader.dismiss();
            number.setError("Number Box Empty..");
            number.requestFocus();
        } else if (opMobile.length() < 10) {
            loader.dismiss();
            number.setError("Number less than ten digit");
            number.requestFocus();
        } else if (genderData.isEmpty() || genderData.equalsIgnoreCase("gender")) {
            loader.dismiss();
            gender.setError("Select Gender");
            gender.requestFocus();
        } else if (email.getText().toString().isEmpty() || !email.getText().toString().matches(emailPattern)) {
            loader.dismiss();
            email.setError("Email Not Valid");
            email.requestFocus();
        } else if (!checkBox.isChecked()) {
            loader.dismiss();
            checkBox.setError("Check The Box");
            checkBox.requestFocus();
            warningBox.setBackgroundResource(R.drawable.red_border);
        } else if (!UtilMethods.INSTANCE.appInstalledOrNot(this, "com.nsdl.panservicedriver")) {
            customAlertDialog.appInstall(NSDLActivity.this, 1);

        } else if (!UtilMethods.INSTANCE.appInstalledOrNot(this, "protean.assisted.ekyc")) {
            customAlertDialog.appInstall(NSDLActivity.this, 2);
        } else {

            checkBox.clearFocus();
            warningBox.setBackgroundResource(0);
            loader.show();
            email.setError(null);
            name.setError(null);
            checkBox.setError(null);
            nasdlInit();


        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loader.dismiss();

    }

    public boolean isAppAvailable(String packageName) {
        PackageManager pm = getPackageManager();
        try {
            pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {

            return false;
        }
    }

    private void nasdlInit() {
        String genderStr;


        if (genderData.equalsIgnoreCase("Male")) {
            genderStr = "M";
        } else if (genderData.equalsIgnoreCase("Female")) {
            genderStr = "F";
        } else {
            genderStr = "T";
        }
        if (opTwoData.equalsIgnoreCase("New")) {
            applicationType = "49A";
        } else {
            applicationType = "CR";
        }
        for (int l = 0; l <= numberListResponse.getData().getOperators().size() - 1; l++) {
            if (opOneData.equalsIgnoreCase(numberListResponse.getData().getOperators().get(l).getName())) {
                opOid = String.valueOf(numberListResponse.getData().getOperators().get(l).getOid());
            }
        }


        EndPointInterface pointInterface = ApiClient.getClient().create(EndPointInterface.class);
        Call<JsonObject> call = pointInterface.GetNsdlPanMitra(new NsdlRequest(
                BuildConfig.VERSION_NAME,
                ApplicationConstant.INSTANCE.APP_ID
                , mLoginDataResponse.getData().getUserID()
                , String.valueOf(mLoginDataResponse.getData().getLoginTypeID())
                , mLoginDataResponse.getData().getSessionID()
                , mLoginDataResponse.getData().getSession()
                , opNameData
                , genderStr
                , opMobile
                , opEmail
                , opOid
                , deviceId
                , applicationType


        ));
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.code() == 200) {
                    loader.dismiss();
                    if (response.body().has("statuscode") &&
                            response.body().getAsJsonObject().get("statuscode").getAsString().equalsIgnoreCase("1")) {
                        if (response.body().getAsJsonObject().has("data")) {
                            JsonObject data = response.body().getAsJsonObject().get("data").getAsJsonObject();
                            if (data.has("token") && !data.get("token").isJsonNull()) {
                                String uri = "";
                                if (!data.get("token").isJsonNull()) {
                                    uri= data.get("token").getAsString();
                                } else {
                                    Toast.makeText(NSDLActivity.this, "token Null ", Toast.LENGTH_SHORT).show();
                                }
                                if (!data.get("tid").isJsonNull()) {
                                    tid= data.get("tid").getAsString();
                                } else {
                                    Toast.makeText(NSDLActivity.this, "tid Null ", Toast.LENGTH_SHORT).show();
                                }
                                if (!data.get("transactionID").isJsonNull()) {
                                    transactionID = data.get("transactionID").getAsString();
                                } else {
                                    transactionID = "";
                                    Toast.makeText(NSDLActivity.this, "transactionID Null ", Toast.LENGTH_SHORT).show();
                                }
                                if (!data.get("orderId").isJsonNull()) {
                                    orderId = data.get("orderId").getAsString();
                                } else {
                                    orderId = "";
                                    System.out.println("order id = "+order_id);
                                }
                                openNsdlApp(uri);
                            } else {
                                UtilMethods.INSTANCE.Error(NSDLActivity.this, "Auth Not Found");
                            }
                        } else {
                            UtilMethods.INSTANCE.Error(NSDLActivity.this, "Auth Not Found");
                        }
                    } else {
                        UtilMethods.INSTANCE.Error(NSDLActivity.this,
                                response.body().getAsJsonObject().get("msg").getAsString());
                    }
                } else {
                    UtilMethods.INSTANCE.Error(NSDLActivity.this, String.valueOf("Error : " +
                            response.code()));
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                if (loader != null && loader.isShowing())
                    loader.dismiss();
                UtilMethods.INSTANCE.apiFailureError(NSDLActivity.this,t);
            }
        });

    }


    public void showDropDownPopup(View v, final int selectPos, ArrayList<String> mDropDownList, ClickDropDownItem mClickDropDownItem) {

        if (popup != null && popup.isShowing()) {
            return;
        }

       // View layout = getLayoutInflater().inflate(R.layout.custom_spinner, null);
        View layout = getLayoutInflater().inflate(R.layout.dialog_drop_down, null);
        RecyclerView recyclerView = layout.findViewById(R.id.recyclerView);
        LinearLayout view = layout.findViewById(R.id.view);
        view.getLayoutParams().width = v.getMeasuredWidth();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

         SpinnerAdapter mDropDownStringListAdapter = new SpinnerAdapter(this, mDropDownList, selectPos, (clickPosition, value1, object) -> {
            if (popup != null) {
                popup.dismiss();
            }

            if (mClickDropDownItem != null) {
                mClickDropDownItem.onClick(clickPosition, value1, object);
            }

        });
        recyclerView.setAdapter(mDropDownStringListAdapter);
        popup.setContentView(layout);
        popup.setFocusable(true);
        popup.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        int margine = 10;
        popup.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            popup.setOverlapAnchor(true);
            popup.showAsDropDown(v, -margine, -margine);
        } else {
            // Show anchored to button
            int[] location = new int[2];
            v.getLocationOnScreen(location);
            popup.showAtLocation(v, Gravity.NO_GRAVITY, (int) (location[0] - margine), (int) (location[1] - margine));

        }
        //  popup.showAsDropDown(v);

    }

    public interface ClickDropDownItem {
        void onClick(int clickPosition, String value, Object object);
    }


    private void recipeDialog(ReceiptDetail receiptDetail) {

        Dialog dialog = new Dialog(this,R.style.full_screen_dialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.pan_recipt_dialog);
        LinearLayout bt_share = dialog.findViewById(R.id.bt_share);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        TextView outletDetail = dialog.findViewById(R.id.outletDetail);
        TextView companyName = dialog.findViewById(R.id.companyName);
        ImageView cancel = dialog.findViewById(R.id.cancel);
        RelativeLayout shareData = dialog.findViewById(R.id.shareData);

        CompanyProfileResponse companyData = UtilMethods.INSTANCE.getCompanyProfileDetails(this);

        String company = companyData.getCompanyProfile().getName() + "\n" + Html.fromHtml(companyData.getCompanyProfile().getAddress());
        if (companyData.getCompanyProfile().getPhoneNo() != null && !companyData.getCompanyProfile().getPhoneNo().isEmpty()) {
            company = company + "\nLandline No : " + companyData.getCompanyProfile().getPhoneNo();
        }
        if (companyData.getCompanyProfile().getMobileNo() != null && !companyData.getCompanyProfile().getMobileNo().isEmpty()) {
            company = company + "\nMobile No : " + companyData.getCompanyProfile().getMobileNo();
        }
        if (companyData.getCompanyProfile().getEmailId() != null && !companyData.getCompanyProfile().getEmailId().isEmpty()) {
            company = company + "\nEmail : " + companyData.getCompanyProfile().getEmailId();
        }
        companyName.setText(company);

        String outletDetailStr = "";
        if (mLoginDataResponse.getData().getName() != null && !mLoginDataResponse.getData().getName().isEmpty()) {
            outletDetailStr = outletDetailStr + "Name : " + mLoginDataResponse.getData().getName();
        }
        if (mLoginDataResponse.getData().getOutletName() != null && !mLoginDataResponse.getData().getOutletName().isEmpty()) {
            outletDetailStr = outletDetailStr + " | Shop Name : " + mLoginDataResponse.getData().getOutletName();
        }
        if (mLoginDataResponse.getData().getMobileNo() != null && !mLoginDataResponse.getData().getMobileNo().isEmpty()) {
            outletDetailStr = outletDetailStr + " | Contact No : " + mLoginDataResponse.getData().getMobileNo();
        }
        if (mLoginDataResponse.getData().getEmailID() != null && !mLoginDataResponse.getData().getEmailID().isEmpty()) {
            outletDetailStr = outletDetailStr + " | Email : " + mLoginDataResponse.getData().getEmailID();
        }
        if (mLoginDataResponse.getData().getAddress() != null && !mLoginDataResponse.getData().getAddress().isEmpty()) {
            outletDetailStr = outletDetailStr + " | Address : " + mLoginDataResponse.getData().getAddress();
        }
        outletDetail.setText(outletDetailStr);

        ArrayList<ReceiptObject> mReceiptObjects = new ArrayList<>();

        //Op name
        if (receiptDetail.getOpName()!=null && !receiptDetail.getOpName().trim().isEmpty()) {
            mReceiptObjects.add(new ReceiptObject("Operator Name",receiptDetail.getOpName().trim()));
        }


        //Requested Amount
        if (receiptDetail.getRequestedAmount()!=null) {
            mReceiptObjects.add(new ReceiptObject("Requested Amount",UtilMethods.INSTANCE.formatedAmountWithRupees(receiptDetail.getRequestedAmount()+"")));
        }

        //Name
        if (receiptDetail.getName()!=null && !receiptDetail.getName().trim().isEmpty()) {
            mReceiptObjects.add(new ReceiptObject("Name",receiptDetail.getName().trim()));
        }

        //Bank Name
        if (receiptDetail.getBankName()!=null && !receiptDetail.getBankName().trim().isEmpty()) {
            mReceiptObjects.add(new ReceiptObject("Bank Name",receiptDetail.getBankName().trim()));
        }

        //Mobile No
        if (receiptDetail.getAccount()!=null && !receiptDetail.getAccount().trim().isEmpty()) {
            mReceiptObjects.add(new ReceiptObject("Mobile Number",receiptDetail.getAccount().trim()));
        }

        //Email
        if (receiptDetail.getSenderNo()!=null && !receiptDetail.getSenderNo().trim().isEmpty()) {
            mReceiptObjects.add(new ReceiptObject("Email Id",receiptDetail.getSenderNo().trim()));
        }

        //IFSC
        if (receiptDetail.getIfsc()!=null && !receiptDetail.getIfsc().trim().isEmpty()) {
            mReceiptObjects.add(new ReceiptObject("IFSC",receiptDetail.getIfsc().trim()));
        }

        //Transaction ID
        if (receiptDetail.getTransactionID()!=null && !receiptDetail.getTransactionID().trim().isEmpty()) {
            mReceiptObjects.add(new ReceiptObject("Transaction ID",receiptDetail.getTransactionID().trim()));
        }

        //Entry Date
        if (receiptDetail.getEntryDate()!=null && !receiptDetail.getEntryDate().trim().isEmpty()) {
            mReceiptObjects.add(new ReceiptObject("Entry Date",receiptDetail.getEntryDate().trim()));
        }

        recyclerView.setAdapter(new ReceiptDetailListAdapter(mReceiptObjects, this));


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        bt_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap myBitmap = null;
                shareData.setDrawingCacheEnabled(true);
                myBitmap = Bitmap.createBitmap(shareData.getDrawingCache());
                shareData.setDrawingCacheEnabled(false);
                saveBitmap(myBitmap);
            }
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NSDL_INITIATE_REQUEST_CODE) {
            opOneData="op1";
            opTwoData="op2";
            selectOpTwo.setText("Select Type");
            selectOp.setText("Select Type");
            name.setText("");
            email.setText("");
            number.setText("");
            genderData = "gender";
            gender.setText("Select Gender");
            checkBox.setChecked(false);
            String dataValue;
            if (resultCode == RESULT_OK) {
                if (data != null && data.getExtras() != null) {
                    dataValue = data.getStringExtra("data");

                    //String encData = data.getStringExtra("encrypted_data");
                    if (dataValue != null && !dataValue.isEmpty()) {
                        /*{"status":"FAILED","message":"Transaction Failed","data":{"order_id":"NSDL61596679687500UIUIA","p_order_id":"T83715820312500HXHYM","ack_no":"","error_message":"Back button clicked by user","date":"2022-12-26 16:2:18"}}*/
                        Reciptpojo convertedObject = new Gson().fromJson(dataValue, Reciptpojo.class);
                        if(convertedObject!=null){
                            String status = convertedObject.getStatus();
                            message = convertedObject.getMessage();
                            order_id = convertedObject.getData().getOrderId();
                            p_order_id = convertedObject.getData().getpOrderId();
                            ack_no = convertedObject.getData().getAckNo();
                            date = convertedObject.getData().getDate();
                            if (status!=null && status.equalsIgnoreCase("SUCCESS") || status.equalsIgnoreCase("REFUND")) {
                                hitRecipeApi();

                            } else {
                                UtilMethods.INSTANCE.ErrorWithTitle(this, message, "Order id" + " : " + order_id);
                            }

                        }

                    }
                }
                else {
                    if (message != null) {
                        UtilMethods.INSTANCE.Error(this, message);
                    }else {
                        UtilMethods.INSTANCE.Error(this, "Data Not Found");
                    }
                }

            } else {
                // Popup with cancel msg
                if (message != null) {
                    UtilMethods.INSTANCE.Error(this, message);
                }

            }
        }
    }

    private void hitRecipeApi() {

        EndPointInterface pointInterface = ApiClient.getClient().create(EndPointInterface.class);
        Call<TransactionReceiptResponse> call = pointInterface.getTransactionReceipt(new ReciptRequest(
                tid,
                transactionID,
                0,
                mLoginDataResponse.getData().getUserID(),
                String.valueOf(mLoginDataResponse.getData().getLoginTypeID()),
                ApplicationConstant.INSTANCE.APP_ID,
                deviceId,
                "",
                BuildConfig.VERSION_NAME,
                deviceSerialNum,
                mLoginDataResponse.getData().getSessionID(),
                mLoginDataResponse.getData().getSession()
        ));
        call.enqueue(new Callback<TransactionReceiptResponse>() {
            @Override
            public void onResponse(Call<TransactionReceiptResponse> call, Response<TransactionReceiptResponse> response) {
                if (response.isSuccessful())
                {
                    if (loader != null && loader.isShowing())
                        loader.dismiss();
                    if(response.body()!=null && response.body().getStatuscode()==1){
                        if(response.body().getReceiptDetail()!=null){
                            ReceiptDetail mReceiptDetail=response.body().getReceiptDetail();
                            recipeDialog(mReceiptDetail);
                        }
                    } else if (response.body() != null && response.body().getStatuscode()==-1) {
                        if (!response.body().isVersionValid()) {
                            UtilMethods.INSTANCE.versionDialog(NSDLActivity.this);
                        } else {
                            UtilMethods.INSTANCE.Error(NSDLActivity.this, response.body().getMsg() + "");
                        }
                    }
                }else{
                    if (loader != null && loader.isShowing())
                        loader.dismiss();

                    UtilMethods.INSTANCE.apiErrorHandle(NSDLActivity.this,response.code() ,response.message());
                }

            }

            @Override
            public void onFailure(Call<TransactionReceiptResponse> call, Throwable t) {
                if (loader != null && loader.isShowing())
                    loader.dismiss();
                UtilMethods.INSTANCE.apiFailureError(NSDLActivity.this,t);
            }
        });
    }


    private void saveBitmap(Bitmap bitmap) {
        if (Build.VERSION.SDK_INT >= 30) {
            ContentValues values = contentValues();
            values.put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/" + getString(R.string.app_name));
            values.put(MediaStore.Images.Media.IS_PENDING, true);
            values.put(MediaStore.Images.Media.DISPLAY_NAME, System.currentTimeMillis() + ".png");

            Uri uri = this.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            if (uri != null) {
                try {
                    saveImageToStream(bitmap, this.getContentResolver().openOutputStream(uri));
                    values.put(MediaStore.Images.Media.IS_PENDING, false);
                    this.getContentResolver().update(uri, values, null, null);


                    sendMail(uri);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            }
        } else {
            File directory = new File(Environment.getExternalStorageDirectory().toString() + "/Pictures/" + getString(R.string.app_name));

            if (!directory.exists()) {
                directory.mkdirs();
            }
            String fileName = System.currentTimeMillis() + ".png";
            File file = new File(directory, fileName);
            try {
                saveImageToStream(bitmap, new FileOutputStream(file));
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, file.getAbsolutePath());
                this.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

                sendMail(Uri.parse("file://" + file));

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
    }


    private ContentValues contentValues() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/png");
        values.put(MediaStore.Images.Media.DATE_ADDED, System.currentTimeMillis() / 1000);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis());
        }
        return values;
    }

    private void saveImageToStream(Bitmap bitmap, OutputStream outputStream) {
        if (outputStream != null) {
            try {
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                outputStream.close();
            } catch (FileNotFoundException e) {
                Log.e("GREC", e.getMessage(), e);
            } catch (IOException e) {
                Log.e("GREC", e.getMessage(), e);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void sendMail(Uri myUri) {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT,
                "NSDL  Receipt");
        emailIntent.putExtra(Intent.EXTRA_TEXT,
                "NSDL  Receipt");
        emailIntent.setType("image/png");
        emailIntent.putExtra(Intent.EXTRA_STREAM, myUri);
        startActivity(Intent.createChooser(emailIntent, "Share via..."));
    }

    public void sendMail(String path) {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT,
                "CMS Receipt");
        emailIntent.putExtra(Intent.EXTRA_TEXT,
                "Receipt");
        emailIntent.setType("image/png");
        Uri myUri = Uri.parse("file://" + path);
        emailIntent.putExtra(Intent.EXTRA_STREAM, myUri);
        startActivity(Intent.createChooser(emailIntent, "Share via..."));
    }
}
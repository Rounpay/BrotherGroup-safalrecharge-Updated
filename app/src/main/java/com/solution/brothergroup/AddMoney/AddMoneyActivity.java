package com.solution.brothergroup.AddMoney;

import static com.cashfree.pg.CFPaymentService.PARAM_APP_ID;
import static com.cashfree.pg.CFPaymentService.PARAM_CUSTOMER_EMAIL;
import static com.cashfree.pg.CFPaymentService.PARAM_CUSTOMER_NAME;
import static com.cashfree.pg.CFPaymentService.PARAM_CUSTOMER_PHONE;
import static com.cashfree.pg.CFPaymentService.PARAM_NOTIFY_URL;
import static com.cashfree.pg.CFPaymentService.PARAM_ORDER_AMOUNT;
import static com.cashfree.pg.CFPaymentService.PARAM_ORDER_CURRENCY;
import static com.cashfree.pg.CFPaymentService.PARAM_ORDER_ID;
import static com.cashfree.pg.CFPaymentService.PARAM_ORDER_NOTE;
import static com.cashfree.pg.CFPaymentService.PARAM_PAYMENT_MODES;

import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.cashfree.pg.CFPaymentService;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;
import com.paytm.pgsdk.TransactionManager;
import com.razorpay.Checkout;
import com.razorpay.PaymentData;
import com.razorpay.PaymentResultWithDataListener;

import com.solution.brothergroup.Api.Request.InitiateUPIRequest;
import com.solution.brothergroup.Api.Object.KeyVals;
import com.solution.brothergroup.Api.Object.OperatorList;
import com.solution.brothergroup.Api.Object.SlabDetailDisplayLvl;
import com.solution.brothergroup.Api.Object.SlabRangeDetail;
import com.solution.brothergroup.Api.Object.UPIGatewayRequest;
import com.solution.brothergroup.Api.Object.WalletType;
import com.solution.brothergroup.Api.Request.AggrePayTransactionUpdateRequest;
import com.solution.brothergroup.Api.Request.BasicRequest;
import com.solution.brothergroup.Api.Response.AppUserListResponse;
import com.solution.brothergroup.Api.Response.BalanceResponse;
import com.solution.brothergroup.Api.Response.BasicResponse;
import com.solution.brothergroup.Api.Response.NumberListResponse;

import com.solution.brothergroup.Api.Response.PayTmSuccessItemResponse;
import com.solution.brothergroup.Api.Response.SlabCommissionResponse;
import com.solution.brothergroup.Api.Response.SlabRangeDetailResponse;
import com.solution.brothergroup.Api.Response.WalletTypeResponse;
import com.solution.brothergroup.Authentication.dto.LoginResponse;
import com.solution.brothergroup.BuildConfig;

import com.solution.brothergroup.CommissionSlab.ui.CommissionSlabDetailAdapter;
import com.solution.brothergroup.Fragments.dto.BalanceType;
import com.solution.brothergroup.R;
import com.solution.brothergroup.Util.ApiClient;
import com.solution.brothergroup.Util.ApplicationConstant;
import com.solution.brothergroup.Util.EndPointInterface;
import com.solution.brothergroup.Util.ListPopupWindowAdapter;
import com.solution.brothergroup.Util.UtilMethods;
import com.solution.brothergroup.AddMoney.adapter.AddMoneyTypeAdapter;
import com.solution.brothergroup.AddMoney.adapter.GatewayTypeAdapter;
import com.solution.brothergroup.AddMoney.modelClass.GatewayTransactionRequest;
import com.solution.brothergroup.AddMoney.modelClass.GatwayTransactionResponse;
import com.solution.brothergroup.AddMoney.modelClass.InitiateUPIResponse;
import com.solution.brothergroup.AddMoney.modelClass.PayTMTransactionUpdateRequest;
import com.solution.brothergroup.AddMoney.modelClass.PaymentGatewayType;
import com.solution.brothergroup.AddMoney.modelClass.RPayRequest;
import com.solution.brothergroup.AddMoney.modelClass.RequestPTM;
import com.solution.brothergroup.AddMoney.modelClass.UpdateUPIRequest;
import com.solution.brothergroup.usefull.CustomLoader;
import com.test.pg.secure.pgsdkv4.PGConstants;
import com.test.pg.secure.pgsdkv4.PaymentGatewayPaymentInitializer;
import com.test.pg.secure.pgsdkv4.PaymentParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeoutException;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.ListPopupWindow;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;

public class AddMoneyActivity extends AppCompatActivity implements View.OnClickListener, PaymentResultWithDataListener,
        PaytmPaymentTransactionCallback {

    private static final String TAG = AddMoneyActivity.class.getSimpleName();
    View walletView;
    TextView walletTv, walletAmountTv;
    ImageView arrowIv;
    EditText amountEt;
    RecyclerView recyclerView;
    CustomLoader loader;
    BalanceResponse balanceCheckResponse;
    // private boolean isBankWalletActive;
    private WalletTypeResponse mWalletTypeResponse;
    HashMap<String, Integer> walletIdMap = new HashMap<>();
    ArrayList<BalanceType> mBalanceTypes = new ArrayList<>();
    private int selectedWalletId = 1;

    ArrayList<PaymentGatewayType> pgList = new ArrayList<>();
    private Dialog gatewayDialog, uiWebViewDialog;
    private String selectedMethod;
    int selectedOPId;
    LoginResponse mLoginDataResponse;
    boolean isActivityPause;
    private boolean isDialogShowBackground;
    private String dialogMsg;
    private boolean isSucessDialog;
    AddMoneyActivity activity;
    int INTENT_REQUEST_CODE_PAYTM = 7365;
    private NumberListResponse NumberList;

    /* ---------Razor Payment-------------------*/

    private String key_id, order_id, name, description, image, prefill_name, prefill_contact, prefill_email, callback_url, cancel_url;
    private int amount;


    /*-------------------------UPI Payment--------------------------*/

    private View UPIView, upilogo;
    private Button upiBtn;
    private boolean isUpi, isPaymentGatway;
    private String tid, bankOrderID, mvpa, terminalID;
    private int INTENT_UPI = 6789;
    private int INTENT_UPI_WEB = 8981;
    private String upiTID;
    private boolean isUPIPaymentDone = false;

    private String paymentResponse, txnId, status, txnRef, ApprovalRefNo, TrtxnRef, responseCode, bleTxId;
    private boolean isStatus = false;
    private NumberListResponse mOperatorListResponse;
    private SlabCommissionResponse slabCommissionResponse;
    private SlabDetailDisplayLvl selectedOperator;
    ArrayList<SlabDetailDisplayLvl> operatorArray = new ArrayList<>();
    private RequestPTM ptmRequest;
    private AlertDialog alertDialog;
    private String transactioID, token, orderId;
    private CashFreeData cFItemData;
    private boolean isTransactionCancelledByUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowInsetsControllerCompat controller = new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        controller.setAppearanceLightStatusBars(true);
        controller.setAppearanceLightNavigationBars(true);
        setContentView(R.layout.activity_add_money);
        activity = AddMoneyActivity.this;
        isActivityPause = false;

        getIDS();
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setTitle("Add Money");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_icon);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        if (getIntent() != null) {
            isPaymentGatway = getIntent().getBooleanExtra("isPayment", false);
            isUpi = getIntent().getBooleanExtra("isUPI", false);
        }
        String mLoginResponse = UtilMethods.INSTANCE.getLoginPref(this);
        mLoginDataResponse = new Gson().fromJson(mLoginResponse, LoginResponse.class);
        showWalletListPopupWindow();
        amountEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


                if (amountEt.getText().toString().isEmpty()) {
                    amountEt.setError("Please Enter Amount...");
                    amountEt.requestFocus();
                    if (UPIView.getVisibility() == View.VISIBLE) {
                        upiBtn.setEnabled(false);
                    }
                } else {
                    amountEt.setError(null);
                    if (UPIView.getVisibility() == View.VISIBLE) {
                        upiBtn.setEnabled(true);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mOperatorListResponse = new Gson().fromJson(UtilMethods.INSTANCE.getNumberList(this), NumberListResponse.class);


        SlabCommissionResponse mSlabCommissionResponse = new Gson().fromJson(UtilMethods.INSTANCE.getCommList(this), SlabCommissionResponse.class);

        setUiData(mSlabCommissionResponse);
        HitCommissionApi();


    }

    private void setUiData(SlabCommissionResponse mOperatorListResponse) {

        if (isUpi && !isPaymentGatway) {
            UPIView.setVisibility(View.VISIBLE);
            upilogo.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            getOpId(mOperatorListResponse, 50);
        } else if (isUpi && isPaymentGatway) {
            UPIView.setVisibility(View.GONE);
            upilogo.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            getOperator(mOperatorListResponse, 50, 37);
        } else if (!isUpi && isPaymentGatway) {
            UPIView.setVisibility(View.GONE);
            upilogo.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            getOperator(mOperatorListResponse, 37, -1);
        } else {
            UPIView.setVisibility(View.GONE);
            upilogo.setVisibility(View.GONE);
            recyclerView.setVisibility(View.GONE);
            UtilMethods.INSTANCE.Error(this, "Coming Soon");
            //noDataView.setVisibility(View.VISIBLE);
        }
    }


    private void HitCommissionApi() {
        if (UtilMethods.INSTANCE.isNetworkAvialable(this)) {
            UtilMethods.INSTANCE.MyCommission(this, loader, new UtilMethods.ApiCallBack() {
                @Override
                public void onSucess(Object object) {
                    setUiData((SlabCommissionResponse) object);
                }

            });

        }
    }


    private void getIDS() {
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);
        walletView = findViewById(R.id.walletView);
        walletTv = findViewById(R.id.walletTv);
        walletAmountTv = findViewById(R.id.walletAmountTv);
        arrowIv = findViewById(R.id.arrowIv);
        amountEt = findViewById(R.id.amountEt);
        recyclerView = findViewById(R.id.recyclerView);
        UPIView = findViewById(R.id.UPIView);
        upilogo = findViewById(R.id.upilogo);
        upiBtn = findViewById(R.id.btn_UPIProceed);

        amountEt.setCompoundDrawablesWithIntrinsicBounds(
                AppCompatResources.getDrawable(this, R.drawable.ic_rupee_indian),
                null, null, null);

        recyclerView.setLayoutManager(new LinearLayoutManager(activity));

        UPIView.setVisibility(View.GONE);
        upilogo.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);

        walletView.setOnClickListener(this::onClick);
        upiBtn.setOnClickListener(this::onClick);
    }


    private void getOpId(SlabCommissionResponse mSlabCommissionResponse, int op_Type) {
        if (mSlabCommissionResponse != null && mSlabCommissionResponse.getSlabDetailDisplayLvl() != null &&
                mSlabCommissionResponse.getSlabDetailDisplayLvl().size() > 0 && mOperatorListResponse != null &&
                mOperatorListResponse.getData() != null &&
                mOperatorListResponse.getData().getOperators() != null && mOperatorListResponse.getData().getOperators().size() > 0) {
            operatorArray.clear();
            for (SlabDetailDisplayLvl slab : mSlabCommissionResponse.getSlabDetailDisplayLvl()) {
                if (slab.getOpTypeId() == op_Type) {
                    for (OperatorList op : mOperatorListResponse.getData().getOperators()) {
                        if (slab.getOid() == op.getOid() && op.isActive()) {
                            selectedOPId = slab.getOid();
                            selectedMethod = slab.getOperator();
                            selectedOperator = slab;
                            break;
                        }
                    }
                }
            }

        } else {
            if (mSlabCommissionResponse != null && mSlabCommissionResponse.getSlabDetailDisplayLvl() != null && mSlabCommissionResponse.getSlabDetailDisplayLvl().size() > 0) {
                for (SlabDetailDisplayLvl op : mSlabCommissionResponse.getSlabDetailDisplayLvl()) {
                    if (op.getOpTypeId() == op_Type) {
                        selectedOPId = op.getOid();
                        selectedMethod = op.getOperator();
                        selectedOperator = op;
                    }
                }
            }
        }


    }

    private void getOperator(SlabCommissionResponse mSlabCommissionResponse, int op_Type1, int op_Type2) {
        if (mSlabCommissionResponse != null && mSlabCommissionResponse.getSlabDetailDisplayLvl() != null &&
                mSlabCommissionResponse.getSlabDetailDisplayLvl().size() > 0 && mOperatorListResponse != null &&
                mOperatorListResponse.getData() != null &&
                mOperatorListResponse.getData().getOperators() != null && mOperatorListResponse.getData().getOperators().size() > 0) {
            operatorArray.clear();
            for (SlabDetailDisplayLvl slab : mSlabCommissionResponse.getSlabDetailDisplayLvl()) {
                if (slab.getOpTypeId() == op_Type1 || slab.getOpTypeId() == op_Type2) {

                    for (OperatorList op : mOperatorListResponse.getData().getOperators()) {
                        if (slab.getOid() == op.getOid() && op.isActive()) {
                            operatorArray.add(slab);
                            break;
                        }
                    }
                }
            }

        } else {
            if (mSlabCommissionResponse != null && mSlabCommissionResponse.getSlabDetailDisplayLvl() != null && mSlabCommissionResponse.getSlabDetailDisplayLvl().size() > 0) {
                operatorArray.clear();
                for (SlabDetailDisplayLvl slab : mSlabCommissionResponse.getSlabDetailDisplayLvl()) {
                    if ((slab.getOpTypeId() == op_Type1 || slab.getOpTypeId() == op_Type2)) {
                        operatorArray.add(slab);
                    }

                }
            }
        }

        AddMoneyTypeAdapter addMoneyTypeAdapter = new AddMoneyTypeAdapter(operatorArray, this, mLoginDataResponse.getData().getRoleID());
        recyclerView.setAdapter(addMoneyTypeAdapter);

    }

    public void viewRangeClick(SlabDetailDisplayLvl operator) {
        if (UtilMethods.INSTANCE.isNetworkAvialable(this)) {

            loader.show();
            loader.setCancelable(false);
            loader.setCanceledOnTouchOutside(false);

            UtilMethods.INSTANCE.MyCommissionDetail(this, operator.getOid(), loader, new UtilMethods.ApiCallBack() {
                @Override
                public void onSucess(Object object) {
                    SlabRangeDetailResponse mSlabRangeDetailResponse = (SlabRangeDetailResponse) object;
                    slabDetailDialog(mSlabRangeDetailResponse.getSlabRangeDetail(), operator.getOperator(), UtilMethods.INSTANCE.formatedAmount(operator.getMin() + ""), UtilMethods.INSTANCE.formatedAmount(operator.getMax() + ""), operator.getOid());
                }
            });

        } else {
            UtilMethods.INSTANCE.NetworkError(this);
        }

    }

    public void slabDetailDialog(ArrayList<SlabRangeDetail> mSlabRangeDetail, String operatorValue, String min, String max, int oid) {
        try {
            if (alertDialog != null && alertDialog.isShowing()) {
                return;
            }

            AlertDialog.Builder dialogBuilder;
            dialogBuilder = new AlertDialog.Builder(this);
            alertDialog = dialogBuilder.create();
            alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            LayoutInflater inflater = getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.dialog_slab_range_details, null);
            alertDialog.setView(dialogView);
            View maxCommView, fixedCommView;
            ImageView closeView = dialogView.findViewById(R.id.iv_cancleView);
            ImageView opImage = dialogView.findViewById(R.id.iv_opImage);
            TextView opName = dialogView.findViewById(R.id.tv_opName);
            TextView opRange = dialogView.findViewById(R.id.tv_opRange);
            maxCommView = dialogView.findViewById(R.id.maxCommView);
            fixedCommView = dialogView.findViewById(R.id.fixedCommView);
                            /*maxCommView.setVisibility(View.GONE);
                            fixedCommView.setVisibility(View.GONE);*/

            RecyclerView slabRangeRecyclerView = dialogView.findViewById(R.id.rv_slabRange);
            slabRangeRecyclerView.setLayoutManager(new LinearLayoutManager(this));

            CommissionSlabDetailAdapter commissionSlabAdapter = new CommissionSlabDetailAdapter(mSlabRangeDetail, this);
            slabRangeRecyclerView.setAdapter(commissionSlabAdapter);

            opName.setText(operatorValue + "");
            opRange.setText("Range : " + min + " - " + max);

            closeView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                }
            });

            RequestOptions requestOptions = new RequestOptions();
            requestOptions.placeholder(R.mipmap.ic_launcher);
            requestOptions.error(R.mipmap.ic_launcher);
            requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
            Glide.with(this)
                    .load(ApplicationConstant.INSTANCE.baseIconUrl + oid + ".png")
                    .apply(requestOptions)
                    .into(opImage);

           /* AppCompatImageView closeIv = dialogView.findViewById(R.id.closeIv);
            TextView operator = dialogView.findViewById(R.id.operator);
            TextView maxMin = dialogView.findViewById(R.id.maxMin);
            ImageView iconIv = dialogView.findViewById(R.id.icon);
            RecyclerView recyclerView = dialogView.findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(new CommissionSlabDetailAdapter(mSlabRangeDetail, this));
            operator.setText(operatorValue);
            maxMin.setText("Range : "+maxMinValue);

            Glide.with(this)
                    .load(ApplicationConstant.INSTANCE.baseIconUrl + oid + ".png")
                    .apply(requestOptions)
                    .into(iconIv);
            closeIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                }
            });*/


            alertDialog.show();

        } catch (IllegalStateException ise) {

        } catch (IllegalArgumentException iae) {

        } catch (Exception e) {

        }
    }


    @Override
    public void onClick(View clickView) {

        if (clickView == walletView) {
            showPoupWindow(walletView);
        } else if (clickView == upiBtn) {
            amountEt.setError(null);
            if (selectedOPId != -1 || selectedOperator != null) {
                if (amountEt.getText().toString().trim().isEmpty()) {
                    amountEt.setError("Please Enter Amount");
                    amountEt.requestFocus();
                    return;
                } /*else if (selectedOperator.getMin() != 0 && Integer.parseInt(amountEt.getText().toString().trim()) < selectedOperator.getMin()) {
                    amountEt.setError("Amount can't be less then \u20B9 " + selectedOperator.getMin());
                    amountEt.requestFocus();
                    return;
                } else if (selectedOperator.getMax() != 0 && Integer.parseInt(amountEt.getText().toString().trim()) > selectedOperator.getMax()) {
                    amountEt.setError("Amount can't be greater then \u20B9 " + selectedOperator.getMax());
                    amountEt.requestFocus();
                    return;
                }*/

                ChoosePaymentGateway(true);
                /*initUpi(amountEt.getText().toString().trim());*/

            } else {
                Toast.makeText(AddMoneyActivity.this, "Invalid Operator Id", Toast.LENGTH_SHORT).show();
            }

        }

    }

    private void initUpi(String amount) {
        try {
            loader.show();
            loader.setCancelable(false);
            loader.setCanceledOnTouchOutside(false);

            if (mLoginDataResponse != null) {
                InitiateUPIRequest upiRequest = new InitiateUPIRequest();
                upiRequest.setAmount(amount);
                upiRequest.setOid(selectedOPId + "");
                upiRequest.setUpiid("");
                upiRequest.setWalletID(selectedWalletId);
                upiRequest.setUserID(mLoginDataResponse.getData().getUserID());
                upiRequest.setSession(mLoginDataResponse.getData().getSession());
                upiRequest.setSessionID(mLoginDataResponse.getData().getSessionID());
                upiRequest.setImei(UtilMethods.INSTANCE.getIMEI(activity));
                upiRequest.setRegKey("");
                upiRequest.setSerialNo(UtilMethods.INSTANCE.getSerialNo(activity));
                upiRequest.setLoginTypeID(mLoginDataResponse.getData().getLoginTypeID());
                upiRequest.setAppid(ApplicationConstant.INSTANCE.APP_ID);
                upiRequest.setVersion(BuildConfig.VERSION_NAME);

                UtilMethods.INSTANCE.initiateUPI(activity, upiRequest, loader, new UtilMethods.ApiCallBack() {
                    @Override
                    public void onSucess(Object object) {

                        if (object != null && object instanceof InitiateUPIResponse) {

                            InitiateUPIResponse upiResponse = (InitiateUPIResponse) object;
                            upiTID = upiResponse.getTid() != null ? upiResponse.getTid() : "";
                            bankOrderID = upiResponse.getBankOrderID() != null ? upiResponse.getBankOrderID() : "";
                            mvpa = upiResponse.getMvpa() != null ? upiResponse.getMvpa() : "";
                            terminalID = upiResponse.getTerminalID() != null ? upiResponse.getTerminalID() : "";

                            openUpiIntent(getUPIString(mvpa, mLoginDataResponse.getData().getName(),
                                    terminalID, bankOrderID,
                                    getString(R.string.app_name).replaceAll(" ", "") + "UPITransaction",
                                    amount,
                                    ApplicationConstant.INSTANCE.baseUrl));


                            // String upi="upi://pay?pa=uattesting0026@icici&pn=Abc&tr=EZY2020082517001900012033&am=44&cu=INR&mc=5411";

                        }
                    }
                });
            }
        } catch (Exception ex) {
            if (loader != null && loader.isShowing())
                loader.dismiss();
            UtilMethods.INSTANCE.Error(activity, ex.getMessage());
        }
    }


    private Uri getUPIString(String payeeAddress, String payeeName, String merchentCode, String tref,
                             String trxnNote, String payeeAmount, String refUrl) {
        Uri uri =
                new Uri.Builder()
                        .scheme("upi")
                        .authority("pay")
                        .appendQueryParameter("pa", payeeAddress)
                        .appendQueryParameter("pn", payeeName)
                        .appendQueryParameter("mc", merchentCode)
                        .appendQueryParameter("tr", tref)
                        .appendQueryParameter("tn", trxnNote)
                        .appendQueryParameter("am", payeeAmount)
                        .appendQueryParameter("cu", "INR")
                        .appendQueryParameter("url", refUrl)
                        .build();
        return uri;
    }

    void openUpiIntent(Uri Upi) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        //intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Upi);
        Intent chooser = Intent.createChooser(intent, "Pay with...");
        //chooser.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            startActivityForResult(chooser, INTENT_UPI, null);
        }*/

        // will always show a dialog to user to choose an app
        if (null != chooser.resolveActivity(getPackageManager())) {
            startActivityForResult(chooser, INTENT_UPI);
        } else {
            Toast.makeText(this, "No UPI app found, please install one to continue", Toast.LENGTH_SHORT).show();
        }
    }

    private void showPoupWindow(View anchor) {
        if (mBalanceTypes != null && mBalanceTypes.size() > 0) {
            final ListPopupWindow listPopupWindow =
                    createListPopupWindow(anchor, ViewGroup.LayoutParams.WRAP_CONTENT, mBalanceTypes);
            listPopupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.rect));
            listPopupWindow.show();
        } else {
            showWalletListPopupWindow();
        }
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
                if (mBalanceTypes.size() == 1) {
                    arrowIv.setVisibility(View.GONE);
                    walletView.setClickable(false);
                } else {
                    arrowIv.setVisibility(View.VISIBLE);
                    walletView.setClickable(true);
                }
                setWalletIds();
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

    void setWalletIds() {

        if (mWalletTypeResponse != null && mWalletTypeResponse.getWalletTypes() != null && mWalletTypeResponse.getWalletTypes().size() > 0) {
            int count = 0;
            for (WalletType object : mWalletTypeResponse.getWalletTypes()) {
                if (object.getInFundProcess()) {
                    for (BalanceType objectWallet : mBalanceTypes) {
                        if (objectWallet.getName().contains(object.getName())) {
                            walletIdMap.put(objectWallet.getName(), object.getId());
                            if (count == 0) {
                                walletTv.setText(objectWallet.getName());
                                walletAmountTv.setText("\u20B9 " + objectWallet.getAmount());
                                selectedWalletId = object.getId();
                            }
                            count++;
                        }
                    }
                }
            }

        } else {
            mWalletTypeResponse = new Gson().fromJson(UtilMethods.INSTANCE.getWalletType(this), WalletTypeResponse.class);
            if (mWalletTypeResponse != null && mWalletTypeResponse.getWalletTypes() != null && mWalletTypeResponse.getWalletTypes().size() > 0) {
                setWalletIds();
            } else {
                UtilMethods.INSTANCE.WalletType(activity, new UtilMethods.ApiCallBack() {
                    @Override
                    public void onSucess(Object object) {
                        mWalletTypeResponse = (WalletTypeResponse) object;
                        if (mWalletTypeResponse != null && mWalletTypeResponse.getWalletTypes() != null && mWalletTypeResponse.getWalletTypes().size() > 0) {
                            setWalletIds();
                        }
                    }
                });
            }
        }
    }

    private ListPopupWindow createListPopupWindow(View anchor, int width, ArrayList<BalanceType> items) {
        final ListPopupWindow popup = new ListPopupWindow(this);

        ListAdapter adapter = new ListPopupWindowAdapter(items, this, false, R.layout.wallet_list_popup, new ListPopupWindowAdapter.ClickView() {
            @Override
            public void onClickView(String walletName, String amount) {
                walletTv.setText(walletName);
                walletAmountTv.setText(amount);
                selectedWalletId = walletIdMap.get(walletName);
                popup.dismiss();
            }
        });
        // popup.setWidth((int) getResources().getDimension(R.dimen._200sdp));
        popup.setAnchorView(anchor);
        popup.setAdapter(adapter);
        return popup;
    }

    public void paymentTypeClick(SlabDetailDisplayLvl operator) {
        amountEt.setError(null);
        if (amountEt.getText().toString().trim().isEmpty()) {
            amountEt.setError("Please Enter Amount");
            amountEt.requestFocus();
            return;
        } else if (operator.getMin() != 0 && Integer.parseInt(amountEt.getText().toString().trim()) < operator.getMin()) {
            amountEt.setError("Amount can't be less then \u20B9 " + operator.getMin());
            amountEt.requestFocus();
            return;
        } else if (operator.getMax() != 0 && Integer.parseInt(amountEt.getText().toString().trim()) > operator.getMax()) {
            amountEt.setError("Amount can't be greater then \u20B9 " + operator.getMax());
            amountEt.requestFocus();
            return;
        }
        selectedMethod = operator.getOperator();
        selectedOPId = operator.getOid();
        selectedOperator = operator;
        if (operator.getOpTypeId() == 50) {
            /* initUpi(amountEt.getText().toString().trim());*/
            ChoosePaymentGateway(true);
        } else {
            if (pgList != null && pgList.size() > 0) {
                if (pgList.size() == 1) {
                    startGateway(pgList.get(0));
                } else {
                    showPopupGateWay();
                }
            } else {
                ChoosePaymentGateway(false);
            }
        }

    }

    public void startGateway(PaymentGatewayType paymentGatewayType) {

        if (gatewayDialog != null && gatewayDialog.isShowing()) {
            gatewayDialog.dismiss();
        }
        GatewayTransaction(paymentGatewayType);

    }

    private void openUpiWeb(String url) {

        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setPackage("com.android.chrome");
            startActivity(intent);
        } catch (ActivityNotFoundException ex) {
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            } catch (ActivityNotFoundException ex1) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        }
    }

    private void showUPIWebview(String url) {

        if (uiWebViewDialog != null && uiWebViewDialog.isShowing()) {
            return;
        }
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE); // or (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewMyLayout = inflater.inflate(R.layout.dialog_upi_webview, null);
        WebView webView = viewMyLayout.findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setSupportMultipleWindows(true);
        webView.setWebViewClient(new MyWebViewClient());
        webView.loadUrl(url);


        View closeBtn = viewMyLayout.findViewById(R.id.closeIv);
        uiWebViewDialog = new Dialog(this, R.style.Theme_AppCompat_Dialog_Alert);
        uiWebViewDialog.setCancelable(false);
        uiWebViewDialog.setContentView(viewMyLayout);
        /* dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));*/
        uiWebViewDialog.getWindow().setLayout(Toolbar.LayoutParams.MATCH_PARENT, Toolbar.LayoutParams.MATCH_PARENT);
        uiWebViewDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uiWebViewDialog.dismiss();
                loader.show();
                callUPIWebUpdate(false);
            }
        });


        uiWebViewDialog.show();
        // Window window = dialog.getWindow();
        //window.setLayout(Toolbar.LayoutParams.MATCH_PARENT, Toolbar.LayoutParams.MATCH_PARENT);


    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.contains("upi://pay")) {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url));
                    Intent chooser = Intent.createChooser(intent, "Pay with...");
                    if (null != chooser.resolveActivity(getPackageManager())) {
                        startActivityForResult(chooser, INTENT_UPI_WEB);
                    } else {
                        Toast.makeText(AddMoneyActivity.this, "No UPI app found, please install one to continue", Toast.LENGTH_SHORT).show();
                    }
                } catch (ActivityNotFoundException anfe) {
                    try {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        Intent chooser = Intent.createChooser(intent, "Pay with...");
                        startActivityForResult(chooser, INTENT_UPI_WEB);
                    } catch (ActivityNotFoundException anfe1) {
                        Toast.makeText(AddMoneyActivity.this, "No UPI app found, please install one to continue", Toast.LENGTH_SHORT).show();
                    }
                }

                return true;
            } else {
                view.loadUrl(url);
                return false;
            }

        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            loader.show();
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            loader.dismiss();

            if (url.toLowerCase().contains("pgcallback/upigatewayredirectnew")) {

                if (uiWebViewDialog != null && uiWebViewDialog.isShowing()) {
                    uiWebViewDialog.dismiss();
                    loader.show();
                    callUPIWebUpdate(false);
                }

            }
        }

    }

    private void showPopupGateWay() {

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE); // or (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewMyLayout = inflater.inflate(R.layout.dialog_select_gateway, null);
        RecyclerView recyclerView = viewMyLayout.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        View closeBtn = viewMyLayout.findViewById(R.id.closeBtn);

        GatewayTypeAdapter gatewayTypeAdapter = new GatewayTypeAdapter(pgList, activity);
        recyclerView.setAdapter(gatewayTypeAdapter);
        gatewayDialog = new Dialog(this, R.style.Theme_AppCompat_Dialog_Alert);
        gatewayDialog.setCancelable(false);
        gatewayDialog.setContentView(viewMyLayout);
        /* dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));*/
        gatewayDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gatewayDialog.dismiss();
            }
        });

        gatewayDialog.show();
        // Window window = dialog.getWindow();
        //window.setLayout(Toolbar.LayoutParams.MATCH_PARENT, Toolbar.LayoutParams.MATCH_PARENT);


    }

    /*--------Initiate Razor Payment Gat way-------------*/

    private void initRazorPaySdk(final RPayRequest rPayRequest) {

        key_id = rPayRequest.getKeyId();
        order_id = rPayRequest.getOrderId();
        name = rPayRequest.getName();
        description = rPayRequest.getDescription();
        image = rPayRequest.getImage();
        prefill_name = rPayRequest.getPrefillName();
        prefill_contact = rPayRequest.getPrefillContact();
        prefill_email = rPayRequest.getPrefillEmail();
        callback_url = rPayRequest.getCallbackUrl();
        cancel_url = rPayRequest.getCancelUrl();
        amount = rPayRequest.getAmount();
        final Activity activity = this;

        final Checkout checkout = new Checkout();
        checkout.setImage(R.drawable.rnd_logo);
        checkout.setKeyID(key_id);

        try {
            JSONObject options = new JSONObject();
            options.put("name", name);
            options.put("description", description);
            //You can omit the image option to fetch the image from dashboard
            options.put("image", image);
            options.put("order_id", order_id);
            options.put("theme.color", "#" + Integer.toHexString(ContextCompat.getColor(this, R.color.colorPrimary) & 0x00ffffff));
            options.put("currency", "INR");
            options.put("amount", amount);


            /*options.put("prefill.email", "gaurav.kumar@example.com");
      options.put("prefill.contact","9988776655");*/

            JSONObject preFill = new JSONObject();
            preFill.put("email", prefill_email);
            preFill.put("contact", prefill_contact);
            if (selectedMethod != null) {
                if (selectedMethod.toLowerCase().contains("card")) {
                    preFill.put("method", "card");
                }
                if (selectedMethod.toLowerCase().contains("net banking") || selectedMethod.toLowerCase().contains("netbanking")) {
                    preFill.put("method", "netbanking");
                }
                if (selectedMethod.toLowerCase().contains("upi")) {
                    preFill.put("method", "upi");
                }
                if (selectedMethod.toLowerCase().contains("wallet")) {
                    preFill.put("method", "wallet");
                }
                if (selectedMethod.toLowerCase().contains("emi")) {
                    preFill.put("method", "emi");
                }
            }
            options.put("prefill", preFill);

            checkout.open(activity, options);

        } catch (Exception e) {
            UtilMethods.INSTANCE.Error(activity, "Error in payment due to \n" + e.getMessage());
            e.printStackTrace();
        }

    }

    /*-------Cash Free SDK Integrations..----*/

    private void initCashFreeSdk() {

        try {
            CFPaymentService cfPaymentService = CFPaymentService.getCFPaymentServiceInstance();
            cfPaymentService.setOrientation(0);
            cfPaymentService.setConfirmOnExit(true);
            String colorValue1 = "#0C5E73", colorValue2 = "#FFFFFF";

            try {
                colorValue1 = "#" + Integer.toHexString(ContextCompat.getColor(this, R.color.colorPrimary) & 0x00ffffff);
            } catch (NullPointerException npe) {

            } catch (Exception e) {

            }

            if (selectedMethod.toLowerCase().contains("upi")) {

                Intent upiIntent = new Intent(Intent.ACTION_VIEW);
                upiIntent.setData(Uri.parse("upi://pay"));

                /*isAppAvailable(upiIntent)*/
                if (upiIntent.resolveActivity(getPackageManager()) != null) {
                    cfPaymentService.upiPayment(this, getInputParams(), cFItemData.getToken(), ApplicationConstant.INSTANCE.CFStage);

                } else {
                    cfPaymentService.doPayment(this, getInputParams(), cFItemData.getToken(), ApplicationConstant.INSTANCE.CFStage, colorValue1, colorValue2, false);

                }

            } else {
                cfPaymentService.doPayment(this, getInputParams(), cFItemData.getToken(), ApplicationConstant.INSTANCE.CFStage, colorValue1, colorValue2, false);

            }

            Log.d(TAG, "ReqCode : " + new Gson().toJson(cfPaymentService));


        } catch (Exception exception) {
            UtilMethods.INSTANCE.Error(this, "" + exception.getMessage());
        }

    }

    private boolean isAppAvailable(Intent intent) {
        List<ResolveInfo> list = getPackageManager().queryIntentActivities(intent,
                PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }

    private Map<String, String> getInputParams() {

        String orderAppId = "", orderAmount = "", orderId = "", orderNote = "", customerName = "", customerPhone = "", customerEmail = "", orderCurrency = "INR", orderNotifyURL = "";

        if (cFItemData.getAppId() != null && !cFItemData.getAppId().isEmpty())
            orderAppId = cFItemData.getAppId();


        if (cFItemData.getOrderAmount() != null)
            orderAmount = String.valueOf(cFItemData.getOrderAmount());

        else
            orderAmount = amountEt.getText().toString();

        if (cFItemData.getOrderID() != null && !cFItemData.getOrderID().isEmpty())
            orderId = cFItemData.getOrderID();

        customerName = mLoginDataResponse.getData().getName();

        if (cFItemData.getCustomerPhone() != null && !cFItemData.getCustomerPhone().isEmpty())
            customerPhone = cFItemData.getCustomerPhone();
        else
            customerPhone = mLoginDataResponse.getData().getMobileNo();

        if (cFItemData.getCustomerEmail() != null && !cFItemData.getCustomerEmail().isEmpty())
            customerEmail = cFItemData.getCustomerEmail();
        else
            customerEmail = mLoginDataResponse.getData().getEmailID();

        if (cFItemData.getOrderCurrency() != null && !cFItemData.getOrderCurrency().isEmpty())
            orderCurrency = cFItemData.getOrderCurrency().trim();

        if (cFItemData.getNotifyUrl() != null)
            orderNotifyURL = cFItemData.getNotifyUrl();

        Map<String, String> params = new HashMap<>();

        params.put(PARAM_APP_ID, orderAppId);
        params.put(PARAM_ORDER_ID, orderId);
        if (selectedMethod.toLowerCase().contains("upi")) {
            params.put(PARAM_PAYMENT_MODES, "upi");
        } else if (selectedMethod.toLowerCase().contains("credit")) {
            params.put(PARAM_PAYMENT_MODES, "cc");
        } else if (selectedMethod.toLowerCase().contains("debit")) {
            params.put(PARAM_PAYMENT_MODES, "dc");
        } else if (selectedMethod.toLowerCase().contains("net")) {
            params.put(PARAM_PAYMENT_MODES, "nb");
        } else if (selectedMethod.toLowerCase().contains("wallet")) {
            params.put(PARAM_PAYMENT_MODES, "wallet");
        }

        params.put(PARAM_ORDER_AMOUNT, orderAmount);
        params.put(PARAM_ORDER_NOTE, orderNote);
        params.put(PARAM_CUSTOMER_NAME, customerName);
        params.put(PARAM_CUSTOMER_PHONE, customerPhone);
        params.put(PARAM_CUSTOMER_EMAIL, customerEmail);
        params.put(PARAM_ORDER_CURRENCY, orderCurrency);
        params.put(PARAM_NOTIFY_URL, orderNotifyURL);

       /* for(Map.Entry entry : params.entrySet()) {
            Log.e("CFSKDRequest", entry.getKey() + " " + entry.getValue());
        }*/
        return params;
    }

    private String transResponseBundleToString(Bundle bundle) {
        if (bundle == null) {
            return null;
        }
        String response = "";
        Map<String, String> mapResponseData = new HashMap<>();
        for (String key : bundle.keySet()) {
            if (bundle.getString(key) != null) {
                Log.e("CashFreeCallBack : ", key + " : " + bundle.getString(key));
                response = response.concat(String.format("%s : %s \n", key.toUpperCase(), bundle.get(key)));
                mapResponseData.put(key, bundle.getString(key));
            }
        }
        /*for (String key : bundle.keySet()) {
            if (bundle.getString(key) != null) {
                Log.e("CashFreeCallBack : ", key + " : " + bundle.getString(key));
                response=response.concat(String.format("%$ : %$\n",key,bundle.get(key)));
                Object value = bundle.get(key);
                mapResponseData.put(key,bundle.getString(key));
                stringBuilder.append(
                        String.format("%s %s (%s)\n", key, value*/
        /*, value == null ? "null" : value.getClass().getName()*//*));

            }
        }*/
        /*return stringBuilder.substring(0, stringBuilder.length() - 1);*/
        return response;

    }

    private JSONObject transResponseBundleToJsonOb(Bundle bundle) {
        {
            if (bundle == null) {
                return null;
            }
            JSONObject json = new JSONObject();
            JsonObject json1 = new JsonObject();
            for (String key : bundle.keySet()) {
                if (bundle.getString(key) != null) {
                    Log.e("CashFreeCallBack : ", key + " : " + bundle.getString(key));
                    try {
                        // json.put(key, bundle.get(key)); see edit below
                        json.put(key, JSONObject.wrap(bundle.get(key)));
                        json1.addProperty(key, (String) bundle.get(key));
                    } catch (JSONException e) {
                        //Handle exception here
                    }

                }
            }
            /*return stringBuilder.substring(0, stringBuilder.length() - 1);*/
            return json;

        }
    }

    private void showResponse(String response) {

        new MaterialAlertDialogBuilder(this).setMessage(response).setTitle("Alert").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss()).show();
    }

    private Bundle cashFreeSuccessData(CashFreeData cFResponseItem, JSONObject sdkResponseItem, int statusCode) {
        Bundle inResponse = new Bundle();
        try {
            inResponse.putString("STATUS", sdkResponseItem.getString("txStatus"));
        } catch (JSONException e) {
            e.printStackTrace();
            inResponse.putString("STATUS", "");//STATUS
        }
        try {
            /*signature : 4Kt4VfWYu2yRvFqXFrKO5sufD9N5/9fSvXCVaUN0F/4=*/
            inResponse.putString("CHECKSUMHASH", "" + sdkResponseItem.getString("signature"));
        } catch (JSONException e) {
            e.printStackTrace();
            inResponse.putString("CHECKSUMHASH", "");
        }

        inResponse.putString("BANKNAME", "");
        try {
            inResponse.putString("ORDERID", sdkResponseItem.getString("orderId"));
        } catch (JSONException e) {
            e.printStackTrace();
            inResponse.putString("ORDERID", cFResponseItem.getOrderID());//ORDERID
        }

        try {
            inResponse.putString("TXNAMOUNT", String.valueOf(sdkResponseItem.getDouble("orderAmount")));
        } catch (JSONException e) {
            e.printStackTrace();
            inResponse.putString("TXNAMOUNT", amountEt.getText().toString() + "");//TXNAMOUNT
        }
        inResponse.putString("MID", "");

        try {
            inResponse.putString("TXNID", sdkResponseItem.getString("referenceId"));
        } catch (JSONException e) {
            e.printStackTrace();
            inResponse.putString("TXNID", "");//TXNID
        }
        /*if(statusCode==1){

        }*/
        inResponse.putString("RESPCODE", statusCode + "");//RESPCODE
        try {
            inResponse.putString("PAYMENTMODE", sdkResponseItem.getString("paymentMode"));
        } catch (JSONException e) {
            e.printStackTrace();
            inResponse.putString("PAYMENTMODE", "" + cFResponseItem.getPaymentModes());//PAYMENTMODE
        }
        try {
            inResponse.putString("BANKTXNID", sdkResponseItem.getString("referenceId"));
        } catch (JSONException e) {
            e.printStackTrace();
            inResponse.putString("BANKTXNID", "");//BANKTXNID
        }
        inResponse.putString("CURRENCY", "INR");
        try {
            inResponse.putString("GATEWAYNAME", sdkResponseItem.getString("type"));
        } catch (JSONException e) {
            e.printStackTrace();
            inResponse.putString("GATEWAYNAME", "");//GATEWAYNAME
        }
        try {
            inResponse.putString("TXNDATE", sdkResponseItem.getString("txTime"));//txTime
        } catch (JSONException e) {
            e.printStackTrace();
            inResponse.putString("TXNDATE", "");//TXNDATE
        }
        try {
            inResponse.putString("RESPMSG", sdkResponseItem.getString("txMsg"));//txMsg
        } catch (JSONException e) {
            e.printStackTrace();
            inResponse.putString("RESPMSG", "");//txMsg
        }
        return inResponse;
    }

    private Bundle cashFreeFailedData(CashFreeData requestCFData, int errorCode, String status, String errorMsg) {
        Bundle inResponse = new Bundle();
        inResponse.putString("STATUS", status);
        inResponse.putString("CHECKSUMHASH", "");
        inResponse.putString("BANKNAME", "");
        inResponse.putString("ORDERID", requestCFData.getOrderID());
        inResponse.putString("TXNAMOUNT", String.valueOf(requestCFData.getOrderAmount()));
        inResponse.putString("MID", "");
        inResponse.putString("TXNID", "");
        inResponse.putString("RESPCODE", errorCode + "");
        inResponse.putString("PAYMENTMODE", "");
        inResponse.putString("BANKTXNID", "");
        inResponse.putString("CURRENCY", "INR");
        inResponse.putString("GATEWAYNAME", "");
        inResponse.putString("RESPMSG", errorMsg);

        return inResponse;
    }

    private void cashFreeCallBackApi(Bundle inResponse) {
        JsonObject json = new JsonObject();
        if (inResponse != null) {
            Set<String> keys = inResponse.keySet();
            for (String key : keys) {
                json.addProperty(key, (String) inResponse.get(key));
            }
        }
        CashFreeTransactionUpdate(json);
    }

    public void CashFreeTransactionUpdate(JsonObject response) {
        try {
            if (!isActivityPause) {
                loader.show();
            }
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<BasicResponse> call;
            call = git.CashFreeTransactionUpdate(new PayTMTransactionUpdateRequest(response, mLoginDataResponse.getData().getUserID(), mLoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID, UtilMethods.INSTANCE.getDeviceId(activity),
                    "", BuildConfig.VERSION_NAME, UtilMethods.INSTANCE.getSerialNo(activity), mLoginDataResponse.getData().getSessionID(),
                    mLoginDataResponse.getData().getSession()));
            call.enqueue(new Callback<BasicResponse>() {
                @Override
                public void onResponse(Call<BasicResponse> call, final retrofit2.Response<BasicResponse> response) {

                    try {
                        if (loader != null && loader.isShowing()) {
                            loader.dismiss();
                        }
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                if (response.body().getStatuscode() == 1) {
                                    amountEt.setText("");
                                    UtilMethods.INSTANCE.Balancecheck(AddMoneyActivity.this, loader, object -> {
                                        balanceCheckResponse = (BalanceResponse) object;
                                        if (balanceCheckResponse != null && balanceCheckResponse.getBalanceData() != null) {
                                            showWalletListPopupWindow();
                                        }
                                    });

                                    if (isTransactionCancelledByUser) {
                                        UtilMethods.INSTANCE.ErrorWithTitle(AddMoneyActivity.this, "TXN CANCEL", "Cancelled By User");
                                    } else {
                                        if (response.body().getMsg() != null && response.body().getMsg().equalsIgnoreCase("PENDING")) {
                                            UtilMethods.INSTANCE.Processing(AddMoneyActivity.this, "Your Order" + cFItemData.getOrderID() + " for Amount " + getString(R.string.rupiya) + cFItemData.getOrderAmount() + " is awaited from Bank Side \n" + "We will Update once we get Response From bank Side ");

                                        } else if (response.body().getMsg() != null && response.body().getMsg().equalsIgnoreCase("Transaction Success")) {
                                            if (!isActivityPause) {
                                                UtilMethods.INSTANCE.Successful(AddMoneyActivity.this, "Money Added Successfully");
                                            } else {
                                                isDialogShowBackground = true;
                                                dialogMsg = "Money Added Successfully";
                                                isSucessDialog = true;
                                            }
                                        } else {
                                            showResponse(response.body().getMsg());
                                        }
                                    }


                                } else {

                                    if (!isActivityPause) {
                                        UtilMethods.INSTANCE.Error(AddMoneyActivity.this, response.body().getMsg() + "");
                                    } else {
                                        isDialogShowBackground = true;
                                        dialogMsg = response.body().getMsg() + "";
                                        isSucessDialog = false;
                                    }
                                }

                            } else {

                                if (!isActivityPause) {
                                    UtilMethods.INSTANCE.Error(AddMoneyActivity.this, getString(R.string.some_thing_error));
                                } else {
                                    isDialogShowBackground = true;
                                    dialogMsg = getString(R.string.some_thing_error);
                                    isSucessDialog = false;
                                }
                            }
                        } else {
                            UtilMethods.INSTANCE.apiErrorHandle(AddMoneyActivity.this, response.code(), response.message());
                        }
                    } catch (Exception e) {
                        if (loader.isShowing()) {
                            loader.dismiss();
                        }

                        if (!isActivityPause) {
                            UtilMethods.INSTANCE.Error(AddMoneyActivity.this, e.getMessage() + "");
                        } else {
                            isDialogShowBackground = true;
                            dialogMsg = e.getMessage() + "";
                            isSucessDialog = false;
                        }
                    }
                }

                @Override
                public void onFailure(Call<BasicResponse> call, Throwable t) {
                    try {
                        if (loader.isShowing()) {
                            loader.dismiss();
                        }

                        if (t instanceof UnknownHostException || t instanceof IOException) {
                            if (!isActivityPause) {
                                UtilMethods.INSTANCE.NetworkError(AddMoneyActivity.this);

                            } else {
                                isDialogShowBackground = true;
                                dialogMsg = getString(R.string.err_msg_network);
                                isSucessDialog = false;
                            }
                        } else if (t instanceof SocketTimeoutException || t instanceof TimeoutException) {
                            if (!isActivityPause) {
                                UtilMethods.INSTANCE.ErrorWithTitle(AddMoneyActivity.this, "TIME OUT ERROR", t.getMessage() + "");
                            } else {
                                isDialogShowBackground = true;
                                dialogMsg = t.getMessage();
                                isSucessDialog = false;
                            }
                        } else {
                            if (t.getMessage() != null && !t.getMessage().isEmpty()) {
                                if (!isActivityPause) {
                                    UtilMethods.INSTANCE.ErrorWithTitle(AddMoneyActivity.this, "FATAL ERROR", t.getMessage() + "");
                                } else {
                                    isDialogShowBackground = true;
                                    dialogMsg = t.getMessage();
                                    isSucessDialog = false;
                                }
                            } else {
                                if (!isActivityPause) {
                                    UtilMethods.INSTANCE.Error(AddMoneyActivity.this, getString(R.string.some_thing_error));
                                } else {
                                    isDialogShowBackground = true;
                                    dialogMsg = t.getMessage();
                                    isSucessDialog = false;
                                }
                            }
                        }


                    } catch (IllegalStateException ise) {
                        if (loader.isShowing()) {
                            loader.dismiss();
                        }

                        if (!isActivityPause) {
                            UtilMethods.INSTANCE.Error(AddMoneyActivity.this, getString(R.string.some_thing_error));
                        } else {
                            isDialogShowBackground = true;
                            dialogMsg = getString(R.string.some_thing_error);
                            isSucessDialog = false;
                        }
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            if (loader.isShowing()) {
                loader.dismiss();
            }

            if (!isActivityPause) {
                UtilMethods.INSTANCE.Error(AddMoneyActivity.this, e.getMessage() + "");
            } else {
                isDialogShowBackground = true;
                dialogMsg = e.getMessage() + "";
                isSucessDialog = false;
            }
        }
    }

    /*-------Cash Free SDK Integrations..----*/


    /*---------------Initiate PayTm Gat way-----------*/

    void initPaytmSdk(final RequestPTM requestPTM) {
        PaytmPGService mService = /*BuildConfig.DEBUG ? PaytmPGService.getStagingService() :*/ PaytmPGService.getProductionService();
        HashMap<String, String> paramMap = new HashMap<>();
        paramMap.put("MID", requestPTM.getMid() + "");
        paramMap.put("ORDER_ID", requestPTM.getOrdeR_ID() + "");
        paramMap.put("CUST_ID", requestPTM.getCusT_ID() + "");
        paramMap.put("MOBILE_NO", requestPTM.getMobilE_NO() + "");
        paramMap.put("EMAIL", requestPTM.getEmail() + "");
        paramMap.put("CHANNEL_ID", requestPTM.getChanneL_ID() + "");
        paramMap.put("TXN_AMOUNT", requestPTM.getTxN_AMOUNT() + "");
        paramMap.put("WEBSITE", requestPTM.getWebsite() + "");
        paramMap.put("INDUSTRY_TYPE_ID", requestPTM.getIndustrY_TYPE_ID() + "");
        //BuildConfig.DEBUG ? "https://securegw-stage.paytm.in/theia/paytmCallback?ORDER_ID=" + data.getNewOrderNo() : "https://securegw.paytm.in/theia/paytmCallback?ORDER_ID=" + data.getNewOrderNo()
        paramMap.put("CALLBACK_URL", requestPTM.getCallbacK_URL() + "");
        paramMap.put("CHECKSUMHASH", requestPTM.getChecksumhash() + "");
        PaytmOrder Order = new PaytmOrder(paramMap);
        mService.initialize(Order, null);
        ptmRequest = requestPTM;

        mService.startPaymentTransaction(this, true, true, this);
       /* mService.startPaymentTransaction(this, true, true, new PaytmPaymentTransactionCallback() {

            public void someUIErrorOccurred(String inErrorMessage) {
                paytmCallBackApi(paytmFailedData(requestPTM, 0, "TXN_CANCEL", inErrorMessage));
                // Toast.makeText(getApplicationContext(), "UI Error " + inErrorMessage, Toast.LENGTH_LONG).show();
            }

            public void onTransactionResponse(Bundle inResponse) {
                paytmCallBackApi(inResponse);
            }

            public void networkNotAvailable() {
                paytmCallBackApi(paytmFailedData(requestPTM, 0, "TXN_CANCEL", "Network not available"));
                // UtilMethods.INSTANCE.Error(AddMoneyActivity.this, getString(R.string.err_msg_network));
            }

            @Override
            public void onErrorProceed(String string) {
                paytmCallBackApi(paytmFailedData(requestPTM, 0, "TXN_CANCEL", string));
            }

            public void clientAuthenticationFailed(String inErrorMessage) {
                paytmCallBackApi(paytmFailedData(requestPTM, 0, "TXN_CANCEL", inErrorMessage));
                // Toast.makeText(getApplicationContext(), "Authentication failed: Server error" + inErrorMessage, Toast.LENGTH_LONG).show();
            }

            public void onErrorLoadingWebPage(int iniErrorCode, String inErrorMessage, String inFailingUrl) {
                paytmCallBackApi(paytmFailedData(requestPTM, iniErrorCode, "TXN_CANCEL", inErrorMessage));
                // Toast.makeText(getApplicationContext(), "Unable to load webpage " + inErrorMessage, Toast.LENGTH_LONG).show();
            }

            public void onBackPressedCancelTransaction() {
                paytmCallBackApi(paytmFailedData(requestPTM, 0, "TXN_CANCEL", "Transaction canceled by user"));
                //Toast.makeText(getApplicationContext(), "Transaction cancelled", Toast.LENGTH_LONG).show();
            }

            public void onTransactionCancel(String inErrorMessage, Bundle inResponse) {
                paytmCallBackApi(inResponse);
            }
        });*/
    }


    private void initUpdatePaytmSdk(RequestPTM requestPTM, String token) {
        String host = "https://securegw.paytm.in/";
        ptmRequest = requestPTM;
        PaytmOrder paytmOrder = new PaytmOrder(requestPTM.getOrdeR_ID(),
                requestPTM.getMid(),
                token, amountEt.getText().toString(),
                requestPTM.getCallbacK_URL());

        TransactionManager transactionManager = new TransactionManager(paytmOrder, this);
        // transactionManager.setAppInvokeEnabled(false);
        transactionManager.setShowPaymentUrl(host + "theia/api/v1/showPaymentPage");
        transactionManager.startTransaction(this, INTENT_REQUEST_CODE_PAYTM);
    }

    @Override
    public void onTransactionResponse(Bundle inResponse) {
        paytmCallBackApi(inResponse);
    }

    @Override
    public void networkNotAvailable() {
        paytmCallBackApi(paytmFailedData(ptmRequest, 0, "TXN_CANCEL", "Network not available"));
    }

    @Override
    public void clientAuthenticationFailed(String inErrorMessage) {
        paytmCallBackApi(paytmFailedData(ptmRequest, 0, "TXN_CANCEL", inErrorMessage));
    }

    @Override
    public void someUIErrorOccurred(String inErrorMessage) {
        paytmCallBackApi(paytmFailedData(ptmRequest, 0, "TXN_CANCEL", inErrorMessage));
    }

    @Override
    public void onErrorLoadingWebPage(int iniErrorCode, String inErrorMessage, String inFailingUrl) {
        paytmCallBackApi(paytmFailedData(ptmRequest, iniErrorCode, "TXN_CANCEL", inErrorMessage));
    }

    @Override
    public void onBackPressedCancelTransaction() {
        paytmCallBackApi(paytmFailedData(ptmRequest, 0, "TXN_CANCEL", "Transaction canceled by user"));
    }

    @Override
    public void onTransactionCancel(String inErrorMessage, Bundle inResponse) {
        paytmCallBackApi(inResponse);
    }

    @Override
    public void onErrorProceed(String string) {
        paytmCallBackApi(paytmFailedData(ptmRequest, 0, "TXN_CANCEL", string));
    }

    /*---------------Initiate UPIGatway-----------*/
    private void initUPIPayGateWay(String amount, final UPIGatewayRequest upiGatewayRequest) {

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE); // or (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewMyLayout = inflater.inflate(R.layout.dialog_payment_upi, null);
        View closeBtn = viewMyLayout.findViewById(R.id.closeBtn);
        View btn_pay = viewMyLayout.findViewById(R.id.upiBtn);
        final EditText amountTv = viewMyLayout.findViewById(R.id.amountEt);
        final EditText vpaEt = viewMyLayout.findViewById(R.id.vpaEt);

        amountTv.setText("" + amount);
        amountTv.setEnabled(false);

        gatewayDialog = new Dialog(this, R.style.Theme_AppCompat_Dialog_Alert);
        gatewayDialog.setCancelable(false);
        gatewayDialog.setContentView(viewMyLayout);
        /* dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));*/
        gatewayDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gatewayDialog.dismiss();
            }
        });

        btn_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (amountEt.getText().toString().isEmpty()) {
                    amountEt.setError(getResources().getString(R.string.err_empty_field));
                    amountEt.requestFocus();
                } else if (vpaEt.getText().toString().isEmpty()) {
                    vpaEt.setError(getResources().getString(R.string.err_empty_field));
                    vpaEt.requestFocus();
                } else {
                    if (!isUpiValid(vpaEt.getText().toString().trim())) {
                        Toast.makeText(getApplicationContext(), "Invalid UPI", Toast.LENGTH_LONG).show();
                        return;
                    }

                    openPaymentGatwayUPI(upiGatewayRequest.getKeyVals(), vpaEt);

                }
            }


        });

        gatewayDialog.show();
        // Window window = dialog.getWindow();
        //window.setLayout(Toolbar.LayoutParams.MATCH_PARENT, Toolbar.LayoutParams.MATCH_PARENT);
    }

    private void openPaymentGatwayUPI(KeyVals vals, EditText vpaEt) {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority("upigateway.com")
                .appendPath("gateway")
                .appendPath("android")
                .appendQueryParameter("key", vals.getKey())
                .appendQueryParameter("client_vpa", vpaEt.getText().toString().trim())
                .appendQueryParameter("client_txn_id", vals.getClientTxnId())
                .appendQueryParameter("amount", vals.getAmount() + "")
                .appendQueryParameter("p_info", vals.getpInfo())
                .appendQueryParameter("client_name", vals.getClientName())
                .appendQueryParameter("client_email", vals.getClientEmail())
                .appendQueryParameter("client_mobile", vals.getClientMobile())
                .appendQueryParameter("udf1", vals.getUdf1())
                .appendQueryParameter("udf2", vals.getUdf2())
                .appendQueryParameter("udf3", vals.getUdf3())
                .appendQueryParameter("redirect_url", vals.getRedirectUrl());

        Intent intent = new Intent(Intent.ACTION_VIEW, builder.build());
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setPackage("com.android.chrome");
        gatewayDialog.dismiss();
        amountEt.setText("");
        try {
            getApplicationContext().startActivity(intent);
        } catch (ActivityNotFoundException ex) {
            // Chrome browser presumably not installed so allow user to choose instead
            intent.setPackage(null);
            getApplicationContext().startActivity(intent);
        }
    }

    public boolean isUpiValid(String text) {
        return text.matches("^[\\w-]+@\\w+$");
    }

    /*---------------Initiate AggrePay Gat way-----------*/

    void initAggrePaySdk(KeyVals mKeyVals) {
        PaymentParams pgPaymentParams = new PaymentParams();
        pgPaymentParams.setAPiKey(mKeyVals.getApiKey() + "");
        pgPaymentParams.setAmount(mKeyVals.getAmount() + "");
        pgPaymentParams.setEmail(mKeyVals.getEmail() + "");
        pgPaymentParams.setName(mKeyVals.getName() + "");
        pgPaymentParams.setPhone(mKeyVals.getPhone() + "");
        pgPaymentParams.setOrderId(mKeyVals.getOrderId() + "");
        pgPaymentParams.setCurrency(mKeyVals.getCurrency() + "");
        pgPaymentParams.setDescription(mKeyVals.getDescription() + "");
        pgPaymentParams.setCity(mKeyVals.getCity() + "");
        pgPaymentParams.setState(mKeyVals.getState() + "");
        /*pgPaymentParams.setAddressLine1(SampleAppConstants.PG_ADD_1);
        pgPaymentParams.setAddressLine2(SampleAppConstants.PG_ADD_2);*/
        pgPaymentParams.setZipCode(mKeyVals.getZipCode() + "");
        pgPaymentParams.setCountry(mKeyVals.getCountry() + "");
        pgPaymentParams.setReturnUrl(mKeyVals.getReturnUrl() != null &&
                (mKeyVals.getReturnUrl().contains("http") || mKeyVals.getReturnUrl().contains("www.")) ?
                mKeyVals.getReturnUrl() + "" : ApplicationConstant.INSTANCE.baseUrl + mKeyVals.getReturnUrl() + "");
        pgPaymentParams.setMode(mKeyVals.getMode() + "");
        /*pgPaymentParams.setUdf1(SampleAppConstants.PG_UDF1);
        pgPaymentParams.setUdf2(SampleAppConstants.PG_UDF2);
        pgPaymentParams.setUdf3(SampleAppConstants.PG_UDF3);
        pgPaymentParams.setUdf4(SampleAppConstants.PG_UDF4);
        pgPaymentParams.setUdf5(SampleAppConstants.PG_UDF5);*/

        PaymentGatewayPaymentInitializer pgPaymentInitialzer = new PaymentGatewayPaymentInitializer(pgPaymentParams, this);
        pgPaymentInitialzer.initiatePaymentProcess();
    }


    void paytmCallBackApi(Bundle inResponse) {
        JsonObject json = new JsonObject();
        if (inResponse != null) {
            Set<String> keys = inResponse.keySet();
            for (String key : keys) {
                json.addProperty(key, (String) inResponse.get(key));
            }
        }
        PayTMTransactionUpdate(json);
    }

    private Bundle payTmSuccessData(PayTmSuccessItemResponse itemResponse) throws JSONException {
        Bundle inResponse = new Bundle();
        inResponse.putString("STATUS", itemResponse.getStatus());
        inResponse.putString("CHECKSUMHASH", itemResponse.getChecksumhash());
        inResponse.putString("BANKNAME", "");
        inResponse.putString("ORDERID", itemResponse.getOrderid());
        inResponse.putString("TXNAMOUNT", itemResponse.getTxnamount());
        inResponse.putString("MID", itemResponse.getMid());
        inResponse.putString("TXNID", itemResponse.getTxnid());
        inResponse.putString("RESPCODE", itemResponse.getRespcode());
        inResponse.putString("PAYMENTMODE", itemResponse.getPaymentmode());
        inResponse.putString("BANKTXNID", itemResponse.getBanktxnid());
        inResponse.putString("CURRENCY", itemResponse.getCurrency());
        inResponse.putString("GATEWAYNAME", itemResponse.getGatewayname());
        inResponse.putString("TXNDATE", itemResponse.getTxndate());
        inResponse.putString("RESPMSG", itemResponse.getRespmsg());
        return inResponse;

        /*{"BANKTXNID":"124393918986","CHECKSUMHASH":"APrEn/NS/lJIxkogDFQIGY+Z6msurKi5w97QWr3kSJsS0+5UlFVMTZzFcJtCcrdQS6awldvrvWI0D8C0lVBoUOHQqCtgIlu/5beg8brAtNs\u003d","CURRENCY":"INR","GATEWAYNAME":"PPBEX","MID":"Ambika03730389235960","ORDERID":"2720070","PAYMENTMODE":"UPI","RESPCODE":"01","RESPMSG":"Txn Success","STATUS":"TXN_SUCCESS","TXNAMOUNT":"1.00","TXNDATE":"2021-08-31 11:44:54.0","TXNID":"20210831111212800110168220137664247"}*/
    }

    private Bundle paytmFailedData(RequestPTM requestPTM, int errorCode, String status, String errorMsg) {
        Bundle inResponse = new Bundle();
        inResponse.putString("STATUS", status);
        inResponse.putString("CHECKSUMHASH", requestPTM.getChecksumhash());
        inResponse.putString("BANKNAME", "");
        inResponse.putString("ORDERID", requestPTM.getOrdeR_ID());
        inResponse.putString("TXNAMOUNT", requestPTM.getTxN_AMOUNT());
        inResponse.putString("MID", requestPTM.getMid());
        inResponse.putString("TXNID", "");
        inResponse.putString("RESPCODE", errorCode + "");
        inResponse.putString("PAYMENTMODE", "");
        inResponse.putString("BANKTXNID", "");
        inResponse.putString("CURRENCY", "INR");
        inResponse.putString("GATEWAYNAME", "");
        inResponse.putString("RESPMSG", errorMsg);

        return inResponse;
    }


    public void PayTMTransactionUpdate(JsonObject response) {
        try {
            if (!isActivityPause) {
                loader.show();
            }
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<BasicResponse> call = git.PayTMTransactionUpdate(new PayTMTransactionUpdateRequest(response, mLoginDataResponse.getData().getUserID(),
                    mLoginDataResponse.getData().getLoginTypeID(), ApplicationConstant.INSTANCE.APP_ID, UtilMethods.INSTANCE.getIMEI(activity),
                    "", BuildConfig.VERSION_NAME, UtilMethods.INSTANCE.getSerialNo(activity), mLoginDataResponse.getData().getSessionID(),
                    mLoginDataResponse.getData().getSession()));

            call.enqueue(new Callback<BasicResponse>() {
                @Override
                public void onResponse(Call<BasicResponse> call, final retrofit2.Response<BasicResponse> response) {

                    try {
                        if (loader.isShowing()) {
                            loader.dismiss();
                        }
                        if (response.body() != null) {
                            if (response.body().getStatuscode() == 1) {
                                amountEt.setText("");
                                UtilMethods.INSTANCE.BalancecheckNew(activity, null, null, new UtilMethods.ApiCallBack() {
                                    @Override
                                    public void onSucess(Object object) {

                                        balanceCheckResponse = (BalanceResponse) object;
                                        if (balanceCheckResponse != null && balanceCheckResponse.getBalanceData() != null) {
                                            showWalletListPopupWindow();
                                        }

                                    }
                                });

                                if (!isActivityPause) {
                                    //  AlertDialogForConfirmation forConfirmation = new AlertDialogForConfirmation(AddMoneyActivity.this,true);

                                    //  forConfirmation.showMessage("Success", response.body().getMsg() +"",R.drawable.ic_check_circle_black_24dp,0);

                                    UtilMethods.INSTANCE.Successful(activity, "Money Added Sucessfully");
                                } else {
                                    isDialogShowBackground = true;
                                    dialogMsg = "Money Added Sucessfully";
                                    isSucessDialog = true;
                                }
                            } else if (response.body().getStatuscode() == 2) {
                                amountEt.setText("");
                                UtilMethods.INSTANCE.BalancecheckNew(activity, null, null, new UtilMethods.ApiCallBack() {
                                    @Override
                                    public void onSucess(Object object) {

                                        balanceCheckResponse = (BalanceResponse) object;
                                        if (balanceCheckResponse != null && balanceCheckResponse.getBalanceData() != null) {
                                            showWalletListPopupWindow();
                                        }

                                    }
                                });

                                if (!isActivityPause) {
                                    // AlertDialogForConfirmation forConfirmation = new AlertDialogForConfirmation(AddMoneyActivity.this,true);

                                    //forConfirmation.showMessage("Success", response.body().getMsg() +"",R.drawable.ic_check_circle_black_24dp,0);

                                    UtilMethods.INSTANCE.Successful(activity, "Money Added Sucessfully");
                                } else {
                                    isDialogShowBackground = true;
                                    dialogMsg = "Money Added Sucessfully";
                                    isSucessDialog = true;
                                }
                            } else {

                                if (!isActivityPause) {
                                    UtilMethods.INSTANCE.Error(activity, response.body().getMsg() + "");
                                } else {
                                    isDialogShowBackground = true;
                                    dialogMsg = response.body().getMsg() + "";
                                    isSucessDialog = false;
                                }
                            }

                        } else {

                            if (!isActivityPause) {
                                UtilMethods.INSTANCE.Error(activity, getString(R.string.some_thing_error));
                            } else {
                                isDialogShowBackground = true;
                                dialogMsg = getString(R.string.some_thing_error);
                                isSucessDialog = false;
                            }
                        }

                    } catch (Exception e) {
                        if (loader.isShowing()) {
                            loader.dismiss();
                        }

                        if (!isActivityPause) {
                            UtilMethods.INSTANCE.Error(activity, e.getMessage() + "");
                        } else {
                            isDialogShowBackground = true;
                            dialogMsg = e.getMessage() + "";
                            isSucessDialog = false;
                        }
                    }
                }

                @Override
                public void onFailure(Call<BasicResponse> call, Throwable t) {
                    try {
                        if (loader.isShowing()) {
                            loader.dismiss();
                        }
                        if (t.getMessage() != null && !t.getMessage().isEmpty()) {

                            if (t.getMessage().contains("No address associated with hostname")) {

                                if (!isActivityPause) {
                                    UtilMethods.INSTANCE.Error(activity, getString(R.string.err_msg_network));
                                } else {
                                    isDialogShowBackground = true;
                                    dialogMsg = getString(R.string.err_msg_network);
                                    isSucessDialog = false;
                                }
                            } else {

                                if (!isActivityPause) {
                                    UtilMethods.INSTANCE.Error(activity, t.getMessage());
                                } else {
                                    isDialogShowBackground = true;
                                    dialogMsg = t.getMessage();
                                    isSucessDialog = false;
                                }
                            }

                        } else {

                            if (!isActivityPause) {
                                UtilMethods.INSTANCE.Error(activity, getString(R.string.some_thing_error));
                            } else {
                                isDialogShowBackground = true;
                                dialogMsg = getString(R.string.some_thing_error);
                                isSucessDialog = false;
                            }
                        }
                    } catch (IllegalStateException ise) {
                        if (loader.isShowing()) {
                            loader.dismiss();
                        }

                        if (!isActivityPause) {
                            UtilMethods.INSTANCE.Error(activity, getString(R.string.some_thing_error));
                        } else {
                            isDialogShowBackground = true;
                            dialogMsg = getString(R.string.some_thing_error);
                            isSucessDialog = false;
                        }
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            if (loader.isShowing()) {
                loader.dismiss();
            }

            if (!isActivityPause) {
                UtilMethods.INSTANCE.Error(activity, e.getMessage() + "");
            } else {
                isDialogShowBackground = true;
                dialogMsg = e.getMessage() + "";
                isSucessDialog = false;
            }
        }
    }


    public void AggrePayTransactionUpdate(String tid, int amount, String hash) {
        try {
            if (!isActivityPause) {
                loader.show();
            }
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<BasicResponse> call = git.AggrePayTransactionUpdate(new AggrePayTransactionUpdateRequest(tid, amount, hash,
                    mLoginDataResponse.getData().getUserID(),
                    mLoginDataResponse.getData().getLoginTypeID(), ApplicationConstant.INSTANCE.APP_ID,
                    UtilMethods.INSTANCE.getIMEI(AddMoneyActivity.this),
                    "", BuildConfig.VERSION_NAME, UtilMethods.INSTANCE.getSerialNo(AddMoneyActivity.this),
                    mLoginDataResponse.getData().getSessionID(),
                    mLoginDataResponse.getData().getSession()));

            call.enqueue(new Callback<BasicResponse>() {
                @Override
                public void onResponse(Call<BasicResponse> call, final retrofit2.Response<BasicResponse> response) {

                    try {
                        if (loader.isShowing()) {
                            loader.dismiss();
                        }
                        if (response.body() != null) {
                            if (response.body().getStatuscode() == 2) {
                                amountEt.setText("");
                                UtilMethods.INSTANCE.BalancecheckNew(AddMoneyActivity.this, null, null, new UtilMethods.ApiCallBack() {
                                    @Override
                                    public void onSucess(Object object) {
                                        balanceCheckResponse = (BalanceResponse) object;
                                        if (balanceCheckResponse != null && balanceCheckResponse.getBalanceData() != null) {
                                            showWalletListPopupWindow();
                                        }
                                    }
                                });

                                if (!isActivityPause) {
                                    UtilMethods.INSTANCE.Successful(AddMoneyActivity.this, "Money Added Sucessfully");
                                } else {
                                    isDialogShowBackground = true;
                                    dialogMsg = "Money Added Sucessfully";
                                    isSucessDialog = true;
                                }
                            } else if (response.body().getStatuscode() == 1) {
                                amountEt.setText("");

                                if (!isActivityPause) {
                                    UtilMethods.INSTANCE.Processing(AddMoneyActivity.this, "Your transcation under process, please wait 48 Hour to confirmation.");
                                } else {
                                    isDialogShowBackground = true;
                                    dialogMsg = "Your transcation under process, please wait 48 Hour to confirmation.";
                                    isSucessDialog = true;
                                }
                            } else {

                                if (!isActivityPause) {
                                    UtilMethods.INSTANCE.Error(AddMoneyActivity.this, response.body().getMsg() + "");
                                } else {
                                    isDialogShowBackground = true;
                                    dialogMsg = response.body().getMsg() + "";
                                    isSucessDialog = false;
                                }
                            }

                        } else {

                            if (!isActivityPause) {
                                UtilMethods.INSTANCE.Error(AddMoneyActivity.this, getString(R.string.some_thing_error));
                            } else {
                                isDialogShowBackground = true;
                                dialogMsg = getString(R.string.some_thing_error);
                                isSucessDialog = false;
                            }
                        }

                    } catch (Exception e) {
                        if (loader.isShowing()) {
                            loader.dismiss();
                        }

                        if (!isActivityPause) {
                            UtilMethods.INSTANCE.Error(AddMoneyActivity.this, e.getMessage() + "");
                        } else {
                            isDialogShowBackground = true;
                            dialogMsg = e.getMessage() + "";
                            isSucessDialog = false;
                        }
                    }
                }

                @Override
                public void onFailure(Call<BasicResponse> call, Throwable t) {
                    try {
                        if (loader.isShowing()) {
                            loader.dismiss();
                        }
                        if (t.getMessage() != null && !t.getMessage().isEmpty()) {

                            if (t.getMessage().contains("No address associated with hostname")) {

                                if (!isActivityPause) {
                                    UtilMethods.INSTANCE.Error(AddMoneyActivity.this, getString(R.string.err_msg_network));
                                } else {
                                    isDialogShowBackground = true;
                                    dialogMsg = getString(R.string.err_msg_network);
                                    isSucessDialog = false;
                                }
                            } else {

                                if (!isActivityPause) {
                                    UtilMethods.INSTANCE.Error(AddMoneyActivity.this, t.getMessage());
                                } else {
                                    isDialogShowBackground = true;
                                    dialogMsg = t.getMessage();
                                    isSucessDialog = false;
                                }
                            }

                        } else {

                            if (!isActivityPause) {
                                UtilMethods.INSTANCE.Error(AddMoneyActivity.this, getString(R.string.some_thing_error));
                            } else {
                                isDialogShowBackground = true;
                                dialogMsg = getString(R.string.some_thing_error);
                                isSucessDialog = false;
                            }
                        }
                    } catch (IllegalStateException ise) {
                        if (loader.isShowing()) {
                            loader.dismiss();
                        }

                        if (!isActivityPause) {
                            UtilMethods.INSTANCE.Error(AddMoneyActivity.this, getString(R.string.some_thing_error));
                        } else {
                            isDialogShowBackground = true;
                            dialogMsg = getString(R.string.some_thing_error);
                            isSucessDialog = false;
                        }
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            if (loader.isShowing()) {
                loader.dismiss();
            }

            if (!isActivityPause) {
                UtilMethods.INSTANCE.Error(AddMoneyActivity.this, e.getMessage() + "");
            } else {
                isDialogShowBackground = true;
                dialogMsg = e.getMessage() + "";
                isSucessDialog = false;
            }
        }
    }

    public void ChoosePaymentGateway(boolean isUPI) {
        try {
            loader.show();
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);

            Call<AppUserListResponse> call = git.ChoosePaymentGateway(new BasicRequest(isUPI,
                    mLoginDataResponse.getData().getUserID(), mLoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID,
                    UtilMethods.INSTANCE.getIMEI(activity),
                    "", BuildConfig.VERSION_NAME, UtilMethods.INSTANCE.getSerialNo(activity), mLoginDataResponse.getData().getSessionID(), mLoginDataResponse.getData().getSession()));

            call.enqueue(new Callback<AppUserListResponse>() {
                @Override
                public void onResponse(Call<AppUserListResponse> call, final retrofit2.Response<AppUserListResponse> response) {

                    try {
                        if (loader.isShowing())
                            loader.dismiss();
                        if (response.body() != null && response.body().getStatuscode() == 1) {

                            if (response.body().getpGs() != null && response.body().getpGs().size() > 0) {
                                pgList = response.body().getpGs();
                                if (response.body().getpGs().size() == 1) {
                                    if (response.body().getpGs().get(0).getPgType() == 3) {
                                        initUpi(amountEt.getText().toString().trim());
                                    } else {
                                        startGateway(pgList.get(0));
                                    }
                                } else {
                                    showPopupGateWay();
                                }

                            } else {
                                UtilMethods.INSTANCE.Processing(activity, "Service is currently down.");
                            }

                        }
                    } catch (Exception e) {
                        if (loader.isShowing())
                            loader.dismiss();

                        UtilMethods.INSTANCE.Error(activity, e.getMessage() + "");
                    }

                }

                @Override
                public void onFailure(Call<AppUserListResponse> call, Throwable t) {
                    try {
                        if (loader.isShowing()) {
                            loader.dismiss();
                        }
                        if (t.getMessage() != null && !t.getMessage().isEmpty()) {

                            if (t.getMessage().contains("No address associated with hostname")) {

                                UtilMethods.INSTANCE.Error(activity, getString(R.string.err_msg_network));


                            } else {

                                UtilMethods.INSTANCE.Error(activity, t.getMessage());


                            }

                        } else {

                            UtilMethods.INSTANCE.Error(activity, getString(R.string.some_thing_error));
                        }
                    } catch (IllegalStateException ise) {
                        loader.dismiss();

                        UtilMethods.INSTANCE.Error(activity, getString(R.string.some_thing_error));
                    }

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            if (loader.isShowing())
                loader.dismiss();
            UtilMethods.INSTANCE.Error(activity, e.getMessage() + "");
        }
    }

    public void GatewayTransaction(final PaymentGatewayType paymentGatewayType) {
        try {
            loader.show();
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);

            Call<GatwayTransactionResponse> call = git.GatewayTransaction(new GatewayTransactionRequest(amountEt.getText().toString(), paymentGatewayType.getId() + "", selectedWalletId + "", selectedOPId + "",
                    mLoginDataResponse.getData().getUserID(), mLoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID,
                    UtilMethods.INSTANCE.getIMEI(activity),
                    "", BuildConfig.VERSION_NAME, UtilMethods.INSTANCE.getSerialNo(activity), mLoginDataResponse.getData().getSessionID(), mLoginDataResponse.getData().getSession()));

            call.enqueue(new Callback<GatwayTransactionResponse>() {
                @Override
                public void onResponse(Call<GatwayTransactionResponse> call, final retrofit2.Response<GatwayTransactionResponse> response) {

                    try {
                        if (loader.isShowing()) {
                            loader.dismiss();
                        }
                        if (response.body() != null) {
                            if (response.body().getStatuscode() == 1) {
                                if (response.body().getpGModelForApp() != null) {
                                    if (response.body().getpGModelForApp().getStatuscode() == 1) {

                                        if (response.body().getpGModelForApp().getPgid() == 1 || paymentGatewayType.getPgType() == 1) {
                                            if (response.body().getpGModelForApp().getRequestPTM() != null) {
                                                initPaytmSdk(response.body().getpGModelForApp().getRequestPTM());
                                            } else {
                                                UtilMethods.INSTANCE.Processing(activity, response.body().getpGModelForApp().getMsg() + "");
                                            }
                                        } else if (response.body().getpGModelForApp().getPgid() == 2 || paymentGatewayType.getPgType() == 2) {
                                            if (response.body().getpGModelForApp().getrPayRequest() != null) {
                                                initRazorPaySdk(response.body().getpGModelForApp().getrPayRequest());
                                            } else {
                                                UtilMethods.INSTANCE.Processing(activity, response.body().getpGModelForApp().getMsg() + "");
                                            }
                                        } else if (response.body().getpGModelForApp().getPgid() == 3 || paymentGatewayType.getPgType() == 3) {

                                            initUpi(amountEt.getText().toString().trim());

                                        } else if (response.body().getpGModelForApp().getPgid() == 4 || paymentGatewayType.getPgType() == 4) {
                                            if (response.body().getpGModelForApp().getAggrePayRequest() != null) {
                                                if (response.body().getpGModelForApp().getAggrePayRequest().getStatuscode() == 1) {
                                                    if (response.body().getpGModelForApp().getAggrePayRequest().getKeyVals() != null) {
                                                        initAggrePaySdk(response.body().getpGModelForApp().getAggrePayRequest().getKeyVals());
                                                    } else {
                                                        UtilMethods.INSTANCE.Processing(activity, "Keys values not found.");
                                                    }
                                                    //AggrePay
                                                } else {
                                                    UtilMethods.INSTANCE.Processing(activity, response.body().getpGModelForApp().getAggrePayRequest().getMsg() + "");
                                                }

                                            } else {
                                                UtilMethods.INSTANCE.Processing(activity, response.body().getpGModelForApp().getMsg() + "");
                                            }
                                        } else if (response.body().getpGModelForApp().getPgid() == 5 || paymentGatewayType.getPgType() == 5) {
                                            if (response.body().getpGModelForApp().getUpiGatewayRequest() != null && response.body().getpGModelForApp().getUpiGatewayRequest().getKeyVals() != null) {
                                                initUPIPayGateWay(amountEt.getText().toString().trim(), response.body().getpGModelForApp().getUpiGatewayRequest());

                                            } else {
                                                UtilMethods.INSTANCE.Processing(AddMoneyActivity.this, response.body().getpGModelForApp().getMsg() + "");
                                            }
                                        } else if (response.body().getpGModelForApp().getPgid() == 7 || paymentGatewayType.getPgType() == 7) {
                                            if (response.body().getpGModelForApp().getRequestPTM() != null && response.body().getpGModelForApp().getToken() != null && !response.body().getpGModelForApp().getToken().isEmpty()) {
                                                initUpdatePaytmSdk(response.body().getpGModelForApp().getRequestPTM(), response.body().getpGModelForApp().getToken());
                                            } else {
                                                UtilMethods.INSTANCE.Processing(activity, response.body().getpGModelForApp().getMsg() + "");
                                            }
                                        } else if (response.body().getpGModelForApp().getPgid() == 8 || paymentGatewayType.getPgType() == 8) {
                                            if (response.body().getpGModelForApp().getCashFreeResponse() != null) {
                                                if (response.body().getpGModelForApp().getCashFreeResponse().getToken() != null && !response.body().getpGModelForApp().getCashFreeResponse().getToken().isEmpty()) {

                                                    if (response.body().getpGModelForApp().getCashFreeResponse().getOrderID() != null && !response.body().getpGModelForApp().getCashFreeResponse().getOrderID().isEmpty()) {
                                                        transactioID = response.body().getpGModelForApp().getTransactionID();
                                                        token = response.body().getpGModelForApp().getCashFreeResponse().getToken();
                                                        orderId = response.body().getpGModelForApp().getCashFreeResponse().getOrderID();
                                                        //cFItemData=response.body().getpGModelForApp().getCashFreeResponse();
                                                        cFItemData = response.body().getpGModelForApp().getCashFreeResponse();
                                                        initCashFreeSdk();
                                                    } else {
                                                        UtilMethods.INSTANCE.Error(AddMoneyActivity.this, "OrderId Required");
                                                    }
                                                } else {
                                                    UtilMethods.INSTANCE.Error(AddMoneyActivity.this, "Token Required");
                                                }

                                            } else {
                                                UtilMethods.INSTANCE.Processing(AddMoneyActivity.this, "Data is not available");
                                            }
                                        } else if (response.body().getpGModelForApp().getPgid() == 10 ||
                                                paymentGatewayType.getPgType() == 10) {
                                            if (response.body().getpGModelForApp().getUpiGatewayRequest() != null) {
                                                if (response.body().getpGModelForApp().getUpiGatewayRequest().getUrl() != null
                                                        && URLUtil.isValidUrl(response.body().getpGModelForApp().getUpiGatewayRequest().getUrl())) {
                                                    upiTID = response.body().getpGModelForApp().getTid() + "";
                                                    showUPIWebview(response.body().getpGModelForApp().getUpiGatewayRequest().getUrl());
                                                    // openUpiWeb(response.body().getpGModelForApp().getUpiGatewayRequest().getUrl());
                                                } else {
                                                    UtilMethods.INSTANCE.Error(AddMoneyActivity.this, "Url is not available");
                                                }

                                            } else {
                                                UtilMethods.INSTANCE.Processing(AddMoneyActivity.this, "Data is not available");
                                            }
                                        } else {
                                            UtilMethods.INSTANCE.Processing(AddMoneyActivity.this, "SDK is not available");

                                        }
                                    } else {
                                        UtilMethods.INSTANCE.Error(activity, response.body().getpGModelForApp().getMsg() + "");
                                    }
                                } else {
                                    UtilMethods.INSTANCE.Error(activity, response.body().getMsg() + " " + getString(R.string.some_thing_error));
                                }
                            } else {
                                UtilMethods.INSTANCE.Error(activity, response.body().getMsg() + "");
                            }

                        } else {
                            UtilMethods.INSTANCE.Error(activity, getString(R.string.some_thing_error));
                        }

                    } catch (Exception e) {
                        if (loader.isShowing()) {
                            loader.dismiss();
                        }
                        UtilMethods.INSTANCE.Error(activity, e.getMessage() + "");
                    }
                }

                @Override
                public void onFailure(Call<GatwayTransactionResponse> call, Throwable t) {
                    try {
                        if (loader.isShowing()) {
                            loader.dismiss();
                        }
                        if (t.getMessage() != null && !t.getMessage().isEmpty()) {

                            if (t.getMessage().contains("No address associated with hostname")) {
                                UtilMethods.INSTANCE.Error(activity, getString(R.string.err_msg_network));
                            } else {
                                UtilMethods.INSTANCE.Error(activity, t.getMessage());
                            }

                        } else {
                            UtilMethods.INSTANCE.Error(activity, getString(R.string.some_thing_error));
                        }
                    } catch (IllegalStateException ise) {
                        if (loader.isShowing()) {
                            loader.dismiss();
                        }
                        UtilMethods.INSTANCE.Error(activity, getString(R.string.some_thing_error));
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            if (loader.isShowing()) {
                loader.dismiss();
            }
            UtilMethods.INSTANCE.Error(activity, e.getMessage() + "");
        }
    }




    @Override
    public void onPaymentSuccess(String razorpayPaymentID, PaymentData paymentData) {

        try {
            String paymentId = paymentData.getPaymentId();
            String signature = paymentData.getSignature();
            String orderId = paymentData.getOrderId();

            //JsonObject jsonObject=paymentData.getData().get("JSONObject");
            //Log.e("onPaymentSuccess", new Gson().toJson(paymentData));
            amountEt.setText("");
            UtilMethods.INSTANCE.Successful(activity, "Money Successfully Added");
            Checkout.clearUserData(this);
            UtilMethods.INSTANCE.BalancecheckNew(activity, null, null, new UtilMethods.ApiCallBack() {
                @Override
                public void onSucess(Object object) {
                    balanceCheckResponse = (BalanceResponse) object;
                    if (balanceCheckResponse != null && balanceCheckResponse.getBalanceData() != null) {
                        showWalletListPopupWindow();
                    }

                }
            });
            // PayTMTransactionUpdate(jsonObject);
        } catch (Exception ex) {
            UtilMethods.INSTANCE.Error(this, "Exception in onPaymentSuccess \n" + ex.getMessage());
            //Log.e(TAG, "Exception in onPaymentSuccess", ex);
        }
        //UtilMethods.INSTANCE.Successful(this,"Payment Successful: " + razorpayPaymentID);

    }

    @Override
    public void onPaymentError(int code, String response, PaymentData paymentData) {
        //Log.e("onPaymentError", "" + new Gson().toJson(paymentData));
        try {
            //Log.e("onPaymentError", "" + new Gson().toJson(paymentData));
            UtilMethods.INSTANCE.Failed(this, "Payment failed due to: " + response);

            Checkout.clearUserData(this);
        } catch (Exception ex) {
            UtilMethods.INSTANCE.Error(this, "Exception in onPaymentError \n" + ex.getMessage());
            //Log.e(TAG, "Exception in onPaymentError", ex);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == INTENT_UPI) {
            if (data != null && resultCode == RESULT_OK) {

                //isUPIPaymentDone=true;
                paymentResponse = data.getStringExtra("response");
                txnId = data.getStringExtra("txnId");
                status = data.getStringExtra("Status");
                txnRef = data.getStringExtra("txnRef");
                ApprovalRefNo = data.getStringExtra("ApprovalRefNo");
                TrtxnRef = data.getStringExtra("TrtxnRef");
                responseCode = data.getStringExtra("responseCode");
                bleTxId = data.getStringExtra("bleTxId");/*TrtxnRef*/
                //Log.e("UPITransaction", "paymentResponse :" + paymentResponse + " ,txnId :" + txnId + " ,status :" + status + " ,txnRef :" + txnRef);


                /*if ((status == null || status.isEmpty() || status.toLowerCase().contains("null") || status.toLowerCase().contains("undefined"))
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

                }*/
                isStatus = false;
                if (status != null && !status.isEmpty()) {
                    isStatus = true;
                    if (status.toLowerCase().equalsIgnoreCase("success")) {
                        amountEt.setText("");
                        UtilMethods.INSTANCE.SuccessfulWithTitle(activity, "Success", "Transaction Successful.");
                    } else if (status.toLowerCase().equalsIgnoreCase("submitted")) {
                        amountEt.setText("");
                        UtilMethods.INSTANCE.ProcessingWithTitle(activity, "Submitted", "Transaction submitted, Please wait some time for confirmation.");
                    } else {
                        UtilMethods.INSTANCE.ErrorWithTitle(activity, "Failed", "Transaction Failed, Please Try After Some Time.");
                    }

                }

                callUPIUpdate(isStatus, status);


            }
        }
        else if (requestCode == INTENT_UPI_WEB) {
            if (data != null && resultCode == RESULT_OK) {
                if (uiWebViewDialog != null && uiWebViewDialog.isShowing()) {
                    uiWebViewDialog.dismiss();
                }
                //isUPIPaymentDone=true;
                paymentResponse = data.getStringExtra("response");
                txnId = data.getStringExtra("txnId");
                status = data.getStringExtra("Status");
                txnRef = data.getStringExtra("txnRef");
                ApprovalRefNo = data.getStringExtra("ApprovalRefNo");
                TrtxnRef = data.getStringExtra("TrtxnRef");
                responseCode = data.getStringExtra("responseCode");
                bleTxId = data.getStringExtra("bleTxId");/*TrtxnRef*/
                //Log.e("UPITransaction", "paymentResponse :" + paymentResponse + " ,txnId :" + txnId + " ,status :" + status + " ,txnRef :" + txnRef);


                /*if ((status == null || status.isEmpty() || status.toLowerCase().contains("null") || status.toLowerCase().contains("undefined"))
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

                }*/
                isStatus = false;
                if (status != null && !status.isEmpty()) {
                    isStatus = true;
                    if (status.toLowerCase().equalsIgnoreCase("success")) {
                        amountEt.setText("");
                        UtilMethods.INSTANCE.SuccessfulWithTitle(activity, "Success", "Transaction Successful.");
                    } else if (status.toLowerCase().equalsIgnoreCase("submitted")) {
                        amountEt.setText("");
                        UtilMethods.INSTANCE.ProcessingWithTitle(activity, "Submitted", "Transaction submitted, Please wait some time for confirmation.");
                    } else {
                        UtilMethods.INSTANCE.ErrorWithTitle(activity, "Failed", "Transaction Failed, Please Try After Some Time.");
                    }

                }

                callUPIWebUpdate(isStatus);


            }
        } else if (requestCode == PGConstants.REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                try {
                    String paymentResponse = data.getStringExtra(PGConstants.PAYMENT_RESPONSE);
                    if (paymentResponse == null || paymentResponse.equals("null")) {
                        UtilMethods.INSTANCE.Error(AddMoneyActivity.this, "Transaction Error!");
                        // transactionIdView.setText("Transaction ID: NIL");
                        //transactionStatusView.setText("Transaction Status: Transaction Error!");
                    } else {
                        JSONObject response = new JSONObject(paymentResponse);
                        AggrePayTransactionUpdate(response.optString("order_id"), response.optInt("amount"), response.optString("hash"));
                        //Log.e("Aggre Pay", "Transaction ID: " + response.getString("transaction_id"));

                        //Log.e("Aggre Pay", "Transaction Status: " + response.getString("response_message"));

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
                UtilMethods.INSTANCE.Error(AddMoneyActivity.this, "Transaction Canceled!");
            }

        } else if (requestCode == INTENT_REQUEST_CODE_PAYTM) {
            if (resultCode == RESULT_OK && data != null) {

                if (data.getStringExtra("response") != null && !data.getStringExtra("response").isEmpty()) {
                   /* Bundle bundle = data.getExtras();
                    if (bundle != null) {
                        for (String key : bundle.keySet()) {
                            Log.e(TAG, key + " : " + (bundle.get(key) != null ? bundle.get(key) : ""));
                        }

                    }
*/

                    try {
                        PayTmSuccessItemResponse itemResponse = new Gson().fromJson(data.getStringExtra("response"), PayTmSuccessItemResponse.class);
                   /* JSONObject jsonObject=new JSONObject();
                    JSONObject jsonItem=  jsonObject.getJSONObject(data.getStringExtra("response"));*/
                        paytmCallBackApi(payTmSuccessData(itemResponse));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                } else {
                    paytmCallBackApi(paytmFailedData(ptmRequest, 0, "TXN_CANCEL", "Transaction canceled"));
                }


               /* Log.e(TAG, " PayTm response 1- " + data.getStringExtra("response"));
                Log.e(TAG, " PayTm response 2- " + data.getStringExtra("nativeSdkForMerchantMessage"));
*/
            } else {
                paytmCallBackApi(paytmFailedData(ptmRequest, 0, "TXN_CANCEL", "Transaction Failed"));
            }
        } else if (requestCode == CFPaymentService.REQ_CODE) {
            if (data != null) {
                Bundle bundle = data.getExtras();
                if (bundle != null) {

                    JSONObject responseJson = transResponseBundleToJsonOb(bundle);
                    try {
                        String txnStatus = responseJson.getString("txStatus");
                        //SUCCESS--
                        if (txnStatus != null && !txnStatus.isEmpty() && txnStatus.equalsIgnoreCase("SUCCESS")) {
                            isTransactionCancelledByUser = false;
                            cashFreeCallBackApi(cashFreeSuccessData(cFItemData, responseJson, 2));

                        } else if (txnStatus != null && !txnStatus.isEmpty() && txnStatus.equalsIgnoreCase("PENDING")) {
                            isTransactionCancelledByUser = false;
                            cashFreeCallBackApi(cashFreeSuccessData(cFItemData, responseJson, 1));

                        } else if (txnStatus != null && !txnStatus.isEmpty() && txnStatus.equalsIgnoreCase("CANCELLED")) {
                            isTransactionCancelledByUser = true;
                            cashFreeCallBackApi(cashFreeFailedData(cFItemData, 0, "TXN_CANCEL", "Transaction canceled by user"));

                        } else if (txnStatus != null && !txnStatus.isEmpty() && txnStatus.equalsIgnoreCase("FAILED")) {
                            isTransactionCancelledByUser = false;
                            cashFreeCallBackApi(cashFreeSuccessData(cFItemData, responseJson, 3));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }


            } else {
                UtilMethods.INSTANCE.Error(this, "Transaction Failed");

            }
        }
    }

    private void callUPIUpdate(boolean isStatus, String status) {

        UpdateUPIRequest upiRequest = new UpdateUPIRequest();
        upiRequest.setBankStatus(status);
        upiRequest.setTid(upiTID);
        upiRequest.setUserID(mLoginDataResponse.getData().getUserID());
        upiRequest.setSession(mLoginDataResponse.getData().getSession());
        upiRequest.setSessionID(mLoginDataResponse.getData().getSessionID());
        upiRequest.setImei(UtilMethods.INSTANCE.getIMEI(activity));
        upiRequest.setRegKey("");
        upiRequest.setSerialNo(UtilMethods.INSTANCE.getSerialNo(activity));
        upiRequest.setLoginTypeID(mLoginDataResponse.getData().getLoginTypeID());
        upiRequest.setAppid(ApplicationConstant.INSTANCE.APP_ID);
        upiRequest.setVersion(BuildConfig.VERSION_NAME);


        // loader.show();
        final boolean finalIsStatus = isStatus;
        UtilMethods.INSTANCE.UPIPaymentUpdate(upiRequest, loader, new UtilMethods.ApiCallBack() {
            @Override
            public void onSucess(Object object) {
                if (!finalIsStatus) {
                    InitiateUPIResponse apiData = (InitiateUPIResponse) object;
                    UtilMethods.INSTANCE.Successful(activity, apiData.getMsg() + "");
                }
            }
        });

    }

    private void callUPIWebUpdate(boolean isStatus) {


        // loader.show();
        final boolean finalIsStatus = isStatus;
        UtilMethods.INSTANCE.CashFreeStatusCheck(upiTID, loader, new UtilMethods.ApiCallBack() {
            @Override
            public void onSucess(Object object) {
                if (!finalIsStatus) {
                    InitiateUPIResponse apiData = (InitiateUPIResponse) object;
                    if (apiData.getStatus() == 0 || apiData.getStatus() == 1) {
                        UtilMethods.INSTANCE.Processing(activity, "Your transaction is pending");
                    } else if (apiData.getStatus() == 2) {
                        UtilMethods.INSTANCE.Successful(activity, "Your transaction is successfully completed");
                    } else {
                        UtilMethods.INSTANCE.Error(activity, "Your transaction failed");
                    }

                }
            }
        });

    }

    @Override
    protected void onResume() {
        isActivityPause = false;
        super.onResume();

        if (isDialogShowBackground) {
            isDialogShowBackground = false;

            if (isSucessDialog) {
                UtilMethods.INSTANCE.Successful(activity, dialogMsg);
            } else {
                UtilMethods.INSTANCE.Error(activity, dialogMsg);
            }
        }
    }

    @Override
    protected void onPause() {
        isActivityPause = true;
        super.onPause();
    }

    @Override
    protected void onStart() {
        isActivityPause = false;
        super.onStart();
    }

    @Override
    protected void onStop() {
        isActivityPause = true;
        super.onStop();
    }


}
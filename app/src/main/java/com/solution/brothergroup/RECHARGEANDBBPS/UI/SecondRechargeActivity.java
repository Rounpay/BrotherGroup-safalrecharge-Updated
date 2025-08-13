package com.solution.brothergroup.RECHARGEANDBBPS.UI;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.solution.brothergroup.Adapter.IncentiveAdapter;
import com.solution.brothergroup.Adapter.RechargeReportAdapter;
import com.solution.brothergroup.Api.Object.CommissionDisplay;


import com.solution.brothergroup.Api.Object.IncentiveDetails;


import com.solution.brothergroup.Api.Object.OpOptionalDic;
import com.solution.brothergroup.Api.Object.OperatorList;
import com.solution.brothergroup.Api.Object.OperatorOptional;

import com.solution.brothergroup.Api.Object.OperatorParams;
import com.solution.brothergroup.Api.Object.RealLapuCommissionSlab;
import com.solution.brothergroup.Api.Object.RechargeStatus;
import com.solution.brothergroup.Api.Request.HeavyrefreshRequest;
import com.solution.brothergroup.Api.Request.OptionalOperatorRequest;

import com.solution.brothergroup.Api.Response.AppUserListResponse;
import com.solution.brothergroup.Api.Response.NumberListResponse;
import com.solution.brothergroup.Api.Response.OnboardingResponse;
import com.solution.brothergroup.Api.Response.OperatorOptionalResponse;
import com.solution.brothergroup.Api.Response.RechargeReportResponse;

import com.solution.brothergroup.Authentication.dto.LoginResponse;
import com.solution.brothergroup.BuildConfig;
import com.solution.brothergroup.DthCustomerInfo.dto.DTHInfoData;
import com.solution.brothergroup.DthCustomerInfo.dto.DTHInfoRecords;
import com.solution.brothergroup.DthCustomerInfo.dto.DTHInfoResponse;


import com.solution.brothergroup.DthPlan.UI.DthPlanInfoActivity;
import com.solution.brothergroup.DthPlan.UI.DthPlanInfoNewActivity;
import com.solution.brothergroup.DthPlan.dto.DthPlanInfoResponse;
import com.solution.brothergroup.R;

import com.solution.brothergroup.RECHARGEANDBBPS.ADAPTER.BillDetailAdapter;
import com.solution.brothergroup.RECHARGEANDBBPS.ADAPTER.DthCustInfoAdapter;
import com.solution.brothergroup.RECHARGEANDBBPS.API.FetchBillResponse;
import com.solution.brothergroup.ROffer.dto.ROfferRequest;
import com.solution.brothergroup.Util.ActivityActivityMessage;
import com.solution.brothergroup.Util.ApiClient;
import com.solution.brothergroup.Util.CustomAlertDialog;

import com.solution.brothergroup.Util.DropdownDialog.DropDownDialog;
import com.solution.brothergroup.Util.EndPointInterface;
import com.solution.brothergroup.Util.GetLocation;

import com.solution.brothergroup.Util.ApplicationConstant;
import com.solution.brothergroup.Util.GlobalBus;
import com.solution.brothergroup.Util.UtilMethods;
import com.solution.brothergroup.usefull.CustomLoader;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SecondRechargeActivity extends AppCompatActivity implements TextToSpeech.OnInitListener, View.OnClickListener {


    private LinearLayout rechargeView, ll_customerNum;
    private ImageView ivOprator;

    private AppCompatTextView tvOperator;

    private ImageView billLogo;
    private TextView heavyRefresh;
    private RelativeLayout rlImg;
    private ImageView ivNumber;

    private EditText etNumber;
    private TextView AcountRemarkTv;
    private TextView MobileNoCount;
    private LinearLayout rlOption1;
    private EditText etOption1;
    private TextView option1Remark, option1Error;
    private LinearLayout rlOption2;
    private EditText etOption2;
    private TextView option2Remark, option2Error;
    private LinearLayout rlOption3;
    private EditText etOption3;
    private TextView option3Remark, option3Error;
    private LinearLayout rlOption4;
    private EditText etOption4;
    private TextView option4Remark, option4Error;
    private LinearLayout llDropDownOption1;
    private TextView tvDropDownOption1;
    private TextView dropDownOption1Remark, dropDownOption1Error;
    private LinearLayout llDropDownOption2;
    private TextView tvDropDownOption2;
    private TextView dropDownOption2Remark, dropDownOption2Error;
    private LinearLayout llDropDownOption3;
    private TextView tvDropDownOption3;
    private TextView dropDownOption3Remark, dropDownOption3Error;
    private LinearLayout llDropDownOption4;
    private TextView tvDropDownOption4;
    private TextView dropDownOption4Remark, dropDownOption4Error;
    private LinearLayout rlDthInfoDetail;
    private AppCompatImageView operatorIcon;
    private TextView operator;
    private TextView tel;
    private LinearLayout llCustomerLayout;
    private TextView customerName;
    private LinearLayout llBalAmount;
    private TextView Balance;
    private LinearLayout llPlanName;
    private TextView planname;
    private LinearLayout llRechargeDate;
    private TextView NextRechargeDate;
    private LinearLayout llPackageAmt;
    RecyclerView custInfoRecyclerView;
    private TextView RechargeAmount;
    private LinearLayout rlBillDetail;
    private LinearLayout rlEtAmount;
    private TextView billDetailTitle;
    private LinearLayout billDetailContent;
    private LinearLayout consumerNameView;
    private TextView consumerName;
    private LinearLayout consumerNumView;
    private TextView consumerNum;
    private LinearLayout paybleAmtView;
    private TextView paybleAmt;
    private LinearLayout dueDateView, bilDateView;
    private TextView dueDate, bilDate;
    private TextView errorMsgBilldetail;
    private LinearLayout switchView;
    private SwitchCompat lapuSwitch;
    private SwitchCompat realApiSwitch;
    private RelativeLayout rlCustNumber;
    private ImageView ivPhone, amountDetailArrow;
    private RecyclerView billDetailRecyclerView, additionalInfoRecyclerView;
    private View billDetailclickView, billPeriodView;
    private TextView tvName, billPeriod;
    private EditText etCustNumber;
    private TextView MobileNoError;
    private ImageView ivPhoneBook;
    private EditText etAmount;
    private TextView AmountError;
    private AppCompatTextView cashBackTv;
    private TextView desc;
    private RelativeLayout llBrowsePlan;
    private TextView tvBrowsePlan;
    private TextView tvPdfPlan;
    private TextView tvRofferPlan, numberError;
    private LinearLayout historyView;
    private AppCompatTextView recentRechargeTv;
    private AppCompatTextView viewMore;
    private RecyclerView recyclerView;
    View ll_coming_soon;
    private Button btRecharge;


    int maxAmountLength;
    int minAmountLength;
    boolean isAcountNumeric, isTakeCustomerNum;
    int minNumberLength = 0, maxNumberLength = 0;
    String AccountName = "Number", Account_Remark = "";
    boolean isBBPS = false, isPartial = false;
    boolean isBilling = false;
    String StartWith = "";
    boolean isFetchBill;
    ArrayList<String> StartWithArray = new ArrayList<>();
    String operatorSelected = "";
    int operatorSelectedId = 0, incentiveOperatorSelectedId;
    String operatorDocName;
    private Dialog incentiveDialog;
    ArrayList<IncentiveDetails> incentiveList = new ArrayList<>();
    String from;
    String Icon;
    // Declare CustomLoader.....
    CustomLoader loader;
    LoginResponse mLoginDataResponse;
    GetLocation mGetLocation;


    private String fetchBillRefId;
    private String number;
    private String fromDateStr, toDateStr;
    private int fromId;
    RequestOptions requestOptions;

    private String operatorCircleCode, operatorRefCircleID;
    private CustomAlertDialog mCustomPassDialog;
    /*private int PICK_CONTACT = 12;
    private int PICK_CUSTOMER_CONTACT = 23;*/
    private int INTENT_ROFFER = 153;
    private int INTENT_VIEW_PLAN = 372;

    private int INTENT_PERMISSION = 583;
    private int INTENT_CUSTOMER_PERMISSION = 619;

    private int INTENT_RECHARGE_SLIP = 832;
    private DTHInfoRecords mDthInfoRecords;
    private String realCommisionStr;
    private Toolbar toolBar;
    private boolean isRecharge;


    /*private TextToSpeech tts;*/
    private boolean isTTSInit;
    private String msgSpeak = "";
    private OnboardingResponse mOnboardingResponse;
    private boolean isOnboardingSuccess;
    private int fetchBillId;
    private String regularExpress;
    private int exactness;
    private double billAmount;
    private ArrayList<String> dropDownOption1Array, dropDownOption2Array, dropDownOption3Array, dropDownOption4Array;
    private DropDownDialog mDropDownDialog;
    private int selectedDropDownOption1Pos = -1;
    private int selectedDropDownOption2Pos = -1;
    private int selectedDropDownOption3Pos = -1;
    private int selectedDropDownOption4Pos = -1;
    private NumberListResponse mNumberListResponse;
    HashMap<String, IncentiveDetails> incentiveMap = new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowInsetsControllerCompat controller = new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        controller.setAppearanceLightStatusBars(true);
        controller.setAppearanceLightNavigationBars(true);
        setContentView(R.layout.activity_second_recharge);
        mDropDownDialog = new DropDownDialog(this);
        requestOptions = new RequestOptions();
        requestOptions.circleCropTransform();
        requestOptions.placeholder(R.mipmap.ic_launcher);
        requestOptions.error(R.mipmap.ic_launcher);
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
        mCustomPassDialog = new CustomAlertDialog(this, true);
        String LoginResponse = UtilMethods.INSTANCE.getLoginPref(this);
        mLoginDataResponse = new Gson().fromJson(LoginResponse, LoginResponse.class);
         mNumberListResponse =  new Gson().fromJson(UtilMethods.INSTANCE.getNumberList(this), NumberListResponse.class);

       /* if (!mAppPreferences.getBoolean(ApplicationConstant.INSTANCE.voiceEnablePref)) {
            tts = new TextToSpeech(this, this);
        }*/
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);
        from = getIntent().getExtras().getString("from");
        fromId = getIntent().getExtras().getInt("fromId", 0);

        findViews();
        final Calendar myCalendar = Calendar.getInstance();
        String myFormat = "dd-MMM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        fromDateStr = sdf.format(myCalendar.getTime());
        toDateStr = sdf.format(myCalendar.getTime());

        mGetLocation = new GetLocation(SecondRechargeActivity.this, loader);
        if (UtilMethods.INSTANCE.getLattitude == 0 || UtilMethods.INSTANCE.getLongitude == 0) {
            mGetLocation.startLocationUpdatesIfSettingEnable((lattitude, longitude) -> {
                UtilMethods.INSTANCE.getLattitude = lattitude;
                UtilMethods.INSTANCE.getLongitude = longitude;
            });
        }
        HitApi();
    }


    private void findViews() {
        toolBar = findViewById(R.id.toolbar);
        toolBar.setTitle(from + "");
        toolBar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolBar);
        toolBar.setNavigationIcon(R.drawable.ic_arrow_back_icon);
        toolBar.setNavigationOnClickListener(v -> onBackPressed());


        rechargeView = (LinearLayout) findViewById(R.id.rechargeView);
        ll_customerNum = (LinearLayout) findViewById(R.id.ll_customerNum);
        ivOprator = (ImageView) findViewById(R.id.iv_oprator);

        tvOperator = (AppCompatTextView) findViewById(R.id.tv_operator);

        billLogo = (ImageView) findViewById(R.id.bill_logo);
        heavyRefresh = (TextView) findViewById(R.id.heavyRefresh);
        rlImg = (RelativeLayout) findViewById(R.id.rl_img);
        ivNumber = (ImageView) findViewById(R.id.iv_number);
        etNumber = (EditText) findViewById(R.id.et_number);
        AcountRemarkTv = (TextView) findViewById(R.id.AcountRemark);
        MobileNoCount = (TextView) findViewById(R.id.MobileNoCount);
        rlOption1 = (LinearLayout) findViewById(R.id.rl_option1);
        etOption1 = (EditText) findViewById(R.id.et_option1);
        option1Remark = (TextView) findViewById(R.id.option1_remark);
        option1Error = findViewById(R.id.option1_error);
        rlOption2 = (LinearLayout) findViewById(R.id.rl_option2);
        etOption2 = (EditText) findViewById(R.id.et_option2);
        option2Remark = (TextView) findViewById(R.id.option2_remark);
        option2Error = findViewById(R.id.option2_error);
        rlOption3 = (LinearLayout) findViewById(R.id.rl_option3);
        etOption3 = (EditText) findViewById(R.id.et_option3);
        option3Remark = (TextView) findViewById(R.id.option3_remark);
        option3Error = findViewById(R.id.option3_error);
        rlOption4 = (LinearLayout) findViewById(R.id.rl_option4);
        etOption4 = (EditText) findViewById(R.id.et_option4);
        option4Remark = (TextView) findViewById(R.id.option4_remark);
        option4Error = findViewById(R.id.option4_error);
        llDropDownOption1 = findViewById(R.id.ll_drop_down_option1);
        tvDropDownOption1 = findViewById(R.id.tv_drop_down_option1);
        dropDownOption1Remark = findViewById(R.id.drop_down_option1_remark);
        dropDownOption1Error = findViewById(R.id.drop_down_option1_error);
        llDropDownOption2 = findViewById(R.id.ll_drop_down_option2);
        tvDropDownOption2 = findViewById(R.id.tv_drop_down_option2);
        dropDownOption2Remark = findViewById(R.id.drop_down_option2_remark);
        dropDownOption2Error = findViewById(R.id.drop_down_option2_error);
        llDropDownOption3 = findViewById(R.id.ll_drop_down_option3);
        tvDropDownOption3 = findViewById(R.id.tv_drop_down_option3);
        dropDownOption3Remark = findViewById(R.id.drop_down_option3_remark);
        dropDownOption3Error = findViewById(R.id.drop_down_option3_error);
        llDropDownOption4 = findViewById(R.id.ll_drop_down_option4);
        tvDropDownOption4 = findViewById(R.id.tv_drop_down_option4);
        dropDownOption4Remark = findViewById(R.id.drop_down_option4_remark);
        dropDownOption4Error = findViewById(R.id.drop_down_option4_error);
        rlDthInfoDetail = (LinearLayout) findViewById(R.id.rl_DthInfoDetail);
        operatorIcon = (AppCompatImageView) findViewById(R.id.operatorIcon);
        operator = (TextView) findViewById(R.id.operator);
        tel = (TextView) findViewById(R.id.tel);
        llCustomerLayout = (LinearLayout) findViewById(R.id.ll_customer_layout);
        customerName = (TextView) findViewById(R.id.customerName);
        llBalAmount = (LinearLayout) findViewById(R.id.ll_bal_amount);
        Balance = (TextView) findViewById(R.id.Balance);
        llPlanName = (LinearLayout) findViewById(R.id.ll_plan_name);
        planname = (TextView) findViewById(R.id.planname);
        llRechargeDate = (LinearLayout) findViewById(R.id.ll_RechargeDate);
        NextRechargeDate = (TextView) findViewById(R.id.NextRechargeDate);
        llPackageAmt = (LinearLayout) findViewById(R.id.ll_packageAmt);
        custInfoRecyclerView = findViewById(R.id.custInfoRecyclerView);
        custInfoRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        RechargeAmount = (TextView) findViewById(R.id.RechargeAmount);
        rlBillDetail = (LinearLayout) findViewById(R.id.rl_billDetail);
        billDetailTitle = (TextView) findViewById(R.id.billDetailTitle);
        billDetailContent = (LinearLayout) findViewById(R.id.billDetailContent);
        consumerNameView = (LinearLayout) findViewById(R.id.consumerNameView);
        consumerName = (TextView) findViewById(R.id.consumerName);
        consumerNumView = (LinearLayout) findViewById(R.id.consumerNumView);
        consumerNum = (TextView) findViewById(R.id.consumerNum);
        paybleAmtView = (LinearLayout) findViewById(R.id.paybleAmtView);
        paybleAmt = (TextView) findViewById(R.id.paybleAmt);
        dueDateView = (LinearLayout) findViewById(R.id.dueDateView);
        bilDateView = (LinearLayout) findViewById(R.id.bilDateView);
        dueDate = (TextView) findViewById(R.id.dueDate);
        bilDate = (TextView) findViewById(R.id.bilDate);
        errorMsgBilldetail = (TextView) findViewById(R.id.errorMsgBilldetail);
        switchView = (LinearLayout) findViewById(R.id.switchView);
        lapuSwitch = (SwitchCompat) findViewById(R.id.lapuSwitch);
        realApiSwitch = (SwitchCompat) findViewById(R.id.realApiSwitch);
        rlCustNumber = (RelativeLayout) findViewById(R.id.rl_cust_number);
        ivPhone = (ImageView) findViewById(R.id.iv_phone);
        amountDetailArrow = (ImageView) findViewById(R.id.amountDetailArrow);
        tvName = (TextView) findViewById(R.id.tv_name);
        etCustNumber = (EditText) findViewById(R.id.et_cust_number);
        MobileNoError = findViewById(R.id.MobileNoError);
        ivPhoneBook = (ImageView) findViewById(R.id.iv_phone_book);
        etAmount = (EditText) findViewById(R.id.et_amount);
        AmountError = findViewById(R.id.AmountError);
        cashBackTv = (AppCompatTextView) findViewById(R.id.cashBackTv);
        desc = (TextView) findViewById(R.id.desc);
        llBrowsePlan = findViewById(R.id.ll_browse_plan);
        tvBrowsePlan = (TextView) findViewById(R.id.tv_browse_plan);
        tvPdfPlan = (TextView) findViewById(R.id.tv_pdf_plan);
        tvRofferPlan = (TextView) findViewById(R.id.tv_roffer_plan);
        numberError = (TextView) findViewById(R.id.numberError);
        historyView = (LinearLayout) findViewById(R.id.historyView);
        recentRechargeTv = (AppCompatTextView) findViewById(R.id.recentRechargeTv);
        viewMore = (AppCompatTextView) findViewById(R.id.viewMore);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ll_coming_soon = findViewById(R.id.ll_coming_soon);
        btRecharge = (Button) findViewById(R.id.bt_recharge);
        rlEtAmount = findViewById(R.id.rl_etAmount);
        billPeriod = findViewById(R.id.billPeriod);
        billPeriodView = findViewById(R.id.pbillPeriodView);
        billDetailclickView = findViewById(R.id.billDetailclickView);
        billDetailRecyclerView = findViewById(R.id.billDetailRecyclerView);
        billDetailRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        additionalInfoRecyclerView = findViewById(R.id.additionalInfoRecyclerView);
        additionalInfoRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        setListners();
        setDataIdWise(fromId);
        getUiData();
    }

    private void getUiData() {
        billDetailTitle.setText(from + " Bill Details");

        if (UtilMethods.INSTANCE.getIsRealAPIPerTransaction(this)) {
            switchView.setVisibility(View.VISIBLE);
            lapuSwitch.setChecked(true);
        } else {
            switchView.setVisibility(View.GONE);
        }


        OperatorList mOperatorList = (OperatorList) getIntent().getSerializableExtra("SelectedOperator");
        if (mOperatorList != null) {
            setIntentData(mOperatorList);
        }

    }

    private void setListners() {
        btRecharge.setOnClickListener(this);
        ivPhoneBook.setOnClickListener(this);
        viewMore.setOnClickListener(this);
        tvBrowsePlan.setOnClickListener(this);
        tvPdfPlan.setOnClickListener(this);
        tvRofferPlan.setOnClickListener(this);
findViewById(R.id.historyViewTab).setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        rechargeView.setVisibility(View.GONE);
        btRecharge.setVisibility(View.GONE);
        historyView.setVisibility(View.VISIBLE);
    }
});findViewById(R.id.rechargeViewTab).setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        rechargeView.setVisibility(View.VISIBLE);
        btRecharge.setVisibility(View.VISIBLE);
        historyView.setVisibility(View.GONE);
    }
});
        llDropDownOption1.setOnClickListener(view -> {
            if (dropDownOption1Array != null && dropDownOption1Array.size() > 0) {
                mDropDownDialog.showDropDownPopup(view, selectedDropDownOption1Pos, dropDownOption1Array, (clickPosition, value, object) -> {

                            dropDownOption1Error.setVisibility(View.GONE);
                            if (selectedDropDownOption1Pos != clickPosition) {
                                tvDropDownOption1.setText(value + "");
                                selectedDropDownOption1Pos = clickPosition;
                            }
                        }
                );
            } else {
                dropDownOption1Error.setVisibility(View.VISIBLE);
                dropDownOption1Error.setText("Data is not available for selection");
                tvDropDownOption1.requestFocus();
            }
        });

        llDropDownOption2.setOnClickListener(view -> {
            if (dropDownOption2Array != null && dropDownOption2Array.size() > 0) {
                mDropDownDialog.showDropDownPopup(view, selectedDropDownOption2Pos, dropDownOption2Array, (clickPosition, value, object) -> {
                            dropDownOption2Error.setVisibility(View.GONE);

                            if (selectedDropDownOption2Pos != clickPosition) {
                                tvDropDownOption2.setText(value + "");
                                selectedDropDownOption2Pos = clickPosition;
                            }
                        }
                );
            } else {
                dropDownOption2Error.setVisibility(View.VISIBLE);
                dropDownOption2Error.setText("Data is not available for selection");
                tvDropDownOption2.requestFocus();
            }
        });

        llDropDownOption3.setOnClickListener(view -> {
            if (dropDownOption3Array != null && dropDownOption3Array.size() > 0) {
                mDropDownDialog.showDropDownPopup(view, selectedDropDownOption3Pos, dropDownOption3Array, (clickPosition, value, object) -> {
                            dropDownOption3Error.setVisibility(View.GONE);
                            if (selectedDropDownOption3Pos != clickPosition) {
                                tvDropDownOption3.setText(value + "");
                                selectedDropDownOption3Pos = clickPosition;
                            }
                        }
                );
            } else {
                dropDownOption3Error.setVisibility(View.VISIBLE);
                dropDownOption3Error.setText("Data is not available for selection");
                tvDropDownOption3.requestFocus();
            }
        });


        llDropDownOption4.setOnClickListener(view -> {
            if (dropDownOption4Array != null && dropDownOption4Array.size() > 0) {
                mDropDownDialog.showDropDownPopup(view, selectedDropDownOption4Pos, dropDownOption4Array, (clickPosition, value, object) -> {
                            dropDownOption4Error.setVisibility(View.GONE);
                            if (selectedDropDownOption4Pos != clickPosition) {
                                tvDropDownOption4.setText(value + "");
                                selectedDropDownOption4Pos = clickPosition;
                            }
                        }
                );
            } else {
                dropDownOption4Error.setVisibility(View.VISIBLE);
                dropDownOption4Error.setText("Data is not available for selection");
                tvDropDownOption4.requestFocus();
            }
        });

        cashBackTv.setOnClickListener(v ->

        {
            if (operatorSelectedId != 0) {
                if (incentiveList != null && incentiveList.size() > 0 && incentiveOperatorSelectedId == operatorSelectedId) {
                    showPopupIncentive();
                } else {
                    HitIncentiveApi();
                }
            } else {
                UtilMethods.INSTANCE.Error(this, "Please Select Operator.");
            }
        });

        heavyRefresh.setOnClickListener(v ->

        {
            if (!validateNumber()) {
                return;
            }
            HeavyRefresh();
        });


        etNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
               /* if (!validateNumber()) {
                    return;
                }*/
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {

                    MobileNoCount.setVisibility(View.VISIBLE);
                    MobileNoCount.setText("Number Digit- " + s.length());
                } else {
                    MobileNoCount.setVisibility(View.GONE);
                }


                if (maxNumberLength != 0 && maxNumberLength != s.length()) {
                    tvName.setText("");
                    tvName.setVisibility(View.GONE);
                }
                if (maxNumberLength != 0 && maxNumberLength == s.length()) {
                    etAmount.requestFocus();
                    numberError.setVisibility(View.GONE);

                    if (fromId == 3) {
                        if (UtilMethods.INSTANCE.getIsDTHInfoAutoCall(SecondRechargeActivity.this)) {
                            DTHinfo();
                        }
                    }
                }
                /*if (fromId==3 && UtilMethods.INSTANCE.getIsDTHInfoAutoCall(SecondBillPaymentActivity.this)) {
                    if (s.length() == maxNumberLength) {
                        DTHinfo();
                    }
                }*/
            }
        });

        if (fromId == 3) {
            if (UtilMethods.INSTANCE.getIsShowPDFPlan(SecondRechargeActivity.this)) {
                tvPdfPlan.setVisibility(View.VISIBLE);
            } else {
                tvPdfPlan.setVisibility(View.GONE);
            }
            etAmount.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                    if (desc.getVisibility() == View.VISIBLE) {
                        desc.setVisibility(View.GONE);
                    }
                    desc.setText("");

                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }


        lapuSwitch.setOnCheckedChangeListener((buttonView, isChecked) ->

        {
            if (isChecked) {
                lapuSwitch.setTextColor(getResources().getColor(R.color.colorAccent));
                realApiSwitch.setTextColor(getResources().getColor(R.color.grey));
                realApiSwitch.setChecked(false);
                if (fromId == 1 || fromId == 32) {
                    if (mLoginDataResponse.isDenominationIncentive()) {
                        cashBackTv.setVisibility(View.VISIBLE);
                    } else {
                        cashBackTv.setVisibility(View.GONE);
                    }
                    if (desc.getText().toString().contains("Cash Back")) {
                        desc.setVisibility(View.VISIBLE);
                    }
                }
            } else {
                if (cashBackTv.getVisibility() == View.VISIBLE) {
                    cashBackTv.setVisibility(View.GONE);
                    desc.setVisibility(View.GONE);
                }
                realApiSwitch.setChecked(true);
            }
        });

        realApiSwitch.setOnCheckedChangeListener((buttonView, isChecked) ->

        {
            if (isChecked) {
                lapuSwitch.setTextColor(getResources().getColor(R.color.grey));
                realApiSwitch.setTextColor(getResources().getColor(R.color.colorAccent));
                lapuSwitch.setChecked(false);
                if (operatorSelectedId != 0 && realCommisionStr != null && !realCommisionStr.isEmpty()) {
                    realApiSwitch.setText("Recharge With Real " + realCommisionStr);
                } else {
                    realApiSwitch.setText("Recharge With Real");
                }

            } else {
                realApiSwitch.setText("Recharge With Real");
                lapuSwitch.setChecked(true);
            }
        });

        billDetailclickView.setOnClickListener(view ->

        {
            if (amountDetailArrow.getVisibility() == View.VISIBLE) {
                if (billDetailRecyclerView.getVisibility() == View.VISIBLE) {
                    billDetailRecyclerView.setVisibility(View.GONE);
                    paybleAmtView.setBackgroundColor(0);
                    amountDetailArrow.setRotation(0);
                } else {
                    amountDetailArrow.setRotation(180);
                    billDetailRecyclerView.setVisibility(View.VISIBLE);
                    paybleAmtView.setBackgroundColor(getResources().getColor(R.color.devider_color_alpha));
                }
            }
        });
    }

    @Override
    public void onClick(View v) {

        if (v == viewMore) {
            Intent intent = new Intent(this, RechargeHistory.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        } /*else if (v == ivNumberPhoneBook) {
            openPhoneBook(PICK_CONTACT, INTENT_PERMISSION);
        } else if (v == ivPhoneBook) {
            openPhoneBook(PICK_CUSTOMER_CONTACT, INTENT_CUSTOMER_PERMISSION);
        }*/ else if (v == tvRofferPlan) {
            if (!validateNumber()) {
                return;
            }

            if (fromId == 3) {
                DTHinfo();
            }

        } else if (v == tvPdfPlan) {
            if (operatorSelectedId == 0 || tvOperator.getText().toString().isEmpty()) {
                msgSpeak = "Please select operator first.";
                Toast.makeText(this, msgSpeak, Toast.LENGTH_SHORT).show();
                playVoice();
                return;
            } else if (operatorDocName == null || operatorDocName.isEmpty()) {
                msgSpeak = "Document not available, please try after some time.";
                Toast.makeText(this, msgSpeak, Toast.LENGTH_LONG).show();
                playVoice();
                return;
            }
            try {
                Intent dialIntent = new Intent(Intent.ACTION_VIEW);
                dialIntent.setData(Uri.parse(ApplicationConstant.INSTANCE.baseUrl + "Image/planDoc/" + operatorDocName));
                startActivity(dialIntent);
            } catch (Exception e) {
                Intent dialIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(ApplicationConstant.INSTANCE.baseUrl + "Image/planDoc/" + operatorDocName));
                startActivity(dialIntent);
            }

        } else if (v == tvBrowsePlan) {

            if (operatorSelectedId == 0 || tvOperator.getText().toString().isEmpty()) {
                msgSpeak = "Please select operator first.";
                Toast.makeText(this, msgSpeak, Toast.LENGTH_SHORT).show();
                playVoice();
                return;
            }
            if (fromId == 3) {
                ViewPlan();
            }

        } else if (v == btRecharge) {

            if (!isOnboardingSuccess && mOnboardingResponse != null) {
                mCustomPassDialog = new CustomAlertDialog(this,true);
                UtilMethods.INSTANCE.showCallOnBoardingMsgs(this, mOnboardingResponse, mCustomPassDialog);
                return;
            } else if (!isOnboardingSuccess && mOnboardingResponse == null) {
                loader.show();
                checkOnBoarding();
                return;
            }

            if (isFetchBill) {
                if (!validateFetchBillNumber()) {
                    return;
                }
                if (UtilMethods.INSTANCE.isNetworkAvialable(SecondRechargeActivity.this)) {

                    if (UtilMethods.INSTANCE.getLattitude != 0 && UtilMethods.INSTANCE.getLongitude != 0) {
                        fetchBill(UtilMethods.INSTANCE.getLattitude, UtilMethods.INSTANCE.getLongitude);
                    } else {
                        if (mGetLocation != null) {
                            mGetLocation.startLocationUpdates((lattitude, longitude) -> {
                                UtilMethods.INSTANCE.getLattitude = lattitude;
                                UtilMethods.INSTANCE.getLongitude = longitude;
                                fetchBill(lattitude, longitude);
                            });
                        } else {
                            mGetLocation = new GetLocation(SecondRechargeActivity.this, loader);
                            mGetLocation.startLocationUpdates((lattitude, longitude) -> {
                                UtilMethods.INSTANCE.getLattitude = lattitude;
                                UtilMethods.INSTANCE.getLongitude = longitude;
                                fetchBill(lattitude, longitude);
                            });
                        }
                    }

                } else {
                    UtilMethods.INSTANCE.NetworkError(SecondRechargeActivity.this);
                }
            } else {
                if (!validateNumber()) {
                    return;
                } else if (rlEtAmount.getVisibility() == View.VISIBLE && !validateAmount()) {
                    return;
                }

                if (realApiSwitch.isChecked() && switchView.getVisibility() == View.VISIBLE) {
                    UtilMethods.INSTANCE.GetCalculateLapuRealCommission(SecondRechargeActivity.this, operatorSelectedId + "", etAmount.getText().toString().trim(), loader, new UtilMethods.ApiCallBackTwoMethod() {
                        @Override
                        public void onSucess(Object object) {
                            CommissionDisplay mCommissionDisplay = (CommissionDisplay) object;
                            RechargeDialog(mCommissionDisplay);
                        }

                        @Override
                        public void onError(Object object) {

                        }
                    });
                } else {
                    RechargeDialog(null);
                }
                // RechargeDialog();

            }
        }

    }

    private void fetchBill(double lattitude, double longitude) {
        loader.show();
        loader.setCancelable(false);
        loader.setCanceledOnTouchOutside(false);

        String option1Value = "", option2Value = "", option3Value = "", option4Value = "";

        if (rlOption1.getVisibility() == View.VISIBLE) {
            option1Value = etOption1.getText().toString();
        } else if (llDropDownOption1.getVisibility() == View.VISIBLE) {
            option1Value = tvDropDownOption1.getText().toString();
        }

        if (rlOption2.getVisibility() == View.VISIBLE) {
            option2Value = etOption2.getText().toString();
        } else if (llDropDownOption2.getVisibility() == View.VISIBLE) {
            option2Value = tvDropDownOption2.getText().toString();
        }

        if (rlOption3.getVisibility() == View.VISIBLE) {
            option3Value = etOption3.getText().toString();
        } else if (llDropDownOption3.getVisibility() == View.VISIBLE) {
            option3Value = tvDropDownOption3.getText().toString();
        }

        if (rlOption4.getVisibility() == View.VISIBLE) {
            option4Value = etOption4.getText().toString();
        } else if (llDropDownOption4.getVisibility() == View.VISIBLE) {
            option4Value = tvDropDownOption4.getText().toString();
        }
        UtilMethods.INSTANCE.FetchBillApi(SecondRechargeActivity.this, etCustNumber.getText().toString(),
                operatorSelectedId + "", etNumber.getText().toString().trim(),
                option1Value, option2Value, option3Value, option4Value,
                lattitude + "," + longitude,
                "10", loader, btRecharge, mLoginDataResponse,
                 new UtilMethods.ApiCallBackTwoMethod() {
                    @Override
                    public void onSucess(Object object) {
                        FetchBillResponse response = (FetchBillResponse) object;
                        rlBillDetail.setVisibility(View.VISIBLE);
                        billDetailContent.setVisibility(View.VISIBLE);
                        billDetailTitle.setVisibility(View.VISIBLE);
                        errorMsgBilldetail.setVisibility(View.GONE);
                        isFetchBill = false;
                        exactness = response.getbBPSResponse().getExactness();
                        fetchBillRefId = response.getbBPSResponse().getRefID();
                        fetchBillId = response.getbBPSResponse().getFetchBillID();
                        etAmount.setText(response.getbBPSResponse().getAmount());
                        try {
                            billAmount = Double.parseDouble(response.getbBPSResponse().getAmount());
                        } catch (NumberFormatException nfe) {

                        }
                        rlEtAmount.setVisibility(View.GONE);
                        btRecharge.setText("Bill Payment");

                        if (response.getbBPSResponse().getCustomerName() != null && !response.getbBPSResponse().getCustomerName().isEmpty()) {
                            consumerName.setText(response.getbBPSResponse().getCustomerName() + "");
                            consumerNameView.setVisibility(View.VISIBLE);
                        } else {
                            consumerNameView.setVisibility(View.GONE);
                        }

                        if (response.getbBPSResponse().getBillNumber() != null && !response.getbBPSResponse().getBillNumber().isEmpty()) {
                            consumerNum.setText(response.getbBPSResponse().getBillNumber() + "");
                            consumerNumView.setVisibility(View.VISIBLE);
                        } else {
                            consumerNumView.setVisibility(View.GONE);
                        }

                        if (response.getbBPSResponse().getAmount() != null && !response.getbBPSResponse().getAmount().isEmpty()) {
                            paybleAmt.setText(getString(R.string.rupiya) + " " + response.getbBPSResponse().getAmount() + "");
                            etAmount.setText(response.getbBPSResponse().getAmount() + "");
                            paybleAmtView.setVisibility(View.VISIBLE);
                        } else {
                            paybleAmtView.setVisibility(View.GONE);
                        }

                        if (response.getbBPSResponse().getBillDate() != null && !response.getbBPSResponse().getBillDate().isEmpty()) {
                            bilDate.setText(response.getbBPSResponse().getBillDate() + "");
                            bilDateView.setVisibility(View.VISIBLE);
                        } else {
                            bilDateView.setVisibility(View.GONE);
                        }
                        if (response.getbBPSResponse().getDueDate() != null && !response.getbBPSResponse().getDueDate().isEmpty()) {
                            dueDate.setText(response.getbBPSResponse().getDueDate() + "");
                            dueDateView.setVisibility(View.VISIBLE);
                        } else {
                            dueDateView.setVisibility(View.GONE);
                        }
                        if (response.getbBPSResponse().getBillPeriod() != null && !response.getbBPSResponse().getBillPeriod().isEmpty()) {
                            billPeriod.setText(response.getbBPSResponse().getBillPeriod() + "");
                            billPeriodView.setVisibility(View.VISIBLE);
                        } else {
                            billPeriodView.setVisibility(View.GONE);
                        }


                        if (response.getbBPSResponse().getIsEditable() && exactness != 1) {
                            rlEtAmount.setVisibility(View.VISIBLE);
                            ll_customerNum.setVisibility(View.VISIBLE);
                        } else {
                            rlEtAmount.setVisibility(View.GONE);
                        }


                        if (response.getbBPSResponse().getIsEnablePayment()) {
                            isFetchBill = false;
                            btRecharge.setText("Bill Payment");
                        } else {
                            isFetchBill = true;
                            btRecharge.setText("Fetch Bill");

                        }


                        if (response.getbBPSResponse().getBillAmountOptions() != null && response.getbBPSResponse().getBillAmountOptions().size() > 0) {

                            amountDetailArrow.setVisibility(View.VISIBLE);
                            paybleAmtView.setBackgroundColor(0);
                            billDetailRecyclerView.setVisibility(View.GONE);
                            billDetailRecyclerView.setAdapter(new BillDetailAdapter(response.getbBPSResponse().getBillAmountOptions(),
                                    SecondRechargeActivity.this, R.layout.adapter_bill_detail));
                        } else {
                            paybleAmtView.setBackgroundColor(0);
                            billDetailRecyclerView.setVisibility(View.GONE);
                            amountDetailArrow.setVisibility(View.GONE);
                        }
                        if (response.getbBPSResponse().getBillAdditionalInfo() != null && response.getbBPSResponse().getBillAdditionalInfo().size() > 0) {
                            additionalInfoRecyclerView.setVisibility(View.VISIBLE);
                            additionalInfoRecyclerView.setAdapter(new BillDetailAdapter(response.getbBPSResponse().getBillAdditionalInfo(),
                                    SecondRechargeActivity.this, R.layout.adapter_aditional_info));
                        } else {
                            additionalInfoRecyclerView.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onError(Object object) {
                        isFetchBill = false;
                        if (object instanceof String) {
                            billDetailContent.setVisibility(View.GONE);
                            rlBillDetail.setVisibility(View.VISIBLE);
                            billDetailTitle.setVisibility(View.VISIBLE);
                            errorMsgBilldetail.setVisibility(View.VISIBLE);
                            errorMsgBilldetail.setText((String) object);
                            rlEtAmount.setVisibility(View.VISIBLE);
                            ll_customerNum.setVisibility(View.VISIBLE);

                        }
                        if (object instanceof FetchBillResponse) {
                            FetchBillResponse response = (FetchBillResponse) object;

                            if (response != null && response.getbBPSResponse() != null) {
                                if (response.getbBPSResponse().getIsShowMsgOnly()) {
                                    billDetailContent.setVisibility(View.GONE);
                                    rlBillDetail.setVisibility(View.VISIBLE);
                                    billDetailTitle.setVisibility(View.VISIBLE);
                                    errorMsgBilldetail.setVisibility(View.VISIBLE);
                                    errorMsgBilldetail.setText(response.getbBPSResponse().getErrorMsg());

                                } else {
                                    billDetailContent.setVisibility(View.GONE);
                                    rlBillDetail.setVisibility(View.GONE);
                                    billDetailTitle.setVisibility(View.GONE);
                                    errorMsgBilldetail.setVisibility(View.GONE);
                                }


                                if (response.getbBPSResponse().getIsEditable()) {
                                    rlEtAmount.setVisibility(View.VISIBLE);
                                    ll_customerNum.setVisibility(View.VISIBLE);
                                } else {
                                    rlEtAmount.setVisibility(View.GONE);
                                }


                                if (response.getbBPSResponse().getIsEnablePayment()) {
                                    isFetchBill = false;
                                    btRecharge.setText("Bill Payment");
                                } else {
                                    isFetchBill = true;
                                    btRecharge.setText("Fetch Bill");

                                }

                            }

                        }


                    }
                });
    }


   /* void openPhoneBook(int intentRequest, int inentPermisssion) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                startActivityForResult(new Intent(this, PermissionActivity.class),
                        inentPermisssion);

            } else {
                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivityForResult(intent, intentRequest);
            }
        } else {
            Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivityForResult(intent, intentRequest);
        }
    }*/

    void setDataIdWise(int id) {


         if (id == 3) {
            isRecharge = true;
            isOnboardingSuccess = true;
            refreshData(id);
             rlCustNumber.setVisibility(View.GONE);
            if (mLoginDataResponse.isDenominationIncentive()) {
                cashBackTv.setVisibility(View.VISIBLE);
            } else {
                cashBackTv.setVisibility(View.GONE);
            }
            if (UtilMethods.INSTANCE.getIsHeavyRefresh(this)) {
                heavyRefresh.setVisibility(View.VISIBLE);
            } else {
                heavyRefresh.setVisibility(View.GONE);
            }
            if (UtilMethods.INSTANCE.getIsDTHInfo(this)) {
                tvRofferPlan.setVisibility(View.VISIBLE);
                tvRofferPlan.setText("DTH Info");
            } else {
                tvRofferPlan.setVisibility(View.GONE);
            }
            llBrowsePlan.setVisibility(View.VISIBLE);
             ll_customerNum.setVisibility(View.VISIBLE);
            /*recentRechargeTv.setText("Recent Recharges");*/
            //  ivNumberPhoneBook.setVisibility(View.GONE);


        }
         else if (id == 32) {
            isOnboardingSuccess = true;
            isRecharge = true;
            refreshData(id);

            rlCustNumber.setVisibility(View.GONE);

            if (mLoginDataResponse.isDenominationIncentive()) {
                cashBackTv.setVisibility(View.VISIBLE);
            } else {
                cashBackTv.setVisibility(View.GONE);
            }
            llBrowsePlan.setVisibility(View.GONE);
            // ivNumberPhoneBook.setVisibility(View.VISIBLE);
            /*recentRechargeTv.setText("Recent Recharges");*/

        } else if (id == 33) {
            isOnboardingSuccess = true;
            isRecharge = true;
            refreshData(id);
            rlCustNumber.setVisibility(View.GONE);
            if (mLoginDataResponse.isDenominationIncentive()) {
                cashBackTv.setVisibility(View.VISIBLE);
            } else {
                cashBackTv.setVisibility(View.GONE);
            }
            llBrowsePlan.setVisibility(View.GONE);
            // ivNumberPhoneBook.setVisibility(View.VISIBLE);
            /*recentRechargeTv.setText("Recent Recharges");*/

        } else {
            isRecharge = false;
            refreshData(id);


            /*recentRechargeTv.setText("Recent Bill Payments");*/
            cashBackTv.setVisibility(View.GONE);
            llBrowsePlan.setVisibility(View.GONE);
            heavyRefresh.setVisibility(View.GONE);
            // ivNumberPhoneBook.setVisibility(View.GONE);
        }
    }

    void refreshData(int id) {
        if (id == 1 || id == 2 || id == 3 || id == 32 || id == 33) {
            operatorSelected = "";
            operatorSelectedId = 0;
            operatorDocName = "";
            minAmountLength = 0;
            maxAmountLength = 0;
            minNumberLength = 0;
            maxNumberLength = 0;
            regularExpress = "";
            isAcountNumeric = false;
            isPartial = false;
            isBBPS = false;
            isBilling = false;
            AccountName = "Number";
            Account_Remark = "";
            StartWith = "";
            Icon = "";
            billLogo.setVisibility(View.GONE);
            ivOprator.setImageResource(R.drawable.ic_tower);

            etNumber.setHint("Enter " + from + " Number");
            tvOperator.setText("");
            AcountRemarkTv.setText("");
            AcountRemarkTv.setVisibility(View.GONE);
            desc.setText("");
            desc.setVisibility(View.GONE);
            rlEtAmount.setVisibility(View.VISIBLE);
            ll_customerNum.setVisibility(View.VISIBLE);
        }
        if(isRecharge){
            isFetchBill = false;
            btRecharge.setText("Recharge");
            rlEtAmount.setVisibility(View.VISIBLE);
            ll_customerNum.setVisibility(View.VISIBLE);
        }else {
            rlEtAmount.setVisibility(View.GONE);
            if (isBilling) {
                isFetchBill = true;
                btRecharge.setText("Fetch Bill");

            }else {
                isFetchBill = false;
                btRecharge.setText("Bill Payment");
            }
        }
        /*if (!isRecharge && isBilling) {
            isFetchBill = true;
            btRecharge.setText("Fetch Bill");
            rlEtAmount.setVisibility(View.GONE);
        } else {
            isFetchBill = false;
            if (isRecharge) {
                btRecharge.setText("Recharge");
            } else {
                btRecharge.setText("Bill Payment");
            }
            rlEtAmount.setVisibility(View.VISIBLE);
            ll_customerNum.setVisibility(View.VISIBLE);
        }*/


        etCustNumber.setText("");
        MobileNoError.setVisibility(View.GONE);
        etOption1.setText("");
        option1Error.setVisibility(View.GONE);
        etOption2.setText("");
        option2Error.setVisibility(View.GONE);
        etOption3.setText("");
        option3Error.setVisibility(View.GONE);
        etOption4.setText("");
        option4Error.setVisibility(View.GONE);
        etAmount.setText("");
        AmountError.setVisibility(View.GONE);
        etNumber.setText("");
        numberError.setVisibility(View.GONE);
        tvName.setVisibility(View.GONE);
        rlDthInfoDetail.setVisibility(View.GONE);
        rlBillDetail.setVisibility(View.GONE);
        heavyRefresh.setVisibility(View.GONE);

    }

    public void HitApi() {
        if (UtilMethods.INSTANCE.isNetworkAvialable(this)) {
            UtilMethods.INSTANCE.RechargeReport(this, "0", "10", 0, fromDateStr, toDateStr,
                    "", "", "", "false", true, loader, new UtilMethods.ApiCallBackTwoMethod() {
                @Override
                public void onSucess(Object object) {
                    RechargeReportResponse mRechargeReportResponse = (RechargeReportResponse) object;
                    dataParse(mRechargeReportResponse);
                }

                        @Override
                        public void onError(Object object) {
                            ll_coming_soon.setVisibility(View.VISIBLE);
                        }
                    });

        } else {
            UtilMethods.INSTANCE.NetworkError(this);
        }
    }

    public void dataParse(RechargeReportResponse response) {

        ArrayList<RechargeStatus> transactionsObjects = new ArrayList<>();
        transactionsObjects = response.getRechargeReport();
        if (transactionsObjects.size() > 0) {
            RechargeReportAdapter mAdapter = new RechargeReportAdapter(transactionsObjects, this, false,
                    mLoginDataResponse.getData().getRoleID());

            ll_coming_soon.setVisibility(View.GONE);
            recyclerView.setAdapter(mAdapter);
        } else {
            ll_coming_soon.setVisibility(View.VISIBLE);
        }
    }




    private void RechargeDialog(CommissionDisplay mCommissionDisplay) {

        if (UtilMethods.INSTANCE.isVpnConnected(this)) {
            return;
        }

        if (UtilMethods.INSTANCE.getLattitude != 0 && UtilMethods.INSTANCE.getLongitude != 0) {
            recharge(UtilMethods.INSTANCE.getLattitude, UtilMethods.INSTANCE.getLongitude, mCommissionDisplay);
        } else {
            if (mGetLocation != null) {
                mGetLocation.startLocationUpdates((lattitude, longitude) -> {
                    UtilMethods.INSTANCE.getLattitude = lattitude;
                    UtilMethods.INSTANCE.getLongitude = longitude;
                    recharge(lattitude, longitude, mCommissionDisplay);
                });
            } else {
                mGetLocation = new GetLocation(SecondRechargeActivity.this, loader);
                mGetLocation.startLocationUpdates((lattitude, longitude) -> {
                    UtilMethods.INSTANCE.getLattitude = lattitude;
                    UtilMethods.INSTANCE.getLongitude = longitude;
                    recharge(lattitude, longitude, mCommissionDisplay);
                });
            }
        }

    }

  /*  private void recharge(double lattitude, double longitude, CommissionDisplay
            mCommissionDisplay) {

        UtilMethods.INSTANCE.rechargeConfiormDialog(this, mCommissionDisplay,
                realApiSwitch.isChecked() ? true : false, UtilMethods.INSTANCE.getDoubleFactorPref(this),
                ApplicationConstant.INSTANCE.baseIconUrl + Icon, etNumber.getText().toString(), operatorSelected,
                etAmount.getText().toString(), new UtilMethods.DialogCallBack() {
            @Override
            public void onPositiveClick(String pinPass) {
                btRecharge.setEnabled(false);
                if (UtilMethods.INSTANCE.isNetworkAvialable(SecondRechargeActivity.this)) {
                    loader.show();
                    loader.setCancelable(false);
                    loader.setCanceledOnTouchOutside(false);
                            String option1Value = "", option2Value = "", option3Value = "", option4Value = "";

                            if (rlOption1.getVisibility() == View.VISIBLE) {
                                option1Value = etOption1.getText().toString();
                            } else if (llDropDownOption1.getVisibility() == View.VISIBLE) {
                                option1Value = tvDropDownOption1.getText().toString();
                            }

                            if (rlOption2.getVisibility() == View.VISIBLE) {
                                option2Value = etOption2.getText().toString();
                            } else if (llDropDownOption2.getVisibility() == View.VISIBLE) {
                                option2Value = tvDropDownOption2.getText().toString();
                            }

                            if (rlOption3.getVisibility() == View.VISIBLE) {
                                option3Value = etOption3.getText().toString();
                            } else if (llDropDownOption3.getVisibility() == View.VISIBLE) {
                                option3Value = tvDropDownOption3.getText().toString();
                            }

                            if (rlOption4.getVisibility() == View.VISIBLE) {
                                option4Value = etOption4.getText().toString();
                            } else if (llDropDownOption4.getVisibility() == View.VISIBLE) {
                                option4Value = tvDropDownOption4.getText().toString();
                            }

                    UtilMethods.INSTANCE.Recharge(SecondRechargeActivity.this, realApiSwitch.isChecked() ? true : false,
                            operatorSelectedId, etNumber.getText().toString().trim(),
                            etAmount.getText().toString().trim(), option1Value, option2Value,
                            option3Value, option4Value, etCustNumber.getText().toString(),
                            fetchBillRefId != null ? fetchBillRefId : "",fetchBillId,
                            lattitude + "," + longitude
                            , pinPass, loader);

                        } else {
                            UtilMethods.INSTANCE.NetworkError(SecondRechargeActivity.this);
                        }
                    }

                    @Override
                    public void onResetCallback(String value) {

                    }

                    @Override
                    public void onCancelClick() {

                    }
                });


    }*/
  private void recharge(double lattitude, double longitude, CommissionDisplay
          mCommissionDisplay) {

      UtilMethods.INSTANCE.rechargeConfiormDialog(this, incentiveMap, mCommissionDisplay,
              realApiSwitch.isChecked() ? true : false, UtilMethods.INSTANCE.getDoubleFactorPref(this),
              ApplicationConstant.INSTANCE.baseIconUrl + Icon, etNumber.getText().toString(), operatorSelected,
              etAmount.getText().toString(), new UtilMethods.DialogCallBack() {
                  @Override
                  public void onPositiveClick(String pinPass) {
                      btRecharge.setEnabled(false);
                      if (UtilMethods.INSTANCE.isNetworkAvialable(SecondRechargeActivity.this)) {
                          loader.show();
                          loader.setCancelable(false);
                          loader.setCanceledOnTouchOutside(false);
                          String option1Value = "", option2Value = "", option3Value = "", option4Value = "";

                          if (rlOption1.getVisibility() == View.VISIBLE) {
                              option1Value = etOption1.getText().toString();
                          } else if (llDropDownOption1.getVisibility() == View.VISIBLE) {
                              option1Value = tvDropDownOption1.getText().toString();
                          }

                          if (rlOption2.getVisibility() == View.VISIBLE) {
                              option2Value = etOption2.getText().toString();
                          } else if (llDropDownOption2.getVisibility() == View.VISIBLE) {
                              option2Value = tvDropDownOption2.getText().toString();
                          }

                          if (rlOption3.getVisibility() == View.VISIBLE) {
                              option3Value = etOption3.getText().toString();
                          } else if (llDropDownOption3.getVisibility() == View.VISIBLE) {
                              option3Value = tvDropDownOption3.getText().toString();
                          }

                          if (rlOption4.getVisibility() == View.VISIBLE) {
                              option4Value = etOption4.getText().toString();
                          } else if (llDropDownOption4.getVisibility() == View.VISIBLE) {
                              option4Value = tvDropDownOption4.getText().toString();
                          }

                          UtilMethods.INSTANCE.Recharge(SecondRechargeActivity.this, realApiSwitch.isChecked() ? true : false,
                                  operatorSelectedId, operatorSelected, etNumber.getText().toString().trim(),
                                  etAmount.getText().toString().trim(), option1Value, option2Value,
                                  option3Value, option4Value, etCustNumber.getText().toString(),
                                  fetchBillRefId != null ? fetchBillRefId : "", fetchBillId,
                                  lattitude + "," + longitude
                                  , pinPass, loader);

                      } else {
                          UtilMethods.INSTANCE.NetworkError(SecondRechargeActivity.this);
                      }
                  }

                  @Override
                  public void onResetCallback(String value) {

                  }

                  @Override
                  public void onCancelClick() {

                  }
              });


  }
    void getLapuRealCommission() {
        UtilMethods.INSTANCE.GetLapuRealCommission(this, operatorSelectedId + "",
                mLoginDataResponse, object -> {
                    RealLapuCommissionSlab data = (RealLapuCommissionSlab) object;
                    String comType, comm, rComType, rComm;
                    if (data.getCommType() == 0) {
                        comType = "(COM)";
                    } else {
                        comType = "(SUR)";
                    }

                    if (data.getAmtType() == 0) {
                        comm = UtilMethods.INSTANCE.formatedAmount(data.getComm() + "") + " %";
                    } else {
                        comm = UtilMethods.INSTANCE.formatedAmountWithRupees(data.getComm() + "");
                    }

                    if (data.getrCommType() == 0) {
                        rComType = "(COM)";
                    } else {
                        rComType = "(SUR)";
                    }

                    if (data.getrAmtType() == 0) {
                        rComm = UtilMethods.INSTANCE.formatedAmount(data.getrComm() + "") + " %";
                    } else {
                        rComm = UtilMethods.INSTANCE.formatedAmountWithRupees(data.getrComm() + "");
                    }
                    lapuSwitch.setText("Lapu\n" + comm + " " + comType);
                    realCommisionStr = rComm + " " + rComType;
                    if (realApiSwitch.isChecked()) {
                        realApiSwitch.setText("Recharge With Real " + realCommisionStr);
                    }


                });
    }


    public void BBPSApi() {
        try {
            loader.show();
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);

            Call<OperatorOptionalResponse> call = git.GetOperatorOptionals(new OptionalOperatorRequest(operatorSelectedId + "",
                    mLoginDataResponse.getData().getUserID(), mLoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID, UtilMethods.INSTANCE.getIMEI(this), "",
                    BuildConfig.VERSION_NAME, UtilMethods.INSTANCE.getSerialNo(this),
                    mLoginDataResponse.getData().getSessionID(), mLoginDataResponse.getData().getSession()));


            call.enqueue(new Callback<OperatorOptionalResponse>() {
                @Override
                public void onResponse(Call<OperatorOptionalResponse> call, final Response<OperatorOptionalResponse> response) {

                    try {
                        if (loader != null && loader.isShowing()) {
                            loader.dismiss();
                        }
                        if (response.isSuccessful()) {
                            OperatorOptionalResponse mOperatorOptionalResponse = response.body();
                            if (mOperatorOptionalResponse != null) {

                                if (mOperatorOptionalResponse.getStatuscode() == 1) {
                                    if (mOperatorOptionalResponse.getData() != null && mOperatorOptionalResponse.getData().getOperatorParams() != null
                                            && mOperatorOptionalResponse.getData().getOperatorParams().size() > 0) {
                                        hideShowOptionalParameter2(mOperatorOptionalResponse.getData().getOperatorParams(), mOperatorOptionalResponse.getData().getOpOptionalDic());
                                    } else if (mOperatorOptionalResponse.getData() != null && mOperatorOptionalResponse.getData().getOperatorOptionals() != null
                                            && mOperatorOptionalResponse.getData().getOperatorOptionals().size() > 0) {
                                        hideShowOptionalParameter1(mOperatorOptionalResponse.getData().getOperatorOptionals());
                                    } else {
                                        if (mNumberListResponse != null && mNumberListResponse.isTakeCustomerNo() && isTakeCustomerNum) {
                                            rlCustNumber.setVisibility(View.VISIBLE);
                                            ll_customerNum.setVisibility(View.VISIBLE);
                                        } else {
                                            rlCustNumber.setVisibility(View.GONE);
                                        }
                                    }

                                } else {

                                    UtilMethods.INSTANCE.Error(SecondRechargeActivity.this, mOperatorOptionalResponse.getMsg() + "");
                                }

                            } else {
                                UtilMethods.INSTANCE.Error(SecondRechargeActivity.this, "Something went wrong, try after some time.");
                            }
                        } else {
                            UtilMethods.INSTANCE.apiErrorHandle(SecondRechargeActivity.this, response.code(), response.message());
                        }
                    } catch (Exception e) {
                        if (loader != null && loader.isShowing()) {
                            loader.dismiss();
                        }
                        UtilMethods.INSTANCE.Error(SecondRechargeActivity.this, e.getMessage() + "");
                    }

                }

                @Override
                public void onFailure(Call<OperatorOptionalResponse> call, Throwable t) {

                    try {
                        if (loader != null && loader.isShowing()) {
                            loader.dismiss();
                        }

                        UtilMethods.INSTANCE.apiFailureError(SecondRechargeActivity.this, t);


                    } catch (IllegalStateException ise) {
                        if (loader != null && loader.isShowing()) {
                            loader.dismiss();
                        }
                        UtilMethods.INSTANCE.Error(SecondRechargeActivity.this, ise.getMessage());

                    }

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            if (loader != null && loader.isShowing()) {
                loader.dismiss();
            }
        }
    }


    void hideShowOptionalParameter1(List<OperatorOptional> mOperatorOptionals) {
        for (int i = 0; i < mOperatorOptionals.size(); i++) {
            if (mOperatorOptionals.get(i).getOptionalType() == 1) {
                rlOption1.setVisibility(View.VISIBLE);
                etOption1.setHint("Enter " + mOperatorOptionals.get(i).getDisplayName());
                option1Remark.setVisibility(View.VISIBLE);
                option1Remark.setText(mOperatorOptionals.get(i).getRemark() + "");
            }
            if (mOperatorOptionals.get(i).getOptionalType() == 2) {
                rlOption2.setVisibility(View.VISIBLE);
                etOption2.setHint("Enter " + mOperatorOptionals.get(i).getDisplayName());
                option2Remark.setVisibility(View.VISIBLE);
                option2Remark.setText(mOperatorOptionals.get(i).getRemark() + "");
            }
            if (mOperatorOptionals.get(i).getOptionalType() == 3) {
                rlOption3.setVisibility(View.VISIBLE);
                etOption3.setHint("Enter " + mOperatorOptionals.get(i).getDisplayName());
                option3Remark.setVisibility(View.VISIBLE);
                option3Remark.setText(mOperatorOptionals.get(i).getRemark() + "");
            }
            if (mOperatorOptionals.get(i).getOptionalType() == 4) {
                rlOption4.setVisibility(View.VISIBLE);
                etOption4.setHint("Enter " + mOperatorOptionals.get(i).getDisplayName());
                option4Remark.setVisibility(View.VISIBLE);
                option4Remark.setText(mOperatorOptionals.get(i).getRemark() + "");
            }
        }

        if (mNumberListResponse != null && mNumberListResponse.isTakeCustomerNo() && isTakeCustomerNum) {
            rlCustNumber.setVisibility(View.VISIBLE);
            ll_customerNum.setVisibility(View.VISIBLE);
        } else {
            rlCustNumber.setVisibility(View.GONE);
        }
    }

    void hideShowOptionalParameter2(List<OperatorParams> mOperatorOptionalsList, List<OpOptionalDic> mOpOptionalDic) {
        boolean isCustomerNum = false;
        for (OperatorParams mOperatorOptionals : mOperatorOptionalsList) {

            if (!isCustomerNum && mOperatorOptionals.isCustomerNo()) {
                isCustomerNum = true;
            }

            if (mOperatorOptionals.getInd() == 1) {
                if (mOperatorOptionals.isDropDown() && mOpOptionalDic != null && mOpOptionalDic.size() > 0) {
                    dropDownOption1Array = new ArrayList<>();
                    for (OpOptionalDic item : mOpOptionalDic) {
                        if (item.getOptionalID() == mOperatorOptionals.getId()) {
                            dropDownOption1Array.add(item.getValue() + "");
                        }
                    }

                    if (dropDownOption1Array != null && dropDownOption1Array.size() > 0) {
                        llDropDownOption1.setVisibility(View.VISIBLE);
                        tvDropDownOption1.setHint("Select " + mOperatorOptionals.getParamName());
                        if (mOperatorOptionals.getRemark() != null && !mOperatorOptionals.getRemark().isEmpty()) {
                            dropDownOption1Remark.setVisibility(View.VISIBLE);
                            dropDownOption1Remark.setText(mOperatorOptionals.getRemark() + "");
                        }
                    } else {
                        setupOptionalView(mOperatorOptionals, rlOption1, etOption1, option1Remark);
                    }


                } else {

                    setupOptionalView(mOperatorOptionals, rlOption1, etOption1, option1Remark);
                }
            }
            if (mOperatorOptionals.getInd() == 2) {
                if (mOperatorOptionals.isDropDown() && mOpOptionalDic != null && mOpOptionalDic.size() > 0) {
                    dropDownOption2Array = new ArrayList<>();
                    for (OpOptionalDic item : mOpOptionalDic) {
                        if (item.getOptionalID() == mOperatorOptionals.getId()) {
                            dropDownOption2Array.add(item.getValue() + "");
                        }
                    }

                    if (dropDownOption2Array != null && dropDownOption2Array.size() > 0) {
                        llDropDownOption2.setVisibility(View.VISIBLE);
                        tvDropDownOption2.setHint("Select " + mOperatorOptionals.getParamName());
                        if (mOperatorOptionals.getRemark() != null && !mOperatorOptionals.getRemark().isEmpty()) {
                            dropDownOption2Remark.setVisibility(View.VISIBLE);
                            dropDownOption2Remark.setText(mOperatorOptionals.getRemark() + "");
                        }
                    } else {
                        setupOptionalView(mOperatorOptionals, rlOption2, etOption2, option2Remark);
                    }


                } else {
                    setupOptionalView(mOperatorOptionals, rlOption2, etOption2, option2Remark);
                }
            }
            if (mOperatorOptionals.getInd() == 3) {
                if (mOperatorOptionals.isDropDown() && mOpOptionalDic != null && mOpOptionalDic.size() > 0) {

                    dropDownOption3Array = new ArrayList<>();
                    for (OpOptionalDic item : mOpOptionalDic) {
                        if (item.getOptionalID() == mOperatorOptionals.getId()) {
                            dropDownOption3Array.add(item.getValue() + "");
                        }
                    }

                    if (dropDownOption3Array != null && dropDownOption3Array.size() > 0) {
                        llDropDownOption3.setVisibility(View.VISIBLE);
                        tvDropDownOption3.setHint("Select " + mOperatorOptionals.getParamName());
                        if (mOperatorOptionals.getRemark() != null && !mOperatorOptionals.getRemark().isEmpty()) {
                            dropDownOption3Remark.setVisibility(View.VISIBLE);
                            dropDownOption3Remark.setText(mOperatorOptionals.getRemark() + "");
                        }
                    } else {
                        setupOptionalView(mOperatorOptionals, rlOption3, etOption3, option3Remark);
                    }


                } else {
                    setupOptionalView(mOperatorOptionals, rlOption3, etOption3, option3Remark);
                }
            }
            if (mOperatorOptionals.getInd() == 4) {
                if (mOperatorOptionals.isDropDown() && mOpOptionalDic != null && mOpOptionalDic.size() > 0) {
                    dropDownOption4Array = new ArrayList<>();
                    for (OpOptionalDic item : mOpOptionalDic) {
                        if (item.getOptionalID() == mOperatorOptionals.getId()) {
                            dropDownOption4Array.add(item.getValue() + "");
                        }
                    }

                    if (dropDownOption4Array != null && dropDownOption4Array.size() > 0) {
                        llDropDownOption4.setVisibility(View.VISIBLE);
                        tvDropDownOption4.setHint("Select " + mOperatorOptionals.getParamName());
                        if (mOperatorOptionals.getRemark() != null && !mOperatorOptionals.getRemark().isEmpty()) {
                            dropDownOption4Remark.setVisibility(View.VISIBLE);
                            dropDownOption4Remark.setText(mOperatorOptionals.getRemark() + "");
                        }
                    } else {
                        setupOptionalView(mOperatorOptionals, rlOption4, etOption4, option4Remark);
                    }


                } else {
                    setupOptionalView(mOperatorOptionals, rlOption4, etOption4, option4Remark);
                }
            }
        }

        if (!isCustomerNum) {
            if (mNumberListResponse != null && mNumberListResponse.isTakeCustomerNo() && isTakeCustomerNum) {
                rlCustNumber.setVisibility(View.VISIBLE);
                ll_customerNum.setVisibility(View.VISIBLE);
            } else {
                rlCustNumber.setVisibility(View.GONE);
            }
        } else {
            rlCustNumber.setVisibility(View.GONE);
        }
    }

    /* void hideShowOptionalParameter2
             (List<OperatorParams> mOperatorOptionalsList, List<OpOptionalDic> mOpOptionalDic) {
         boolean isCustomerNum = false;
         for (OperatorParams mOperatorOptionals : mOperatorOptionalsList) {

             if (!isCustomerNum && mOperatorOptionals.isCustomerNo()) {
                 isCustomerNum = true;
             }

             if (mOperatorOptionals.getInd() == 1) {
                 if (mOperatorOptionals.isDropDown() && mOpOptionalDic != null && mOpOptionalDic.size() > 0) {
                     dropDownOption1Array = new ArrayList<>();
                     for (OpOptionalDic item : mOpOptionalDic) {
                         if (item.getOptionalID() == mOperatorOptionals.getId()) {
                             dropDownOption1Array.add(item.getValue() + "");
                         }
                     }


                     llDropDownOption1.setVisibility(View.VISIBLE);
                     tvDropDownOption1.setHint("Select " + mOperatorOptionals.getParamName());
                     if (mOperatorOptionals.getRemark() != null && !mOperatorOptionals.getRemark().isEmpty()) {
                         dropDownOption1Remark.setVisibility(View.VISIBLE);
                         dropDownOption1Remark.setText(mOperatorOptionals.getRemark() + "");
                     }


                 } else {

                     rlOption1.setTag(mOperatorOptionals);
                     rlOption1.setVisibility(View.VISIBLE);

                     if (mOperatorOptionals.getDataType().equalsIgnoreCase("NUMERIC")) {
                         etOption1.setInputType(InputType.TYPE_CLASS_NUMBER);
                     } else {
                         etOption1.setInputType(InputType.TYPE_CLASS_TEXT);
                     }

                     if (mOperatorOptionals.getMaxLength() > 0) {
                         InputFilter[] filterArray = new InputFilter[1];
                         filterArray[0] = new InputFilter.LengthFilter(mOperatorOptionals.getMaxLength());
                         etOption1.setFilters(filterArray);
                     }
                     etOption1.setHint("Enter " + mOperatorOptionals.getParamName());
                     if (mOperatorOptionals.getCustomRemark() != null && !mOperatorOptionals.getCustomRemark().isEmpty()) {
                         option1Remark.setVisibility(View.VISIBLE);
                         option1Remark.setText(mOperatorOptionals.getCustomRemark() + "");
                     }
                 }
             }
             if (mOperatorOptionals.getInd() == 2) {
                 if (mOperatorOptionals.isDropDown() && mOpOptionalDic != null && mOpOptionalDic.size() > 0) {
                     dropDownOption2Array = new ArrayList<>();
                     for (OpOptionalDic item : mOpOptionalDic) {
                         if (item.getOptionalID() == mOperatorOptionals.getId()) {
                             dropDownOption2Array.add(item.getValue() + "");
                         }
                     }

                     llDropDownOption2.setVisibility(View.VISIBLE);
                     tvDropDownOption2.setHint("Select " + mOperatorOptionals.getParamName());
                     if (mOperatorOptionals.getRemark() != null && !mOperatorOptionals.getRemark().isEmpty()) {
                         dropDownOption2Remark.setVisibility(View.VISIBLE);
                         dropDownOption2Remark.setText(mOperatorOptionals.getRemark() + "");
                     }


                 } else {
                     rlOption2.setTag(mOperatorOptionals);
                     rlOption2.setVisibility(View.VISIBLE);

                     if (mOperatorOptionals.getDataType().equalsIgnoreCase("NUMERIC")) {
                         etOption2.setInputType(InputType.TYPE_CLASS_NUMBER);
                     } else {
                         etOption2.setInputType(InputType.TYPE_CLASS_TEXT);
                     }

                     if (mOperatorOptionals.getMaxLength() > 0) {
                         InputFilter[] filterArray = new InputFilter[1];
                         filterArray[0] = new InputFilter.LengthFilter(mOperatorOptionals.getMaxLength());
                         etOption2.setFilters(filterArray);
                     }
                     etOption2.setHint("Enter " + mOperatorOptionals.getParamName());
                     if (mOperatorOptionals.getCustomRemark() != null && !mOperatorOptionals.getCustomRemark().isEmpty()) {
                         option2Remark.setVisibility(View.VISIBLE);
                         option2Remark.setText(mOperatorOptionals.getCustomRemark() + "");
                     }
                 }
             }
             if (mOperatorOptionals.getInd() == 3) {
                 if (mOperatorOptionals.isDropDown() && mOpOptionalDic != null && mOpOptionalDic.size() > 0) {

                     dropDownOption3Array = new ArrayList<>();
                     for (OpOptionalDic item : mOpOptionalDic) {
                         if (item.getOptionalID() == mOperatorOptionals.getId()) {
                             dropDownOption3Array.add(item.getValue() + "");
                         }
                     }

                     llDropDownOption3.setVisibility(View.VISIBLE);
                     tvDropDownOption3.setHint("Select " + mOperatorOptionals.getParamName());
                     if (mOperatorOptionals.getRemark() != null && !mOperatorOptionals.getRemark().isEmpty()) {
                         dropDownOption3Remark.setVisibility(View.VISIBLE);
                         dropDownOption3Remark.setText(mOperatorOptionals.getRemark() + "");
                     }


                 } else {
                     rlOption3.setTag(mOperatorOptionals);
                     rlOption3.setVisibility(View.VISIBLE);

                     if (mOperatorOptionals.getDataType().equalsIgnoreCase("NUMERIC")) {
                         etOption3.setInputType(InputType.TYPE_CLASS_NUMBER);
                     } else {
                         etOption3.setInputType(InputType.TYPE_CLASS_TEXT);
                     }

                     if (mOperatorOptionals.getMaxLength() > 0) {
                         InputFilter[] filterArray = new InputFilter[1];
                         filterArray[0] = new InputFilter.LengthFilter(mOperatorOptionals.getMaxLength());
                         etOption3.setFilters(filterArray);
                     }
                     etOption3.setHint("Enter " + mOperatorOptionals.getParamName());
                     if (mOperatorOptionals.getCustomRemark() != null && !mOperatorOptionals.getCustomRemark().isEmpty()) {
                         option3Remark.setVisibility(View.VISIBLE);
                         option3Remark.setText(mOperatorOptionals.getCustomRemark() + "");
                     }
                 }
             }
             if (mOperatorOptionals.getInd() == 4) {
                 if (mOperatorOptionals.isDropDown() && mOpOptionalDic != null && mOpOptionalDic.size() > 0) {
                     dropDownOption4Array = new ArrayList<>();
                     for (OpOptionalDic item : mOpOptionalDic) {
                         if (item.getOptionalID() == mOperatorOptionals.getId()) {
                             dropDownOption4Array.add(item.getValue() + "");
                         }
                     }

                     llDropDownOption4.setVisibility(View.VISIBLE);
                     tvDropDownOption4.setHint("Select " + mOperatorOptionals.getParamName());
                     if (mOperatorOptionals.getRemark() != null && !mOperatorOptionals.getRemark().isEmpty()) {
                         dropDownOption4Remark.setVisibility(View.VISIBLE);
                         dropDownOption4Remark.setText(mOperatorOptionals.getRemark() + "");
                     }


                 } else {
                     rlOption4.setTag(mOperatorOptionals);
                     rlOption4.setVisibility(View.VISIBLE);

                     if (mOperatorOptionals.getDataType().equalsIgnoreCase("NUMERIC")) {
                         etOption4.setInputType(InputType.TYPE_CLASS_NUMBER);
                     } else {
                         etOption4.setInputType(InputType.TYPE_CLASS_TEXT);
                     }

                     if (mOperatorOptionals.getMaxLength() > 0) {
                         InputFilter[] filterArray = new InputFilter[1];
                         filterArray[0] = new InputFilter.LengthFilter(mOperatorOptionals.getMaxLength());
                         etOption4.setFilters(filterArray);
                     }
                     etOption4.setHint("Enter " + mOperatorOptionals.getParamName());
                     if (mOperatorOptionals.getCustomRemark() != null && !mOperatorOptionals.getCustomRemark().isEmpty()) {
                         option4Remark.setVisibility(View.VISIBLE);
                         option4Remark.setText(mOperatorOptionals.getCustomRemark() + "");
                     }
                 }
             }
         }

         if (!isCustomerNum) {
             if (operatorListResponse != null && operatorListResponse.isTakeCustomerNo() && isTakeCustomerNum) {
                 rlCustNumber.setVisibility(View.VISIBLE);
                 ll_customerNum.setVisibility(View.VISIBLE);
             } else {
                 rlCustNumber.setVisibility(View.GONE);
             }
         } else {
             rlCustNumber.setVisibility(View.GONE);
         }
     }
 */

    private void setupOptionalView(OperatorParams mOperatorOptionals, LinearLayout rlOption, EditText etOption, TextView optionRemark) {
        rlOption.setTag(mOperatorOptionals);
        rlOption.setVisibility(View.VISIBLE);

        if (mOperatorOptionals.getDataType().equalsIgnoreCase("NUMERIC")) {
            etOption.setInputType(InputType.TYPE_CLASS_NUMBER);
        } else {
            etOption.setInputType(InputType.TYPE_CLASS_TEXT);
        }

        if (mOperatorOptionals.getMaxLength() > 0) {
            InputFilter[] filterArray = new InputFilter[1];
            filterArray[0] = new InputFilter.LengthFilter(mOperatorOptionals.getMaxLength());
            etOption.setFilters(filterArray);
        }
        etOption.setHint("Enter " + mOperatorOptionals.getParamName());
        if (mOperatorOptionals.getCustomRemark() != null && !mOperatorOptionals.getCustomRemark().isEmpty()) {
            optionRemark.setVisibility(View.VISIBLE);
            optionRemark.setText(mOperatorOptionals.getCustomRemark() + "");
        }
    }


    private boolean validateNumber() {
        numberError.setVisibility(View.GONE);
        option1Error.setVisibility(View.GONE);
        option2Error.setVisibility(View.GONE);
        option3Error.setVisibility(View.GONE);
        option4Error.setVisibility(View.GONE);
        dropDownOption1Error.setVisibility(View.GONE);
        dropDownOption2Error.setVisibility(View.GONE);
        dropDownOption3Error.setVisibility(View.GONE);
        dropDownOption4Error.setVisibility(View.GONE);

        if ((fromId != 1 && fromId != 2) && (operatorSelectedId == 0 || tvOperator.getText().toString().isEmpty())) {
            msgSpeak = "Please select operator first.";
            Toast.makeText(this, msgSpeak, Toast.LENGTH_SHORT).show();
            playVoice();
            return false;
        } else if (etNumber.getText().toString().trim().isEmpty()) {
            msgSpeak = "Please enter valid " + AccountName.trim();
            numberError.setVisibility(View.VISIBLE);
            numberError.setText(msgSpeak);
            etNumber.requestFocus();
            playVoice();
            return false;
        } else if (StartWithArray != null && StartWithArray.size() > 0 && !checkContains(StartWithArray, etNumber.getText().toString().trim())) {
            msgSpeak = AccountName.trim() + " must be start with " + StartWith;
            numberError.setVisibility(View.VISIBLE);
            numberError.setText(msgSpeak);
            etNumber.requestFocus();
            playVoice();
            return false;

        } else if (minNumberLength != 0 && maxNumberLength != 0 && minNumberLength != maxNumberLength && etNumber.getText().length() < minNumberLength) {
            msgSpeak = AccountName.trim() + " must be length of " + minNumberLength + " to " + maxNumberLength + (isAcountNumeric ? " digits" : " characters");
            numberError.setVisibility(View.VISIBLE);
            numberError.setText(msgSpeak);
            etNumber.requestFocus();
            playVoice();
            return false;
        } else if (minNumberLength != 0 && maxNumberLength != 0 && minNumberLength != maxNumberLength && etNumber.getText().length() > maxNumberLength) {
            msgSpeak = AccountName.trim() + " must be length of " + minNumberLength + " to " + maxNumberLength + (isAcountNumeric ? " digits" : " characters");
            numberError.setVisibility(View.VISIBLE);
            numberError.setText(msgSpeak);
            etNumber.requestFocus();
            playVoice();
            return false;
        } else if (minNumberLength != 0 && maxNumberLength != 0 && minNumberLength == maxNumberLength && etNumber.getText().length() != maxNumberLength) {
            msgSpeak = AccountName.trim() + " must be length of " + maxNumberLength + (isAcountNumeric ? " digits" : " characters");
            numberError.setVisibility(View.VISIBLE);
            numberError.setText(msgSpeak);
            etNumber.requestFocus();
            playVoice();
            return false;
        } else if (minNumberLength != 0 && maxNumberLength == 0 && etNumber.getText().length() != minNumberLength) {
            msgSpeak = AccountName.trim() + " must be length of " + minNumberLength + (isAcountNumeric ? " digits" : " characters");
            numberError.setVisibility(View.VISIBLE);
            numberError.setText(msgSpeak);
            etNumber.requestFocus();
            playVoice();
            return false;
        } else if (minNumberLength == 0 && maxNumberLength != 0 && etNumber.getText().length() != maxNumberLength) {
            msgSpeak = AccountName.trim() + " must be length of " + maxNumberLength + (isAcountNumeric ? " digits" : " characters");
            numberError.setVisibility(View.VISIBLE);
            numberError.setText(msgSpeak);
            etNumber.requestFocus();
            playVoice();
            return false;
        } else if (regularExpress != null && !regularExpress.isEmpty() && !etNumber.getText().toString().matches(regularExpress)) {
            msgSpeak = "Please enter valid " + AccountName.trim();
            numberError.setVisibility(View.VISIBLE);
            numberError.setText(msgSpeak);
            etNumber.requestFocus();
            playVoice();
            return false;
        } else if (operatorSelectedId == 0 || tvOperator.getText().toString().isEmpty()) {
            msgSpeak = "Please select operator first.";
            Toast.makeText(this, msgSpeak, Toast.LENGTH_SHORT).show();
            playVoice();
            return false;
        } else if (rlOption1.getVisibility() == View.VISIBLE && !validateOptionalParam(rlOption1, etOption1)) {
            option1Error.setVisibility(View.VISIBLE);
            option1Error.setText(msgSpeak);
            etOption1.requestFocus();
            playVoice();
            return false;
        } else if (llDropDownOption1.getVisibility() == View.VISIBLE && tvDropDownOption1.getText().toString().isEmpty()) {
            msgSpeak = "Please " + tvDropDownOption1.getHint().toString() + " first.";
            dropDownOption1Error.setVisibility(View.VISIBLE);
            dropDownOption1Error.setText(msgSpeak);
            tvDropDownOption1.requestFocus();
            playVoice();
            return false;
        } else if (rlOption2.getVisibility() == View.VISIBLE && !validateOptionalParam(rlOption2, etOption2)) {
            option2Error.setVisibility(View.VISIBLE);
            option2Error.setText(msgSpeak);
            etOption2.requestFocus();
            playVoice();
            return false;
        } else if (llDropDownOption2.getVisibility() == View.VISIBLE && tvDropDownOption2.getText().toString().isEmpty()) {
            msgSpeak = "Please " + tvDropDownOption2.getHint().toString() + " first.";
            dropDownOption2Error.setVisibility(View.VISIBLE);
            dropDownOption2Error.setText(msgSpeak);
            tvDropDownOption2.requestFocus();
            playVoice();
            return false;
        } else if (rlOption3.getVisibility() == View.VISIBLE && !validateOptionalParam(rlOption3, etOption3)) {
            option3Error.setVisibility(View.VISIBLE);
            option3Error.setText(msgSpeak);
            etOption3.requestFocus();
            playVoice();
            return false;
        } else if (llDropDownOption3.getVisibility() == View.VISIBLE && tvDropDownOption3.getText().toString().isEmpty()) {
            msgSpeak = "Please " + tvDropDownOption3.getHint().toString() + " first.";
            dropDownOption3Error.setVisibility(View.VISIBLE);
            dropDownOption3Error.setText(msgSpeak);
            tvDropDownOption3.requestFocus();
            playVoice();
            return false;
        } else if (rlOption4.getVisibility() == View.VISIBLE && !validateOptionalParam(rlOption4, etOption4)) {
            option4Error.setVisibility(View.VISIBLE);
            option4Error.setText(msgSpeak);
            etOption4.requestFocus();
            playVoice();
            return false;
        } else if (llDropDownOption4.getVisibility() == View.VISIBLE && tvDropDownOption4.getText().toString().isEmpty()) {
            msgSpeak = "Please " + tvDropDownOption4.getHint().toString() + " first.";
            dropDownOption4Error.setVisibility(View.VISIBLE);
            dropDownOption4Error.setText(msgSpeak);
            tvDropDownOption4.requestFocus();
            playVoice();
            return false;
        } else if (rlCustNumber.getVisibility() == View.VISIBLE && etCustNumber.getText().toString().trim().isEmpty()) {
            msgSpeak = "Customer Mobile Number field can't be empty";
            MobileNoError.setVisibility(View.VISIBLE);
            MobileNoError.setText(msgSpeak);
            etCustNumber.requestFocus();
            playVoice();
            return false;
        } else if (rlCustNumber.getVisibility() == View.VISIBLE && etCustNumber.getText().toString().length() != 10) {
            msgSpeak = "Enter a valid Customer Mobile Number.";
            MobileNoError.setVisibility(View.VISIBLE);
            MobileNoError.setText(msgSpeak);
            etCustNumber.requestFocus();
            playVoice();
            return false;
        }

        return true;
    }


    private boolean validateFetchBillNumber() {
        numberError.setVisibility(View.GONE);
        option1Error.setVisibility(View.GONE);
        option2Error.setVisibility(View.GONE);
        option3Error.setVisibility(View.GONE);
        option4Error.setVisibility(View.GONE);
        dropDownOption1Error.setVisibility(View.GONE);
        dropDownOption2Error.setVisibility(View.GONE);
        dropDownOption3Error.setVisibility(View.GONE);
        dropDownOption4Error.setVisibility(View.GONE);

        if (etNumber.getText().toString().trim().isEmpty()) {
            msgSpeak = "Please enter valid " + AccountName.trim();
            numberError.setVisibility(View.VISIBLE);
            numberError.setText(msgSpeak);
            etNumber.requestFocus();
            playVoice();
            return false;
        } else if (StartWithArray != null && StartWithArray.size() > 0 && !checkContains(StartWithArray, etNumber.getText().toString().trim())) {
            msgSpeak = AccountName.trim() + " must be start with " + StartWith;
            numberError.setVisibility(View.VISIBLE);
            numberError.setText(msgSpeak);
            etNumber.requestFocus();
            playVoice();
            return false;
        } else if (minNumberLength != 0 && maxNumberLength != 0 && minNumberLength != maxNumberLength && etNumber.getText().length() < minNumberLength) {
            msgSpeak = AccountName.trim() + " must be length of " + minNumberLength + " to " + maxNumberLength + (isAcountNumeric ? " digits" : " characters");
            numberError.setVisibility(View.VISIBLE);
            numberError.setText(msgSpeak);
            etNumber.requestFocus();
            playVoice();
            return false;
        } else if (minNumberLength != 0 && maxNumberLength != 0 && minNumberLength != maxNumberLength && etNumber.getText().length() > maxNumberLength) {
            msgSpeak = AccountName.trim() + " must be length of " + minNumberLength + " to " + maxNumberLength + (isAcountNumeric ? " digits" : " characters");
            numberError.setVisibility(View.VISIBLE);
            numberError.setText(msgSpeak);
            etNumber.requestFocus();
            playVoice();
            return false;
        } else if (minNumberLength != 0 && maxNumberLength != 0 && minNumberLength == maxNumberLength && etNumber.getText().length() != maxNumberLength) {
            msgSpeak = AccountName.trim() + " must be length of " + maxNumberLength + (isAcountNumeric ? " digits" : " characters");
            numberError.setVisibility(View.VISIBLE);
            numberError.setText(msgSpeak);
            etNumber.requestFocus();
            playVoice();
            return false;
        } else if (minNumberLength != 0 && maxNumberLength == 0 && etNumber.getText().length() != minNumberLength) {
            msgSpeak = AccountName.trim() + " must be length of " + minNumberLength + (isAcountNumeric ? " digits" : " characters");
            numberError.setVisibility(View.VISIBLE);
            numberError.setText(msgSpeak);
            etNumber.requestFocus();
            playVoice();
            return false;
        } else if (minNumberLength == 0 && maxNumberLength != 0 && etNumber.getText().length() != maxNumberLength) {
            msgSpeak = AccountName.trim() + " must be length of " + maxNumberLength + (isAcountNumeric ? " digits" : " characters");
            numberError.setVisibility(View.VISIBLE);
            numberError.setText(msgSpeak);
            etNumber.requestFocus();
            playVoice();
            return false;
        } else if (regularExpress != null && !regularExpress.isEmpty() && !etNumber.getText().toString().matches(regularExpress)) {
            msgSpeak = "Please enter valid " + AccountName.trim();
            numberError.setVisibility(View.VISIBLE);
            numberError.setText(msgSpeak);
            etNumber.requestFocus();
            playVoice();
            return false;
        } else if (rlOption1.getVisibility() == View.VISIBLE && !validateOptionalParam(rlOption1, etOption1)) {
            option1Error.setVisibility(View.VISIBLE);
            option1Error.setText(msgSpeak);
            etOption1.requestFocus();
            playVoice();
            return false;
        } else if (llDropDownOption1.getVisibility() == View.VISIBLE && tvDropDownOption1.getText().toString().isEmpty()) {
            msgSpeak = "Please " + tvDropDownOption1.getHint().toString() + " first.";
            dropDownOption1Error.setVisibility(View.VISIBLE);
            dropDownOption1Error.setText(msgSpeak);
            tvDropDownOption1.requestFocus();
            playVoice();
            return false;
        } else if (rlOption2.getVisibility() == View.VISIBLE && !validateOptionalParam(rlOption2, etOption2)) {
            option2Error.setVisibility(View.VISIBLE);
            option2Error.setText(msgSpeak);
            etOption2.requestFocus();
            playVoice();
            return false;
        } else if (llDropDownOption2.getVisibility() == View.VISIBLE && tvDropDownOption2.getText().toString().isEmpty()) {
            msgSpeak = "Please " + tvDropDownOption2.getHint().toString() + " first.";
            dropDownOption2Error.setVisibility(View.VISIBLE);
            dropDownOption2Error.setText(msgSpeak);
            tvDropDownOption2.requestFocus();
            playVoice();
            return false;
        } else if (rlOption3.getVisibility() == View.VISIBLE && !validateOptionalParam(rlOption3, etOption3)) {
            option3Error.setVisibility(View.VISIBLE);
            option3Error.setText(msgSpeak);
            etOption3.requestFocus();
            playVoice();
            return false;
        } else if (llDropDownOption3.getVisibility() == View.VISIBLE && tvDropDownOption3.getText().toString().isEmpty()) {
            msgSpeak = "Please " + tvDropDownOption3.getHint().toString() + " first.";
            dropDownOption3Error.setVisibility(View.VISIBLE);
            dropDownOption3Error.setText(msgSpeak);
            tvDropDownOption3.requestFocus();
            playVoice();
            return false;
        } else if (rlOption4.getVisibility() == View.VISIBLE && !validateOptionalParam(rlOption4, etOption4)) {
            option4Error.setVisibility(View.VISIBLE);
            option4Error.setText(msgSpeak);
            etOption4.requestFocus();
            playVoice();
            return false;
        } else if (llDropDownOption4.getVisibility() == View.VISIBLE && tvDropDownOption4.getText().toString().isEmpty()) {
            msgSpeak = "Please " + tvDropDownOption4.getHint().toString() + " first.";
            dropDownOption4Error.setVisibility(View.VISIBLE);
            dropDownOption4Error.setText(msgSpeak);
            tvDropDownOption4.requestFocus();
            playVoice();
            return false;
        } else if (rlCustNumber.getVisibility() == View.VISIBLE && etCustNumber.getText().toString().trim().isEmpty()) {
            msgSpeak = "Customer Mobile Number field can't be empty";
            MobileNoError.setVisibility(View.VISIBLE);
            MobileNoError.setText(msgSpeak);
            etCustNumber.requestFocus();
            playVoice();
            return false;
        } else if (rlCustNumber.getVisibility() == View.VISIBLE && etCustNumber.getText().toString().length() != 10) {
            msgSpeak = "Enter a valid Customer Mobile Number.";
            MobileNoError.setVisibility(View.VISIBLE);
            MobileNoError.setText(msgSpeak);
            etCustNumber.requestFocus();
            playVoice();
            return false;
        }

        return true;
    }


    boolean validateOptionalParam(LinearLayout llOption, EditText etOption) {
        if (etOption.getText().toString().trim().isEmpty()) {
            msgSpeak = etOption.getHint() + ", field can't be empty";
            return false;
        } else if (llOption.getTag() != null) {
            OperatorParams mOperatorParams = (OperatorParams) llOption.getTag();
            if (mOperatorParams != null) {
                String postfix = " ";
                if (mOperatorParams.getDataType().equalsIgnoreCase("NUMERIC")) {
                    postfix = " digits ";
                } else {
                    postfix = " characters ";
                }
                if (mOperatorParams.getMinLength() != 0 && mOperatorParams.getMaxLength() != 0 &&
                        mOperatorParams.getMinLength() != mOperatorParams.getMaxLength() &&
                        etOption.getText().length() < mOperatorParams.getMinLength()) {
                    msgSpeak = "Invalid length of " + mOperatorParams.getParamName().trim() + ", please enter a valid " + mOperatorParams.getMinLength() + " to " +
                            mOperatorParams.getMaxLength() + postfix + mOperatorParams.getParamName().trim();

                    return false;
                } else if (mOperatorParams.getMinLength() != 0 && mOperatorParams.getMaxLength() != 0 &&
                        mOperatorParams.getMinLength() != mOperatorParams.getMaxLength() &&
                        etOption.getText().length() > mOperatorParams.getMaxLength()) {
                    msgSpeak = "Invalid length of " + mOperatorParams.getParamName().trim() + ", please enter a valid " + mOperatorParams.getMinLength() + " to " +
                            mOperatorParams.getMaxLength() + postfix + mOperatorParams.getParamName().trim();
                    return false;
                } else if (mOperatorParams.getMinLength() != 0 && mOperatorParams.getMaxLength() != 0 &&
                        mOperatorParams.getMinLength() == mOperatorParams.getMaxLength() &&
                        etOption.getText().length() != mOperatorParams.getMaxLength()) {
                    msgSpeak = "Invalid length of " + mOperatorParams.getParamName().trim() + ", please enter a valid " + mOperatorParams.getMaxLength() + postfix + mOperatorParams.getParamName().trim();

                    return false;
                } else if (mOperatorParams.getMinLength() != 0 && mOperatorParams.getMaxLength() == 0 &&
                        etOption.getText().length() != mOperatorParams.getMinLength()) {
                    msgSpeak = "Invalid length of " + mOperatorParams.getParamName().trim() + ", please enter a valid " + mOperatorParams.getMinLength() + postfix + mOperatorParams.getParamName().trim();

                    return false;
                } else if (mOperatorParams.getMinLength() == 0 && mOperatorParams.getMaxLength() != 0
                        && etOption.getText().length() != mOperatorParams.getMaxLength()) {
                    msgSpeak = "Invalid length of " + mOperatorParams.getParamName().trim() + ", please enter a valid " + mOperatorParams.getMaxLength() + postfix + mOperatorParams.getParamName().trim();

                    return false;
                } else if (mOperatorParams.getRegEx() != null && !mOperatorParams.getRegEx().isEmpty()) {

                    if (etOption.getText().toString().matches(mOperatorParams.getRegEx())) {
                        return true;
                    } else {
                        msgSpeak = "Please enter a valid " + mOperatorParams.getParamName().trim();
                        return false;
                    }
                }
            } else {
                return true;
            }
        }
        return true;
    }


    boolean checkContains(ArrayList<String> StartWithArray, String value) {
        boolean isPrefixAvailable = false;
        for (String prefix : StartWithArray) {
            if (value.startsWith(prefix)) {
                isPrefixAvailable = true;
                break;
            }
        }
        return isPrefixAvailable;
    }

    private boolean validateAmount() {
        AmountError.setVisibility(View.GONE);
        double amount = 0;
        try {
            if (!etAmount.getText().toString().trim().isEmpty()) {
                amount = Double.parseDouble(etAmount.getText().toString().trim());
            }
        } catch (NumberFormatException nfe) {

        }

        if (etAmount.getText().toString().trim().isEmpty() || amount <= 0) {
            msgSpeak = "Please enter valid amount";
            AmountError.setVisibility(View.VISIBLE);
            AmountError.setText(msgSpeak);
            etAmount.requestFocus();
            playVoice();
            return false;
        } else if (exactness == 2 && billAmount != 0 && maxAmountLength != 0 && !(amount >= billAmount && amount <= maxAmountLength)) {
            msgSpeak = "Amount must be between " + UtilMethods.INSTANCE.formatedAmountWithRupees(billAmount + "") + " to \u20B9 " + maxAmountLength;
            AmountError.setVisibility(View.VISIBLE);
            AmountError.setText(msgSpeak);
            etAmount.requestFocus();
            playVoice();
            return false;
        } else if (exactness == 2 && billAmount != 0 && maxAmountLength == 0 && amount < billAmount) {
            msgSpeak = "Amount can't be less then " + UtilMethods.INSTANCE.formatedAmountWithRupees(billAmount + "");
            AmountError.setVisibility(View.VISIBLE);
            AmountError.setText(msgSpeak);
            etAmount.requestFocus();
            playVoice();
            return false;
        } else if (exactness == 3 && billAmount != 0 && minAmountLength != 0 && !(amount >= minAmountLength && amount <= billAmount)) {
            msgSpeak = "Amount must be between \u20B9 " + minAmountLength + " to " + UtilMethods.INSTANCE.formatedAmountWithRupees(billAmount + "");
            AmountError.setVisibility(View.VISIBLE);
            AmountError.setText(msgSpeak);
            etAmount.requestFocus();
            playVoice();
            return false;
        } else if (exactness == 3 && billAmount != 0 && minAmountLength == 0 && amount > billAmount) {
            msgSpeak = "Amount can't be more then " + UtilMethods.INSTANCE.formatedAmountWithRupees(billAmount + "");
            AmountError.setVisibility(View.VISIBLE);
            AmountError.setText(msgSpeak);
            etAmount.requestFocus();
            playVoice();
            return false;
        } else if (minAmountLength != 0 && amount < minAmountLength) {
            msgSpeak = "Amount can't be less then \u20B9 " + minAmountLength;
            AmountError.setVisibility(View.VISIBLE);
            AmountError.setText(msgSpeak);
            etAmount.requestFocus();
            playVoice();
            return false;
        } else if (maxAmountLength != 0 && amount > maxAmountLength) {
            msgSpeak = "Amount must be less then \u20B9 " + maxAmountLength;
            AmountError.setVisibility(View.VISIBLE);
            AmountError.setText(msgSpeak);
            etAmount.requestFocus();
            playVoice();
            return false;
        } else if (minAmountLength != 0 && maxAmountLength != 0 && !(amount >= minAmountLength && amount <= maxAmountLength)) {
            msgSpeak = "Amount must be between \u20B9 " + minAmountLength + " to \u20B9 " + maxAmountLength;
            AmountError.setVisibility(View.VISIBLE);
            AmountError.setText(msgSpeak);
            etAmount.requestFocus();
            playVoice();
            return false;
        } else {
            AmountError.setVisibility(View.GONE);

        }
        return true;

    }


    public void ViewPlan() {

        if(mLoginDataResponse.isPlanServiceUpdated()){
            Intent planIntent = new Intent(this, DthPlanInfoNewActivity.class);
            planIntent.putExtra("OperatorSelectedId", operatorSelectedId + "");
            planIntent.putExtra("Number", etNumber.getText().toString().trim());
            planIntent.putExtra("IsPlanServiceUpdated", mLoginDataResponse.isPlanServiceUpdated());
            planIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivityForResult(planIntent, INTENT_VIEW_PLAN);
        }else {
               try {
            loader.show();
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            ROfferRequest request = new ROfferRequest(operatorSelectedId + "", etNumber.getText().toString().trim(),
                    ApplicationConstant.INSTANCE.APP_ID,
                    UtilMethods.INSTANCE.getIMEI(SecondRechargeActivity.this),
                    "", BuildConfig.VERSION_NAME, UtilMethods.INSTANCE.getSerialNo(SecondRechargeActivity.this),mLoginDataResponse.getData().getUserID()
                    ,mLoginDataResponse.getData().getLoginTypeID(),
                    mLoginDataResponse.getData().getSessionID(),
                    mLoginDataResponse.getData().getSession());

            Call<DthPlanInfoResponse> call = git.DTHSimplePlanInfo(request);
            call.enqueue(new Callback<DthPlanInfoResponse>() {
                @Override
                public void onResponse(Call<DthPlanInfoResponse> call, final Response<DthPlanInfoResponse> response) {

                    try {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        if (response.body() != null) {

                            if (response.body().getStatuscode() == 1 && response.body().getData() != null && response.body().getData().getRecords() != null) {
                                Intent planIntent = new Intent(SecondRechargeActivity.this, DthPlanInfoActivity.class);
                                planIntent.putExtra("response", response.body().getData().getRecords());
                                planIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                startActivityForResult(planIntent, INTENT_VIEW_PLAN);
                            } else if (response.body().getStatuscode() == 1 && response.body().getDataRP() != null && response.body().getDataRP().getResponse() != null && response.body().getDataRP().getResponse().size() > 0) {
                                Intent planIntent = new Intent(SecondRechargeActivity.this, DthPlanInfoActivity.class);
                                planIntent.putExtra("responseRP", response.body().getDataRP());
                                planIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                startActivityForResult(planIntent, INTENT_VIEW_PLAN);
                            }else if (response.body().getStatuscode() == 1 && response.body().getDataRPDTHWithPackage() != null && response.body().getDataRPDTHWithPackage().getResponse() != null && response.body().getDataRPDTHWithPackage().getResponse().size() > 0) {
                                Intent planIntent = new Intent(SecondRechargeActivity.this, DthPlanInfoActivity.class);
                                planIntent.putExtra("responseRPPackage", response.body().getDataRPDTHWithPackage());
                                planIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                startActivityForResult(planIntent, INTENT_VIEW_PLAN);
                            } else if (response.body().getStatuscode() == 1 && response.body().getDataPA() != null && response.body().getDataPA().getRecords() != null) {
                                Intent planIntent = new Intent(SecondRechargeActivity.this, DthPlanInfoActivity.class);
                                planIntent.putExtra("responsePA", response.body().getDataPA().getRecords());
                                planIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                startActivityForResult(planIntent, INTENT_VIEW_PLAN);
                            } else if (response.body().getStatuscode() == 1 && response.body().getMyPlanData() != null &&
                                    response.body().getMyPlanData().getResult() != null&&
                                    response.body().getMyPlanData().getResult().getRecords() != null) {
                                Intent planIntent = new Intent(SecondRechargeActivity.this, DthPlanInfoActivity.class);
                                planIntent.putExtra("responsePA", response.body().getMyPlanData().getResult().getRecords());
                                planIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                startActivityForResult(planIntent, INTENT_VIEW_PLAN);
                            } else {
                                UtilMethods.INSTANCE.Error(SecondRechargeActivity.this, "Plan not found");
                            }

                        } else {
                            UtilMethods.INSTANCE.Error(SecondRechargeActivity.this, "Something went wrong, try after some time.");
                        }
                    } catch (Exception e) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                    }

                }

                @Override
                public void onFailure(Call<DthPlanInfoResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            loader.dismiss();
                    }
                    try {
                        if (t.getMessage() != null && !t.getMessage().isEmpty()) {

                            if (t.getMessage().contains("No address associated with hostname")) {
                                UtilMethods.INSTANCE.NetworkError(SecondRechargeActivity.this, getString(R.string.err_msg_network_title),
                                        getString(R.string.err_msg_network));
                            } else {
                                UtilMethods.INSTANCE.Error(SecondRechargeActivity.this, t.getMessage());

                            }

                        } else {
                            UtilMethods.INSTANCE.Error(SecondRechargeActivity.this, getString(R.string.some_thing_error));

                        }
                    } catch (IllegalStateException ise) {
                        UtilMethods.INSTANCE.Error(SecondRechargeActivity.this, ise.getMessage());

                    }

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

       if (resultCode == RESULT_OK && requestCode == INTENT_VIEW_PLAN) {
            etAmount.setText(data.getStringExtra("Amount"));
            desc.setVisibility(View.VISIBLE);
            AmountError.setVisibility(View.GONE);
            desc.setText(data.getStringExtra("desc"));
        } else if (requestCode == INTENT_RECHARGE_SLIP) {
            if (resultCode == RESULT_OK) {
                refreshData(fromId);
                HitApi();
            } else {
                HitApi();
            }
        } else {
            if (mGetLocation != null) {
                mGetLocation.onActivityResult(requestCode, resultCode, data);
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


    void setIntentData(OperatorList mOperatorList) {


        operatorSelected = mOperatorList.getName();
        operatorSelectedId = mOperatorList.getOid();
        operatorDocName = mOperatorList.getPlanDocName();
        minAmountLength = mOperatorList.getMin();
        maxAmountLength = mOperatorList.getMax();
        minNumberLength = mOperatorList.getLength();
        maxNumberLength = mOperatorList.getLengthMax();
        isAcountNumeric = mOperatorList.getIsAccountNumeric();
        isTakeCustomerNum = mOperatorList.isTakeCustomerNum();

        isPartial = mOperatorList.getIsPartial();
        isBBPS = mOperatorList.getBBPS();
        isBilling = mOperatorList.getIsBilling();
        AccountName = mOperatorList.getAccountName();
        Account_Remark = mOperatorList.getAccountRemak();
        regularExpress = mOperatorList.getRegExAccount();
        StartWith = mOperatorList.getStartWith();
        Icon = mOperatorList.getImage();

        StartWithArray.clear();

        if (StartWith != null && StartWith.length() > 0 && StartWith.contains(",")) {
            StartWithArray = new ArrayList<>(Arrays.asList(StartWith.trim().split(",")));
        } else if (StartWith != null && !StartWith.isEmpty() && !StartWith.equalsIgnoreCase("0")) {
            StartWithArray.add(StartWith);
        }

        tvOperator.setText(operatorSelected + "");
        if (Icon != null && !Icon.isEmpty()) {
            Glide.with(this)
                    .load(ApplicationConstant.INSTANCE.baseIconUrl + Icon)
                    .apply(requestOptions)
                    .into(ivOprator);
        } else {
            ivOprator.setImageResource(R.drawable.ic_tower);
        }

        if (isAcountNumeric == true) {
            etNumber.setInputType(InputType.TYPE_CLASS_NUMBER);
        } else {
            etNumber.setInputType(InputType.TYPE_CLASS_TEXT);
        }

        if (maxNumberLength != 0 && maxNumberLength > 0) {
            InputFilter[] filterArray = new InputFilter[1];
            filterArray[0] = new InputFilter.LengthFilter(maxNumberLength);
            etNumber.setFilters(filterArray);
        }


        if (!AccountName.equals("")) {
            etNumber.setHint("Enter " + AccountName);
        }
        if (!Account_Remark.equals("")) {
            AcountRemarkTv.setVisibility(View.VISIBLE);
            AcountRemarkTv.setText(Account_Remark.trim());
        } else {
            AcountRemarkTv.setVisibility(View.GONE);
        }
        if (isBilling) {
            isFetchBill = true;
            btRecharge.setText("Fetch Bill");
            rlEtAmount.setVisibility(View.GONE);


        } else {
            isFetchBill = false;
            rlEtAmount.setVisibility(View.VISIBLE);
            ll_customerNum.setVisibility(View.VISIBLE);
        }

        /*if (operatorListResponse != null && operatorListResponse.isTakeCustomerNo() && isTakeCustomerNum) {
            rlCustNumber.setVisibility(View.VISIBLE);
            ll_customerNum.setVisibility(View.VISIBLE);
        } else {
            rlCustNumber.setVisibility(View.GONE);
        }*/

        if (isBBPS) {
            billLogo.setVisibility(View.VISIBLE);
            BBPSApi();
        } else {
            if (mNumberListResponse != null && mNumberListResponse.isTakeCustomerNo() && isTakeCustomerNum) {
                rlCustNumber.setVisibility(View.VISIBLE);
                ll_customerNum.setVisibility(View.VISIBLE);
            } else {
                rlCustNumber.setVisibility(View.GONE);
            }
            billLogo.setVisibility(View.GONE);
        }


        if (isPartial) {
            etAmount.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        } else {
            etAmount.setInputType(InputType.TYPE_CLASS_NUMBER);
        }

        if (switchView.getVisibility() == View.VISIBLE) {
            if (operatorSelectedId != 0) {
                getLapuRealCommission();
            } else {
                lapuSwitch.setText("Lapu");
                realApiSwitch.setText("Recharge With Real");
            }
        }

        if (operatorSelectedId != 0 && switchView.getVisibility() == View.VISIBLE) {
            getLapuRealCommission();
        }
        checkOnBoarding();

    }


    void checkOnBoarding() {
        if (isBBPS) {

            isOnboardingSuccess = false;
            mOnboardingResponse = null;
            if (UtilMethods.INSTANCE.isNetworkAvialable(this)) {
                if (UtilMethods.INSTANCE.getLattitude != 0 && UtilMethods.INSTANCE.getLongitude != 0) {
                    UtilMethods.INSTANCE.CallOnboarding(this, 0, false, getSupportFragmentManager(), operatorSelectedId, "", "0", "", false, true, false,
                            null, null, null,
                            loader, mLoginDataResponse,UtilMethods.INSTANCE.getLattitude + "", UtilMethods.INSTANCE.getLongitude + "", new UtilMethods.ApiCallBackOnBoardingMethod() {
                                @Override
                                public void onSuccess(Object object) {
                                    if (object != null) {
                                        mOnboardingResponse = (OnboardingResponse) object;
                                        if (mOnboardingResponse != null) {
                                            isOnboardingSuccess = true;
                                        }
                                    }
                                }

                                @Override
                                public void onError(Object object) {
                                    mOnboardingResponse = (OnboardingResponse) object;
                                    isOnboardingSuccess = false;
                                }

                                @Override
                                public void onOnBoarding(Object object) {

                                }
                            });

                }
                else {
                    if (mGetLocation != null) {
                        mGetLocation.startLocationUpdates((lattitude, longitude) -> {
                            UtilMethods.INSTANCE.getLattitude = lattitude;
                            UtilMethods.INSTANCE.getLongitude = longitude;
                            UtilMethods.INSTANCE.CallOnboarding(this, 0, false, getSupportFragmentManager(), operatorSelectedId, "", "0", "", false, true, false,
                                    null, null, null,
                                    loader, mLoginDataResponse,UtilMethods.INSTANCE.getLattitude + "", UtilMethods.INSTANCE.getLongitude + "", new UtilMethods.ApiCallBackOnBoardingMethod() {
                                        @Override
                                        public void onSuccess(Object object) {
                                            if (object != null) {
                                                mOnboardingResponse = (OnboardingResponse) object;
                                                if (mOnboardingResponse != null) {
                                                    isOnboardingSuccess = true;
                                                }
                                            }
                                        }

                                        @Override
                                        public void onError(Object object) {
                                            mOnboardingResponse = (OnboardingResponse) object;
                                            isOnboardingSuccess = false;
                                        }

                                        @Override
                                        public void onOnBoarding(Object object) {

                                        }
                                    });
                        });
                    }

                }
            } else {
                isOnboardingSuccess = false;
                UtilMethods.INSTANCE.NetworkError(this);
            }

        } else {
            isOnboardingSuccess = true;
        }
    }


    public void HeavyRefresh() {
        try {
            loader.show();
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<DthPlanInfoResponse> call;
            if (mLoginDataResponse.isPlanServiceUpdated()) {
                call = git.GetRNPDTHHeavyRefresh(new HeavyrefreshRequest(operatorSelectedId + "", etNumber.getText().toString(),
                        ApplicationConstant.INSTANCE.APP_ID, UtilMethods.INSTANCE.getIMEI(this),
                        "", BuildConfig.VERSION_NAME,  UtilMethods.INSTANCE.getSerialNo(this)));
            } else {
                call = git.DTHHeavyRefresh(new HeavyrefreshRequest(operatorSelectedId + "", etNumber.getText().toString(),
                        ApplicationConstant.INSTANCE.APP_ID, UtilMethods.INSTANCE.getIMEI(this),
                        "", BuildConfig.VERSION_NAME,  UtilMethods.INSTANCE.getSerialNo(this)));
            }

            call.enqueue(new Callback<DthPlanInfoResponse>() {
                @Override
                public void onResponse(Call<DthPlanInfoResponse> call, Response<DthPlanInfoResponse> response) {
                    try {
                        if (loader != null && loader.isShowing()) {
                            loader.dismiss();
                        }
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                if (response.body().getStatuscode() == 1) {

                                    if (response.body().getDataRP() != null && response.body().getDataRP().getRecords() != null) {
                                        if (response.body().getDataRP().getRecords().getDesc() != null) {
                                            UtilMethods.INSTANCE.Successful(SecondRechargeActivity.this, "" + response.body().getDataRP().getRecords().getDesc() + "");
                                        } else {
                                            UtilMethods.INSTANCE.Error(SecondRechargeActivity.this, "No Record Found");
                                        }
                                    } else if (response.body().getDthhrData() != null) {
                                        if (response.body().getDthhrData().getStatusCode() == 1) {
                                            if (response.body().getDthhrData().getResponse() != null) {
                                                UtilMethods.INSTANCE.Successful(SecondRechargeActivity.this, response.body().getDthhrData().getResponse() + "");
                                            } else {
                                                UtilMethods.INSTANCE.Successful(SecondRechargeActivity.this, "Dear Customer , HR request is captured , Please Ensure your STB is Switched on");
                                            }
                                        } else {
                                            UtilMethods.INSTANCE.Error(SecondRechargeActivity.this, "No Record Found");
                                        }
                                    }else {
                                        UtilMethods.INSTANCE.Error(SecondRechargeActivity.this, "No Record Found");
                                    }
                                } else {
                                    UtilMethods.INSTANCE.Error(SecondRechargeActivity.this, "No Record Found");
                                }
                            }
                        } else {
                            UtilMethods.INSTANCE.apiErrorHandle(SecondRechargeActivity.this, response.code(), response.message());
                        }
                    } catch (Exception e) {
                        if (loader != null && loader.isShowing()) {
                            loader.dismiss();
                        }
                    }
                }

                @Override
                public void onFailure(Call<DthPlanInfoResponse> call, Throwable t) {
                    if (loader != null && loader.isShowing()) {
                        loader.dismiss();
                    }
                    try {

                        UtilMethods.INSTANCE.apiFailureError(SecondRechargeActivity.this, t);


                    } catch (IllegalStateException ise) {
                        UtilMethods.INSTANCE.Error(SecondRechargeActivity.this, ise.getMessage());

                    }
                }
            });
        } catch (Exception e) {
            if (loader != null && loader.isShowing()) {
                loader.dismiss();
            }
            UtilMethods.INSTANCE.Error(SecondRechargeActivity.this, e.getMessage() + "");
        }
    }

    public void DTHinfo() {
        try {
            loader.show();
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            ROfferRequest request = new ROfferRequest(operatorSelectedId + "", etNumber.getText().toString().trim(),
                    ApplicationConstant.INSTANCE.APP_ID, UtilMethods.INSTANCE.getIMEI(this),
                    "", BuildConfig.VERSION_NAME, UtilMethods.INSTANCE.getSerialNo(this),mLoginDataResponse.getData().getUserID()
                    ,mLoginDataResponse.getData().getLoginTypeID(),
                    mLoginDataResponse.getData().getSessionID(),
                    mLoginDataResponse.getData().getSession());
            Call<DTHInfoResponse> call;
            if (mLoginDataResponse.isPlanServiceUpdated()) {
                call = git.GetRNPDTHCustInfo(request);
            } else {
                call = git.DTHCustomerInfo(request);
            }
            call.enqueue(new Callback<DTHInfoResponse>() {
                @Override
                public void onResponse(Call<DTHInfoResponse> call, final Response<DTHInfoResponse> response) {

                    try {
                        if (loader != null && loader.isShowing()) {
                            loader.dismiss();
                        }
                        if (response.isSuccessful()) {
                            if (response.body() != null) {

                                if (response.body().getStatuscode() == 1) {
                                    if (response.body() != null) {
                                        if (response.body().getData() != null && response.body().getData().getRecords() != null && response.body().getData().getRecords().size() > 0) {
                                            parseDthInfoData(response.body().getData());

                                        } else if (response.body().getDataPA() != null && response.body().getDataPA().getStatus() == 0 && response.body().getDataPA().getData() != null) {
                                            parseDthInfoData(response.body().getDataPA());
                                        } else if (response.body().getDthciData() != null && response.body().getDthciData().getStatusCode() == 1) {
                                            parseDthInfoData(response.body().getDthciData());
                                        } else {
                                            rlDthInfoDetail.setVisibility(View.GONE);
                                        }
                                    } else {
                                        rlDthInfoDetail.setVisibility(View.GONE);
                                        UtilMethods.INSTANCE.Error(SecondRechargeActivity.this, "DTH Info not found, Please try after some time.");
                                    }

                                } else {
                                    rlDthInfoDetail.setVisibility(View.GONE);
                                    UtilMethods.INSTANCE.Error(SecondRechargeActivity.this, response.body().getMsg() + "");
                                }

                            } else {
                                rlDthInfoDetail.setVisibility(View.GONE);
                                UtilMethods.INSTANCE.Error(SecondRechargeActivity.this, "Something went wrong, try after some time.");
                            }
                        } else {
                            rlDthInfoDetail.setVisibility(View.GONE);
                            UtilMethods.INSTANCE.apiErrorHandle(SecondRechargeActivity.this, response.code(), response.message());
                        }
                    } catch (Exception e) {
                        if (loader != null && loader.isShowing()) {
                            loader.dismiss();
                        }
                        rlDthInfoDetail.setVisibility(View.GONE);
                    }

                }

                @Override
                public void onFailure(Call<DTHInfoResponse> call, Throwable t) {
                    if (loader != null && loader.isShowing()) {
                        loader.dismiss();
                    }
                    rlDthInfoDetail.setVisibility(View.GONE);
                    try {

                        UtilMethods.INSTANCE.apiFailureError(SecondRechargeActivity.this, t);


                    } catch (IllegalStateException ise) {
                        UtilMethods.INSTANCE.Error(SecondRechargeActivity.this, ise.getMessage());

                    }

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            if (loader != null && loader.isShowing()) {
                loader.dismiss();
            }
            rlDthInfoDetail.setVisibility(View.GONE);
        }
    }

    private void parseDthInfoData(DTHInfoRecords responsePlan) {

        if (responsePlan != null) {
            mDthInfoRecords = responsePlan;
        }

        if (mDthInfoRecords != null) {
            rlDthInfoDetail.setVisibility(View.VISIBLE);

            Glide.with(this)
                    .load(ApplicationConstant.INSTANCE.baseIconUrl + Icon)
                    .apply(requestOptions)
                    .into(operatorIcon);

            if (responsePlan.getAccountNo() != null && !responsePlan.getAccountNo().isEmpty()) {
                tel.setText(/*AccountName.trim() + " : " +*/ (responsePlan.getAccountNo() + "").trim());
            } else {
                tel.setText(/*AccountName.trim() + " : " +*/ etNumber.getText().toString() + "");
            }

            operator.setText(operatorSelected.trim() + "");

            if (mDthInfoRecords.getData() != null && mDthInfoRecords.getData().size() > 0) {

                ArrayList<DTHInfoRecords> mList = new ArrayList<>();
                for (DTHInfoRecords mItem : mDthInfoRecords.getData()) {
                    if (mItem.getKey() != null && !mItem.getKey().isEmpty() && mItem.getValue() != null && !mItem.getValue().isEmpty()) {
                        mList.add(mItem);
                    }
                }
                if (mList != null && mList.size() > 0) {
                    custInfoRecyclerView.setVisibility(View.VISIBLE);
                    custInfoRecyclerView.setAdapter(new DthCustInfoAdapter(mList, this));
                } else {
                    custInfoRecyclerView.setVisibility(View.GONE);
                }
            } else {
                custInfoRecyclerView.setVisibility(View.GONE);
            }
            if (mDthInfoRecords.getCustomerName() != null && !mDthInfoRecords.getCustomerName().isEmpty()) {
                llCustomerLayout.setVisibility(View.VISIBLE);
                customerName.setText((mDthInfoRecords.getCustomerName() + "").trim());
            } else {
                llCustomerLayout.setVisibility(View.GONE);
            }

            if (mDthInfoRecords.getBalance() != null && !mDthInfoRecords.getBalance().isEmpty()) {
                llBalAmount.setVisibility(View.VISIBLE);
                Balance.setText(getResources().getString(R.string.rupiya) + " " + (mDthInfoRecords.getBalance() + "").trim());
            } else {
                llBalAmount.setVisibility(View.GONE);
            }

            if (mDthInfoRecords.getPlanname() != null && !mDthInfoRecords.getPlanname().isEmpty()) {
                llPlanName.setVisibility(View.VISIBLE);
                planname.setText((mDthInfoRecords.getPlanname() + "").trim());
            } else {
                llPlanName.setVisibility(View.GONE);
            }

            if (mDthInfoRecords.getNextRechargeDate() != null && !mDthInfoRecords.getNextRechargeDate().isEmpty()) {
                llRechargeDate.setVisibility(View.VISIBLE);
                NextRechargeDate.setText((mDthInfoRecords.getNextRechargeDate() + "").trim());
            } else {
                llRechargeDate.setVisibility(View.GONE);
            }


            if (mDthInfoRecords.getMonthlyRecharge() != null && !mDthInfoRecords.getMonthlyRecharge().isEmpty()) {
                llPackageAmt.setVisibility(View.VISIBLE);
                RechargeAmount.setText(getResources().getString(R.string.rupiya) + " " + (mDthInfoRecords.getMonthlyRecharge() + "").trim());
            } else {
                llPackageAmt.setVisibility(View.GONE);
            }


            RechargeAmount.setOnClickListener(view -> etAmount.setText(mDthInfoRecords.getMonthlyRecharge() + ""));

        }
    }


    private void parseDthInfoData(DTHInfoData responsePlan) {

        if (responsePlan.getRecords() != null && responsePlan.getRecords().size() > 0) {
            mDthInfoRecords = responsePlan.getRecords().get(0);
        } else if (responsePlan.getData() != null) {
            mDthInfoRecords = responsePlan.getData();
        }

        if (mDthInfoRecords != null) {
            rlDthInfoDetail.setVisibility(View.VISIBLE);

            Glide.with(this)
                    .load(ApplicationConstant.INSTANCE.baseIconUrl + Icon)
                    .apply(requestOptions)
                    .into(operatorIcon);

            if (responsePlan.getTel() != null && !responsePlan.getTel().isEmpty()) {
                tel.setText(/*AccountName.trim() + " : " +*/ (responsePlan.getTel() + "").trim());
            } else {
                tel.setText(/*AccountName.trim() + " : " +*/ etNumber.getText().toString() + "");
            }
            if (responsePlan.getOperator() != null && !responsePlan.getOperator().isEmpty()) {
                operator.setText((responsePlan.getOperator() + "").trim());
            } else {
                operator.setText(operatorSelected.trim() + "");
            }


            if (mDthInfoRecords.getCustomerName() != null && !mDthInfoRecords.getCustomerName().isEmpty()) {
                llCustomerLayout.setVisibility(View.VISIBLE);
                customerName.setText((mDthInfoRecords.getCustomerName() + "").trim());
            } else {
                llCustomerLayout.setVisibility(View.GONE);
            }

            if (mDthInfoRecords.getBalance() != null && !mDthInfoRecords.getBalance().isEmpty()) {
                llBalAmount.setVisibility(View.VISIBLE);
                Balance.setText(getResources().getString(R.string.rupiya) + " " + (mDthInfoRecords.getBalance() + "").trim());
            } else {
                llBalAmount.setVisibility(View.GONE);
            }

            if (mDthInfoRecords.getPlanname() != null && !mDthInfoRecords.getPlanname().isEmpty()) {
                llPlanName.setVisibility(View.VISIBLE);
                planname.setText((mDthInfoRecords.getPlanname() + "").trim());
            } else {
                llPlanName.setVisibility(View.GONE);
            }

            if (mDthInfoRecords.getNextRechargeDate() != null && !mDthInfoRecords.getNextRechargeDate().isEmpty()) {
                llRechargeDate.setVisibility(View.VISIBLE);
                NextRechargeDate.setText((mDthInfoRecords.getNextRechargeDate() + "").trim());
            } else {
                llRechargeDate.setVisibility(View.GONE);
            }


            if (mDthInfoRecords.getMonthlyRecharge() != null && !mDthInfoRecords.getMonthlyRecharge().isEmpty()) {
                llPackageAmt.setVisibility(View.VISIBLE);
                RechargeAmount.setText(getResources().getString(R.string.rupiya) + " " + (mDthInfoRecords.getMonthlyRecharge() + "").trim());
            } else {
                llPackageAmt.setVisibility(View.GONE);
            }


            RechargeAmount.setOnClickListener(view -> etAmount.setText(mDthInfoRecords.getMonthlyRecharge() + ""));

        }
    }



    public void HitIncentiveApi() {
        if (UtilMethods.INSTANCE.isNetworkAvialable(this)) {
            loader.show();
            loader.setCancelable(false);
            loader.setCanceledOnTouchOutside(false);
            UtilMethods.INSTANCE.IncentiveDetail(this, operatorSelectedId + "", loader, object -> {
                AppUserListResponse mAppUserListResponse = (AppUserListResponse) object;
                incentiveOperatorSelectedId = operatorSelectedId;

                incentiveList = mAppUserListResponse.getIncentiveDetails();
                showPopupIncentive();
            });
        } else {
            UtilMethods.INSTANCE.NetworkError(this);
        }
    }


    private void showPopupIncentive() {

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE); // or (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewMyLayout = inflater.inflate(R.layout.dialog_incentive, null);
        RecyclerView recyclerView = viewMyLayout.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        View closeBtn = viewMyLayout.findViewById(R.id.closeBtn);

        IncentiveAdapter incentiveAdapter = new IncentiveAdapter(incentiveList, SecondRechargeActivity.this);
        recyclerView.setAdapter(incentiveAdapter);
        incentiveDialog = new Dialog(this, R.style.Theme_AppCompat_Dialog_Alert);
        incentiveDialog.setCancelable(false);
        incentiveDialog.setContentView(viewMyLayout);
        /* dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));*/
        incentiveDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incentiveDialog.dismiss();
            }
        });

        incentiveDialog.show();
        // Window window = dialog.getWindow();
        //window.setLayout(Toolbar.LayoutParams.MATCH_PARENT, Toolbar.LayoutParams.MATCH_PARENT);


    }

    public void setCashBackAmount(IncentiveDetails item) {
        if (incentiveDialog != null && incentiveDialog.isShowing()) {
            incentiveDialog.dismiss();
        }
        etAmount.setText(item.getDenomination() + "");
        desc.setVisibility(View.VISIBLE);
        desc.setText("You are eligible for " + item.getComm() + (item.isAmtType() ? " \u20B9 Cash Back" : " % Cash Back"));
    }

    public void SetNumber(final String Number, EditText etView) {
        String Number1 = Number.replace("+91", "");
        String Number2 = Number1.replace(" ", "");
        String Number3 = Number2.replace("(", "");
        String Number4 = Number3.replace(")", "");
        String Number5 = Number4.replace("_", "");
        String Number6 = Number5.replace("-", "");
        etView.setText(Number6);

    }


    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            isTTSInit = true;
            if (msgSpeak != null && !msgSpeak.isEmpty()) {
                playVoice();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Init failed", Toast.LENGTH_SHORT).show();
        }
    }

    private void playVoice() {
       /* if (tts != null && isTTSInit) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                tts.speak(msgSpeak, TextToSpeech.QUEUE_FLUSH, null, null);
            } else {
                tts.speak(msgSpeak, TextToSpeech.QUEUE_FLUSH, null);
            }
            msgSpeak = "";
        }*/
    }

    @Override
    public void onDestroy() {

        GlobalBus.getBus().unregister(this);
        /*if (tts != null) {
            tts.stop();
            tts.shutdown();
        }*/
        super.onDestroy();

    }

    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            GlobalBus.getBus().register(this);
        }
    }



    @Subscribe
    public void onActivityActivityMessage(ActivityActivityMessage activityFragmentMessage) {
        if (activityFragmentMessage.getMessage().equalsIgnoreCase("refreshvalue_Sec")) {
            onBackPressed();
        }
        if (activityFragmentMessage.getFrom().equalsIgnoreCase("refreshvalue")) {
            etNumber.setText("");
            etAmount.setText("");
            etCustNumber.setText("");
            etOption1.setText("");
            etOption2.setText("");
            etOption3.setText("");
            etOption4.setText("");
            numberError.setVisibility(View.GONE);
            AmountError.setVisibility(View.GONE);
            option1Error.setVisibility(View.GONE);
            option2Error.setVisibility(View.GONE);
            option3Error.setVisibility(View.GONE);
            option4Error.setVisibility(View.GONE);
            MobileNoError.setVisibility(View.GONE);
            btRecharge.setEnabled(true);
            tvName.setVisibility(View.GONE);
            if (UtilMethods.INSTANCE.isNetworkAvialable(SecondRechargeActivity.this)) {
                UtilMethods.INSTANCE.Balancecheck(SecondRechargeActivity.this, loader, null);
            }
        }
        if (activityFragmentMessage.getMessage().equalsIgnoreCase("viewbill")) {
            etAmount.setText(activityFragmentMessage.getFrom());
        }
    }
}

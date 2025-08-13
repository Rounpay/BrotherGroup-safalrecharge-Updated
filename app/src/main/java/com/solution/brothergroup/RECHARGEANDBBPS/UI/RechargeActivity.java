package com.solution.brothergroup.RECHARGEANDBBPS.UI;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.solution.brothergroup.Activities.SelectZoneOption;
import com.solution.brothergroup.Adapter.IncentiveAdapter;
import com.solution.brothergroup.Adapter.RechargeReportAdapter;
import com.solution.brothergroup.Api.Object.CommissionDisplay;
import com.solution.brothergroup.Api.Object.IncentiveDetails;
import com.solution.brothergroup.Api.Object.OperatorList;
import com.solution.brothergroup.Api.Object.RealLapuCommissionSlab;
import com.solution.brothergroup.Api.Object.RechargeStatus;
import com.solution.brothergroup.Api.Request.GetHLRLookUpRequest;
import com.solution.brothergroup.Api.Response.AppUserListResponse;
import com.solution.brothergroup.Api.Response.GetHLRLookUPResponse;
import com.solution.brothergroup.Api.Response.NumberListResponse;
import com.solution.brothergroup.Api.Response.RechargeReportResponse;
import com.solution.brothergroup.Authentication.dto.LoginResponse;
import com.solution.brothergroup.BuildConfig;
import com.solution.brothergroup.R;
import com.solution.brothergroup.Util.ActivityActivityMessage;
import com.solution.brothergroup.Util.ApiClient;
import com.solution.brothergroup.Util.ApplicationConstant;
import com.solution.brothergroup.Util.ChangePassUtils;
import com.solution.brothergroup.Util.CustomAlertDialog;
import com.solution.brothergroup.Util.EndPointInterface;
import com.solution.brothergroup.Util.GetLocation;
import com.solution.brothergroup.Util.GlobalBus;
import com.solution.brothergroup.Util.UtilMethods;
import com.solution.brothergroup.usefull.CustomLoader;
import com.solution.brothergroup.usefull.Preferences;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;

public class RechargeActivity extends AppCompatActivity implements View.OnClickListener {


    static final int PICK_CONTACT = 1;
    public int operatorSelectedId, incentiveOperatorSelectedId;
    TextView MobileNoCount;
    View ll_browse_plan, rechargeView, ll_comingsoon;
    TextView rechargeViewTab, historyViewTab;
    View rl_oprator;
    // RadioButton radio_prepaid, radio_postpaid;
    TextView tvName, tv_operator;
    ImageView iv_oprator;
    TextView tvBrowsePlan, tvPdfPlan;
    TextView tv_roffer_plan;
    TextView noticePlan;
    RecyclerView recyclerView;
    TextView recentRechargeTv, viewMore;
    ImageView ivPhoneBook;

    String OpRefOp = "";
    String OpRefCircleID = "";
    String Number = "";
    String operatorSelected = "";
    CustomLoader loader;
    Preferences pref;
    TextView AmountError, MobileNoError, OperatorError;
    View view, historyView;
    int minAmountLength, maxAmountLength;
    String AccountName = "Mobile Number", AccountRemark = "", Icon = "";
    boolean isAcountNumeric;
    int minNumberLength = 0, maxNumberLength = 0;
    String Startwith;
    TextView desc;

    String opid = "";
    String OpCircleCode = "";
    ArrayList<String> StartWithArray = new ArrayList<>();
    private EditText etNumber;
    private EditText etAmount;
    private Button btRecharge;
    private String fromDateStr, toDateStr;
    private LoginResponse mLoginDataResponse;
    private int INTENT_SELECT_OPERATOR = 7291;
    View viewOffer;
    // RadioGroup mRadioGroup;
    private String from;
    private int fromId;
    SwitchCompat lapuSwitch, realApiSwitch;
    // TextView lapuTv, realApiTv;
    View switchView;
    TextView cashBackTv;
    private Dialog incentiveDialog;
    ArrayList<IncentiveDetails> incentiveList = new ArrayList<>();
    RequestOptions requestOptions;
    private GetLocation mGetLocation;
    HashMap<String, IncentiveDetails> incentiveMap = new HashMap<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowInsetsControllerCompat controller = new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        controller.setAppearanceLightStatusBars(true);
        controller.setAppearanceLightNavigationBars(true);
        setContentView(R.layout.activity_recharge);
        String LoginResponse = UtilMethods.INSTANCE.getLoginPref(this);
        mLoginDataResponse = new Gson().fromJson(LoginResponse, LoginResponse.class);
        requestOptions = new RequestOptions();
        requestOptions.circleCropTransform();
        requestOptions.placeholder(R.drawable.circle_logo);
        requestOptions.error(R.drawable.circle_logo);
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
        from = getIntent().getExtras().getString("from");
        fromId = getIntent().getExtras().getInt("fromId", 0);
        getIds();
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);


        final Calendar myCalendar = Calendar.getInstance();
        String myFormat = "dd-MMM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        fromDateStr = sdf.format(myCalendar.getTime());
        toDateStr = sdf.format(myCalendar.getTime());
        HitApi();
        mGetLocation = new GetLocation(RechargeActivity.this, loader);
        if (UtilMethods.INSTANCE.getLattitude == 0 || UtilMethods.INSTANCE.getLongitude == 0) {
            mGetLocation.startLocationUpdatesIfSettingEnable((lattitude, longitude) -> {
                UtilMethods.INSTANCE.getLattitude = lattitude;
                UtilMethods.INSTANCE.getLongitude = longitude;
            });
        }
    }

    private void getIds() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setTitle(from);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_icon);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        pref = new Preferences(this);
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        tvName = findViewById(R.id.tv_name);
        switchView = findViewById(R.id.switchView);
        lapuSwitch = findViewById(R.id.lapuSwitch);
        realApiSwitch = findViewById(R.id.realApiSwitch);
        cashBackTv = findViewById(R.id.cashBackTv);
        //lapuTv = findViewById(R.id.lapuTv);
        // realApiTv = findViewById(R.id.realApiTv);
        viewOffer = findViewById(R.id.viewOffer);
        MobileNoCount = findViewById(R.id.MobileNoCount);
        ll_comingsoon = findViewById(R.id.ll_coming_soon);
        rechargeView = findViewById(R.id.rechargeView);
        historyView = findViewById(R.id.historyView);
        rechargeViewTab = findViewById(R.id.rechargeViewTab);
        historyViewTab = findViewById(R.id.historyViewTab);
        tvPdfPlan = findViewById(R.id.tv_pdf_plan);

        if (UtilMethods.INSTANCE.getIsRealAPIPerTransaction(this)) {
            switchView.setVisibility(View.VISIBLE);
            lapuSwitch.setChecked(true);
        } else {
            switchView.setVisibility(View.GONE);
        }
        if (from.equalsIgnoreCase("Prepaid")) {
            rechargeViewTab.setText("Recharge");
            cashBackTv.setVisibility(View.VISIBLE);
            if (UtilMethods.INSTANCE.getIsShowPDFPlan(RechargeActivity.this)) {
                tvPdfPlan.setVisibility(View.VISIBLE);
            } else {
                tvPdfPlan.setVisibility(View.GONE);
            }
        } else if (from.equalsIgnoreCase("FRC Prepaid")) {
            rechargeViewTab.setText("Recharge");
            tvPdfPlan.setVisibility(View.GONE);
            cashBackTv.setVisibility(View.VISIBLE);
        } else {
            rechargeViewTab.setText("Bill Payment");
            tvPdfPlan.setVisibility(View.GONE);
            cashBackTv.setVisibility(View.GONE);
        }
        desc = findViewById(R.id.desc);
        tv_operator = findViewById(R.id.tv_operator);
        iv_oprator = findViewById(R.id.iv_oprator);
        // mRadioGroup = findViewById(R.id.radioGroup);
        ll_browse_plan = findViewById(R.id.ll_browse_plan);

        rl_oprator = findViewById(R.id.rl_oprator);
        //radio_prepaid = findViewById(R.id.radio_prepaid);
        //  radio_postpaid = findViewById(R.id.radio_postpaid);
        noticePlan = findViewById(R.id.noticePlan);
        noticePlan.setVisibility(View.GONE);
        tvBrowsePlan = findViewById(R.id.tv_browse_plan);
        tvBrowsePlan.setVisibility(View.VISIBLE);
        tv_roffer_plan = findViewById(R.id.tv_roffer_plan);
        if (UtilMethods.INSTANCE.getIsRoffer(this)) {
            tv_roffer_plan.setVisibility(View.VISIBLE);
        }
        AmountError = findViewById(R.id.Amounterror);

        MobileNoError = findViewById(R.id.MobileNoError);
        OperatorError = findViewById(R.id.OperatorError);
        ivPhoneBook = findViewById(R.id.iv_phone_book);

        recentRechargeTv = findViewById(R.id.recentRechargeTv);
        viewMore = findViewById(R.id.viewMore);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        btRecharge = findViewById(R.id.bt_recharge);

        etNumber = findViewById(R.id.et_number);
        etAmount = findViewById(R.id.et_amount);

        etNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s.length() > 0) {
                    MobileNoCount.setVisibility(View.VISIBLE);
                    MobileNoCount.setText("Number Digit- " + s.length());
                } else {
                    MobileNoCount.setVisibility(View.GONE);
                }
                if (s.length() < 4) {
                    iv_oprator.setImageResource(R.drawable.ic_tower);
                    tv_operator.setText("");
                    operatorSelected = "";
                    operatorSelectedId = 0;
                    lapuSwitch.setText("Lapu");
                    realApiSwitch.setText("Recharge With Real");
                    opid = "";
                    OpCircleCode = "";
                    OpRefCircleID = "";
                }

                if (from.equalsIgnoreCase("Prepaid")) {
                    if (UtilMethods.INSTANCE.getIsLookUpFromAPI(RechargeActivity.this)) {
                        if (s.length() == 10) {
                            getHLRLookUp();
                        }
                    } else {
                        if (s.length() == 4) {
                            SelectOperator(s.toString());
                        }
                    }
                }
                if (s.length() == 10) {
                    etAmount.requestFocus();
                }
                if (s.length() != 10) {
                    tvName.setText("");
                    tvName.setVisibility(View.GONE);
                }
            }
        });
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


        if (from.equalsIgnoreCase("Prepaid")) {
            ll_browse_plan.setVisibility(View.VISIBLE);
            btRecharge.setText("Recharge");
            recentRechargeTv.setText("Recent Recharges");
        } else if (from.equalsIgnoreCase("FRC Prepaid")) {
            ll_browse_plan.setVisibility(View.GONE);
            btRecharge.setText("Recharge");
            recentRechargeTv.setText("Recent Recharges");
        } else {
            ll_browse_plan.setVisibility(View.GONE);
            btRecharge.setText("Bill Payment");
            recentRechargeTv.setText("Recent Bill Payments");
        }
        /*mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radio_prepaid) {
                    ll_browse_plan.setVisibility(View.VISIBLE);
                    btRecharge.setText("Recharge");
                    recentRechargeTv.setText("Recent Recharges");
                } else {
                    ll_browse_plan.setVisibility(View.GONE);
                    btRecharge.setText("Bill Payment");
                    recentRechargeTv.setText("Recent Bill Payments");
                }
            }
        });*/


        lapuSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    lapuSwitch.setTextColor(getResources().getColor(R.color.colorAccent));
                    realApiSwitch.setTextColor(getResources().getColor(R.color.grey));
                    realApiSwitch.setChecked(false);
                    if (from.equalsIgnoreCase("Prepaid") || from.equalsIgnoreCase("FRC Prepaid")) {
                        cashBackTv.setVisibility(View.VISIBLE);
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
            }
        });
        realApiSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    lapuSwitch.setTextColor(getResources().getColor(R.color.grey));
                    realApiSwitch.setTextColor(getResources().getColor(R.color.colorAccent));
                    lapuSwitch.setChecked(false);
                } else {
                    lapuSwitch.setChecked(true);
                }
            }
        });

        setListners();


    }

    private void setListners() {
        rl_oprator.setOnClickListener(this);
        tvBrowsePlan.setOnClickListener(this);
        tvPdfPlan.setOnClickListener(this);
        ivPhoneBook.setOnClickListener(this);
        btRecharge.setOnClickListener(this);
        viewOffer.setOnClickListener(this);
        tv_roffer_plan.setOnClickListener(this);
        viewMore.setOnClickListener(this);
        rechargeViewTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                historyView.setVisibility(View.GONE);
                rechargeView.setVisibility(View.VISIBLE);
                btRecharge.setVisibility(View.VISIBLE);
                rechargeViewTab.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                historyViewTab.setBackgroundColor(getResources().getColor(R.color.light_grey));
            }
        });

        historyViewTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                historyView.setVisibility(View.VISIBLE);
                rechargeView.setVisibility(View.GONE);
                btRecharge.setVisibility(View.GONE);
                rechargeViewTab.setBackgroundColor(getResources().getColor(R.color.light_grey));
                historyViewTab.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                HitApi();
            }
        });


        cashBackTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (operatorSelectedId != 0) {
                    if (incentiveList != null && incentiveList.size() > 0 && incentiveOperatorSelectedId == operatorSelectedId) {
                        showPopupIncentive();
                    } else {
                        HitIncentiveApi();
                    }
                } else {
                    UtilMethods.INSTANCE.Error(RechargeActivity.this, "Please Select Operator.");
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
        } /*else if (v == tvCircle) {
            if ((!OpRefOp.equals("") || operatorSelectedId != 0)) {
                Intent planOptionIntent = new Intent(this, SelectZoneOption.class);
                planOptionIntent.putExtra("from", "zoneOp");
                planOptionIntent.putExtra("opList", 1);
                planOptionIntent.putExtra("opListName", operatorSelected);
                planOptionIntent.putExtra("operatorId", OpRefOp);
                startActivityForResult(planOptionIntent, 4);
            } else {
                UtilMethods.INSTANCE.Error(this, "Please Select the Operator.");
            }

        }*/ else if (v == rl_oprator) {
            Intent i = new Intent(this, RechargeProviderActivity.class);
            i.putExtra("from", from);
            i.putExtra("fromId", fromId);
            i.putExtra("fromPhoneRecharge", true);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivityForResult(i, INTENT_SELECT_OPERATOR);
        } else if (v == ivPhoneBook) {
            Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            ;
            startActivityForResult(intent, PICK_CONTACT);
        } else if (v == tv_roffer_plan) {
            if (operatorSelectedId != 0 && !etNumber.getText().toString().trim().equals("")) {

                if (operatorSelected.toLowerCase().contains("jio")) {
                    tvBrowsePlan.performClick();
                } else {
                    if (UtilMethods.INSTANCE.isNetworkAvialable(this)) {
                        loader.show();
                        loader.setCancelable(false);
                        loader.setCanceledOnTouchOutside(false);
                        UtilMethods.INSTANCE.ROffer(this,mLoginDataResponse, mLoginDataResponse.isPlanServiceUpdated(), operatorSelectedId + "", etNumber.getText().toString().trim(), loader);
                    } else {
                        UtilMethods.INSTANCE.NetworkError(this, getResources().getString(R.string.err_msg_network_title),
                                getResources().getString(R.string.err_msg_network));
                    }
                }
            } else {
                UtilMethods.INSTANCE.Error(this, "Please enter Number and select Operator both");
            }
        } else if (v == tvPdfPlan) {
            if (operatorSelectedId != 0) {
                try {
                    Intent dialIntent = new Intent(Intent.ACTION_VIEW);
                    dialIntent.setData(Uri.parse(ApplicationConstant.INSTANCE.baseUrl + "Image/planDoc/" + operatorSelectedId + ".pdf"));
                    startActivity(dialIntent);
                } catch (Exception e) {
                    Intent dialIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(ApplicationConstant.INSTANCE.baseUrl + "Image/planDoc/" + operatorSelectedId + ".pdf"));
                    startActivity(dialIntent);
                }
            } else {
                UtilMethods.INSTANCE.Error(this, "Please Select the Operator.");
            }
        } else if (v == tvBrowsePlan) {
            if (operatorSelectedId != 0 && OpRefCircleID != null && !OpRefCircleID.isEmpty()) {
                if (UtilMethods.INSTANCE.isNetworkAvialable(this)) {
                    loader.show();
                    loader.setCancelable(false);
                    loader.setCanceledOnTouchOutside(false);
                    UtilMethods.INSTANCE.ViewPlan(this, mLoginDataResponse,mLoginDataResponse.isPlanServiceUpdated(), operatorSelectedId + "", OpRefCircleID, loader);
                } else {
                    UtilMethods.INSTANCE.NetworkError(this, getResources().getString(R.string.err_msg_network_title),
                            getResources().getString(R.string.err_msg_network));
                }
            } else {
                if ((!OpRefOp.equals("") || operatorSelectedId != 0)) {
                    Intent planOptionIntent = new Intent(this, SelectZoneOption.class);
                    planOptionIntent.putExtra("from", "zoneOp");
                    planOptionIntent.putExtra("opList", 1);
                    planOptionIntent.putExtra("opListName", operatorSelected);
                    planOptionIntent.putExtra("operatorId", OpRefOp);
                    planOptionIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivityForResult(planOptionIntent, 3);
                } else {
                    UtilMethods.INSTANCE.Error(this, "Please Select the Operator.");
                }
            }


        } else if (v == btRecharge) {
            AmountError.setVisibility(View.GONE);
            MobileNoError.setVisibility(View.GONE);
            OperatorError.setVisibility(View.GONE);
            if (!validateMobile()) {
                return;
            } else if (tv_operator.getText().toString().isEmpty()) {
                OperatorError.setVisibility(View.VISIBLE);
                return;
            } else if (!validateAmount()) {
                return;
            }


            if (UtilMethods.INSTANCE.isVpnConnected(this)) {
                return;
            }


            if (realApiSwitch.isChecked() && switchView.getVisibility() == View.VISIBLE) {
                UtilMethods.INSTANCE.GetCalculateLapuRealCommission(RechargeActivity.this, operatorSelectedId + "", etAmount.getText().toString().trim(), loader, new UtilMethods.ApiCallBackTwoMethod() {
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


        } else if (v == viewOffer) {

        }


    }

    void RechargeDialog(CommissionDisplay mCommissionDisplay) {
        UtilMethods.INSTANCE.rechargeConfiormDialog(this, incentiveMap, mCommissionDisplay, realApiSwitch.isChecked() ? true : false, UtilMethods.INSTANCE.getDoubleFactorPref(this), ApplicationConstant.INSTANCE.baseIconUrl + Icon, etNumber.getText().toString(), operatorSelected, etAmount.getText().toString(), new UtilMethods.DialogCallBack() {
            @Override
            public void onPositiveClick(String pinPass) {
                btRecharge.setEnabled(false);
                if (UtilMethods.INSTANCE.isNetworkAvialable(RechargeActivity.this)) {
                    loader.show();
                    loader.setCancelable(false);
                    loader.setCanceledOnTouchOutside(false);
                    if (UtilMethods.INSTANCE.getLattitude != 0 && UtilMethods.INSTANCE.getLongitude != 0) {

                        UtilMethods.INSTANCE.Recharge(RechargeActivity.this, realApiSwitch.isChecked() ? true : false, operatorSelectedId, operatorSelected,etNumber.getText().toString().trim(),
                                etAmount.getText().toString().trim(), "", "", "", "", "", "",
                                0, UtilMethods.INSTANCE.getLattitude + "," + UtilMethods.INSTANCE.getLongitude
                                , pinPass, loader);
                    } else {
                        if (mGetLocation != null) {
                            mGetLocation.startLocationUpdates((lattitude, longitude) -> {
                                UtilMethods.INSTANCE.getLattitude = lattitude;
                                UtilMethods.INSTANCE.getLongitude = longitude;
                                UtilMethods.INSTANCE.Recharge(RechargeActivity.this, realApiSwitch.isChecked() ? true : false, operatorSelectedId,operatorSelected, etNumber.getText().toString().trim(),
                                        etAmount.getText().toString().trim(), "", "", "", "", "", "",
                                        0, lattitude + "," + longitude
                                        , pinPass, loader);
                            });
                        } else {
                            mGetLocation = new GetLocation(RechargeActivity.this, loader);
                            mGetLocation.startLocationUpdates((lattitude, longitude) -> {
                                UtilMethods.INSTANCE.getLattitude = lattitude;
                                UtilMethods.INSTANCE.getLongitude = longitude;
                                UtilMethods.INSTANCE.Recharge(RechargeActivity.this, realApiSwitch.isChecked() ? true : false, operatorSelectedId,operatorSelected, etNumber.getText().toString().trim(),
                                        etAmount.getText().toString().trim(), "", "", "", "", "", "",
                                        0, lattitude + "," + longitude
                                        , pinPass, loader);
                            });
                        }
                    }


                } else {
                    UtilMethods.INSTANCE.NetworkError(RechargeActivity.this, getResources().getString(R.string.err_msg_network_title),
                            getResources().getString(R.string.err_msg_network));
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


    
   /* void RechargeDialog(CommissionDisplay mCommissionDisplay) {
        UtilMethods.INSTANCE.rechargeConfiormDialog(this, mCommissionDisplay, realApiSwitch.isChecked() ? true : false, UtilMethods.INSTANCE.getDoubleFactorPref(this), ApplicationConstant.INSTANCE.baseIconUrl + Icon, etNumber.getText().toString(), operatorSelected, etAmount.getText().toString(), new UtilMethods.DialogCallBack() {
            @Override
            public void onPositiveClick(String pinPass) {
                btRecharge.setEnabled(false);
                if (UtilMethods.INSTANCE.isNetworkAvialable(RechargeActivity.this)) {
                    loader.show();
                    loader.setCancelable(false);
                    loader.setCanceledOnTouchOutside(false);
                    if (UtilMethods.INSTANCE.getLattitude != 0 && UtilMethods.INSTANCE.getLongitude != 0) {

                        UtilMethods.INSTANCE.Recharge(RechargeActivity.this, realApiSwitch.isChecked() ? true : false, operatorSelectedId, etNumber.getText().toString().trim(),
                                etAmount.getText().toString().trim(), "", "", "", "", "", "",
                                0, UtilMethods.INSTANCE.getLattitude + "," + UtilMethods.INSTANCE.getLongitude
                                , pinPass, loader);
                    } else {
                        if (mGetLocation != null) {
                            mGetLocation.startLocationUpdates((lattitude, longitude) -> {
                                UtilMethods.INSTANCE.getLattitude = lattitude;
                                UtilMethods.INSTANCE.getLongitude = longitude;
                                UtilMethods.INSTANCE.Recharge(RechargeActivity.this, realApiSwitch.isChecked() ? true : false, operatorSelectedId, etNumber.getText().toString().trim(),
                                        etAmount.getText().toString().trim(), "", "", "", "", "", "",
                                        0, lattitude + "," + longitude
                                        , pinPass, loader);
                            });
                        } else {
                            mGetLocation = new GetLocation(RechargeActivity.this, loader);
                            mGetLocation.startLocationUpdates((lattitude, longitude) -> {
                                UtilMethods.INSTANCE.getLattitude = lattitude;
                                UtilMethods.INSTANCE.getLongitude = longitude;
                                UtilMethods.INSTANCE.Recharge(RechargeActivity.this, realApiSwitch.isChecked() ? true : false, operatorSelectedId, etNumber.getText().toString().trim(),
                                        etAmount.getText().toString().trim(), "", "", "", "", "", "",
                                        0, lattitude + "," + longitude
                                        , pinPass, loader);
                            });
                        }
                    }


                } else {
                    UtilMethods.INSTANCE.NetworkError(RechargeActivity.this, getResources().getString(R.string.err_msg_network_title),
                            getResources().getString(R.string.err_msg_network));
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

    //code for pic the contact from the phonebook...
    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

      /*  switch (reqCode) {
            case (PICK_CONTACT):
                if (resultCode == Activity.RESULT_OK) {
                    Uri contactData = data.getData();
                    Cursor c = this.managedQuery(contactData, null, null, null, null);
                    if (c.moveToFirst()) {
                        String id = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID));
                        String hasPhone = c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

                        if (hasPhone.equalsIgnoreCase("1")) {
                            Cursor phones = this.getContentResolver().query(
                                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id,
                                    null, null);
                            phones.moveToFirst();
                            // For Clear Old Value Of Number...
                            etNumber.setText("");
                            // <------------------------------------------------------------------>
                            if (phones != null && phones.moveToFirst()) {
                                Number = phones.getString(phones.getColumnIndex("data1"));

                                phones.close();
                            }
                        }
                        String Name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                        if (!Number.equals("")) {
                            if (!Name.equals("")) {
                                tvName.setText(Name);
                                tvName.setVisibility(View.VISIBLE);
                            } else {
                                tvName.setVisibility(View.VISIBLE);
                            }
                            //Set the Number Without space and +91..
                            SetNumber(Number);
                        } else {
                            Toast.makeText(this, "Please select a valid number", Toast.LENGTH_SHORT).show();
                        }

                    }
                }
                break;
        }*/

       /* if (resultCode == 1) {
            operatorSelected = data.getExtras().getString("selected");
            operatorSelectedId = data.getExtras().getInt("selectedId");
            Startwith = data.getExtras().getString("startwith");
            if (Startwith != null && Startwith.length() > 0 && Startwith.contains(",")) {
                StartWithArray = new ArrayList<>(Arrays.asList(Startwith.trim().split(",")));
            } else if (Startwith != null && !Startwith.isEmpty() && !Startwith.equalsIgnoreCase("0")) {
                StartWithArray.add(Startwith);
            }
            minAmountLength = data.getExtras().getString("Min");
            maxAmountLength = data.getExtras().getString("Max");
            minNumberLength = data.getExtras().getInt("length", 0);
            maxNumberLength = data.getExtras().getInt("maxLength", 0);
            isAcountNumeric = data.getExtras().getBoolean("IsAccountNumeric", false);
            AccountName = data.getExtras().getString("AccountName");
            AccountRemark = data.getExtras().getString("AccountRemark");
            Icon = data.getExtras().getString("Icon");
            OpRefOp = UtilMethods.INSTANCE.fetchShortCode(this, operatorSelected);
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

            if (operatorSelectedId != 0) {
                getLapuRealCommission();
            } else {
                lapuSwitch.setText("Lapu");
                realApiSwitch.setText("Recharge With Real");
            }

        } *//*else*/
        if (resultCode == RESULT_OK && reqCode == 45) {
            etAmount.setText(data.getStringExtra("amount"));
            desc.setVisibility(View.VISIBLE);
            desc.setText(data.getStringExtra("desc"));
        } else if (resultCode == 3) {
            String circle = data.getExtras().getString("selectedCircleName");
            OpRefCircleID = data.getExtras().getString("selectedCircleId");
            if (operatorSelectedId != 0 && OpRefCircleID != null && !OpRefCircleID.isEmpty()) {
                if (UtilMethods.INSTANCE.isNetworkAvialable(this)) {
                    loader.show();
                    loader.setCancelable(false);
                    loader.setCanceledOnTouchOutside(false);
                    UtilMethods.INSTANCE.ViewPlan(this,mLoginDataResponse, mLoginDataResponse.isPlanServiceUpdated(), operatorSelectedId + "", OpRefCircleID, loader);
                } else {
                    UtilMethods.INSTANCE.NetworkError(this, getResources().getString(R.string.err_msg_network_title),
                            getResources().getString(R.string.err_msg_network));
                }
            } else {
                UtilMethods.INSTANCE.Error(this, "Please select Operator and Circle both");
            }


        } else if (reqCode == INTENT_SELECT_OPERATOR && resultCode == RESULT_OK) {
            OperatorList operator = (OperatorList) data.getSerializableExtra("SelectedOperator");
            operatorSelected = operator.getName();
            operatorSelectedId = operator.getOid();
            minAmountLength = operator.getMin();
            maxAmountLength = operator.getMax();
            minNumberLength = operator.getLength();
            maxNumberLength = operator.getLengthMax();
            isAcountNumeric = operator.getIsAccountNumeric();
            AccountName = operator.getAccountName();
            AccountRemark = operator.getAccountRemak();
            Startwith = operator.getStartWith();
            Icon = operator.getImage();

            if (Startwith != null && Startwith.length() > 0 && Startwith.contains(",")) {
                StartWithArray = new ArrayList<>(Arrays.asList(Startwith.trim().split(",")));
            } else if (Startwith != null && !Startwith.isEmpty() && !Startwith.equalsIgnoreCase("0")) {
                StartWithArray.add(Startwith);
            }

            tv_operator.setText(operatorSelected + "");
            OperatorError.setVisibility(View.GONE);
            Glide.with(this)
                    .load(ApplicationConstant.INSTANCE.baseIconUrl + Icon)
                    .apply(requestOptions)
                    .into(iv_oprator);

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

            if (operatorSelectedId != 0) {
                getLapuRealCommission();
            } else {
                lapuSwitch.setText("Lapu");
                realApiSwitch.setText("Recharge With Real");
            }
        } else {
            if (mGetLocation != null) {
                mGetLocation.onActivityResult(reqCode, resultCode, data);
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

    public void HitApi() {
        if (UtilMethods.INSTANCE.isNetworkAvialable(this)) {
            UtilMethods.INSTANCE.RechargeReport(this, "0", "50", 0,
                    fromDateStr, toDateStr, "", "", "", "false",
                    true, loader, new UtilMethods.ApiCallBackTwoMethod() {
                        @Override
                        public void onSucess(Object object) {
                            RechargeReportResponse mRechargeReportResponse = (RechargeReportResponse) object;
                            dataParse(mRechargeReportResponse);
                        }

                        @Override
                        public void onError(Object object) {

                        }
                    });
           /* UtilMethods.INSTANCE.LastRechargeReport(this, "0", "0", fromDateStr, toDateStr, "", "", "false", ll_comingsoon, loader, new UtilMethods.ApiCallBack() {
                @Override
                public void onSucess(Object object) {
                    RechargeReportResponse mRechargeReportResponse = (RechargeReportResponse) object;
                    dataParse(mRechargeReportResponse);
                }
            });*/
        } else {
            UtilMethods.INSTANCE.NetworkError(this, getResources().getString(R.string.err_msg_network_title),
                    getResources().getString(R.string.err_msg_network));
        }
    }

    public void dataParse(RechargeReportResponse response) {

        ArrayList<RechargeStatus> transactionsObjects = new ArrayList<>();
        transactionsObjects = response.getRechargeReport();
        if (transactionsObjects.size() > 0) {
            RechargeReportAdapter mAdapter = new RechargeReportAdapter(transactionsObjects, this, false, mLoginDataResponse.getData().getRoleID());

            recyclerView.setAdapter(mAdapter);
            ll_comingsoon.setVisibility(View.GONE);
        } else {
            ll_comingsoon.setVisibility(View.VISIBLE);
        }
    }

    // For Setting the Number in the Phonenumber...
    public void SetNumber(final String Number) {
        String Number1 = Number.replace("+91", "");
        String Number2 = Number1.replace(" ", "");
        String Number3 = Number2.replace("(", "");
        String Number4 = Number3.replace(")", "");
        String Number5 = Number4.replace("_", "");
        String Number6 = Number5.replace("-", "");
        etNumber.setText(Number6);
        String snum = etNumber.getText().toString();
        if (snum.length() > 4) { //just checks that there is something. You may want to check that length is greater than or equal to 3
            String bar = snum.substring(0, 4);

            SelectOperator(bar);
        }
    }

    private boolean validateMobile() {
        if (etNumber.getText().toString().trim().isEmpty()) {

            MobileNoError.setText("Number can't be empty.");
            MobileNoError.setVisibility(View.VISIBLE);
            etNumber.requestFocus();
            return false;
        } else if (StartWithArray != null && StartWithArray.size() > 0 && !StartWithArray.contains(etNumber.getText().toString().substring(0, 1))) {

            MobileNoError.setText(AccountName.trim() + " should start with " + Startwith);
            MobileNoError.setVisibility(View.VISIBLE);
            etNumber.requestFocus();
            return false;

        } else if (minNumberLength == 0 && maxNumberLength == 0 && etNumber.getText().length() != 10) {
            MobileNoError.setText(AccountName + " should be length of " + 10);
            MobileNoError.setVisibility(View.VISIBLE);
            etNumber.requestFocus();
            return false;
        } else if (minNumberLength != 0 && maxNumberLength != 0 && minNumberLength != maxNumberLength) {
            boolean value;
            if (etNumber.getText().length() < minNumberLength) {
                MobileNoError.setText(AccountName + " should be length of " + minNumberLength + " to " + maxNumberLength);
                MobileNoError.setVisibility(View.VISIBLE);
                etNumber.requestFocus();
                value = false;
            } else if (etNumber.getText().length() > maxNumberLength) {
                MobileNoError.setText(AccountName + " should be length of " + minNumberLength + " to " + maxNumberLength);
                MobileNoError.setVisibility(View.VISIBLE);
                etNumber.requestFocus();
                value = false;
            } else {
                value = true;
            }
            return value;

        } else if (minNumberLength != 0 && maxNumberLength != 0 && minNumberLength == maxNumberLength && etNumber.getText().length() != maxNumberLength) {
            MobileNoError.setText(AccountName + " should be length of " + maxNumberLength);
            MobileNoError.setVisibility(View.VISIBLE);
            etNumber.requestFocus();
            return false;
        } else if (minNumberLength != 0 && maxNumberLength == 0 && etNumber.getText().length() != minNumberLength) {
            MobileNoError.setText(AccountName + " should be length of " + minNumberLength);
            MobileNoError.setVisibility(View.VISIBLE);
            etNumber.requestFocus();
            return false;
        } else if (minNumberLength == 0 && maxNumberLength != 0 && etNumber.getText().length() != maxNumberLength) {
            MobileNoError.setText(AccountName + " should be length of " + maxNumberLength);
            MobileNoError.setVisibility(View.VISIBLE);
            etNumber.requestFocus();
            return false;
        } else {
            etAmount.requestFocus();
            MobileNoError.setVisibility(View.GONE);
        }

        return true;
    }

    private boolean validateAmount() {
        double amount = 0;
        if (!etAmount.getText().toString().trim().isEmpty()) {
            amount = Double.parseDouble(etAmount.getText().toString().trim());
        }
        int max = maxAmountLength;
        int min = minAmountLength;

        if (etAmount.getText().toString().trim().isEmpty()) {
            AmountError.setText("Amount can't be empty");
            AmountError.setVisibility(View.VISIBLE);
            etAmount.requestFocus();
            return false;
        } else if (amount == 0) {
            AmountError.setText("Amount can't be 0");
            AmountError.setVisibility(View.VISIBLE);
            etAmount.requestFocus();
            return false;
        } else if (amount != 0 && min != 0 && amount < min) {
            AmountError.setText("Amount can't be less then " + min);
            AmountError.setVisibility(View.VISIBLE);
            etAmount.requestFocus();
            return false;
        } else if (amount != 0 && max != 0 && amount > max) {
            AmountError.setText("Amount can't be more then " + max);
            AmountError.setVisibility(View.VISIBLE);
            etAmount.requestFocus();
            return false;
        } else if (amount != 0 && min != 0 && max != 0 && !(amount >= min && amount <= max)) {
            AmountError.setText("Amount shold be between " + min + " to " + max);
            AmountError.setVisibility(View.VISIBLE);
            etAmount.requestFocus();
            return false;
        } else {
            AmountError.setVisibility(View.GONE);

        }
        return true;
    }


    private void SelectOperator(String s) {
        String param = UtilMethods.INSTANCE.fetchOperator(this, s);
        String[] parts = param.split(",");
        if (parts.length == 2) {
            opid = parts[0];
            OpCircleCode = parts[1];
            OpRefCircleID = parts[1];

        } else {
            opid = "";
            OpCircleCode = "";
            OpRefCircleID = "";
        }
        ////////////////////////////////////////////////////////

        NumberListResponse numberListResponse = new NumberListResponse();
        SharedPreferences prefs = this.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String response = prefs.getString(ApplicationConstant.INSTANCE.numberListPref, null);
        Gson gson = new Gson();
        numberListResponse = gson.fromJson(response, NumberListResponse.class);

        if (opid != null && !opid.isEmpty()) {
            for (OperatorList op : numberListResponse.getData().getOperators()) {
                if ((op.getOid() + "").equalsIgnoreCase(opid)) {
                    operatorSelected = op.getName();
                    operatorSelectedId = op.getOid();
                    Startwith = op.getStartWith();
                    if (Startwith != null && Startwith.length() > 0 && Startwith.contains(",")) {
                        StartWithArray = new ArrayList<>(Arrays.asList(Startwith.trim().split(",")));
                    } else if (Startwith != null && !Startwith.isEmpty() && !Startwith.equalsIgnoreCase("0")) {
                        StartWithArray.add(Startwith);
                    }

                    minAmountLength = op.getMin();
                    maxAmountLength = op.getMax();
                    minNumberLength = op.getLength();
                    maxNumberLength = op.getLengthMax();
                    isAcountNumeric = op.getIsAccountNumeric();
                    AccountName = op.getAccountName();
                    AccountRemark = op.getAccountRemak();
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


                    Icon = op.getImage();
                    tv_operator.setText(operatorSelected + "");
                    OperatorError.setVisibility(View.GONE);
                    Glide.with(this)
                            .load(ApplicationConstant.INSTANCE.baseIconUrl + Icon)
                            .apply(requestOptions)
                            .into(iv_oprator);

                    if (operatorSelectedId != 0) {
                        getLapuRealCommission();
                    } else {
                        lapuSwitch.setText("Lapu");
                        realApiSwitch.setText("Recharge With Real");
                    }
                    break;
                }
            }
        } else {
            iv_oprator.setImageResource(R.drawable.ic_tower);
            tv_operator.setText("");
            operatorSelected = "";
            operatorSelectedId = 0;
            lapuSwitch.setText("Lapu");
            realApiSwitch.setText("Recharge With Real");
        }


    }


    void getLapuRealCommission() {
        UtilMethods.INSTANCE.GetLapuRealCommission(RechargeActivity.this, operatorSelectedId + "",
                mLoginDataResponse, new UtilMethods.ApiCallBack() {
                    @Override
                    public void onSucess(Object object) {
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
                            comm = getString(R.string.rupiya) + " " + UtilMethods.INSTANCE.formatedAmount(data.getComm() + "");
                        }

                        if (data.getrCommType() == 0) {
                            rComType = "(COM)";
                        } else {
                            rComType = "(SUR)";
                        }

                        if (data.getrAmtType() == 0) {
                            rComm = UtilMethods.INSTANCE.formatedAmount(data.getrComm() + "") + " %";
                        } else {
                            rComm = getString(R.string.rupiya) + " " + UtilMethods.INSTANCE.formatedAmount(data.getrComm() + "");
                        }
                        lapuSwitch.setText("Lapu\n" + comm + " " + comType);
                        realApiSwitch.setText("Recharge With Real " + rComm + " " + rComType);

                    }
                });
    }

    private void SelectOperator(int opretorId, int circleId) {


        opid = String.valueOf(opretorId);
        OpRefCircleID = String.valueOf(circleId);


        ////////////////////////////////////////////////////////

        NumberListResponse numberListResponse = new NumberListResponse();
        SharedPreferences prefs = this.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String response = prefs.getString(ApplicationConstant.INSTANCE.numberListPref, null);
        Gson gson = new Gson();
        numberListResponse = gson.fromJson(response, NumberListResponse.class);

        for (OperatorList op : numberListResponse.getData().getOperators()) {
            if ((op.getOid() + "").equalsIgnoreCase(opid)) {
                operatorSelected = op.getName();
                operatorSelectedId = op.getOid();
                Startwith = op.getStartWith();
                if (Startwith != null && Startwith.length() > 0 && Startwith.contains(",")) {
                    StartWithArray = new ArrayList<>(Arrays.asList(Startwith.trim().split(",")));
                } else if (Startwith != null && !Startwith.isEmpty() && !Startwith.equalsIgnoreCase("0")) {
                    StartWithArray.add(Startwith);
                }

                minAmountLength = op.getMin();
                maxAmountLength = op.getMax();
                minNumberLength = op.getLength();
                maxNumberLength = op.getLengthMax();
                isAcountNumeric = op.getIsAccountNumeric();
                AccountName = op.getAccountName();
                AccountRemark = op.getAccountRemak();
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


                Icon = op.getImage();
                tv_operator.setText(operatorSelected + "");
                OperatorError.setVisibility(View.GONE);
                Glide.with(this)
                        .load(ApplicationConstant.INSTANCE.baseIconUrl + Icon)
                        .apply(requestOptions)
                        .into(iv_oprator);

                if (operatorSelectedId != 0) {
                    getLapuRealCommission();
                } else {
                    lapuSwitch.setText("Lapu");
                    realApiSwitch.setText("Recharge With Real");
                }

                break;
            }
        }

    }
/*

    public void ItemClick(String name, int id, String param1, String param2, String param3, String param4, String Roundpay, String ican) {
        opName.setText(name);
        OpName = name;
        operatorSelectedId = id;
        RoundPayId = Roundpay;
        opIcon = ican;


        Glide.with(this).load(ApplicationConstant.INSTANCE.baseIconUrl + opIcon).
                apply(requestOptions).into(opImage);


        // Log.e("val", name + " , " + id);

        OpRefOp = UtilMethods.INSTANCE.fetchShortCode(this, name);

        FragmentActivityMessage activityActivityMessage =
                new FragmentActivityMessage("" + "ok", "setValue");
        GlobalBus.getBus().post(activityActivityMessage);

        if (mobileType.equalsIgnoreCase("prepaid")) {
            if (OpRefOp.isEmpty()) {
                tvCircle.setVisibility(View.GONE);

            } else {
                tvCircle.setVisibility(View.VISIBLE);

            }
        } else {
            tvCircle.setVisibility(View.GONE);

        }
        tvOperator.setText("" + OpName);
    }

    public void CircleClick(String CircleName, String CircleCode) {
        // Log.e("val1", CircleName + " , " + CircleCode);
        OpRefCircleID = CircleCode;
        tvBrowsePlan.setVisibility(View.VISIBLE);

        FragmentActivityMessage activityActivityMessage =
                new FragmentActivityMessage("" + "ok", "setCircle");
        GlobalBus.getBus().post(activityActivityMessage);
    }
*/

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

    @Subscribe
    public void onActivityActivityMessage(ActivityActivityMessage activityFragmentMessage) {
        if (activityFragmentMessage.getMessage().equalsIgnoreCase("planSelected")) {
            String[] str = activityFragmentMessage.getFrom().split("_");
            etAmount.setText(str[0] + "");
            try {
                desc.setVisibility(View.VISIBLE);
                desc.setText(str[1] + "");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (activityFragmentMessage.getMessage().equalsIgnoreCase("planSelectedDetail")) {
            noticePlan.setVisibility(View.VISIBLE);
            noticePlan.setText(activityFragmentMessage.getFrom() + "");
        } else if (activityFragmentMessage.getMessage().equalsIgnoreCase("view_bill_post")) {
            String[] data = activityFragmentMessage.getFrom().split(",");
            String balance = data[0];
            String partial = data[1];
            etAmount.setText(balance);
            etAmount.setEnabled(true);
        } else if (activityFragmentMessage.getFrom().equalsIgnoreCase("refreshvalue")) {
            etNumber.setText("");
            etAmount.setText("");
            MobileNoError.setVisibility(View.GONE);
            AmountError.setVisibility(View.GONE);
            btRecharge.setEnabled(true);
            tvName.setVisibility(View.GONE);
            if (UtilMethods.INSTANCE.isNetworkAvialable(RechargeActivity.this)) {
                UtilMethods.INSTANCE.Balancecheck(RechargeActivity.this, loader, null);
            }
        } else if (activityFragmentMessage.getMessage().equalsIgnoreCase("rOffer_Amount")) {
            String[] str = activityFragmentMessage.getFrom().split("_");
            etAmount.setText(str[0] + "");
            try {
                desc.setVisibility(View.VISIBLE);
                desc.setText(str[1] + "");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public void getHLRLookUp() {
        try {
            loader.show();
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            String LoginResponse = UtilMethods.INSTANCE.getLoginPref(this);
            LoginResponse LoginDataResponse = new Gson().fromJson(LoginResponse, LoginResponse.class);
            Call<GetHLRLookUPResponse> call = git.GetHLRLookUp(new GetHLRLookUpRequest(etNumber.getText().toString(),
                    LoginDataResponse.getData().getUserID(), LoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID,
                    UtilMethods.INSTANCE.getIMEI(this),
                    "", BuildConfig.VERSION_NAME, UtilMethods.INSTANCE.getSerialNo(this), LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));

            call.enqueue(new Callback<GetHLRLookUPResponse>() {

                @Override
                public void onResponse(Call<GetHLRLookUPResponse> call, retrofit2.Response<GetHLRLookUPResponse> response) {
//                     // Log.e("Payment response", "hello response : " + new Gson().toJson(response.body()).toString());
                    if (loader.isShowing())
                        loader.dismiss();
                    GetHLRLookUPResponse mGetHLRLookUPResponse = response.body();
                    if (mGetHLRLookUPResponse != null) {

                        if (!UtilMethods.INSTANCE.isPassChangeDialogShowing) {
                            if (mGetHLRLookUPResponse.isPasswordExpired()) {

                                CustomAlertDialog customPassDialog = new CustomAlertDialog(RechargeActivity.this, true);
                                customPassDialog.WarningWithSingleBtnCallBack(getString(R.string.password_expired_msg), "Change", false, new CustomAlertDialog.DialogCallBack() {
                                    @Override
                                    public void onPositiveClick() {
                                        new ChangePassUtils(RechargeActivity.this).changePassword(false, false);
                                    }

                                    @Override
                                    public void onNegativeClick() {

                                    }
                                });

                            }
                        }


                        if (mGetHLRLookUPResponse.getStatuscode() == 1) {
                            if (mGetHLRLookUPResponse.getOid() != 0) {
                                SelectOperator(mGetHLRLookUPResponse.getOid(), mGetHLRLookUPResponse.getCircleID());
                            } else {
                                tv_operator.setText("");
                                iv_oprator.setImageResource(R.drawable.ic_tower);
                                operatorSelected = "";
                                operatorSelectedId = 0;
                                opid = "";
                                OpCircleCode = "";
                                OpRefCircleID = "";
                                lapuSwitch.setText("Lapu");
                                realApiSwitch.setText("Recharge With Real");
                            }


                        } else if (response.body().getStatuscode() == -1) {

                            UtilMethods.INSTANCE.Error(RechargeActivity.this, mGetHLRLookUPResponse.getMsg() + "");
                        } else if (response.body().getStatuscode() == 0) {

                            UtilMethods.INSTANCE.Error(RechargeActivity.this, mGetHLRLookUPResponse.getMsg() + "");
                        } else {

                            UtilMethods.INSTANCE.Error(RechargeActivity.this, mGetHLRLookUPResponse.getMsg() + "");
                        }

                    } else {

                        UtilMethods.INSTANCE.Error(RechargeActivity.this, getString(R.string.some_thing_error));
                    }
                }

                @Override
                public void onFailure(Call<GetHLRLookUPResponse> call, Throwable t) {

                    try {
                        if (loader.isShowing()) {
                            loader.dismiss();
                        }
                        if (t.getMessage() != null && !t.getMessage().isEmpty()) {

                            if (t.getMessage().contains("No address associated with hostname")) {

                                UtilMethods.INSTANCE.NetworkError(RechargeActivity.this, getString(R.string.network_error_title), getString(R.string.err_msg_network));


                            } else {

                                UtilMethods.INSTANCE.Error(RechargeActivity.this, t.getMessage());


                            }

                        } else {

                            UtilMethods.INSTANCE.Error(RechargeActivity.this, getString(R.string.some_thing_error));
                        }
                    } catch (IllegalStateException ise) {
                        loader.dismiss();

                        UtilMethods.INSTANCE.Error(RechargeActivity.this, ise.getMessage() + "");
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();

            UtilMethods.INSTANCE.Error(RechargeActivity.this, e.getMessage() + "");
        }

    }


    public void HitIncentiveApi() {
        if (UtilMethods.INSTANCE.isNetworkAvialable(this)) {
            loader.show();
            loader.setCancelable(false);
            loader.setCanceledOnTouchOutside(false);
            UtilMethods.INSTANCE.IncentiveDetail(this, operatorSelectedId + "", loader, new UtilMethods.ApiCallBack() {
                @Override
                public void onSucess(Object object) {
                    AppUserListResponse mAppUserListResponse = (AppUserListResponse) object;
                    incentiveOperatorSelectedId = operatorSelectedId;

                    incentiveList = mAppUserListResponse.getIncentiveDetails();
                    showPopupIncentive();
                }
            });
        } else {
            UtilMethods.INSTANCE.NetworkError(this, getResources().getString(R.string.err_msg_network_title),
                    getResources().getString(R.string.err_msg_network));
        }
    }


    private void showPopupIncentive() {

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE); // or (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewMyLayout = inflater.inflate(R.layout.dialog_incentive, null);
        RecyclerView recyclerView = viewMyLayout.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        View closeBtn = viewMyLayout.findViewById(R.id.closeBtn);

        IncentiveAdapter incentiveAdapter = new IncentiveAdapter(incentiveList, RechargeActivity.this);
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
        AmountError.setVisibility(View.GONE);
        desc.setText("You are eligible for " + item.getComm() + (item.isAmtType() ? " \u20B9 Cash Back" : " % Cash Back"));
    }
}

package com.solution.brothergroup.DTH.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.appbar.AppBarLayout;
import com.google.gson.Gson;
import com.solution.brothergroup.Api.Object.CommissionDisplay;
import com.solution.brothergroup.Api.Object.OperatorList;
import com.solution.brothergroup.Authentication.dto.LoginResponse;
import com.solution.brothergroup.DTH.Adapter.DthSubscriptionReportAdapter;
import com.solution.brothergroup.DTH.dto.DthPackage;
import com.solution.brothergroup.DTH.dto.DthSubscriptionReport;
import com.solution.brothergroup.DTH.dto.DthSubscriptionReportResponse;
import com.solution.brothergroup.DTH.dto.GetDthPackageResponse;
import com.solution.brothergroup.DTH.dto.PincodeArea;
import com.solution.brothergroup.DTH.dto.PincodeAreaResponse;
import com.solution.brothergroup.Fragments.dto.BalanceType;
import com.solution.brothergroup.R;
import com.solution.brothergroup.Util.ApplicationConstant;
import com.solution.brothergroup.Util.CustomAlertDialog;
import com.solution.brothergroup.Util.GetLocation;
import com.solution.brothergroup.Util.ListAreaPopupWindowAdapter;
import com.solution.brothergroup.Util.ListPopupWindowAdapter;
import com.solution.brothergroup.Util.UtilMethods;
import com.solution.brothergroup.usefull.CustomLoader;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.ListPopupWindow;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.recyclerview.widget.RecyclerView;

public class DTHSubscriptionActivity extends AppCompatActivity {
    private AppBarLayout appBarLayout;
    private AppCompatTextView rechargeViewTab;
    private AppCompatTextView historyViewTab;
    private LinearLayout rechargeView;
    private LinearLayout llMain;
    private ImageView ivIcon;
    private AppCompatTextView opName;
    private ImageView billLogo;
    private RelativeLayout rlPackage, rlGender, rlArea;
    private ImageView ivPackage;
    private AppCompatTextView tvPackage, tvGender, tvArea;
    private ImageView ivArrowPackage;
    private TextView packageError, viewChannel;
    private LinearLayout rlName, rlLastName;
    private ImageView ivName;
    private EditText etName, etLastName;
    private TextView nameError;
    private RelativeLayout rlNumber;
    private ImageView ivNumber;
    private LinearLayout numberView;
    private TextView tvName;
    private EditText etNumber;
    private ImageView ivPhoneBook;
    private TextView numberError;
    private LinearLayout rlAddress;
    private EditText etAddress;
    private TextView addressError;
    private LinearLayout rlPincode;
    private EditText etPincode;
    private TextView pincodeError, areaError, lastNameError, genderError;
    private RelativeLayout historyView;
    private RelativeLayout historyHeader;
    private AppCompatTextView recentRechargeTv;
    private AppCompatTextView viewMore;
    private RecyclerView recyclerView;
    private LinearLayout llComingSoon;
    private Button btRecharge;
    private String fromDateStr, toDateStr;
    int maxAmountLength ;
    int minAmountLength ;
    boolean isAcountNumeric;
    int minNumberLength = 0, maxNumberLength = 0;
    String AccountName = "", Account_Remark = "";
    Boolean BBPS = false, IsPartial = false;
    Boolean isBilling = false;
    String StartWith = "";
    boolean isFetchBill;
    ArrayList<String> StartWithArray = new ArrayList<>();
    String operatorSelected = "";
    int operatorSelectedId = 0, incentiveOperatorSelectedId;
    private String icon;
    private RequestOptions requestOptions;
    private String from;
    private int fromId;
    int PICK_CONTACT = 142;
    private String number;
    private CustomLoader loader;
    CustomAlertDialog mCustomAlertDialog;
    private String selectedPackageAmount;
    private GetDthPackageResponse mGetDthPackageResponse;
    private int INTENT_PACKAGE = 529;
    private DthPackage mIntentDthPackage;
    private LoginResponse mLoginDataResponse;
    private double getLattitude, getLongitude;
    private GetLocation mGetLocation;
    private String dataOfPincode = "";
    ArrayList<PincodeArea> pincodeAreasArray = new ArrayList<>();
    ArrayList<BalanceType> itemsGender = new ArrayList<>();
    private int selectedAreaId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowInsetsControllerCompat controller = new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        controller.setAppearanceLightStatusBars(true);
        controller.setAppearanceLightNavigationBars(true);
        setContentView(R.layout.activity_dth_subscription);
        itemsGender.add(new BalanceType("Male", "Hide"));
        itemsGender.add(new BalanceType("Female", "Hide"));

            mLoginDataResponse = new Gson().fromJson(UtilMethods.INSTANCE.getLoginPref(this), LoginResponse.class);


        mCustomAlertDialog = new CustomAlertDialog(this, true);
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);
        findViews();
        getDthPackage();

        final Calendar myCalendar = Calendar.getInstance();
        String myFormat = "dd-MMM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        fromDateStr = sdf.format(myCalendar.getTime());
        toDateStr = sdf.format(myCalendar.getTime());
        mGetLocation = new GetLocation(this, loader);
        mGetLocation.startLocationUpdatesIfSettingEnable((lattitude, longitude) -> {
            getLattitude = lattitude;
            getLongitude = longitude;
        });
        HitApi();
    }


    private void findViews() {
        from = getIntent().getExtras().getString("from");
        fromId = getIntent().getExtras().getInt("fromId", 0);
        OperatorList mOperatorList = (OperatorList) getIntent().getSerializableExtra("SelectedOperator");
        if (mOperatorList != null) {
            operatorSelected = mOperatorList.getName();
            operatorSelectedId = mOperatorList.getOid();
            minAmountLength = mOperatorList.getMin();
            maxAmountLength = mOperatorList.getMax();
            minNumberLength = mOperatorList.getLength();
            maxNumberLength = mOperatorList.getLengthMax();
            isAcountNumeric = mOperatorList.getIsAccountNumeric();

            IsPartial = mOperatorList.getIsPartial();
            BBPS = mOperatorList.getBBPS();
            isBilling = mOperatorList.getIsBilling();
            AccountName = mOperatorList.getAccountName();
            Account_Remark = mOperatorList.getAccountRemak();
            StartWith = mOperatorList.getStartWith();
            if (StartWith != null && StartWith.length() > 0 && StartWith.contains(",")) {
                StartWithArray = new ArrayList<>(Arrays.asList(StartWith.trim().split(",")));
            } else if (StartWith != null && !StartWith.isEmpty() && !StartWith.equalsIgnoreCase("0")) {
                StartWithArray.add(StartWith);
            }
            icon = mOperatorList.getImage();
        }


        Toolbar toolBar = findViewById(R.id.toolbar);
        toolBar.setTitle(from + " Subscriptions");
        toolBar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolBar);
        toolBar.setNavigationIcon(R.drawable.ic_arrow_back_icon);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        requestOptions = new RequestOptions();
        requestOptions.circleCropTransform();
        requestOptions.placeholder(R.mipmap.ic_launcher_round);
        requestOptions.error(R.mipmap.ic_launcher_round);
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);

        viewChannel = findViewById(R.id.viewChannel);
        rechargeViewTab = findViewById(R.id.rechargeViewTab);
        historyViewTab = findViewById(R.id.historyViewTab);
        rechargeView = findViewById(R.id.rechargeView);
        llMain = findViewById(R.id.ll_main);
        ivIcon = findViewById(R.id.iv_icon);
        opName = findViewById(R.id.opName);
        billLogo = findViewById(R.id.bill_logo);
        rlPackage = findViewById(R.id.rl_package);
        rlGender = findViewById(R.id.rl_gender);
        rlArea = findViewById(R.id.rl_area);
        ivPackage = findViewById(R.id.iv_package);
        tvPackage = findViewById(R.id.tv_package);
        tvGender = findViewById(R.id.tv_gender);
        tvArea = findViewById(R.id.tv_area);
        ivArrowPackage = findViewById(R.id.iv_arrow_package);
        packageError = findViewById(R.id.package_error);
        rlName = findViewById(R.id.rl_name);
        rlLastName = findViewById(R.id.rl_last_name);
        ivName = findViewById(R.id.iv_name);
        etName = findViewById(R.id.et_name);
        etLastName = findViewById(R.id.et_last_name);
        nameError = findViewById(R.id.name_error);
        rlNumber = findViewById(R.id.rl_number);
        ivNumber = findViewById(R.id.iv_number);
        numberView = findViewById(R.id.numberView);
        tvName = findViewById(R.id.tv_name);
        etNumber = findViewById(R.id.et_number);
        ivPhoneBook = findViewById(R.id.iv_phone_book);
        numberError = findViewById(R.id.number_error);
        rlAddress = findViewById(R.id.rl_address);
        etAddress = findViewById(R.id.et_address);
        addressError = findViewById(R.id.address_error);
        rlPincode = findViewById(R.id.rl_pincode);
        etPincode = findViewById(R.id.et_pincode);
        pincodeError = findViewById(R.id.pincode_error);
        areaError = findViewById(R.id.area_error);
        lastNameError = findViewById(R.id.last_name_error);
        genderError = findViewById(R.id.gender_error);
        historyView = findViewById(R.id.historyView);
        historyHeader = findViewById(R.id.historyHeader);
        recentRechargeTv = findViewById(R.id.recentRechargeTv);
        viewMore = findViewById(R.id.viewMore);
        recyclerView = findViewById(R.id.recyclerView);
        llComingSoon = findViewById(R.id.ll_coming_soon);
        btRecharge = findViewById(R.id.bt_recharge);

        opName.setText(operatorSelected);
        rechargeViewTab.setText(from + "");
        Glide.with(this)
                .load(ApplicationConstant.INSTANCE.baseIconUrl + icon)
                .apply(requestOptions)
                .into(ivIcon);

        if (BBPS) {
            billLogo.setVisibility(View.VISIBLE);
        } else {
            billLogo.setVisibility(View.GONE);
        }
        viewMore.setOnClickListener(v -> {
            Intent i = new Intent(DTHSubscriptionActivity.this, DthSubscriptionReportActivity.class);
            i.putExtra("from", from + "");
            i.putExtra("fromId", fromId);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
        });
        rechargeViewTab.setOnClickListener(v -> {
            historyView.setVisibility(View.GONE);
            rechargeView.setVisibility(View.VISIBLE);
            btRecharge.setVisibility(View.VISIBLE);
            rechargeViewTab.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            historyViewTab.setBackgroundColor(getResources().getColor(R.color.light_grey));
        });

        historyViewTab.setOnClickListener(v -> {
            historyView.setVisibility(View.VISIBLE);
            rechargeView.setVisibility(View.GONE);
            btRecharge.setVisibility(View.GONE);
            rechargeViewTab.setBackgroundColor(getResources().getColor(R.color.light_grey));
            historyViewTab.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            HitApi();
        });

        ivPhoneBook.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivityForResult(intent, PICK_CONTACT);
        });
        rlPackage.setOnClickListener(v -> {
            if (mGetDthPackageResponse != null && mGetDthPackageResponse.getDthPackage() != null && mGetDthPackageResponse.getDthPackage().size() > 0) {
                startActivityForResult(new Intent(DTHSubscriptionActivity.this, DthPackageActivity.class)
                        .putExtra("PACKAGE_DATA_ARRAY", mGetDthPackageResponse.getDthPackage())
                        .putExtra("Title", from + " (" + operatorSelected + ")")
                        .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), INTENT_PACKAGE);
            } else {
                mCustomAlertDialog.WarningWithDoubleBtnCallBack("Sorry, Package Not Found.", "Retry", true, new CustomAlertDialog.DialogCallBack() {
                    @Override
                    public void onPositiveClick() {
                        getDthPackage();
                    }

                    @Override
                    public void onNegativeClick() {

                    }
                });
            }
        });

        viewChannel.setOnClickListener(v ->
                startActivity(new Intent(DTHSubscriptionActivity.this, DthChannelActivity.class)
                        .putExtra("DTH_PACKAGE", mIntentDthPackage)
                        .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)));


        etNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (tvName.getVisibility() == View.VISIBLE && s.length() != 10) {
                    tvName.setVisibility(View.GONE);
                }
            }
        });

        rlGender.setOnClickListener(v -> showGenderPoupWindow(tvGender));

        rlArea.setOnClickListener(v -> {
            pincodeError.setVisibility(View.GONE);
            if (TextUtils.isEmpty(etPincode.getText())) {
                pincodeError.setVisibility(View.VISIBLE);
                pincodeError.setText("Please Enter Valid Area Pincode");
                etPincode.requestFocus();
                return;
            } else if (etPincode.getText().toString().replaceAll(" ", "").length() != 6) {
                pincodeError.setVisibility(View.VISIBLE);
                pincodeError.setText("Please Enter 6 Digit Area Pincode");
                etPincode.requestFocus();
                return;
            } else if (pincodeAreasArray == null || pincodeAreasArray.size() == 0) {
                pincodeError.setVisibility(View.VISIBLE);
                pincodeError.setText("Please Enter Valid Area Pincode");
                etPincode.requestFocus();
                return;
            }

            showAreaPoupWindow(v);
        });
        etPincode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (dataOfPincode != null && !dataOfPincode.equalsIgnoreCase(s.toString()) && s.toString().trim().length() == 6) {
                    tvArea.setText("");
                    pincodeAreasArray.clear();
                    PincodeArea();
                }
            }
        });
        btRecharge.setOnClickListener(v -> submitDetail());
    }


    void submitDetail() {

        packageError.setVisibility(View.GONE);
        nameError.setVisibility(View.GONE);
        lastNameError.setVisibility(View.GONE);
        genderError.setVisibility(View.GONE);
        numberError.setVisibility(View.GONE);
        addressError.setVisibility(View.GONE);
        pincodeError.setVisibility(View.GONE);
        areaError.setVisibility(View.GONE);
        if (TextUtils.isEmpty(tvPackage.getText())) {
            packageError.setVisibility(View.VISIBLE);
            packageError.setText("Please Select Package First");
            tvPackage.requestFocus();
            return;
        } else if (TextUtils.isEmpty(etName.getText())) {
            nameError.setVisibility(View.VISIBLE);
            nameError.setText("Please Enter Your First Name");
            etName.requestFocus();
            return;
        } else if (TextUtils.isEmpty(etLastName.getText())) {
            lastNameError.setVisibility(View.VISIBLE);
            lastNameError.setText("Please Enter Your Last Name");
            etLastName.requestFocus();
            return;
        } else if (TextUtils.isEmpty(tvGender.getText())) {
            genderError.setVisibility(View.VISIBLE);
            genderError.setText("Please Select Your Gender");
            tvGender.requestFocus();
            return;
        } else if (TextUtils.isEmpty(etNumber.getText())) {
            numberError.setVisibility(View.VISIBLE);
            numberError.setText("Please Enter Valid Mobile Number");
            etNumber.requestFocus();
            return;
        } else if (etNumber.getText().toString().replaceAll(" ", "").length() != 10) {
            numberError.setVisibility(View.VISIBLE);
            numberError.setText("Please Enter 10 Digit Mobile Number");
            etNumber.requestFocus();
            return;
        } else if (TextUtils.isEmpty(etAddress.getText())) {
            addressError.setVisibility(View.VISIBLE);
            addressError.setText("Please Enter Your Address");
            etAddress.requestFocus();
            return;
        } else if (TextUtils.isEmpty(etPincode.getText())) {
            pincodeError.setVisibility(View.VISIBLE);
            pincodeError.setText("Please Enter Valid Area Pincode");
            etPincode.requestFocus();
            return;
        } else if (etPincode.getText().toString().replaceAll(" ", "").length() != 6) {
            pincodeError.setVisibility(View.VISIBLE);
            pincodeError.setText("Please Enter 6 Digit Area Pincode");
            etPincode.requestFocus();
            return;
        } else if (TextUtils.isEmpty(tvArea.getText())) {
            areaError.setVisibility(View.VISIBLE);
            areaError.setText("Please Select Your Area");
            tvArea.requestFocus();
            return;
        }


        ConfirmDialog(null);
    }


    void ConfirmDialog(CommissionDisplay mCommissionDisplay) {
        String fullAddress = etAddress.getText().toString().contains(etPincode.getText().toString()) ? etAddress.getText().toString() : etAddress.getText().toString() + " - " + etPincode.getText().toString();
        UtilMethods.INSTANCE.dthSubscriptionConfiormDialog(this, mCommissionDisplay, false,
                UtilMethods.INSTANCE.getDoubleFactorPref(this), ApplicationConstant.INSTANCE.baseIconUrl + icon,
                etNumber.getText().toString(), operatorSelected, UtilMethods.INSTANCE.formatedAmount(mIntentDthPackage.getPackageMRP() + ""),
                tvPackage.getText().toString(), etName.getText().toString(),
                fullAddress, new UtilMethods.DialogCallBack() {
                    @Override
                    public void onPositiveClick(String pinPass) {
                        if (UtilMethods.INSTANCE.isNetworkAvialable(DTHSubscriptionActivity.this)) {
                            if (getLattitude != 0 && getLongitude != 0) {
                                DthSubscription(getLattitude, getLongitude, pinPass);
                            } else {
                                if (mGetLocation != null) {
                                    mGetLocation.startLocationUpdates((lattitude, longitude) -> {
                                        getLattitude = lattitude;
                                        getLongitude = longitude;
                                        DthSubscription(lattitude, longitude, pinPass);
                                    });
                                } else {
                                    mGetLocation = new GetLocation(DTHSubscriptionActivity.this, loader);
                                    mGetLocation.startLocationUpdates((lattitude, longitude) -> {
                                        getLattitude = lattitude;
                                        getLongitude = longitude;
                                        DthSubscription(lattitude, longitude, pinPass);
                                    });
                                }
                            }

                        } else {

                            UtilMethods.INSTANCE.NetworkError(DTHSubscriptionActivity.this, getResources().getString(R.string.err_msg_network_title),
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


    void DthSubscription(double lati, double longi, String pinPass) {
        UtilMethods.INSTANCE.DTHSubscription(DTHSubscriptionActivity.this, false,
                mIntentDthPackage != null ? mIntentDthPackage.getId() : 0, etLastName.getText().toString(),
                tvGender.getText().toString(), selectedAreaId,
                operatorSelected, etName.getText().toString(), etAddress.getText().toString(),
                etPincode.getText().toString().trim(), etNumber.getText().toString().trim(),
                lati + "," + longi
                , pinPass, mIntentDthPackage, loader,mLoginDataResponse);
    }

    void PincodeArea() {
        loader.show();
        UtilMethods.INSTANCE.PincodeArea(DTHSubscriptionActivity.this, etPincode.getText().toString().trim(),
                loader,mLoginDataResponse, new UtilMethods.ApiCallBack() {
                    @Override
                    public void onSucess(Object object) {
                        PincodeAreaResponse mPincodeAreaResponse = (PincodeAreaResponse) object;
                        dataOfPincode = etPincode.getText().toString();
                        pincodeAreasArray = mPincodeAreaResponse.getAreas();
                    }
                });
    }

    public void HitApi() {
        if (UtilMethods.INSTANCE.isNetworkAvialable(this)) {
            UtilMethods.INSTANCE.DthSubscriptionReport(this, fromId + "", "20", 0, 0, fromDateStr, toDateStr, "", "", "", "false", true, loader, mLoginDataResponse, new UtilMethods.ApiCallBack() {
                @Override
                public void onSucess(Object object) {
                    DthSubscriptionReportResponse mRechargeReportResponse = (DthSubscriptionReportResponse) object;
                    dataParse(mRechargeReportResponse);
                }
            });

        } else {
            UtilMethods.INSTANCE.NetworkError(this, getResources().getString(R.string.err_msg_network_title),
                    getResources().getString(R.string.err_msg_network));
        }
    }


    public void dataParse(DthSubscriptionReportResponse response) {

        ArrayList<DthSubscriptionReport> transactionsObjects = response.getDTHSubscriptionReport();
        if (transactionsObjects != null && transactionsObjects.size() > 0) {
            DthSubscriptionReportAdapter mAdapter = new DthSubscriptionReportAdapter(transactionsObjects, this, mLoginDataResponse.getData().getRoleID());
            recyclerView.setAdapter(mAdapter);
            llComingSoon.setVisibility(View.GONE);
        } else {
            llComingSoon.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        if (reqCode == PICK_CONTACT && resultCode == RESULT_OK) {
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
                        number = phones.getString(phones.getColumnIndex("data1"));

                        phones.close();
                    }
                }
                String Name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                if (!number.equals("")) {
                    if (!Name.equals("")) {
                        tvName.setText(Name);
                        tvName.setVisibility(View.VISIBLE);
                    } else {
                        tvName.setVisibility(View.VISIBLE);
                    }
                    //Set the Number Without space and +91..
                    SetNumber(number);
                } else {
                    Toast.makeText(this, "Please select a valid number", Toast.LENGTH_SHORT).show();
                }

            }
        } else if (reqCode == INTENT_PACKAGE && resultCode == RESULT_OK) {
            mIntentDthPackage = (DthPackage) data.getSerializableExtra("Package");
            if (mIntentDthPackage != null) {
                tvPackage.setText(mIntentDthPackage.getPackageName() + "");
                viewChannel.setVisibility(View.VISIBLE);
            }

        } else {
            if (mGetLocation != null) {
                mGetLocation.onActivityResult(reqCode, resultCode, data);
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (mGetLocation != null) {
            mGetLocation.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    protected void onPause() {
        if (mGetLocation != null) {
            mGetLocation.onPause();
        }
        super.onPause();
    }


    public void SetNumber(final String Number) {
        String Number1 = Number.replace("+91", "");
        String Number2 = Number1.replace(" ", "");
        String Number3 = Number2.replace("(", "");
        String Number4 = Number3.replace(")", "");
        String Number5 = Number4.replace("_", "");
        String Number6 = Number5.replace("-", "");
        etNumber.setText(Number6);
    }


    void getDthPackage() {

        UtilMethods.INSTANCE.GetDthPackage(DTHSubscriptionActivity.this, operatorSelectedId + "", loader,mLoginDataResponse, object -> {
            mGetDthPackageResponse = (GetDthPackageResponse) object;


        });
    }


    private void showGenderPoupWindow(View anchor) {


        final ListPopupWindow popup = new ListPopupWindow(this);
        ListAdapter adapter = new ListPopupWindowAdapter(itemsGender, this, false, R.layout.wallet_list_popup, (name, value) -> {
            tvGender.setText(name);
            popup.dismiss();
        });
        popup.setAnchorView(anchor);
        popup.setAdapter(adapter);
        popup.setBackgroundDrawable(getResources().getDrawable(R.drawable.rect));
        popup.show();
    }

    private void showAreaPoupWindow(View anchor) {

        final ListPopupWindow popup = new ListPopupWindow(this);
        ListAdapter adapter = new ListAreaPopupWindowAdapter(pincodeAreasArray, this, R.layout.wallet_list_popup, (area, id, time) -> {
            if (time == 0) {
                tvArea.setText(area);
            } else {
                tvArea.setText(area + " (Reach Time - " + UtilMethods.INSTANCE.formatedAmount(time + "") + " Hr)");
            }

            selectedAreaId = id;
            popup.dismiss();
        });
        popup.setAnchorView(anchor);
        popup.setAdapter(adapter);
        popup.setBackgroundDrawable(getResources().getDrawable(R.drawable.rect));
        popup.show();
    }

}

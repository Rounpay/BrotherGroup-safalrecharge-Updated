package com.solution.brothergroup.Activities;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;

import com.solution.brothergroup.Adapter.MoveToBankReportAdapter;
import com.solution.brothergroup.Api.Object.MoveToWalletReportData;
import com.solution.brothergroup.Api.Object.OperatorList;
import com.solution.brothergroup.Api.Response.NumberListResponse;
import com.solution.brothergroup.Api.Response.RechargeReportResponse;
import com.solution.brothergroup.Authentication.dto.LoginResponse;
import com.solution.brothergroup.R;
import com.solution.brothergroup.Util.CustomFilterDialogUtils.CustomFilterDialog;
import com.solution.brothergroup.Util.UtilMethods;
import com.solution.brothergroup.usefull.CustomLoader;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Vishnu on 18-04-2017.
 */

public class MoveToBankReportActivity extends AppCompatActivity {

    RecyclerView recycler_view;
    MoveToBankReportAdapter mAdapter;
    private CustomFilterDialog mCustomFilterDialog;
    ArrayList<MoveToWalletReportData> transactionsObjects = new ArrayList<>();

    CustomLoader loader;
    EditText search_all;

    private String filterFromDate = "", filterToDate = "";
    String filterTransactionId, filterChildMobileNo;
    private int filterTopValue = 50;
    private String filterStatus = "";

    private int filterStatusId;


    private String filterModeValue = "";
    private int filterModeId;
    private LoginResponse mLoginDataResponse;
    private String deviceId, deviceSerialNum;

    private HashMap<String, Integer> disputeStatusMap = new HashMap<>();
    private String[] disputeStatusArray = {":: Select Status ::", "REQUESTED", "ACCEPTED", "REJECTED"};

    private String[] ledgerChooseTopArray = {"50", "100", "200", "500", "1000", "1500", "ALL"};
    private String[] chooseModeArray = {};
    private HashMap<String, Integer> chooseModeMap = new HashMap<>();
    private NumberListResponse operatorListResponse;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowInsetsControllerCompat controller = new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        controller.setAppearanceLightStatusBars(true);
        controller.setAppearanceLightNavigationBars(true);
        setContentView(R.layout.activity_movetobank_report);
        Gson gson = new Gson();
        mLoginDataResponse = gson.fromJson(UtilMethods.INSTANCE.getLoginPref(this), LoginResponse.class);
        deviceId = UtilMethods.INSTANCE.getIMEI(this);
        deviceSerialNum = UtilMethods.INSTANCE.getSerialNo(this);
        mCustomFilterDialog = new CustomFilterDialog(this);
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);
        filterChildMobileNo = mLoginDataResponse.getData().getMobileNo() + "";

        disputeStatusMap.put(":: Select Status ::", 0);
        disputeStatusMap.put("REQUESTED", 1);
        disputeStatusMap.put("ACCEPTED", 2);
        disputeStatusMap.put("REJECTED", 3);

        findViews();
        final Calendar myCalendar = Calendar.getInstance();
        String myFormat = "dd-MMM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        filterFromDate = sdf.format(myCalendar.getTime());

        filterToDate = filterFromDate;
        findViewById(R.id.clearIcon).setOnClickListener(v -> search_all.setText(""));
        search_all.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence newText, int start, int before, int count) {
                if (mAdapter != null) {
                    mAdapter.filter(newText.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        operatorListResponse = gson.fromJson(UtilMethods.INSTANCE.getNumberList(this), NumberListResponse.class);


        getOperator(42, operatorListResponse.getData().getOperators());

        HitApi(true);

    }


    void findViews() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setTitle("Move To Bank Report");
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_icon);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        search_all = findViewById(R.id.search_all);
        recycler_view = findViewById(R.id.recycler_view);
        recycler_view.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new MoveToBankReportAdapter(transactionsObjects, com.solution.brothergroup.Activities.MoveToBankReportActivity.this);
        recycler_view.setAdapter(mAdapter);
    }

    private void HitApi(boolean isLoaderShow) {
        if (UtilMethods.INSTANCE.isNetworkAvialable(this)) {

            if (isLoaderShow) {
                loader.show();
                loader.setCancelable(false);
                loader.setCanceledOnTouchOutside(false);
            }
            UtilMethods.INSTANCE.MoveToBankReport(this, filterStatusId, filterTopValue + "", filterModeId,
                    filterFromDate, filterToDate, filterTransactionId, filterChildMobileNo,
                    loader, mLoginDataResponse, deviceId, deviceSerialNum, new UtilMethods.ApiCallBack() {
                        @Override
                        public void onSucess(Object object) {
                            RechargeReportResponse mRechargeReportResponse = (RechargeReportResponse) object;
                            dataParse(mRechargeReportResponse);
                        }


                    });

        } else {

            UtilMethods.INSTANCE.NetworkError(this);
        }
    }


    public void dataParse(RechargeReportResponse transactions) {

        if (transactions != null && transactions.getMoveToWalletReport() != null) {
            transactionsObjects.clear();
            transactionsObjects.addAll(transactions.getMoveToWalletReport());
        }
        if (transactionsObjects.size() > 0 && transactionsObjects != null) {
            recycler_view.setVisibility(View.VISIBLE);
            mAdapter.notifyDataSetChanged();
        } else {

            UtilMethods.INSTANCE.Error(this, "Record not found between\n" + filterFromDate + " to " + filterToDate);
            recycler_view.setVisibility(View.GONE);
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_filter, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_filter) {
            filterShow();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void filterShow() {
        openFilter();

    }

    private void getOperator(int op_Type, ArrayList<OperatorList> mOperatorLists) {

        if (mOperatorLists != null && mOperatorLists.size() > 0) {

            ArrayList<String> modeArray = new ArrayList<>();
            modeArray.add(":: Select Mode ::");
            chooseModeMap.put(":: Select Mode ::", 0);
            for (OperatorList op : mOperatorLists) {
                if (op.getOpType() == op_Type && op.isActive()) {
                    modeArray.add(op.getName());
                    chooseModeMap.put(op.getName(), op.getOid());
                }
            }
            chooseModeArray = modeArray.toArray(new String[modeArray.size()]);
        } else {

            UtilMethods.INSTANCE.NumberList(this, null, object -> {
                operatorListResponse = (NumberListResponse) object;
                if (operatorListResponse != null && operatorListResponse.getData().getOperators() != null &&
                        operatorListResponse.getData().getOperators().size() > 0) {
                    getOperator(op_Type, operatorListResponse.getData().getOperators());
                }
            });

        }
    }


    public void openFilter() {
        final BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(this);

        View sheetView = getLayoutInflater().inflate(R.layout.ledger_filter, null);
        final LinearLayout walletTypeView = sheetView.findViewById(R.id.walletTypeView);
        /*final AppCompatTextView walletTypeChooser = sheetView.findViewById(R.id.walletTypeChooser);*/
        /*LinearLayout container = sheetView.findViewById(R.id.container);
        ImageView mobileNumberLeftIcon = sheetView.findViewById(R.id.mobileNumberLeftIcon);*/
        LinearLayout startDateView = sheetView.findViewById(R.id.startDateView);
        /*AppCompatTextView mobileNumTitle = sheetView.findViewById(R.id.mobileNumTitle);*/
        final AppCompatTextView startDate = sheetView.findViewById(R.id.startDate);

        LinearLayout endDateView = sheetView.findViewById(R.id.endDateView);
        final AppCompatTextView endDate = sheetView.findViewById(R.id.endDate);

        LinearLayout mobileView = sheetView.findViewById(R.id.mobileView);
        /*final AppCompatEditText mobileNoEt = sheetView.findViewById(R.id.mobileNoEt);
        mobileNoEt.setText(filterMobileNo);*/
        LinearLayout transactionIdView = sheetView.findViewById(R.id.transactionIdView);
        final AppCompatEditText transactionIdEt = sheetView.findViewById(R.id.transactionIdEt);

        LinearLayout childMobileView = sheetView.findViewById(R.id.childMobileView);
        final AppCompatEditText childMobileNoEt = sheetView.findViewById(R.id.childMobileNoEt);

        LinearLayout accountNoView = sheetView.findViewById(R.id.accountNoView);
/*        TextView accountNoTitle = sheetView.findViewById(R.id.accountNoTitle);
        final AppCompatEditText accountNoEt = sheetView.findViewById(R.id.accountNoEt);
        accountNoEt.setText(filterAccountNo);*/
        LinearLayout topChooserView = sheetView.findViewById(R.id.topChooserView);
        final AppCompatTextView topChooser = sheetView.findViewById(R.id.topChooser);
        AppCompatTextView statusTitleTv = sheetView.findViewById(R.id.statusTitleTv);


        /*TextView dateTypeTitle = sheetView.findViewById(R.id.dateTypeTitle);*/
        LinearLayout refundStatusChooserView = sheetView.findViewById(R.id.refundStatusChooserView);
        final AppCompatTextView refundStatusChooser = sheetView.findViewById(R.id.refundStatusChooser);

        LinearLayout dateTypeChooserView = sheetView.findViewById(R.id.dateTypeChooserView);
        /* final AppCompatTextView dateTypeChooser = sheetView.findViewById(R.id.dateTypeChooser);*/
        LinearLayout modeChooserView = sheetView.findViewById(R.id.modeChooserView);
        final AppCompatTextView modeChooser = sheetView.findViewById(R.id.modeChooser);
        LinearLayout criteriaChooserView = sheetView.findViewById(R.id.criteriaChooserView);
        /*final AppCompatTextView criteriaChooser = sheetView.findViewById(R.id.criteriaChooser);*/
        LinearLayout criteriaView = sheetView.findViewById(R.id.criteriaView);
        /*final TextView criteriaTitle = sheetView.findViewById(R.id.criteriaTitle);
        final AppCompatEditText criteriaEt = sheetView.findViewById(R.id.criteriaEt);*/
        Button filter = sheetView.findViewById(R.id.filter);

        mobileView.setVisibility(View.GONE);
        walletTypeView.setVisibility(View.GONE);
        accountNoView.setVisibility(View.GONE);
        dateTypeChooserView.setVisibility(View.GONE);
        criteriaChooserView.setVisibility(View.GONE);
        criteriaView.setVisibility(View.GONE);

        topChooserView.setVisibility(View.VISIBLE);
        modeChooserView.setVisibility(View.VISIBLE);
        childMobileView.setVisibility(View.VISIBLE);
        transactionIdView.setVisibility(View.VISIBLE);
        refundStatusChooserView.setVisibility(View.VISIBLE);

        startDate.setText(filterFromDate);
        endDate.setText(filterToDate);
        topChooser.setText(filterTopValue + "");
        childMobileNoEt.setText(filterChildMobileNo);
        transactionIdEt.setText(filterTransactionId);
        statusTitleTv.setText("Choose Status");
        refundStatusChooser.setHint("Choose Status");

        modeChooser.setText(filterModeValue != null && !filterModeValue.isEmpty() ? filterModeValue : chooseModeArray[0]);
        refundStatusChooser.setText(filterStatus != null && !filterStatus.isEmpty() ? filterStatus : disputeStatusArray[0]);


        topChooser.setOnClickListener(v -> {
            int selectedIndex = 0;
            if (topChooser.getText().toString().length() == 0) {
                selectedIndex = 0;
            } else {
                selectedIndex = Arrays.asList(ledgerChooseTopArray).indexOf(topChooser.getText().toString());
            }
            mCustomFilterDialog.showSingleChoiceAlert(ledgerChooseTopArray, selectedIndex, "Select Top", "Choose Top", new CustomFilterDialog.SingleChoiceDialogCallBack() {
                @Override
                public void onPositiveClick(int index) {
                    topChooser.setText(ledgerChooseTopArray[index]);
                }

                @Override
                public void onNegativeClick() {

                }
            });
        });


        modeChooser.setOnClickListener(v -> {
            int selectedIndex = 0;
            if (modeChooser.getText().toString().length() == 0) {
                selectedIndex = 0;
            } else {
                selectedIndex = Arrays.asList(chooseModeArray).indexOf(modeChooser.getText().toString());
            }
            mCustomFilterDialog.showSingleChoiceAlert(chooseModeArray, selectedIndex, "Select Mode", "Choose Mode", new CustomFilterDialog.SingleChoiceDialogCallBack() {
                @Override
                public void onPositiveClick(int index) {
                    modeChooser.setText(chooseModeArray[index]);
                }

                @Override
                public void onNegativeClick() {

                }
            });
        });

        refundStatusChooser.setOnClickListener(v -> {

            int selectedIndex = 0;
            if (refundStatusChooser.getText().toString().length() == 0) {
                selectedIndex = 0;
            } else {
                selectedIndex = Arrays.asList(disputeStatusArray).indexOf(refundStatusChooser.getText().toString());
            }
            mCustomFilterDialog.showSingleChoiceAlert(disputeStatusArray, selectedIndex, "Status", "Choose Status", new CustomFilterDialog.SingleChoiceDialogCallBack() {
                @Override
                public void onPositiveClick(int index) {
                    refundStatusChooser.setText(disputeStatusArray[index]);
                }

                @Override
                public void onNegativeClick() {

                }
            });


        });
        startDateView.setOnClickListener(v -> mCustomFilterDialog.setDCFromDate(startDate, endDate));
        endDateView.setOnClickListener(v -> mCustomFilterDialog.setDCToDate(startDate, endDate));


        filter.setOnClickListener(v -> {
            mBottomSheetDialog.dismiss();


            filterFromDate = startDate.getText().toString();
            filterToDate = endDate.getText().toString();
            filterChildMobileNo = childMobileNoEt.getText().toString();
            filterTransactionId = transactionIdEt.getText().toString();
            filterTopValue = topChooser.getText().toString().equalsIgnoreCase("ALL") ? 5000 : Integer.parseInt(topChooser.getText().toString());

            filterStatusId = !refundStatusChooser.getText().toString().isEmpty() ? (disputeStatusMap.get(refundStatusChooser.getText().toString())) : 0;
            filterStatus = refundStatusChooser.getText().toString();

            filterModeId = !modeChooser.getText().toString().isEmpty() ? (chooseModeMap.get(modeChooser.getText().toString())) : 0;
            filterModeValue = modeChooser.getText().toString();

            HitApi(true);
        });
        mBottomSheetDialog.setContentView(sheetView);

        BottomSheetBehavior
                .from(mBottomSheetDialog.findViewById(R.id.design_bottom_sheet))
                .setState(BottomSheetBehavior.STATE_EXPANDED);

        mBottomSheetDialog.show();

    }

}

package com.solution.brothergroup.AppUser.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.solution.brothergroup.Api.Object.AreaMaster;
import com.solution.brothergroup.Api.Object.LedgerObject;
import com.solution.brothergroup.Api.Response.AppGetAMResponse;
import com.solution.brothergroup.Api.Response.FosAccStmtAndCollReportResponse;
import com.solution.brothergroup.AppUser.Adapter.AccountStatementReportAdapter;
import com.solution.brothergroup.Authentication.dto.LoginResponse;
import com.solution.brothergroup.R;
import com.solution.brothergroup.Util.CustomFilterDialogUtils.CustomFilterDialog;
import com.solution.brothergroup.Util.UtilMethods;
import com.solution.brothergroup.usefull.CustomLoader;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class AccountStatementReportActivity extends AppCompatActivity {


    RecyclerView recycler_view;
    AccountStatementReportAdapter accountStatementReportAdapter;
    ArrayList<LedgerObject> transactionsObjects = new ArrayList<>();
    EditText search_all;
    CustomLoader loader;
    AreaMaster areaMaster;
    AppGetAMResponse appGetAMResponse;

    private Toolbar toolbar;
    private String filterFromDate = "", filterToDate = "";
    private int filterTopValue = 50;
    String filterMobile = "";
    private LoginResponse loginPrefResponse;
    String intentWalletName;

    int areaId;
    private int INTENT_SELECT_AREA = 4231;
    private String[] ledgerChooseTopArray = {"50", "100", "200", "500", "1000", "1500", "ALL"};
    private CustomFilterDialog mCustomFilterDialog;
    private String filterArea = "";
    private TextView refundStatusChooser;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_statement_report);

        loginPrefResponse = new Gson().fromJson(UtilMethods.INSTANCE.getLoginPref(this), LoginResponse.class);
        mCustomFilterDialog = new CustomFilterDialog(this);
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);
        filterMobile = loginPrefResponse.getData().getMobileNo();
        findViews();
        clickView();


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            areaId = extras.getInt("areaID");

        }


        HitApi();

    }


    void findViews() {
        search_all = findViewById(R.id.search_all);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setTitle("Account Statement");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_icon);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        recycler_view = findViewById(R.id.recycler_view);
        accountStatementReportAdapter = new AccountStatementReportAdapter(transactionsObjects, this);

        recycler_view.setLayoutManager(new LinearLayoutManager(this.getApplicationContext()));
        recycler_view.setAdapter(accountStatementReportAdapter);
        final Calendar myCalendar = Calendar.getInstance();
        String myFormat = "dd-MMM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        String today = sdf.format(myCalendar.getTime());
        filterFromDate = today;
        filterToDate = today;
    }


    void clickView() {
        findViewById(R.id.clearIcon).setOnClickListener(v -> search_all.setText(""));


        search_all.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence newText, int start, int before, int count) {
                accountStatementReportAdapter.getFilter().filter(newText.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    public void HitApi() {
        if (UtilMethods.INSTANCE.isNetworkAvialable(this)) {

            loader.show();
            loader.setCancelable(false);
            loader.setCanceledOnTouchOutside(false);

            UtilMethods.INSTANCE.AccStmtRport(this, filterMobile, filterTopValue + "", filterFromDate, filterToDate, "1", loader, loginPrefResponse
                    , areaId, new UtilMethods.ApiCallBack() {
                        @Override
                        public void onSucess(Object object) {
                            FosAccStmtAndCollReportResponse fosAccStmtAndCollReportResponse = (FosAccStmtAndCollReportResponse) object;
                            dataParse(fosAccStmtAndCollReportResponse);
                        }


                    });
        } else {

            UtilMethods.INSTANCE.NetworkError(this);
        }
    }


    public void dataParse(FosAccStmtAndCollReportResponse transactions) {

        if (transactions != null && transactions.getAccountStatementSummary() != null) {
            transactionsObjects.clear();
            transactionsObjects.addAll(transactions.getAccountStatementSummary());
        }
        if (transactionsObjects.size() > 0 && transactionsObjects != null) {
            recycler_view.setVisibility(View.VISIBLE);
            accountStatementReportAdapter.notifyDataSetChanged();
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

            openReportFosFilter();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    public void openReportFosFilter() {
        final BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(this);

        View sheetView = getLayoutInflater().inflate(R.layout.ledger_filter, null);
        LinearLayout childMobileView = sheetView.findViewById(R.id.childMobileView);
        childMobileView.setVisibility(View.GONE);
        EditText mobileNoEt = sheetView.findViewById(R.id.mobileNoEt);
        mobileNoEt.setText(filterMobile + "");

        LinearLayout mobileView = sheetView.findViewById(R.id.mobileView);
        mobileView.setVisibility(View.VISIBLE);

        LinearLayout accountNoView = sheetView.findViewById(R.id.accountNoView);
        accountNoView.setVisibility(View.GONE);

        LinearLayout transactionIdView = sheetView.findViewById(R.id.transactionIdView);
        transactionIdView.setVisibility(View.GONE);

        LinearLayout walletTypeView = sheetView.findViewById(R.id.walletTypeView);
        walletTypeView.setVisibility(View.GONE);


        LinearLayout dateTypeChooserView = sheetView.findViewById(R.id.dateTypeChooserView);
        dateTypeChooserView.setVisibility(View.GONE);

        LinearLayout modeChooserView = sheetView.findViewById(R.id.modeChooserView);
        modeChooserView.setVisibility(View.GONE);

        LinearLayout criteriaView = sheetView.findViewById(R.id.criteriaView);
        criteriaView.setVisibility(View.GONE);

        LinearLayout criteriaChooserView = sheetView.findViewById(R.id.criteriaChooserView);
        criteriaChooserView.setVisibility(View.GONE);


        LinearLayout startDateView = sheetView.findViewById(R.id.startDateView);
        final AppCompatTextView startDate = sheetView.findViewById(R.id.startDate);
        startDate.setText(filterFromDate);
        LinearLayout endDateView = sheetView.findViewById(R.id.endDateView);
        final AppCompatTextView endDate = sheetView.findViewById(R.id.endDate);
        endDate.setText(filterToDate);


        LinearLayout topChooserView = sheetView.findViewById(R.id.topChooserView);
        final AppCompatTextView topChooser = sheetView.findViewById(R.id.topChooser);
        topChooser.setText(filterTopValue + "");


        Button filter = sheetView.findViewById(R.id.filter);

        LinearLayout refundStatusChooserView = sheetView.findViewById(R.id.refundStatusChooserView);
        TextView statusTitleTv = sheetView.findViewById(R.id.statusTitleTv);
        refundStatusChooser = sheetView.findViewById(R.id.refundStatusChooser);


       /* statusTitleTv.setText("Select Area");
        refundStatusChooser.setHint("Select Area");*/
        refundStatusChooserView.setVisibility(View.GONE);
        /*refundStatusChooser.setText(filterArea + "");
        refundStatusChooser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccountStatementReportActivity.this, com.solution.s.recharge.AppUser.Activity.FosAreaReportActivity.class);
                startActivityForResult(intent, INTENT_SELECT_AREA);
            }
        });*/
        topChooser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
            }
        });
        startDateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCustomFilterDialog.setDCFromDate(startDate, endDate);
            }
        });
        endDateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCustomFilterDialog.setDCToDate(startDate, endDate);
            }
        });


        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialog.dismiss();
                filterFromDate = startDate.getText().toString();
                filterToDate = endDate.getText().toString();
                filterMobile = mobileNoEt.getText().toString();
                filterTopValue = topChooser.getText().toString().equalsIgnoreCase("ALL") ? 5000 : Integer.parseInt(topChooser.getText().toString());
                HitApi();

            }
        });
        mBottomSheetDialog.setContentView(sheetView);
        mBottomSheetDialog.show();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 4231 && resultCode == RESULT_OK) {
            areaId = data.getIntExtra("areaID", 0);
            filterArea = data.getStringExtra("area");

            if (refundStatusChooser != null) {
                refundStatusChooser.setText(filterArea + "");
            }
        }
    }
}

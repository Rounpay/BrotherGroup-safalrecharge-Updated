package com.solution.brothergroup.AppUser.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.solution.brothergroup.Adapter.BankListScreenAdapter;
import com.solution.brothergroup.Authentication.dto.LoginResponse;
import com.solution.brothergroup.R;
import com.solution.brothergroup.Util.UtilMethods;
import com.solution.brothergroup.usefull.CustomLoader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.view.WindowInsetsControllerCompat;

public class FosCollectionActivity extends AppCompatActivity {
    private AppCompatTextView titleTv;
    private LinearLayout nestedff;
    private LinearLayout changetTypeView;
    private LinearLayout collectionTypeView;
    private AppCompatTextView cashCollectionTv;
    private AppCompatTextView bankCollectionTv;
    private LinearLayout bankView;
    private RelativeLayout bankSelect_ll;
    private TextView bankTv;
    private ImageView iv_arrow;
    private AppCompatTextView amountTitleTv;
    private RelativeLayout amountView;
    private AppCompatEditText amountEt;
    private AppCompatTextView remarksTv;
    private AppCompatEditText remarksEt;
    private AppCompatTextView bankUtr_tv;
    private AppCompatEditText bankUtrEt;
    private LinearLayout bottomBtnView;
    private AppCompatTextView cancelBtn;
    private AppCompatTextView fundCollectBtn;
    private AppCompatImageView closeIv;


    String bankId;
    private int INTENT_BANK = 543;
    private int INTENT_SELECT_BANK = 4343;
    BankListScreenAdapter bankListScreenAdapter;
    private CustomLoader loader;
    private LoginResponse mLoginDataResponse;
    String remark, amount;
    String userName, Uid, userListName;
    private LoginResponse loginPrefResponse;
    // String Uid;
    String mobileNumber = " ";
    String bankName;
    int userListid;
    boolean isBank;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowInsetsControllerCompat controller = new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        controller.setAppearanceLightStatusBars(true);
        controller.setAppearanceLightNavigationBars(true);
        setContentView(R.layout.activity_distributor_collection_from_fos);
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);
        mLoginDataResponse = new Gson().fromJson(UtilMethods.INSTANCE.getLoginPref(this), LoginResponse.class);
        Bundle bundle1 = getIntent().getExtras();
        userListName = bundle1.getString("outletName");
        userListid = bundle1.getInt("id");
        mobileNumber = bundle1.getString("mobile");

        titleTv = (AppCompatTextView) findViewById(R.id.titleTv);
        nestedff = (LinearLayout) findViewById(R.id.nestedff);
        changetTypeView = (LinearLayout) findViewById(R.id.changetTypeView);
        collectionTypeView = (LinearLayout) findViewById(R.id.collectionTypeView);
        cashCollectionTv = (AppCompatTextView) findViewById(R.id.cashCollectionTv);
        bankCollectionTv = (AppCompatTextView) findViewById(R.id.bankCollectionTv);
        bankView = (LinearLayout) findViewById(R.id.bankView);
        bankSelect_ll = (RelativeLayout) findViewById(R.id.bankSelect_ll);
        bankTv = (TextView) findViewById(R.id.bankTv);
        iv_arrow = (ImageView) findViewById(R.id.iv_arrow);
        amountTitleTv = (AppCompatTextView) findViewById(R.id.amountTitleTv);
        amountView = (RelativeLayout) findViewById(R.id.amountView);
        amountEt = (AppCompatEditText) findViewById(R.id.amountEt);
        remarksTv = (AppCompatTextView) findViewById(R.id.remarksTv);
        remarksEt = (AppCompatEditText) findViewById(R.id.remarksEt);
        bankUtr_tv = (AppCompatTextView) findViewById(R.id.bankUtr_tv);
        bankUtrEt = (AppCompatEditText) findViewById(R.id.bankUtrEt);
        bottomBtnView = (LinearLayout) findViewById(R.id.bottomBtnView);
        cancelBtn = (AppCompatTextView) findViewById(R.id.cancelBtn);
        fundCollectBtn = (AppCompatTextView) findViewById(R.id.fundCollectBtn);
        closeIv = (AppCompatImageView) findViewById(R.id.closeIv);

        closeIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        cashCollectionTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bankUtr_tv.setVisibility(View.GONE);
                bankUtrEt.setVisibility(View.GONE);
                bankView.setVisibility(View.GONE);
                cashCollectionTv.setBackgroundResource(R.drawable.rounded_light_green);
                bankCollectionTv.setBackgroundResource(0);
                bankCollectionTv.setTextColor(getResources().getColor(R.color.lightDarkGreen));
                cashCollectionTv.setTextColor(getResources().getColor(R.color.white));


            }
        });

        bankCollectionTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bankUtr_tv.setVisibility(View.VISIBLE);
                bankUtrEt.setVisibility(View.VISIBLE);
                bankView.setVisibility(View.VISIBLE);
                bankCollectionTv.setBackgroundResource(R.drawable.rounded_light_green);
                cashCollectionTv.setBackgroundResource(0);
                cashCollectionTv.setTextColor(getResources().getColor(R.color.lightDarkGreen));
                bankCollectionTv.setTextColor(getResources().getColor(R.color.white));
            }
        });


        bankSelect_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent bankIntent = new Intent(FosCollectionActivity.this, FosCollectionBankListActivity.class);
                bankIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivityForResult(bankIntent, INTENT_SELECT_BANK);
            }
        });


        fundCollectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (bankView.getVisibility() == View.VISIBLE && bankTv.getText().toString().isEmpty()) {
                    bankTv.setError("This Field Is Empty");
                    bankTv.requestFocus();
                } else if (bankView.getVisibility() == View.VISIBLE && bankUtrEt.getText().toString().isEmpty()) {
                    bankUtrEt.setError("This Field Is Empty");
                    bankUtrEt.requestFocus();
                } else if (amountEt.getText().toString().isEmpty()) {
                    amountEt.setError("This Field Is Empty");
                    amountEt.requestFocus();
                } else if (remarksEt.getText().toString().isEmpty()) {
                    remarksEt.setError("Fill valid Remark");
                    remarksEt.requestFocus();
                } else if (!remarksEt.getText().toString().matches("[a-zA-Z.? ]*")) {
                    remarksEt.setError("Fill valid Remark");
                    remarksEt.requestFocus();
                } else {

                    UtilMethods.INSTANCE.ASPayCollect(FosCollectionActivity.this, userListid,
                            remarksEt.getText().toString(), amountEt.getText().toString(),
                            userListName, loader, isBank ? "Bank" : "Cash", bankUtrEt.getText().toString(), bankTv.getText().toString(),
                            mLoginDataResponse,
                            new UtilMethods.ApiCallBack() {
                                @Override
                                public void onSucess(Object object) {

                                    setResult(RESULT_OK);
                                }
                            });


                }

            }


        });


        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == INTENT_SELECT_BANK && resultCode == RESULT_OK) {
            bankId = data.getExtras().getString("bankId");
            bankName = data.getExtras().getString("bankName");
            bankTv.setText("" + bankName);

        }
    }


}

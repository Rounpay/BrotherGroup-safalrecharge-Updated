package com.solution.brothergroup.Aeps.UI;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.solution.brothergroup.Authentication.dto.LoginResponse;
import com.solution.brothergroup.R;
import com.solution.brothergroup.Util.UtilMethods;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.WindowInsetsControllerCompat;

public class AepsCashWithDrawlActivity extends AppCompatActivity {

    String txnTime,rnpLiveId,transId,rnpTransactionId,bankMsg,customerNum,bankName,bankRrn,aadharNum,deviceName,uidlcode,transType,bcLoc,bcCode,bcName,stanNo,beneficaryName,accountNum;
    double transAmount,balAmount;
    int transactionStatus;
    TextView status_TV;
    ImageView status_imgIV;
    RelativeLayout status_res_RL,beneNameRL,accountnameRL;
    private AppCompatTextView balAmt, TxnAmt,txnDate,rrpTransactionId,LiveId,transactionId,tvBankName,tvBankMsg,tvBankRrn,tvDeviceName,tvAadharNum,tvCustomerNum,tvCompanyDetails,shareBtn,beneNametv,accountNotv;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowInsetsControllerCompat controller = new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        controller.setAppearanceLightStatusBars(true);
        controller.setAppearanceLightNavigationBars(true);
        setContentView(R.layout.activity_aeps_cash_withdrawl_activity);
        toolbar=findViewById(R.id.toolbar);
        toolbar.setTitle("AEPS Transaction Receipt");
        balAmt=findViewById(R.id.balAmt);
        TxnAmt=findViewById(R.id.TxnAmt);
        txnDate=findViewById(R.id.txnDate);
        status_res_RL = findViewById(R.id.status_res_RL);
        accountnameRL = findViewById(R.id.accountNumberRl);
        beneNameRL = findViewById(R.id.beneNameRl);
        status_imgIV = findViewById(R.id.status_imgIV);
        status_TV = findViewById(R.id.status_TV);
        rrpTransactionId=findViewById(R.id.rnpTransactionId);
        LiveId=findViewById(R.id.rnpLiveId);
        transactionId=findViewById(R.id.TransactionId);
        tvBankName=findViewById(R.id.bankName);
        tvBankMsg=findViewById(R.id.bankMsg);
        tvBankRrn=findViewById(R.id.bankRrn);
        tvDeviceName=findViewById(R.id.deviceName);
        tvAadharNum=findViewById(R.id.aadharNum);
        tvCustomerNum=findViewById(R.id.customerNum);
        tvCompanyDetails = findViewById(R.id.tv_CompanyDetails);
        shareBtn = findViewById(R.id.tv_share);
        beneNametv = findViewById(R.id.beneNametv);
        accountNotv = findViewById(R.id.accountNumbertv);
        setOutletDetail();
        if(getIntent()!=null)
        {
            txnTime=getIntent().getStringExtra("time");
            rnpLiveId=getIntent().getStringExtra("rnpLiveId");
            rnpTransactionId=getIntent().getStringExtra("rnpTransactionId");
            transId=getIntent().getStringExtra("transId");
            transAmount=getIntent().getDoubleExtra("transAmount",0.0);
            balAmount=getIntent().getDoubleExtra("balAmount",0.0);
            bankMsg=getIntent().getStringExtra("bankMsg");
            customerNum=getIntent().getStringExtra("customerNum");
            bankName=getIntent().getStringExtra("bankName");
            bankRrn=getIntent().getStringExtra("bankRrn");
            beneficaryName=getIntent().getStringExtra("bene_name");
            accountNum=getIntent().getStringExtra("account_number");
            aadharNum=getIntent().getStringExtra("aadharNum");
            deviceName=getIntent().getStringExtra("deviceName");
            stanNo=getIntent().getStringExtra("stanNo");
            bcName=getIntent().getStringExtra("bcName");
            bcCode=getIntent().getStringExtra("bcCode");
            bcLoc=getIntent().getStringExtra("bcLoc");
            transType=getIntent().getStringExtra("transType");
            uidlcode=getIntent().getStringExtra("uidlcode");
            transactionStatus=getIntent().getIntExtra("txn_status",0);
        }


        if (transactionStatus == 1) {
            status_TV.setText("Pending");
            status_imgIV.setImageResource(R.drawable.ic_pending_black_24dp);
            status_res_RL.setVisibility(View.VISIBLE);

        } else if (transactionStatus == 2) {
            status_TV.setText("Success");
            status_imgIV.setImageResource(R.drawable.success);
            status_res_RL.setVisibility(View.VISIBLE);

        } else if (transactionStatus == 3) {
            status_TV.setText("Failed");
            status_imgIV.setImageResource(R.drawable.failure_icon);
            status_res_RL.setVisibility(View.VISIBLE);
        }else {
            status_res_RL.setVisibility(View.GONE);

        }



        if(beneficaryName!=null && !beneficaryName.isEmpty())
            beneNametv.setText(beneficaryName);
        else
            beneNameRL.setVisibility(View.GONE);


        if(accountNum!=null && !accountNum.isEmpty())
            accountNotv.setText(accountNum);
        else
            accountnameRL.setVisibility(View.GONE);



        if(txnTime!=null && !txnTime.isEmpty())
            txnDate.setText(txnTime);
        else
            txnDate.setText("NA");

        if(rnpLiveId!=null && !rnpLiveId.isEmpty())
            LiveId.setText(rnpLiveId);
        else
            LiveId.setText("NA");


        if(rnpTransactionId!=null && !rnpTransactionId.isEmpty())
            rrpTransactionId.setText(rnpTransactionId);
        else
            rrpTransactionId.setText("NA");


        if(transId!=null && !transId.isEmpty())
            transactionId.setText(transId);
        else
            transactionId.setText("NA");

        if(bankName!=null && !bankName.isEmpty())
            tvBankName.setText(bankName);
        else
            tvBankName.setText("NA");

        if(bankMsg!=null && !bankMsg.isEmpty()){
            tvBankMsg.setText(bankMsg+"");}
        else {
            findViewById(R.id.bankMsgView).setVisibility(View.GONE);
            //tvBankMsg.setText("NA");
        }

        if(bankRrn!=null && !bankRrn.isEmpty())
            tvBankRrn.setText(bankRrn+"");
        else
            tvBankRrn.setText("NA");

        if(customerNum!=null && !customerNum.isEmpty()){
            tvCustomerNum.setText(customerNum+"");}
        else {
            findViewById(R.id.customerNumView).setVisibility(View.GONE);
           // tvCustomerNum.setText("NA");
        }

        if(aadharNum!=null && !aadharNum.isEmpty())
            tvAadharNum.setText(aadharNum+"");
        else
            tvAadharNum.setText("NA");

        if(deviceName!=null && !deviceName.isEmpty())
            tvDeviceName.setText(deviceName+"");
        else
            tvDeviceName.setText("NA");



        balAmt.setText("Balance Amount\n"+ getResources().getString(R.string.rupiya) + balAmount);
        TxnAmt.setText("Transaction Amount\n"+ getResources().getString(R.string.rupiya) + transAmount);

        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareit();
            }
        });

    }

    void setOutletDetail() {
        LoginResponse LoginDataResponse = new Gson().fromJson(UtilMethods.INSTANCE.getLoginPref(this), LoginResponse.class);

        String outletDetailStr = "";
        if (LoginDataResponse.getData().getName() != null && !LoginDataResponse.getData().getName().isEmpty()) {
            outletDetailStr = outletDetailStr + "Name : " + LoginDataResponse.getData().getName();
        }
        if (LoginDataResponse.getData().getOutletName() != null && !LoginDataResponse.getData().getOutletName().isEmpty()) {
            outletDetailStr = outletDetailStr + " | Shop Name : " + LoginDataResponse.getData().getOutletName();
        }
        if (LoginDataResponse.getData().getMobileNo() != null && !LoginDataResponse.getData().getMobileNo().isEmpty()) {
            outletDetailStr = outletDetailStr + " | Contact No : " + LoginDataResponse.getData().getMobileNo();
        }
        if (LoginDataResponse.getData().getEmailID() != null && !LoginDataResponse.getData().getEmailID().isEmpty()) {
            outletDetailStr = outletDetailStr + " | Email : " + LoginDataResponse.getData().getEmailID();
        }
        if (LoginDataResponse.getData().getAddress() != null && !LoginDataResponse.getData().getAddress().isEmpty()) {
            outletDetailStr = outletDetailStr + " | Address : " + LoginDataResponse.getData().getAddress();
        }
        tvCompanyDetails.setText(outletDetailStr);

    }

    public void shareit() {
        File picFile = null;
        Bitmap myBitmap = null;

        View v1 = getWindow().getDecorView().getRootView();
        v1.setDrawingCacheEnabled(true);
        myBitmap = Bitmap.createBitmap(v1.getDrawingCache());
        v1.setDrawingCacheEnabled(false);
        saveBitmap(myBitmap);

    }

    public void saveBitmap(Bitmap bitmap) {
        // Create a media file name
        Log.v("first", "first");
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());

        String filePath = Environment.getExternalStorageDirectory().toString()
                + "/" + timeStamp + ".jpg";
        File imagePath = new File(filePath);
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(imagePath);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
            Log.v("first", "second");
            sendMail(filePath);
        } catch (FileNotFoundException e) {
            // Log.e("GREC", e.getMessage(), e);
        } catch (IOException e) {
            // Log.e("GREC", e.getMessage(), e);
        }
    }

    public void sendMail(String path) {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT,
                "Recharge Receipt");
        emailIntent.putExtra(Intent.EXTRA_TEXT,
                "Receipt");
        emailIntent.setType("image/png");
        Uri myUri = Uri.parse("file://" + path);
        emailIntent.putExtra(Intent.EXTRA_STREAM, myUri);
        startActivity(Intent.createChooser(emailIntent, "Share via..."));
    }
}
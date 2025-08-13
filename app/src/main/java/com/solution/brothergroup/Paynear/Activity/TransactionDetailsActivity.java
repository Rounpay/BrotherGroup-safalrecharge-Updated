package com.solution.brothergroup.Paynear.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.pnsol.sdk.interfaces.PaymentTransactionConstants;
import com.pnsol.sdk.payment.PaymentInitialization;
import com.pnsol.sdk.vo.response.ICCTransactionResponse;
import com.pnsol.sdk.vo.response.TransactionStatusResponse;
import com.solution.brothergroup.R;
import com.solution.brothergroup.Util.UtilMethods;
import com.solution.brothergroup.usefull.CustomLoader;


import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.WindowInsetsControllerCompat;

/**
 * @author pradeep.arige
 * updated by vasanthi.k  on 08/12/2018
 */

public class TransactionDetailsActivity extends AppCompatActivity implements
        PaymentTransactionConstants {

    private Button details;
    private ICCTransactionResponse iccTransactionResponse;
    private RadioGroup radiodevice, radioComm;
    // private TextView response_txn;
    private String paymentMode;
    ImageView statusIconIv;
    TextView statusAmountTv, statusRRNTv, statusTv, statusMsgTv;
    private CustomLoader loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowInsetsControllerCompat controller = new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        controller.setAppearanceLightStatusBars(true);
        controller.setAppearanceLightNavigationBars(true);
        setContentView(R.layout.activity_paynear_transaction_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Transaction Details");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_icon);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);
        loader.setCancelable(false);
        radiodevice = (RadioGroup) findViewById(R.id.radiodevice);
        details = (Button) findViewById(R.id.details);
        //response_txn = (TextView) findViewById(R.id.response_txn);
        statusIconIv = findViewById(R.id.statusIcon);
        statusAmountTv = findViewById(R.id.statusAmt);
        statusRRNTv = findViewById(R.id.statusRRN);
        statusTv = findViewById(R.id.status);
        statusMsgTv = findViewById(R.id.statusMsg);
        iccTransactionResponse = (ICCTransactionResponse) getIntent().getSerializableExtra("vo");

       /* String data = "";
        try {

            data = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(iccTransactionResponse);
        } catch (JsonGenerationException e1) {
            e1.printStackTrace();
        } catch (JsonMappingException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }*/
        setResponseData(iccTransactionResponse);
        //  response_txn.setText(data);
        details.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    loader.show();
                    PaymentInitialization initialization = new PaymentInitialization(TransactionDetailsActivity.this);
                    //initialization.initiateTransactionDetails(handler, iccTransactionResponse.getReferenceNumber(),null,"","");
                    initialization.initiateTransactionDetails(handler, iccTransactionResponse.getReferenceNumber(), null, paymentMode, null, null);
                } catch (RuntimeException e) {
                    e.printStackTrace();
                }
            }
        });
        radiodevice.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.sale) {
                    paymentMode = SALE;
                } else if (checkedId == R.id.cashpos) {
                    paymentMode = CASH_AT_POS;
                } else if (checkedId == R.id.balanceEnquiry) {

                    paymentMode = BALANCE_ENQUIRY;
                } else if (checkedId == R.id.matm) {

                    paymentMode = MICRO_ATM;
                }
            }
        });

    }

    void setResponseData(ICCTransactionResponse data) {
        if (data != null) {
            if (data.getTransactionStatus() != null && data.getTransactionStatus().toLowerCase().contains("approved")) {
                statusIconIv.setImageResource(R.drawable.ic_check_mark_outline);
                statusAmountTv.setText(UtilMethods.INSTANCE.formatedAmountWithRupees(data.getAmount() + ""));
                if (data.getRrn() != null && !data.getRrn().isEmpty()) {
                    statusRRNTv.setVisibility(View.VISIBLE);
                    statusRRNTv.setText("RRN Number : " + data.getRrn() + "");
                } else {
                    statusRRNTv.setVisibility(View.GONE);
                }
                if (data.getTransactionStatus() != null && !data.getTransactionStatus().isEmpty()) {
                    statusTv.setVisibility(View.VISIBLE);
                    statusTv.setText(data.getTransactionStatus() + "");
                    statusTv.setTextColor(getResources().getColor(R.color.green));
                } else {
                    statusTv.setVisibility(View.GONE);
                }
                statusMsgTv.setVisibility(View.GONE);
            } else if (data.getTransactionStatus() != null && data.getTransactionStatus().toLowerCase().contains("pending")) {
                statusIconIv.setImageResource(R.drawable.ic_pending_outline);
                statusAmountTv.setText(UtilMethods.INSTANCE.formatedAmountWithRupees(data.getAmount() + ""));
                if (data.getRrn() != null && !data.getRrn().isEmpty()) {
                    statusRRNTv.setVisibility(View.VISIBLE);
                    statusRRNTv.setText("RRN Number : " + data.getRrn() + "");
                } else {
                    statusRRNTv.setVisibility(View.GONE);
                }
                if (data.getTransactionStatus() != null && !data.getTransactionStatus().isEmpty()) {
                    statusTv.setVisibility(View.VISIBLE);
                    statusTv.setText(data.getTransactionStatus() + "");
                    statusTv.setTextColor(getResources().getColor(R.color.color_orange));
                } else {
                    statusTv.setVisibility(View.GONE);
                }
                statusMsgTv.setVisibility(View.GONE);
            } else {
                statusIconIv.setImageResource(R.drawable.ic_cross_mark_outline);
                statusAmountTv.setText(UtilMethods.INSTANCE.formatedAmountWithRupees(data.getAmount() + ""));
                if (data.getRrn() != null && !data.getRrn().isEmpty()) {
                    statusRRNTv.setVisibility(View.VISIBLE);
                    statusRRNTv.setText("RRN Number : " + data.getRrn() + "");
                } else {
                    statusRRNTv.setVisibility(View.GONE);
                }

                if (data.getTransactionStatus() != null && !data.getTransactionStatus().isEmpty()) {
                    statusTv.setVisibility(View.VISIBLE);
                    statusTv.setText(data.getTransactionStatus() + "");
                    statusTv.setTextColor(getResources().getColor(R.color.color_red));
                } else {
                    statusTv.setVisibility(View.GONE);
                }

                if (data.getResponseMessage() != null && !data.getResponseMessage().isEmpty()) {
                    statusMsgTv.setVisibility(View.VISIBLE);
                    statusMsgTv.setText(data.getResponseMessage() + "");
                } else {
                    statusMsgTv.setVisibility(View.VISIBLE);
                }

            }
        } else {
            statusTv.setText("Data not Found");
            statusTv.setTextColor(getResources().getColor(R.color.color_red));
            statusRRNTv.setVisibility(View.GONE);
            statusAmountTv.setVisibility(View.GONE);
            statusIconIv.setImageResource(R.drawable.ic_cross_mark_outline);
            statusMsgTv.setVisibility(View.GONE);
        }
    }

    // The Handler that gets information back from the PaymentProcessThread
    @SuppressLint("HandlerLeak")
    private final Handler handler = new Handler() {

        public void handleMessage(android.os.Message msg) {
            loader.dismiss();
            if (msg.what == SUCCESS) {
                //
                List<TransactionStatusResponse> transactionStatusResponse = (List<TransactionStatusResponse>) msg.obj;

                /*Intent i = new Intent(TransactionDetailsActivity.this, PaymentDetailsActivity.class);*/
                Intent i = new Intent(TransactionDetailsActivity.this, PaymentDetailsSlipActivity.class);
                i.putExtra("txnResponse", transactionStatusResponse.get(0));
                i.putExtra("referenceNumber", iccTransactionResponse.getReferenceNumber());
                i.putExtra("rrn", iccTransactionResponse.getRrn());
                startActivity(i);

            } else if (msg.what == FAIL) {
                Toast.makeText(TransactionDetailsActivity.this, (String) msg.obj,
                        Toast.LENGTH_LONG).show();
                //finish();
            } else if (msg.what == ERROR_MESSAGE) {
                Toast.makeText(TransactionDetailsActivity.this, (String) msg.obj,
                        Toast.LENGTH_LONG).show();
            } else {
                if (msg.obj != null) {
                    Toast.makeText(TransactionDetailsActivity.this, (String) msg.obj,
                            Toast.LENGTH_LONG).show();
                }
            }

        }

        ;
    };

}

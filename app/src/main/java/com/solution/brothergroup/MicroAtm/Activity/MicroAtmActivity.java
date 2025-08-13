package com.solution.brothergroup.MicroAtm.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.fingpay.microatmsdk.MATMHistoryScreen;
import com.fingpay.microatmsdk.MicroAtmLoginScreen;
import com.fingpay.microatmsdk.utils.Constants;
import com.google.gson.Gson;
import com.solution.brothergroup.Aeps.dto.SdkDetail;
import com.solution.brothergroup.Authentication.dto.LoginResponse;
import com.solution.brothergroup.FingPayAEPS.AEPSWebConnectivity;
import com.solution.brothergroup.FingPayAEPS.EKYCProcessActivity;
import com.solution.brothergroup.FingPayAEPS.dto.InitiateMiniBankResponse;
import com.solution.brothergroup.R;
import com.solution.brothergroup.Util.DropdownDialog.DropDownDialog;
import com.solution.brothergroup.Util.GetLocation;
import com.solution.brothergroup.Util.UtilMethods;
import com.solution.brothergroup.usefull.CustomLoader;

import java.util.ArrayList;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsControllerCompat;


public class MicroAtmActivity extends AppCompatActivity {

    private CustomLoader loader;
    private EditText amountEt, remarksEt;
    private Button fingPayBtn, historyBtn;
    View amountView, remarkView;

    private static final int CODE = 123;

    public String superMerchentId = "266";

    private String merchantId, password, mobile;
    private String intentSdkType, intentOid;
    private LoginResponse LoginDataResponse;
    SdkDetail intentSdkDetail;
    private GetLocation mGetLocation;
    private String tid;
    private AlertDialog alertDialogReport;
    View txnTypeChooserView;
    TextView txnType;
    private DropDownDialog mDropDownDialog;
    private int selectedTypePos;
    private ArrayList<String> arrayListType = new ArrayList<>();
    private View btn_kyc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowInsetsControllerCompat controller = new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        controller.setAppearanceLightStatusBars(true);
        controller.setAppearanceLightNavigationBars(true);

        setContentView(R.layout.activity_micro_atm);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Mini ATM");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_icon);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        mDropDownDialog = new DropDownDialog(this);
        intentSdkType = getIntent().getStringExtra("SDKType");
        intentOid = getIntent().getStringExtra("OID");
        intentSdkDetail = (SdkDetail) getIntent().getSerializableExtra("SDKDetails");
        if (intentSdkDetail != null) {
            merchantId = intentSdkDetail.getApiOutletID();
            password = intentSdkDetail.getApiOutletPassword();
            mobile = intentSdkDetail.getApiOutletMob();
            superMerchentId = intentSdkDetail.getApiPartnerID();
        }
        String LoginResponse = UtilMethods.INSTANCE.getLoginPref(this);
        LoginDataResponse = new Gson().fromJson(LoginResponse, LoginResponse.class);

        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);


        amountEt = findViewById(R.id.et_amount);
        remarksEt = findViewById(R.id.et_remarks);
        txnTypeChooserView = findViewById(R.id.txnTypeChooserView);
        txnType = findViewById(R.id.txnType);
        btn_kyc = findViewById(R.id.btn_kyc);
        amountView = findViewById(R.id.amountView);
        remarkView = findViewById(R.id.remarkView);
        fingPayBtn = findViewById(R.id.btn_fingpay);
        fingPayBtn.setOnClickListener(v -> launch());

        historyBtn = findViewById(R.id.btn_history);
        historyBtn.setOnClickListener(v -> history());

        mGetLocation = new GetLocation(this, loader);
        if (UtilMethods.INSTANCE.getLattitude == 0 || UtilMethods.INSTANCE.getLongitude == 0) {
            mGetLocation.startLocationUpdatesIfSettingEnable((lattitude, longitude) -> {
                UtilMethods.INSTANCE.getLattitude = lattitude;
                UtilMethods.INSTANCE.getLongitude = longitude;
            });
        }
        arrayListType.add(getString(R.string.cash_withdrawal));
        arrayListType.add(getString(R.string.cash_deposit));
        arrayListType.add(getString(R.string.balance_enq));
        arrayListType.add(getString(R.string.mini_statement));
        /*arrayListType.add(getString(R.string.card_activation));
        arrayListType.add(getString(R.string.pin_reset));
        arrayListType.add(getString(R.string.change_pin));*/


        txnTypeChooserView.setOnClickListener(v ->
                mDropDownDialog.showDropDownPopup(v, selectedTypePos, arrayListType, (clickPosition, value, object) -> {

                            if (selectedTypePos != clickPosition) {
                                txnType.setText(value + "");
                                selectedTypePos = clickPosition;
                                if(clickPosition==0 ||clickPosition==1){
                                    amountView.setVisibility(View.VISIBLE);
                                    remarkView.setVisibility(View.VISIBLE);
                                } else {
                                    amountView.setVisibility(View.GONE);
                                    remarkView.setVisibility(View.GONE);
                                }
                            }
                        }
                ));
        btn_kyc.setOnClickListener(v -> {

            startActivity(new Intent(this, EKYCProcessActivity.class)
                    .putExtra("SDKDetail", intentSdkDetail)
                    .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
        });

    }

    void launch() {

        if (selectedTypePos == -1) {
            Toast.makeText(this, "Please select any type", Toast.LENGTH_SHORT).show();
            return;
        } else if (selectedTypePos==0 && amountEt.getText().toString().isEmpty()) {
            amountEt.setError("Please enter amount.");
            amountEt.requestFocus();
            return;

        } else if (selectedTypePos==1 && amountEt.getText().toString().isEmpty()) {
            amountEt.setError("Please enter amount.");
            amountEt.requestFocus();
            return;

        }
        if (selectedTypePos==0 || selectedTypePos==1) {
            InitiateMiniBank(this, loader);
        } else {
            String tid = "fingpay" + String.valueOf(new Date().getTime());
            getLocation(tid);
        }
    }


    void getLocation(String tid) {
        if (UtilMethods.INSTANCE.getLattitude != 0 && UtilMethods.INSTANCE.getLongitude != 0) {

            fingpaySubmit(tid, UtilMethods.INSTANCE.getLattitude, UtilMethods.INSTANCE.getLongitude);
        } else {
            if (mGetLocation != null) {
                mGetLocation.startLocationUpdates((lattitude, longitude) -> {
                    UtilMethods.INSTANCE.getLattitude = lattitude;
                    UtilMethods.INSTANCE.getLongitude = longitude;
                    fingpaySubmit(tid, lattitude, longitude);
                });
            } else {
                mGetLocation = new GetLocation(this, loader);
                mGetLocation.startLocationUpdates((lattitude, longitude) -> {
                    UtilMethods.INSTANCE.getLattitude = lattitude;
                    UtilMethods.INSTANCE.getLongitude = longitude;
                    fingpaySubmit(tid, lattitude, longitude);
                });
            }
        }
    }

    void fingpaySubmit(String tid, double lattitude, double longitude) {

        String amount = amountEt.getText().toString().trim();
        String remarks = remarksEt.getText().toString().trim();

        if (UtilMethods.INSTANCE.isValidString(merchantId)) {
            if (UtilMethods.INSTANCE.isValidString(password)) {

                Intent intent = new Intent(this, MicroAtmLoginScreen.class);
                intent.putExtra(Constants.MERCHANT_USERID, merchantId);
                intent.putExtra(Constants.MERCHANT_PASSWORD, password);
                intent.putExtra(Constants.AMOUNT, amount);
                intent.putExtra(Constants.REMARKS, remarks);
                intent.putExtra(Constants.MOBILE_NUMBER, mobile);
                intent.putExtra(Constants.AMOUNT_EDITABLE, false);
                intent.putExtra(Constants.TXN_ID, tid + "");
                intent.putExtra(Constants.SUPER_MERCHANTID, superMerchentId);
                intent.putExtra(Constants.IMEI, UtilMethods.INSTANCE.getIMEI(this));
                intent.putExtra(Constants.LATITUDE, lattitude);
                intent.putExtra(Constants.LONGITUDE, longitude);



                switch (selectedTypePos) {
                    case 0:
                        if (amountEt.getText().toString().isEmpty()) {
                            amountEt.setError("Please enter amount.");
                            amountEt.requestFocus();
                            return;
                        }
                        intent.putExtra(Constants.TYPE, Constants.CASH_WITHDRAWAL);
                        break;

                    case 1:
                        if (amountEt.getText().toString().isEmpty()) {
                            amountEt.setError("Please enter amount.");
                            amountEt.requestFocus();
                            return;
                        }
                        intent.putExtra(Constants.TYPE, Constants.CASH_DEPOSIT);
                        break;

                    case 2:
                        intent.putExtra(Constants.TYPE, Constants.BALANCE_ENQUIRY);
                        break;

                    case 3:
                        intent.putExtra(Constants.TYPE, Constants.MINI_STATEMENT);
                        break;

                    case 4:
                        intent.putExtra(Constants.TYPE, Constants.CARD_ACTIVATION);

                        break;
                    case 5:
                        intent.putExtra(Constants.TYPE, Constants.PIN_RESET);
                        break;

                    case 6:
                        intent.putExtra(Constants.TYPE, Constants.CHANGE_PIN);
                        break;
                }

                intent.putExtra(Constants.MICROATM_MANUFACTURER, Constants.MoreFun);
                startActivityForResult(intent, CODE);
            } else {
                Toast.makeText(this, "Please enter the password", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Please enter the merchant id", Toast.LENGTH_SHORT).show();
        }
    }

    void history() {
        Intent intent = new Intent(this, MATMHistoryScreen.class);
        intent.putExtra(Constants.MERCHANT_USERID, merchantId);
        intent.putExtra(Constants.MERCHANT_PASSWORD, password);
        intent.putExtra(Constants.SUPER_MERCHANTID, superMerchentId);
        intent.putExtra(Constants.IMEI, UtilMethods.INSTANCE.getIMEI(this));
        startActivity(intent);

        /*For Testing
        Intent intent = new Intent(this, MicroATMStatusActivity.class);
        Intent inte = new Intent();
        inte.putExtra(Constants.TRANS_STATUS, false);
        inte.putExtra(Constants.MESSAGE, "Hello Vishnu How are u");
        inte.putExtra(Constants.TRANS_AMOUNT, 34656.0);
        inte.putExtra(Constants.BALANCE_AMOUNT, 3453.0);
        inte.putExtra(Constants.RRN, "djwid28e092");
        inte.putExtra(Constants.TRANS_TYPE, "Cash Withdrawal");
        inte.putExtra(Constants.TYPE, Constants.CASH_WITHDRAWAL);
        inte.putExtra(Constants.CARD_NUM, "6372472482992");
        inte.putExtra(Constants.BANK_NAME, "Punjab Bank");
        inte.putExtra(Constants.CARD_TYPE, "VISA");
        inte.putExtra(Constants.TERMINAL_ID, "73rhiwef");
        inte.putExtra(Constants.FP_TRANS_ID, "ue93urhf");
        inte.putExtra(Constants.TRANS_ID, "e982dgwdfgow290e");
        int size = inte.getExtras().size();
        intent.putExtras(inte);
        startActivity(intent);*/

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CODE) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    boolean status = data.getBooleanExtra(Constants.TRANS_STATUS, false);
                    String response = data.getStringExtra(Constants.MESSAGE);
                    double transAmount = data.getDoubleExtra(Constants.TRANS_AMOUNT, 0);
                    double balAmount = data.getDoubleExtra(Constants.BALANCE_AMOUNT, 0);
                    String bankRrn = data.getStringExtra(Constants.RRN);
                    String transType = data.getStringExtra(Constants.TRANS_TYPE);
                    int type = data.getIntExtra(Constants.TYPE, 0);
                    String cardNum = getIntent().getStringExtra(Constants.CARD_NUM);
                    String bankName = getIntent().getStringExtra(Constants.BANK_NAME);

                    if (UtilMethods.INSTANCE.isValidString(response)) {
                        if (data.getExtras().size() > 7) {
                            Intent intent = new Intent(MicroAtmActivity.this, MicroATMStatusActivity.class);
                            intent.putExtras(data);
                            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            startActivity(intent);
                        } else {
                            showData(status, response, transAmount, balAmount, bankRrn, transType, type);
                        }

                        if (type == Constants.CASH_WITHDRAWAL || type == Constants.CASH_DEPOSIT) {
                            if (status) {
                                UpdateMiniBankStatus(MicroAtmActivity.this, cardNum, bankName, loader, bankRrn, "2", response);
                            } else {
                                UpdateMiniBankStatus(MicroAtmActivity.this, cardNum, bankName, loader, bankRrn, "3", response);
                            }
                        }
                    }

                } else {
                    Toast.makeText(this, "Data not found", Toast.LENGTH_SHORT).show();
                    if (selectedTypePos==0 || selectedTypePos==1) {
                        UpdateMiniBankStatus(MicroAtmActivity.this, "", "", loader, "", "3", "Data not found");
                    }
                }
            } else if (resultCode == RESULT_CANCELED) {
                if (selectedTypePos==0|| selectedTypePos==1) {
                    UpdateMiniBankStatus(MicroAtmActivity.this, "", "", loader, "", "3", "Canceled");
                }
                Toast.makeText(this, "canceled", Toast.LENGTH_SHORT).show();

            }
        } else {
            if (mGetLocation != null) {
                mGetLocation.onActivityResult(requestCode, resultCode, data);
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

    public void InitiateMiniBank(final Activity context, final CustomLoader loader) {

        loader.show();
        AEPSWebConnectivity.INSTANCE.InitiateMiniBank(context, intentSdkType, intentOid, amountEt.getText().toString().trim(), LoginDataResponse, loader, new UtilMethods.ApiCallBack() {
            @Override
            public void onSucess(Object object) {
                InitiateMiniBankResponse miniBankResponse = (InitiateMiniBankResponse) object;
                tid = miniBankResponse.getTid() + "";
                getLocation(tid);
            }
        });

    }

    public void UpdateMiniBankStatus(final Activity context, String cardNum, String bankName, final CustomLoader loader, String vendorId, String apiStatus, String remark) {

        loader.show();
        AEPSWebConnectivity.INSTANCE.UpdateMiniBankStatus(context, cardNum, bankName, loader, tid, vendorId, apiStatus, remark, LoginDataResponse, new UtilMethods.ApiCallBack() {
            @Override
            public void onSucess(Object object) {

            }
        });

    }


    void showData(boolean status, String response, double transAmount, double balAmount, String bankRrn, String transType, int type) {
        try {
            if (alertDialogReport != null && alertDialogReport.isShowing()) {
                return;
            }
            AlertDialog.Builder dialogBuilder;
            dialogBuilder = new AlertDialog.Builder(this);
            alertDialogReport = dialogBuilder.create();
            alertDialogReport.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            LayoutInflater inflater = getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.dialog_mini_atm_response, null);
            alertDialogReport.setView(dialogView);

            LinearLayout shareView = dialogView.findViewById(R.id.shareView);
            RelativeLayout topView = dialogView.findViewById(R.id.topView);
            RelativeLayout statusBg = dialogView.findViewById(R.id.statusBg);
            TextView typeTv = dialogView.findViewById(R.id.typeTv);
            ImageView closeIv = dialogView.findViewById(R.id.closeIv);
            ImageView statusIcon = dialogView.findViewById(R.id.statusIcon);
            TextView statusTv = dialogView.findViewById(R.id.statusTv);
            TextView statusMsg = dialogView.findViewById(R.id.statusMsg);
            LinearLayout tamtView = dialogView.findViewById(R.id.tamtView);
            TextView txnAmt = dialogView.findViewById(R.id.txnAmt);
            LinearLayout bAmtView = dialogView.findViewById(R.id.bAmtView);
            TextView balAmt = dialogView.findViewById(R.id.balAmt);
            LinearLayout rrnView = dialogView.findViewById(R.id.rrnView);
            TextView rrn = dialogView.findViewById(R.id.rrn);
            LinearLayout TypeView = dialogView.findViewById(R.id.TypeView);
            TextView txnType = dialogView.findViewById(R.id.txnType);
            Button closeBtn = dialogView.findViewById(R.id.closeBtn);
            Button shareBtn = dialogView.findViewById(R.id.shareBtn);
            Button repetBtn = dialogView.findViewById(R.id.repetBtn);


            String responseStr = "";

            if (status) {
                statusTv.setText("Success");
                statusIcon.setImageResource(R.drawable.ic_check_mark_outline);
              //  ImageViewCompat.setImageTintList(statusIcon, AppCompatResources.getColorStateList(this, R.color.green));
                ViewCompat.setBackgroundTintList(statusBg, AppCompatResources.getColorStateList(this, R.color.green));
                statusTv.setTextColor(getResources().getColor(R.color.green));

            } else {
                statusTv.setText("Failed");
                statusIcon.setImageResource(R.drawable.ic_cross_mark_outline);
               // ImageViewCompat.setImageTintList(statusIcon, AppCompatResources.getColorStateList(this, R.color.color_red));
                ViewCompat.setBackgroundTintList(statusBg, AppCompatResources.getColorStateList(this, R.color.color_red));
                statusTv.setTextColor(getResources().getColor(R.color.color_red));

            }

            if (type == Constants.CASH_WITHDRAWAL) {
                typeTv.setText("Cash Withdrawl");
            } else if (type == Constants.CASH_DEPOSIT) {
                typeTv.setText("Cash Deposit");
            } else if (type == Constants.BALANCE_ENQUIRY) {
                typeTv.setText("Balance Enquiry");
            } else if (type == Constants.MINI_STATEMENT) {
                typeTv.setText("Mini Statement");
            } else if (type == Constants.CARD_ACTIVATION) {
                typeTv.setText("Card Activation");
            } else if (type == Constants.PIN_RESET) {
                typeTv.setText("Reset Pin");
            } else if (type == Constants.CHANGE_PIN) {
                typeTv.setText("Change Pin");
            }

            if (response != null && !response.isEmpty()) {
                statusMsg.setText(response);
                statusMsg.setVisibility(View.VISIBLE);
            } else {
                statusMsg.setVisibility(View.GONE);
            }

            if (type == Constants.BALANCE_ENQUIRY || type == Constants.MINI_STATEMENT) {
                if (transAmount != 0) {
                    txnAmt.setText("\u20B9 " + UtilMethods.INSTANCE.formatedAmount(transAmount + ""));
                    tamtView.setVisibility(View.VISIBLE);
                } else {
                    tamtView.setVisibility(View.GONE);
                }
                balAmt.setText("\u20B9 " + UtilMethods.INSTANCE.formatedAmount(balAmount + ""));
                bAmtView.setVisibility(View.VISIBLE);
            } else if (type == Constants.CASH_WITHDRAWAL || type == Constants.CASH_DEPOSIT) {
                txnAmt.setText("\u20B9 " + UtilMethods.INSTANCE.formatedAmount(transAmount + ""));
                tamtView.setVisibility(View.VISIBLE);

                if (balAmount != 0) {
                    balAmt.setText("\u20B9 " + UtilMethods.INSTANCE.formatedAmount(balAmount + ""));
                    bAmtView.setVisibility(View.VISIBLE);
                } else {
                    bAmtView.setVisibility(View.GONE);
                }
            } else {
                if (transAmount != 0) {
                    txnAmt.setText("\u20B9 " + UtilMethods.INSTANCE.formatedAmount(transAmount + ""));
                    tamtView.setVisibility(View.VISIBLE);
                } else {
                    tamtView.setVisibility(View.GONE);
                }

                if (balAmount != 0) {
                    balAmt.setText("\u20B9 " + UtilMethods.INSTANCE.formatedAmount(balAmount + ""));
                    bAmtView.setVisibility(View.VISIBLE);
                } else {
                    bAmtView.setVisibility(View.GONE);
                }
            }


            if (bankRrn != null && !bankRrn.isEmpty()) {
                rrn.setText(bankRrn);
                rrnView.setVisibility(View.VISIBLE);
            } else {
                rrnView.setVisibility(View.GONE);

            }

            if (transType != null && !transType.isEmpty()) {
                txnType.setText(transType);
                TypeView.setVisibility(View.VISIBLE);
            } else {
                TypeView.setVisibility(View.GONE);

            }

            closeIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialogReport.dismiss();
                }
            });
            alertDialogReport.show();

        } catch (IllegalStateException ise) {

        } catch (IllegalArgumentException iae) {

        } catch (Exception e) {

        }

    }

}

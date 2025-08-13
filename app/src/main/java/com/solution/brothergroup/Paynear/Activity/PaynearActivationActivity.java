package com.solution.brothergroup.Paynear.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.pnsol.sdk.auth.AccountValidator;
import com.pnsol.sdk.interfaces.PaymentTransactionConstants;
import com.pnsol.sdk.vo.response.LoginResponse;
import com.solution.brothergroup.R;
import com.solution.brothergroup.Util.CustomAlertDialog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

/**
 * @Author : Bhavani.A
 * @Version : V_13
 * @Date : Fed 20, 2017
 * @Copyright : (C) Paynear Solutions Pvt. Ltd.
 * <p>
 * updated By vasanthi.k 10/22/2018
 */

public class PaynearActivationActivity extends AppCompatActivity implements
        PaymentTransactionConstants {

    private static final int REQUEST_PERMISSIONS = 123;
    private String merchantAPIKey = "8A23F8787722"/*"763432092B47"*/;
    private String partnerAPIKey = "41401A93062F"/*"763432092B47"*/;
    private int INTENT_PERMISSION_STORAGE = 123;
    private Snackbar mSnackBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paynear_activation);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("POS Activation");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_icon);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(view -> onBackPressed());

    }


    @Override
    protected void onStart() {
        super.onStart();

    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            if (msg != null) {
                if (msg.what == SUCCESS) {
                    LoginResponse vo = (LoginResponse) msg.obj;

                    //Toast.makeText(PaynearActivationActivity.this, "" + vo.getResponseMessage(), Toast.LENGTH_LONG).show();
                    Intent i = new Intent(PaynearActivationActivity.this, PaynearActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(i);
                    finish();
                } else if (msg.what == FAIL) {
                    alertMessageError("Failed",(String) msg.obj);
                   /* Toast.makeText(PaynearActivationActivity.this, (String) msg.obj,
                            Toast.LENGTH_SHORT).show();*/
                } else if (msg.what == ERROR_MESSAGE) {
                    alertMessageError("Error",(String) msg.obj);
                    /*Toast.makeText(PaynearActivationActivity.this, (String) msg.obj,
                            Toast.LENGTH_LONG).show();*/
                } else {
                    alertMessageError("Alert",(String) msg.obj);
                    /*Toast.makeText(PaynearActivationActivity.this, (String) msg.obj,
                            Toast.LENGTH_LONG).show();*/
                }
            } else {
                alertMessageError("Error","null object error");
              /*  Toast.makeText(PaynearActivationActivity.this, "null object error",
                        Toast.LENGTH_LONG).show();*/
            }
        }

    };

    public void alertMessageError(String title, String message) {

            new CustomAlertDialog(PaynearActivationActivity.this,true).ErrorWithSingleBtnCallBack(title, message, "Ok", false,
                    new CustomAlertDialog.DialogCallBack() {
                        @Override
                        public void onPositiveClick() {
                           /* finish();*/
                        }

                        @Override
                        public void onNegativeClick() {

                        }
                    });

    }


    @Override
    protected void onResume() {
        super.onResume();

        if (ContextCompat.checkSelfPermission(PaynearActivationActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSIONS);
        } else {
            AccountValidator validator = new AccountValidator(getApplicationContext());
            if (validator.isAccountActivated()) {
                Intent i = new Intent(PaynearActivationActivity.this, PaynearActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(i);
                finish();
            } else {
                validator.accountActivation(handler, merchantAPIKey, partnerAPIKey);
            }


        }


    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_PERMISSIONS) {

            int permissionCheck = PackageManager.PERMISSION_GRANTED;
            for (int permission : grantResults) {
                permissionCheck = permissionCheck + permission;
            }
            if ((grantResults.length > 0) && permissionCheck == PackageManager.PERMISSION_GRANTED) {

                onResume();
            } else {
                showWarningSnack(R.string.str_ShowOnPermisstionDenied, "Enable", true);
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }


    }

    void showWarningSnack(int stringId, String btn, final boolean isForSetting) {
        if (mSnackBar != null && mSnackBar.isShown()) {
            return;
        }
        mSnackBar = Snackbar.make(findViewById(android.R.id.content), stringId,
                Snackbar.LENGTH_INDEFINITE).setAction(btn,
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (isForSetting) {
                            Intent intent = new Intent();
                            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            intent.addCategory(Intent.CATEGORY_DEFAULT);
                            intent.setData(Uri.parse("package:" + getPackageName()));
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                            intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                            startActivity(intent);
                        } else {
                            ActivityCompat.requestPermissions(PaynearActivationActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSIONS);
                        }

                    }
                });

        mSnackBar.setActionTextColor(getResources().getColor(R.color.colorPrimary));
        TextView mainTextView = (TextView) (mSnackBar.getView()).
                findViewById(R.id.snackbar_text);
        mainTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen._12sdp));
        mainTextView.setMaxLines(4);
        mSnackBar.show();

    }
}

package com.solution.brothergroup.Authentication;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import androidx.annotation.RequiresApi;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowInsetsControllerCompat;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.solution.brothergroup.Activities.MainActivity;
import com.solution.brothergroup.BuildConfig;
import com.solution.brothergroup.Notification.app.Config;
import com.solution.brothergroup.R;
import com.solution.brothergroup.Util.UtilMethods;
import com.solution.brothergroup.usefull.CustomLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int REQUEST_PERMISSIONS = 20;
    private static String IMEI;
    String number="";

    // Declare Button.....
    public Button btLogin;
    public Button FwdokButton;
    public Button cancelButton;
    // Declare RelativeLayout.....
    public LinearLayout whatsapp;
    public RelativeLayout rlForgotPass;
    // Declare TextInputLayout.....
    public TextInputLayout tilMobile;
    public TextInputLayout tilPass;
    public TextInputLayout tilMobileFwp;

    // Declare TextInputLayout.....
    public AutoCompleteTextView edMobile;
    public AutoCompleteTextView edPass;
    public EditText edMobileFwp;
    LinearLayout llLogin;
    RelativeLayout rlLayoutFwdPass;
    // Declare CustomLoader.....
    CustomLoader loader;
    boolean isLogin;
    String[] recent;
    ArrayList<String> recentNumber = new ArrayList<>();
    HashMap<String, String> recentNumberMap = new HashMap<>();
    // Declare RelativeLayout.....
    ArrayAdapter<String> adapter;
    CheckBox rememberCheck;
    private AlertDialog alertDialog;
    private SparseIntArray mErrorString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowInsetsControllerCompat controller = new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        controller.setAppearanceLightStatusBars(true);
        controller.setAppearanceLightNavigationBars(true);
        setContentView(R.layout.activity_login);
        getId();
        UtilMethods.INSTANCE.openImageDialog(this, "Image/Website/1/Popup/AppBeforeLogin.png");
        if( !UtilMethods.INSTANCE.isReferrerIdSetData(this)){
            new InstallReferral(this).InstllReferralData();
        }
        RecentNumbers();
        startDashboard();
    }

    public void RecentNumbers() {
        String abcd = UtilMethods.INSTANCE.getRecentLogin(this);
        if (abcd != null && abcd.length() > 4) {
            recentNumberMap = new Gson().fromJson(abcd, new TypeToken<HashMap<String, String>>() {
            }.getType());

            int index = 0;
            for (Map.Entry<String, String> e : recentNumberMap.entrySet()) {
                String key = e.getKey();
                /* String value = e.getValue();*/
                recentNumber.add(key);
                index++;

            }

            adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, recentNumber);
            edMobile.setAdapter(adapter);
            edMobile.setThreshold(1);
        }


        edMobile.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                edPass.setText(recentNumberMap.get(recentNumber.get(position)));

            }
        });
    }

    public void getId() {
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        btLogin = (Button) findViewById(R.id.bt_login);
        rememberCheck = (CheckBox) findViewById(R.id.check_pass);
        rlForgotPass = (RelativeLayout) findViewById(R.id.rl_fwd_pass);
        rlLayoutFwdPass = findViewById(R.id.rl_layout_fwd_pass);
        edMobileFwp = findViewById(R.id.ed_mobile_fwp);
        tilMobileFwp = findViewById(R.id.til_mobile_fwp);
        FwdokButton = findViewById(R.id.okButton);
        cancelButton = findViewById(R.id.cancelButton);
        llLogin = findViewById(R.id.ll_login);
        whatsapp=findViewById(R.id.whatsapp);
        TextView currentVersion = findViewById(R.id.currentVersion);
        currentVersion.setText(getString(R.string.app_name) + " Version " + BuildConfig.VERSION_NAME);
        tilMobile = (TextInputLayout) findViewById(R.id.til_mobile);
        tilPass = (TextInputLayout) findViewById(R.id.til_pass);

        edMobile = (AutoCompleteTextView) findViewById(R.id.ed_mobile);
        edPass = (AutoCompleteTextView) findViewById(R.id.ed_pass);
        TextView termAndPrivacyTxt = findViewById(R.id.term_and_privacy_txt);
//        termAndPrivacyTxt.setMovementMethod(LinkMovementMethod.getInstance());
        termAndPrivacyTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,PrivacyPolicy.class);
                startActivity(intent);
            }
        });
        setListners();
        edMobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!validateMobile()) {
                    return;
                }
            }
        });
        edPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!validatePass()) {
                    return;
                }
            }
        });


        findViewById(R.id.tv_signup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignupActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
            }
        });
    }

    public void setListners() {
        btLogin.setOnClickListener(this);
        FwdokButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);
        rlForgotPass.setOnClickListener(this);
        whatsapp.setOnClickListener(this);

    }

    private boolean validateMobile() {
        if (edMobile.getText().toString().trim().isEmpty()) {
            tilMobile.setError("");
            requestFocus(edMobile);
            btLogin.setEnabled(false);
            return false;
        } else {
            tilMobile.setErrorEnabled(false);
            // edPass.requestFocus();
        }

        return true;
    }

    private boolean validatePass() {
        if (edPass.getText().toString().trim().isEmpty()) {
            tilPass.setError(getString(R.string.err_msg_pass));
            requestFocus(edPass);
            btLogin.setEnabled(false);
            return false;
        } else {
            tilPass.setErrorEnabled(false);
            btLogin.setEnabled(true);
        }
        return true;
    }

    public boolean validateMobileFwp() {
        if (edMobileFwp.getText().toString().trim().isEmpty()) {
            tilMobileFwp.setError(getString(R.string.err_msg_mobile));
            requestFocus(edMobileFwp);
            return false;
        } else if (edMobileFwp.getText().toString().trim().matches("[a-zA-Z]+") && edMobileFwp.getText().toString().trim().length() < 4) {
            tilMobileFwp.setError(getString(R.string.err_msg_mobile_length));
            requestFocus(edMobileFwp);
            return false;
        } else if (edMobileFwp.getText().toString().trim().matches("[0-9]+") && edMobileFwp.getText().toString().trim().length() != 10) {
            tilMobileFwp.setError(getString(R.string.err_msg_mobile_length));
            requestFocus(edMobileFwp);
            return false;
        } else {
            tilMobileFwp.setErrorEnabled(false);
            FwdokButton.setEnabled(true);
        }
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View v) {
        if (v == btLogin) {
            if (!validateMobile()) {
                return;
            }

            if (!validatePass()) {
                return;
            }

            if (rememberCheck.isChecked()) {
                recentNumberMap.put(edMobile.getText().toString(), edPass.getText().toString());
                recentNumber.add(edMobile.getText().toString());
                adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, recentNumber);
                edMobile.setAdapter(adapter);
                edMobile.setThreshold(1);

                UtilMethods.INSTANCE.setRecentLogin(this, new Gson().toJson(recentNumberMap));

            }


            /* if (!(UtilMethods.INSTANCE.isVpnConnected(this) || UtilMethods.INSTANCE.isSimAvailable(this))) {*/
            if (UtilMethods.INSTANCE.isNetworkAvialable(LoginActivity.this)) {
                loader.show();
                loader.setCancelable(false);
                loader.setCanceledOnTouchOutside(false);
                UtilMethods.INSTANCE.secureLogin(LoginActivity.this, edMobile.getText().toString().trim(),
                        edPass.getText().toString().trim(),  loader);
            } else {
                UtilMethods.INSTANCE.NetworkError(LoginActivity.this, getResources().getString(R.string.err_msg_network_title),
                        getResources().getString(R.string.err_msg_network));
            }
        }

        if (v == rlForgotPass) {

            llLogin.setVisibility(View.GONE);
            rlLayoutFwdPass.setVisibility(View.VISIBLE);
            //OpenDialogFwd();
        }
        if (v == FwdokButton) {
            if (!validateMobileFwp()) {
                return;
            }

            if (UtilMethods.INSTANCE.isNetworkAvialable(LoginActivity.this)) {
                loader.show();
                UtilMethods.INSTANCE.ForgotPass(LoginActivity.this, edMobileFwp.getText().toString().trim(), loader);

            } else {
                UtilMethods.INSTANCE.NetworkError(LoginActivity.this, getResources().getString(R.string.err_msg_network_title),
                        getResources().getString(R.string.err_msg_network));
            }
        }
        if (v == cancelButton) {
            llLogin.setVisibility(View.VISIBLE);
            rlLayoutFwdPass.setVisibility(View.GONE);
            edMobileFwp.setText("");
        }
        if (v == whatsapp){
          /*  Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "BrotherGroup");
            sendIntent.setType("text/plain");
            sendIntent.setPackage("com.whatsapp");
            //  startActivity(Intent.createChooser(sendIntent, ""));
            startActivity(sendIntent);*/
            String url = "https://api.whatsapp.com/send?phone="+number;
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        }
    }


    public void OpenDialogFwd() {

        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.forgotpass, null);

        final EditText edMobileFwp = (EditText) view.findViewById(R.id.ed_mobile_fwp);
        TextInputLayout tilMobileFwp = (TextInputLayout) view.findViewById(R.id.til_mobile_fwp);
        Button FwdokButton = (Button) view.findViewById(R.id.okButton);
        Button cancelButton = (Button) view.findViewById(R.id.cancelButton);

        final Dialog dialog = new Dialog(this, R.style.alert_dialog_light);

        dialog.setCancelable(false);
        dialog.setContentView(view);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        edMobileFwp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!validateMobileFwp()) {
                    return;
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        FwdokButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!validateMobileFwp()) {
                    return;
                }

                if (UtilMethods.INSTANCE.isNetworkAvialable(LoginActivity.this)) {
                    loader.show();
                    UtilMethods.INSTANCE.ForgotPass(LoginActivity.this, edMobileFwp.getText().toString().trim(), loader);

                } else {
                    UtilMethods.INSTANCE.NetworkError(LoginActivity.this, getResources().getString(R.string.err_msg_network_title),
                            getResources().getString(R.string.err_msg_network));
                }

                dialog.dismiss();
            }
        });

        dialog.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // Utils.goAnotherActivity(this,Login.class);
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    public void startDashboard() {

        FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);
        if (UtilMethods.INSTANCE.isLogin(this)){
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
           /* intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);*/
            startActivity(intent);
            finish();
        }

    }

    //============================================== Permissions Related code start==============================================//

    private void checkPermission() {
        try {
            alertDialog.setTitle("Permission Error!");
            alertDialog.setMessage(this.getResources().getString(R.string.str_ShowOnPermisstion));
            alertDialog.show();
            requestAppPermissions(new String[]{Manifest.permission.READ_CONTACTS, Manifest.permission.READ_PHONE_STATE, Manifest.permission.INTERNET, Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.ACCESS_WIFI_STATE}, R.string.str_ShowOnPermisstion, REQUEST_PERMISSIONS);
        } catch (Exception ex) {
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        int permissionCheck = PackageManager.PERMISSION_GRANTED;
        for (int permission : grantResults) {
            permissionCheck = permissionCheck + permission;
        }
        if ((grantResults.length > 0) && permissionCheck == PackageManager.PERMISSION_GRANTED) {
            onPermissionsGranted(requestCode);
        } else {
            Snackbar.make(findViewById(android.R.id.content), mErrorString.get(requestCode),
                    Snackbar.LENGTH_INDEFINITE).setAction("ENABLE",
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent();
                            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            intent.addCategory(Intent.CATEGORY_DEFAULT);
                            intent.setData(Uri.parse("package:" + getPackageName()));
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                            intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                            startActivity(intent);
                        }
                    }).show();
        }
    }

    public void requestAppPermissions(final String[] requestedPermissions,
                                      final int stringId, final int requestCode) {
        mErrorString.put(requestCode, stringId);
        int permissionCheck = PackageManager.PERMISSION_GRANTED;
        boolean shouldShowRequestPermissionRationale = false;
        for (String permission : requestedPermissions) {
            permissionCheck = permissionCheck + ContextCompat.checkSelfPermission(this, permission);
            shouldShowRequestPermissionRationale = shouldShowRequestPermissionRationale || ActivityCompat.shouldShowRequestPermissionRationale(this, permission);
        }
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            if (shouldShowRequestPermissionRationale) {
                Snackbar.make(findViewById(android.R.id.content), stringId,
                        Snackbar.LENGTH_INDEFINITE).setAction("GRANT",
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ActivityCompat.requestPermissions(LoginActivity.this, requestedPermissions, requestCode);
                            }
                        }).show();
            } else {
                ActivityCompat.requestPermissions(this, requestedPermissions, requestCode);
            }
        } else {
            onPermissionsGranted(requestCode);
        }
    }

    public void onPermissionsGranted(int requestCode) {

        IMEI = UtilMethods.INSTANCE.getDeviceId(LoginActivity.this);
    }
    //============================================== Permissions Related code end==============================================//

}

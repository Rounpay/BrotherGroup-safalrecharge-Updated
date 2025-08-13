package com.solution.brothergroup.DMRNEW.ui;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.WindowInsetsControllerCompat;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.solution.brothergroup.Activities.DMRReport;
import com.solution.brothergroup.DMRNEW.dto.TABLE;
import com.solution.brothergroup.R;
import com.solution.brothergroup.Util.ActivityActivityMessage;
import com.solution.brothergroup.Util.ApplicationConstant;
import com.solution.brothergroup.Util.GlobalBus;
import com.solution.brothergroup.Util.UtilMethods;
import com.solution.brothergroup.usefull.CustomLoader;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class DMRLogin extends AppCompatActivity implements View.OnClickListener {

    static final int PICK_CONTACT = 1;
    RadioButton login, create;
    EditText senderNo, senderFirstName, lastNameval, pincodeval, addressval, areaval;
    TextView DateOfBirth;
    Button submit;
    LinearLayout dmr_create, sender_layout, dmr_layout;
    TextView sender_num, sender_name, currency, remaining, limitUsed;
    CustomLoader loader = null;
    ImageView dmr_logout;
    RelativeLayout add_bene, bene_list;
    LinearLayout dmr_report;
    AlertDialog.Builder alertDialog;
    TABLE senderTableInfo;
    ImageView ivContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowInsetsControllerCompat controller = new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        controller.setAppearanceLightStatusBars(true);
        controller.setAppearanceLightNavigationBars(true);
        setContentView(R.layout.activity_dmrlogin);
        UtilMethods.INSTANCE.setDashboardStatus(this, false);
        GetId();
    }


    /*   @Override
       protected void onCreate(Bundle savedInstanceState) {
           super.onCreate(savedInstanceState);
           setContentView(R.layout.activity_dmrlogin);

           GetId();
       }*/
    private void GetId() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Money Transfer");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        senderNo = (EditText) findViewById(R.id.sender_number);
        senderFirstName = (EditText) findViewById(R.id.first_name);
        lastNameval = (EditText) findViewById(R.id.last_name);
        pincodeval = (EditText) findViewById(R.id.pincode);
        addressval = (EditText) findViewById(R.id.address);
        areaval = (EditText) findViewById(R.id.area);
        DateOfBirth = (TextView) findViewById(R.id.DateOfBirth);
        DateOfBirth.setCompoundDrawablesWithIntrinsicBounds(null, null, AppCompatResources.getDrawable(DMRLogin.this, R.drawable.ic_calendar_icon), null);

        ivContact = (ImageView) findViewById(R.id.iv_contact);
        login = (RadioButton) findViewById(R.id.login);
        create = (RadioButton) findViewById(R.id.create);
        submit = (Button) findViewById(R.id.submit);
        dmr_create = (LinearLayout) findViewById(R.id.dmr_create);
        dmr_layout = (LinearLayout) findViewById(R.id.dmr_layout);
        sender_layout = (LinearLayout) findViewById(R.id.sender_layout);
        bene_list = (RelativeLayout) findViewById(R.id.bene_list);
        add_bene = (RelativeLayout) findViewById(R.id.add_bene);
        dmr_report = (LinearLayout) findViewById(R.id.dmr_report);
        dmr_logout = (ImageView) findViewById(R.id.dmr_logout);
        sender_num = (TextView) findViewById(R.id.sender_num);
        sender_name = (TextView) findViewById(R.id.sender_name);
        //kycText = (TextView) findViewById(R.id.kycText);
        currency = (TextView) findViewById(R.id.currency);
        remaining = (TextView) findViewById(R.id.remaining);
        limitUsed = (TextView) findViewById(R.id.limitUsed);
        loader = new CustomLoader(DMRLogin.this, android.R.style.Theme_Translucent_NoTitleBar);
        submit.setText("Login");
        submit.setVisibility(View.GONE);
        senderNo.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 10) {
                    loader.show();
                    UtilMethods.INSTANCE.GetSender(DMRLogin.this, senderNo.getText().toString(), loader);
                }
            }
        });
        SetListener();
        DMRStatus();
    }

    private void DMRStatus() {
        dmr_layout.setVisibility(View.VISIBLE);
        sender_layout.setVisibility(View.GONE);
        submit.setVisibility(View.GONE);
//        if (IsSenderLogin()) {
//            dmr_layout.setVisibility(View.GONE);
//            sender_layout.setVisibility(View.VISIBLE);
//            submit.setVisibility(View.GONE);
//            SharedPreferences prefs = DMRLogin.this.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, DMRLogin.this.MODE_PRIVATE);
//            setCurrentDetail( prefs.getString(ApplicationConstant.INSTANCE.senderNumberPref, ""),prefs.getString(ApplicationConstant.INSTANCE.senderBalance, ""));
//
//        } else {
//            dmr_layout.setVisibility(View.VISIBLE);
//            sender_layout.setVisibility(View.GONE);
//            submit.setVisibility(View.GONE);
//        }

    }

    public boolean IsSenderLogin() {

        SharedPreferences prefs = DMRLogin.this.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, DMRLogin.this.MODE_PRIVATE);
        String isLogin = prefs.getString(ApplicationConstant.INSTANCE.senderNumberPref, null);

        if (isLogin != null && isLogin.length() > 0) {
            return true;
        } else {
            return false;
        }
    }

    private void SetListener() {
        login.setOnClickListener(this);
        create.setOnClickListener(this);
        ivContact.setOnClickListener(this);
        submit.setOnClickListener(this);
        dmr_logout.setOnClickListener(this);
        add_bene.setOnClickListener(this);
        bene_list.setOnClickListener(this);
        dmr_report.setOnClickListener(this);
        DateOfBirth.setOnClickListener(this);
    }

    public void setCurrentDetail(String number, String name, String balance) {
        sender_num.setText("" + number);
        sender_name.setText(name);
        remaining.setText(DMRLogin.this.getResources().getString(R.string.rupiya) + " : " + balance);
        dmr_layout.setVisibility(View.GONE);
        sender_layout.setVisibility(View.VISIBLE);
        submit.setVisibility(View.GONE);
    }

    @Subscribe
    public void onActivityActivityMessage(ActivityActivityMessage activityActivityMessage) {
        if (activityActivityMessage.getFrom().equalsIgnoreCase("GetSender")) {
            String[] data = activityActivityMessage.getMessage().split(",");
            setCurrentDetail(data[0], data[1], data[2]);
        } else if (activityActivityMessage.getFrom().equalsIgnoreCase("CallgetSenderSender")) {
            login.setChecked(false);
            create.setChecked(true);
            dmr_create.setVisibility(View.VISIBLE);
            submit.setVisibility(View.VISIBLE);
            submit.setText("Create");
        }
    }


    @Override
    public void onClick(View v) {

        if (v == login) {

            login.setChecked(true);
            create.setChecked(false);
            dmr_create.setVisibility(View.GONE);
            submit.setVisibility(View.GONE);
            submit.setText("Login");
        }
        if (v == create) {
            login.setChecked(false);
            create.setChecked(true);
            dmr_create.setVisibility(View.VISIBLE);
            submit.setVisibility(View.VISIBLE);
            submit.setText("Create");
        }

        if (v == DateOfBirth) {
            final Calendar myCalendar = Calendar.getInstance();
            final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth) {
                    // TODO Auto-generated method stub
                    myCalendar.set(Calendar.YEAR, year);
                    myCalendar.set(Calendar.MONTH, monthOfYear);
                    myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    String myFormat = "dd MMM yyyy"; //In which you need put here
                    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                    DateOfBirth.setText(sdf.format(myCalendar.getTime()));
                }

            };
            new DatePickerDialog(DMRLogin.this, date, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();
        }
        if (v == ivContact) {
            Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivityForResult(intent, PICK_CONTACT);
        }
        if (v == submit) {
            if (UtilMethods.INSTANCE.isNetworkAvialable(DMRLogin.this)) {
                loader.show();
                loader.setCancelable(false);
                loader.setCanceledOnTouchOutside(false);
                if (create.isChecked()) {
                    UtilMethods.INSTANCE.CreateSender(DMRLogin.this, senderNo.getText().toString(), senderFirstName.getText().toString(),
                            lastNameval.getText().toString(),
                            pincodeval.getText().toString(),
                            addressval.getText().toString(),
                            "",
                            DateOfBirth.getText().toString(), loader);
                }
            } else {
                UtilMethods.INSTANCE.NetworkError(DMRLogin.this, getResources().getString(R.string.err_msg_network_title),
                        getResources().getString(R.string.err_msg_network));
            }
        }
        if (v == dmr_logout) {
            UtilMethods.INSTANCE.setSenderNumber(DMRLogin.this, "", "", "", "");
            UtilMethods.INSTANCE.setSenderInfo(DMRLogin.this, "", "", false, null);
            UtilMethods.INSTANCE.setBeneficiaryList(DMRLogin.this, "");
            login.setChecked(true);
            create.setChecked(false);
            dmr_create.setVisibility(View.GONE);
            dmr_layout.setVisibility(View.VISIBLE);
            sender_layout.setVisibility(View.GONE);
            submit.setVisibility(View.GONE);
        }
        if (v == add_bene) {

            Intent i = new Intent(DMRLogin.this, AddBeneficiary.class);
            startActivity(i);

        }
        if (v == bene_list) {
            SharedPreferences prefs = DMRLogin.this.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, Context.MODE_PRIVATE);
            if (UtilMethods.INSTANCE.isNetworkAvialable(DMRLogin.this)) {
                loader.show();
                loader.setCancelable(false);
                loader.setCanceledOnTouchOutside(false);
                UtilMethods.INSTANCE.GetBeneficiary(DMRLogin.this, prefs.getString(ApplicationConstant.INSTANCE.senderNumberPref, ""), loader);


            } else {
                UtilMethods.INSTANCE.NetworkError(DMRLogin.this, getResources().getString(R.string.network_error_title),
                        getResources().getString(R.string.network_error_message));
            }
        }
        if (v == dmr_report) {

            Intent m = new Intent(DMRLogin.this, DMRReport.class);
            m.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(m);

        }

    }

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

    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        switch (reqCode) {
            case (PICK_CONTACT):
                if (resultCode == Activity.RESULT_OK) {

                    Uri contactData = data.getData();
                    Cursor c = DMRLogin.this.getContentResolver().query(contactData, null, null, null, null);
                    if (c.moveToFirst()) {

                        String id = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID));
                        String hasPhone = c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
                        String Number = "";

                        if (hasPhone.equalsIgnoreCase("1")) {
                            Cursor phones = DMRLogin.this.getContentResolver().query(
                                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id,
                                    null, null);
                            phones.moveToFirst();
                            // For Clear Old Value Of Number...
                            senderNo.setText("");
                            // <------------------------------------------------------------------>
                            Number = phones.getString(phones.getColumnIndex("data1"));
//                            // Log.e("number is:",Number);
                        }
                        String Name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                        if (!Number.equals("")) {
                            SetNumber(Number);
                        } else {
                            Toast.makeText(DMRLogin.this, "Please select a valid number", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                break;
        }
    }

    public void SetNumber(final String Number) {
        String Number1 = Number.replace("+91", "");
        String Number2 = Number1.replace("(", "");
        String Number3 = Number2.replace(")", "");
        String Number4 = Number3.replace(" ", "");
        String Number5 = Number4.replace("-", "");
        String Number6 = Number5.replace("_", "");
        senderNo.setText(Number6);
    }
}

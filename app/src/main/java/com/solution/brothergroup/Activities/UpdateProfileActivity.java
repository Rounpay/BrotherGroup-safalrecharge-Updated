package com.solution.brothergroup.Activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.safalrecharges.imagepicker.ImagePicker;
import com.safalrecharges.imagepicker.OnImagePickedListener;
import com.solution.brothergroup.Adapter.UploadDocsAdapter;
import com.solution.brothergroup.Api.Object.BankListObject;
import com.solution.brothergroup.Api.Request.BasicRequest;
import com.solution.brothergroup.Api.Request.EditUser;
import com.solution.brothergroup.Api.Request.UpdateKycStatusRequest;
import com.solution.brothergroup.Api.Request.UpdateUserRequest;
import com.solution.brothergroup.Api.Request.UploadDocRequest;
import com.solution.brothergroup.Api.Response.BankListResponse;
import com.solution.brothergroup.Api.Response.BasicResponse;
import com.solution.brothergroup.Api.Response.UpdateKycResponse;
import com.solution.brothergroup.Authentication.dto.LoginResponse;
import com.solution.brothergroup.BuildConfig;
import com.solution.brothergroup.Fragments.dto.GetUserResponse;
import com.solution.brothergroup.R;
import com.solution.brothergroup.Util.ApiClient;
import com.solution.brothergroup.Util.ApplicationConstant;
import com.solution.brothergroup.Util.ChangePassUtils;
import com.solution.brothergroup.Util.CustomAlertDialog;
import com.solution.brothergroup.Util.EndPointInterface;
import com.solution.brothergroup.Util.TooltipPopup.BubbleDialog;
import com.solution.brothergroup.Util.UtilMethods;
import com.solution.brothergroup.usefull.CustomLoader;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

public class UpdateProfileActivity extends AppCompatActivity {
    GetUserResponse mGetUserResponse;
    AppCompatButton kycUploadDocBtn;
    private TextView notice, tv_dob, tv_qualification, tv_LocationType, tv_Bank, tv_ShopType, tv_Poupulation;
    private EditText MobileNumberEt, AlternateMobileNumberEt, IFSCEt, LandmarkEt, AccountNumberEt, AccountNameEt, branchNameEt;
    private Spinner spin_Qualification, spin_Poupulation, spin_ShopType, spin_Bank, spin_LocationType;
    private TextInputLayout txt_dob, txt_Qualification, txt_Bank, txt_LocationType, txt_ShopType, txt_Poupulation;
    private EditText nameEt;
    private EditText outletNameEt;
    private EditText emailIdEt;
    private EditText pincodeEt;
    private EditText commRate;
    private EditText addressEt;
    private EditText gstinEt;
    private EditText aadharEt;
    private EditText panEt;
    private Button submitBtn;
    private Button kycBtn;
    private CustomLoader loader;
    private ImagePicker imagePicker;
    private int docTypeId, uid;
    private AlertDialog alertDialog;
    private AlertDialog alertDialogSelectDoc;
    private RecyclerView recyclerViewDocs;
    private UpdateKycResponse mUpdateKycResponse;
    private Map<String, Integer> hMapbankId = new HashMap<>();
    private Map<String, Integer> hMapQualification = new HashMap<>();
    private Map<String, Integer> hMapPopulation = new HashMap<>();
    private String[] arrayQualification;
    private String[] arrayPoupulation;
    private String[] arrayShopType;
    private String[] arrayLocationType;
    private String[] arrayBank;
    private ArrayAdapter<String> arrayAdapter;
    private String selectedQualification = "", selectedPopulation = "", selectedLocation = "", selectedShopType = "", selectedBank = "", ifsc = "";
    private int bankId;
    private int INTENT_MULTI_IMAGE = 5109;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar);
        findViews();
        if (UtilMethods.INSTANCE.getBankList(this) != null && UtilMethods.INSTANCE.getBankList(this).length() > 0) {
            getBankData(new Gson().fromJson(UtilMethods.INSTANCE.getBankList(this), BankListResponse.class));
        } else {
            HitApi();
        }

        arrayQualification = getResources().getStringArray(R.array.Qualification);
        arrayPoupulation = getResources().getStringArray(R.array.Poupulation);
        arrayShopType = getResources().getStringArray(R.array.ShopType);
        arrayLocationType = getResources().getStringArray(R.array.LocationType);

        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayQualification);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_Qualification.setAdapter(arrayAdapter);
        spin_Qualification.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (spin_Qualification.getSelectedItem().toString() != null && !spin_Qualification.getSelectedItem().toString().equalsIgnoreCase("Select Qualification")) {
                    selectedQualification = spin_Qualification.getSelectedItem().toString().trim();
                    txt_Qualification.setErrorEnabled(false);
                } else {
                    selectedQualification = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                spin_Qualification.setSelection(Integer.parseInt(arrayQualification[0]));
            }
        });


        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayPoupulation);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_Poupulation.setAdapter(arrayAdapter);
        spin_Poupulation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (spin_Poupulation.getSelectedItem().toString() != null && !spin_Poupulation.getSelectedItem().toString().equalsIgnoreCase("Select Poupulation")) {
                    selectedPopulation = spin_Poupulation.getSelectedItem().toString().trim();
                    txt_Poupulation.setErrorEnabled(false);
                } else {
                    selectedPopulation = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                spin_Poupulation.setSelection(Integer.parseInt(arrayPoupulation[0]));
            }
        });


        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayShopType);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_ShopType.setAdapter(arrayAdapter);
        spin_ShopType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (spin_ShopType.getSelectedItem().toString() != null && !spin_ShopType.getSelectedItem().toString().equalsIgnoreCase("Select Shop Type")) {
                    selectedShopType = spin_ShopType.getSelectedItem().toString().trim();
                    txt_ShopType.setErrorEnabled(false);
                } else {
                    selectedShopType = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                spin_ShopType.setSelection(Integer.parseInt(arrayShopType[0]));
            }
        });


        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayLocationType);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_LocationType.setAdapter(arrayAdapter);
        spin_LocationType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (spin_LocationType.getSelectedItem().toString() != null && !spin_LocationType.getSelectedItem().toString().equalsIgnoreCase("Select LocationType")) {
                    selectedLocation = spin_LocationType.getSelectedItem().toString().trim();
                    txt_LocationType.setErrorEnabled(false);
                } else {
                    selectedLocation = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                spin_LocationType.setSelection(Integer.parseInt(arrayLocationType[0]));
            }
        });


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
                tv_dob.setText(sdf.format(myCalendar.getTime()));
            }
        };
        tv_dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(UpdateProfileActivity.this, R.style.Theme_AppCompat_Light_Dialog_Alert, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        mGetUserResponse = (GetUserResponse) getIntent().getSerializableExtra("UserData");
        if (mGetUserResponse != null && mGetUserResponse.getUserInfo() != null) {
            setUserData();
        } else {
            mGetUserResponse = new Gson().fromJson(UtilMethods.INSTANCE.getUserDataPref(this), GetUserResponse.class);
            if (mGetUserResponse != null && mGetUserResponse.getUserInfo() != null) {
                setUserData();
            } else {
                getUserDetail();
            }

        }

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfile();
            }
        });

        kycBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateKyc();
            }
        });
        showKycButton();
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("photopath", ImagePicker.currentCameraFileName);
        outState.putInt("docTypeId", docTypeId);
        outState.putInt("uID", uid);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey("photopath") && ImagePicker.currentCameraFileName.length() < 5 && docTypeId == 0 && uid == 0) {
                ImagePicker.currentCameraFileName = savedInstanceState.getString("photopath");
                docTypeId = savedInstanceState.getInt("docTypeId");
                uid = savedInstanceState.getInt("uID");
            }
        }

        super.onRestoreInstanceState(savedInstanceState);
    }

    private void findViews() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setTitle("Update Profile");
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_icon);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        imagePicker = new ImagePicker(this, null, new OnImagePickedListener() {
            @Override
            public void onImagePicked(Uri imageUri) {
                Uri imgUri = imageUri;
                uploadDocApi(new File(imgUri.getPath()));
            }
        }).setWithImageCrop();

        notice = findViewById(R.id.notice);
        nameEt = findViewById(R.id.nameEt);
        outletNameEt = findViewById(R.id.outletNameEt);
        emailIdEt = findViewById(R.id.emailIdEt);
        pincodeEt = findViewById(R.id.pincodeEt);
        commRate = findViewById(R.id.commRate);
        addressEt = findViewById(R.id.addressEt);
        gstinEt = findViewById(R.id.gstinEt);
        aadharEt = findViewById(R.id.aadharEt);
        panEt = findViewById(R.id.panEt);
        submitBtn = findViewById(R.id.submitBtn);
        kycBtn = findViewById(R.id.kycBtn);
        txt_dob = findViewById(R.id.txt_dob);
        tv_dob = findViewById(R.id.tv_dob);
        MobileNumberEt = (EditText) findViewById(R.id.MobileNumberEt);
        AlternateMobileNumberEt = (EditText) findViewById(R.id.AlternateMobileNumberEt);
        IFSCEt = (EditText) findViewById(R.id.IFSCEt);
        LandmarkEt = (EditText) findViewById(R.id.LandmarkEt);
        AccountNumberEt = (EditText) findViewById(R.id.AccountNumberEt);
        AccountNameEt = (EditText) findViewById(R.id.AccountNameEt);
        branchNameEt = (EditText) findViewById(R.id.branchNameEt);
        spin_Qualification = (Spinner) findViewById(R.id.spin_Qualification);
        spin_Poupulation = (Spinner) findViewById(R.id.spin_Poupulation);
        spin_ShopType = (Spinner) findViewById(R.id.spin_ShopType);
        spin_Bank = (Spinner) findViewById(R.id.spin_Bank);
        spin_LocationType = (Spinner) findViewById(R.id.spin_LocationType);
        txt_Qualification = findViewById(R.id.txt_Qualification);
        txt_Bank = findViewById(R.id.txt_Bank);
        txt_LocationType = findViewById(R.id.txt_LocationType);
        txt_ShopType = findViewById(R.id.txt_ShopType);
        txt_Poupulation = findViewById(R.id.txt_Poupulation);
        tv_qualification = findViewById(R.id.tv_qualification);
        tv_Poupulation = findViewById(R.id.tv_Poupulation);
        tv_ShopType = findViewById(R.id.tv_ShopType);
        tv_Bank = findViewById(R.id.tv_Bank);
        tv_LocationType = findViewById(R.id.tv_LocationType);
    }


    private void getBankData(BankListResponse bankListResponse) {
        try {
            if (bankListResponse != null && bankListResponse.getBankMasters() != null && bankListResponse.getBankMasters().size() > 0) {

                final ArrayList<BankListObject> Bank = bankListResponse.getBankMasters();
                final ArrayList<String> arrayListBank = new ArrayList<String>();
                final ArrayList<String> BankidID = new ArrayList<String>();

                arrayBank = new String[bankListResponse.getBankMasters().size() + 1];
                arrayBank[0] = "Select Bank";

                for (int i = 0; i < Bank.size(); i++) {
                    arrayBank[i + 1] = Bank.get(i).getBankName();
                    arrayListBank.add(Bank.get(i).getBankName());
                    /*BankidID.add(Bank.get(i).getId() + "_" + Bank.get(i).getAccountHolder() + "_" + Bank.get(i).getAccountNo());*/
                    BankidID.add(Bank.get(i).getId());
                    hMapbankId.put(Bank.get(i).getBankName(), Integer.valueOf(Bank.get(i).getId()));
                }

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayBank);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spin_Bank.setAdapter(arrayAdapter);
                spin_Bank.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        selectedBank = spin_Bank.getSelectedItem().toString();
                        if (selectedBank != null && !selectedBank.equalsIgnoreCase("Select Bank")) {
                            bankId = hMapbankId.get(selectedBank);
                            selectedBank = spin_Bank.getSelectedItem().toString().trim();
                            ifsc = Bank.get(position).getIfsc();
                            IFSCEt.setText(ifsc + "");
                            txt_Bank.setErrorEnabled(false);
                        } else {
                            IFSCEt.setText("");
                            selectedBank = "";
                        }



                   /* bankId[0] = BankidID.get(position);
                    String[] str = bankId[0].split("_");
                    BankID = str[0];
                    txtAccountID.setText(str[1] + "");
                    number.setText(str[2] + "");*/
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        spin_Bank.setSelection(Integer.parseInt(arrayBank[0]));
                    }
                });
            } else {

            }
        } catch (Exception ex) {
            UtilMethods.INSTANCE.Error(this, "Something went wrong!!");
            // Toast.makeText(this, "Bank List is not found,Try again!!", Toast.LENGTH_SHORT).show();

        }


    }

    private void HitApi() {
        if (UtilMethods.INSTANCE.isNetworkAvialable(this)) {
            loader.show();
            loader.setCancelable(false);
            loader.setCanceledOnTouchOutside(false);
            UtilMethods.INSTANCE.GetBanklist(this, loader, new UtilMethods.ApiCallBack() {
                @Override
                public void onSucess(Object object) {
                    getBankData((BankListResponse) object);
                }
            });
        } else {
            UtilMethods.INSTANCE.dialogOk(this, getResources().getString(R.string.network_error_title),
                    getResources().getString(R.string.network_error_message), 2);
        }
    }

    public void getUserDetail() {
        try {
            loader.show();
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            String LoginResponse = UtilMethods.INSTANCE.getLoginPref(this);
            LoginResponse LoginDataResponse = new Gson().fromJson(LoginResponse, LoginResponse.class);
            Call<GetUserResponse> call = git.GetProfile(new BasicRequest(
                    LoginDataResponse.getData().getUserID(), LoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID,
                    UtilMethods.INSTANCE.getIMEI(UpdateProfileActivity.this),
                    "", BuildConfig.VERSION_NAME, UtilMethods.INSTANCE.getSerialNo(UpdateProfileActivity.this), LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));

            call.enqueue(new Callback<GetUserResponse>() {

                @Override
                public void onResponse(Call<GetUserResponse> call, retrofit2.Response<GetUserResponse> response) {

                    if (loader.isShowing())
                        loader.dismiss();
                    mGetUserResponse = response.body();
                    if (mGetUserResponse != null) {

                        if (!UtilMethods.INSTANCE.isPassChangeDialogShowing) {
                            if (mGetUserResponse.getPasswordExpired()) {

                                CustomAlertDialog customPassDialog = new CustomAlertDialog(UpdateProfileActivity.this, true);
                                customPassDialog.WarningWithSingleBtnCallBack(UpdateProfileActivity.this.getResources().getString(R.string.password_expired_msg), "Change", false, new CustomAlertDialog.DialogCallBack() {
                                    @Override
                                    public void onPositiveClick() {
                                        new ChangePassUtils(UpdateProfileActivity.this).changePassword(false, false);
                                    }

                                    @Override
                                    public void onNegativeClick() {

                                    }
                                });

                            }
                        }

                        if (mGetUserResponse.getStatuscode() == 1) {
                            UtilMethods.INSTANCE.setUserDataPref(UpdateProfileActivity.this, new Gson().toJson(mGetUserResponse));
                            UtilMethods.INSTANCE.setDoubleFactorPref(UpdateProfileActivity.this, mGetUserResponse.getUserInfo().isDoubleFactor());
                            setUserData();


                        } else if (response.body().getStatuscode() == -1) {

                            UtilMethods.INSTANCE.Error(UpdateProfileActivity.this, mGetUserResponse.getMsg() + "");
                        } else if (response.body().getStatuscode() == 0) {

                            UtilMethods.INSTANCE.Error(UpdateProfileActivity.this, mGetUserResponse.getMsg() + "");
                        } else {

                            UtilMethods.INSTANCE.Error(UpdateProfileActivity.this, mGetUserResponse.getMsg() + "");
                        }

                    } else {

                        UtilMethods.INSTANCE.Error(UpdateProfileActivity.this, getString(R.string.some_thing_error));
                    }
                }

                @Override
                public void onFailure(Call<GetUserResponse> call, Throwable t) {

                    try {
                        if (loader.isShowing()) {
                            loader.dismiss();
                        }
                        if (t.getMessage() != null && !t.getMessage().isEmpty()) {

                            if (t.getMessage().contains("No address associated with hostname")) {

                                UtilMethods.INSTANCE.Error(UpdateProfileActivity.this, getString(R.string.err_msg_network));


                            } else {

                                UtilMethods.INSTANCE.Error(UpdateProfileActivity.this, t.getMessage());


                            }

                        } else {

                            UtilMethods.INSTANCE.Error(UpdateProfileActivity.this, getString(R.string.some_thing_error));
                        }
                    } catch (IllegalStateException ise) {
                        loader.dismiss();

                        UtilMethods.INSTANCE.Error(UpdateProfileActivity.this, getString(R.string.some_thing_error));
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();

            UtilMethods.INSTANCE.Error(UpdateProfileActivity.this, getString(R.string.some_thing_error));
        }

    }

    void setUserData() {
        if (mGetUserResponse != null && mGetUserResponse.getUserInfo() != null) {
            if (mGetUserResponse.getUserInfo().getKycStatus() == 2 || mGetUserResponse.getUserInfo().getKycStatus() == 3) {
                kycBtn.setText("View KYC");
                submitBtn.setVisibility(View.GONE);
            } else {
                kycBtn.setText("Update KYC");
                submitBtn.setVisibility(View.VISIBLE);
            }
            if (mGetUserResponse.getUserInfo().getName() != null && !mGetUserResponse.getUserInfo().getName().isEmpty()) {
                nameEt.setText(mGetUserResponse.getUserInfo().getName());
            }
            if (mGetUserResponse.getUserInfo().getOutletName() != null && !mGetUserResponse.getUserInfo().getOutletName().isEmpty()) {
                emailIdEt.setText(mGetUserResponse.getUserInfo().getEmailID());
            }
            if (mGetUserResponse.getUserInfo().getEmailID() != null && !mGetUserResponse.getUserInfo().getEmailID().isEmpty()) {
                outletNameEt.setText(mGetUserResponse.getUserInfo().getOutletName());
            }
            if (mGetUserResponse.getUserInfo().getAddress() != null && !mGetUserResponse.getUserInfo().getAddress().isEmpty()) {
                addressEt.setText(mGetUserResponse.getUserInfo().getAddress());
            }
            if (mGetUserResponse.getUserInfo().getPincode() != null && !mGetUserResponse.getUserInfo().getPincode().isEmpty()) {
                pincodeEt.setText(mGetUserResponse.getUserInfo().getPincode());
            }

            if (mGetUserResponse.getUserInfo().getGstin() != null && !mGetUserResponse.getUserInfo().getGstin().isEmpty()) {
                gstinEt.setText(mGetUserResponse.getUserInfo().getGstin());
            }
            if (mGetUserResponse.getUserInfo().getAadhar() != null && !mGetUserResponse.getUserInfo().getAadhar().isEmpty()) {
                aadharEt.setText(mGetUserResponse.getUserInfo().getAadhar());
            }
            if (mGetUserResponse.getUserInfo().getPan() != null && !mGetUserResponse.getUserInfo().getPan().isEmpty()) {
                panEt.setText(mGetUserResponse.getUserInfo().getPan());
            }
            if (mGetUserResponse.getUserInfo().getMobileNo() != null && !mGetUserResponse.getUserInfo().getMobileNo().isEmpty()) {
                MobileNumberEt.setText(mGetUserResponse.getUserInfo().getMobileNo());
            }
            if (mGetUserResponse.getUserInfo().getAlternateMobile() != null && !mGetUserResponse.getUserInfo().getAlternateMobile().isEmpty()) {
                AlternateMobileNumberEt.setText(mGetUserResponse.getUserInfo().getAlternateMobile());
            }

            if (mGetUserResponse.getUserInfo().getDob() != null && !mGetUserResponse.getUserInfo().getDob().isEmpty()) {
                tv_dob.setText(mGetUserResponse.getUserInfo().getDob());
            }

            if (mGetUserResponse.getUserInfo().getIfsc() != null && !mGetUserResponse.getUserInfo().getIfsc().isEmpty()) {
                IFSCEt.setText(mGetUserResponse.getUserInfo().getIfsc());
            }

            if (mGetUserResponse.getUserInfo().getLandmark() != null && !mGetUserResponse.getUserInfo().getLandmark().isEmpty()) {
                LandmarkEt.setText(mGetUserResponse.getUserInfo().getLandmark());
            }

            if (mGetUserResponse.getUserInfo().getAccountNumber() != null && !mGetUserResponse.getUserInfo().getAccountNumber().isEmpty()) {
                AccountNumberEt.setText(mGetUserResponse.getUserInfo().getAccountNumber());
            }
            if (mGetUserResponse.getUserInfo().getAccountName() != null && !mGetUserResponse.getUserInfo().getAccountName().isEmpty()) {
                AccountNameEt.setText(mGetUserResponse.getUserInfo().getAccountName());
            }

            if (mGetUserResponse.getUserInfo().getBranchName() != null && !mGetUserResponse.getUserInfo().getBranchName().isEmpty()) {
                branchNameEt.setText(mGetUserResponse.getUserInfo().getBranchName());
            }


            if (mGetUserResponse.getUserInfo().getQualification() != null && !mGetUserResponse.getUserInfo().getQualification().isEmpty()) {
                ArrayAdapter myAdap = (ArrayAdapter) spin_Qualification.getAdapter();
                int spinnerPosition = myAdap.getPosition(mGetUserResponse.getUserInfo().getQualification());

                //set the default according to value
                spin_Qualification.setSelection(spinnerPosition);
            }


            /*---------setting Population Value---------*/

            if (mGetUserResponse.getUserInfo().getPoupulation() != null && !mGetUserResponse.getUserInfo().getPoupulation().isEmpty()) {
                ArrayAdapter myAdap = (ArrayAdapter) spin_Poupulation.getAdapter();
                int spinnerPosition = myAdap.getPosition(mGetUserResponse.getUserInfo().getPoupulation());

                //set the default according to value
                spin_Poupulation.setSelection(spinnerPosition);
            }

            /*-------------setting Location Value---------*/

            if (mGetUserResponse.getUserInfo().getLocationType() != null && !mGetUserResponse.getUserInfo().getLocationType().isEmpty()) {
                ArrayAdapter myAdap = (ArrayAdapter) spin_LocationType.getAdapter();
                int spinnerPosition = myAdap.getPosition(mGetUserResponse.getUserInfo().getLocationType());

                //set the default according to value
                spin_LocationType.setSelection(spinnerPosition);
            }


            /*------------setting ShopType Value---------*/

            if (mGetUserResponse.getUserInfo().getShoptype() != null && !mGetUserResponse.getUserInfo().getShoptype().isEmpty()) {
                ArrayAdapter myAdap = (ArrayAdapter) spin_ShopType.getAdapter();
                int spinnerPosition = myAdap.getPosition(mGetUserResponse.getUserInfo().getShoptype());

                //set the default according to value
                spin_ShopType.setSelection(spinnerPosition);
            }


            /*------------setting Bank Value---------*/

            if (mGetUserResponse.getUserInfo().getBankName() != null && !mGetUserResponse.getUserInfo().getBankName().isEmpty()) {
                selectedBank = mGetUserResponse.getUserInfo().getBankName();
                ArrayAdapter myAdap = (ArrayAdapter) spin_Bank.getAdapter();
                if (myAdap != null) {
                    int spinnerPosition = myAdap.getPosition(mGetUserResponse.getUserInfo().getBankName());

                    //set the default according to value
                    spin_Bank.setSelection(spinnerPosition);
                }

            }
        }
    }

    void showKycButton() {


        if (!nameEt.getText().toString().isEmpty() && !outletNameEt.getText().toString().isEmpty() &&
                !MobileNumberEt.getText().toString().trim().isEmpty() && MobileNumberEt.getText().toString().trim().length() == 10 &&
                !emailIdEt.getText().toString().isEmpty() && emailIdEt.getText().toString().contains("@") && emailIdEt.getText().toString().contains(".") &&
                !pincodeEt.getText().toString().isEmpty() && pincodeEt.getText().toString().length() >= 6 &&
                !addressEt.getText().toString().isEmpty() &&
                !aadharEt.getText().toString().isEmpty() && aadharEt.getText().toString().length() == 12 &&
                !panEt.getText().toString().isEmpty() && panEt.getText().toString().length() == 10 &&
                selectedBank != null && selectedBank.length() > 0 &&
                !selectedBank.equalsIgnoreCase("Select Bank") && !IFSCEt.getText().toString().isEmpty() &&
                !AccountNumberEt.getText().toString().isEmpty() && !AccountNameEt.getText().toString().isEmpty()) {

            kycBtn.setVisibility(View.VISIBLE);
            notice.setVisibility(View.GONE);
        } else {
            kycBtn.setVisibility(View.GONE);
            notice.setVisibility(View.VISIBLE);
        }
    }

    void updateProfile() {
        if (nameEt.getText().toString().isEmpty()) {
            nameEt.setError(getString(R.string.err_empty_field));
            nameEt.requestFocus();
        } else if (outletNameEt.getText().toString().isEmpty()) {
            outletNameEt.setError(getString(R.string.err_empty_field));
            outletNameEt.requestFocus();
        } else if (MobileNumberEt.getText().toString().isEmpty()) {
            MobileNumberEt.setError(getString(R.string.err_empty_field));
            MobileNumberEt.requestFocus();
        } else if (MobileNumberEt.getText().toString().length() != 10) {
            MobileNumberEt.setError(getString(R.string.err_msg_valid_mobile));
            MobileNumberEt.requestFocus();
        } else if (emailIdEt.getText().toString().isEmpty()) {
            emailIdEt.setError(getString(R.string.err_empty_field));
            emailIdEt.requestFocus();
        } else if (!emailIdEt.getText().toString().contains("@") || !emailIdEt.getText().toString().contains(".")) {
            emailIdEt.setError(getString(R.string.err_msg_email));
            emailIdEt.requestFocus();
        } else if (pincodeEt.getText().toString().isEmpty()) {
            pincodeEt.setError(getString(R.string.err_empty_field));
            pincodeEt.requestFocus();
        } else if (pincodeEt.getText().toString().length() < 6) {
            pincodeEt.setError(getString(R.string.pincode_error));
            pincodeEt.requestFocus();
        }/*else if (addressEt.getText().toString().isEmpty()) {
            aadharEt.setError(getString(R.string.err_empty_field));
            addressEt.requestFocus();
        }*//*else if (gstinEt.getText().toString().isEmpty()) {
            gstinEt.setError(getString(R.string.err_empty_field));
            gstinEt.requestFocus();
        }*//*else if (aadharEt.getText().toString().isEmpty()) {
            aadharEt.setError(getString(R.string.err_empty_field));
            aadharEt.requestFocus();
        }*/ else if (!aadharEt.getText().toString().isEmpty() && aadharEt.getText().toString().length() < 12) {
            aadharEt.setError(getString(R.string.invalid_aadhar));
            aadharEt.requestFocus();
        }/*else if (panEt.getText().toString().isEmpty()) {
            panEt.setError(getString(R.string.err_empty_field));
            panEt.requestFocus();
        }*/ else if (!panEt.getText().toString().isEmpty() && panEt.getText().toString().length() < 10) {
            panEt.setError(getString(R.string.invalid_pan));
            panEt.requestFocus();
        } else if (!spin_Bank.getSelectedItem().toString().isEmpty() && spin_Bank.getSelectedItem().toString().equalsIgnoreCase("Select Bank")) {
            txt_Bank.setError("Please Select Bank");

            /*tv_Bank.setVisibility(View.VISIBLE);
            tv_Bank.setText("Please Select Bank");
            tv_Bank.requestFocus();*/
        } else if (IFSCEt.getText().toString().isEmpty()) {
            IFSCEt.setError(getString(R.string.err_empty_field));
            IFSCEt.requestFocus();
        } else if (AccountNumberEt.getText().toString().isEmpty()) {
            AccountNumberEt.setError(getString(R.string.err_empty_field));
            AccountNumberEt.requestFocus();
        } else if (AccountNameEt.getText().toString().isEmpty()) {
            AccountNameEt.setError(getString(R.string.err_empty_field));
            AccountNameEt.requestFocus();
        } else if (branchNameEt.getText().toString().isEmpty()) {
            branchNameEt.setError(getString(R.string.err_empty_field));
            branchNameEt.requestFocus();
        } else {
            updateProfileApi();
        }
    }


    void updateKyc() {
        if (nameEt.getText().toString().isEmpty()) {
            nameEt.setError(getString(R.string.err_empty_field));
            nameEt.requestFocus();
        } else if (outletNameEt.getText().toString().isEmpty()) {
            outletNameEt.setError(getString(R.string.err_empty_field));
            outletNameEt.requestFocus();
        } else if (AlternateMobileNumberEt.getText().toString().isEmpty()) {
            AlternateMobileNumberEt.setError(getString(R.string.err_empty_field));
            AlternateMobileNumberEt.requestFocus();
        } else if (AlternateMobileNumberEt.getText().toString().length() != 10) {
            AlternateMobileNumberEt.setError(getString(R.string.err_msg_valid_mobile));
            AlternateMobileNumberEt.requestFocus();
        } else if (emailIdEt.getText().toString().isEmpty()) {
            emailIdEt.setError(getString(R.string.err_empty_field));
            emailIdEt.requestFocus();
        } else if (!emailIdEt.getText().toString().contains("@") || !emailIdEt.getText().toString().contains(".")) {
            emailIdEt.setError(getString(R.string.err_msg_email));
            emailIdEt.requestFocus();
        } else if (pincodeEt.getText().toString().isEmpty()) {
            pincodeEt.setError(getString(R.string.err_empty_field));
            pincodeEt.requestFocus();
        } else if (pincodeEt.getText().toString().length() < 6) {
            pincodeEt.setError(getString(R.string.pincode_error));
            pincodeEt.requestFocus();
        } else if (addressEt.getText().toString().isEmpty()) {
            aadharEt.setError(getString(R.string.err_empty_field));
            addressEt.requestFocus();
        }/*else if (gstinEt.getText().toString().isEmpty()) {
            gstinEt.setError(getString(R.string.err_empty_field));
            gstinEt.requestFocus();
        }*/ else if (aadharEt.getText().toString().isEmpty()) {
            aadharEt.setError(getString(R.string.err_empty_field));
            aadharEt.requestFocus();
        } else if (aadharEt.getText().toString().length() < 12) {
            aadharEt.setError(getString(R.string.invalid_aadhar));
            aadharEt.requestFocus();
        } else if (panEt.getText().toString().isEmpty()) {
            panEt.setError(getString(R.string.err_empty_field));
            panEt.requestFocus();
        } else if (panEt.getText().toString().length() < 10) {
            panEt.setError(getString(R.string.invalid_pan));
            panEt.requestFocus();
        } else if (tv_dob.getText().toString().isEmpty()) {
            txt_dob.setError(getString(R.string.err_empty_field));
            tv_dob.requestFocus();
        } else if (spin_Qualification.getSelectedItem().toString() != null && spin_Qualification.getSelectedItem().toString().equalsIgnoreCase("Select Qualification")) {
            txt_Qualification.setError("Please Select Qualification");
            txt_Qualification.requestFocus();
           /* tv_qualification.setVisibility(View.VISIBLE);
            tv_qualification.setText("Please Select Qualification");
            tv_qualification.requestFocus();*/
        } else if (spin_Poupulation.getSelectedItem().toString() != null && spin_Poupulation.getSelectedItem().toString().equalsIgnoreCase("Select Poupulation")) {
            txt_Poupulation.setError("Please Select Poupulation");
            txt_Poupulation.requestFocus();
            /*tv_Poupulation.setVisibility(View.VISIBLE);
            tv_Poupulation.setText("Please Select Poupulation");
            tv_Poupulation.requestFocus();*/

        } else if (spin_ShopType.getSelectedItem().toString() != null && spin_ShopType.getSelectedItem().toString().equalsIgnoreCase("Select Shop Type")) {
            txt_ShopType.setError("Please Select Shop Type");
            txt_ShopType.requestFocus();
            /*tv_ShopType.setVisibility(View.VISIBLE);
            tv_ShopType.setText("Please Select Shop Type");
            tv_ShopType.requestFocus();*/

        } else if (spin_Bank.getSelectedItem().toString() != null && spin_Bank.getSelectedItem().toString().equalsIgnoreCase("Select Bank")) {
            txt_Bank.setError("Please Select Bank");
            txt_Bank.requestFocus();
            /*tv_Bank.setVisibility(View.VISIBLE);
            tv_Bank.setText("Please Select Bank");
            tv_Bank.requestFocus();*/
        } else if (IFSCEt.getText().toString().isEmpty()) {
            IFSCEt.setError(getString(R.string.err_empty_field));
            IFSCEt.requestFocus();
        } else if (spin_LocationType.getSelectedItem().toString() != null && spin_LocationType.getSelectedItem().toString().equalsIgnoreCase("Select LocationType")) {
            txt_LocationType.setError("Please Select LocationType");
            txt_LocationType.requestFocus();
            /*tv_LocationType.setVisibility(View.VISIBLE);
            tv_LocationType.setText("Please Select LocationType");
            tv_LocationType.requestFocus();*/
        } else if (LandmarkEt.getText().toString().isEmpty()) {
            LandmarkEt.setError(getString(R.string.err_empty_field));
            LandmarkEt.requestFocus();
        } else if (AccountNumberEt.getText().toString().isEmpty()) {
            AccountNumberEt.setError(getString(R.string.err_empty_field));
            AccountNumberEt.requestFocus();
        } else if (AccountNameEt.getText().toString().isEmpty()) {
            AccountNameEt.setError(getString(R.string.err_empty_field));
            AccountNameEt.requestFocus();
        } else {
            updateKycApi();
            // imagePicker.choosePicture(true);
        }
    }


    public void updateProfileApi() {
        try {
            loader.show();
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            String LoginResponse = UtilMethods.INSTANCE.getLoginPref(this);
            LoginResponse LoginDataResponse = new Gson().fromJson(LoginResponse, LoginResponse.class);
            EditUser editUser = new EditUser(mGetUserResponse.getUserInfo().getCommRate(), mGetUserResponse.getUserInfo().getProfilePic(),
                    aadharEt.getText().toString(), panEt.getText().toString(), gstinEt.getText().toString(), addressEt.getText().toString(),
                    mGetUserResponse.getUserInfo().getUserID(), MobileNumberEt.getText().toString().trim()/*mGetUserResponse.getUserInfo().getMobileNo()*/,
                    nameEt.getText().toString(), outletNameEt.getText().toString(), emailIdEt.getText().toString(),
                    mGetUserResponse.getUserInfo().getGSTApplicable(), mGetUserResponse.getUserInfo().getTDSApplicable(),
                    mGetUserResponse.getUserInfo().getCCFGstApplicable(),
                    pincodeEt.getText().toString(), tv_dob.getText().toString(), selectedShopType, selectedQualification, selectedPopulation,
                    selectedLocation, LandmarkEt.getText().toString(), AlternateMobileNumberEt.getText().toString(), selectedBank,
                    IFSCEt.getText().toString(), AccountNumberEt.getText().toString(), AccountNameEt.getText().toString(), branchNameEt.getText().toString());

            Call<BasicResponse> call = git.UpdateProfile(new UpdateUserRequest(
                    LoginDataResponse.getData().getUserID(), LoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID,
                    UtilMethods.INSTANCE.getIMEI(this),
                    "", BuildConfig.VERSION_NAME, UtilMethods.INSTANCE.getSerialNo(this), LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession(), editUser));

            call.enqueue(new Callback<BasicResponse>() {

                @Override
                public void onResponse(Call<BasicResponse> call, retrofit2.Response<BasicResponse> response) {

                    if (loader.isShowing())
                        loader.dismiss();
                    BasicResponse data = response.body();
                    if (data != null) {
                        if (data.getStatuscode() == 1) {
                            UtilMethods.INSTANCE.Successful(UpdateProfileActivity.this, data.getMsg() + "");
                            mGetUserResponse.getUserInfo().setName(nameEt.getText().toString());
                            mGetUserResponse.getUserInfo().setOutletName(outletNameEt.getText().toString());
                            mGetUserResponse.getUserInfo().setEmailID(emailIdEt.getText().toString());
                            mGetUserResponse.getUserInfo().setPincode(pincodeEt.getText().toString());
                            mGetUserResponse.getUserInfo().setAddress(addressEt.getText().toString());
                            mGetUserResponse.getUserInfo().setGstin(gstinEt.getText().toString());
                            mGetUserResponse.getUserInfo().setAadhar(aadharEt.getText().toString());
                            mGetUserResponse.getUserInfo().setPan(panEt.getText().toString());
                            mGetUserResponse.getUserInfo().setMobileNo(MobileNumberEt.getText().toString());
                            mGetUserResponse.getUserInfo().setAlternateMobile(AlternateMobileNumberEt.getText().toString());
                            mGetUserResponse.getUserInfo().setDob(tv_dob.getText().toString());
                            mGetUserResponse.getUserInfo().setAccountName(AccountNameEt.getText().toString());
                            mGetUserResponse.getUserInfo().setAccountNumber(AccountNumberEt.getText().toString());
                            mGetUserResponse.getUserInfo().setLandmark(LandmarkEt.getText().toString());
                            mGetUserResponse.getUserInfo().setIfsc(IFSCEt.getText().toString());
                            mGetUserResponse.getUserInfo().setBankName(selectedBank);
                            mGetUserResponse.getUserInfo().setBranchName(branchNameEt.getText().toString());
                            mGetUserResponse.getUserInfo().setQualification(selectedQualification);
                            mGetUserResponse.getUserInfo().setLocationType(selectedLocation);
                            mGetUserResponse.getUserInfo().setShoptype(selectedShopType);
                            mGetUserResponse.getUserInfo().setPoupulation(selectedPopulation);
                            showKycButton();
                            setResult(RESULT_OK);
                            UtilMethods.INSTANCE.setUserDataPref(UpdateProfileActivity.this, new Gson().toJson(mGetUserResponse));
                        } else if (response.body().getStatuscode() == -1) {

                            UtilMethods.INSTANCE.Error(UpdateProfileActivity.this, data.getMsg() + "");
                        } else if (response.body().getStatuscode() == 0) {

                            UtilMethods.INSTANCE.Error(UpdateProfileActivity.this, data.getMsg() + "");
                        } else {

                            UtilMethods.INSTANCE.Error(UpdateProfileActivity.this, data.getMsg() + "");
                        }

                    } else {

                        UtilMethods.INSTANCE.Error(UpdateProfileActivity.this, getString(R.string.some_thing_error));
                    }
                }

                @Override
                public void onFailure(Call<BasicResponse> call, Throwable t) {


                    try {
                        if (loader.isShowing()) {
                            loader.dismiss();
                        }
                        if (t.getMessage() != null && !t.getMessage().isEmpty()) {

                            if (t.getMessage().contains("No address associated with hostname")) {

                                UtilMethods.INSTANCE.NetworkError(UpdateProfileActivity.this, getString(R.string.network_error_title), getString(R.string.err_msg_network));


                            } else {

                                UtilMethods.INSTANCE.Error(UpdateProfileActivity.this, t.getMessage());


                            }

                        } else {

                            UtilMethods.INSTANCE.Error(UpdateProfileActivity.this, getString(R.string.some_thing_error));
                        }
                    } catch (IllegalStateException ise) {
                        loader.dismiss();

                        UtilMethods.INSTANCE.Error(UpdateProfileActivity.this, getString(R.string.some_thing_error));
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();

            UtilMethods.INSTANCE.Error(UpdateProfileActivity.this, getString(R.string.some_thing_error));
        }

    }


    public void updateKycApi() {
        try {
            loader.show();
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            String LoginResponse = UtilMethods.INSTANCE.getLoginPref(this);
            LoginResponse LoginDataResponse = new Gson().fromJson(LoginResponse, LoginResponse.class);

            Call<UpdateKycResponse> call = git.UpdateKycApi(new BasicRequest(mGetUserResponse.getUserInfo().getUserID(),
                    LoginDataResponse.getData().getUserID(), LoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID,
                    UtilMethods.INSTANCE.getIMEI(this),
                    "", BuildConfig.VERSION_NAME, UtilMethods.INSTANCE.getSerialNo(this), LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));

            call.enqueue(new Callback<UpdateKycResponse>() {

                @Override
                public void onResponse(Call<UpdateKycResponse> call, retrofit2.Response<UpdateKycResponse> response) {

                    if (loader.isShowing())
                        loader.dismiss();
                    mUpdateKycResponse = response.body();
                    if (mUpdateKycResponse != null) {
                        if (mUpdateKycResponse.getStatuscode() == 1) {
                            //UtilMethods.INSTANCE.Successful(UpdateProfileActivity.this, data.getMsg() + "");
                            if (alertDialog != null && alertDialog.isShowing() && recyclerViewDocs != null) {
                                UploadDocsAdapter mUploadDocsAdapter = new UploadDocsAdapter(UpdateProfileActivity.this, mUpdateKycResponse.getUserDox());
                                recyclerViewDocs.setAdapter(mUploadDocsAdapter);
                                setKycBtnText();
                                setResult(RESULT_OK);
                            } else {
                                showDocumentDialog();
                            }
                        } else if (response.body().getStatuscode() == -1) {

                            UtilMethods.INSTANCE.Error(UpdateProfileActivity.this, mUpdateKycResponse.getMsg() + "");
                        } else if (response.body().getStatuscode() == 0) {

                            UtilMethods.INSTANCE.Error(UpdateProfileActivity.this, mUpdateKycResponse.getMsg() + "");
                        } else {

                            UtilMethods.INSTANCE.Error(UpdateProfileActivity.this, mUpdateKycResponse.getMsg() + "");
                        }

                    } else {

                        UtilMethods.INSTANCE.Error(UpdateProfileActivity.this, getString(R.string.some_thing_error));
                    }
                }

                @Override
                public void onFailure(Call<UpdateKycResponse> call, Throwable t) {


                    try {
                        if (loader.isShowing()) {
                            loader.dismiss();
                        }
                        if (t.getMessage() != null && !t.getMessage().isEmpty()) {

                            if (t.getMessage().contains("No address associated with hostname")) {

                                UtilMethods.INSTANCE.NetworkError(UpdateProfileActivity.this, getString(R.string.network_error_title), getString(R.string.err_msg_network));


                            } else {

                                UtilMethods.INSTANCE.Error(UpdateProfileActivity.this, t.getMessage());


                            }

                        } else {

                            UtilMethods.INSTANCE.Error(UpdateProfileActivity.this, getString(R.string.some_thing_error));
                        }
                    } catch (IllegalStateException ise) {
                        loader.dismiss();

                        UtilMethods.INSTANCE.Error(UpdateProfileActivity.this, getString(R.string.some_thing_error));
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();

            UtilMethods.INSTANCE.Error(UpdateProfileActivity.this, getString(R.string.some_thing_error));
        }

    }

    public void updateKycStatusApi(int outletId) {
        try {
            loader.show();
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            String LoginResponse = UtilMethods.INSTANCE.getLoginPref(this);
            LoginResponse LoginDataResponse = new Gson().fromJson(LoginResponse, LoginResponse.class);


            Call<UpdateKycResponse> call = git.UpdateKYCStatus(new UpdateKycStatusRequest(outletId,
                    LoginDataResponse.getData().getUserID(), LoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID,
                    UtilMethods.INSTANCE.getIMEI(this),
                    "", BuildConfig.VERSION_NAME, UtilMethods.INSTANCE.getSerialNo(this), LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));

            call.enqueue(new Callback<UpdateKycResponse>() {

                @Override
                public void onResponse(Call<UpdateKycResponse> call, retrofit2.Response<UpdateKycResponse> response) {
//                     // Log.e("Payment response", "hello response : " + new Gson().toJson(response.body()).toString());
                    if (loader.isShowing())
                        loader.dismiss();
                    UpdateKycResponse data = response.body();
                    if (data != null) {
                        if (data.getStatuscode() == 1) {
                            UtilMethods.INSTANCE.Successful(UpdateProfileActivity.this, data.getMsg() + "");
                            updateKycApi();
                        } else if (response.body().getStatuscode() == -1) {

                            UtilMethods.INSTANCE.Error(UpdateProfileActivity.this, data.getMsg() + "");
                        } else if (response.body().getStatuscode() == 0) {

                            UtilMethods.INSTANCE.Error(UpdateProfileActivity.this, data.getMsg() + "");
                        } else {

                            UtilMethods.INSTANCE.Error(UpdateProfileActivity.this, data.getMsg() + "");
                        }

                    } else {

                        UtilMethods.INSTANCE.Error(UpdateProfileActivity.this, getString(R.string.some_thing_error));
                    }
                }

                @Override
                public void onFailure(Call<UpdateKycResponse> call, Throwable t) {
                    // Log.e("response", "error ");

                    try {
                        if (loader.isShowing()) {
                            loader.dismiss();
                        }
                        if (t.getMessage() != null && !t.getMessage().isEmpty()) {

                            if (t.getMessage().contains("No address associated with hostname")) {

                                UtilMethods.INSTANCE.NetworkError(UpdateProfileActivity.this, getString(R.string.network_error_title), getString(R.string.err_msg_network));


                            } else {

                                UtilMethods.INSTANCE.Error(UpdateProfileActivity.this, t.getMessage());


                            }

                        } else {

                            UtilMethods.INSTANCE.Error(UpdateProfileActivity.this, getString(R.string.some_thing_error));
                        }
                    } catch (IllegalStateException ise) {
                        loader.dismiss();

                        UtilMethods.INSTANCE.Error(UpdateProfileActivity.this, getString(R.string.some_thing_error));
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();

            UtilMethods.INSTANCE.Error(UpdateProfileActivity.this, getString(R.string.some_thing_error));
        }

    }


    public void uploadDocApi(File file) {
        try {
            loader.show();
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            String LoginResponse = UtilMethods.INSTANCE.getLoginPref(this);
            LoginResponse LoginDataResponse = new Gson().fromJson(LoginResponse, LoginResponse.class);
            UploadDocRequest mUploadDocRequest = new UploadDocRequest(docTypeId, uid,
                    LoginDataResponse.getData().getUserID(), LoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID,
                    UtilMethods.INSTANCE.getIMEI(this),
                    "", BuildConfig.VERSION_NAME, UtilMethods.INSTANCE.getSerialNo(this), LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession());

            String req = new Gson().toJson(mUploadDocRequest);
            // Parsing any Media type file
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);

            MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), requestBody);

            RequestBody requestStr = RequestBody.create(MediaType.parse("text/plain"), req);


            Call<BasicResponse> call = git.UploadDocs(fileToUpload, requestStr);

            call.enqueue(new Callback<BasicResponse>() {

                @Override
                public void onResponse(Call<BasicResponse> call, retrofit2.Response<BasicResponse> response) {
//                     // Log.e("Payment response", "hello response : " + new Gson().toJson(response.body()).toString());
                    if (loader.isShowing())
                        loader.dismiss();
                    BasicResponse data = response.body();
                    if (data != null) {
                        if (data.getStatuscode() == 1) {
                            UtilMethods.INSTANCE.Successful(UpdateProfileActivity.this, data.getMsg() + "");
                            updateKycApi();
                        } else if (response.body().getStatuscode() == -1) {

                            UtilMethods.INSTANCE.Error(UpdateProfileActivity.this, data.getMsg() + "");
                        } else if (response.body().getStatuscode() == 0) {

                            UtilMethods.INSTANCE.Error(UpdateProfileActivity.this, data.getMsg() + "");
                        } else {

                            UtilMethods.INSTANCE.Error(UpdateProfileActivity.this, data.getMsg() + "");
                        }

                    } else {

                        UtilMethods.INSTANCE.Error(UpdateProfileActivity.this, getString(R.string.some_thing_error));
                    }
                }

                @Override
                public void onFailure(Call<BasicResponse> call, Throwable t) {
                    // Log.e("response", "error ");

                    try {
                        if (loader.isShowing()) {
                            loader.dismiss();
                        }
                        if (t.getMessage() != null && !t.getMessage().isEmpty()) {

                            if (t.getMessage().contains("No address associated with hostname")) {

                                UtilMethods.INSTANCE.NetworkError(UpdateProfileActivity.this, getString(R.string.network_error_title), getString(R.string.err_msg_network));


                            } else {

                                UtilMethods.INSTANCE.Error(UpdateProfileActivity.this, t.getMessage());


                            }

                        } else {

                            UtilMethods.INSTANCE.Error(UpdateProfileActivity.this, getString(R.string.some_thing_error));
                        }
                    } catch (IllegalStateException ise) {
                        loader.dismiss();

                        UtilMethods.INSTANCE.Error(UpdateProfileActivity.this, getString(R.string.some_thing_error));
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();

            UtilMethods.INSTANCE.Error(UpdateProfileActivity.this, getString(R.string.some_thing_error));
        }

    }


    void showDocumentDialog() {


        alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setCancelable(false);
        alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);


        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_upload_docs, null);
        alertDialog.setView(dialogView);
        AppCompatImageView closeBtn = dialogView.findViewById(R.id.closeBtn);
        recyclerViewDocs = dialogView.findViewById(R.id.recyclerView);
        recyclerViewDocs.setLayoutManager(new LinearLayoutManager(this));
        kycUploadDocBtn = dialogView.findViewById(R.id.kycBtn);
        setKycBtnText();
        UploadDocsAdapter mUploadDocsAdapter = new UploadDocsAdapter(this, mUpdateKycResponse.getUserDox());
        recyclerViewDocs.setAdapter(mUploadDocsAdapter);

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();

            }
        });


        kycUploadDocBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUpdateKycResponse.getUserDox().get(0).getKycStatus() == 1 || mUpdateKycResponse.getUserDox().get(0).getKycStatus() == 4 || mUpdateKycResponse.getUserDox().get(0).getKycStatus() == 5) {
                    updateKycStatusApi(mUpdateKycResponse.getUserDox().get(0).getOutletID());
                } else {

                }
            }
        });

        alertDialog.show();
        alertDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
       /* if (isFullScreen) {
            alertDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }*/
    }


    void setKycBtnText() {
        if (mUpdateKycResponse != null && mUpdateKycResponse.getUserDox() != null && mUpdateKycResponse.getUserDox().size() > 0) {
            if (mUpdateKycResponse.getUserDox().get(0).getKycStatus() == 1) {
                kycUploadDocBtn.setText("Apply for KYC");
                kycBtn.setText("Update KYC");
                submitBtn.setVisibility(View.VISIBLE);
                ViewCompat.setBackgroundTintList(kycUploadDocBtn, ColorStateList.valueOf
                        (getResources().getColor(R.color.grey)));
            } else if (mUpdateKycResponse.getUserDox().get(0).getKycStatus() == 2) {
                kycUploadDocBtn.setText("KYC Applied");
                kycBtn.setText("View KYC");
                submitBtn.setVisibility(View.GONE);
                ViewCompat.setBackgroundTintList(kycUploadDocBtn, ColorStateList.valueOf
                        (getResources().getColor(R.color.style_color_accent)));
            } else if (mUpdateKycResponse.getUserDox().get(0).getKycStatus() == 3) {
                kycUploadDocBtn.setText("KYC Completed");
                kycBtn.setText("View KYC");
                submitBtn.setVisibility(View.GONE);
                ViewCompat.setBackgroundTintList(kycUploadDocBtn, ColorStateList.valueOf
                        (getResources().getColor(R.color.green)));
            } else if (mUpdateKycResponse.getUserDox().get(0).getKycStatus() == 4) {
                kycUploadDocBtn.setText("Apply for REKYC");
                kycBtn.setText("Update KYC");
                submitBtn.setVisibility(View.VISIBLE);
                ViewCompat.setBackgroundTintList(kycUploadDocBtn, ColorStateList.valueOf
                        (getResources().getColor(R.color.grey)));
            } else if (mUpdateKycResponse.getUserDox().get(0).getKycStatus() == 5) {
                kycUploadDocBtn.setText("KYC rejected REAPPLY");
                kycBtn.setText("Update KYC");
                submitBtn.setVisibility(View.VISIBLE);
                ViewCompat.setBackgroundTintList(kycUploadDocBtn, ColorStateList.valueOf
                        (getResources().getColor(R.color.red)));
            }
        }
    }

   /* public void showInfo(String info, View v) {
        View view = LayoutInflater.from(this).inflate(R.layout.document_info, null);
        TextView infoTv = view.findViewById(R.id.info);
        infoTv.setText(info);
        BubbleDialog dialog = new BubbleDialog(this);
        dialog.addContentView(view);
        dialog.setClickedView(v);
        dialog.calBar(false);
        dialog.show();
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    }*/

    public void showInfo(String info, View v) {
        View view = LayoutInflater.from(this).inflate(R.layout.document_info, null);
        TextView infoTv = view.findViewById(R.id.info);
        infoTv.setText(info);
        BubbleDialog dialog = new BubbleDialog(this);
        dialog.addContentView(view);
        dialog.setClickedView(v);
        dialog.calBar(false);
        dialog.show();
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    }

    /*public void uploadDocs(Integer doc_TypeID, Integer userId) {
        docTypeId = doc_TypeID;
        uid = userId;
        imagePicker.choosePicture(true);
    }*/

    public void uploadDocs(int doc_TypeID, int userId, String docName) {
        docTypeId = doc_TypeID;
        uid = userId;

        if (doc_TypeID == 2) {
            selectImageDialog(docName);
        } else {

            imagePicker.choosePicture(true);
        }

    }


    public void selectImageDialog(String docName) {
        try {
            if (alertDialogSelectDoc != null && alertDialogSelectDoc.isShowing()) {
                return;
            }


            alertDialogSelectDoc = new AlertDialog.Builder(this).create();
            alertDialogSelectDoc.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            LayoutInflater inflater = getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.dialog_select_image_type, null);
            alertDialogSelectDoc.setView(dialogView);

            AppCompatImageView closeIv = dialogView.findViewById(R.id.closeIv);


            dialogView.findViewById(R.id.singleImage).setOnClickListener(v -> {
                alertDialogSelectDoc.dismiss();
                imagePicker.choosePicture(true);
            });


            dialogView.findViewById(R.id.multiImage).setOnClickListener(v -> {
                alertDialogSelectDoc.dismiss();
                startActivityForResult(new Intent(UpdateProfileActivity.this, MultiImageUploadActivity.class)
                        .putExtra("Title", docName + "")
                        .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), INTENT_MULTI_IMAGE);
            });

            closeIv.setOnClickListener(v -> alertDialogSelectDoc.dismiss());


            alertDialogSelectDoc.show();

        } catch (IllegalStateException ise) {

        } catch (IllegalArgumentException iae) {

        } catch (Exception e) {

        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == INTENT_MULTI_IMAGE && resultCode == RESULT_OK) {
            if (data != null) {
                uploadDocApi(new File(data.getStringExtra("DocPath")));
            } else {
                Toast.makeText(this, "Error in image file", Toast.LENGTH_SHORT).show();
            }

        } else {
            imagePicker.handleActivityResult(resultCode, requestCode, data);
            /* Toast.makeText(this,"Unable to capture image, please try again",Toast.LENGTH_LONG).show();*/
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        imagePicker.handlePermission(requestCode, grantResults);
    }

  /*  @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imagePicker.handleActivityResult(resultCode, requestCode, data);
        *//* Toast.makeText(this,"Unable to capture image, please try again",Toast.LENGTH_LONG).show();*//*

    }*/

  /*  @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        imagePicker.handlePermission(requestCode, grantResults);
    }*/
}
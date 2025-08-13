package com.solution.brothergroup.Fragments;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.core.view.ViewCompat;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;

import android.provider.Settings;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.safalrecharges.imagepicker.ImagePicker;
import com.solution.brothergroup.Activities.MainActivity;
import com.solution.brothergroup.Activities.SettlementBankListActivity;
import com.solution.brothergroup.Activities.UpdateProfileActivity;
import com.solution.brothergroup.Api.Request.BasicRequest;
import com.solution.brothergroup.Api.Response.BalanceResponse;
import com.solution.brothergroup.BuildConfig;
import com.solution.brothergroup.Fragments.Adapter.WalletBalanceAdapter;
import com.solution.brothergroup.Fragments.dto.BalanceType;
import com.solution.brothergroup.Fragments.dto.GetUserResponse;
import com.solution.brothergroup.Authentication.dto.LoginResponse;
import com.solution.brothergroup.MoveToWallet.ui.MoveToWalletNew;
import com.solution.brothergroup.R;
import com.solution.brothergroup.Util.ApiClient;
import com.solution.brothergroup.Util.ApplicationConstant;
import com.solution.brothergroup.Util.ChangePassUtils;
import com.solution.brothergroup.Util.CustomAlertDialog;
import com.solution.brothergroup.Util.EndPointInterface;
import com.solution.brothergroup.Util.UtilMethods;
import com.solution.brothergroup.usefull.CustomLoader;

import java.io.File;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Administrator on 04-11-2017.
 */

public class ProfileFragment extends Fragment implements View.OnClickListener {

    private static final int REQUEST_PERMISSIONS = 123;
    private int INTENT_PERMISSION_IMAGE = 4621;
    TextView editProfile;
    TextView kycStatus, moveBalanceTv;
    CustomLoader loader;
    View changePassword, createUser, changePinPassword;
    ChangePassUtils mChangePassUtils;
    View view;
    ArrayList<BalanceType> mBalanceTypes = new ArrayList<>();
    LoginResponse LoginPrefResponse;
    BalanceResponse balanceCheckResponse;
    SharedPreferences myPrefs;
    View kycDetailCard;
    GetUserResponse mGetUserResponse;
    SwitchCompat toggleDoubleFactor, toggleRealApi;
    private TextView outletName, realApiLabel;
    private View addressView, outletNameView;
    private TextView address;
    private View pincodeView;
    private TextView pincode;
    private View stateView;
    private TextView state;
    private View cityView;
    private TextView city;
    private View gstinView;
    private TextView gstin;
    private View aadharView;
    private TextView aadhar;
    private View panView;
    private TextView pan, doubleFactorLabel;
    private TextView name, nameUser;
    private TextView mobile;
    private TextView email, role;
    private ImageView logo;
    private CardView realApiLayoutContainer, pinPasswordLayoutContainer, doubleFactorLayoutContainer;
    private ImageView pinPasswordChangeLayout;
    private ImageView toggle;
    private boolean flagPasscode = false;
    private Toolbar toolbar;
    private boolean isDoubleFactorEnable;
    private DFStatusResponse mDfStatusResponse;
    private Dialog otpDialog;
    private WalletBalanceAdapter mWalletBalanceAdapter;
    private int INTENT_EDIT_PROFILE = 578;
    private ImagePicker imagePicker;
    private ImageView userImage;
    private Snackbar mSnackBar;
    View mainView;
    private boolean isEditProfie;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        loader = new CustomLoader(getActivity(), android.R.style.Theme_Translucent_NoTitleBar);

        UtilMethods.INSTANCE.setDashboardStatus(getActivity(), false);

        try {
            mChangePassUtils = ((MainActivity) getActivity()).mChangePassUtils;
            if (mChangePassUtils == null) {
                mChangePassUtils = new ChangePassUtils(getActivity());
            }
        } catch (NullPointerException npe) {
            mChangePassUtils = new ChangePassUtils(getActivity());
        } catch (Exception e) {
            mChangePassUtils = new ChangePassUtils(getActivity());
        }
        try {
            LoginPrefResponse = ((MainActivity) getActivity()).LoginPrefResponse;
            if (LoginPrefResponse == null) {
                LoginPrefResponse = new Gson().fromJson(UtilMethods.INSTANCE.getLoginPref(getActivity()), LoginResponse.class);
            }
        } catch (NullPointerException npe) {
            LoginPrefResponse = new Gson().fromJson(UtilMethods.INSTANCE.getLoginPref(getActivity()), LoginResponse.class);
        } catch (Exception e) {
            LoginPrefResponse = new Gson().fromJson(UtilMethods.INSTANCE.getLoginPref(getActivity()), LoginResponse.class);
        }

        try {
            balanceCheckResponse = ((MainActivity) getActivity()).balanceCheckResponse;
            if (balanceCheckResponse == null) {
                SharedPreferences myPreferences = getActivity().getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, getActivity().MODE_PRIVATE);
                String balanceResponse = myPreferences.getString(ApplicationConstant.INSTANCE.balancePref, null);
                balanceCheckResponse = new Gson().fromJson(balanceResponse, BalanceResponse.class);
            }
        } catch (NullPointerException npe) {
            SharedPreferences myPreferences = getActivity().getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, getActivity().MODE_PRIVATE);
            String balanceResponse = myPreferences.getString(ApplicationConstant.INSTANCE.balancePref, null);
            balanceCheckResponse = new Gson().fromJson(balanceResponse, BalanceResponse.class);
        } catch (Exception e) {
            SharedPreferences myPreferences = getActivity().getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, getActivity().MODE_PRIVATE);
            String balanceResponse = myPreferences.getString(ApplicationConstant.INSTANCE.balancePref, null);
            balanceCheckResponse = new Gson().fromJson(balanceResponse, BalanceResponse.class);
        }


        name = view.findViewById(R.id.name);
        mainView = view.findViewById(R.id.mainView);
        nameUser = view.findViewById(R.id.nameUser);
        doubleFactorLabel = view.findViewById(R.id.doubleFactorLabel);
        toggleRealApi = view.findViewById(R.id.toggleRealApi);
        toggleDoubleFactor = view.findViewById(R.id.toggleDoubleFactor);
        doubleFactorLayoutContainer = view.findViewById(R.id.doubleFactorLayoutContainer);
        editProfile = view.findViewById(R.id.editProfile);
        realApiLabel = view.findViewById(R.id.realApiLabel);
        outletName = view.findViewById(R.id.outletName);
        outletNameView = view.findViewById(R.id.outletNameView);
        addressView = view.findViewById(R.id.addressView);
        address = view.findViewById(R.id.address);
        kycDetailCard = view.findViewById(R.id.kycDetailCard);
        kycStatus = view.findViewById(R.id.kycStatus);
        pincodeView = view.findViewById(R.id.pincodeView);
        pincode = view.findViewById(R.id.pincode);
        stateView = view.findViewById(R.id.stateView);
        state = view.findViewById(R.id.state);
        cityView = view.findViewById(R.id.cityView);
        city = view.findViewById(R.id.city);
        gstinView = view.findViewById(R.id.gstinView);
        gstin = view.findViewById(R.id.gstin);
        aadharView = view.findViewById(R.id.aadharView);
        aadhar = view.findViewById(R.id.aadhar);
        panView = view.findViewById(R.id.panView);
        pan = view.findViewById(R.id.pan);
        mobile = view.findViewById(R.id.mobile);
        moveBalanceTv = view.findViewById(R.id.moveBalanceTv);
        moveBalanceTv.setOnClickListener(this);
        changePassword = view.findViewById(R.id.changePassword);
        changePinPassword = view.findViewById(R.id.changePinPassword);
        createUser = view.findViewById(R.id.createUser);
        email = view.findViewById(R.id.email);
        role = view.findViewById(R.id.role);
        changePassword.setOnClickListener(this);
        changePinPassword.setOnClickListener(this);
        createUser.setOnClickListener(this);
        realApiLayoutContainer = view.findViewById(R.id.realApiLayoutContainer);
        pinPasswordLayoutContainer = view.findViewById(R.id.pinPasswordLayoutContainer);
        pinPasswordChangeLayout = view.findViewById(R.id.changePin);
        toggle = view.findViewById(R.id.toggle);
        toggle.setOnClickListener(this);
        pinPasswordLayoutContainer.setOnClickListener(this);
        doubleFactorLayoutContainer.setOnClickListener(this);
        realApiLayoutContainer.setOnClickListener(this);
        editProfile.setOnClickListener(this);
        pinPasswordChangeLayout.setOnClickListener(this);
        initialValues();

        try {
            mGetUserResponse = ((MainActivity) getActivity()).mGetUserResponse;
            if (mGetUserResponse == null) {
                mGetUserResponse = new Gson().fromJson(UtilMethods.INSTANCE.getUserDataPref(getActivity()), GetUserResponse.class);
            }
        } catch (NullPointerException npe) {
            mGetUserResponse = new Gson().fromJson(UtilMethods.INSTANCE.getUserDataPref(getActivity()), GetUserResponse.class);
        } catch (Exception e) {
            mGetUserResponse = new Gson().fromJson(UtilMethods.INSTANCE.getUserDataPref(getActivity()), GetUserResponse.class);
        }

        RecyclerView balanceRecyclerView = view.findViewById(R.id.balanceRecyclerView);
        balanceRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mWalletBalanceAdapter = new WalletBalanceAdapter(getActivity(), mBalanceTypes);
        balanceRecyclerView.setAdapter(mWalletBalanceAdapter);
        showBalanceData();
        setUserData();
        getUserDetail();

        userImage = view.findViewById(R.id.userImage);
        userImage.setOnClickListener(this);
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.circle_logo);
        requestOptions.error(R.drawable.circle_logo);
        requestOptions.diskCacheStrategy(DiskCacheStrategy.NONE);
        requestOptions.skipMemoryCache(true);
        requestOptions.transform(new CircleCrop());

        Glide.with(getActivity())
                .load(ApplicationConstant.INSTANCE.baseProfilePicUrl + LoginPrefResponse.getData().getUserID() + ".png")
                .apply(requestOptions)
                .into(userImage);

        imagePicker = new ImagePicker(getActivity(), this, imageUri -> {
            Uri imgUri = imageUri;
            File selectedImageFile = new File(imgUri.getPath());
            // image.setImageURI(imgUri);


            Glide.with(getActivity())
                    .load(selectedImageFile)
                    .apply(requestOptions)
                    .into(userImage);

            uploadPicApi(selectedImageFile);
        }).setWithImageCrop();
        return view;
    }

    private void uploadPicApi(File selectedImageFile) {
        if (UtilMethods.INSTANCE.isNetworkAvialable(getActivity())) {
            loader.show();
            loader.setCancelable(false);
            loader.setCanceledOnTouchOutside(false);

            UtilMethods.INSTANCE.UploadProfilePic(getActivity(), selectedImageFile, loader, LoginPrefResponse, object -> {


            });

        } else {
            UtilMethods.INSTANCE.NetworkError(getActivity());
        }
    }

    void EnableDisableRealApi() {
        if (UtilMethods.INSTANCE.isNetworkAvialable(getActivity())) {
            loader.show();
            loader.setCancelable(false);
            loader.setCanceledOnTouchOutside(false);

            UtilMethods.INSTANCE.EnableDisableRealApi(getActivity(), toggleRealApi.isChecked() ? false : true, loader, new UtilMethods.ApiCallBack() {
                @Override
                public void onSucess(Object object) {
                    UtilMethods.INSTANCE.setIsRealApiPref(getActivity(), toggleRealApi.isChecked() ? false : true);
                    toggleRealApi.setChecked(toggleRealApi.isChecked() ? false : true);
                    if (toggleRealApi.isChecked()) {
                        realApiLabel.setText("Disable Real Api");
                    } else {
                        realApiLabel.setText("Enable Real Api");
                    }

                }
            });

        } else {
            UtilMethods.INSTANCE.NetworkError(getActivity());
        }
    }


    private void showBalanceData() {
        mBalanceTypes.clear();
        if (balanceCheckResponse != null && balanceCheckResponse.getBalanceData() != null) {
            if (balanceCheckResponse.getBalanceData().isBalance()) {
                mBalanceTypes.add(new BalanceType("Prepaid Wallet", balanceCheckResponse.getBalanceData().getBalance() + ""));
            }
            if (balanceCheckResponse.getBalanceData().isUBalance()) {
                mBalanceTypes.add(new BalanceType("Utility Wallet", balanceCheckResponse.getBalanceData().getuBalance() + ""));

            }
            if (balanceCheckResponse.getBalanceData().isBBalance()) {
                mBalanceTypes.add(new BalanceType("Bank Wallet", balanceCheckResponse.getBalanceData().getbBalance() + ""));
            }
            if (balanceCheckResponse.getBalanceData().isCBalance()) {
                mBalanceTypes.add(new BalanceType("Card Wallet", balanceCheckResponse.getBalanceData().getcBalance() + ""));
            }
            if (balanceCheckResponse.getBalanceData().isIDBalance()) {
                mBalanceTypes.add(new BalanceType("Registration Wallet", balanceCheckResponse.getBalanceData().getIdBalnace() + ""));
            }
            if (balanceCheckResponse.getBalanceData().isAEPSBalance()) {
                mBalanceTypes.add(new BalanceType("Aeps Wallet", balanceCheckResponse.getBalanceData().getAepsBalnace() + ""));
            }
            if (balanceCheckResponse.getBalanceData().isPacakgeBalance()) {
                mBalanceTypes.add(new BalanceType("Package Wallet", balanceCheckResponse.getBalanceData().getPackageBalnace() + ""));
            }
            if (LoginPrefResponse.isAccountStatement()) {
                mBalanceTypes.add(new BalanceType("Outstanding Wallet", balanceCheckResponse.getBalanceData().getOsBalance() + ""));
            }
            if (mBalanceTypes.size() > 1) {
                moveBalanceTv.setVisibility(View.VISIBLE);
            } else {
                moveBalanceTv.setVisibility(View.GONE);
            }
            mWalletBalanceAdapter.notifyDataSetChanged();
        } else {
            SharedPreferences myPreferences = getActivity().getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
            String balanceResponse = myPreferences.getString(ApplicationConstant.INSTANCE.balancePref, "");
            balanceCheckResponse = new Gson().fromJson(balanceResponse, BalanceResponse.class);
            if (balanceCheckResponse != null && balanceCheckResponse.getBalanceData() != null) {
                showBalanceData();
            } else {
                UtilMethods.INSTANCE.Balancecheck(getActivity(), null, new UtilMethods.ApiCallBack() {
                    @Override
                    public void onSucess(Object object) {
                        balanceCheckResponse = (BalanceResponse) object;
                        try {
                            ((MainActivity) getActivity()).balanceCheckResponse = balanceCheckResponse;
                        } catch (NullPointerException npe) {

                        }
                        if (balanceCheckResponse != null && balanceCheckResponse.getBalanceData() != null) {
                            showBalanceData();
                        }
                    }
                });
            }

        }

    }

    private void initialValues() {

        if (LoginPrefResponse.getData().getMobileNo() != null && LoginPrefResponse.getData().getMobileNo().length() > 0) {
            mobile.setText(LoginPrefResponse.getData().getMobileNo() + "");
        }
        if (LoginPrefResponse.getData().getEmailID() != null && LoginPrefResponse.getData().getEmailID().length() > 0) {
            email.setText(LoginPrefResponse.getData().getEmailID() + "");
        }

        if (LoginPrefResponse.getData().getName() != null && LoginPrefResponse.getData().getName().length() > 0) {
            name.setText(LoginPrefResponse.getData().getName() + "");
            nameUser.setText(LoginPrefResponse.getData().getName() + "");
        }
        if (LoginPrefResponse.getData().getRoleName() != null && LoginPrefResponse.getData().getRoleName().length() > 0) {
            role.setText(LoginPrefResponse.getData().getRoleName() + "");
        }

        //////////////////////////////////////////////////////////////////////////
        SharedPreferences myPreferences = getActivity().getSharedPreferences(ApplicationConstant.INSTANCE.prefNameLoginPref, MODE_PRIVATE);
        flagPasscode = myPreferences.getBoolean(ApplicationConstant.INSTANCE.PinPasscodeFlag, false);


        if (flagPasscode) {
            toggle.setImageResource(R.drawable.ic_toggle_on);
        } else {
            toggle.setImageResource(R.drawable.ic_toggle_off);
        }


    }

    @Override
    public void onClick(View v) {

        if (v == userImage) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                        ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, REQUEST_PERMISSIONS);

                } else {
                    imagePicker.choosePicture(true);
                }
            } else {
                imagePicker.choosePicture(true);
            }
        }
        if (v == editProfile) {
            if (isEditProfie) {
                startActivityForResult(new Intent(getActivity(), UpdateProfileActivity.class)
                        .putExtra("UserData", mGetUserResponse)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP), INTENT_EDIT_PROFILE);
            } else {
                /*startActivityForResult(new Intent(getActivity(), UpdateBankActivity.class)
                        .putExtra("UserData", mGetUserResponse)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP), INTENT_EDIT_PROFILE);*/
                startActivity(new Intent(getActivity(), SettlementBankListActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
            }
        }
        if (v == pinPasswordChangeLayout) {

            ////////////////////////////////////////////////////////////////////////////////
            LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.password_verification, null);

            final TextInputLayout passwordTextLayout = view.findViewById(R.id.passwordTextLayout);
            final EditText password = view.findViewById(R.id.password);
            final AppCompatButton okButton = view.findViewById(R.id.okButton);
            final AppCompatButton cancelButton = view.findViewById(R.id.cancelButton);

            final Dialog dialog = new Dialog(getActivity());

            dialog.setCancelable(false);
            dialog.setContentView(view);

            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            SharedPreferences myPrefs = getActivity().getSharedPreferences(ApplicationConstant.INSTANCE.prefNameLoginPref, MODE_PRIVATE);
            final String passwordpref = myPrefs.getString(ApplicationConstant.INSTANCE.UPassword, null);

            okButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (password.getText() != null && password.getText().toString().trim().equalsIgnoreCase(passwordpref)) {
                        passwordTextLayout.setErrorEnabled(false);
                        if (UtilMethods.INSTANCE.isNetworkAvialable(getActivity())) {
                            dialog.dismiss();
                            /*  UtilMethods.INSTANCE.changePinPassword(getActivity(), password.getText().toString().trim(), loader);*/
                        } else {
                            UtilMethods.INSTANCE.dialogOk(getActivity(), getResources().getString(R.string.err_msg_network),
                                    getResources().getString(R.string.err_msg_network), 2);
                        }

                        dialog.dismiss();

                    } else {
                        password.setError("Please enter a valid Password !!");
                        password.requestFocus();
                    }
                }
            });
            dialog.show();
            ////////////////////////////////////////////////////////////////////////////////
        }
        if (v == moveBalanceTv) {
            startActivity(new Intent(getActivity(), MoveToWalletNew.class)
                    .putExtra("items", mBalanceTypes)
                    .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
        }

        if (v == createUser) {
            if (UtilMethods.INSTANCE.isNetworkAvialable(getActivity())) {

                loader.show();
                loader.setCancelable(false);
                loader.setCanceledOnTouchOutside(false);

                //      UtilMethods.INSTANCE.GetSlab(getActivity(), loader);
            } else {
                UtilMethods.INSTANCE.NetworkError(getActivity(), getResources().getString(R.string.network_error_title),
                        getResources().getString(R.string.network_error_message));
            }
        }
        if (v == changePinPassword) {
            mChangePassUtils.changePassword(true, true);
        }
        if (v == changePassword) {
            mChangePassUtils.changePassword(false, true);


        }


        if (v == toggle || v == pinPasswordLayoutContainer) {

            LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.password_verification, null);

            final TextInputLayout passwordTextLayout = view.findViewById(R.id.passwordTextLayout);
            final EditText password = view.findViewById(R.id.password);
            final AppCompatButton okButton = view.findViewById(R.id.okButton);
            final AppCompatButton cancelButton = view.findViewById(R.id.cancelButton);

            final Dialog dialog = new Dialog(getActivity());

            dialog.setCancelable(false);
            dialog.setContentView(view);

            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            SharedPreferences myPrefs = getActivity().getSharedPreferences(ApplicationConstant.INSTANCE.prefNameLoginPref, MODE_PRIVATE);
            final String passwordpref = myPrefs.getString(ApplicationConstant.INSTANCE.UPassword, null);

            okButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (password.getText() != null && password.getText().toString().trim().equalsIgnoreCase(passwordpref)) {
                        passwordTextLayout.setErrorEnabled(false);
                        if (flagPasscode) {
                            toggle.setImageResource(R.drawable.ic_toggle_off);
                            UtilMethods.INSTANCE.pinpasscode(getActivity(), "", false);
                            flagPasscode = false;
                        } else {
                            toggle.setImageResource(R.drawable.ic_toggle_on);
                            UtilMethods.INSTANCE.pinpasscode(getActivity(), "", true);
                            flagPasscode = true;
                        }

                        dialog.dismiss();

                    } else {
                        password.setError("Please enter a valid Password !!");
                        password.requestFocus();
                    }
                }
            });
            dialog.show();
            ////////////////////////////////////////////////////////////////////////////////
        }

        if (v == doubleFactorLayoutContainer) {

            otpApi(!isDoubleFactorEnable, "", "");
        }
        if (v == realApiLayoutContainer) {

            EnableDisableRealApi();
        }


    }


    private void openOTPDialog(final Context context) {
        if (otpDialog != null && otpDialog.isShowing()) {
            return;
        }
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.verifyotp, null);

        final EditText edMobileOtp = view.findViewById(R.id.ed_mobile_otp);
        edMobileOtp.setInputType(InputType.TYPE_CLASS_TEXT);
        final TextInputLayout tilMobileOtp = view.findViewById(R.id.til_mobile_otp);
        final Button okButton = view.findViewById(R.id.okButton);
        final Button cancelButton = view.findViewById(R.id.cancelButton);
        final Button resendButton = view.findViewById(R.id.resendButton);
        resendButton.setVisibility(View.VISIBLE);
        otpDialog = new Dialog(context);
        otpDialog.setCancelable(false);
        otpDialog.setContentView(view);
        otpDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otpDialog.dismiss();
            }
        });

        edMobileOtp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s.length() < 6) {
                    tilMobileOtp.setError(context.getString(R.string.err_msg_otp));
                    okButton.setEnabled(false);
                } else {
                    tilMobileOtp.setErrorEnabled(false);
                    okButton.setEnabled(true);
                }
            }
        });

        resendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mDfStatusResponse != null) {
                    otpApi(!isDoubleFactorEnable, "OTP", mDfStatusResponse.getRefID());
                } else {
                    otpApi(!isDoubleFactorEnable, "OTP", "");
                }
            }
        });

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edMobileOtp.getText() != null && edMobileOtp.getText().length() == 6) {
                    tilMobileOtp.setErrorEnabled(false);
                    if (mDfStatusResponse != null) {
                        otpApi(!isDoubleFactorEnable, edMobileOtp.getText().toString(), mDfStatusResponse.getRefID());
                    } else {
                        otpApi(!isDoubleFactorEnable, edMobileOtp.getText().toString(), "");
                    }

                } else {
                    tilMobileOtp.setError(context.getString(R.string.err_msg_otp));
                }
            }
        });
        otpDialog.show();
    }


    void otpApi(boolean isDoubleFactor, String otp, final String refId) {
        if (UtilMethods.INSTANCE.isNetworkAvialable(getActivity())) {
            loader.show();
            loader.setCancelable(false);
            loader.setCanceledOnTouchOutside(false);

            UtilMethods.INSTANCE.DoubleFactorOtp(getActivity(), isDoubleFactor, otp, refId, loader, new UtilMethods.ApiCallBack() {
                @Override
                public void onSucess(Object object) {
                    mDfStatusResponse = (DFStatusResponse) object;

                    if (mDfStatusResponse.isOTPRequired()) {
                        openOTPDialog(getActivity());
                    } else if (mDfStatusResponse.getRefID() == null && !mDfStatusResponse.isOTPRequired() || mDfStatusResponse.getRefID().isEmpty() && !mDfStatusResponse.isOTPRequired()) {
                        UtilMethods.INSTANCE.Successful(getActivity(), mDfStatusResponse.getMsg() + "");
                        if (otpDialog != null && otpDialog.isShowing()) {
                            otpDialog.dismiss();
                        }
                        isDoubleFactorEnable = !isDoubleFactorEnable;
                        UtilMethods.INSTANCE.setDoubleFactorPref(getActivity(), isDoubleFactorEnable);
                        if (isDoubleFactorEnable) {
                            doubleFactorLabel.setText("Disable Double Factor");
                            toggleDoubleFactor.setChecked(true);
                        } else {
                            doubleFactorLabel.setText("Enable Double Factor");
                            toggleDoubleFactor.setChecked(false);
                        }
                    } else {
                        UtilMethods.INSTANCE.Successful(getActivity(), mDfStatusResponse.getMsg() + "");
                    }
                }
            });

        } else {
            UtilMethods.INSTANCE.NetworkError(getActivity(), getActivity().getResources().getString(R.string.err_msg_network_title),
                    getActivity().getResources().getString(R.string.err_msg_network));
        }
    }

    private void getDialogPin(final TextView password1) {
        ////////////////////////////////////////////////////////////////////////////////
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.pin_verification, null);

        final TextInputLayout passwordTextLayout = view.findViewById(R.id.passwordTextLayout);
        final EditText password = view.findViewById(R.id.password);
        final AppCompatButton okButton = view.findViewById(R.id.okButton);
        final AppCompatButton cancelButton = view.findViewById(R.id.cancelButton);

        final Dialog dialog = new Dialog(getActivity());

        dialog.setCancelable(false);
        dialog.setContentView(view);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        SharedPreferences myPrefs = getActivity().getSharedPreferences(ApplicationConstant.INSTANCE.prefNameLoginPref, MODE_PRIVATE);
        final String passwordpref = myPrefs.getString(ApplicationConstant.INSTANCE.UPassword, null);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (password.getText() != null && password.getText().toString().trim().equalsIgnoreCase(passwordpref)) {
                    passwordTextLayout.setErrorEnabled(false);
                    password1.setInputType(InputType.TYPE_CLASS_TEXT);

                    dialog.dismiss();

                } else {
                    password.setError("Please enter a valid Password !!");
                    password.requestFocus();
                }
            }
        });
        dialog.show();
    }


    public void getUserDetail() {
        try {
            loader.show();
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            String LoginResponse = UtilMethods.INSTANCE.getLoginPref(getActivity());
            LoginResponse LoginDataResponse = new Gson().fromJson(LoginResponse, LoginResponse.class);
            Call<GetUserResponse> call = git.GetProfile(new BasicRequest(
                    LoginDataResponse.getData().getUserID(), LoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID,
                    UtilMethods.INSTANCE.getIMEI(getActivity()),
                    "", BuildConfig.VERSION_NAME, UtilMethods.INSTANCE.getSerialNo(getActivity()), LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));

            call.enqueue(new Callback<GetUserResponse>() {

                @Override
                public void onResponse(Call<GetUserResponse> call, retrofit2.Response<GetUserResponse> response) {

                    if (loader.isShowing())
                        loader.dismiss();
                    mGetUserResponse = response.body();
                    if (mGetUserResponse != null) {
                        try {
                            ((MainActivity) getActivity()).mGetUserResponse = mGetUserResponse;
                        } catch (NullPointerException npe) {

                        }
                        if (!UtilMethods.INSTANCE.isPassChangeDialogShowing) {
                            if (mGetUserResponse.getPasswordExpired()) {

                                CustomAlertDialog customPassDialog = new CustomAlertDialog(getActivity(), true);
                                customPassDialog.WarningWithSingleBtnCallBack(getActivity().getResources().getString(R.string.password_expired_msg), "Change", false, new CustomAlertDialog.DialogCallBack() {
                                    @Override
                                    public void onPositiveClick() {
                                        new ChangePassUtils(getActivity()).changePassword(false, false);
                                    }

                                    @Override
                                    public void onNegativeClick() {

                                    }
                                });

                            }
                        }

                        if (mGetUserResponse.getStatuscode() == 1) {
                            UtilMethods.INSTANCE.setUserDataPref(getActivity(), new Gson().toJson(mGetUserResponse));
                            UtilMethods.INSTANCE.setDoubleFactorPref(getActivity(), mGetUserResponse.getUserInfo().isDoubleFactor());
                            setUserData();


                        } else if (response.body().getStatuscode() == -1) {

                            UtilMethods.INSTANCE.Error(getActivity(), mGetUserResponse.getMsg() + "");
                        } else if (response.body().getStatuscode() == 0) {

                            UtilMethods.INSTANCE.Error(getActivity(), mGetUserResponse.getMsg() + "");
                        } else {

                            UtilMethods.INSTANCE.Error(getActivity(), mGetUserResponse.getMsg() + "");
                        }

                    } else {

                        UtilMethods.INSTANCE.Error(getActivity(), getString(R.string.some_thing_error));
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

                                UtilMethods.INSTANCE.Error(getActivity(), getString(R.string.err_msg_network));


                            } else {

                                UtilMethods.INSTANCE.Error(getActivity(), t.getMessage());


                            }

                        } else {

                            UtilMethods.INSTANCE.Error(getActivity(), getString(R.string.some_thing_error));
                        }
                    } catch (IllegalStateException ise) {
                        loader.dismiss();

                        UtilMethods.INSTANCE.Error(getActivity(), getString(R.string.some_thing_error));
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();

            UtilMethods.INSTANCE.Error(getActivity(), getString(R.string.some_thing_error));
        }

    }


    void setUserData() {

        if (mGetUserResponse != null && mGetUserResponse.getUserInfo() != null) {
            if (mGetUserResponse.getUserInfo().getName() != null && !mGetUserResponse.getUserInfo().getName().isEmpty()) {
                name.setText(mGetUserResponse.getUserInfo().getName());
                nameUser.setText(mGetUserResponse.getUserInfo().getName());
            }
            if (mGetUserResponse.getUserInfo().getRole() != null && !mGetUserResponse.getUserInfo().getRole().isEmpty()) {
                role.setText(mGetUserResponse.getUserInfo().getRole());
            }
            if (mGetUserResponse.getUserInfo().getOutletName() != null && !mGetUserResponse.getUserInfo().getOutletName().isEmpty()) {
                outletName.setText(mGetUserResponse.getUserInfo().getOutletName());
            } else {
                outletNameView.setVisibility(View.GONE);
            }
            if (mGetUserResponse.getUserInfo().getAddress() != null && !mGetUserResponse.getUserInfo().getAddress().isEmpty()) {
                address.setText(mGetUserResponse.getUserInfo().getAddress());
            } else {
                addressView.setVisibility(View.GONE);
            }
            if (mGetUserResponse.getUserInfo().getPincode() != null && !mGetUserResponse.getUserInfo().getPincode().isEmpty()) {
                pincode.setText(mGetUserResponse.getUserInfo().getPincode());
            } else {
                pincodeView.setVisibility(View.GONE);
            }
            if (mGetUserResponse.getUserInfo().getStateName() != null && !mGetUserResponse.getUserInfo().getStateName().isEmpty()) {
                state.setText(mGetUserResponse.getUserInfo().getStateName());
            } else {
                stateView.setVisibility(View.GONE);
            }
            if (mGetUserResponse.getUserInfo().getCity() != null && !mGetUserResponse.getUserInfo().getCity().isEmpty()) {
                city.setText(mGetUserResponse.getUserInfo().getCity());
            } else {
                cityView.setVisibility(View.GONE);
            }
            if (mGetUserResponse.getUserInfo().getGstin() != null && !mGetUserResponse.getUserInfo().getGstin().isEmpty()) {
                kycDetailCard.setVisibility(View.VISIBLE);
                gstin.setText(mGetUserResponse.getUserInfo().getGstin());
            } else {
                gstinView.setVisibility(View.GONE);
            }
            if (mGetUserResponse.getUserInfo().getAadhar() != null && !mGetUserResponse.getUserInfo().getAadhar().isEmpty()) {
                kycDetailCard.setVisibility(View.VISIBLE);
                aadhar.setText(mGetUserResponse.getUserInfo().getAadhar());
            } else {
                aadharView.setVisibility(View.GONE);
            }
            if (mGetUserResponse.getUserInfo().getPan() != null && !mGetUserResponse.getUserInfo().getPan().isEmpty()) {
                kycDetailCard.setVisibility(View.VISIBLE);
                pan.setText(mGetUserResponse.getUserInfo().getPan());
            } else {
                panView.setVisibility(View.GONE);
            }
            if (mGetUserResponse.getUserInfo().getKycStatus() == 2 || mGetUserResponse.getUserInfo().getKycStatus() == 3) {
//                editProfile.setVisibility(View.GONE);
                editProfile.setText("Update Bank");
                isEditProfie = false;
            } else {
                editProfile.setText("Edit Profile");
                isEditProfie = true;
//                editProfile.setVisibility(View.VISIBLE);
            }

            if (mGetUserResponse.getUserInfo().getKycStatus() == 1) {
                kycStatus.setText("Apply for KYC");
                ViewCompat.setBackgroundTintList(kycStatus, ColorStateList.valueOf
                        (getResources().getColor(R.color.grey)));
            } else if (mGetUserResponse.getUserInfo().getKycStatus() == 2) {
                kycStatus.setText("KYC Applied");
                ViewCompat.setBackgroundTintList(kycStatus, ColorStateList.valueOf
                        (getResources().getColor(R.color.style_color_accent)));
            } else if (mGetUserResponse.getUserInfo().getKycStatus() == 3) {
                kycStatus.setText("KYC Completed");
                ViewCompat.setBackgroundTintList(kycStatus, ColorStateList.valueOf
                        (getResources().getColor(R.color.green)));
            } else if (mGetUserResponse.getUserInfo().getKycStatus() == 4) {
                kycStatus.setText("Apply for REKYC");
                ViewCompat.setBackgroundTintList(kycStatus, ColorStateList.valueOf
                        (getResources().getColor(R.color.grey)));
            } else if (mGetUserResponse.getUserInfo().getKycStatus() == 5) {
                kycStatus.setText("KYC rejected REAPPLY");
                ViewCompat.setBackgroundTintList(kycStatus, ColorStateList.valueOf
                        (getResources().getColor(R.color.red)));
            }

            if (UtilMethods.INSTANCE.getDoubleFactorPref(getActivity())) {
                isDoubleFactorEnable = true;
                doubleFactorLabel.setText("Disable Double Factor");
                toggleDoubleFactor.setChecked(true);
            } else {
                isDoubleFactorEnable = false;
                doubleFactorLabel.setText("Enable Double Factor");
                toggleDoubleFactor.setChecked(false);
            }

            toggleRealApi.setChecked(mGetUserResponse.getUserInfo().isRealAPI());
            if (mGetUserResponse.getUserInfo().isRealAPI()) {
                realApiLabel.setText("Disable Real Api");
            } else {
                realApiLabel.setText("Enable Real Api");
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
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
            imagePicker.handlePermission(requestCode, grantResults);
        }
    }

    void showWarningSnack(int stringId, String btn, final boolean isForSetting) {
        if (mSnackBar != null && mSnackBar.isShown()) {
            return;
        }

        mSnackBar = Snackbar.make(mainView, stringId,
                Snackbar.LENGTH_INDEFINITE).setAction(btn,
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (isForSetting) {
                            Intent intent = new Intent();
                            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            intent.addCategory(Intent.CATEGORY_DEFAULT);
                            intent.setData(Uri.parse("package:" + getActivity().getPackageName()));
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                            intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                            startActivity(intent);
                        } else {
                            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSIONS);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == INTENT_EDIT_PROFILE && resultCode == RESULT_OK) {
            getUserDetail();
        } else if (requestCode == INTENT_PERMISSION_IMAGE && resultCode == RESULT_OK) {
            imagePicker.choosePicture(true);
        } else {
            imagePicker.handleActivityResult(resultCode, requestCode, data);
        }
    }
}

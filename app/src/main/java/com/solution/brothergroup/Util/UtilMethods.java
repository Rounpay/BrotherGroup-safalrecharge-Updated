package com.solution.brothergroup.Util;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.media.MediaDrm;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.gson.Gson;
import com.solution.brothergroup.Activities.DMRReport;
import com.solution.brothergroup.Activities.MainActivity;
import com.solution.brothergroup.Activities.PaymentRequest;
import com.solution.brothergroup.Activities.UserDayBookActivity;
import com.solution.brothergroup.AddMoney.modelClass.InitiateUPIResponse;
import com.solution.brothergroup.AddMoney.modelClass.UpdateUPIRequest;
import com.solution.brothergroup.Api.Object.AssignedOpType;
import com.solution.brothergroup.Api.Object.BeneDetail;
import com.solution.brothergroup.Api.Object.CommissionDisplay;
import com.solution.brothergroup.Api.Object.IncentiveDetails;
import com.solution.brothergroup.Api.Object.NumberList;
import com.solution.brothergroup.Api.Object.OpTypeRollIdWiseServices;
import com.solution.brothergroup.Api.Object.OperatorList;
import com.solution.brothergroup.Api.Request.ASPayCollectRequest;
import com.solution.brothergroup.Api.Request.AccountOpenListRequest;
import com.solution.brothergroup.Api.Request.AchieveTargetRequest;
import com.solution.brothergroup.Api.Request.AddFundRequest;
import com.solution.brothergroup.Api.Request.AppGetAMRequest;
import com.solution.brothergroup.Api.Request.BalanceRequest;
import com.solution.brothergroup.Api.Request.BasicRequest;
import com.solution.brothergroup.Api.Request.CallBackRequest;
import com.solution.brothergroup.Api.Request.ChangePinPasswordRequest;
import com.solution.brothergroup.Api.Request.DFStatusRequest;
import com.solution.brothergroup.Api.Request.DmrRequest;
import com.solution.brothergroup.Api.Request.FetchBillRequest;
import com.solution.brothergroup.Api.Request.FosAccStmtAndCollReportRequest;
import com.solution.brothergroup.Api.Request.FundDCReportRequest;
import com.solution.brothergroup.Api.Request.FundTransferRequest;
import com.solution.brothergroup.Api.Request.IncentiveDetailRequest;
import com.solution.brothergroup.Api.Request.InitiateUPIRequest;
import com.solution.brothergroup.Api.Request.LapuRealCommissionRequest;
import com.solution.brothergroup.Api.Request.LogoutRequest;
import com.solution.brothergroup.Api.Request.MoveToBankReportRequest;
import com.solution.brothergroup.Api.Request.NewsRequest;
import com.solution.brothergroup.Api.Request.OnboardingRequest;
import com.solution.brothergroup.Api.Request.PurchaseTokenRequest;
import com.solution.brothergroup.Api.Request.RealApiChangeRequest;
import com.solution.brothergroup.Api.Request.RefundLogRequest;
import com.solution.brothergroup.Api.Request.ResendOtpRequest;
import com.solution.brothergroup.Api.Request.SlabRangeDetailRequest;
import com.solution.brothergroup.Api.Request.SubmitSocialDetailsRequest;
import com.solution.brothergroup.Api.Request.UpdateFcmRequest;
import com.solution.brothergroup.Api.Request.UserDayBookRequest;
import com.solution.brothergroup.Api.Response.AccountOpenListResponse;
import com.solution.brothergroup.Api.Response.AppGetAMResponse;
import com.solution.brothergroup.Api.Response.AppUserListResponse;
import com.solution.brothergroup.Api.Response.BalanceResponse;
import com.solution.brothergroup.Api.Response.BankListResponse;
import com.solution.brothergroup.Api.Response.BasicResponse;
import com.solution.brothergroup.Api.Response.DMTReceiptResponse;
import com.solution.brothergroup.Api.Response.FosAccStmtAndCollReportResponse;
import com.solution.brothergroup.Api.Response.FundreqToResponse;
import com.solution.brothergroup.Api.Response.GetBankAndPaymentModeResponse;
import com.solution.brothergroup.Api.Response.GetVAResponse;
import com.solution.brothergroup.Api.Response.NumberListResponse;
import com.solution.brothergroup.Api.Response.OnboardingResponse;
import com.solution.brothergroup.Api.Response.RechargeReportResponse;
import com.solution.brothergroup.Api.Response.SlabCommissionResponse;
import com.solution.brothergroup.Api.Response.SlabRangeDetailResponse;
import com.solution.brothergroup.Api.Response.WalletTypeResponse;
import com.solution.brothergroup.Authentication.LoginActivity;
import com.solution.brothergroup.Authentication.dto.LoginResponse;
import com.solution.brothergroup.BrowsePlan.dto.ResponsePlan;
import com.solution.brothergroup.BrowsePlan.ui.BrowsePlanScreenActivity;
import com.solution.brothergroup.BuildConfig;
import com.solution.brothergroup.DMRNEW.ui.BeneficiaryListScreen;
import com.solution.brothergroup.DMRNEW.ui.DMRReciept;
import com.solution.brothergroup.DMTWithPipe.networkAPI.UtilsMethodDMTPipe;
import com.solution.brothergroup.DTH.Activity.DthSubscriptionStatusActivity;
import com.solution.brothergroup.DTH.dto.DTHSubscriptionRequest;
import com.solution.brothergroup.DTH.dto.DthPackage;
import com.solution.brothergroup.DTH.dto.DthSubscriptionReportRequest;
import com.solution.brothergroup.DTH.dto.DthSubscriptionReportResponse;
import com.solution.brothergroup.DTH.dto.GetDthPackageChannelRequest;
import com.solution.brothergroup.DTH.dto.GetDthPackageRequest;
import com.solution.brothergroup.DTH.dto.GetDthPackageResponse;
import com.solution.brothergroup.DTH.dto.PincodeAreaRequest;
import com.solution.brothergroup.DTH.dto.PincodeAreaResponse;
import com.solution.brothergroup.FingPayAEPS.Fragment.AEPSFingerPrintEKycDialogFragment;
import com.solution.brothergroup.Fragments.DFStatusResponse;
import com.solution.brothergroup.R;
import com.solution.brothergroup.RECHARGEANDBBPS.API.FetchBillResponse;
import com.solution.brothergroup.ROffer.UI.ROfferActivity;
import com.solution.brothergroup.ROffer.dto.ROfferRequest;
import com.solution.brothergroup.ROffer.dto.RofferResponse;
import com.solution.brothergroup.Splash.Splash;
import com.solution.brothergroup.UPIPayment.UI.CompanyProfileResponse;
import com.solution.brothergroup.UpgradePacakge.dto.UpgradePackageRequest;
import com.solution.brothergroup.UpgradePacakge.dto.UpgradePackageResponse;
import com.solution.brothergroup.W2RRequest.dto.W2RRequest;
import com.solution.brothergroup.usefull.CustomLoader;

import java.io.File;
import java.io.IOException;
import java.net.NetworkInterface;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Vishnu on 24-08-2017.
 */

public enum UtilMethods {

    INSTANCE;
    public double getLattitude, getLongitude;
    public String deviceId;
    private int REQUEST_EXTERNAL_STORAGE = 1;
    public static boolean isPassChangeDialogShowing;
    public static boolean isFilterBottomSheetDialogShowing;
    EditText edMobileOtp;
    Preferences pref;
    AlertDialog alertDialogVersion;
    CustomAlertDialog customPassDialog;
    AlertDialog alertDialog;
    private AlertDialog alertDialogMobile;
    private CountDownTimer countDownTimer;
    boolean isLogin;
    private boolean isSocialEmailVerfiedChecked,isBioMetricDialogShown;
    private Dialog alertDialogSocial;
    private AlertDialog alertDialogDth;
    private int callOnBoardOId, callOnBoardBioAuthType;
    private String onboardingOTPReffId;
    private Dialog dialogOTP;

    public static final String md5Convertor(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
    public void CashFreeStatusCheck(String tid, CustomLoader loader, ApiCallBack apiCallBack) {

        try {

            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<InitiateUPIResponse> call = git.CashFreeStatusCheck(tid);

            call.enqueue(new Callback<InitiateUPIResponse>() {

                @Override
                public void onResponse(Call<InitiateUPIResponse> call, retrofit2.Response<InitiateUPIResponse> response) {
                    if (loader != null && loader.isShowing())
                        loader.dismiss();

                    if (response.isSuccessful()) {
                        if (response.body() != null) {


                            if (response.body().getStatuscode() == 1) {
                                if (apiCallBack != null) {
                                    apiCallBack.onSucess(response.body());
                                }
                            }

                            //Error(context,response.body().getMsg()+"");


                        }
                    }

                }

                @Override
                public void onFailure(Call<InitiateUPIResponse> call, Throwable t) {
                    if (loader != null && loader.isShowing())
                        loader.dismiss();
                   /* if (t.getMessage().contains("No address associated with hostname")) {
                        Error(context, context.getResources().getString(R.string.network_error));
                    } else {
                        Error(context, t.getMessage());
                    }*/
                }
            });

        } catch (Exception e) {
            if (loader != null && loader.isShowing())
                loader.dismiss();
            e.printStackTrace();
        }

    }

    public String getSerialNo(Context context) {
        String serialNo = "";

        if (android.os.Build.VERSION.SDK_INT >= 29) {
            serialNo = androidId(context);
        } else if (android.os.Build.VERSION.SDK_INT >= 26) {
            // only for gingerbread and newer versions

            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return null;
            }
            serialNo = android.os.Build.getSerial() + "";
        } else if (android.os.Build.VERSION.SDK_INT <= 25) {
            serialNo = android.os.Build.SERIAL + "";
        }
        // Log.e("seriolNo", serialNo);
        return serialNo;
    }

    public void UPIPaymentUpdate(UpdateUPIRequest upiRequest, CustomLoader loader, ApiCallBack apiCallBack) {

        try {

            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<InitiateUPIResponse> call = git.UPIPaymentUpdate(upiRequest);

            call.enqueue(new Callback<InitiateUPIResponse>() {

                @Override
                public void onResponse(Call<InitiateUPIResponse> call, retrofit2.Response<InitiateUPIResponse> response) {
                    if (loader != null && loader.isShowing())
                        loader.dismiss();

                    if (response.isSuccessful()) {
                        if (response.body() != null) {

                            if (response.body().getStatuscode() != null) {
                                if (response.body().getStatuscode() == 2 || response.body().getStatuscode() == 1) {
                                    if (apiCallBack != null) {
                                        apiCallBack.onSucess(response.body());
                                    }
                                }
                            } else {
                                //Error(context,response.body().getMsg()+"");
                            }

                        }
                    }

                }

                @Override
                public void onFailure(Call<InitiateUPIResponse> call, Throwable t) {
                    if (loader != null && loader.isShowing())
                        loader.dismiss();
                   /* if (t.getMessage().contains("No address associated with hostname")) {
                        Error(context, context.getResources().getString(R.string.network_error));
                    } else {
                        Error(context, t.getMessage());
                    }*/
                }
            });

        } catch (Exception e) {
            if (loader != null && loader.isShowing())
                loader.dismiss();
            e.printStackTrace();
        }

    }

    public void AEPSReport(final Activity context, String opTypeId, String topValue,
                           int status, String fromDate, String toDate, String transactionID, String accountNo, String childMobileNumnber, String isExport, boolean IsRecent, final CustomLoader loader, final ApiCallBack mApiCallBack) {
        try {
            String LoginResponse = getLoginPref(context);
            LoginResponse LoginDataResponse = new Gson().fromJson(LoginResponse, LoginResponse.class);
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<RechargeReportResponse> call = git.AEPSReport(new RechargeReportRequest(IsRecent, opTypeId, "0",
                    ApplicationConstant.INSTANCE.APP_ID,
                    getIMEI(context),
                    "", BuildConfig.VERSION_NAME, getSerialNo(context),
                    topValue, status, fromDate, toDate, transactionID, accountNo, childMobileNumnber, isExport,
                    LoginDataResponse.getData().getUserID()
                    , LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession(),
                    LoginDataResponse.getData().getLoginTypeID()));


            call.enqueue(new Callback<RechargeReportResponse>() {
                @Override
                public void onResponse(Call<RechargeReportResponse> call, final retrofit2.Response<RechargeReportResponse> response) {
                    try {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        if (response.body() != null && response.body().getStatuscode() != null) {
                            if (response.body().getStatuscode().equalsIgnoreCase("1")) {
                                // Balancecheck(context, loader, null);
                                if (mApiCallBack != null) {
                                    mApiCallBack.onSucess(response.body());
                                }
                                /*ActivityActivityMessage activityActivityMessage =
                                        new ActivityActivityMessage("recent", "" + new Gson().toJson(response.body()));
                                GlobalBus.getBus().post(activityActivityMessage);*/
                            } else if (response.body().getStatuscode().equalsIgnoreCase("-1")) {
                                if (response.body().getIsVersionValid() != null && response.body().getIsVersionValid().equalsIgnoreCase("false")) {
                                    versionDialog(context);
                                } else {
                                    Error(context, response.body().getMsg() + "");
                                }
                            }
                        }
                    } catch (Exception e) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                    }

                }

                @Override
                public void onFailure(Call<RechargeReportResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            loader.dismiss();
                    }
                    try {
                        if (t.getMessage() != null && !t.getMessage().isEmpty()) {

                            if (t.getMessage().contains("No address associated with hostname")) {
                                NetworkError(context, context.getResources().getString(R.string.err_msg_network_title),
                                        context.getResources().getString(R.string.err_msg_network));
                            } else {
                                Error(context, t.getMessage());

                            }

                        } else {
                            Error(context, context.getResources().getString(R.string.some_thing_error));

                        }
                    } catch (IllegalStateException ise) {
                        Error(context, ise.getMessage());

                    }

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String formatedDateTimeOfSlash(String dateStr) {
        if (dateStr != null && !dateStr.isEmpty()) {
            String formateDate = null;
            SimpleDateFormat inputFormat;

            inputFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");


            SimpleDateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy hh:mm aa");
            try {
                Date date = inputFormat.parse(dateStr);
                formateDate = outputFormat.format(date);
                return formateDate;
            } catch (ParseException e) {
                e.printStackTrace();
                return dateStr;
            }


        }
        return dateStr;
    }

    public String getAmountFormat(String amount) {
        StringBuilder strBind = new StringBuilder(amount);
        strBind.append(".00");
        return strBind.toString();
    }

    public String getAmountFormawitdot(String amount) {
        Pattern regex = Pattern.compile("[.]");
        Matcher matcher = regex.matcher(amount);
        if (matcher.find()) {
            // Do something
            return amount;
        } else {
            return getAmountFormat(amount);
        }

    }


    public void GetAccountOpenlist(final Activity context, int opTypeId, final CustomLoader loader, final UtilMethods.ApiCallBack mApiCallBack) {
        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);

            String LoginResponse = getLoginPref(context);
            LoginResponse LoginDataResponse = new Gson().fromJson(LoginResponse, LoginResponse.class);

            Call<AccountOpenListResponse> call = git.GetAccountOpeningList(new AccountOpenListRequest(opTypeId,
                    LoginDataResponse.getData().getUserID() + "", LoginDataResponse.getData().getLoginTypeID() + "",
                    ApplicationConstant.INSTANCE.APP_ID,
                    getIMEI(context),
                    "", BuildConfig.VERSION_NAME, getSerialNo(context),
                    LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));
            call.enqueue(new Callback<AccountOpenListResponse>() {
                @Override
                public void onResponse(Call<AccountOpenListResponse> call, final retrofit2.Response<AccountOpenListResponse> response) {

                    try {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        if (response.isSuccessful()) {
                            if (response.body() != null) {

                                if (response.body().getStatuscode() == 1) {
                                    if (response.body().getAccountOpeningDeatils() != null &&
                                            response.body().getAccountOpeningDeatils().size() > 0) {
                                        setAccountOpenList(context, new Gson().toJson(response.body()));
                                        if (mApiCallBack != null) {
                                            mApiCallBack.onSucess(response.body());
                                        }
                                    } else {
                                        Error(context, "Record not found");
                                    }
                                } else {
                                    if (!response.body().isVersionValid()) {
                                        versionDialog(context);
                                    } else {
                                        Error(context, response.body().getMsg() + "");
                                    }
                                }
                            }
                        } else {
                            apiErrorHandle(context, response.code(), response.message());
                        }
                    } catch (Exception e) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                    }

                }

                @Override
                public void onFailure(Call<AccountOpenListResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            loader.dismiss();
                    }
                    try {
                        apiFailureError(context, t);
                    } catch (IllegalStateException ise) {
                        Error(context, ise.getMessage());

                    }

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isVoiceEnable(Activity context) {
        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNameLoginPref, context.MODE_PRIVATE);
        return prefs.getBoolean(ApplicationConstant.INSTANCE.voiceEnablePref, true);

    }

    public String getOperatorList(Activity context) {
        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNonRemovalPref, context.MODE_PRIVATE);
        String numberList = prefs.getString(ApplicationConstant.INSTANCE.operatorListDataPref, "");

        return numberList;
    }

    public boolean appInstalledOrNot(Context context,
                                     String packageName) {
        try {
            context.getPackageManager().getPackageInfo(packageName,
                    PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }


    }

    public CompanyProfileResponse getCompanyProfileDetails(Context context) {
        CompanyProfileResponse response = null;
        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, context.MODE_PRIVATE);
        prefs.getString(ApplicationConstant.INSTANCE.companyPref, null);
        try {
            response = new Gson().fromJson(prefs.getString(ApplicationConstant.INSTANCE.companyPref, null), CompanyProfileResponse.class);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return response;
    }

    public int getWIDPref(Activity context) {
        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNameLoginPref, context.MODE_PRIVATE);
        return prefs.getInt(ApplicationConstant.INSTANCE.WidPref, 0);

    }




  /*  public  boolean isTakeCustomerNumber(Context context){
        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, context.MODE_PRIVATE);
        return prefs.getBoolean(ApplicationConstant.INSTANCE.TakeCustomerNoPref,false);
    }
*/

    public interface FundTransferCallBAck {
        void onSucessFund();

        void onSucessEdit();
    }

    public String getRecentLogin(Activity context) {
        SharedPreferences myPrefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, context.MODE_PRIVATE);
        String key = myPrefs.getString(ApplicationConstant.INSTANCE.regRecentLoginPref, null);
        return key;
    }

    public void fundTransferApi(final Activity context, boolean isMarkCredit, final String securityKey, boolean oType,
                                int uid, String remark, int walletType, String amount,
                                final String userName, final androidx.appcompat.app.AlertDialog alertDialogFundTransfer,
                                final CustomLoader loader, final FundTransferCallBAck mFundTransferCallBAck) {
        try {
            loader.show();
            String LoginResponse = getLoginPref(context);
            LoginResponse LoginDataResponse = new Gson().fromJson(LoginResponse, LoginResponse.class);
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);

            Call<AppUserListResponse> call = git.AppFundTransfer(new FundTransferRequest(isMarkCredit, securityKey, oType, uid, remark, walletType, 0, amount, LoginDataResponse.getData().getUserID(), LoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID,
                    getIMEI(context),
                    "", BuildConfig.VERSION_NAME, getSerialNo(context), LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));
            call.enqueue(new Callback<AppUserListResponse>() {

                @Override
                public void onResponse(Call<AppUserListResponse> call, retrofit2.Response<AppUserListResponse> response) {
                    // Log.e("Payment response", "hello response : " + new Gson().toJson(response.body()).toString());
                    if (loader.isShowing())
                        loader.dismiss();
                    AppUserListResponse data = response.body();
                    if (data != null) {
                        if (data.getStatuscode() == 1) {
                            if (mFundTransferCallBAck != null) {
                                mFundTransferCallBAck.onSucessFund();
                            }
                            alertDialogFundTransfer.dismiss();
                            Successful(context, data.getMsg().replace("{User}", userName));
                        } else if (response.body().getStatuscode() == -1) {

                            Error(context, data.getMsg().replace("{User}", userName));
                        } else if (response.body().getStatuscode() == 0) {

                            Error(context, data.getMsg().replace("{User}", userName));
                        } else {

                            Error(context, data.getMsg().replace("{User}", userName));
                        }

                    } else {

                        Error(context, context.getResources().getString(R.string.some_thing_error));
                    }
                }

                @Override
                public void onFailure(Call<AppUserListResponse> call, Throwable t) {
                    // Log.e("response", "error ");

                    try {
                        if (loader.isShowing()) {
                            loader.dismiss();
                        }
                        if (t.getMessage() != null && !t.getMessage().isEmpty()) {

                            if (t.getMessage().contains("No address associated with hostname")) {

                                Error(context, context.getResources().getString(R.string.err_msg_network));


                            } else {

                                Error(context, t.getMessage());


                            }

                        } else {

                            Error(context, context.getResources().getString(R.string.some_thing_error));
                        }
                    } catch (IllegalStateException ise) {
                        loader.dismiss();

                        Error(context, context.getResources().getString(R.string.some_thing_error));
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();

            Error(context, context.getResources().getString(R.string.some_thing_error));
        }

    }

    public void UploadProfilePic(final Activity context, File selectedFile, final CustomLoader loader, LoginResponse LoginDataResponse,
                                 final ApiCallBack mApiCallBack) {
        try {


            BasicRequest mBasicRequest = new BasicRequest(
                    LoginDataResponse.getData().getUserID() + "", LoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID, getIMEI(context), "", BuildConfig.VERSION_NAME, getSerialNo(context),
                    LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession());

            String req = new Gson().toJson(mBasicRequest);
            // Parsing any Media type file
            MultipartBody.Part fileToUpload = null;
            if (selectedFile != null) {
                RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), selectedFile);
                if (selectedFile != null) {
                    fileToUpload = MultipartBody.Part.createFormData("file", selectedFile.getName(), requestBody);
                }
            }
            RequestBody requestStr = RequestBody.create(MediaType.parse("text/plain"), req);
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<BasicResponse> call = git.UploadProfile(fileToUpload, requestStr);
            call.enqueue(new Callback<BasicResponse>() {

                @Override
                public void onResponse(Call<BasicResponse> call, retrofit2.Response<BasicResponse> response) {

                    try {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                if (response.body().getStatuscode() == 1) {
                                    // setFundreqToList(context, new Gson().toJson(response.body()).toString());
                                    Successful(context, response.body().getMsg() + "");

                                    if (mApiCallBack != null) {
                                        mApiCallBack.onSucess(response.body());
                                    }

                                } else if (response.body().getStatuscode() == -1) {
                                    if (response.body().getVersionValid() == false) {
                                        versionDialog(context);
                                    } else {
                                        Error(context, response.body().getMsg() + "");
                                    }
                                } else {
                                    Error(context, response.body().getMsg() + "");
                                }

                            } else {
                                Error(context, context.getResources().getString(R.string.some_thing_error));
                            }
                        } else {
                            apiErrorHandle(context, response.code(), response.message());
                        }
                    } catch (Exception e) {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        Error(context, e.getMessage() + "");
                    }

                }

                @Override
                public void onFailure(Call<BasicResponse> call, Throwable t) {

                    try {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        apiFailureError(context, t);
                        /*  Error(context, context.getResources().getString(R.string.err_something_went_wrong) + "");*/
                    } catch (IllegalStateException ise) {
                        Error(context, ise.getMessage());
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                    }
                }
            });

        } catch (Exception e) {

            if (loader != null) {
                if (loader.isShowing()) {
                    loader.dismiss();
                }
            }
            Error(context, e.getMessage() + "");
            e.printStackTrace();
        }

    }

    ////////////////////////////DTh Subscription/////////////////
    public void GetDthPackage(final Activity context, String oid, CustomLoader loader, LoginResponse LoginDataResponse,
                              final ApiCallBack mApiCallBack) {
        try {
            loader.show();
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<GetDthPackageResponse> call = git.GetDTHPackage(new GetDthPackageRequest(oid,
                    LoginDataResponse.getData().getUserID() + "", LoginDataResponse.getData().getLoginTypeID() + "",
                    ApplicationConstant.INSTANCE.APP_ID,
                    getIMEI(context),
                    "", BuildConfig.VERSION_NAME, getSerialNo(context), LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));

            call.enqueue(new Callback<GetDthPackageResponse>() {

                @Override
                public void onResponse(Call<GetDthPackageResponse> call, retrofit2.Response<GetDthPackageResponse> response) {
                    loader.dismiss();
                    if (response.body() != null && response.body().getStatuscode() == 1) {
                        if (response.body().getDthPackage() != null && response.body().getDthPackage().size() > 0) {
                            if (mApiCallBack != null) {
                                mApiCallBack.onSucess(response.body());
                            }

                        } else {
                            Error(context, "Package Not Found.");
                        }

                    } else {
                        if (response.body() != null && response.body().getMsg() != null) {
                            Error(context, response.body().getMsg() + "");
                        } else {
                            Error(context, context.getResources().getString(R.string.some_thing_error) + "");
                        }
                    }
                }

                @Override
                public void onFailure(Call<GetDthPackageResponse> call, Throwable t) {
                    loader.dismiss();
                    Error(context, t.getMessage() + "");
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            loader.dismiss();
            Error(context, e.getMessage() + "");
        }

    }


    public void GetDthChannel(final Activity context, String pid, String oid, CustomLoader loader, final ApiCallBack mApiCallBack) {
        try {
            loader.show();
            String LoginResponse = getLoginPref(context);
            LoginResponse LoginDataResponse = new Gson().fromJson(LoginResponse, LoginResponse.class);
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<GetDthPackageResponse> call = git.DTHChannelByPackageID(new GetDthPackageChannelRequest(pid, oid,
                    LoginDataResponse.getData().getUserID() + "", LoginDataResponse.getData().getLoginTypeID() + "",
                    ApplicationConstant.INSTANCE.APP_ID,
                    getIMEI(context),
                    "", BuildConfig.VERSION_NAME, getSerialNo(context), LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));

            call.enqueue(new Callback<GetDthPackageResponse>() {

                @Override
                public void onResponse(Call<GetDthPackageResponse> call, retrofit2.Response<GetDthPackageResponse> response) {
                    loader.dismiss();
                    if (response.body() != null && response.body().getStatuscode() == 1) {
                        if (response.body().getDthChannels() != null && response.body().getDthChannels().size() > 0) {
                            if (mApiCallBack != null) {
                                mApiCallBack.onSucess(response.body());
                            }

                        } else {
                            Error(context, "Channel Not Found.");
                        }

                    } else {
                        if (response.body() != null && response.body().getMsg() != null) {
                            Error(context, response.body().getMsg() + "");
                        } else {
                            Error(context, context.getResources().getString(R.string.some_thing_error) + "");
                        }
                    }
                }

                @Override
                public void onFailure(Call<GetDthPackageResponse> call, Throwable t) {
                    loader.dismiss();
                    Error(context, t.getMessage() + "");
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            loader.dismiss();
            Error(context, e.getMessage() + "");
        }

    }

    public void DthSubscriptionReport(final Activity context, String opTypeId, String topValue,
                                      int status, int bookingStatus, String fromDate, String toDate, String transactionID, String accountNo,
                                      String childMobileNumnber, String isExport, boolean IsRecent,
                                      final CustomLoader loader, LoginResponse LoginDataResponse, final ApiCallBack mApiCallBack) {
        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<DthSubscriptionReportResponse> call = git.DTHSubscriptionReport(new DthSubscriptionReportRequest(IsRecent, opTypeId, "0",
                    ApplicationConstant.INSTANCE.APP_ID,
                    getIMEI(context),
                    "", BuildConfig.VERSION_NAME, getSerialNo(context),
                    topValue, status, bookingStatus, fromDate, toDate, transactionID, accountNo, childMobileNumnber, isExport,
                    LoginDataResponse.getData().getUserID()
                    , LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession(),
                    LoginDataResponse.getData().getLoginTypeID()));
            call.enqueue(new Callback<DthSubscriptionReportResponse>() {
                @Override
                public void onResponse(Call<DthSubscriptionReportResponse> call, final retrofit2.Response<DthSubscriptionReportResponse> response) {
                    try {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        if (response.body() != null && response.body().getStatuscode() != null) {
                            if (response.body().getStatuscode().equalsIgnoreCase("1")) {
                                if (mApiCallBack != null) {
                                    mApiCallBack.onSucess(response.body());
                                }

                            } else if (response.body().getStatuscode().equalsIgnoreCase("-1")) {
                                if (response.body().getIsVersionValid() != null && response.body().getIsVersionValid().equalsIgnoreCase("false")) {
                                    versionDialog(context);
                                } else {
                                    Error(context, response.body().getMsg() + "");
                                }
                            }
                        }
                    } catch (Exception e) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                    }

                }

                @Override
                public void onFailure(Call<DthSubscriptionReportResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            loader.dismiss();
                    }
                    try {
                        if (t.getMessage() != null && !t.getMessage().isEmpty()) {

                            NetworkError(context, context.getResources().getString(R.string.err_msg_network_title), context.getResources().getString(R.string.err_msg_network));

                        } else {
                            Error(context, context.getResources().getString(R.string.some_thing_error));

                        }
                    } catch (IllegalStateException ise) {
                        Error(context, ise.getMessage());

                    }

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void DTHSubscription(final Activity context, boolean isReal, int pid, String surname, String gender, int areaID, final String opName, String customer, String address, String pincode,
                                String customerNo, String GeoCode, String securityKey, DthPackage mthPackage,
                                final CustomLoader loader, LoginResponse LoginDataResponse) {
        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<RechargeCResponse> call = git.DTHSubscription(new DTHSubscriptionRequest(
                    ApplicationConstant.INSTANCE.APP_ID,
                    getIMEI(context),
                    "", BuildConfig.VERSION_NAME, getSerialNo(context), LoginDataResponse.getData().getLoginTypeID(), pid,
                    customer, customerNo, address, pincode,
                    LoginDataResponse.getData().getUserID(), LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession(), GeoCode,
                    securityKey, isReal, surname, gender, areaID)
            );
            call.enqueue(new Callback<RechargeCResponse>() {
                @Override
                public void onResponse(Call<RechargeCResponse> call, final retrofit2.Response<RechargeCResponse> response) {
                    try {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        if (response.body() != null) {

                            if (response.body().getStatuscode() == 1 || response.body().getStatuscode() == 2) {
                                // Processing(context, response.body().getMsg() + "");
                                MediaPlayer mp = MediaPlayer.create(context, R.raw.beep);
                                mp.start();
                                context.startActivity(new Intent(context, DthSubscriptionStatusActivity.class)
                                        .putExtra("MESSAGE", response.body().getMsg() + "")
                                        .putExtra("STATUS", response.body().getStatuscode())
                                        .putExtra("LIVE_ID", response.body().getLiveID() + "")
                                        .putExtra("TRANSACTION_ID", response.body().getTransactionID() + "")
                                        .putExtra("OP_NAME", opName)
                                        .putExtra("AMOUNT", mthPackage.getPackageMRP() + "")
                                        .putExtra("NUMBER", customerNo)
                                        .putExtra("PACKAGE_NAME", mthPackage.getPackageName())
                                        .putExtra("COMM", ""));
                            } /*else if (response.body().getStatuscode()==2) {
                                Successful(context, response.body().getMsg());
                            }*/ else if (response.body().getStatuscode() == 3) {
                                if (response.body().getMsg().contains("(NRAF-0)")) {
                                    String dialogMsgTxt = context.getResources().getString(R.string.realapi_popup_english_dthsubscription_msg, opName, opName) + "\n\n" +
                                            context.getResources().getString(R.string.realapi_popup_hindi_dthsubscription_msg, opName);


                                    new CustomAlertDialog(context, true).enableRealApiDialog(dialogMsgTxt, (mobile, emailId) ->
                                            EnableDisableRealApi(context, true, loader, object ->
                                                    Successful(context, ((BasicResponse) object).getMsg() + "")));
                                } else {
                                    context.startActivity(new Intent(context, DthSubscriptionStatusActivity.class)
                                            .putExtra("MESSAGE", response.body().getMsg() + "")
                                            .putExtra("STATUS", response.body().getStatuscode())
                                            .putExtra("LIVE_ID", response.body().getLiveID() + "")
                                            .putExtra("TRANSACTION_ID", response.body().getTransactionID() + "")
                                            .putExtra("OP_NAME", opName)
                                            .putExtra("AMOUNT", mthPackage.getPackageMRP() + "")
                                            .putExtra("NUMBER", customerNo)
                                            .putExtra("PACKAGE_NAME", mthPackage.getPackageName())
                                            .putExtra("COMM", ""));
                                    // Failed(context, response.body().getMsg());
                                }
                            } else if (response.body().getStatuscode() == -1) {
                                if (response.body().getIsVersionValid() != null && response.body().getIsVersionValid().equalsIgnoreCase("false")) {
                                    versionDialog(context);
                                } else {
                                    Error(context, response.body().getMsg() + "");
                                }
                            }
                            ActivityActivityMessage activityActivityMessage =
                                    new ActivityActivityMessage("" + new Gson().toJson(response.body()).toString(), "refreshvalue");
                            GlobalBus.getBus().post(activityActivityMessage);
                            BalancecheckNew(context, null, null, null);
                        }
                    } catch (Exception e) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                    }

                }

                @Override
                public void onFailure(Call<RechargeCResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            loader.dismiss();
                    }
                    try {
                        if (t.getMessage() != null && !t.getMessage().isEmpty()) {

                            if (t.getMessage().contains("No address associated with hostname")) {
                                NetworkError(context, context.getResources().getString(R.string.err_msg_network_title),
                                        context.getResources().getString(R.string.err_msg_network));
                            } else {
                                Processing(context, "Recharge request Accepted");

                            }

                        } else {
                            Processing(context, "Recharge request Accepted");

                        }
                    } catch (IllegalStateException ise) {
                        Error(context, ise.getMessage());

                    }


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void PincodeArea(final Activity context, String pincode, final CustomLoader loader, LoginResponse LoginDataResponse,
                            ApiCallBack mApiCallBack) {
        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<PincodeAreaResponse> call = git.GetPincodeArea(new PincodeAreaRequest(pincode,
                    ApplicationConstant.INSTANCE.APP_ID,
                    getIMEI(context),
                    "", BuildConfig.VERSION_NAME, getSerialNo(context), LoginDataResponse.getData().getLoginTypeID(),
                    LoginDataResponse.getData().getUserID(), LoginDataResponse.getData().getSessionID(),
                    LoginDataResponse.getData().getSession())
            );
            call.enqueue(new Callback<PincodeAreaResponse>() {
                @Override
                public void onResponse(Call<PincodeAreaResponse> call, final retrofit2.Response<PincodeAreaResponse> response) {
                    try {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        if (response.body() != null) {

                            if (response.body().getStatuscode() == 1) {
                                if (response.body().getAreas() != null && response.body().getAreas().size() > 0) {
                                    if (mApiCallBack != null) {
                                        mApiCallBack.onSucess(response.body());
                                    }
                                } else {
                                    Error(context, "Area not available or may be pincode doesn't exist.");
                                }

                            } else {
                                if (!response.body().isVersionValid()) {
                                    versionDialog(context);
                                } else {
                                    Error(context, response.body().getMsg() + "");
                                }
                            }

                        }
                    } catch (Exception e) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                    }

                }

                @Override
                public void onFailure(Call<PincodeAreaResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            loader.dismiss();
                    }
                    try {
                        if (t.getMessage() != null && !t.getMessage().isEmpty()) {

                            NetworkError(context, context.getResources().getString(R.string.err_msg_network_title), context.getResources().getString(R.string.err_msg_network));
                        } else {
                            Error(context, context.getResources().getString(R.string.some_thing_error));
                        }
                    } catch (IllegalStateException ise) {
                        Error(context, ise.getMessage());

                    }


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void dthSubscriptionConfiormDialog(Activity context, CommissionDisplay mCommissionDisplay,
                                              final boolean isReal, final boolean isPinPass,
                                              String logo, String number, String operator,
                                              String amount, String packageName, String name, String address,
                                              final DialogCallBack mDialogCallBack) {
        if (alertDialogDth != null && alertDialogDth.isShowing()) {
            return;
        }
        androidx.appcompat.app.AlertDialog.Builder dialogBuilder;
        dialogBuilder = new androidx.appcompat.app.AlertDialog.Builder(context);

        alertDialogDth = dialogBuilder.create();
        alertDialogDth.setCancelable(true);

        alertDialogDth.getWindow().setBackgroundDrawableResource(android.R.color.transparent);


        LayoutInflater inflater = context.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_dth_subscription_confiorm, null);
        alertDialogDth.setView(dialogView);
        LinearLayout commView = dialogView.findViewById(R.id.commView);
        LinearLayout lapuView = dialogView.findViewById(R.id.lapuView);
        LinearLayout realView = dialogView.findViewById(R.id.realView);
        TextView packageTv = dialogView.findViewById(R.id.packagetv);
        packageTv.setText(packageName + "");
        TextView lapuTitle = dialogView.findViewById(R.id.lapuTitle);
        // TextView lapuAmt = dialogView.findViewById(R.id.lapuAmt);
        TextView realTitle = dialogView.findViewById(R.id.realTitle);
        // TextView realAmt = dialogView.findViewById(R.id.realAmt);
        if (mCommissionDisplay != null) {
            commView.setVisibility(View.VISIBLE);
            if (isReal) {
                lapuView.setVisibility(View.GONE);
                realView.setVisibility(View.VISIBLE);
            } else {
                lapuView.setVisibility(View.VISIBLE);
                realView.setVisibility(View.GONE);
            }

            lapuTitle.setText((mCommissionDisplay.isCommType() ? "Surcharge " : "Commission ") + context.getResources().getString(R.string.rupiya) + " " + formatedAmount(mCommissionDisplay.getCommission() + ""));
            // lapuAmt.setText(context.getResources().getString(R.string.rupiya)+" "+formatedAmount(mCommissionDisplay.getCommission()+""));
            realTitle.setText((mCommissionDisplay.isrCommType() ? "Surcharge " : "Commission ") + context.getResources().getString(R.string.rupiya) + " " + formatedAmount(mCommissionDisplay.getrCommission() + ""));
            // realAmt.setText(context.getResources().getString(R.string.rupiya)+" "+formatedAmount(mCommissionDisplay.getrCommission()+""));
        } else {
            commView.setVisibility(View.GONE);
        }
        AppCompatTextView amountTv = dialogView.findViewById(R.id.amount);
        amountTv.setText(context.getResources().getString(R.string.rupiya) + " " + amount);
        final TextInputLayout til_pinPass = dialogView.findViewById(R.id.til_pinPass);
        final EditText pinPassEt = dialogView.findViewById(R.id.pinPassEt);
        if (isPinPass) {
            til_pinPass.setVisibility(View.VISIBLE);
        } else {
            til_pinPass.setVisibility(View.GONE);
        }
        AppCompatTextView operatorTv = dialogView.findViewById(R.id.operator);
        operatorTv.setText(operator);
        AppCompatTextView numberTv = dialogView.findViewById(R.id.number);
        numberTv.setText(number);
        AppCompatTextView nameTv = dialogView.findViewById(R.id.name);
        nameTv.setText(name);
        AppCompatTextView addressTv = dialogView.findViewById(R.id.address);
        addressTv.setText(address);
        AppCompatTextView cancelTv = dialogView.findViewById(R.id.cancel);
        AppCompatTextView okTv = dialogView.findViewById(R.id.ok);
        AppCompatImageView logoIv = dialogView.findViewById(R.id.logo);
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);

        Glide.with(context)
                .load(logo)
                .apply(RequestOptions.circleCropTransform())
                .apply(RequestOptions.placeholderOf(R.mipmap.ic_launcher))
                .into(logoIv);


        okTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isPinPass && pinPassEt.getText().toString().isEmpty()) {
                    pinPassEt.setError("Field can't be empty");
                    pinPassEt.requestFocus();
                    return;
                }
                alertDialogDth.dismiss();
                if (mDialogCallBack != null) {
                    mDialogCallBack.onPositiveClick(pinPassEt.getText().toString());
                }
            }
        });

        cancelTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialogDth.dismiss();
                if (mDialogCallBack != null) {
                    mDialogCallBack.onCancelClick();
                }
            }
        });


        alertDialogDth.show();
        /*if (isFullScreen) {
            alertDialogDth.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }*/
    }


    public void setRecentLogin(Activity context, String key) {
        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(ApplicationConstant.INSTANCE.regRecentLoginPref, key);
        editor.apply();
    }

    public void NumberList(final Activity context, final CustomLoader loader, final int flag) {
        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<NumberListResponse> call = git.GetNumberList(new NunberListRequest(
                    ApplicationConstant.INSTANCE.APP_ID,
                    getIMEI(context),
                    "", BuildConfig.VERSION_NAME, getSerialNo(context)));
            call.enqueue(new Callback<NumberListResponse>() {
                @Override
                public void onResponse(Call<NumberListResponse> call, final retrofit2.Response<NumberListResponse> response) {
                    if (loader != null) {
                        if (loader.isShowing())
                            loader.dismiss();
                    }
                    try {
                        if (response.body() != null && response.body().getStatuscode() != null) {
                            if (response.body().getStatuscode().equalsIgnoreCase("1")) {
                                setNumberList(context, new Gson().toJson(response.body()).toString());
                                ArrayList<OperatorList> mOperatorLists = new ArrayList<>();
                                for (OperatorList op : response.body().getData().getOperators()) {
                                    if (op.getOpType() == 14) {
                                        mOperatorLists.add(op);
                                    }
                                }
                                UtilsMethodDMTPipe.INSTANCE.setDMTOperatorList(context, mOperatorLists);
                                try {
                                    if (flag == 0) {
                                        String LoginResponse = getLoginPref(context);
                                        com.solution.brothergroup.Authentication.dto.LoginResponse LoginDataResponse = new Gson().fromJson(LoginResponse, LoginResponse.class);
                                        if (LoginDataResponse != null && LoginDataResponse.getData() != null && LoginDataResponse.getData().getSessionID() != null && LoginDataResponse.getData().getSessionID().length() > 0) {
                                            dashboard(context);

                                        } else {
                                            if (isNetworkAvialable(context)) {
                                               login(context);
                                            } else {
                                                NetworkError(context, context.getResources().getString(R.string.err_msg_network_title),
                                                        context.getResources().getString(R.string.err_msg_network));
                                            }
                                        }
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                    }

                }

                @Override
                public void onFailure(Call<NumberListResponse> call, Throwable t) {

                    if (loader != null) {
                        if (loader.isShowing())
                            loader.dismiss();
                    }
                    Error(context, context.getResources().getString(R.string.err_something_went_wrong));
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    void dashboard(Activity context){
        Intent intent = new Intent(context, MainActivity.class);
      /*  intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);*/
        context.startActivity(intent);
        context.finish();
    }

    void login(Activity context){
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
        context.finish();
    }

    public void GetCallMeUserReq(final Activity mActivity, String mobNo, final CustomLoader loader) {
        try {
            loader.show();
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            String LoginResponse = getLoginPref(mActivity);
            LoginResponse LoginDataResponse = new Gson().fromJson(LoginResponse, LoginResponse.class);

            Call<BasicResponse> call = git.GetCallMeUserReq(new CallBackRequest(mobNo,
                    LoginDataResponse.getData().getUserID(), LoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID,
                    getIMEI(mActivity),
                    "", BuildConfig.VERSION_NAME, getSerialNo(mActivity), LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));

            call.enqueue(new Callback<BasicResponse>() {

                @Override
                public void onResponse(Call<BasicResponse> call, retrofit2.Response<BasicResponse> response) {

                    if (loader.isShowing())
                        loader.dismiss();
                    BasicResponse mBasicResponse = response.body();
                    if (mBasicResponse != null) {
                        if (mBasicResponse.getStatuscode() == 1) {
                            Successful(mActivity, mBasicResponse.getMsg() + "");

                        } else if (response.body().getStatuscode() == -1) {

                            Processing(mActivity, mBasicResponse.getMsg() + "");
                        } else {
                            Processing(mActivity, mBasicResponse.getMsg() + "");
                        }

                    } else {

                        Error(mActivity, mActivity.getResources().getString(R.string.some_thing_error));
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

                                NetworkError(mActivity, mActivity.getResources().getString(R.string.network_error_title), mActivity.getResources().getString(R.string.err_msg_network));


                            } else {

                                Error(mActivity, t.getMessage());


                            }

                        } else {

                            Error(mActivity, mActivity.getResources().getString(R.string.some_thing_error));
                        }
                    } catch (IllegalStateException ise) {
                        loader.dismiss();

                        Error(mActivity, mActivity.getResources().getString(R.string.some_thing_error));
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();

            Error(mActivity, mActivity.getResources().getString(R.string.some_thing_error));
        }

    }


    public void EnableDisableRealApi(final Activity context, boolean isRealApi, final CustomLoader loader, final ApiCallBack mApiCallBack) {
        try {
            String LoginResponse = getLoginPref(context);
            LoginResponse LoginDataResponse = new Gson().fromJson(LoginResponse, LoginResponse.class);
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<BasicResponse> call = git.ChangeRealAPIStatus(new RealApiChangeRequest(isRealApi,
                    LoginDataResponse.getData().getUserID() + "", LoginDataResponse.getData().getLoginTypeID() + "",
                    ApplicationConstant.INSTANCE.APP_ID,
                    getIMEI(context),
                    "", BuildConfig.VERSION_NAME, getSerialNo(context), LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));

            call.enqueue(new Callback<BasicResponse>() {

                @Override
                public void onResponse(Call<BasicResponse> call, retrofit2.Response<BasicResponse> response) {
                    if (loader != null && loader.isShowing()) {
                        loader.dismiss();
                    }
                    if (response.isSuccessful()) {

                        if (response.body() != null) {
                            if (response.body().getStatuscode() == 1) {
                                if (mApiCallBack != null) {
                                    mApiCallBack.onSucess(response.body());
                                }
                            } else {
                                if (response.body().getVersionValid() == false) {
                                    versionDialog(context);
                                } else {
                                    Error(context, response.body().getMsg() + "");
                                }
                            }

                        }
                    } else {
                        apiErrorHandle(context, response.code(), response.message());
                    }
                }

                @Override
                public void onFailure(Call<BasicResponse> call, Throwable t) {

                    if (loader != null && loader.isShowing()) {
                        loader.dismiss();
                    }
                    apiFailureError(context, t);
                }
            });

        } catch (Exception e) {
            if (loader != null && loader.isShowing()) {
                loader.dismiss();
            }
            e.printStackTrace();
        }

    }


    public void NumberList(final Activity context, final ImageView refreshIv, ApiCallBack mApiCallBack) {
        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<NumberListResponse> call = git.GetNumberList(new NunberListRequest(
                    ApplicationConstant.INSTANCE.APP_ID,
                    getIMEI(context),
                    "", BuildConfig.VERSION_NAME, getSerialNo(context)));
            call.enqueue(new Callback<NumberListResponse>() {
                @Override
                public void onResponse(Call<NumberListResponse> call, final retrofit2.Response<NumberListResponse> response) {

                    try {
                        if (refreshIv != null) {
                            refreshIv.clearAnimation();
                        }
                        if (response.body() != null && response.body().getStatuscode() != null) {
                            if (response.body().getStatuscode().equalsIgnoreCase("1")) {
                                setNumberList(context, new Gson().toJson(response.body()).toString());

                                ArrayList<OperatorList> mOperatorLists = new ArrayList<>();
                                for (OperatorList op : response.body().getData().getOperators()) {
                                    if (op.getOpType() == 14) {
                                        mOperatorLists.add(op);
                                    }
                                }
                                UtilsMethodDMTPipe.INSTANCE.setDMTOperatorList(context, mOperatorLists);

                                if (mApiCallBack != null) {
                                    mApiCallBack.onSucess(response.body());
                                }
                            } else {
                                if (response.body().getMsg() != null) {
                                    Error(context, response.body().getMsg());
                                } else {
                                    Error(context, context.getResources().getString(R.string.err_something_went_wrong));
                                }

                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Error(context, e.getMessage());
                        if (refreshIv != null) {
                            refreshIv.clearAnimation();
                        }
                    }

                }

                @Override
                public void onFailure(Call<NumberListResponse> call, Throwable t) {
                    if (refreshIv != null) {
                        refreshIv.clearAnimation();
                    }
                    Error(context, context.getResources().getString(R.string.err_something_went_wrong));
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            Error(context, e.getMessage());
            if (refreshIv != null) {
                refreshIv.clearAnimation();
            }
        }
    }

    /*public void Balancecheck(final Activity context, final CustomLoader loader) {
        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            String LoginResponse = getLoginPref(context);
            LoginResponse LoginDataResponse = new Gson().fromJson(LoginResponse, LoginResponse.class);

            Call<LoginResponse> call = git.Balancecheck(new BalanceRequest(LoginDataResponse.getData().getUserID() + "", LoginDataResponse.getData().getLoginTypeID() + "",
                    ApplicationConstant.INSTANCE.APP_ID,
                    getIMEI(context),
                    "", BuildConfig.VERSION_NAME, getSerialNo(context), LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));
            call.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, final retrofit2.Response<LoginResponse> response) {

                    try {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        if (response.body() != null && response.body().getStatuscode() != null) {

                            if (response.body().getStatuscode().equalsIgnoreCase("1")) {
                                setBalanceCheck(context, new Gson().toJson(response.body()).toString());
                            } else if (response.body().getStatuscode().equalsIgnoreCase("2")) {
                                //   openOTPDialog(context,loader,password);
                            } else if (response.body().getStatuscode().equalsIgnoreCase("-1")) {
                                if (response.body().getIsVersionValid() != null && response.body().getIsVersionValid().equalsIgnoreCase("false")) {
                                    versionDialog(context);
                                } else {
                                    Error(context, response.body().getMsg() + "");
                                }
                            }
                        }
                    } catch (Exception e) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                    }

                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            loader.dismiss();
                    }
                    Error(context, context.getResources().getString(R.string.err_something_went_wrong));

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    public void secureLogin(final Activity context, String mobile, final String password, final CustomLoader loader) {
        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<LoginResponse> call = git.secureLogin(new LoginRequest(1, mobile, password,
                    ApplicationConstant.INSTANCE.Domain,
                    ApplicationConstant.INSTANCE.APP_ID,
                    getIMEI(context),
                    "", BuildConfig.VERSION_NAME, getSerialNo(context)));


            call.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, final retrofit2.Response<LoginResponse> response) {
                    if (loader != null) {
                        if (loader.isShowing())
                            loader.dismiss();
                    }
                    try {
                        if (response.body() != null && response.body().getStatuscode() != null) {
                            if (response.body().getStatuscode().equalsIgnoreCase("1")) {
                                setDoubleFactorPref(context, response.body().getData().getIsDoubleFactor());
                                setIsLookUpFromAPI(context, response.body().isLookUpFromAPI());
                                setIsDTHInfoAutoCall(context, response.body().isDTHInfoCall());
                                setIsHeavyRefresh(context, response.body().isHeavyRefresh());
                                setIsAutoBilling(context, response.body().isAutoBilling());
                                setIsRealAPIPerTransaction(context, response.body().isRealAPIPerTransaction());
                                setIsRoffer(context, response.body().isRoffer());
                                setIsDTHInfo(context, response.body().isDTHInfo());
                                setIsTargetShow(context, response.body().isTargetShow());
                                //  Toast.makeText(context, ""+response.body().isTargetShow(), Toast.LENGTH_SHORT).show();
                                setLoginPref(context, password, response.body().getData().getMobileNo(), new Gson().toJson(response.body()).toString());
                                updateFcm(context);
                                GetCompanyProfile(context, null);
                                GetActiveService(context, null);
                                setIsLogin(context, true);

                                if (loader != null)
                                    ((LoginActivity) context).startDashboard();
                                else
                                    dashboard(context);
                            } else if (response.body().getStatuscode().equalsIgnoreCase("2")) {
                                openOTPDialog(context, loader, "Login", response.body().getOtpSession(), password);
                            } else if (response.body().getStatuscode().equalsIgnoreCase("3")) {
                                openTOTPDialog(context, loader, response.body().getOtpSession(), password);
                            } else if (response.body().getStatuscode().equalsIgnoreCase("-1")) {
                                if (response.body().getIsVersionValid() != null && response.body().getIsVersionValid().equalsIgnoreCase("false")) {
                                    versionDialog(context);
                                } else {
                                    Error(context, response.body().getMsg() + "");
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {

                    if (loader != null) {
                        if (loader.isShowing())
                            loader.dismiss();
                    }
                    if (t.getMessage().contains("No address associated with hostname")) {
                        Error(context, context.getResources().getString(R.string.network_error));
                    } else {
                        Error(context, t.getMessage());
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void ResendOTP(final Activity context, String otpSession, final CustomLoader loader, final ApiCallBack mApiCallBack) {
        try {
            loader.show();
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);

            Call<BasicResponse> call = git.ResendOTP(new ResendOtpRequest(otpSession, ApplicationConstant.INSTANCE.Domain,
                    ApplicationConstant.INSTANCE.APP_ID,
                    getIMEI(context),
                    "", BuildConfig.VERSION_NAME, getSerialNo(context)));
            call.enqueue(new Callback<BasicResponse>() {
                @Override
                public void onResponse(Call<BasicResponse> call, final retrofit2.Response<BasicResponse> response) {

                    try {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        if (response.body() != null) {

                            if (response.body().getStatuscode() == 2) {
                                Successful(context, response.body().getMsg() + "");
                                if (mApiCallBack != null) {
                                    mApiCallBack.onSucess(response.body());
                                }
                            } else {
                                Error(context, response.body().getMsg() + "");
                            }
                        }
                    } catch (Exception e) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        Error(context, e.getMessage() + "");
                    }

                }

                @Override
                public void onFailure(Call<BasicResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            loader.dismiss();
                    }
                    Error(context, context.getResources().getString(R.string.err_something_went_wrong));

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            if (loader != null) {
                if (loader.isShowing())
                    loader.dismiss();
            }
            Error(context, e.getMessage() + "");
        }
    }

    public void updateFcm(final Context context) {
        try {
            final String fcmId = getFCMRegKey(context);

            if (fcmId == null || fcmId.isEmpty()) {
                FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
                    @Override
                    public void onSuccess(InstanceIdResult instanceIdResult) {
                        String newToken = instanceIdResult.getToken();

                        if (newToken != null && !newToken.isEmpty()) {
                            setFCMRegKey(context, newToken);
                            updateFcm(context, newToken);
                        } else {
                            updateFcm(context, fcmId);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        updateFcm(context, fcmId);
                    }
                });
            } else {
                updateFcm(context, fcmId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void initiateUPI(final Context context, final InitiateUPIRequest upiRequest, final CustomLoader loader, final ApiCallBack mApiCallBack) {
        try {

            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<InitiateUPIResponse> call = git.intiateUPI(upiRequest);

            call.enqueue(new Callback<InitiateUPIResponse>() {

                @Override
                public void onResponse(Call<InitiateUPIResponse> call, retrofit2.Response<InitiateUPIResponse> response) {
                    if (loader != null && loader.isShowing())
                        loader.dismiss();

                    if (response.isSuccessful()) {
                        if (response.body() != null) {

                            if (response.body().getStatuscode() != null && response.body().getStatuscode() == 1) {
                                if (mApiCallBack != null) {
                                    mApiCallBack.onSucess(response.body());
                                }
                            } else {
                                Error((Activity) context, response.body().getMsg() + "");
                            }

                        }
                    }

                }

                @Override
                public void onFailure(Call<InitiateUPIResponse> call, Throwable t) {
                    if (loader != null && loader.isShowing())
                        loader.dismiss();
                    if (t.getMessage().contains("No address associated with hostname")) {
                        Error((Activity) context, context.getResources().getString(R.string.network_error));
                    } else {
                        Error((Activity) context, t.getMessage());
                    }
                }
            });

        } catch (Exception e) {
            if (loader != null && loader.isShowing())
                loader.dismiss();
            e.printStackTrace();
        }

    }


    private void updateFcm(final Context context, String fcmId) {
        try {


            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            String LoginResponse = getLoginPref(context);
            LoginResponse LoginDataResponse = new Gson().fromJson(LoginResponse, LoginResponse.class);
            Call<BasicResponse> call = git.UpdateFCMID(new UpdateFcmRequest(
                    LoginDataResponse.getData().getUserID(), LoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID,
                    getIMEI(context),
                    "", BuildConfig.VERSION_NAME, getSerialNo(context), LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession(), fcmId));

            call.enqueue(new Callback<BasicResponse>() {
                @Override
                public void onResponse(Call<BasicResponse> call, final retrofit2.Response<BasicResponse> response) {

                    try {
                        if (response.body() != null) {
                            if (response.body().getStatuscode() == 1) {

                            } else if (response.body().getStatuscode() == 2) {

                            } else if (response.body().getStatuscode() == -1) {

                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();

                    }
                }

                @Override
                public void onFailure(Call<BasicResponse> call, Throwable t) {


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ForgotPass(final Activity context, String userId, final CustomLoader loader) {
        try {


            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);

            Call<BasicResponse> call = git.ForgetPassword(new LoginRequest(1, userId, "",
                    ApplicationConstant.INSTANCE.Domain,
                    ApplicationConstant.INSTANCE.APP_ID,
                    getIMEI(context),
                    "", BuildConfig.VERSION_NAME, getSerialNo(context)));

            call.enqueue(new Callback<BasicResponse>() {
                @Override
                public void onResponse(Call<BasicResponse> call, final retrofit2.Response<BasicResponse> response) {

                    try {
                        loader.dismiss();
                        if (response.body() != null) {
                            if (response.body().getStatuscode() == 1) {
                                Successful(context, response.body().getMsg());
                            } else if (response.body().getStatuscode() == 2) {
                                Failed(context, response.body().getMsg());
                            } else if (response.body().getStatuscode() == -1) {
                                Failed(context, response.body().getMsg());
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        loader.dismiss();
                        Successful(context, e.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<BasicResponse> call, Throwable t) {
                    loader.dismiss();
                    Successful(context, t.getMessage());
                }
            });

        } catch (Exception e) {
            loader.dismiss();
            e.printStackTrace();
        }
    }

    public void Balancecheck(final Activity context, final CustomLoader loader, final ApiCallBack mApiCallBack) {
        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            String LoginResponse = getLoginPref(context);
            LoginResponse LoginDataResponse = new Gson().fromJson(LoginResponse, LoginResponse.class);
            Call<BalanceResponse> call = git.Balancecheck(new BalanceRequest(LoginDataResponse.getData().getUserID() + "", LoginDataResponse.getData().getLoginTypeID() + "",
                    ApplicationConstant.INSTANCE.APP_ID,
                    getIMEI(context),
                    "", BuildConfig.VERSION_NAME, getSerialNo(context), LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));
            call.enqueue(new Callback<BalanceResponse>() {
                @Override
                public void onResponse(Call<BalanceResponse> call, final retrofit2.Response<BalanceResponse> response) {

                    try {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        if (!isPassChangeDialogShowing) {
                            if (response.body() != null && response.body().getBalanceData() != null && response.body().getBalanceData().isPN()) {

                                CustomAlertDialog customPassDialog = new CustomAlertDialog(context, true);
                                customPassDialog.WarningWithSingleBtnCallBack(context.getResources().getString(R.string.pin_password_expired_msg), "Create", false, new CustomAlertDialog.DialogCallBack() {
                                    @Override
                                    public void onPositiveClick() {
                                        new ChangePassUtils(context).changePassword(true, false);
                                    }

                                    @Override
                                    public void onNegativeClick() {

                                    }
                                });

                            } else if (response.body() != null && response.body().getIsPasswordExpired()) {
                                CustomAlertDialog customPassDialog = new CustomAlertDialog(context, true);
                                customPassDialog.WarningWithSingleBtnCallBack(context.getResources().getString(R.string.password_expired_msg), "Change", false, new CustomAlertDialog.DialogCallBack() {
                                    @Override
                                    public void onPositiveClick() {
                                        new ChangePassUtils(context).changePassword(false, false);
                                    }

                                    @Override
                                    public void onNegativeClick() {

                                    }
                                });

                            } else if (!isSocialEmailVerfiedChecked) {
                                if (!getEmailVerifiedPref(context) ||
                                        !getSocialLinkSavedPref(context)) {
                                    long time = hourDifference(getSocialorEmailDialogTimePref(context), System.currentTimeMillis());
                                    if (time >= 8) {
                                        CheckFlagsEmail(context, loader, LoginDataResponse);
                                    }
                                }
                                isSocialEmailVerfiedChecked = true;
                            }

                        }
                        if (response.body() != null && response.body().getStatuscode() != null) {

                            if (response.body().getStatuscode() == 1) {
                                if (mApiCallBack != null) {
                                    mApiCallBack.onSucess(response.body());
                                }
                                setBalanceCheck(context, new Gson().toJson(response.body()));
                                setIsLookUpFromAPI(context, response.body().isLookUpFromAPI());
                                setIsDTHInfoAutoCall(context, response.body().isDTHInfoCall());
                                setIsFlatCommission(context, response.body().isFlatCommission());

                                // setIsHeavyRefresh(context, response.body().isHeavyRefresh());
                                setIsRoffer(context, response.body().isRoffer());
                                setIsDTHInfo(context, response.body().isDTHInfo());
                                setIsShowPDFPlan(context, response.body().isShowPDFPlan());
                            } else if (response.body().getStatuscode() == 2) {
                                //   openOTPDialog(context,loader,password);
                            } else if (response.body().getStatuscode() == -1) {
                                if (response.body().getVersionValid() != null && response.body().getVersionValid() == false) {
                                    versionDialog(context);
                                } else {
                                    Error(context, response.body().getMsg() + "");
                                }
                            }
                        }
                    } catch (Exception e) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                    }

                }

                @Override
                public void onFailure(Call<BalanceResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            loader.dismiss();
                    }
                    try {
                        if (t.getMessage() != null && !t.getMessage().isEmpty()) {

                            if (t.getMessage().contains("No address associated with hostname")) {
                               /* NetworkError(context, context.getResources().getString(R.string.err_msg_network_title),
                                        context.getResources().getString(R.string.err_msg_network));*/
                            } else {
                                Error(context, t.getMessage());

                            }

                        } else {
                            Error(context, context.getResources().getString(R.string.some_thing_error));

                        }
                    } catch (IllegalStateException ise) {
                        Error(context, ise.getMessage());

                    }

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setIsLogin(Context context, boolean IsLogin) {
        try {
            SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.IsLoginPref, context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean(ApplicationConstant.INSTANCE.IsLogin, IsLogin);
            editor.apply();
        } catch (Exception e) {
        }

        isLogin = IsLogin;
    }

    public boolean isLogin(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.IsLoginPref, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        return prefs.getBoolean(ApplicationConstant.INSTANCE.IsLogin, false);

    }

    public void BalancecheckNew(final Activity context, CustomAlertDialog customAlertDialog, final ChangePassUtils mChangePassUtils, ApiCallBack apiCallBack) {
        try {
            customPassDialog = customAlertDialog;
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            String LoginResponse = getLoginPref(context);
            LoginResponse LoginDataResponse = new Gson().fromJson(LoginResponse, LoginResponse.class);
            Call<BalanceResponse> call = git.Balancecheck(new BalanceRequest(LoginDataResponse.getData().getUserID() + "", LoginDataResponse.getData().getLoginTypeID() + "",
                    ApplicationConstant.INSTANCE.APP_ID,
                    getIMEI(context),
                    "", BuildConfig.VERSION_NAME, getSerialNo(context), LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));
            call.enqueue(new Callback<BalanceResponse>() {
                @Override
                public void onResponse(Call<BalanceResponse> call, final retrofit2.Response<BalanceResponse> response) {

                    try {
                        if (!isPassChangeDialogShowing) {
                            if (response.body() != null && response.body().getBalanceData() != null && response.body().getBalanceData().isPN()) {

                                if (customPassDialog != null) {
                                    if (customPassDialog.returnDialog() != null && customPassDialog.returnDialog().isShowing()) {
                                        return;
                                    }
                                    if (mChangePassUtils != null && mChangePassUtils.returnDialog() != null && mChangePassUtils.returnDialog().isShowing()) {
                                        return;
                                    }
                                    customPassDialog.WarningWithSingleBtnCallBack(context.getResources().getString(R.string.pin_password_expired_msg), "Create", false, new CustomAlertDialog.DialogCallBack() {
                                        @Override
                                        public void onPositiveClick() {
                                            if (mChangePassUtils != null) {
                                                mChangePassUtils.changePassword(true, false);
                                            } else {
                                                new ChangePassUtils(context).changePassword(true, false);
                                            }

                                        }

                                        @Override
                                        public void onNegativeClick() {

                                        }
                                    });
                                } else {
                                    customPassDialog = new CustomAlertDialog(context, true);
                                    customPassDialog.WarningWithSingleBtnCallBack(context.getResources().getString(R.string.pin_password_expired_msg), "Create", false, new CustomAlertDialog.DialogCallBack() {
                                        @Override
                                        public void onPositiveClick() {
                                            if (mChangePassUtils != null) {
                                                mChangePassUtils.changePassword(true, false);
                                            } else {
                                                new ChangePassUtils(context).changePassword(true, false);
                                            }
                                        }

                                        @Override
                                        public void onNegativeClick() {

                                        }
                                    });
                                }


                            } else if (response.body() != null && response.body().getIsPasswordExpired()) {

                                if (customPassDialog != null) {
                                    if (customPassDialog.returnDialog() != null && customPassDialog.returnDialog().isShowing()) {
                                        return;
                                    }
                                    if (mChangePassUtils != null && mChangePassUtils.returnDialog() != null && mChangePassUtils.returnDialog().isShowing()) {
                                        return;
                                    }
                                    customPassDialog.WarningWithSingleBtnCallBack(context.getResources().getString(R.string.password_expired_msg), "Change", false, new CustomAlertDialog.DialogCallBack() {
                                        @Override
                                        public void onPositiveClick() {
                                            if (mChangePassUtils != null) {
                                                mChangePassUtils.changePassword(false, false);
                                            } else {
                                                new ChangePassUtils(context).changePassword(false, false);
                                            }
                                        }

                                        @Override
                                        public void onNegativeClick() {

                                        }
                                    });
                                } else {
                                    customPassDialog = new CustomAlertDialog(context, true);
                                    customPassDialog.WarningWithSingleBtnCallBack(context.getResources().getString(R.string.password_expired_msg), "Change", false, new CustomAlertDialog.DialogCallBack() {
                                        @Override
                                        public void onPositiveClick() {
                                            if (mChangePassUtils != null) {
                                                mChangePassUtils.changePassword(false, false);
                                            } else {
                                                new ChangePassUtils(context).changePassword(false, false);
                                            }
                                        }

                                        @Override
                                        public void onNegativeClick() {

                                        }
                                    });
                                }
                            } else if (!isSocialEmailVerfiedChecked) {
                                if (!getEmailVerifiedPref(context) ||
                                        !getSocialLinkSavedPref(context)) {
                                    long time = hourDifference(getSocialorEmailDialogTimePref(context), System.currentTimeMillis());
                                    if (time >= 8) {
                                        CheckFlagsEmail(context, null, LoginDataResponse);
                                    }
                                }
                                isSocialEmailVerfiedChecked = true;
                            }
                        }

                        if (response.body() != null && response.body().getStatuscode() != null) {

                            if (response.body().getStatuscode() == 1) {
                                setBalanceCheck(context, new Gson().toJson(response.body()));
                                setIsLookUpFromAPI(context, response.body().isLookUpFromAPI());
                                setIsDTHInfoAutoCall(context, response.body().isDTHInfoCall());
                                setIsFlatCommission(context, response.body().isFlatCommission());
                                setIsRoffer(context, response.body().isRoffer());
                                setIsDTHInfo(context, response.body().isDTHInfo());

                                if (apiCallBack != null) {
                                    apiCallBack.onSucess(response.body());
                                }

                                long time = hourDifference(getBalanceLowTime(context), System.currentTimeMillis());
                                if (response.body().getBalanceData().isLowBalance() && time >= 1) {
                                    if (customPassDialog != null) {
                                        if (customPassDialog.returnDialog() != null && customPassDialog.returnDialog().isShowing()) {
                                            return;
                                        } else {
                                            setBalanceLowTime(context, System.currentTimeMillis());
                                            String msg = "Your Balance is low.<br>Current Balance - " + context.getResources().getString(R.string.rupiya) + " " + response.body().getBalanceData().getBalance();
                                            customPassDialog.WarningWithCallBack(msg, "Low Balance", "Fund Request", true, new CustomAlertDialog.DialogCallBack() {
                                                @Override
                                                public void onPositiveClick() {
                                                    Intent i = new Intent(context, PaymentRequest.class);
                                                    i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                                    context.startActivity(i);
                                                }

                                                @Override
                                                public void onNegativeClick() {

                                                }
                                            });
                                        }
                                    }
                                }
                            } else if (response.body().getStatuscode() == 2) {
                                //   openOTPDialog(context,loader,password);
                            } else if (response.body().getStatuscode() == -1) {
                                if (response.body().getVersionValid() != null && response.body().getVersionValid() == false) {
                                    versionDialog(context);
                                } else {
                                    Error(context, response.body().getMsg() + "");
                                }
                            }
                        }
                    } catch (Exception e) {

                    }

                }

                @Override
                public void onFailure(Call<BalanceResponse> call, Throwable t) {

                    try {
                        if (t.getMessage() != null && !t.getMessage().isEmpty()) {

                            if (t.getMessage().contains("No address associated with hostname")) {

                               /* NetworkError(context, context.getResources().getString(R.string.err_msg_network_title),
                                        context.getResources().getString(R.string.err_msg_network));*/
                            } else {
                                Error(context, t.getMessage());

                            }

                        } else {
                            Error(context, context.getResources().getString(R.string.some_thing_error));

                        }
                    } catch (IllegalStateException ise) {
                        Error(context, ise.getMessage());

                    }


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private long hourDifference(long millisFirst, long millisSecond) {
        return TimeUnit.MILLISECONDS.toHours(millisSecond - millisFirst);
    }


    public void CheckFlagsEmail(Activity mContext, CustomLoader loader,
                                LoginResponse LoginDataResponse) {
        try {

            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<BasicResponse> call = git.CheckFlagsEmail(new BasicRequest(LoginDataResponse.getData().getUserID(),
                    LoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID, getIMEI(mContext), "", BuildConfig.VERSION_NAME, getSerialNo(mContext),
                    LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));

            call.enqueue(new Callback<BasicResponse>() {

                @Override
                public void onResponse(Call<BasicResponse> call, retrofit2.Response<BasicResponse> response) {
                    if (response.isSuccessful()) {
                        try {

                            BasicResponse mBasicResponse = response.body();
                            if (mBasicResponse != null) {
                                if (mBasicResponse.getStatuscode() == 1) {
                                    setSocialorEmailDialogTimePref(mContext, System.currentTimeMillis());
                                    if (!mBasicResponse.isEmailVerified() || !mBasicResponse.isSocialAlert()) {
                                        emailVerifySocialLinkDialog(mContext, mBasicResponse.isEmailVerified(), mBasicResponse.isSocialAlert(),
                                                loader, LoginDataResponse);
                                    } else {
                                        setEmailVerifiedPref(mContext, true);
                                        setSocialLinkSavedPref(mContext, true);
                                    }
                                } else {
                                    if (response.body().getVersionValid()) {
                                        if (mBasicResponse.getStatuscode() == 0) {
                                            setEmailVerifiedPref(mContext, true);
                                            setSocialLinkSavedPref(mContext, true);
                                            setSocialorEmailDialogTimePref(mContext, System.currentTimeMillis());
                                        }
                                    }

                                }

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        apiErrorHandle(mContext, response.code(), response.message());
                    }
                }

                @Override
                public void onFailure(Call<BasicResponse> call, Throwable t) {
                    t.printStackTrace();

                }
            });

        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    void emailVerifySocialLinkDialog(Activity context, final boolean isEmailVerified, final boolean isSocialAlert, CustomLoader loader,
                                     LoginResponse loginDataResponse) {
        if (alertDialogSocial != null && alertDialogSocial.isShowing()) {
            return;
        }
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.dialog_email_verify_social_link_update, null);
        alertDialogSocial = new Dialog(context);
        alertDialogSocial.setCancelable(false);
        alertDialogSocial.setContentView(dialogView);
        alertDialogSocial.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ImageView closeBtn = dialogView.findViewById(R.id.closeBtn);
        TextView title = dialogView.findViewById(R.id.title);
        LinearLayout verifyEmailView = dialogView.findViewById(R.id.verifyEmailView);
        LinearLayout socialView = dialogView.findViewById(R.id.socialView);
        LinearLayout socialInputView = dialogView.findViewById(R.id.socialInputView);
        TextView verifyEmailTxt = dialogView.findViewById(R.id.verifyEmailTxt);
        TextView verifyEmailBtn = dialogView.findViewById(R.id.verifyEmailBtn);
        View line = dialogView.findViewById(R.id.line);
        TextView socialSaveTxt = dialogView.findViewById(R.id.socialSaveTxt);
        TextView whatsappTitle = dialogView.findViewById(R.id.whatsappTitle);
        EditText whatsappNumberEt = dialogView.findViewById(R.id.whatsappNumberEt);
        TextView telegramTitle = dialogView.findViewById(R.id.telegramTitle);
        EditText telegramNumberEt = dialogView.findViewById(R.id.telegramNumberEt);
        TextView hangoutTitle = dialogView.findViewById(R.id.hangoutTitle);
        EditText hangoutEt = dialogView.findViewById(R.id.hangoutEt);
        TextView submitBtn = dialogView.findViewById(R.id.submitBtn);

        if (!isEmailVerified && isSocialAlert) {
            title.setText("Verify Email Id");
        } else if (isEmailVerified && !isSocialAlert) {
            title.setText("Update Social Link");
        } else {
            title.setText("Verify Email Id And Update Social Link");
        }
        if (isEmailVerified) {
            line.setVisibility(View.GONE);
            verifyEmailView.setVisibility(View.GONE);
        }
        if (isSocialAlert) {
            line.setVisibility(View.GONE);
            socialView.setVisibility(View.GONE);
        } else {
            hangoutEt.setText(loginDataResponse.getData().getEmailID() + "");
            whatsappNumberEt.setText(loginDataResponse.getData().getMobileNo() + "");
        }
        closeBtn.setOnClickListener(v -> alertDialogSocial.dismiss());
        verifyEmailBtn.setOnClickListener(v -> {
            if (isNetworkAvialable(context)) {
                VerifyEmail(context, loader, loginDataResponse, object -> {
                    BasicResponse mBasicResponse = (BasicResponse) object;
                    verifyEmailTxt.setText(mBasicResponse.getMsg() + "");
                    verifyEmailTxt.setTextColor(context.getResources().getColor(R.color.green));
                    verifyEmailBtn.setVisibility(View.GONE);

                    if (socialView.getVisibility() == View.GONE || socialInputView.getVisibility() == View.GONE) {
                        alertDialogSocial.setCancelable(true);
                    }
                });
            } else {
                NetworkError(context);
            }

        });

        submitBtn.setOnClickListener(v -> {
            if (whatsappNumberEt.getText().toString().trim().isEmpty() || whatsappNumberEt.getText().toString().trim().length() != 10) {
                whatsappNumberEt.setError("Please enter valid Whatsapp Number");
                whatsappNumberEt.requestFocus();
                return;
            } else if (telegramNumberEt.getText().toString().trim().isEmpty() || telegramNumberEt.getText().toString().trim().length() != 10) {
                telegramNumberEt.setError("Please enter valid Telegram Number");
                telegramNumberEt.requestFocus();
                return;
            } else if (hangoutEt.getText().toString().trim().isEmpty() || !hangoutEt.getText().toString().trim().contains("@")
                    || !hangoutEt.getText().toString().trim().contains(".")) {
                hangoutEt.setError("Please enter valid Hangout Email Id");
                hangoutEt.requestFocus();
                return;
            }

            if (isNetworkAvialable(context)) {
                submitSocialDetails(context, whatsappNumberEt.getText().toString().trim(), telegramNumberEt.getText().toString().trim(),
                        hangoutEt.getText().toString().trim(), loader, loginDataResponse, object -> {
                            BasicResponse mBasicResponse = (BasicResponse) object;
                            socialSaveTxt.setText(mBasicResponse.getMsg() + "");
                            socialSaveTxt.setTextColor(context.getResources().getColor(R.color.green));
                            socialSaveTxt.setVisibility(View.VISIBLE);
                            socialInputView.setVisibility(View.GONE);

                            if (verifyEmailView.getVisibility() == View.GONE || verifyEmailBtn.getVisibility() == View.GONE) {
                                alertDialogSocial.setCancelable(true);
                            }
                        });
            } else {
                NetworkError(context);
            }
        });


        alertDialogSocial.show();
    }


    public void VerifyEmail(Activity mContext, CustomLoader loader,
                            LoginResponse LoginDataResponse, ApiCallBack mApiCallBack) {
        try {
            if (loader != null) {
                loader.show();
            }
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<BasicResponse> call = git.SendEmailVerification(new BasicRequest(LoginDataResponse.getData().getUserID(),
                    LoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID, getIMEI(mContext), "", BuildConfig.VERSION_NAME, getSerialNo(mContext),
                    LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));

            call.enqueue(new Callback<BasicResponse>() {

                @Override
                public void onResponse(Call<BasicResponse> call, retrofit2.Response<BasicResponse> response) {

                    try {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        if (response.isSuccessful()) {
                            BasicResponse mBasicResponse = response.body();
                            if (mBasicResponse != null) {
                                if (mBasicResponse.getStatuscode() == 1) {
                                    setSocialorEmailDialogTimePref(mContext, System.currentTimeMillis());
                                    setEmailVerifiedPref(mContext, true);
                                    if (mApiCallBack != null) {
                                        mApiCallBack.onSucess(mBasicResponse);
                                    }
                                } else {
                                    if (!response.body().getVersionValid()) {
                                        versionDialog(mContext);
                                    } else {
                                        Error(mContext, mBasicResponse.getMsg() + "");
                                    }

                                }

                            }
                        } else {
                            apiErrorHandle(mContext, response.code(), response.message());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        Error(mContext, e.getMessage() + "");
                    }


                }

                @Override
                public void onFailure(Call<BasicResponse> call, Throwable t) {
                    t.printStackTrace();

                    try {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        apiFailureError(mContext, t);
                    } catch (IllegalStateException ise) {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }

                        Error(mContext, ise.getMessage() + "");
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            if (loader != null) {
                if (loader.isShowing()) {
                    loader.dismiss();
                }
            }
            Error(mContext, e.getMessage() + "");
        }

    }

    public void submitSocialDetails(Activity mContext, String whatsappNum, String telegramNum, String hangoutId, CustomLoader loader,
                                    LoginResponse LoginDataResponse, ApiCallBack mApiCallBack) {
        try {
            if (loader != null) {
                loader.show();
            }
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<BasicResponse> call = git.SaveSocialAlertSetting(new SubmitSocialDetailsRequest(whatsappNum, telegramNum, hangoutId,
                    LoginDataResponse.getData().getUserID(),
                    LoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID, getIMEI(mContext), "", BuildConfig.VERSION_NAME, getSerialNo(mContext),
                    LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));

            call.enqueue(new Callback<BasicResponse>() {

                @Override
                public void onResponse(Call<BasicResponse> call, retrofit2.Response<BasicResponse> response) {


                    try {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        if (response.isSuccessful()) {
                            BasicResponse mBasicResponse = response.body();
                            if (mBasicResponse != null) {
                                if (mBasicResponse.getStatuscode() == 1) {
                                    setSocialorEmailDialogTimePref(mContext, System.currentTimeMillis());
                                    setSocialLinkSavedPref(mContext, true);
                                    if (mApiCallBack != null) {
                                        mApiCallBack.onSucess(mBasicResponse);
                                    }
                                } else {
                                    if (!response.body().getVersionValid()) {
                                        versionDialog(mContext);
                                    } else {
                                        Error(mContext, mBasicResponse.getMsg() + "");
                                    }

                                }

                            }
                        } else {
                            apiErrorHandle(mContext, response.code(), response.message());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        Error(mContext, e.getMessage() + "");
                    }

                }

                @Override
                public void onFailure(Call<BasicResponse> call, Throwable t) {
                    t.printStackTrace();

                    try {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        apiFailureError(mContext, t);
                    } catch (IllegalStateException ise) {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }

                        Error(mContext, ise.getMessage() + "");
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            if (loader != null) {
                if (loader.isShowing()) {
                    loader.dismiss();
                }
            }
            Error(mContext, e.getMessage() + "");
        }

    }

    public void GetASCollectBank(final Activity context, final CustomLoader loader, LoginResponse LoginDataResponse,
                                 final ApiCallBack mApiCallBack) {
        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<BankListResponse> call = git.GetASCollectBank(new BalanceRequest(LoginDataResponse.getData().getUserID() + "", LoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID, getIMEI(context), "", BuildConfig.VERSION_NAME,
                    getSerialNo(context), LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));
            call.enqueue(new Callback<BankListResponse>() {
                @Override
                public void onResponse(Call<BankListResponse> call, final retrofit2.Response<BankListResponse> response) {
                    try {

                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        if (response.isSuccessful()) {
                            if (response.body() != null) {

                                if (response.body().getStatuscode() == 1) {
                                    if (response.body().getBankMasters() != null && response.body().getBankMasters().size() > 0) {
                                        setFosBankList(context, new Gson().toJson(response.body()));
                                        if (mApiCallBack != null) {
                                            mApiCallBack.onSucess(response.body());
                                        }
                                    } else {
                                        Error(context, "Bank not found");

                                    }

                                } else {
                                    if (!response.body().getIsVersionValid()) {
                                        versionDialog(context);
                                    } else {
                                        Error(context, response.body().getMsg() + "");
                                    }


                                }
                            }
                        } else {

                            apiErrorHandle(context, response.code(), response.message());
                        }
                    } catch (Exception e) {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }

                    }


                }

                @Override
                public void onFailure(Call<BankListResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing()) {
                            loader.dismiss();
                        }
                    }
                    try {
                        if (t instanceof UnknownHostException || t instanceof IOException) {
                            NetworkError(context);

                        } else if (t instanceof SocketTimeoutException || t instanceof TimeoutException) {
                            ErrorWithTitle(context, "TIME OUT ERROR", t.getMessage() + "");

                        } else {

                            if (t.getMessage() != null && !t.getMessage().isEmpty()) {
                                ErrorWithTitle(context, "FATAL ERROR", t.getMessage() + "");
                            } else {
                                Error(context, context.getResources().getString(R.string.some_thing_error));
                            }
                        }


                    } catch (IllegalStateException ise) {
                        Error(context, ise.getMessage());

                    }

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            Error(context, e.getMessage());
            if (loader != null) {
                if (loader.isShowing()) {
                    loader.dismiss();
                }
            }

        }
    }

    public void GeUserCommissionRate(Activity mContext, int uid, CustomLoader loader, LoginResponse LoginDataResponse, ApiCallBack mApiCallBack) {
        try {
            loader.show();
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<BasicResponse> call = git.GeUserCommissionRate(new BasicRequest(uid,
                    LoginDataResponse.getData().getUserID(), LoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID, getIMEI(mContext), "", BuildConfig.VERSION_NAME, getSerialNo(mContext),
                    LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));

            call.enqueue(new Callback<BasicResponse>() {

                @Override
                public void onResponse(Call<BasicResponse> call, retrofit2.Response<BasicResponse> response) {

                    try {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        if (response.isSuccessful()) {
                            BasicResponse mBasicResponse = response.body();
                            if (mBasicResponse != null) {
                                if (mBasicResponse.getStatuscode() == 1) {

                                    if (mApiCallBack != null) {
                                        mApiCallBack.onSucess(mBasicResponse);
                                    }
                                } else {

                                    Error(mContext, mBasicResponse.getMsg() + "");
                                }

                            } else {

                                Error(mContext, mContext.getResources().getString(R.string.some_thing_error));
                            }
                        } else {
                            apiErrorHandle(mContext, response.code(), response.message());
                        }
                    } catch (Exception e) {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        Error(mContext, e.getMessage() + "");
                    }

                }

                @Override
                public void onFailure(Call<BasicResponse> call, Throwable t) {


                    try {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        apiFailureError(mContext, t);
                    } catch (IllegalStateException ise) {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }

                        Error(mContext, mContext.getResources().getString(R.string.some_thing_error));
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            if (loader != null) {
                if (loader.isShowing()) {
                    loader.dismiss();
                }
            }
            Error(mContext, mContext.getResources().getString(R.string.some_thing_error));
        }

    }

    public void GetArealist(final Activity context, final CustomLoader loader, LoginResponse LoginDataResponse, final ApiCallBack mApiCallBack) {
        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<AppGetAMResponse> call = git.AppGetAM(new AppGetAMRequest(ApplicationConstant.INSTANCE.APP_ID, getIMEI(context),
                    "", getSerialNo(context), LoginDataResponse.getData().getSession(),
                    LoginDataResponse.getData().getSessionID(), BuildConfig.VERSION_NAME, LoginDataResponse.getData().getLoginTypeID(), LoginDataResponse.getData().getUserID()
            ));
            call.enqueue(new Callback<AppGetAMResponse>() {
                @Override
                public void onResponse(Call<AppGetAMResponse> call, final retrofit2.Response<AppGetAMResponse> response) {


                    try {

                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        if (response.isSuccessful()) {
                            if (response.body() != null) {

                                if (response.body().getStatuscode() == 1) {
                                    /*  if (response.body().getAreaMaster() != null && !response.body().getAreaMaster().isEmpty()) {*/

                                    setAreaListPref(context, new Gson().toJson(response.body()));


                                    if (mApiCallBack != null) {
                                        mApiCallBack.onSucess(response.body());
                                    }
                                    /*else {
                                        Error(context, "Area not found");
                                        if (mApiCallBack != null) {
                                            mApiCallBack.onError(ERROR_OTHER);
                                        }
                                    }*/

                                } else {
                                    if (!response.body().getIsVersionValid()) {
                                        versionDialog(context);
                                    } else {
                                        Error(context, response.body().getMsg() + "");
                                    }

                                }
                            }
                        } else {

                            apiErrorHandle(context, response.code(), response.message());
                        }
                    } catch (Exception e) {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }

                    }


                }

                @Override
                public void onFailure(Call<AppGetAMResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing()) {
                            loader.dismiss();
                        }
                    }
                    try {
                        if (t instanceof UnknownHostException || t instanceof IOException) {
                            NetworkError(context);

                        } else if (t instanceof SocketTimeoutException || t instanceof TimeoutException) {
                            ErrorWithTitle(context, "TIME OUT ERROR", t.getMessage() + "");

                        } else {

                            if (t.getMessage() != null && !t.getMessage().isEmpty()) {
                                ErrorWithTitle(context, "FATAL ERROR", t.getMessage() + "");
                            } else {
                                Error(context, context.getResources().getString(R.string.some_thing_error));
                            }
                        }


                    } catch (IllegalStateException ise) {
                        Error(context, ise.getMessage());

                    }
                }


            });

        } catch (Exception e) {
            e.printStackTrace();
            Error(context, e.getMessage());
            if (loader != null) {
                if (loader.isShowing()) {
                    loader.dismiss();
                }
            }

        }
    }


    /* public void FosAccStmtAndCollFilterFos(final Activity context, String topRows,
                                           String fromDate, String toDate,
                                           String utype,
                                           final CustomLoader loader, LoginResponse mLoginDataResponse, int areaid, final ApiCallBack mApiCallBack) {
        try {


            EndPointInterface git = ApiClient.getClient().create( EndPointInterface.class );
            Call<FosAccStmtAndCollReportResponse> call = git.AccStmtAndColl( new FosAccStmtAndCollReportRequest( ApplicationConstant.INSTANCE.APP_ID,
                    fromDate, ""
                    , getSerialNo(context), mLoginDataResponse.getData().getSession(),
                    mLoginDataResponse.getData().getSessionID(), toDate, topRows
                    , BuildConfig.VERSION_NAME, utype,
                    areaid, mLoginDataResponse.getData().getUserID(),
                    mLoginDataResponse.getData().getLoginTypeID(), getIMEI( context )
            ) );
            call.enqueue( new Callback<FosAccStmtAndCollReportResponse>() {

                @Override
                public void onResponse(Call<FosAccStmtAndCollReportResponse> call, final retrofit2.Response<FosAccStmtAndCollReportResponse> response) {

                    try {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        if (response.isSuccessful()) {
                            mAppPreferences.set( ApplicationConstant.INSTANCE.ascReportPref, new Gson().toJson( response.body() ) );


                            if (response.body() != null) {
                                if (response.body().getStatuscode() == 1) {

                                    if (mApiCallBack != null) {
                                        mApiCallBack.onSucess( response.body() );
                                    }

                                } else {
                                    if (mApiCallBack != null) {
                                        mApiCallBack.onError( ERROR_OTHER );
                                    }
                                    if (!response.body().getIsVersionValid()) {
                                        versionDialog( context );
                                    } else {
                                        Error( context, response.body().getMsg() + "" );
                                    }
                                }
                            }
                        } else {
                            if (mApiCallBack != null) {
                                mApiCallBack.onError( ERROR_OTHER );
                            }
                            apiErrorHandle( context, response.code(), response.message() );
                        }
                    } catch (Exception e) {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        if (mApiCallBack != null) {
                            mApiCallBack.onError( ERROR_OTHER );
                        }
                    }

                }

                @Override
                public void onFailure(Call<FosAccStmtAndCollReportResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing()) {
                            loader.dismiss();
                        }
                    }
                    try {
                        if (t instanceof UnknownHostException || t instanceof IOException) {
                            NetworkError( context );
                            if (mApiCallBack != null) {
                                mApiCallBack.onError( ERROR_NETWORK );
                            }
                        } else if (t instanceof SocketTimeoutException || t instanceof TimeoutException) {
                            ErrorWithTitle( context, "TIME OUT ERROR", t.getMessage() + "" );
                            if (mApiCallBack != null) {
                                mApiCallBack.onError( ERROR_OTHER );
                            }
                        } else {
                            if (mApiCallBack != null) {
                                mApiCallBack.onError( ERROR_OTHER );
                            }
                            if (t.getMessage() != null && !t.getMessage().isEmpty()) {
                                ErrorWithTitle( context, "FATAL ERROR", t.getMessage() + "" );
                            } else {
                                Error( context, context.getResources().getString( R.string.some_thing_error ) );
                            }
                        }

                    } catch (IllegalStateException ise) {
                        if (mApiCallBack != null) {
                            mApiCallBack.onError( ERROR_OTHER );
                        }
                        Error( context, ise.getMessage() );

                    }
                }

            } );

        } catch (Exception e) {
            if (mApiCallBack != null) {
                mApiCallBack.onError( ERROR_OTHER );
            }
            e.printStackTrace();
        }
    }
*/
    public void AccStmtAndCollFilterFosClick(final Activity context, String topRows,
                                             String fromDate, String toDate,
                                             String utype,
                                             final CustomLoader loader, LoginResponse mLoginDataResponse, int areaid, final ApiCallBack mApiCallBack) {
        try {


            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<FosAccStmtAndCollReportResponse> call = git.AccStmtAndColl(new FosAccStmtAndCollReportRequest(ApplicationConstant.INSTANCE.APP_ID,
                    fromDate, ""
                    , getSerialNo(context), mLoginDataResponse.getData().getSession(), mLoginDataResponse.getData().getSessionID(), toDate, topRows
                    , BuildConfig.VERSION_NAME, utype, areaid,
                    mLoginDataResponse.getData().getUserID(), mLoginDataResponse.getData().getLoginTypeID(), getIMEI(context)
            ));
            call.enqueue(new Callback<FosAccStmtAndCollReportResponse>() {

                @Override
                public void onResponse(Call<FosAccStmtAndCollReportResponse> call, final retrofit2.Response<FosAccStmtAndCollReportResponse> response) {

                    try {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        if (response.isSuccessful()) {


                            if (response.body() != null) {
                                if (response.body().getStatuscode() == 1) {
                                    // setAscReportPref(context, new Gson().toJson( response.body() ) );
                                    if (mApiCallBack != null) {
                                        mApiCallBack.onSucess(response.body());
                                    }

                                } else {

                                    if (!response.body().getIsVersionValid()) {
                                        versionDialog(context);
                                    } else {
                                        Error(context, response.body().getMsg() + "");
                                    }
                                }
                            }
                        } else {

                            apiErrorHandle(context, response.code(), response.message());
                        }
                    } catch (Exception e) {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        Error(context, e.getMessage());
                    }

                }

                @Override
                public void onFailure(Call<FosAccStmtAndCollReportResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing()) {
                            loader.dismiss();
                        }
                    }
                    try {
                        if (t instanceof UnknownHostException || t instanceof IOException) {
                            NetworkError(context);

                        } else if (t instanceof SocketTimeoutException || t instanceof TimeoutException) {
                            ErrorWithTitle(context, "TIME OUT ERROR", t.getMessage() + "");

                        } else {

                            if (t.getMessage() != null && !t.getMessage().isEmpty()) {
                                ErrorWithTitle(context, "FATAL ERROR", t.getMessage() + "");
                            } else {
                                Error(context, context.getResources().getString(R.string.some_thing_error));
                            }
                        }

                    } catch (IllegalStateException ise) {

                        Error(context, ise.getMessage());

                    }
                }

            });

        } catch (Exception e) {
            Error(context, e.getMessage());
            e.printStackTrace();
        }
    }

    public void AccStmtRport(final Activity context, String mobile, String topRows,
                             String fromDate, String toDate,
                             String utype,
                             final CustomLoader loader, LoginResponse mLoginDataResponse, int areaid, final ApiCallBack mApiCallBack) {
        try {


            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<FosAccStmtAndCollReportResponse> call = git.GetASSumm(new FosAccStmtAndCollReportRequest(mobile, ApplicationConstant.INSTANCE.APP_ID,
                    fromDate, ""
                    , getSerialNo(context), mLoginDataResponse.getData().getSession(), mLoginDataResponse.getData().getSessionID(), toDate, topRows
                    , BuildConfig.VERSION_NAME, utype, areaid,
                    mLoginDataResponse.getData().getUserID(), mLoginDataResponse.getData().getLoginTypeID(), getIMEI(context)
            ));
            call.enqueue(new Callback<FosAccStmtAndCollReportResponse>() {

                @Override
                public void onResponse(Call<FosAccStmtAndCollReportResponse> call, final retrofit2.Response<FosAccStmtAndCollReportResponse> response) {

                    try {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        if (response.isSuccessful()) {


                            if (response.body() != null) {
                                if (response.body().getStatuscode() == 1) {
                                    // setAscReportPref(context, new Gson().toJson( response.body() ) );
                                    if (mApiCallBack != null) {
                                        mApiCallBack.onSucess(response.body());
                                    }

                                } else {

                                    if (!response.body().getIsVersionValid()) {
                                        versionDialog(context);
                                    } else {
                                        Error(context, response.body().getMsg() + "");
                                    }
                                }
                            }
                        } else {

                            apiErrorHandle(context, response.code(), response.message());
                        }
                    } catch (Exception e) {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        Error(context, e.getMessage());
                    }

                }

                @Override
                public void onFailure(Call<FosAccStmtAndCollReportResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing()) {
                            loader.dismiss();
                        }
                    }
                    try {
                        if (t instanceof UnknownHostException || t instanceof IOException) {
                            NetworkError(context);

                        } else if (t instanceof SocketTimeoutException || t instanceof TimeoutException) {
                            ErrorWithTitle(context, "TIME OUT ERROR", t.getMessage() + "");

                        } else {

                            if (t.getMessage() != null && !t.getMessage().isEmpty()) {
                                ErrorWithTitle(context, "FATAL ERROR", t.getMessage() + "");
                            } else {
                                Error(context, context.getResources().getString(R.string.some_thing_error));
                            }
                        }

                    } catch (IllegalStateException ise) {

                        Error(context, ise.getMessage());

                    }
                }

            });

        } catch (Exception e) {
            Error(context, e.getMessage());
            e.printStackTrace();
        }
    }


    public void ASPayCollect(Activity mContext, int uid, String remark, String amount,
                             final String userName, CustomLoader loader, final String collectionMode, String bankUtr, String bankName,
                             LoginResponse mLoginDataResponse,
                             final ApiCallBack mFundTransferCallBAck) {

        try {
            loader.show();

            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<AppUserListResponse> call = git.ASPayCollect(new ASPayCollectRequest(ApplicationConstant.INSTANCE.APP_ID
                    , getIMEI(mContext), "", getSerialNo(mContext), mLoginDataResponse.getData().getSession()
                    , mLoginDataResponse.getData().getSessionID(), BuildConfig.VERSION_NAME
                    , mLoginDataResponse.getData().getLoginTypeID(), mLoginDataResponse.getData().getUserID()
                    , uid, collectionMode, amount, remark, bankName, bankUtr));

            call.enqueue(new Callback<AppUserListResponse>() {

                @Override
                public void onResponse(Call<AppUserListResponse> call, retrofit2.Response<AppUserListResponse> response) {


                    try {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }


                        if (response.isSuccessful()) {
                            AppUserListResponse data = response.body();
                            if (data != null) {
                                if (data.getStatuscode() == 1) {

                                    if (mFundTransferCallBAck != null) {
                                        mFundTransferCallBAck.onSucess(data);
                                    }
                                    Successful(mContext, data.getMsg().replace("{User}", userName));

                                } else {
                                    Error(mContext, data.getMsg().replace("{User}", userName));
                                }

                            } else {
                                Error(mContext, mContext.getResources().getString(R.string.some_thing_error));
                            }


                        } else {
                            apiErrorHandle(mContext, response.code(), response.message());
                        }

                    } catch (Exception e) {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        Error(mContext, e.getMessage() + "");
                    }

                }

                @Override
                public void onFailure(Call<AppUserListResponse> call, Throwable t) {

                    try {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        apiFailureError(mContext, t);
                    } catch (IllegalStateException ise) {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        Error(mContext, ise.getMessage() + "");
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            if (loader != null) {
                if (loader.isShowing()) {
//                    loader.dismiss();
                }
            }
        }
    }


    public void SummaryDashboard(final Activity context, final CustomLoader loader) {
        try {

            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            String LoginResponse = getLoginPref(context);
            LoginResponse LoginDataResponse = new Gson().fromJson(LoginResponse, LoginResponse.class);

            Call<Summary> call = git.SummaryDashboard(new BalanceRequest(LoginDataResponse.getData().getUserID() + "", LoginDataResponse.getData().getLoginTypeID() + "", ApplicationConstant.INSTANCE.APP_ID, getIMEI(context), "", BuildConfig.VERSION_NAME, getSerialNo(context), LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));
            call.enqueue(new Callback<Summary>() {
                @Override
                public void onResponse(Call<Summary> call, final retrofit2.Response<Summary> response) {
                    try {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        /*Your Code Here*/
                        if (response.body() != null
                                && response.body().getStatuscode() != null
                                && response.body().getStatuscode().equalsIgnoreCase("1")
                                && response.body().getAccountSummary() != null
                                && response.body().getAccountSummary().getStatusCode().equalsIgnoreCase("1")) {
                            FragmentActivityMessage fragmentActivityMessage = new FragmentActivityMessage("" + new Gson().toJson(response.body()).toString(), "SummaryDashboard");
                            GlobalBus.getBus().post(fragmentActivityMessage);
                        }
                    } catch (Exception e) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                    }

                }

                @Override
                public void onFailure(Call<Summary> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            loader.dismiss();
                    }

                    Error(context, context.getResources().getString(R.string.err_something_went_wrong));

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ChangePinPassword(final Activity context, final boolean isPin, final String oldPass, String newPass, String confPass, final CustomLoader loader, final Dialog dialog) {
        try {
            String LoginResponse = getLoginPref(context);
            LoginResponse LoginDataResponse = new Gson().fromJson(LoginResponse, LoginResponse.class);
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);

            Call<RechargeReportResponse> call = git.ChangePinOrPassword(new ChangePinPasswordRequest(isPin, oldPass, newPass, confPass,
                    LoginDataResponse.getData().getUserID() + "", LoginDataResponse.getData().getLoginTypeID() + "",
                    ApplicationConstant.INSTANCE.APP_ID,
                    getIMEI(context),
                    "", BuildConfig.VERSION_NAME, getSerialNo(context), LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));

            call.enqueue(new Callback<RechargeReportResponse>() {

                @Override
                public void onResponse(Call<RechargeReportResponse> call, Response<RechargeReportResponse> response) {
                    if (loader.isShowing())
                        loader.dismiss();

                    if (response.body() != null && response.body().getStatuscode() != null) {
                        if (response.body().getStatuscode().equalsIgnoreCase("1")) {
                            dialog.dismiss();
                            isPassChangeDialogShowing = false;
                            Successful(context, response.body().getMsg() + "");
                           /* if (!isPin) {
                                logout(context);
                            }*/

                        } else if (response.body().getStatuscode().equalsIgnoreCase("-1")) {
                            if (response.body().getIsVersionValid() != null && response.body().getIsVersionValid().equalsIgnoreCase("false")) {
                                versionDialog(context);
                            } else {
                                Error(context, response.body().getMsg() + "");
                            }
                        }

                    }
                }

                @Override
                public void onFailure(Call<RechargeReportResponse> call, Throwable t) {

                    if (loader.isShowing())
                        loader.dismiss();
                    Error(context, context.getResources().getString(R.string.err_something_went_wrong) + "");
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void GetBanklist(final Activity context, final CustomLoader loader, final ApiCallBack mApiCallBack) {
        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            String LoginResponse = getLoginPref(context);
            LoginResponse LoginDataResponse = new Gson().fromJson(LoginResponse, LoginResponse.class);
            Call<BankListResponse> call = git.GetBankList(new BalanceRequest(LoginDataResponse.getData().getUserID() + "", LoginDataResponse.getData().getLoginTypeID() + "",
                    ApplicationConstant.INSTANCE.APP_ID,
                    getIMEI(context),
                    "", BuildConfig.VERSION_NAME, getSerialNo(context), LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));
            call.enqueue(new Callback<BankListResponse>() {
                @Override
                public void onResponse(Call<BankListResponse> call, final retrofit2.Response<BankListResponse> response) {

                    try {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        if (response.body() != null) {

                            if (response.body().getStatuscode() == 1) {
                                setBankList(context, new Gson().toJson(response.body()));
                                if (mApiCallBack != null) {
                                    mApiCallBack.onSucess(response.body());
                                }
                            } else {
                                if (!response.body().getIsVersionValid()) {
                                    versionDialog(context);
                                } else {
                                    Error(context, response.body().getMsg() + "");
                                }
                            }
                        }
                    } catch (Exception e) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                    }

                }

                @Override
                public void onFailure(Call<BankListResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            loader.dismiss();
                    }
                    try {
                        if (t.getMessage() != null && !t.getMessage().isEmpty()) {

                            if (t.getMessage().contains("No address associated with hostname")) {
                                NetworkError(context, context.getResources().getString(R.string.err_msg_network_title),
                                        context.getResources().getString(R.string.err_msg_network));
                            } else {
                                Error(context, t.getMessage());

                            }

                        } else {
                            Error(context, context.getResources().getString(R.string.some_thing_error));

                        }
                    } catch (IllegalStateException ise) {
                        Error(context, ise.getMessage());

                    }

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void FundRequestTo(final Activity context, final CustomLoader loader, final ApiCallBack mApiCallBack) {
        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            String LoginResponse = getLoginPref(context);
            LoginResponse LoginDataResponse = new Gson().fromJson(LoginResponse, LoginResponse.class);
            Call<FundreqToResponse> call = git.FundRequestTo(new BalanceRequest(LoginDataResponse.getData().getUserID() + "", LoginDataResponse.getData().getLoginTypeID() + "",
                    ApplicationConstant.INSTANCE.APP_ID,
                    getIMEI(context),
                    "", BuildConfig.VERSION_NAME, getSerialNo(context), LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));
            call.enqueue(new Callback<FundreqToResponse>() {
                @Override
                public void onResponse(Call<FundreqToResponse> call, final retrofit2.Response<FundreqToResponse> response) {

                    try {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        if (response.body() != null && response.body().getStatuscode() != null) {

                            if (response.body().getStatuscode().equalsIgnoreCase("1")) {
                                setFundreqToList(context, new Gson().toJson(response.body()).toString());
                                if (mApiCallBack != null) {
                                    mApiCallBack.onSucess(response.body());
                                }
                               /* FragmentActivityMessage activityActivityMessage =
                                        new FragmentActivityMessage(new Gson().toJson(response.body()).toString(), "SelectedRole");
                                GlobalBus.getBus().post(activityActivityMessage);*/
                            } else if (response.body().getStatuscode().equalsIgnoreCase("-1")) {
                                if (response.body().getIsVersionValid() != null && response.body().getIsVersionValid().equalsIgnoreCase("false")) {
                                    versionDialog(context);
                                } else {
                                    Error(context, response.body().getMsg() + "");
                                }
                            }
                        }
                    } catch (Exception e) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                    }

                }

                @Override
                public void onFailure(Call<FundreqToResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            loader.dismiss();
                    }
                    Error(context, context.getResources().getString(R.string.err_something_went_wrong));

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void GetBankAndPaymentMode(final Activity context, String Parentid, final CustomLoader loader, final ApiCallBack mApiCallBack) {
        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            String LoginResponse = getLoginPref(context);
            LoginResponse LoginDataResponse = new Gson().fromJson(LoginResponse, LoginResponse.class);

            BalanceRequest mBalanceRequest = new BalanceRequest(Parentid, "",
                    LoginDataResponse.getData().getUserID() + "", LoginDataResponse.getData().getLoginTypeID() + "",
                    ApplicationConstant.INSTANCE.APP_ID,
                    getIMEI(context),
                    "", BuildConfig.VERSION_NAME, getSerialNo(context), LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession());
            String str = new Gson().toJson(mBalanceRequest);
            Call<GetBankAndPaymentModeResponse> call = git.GetBankAndPaymentMode(mBalanceRequest);
            call.enqueue(new Callback<GetBankAndPaymentModeResponse>() {
                @Override
                public void onResponse(Call<GetBankAndPaymentModeResponse> call, final retrofit2.Response<GetBankAndPaymentModeResponse> response) {

                    try {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        if (response.body() != null && response.body().getStatuscode() != null) {
                            if (response.body().getStatuscode().equalsIgnoreCase("1")) {
                                // setFundreqToList(context, new Gson().toJson(response.body()).toString());
                                if (mApiCallBack != null) {
                                    mApiCallBack.onSucess(response.body());
                                }
                                /*FragmentActivityMessage activityActivityMessage =
                                        new FragmentActivityMessage(new Gson().toJson(response.body()).toString(), "SelectedBank");
                                GlobalBus.getBus().post(activityActivityMessage);*/
                            } else if (response.body().getStatuscode().equalsIgnoreCase("-1")) {
                                if (response.body().getIsVersionValid() == false) {
                                    versionDialog(context);
                                } else {
                                    Error(context, response.body().getMsg() + "");
                                }
                            }
                        }
                    } catch (Exception e) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                    }

                }

                @Override
                public void onFailure(Call<GetBankAndPaymentModeResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            loader.dismiss();
                    }
                    Error(context, context.getResources().getString(R.string.err_something_went_wrong));

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            if (loader != null) {
                if (loader.isShowing())
                    loader.dismiss();
            }
        }
    }

    public void GetVADetails(final Activity context, final CustomLoader loader, LoginResponse LoginDataResponse,
                             final ApiCallBack mApiCallBack) {
        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);

            Call<GetVAResponse> call = git.GetVADetail(new BasicRequest(
                    LoginDataResponse.getData().getUserID(), LoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID,
                    getIMEI(context),
                    "", BuildConfig.VERSION_NAME, getSerialNo(context), LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));
            call.enqueue(new Callback<GetVAResponse>() {

                @Override
                public void onResponse(Call<GetVAResponse> call, retrofit2.Response<GetVAResponse> response) {
                    if (loader != null && loader.isShowing()) {
                        loader.dismiss();
                    }
                    if (response.isSuccessful()) {
                        GetVAResponse apiData = response.body();
                        if (apiData != null) {
                            if (apiData.getStatuscode() == 1) {
                                if (mApiCallBack != null) {
                                    mApiCallBack.onSucess(apiData.getUserQRInfo());
                                }

//
                            } else {
                                if (apiData.getVersionValid() == false) {
                                    versionDialog(context);
                                } else {
                                    Error(context, apiData.getMsg() + "");
                                }
                            }

                        }
                    } else {
                        apiErrorHandle(context, response.code(), response.message());
                    }
                }

                @Override
                public void onFailure(Call<GetVAResponse> call, Throwable t) {
                    if (loader != null && loader.isShowing()) {
                        loader.dismiss();
                    }
                    try {
                        if (t instanceof UnknownHostException || t instanceof IOException) {
                            NetworkError(context);
                        } else if (t instanceof SocketTimeoutException || t instanceof TimeoutException) {
                            ErrorWithTitle(context, "TIME OUT ERROR", t.getMessage() + "");
                        } else {
                            if (t.getMessage() != null && !t.getMessage().isEmpty()) {
                                ErrorWithTitle(context, "FATAL ERROR", t.getMessage() + "");
                            } else {
                                Error(context, context.getResources().getString(R.string.some_thing_error));
                            }
                        }
                    } catch (IllegalStateException ise) {
                        Error(context, ise.getMessage());

                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void GetOpTypes(final Activity context, final CustomLoader loader, final ApiCallBack mApiCallBack) {
        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            String LoginResponse = getLoginPref(context);
            LoginResponse LoginDataResponse = new Gson().fromJson(LoginResponse, LoginResponse.class);
            Call<OpTypeResponse> call = git.GetOpTypes(new BalanceRequest(LoginDataResponse.getData().getUserID() + "", LoginDataResponse.getData().getLoginTypeID() + "",
                    ApplicationConstant.INSTANCE.APP_ID,
                    getIMEI(context),
                    "", BuildConfig.VERSION_NAME, getSerialNo(context), LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));


            call.enqueue(new Callback<OpTypeResponse>() {
                @Override
                public void onResponse(Call<OpTypeResponse> call, final retrofit2.Response<OpTypeResponse> response) {
                    try {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        if (response.body() != null) {

                            if (response.body().getStatuscode() == 1) {

                                setOPTypeResponse(context, new Gson().toJson(response.body()).toString());
                                if (mApiCallBack != null) {
                                    mApiCallBack.onSucess(response.body());
                                }
                            } else if (response.body().getStatuscode() == -1) {
                                if (!response.body().getIsVersionValid()) {
                                    versionDialog(context);
                                } else {
                                    Error(context, response.body().getMsg() + "");
                                }
                            }
                        }
                    } catch (Exception e) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                    }

                }

                @Override
                public void onFailure(Call<OpTypeResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            loader.dismiss();
                    }
                    Error(context, context.getResources().getString(R.string.err_something_went_wrong));

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void Recharge(final Activity context, boolean isReal, int Opid,final String opName, String AccountNo, String Amount, String o1,
                         String o2, String o3, String o4, String customerNo, String refID, int fetchBillID, String GeoCode,
                         String securityKey, final CustomLoader loader) {
        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            String LoginResponse = getLoginPref(context);
            LoginResponse LoginDataResponse = new Gson().fromJson(LoginResponse, LoginResponse.class);
            Call<RechargeCResponse> call = git.Recharge(new RechargeRequest(isReal,
                    ApplicationConstant.INSTANCE.APP_ID,
                    getIMEI(context),
                    "", BuildConfig.VERSION_NAME, getSerialNo(context), LoginDataResponse.getData().getLoginTypeID(), Opid,
                    AccountNo, Amount, o1, o2, o3, o4, customerNo,
                    refID, GeoCode,
                    LoginDataResponse.getData().getUserID(), LoginDataResponse.getData().getSessionID(),
                    LoginDataResponse.getData().getSession(), securityKey, fetchBillID)
            );
            call.enqueue(new Callback<RechargeCResponse>() {
                @Override
                public void onResponse(Call<RechargeCResponse> call, final retrofit2.Response<RechargeCResponse> response) {
                    try {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        if (response.body() != null) {

                            if (response.body().getStatuscode() == 1) {

                                //Processing(context, response.body().getMsg() + "");
                                context.startActivity(new Intent(context, RechargeStatusActivity.class)
                                        .putExtra("MESSAGE", response.body().getMsg() + "")
                                        .putExtra("STATUS", response.body().getStatuscode())
                                        .putExtra("LIVE_ID", response.body().getLiveID() + "")
                                        .putExtra("TRANSACTION_ID", response.body().getTransactionID() + "")
                                        .putExtra("AMOUNT", Amount)
                                        .putExtra("OP_NAME", opName)
                                        .putExtra("NUMBER", AccountNo));
                            } else if (response.body().getStatuscode() == 2) {
                                context.startActivity(new Intent(context, RechargeStatusActivity.class)
                                        .putExtra("MESSAGE", response.body().getMsg() + "")
                                        .putExtra("STATUS", response.body().getStatuscode())
                                        .putExtra("LIVE_ID", response.body().getLiveID() + "")
                                        .putExtra("TRANSACTION_ID", response.body().getTransactionID() + "")
                                        .putExtra("AMOUNT", Amount)
                                        .putExtra("OP_NAME", opName)
                                        .putExtra("NUMBER", AccountNo));
                                //Successful(context, response.body().getMsg());
                            } else if (response.body().getStatuscode() == 3) {
                                //Failed(context, response.body().getMsg());
                                context.startActivity(new Intent(context, RechargeStatusActivity.class)
                                        .putExtra("MESSAGE", response.body().getMsg() + "")
                                        .putExtra("STATUS", response.body().getStatuscode())
                                        .putExtra("LIVE_ID", response.body().getLiveID() + "")
                                        .putExtra("TRANSACTION_ID", response.body().getTransactionID() + "")
                                        .putExtra("AMOUNT", Amount)
                                        .putExtra("OP_NAME", opName)
                                        .putExtra("NUMBER", AccountNo));

                            } else if (response.body().getStatuscode() == -1) {
                                if (response.body().getIsVersionValid() != null && response.body().getIsVersionValid().equalsIgnoreCase("false")) {
                                    versionDialog(context);
                                } else {
                                    Error(context, response.body().getMsg() + "");
                                }
                            }
                            ActivityActivityMessage activityActivityMessage =
                                    new ActivityActivityMessage("" + new Gson().toJson(response.body()).toString(), "refreshvalue");
                            GlobalBus.getBus().post(activityActivityMessage);
                            Balancecheck(context, loader, null);
                        }
                    } catch (Exception e) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                    }

                }

                @Override
                public void onFailure(Call<RechargeCResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            loader.dismiss();
                    }
                    try {
                        if (t.getMessage() != null && !t.getMessage().isEmpty()) {

                            if (t.getMessage().contains("No address associated with hostname")) {
                                NetworkError(context, context.getResources().getString(R.string.err_msg_network_title),
                                        context.getResources().getString(R.string.err_msg_network));
                            } else {
                                Processing(context, "Recharge request Accepted");

                            }

                        } else {
                            Processing(context, "Recharge request Accepted");

                        }
                    } catch (IllegalStateException ise) {
                        Error(context, ise.getMessage());

                    }


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ROffer(final Activity context,LoginResponse mLoginDataResponse, boolean isUpdatedPlan, String Opid, String AccountNo, final CustomLoader loader) {
        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<RofferResponse> call;
            if (isUpdatedPlan) {
                call = git.GetRNPRoffer(new ROfferRequest(Opid, AccountNo,
                        ApplicationConstant.INSTANCE.APP_ID,
                        getIMEI(context),
                        "", BuildConfig.VERSION_NAME, getSerialNo(context),mLoginDataResponse.getData().getUserID()
                        ,mLoginDataResponse.getData().getLoginTypeID(),
                        mLoginDataResponse.getData().getSessionID(),
                        mLoginDataResponse.getData().getSession()));
            } else {
                call = git.ROffer(new ROfferRequest(Opid, AccountNo,
                        ApplicationConstant.INSTANCE.APP_ID,
                        getIMEI(context),
                        "", BuildConfig.VERSION_NAME, getSerialNo(context),mLoginDataResponse.getData().getUserID()
                        ,mLoginDataResponse.getData().getLoginTypeID(),
                        mLoginDataResponse.getData().getSessionID(),
                        mLoginDataResponse.getData().getSession()));
            }

            call.enqueue(new Callback<RofferResponse>() {
                @Override
                public void onResponse(Call<RofferResponse> call, final retrofit2.Response<RofferResponse> response) {
                    try {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        if (response.body() != null) {

                            if (response.body().getStatuscode() == 1) {

                                if (response.body().getData() != null && response.body().getData().getRecords() != null && response.body().getData().getRecords().size() > 0 ||
                                        response.body().getRofferData() != null && response.body().getRofferData().size() > 0 ||
                                        response.body().getDataPA() != null && response.body().getDataPA().getError() == 0 && response.body().getDataPA().getRecords() != null && response.body().getDataPA().getRecords().size() > 0) {
                                    Intent browseIntent = new Intent(context, ROfferActivity.class);
                                    browseIntent.putExtra("response", response.body());
                                    context.startActivity(browseIntent);
                                } else if (response.body().getDataPA() != null && response.body().getDataPA().getError() != 0) {
                                    Error(context, response.body().getDataPA().getMessage() + "");
                                } else {
                                    Error(context, "Records not found");
                                }
                            } else if (response.body().getStatuscode() == -1) {
                                if (!response.body().isVersionValid()) {
                                    versionDialog(context);
                                } else {
                                    Error(context, response.body().getMsg() + "");
                                }
                            }

                        }
                    } catch (Exception e) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                    }

                }

                @Override
                public void onFailure(Call<RofferResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            loader.dismiss();
                    }
                    try {
                        if (t.getMessage() != null && !t.getMessage().isEmpty()) {

                            if (t.getMessage().contains("No address associated with hostname")) {
                                NetworkError(context, context.getResources().getString(R.string.err_msg_network_title),
                                        context.getResources().getString(R.string.err_msg_network));
                            } else {
                                Error(context, t.getMessage());

                            }

                        } else {
                            Error(context, context.getResources().getString(R.string.some_thing_error));

                        }
                    } catch (IllegalStateException ise) {
                        Error(context, ise.getMessage());

                    }

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void ViewPlan(final Activity context,LoginResponse mLoginDataResponse , boolean isPlanUpdated, String Opid, String CircleID, final CustomLoader loader) {
        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<ResponsePlan> call;
            if (isPlanUpdated) {
                call = git.RechagePlansUpdated(new PlanRequest(Opid, CircleID,
                        ApplicationConstant.INSTANCE.APP_ID,
                        getIMEI(context),
                        "", BuildConfig.VERSION_NAME, getSerialNo(context) ,mLoginDataResponse.getData().getUserID()
                        ,mLoginDataResponse.getData().getLoginTypeID(),
                        mLoginDataResponse.getData().getSessionID(),
                        mLoginDataResponse.getData().getSession()));
            } else {
                call = git.Rechageplans(new PlanRequest(Opid, CircleID,
                        ApplicationConstant.INSTANCE.APP_ID,
                        getIMEI(context),
                        "", BuildConfig.VERSION_NAME, getSerialNo(context) ,mLoginDataResponse.getData().getUserID()
                        ,mLoginDataResponse.getData().getLoginTypeID(),
                        mLoginDataResponse.getData().getSessionID(),
                        mLoginDataResponse.getData().getSession()));
            }

            call.enqueue(new Callback<ResponsePlan>() {
                @Override
                public void onResponse(Call<ResponsePlan> call, final retrofit2.Response<ResponsePlan> response) {
                    try {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        if (response.body() != null && response.body().getStatuscode() != null) {

                            if (response.body().getStatuscode().equalsIgnoreCase("1")) {
                                if (response.body().getData() != null && response.body().getData().getRecords() != null ||
                                        response.body().getData() != null && response.body().getData().getTypes() != null && response.body().getData().getTypes().size() > 0 ||
                                        response.body().getDataRP() != null && response.body().getDataRP().getRecords() != null ||
                                        response.body().getDataPA() != null && response.body().getDataPA().getError() == 0 && response.body().getDataPA().getRecords() != null) {

                                    Intent browseIntent = new Intent(context, BrowsePlanScreenActivity.class);
                                    browseIntent.putExtra("response", "" + new Gson().toJson(response.body()));
                                    browseIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                    context.startActivityForResult(browseIntent, 45);
                                } else if (response.body().getDataPA() != null && response.body().getDataPA().getError() != 0) {
                                    Error(context, response.body().getDataPA().getMessage() + "");
                                } else if (response.body().getStatuscode().equalsIgnoreCase("1")) {
                                    Error(context, "Plan not found");
                                }
                            } else if (response.body().getStatuscode().equalsIgnoreCase("-1")) {
                                if (response.body().getIsVersionValid() != null && response.body().getIsVersionValid().equalsIgnoreCase("false")) {
                                    versionDialog(context);
                                } else {
                                    Error(context, response.body().getMsg() + "");
                                }
                            }

                        }
                    } catch (Exception e) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        Error(context, e.getMessage() + "");
                    }

                }

                @Override
                public void onFailure(Call<ResponsePlan> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            loader.dismiss();
                    }
                    try {
                        if (t.getMessage() != null && !t.getMessage().isEmpty()) {

                            if (t.getMessage().contains("No address associated with hostname")) {
                                NetworkError(context, context.getResources().getString(R.string.err_msg_network_title),
                                        context.getResources().getString(R.string.err_msg_network));
                            } else {
                                Error(context, t.getMessage() + "");

                            }

                        } else {
                            Error(context, context.getResources().getString(R.string.some_thing_error));

                        }
                    } catch (IllegalStateException ise) {
                        Error(context, ise.getMessage());

                    }

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void RechargeReport(final Activity context, String opTypeId, String topValue,
                               int status, String fromDate, String toDate, String transactionID, String accountNo, String childMobileNumnber, String isExport, boolean IsRecent, final CustomLoader loader, final ApiCallBackTwoMethod mApiCallBack) {
        try {
            String LoginResponse = getLoginPref(context);
            LoginResponse LoginDataResponse = new Gson().fromJson(LoginResponse, LoginResponse.class);
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<RechargeReportResponse> call = git.RechargeReport(new RechargeReportRequest(IsRecent, opTypeId, "0",
                    ApplicationConstant.INSTANCE.APP_ID,
                    getIMEI(context),
                    "", BuildConfig.VERSION_NAME, getSerialNo(context),
                    topValue, status, fromDate, toDate, transactionID, accountNo, childMobileNumnber, isExport,
                    LoginDataResponse.getData().getUserID()
                    , LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession(),
                    LoginDataResponse.getData().getLoginTypeID()));
            call.enqueue(new Callback<RechargeReportResponse>() {
                @Override
                public void onResponse(Call<RechargeReportResponse> call, final retrofit2.Response<RechargeReportResponse> response) {
                    try {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        if (response.body() != null && response.body().getStatuscode() != null) {
                            if (response.body().getStatuscode().equalsIgnoreCase("1")) {
                                // Balancecheck(context, loader, null);
                                if (mApiCallBack != null) {
                                    mApiCallBack.onSucess(response.body());
                                }
                                /*ActivityActivityMessage activityActivityMessage =
                                        new ActivityActivityMessage("recent", "" + new Gson().toJson(response.body()));
                                GlobalBus.getBus().post(activityActivityMessage);*/
                            } else if (response.body().getStatuscode().equalsIgnoreCase("-1")) {
                                if (mApiCallBack != null) {
                                    mApiCallBack.onError(response.body());
                                }
                                if (response.body().getIsVersionValid() != null && response.body().getIsVersionValid().equalsIgnoreCase("false")) {
                                    versionDialog(context);
                                } else {
                                    Error(context, response.body().getMsg() + "");
                                }
                            }
                        } else {
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(null);
                            }
                        }
                    } catch (Exception e) {
                        if (mApiCallBack != null) {
                            mApiCallBack.onError(null);
                        }
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                    }

                }

                @Override
                public void onFailure(Call<RechargeReportResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            loader.dismiss();
                    }
                    if (mApiCallBack != null) {
                        mApiCallBack.onError(null);
                    }
                    try {
                        if (t.getMessage() != null && !t.getMessage().isEmpty()) {

                            if (t.getMessage().contains("No address associated with hostname")) {
                                NetworkError(context, context.getResources().getString(R.string.err_msg_network_title),
                                        context.getResources().getString(R.string.err_msg_network));
                            } else {
                                Error(context, t.getMessage());

                            }

                        } else {
                            Error(context, context.getResources().getString(R.string.some_thing_error));

                        }
                    } catch (IllegalStateException ise) {
                        Error(context, ise.getMessage());
                        if (mApiCallBack != null) {
                            mApiCallBack.onError(null);
                        }
                    }

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            if (mApiCallBack != null) {
                mApiCallBack.onError(null);
            }
        }
    }


    public void IncentiveDetail(final Activity context, String opTypeId, final CustomLoader loader, final ApiCallBack mApiCallBack) {
        try {
            String LoginResponse = getLoginPref(context);
            LoginResponse LoginDataResponse = new Gson().fromJson(LoginResponse, LoginResponse.class);
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<AppUserListResponse> call = git.IncentiveDetail(new IncentiveDetailRequest(opTypeId,
                    LoginDataResponse.getData().getUserID(), LoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID,
                    getIMEI(context),
                    "", BuildConfig.VERSION_NAME, getSerialNo(context), LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));
            call.enqueue(new Callback<AppUserListResponse>() {
                @Override
                public void onResponse(Call<AppUserListResponse> call, final retrofit2.Response<AppUserListResponse> response) {
                    try {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        if (response.body() != null) {
                            if (response.body().getStatuscode() == 1) {
                                if (response.body().getIncentiveDetails() != null && response.body().getIncentiveDetails().size() > 0) {
                                    if (mApiCallBack != null) {
                                        mApiCallBack.onSucess(response.body());
                                    }
                                } else {
                                    Error(context, "Data Not Found");
                                }
                            } else if (response.body().getStatuscode() == -1) {
                                if (response.body().isVersionValid() == false) {
                                    versionDialog(context);
                                } else {
                                    Error(context, response.body().getMsg() + "");
                                }
                            }
                        } else {
                            Error(context, context.getResources().getString(R.string.some_thing_error));
                        }
                    } catch (Exception e) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        Error(context, e.getMessage() + "");

                    }

                }

                @Override
                public void onFailure(Call<AppUserListResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            loader.dismiss();
                    }
                    try {
                        if (t.getMessage() != null && !t.getMessage().isEmpty()) {

                            if (t.getMessage().contains("No address associated with hostname")) {
                                NetworkError(context, context.getResources().getString(R.string.err_msg_network_title),
                                        context.getResources().getString(R.string.err_msg_network));
                            } else {
                                Error(context, t.getMessage());

                            }

                        } else {
                            Error(context, context.getResources().getString(R.string.some_thing_error));

                        }
                    } catch (IllegalStateException ise) {
                        Error(context, ise.getMessage() + "");

                    }

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            Error(context, e.getMessage() + "");
        }
    }

    /* public void LastRechargeReport(final Activity context, String oid,
                                    String status, String fromDate, String toDate, String transactionID, String accountNo, String isExport, final View comingSoonView, final CustomLoader loader, final ApiCallBack mApiCallBack) {
         try {
             String LoginResponse = getLoginPref(context);
             LoginResponse LoginDataResponse = new Gson().fromJson(LoginResponse, LoginResponse.class);
             EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
             Call<RechargeReportResponse> call = git.RechargeReport(new RechargeReportRequest(true,"0", oid,
                     ApplicationConstant.INSTANCE.APP_ID,
                     getIMEI(context),
                     "", BuildConfig.VERSION_NAME, getSerialNo(context),
                     "100", status, fromDate, toDate, transactionID, accountNo, isExport,
                     LoginDataResponse.getData().getUserID()
                     , LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession(),
                     LoginDataResponse.getData().getLoginTypeID()));
             call.enqueue(new Callback<RechargeReportResponse>() {
                 @Override
                 public void onResponse(Call<RechargeReportResponse> call, final retrofit2.Response<RechargeReportResponse> response) {

                     try {
                         if (loader != null) {
                             if (loader.isShowing())
                                 loader.dismiss();
                         }
                         if (response.body() != null && response.body().getStatuscode() != null) {
                             if (response.body().getStatuscode().equalsIgnoreCase("1")) {
                                 Balancecheck(context, loader, null);
                                 if (comingSoonView != null) {
                                     comingSoonView.setVisibility(View.GONE);
                                 }
                                 if (mApiCallBack != null) {
                                     mApiCallBack.onSucess(response.body());
                                 }

                             } else if (response.body().getStatuscode().equalsIgnoreCase("-1")) {
                                 if (comingSoonView != null) {
                                     comingSoonView.setVisibility(View.VISIBLE);
                                 }
                                 if (response.body().getIsVersionValid() != null && response.body().getIsVersionValid().equalsIgnoreCase("false")) {
                                     versionDialog(context);
                                 } else {
                                     Error(context, response.body().getMsg() + "");
                                 }
                             }
                         } else {
                             if (comingSoonView != null) {
                                 comingSoonView.setVisibility(View.VISIBLE);
                             }
                         }
                     } catch (Exception e) {
                         if (comingSoonView != null) {
                             comingSoonView.setVisibility(View.VISIBLE);
                         }
                         if (loader != null) {
                             if (loader.isShowing())
                                 loader.dismiss();
                         }
                     }

                 }

                 @Override
                 public void onFailure(Call<RechargeReportResponse> call, Throwable t) {
                     if (loader != null) {
                         if (loader.isShowing())
                             loader.dismiss();
                     }
                     if (comingSoonView != null) {
                         comingSoonView.setVisibility(View.VISIBLE);
                     }
                     try {
                         if (t.getMessage() != null && !t.getMessage().isEmpty()) {

                             if (t.getMessage().contains("No address associated with hostname")) {
                                 NetworkError(context, context.getResources().getString(R.string.err_msg_network_title),
                                         context.getResources().getString(R.string.err_msg_network));
                             } else {
                                 Error(context, t.getMessage());

                             }

                         } else {
                             Error(context, context.getResources().getString(R.string.some_thing_error));

                         }
                     } catch (IllegalStateException ise) {
                         Error(context, ise.getMessage());

                     }

                 }
             });

         } catch (Exception e) {
             if (comingSoonView != null) {
                 comingSoonView.setVisibility(View.VISIBLE);
             }
             e.printStackTrace();
         }
     }
 */
    public void FundOrderReport(final Activity context, String oid,
                                String status, String fromDate, String toDate, String transactionID, String accountNo, String isExport, String tMode, String isSelf, String uMobile, final CustomLoader loader) {
        try {
            String LoginResponse = getLoginPref(context);
            LoginResponse LoginDataResponse = new Gson().fromJson(LoginResponse, LoginResponse.class);
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<RechargeReportResponse> call = git.FundOrderReport(new LedgerReportRequest(
                    "100", status, oid, fromDate, toDate, transactionID, accountNo, isExport, LoginDataResponse.getData().getUserID(),
                    LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession(),
                    ApplicationConstant.INSTANCE.APP_ID,
                    getIMEI(context),
                    "", BuildConfig.VERSION_NAME, getSerialNo(context), LoginDataResponse.getData().getLoginTypeID(),
                    tMode, isSelf, uMobile));
            call.enqueue(new Callback<RechargeReportResponse>() {
                @Override
                public void onResponse(Call<RechargeReportResponse> call, final retrofit2.Response<RechargeReportResponse> response) {
                    try {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        if (response.body() != null && response.body().getStatuscode() != null) {
                            if (response.body().getStatuscode().equalsIgnoreCase("1")) {
                                Balancecheck(context, loader, null);
                                ActivityActivityMessage activityActivityMessage =
                                        new ActivityActivityMessage("fund_receive", "" + new Gson().toJson(response.body()).toString());
                                GlobalBus.getBus().post(activityActivityMessage);
                            } else if (response.body().getStatuscode().equalsIgnoreCase("-1")) {
                                if (response.body().getIsVersionValid() != null && response.body().getIsVersionValid().equalsIgnoreCase("false")) {
                                    versionDialog(context);
                                } else {
                                    Error(context, response.body().getMsg() + "");
                                }
                            }
                        }
                    } catch (Exception e) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                    }

                }

                @Override
                public void onFailure(Call<RechargeReportResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            loader.dismiss();
                    }
                    Error(context, context.getResources().getString(R.string.err_something_went_wrong));

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void Logout(final Activity context, String sessType) {
        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            String LoginResponse = getLoginPref(context);
            LoginResponse LoginDataResponse = new Gson().fromJson(LoginResponse, LoginResponse.class);
            Call<RechargeCResponse> call = git.Logout(new LogoutRequest(sessType,
                    LoginDataResponse.getData().getUserID(), LoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID,
                    getIMEI(context),
                    "", BuildConfig.VERSION_NAME, getSerialNo(context), LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));

            call.enqueue(new Callback<RechargeCResponse>() {
                @Override
                public void onResponse(Call<RechargeCResponse> call, final retrofit2.Response<RechargeCResponse> response) {
                    try {

                        if (response.body() != null) {

                           /* if (response.body().getStatuscode().equalsIgnoreCase("1")) {
                                logout(context);
                                context.finish();
                            } else if (response.body().getStatuscode().equalsIgnoreCase("2")) {
                                Successful(context, response.body().getMsg());
                            } else if (response.body().getStatuscode().equalsIgnoreCase("3")) {
                                Failed(context, response.body().getMsg());
                            } else if (response.body().getStatuscode().equalsIgnoreCase("-1")) {

                                Error(context, response.body().getMsg() + "");

                            }*/
                            logout(context);
                            context.finish();

                        }
                    } catch (Exception e) {

                    }

                }

                @Override
                public void onFailure(Call<RechargeCResponse> call, Throwable t) {

                    Error(context, context.getResources().getString(R.string.err_something_went_wrong));

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void NewsApi(final Activity context, boolean isLoginNews, final TextView mNewsView, final View newsCard) {
        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            String LoginResponse = getLoginPref(context);
            LoginResponse LoginDataResponse = new Gson().fromJson(LoginResponse, LoginResponse.class);
            Call<AppUserListResponse> call = git.GetAppNews(new NewsRequest(isLoginNews,
                    LoginDataResponse.getData().getUserID(), LoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID,
                    getIMEI(context),
                    "", BuildConfig.VERSION_NAME, getSerialNo(context), LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));

            call.enqueue(new Callback<AppUserListResponse>() {
                @Override
                public void onResponse(Call<AppUserListResponse> call, final retrofit2.Response<AppUserListResponse> response) {
                    try {

                        if (response.body() != null && response.body().getStatuscode() == 1) {
                            setNewsData(context, new Gson().toJson(response.body()));
                            if (response.body().getNewsContent() != null && response.body().getNewsContent().getNewsDetail() != null && !response.body().getNewsContent().getNewsDetail().isEmpty()) {
                                //String news = "<body style='margin: 0; padding: 0'>" + response.body().getNewsContent().getNewsDetail() + "</body>";
                                mNewsView.setText(Html.fromHtml(response.body().getNewsContent().getNewsDetail() + ""));
                                newsCard.setVisibility(View.VISIBLE);
                            } else {
                                newsCard.setVisibility(View.GONE);
                            }

                        } else {
                            newsCard.setVisibility(View.GONE);
                        }
                    } catch (Exception e) {

                    }

                }

                @Override
                public void onFailure(Call<AppUserListResponse> call, Throwable t) {


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void FundDCReport(final Activity context, boolean isSelf, int walletTypeID, int serviceID, String otherUserMob, String fromDate, String toDate, String accountNo, final CustomLoader loader) {
        try {
            String LoginResponse = getLoginPref(context);
            LoginResponse LoginDataResponse = new Gson().fromJson(LoginResponse, LoginResponse.class);
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            FundDCReportRequest mFundDCReportRequest = new FundDCReportRequest(isSelf, walletTypeID,
                    serviceID, otherUserMob, fromDate, toDate, accountNo, LoginDataResponse.getData().getUserID(),
                    LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession(),
                    ApplicationConstant.INSTANCE.APP_ID,
                    getIMEI(context),
                    "", BuildConfig.VERSION_NAME, getSerialNo(context), LoginDataResponse.getData().getLoginTypeID());
            /* String str =new Gson().toJson(mFundDCReportRequest);*/
            Call<RechargeReportResponse> call = git.FundDCReport(mFundDCReportRequest);
            call.enqueue(new Callback<RechargeReportResponse>() {
                @Override
                public void onResponse(Call<RechargeReportResponse> call, final retrofit2.Response<RechargeReportResponse> response) {
                    try {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        if (response.body() != null && response.body().getStatuscode() != null) {
                            if (response.body().getStatuscode().equalsIgnoreCase("1")) {
                                Balancecheck(context, loader, null);
                                ActivityActivityMessage activityActivityMessage =
                                        new ActivityActivityMessage("fund_receive", "" + new Gson().toJson(response.body()).toString());
                                GlobalBus.getBus().post(activityActivityMessage);
                            } else if (response.body().getStatuscode().equalsIgnoreCase("-1")) {
                                if (response.body().getIsVersionValid() != null && response.body().getIsVersionValid().equalsIgnoreCase("false")) {
                                    versionDialog(context);
                                } else {
                                    Error(context, response.body().getMsg() + "");
                                }
                            }
                        }
                    } catch (Exception e) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                    }

                }

                @Override
                public void onFailure(Call<RechargeReportResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            loader.dismiss();
                    }
                    Error(context, context.getResources().getString(R.string.err_something_went_wrong));

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void RefundLog(final Activity context, int topRows, int criteria, String criteriaText, int status, String fromDate, String toDate, int dateType, String transactionID, final CustomLoader loader) {
        try {
            String LoginResponse = getLoginPref(context);
            LoginResponse LoginDataResponse = new Gson().fromJson(LoginResponse, LoginResponse.class);
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            RefundLogRequest mRefundLogRequest = new RefundLogRequest(topRows, criteria,
                    criteriaText, status, fromDate, toDate, dateType, transactionID, LoginDataResponse.getData().getUserID(),
                    LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession(),
                    ApplicationConstant.INSTANCE.APP_ID,
                    getIMEI(context),
                    "", BuildConfig.VERSION_NAME, getSerialNo(context), LoginDataResponse.getData().getLoginTypeID());
            /* String str =new Gson().toJson(mFundDCReportRequest);*/
            Call<AppUserListResponse> call = git.RefundLog(mRefundLogRequest);
            call.enqueue(new Callback<AppUserListResponse>() {
                @Override
                public void onResponse(Call<AppUserListResponse> call, final retrofit2.Response<AppUserListResponse> response) {
                    try {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        if (response.body() != null) {
                            if (response.body().getStatuscode() == 1 && response.body().getRefundLog() != null && response.body().getRefundLog().size() > 0) {
                                ActivityActivityMessage activityActivityMessage =
                                        new ActivityActivityMessage("Refund_Log", "" + new Gson().toJson(response.body()).toString());
                                GlobalBus.getBus().post(activityActivityMessage);
                            } else if (response.body().getStatuscode() == -1) {
                                if (response.body().getVersionValid() == false) {
                                    versionDialog(context);
                                } else {
                                    Error(context, response.body().getMsg() + "");
                                }
                            } else {
                                Error(context, "Report not found.");
                            }
                        }
                    } catch (Exception e) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        Error(context, e.getMessage());
                    }

                }

                @Override
                public void onFailure(Call<AppUserListResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            loader.dismiss();
                    }
                    Error(context, context.getResources().getString(R.string.err_something_went_wrong));

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            Error(context, e.getMessage());
        }
    }

    public void UserAchieveTarget(final Activity context, final boolean isTotal, final CustomLoader loader, final ApiCallBack mApiCallBack) {
        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            String LoginResponse = getLoginPref(context);
            com.solution.brothergroup.Authentication.dto.LoginResponse LoginDataResponse = new Gson().fromJson(LoginResponse, LoginResponse.class);
            Call<AppUserListResponse> call = git.GetTargetAchieved(new AchieveTargetRequest(isTotal,
                    LoginDataResponse.getData().getUserID(), LoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID,
                    getIMEI(context),
                    "", BuildConfig.VERSION_NAME, getSerialNo(context), LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));


            call.enqueue(new Callback<AppUserListResponse>() {
                @Override
                public void onResponse(Call<AppUserListResponse> call, final retrofit2.Response<AppUserListResponse> response) {
                    try {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        if (response.body() != null) {
                            if (response.body().getStatuscode() == 1) {
                                if (isTotal) {
                                    setTotalTargetData(context, new Gson().toJson(response.body()));
                                }
                                if (mApiCallBack != null) {
                                    mApiCallBack.onSucess(response.body());
                                }
                            } else if (response.body().getStatuscode() == -1) {
                                if (!isTotal) {
                                    if (response.body().getVersionValid() == false) {
                                        versionDialog(context);
                                    } else {
                                        Error(context, response.body().getMsg() + "");
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                    }

                }

                @Override
                public void onFailure(Call<AppUserListResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            loader.dismiss();
                    }
                    if (!isTotal) {
                        if (t.getMessage().contains("No address associated with hostname")) {
                            NetworkError(context, context.getResources().getString(R.string.err_msg_network_title),
                                    context.getResources().getString(R.string.err_msg_network));
                        } else {
                            Error(context, t.getMessage());

                        }
                    }

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void MoveToBankReport(final Activity context, int status, String topRow, int oid,
                                 String fromDate, String toDate, String transactionID, String childMobileNo,
                                 final CustomLoader loader, LoginResponse LoginDataResponse, String deviceId,
                                 String deviceSerialNum, final ApiCallBack mApiCallBack) {
        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<RechargeReportResponse> call = git.MoveToBankReport(new MoveToBankReportRequest(topRow, status, oid, fromDate, toDate, transactionID,
                    childMobileNo,
                    LoginDataResponse.getData().getUserID() + "", LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession(),
                    ApplicationConstant.INSTANCE.APP_ID, deviceId, "", BuildConfig.VERSION_NAME, deviceSerialNum,
                    LoginDataResponse.getData().getLoginTypeID()));


            call.enqueue(new Callback<RechargeReportResponse>() {

                @Override
                public void onResponse(Call<RechargeReportResponse> call, retrofit2.Response<RechargeReportResponse> response) {

                    try {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                if (response.body().getStatuscode().equalsIgnoreCase("1")) {
                                    if (mApiCallBack != null) {
                                        mApiCallBack.onSucess(response.body());
                                    }
                                } else {

                                    if (response.body().getIsVersionValid() != null && response.body().getIsVersionValid().equalsIgnoreCase("false")) {

                                        versionDialog(context);
                                    } else {
                                        Error(context, response.body().getMsg() + "");
                                    }
                                }

                            }
                        } else {

                            apiErrorHandle(context, response.code(), response.message());
                        }
                    } catch (Exception e) {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }

                        Error(context, e.getMessage() + "");
                    }


                }

                @Override
                public void onFailure(Call<RechargeReportResponse> call, Throwable t) {

                    if (loader != null) {
                        if (loader.isShowing()) {
                            loader.dismiss();
                        }
                    }
                    try {
                        if (t instanceof UnknownHostException || t instanceof IOException) {
                            NetworkError(context);

                        } else if (t instanceof SocketTimeoutException || t instanceof TimeoutException) {
                            ErrorWithTitle(context, "TIME OUT ERROR", t.getMessage() + "");

                        } else {

                            if (t.getMessage() != null && !t.getMessage().isEmpty()) {
                                ErrorWithTitle(context, "FATAL ERROR", t.getMessage() + "");
                            } else {
                                Error(context, context.getResources().getString(R.string.some_thing_error));
                            }
                        }

                    } catch (IllegalStateException ise) {

                        Error(context, ise.getMessage());

                    }
                }
            });

        } catch (Exception e) {

            if (loader != null) {
                if (loader.isShowing()) {
                    loader.dismiss();
                }
            }
            e.printStackTrace();
            Error(context, e.getMessage());
        }

    }


    public void UserDayBook(final Activity context, String mobileNo, String fromDate, String toDate, final CustomLoader loader, final ApiCallBack mApiCallBack) {
        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            String LoginResponse = getLoginPref(context);
            com.solution.brothergroup.Authentication.dto.LoginResponse LoginDataResponse = new Gson().fromJson(LoginResponse, LoginResponse.class);
            Call<AppUserListResponse> call = git.UserDaybook(new UserDayBookRequest(fromDate, toDate, mobileNo,
                    LoginDataResponse.getData().getUserID(), LoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID,
                    getIMEI(context),
                    "", BuildConfig.VERSION_NAME, getSerialNo(context), LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));


            call.enqueue(new Callback<AppUserListResponse>() {
                @Override
                public void onResponse(Call<AppUserListResponse> call, final retrofit2.Response<AppUserListResponse> response) {
                    try {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        if (response.body() != null) {
                            if (response.body().getStatuscode() == 1) {
                                setDayBookData(context, new Gson().toJson(response.body()));
                                if (mApiCallBack != null) {
                                    mApiCallBack.onSucess(response.body());
                                }
                            } else if (response.body().getStatuscode() == -1) {
                                if (response.body().getVersionValid() == false) {
                                    versionDialog(context);
                                } else {
                                    Error(context, response.body().getMsg() + "");
                                }
                            }
                        }
                    } catch (Exception e) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                    }

                }

                @Override
                public void onFailure(Call<AppUserListResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            loader.dismiss();
                    }

                    if (context instanceof UserDayBookActivity) {
                        if (t != null && t.getMessage().contains("No address associated with hostname")) {
                            NetworkError(context, context.getResources().getString(R.string.err_msg_network_title),
                                    context.getResources().getString(R.string.err_msg_network));
                        } else if (t != null && t.getMessage() != null) {
                            Error(context, t.getMessage());

                        } else {
                            Error(context, context.getResources().getString(R.string.err_something_went_wrong));
                        }
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void UserDayBookDmt(final Activity context, String mobileNo, String fromDate, String toDate, final CustomLoader loader, final ApiCallBack mApiCallBack) {
        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            String LoginResponse = getLoginPref(context);
            LoginResponse LoginDataResponse = new Gson().fromJson(LoginResponse, LoginResponse.class);
            Call<AppUserListResponse> call = git.UserDaybookDmt(new UserDayBookRequest(fromDate, toDate, mobileNo,
                    LoginDataResponse.getData().getUserID(), LoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID,
                    getIMEI(context),
                    "", BuildConfig.VERSION_NAME, getSerialNo(context), LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));


            call.enqueue(new Callback<AppUserListResponse>() {
                @Override
                public void onResponse(Call<AppUserListResponse> call, final retrofit2.Response<AppUserListResponse> response) {
                    try {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        if (response.body() != null) {
                            if (response.body().getStatuscode() == 1) {
                                if (mApiCallBack != null) {
                                    mApiCallBack.onSucess(response.body());
                                }
                            } else if (response.body().getStatuscode() == -1) {
                                if (response.body().getVersionValid() == false) {
                                    versionDialog(context);
                                } else {
                                    Error(context, response.body().getMsg() + "");
                                }
                            }
                        }
                    } catch (Exception e) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                    }

                }

                @Override
                public void onFailure(Call<AppUserListResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            loader.dismiss();
                    }
                    Error(context, context.getResources().getString(R.string.err_something_went_wrong));

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setOPTypeResponse(Activity context, String toString) {
        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(ApplicationConstant.INSTANCE.OpTypePref, toString);
        editor.apply();
    }

    public void ValidateOTP(final Activity context, String Otp, final String Otpsession, final Integer otpType, final String password, final CustomLoader loader) {
        try {

            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<LoginResponse> call = git.ValidateOTP(new OtpRequest(Otp, Otpsession, otpType,
                    ApplicationConstant.INSTANCE.Domain,
                    ApplicationConstant.INSTANCE.APP_ID,
                    getIMEI(context),
                    "", BuildConfig.VERSION_NAME, ""));
            call.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, final retrofit2.Response<LoginResponse> response) {
                    if (loader != null) {
                        if (loader.isShowing())
                            loader.dismiss();
                    }
                    if (response.body() != null && response.body().getStatuscode() != null) {
                        if (response.body().getStatuscode().equalsIgnoreCase("1")) {
                            setDoubleFactorPref(context, response.body().getData().getIsDoubleFactor());
                            setIsLookUpFromAPI(context, response.body().isLookUpFromAPI());
                            setIsDTHInfoAutoCall(context, response.body().isDTHInfoCall());
                            setIsHeavyRefresh(context, response.body().isHeavyRefresh());
                            setIsAutoBilling(context, response.body().isAutoBilling());
                            setIsRealAPIPerTransaction(context, response.body().isRealAPIPerTransaction());
                            setIsRoffer(context, response.body().isRoffer());
                            setIsDTHInfo(context, response.body().isDTHInfo());
                            setIsTargetShow(context, response.body().isTargetShow());
                            setLoginPref(context, password, response.body().getData().getMobileNo(), new Gson().toJson(response.body()));
                            updateFcm(context);
                            setIsLogin(context, true);
                            if (loader != null)
                                ((LoginActivity) context).startDashboard();
                            else
                                dashboard(context);
                        } else if (response.body().getStatuscode().equalsIgnoreCase("-1")) {
                            if (response.body().getIsVersionValid() != null && response.body().getIsVersionValid().equalsIgnoreCase("false")) {
                                versionDialog(context);
                            } else {
                                Error(context, response.body().getMsg());
                            }

                        }
                    }

                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            loader.dismiss();
                    }

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ValidateTOTP(final Activity context, String Otp, final String Otpsession, final Integer otpType, final String password, final CustomLoader loader) {
        try {

            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<LoginResponse> call = git.ValidateGAuthPIN(new OtpRequest(Otp, Otpsession, otpType,
                    ApplicationConstant.INSTANCE.Domain,
                    ApplicationConstant.INSTANCE.APP_ID,
                    getIMEI(context),
                    "", BuildConfig.VERSION_NAME, ""));
            call.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, final retrofit2.Response<LoginResponse> response) {
                    if (loader != null) {
                        if (loader.isShowing())
                            loader.dismiss();
                    }
                    if (response.body() != null && response.body().getStatuscode() != null) {
                        if (response.body().getStatuscode().equalsIgnoreCase("1")) {
                            setDoubleFactorPref(context, response.body().getData().getIsDoubleFactor());
                            setIsLookUpFromAPI(context, response.body().isLookUpFromAPI());
                            setIsDTHInfoAutoCall(context, response.body().isDTHInfoCall());
                            setIsHeavyRefresh(context, response.body().isHeavyRefresh());
                            setIsAutoBilling(context, response.body().isAutoBilling());
                            setIsRealAPIPerTransaction(context, response.body().isRealAPIPerTransaction());
                            setIsRoffer(context, response.body().isRoffer());
                            setIsDTHInfo(context, response.body().isDTHInfo());
                            setIsTargetShow(context, response.body().isTargetShow());
                            setLoginPref(context, password, response.body().getData().getMobileNo(), new Gson().toJson(response.body()));
                            updateFcm(context);
                            setIsLogin(context, true);
                            if (loader != null)
                                ((LoginActivity) context).startDashboard();
                            else
                                dashboard(context);
                        } else if (response.body().getStatuscode().equalsIgnoreCase("-1")) {
                            if (response.body().getIsVersionValid() != null && response.body().getIsVersionValid().equalsIgnoreCase("false")) {
                                versionDialog(context);
                            } else {
                                Error(context, response.body().getMsg());
                            }

                        }
                    }

                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            loader.dismiss();
                    }

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void LedgerReport(final Activity context, String oid, String topRow,
                             String status, String fromDate, String toDate, int walletTypeId, String transactionID, String accountNo, String isExport, final CustomLoader loader) {
        try {
            String LoginResponse = getLoginPref(context);
            LoginResponse LoginDataResponse = new Gson().fromJson(LoginResponse, LoginResponse.class);
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<RechargeReportResponse> call = git.LedgerReport(new LedgerReportRequest(
                    topRow, oid, status, fromDate, toDate, transactionID, accountNo, isExport, LoginDataResponse.getData().getUserID(),
                    LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession(),
                    ApplicationConstant.INSTANCE.APP_ID,
                    getIMEI(context),
                    "", BuildConfig.VERSION_NAME, getSerialNo(context), LoginDataResponse.getData().getLoginTypeID(), walletTypeId));
            call.enqueue(new Callback<RechargeReportResponse>() {
                @Override
                public void onResponse(Call<RechargeReportResponse> call, final retrofit2.Response<RechargeReportResponse> response) {

                    try {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        if (response.body() != null && response.body().getStatuscode() != null) {
                            if (response.body().getStatuscode().equalsIgnoreCase("1")) {
                                ActivityActivityMessage activityActivityMessage =
                                        new ActivityActivityMessage("ledger_respo", "" + new Gson().toJson(response.body()));
                                GlobalBus.getBus().post(activityActivityMessage);
                            } else if (response.body().getStatuscode().equalsIgnoreCase("-1")) {
                                if (response.body().getIsVersionValid() != null && response.body().getIsVersionValid().equalsIgnoreCase("false")) {
                                    versionDialog(context);
                                } else {
                                    Error(context, response.body().getMsg() + "");
                                }
                            }
                        }
                    } catch (Exception e) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                    }

                }

                @Override
                public void onFailure(Call<RechargeReportResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            loader.dismiss();
                    }
                    try {
                        if (t.getMessage() != null && !t.getMessage().isEmpty()) {

                            if (t.getMessage().contains("No address associated with hostname")) {
                                NetworkError(context, context.getResources().getString(R.string.err_msg_network_title),
                                        context.getResources().getString(R.string.err_msg_network));
                            } else {
                                Error(context, t.getMessage());

                            }

                        } else {
                            Error(context, context.getResources().getString(R.string.some_thing_error));

                        }
                    } catch (IllegalStateException ise) {
                        Error(context, ise.getMessage());

                    }

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void MyCommissionDetail(final Activity context, int oid, final CustomLoader loader, final ApiCallBack mApiCallBack) {
        try {
            String LoginResponse = getLoginPref(context);
            LoginResponse LoginDataResponse = new Gson().fromJson(LoginResponse, LoginResponse.class);
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<SlabRangeDetailResponse> call = git.SlabRangDetail(new SlabRangeDetailRequest(oid,
                    LoginDataResponse.getData().getUserID() + "", LoginDataResponse.getData().getLoginTypeID() + "",
                    ApplicationConstant.INSTANCE.APP_ID,
                    getIMEI(context),
                    "", BuildConfig.VERSION_NAME, getSerialNo(context), LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));
            call.enqueue(new Callback<SlabRangeDetailResponse>() {

                @Override
                public void onResponse(Call<SlabRangeDetailResponse> call, retrofit2.Response<SlabRangeDetailResponse> response) {

                    if (loader.isShowing())
                        loader.dismiss();
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            if (response.body().getStatuscode() == 1) {
                                if (response.body().getSlabRangeDetail() != null && response.body().getSlabRangeDetail().size() > 0) {
                                    if (mApiCallBack != null) {
                                        mApiCallBack.onSucess(response.body());
                                    }
                                } else {
                                    Error(context, "Slab Range Data not found.");
                                }

                            } else if (response.body().getStatuscode() == -1) {
                                if (response.body().getVersionValid() == false) {
                                    versionDialog(context);
                                } else {
                                    Error(context, response.body().getMsg() + "");
                                }
                            } else {
                                Error(context, context.getResources().getString(R.string.some_thing_error) + "");
                            }

                        } else {
                            Error(context, context.getResources().getString(R.string.some_thing_error) + "");
                        }
                    } else {
                        apiErrorHandle(context, response.code(), response.message());
                    }
                }

                @Override
                public void onFailure(Call<SlabRangeDetailResponse> call, Throwable t) {

                    if (loader.isShowing())
                        loader.dismiss();
                    apiFailureError(context, t);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            if (loader.isShowing())
                loader.dismiss();
            Error(context, e.getMessage() + "");
        }

    }


    /* public void LedgerReport(final Activity context, String oid,
                              String status, String fromDate, String toDate, String transactionID, String accountNo, String isExport, int walletTypeID, final CustomLoader loader) {
         try {
             String LoginResponse = getLoginPref(context);
             LoginResponse LoginDataResponse = new Gson().fromJson(LoginResponse, LoginResponse.class);
             EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
             Call<RechargeReportResponse> call = git.LedgerReport(new LedgerReportRequest(
                     "100", oid, status, fromDate, toDate, transactionID, accountNo, isExport, LoginDataResponse.getData().getUserID(),
                     LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession(),
                     ApplicationConstant.INSTANCE.APP_ID,
                     getIMEI(context),
                     "", BuildConfig.VERSION_NAME, getSerialNo(context), LoginDataResponse.getData().getLoginTypeID(), walletTypeID));
             call.enqueue(new Callback<RechargeReportResponse>() {
                 @Override
                 public void onResponse(Call<RechargeReportResponse> call, final retrofit2.Response<RechargeReportResponse> response) {

                     try {
                         if (loader != null) {
                             if (loader.isShowing())
                                 loader.dismiss();
                         }
                         if (response.body() != null && response.body().getStatuscode() != null) {
                             if (response.body().getStatuscode().equalsIgnoreCase("1")) {
                                 Balancecheck(context, loader, null);
                                 ActivityActivityMessage activityActivityMessage =
                                         new ActivityActivityMessage("ledger_respo", "" + new Gson().toJson(response.body()));
                                 GlobalBus.getBus().post(activityActivityMessage);
                             } else if (response.body().getStatuscode().equalsIgnoreCase("-1")) {
                                 if (response.body().getIsVersionValid() != null && response.body().getIsVersionValid().equalsIgnoreCase("false")) {
                                     versionDialog(context);
                                 } else {
                                     Error(context, response.body().getMsg() + "");
                                 }
                             }
                         }
                     } catch (Exception e) {
                         if (loader != null) {
                             if (loader.isShowing())
                                 loader.dismiss();
                         }
                     }

                 }

                 @Override
                 public void onFailure(Call<RechargeReportResponse> call, Throwable t) {
                     if (loader != null) {
                         if (loader.isShowing())
                             loader.dismiss();
                     }
                     try {
                         if (t.getMessage() != null && !t.getMessage().isEmpty()) {

                             if (t.getMessage().contains("No address associated with hostname")) {
                                 NetworkError(context, context.getResources().getString(R.string.err_msg_network_title),
                                         context.getResources().getString(R.string.err_msg_network));
                             } else {
                                 Error(context, t.getMessage());

                             }

                         } else {
                             Error(context, context.getResources().getString(R.string.some_thing_error));

                         }
                     } catch (IllegalStateException ise) {
                         Error(context, ise.getMessage());

                     }

                 }
             });

         } catch (Exception e) {
             e.printStackTrace();
         }
     }
 */
    /*public boolean CheckActiveOperator(Activity context, String title) {
        boolean bool = false;
        try {
            SharedPreferences myPreferences = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, context.MODE_PRIVATE);
            String mResponse = myPreferences.getString(ApplicationConstant.INSTANCE.activeServicePref, "");
            OpTypeResponse OpTypeResponse = new Gson().fromJson(mResponse, OpTypeResponse.class);
            for (AssignedOpType at : OpTypeResponse.getData().getAssignedOpTypes()) {
                if (at.getName().equals(title) && !at.getIsActive()) {
                    bool = false;
                } else {
                    bool = true;
                }
            }
        } catch (Exception e) {

        }
        return bool;
    }*/

    ////
    public String getOtpMessage(String otp) {
        edMobileOtp.setText(otp);
        return otp;
    }

    public String getDeviceId(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String deviceId = "";
        try {
            if (Build.VERSION.SDK_INT >= 29) {
                deviceId = getUniqueID(context).replaceAll(" ", "").replaceAll("-", "").substring(0, 15);
            } else if (Build.VERSION.SDK_INT >= 26 && Build.VERSION.SDK_INT <= 28) {

                if (telephonyManager.getPhoneType() == TelephonyManager.PHONE_TYPE_CDMA) {
                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {

                    }
                    deviceId = telephonyManager.getImei();

                } else if (telephonyManager.getPhoneType() == TelephonyManager.PHONE_TYPE_GSM) {
                    deviceId = telephonyManager.getImei();


                } else if (telephonyManager.getPhoneType() == TelephonyManager.PHONE_TYPE_SIP) {
                    deviceId = telephonyManager.getImei();

                } else if (telephonyManager.getPhoneType() == TelephonyManager.PHONE_TYPE_NONE) {
                    deviceId = telephonyManager.getImei();
                }
            } else {
                deviceId = telephonyManager.getDeviceId();
            }

        } catch (SecurityException e) {
        } catch (Exception ex) {

        }
        return deviceId;
    }

    public String getIMEI(Context context) {
        return getDeviceId(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    String getUniqueID(Context mContext) {
        UUID wideVineUuid = new UUID(-0x121074568629b532L, -0x5c37d8232ae2de13L);
        try {
            MediaDrm wvDrm = new MediaDrm(wideVineUuid);
            byte[] wideVineId = wvDrm.getPropertyByteArray(MediaDrm.PROPERTY_DEVICE_UNIQUE_ID);
            String deviceID = java.util.Base64.getEncoder().encodeToString(wideVineId);
            return deviceID;
        } catch (Exception e) {
// Inspect exception
            return androidId(mContext);
        }
// Close resources with close() or release() depending on platform API
// Use ARM on Android P platform or higher, where MediaDrm has the close() method
    }

    public String androidId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
    }

    public void NetworkError(final Activity context, String title, final String message) {
        new SweetAlertDialog(context, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                .setTitleText(title)
                .setContentText(message)
                .setCustomImage(R.drawable.ic_connection_lost_24dp)
                .show();
    }

    public void NetworkError(final Activity context) {
        new SweetAlertDialog(context, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                .setTitleText("Network Error!")
                .setContentText("Slow or No Internet Connection.")
                .setCustomImage(R.drawable.ic_connection_lost_24dp)
                .show();
    }

    public void Processing(final Activity context, final String message) {
        CustomAlertDialog customAlertDialog = new CustomAlertDialog(context, true);
        customAlertDialog.Warning(message);
    }

    public void ProcessingWithTitle(final Activity context, final String title, final String message) {
        CustomAlertDialog customAlertDialog = new CustomAlertDialog(context, true);
        customAlertDialog.Warning(title, message);
    }

    public void Failed(final Activity context, final String message) {
        CustomAlertDialog customAlertDialog = new CustomAlertDialog(context, true);
        customAlertDialog.Failed(message);
    }

    public void Successful(final Activity context, final String message) {
        CustomAlertDialog customAlertDialog = new CustomAlertDialog(context, true);
        customAlertDialog.Successful(message);
    }

    public void SuccessfulWithTitle(final Activity context, final String title, final String message) {
        CustomAlertDialog customAlertDialog = new CustomAlertDialog(context, true);
        customAlertDialog.SuccessfulWithTitle(title, message);
    }

    public void SuccessfulWithFinsh(final Activity context, final String message) {
        CustomAlertDialog customAlertDialog = new CustomAlertDialog(context, true);
        customAlertDialog.SuccessfulWithFinsh(message);
    }

    public void Successfulok(final String message, Activity activity) {
        CustomAlertDialog customAlertDialog = new CustomAlertDialog(activity, true);
        customAlertDialog.Successfulok(message, activity);
    }

    public void SuccessfulWithFinish(final String message, Activity activity, boolean isCancelable) {
        CustomAlertDialog customAlertDialog = new CustomAlertDialog(activity, true);
        customAlertDialog.SuccessfulWithFinsh(message, isCancelable);
    }

    public void Errorok(final Activity context, final String message, Activity activity) {
        CustomAlertDialog customAlertDialog = new CustomAlertDialog(context, true);
        customAlertDialog.Errorok(message, activity);
    }

    public void Error(final Activity context, final String message) {
        CustomAlertDialog customAlertDialog = new CustomAlertDialog(context, true);
        customAlertDialog.Error(message);
    }

    public void ErrorWithTitle(final Activity context, final String title, final String message) {
        CustomAlertDialog customAlertDialog = new CustomAlertDialog(context, true);
        customAlertDialog.ErrorWithTitle(title, message);
    }

    public void Alert(final Activity context, final String message) {
        new SweetAlertDialog(context, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                .setContentText(message)
                .setCustomImage(R.drawable.ic_done_black_24dp)
                .show();
    }

    public boolean isNetworkAvialable(Activity context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public boolean isValidMobile(String mobile) {

        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        String mobilePattern = "^(?:0091|\\\\+91|0)[7-9][0-9]{9}$";
        String mobileSecPattern = "[7-9][0-9]{9}$";

        if (mobile.matches(mobilePattern) || mobile.matches(mobileSecPattern)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isValidEmail(String email) {

        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

    public boolean Matcher(Activity context, String pinpasscode) {
        SharedPreferences myPreferences = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNameLoginPref, context.MODE_PRIVATE);
        String pinPass = myPreferences.getString(ApplicationConstant.INSTANCE.PinPasscode, null);

        if (pinpasscode.equalsIgnoreCase(pinPass)) {
            return true;
        } else {
            return false;
        }
    }

    public void pinpasscode(Activity context, String pinpasscode, boolean flagPinPasscodeFlag) {
        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNameLoginPref, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        if (pinpasscode.equalsIgnoreCase("")) {
            editor.putBoolean(ApplicationConstant.INSTANCE.PinPasscodeFlag, flagPinPasscodeFlag);
        } else {
            editor.putString(ApplicationConstant.INSTANCE.PinPasscode, pinpasscode);
        }

        editor.apply();
    }

    public String fetchOperator(Activity context, String param) {
        int opid = 0;
        String circle = "";
        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, context.MODE_PRIVATE);
        String response = prefs.getString(ApplicationConstant.INSTANCE.numberListPref, null);
        NumberListResponse numberListResponse = new Gson().fromJson(response, NumberListResponse.class);
        for (NumberList numberList : numberListResponse.getData().getNumSeries()) {
            if (numberList.getSeries().equalsIgnoreCase(param)) {
                opid = numberList.getOid();
                circle = numberList.getCircleCode();
                break;
            }
        }
        return opid + "," + circle;
    }

    public String fetchShortCode(Activity context, String operatorName) {
        String paramIReffOp = "";

        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, context.MODE_PRIVATE);
        String response = prefs.getString(ApplicationConstant.INSTANCE.numberListPref, null);

        NumberListResponse numberListResponse = new Gson().fromJson(response, NumberListResponse.class);
//        for (NumberList numberList : numberListResponse.getNumberList()) {
//            // if (numberList.getOperator().equalsIgnoreCase(operatorName)&&numberList.getOperatorId()==opId) {
//            if (numberList.getOperator().equalsIgnoreCase(operatorName)) {
//
//                try {
//                    String str = numberList.getOperator();
//                    paramIReffOp = numberList.getIReffOp();
//                    // Log.e("answer", paramIReffOp);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//                break;
//            }
//        }
        return paramIReffOp;
    }

    public void setNotification(Activity context, String notification, final CustomLoader loader) {
        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(ApplicationConstant.INSTANCE.prefNotificationPref, notification);
        editor.apply();
        ActivityActivityMessage activityActivityMessage =
                new ActivityActivityMessage("", "Notification");
        GlobalBus.getBus().post(activityActivityMessage);
    }

    public void setDashboardStatus(Activity context, boolean bool) {
        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("Dashboard", bool);
        editor.apply();
    }

    public boolean isOnDashboard(Activity context) {
        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        return prefs.getBoolean("Dashboard", false);
    }

    public void setPsaId(Activity context, String data) {
        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(ApplicationConstant.INSTANCE.psaIdPref, data);
        editor.apply();

    }

    public String getPsaId(Activity context) {
        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, context.MODE_PRIVATE);
        return prefs.getString(ApplicationConstant.INSTANCE.psaIdPref, "24");

    }

    public void setIsLookUpFromAPI(Activity context, boolean data) {
        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(ApplicationConstant.INSTANCE.isLookUpFromAPIPref, data);
        editor.apply();

    }

    public boolean getIsLookUpFromAPI(Activity context) {
        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, context.MODE_PRIVATE);
        return prefs.getBoolean(ApplicationConstant.INSTANCE.isLookUpFromAPIPref, false);
    }

    public void setIsDTHInfoAutoCall(Activity context, boolean data) {
        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(ApplicationConstant.INSTANCE.isDTHInfoAutoCallPref, data);
        editor.apply();

    }

    public boolean getIsDTHInfoAutoCall(Activity context) {
        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, context.MODE_PRIVATE);
        return prefs.getBoolean(ApplicationConstant.INSTANCE.isDTHInfoAutoCallPref, false);
    }

    public void setIsFlatCommission(Activity context, boolean data) {
        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(ApplicationConstant.INSTANCE.isFlatCommissionPref, data);
        editor.apply();

    }

    public boolean getIsFlatCommission(Activity context) {
        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, context.MODE_PRIVATE);
        return prefs.getBoolean(ApplicationConstant.INSTANCE.isFlatCommissionPref, false);
    }

    public void setIsHeavyRefresh(Activity context, boolean data) {
        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(ApplicationConstant.INSTANCE.isHeavyRefreshPref, data);
        editor.apply();

    }

    public boolean getIsHeavyRefresh(Activity context) {
        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, context.MODE_PRIVATE);
        return prefs.getBoolean(ApplicationConstant.INSTANCE.isHeavyRefreshPref, false);
    }


    public void setIsAutoBilling(Activity context, boolean data) {
        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(ApplicationConstant.INSTANCE.isAutoBillingPref, data);
        editor.apply();

    }

    public boolean getIsAutoBilling(Activity context) {
        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, context.MODE_PRIVATE);
        return prefs.getBoolean(ApplicationConstant.INSTANCE.isAutoBillingPref, false);
    }

    public void setIsRealAPIPerTransaction(Activity context, boolean data) {
        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(ApplicationConstant.INSTANCE.isRealAPIPerTransactionPref, data);
        editor.apply();

    }

    public boolean getIsRealAPIPerTransaction(Activity context) {
        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, context.MODE_PRIVATE);
        return prefs.getBoolean(ApplicationConstant.INSTANCE.isRealAPIPerTransactionPref, false);
    }

    public void setIsRoffer(Activity context, boolean data) {
        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(ApplicationConstant.INSTANCE.isROfferPref, data);
        editor.apply();

    }

    public boolean getIsRoffer(Activity context) {
        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, context.MODE_PRIVATE);
        return prefs.getBoolean(ApplicationConstant.INSTANCE.isROfferPref, false);
    }

    public void setIsDTHInfo(Activity context, boolean data) {
        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(ApplicationConstant.INSTANCE.isDTHInfoPref, data);
        editor.apply();

    }

    public boolean getIsDTHInfo(Activity context) {
        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, context.MODE_PRIVATE);
        return prefs.getBoolean(ApplicationConstant.INSTANCE.isDTHInfoPref, false);
    }

    public void setIsTargetShow(Activity context, boolean data) {
        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(ApplicationConstant.INSTANCE.isTargetShowPref, data);
        editor.apply();

    }

    public boolean getIsTargetShow(Activity context) {
        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, context.MODE_PRIVATE);
        return prefs.getBoolean(ApplicationConstant.INSTANCE.isTargetShowPref, false);
    }

    public void setAccountOpenList(Activity context, String data) {
        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(ApplicationConstant.INSTANCE.accountOpenListPref, data);
        editor.apply();

    }

    public String getAccountOpenList(Activity context) {
        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, context.MODE_PRIVATE);
        return prefs.getString(ApplicationConstant.INSTANCE.accountOpenListPref, "");
    }


    public void setIsShowPDFPlan(Activity context, boolean data) {
        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(ApplicationConstant.INSTANCE.isShowPDFPlanPref, data);
        editor.apply();

    }

    public void GetAppPackageAvailable(final Activity context, final CustomLoader loader, final ApiCallBack apiCallBack) {
        String LoginResponse = getLoginPref(context);
        LoginResponse LoginDataResponse = new Gson().fromJson(LoginResponse, LoginResponse.class);

        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<UpgradePackageResponse> call = git.GetAvailablePackages(new BalanceRequest(LoginDataResponse.getData().getUserID() + "", LoginDataResponse.getData().getLoginTypeID() + "",
                    ApplicationConstant.INSTANCE.APP_ID,
                    getIMEI(context),
                    "", BuildConfig.VERSION_NAME, getSerialNo(context),
                    LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));
            call.enqueue(new Callback<UpgradePackageResponse>() {


                @Override
                public void onResponse(Call<UpgradePackageResponse> call, retrofit2.Response<UpgradePackageResponse> response) {
                    //     // Log.e("response hello", "TransferToWallet response : " + new Gson().toJson(response.body()).toString());
                    if (loader.isShowing())
                        loader.dismiss();

                    if (response.body() != null && response.body().getStatuscode() != null && response.body().getStatuscode().equalsIgnoreCase("1")) {

                        if (apiCallBack != null) {
                            apiCallBack.onSucess(response.body());
                        }


                        //Balancecheck(context, loader);

                        // Successful(context, response.body().getMsg());


                   /* Intent intent=new Intent(context, UpgradePackage.class);
                    intent.putExtra("response", "" + new Gson().toJson(response.body()).toString());
                    context.startActivity(intent);*/


                    } else {
                        Failed(context, response.body().getMsg());
                    }

                }

                @Override
                public void onFailure(Call<UpgradePackageResponse> call, Throwable t) {
                    if (loader.isShowing())
                        loader.dismiss();

                    Failed(context, t.getMessage());

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            Failed(context, e.getMessage());
        }

    }

    public void GetAppPackageUpgrade(final Activity context, final String uid, final String packageid, final CustomLoader loader, final Button btn_upgrade) {

        String LoginResponse = getLoginPref(context);
        LoginResponse LoginDataResponse = new Gson().fromJson(LoginResponse, LoginResponse.class);

        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<UpgradePackageResponse> call = git.UpgradePackage(new UpgradePackageRequest(uid, packageid, LoginDataResponse.getData().getUserID() + "",
                    LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession(), ApplicationConstant.INSTANCE.APP_ID,
                    getIMEI(context), "", BuildConfig.VERSION_NAME, getSerialNo(context),
                    LoginDataResponse.getData().getLoginTypeID() + ""));


            call.enqueue(new Callback<UpgradePackageResponse>() {


                @Override
                public void onResponse(Call<UpgradePackageResponse> call, retrofit2.Response<UpgradePackageResponse> response) {
                    //        // Log.e("response hello", "TransferToWallet response : " + new Gson().toJson(response.body()).toString());
                    if (loader.isShowing())
                        loader.dismiss();

                    if (response.body() != null) {

                        if (response.body().getStatuscode() != null && response.body().getStatuscode().equalsIgnoreCase("1")) {

                            Successful(context, response.body().getMsg());
                            btn_upgrade.setBackgroundColor(context.getResources().getColor(R.color.grey));
                        } else {
                            Failed(context, response.body().getMsg());
                        }
                    }

                }

                @Override
                public void onFailure(Call<UpgradePackageResponse> call, Throwable t) {
                    if (loader.isShowing())
                        loader.dismiss();
                    Failed(context, t.getMessage());

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            //Failed(context, e.getMessage());
            Failed(context, e.getMessage());
        }

    }

    public boolean getIsShowPDFPlan(Activity context) {
        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, context.MODE_PRIVATE);
        return prefs.getBoolean(ApplicationConstant.INSTANCE.isShowPDFPlanPref, false);
    }

    public void setBalanceLowTime(Activity context, long data) {
        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putLong(ApplicationConstant.INSTANCE.balanceLowTimePref, data);
        editor.apply();

    }

    public long getBalanceLowTime(Activity context) {
        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, context.MODE_PRIVATE);
        return prefs.getLong(ApplicationConstant.INSTANCE.balanceLowTimePref, 0);
    }

    public void setAppLogoUrl(Context context, String data) {
        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(ApplicationConstant.INSTANCE.appLogoUrlPref, data);

        editor.apply();

    }

    public String getAppLogoUrl(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, context.MODE_PRIVATE);
        return prefs.getString(ApplicationConstant.INSTANCE.appLogoUrlPref, "");
    }

    public void setBalanceCheck(Activity context, String balance) {
        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(ApplicationConstant.INSTANCE.balancePref, balance);
        editor.apply();
        ActivityActivityMessage activityActivityMessage =
                new ActivityActivityMessage(balance, "balanceUpdate");
        GlobalBus.getBus().post(activityActivityMessage);
    }

    public void setEmailVerifiedPref(Context context, boolean value) {
        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNameLoginPref, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(ApplicationConstant.INSTANCE.isEmailVerifiedPref, value);
        editor.apply();

    }

    public boolean getEmailVerifiedPref(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNameLoginPref, context.MODE_PRIVATE);
        return prefs.getBoolean(ApplicationConstant.INSTANCE.isEmailVerifiedPref, false);

    }

    public void setSocialLinkSavedPref(Context context, boolean value) {
        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNameLoginPref, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(ApplicationConstant.INSTANCE.isSocialLinkSavedPref, value);
        editor.apply();

    }

    public boolean getSocialLinkSavedPref(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNameLoginPref, context.MODE_PRIVATE);
        return prefs.getBoolean(ApplicationConstant.INSTANCE.isSocialLinkSavedPref, false);

    }

   /* public String getAscReportPref(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNameLoginPref, context.MODE_PRIVATE);
        return prefs.getString(ApplicationConstant.INSTANCE.ascReportPref, "");

    }
    public void setAscReportPref(Context context, String value) {
        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNameLoginPref, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(ApplicationConstant.INSTANCE.ascReportPref, value);
        editor.apply();

    }*/

    public String getAreaListPref(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNameLoginPref, context.MODE_PRIVATE);
        return prefs.getString(ApplicationConstant.INSTANCE.areaListPref, "");

    }

    public void setAreaListPref(Context context, String value) {
        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNameLoginPref, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(ApplicationConstant.INSTANCE.areaListPref, value);
        editor.apply();

    }

    public void setSocialorEmailDialogTimePref(Context context, long value) {
        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNameLoginPref, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putLong(ApplicationConstant.INSTANCE.SocialorEmailDialogTimePref, value);
        editor.apply();

    }

    public long getSocialorEmailDialogTimePref(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNameLoginPref, context.MODE_PRIVATE);
        return prefs.getLong(ApplicationConstant.INSTANCE.SocialorEmailDialogTimePref, 0);

    }

    public String formatedDate2(String dateStr) {
        if (dateStr != null && !dateStr.isEmpty()) {
            String formateDate = null;
            SimpleDateFormat inputFormat;

            inputFormat = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");


            SimpleDateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy hh:mm aa");
            try {
                Date date = inputFormat.parse(dateStr);
                formateDate = outputFormat.format(date);
                System.out.println(date);
            } catch (ParseException e) {
                e.printStackTrace();
                return dateStr;
            }

            return formateDate;
        }
        return dateStr;
    }


    public String formatedAmountWithRupees(String value) {

        if (value != null && !value.isEmpty()) {
            if (value.contains(".")) {
                String postfixValue = value.substring(value.indexOf("."));
                if (postfixValue.equalsIgnoreCase(".0")) {
                    return "\u20B9 " + value.replace(".0", "").trim();
                } else if (postfixValue.equalsIgnoreCase(".00")) {
                    return "\u20B9 " + value.replace(".00", "").trim();
                } else if (postfixValue.equalsIgnoreCase(".000")) {
                    return "\u20B9 " + value.replace(".000", "").trim();
                } else if (postfixValue.equalsIgnoreCase(".0000")) {
                    return "\u20B9 " + value.replace(".0000", "").trim();
                } else {
                    try {
                        return "\u20B9 " + String.format("%.2f", Double.parseDouble(value.trim()));
                    } catch (NumberFormatException nfe) {
                        return "\u20B9 " + value.trim();
                    }
                }
            } else {
                return "\u20B9 " + value.trim();
            }

        } else {
            return "\u20B9 0";
        }
    }

    public String formatedAmount(String value) {
        if (value != null && !value.isEmpty()) {
            if (value.contains(".")) {
                String postfixValue = value.substring(value.indexOf("."));
                if (postfixValue.equalsIgnoreCase(".0")) {
                    return value.replace(".0", "");
                } else if (postfixValue.equalsIgnoreCase(".00")) {
                    return value.replace(".00", "");
                } else if (postfixValue.equalsIgnoreCase(".000")) {
                    return value.replace(".000", "");
                } else if (postfixValue.equalsIgnoreCase(".0000")) {
                    return value.replace(".0000", "");
                } else {
                    return value;
                }
            } else {
                return value;
            }

        } else {
            return "0";
        }
    }

   /* public void setActiveService(Activity context, String data) {
        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(ApplicationConstant.INSTANCE.activeServicePref, data);
        editor.apply();

    }*/

    public void setActiveService(Activity context, String data, boolean isUpi, boolean isUPIQR, boolean isECollectEnable,
                                 boolean isPaymentGatway) {
        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(ApplicationConstant.INSTANCE.activeServicePref, data);
        editor.putBoolean(ApplicationConstant.INSTANCE.isUpi, isUpi);
        editor.putBoolean(ApplicationConstant.INSTANCE.isUpiQR, isUPIQR);
        editor.putBoolean(ApplicationConstant.INSTANCE.isECollectEnable, isECollectEnable);
        editor.putBoolean(ApplicationConstant.INSTANCE.isPaymentGatway, isPaymentGatway);
        editor.apply();

    }

    public String getActiveService(Activity context) {
        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, context.MODE_PRIVATE);
        return prefs.getString(ApplicationConstant.INSTANCE.activeServicePref, "");
    }


    public void setNewsData(Activity context, String data) {
        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(ApplicationConstant.INSTANCE.newsDataPref, data);
        editor.apply();

    }

    public String getNewsData(Activity context) {
        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, context.MODE_PRIVATE);
        return prefs.getString(ApplicationConstant.INSTANCE.newsDataPref, "");
    }

    public void setBannerData(Activity context, String data) {
        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(ApplicationConstant.INSTANCE.bannerDataPref, data);
        editor.apply();

    }

    public String getBannerData(Activity context) {
        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, context.MODE_PRIVATE);
        return prefs.getString(ApplicationConstant.INSTANCE.bannerDataPref, "");
    }

    public void setPaynearUsb(Activity context, boolean data) {
        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(ApplicationConstant.INSTANCE.Paynear_USB, data);
        editor.apply();

    }

    public boolean getPaynearUsb(Activity context) {
        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, context.MODE_PRIVATE);
        return prefs.getBoolean(ApplicationConstant.INSTANCE.Paynear_USB, false);
    }

    public void setTotalTargetData(Activity context, String data) {
        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(ApplicationConstant.INSTANCE.totalTargetDataPref, data);

        editor.apply();

    }

    public String getTotalTargetData(Activity context) {
        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, context.MODE_PRIVATE);
        return prefs.getString(ApplicationConstant.INSTANCE.totalTargetDataPref, "");
    }


    public void setDayBookData(Activity context, String data) {
        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(ApplicationConstant.INSTANCE.dayBookDataPref, data);

        editor.apply();

    }

    public String getDayBookData(Activity context) {
        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, context.MODE_PRIVATE);
        return prefs.getString(ApplicationConstant.INSTANCE.dayBookDataPref, "");
    }


    public void setCompanyProfile(Activity context, String data) {
        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(ApplicationConstant.INSTANCE.contactUsPref, data);
        editor.apply();

    }

    public String getCompanyProfile(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, context.MODE_PRIVATE);
        return prefs.getString(ApplicationConstant.INSTANCE.contactUsPref, "");
    }

    public void setFundreqToList(Activity context, String FundreqTo) {
        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(ApplicationConstant.INSTANCE.FundreqTo, FundreqTo);
        editor.apply();
    }

    public String getFundreqToList(Activity context) {
        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, context.MODE_PRIVATE);

        return prefs.getString(ApplicationConstant.INSTANCE.FundreqTo, "");
    }

    public boolean getDoubleFactorPref(Activity context) {
        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNameLoginPref, context.MODE_PRIVATE);
        return prefs.getBoolean(ApplicationConstant.INSTANCE.DoubleFactorPref, false);

    }

    public void setDoubleFactorPref(Context context, boolean value) {
        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNameLoginPref, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(ApplicationConstant.INSTANCE.DoubleFactorPref, value);

        editor.apply();

    }

    public void setBankList(Activity context, String bankList) {
        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(ApplicationConstant.INSTANCE.bankListPref, bankList);
        editor.apply();
    }

    public String getBankList(Activity context) {

        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, context.MODE_PRIVATE);
        return prefs.getString(ApplicationConstant.INSTANCE.bankListPref, null);

    }

    public void setFosBankList(Activity context, String bankList) {
        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(ApplicationConstant.INSTANCE.bankListFOSPref, bankList);
        editor.apply();
    }

    public String getFosBankList(Activity context) {

        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, context.MODE_PRIVATE);
        return prefs.getString(ApplicationConstant.INSTANCE.bankListFOSPref, null);

    }

    private void openOTPDialog(final Activity context, final CustomLoader loader, final String flag, final String Otprefrence, final String SenderNumber) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.verifyotp, null);

        edMobileOtp = view.findViewById(R.id.ed_mobile_otp);
        final TextInputLayout tilMobileOtp = view.findViewById(R.id.til_mobile_otp);
        final Button okButton = view.findViewById(R.id.okButton);
        final Button cancelButton = view.findViewById(R.id.cancelButton);
        final Button resendButton = view.findViewById(R.id.resendButton);
        final TextView timer = view.findViewById(R.id.timer);
        setTimer(timer, resendButton);
        final Dialog dialog = new Dialog(context, R.style.alert_dialog_light);
        dialog.setCancelable(false);
        dialog.setContentView(view);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
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

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edMobileOtp.getText() != null && edMobileOtp.getText().length() == 6) {
                    tilMobileOtp.setErrorEnabled(false);
                    if (isNetworkAvialable(context)) {
                        loader.show();
                        loader.setCancelable(false);
                        loader.setCanceledOnTouchOutside(false);
                        if (flag.equals("verifySender")) {
                            VerifySender(context, SenderNumber, edMobileOtp.getText().toString(), loader, dialog);

                        } else {
                            ValidateOTP(context, edMobileOtp.getText().toString(), Otprefrence, 1, SenderNumber, loader);
                        }
                    } else {
                        NetworkError(context, context.getResources().getString(R.string.err_msg_network_title),
                                context.getResources().getString(R.string.err_msg_network));
                    }

                } else {
                    tilMobileOtp.setError(context.getString(R.string.err_msg_otp));
                }
            }
        });

        resendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resendButton.setVisibility(View.GONE);
                timer.setText("");
                if (countDownTimer != null) {
                    countDownTimer.cancel();
                    countDownTimer = null;
                }

                ResendOTP(context, Otprefrence, loader, new ApiCallBack() {
                    @Override
                    public void onSucess(Object object) {
                        setTimer(timer, resendButton);
                    }
                });
            }
        });

        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (countDownTimer != null) {
                    countDownTimer.cancel();
                    countDownTimer = null;
                }
            }
        });
        dialog.show();
    }

    private void openTOTPDialog(final Activity context, final CustomLoader loader, final String Otprefrence, final String SenderNumber) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.verifytotp, null);

        edMobileOtp = view.findViewById(R.id.ed_mobile_otp);
        final TextInputLayout tilMobileOtp = view.findViewById(R.id.til_mobile_otp);
        final Button okButton = view.findViewById(R.id.okButton);
        final Button cancelButton = view.findViewById(R.id.cancelButton);
        final Button resendButton = view.findViewById(R.id.resendButton);
        final TextView timer = view.findViewById(R.id.timer);
        setTimer(timer, resendButton);
        final Dialog dialog = new Dialog(context, R.style.alert_dialog_light);
        dialog.setCancelable(false);
        dialog.setContentView(view);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
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

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edMobileOtp.getText() != null && edMobileOtp.getText().length() == 6) {
                    tilMobileOtp.setErrorEnabled(false);
                    if (isNetworkAvialable(context)) {
                        loader.show();
                        loader.setCancelable(false);
                        loader.setCanceledOnTouchOutside(false);

                        ValidateTOTP(context, edMobileOtp.getText().toString(), Otprefrence, 1, SenderNumber, loader);

                    } else {
                        NetworkError(context, context.getResources().getString(R.string.err_msg_network_title),
                                context.getResources().getString(R.string.err_msg_network));
                    }

                } else {
                    tilMobileOtp.setError(context.getString(R.string.err_msg_otp));
                }
            }
        });

        resendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resendButton.setVisibility(View.GONE);
                timer.setText("");
                if (countDownTimer != null) {
                    countDownTimer.cancel();
                    countDownTimer = null;
                }

                ResendOTP(context, Otprefrence, loader, new ApiCallBack() {
                    @Override
                    public void onSucess(Object object) {
                        setTimer(timer, resendButton);
                    }
                });
            }
        });

        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (countDownTimer != null) {
                    countDownTimer.cancel();
                    countDownTimer = null;
                }
            }
        });
        dialog.show();
    }


    private void setTimer(final TextView timer, final Button resendcode) {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        timer.setText("Resend OTP - 00:00");
        countDownTimer = new CountDownTimer(30000, 1000) { // adjust the milli seconds here

            public void onTick(long millisUntilFinished) {
                timer.setText("Resend OTP - " + String.format("%02d:%02d",
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
            }

            public void onFinish() {
                timer.setText("");
                resendcode.setVisibility(View.VISIBLE);
            }
        }.start();
    }

    public void setNumberList(Activity context, String numberList) {
        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(ApplicationConstant.INSTANCE.numberListPref, numberList);
        editor.apply();
    }

    public String getNumberList(Activity context) {
        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, context.MODE_PRIVATE);
        String numberList = prefs.getString(ApplicationConstant.INSTANCE.numberListPref, null);

        return numberList;
    }

    public boolean getIsRealApiPref(Activity context) {
        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNameLoginPref, context.MODE_PRIVATE);
        return prefs.getBoolean(ApplicationConstant.INSTANCE.IsRealApiPref, false);

    }

    public void setIsRealApiPref(Context context, boolean value) {
        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNameLoginPref, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(ApplicationConstant.INSTANCE.IsRealApiPref, value);

        editor.apply();

    }

    public void setFCMRegKey(Context context, String key) {
        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(ApplicationConstant.INSTANCE.regFCMKeyPref, key);
        editor.apply();
    }

    public static String getFCMRegKey(Context context) {
        SharedPreferences myPrefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, context.MODE_PRIVATE);
        String key = myPrefs.getString(ApplicationConstant.INSTANCE.regFCMKeyPref, null);

        return key;
    }

    /*public void PaymentRequest(final Activity context, String s, String bankid, String txttranferAmount, File selectedImageFile, int bankID, String accountNumber, String transactionId, String ChecknumberID, String txtCardNo, String txtMobileNo, String txtAccountID, String PaymentModeID, int paymentModeID, String walletType, final View btnPaymentSubmit, final CustomLoader loader, LoginResponse loginDataResponse, final ApiCallBack mApiCallBack) {
        try {
            btnPaymentSubmit.setEnabled(false);
            String LoginResponse = getLoginPref(context);
            LoginResponse LoginDataResponse = new Gson().fromJson(LoginResponse, LoginResponse.class);
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<GetBankAndPaymentModeResponse> call = git.AppFundOrder(new BalanceRequest(bankid,
                    txttranferAmount, transactionId, txtMobileNo, ChecknumberID, txtCardNo, txtAccountID,
                    accountNumber, PaymentModeID,
                    LoginDataResponse.getData().getUserID() + "", LoginDataResponse.getData().getLoginTypeID() + "",
                    ApplicationConstant.INSTANCE.APP_ID,
                    getIMEI(context),
                    "", BuildConfig.VERSION_NAME, getSerialNo(context), LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession(), walletType));
            call.enqueue(new Callback<GetBankAndPaymentModeResponse>() {

                @Override
                public void onResponse(Call<GetBankAndPaymentModeResponse> call, retrofit2.Response<GetBankAndPaymentModeResponse> response) {
//
                    if (loader.isShowing())
                        loader.dismiss();

                    if (response.body() != null && response.body().getStatuscode() != null) {
                        if (response.body().getStatuscode().equalsIgnoreCase("1")) {
                            // setFundreqToList(context, new Gson().toJson(response.body()).toString());
                            Successful(context, response.body().getMsg() + "");
                            btnPaymentSubmit.setEnabled(false);
                            if (mApiCallBack != null) {
                                mApiCallBack.onSucess(response.body());
                            }
                            *//*FragmentActivityMessage activityActivityMessage =
                                    new FragmentActivityMessage(new Gson().toJson(response.body()).toString(), "refreshvalue");
                            GlobalBus.getBus().post(activityActivityMessage);*//*
                        } else if (response.body().getStatuscode().equalsIgnoreCase("-1")) {
                            btnPaymentSubmit.setEnabled(true);
                            if (response.body().getIsVersionValid() == false) {
                                versionDialog(context);
                            } else {
                                Error(context, response.body().getMsg() + "");
                            }
                        } else {
                            btnPaymentSubmit.setEnabled(true);
                        }

                    }
                }

                @Override
                public void onFailure(Call<GetBankAndPaymentModeResponse> call, Throwable t) {

                    btnPaymentSubmit.setEnabled(true);
                    if (loader.isShowing())
                        loader.dismiss();
                }
            });

        } catch (Exception e) {
            btnPaymentSubmit.setEnabled(true);
            e.printStackTrace();
        }

    }
*/
    public void PaymentRequest(final Activity context, String upiid, String orderID, String checksum, File selectedFile, int bankid, String txttranferAmount,
                               String accountNumber, String transactionId, String ChecknumberID, String txtCardNo,
                               String txtMobileNo, String txtAccountID, int PaymentModeID, String walletType,
                               final View btnPaymentSubmit, final CustomLoader loader, LoginResponse LoginDataResponse, final ApiCallBack mApiCallBack) {
        try {
            btnPaymentSubmit.setEnabled(false);
            int isImage = 0;
            if (selectedFile != null) {
                isImage = 1;
            }
           /* else if(selectedFile.exists()){
                isImage=true;
            }*/


            AddFundRequest mAddFundRequest = new AddFundRequest(upiid, orderID, checksum, isImage, bankid + "",
                    txttranferAmount, transactionId, txtMobileNo, ChecknumberID, txtCardNo, txtAccountID, accountNumber, PaymentModeID + "",
                    LoginDataResponse.getData().getUserID() + "", LoginDataResponse.getData().getLoginTypeID() + "",
                    ApplicationConstant.INSTANCE.APP_ID,
                    getIMEI(context),
                    "", BuildConfig.VERSION_NAME, getSerialNo(context), LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession(), walletType);

            String req = new Gson().toJson(mAddFundRequest);
            // Parsing any Media type file
            MultipartBody.Part fileToUpload = null;
            if (selectedFile != null) {
                RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), selectedFile);
                if (selectedFile != null) {
                    fileToUpload = MultipartBody.Part.createFormData("file", selectedFile.getName(), requestBody);
                }
            }
            RequestBody requestStr = RequestBody.create(MediaType.parse("text/plain"), req);
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<GetBankAndPaymentModeResponse> call = git.AppFundOrder(fileToUpload, requestStr);
            call.enqueue(new Callback<GetBankAndPaymentModeResponse>() {

                @Override
                public void onResponse(Call<GetBankAndPaymentModeResponse> call, retrofit2.Response<GetBankAndPaymentModeResponse> response) {
//
                    if (loader.isShowing())
                        loader.dismiss();
                    if (response.isSuccessful()) {
                        if (response.body() != null && response.body().getStatuscode() != null) {
                            if (response.body().getStatuscode().equalsIgnoreCase("1")) {
                                // setFundreqToList(context, new Gson().toJson(response.body()).toString());
                                SuccessfulWithFinish(response.body().getMsg() + "", context, false);
                                btnPaymentSubmit.setEnabled(false);
                                if (mApiCallBack != null) {
                                    mApiCallBack.onSucess(response.body());
                                }
                            /*FragmentActivityMessage activityActivityMessage =
                                    new FragmentActivityMessage(new Gson().toJson(response.body()).toString(), "refreshvalue");
                            GlobalBus.getBus().post(activityActivityMessage);*/
                            } else if (response.body().getStatuscode().equalsIgnoreCase("-1")) {
                                btnPaymentSubmit.setEnabled(true);
                                if (response.body().getIsVersionValid() == false) {
                                    versionDialog(context);
                                } else {
                                    Error(context, response.body().getMsg() + "");
                                }
                            } else {
                                btnPaymentSubmit.setEnabled(true);
                            }

                        } else {
                            Error(context, context.getResources().getString(R.string.some_thing_error));
                            btnPaymentSubmit.setEnabled(true);
                        }
                    } else {
                        apiErrorHandle(context, response.code(), response.message());
                    }
                }

                @Override
                public void onFailure(Call<GetBankAndPaymentModeResponse> call, Throwable t) {

                    btnPaymentSubmit.setEnabled(true);
                    if (loader.isShowing())
                        loader.dismiss();
                }
            });

        } catch (Exception e) {
            btnPaymentSubmit.setEnabled(true);
            e.printStackTrace();
        }

    }


//    public void setReferrerId(Context context, String value) {
//        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefRefferalDataPref, context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = prefs.edit();
//        editor.putString(ApplicationConstant.INSTANCE.UserReferralPref, value);
//        editor.apply();
//
//    }
//
//    public String getReferrerId(Activity context) {
//        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefRefferalDataPref, context.MODE_PRIVATE);
//        return prefs.getString(ApplicationConstant.INSTANCE.UserReferralPref, "");
//
//    }


    public void setReferrerIdSetData(Context context, boolean value) {
        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefRefferalDataPref, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(ApplicationConstant.INSTANCE.IsUserReferralDataPref, value);
        editor.apply();

    }

    public boolean isReferrerIdSetData(Activity context) {
        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefRefferalDataPref, context.MODE_PRIVATE);
        return prefs.getBoolean(ApplicationConstant.INSTANCE.IsUserReferralDataPref, false);

    }

    public void setReferrerId(Context context, String value) {
        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefRefferalDataPref, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(ApplicationConstant.INSTANCE.UserReferralPref, value);
        editor.apply();

    }

    public String getReferrerId(Activity context) {
        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefRefferalDataPref, context.MODE_PRIVATE);
        return prefs.getString(ApplicationConstant.INSTANCE.UserReferralPref, "");

    }

    public void MyCommission(final Activity context, final CustomLoader loader, final ApiCallBack mApiCallBack) {
        try {
            String LoginResponse = getLoginPref(context);
            LoginResponse LoginDataResponse = new Gson().fromJson(LoginResponse, LoginResponse.class);
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<SlabCommissionResponse> call = git.DisplayCommission(new BalanceRequest("", LoginDataResponse.getData().getSlabID(),
                    LoginDataResponse.getData().getUserID() + "", LoginDataResponse.getData().getLoginTypeID() + "",
                    ApplicationConstant.INSTANCE.APP_ID,
                    getIMEI(context),
                    "", BuildConfig.VERSION_NAME, getSerialNo(context), LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));
            call.enqueue(new Callback<SlabCommissionResponse>() {

                @Override
                public void onResponse(Call<SlabCommissionResponse> call, retrofit2.Response<SlabCommissionResponse> response) {

                    if (loader.isShowing())
                        loader.dismiss();

                    if (response.body() != null && response.body().getStatuscode() != null) {
                        if (response.body().getStatuscode() == 1) {
                            setCommList(context, new Gson().toJson(response.body()).toString());
                            if (mApiCallBack != null) {
                                mApiCallBack.onSucess(response.body());
                            }
                        } else if (response.body().getStatuscode() == -1) {
                            if (response.body().getVersionValid() != null && response.body().getVersionValid() == false) {
                                versionDialog(context);
                            } else {
                                Error(context, response.body().getMsg() + "");
                            }
                        }

                    }
                }

                @Override
                public void onFailure(Call<SlabCommissionResponse> call, Throwable t) {

                    if (loader.isShowing())
                        loader.dismiss();
                    Error(context, context.getResources().getString(R.string.err_something_went_wrong) + "");
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void GetNotifications(final Activity context, final ApiCallBack mApiCallBack) {
        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            String LoginResponse = getLoginPref(context);
            LoginResponse LoginDataResponse = new Gson().fromJson(LoginResponse, LoginResponse.class);
            Call<AppUserListResponse> call = git.GetAppNotification(new BasicRequest(
                    LoginDataResponse.getData().getUserID(), LoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID,
                    getIMEI(context),
                    "", BuildConfig.VERSION_NAME, getSerialNo(context), LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));
            call.enqueue(new Callback<AppUserListResponse>() {

                @Override
                public void onResponse(Call<AppUserListResponse> call, retrofit2.Response<AppUserListResponse> response) {

                    AppUserListResponse apiData = response.body();
                    if (apiData != null) {
                        if (apiData.getStatuscode() == 1) {
                            if (mApiCallBack != null) {
                                ArrayList<Integer> visibleId = new ArrayList<>();
                                int visibleCount = 0;
                                AppUserListResponse storedData = new Gson().fromJson(getNotificationList(context), AppUserListResponse.class);
                                if (storedData != null && storedData.getNotifications() != null && storedData.getNotifications().size() > 0) {

                                    for (int i = 0; i < storedData.getNotifications().size(); i++) {
                                        if (storedData.getNotifications().get(i).isSeen()) {
                                            visibleId.add(storedData.getNotifications().get(i).getId());
                                        }
                                    }
                                }
                                if (visibleId.size() > 0) {
                                    for (int i = 0; i < apiData.getNotifications().size(); i++) {
                                        if (visibleId.contains(apiData.getNotifications().get(i).getId())) {
                                            apiData.getNotifications().get(i).setSeen(true);
                                            visibleCount++;
                                        }
                                    }
                                }
                                setNotificationList(context, new Gson().toJson(apiData));

                                mApiCallBack.onSucess(apiData.getNotifications().size() - visibleCount);
                            }

//
                        } else if (apiData.getStatuscode() == -1) {
                            if (apiData.getVersionValid() == false) {
                                versionDialog(context);
                            } else {
                                Error(context, apiData.getMsg() + "");
                            }
                        }

                    }
                }

                @Override
                public void onFailure(Call<AppUserListResponse> call, Throwable t) {

                    //  Error(context, context.getResources().getString(R.string.err_something_went_wrong) + "");
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void GetActiveService(final Activity context, final ApiActiveServiceCallBack mApiCallBack) {
        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            String LoginResponse = getLoginPref(context);
            LoginResponse LoginDataResponse = new Gson().fromJson(LoginResponse, LoginResponse.class);
            boolean isAccountStatement=LoginDataResponse.isAccountStatement();
            Call<OpTypeResponse> call = git.GetOpTypes(new BalanceRequest(
                    LoginDataResponse.getData().getUserID(), LoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID,
                    getIMEI(context),
                    "", BuildConfig.VERSION_NAME, getSerialNo(context), LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));
            call.enqueue(new Callback<OpTypeResponse>() {

                @Override
                public void onResponse(Call<OpTypeResponse> call, retrofit2.Response<OpTypeResponse> response) {

                    OpTypeResponse apiData = response.body();
                    if (apiData != null) {
                        /*if (apiData.getStatuscode() == 1) {
                            UtilsMethodDMTPipe.INSTANCE.setIsDMTWithPipe(context, apiData.isDMTWithPipe());
                            setActiveService(context, new Gson().toJson(apiData));
                            if (mApiCallBack != null) {
                                mApiCallBack.onSucess(apiData);
                            }

//
                        } else if (apiData.getStatuscode() == -1) {
                            if (apiData.getVersionValid() != null && apiData.getVersionValid() == false) {
                                versionDialog(context);
                            } else {
                                // Error(context, apiData.getMsg() + "");
                            }
                        }*/
                        if (apiData.getStatuscode() == 1) {


                            if (apiData.getData() != null && apiData.getData().getAssignedOpTypes() != null && apiData.getData().getAssignedOpTypes().size() > 0) {
                                ArrayList<AssignedOpType> selectedAssignOpType = new ArrayList<>();

                                ArrayList<Integer> groupServiceid = new ArrayList<>();
                                for (AssignedOpType item : apiData.getData().getAssignedOpTypes()) {
                                    if (item.getIsDisplayService() && !groupServiceid.contains(item.getServiceID())) {
                                        ArrayList<AssignedOpType> selectedSubAssignOpType = new ArrayList<>();
                                        for (AssignedOpType subItem : apiData.getData().getAssignedOpTypes()) {
                                            if (subItem.getParentID() == item.getParentID() && subItem.getIsDisplayService()) {
                                                groupServiceid.add(subItem.getServiceID());
                                                selectedSubAssignOpType.add(subItem);
                                            }
                                        }
                                        selectedAssignOpType.add(new AssignedOpType(item.getServiceID(), item.getParentID(), item.getName(), item.getService(), item.getIsServiceActive(), item.getIsActive(), item.getIsDisplayService(), item.getUpline(), item.getUplineMobile(), item.getCcContact(), selectedSubAssignOpType));
                                    } else if (!groupServiceid.contains(item.getServiceID())) {
                                        selectedAssignOpType.add(new AssignedOpType(item.getServiceID(), item.getParentID(), item.getName(), item.getService(), item.getIsServiceActive(), item.getIsActive(), item.getIsDisplayService(), item.getUpline(), item.getUplineMobile(), item.getCcContact(), new ArrayList<AssignedOpType>()));
                                    }

                                }


                                apiData.getData().setAssignedOpTypes(selectedAssignOpType);


                                UtilsMethodDMTPipe.INSTANCE.setIsDMTWithPipe(context, apiData.isDMTWithPipe());

                                OpTypeRollIdWiseServices mOpTypeRollIdWiseServices = new OpTypeRollIdWiseServices(apiData.getData().getShowableActiveSerive(apiData.isAddMoneyEnable()),
                                        apiData.getData().getShowableOtherReportSerive(apiData.isAddMoneyEnable(),isAccountStatement),
                                        apiData.getData().getShowableRTReportSerive(isAccountStatement), apiData.getData().getShowableFOSSerive(isAccountStatement));

                                /*setActiveService(context, new Gson().toJson(mOpTypeRollIdWiseServices));*/
                                setActiveService(context, new Gson().toJson(mOpTypeRollIdWiseServices), apiData.getUPI(), apiData.getUPIQR(), apiData.getECollectEnable(), apiData.getPaymentGatway());

                                if (mApiCallBack != null) {
                                    mApiCallBack.onSucess(apiData, mOpTypeRollIdWiseServices);
                                }
                            }


//
                        }
                    }
                }

                @Override
                public void onFailure(Call<OpTypeResponse> call, Throwable t) {


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

   /* public CompanyProfileResponse getCompanyProfileDetails(Context context) {
        CompanyProfileResponse response = null;
        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, context.MODE_PRIVATE);
        prefs.getString(ApplicationConstant.INSTANCE.companyPref, null);
        try {
            response = new Gson().fromJson(prefs.getString(ApplicationConstant.INSTANCE.companyPref, null), CompanyProfileResponse.class);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return response;
    }*/


    public void AppPopup(final Context context, final CustomLoader loader) {
        try {
            String LoginResponse = getLoginPref(context);
            LoginResponse LoginDataResponse = new Gson().fromJson(LoginResponse, LoginResponse.class);
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<BalanceResponse> call = git.GetPopupAfterLogin(new BalanceRequest(LoginDataResponse.getData().getUserID() + "", LoginDataResponse.getData().getLoginTypeID() + "",
                    com.solution.brothergroup.Util.ApplicationConstant.INSTANCE.APP_ID,
                    getIMEI(context),
                    "", BuildConfig.VERSION_NAME, getSerialNo(context),
                    LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));
            call.enqueue(new Callback<BalanceResponse>() {
                @Override
                public void onResponse(Call<BalanceResponse> call, final retrofit2.Response<BalanceResponse> response) {
                    // Log.e("balance", "is : " + new Gson().toJson(response.body()).toString());
                    try {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }

                        if (response.body() != null) {
                            if (response.body().getStatuscode() != null && response.body().getStatuscode() == 1) {
                                if (response.body().getPopup() != null && response.body().getPopup().length() > 0) {
                                    openImageDialog(context, response.body().getPopup());
                                }
                            }





                          /*  if (flag == 1) {
                                if (response.body().getBeforeLoginPopupApp()) {

                                    openImageDialog(context,imageurl);
                                }
                            } else if (flag == 2) {

                                if (response.body().getAfterLoginPopupApp()) {
                                    openImageDialog(context,imageurl);
                                }
                            }*/


                        }
                        // apiCallBack.onSucess(response.body());


                    } catch (Exception e) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                    }

                }

                @Override
                public void onFailure(Call<BalanceResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            loader.dismiss();
                    }
                    if (t.getMessage().contains("No address associated with hostname")) {
                        Error((Activity) context, context.getResources().getString(R.string.network_error));
                    } else {
                        Error((Activity) context, t.getMessage());
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void openImageDialog(final Context context, String imageurl) {

        RequestOptions requestOptions = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.NONE) // because file name is always same
                .skipMemoryCache(true);


        Glide.with(context)
                .asBitmap()
                .load(ApplicationConstant.INSTANCE.baseUrl + imageurl)
                .apply(requestOptions)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {


                        if (resource != null) {


                            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                            View dialogLayout = inflater.inflate(R.layout.image_dialog_layout, null);
                            final ImageView dialogImage = dialogLayout.findViewById(R.id.dialogImage);
                            final ImageView btn_ok = dialogLayout.findViewById(R.id.btn_ok);

                            final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            alertDialog = builder.create();
                            alertDialog.setView(dialogLayout);
                            alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);


                            btn_ok.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    alertDialog.dismiss();
                                }
                            });


                            alertDialog.show();
                            dialogImage.setImageBitmap(resource);

                            ////////////////////////////////////////////////////////

                        } else {
                            alertDialog.dismiss();
                        }


                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                    }
                });


    }


    public void WalletType(final Activity context, final ApiCallBack mApiCallBack) {
        try {
            String LoginResponse = getLoginPref(context);
            LoginResponse LoginDataResponse = new Gson().fromJson(LoginResponse, LoginResponse.class);
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<WalletTypeResponse> call = git.GetWalletType(new BalanceRequest("", LoginDataResponse.getData().getSlabID(),
                    LoginDataResponse.getData().getUserID() + "", LoginDataResponse.getData().getLoginTypeID() + "",
                    ApplicationConstant.INSTANCE.APP_ID,
                    getIMEI(context),
                    "", BuildConfig.VERSION_NAME, getSerialNo(context), LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));
            call.enqueue(new Callback<WalletTypeResponse>() {

                @Override
                public void onResponse(Call<WalletTypeResponse> call, retrofit2.Response<WalletTypeResponse> response) {
                    if (response.body() != null && response.body().getStatuscode() != null) {
                        if (response.body().getStatuscode() == 1) {
                            if (response.body().getWalletTypes() != null && response.body().getWalletTypes().size() > 0) {

                                if (mApiCallBack != null) {

                                    mApiCallBack.onSucess(response.body());
                                }

                            }


                            setWalletType(context, new Gson().toJson(response.body()));
                        } else if (response.body().getStatuscode() == -1) {
                            if (response.body().getVersionValid() != null && response.body().getVersionValid() == false) {
                                if (!(context instanceof MainActivity)) {
                                    versionDialog(context);
                                }
                            } else {
                                if (!(context instanceof MainActivity)) {
                                    Error(context, response.body().getMsg() + "");
                                }
                            }
                        }

                    }
                }

                @Override
                public void onFailure(Call<WalletTypeResponse> call, Throwable t) {
                    if (!(context instanceof MainActivity)) {

                        try {
                            if (t.getMessage() != null && !t.getMessage().isEmpty()) {

                                if (t.getMessage().contains("No address associated with hostname")) {
                                   /* NetworkError(context, context.getResources().getString(R.string.err_msg_network_title),
                                            context.getResources().getString(R.string.err_msg_network));*/
                                } else {
                                    Error(context, t.getMessage());

                                }

                            } else {
                                Error(context, context.getResources().getString(R.string.some_thing_error));

                            }
                        } catch (IllegalStateException ise) {
                            Error(context, ise.getMessage());

                        }
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void getCompanyProfile(final Activity context, final CustomLoader loader, ApiCallBack apiCallBack) {
        try {
            String LoginResponse = getLoginPref(context);
            LoginResponse LoginDataResponse = new Gson().fromJson(LoginResponse, LoginResponse.class);

            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);

            Call<CompanyProfileResponse> call = git.getCompanyProfile(new BalanceRequest(LoginDataResponse.getData().getUserID() + "", LoginDataResponse.getData().getLoginTypeID() + "",
                    ApplicationConstant.INSTANCE.APP_ID,
                    getIMEI(context),
                    "", BuildConfig.VERSION_NAME, getSerialNo(context), LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));
            call.enqueue(new Callback<CompanyProfileResponse>() {
                @Override
                public void onResponse(Call<CompanyProfileResponse> call, final retrofit2.Response<CompanyProfileResponse> response) {

                    try {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        if (response.body() != null && response.body().getStatuscode() != null) {

                            if (response.body().getStatuscode() == 1) {
                                setCompanyProfileDetails(context, new Gson().toJson(response.body()));
                                if (apiCallBack != null) {
                                    apiCallBack.onSucess(response.body());
                                }
                            } else if (response.body().getStatuscode() == 2) {
                                //   openOTPDialog(context,loader,password);
                            } else if (response.body().getStatuscode() == -1) {
                                if (response.body().getVersionValid() != null && response.body().getVersionValid() == false) {
                                    versionDialog(context);
                                } else {
                                    Error(context, response.body().getMsg() + "");
                                }
                            }
                        }
                    } catch (Exception e) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                    }

                }

                @Override
                public void onFailure(Call<CompanyProfileResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            loader.dismiss();
                    }
                    if (t.getMessage().contains("No address associated with hostname")) {
                        Error(context, context.getResources().getString(R.string.network_error));
                    } else {
                        Error(context, t.getMessage());
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setCompanyProfileDetails(Context context, String companyProfile) {
        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(ApplicationConstant.INSTANCE.companyPref, companyProfile);
        editor.apply();
        ActivityActivityMessage activityActivityMessage =
                new ActivityActivityMessage("companyProfile", "");
        GlobalBus.getBus().post(activityActivityMessage);
    }

    public void GetCompanyProfile(final Activity context, final ApiCallBack mApiCallBack) {
        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            String LoginResponse = getLoginPref(context);
            LoginResponse LoginDataResponse = new Gson().fromJson(LoginResponse, LoginResponse.class);
            Call<AppUserListResponse> call = git.GetCompanyProfile(new BasicRequest(
                    LoginDataResponse.getData().getUserID(), LoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID,
                    getIMEI(context),
                    "", BuildConfig.VERSION_NAME, getSerialNo(context), LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));
            call.enqueue(new Callback<AppUserListResponse>() {

                @Override
                public void onResponse(Call<AppUserListResponse> call, retrofit2.Response<AppUserListResponse> response) {

                    AppUserListResponse apiData = response.body();
                    if (apiData != null) {
                        if (apiData.getStatuscode() == 1) {
                            setCompanyProfile(context, new Gson().toJson(apiData));
                            if (mApiCallBack != null) {
                                mApiCallBack.onSucess(apiData);
                            }

//
                        } else if (apiData.getStatuscode() == -1) {
                            if (apiData.getVersionValid() == false) {
                                versionDialog(context);
                            } else {
                                Error(context, apiData.getMsg() + "");
                            }
                        }

                    }
                }

                @Override
                public void onFailure(Call<AppUserListResponse> call, Throwable t) {

                    Error(context, context.getResources().getString(R.string.err_something_went_wrong) + "");
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void FetchBillApi(final Activity context, String customerNo, String oid, String accountNum, String o1, String o2, String o3,
                             String o4, String geoCode, String amount, final CustomLoader loader, View submitBtn,
                             LoginResponse LoginDataResponse,
                             final ApiCallBackTwoMethod mApiCallBack) {
        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<FetchBillResponse> call = git.FetchBill(new FetchBillRequest(customerNo, oid, accountNum, o1, o2, o3,
                    o4, geoCode, amount, LoginDataResponse.getData().getOutletID(),
                    LoginDataResponse.getData().getUserID(), LoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID, getIMEI(context), "", BuildConfig.VERSION_NAME,
                    getSerialNo(context), LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));
            call.enqueue(new Callback<FetchBillResponse>() {

                @Override
                public void onResponse(Call<FetchBillResponse> call, retrofit2.Response<FetchBillResponse> response) {

                    try {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        if (response.isSuccessful()) {

                            FetchBillResponse apiData = response.body();
                            if (apiData != null) {
                                if (apiData.getStatuscode() == 1) {
                                    if (apiData.getbBPSResponse() != null && apiData.getbBPSResponse().getStatuscode() == 1) {
                                        if (mApiCallBack != null) {
                                            mApiCallBack.onSucess(apiData);
                                        }

                                    } else if (apiData.getbBPSResponse() != null && apiData.getbBPSResponse().getStatuscode() != 1) {
                                        if (mApiCallBack != null) {
                                            mApiCallBack.onError(apiData);
                                        }
                                    }
                                } else if (apiData.getStatuscode() == -1) {
                                    if (apiData.getVersionValid() == false) {
                                        if (mApiCallBack != null) {
                                            mApiCallBack.onError(apiData.getMsg());
                                        }
                                        versionDialog(context);
                                    } else {
                                        if (mApiCallBack != null) {
                                            if (apiData.getMsg() != null) {
                                                mApiCallBack.onError(apiData.getMsg());
                                                Error(context, apiData.getMsg() + "");
                                            } else if (apiData.getbBPSResponse() != null && apiData.getbBPSResponse().getErrorMsg() != null) {
                                                mApiCallBack.onError(apiData.getbBPSResponse().getErrorMsg());
                                                Error(context, apiData.getbBPSResponse().getErrorMsg() + "");
                                            } else {
                                                mApiCallBack.onError(context.getResources().getString(R.string.some_thing_error));
                                                Error(context, context.getResources().getString(R.string.some_thing_error));
                                            }

                                        }
                                    }

                                } else {
                                    if (mApiCallBack != null) {
                                        if (apiData.getMsg() != null) {
                                            mApiCallBack.onError(apiData.getMsg());
                                            Error(context, apiData.getMsg() + "");
                                        } else if (apiData.getbBPSResponse() != null && apiData.getbBPSResponse().getErrorMsg() != null) {
                                            mApiCallBack.onError(apiData.getbBPSResponse().getErrorMsg());
                                            Error(context, apiData.getbBPSResponse().getErrorMsg() + "");
                                        } else {
                                            mApiCallBack.onError(context.getResources().getString(R.string.some_thing_error));
                                            Error(context, context.getResources().getString(R.string.some_thing_error));
                                        }

                                    }

                                }

                            } else {
                                if (mApiCallBack != null) {
                                    mApiCallBack.onError(context.getResources().getString(R.string.some_thing_error));
                                }
                                Error(context, context.getResources().getString(R.string.some_thing_error));
                            }
                        } else {
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(response.message() + "");
                            }
                            apiErrorHandle(context, response.code(), response.message());
                        }
                    } catch (Exception e) {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        Error(context, e.getMessage() + "");
                    }

                }

                @Override
                public void onFailure(Call<FetchBillResponse> call, Throwable t) {

                    if (mApiCallBack != null) {
                        mApiCallBack.onError(t.getMessage() + "");
                    }
                    try {
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                        apiFailureError(context, t);

                    } catch (IllegalStateException ise) {
                        Error(context, ise.getMessage());
                        if (loader != null) {
                            if (loader.isShowing()) {
                                loader.dismiss();
                            }
                        }
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            if (loader != null) {
                if (loader.isShowing()) {
                    loader.dismiss();
                }
            }
            Error(context, e.getMessage() + "");
        }

    }


  /*  public void FetchBillApi(final Activity context, String oid, String accountNum, String o1, String o2, String o3,
                             String o4, String geoCode, String amount, final String customerNo,final CustomLoader loader, final ApiCallBackTwoMethod mApiCallBack) {
        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            String LoginResponse = getLoginPref(context);
            LoginResponse LoginDataResponse = new Gson().fromJson(LoginResponse, LoginResponse.class);
            Call<FetchBillResponse> call = git.FetchBill(new FetchBillRequest(oid, accountNum, o1, o2, o3,
                    o4, geoCode, amount, LoginDataResponse.getData().getOutletID(),
                    LoginDataResponse.getData().getUserID(), LoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID,
                    getIMEI(context),
                    "", BuildConfig.VERSION_NAME, getSerialNo(context), LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession(),customerNo));
            call.enqueue(new Callback<FetchBillResponse>() {

                @Override
                public void onResponse(Call<FetchBillResponse> call, retrofit2.Response<FetchBillResponse> response) {
                    if (loader != null && loader.isShowing()) {
                        loader.dismiss();
                    }
                    FetchBillResponse apiData = response.body();

                    try {
                        if (apiData != null) {
                            if (apiData.getStatuscode() == 1) {
                                if (apiData.getbBPSResponse() != null) {
                                    if (apiData.getbBPSResponse().getStatuscode() == 1) {
                                        if (apiData.getbBPSResponse().isEnablePayment()) {
                                            if (mApiCallBack != null) {
                                                mApiCallBack.onSucess(apiData);
                                            }
                                        } else {
                                            if (apiData.getbBPSResponse().isShowMsgOnly()) {
                                                if (mApiCallBack != null) {
                                                    if (apiData.getbBPSResponse().getErrmsg() != null && !apiData.getbBPSResponse().getErrmsg().isEmpty()) {
                                                        mApiCallBack.onError(apiData.getbBPSResponse().getErrmsg());

                                                    } else if (apiData.getbBPSResponse().getMsg() != null && !apiData.getbBPSResponse().getMsg().isEmpty()) {
                                                        mApiCallBack.onError(apiData.getbBPSResponse().getMsg() + "");
                                                    } else {
                                                        mApiCallBack.onError(context.getResources().getString(R.string.some_thing_error));
                                                    }

                                                }

                                            }

                                        }
                                    } else if (apiData.getbBPSResponse().getStatuscode() == -1) {
                                        if (apiData.getbBPSResponse().isShowMsgOnly()) {
                                            if (mApiCallBack != null) {
                                                if (apiData.getbBPSResponse().getErrmsg() != null && !apiData.getbBPSResponse().getErrmsg().isEmpty()) {
                                                    mApiCallBack.onError(apiData.getbBPSResponse().getErrmsg());

                                                } else if (apiData.getbBPSResponse().getMsg() != null && !apiData.getbBPSResponse().getMsg().isEmpty()) {
                                                    mApiCallBack.onError(apiData.getbBPSResponse().getMsg() + "");
                                                } else {
                                                    mApiCallBack.onError(context.getResources().getString(R.string.some_thing_error));
                                                }

                                            }

                                        } else {
                                            if (mApiCallBack != null) {

                                                if (apiData.getbBPSResponse().getMsg() != null && !apiData.getbBPSResponse().getMsg().isEmpty()) {
                                                    mApiCallBack.onError(apiData.getbBPSResponse().getMsg() + "");
                                                }
                                            }
                                        }
                                    } else {
                                        if (mApiCallBack != null) {

                                            if (apiData.getbBPSResponse().getMsg() != null && !apiData.getbBPSResponse().getMsg().isEmpty()) {
                                                mApiCallBack.onError(apiData.getbBPSResponse().getMsg() + "");
                                            }
                                        }
                                    }
                                }
                            } else {
                                if (apiData.getVersionValid() == false) {
                                    versionDialog(context);
                                } else {
                                    Error(context, apiData.getMsg() + "");
                                }
                            }
                        }
                    } catch (Exception ex) {
                        if (loader != null && loader.isShowing()) {
                            loader.dismiss();
                        }
                        displayingOnFailuireMessage(context, ex.getMessage());
                    }





                    *//*if (apiData != null) {   // commented on 24-12-2019 accoding to new requirement.....
                        if (apiData.getStatuscode() == 1 && apiData.getbBPSResponse() != null && apiData.getbBPSResponse().getStatuscode() == 1) {
                            if (mApiCallBack != null) {
                                mApiCallBack.onSucess(apiData);
                            }

                        } else if (apiData.getStatuscode() == 1 && apiData.getbBPSResponse() != null && apiData.getbBPSResponse().getStatuscode() != 1) {
                            if (mApiCallBack != null) {
                                mApiCallBack.onError(apiData.getbBPSResponse().getErrmsg());
                            }

//
                        } else if (apiData.getStatuscode() == -1) {
                            if (apiData.getVersionValid() != null && apiData.getVersionValid() == false) {
                                versionDialog(context);
                            } else {
                                if (mApiCallBack != null) {
                                    if (apiData.getbBPSResponse() != null && apiData.getbBPSResponse().getErrmsg() != null) {
                                        mApiCallBack.onError(apiData.getbBPSResponse().getErrmsg());
                                    } else if (apiData.getMsg() != null) {
                                        mApiCallBack.onError(apiData.getMsg());
                                    } else {
                                        mApiCallBack.onError(context.getResources().getString(R.string.some_thing_error));
                                    }

                                }
                                //Error(context, apiData.getMsg() + "");
                            }
                        } else {
                            if (mApiCallBack != null) {
                                if (apiData.getbBPSResponse() != null && apiData.getbBPSResponse().getErrmsg() != null) {
                                    mApiCallBack.onError(apiData.getbBPSResponse().getErrmsg());
                                } else if (apiData.getMsg() != null) {
                                    mApiCallBack.onError(apiData.getMsg());
                                } else {
                                    mApiCallBack.onError(context.getResources().getString(R.string.some_thing_error));
                                }

                            }
                           // Error(context, apiData.getMsg() + "");
                        }

                    } else {
                        if (mApiCallBack != null) {
                            mApiCallBack.onError(context.getResources().getString(R.string.some_thing_error));
                        }
                        //Error(context, context.getResources().getString(R.string.some_thing_error));
                    }*//*
                }

                @Override
                public void onFailure(Call<FetchBillResponse> call, Throwable t) {
                    if (loader != null && loader.isShowing()) {
                        loader.dismiss();
                    }

                    displayingOnFailuireMessage(context, t.getMessage());
                   *//* if (mApiCallBack != null) {
                        mApiCallBack.onError(context.getResources().getString(R.string.err_something_went_wrong));
                    }
                    if(t.getMessage().contains("No address associated with hostname")){
                        Error(context, context.getResources().getString(R.string.network_error));
                    }
                    else {
                        Error(context, t.getMessage());
                    } *//*
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            if (loader != null && loader.isShowing()) {
                loader.dismiss();
            }
        }

    }
*/
  /*  public void FetchBillApi(final Activity context, String oid, String accountNum, String o1, String o2, String o3,
                             String o4, String geoCode, String amount, final CustomLoader loader, View submitBtn, final ApiCallBackTwoMethod mApiCallBack) {
        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            String LoginResponse = getLoginPref(context);
            LoginResponse LoginDataResponse = new Gson().fromJson(LoginResponse, LoginResponse.class);
            Call<FetchBillResponse> call = git.FetchBill(new FetchBillRequest(oid, accountNum, o1, o2, o3,
                    o4, geoCode, amount, LoginDataResponse.getData().getOutletID(),
                    LoginDataResponse.getData().getUserID(), LoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID,
                    getIMEI(context),
                    "", BuildConfig.VERSION_NAME, getSerialNo(context), LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));
            call.enqueue(new Callback<FetchBillResponse>() {

                @Override
                public void onResponse(Call<FetchBillResponse> call, retrofit2.Response<FetchBillResponse> response) {
                    if (loader.isShowing()) {
                        loader.dismiss();
                    }

                    FetchBillResponse apiData = response.body();
                    if (apiData != null) {
                        if (apiData.getStatuscode() == 1) {
                            if (apiData.getbBPSResponse() != null && apiData.getbBPSResponse().getStatuscode() == 1) {
                                if (mApiCallBack != null) {
                                    mApiCallBack.onSucess(apiData);
                                }

                            } else if (apiData.getbBPSResponse() != null && apiData.getbBPSResponse().getStatuscode() != 1) {
                                if (mApiCallBack != null) {
                                    mApiCallBack.onError(apiData);
                                }
                            }
                        } else if (apiData.getStatuscode() == -1) {
                            if (apiData.getVersionValid() == false) {
                                if (mApiCallBack != null) {
                                    mApiCallBack.onError(apiData.getMsg());
                                }
                                versionDialog(context);
                            } else {
                                if (mApiCallBack != null) {
                                    if (apiData.getMsg() != null) {
                                        mApiCallBack.onError(apiData.getMsg());
                                        Error(context, apiData.getMsg() + "");
                                    } else if (apiData.getbBPSResponse() != null && apiData.getbBPSResponse().getErrorMsg() != null) {
                                        mApiCallBack.onError(apiData.getbBPSResponse().getErrorMsg());
                                        Error(context, apiData.getbBPSResponse().getErrorMsg() + "");
                                    } else {
                                        mApiCallBack.onError(context.getResources().getString(R.string.some_thing_error));
                                        Error(context, context.getResources().getString(R.string.some_thing_error));
                                    }

                                }
                            }

                        } else {
                            if (mApiCallBack != null) {
                                if (apiData.getMsg() != null) {
                                    mApiCallBack.onError(apiData.getMsg());
                                    Error(context, apiData.getMsg() + "");
                                } else if (apiData.getbBPSResponse() != null && apiData.getbBPSResponse().getErrorMsg() != null) {
                                    mApiCallBack.onError(apiData.getbBPSResponse().getErrorMsg());
                                    Error(context, apiData.getbBPSResponse().getErrorMsg() + "");
                                } else {
                                    mApiCallBack.onError(context.getResources().getString(R.string.some_thing_error));
                                    Error(context, context.getResources().getString(R.string.some_thing_error));
                                }

                            }

                        }

                    } else {
                        if (mApiCallBack != null) {
                            mApiCallBack.onError(context.getResources().getString(R.string.some_thing_error));
                        }
                        Error(context, context.getResources().getString(R.string.some_thing_error));
                    }


                }

                @Override
                public void onFailure(Call<FetchBillResponse> call, Throwable t) {

                    if (loader.isShowing()) {
                        loader.dismiss();
                    }
                    if (mApiCallBack != null) {
                        mApiCallBack.onError(t.getMessage() + "");
                    }
                    Error(context, t.getMessage() + "");
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }*/


    private void setCommList(Activity context, String toString) {
        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(ApplicationConstant.INSTANCE.commList, toString);
        editor.apply();
    }

    public String getCommList(Activity context) {
        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        return prefs.getString(ApplicationConstant.INSTANCE.commList, "");

    }

    private void setWalletType(Activity context, String toString) {

        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(ApplicationConstant.INSTANCE.walletType, toString);
        editor.apply();
    }

    public String getWalletType(Activity context) {
        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        return prefs.getString(ApplicationConstant.INSTANCE.walletType, "");

    }

    public void setNotificationList(Activity context, String toString) {

        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(ApplicationConstant.INSTANCE.notificationListPref, toString);
        editor.apply();
    }

    public String getNotificationList(Activity context) {
        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        return prefs.getString(ApplicationConstant.INSTANCE.notificationListPref, "");

    }

   /* private String getname(String Opid, Activity context) {
        String operatorName = "";
        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String response = prefs.getString(ApplicationConstant.INSTANCE.numberListPref, "");
        Gson gson = new Gson();
        NumberListResponse NumberList = gson.fromJson(response, NumberListResponse.class);
        for (OperatorList op : NumberList.getData().getOperators()) {
            if (op.getOid().equals(Opid)) {
                operatorName = op.getName();
            }

        }
        return operatorName;
    }*/

    public void GetSender(final Activity context, final String MobileNumber, final CustomLoader loader) {
        try {
            String LoginResponse = getLoginPref(context);
            LoginResponse LoginDataResponse = new Gson().fromJson(LoginResponse, LoginResponse.class);
            /*Log.e("GetSenderReq: ", new Gson().toJson(new GetSenderRequest(new Senderobject(MobileNumber),
                    LoginDataResponse.getData().getUserID() + "", LoginDataResponse.getData().getLoginTypeID() + "",
                    ApplicationConstant.INSTANCE.APP_ID,
                    getIMEI(context),
                    "", BuildConfig.VERSION_NAME, getSerialNo(context), LoginDataResponse.getData().getSessionID(),
                            LoginDataResponse.getData().getSession())));*/
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<CreateSenderResponse> call = git.GetSender(new GetSenderRequest(new Senderobject(MobileNumber),
                    LoginDataResponse.getData().getUserID() + "", LoginDataResponse.getData().getLoginTypeID() + "",
                    ApplicationConstant.INSTANCE.APP_ID,
                    getIMEI(context),
                    "", BuildConfig.VERSION_NAME, getSerialNo(context), LoginDataResponse.getData().getSessionID(),
                    LoginDataResponse.getData().getSession()));

            call.enqueue(new Callback<CreateSenderResponse>() {

                @Override
                public void onResponse(Call<CreateSenderResponse> call, retrofit2.Response<CreateSenderResponse> response) {

                    if (loader.isShowing())
                        loader.dismiss();

                    if (response.body() != null && response.body().getStatuscode() != null) {
                        if (response.body().getStatuscode().equalsIgnoreCase("1")) {
                            if (!response.body().getIsSenderNotExists()) {
                                setSenderNumber(context, MobileNumber, response.body().getSenderName(), response.body().getSenderBalance(), response.body().toString());
                                GetBanklist(context, loader, null);
                                ActivityActivityMessage activityActivityMessage =
                                        new ActivityActivityMessage(MobileNumber + "," + response.body().getSenderName() + "," + response.body().getSenderBalance(), "GetSender");
                                GlobalBus.getBus().post(activityActivityMessage);

                            } else {
                                //Error(context, response.body().getMsg() + "");
                                ActivityActivityMessage activityActivityMessage =
                                        new ActivityActivityMessage("", "CallgetSenderSender");
                                GlobalBus.getBus().post(activityActivityMessage);
                            }

                        } else if (response.body().getStatuscode().equalsIgnoreCase("-1")) {
                            if (response.body().getIsVersionValid() != null && response.body().getIsVersionValid().equalsIgnoreCase("false")) {
                                versionDialog(context);
                            } else {
                                Error(context, response.body().getMsg() + "");
                            }
                        }

                    }
                }

                @Override
                public void onFailure(Call<CreateSenderResponse> call, Throwable t) {

                    if (loader.isShowing())
                        loader.dismiss();
                    Error(context, context.getResources().getString(R.string.err_something_went_wrong) + "");
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void GetBeneficiary(final Activity context, String MobileNumber, final CustomLoader loader) {
        try {
            String LoginResponse = getLoginPref(context);
            LoginResponse LoginDataResponse = new Gson().fromJson(LoginResponse, LoginResponse.class);
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<RechargeReportResponse> call = git.GetBeneficiary(new GetSenderRequest(new Senderobject(MobileNumber),
                    LoginDataResponse.getData().getUserID() + "", LoginDataResponse.getData().getLoginTypeID() + "",
                    ApplicationConstant.INSTANCE.APP_ID,
                    getIMEI(context),
                    "", BuildConfig.VERSION_NAME, getSerialNo(context), LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));

            call.enqueue(new Callback<RechargeReportResponse>() {

                @Override
                public void onResponse(Call<RechargeReportResponse> call, retrofit2.Response<RechargeReportResponse> response) {

                    if (loader.isShowing())
                        loader.dismiss();

                    if (response.body() != null && response.body().getStatuscode() != null) {
                        if (response.body().getStatuscode().equalsIgnoreCase("1")) {
                            setBeneficiaryList(context, new Gson().toJson(response.body()).toString());
                            Intent i = new Intent(context, BeneficiaryListScreen.class);
                            context.startActivity(i);
                        } else if (response.body().getStatuscode().equalsIgnoreCase("-1")) {
                            if (response.body().getIsVersionValid() != null && response.body().getIsVersionValid().equalsIgnoreCase("false")) {
                                versionDialog(context);
                            } else {
                                Error(context, response.body().getMsg() + "");
                            }
                        }

                    }
                }

                @Override
                public void onFailure(Call<RechargeReportResponse> call, Throwable t) {

                    if (loader.isShowing())
                        loader.dismiss();
                    Error(context, context.getResources().getString(R.string.err_something_went_wrong) + "");
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void GetChargedAmount(final Activity context, final String Amount, final String beneID, final String mobileNo, final String ifsc, final String accountNo, final String channel, final String bank, final String beneName, final CustomLoader loader, final Activity activity) {
        try {
            String LoginResponse = getLoginPref(context);
            LoginResponse LoginDataResponse = new Gson().fromJson(LoginResponse, LoginResponse.class);
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<RechargeReportResponse> call = git.GetChargedAmount(new GetChargedAmountRequeat(Amount,
                    LoginDataResponse.getData().getUserID() + "", LoginDataResponse.getData().getLoginTypeID() + "",
                    ApplicationConstant.INSTANCE.APP_ID,
                    getIMEI(context),
                    "", BuildConfig.VERSION_NAME, getSerialNo(context), LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession(), LoginDataResponse.getData().getSession()));

            call.enqueue(new Callback<RechargeReportResponse>() {

                @Override
                public void onResponse(Call<RechargeReportResponse> call, retrofit2.Response<RechargeReportResponse> response) {

                    if (loader.isShowing())
                        loader.dismiss();

                    if (response.body() != null && response.body().getStatuscode() != null) {
                        if (response.body().getStatuscode().equalsIgnoreCase("1")) {
                            double amount = Double.parseDouble(Amount);
                            double charged = Double.parseDouble(response.body().getChargedAmount());
                            double totalAmount = amount + charged;
                            ActivityActivityMessage activityActivityMessage = new ActivityActivityMessage(charged + "," + totalAmount, "SendMoney");
                            GlobalBus.getBus().post(activityActivityMessage);
//                            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
//
//
//                            LayoutInflater inflater = activity.getLayoutInflater();
//                            View view = inflater.inflate(R.layout.custom_dialog_send, null);
//                            AppCompatTextView message = (AppCompatTextView) view.findViewById(R.id.message);
//                            message.setText("You are going to transfer \u20B9 " + Amount + "\nyour Charged Amount is  \u20B9 " + response.body().getChargedAmount() + "\nNow total Transfer Amount is \u20B9 " + totalAmount);
//                            final Button okButton = (Button) view.findViewById(R.id.okButton);
//                            final Button cancelButton = (Button) view.findViewById(R.id.cancelButton);
//                            okButton.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//
//                                   // GetDMTReceipt(context,"S190604122026309F91E","All",loader);
//
//
//                                }
//                            });
//                            cancelButton.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    alertDialog.dismiss();
//                                }
//                            });
//                            dialogBuilder.setView(view);
//                            dialogBuilder.setCancelable(false);
//                            alertDialog = dialogBuilder.create();
//                            alertDialog.show();

                        } else if (response.body().getStatuscode().equalsIgnoreCase("-1")) {
                            if (response.body().getIsVersionValid() != null && response.body().getIsVersionValid().equalsIgnoreCase("false")) {
                                versionDialog(context);
                            } else {
                                Error(context, response.body().getMsg() + "");
                            }
                        }

                    }
                }

                @Override
                public void onFailure(Call<RechargeReportResponse> call, Throwable t) {

                    if (loader.isShowing())
                        loader.dismiss();
                    Error(context, context.getResources().getString(R.string.err_something_went_wrong) + "");
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void SendMoney(final Activity context, String securityKey, String beneID, String mobileNo, String ifsc, String accountNo, String amount, String channel, String bank, String beneName, final CustomLoader loader, final Activity activity, final TextView submitButton) {
        try {
            submitButton.setEnabled(false);
            String LoginResponse = getLoginPref(context);
            LoginResponse LoginDataResponse = new Gson().fromJson(LoginResponse, LoginResponse.class);
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<RechargeReportResponse> call = git.SendMoney(new SendMoneyRequest(new RequestSendMoney(beneID, mobileNo, ifsc, accountNo, amount, channel, bank, beneName), securityKey,
                    LoginDataResponse.getData().getUserID() + "", LoginDataResponse.getData().getLoginTypeID() + "",
                    ApplicationConstant.INSTANCE.APP_ID,
                    getIMEI(context),
                    "", BuildConfig.VERSION_NAME, getSerialNo(context), LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));

            call.enqueue(new Callback<RechargeReportResponse>() {

                @Override
                public void onResponse(Call<RechargeReportResponse> call, retrofit2.Response<RechargeReportResponse> response) {

                    if (loader.isShowing())
                        loader.dismiss();
                    if (response.body() != null && response.body().getStatuscode() != null) {
                        submitButton.setEnabled(true);
                        if (response.body().getStatuscode().equalsIgnoreCase("2")) {
                            // Successfulok(context, response.body().getMsg(), activity);
                            if (response.body().getGroupID() != null && !response.body().getGroupID().isEmpty()) {
                                GetDMTReceipt(activity, response.body().getGroupID(), "All", loader);

                            } else {
                                Successfulok(response.body().getMsg(), activity);
                            }

                        } else if (response.body().getStatuscode().equalsIgnoreCase("-1")) {
                            if (response.body().getIsVersionValid() != null && response.body().getIsVersionValid().equalsIgnoreCase("false")) {
                                versionDialog(context);
                            } else {
                                Error(context, response.body().getMsg() + "");
                            }
                        } else if (response.body().getStatuscode().equalsIgnoreCase("3")) {
                            Error(context, response.body().getMsg() + "");
                        }

                    }
                }

                @Override
                public void onFailure(Call<RechargeReportResponse> call, Throwable t) {

                    submitButton.setEnabled(true);
                    if (loader.isShowing())
                        loader.dismiss();
                    try {
                        if (t.getMessage() != null && !t.getMessage().isEmpty()) {

                            if (t.getMessage().contains("No address associated with hostname")) {
                                NetworkError(context, context.getResources().getString(R.string.err_msg_network_title),
                                        context.getResources().getString(R.string.err_msg_network));
                            } else {
                                Error(context, t.getMessage());

                            }

                        } else {
                            Error(context, context.getResources().getString(R.string.some_thing_error));

                        }
                    } catch (IllegalStateException ise) {
                        Error(context, ise.getMessage());

                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void GetDMTReceipt(final Activity context, final String GroupID, final String flag, final CustomLoader loader) {
        try {
            String LoginResponse = getLoginPref(context);
            LoginResponse LoginDataResponse = new Gson().fromJson(LoginResponse, LoginResponse.class);
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<DMTReceiptResponse> call = git.GetDMTReceipt(new GetDMTReceiptRequest(GroupID,
                    LoginDataResponse.getData().getUserID() + "", LoginDataResponse.getData().getLoginTypeID() + "", ApplicationConstant.INSTANCE.APP_ID,
                    getIMEI(context),
                    "", BuildConfig.VERSION_NAME, getSerialNo(context), LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));

            call.enqueue(new Callback<DMTReceiptResponse>() {

                @Override
                public void onResponse(Call<DMTReceiptResponse> call, retrofit2.Response<DMTReceiptResponse> response) {

                    if (loader.isShowing())
                        loader.dismiss();

                    if (response.body() != null && response.body().getStatuscode() != null) {
                        if (response.body().getStatuscode().equalsIgnoreCase("1")) {
                            if (response.body().getTransactionDetail() != null && response.body().getTransactionDetail().getLists() != null && response.body().getTransactionDetail().getLists().size() > 0) {
                                Intent i = new Intent(context, DMRReciept.class);
                                i.putExtra("response", new Gson().toJson(response.body()));
                                i.putExtra("flag", flag);
                                context.startActivity(i);

                                if (flag.equalsIgnoreCase("All")) {
                                    context.finish();
                                }
                            } else {
                                Error(context, "Failed!!");
                            }

                            // Successful(context, response.body().getMsg());
                        } else if (response.body().getStatuscode().equalsIgnoreCase("-1")) {
                            if (response.body().getIsVersionValid() != null && response.body().getIsVersionValid().equalsIgnoreCase("false")) {
                                versionDialog(context);
                            } else {
                                Error(context, response.body().getMsg() + "");
                            }
                        } else if (response.body().getStatuscode().equalsIgnoreCase("3")) {
                            Error(context, response.body().getMsg() + "");
                        }

                    }
                }

                @Override
                public void onFailure(Call<DMTReceiptResponse> call, Throwable t) {

                    if (loader != null && loader.isShowing())
                        loader.dismiss();
                    displayingOnFailuireMessage(context, t.getMessage());
                    /*  Error(context, context.getResources().getString(R.string.err_something_went_wrong) + "");*/
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            if (loader != null && loader.isShowing())
                loader.dismiss();
            displayingOnFailuireMessage(context, e.getMessage());
        }

    }

    public void DMTReport(final Activity context, int status, String topRow, boolean isRecent, String apiid, String fromDate, String toDate, String transactionID, String accountNo, String childMobileNo, final CustomLoader loader) {
        try {
            String LoginResponse = getLoginPref(context);
            LoginResponse LoginDataResponse = new Gson().fromJson(LoginResponse, LoginResponse.class);
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<RechargeReportResponse> call = git.DMTReport(new DmrRequest(topRow, status, apiid, fromDate, toDate, transactionID, accountNo, childMobileNo, false,
                    LoginDataResponse.getData().getUserID() + "", LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession(),
                    ApplicationConstant.INSTANCE.APP_ID,
                    getIMEI(context),
                    "", BuildConfig.VERSION_NAME, getSerialNo(context), LoginDataResponse.getData().getLoginTypeID() + "", isRecent));

            call.enqueue(new Callback<RechargeReportResponse>() {

                @Override
                public void onResponse(Call<RechargeReportResponse> call, retrofit2.Response<RechargeReportResponse> response) {
                    if (loader.isShowing())
                        loader.dismiss();

                    if (response.body() != null && response.body().getStatuscode() != null) {
                        if (response.body().getStatuscode().equalsIgnoreCase("1")) {
                            ActivityActivityMessage activityActivityMessage =
                                    new ActivityActivityMessage("DMRTransaction", new Gson().toJson(response.body()));
                            GlobalBus.getBus().post(activityActivityMessage);
                        } else if (response.body().getStatuscode().equalsIgnoreCase("-1")) {
                            if (response.body().getIsVersionValid() != null && response.body().getIsVersionValid().equalsIgnoreCase("false")) {
                                versionDialog(context);
                            } else {
                                Error(context, response.body().getMsg() + "");
                            }
                        } else if (response.body().getStatuscode().equalsIgnoreCase("3")) {
                            Error(context, response.body().getMsg() + "");
                        }

                    }
                }

                @Override
                public void onFailure(Call<RechargeReportResponse> call, Throwable t) {

                    if (loader.isShowing())
                        loader.dismiss();
                    try {
                        if (t.getMessage() != null && !t.getMessage().isEmpty()) {

                            if (t.getMessage().contains("No address associated with hostname")) {
                                NetworkError(context, context.getResources().getString(R.string.err_msg_network_title),
                                        context.getResources().getString(R.string.err_msg_network));
                            } else {
                                Error(context, t.getMessage());

                            }

                        } else {
                            Error(context, context.getResources().getString(R.string.some_thing_error));

                        }
                    } catch (IllegalStateException ise) {
                        Error(context, ise.getMessage());

                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void CreateSender(final Activity context, final String MobileNumber, String name, String lastName, String pincode, String address, final String otp, String dob, final CustomLoader loader) {
        try {
            String LoginResponse = getLoginPref(context);
            LoginResponse LoginDataResponse = new Gson().fromJson(LoginResponse, LoginResponse.class);
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<RechargeReportResponse> call = git.CreateSender(new GetSenderRequest(new Senderobject(MobileNumber, name, lastName, pincode, address, otp, dob),
                    LoginDataResponse.getData().getUserID() + "", LoginDataResponse.getData().getLoginTypeID() + "",
                    ApplicationConstant.INSTANCE.APP_ID,
                    getIMEI(context),
                    "", BuildConfig.VERSION_NAME, getSerialNo(context), LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));

            call.enqueue(new Callback<RechargeReportResponse>() {

                @Override
                public void onResponse(Call<RechargeReportResponse> call, retrofit2.Response<RechargeReportResponse> response) {
                    if (loader.isShowing())
                        loader.dismiss();

                    if (response.body() != null && response.body().getStatuscode() != null) {
                        if (response.body().getStatuscode().equalsIgnoreCase("1")) {
                            //  Successful(context, response.body().getMsg() + "");
                            openOTPDialog(context, loader, "verifySender", "", MobileNumber);
                        } else if (response.body().getStatuscode().equalsIgnoreCase("-1")) {
                            if (response.body().getIsVersionValid() != null && response.body().getIsVersionValid().equalsIgnoreCase("false")) {
                                versionDialog(context);
                            } else {
                                Error(context, response.body().getMsg() + "");
                            }
                        }

                    }
                }

                @Override
                public void onFailure(Call<RechargeReportResponse> call, Throwable t) {

                    if (loader.isShowing())
                        loader.dismiss();
                    Error(context, context.getResources().getString(R.string.err_something_went_wrong) + "");
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void Deletebeneficiary(final Activity context, final String MobileNumber, String beneID, final CustomLoader loader) {
        try {
            String LoginResponse = getLoginPref(context);
            LoginResponse LoginDataResponse = new Gson().fromJson(LoginResponse, LoginResponse.class);
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<RechargeReportResponse> call = git.DeleteBeneficiary(new GetSenderRequest(new Senderobject(MobileNumber), new BeneDetail(beneID),
                    LoginDataResponse.getData().getUserID() + "", LoginDataResponse.getData().getLoginTypeID() + "",
                    ApplicationConstant.INSTANCE.APP_ID,
                    getIMEI(context),
                    "", BuildConfig.VERSION_NAME, getSerialNo(context), LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));

            call.enqueue(new Callback<RechargeReportResponse>() {

                @Override
                public void onResponse(Call<RechargeReportResponse> call, retrofit2.Response<RechargeReportResponse> response) {
                    if (loader.isShowing())
                        loader.dismiss();

                    if (response.body() != null && response.body().getStatuscode() != null) {
                        if (response.body().getStatuscode().equalsIgnoreCase("1")) {
                            //  Successful(context, response.body().getMsg() + "");
                            openOTPDialog(context, loader, "verifySender", "", MobileNumber);
                        } else if (response.body().getStatuscode().equalsIgnoreCase("-1")) {
                            if (response.body().getIsVersionValid() != null && response.body().getIsVersionValid().equalsIgnoreCase("false")) {
                                versionDialog(context);
                            } else {
                                Error(context, response.body().getMsg() + "");
                            }
                        }

                    }
                }

                @Override
                public void onFailure(Call<RechargeReportResponse> call, Throwable t) {

                    if (loader.isShowing())
                        loader.dismiss();
                    Error(context, context.getResources().getString(R.string.err_something_went_wrong) + "");
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void VerifySender(final Activity context, final String MobileNumber, String otp, final CustomLoader loader, final Dialog dialog) {
        try {
            String LoginResponse = getLoginPref(context);
            LoginResponse LoginDataResponse = new Gson().fromJson(LoginResponse, LoginResponse.class);
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<RechargeReportResponse> call = git.VerifySender(new GetSenderRequest(new Senderobject(MobileNumber, otp),
                    LoginDataResponse.getData().getUserID() + "", LoginDataResponse.getData().getLoginTypeID() + "",
                    ApplicationConstant.INSTANCE.APP_ID,
                    getIMEI(context),
                    "", BuildConfig.VERSION_NAME, getSerialNo(context), LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));

            call.enqueue(new Callback<RechargeReportResponse>() {

                @Override
                public void onResponse(Call<RechargeReportResponse> call, retrofit2.Response<RechargeReportResponse> response) {
                    if (loader.isShowing())
                        loader.dismiss();

                    if (response.body() != null && response.body().getStatuscode() != null) {
                        if (response.body().getStatuscode().equalsIgnoreCase("1")) {
                            dialog.dismiss();
                            Successful(context, response.body().getMsg() + "");
                            GetSender(context, MobileNumber, loader);
                        } else if (response.body().getStatuscode().equalsIgnoreCase("-1")) {
                            if (response.body().getIsVersionValid() != null && response.body().getIsVersionValid().equalsIgnoreCase("false")) {
                                versionDialog(context);
                            } else {
                                Error(context, response.body().getMsg() + "");
                            }
                        }

                    }
                }

                @Override
                public void onFailure(Call<RechargeReportResponse> call, Throwable t) {

                    if (loader.isShowing())
                        loader.dismiss();
                    Error(context, context.getResources().getString(R.string.err_something_went_wrong) + "");
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void AddBeneficiary(final Activity context, String SenderNO, String BeneMobileNO, String beniName, String ifsc, String accountNo, String bankName, final String bankId, final CustomLoader loader, final Activity activity) {
        try {
            String LoginResponse = getLoginPref(context);
            LoginResponse LoginDataResponse = new Gson().fromJson(LoginResponse, LoginResponse.class);
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<RechargeReportResponse> call = git.AddBeneficiary(new GetSenderRequest(new Senderobject(SenderNO), new BeneDetail(BeneMobileNO, beniName, ifsc, accountNo, bankName, bankId),
                    LoginDataResponse.getData().getUserID() + "", LoginDataResponse.getData().getLoginTypeID() + "",
                    ApplicationConstant.INSTANCE.APP_ID,
                    getIMEI(context),
                    "", BuildConfig.VERSION_NAME, getSerialNo(context), LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));

            call.enqueue(new Callback<RechargeReportResponse>() {

                @Override
                public void onResponse(Call<RechargeReportResponse> call, retrofit2.Response<RechargeReportResponse> response) {
                    if (loader.isShowing())
                        loader.dismiss();

                    if (response.body() != null && response.body().getStatuscode() != null) {
                        if (response.body().getStatuscode().equalsIgnoreCase("1")) {
                            Successfulok(response.body().getMsg() + "", activity);
                            ActivityActivityMessage activityActivityMessage =
                                    new ActivityActivityMessage("", "beneAdded");
                            GlobalBus.getBus().post(activityActivityMessage);

                        } else if (response.body().getStatuscode().equalsIgnoreCase("-1")) {
                            if (response.body().getIsVersionValid() != null && response.body().getIsVersionValid().equalsIgnoreCase("false")) {
                                versionDialog(context);
                            } else {
                                Error(context, response.body().getMsg() + "");
                            }
                        }

                    }
                }

                @Override
                public void onFailure(Call<RechargeReportResponse> call, Throwable t) {

                    if (loader.isShowing())
                        loader.dismiss();
                    Error(context, context.getResources().getString(R.string.err_something_went_wrong) + "");
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void VerifyAccount(final Activity context, String mobileNo, String ifsc, String accountNo, String bankName, final CustomLoader loader) {
        try {
            String LoginResponse = getLoginPref(context);
            LoginResponse LoginDataResponse = new Gson().fromJson(LoginResponse, LoginResponse.class);
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<RechargeReportResponse> call = git.VerifyAccount(new GetSenderRequest(new Senderobject(mobileNo), new BeneDetail(mobileNo, ifsc, accountNo, bankName),
                    LoginDataResponse.getData().getUserID() + "", LoginDataResponse.getData().getLoginTypeID() + "",
                    ApplicationConstant.INSTANCE.APP_ID,
                    getIMEI(context),
                    "", BuildConfig.VERSION_NAME, getSerialNo(context), LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));

            call.enqueue(new Callback<RechargeReportResponse>() {

                @Override
                public void onResponse(Call<RechargeReportResponse> call, retrofit2.Response<RechargeReportResponse> response) {
                    if (loader.isShowing())
                        loader.dismiss();
                    if (response.body() != null && response.body().getStatuscode() != null) {
                        if (response.body().getStatuscode().equalsIgnoreCase("2")) {
                            Successful(context, "Verifications successfully done.");
                            ActivityActivityMessage activityActivityMessage =
                                    new ActivityActivityMessage("AccountVerified", response.body().getBeneName());
                            GlobalBus.getBus().post(activityActivityMessage);
                        } else if (response.body().getStatuscode().equalsIgnoreCase("3") || response.body().getStatuscode().equalsIgnoreCase("-1")) {
                            Error(context, response.body().getMsg() + "");
                        }

                    }
                }

                @Override
                public void onFailure(Call<RechargeReportResponse> call, Throwable t) {
                    if (loader.isShowing())
                        loader.dismiss();
                    Error(context, context.getResources().getString(R.string.err_something_went_wrong) + "");
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void GetLapuRealCommission(final Activity context, String oid, LoginResponse LoginDataResponse, final ApiCallBack mApiCallBack) {
        try {

            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<AppUserListResponse> call = git.GetRealLapuCommission(new LapuRealCommissionRequest(oid,
                    LoginDataResponse.getData().getUserID() + "", LoginDataResponse.getData().getLoginTypeID() + "",
                    ApplicationConstant.INSTANCE.APP_ID,
                    getIMEI(context),
                    "", BuildConfig.VERSION_NAME, getSerialNo(context), LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));

            call.enqueue(new Callback<AppUserListResponse>() {

                @Override
                public void onResponse(Call<AppUserListResponse> call, retrofit2.Response<AppUserListResponse> response) {
                    if (response.body() != null && response.body().getStatuscode() == 1 && response.body().getRealLapuCommissionSlab() != null) {
                        if (mApiCallBack != null) {
                            mApiCallBack.onSucess(response.body().getRealLapuCommissionSlab());
                        }

                    }
                }

                @Override
                public void onFailure(Call<AppUserListResponse> call, Throwable t) {

                    //Error(context, context.getResources().getString(R.string.err_something_went_wrong) + "");
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void GetCalculateLapuRealCommission(final Activity context, String oid, String amount, final CustomLoader loader, final ApiCallBackTwoMethod mApiCallBack) {
        try {
            loader.show();
            String LoginResponse = getLoginPref(context);
            LoginResponse LoginDataResponse = new Gson().fromJson(LoginResponse, LoginResponse.class);
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<AppUserListResponse> call = git.GetCalculatedCommission(new LapuRealCommissionRequest(oid, amount,
                    LoginDataResponse.getData().getUserID() + "", LoginDataResponse.getData().getLoginTypeID() + "",
                    ApplicationConstant.INSTANCE.APP_ID,
                    getIMEI(context),
                    "", BuildConfig.VERSION_NAME, getSerialNo(context), LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));

            call.enqueue(new Callback<AppUserListResponse>() {

                @Override
                public void onResponse(Call<AppUserListResponse> call, retrofit2.Response<AppUserListResponse> response) {
                    loader.dismiss();
                    if (response.body() != null && response.body().getStatuscode() == 1 && response.body().getCommissionDisplay() != null) {
                        if (mApiCallBack != null) {
                            mApiCallBack.onSucess(response.body().getCommissionDisplay());
                        }

                    } else if (response.body() != null && response.body().getStatuscode() != 1) {
                        Error(context, response.body().getMsg() + "");
                        if (mApiCallBack != null) {
                            mApiCallBack.onError("");
                        }
                    } else {
                        if (mApiCallBack != null) {
                            mApiCallBack.onError("");
                        }
                        Error(context, context.getResources().getString(R.string.some_thing_error));
                    }
                }

                @Override
                public void onFailure(Call<AppUserListResponse> call, Throwable t) {
                    loader.dismiss();
                    if (mApiCallBack != null) {
                        mApiCallBack.onError("");
                    }
                    try {
                        if (t.getMessage() != null && !t.getMessage().isEmpty()) {

                            if (t.getMessage().contains("No address associated with hostname")) {
                                NetworkError(context, context.getResources().getString(R.string.err_msg_network_title),
                                        context.getResources().getString(R.string.err_msg_network));
                            } else {
                                Error(context, t.getMessage());

                            }

                        } else {
                            Error(context, context.getResources().getString(R.string.some_thing_error));

                        }
                    } catch (IllegalStateException ise) {
                        Error(context, ise.getMessage());

                    }
                }
            });

        } catch (Exception e) {
            loader.dismiss();
            if (mApiCallBack != null) {
                mApiCallBack.onError("");
            }
            e.printStackTrace();
        }

    }


    public void dialogOk(final Activity context, String title, final String message, final int flag) {
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // "OK" button was clicked
                        if (flag == 4) {
                            ActivityActivityMessage activityActivityMessage =
                                    new ActivityActivityMessage("transferDone", "");
                            GlobalBus.getBus().post(activityActivityMessage);
                        } else if (flag == 3) {
                            // ((RegisterScreen) context).finishMethod();
                        } else if (flag == 8) {

                            ActivityActivityMessage activityActivityMessage = new ActivityActivityMessage("BeneficiaryListScreen", "");
                            GlobalBus.getBus().post(activityActivityMessage);

                        } else if (flag == 6) {
                            //Dispute on DMR
                            ActivityActivityMessage activityActivityMessage =
                                    new ActivityActivityMessage("disputeDMR", "");
                            GlobalBus.getBus().post(activityActivityMessage);

                        } else if (flag == 2) {


                        }

                    }
                })
                .show();
    }

    public void dialogOkSend(final Activity context, String title, final String message, final int flag) {
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // "OK" button was clicked
                        if (flag == 4) {
                            ActivityActivityMessage activityActivityMessage =
                                    new ActivityActivityMessage("transferDone", "");
                            GlobalBus.getBus().post(activityActivityMessage);
                        } else if (flag == 3) {
                            // ((RegisterScreen) context).finishMethod();
                        } else if (flag == 8) {

                            ActivityActivityMessage activityActivityMessage = new ActivityActivityMessage("BeneficiaryListScreen", "");
                            GlobalBus.getBus().post(activityActivityMessage);

                        } else if (flag == 6) {
                            //Dispute on DMR
                            ActivityActivityMessage activityActivityMessage =
                                    new ActivityActivityMessage("disputeDMR", "");
                            GlobalBus.getBus().post(activityActivityMessage);

                        } else if (flag == 2) {


                        }

                    }
                })
                .show();
    }

    public void logout(Activity context) {
        setLoginPref(context, "", "", "");
        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNameLoginPref, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.apply();
        setIsLogin(context, false);
        Intent startIntent = new Intent(context, Splash.class);
        startIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(startIntent);
    }

    public void setLoginPref(Activity context, String password, String mobile, String LoginPref) {
        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNameLoginPref, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(ApplicationConstant.INSTANCE.LoginPref, LoginPref);
        editor.putString(ApplicationConstant.INSTANCE.UPassword, password);
        editor.putString(ApplicationConstant.INSTANCE.UMobile, mobile);

        if (password != null && !password.isEmpty() && LoginPref != null && !LoginPref.isEmpty()) {
            editor.putInt(ApplicationConstant.INSTANCE.IsLoginPref, 1);
        } else {
            editor.putInt(ApplicationConstant.INSTANCE.IsLoginPref, 0);
        }
        editor.apply();

    }

    public String getUserMobile(Activity context) {

        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNameLoginPref, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        return prefs.getString(ApplicationConstant.INSTANCE.UMobile, "");
    }

    public int getIsLogin(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNameLoginPref, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        return prefs.getInt(ApplicationConstant.INSTANCE.IsLoginPref, 0);

    }

    public void setUserDataPref(Context context, String value) {

        try {
            SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNameLoginPref, context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(ApplicationConstant.INSTANCE.UserDetailPref, value);
            editor.apply();
        } catch (Exception e) {
        }


    }

    public String getUserDataPref(Activity context) {
        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNameLoginPref, context.MODE_PRIVATE);
        return prefs.getString(ApplicationConstant.INSTANCE.UserDetailPref, "");

    }

    public String getSessionID(Activity context) {
        SharedPreferences myPrefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNameLoginPref, MODE_PRIVATE);
        String getLoginPref = myPrefs.getString(ApplicationConstant.INSTANCE.LoginPref, "");
        LoginResponse LoginPrefResponse = new Gson().fromJson(getLoginPref, LoginResponse.class);
        return LoginPrefResponse.getData().getSessionID();
    }

    public String getSlabID(Activity context) {
        SharedPreferences myPrefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNameLoginPref, MODE_PRIVATE);
        String getLoginPref = myPrefs.getString(ApplicationConstant.INSTANCE.LoginPref, "");
        LoginResponse LoginPrefResponse = new Gson().fromJson(getLoginPref, LoginResponse.class);
        return LoginPrefResponse.getData().getSlabID();
    }

    public String getLoginPref(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNameLoginPref, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        return prefs.getString(ApplicationConstant.INSTANCE.LoginPref, "");
    }

    public void setServicesPref(Activity context, String Postpaid, String Prepaid, String Landline, String DTH_Datacard, String Electricity,
                                String DMR, String Hotel, String Flight, String InsurancePremium, String Gas, String WaterBills,
                                String DTHConnections) {
        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.servicesPref, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putString(ApplicationConstant.INSTANCE.Services_Postpaid, Postpaid);
        editor.putString(ApplicationConstant.INSTANCE.Services_Prepaid, Prepaid);
        editor.putString(ApplicationConstant.INSTANCE.Services_Landline, Landline);
        editor.putString(ApplicationConstant.INSTANCE.Services_DTH_Datacard, DTH_Datacard);
        editor.putString(ApplicationConstant.INSTANCE.Services_Electricity, Electricity);
        editor.putString(ApplicationConstant.INSTANCE.Services_DMR, DMR);
        editor.putString(ApplicationConstant.INSTANCE.Services_Hotel, Hotel);
        editor.putString(ApplicationConstant.INSTANCE.Services_Flight, Flight);
        editor.putString(ApplicationConstant.INSTANCE.Services_InsurancePremium, InsurancePremium);
        editor.putString(ApplicationConstant.INSTANCE.Services_Gas, Gas);
        editor.putString(ApplicationConstant.INSTANCE.Services_WaterBills, WaterBills);
        editor.putString(ApplicationConstant.INSTANCE.Services_DTHConnections, DTHConnections);
        editor.apply();

    }

    public void setKeyId(Activity context, String key) {
        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(ApplicationConstant.INSTANCE.regKeyIdPref, key);
        editor.apply();
    }

    public void setRegKeyStatus(Activity context, String key) {
        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(ApplicationConstant.INSTANCE.regKeyStatusPref, key);
        editor.apply();
    }

    public String getRegKeyStatus(Activity context) {
        SharedPreferences myPrefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, context.MODE_PRIVATE);
        String key = myPrefs.getString(ApplicationConstant.INSTANCE.regKeyStatusPref, null);
        return key;
    }

    public String getKeyId(Activity context) {
        SharedPreferences myPrefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, context.MODE_PRIVATE);
        String key = myPrefs.getString(ApplicationConstant.INSTANCE.regKeyIdPref, null);
        return key;
    }

    public String getRoleId(Activity context) {
        SharedPreferences myPrefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNameLoginPref, context.MODE_PRIVATE);
        String RoleId = myPrefs.getString(ApplicationConstant.INSTANCE.RoleId, null);

        return RoleId;
    }


    public void setSenderNumber(Activity context, String senderNumber, String senderName, String remainingbal, String senderInfo) {
        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(ApplicationConstant.INSTANCE.senderNumberPref, senderNumber);
        editor.putString(ApplicationConstant.INSTANCE.senderNamePref, senderName);
        editor.putString(ApplicationConstant.INSTANCE.senderBalance, remainingbal);
        editor.putString(ApplicationConstant.INSTANCE.senderInfoPref, senderInfo);
        editor.apply();
    }

    public void setSenderInfo(Activity context, String senderInfo, String senderNumber, boolean flag, final CustomLoader loader) {
        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(ApplicationConstant.INSTANCE.senderInfoPref, senderInfo);
        editor.apply();
    }

    public void setBeneficiaryList(Activity context, String beneficiaryList) {
        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(ApplicationConstant.INSTANCE.beneficiaryListPref, beneficiaryList);
        editor.apply();

    }

    public void setBankDetailList(Activity context, String bankList) {
        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(ApplicationConstant.INSTANCE.bankDetailListPref, bankList);
        editor.apply();
    }

    public String getBusinessModuleID(Activity context) {
        SharedPreferences myPrefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNameLoginPref, context.MODE_PRIVATE);
        String BusinessModuleID = myPrefs.getString(ApplicationConstant.INSTANCE.BusinessModuleID, null);

        return BusinessModuleID;
    }

    public void setSlabList(Activity context, String bankList) {
        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(ApplicationConstant.INSTANCE.slabListPref, bankList);
        editor.apply();
    }


    public boolean isVpnConnected(Context mContext) {
        ConnectivityManager m_ConnectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        List<NetworkInfo> connectedNetworks = new ArrayList<>();

        if (Build.VERSION.SDK_INT >= 21) {
            Network[] networks = m_ConnectivityManager.getAllNetworks();

            for (Network n : networks) {
                NetworkInfo ni = m_ConnectivityManager.getNetworkInfo(n);

                if (ni.isConnectedOrConnecting()) {
                    connectedNetworks.add(ni);
                }
            }
            boolean bHasVPN = false;
            for (NetworkInfo ni : connectedNetworks) {
                bHasVPN |= (ni.getType() == ConnectivityManager.TYPE_VPN);
            }
            return bHasVPN;
        } else {
            List<String> networkList = new ArrayList<>();
            try {
                for (NetworkInterface networkInterface : Collections.list(NetworkInterface.getNetworkInterfaces())) {
                    if (networkInterface.isUp())
                        networkList.add(networkInterface.getName());
                }
            } catch (Exception ex) {

            }
            return networkList.contains("tun0") || networkList.contains("ppp0");
        }


    }


    public boolean isSimAvailable(Activity context) {
        String[] nonActualDeviceArray = context.getResources().getStringArray(R.array.nonActualDeviceSimArray);
        ArrayList<String> nonActualDeviceStringArray = new ArrayList(Arrays.asList(nonActualDeviceArray));

        boolean isSimAvailable = true;
        boolean isActualDevice = true;
        TelephonyManager telMgr = (TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE);

        String networkProviderName = telMgr.getNetworkOperatorName();
        String networkSimOperatorName = telMgr.getSimOperatorName();

        if (networkProviderName != null && !networkProviderName.isEmpty() && nonActualDeviceStringArray.contains(networkProviderName) ||
                networkSimOperatorName != null && !networkSimOperatorName.isEmpty() && nonActualDeviceStringArray.contains(networkSimOperatorName)) {
            isSimAvailable = false;
            isActualDevice = false;
        } else {
            isActualDevice = true;
            int simState = telMgr.getSimState();
            switch (simState) {
                case TelephonyManager.SIM_STATE_ABSENT:
                    isSimAvailable = false;
                    break;
                case TelephonyManager.SIM_STATE_NETWORK_LOCKED:
                    isSimAvailable = false;
                    break;
                case TelephonyManager.SIM_STATE_PIN_REQUIRED:
                    isSimAvailable = false;
                    break;
                case TelephonyManager.SIM_STATE_PUK_REQUIRED:
                    isSimAvailable = false;
                    break;
                case TelephonyManager.SIM_STATE_READY:
                    isSimAvailable = true;
                    break;
                case TelephonyManager.SIM_STATE_UNKNOWN:
                    isSimAvailable = false;
                    break;
            }
        }
        if (!isSimAvailable && isActualDevice) {
            ArrayList<String> networkProvider = getNetworkProvider(context);
            if (networkProvider != null && networkProvider.size() > 0) {
                isSimAvailable = true;
            }
        }
        return isSimAvailable;
    }

    public ArrayList<String> getNetworkProvider(Activity context) {
        ArrayList<String> carrierNameArray = new ArrayList<>();
        int permissionCheck = ContextCompat.checkSelfPermission(context,
                Manifest.permission.READ_PHONE_STATE);

        Activity activity = (Activity) context;
        String networkProvider = "";

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.READ_PHONE_STATE},
                    REQUEST_EXTERNAL_STORAGE);
        } else {
            //TODO
            TelephonyManager telephonyManager = (TelephonyManager)
                    context.getSystemService(Context.TELEPHONY_SERVICE);

            try {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP_MR1) {
                    final SubscriptionManager subscriptionManager;
                    subscriptionManager = SubscriptionManager.from(context);
                    final List<SubscriptionInfo> activeSubscriptionInfoList = subscriptionManager.getActiveSubscriptionInfoList();
                    for (SubscriptionInfo subscriptionInfo : activeSubscriptionInfoList) {
                        final CharSequence carrierName = subscriptionInfo.getCarrierName();
                        carrierNameArray.add(carrierName.toString());
                    }
                } else {
                    networkProvider = telephonyManager.getNetworkOperatorName();
                    if (networkProvider != null && !networkProvider.isEmpty()) {
                        carrierNameArray.add(networkProvider);
                    }
                }
            } catch (Exception e) {

            }
        }


        return carrierNameArray;
    }


    public void versionDialog(final Context mContext) {
        if (alertDialogVersion != null && alertDialogVersion.isShowing()) {
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext); // create an alert box
        builder.setTitle("Alert!!");
        builder.setMessage("New Update Available.");
        builder.setCancelable(false);
        builder.setPositiveButton("OK", null);
        alertDialogVersion = builder.create();
        alertDialogVersion.show();
        alertDialogVersion.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToVersionUpdate(mContext);
            }
        });
    }

    private void goToVersionUpdate(Context mContext) {

        try {
            mContext.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=" + BuildConfig.APPLICATION_ID)));
        } catch (android.content.ActivityNotFoundException anfe) {
            mContext.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=" +
                            BuildConfig.APPLICATION_ID)));
        }
        // finish();
    }

    public void RefundRequest(final Activity context, final String tid, final String transactionID,
                              final String remarkText, final CustomLoader loader, final TextView dispute,
                              String otpRequired, boolean isResend) {
        /*Your Code*/

        String LoginResponse = getLoginPref(context);
        LoginResponse LoginDataResponse = new Gson().fromJson(LoginResponse, LoginResponse.class);
        EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);

        Call<RefundRequestResponse> call = git.RefundRequest(
                new RefundRequestRequest(tid
                        , transactionID
                        , LoginDataResponse.getData().getUserID() + ""
                        , LoginDataResponse.getData().getSessionID() + ""
                        , LoginDataResponse.getData().getSession() + ""
                        , ApplicationConstant.INSTANCE.APP_ID
                        , getIMEI(context)
                        , ""
                        , BuildConfig.VERSION_NAME + ""
                        , getSerialNo(context) + ""
                        , LoginDataResponse.getData().getLoginTypeID() + ""
                        , otpRequired
                        , isResend));
        call.enqueue(new Callback<RefundRequestResponse>() {
            @Override
            public void onResponse(Call<RefundRequestResponse> call, final Response<RefundRequestResponse> response) {

                if (loader != null)
                    if (loader.isShowing())
                        loader.dismiss();

                if (response.body() != null) {

                    if (context instanceof DMRReport) {
                        if (response.body().getOTPRequired() != null && response.body().getOTPRequired()) {
                            openOtpDialog(context, new DialogCallBack() {
                                @Override
                                public void onPositiveClick(String value) {

                                }

                                @Override
                                public void onResetCallback(String value) {

                                }

                                @Override
                                public void onCancelClick() {

                                }
                            });

                            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                            View view = inflater.inflate(R.layout.refund_otp, null);

                            final TextInputLayout otpTextLayout = (TextInputLayout) view.findViewById(R.id.otpTextLayout);
                            final EditText otp = (EditText) view.findViewById(R.id.ed_otp);
                            final Button okButton = (Button) view.findViewById(R.id.okButton);
                            final Button cancelButton = (Button) view.findViewById(R.id.cancelButton);
                            final Button resendOtp = (Button) view.findViewById(R.id.btn_resendOtp);


                            otp.addTextChangedListener(new TextWatcher() {
                                @Override
                                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                                }

                                @Override
                                public void onTextChanged(CharSequence s, int start, int before, int count) {
                                }

                                @Override
                                public void afterTextChanged(Editable s) {

                                    if (s.length() < 6) {
                                        otpTextLayout.setError(context.getString(R.string.err_msg_network_title));
                                        okButton.setEnabled(false);
                                    } else {
                                        otpTextLayout.setErrorEnabled(false);
                                        okButton.setEnabled(true);
                                    }
                                }
                            });
                            final Dialog dialog = new Dialog(context);
                            dialog.setCancelable(false);
                            dialog.setContentView(view);
                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


                            cancelButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                }
                            });

                            resendOtp.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    if (isNetworkAvialable(context)) {

                                        loader.show();
                                        loader.setCancelable(false);
                                        loader.setCanceledOnTouchOutside(false);
                                        boolean isResend = true;
                                        String otpRequired = "";

                                        RefundRequest(context, tid, transactionID, remarkText, loader, dispute, otpRequired, isResend);

                                    }
                                }
                            });

                            okButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (otp.getText() != null && otp.getText().length() == 6) {
                                        otpTextLayout.setErrorEnabled(false);

                                        if (isNetworkAvialable(context)) {

                                            loader.show();
                                            loader.setCancelable(false);
                                            loader.setCanceledOnTouchOutside(false);
                                            boolean isResend = false;
                                            String otpRequired = otp.getText().toString().trim();

                                            RefundRequest(context, tid, transactionID, remarkText, loader, dispute, otpRequired, isResend);
                                        }

                                    } else {
                                        otp.setError("Please enter a valid OTP !!");
                                        otp.requestFocus();
                                    }
                                }
                            });
                            dialog.show();
                        } else {
                            if (response.body().getStatuscode() != null && response.body().getStatuscode() == 1) {

                                if (response.body() != null && response.body().getMsg() != null) {
                                    Successful(context, response.body().getMsg() + "");
                                }
                                dispute.setVisibility(View.GONE);
                            } else if (response.body().getStatuscode() != null && response.body().getStatuscode() == -1) {
                                if (response.body() != null && response.body().getMsg() != null) {
                                    Error(context, response.body().getMsg() + "");
                                }
                            } else {
                                if (response.body() != null && response.body().getMsg() != null) {
                                    Error(context, response.body().getMsg() + "");
                                }
                            }
                        }
                    } else {
                        if (response.body().getStatuscode() != null && response.body().getStatuscode() == 1) {

                            if (response.body() != null && response.body().getMsg() != null) {
                                Successful(context, response.body().getMsg() + "");
                            }
                            dispute.setVisibility(View.GONE);
                        } else if (response.body().getStatuscode() != null && response.body().getStatuscode() == -1) {
                            if (response.body() != null && response.body().getMsg() != null) {
                                Error(context, response.body().getMsg() + "");
                            }
                        } else {
                            if (response.body() != null && response.body().getMsg() != null) {
                                Error(context, response.body().getMsg() + "");
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<RefundRequestResponse> call, Throwable t) {

                if (loader != null)
                    if (loader.isShowing())
                        loader.dismiss();
                Error(context, context.getResources().getString(R.string.err_something_went_wrong) + "");
            }
        });
    }

    public void Dispute(final Activity context, final String transactionId, final String tid, final CustomLoader loader, final ApiCallBack mApiCallBack) {
        try {
            loader.show();
            String LoginResponse = getLoginPref(context);
            LoginResponse LoginDataResponse = new Gson().fromJson(LoginResponse, LoginResponse.class);
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<RefundRequestResponse> call = git.RefundRequest(new RefundRequestRequest(tid
                    , transactionId
                    , LoginDataResponse.getData().getUserID() + ""
                    , LoginDataResponse.getData().getSessionID() + ""
                    , LoginDataResponse.getData().getSession() + ""
                    , ApplicationConstant.INSTANCE.APP_ID
                    , getIMEI(context)
                    , ""
                    , BuildConfig.VERSION_NAME + ""
                    , getSerialNo(context) + ""
                    , LoginDataResponse.getData().getLoginTypeID() + ""
                    , ""
                    , false));

            call.enqueue(new Callback<RefundRequestResponse>() {

                @Override
                public void onResponse(Call<RefundRequestResponse> call, retrofit2.Response<RefundRequestResponse> response) {

                    if (loader.isShowing())
                        loader.dismiss();

                    if (response.body() != null && response.body().getStatuscode() != null) {
                        if (response.body().getStatuscode() == 1) {

                            if (mApiCallBack != null) {
                                mApiCallBack.onSucess(response.body());
                            }
                            Successful(context, response.body().getMsg() + "");


                        } else if (response.body().getStatuscode() == -1) {
                            if (response.body().getVersionValid() != null && response.body().getVersionValid() == false) {
                                versionDialog(context);
                            } else {
                                Error(context, response.body().getMsg() + "");
                            }
                        }

                    }
                }

                @Override
                public void onFailure(Call<RefundRequestResponse> call, Throwable t) {

                    if (loader.isShowing())
                        loader.dismiss();
                    try {
                        if (t.getMessage() != null && !t.getMessage().isEmpty()) {

                            if (t.getMessage().contains("No address associated with hostname")) {
                                NetworkError(context, context.getResources().getString(R.string.err_msg_network_title),
                                        context.getResources().getString(R.string.err_msg_network));
                            } else {
                                Error(context, t.getMessage());

                            }

                        } else {
                            Error(context, context.getResources().getString(R.string.some_thing_error));

                        }
                    } catch (IllegalStateException ise) {
                        Error(context, ise.getMessage());

                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public String GetOperatortypes(Activity context) {
        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, context.MODE_PRIVATE);
        return prefs.getString(ApplicationConstant.INSTANCE.OpTypePref, "");
    }


    public void setPinRequiredPref(Activity context, boolean value) {
        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNameLoginPref, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(ApplicationConstant.INSTANCE.PinRequiredPref, value);
        editor.apply();

    }

    public boolean getPinRequiredPref(Activity context) {
        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNameLoginPref, context.MODE_PRIVATE);
        return prefs.getBoolean(ApplicationConstant.INSTANCE.PinRequiredPref, false);

    }

    protected void makeLinkClickable(final Activity context, SpannableStringBuilder strBuilder, final URLSpan span) {
        int start = strBuilder.getSpanStart(span);
        int end = strBuilder.getSpanEnd(span);
        int flags = strBuilder.getSpanFlags(span);
        ClickableSpan clickable = new ClickableSpan() {
            public void onClick(View view) {
                try {
                    context.startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse(span.getURL()))
                            .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
                } catch (android.content.ActivityNotFoundException anfe) {

                }
            }
        };
        strBuilder.setSpan(clickable, start, end, flags);
        strBuilder.removeSpan(span);
    }

    public void setTextViewHTML(Activity context, TextView text, String html) {
        CharSequence sequence = Html.fromHtml(html);
        SpannableStringBuilder strBuilder = new SpannableStringBuilder(sequence);
        URLSpan[] urls = strBuilder.getSpans(0, sequence.length(), URLSpan.class);
        for (URLSpan span : urls) {
            makeLinkClickable(context, strBuilder, span);
        }
        text.setText(strBuilder);
        text.setMovementMethod(LinkMovementMethod.getInstance());
    }


    public void rechargeConfiormDialog(Activity context, HashMap<String, IncentiveDetails> incentiveMap, CommissionDisplay mCommissionDisplay, final boolean isReal, final boolean isPinPass, String logo, String number, String operator, String amount, final DialogCallBack mDialogCallBack) {
        if (alertDialogMobile != null && alertDialogMobile.isShowing()) {
            return;
        }
        AlertDialog.Builder dialogBuilder;
        dialogBuilder = new AlertDialog.Builder(context);

        alertDialogMobile = dialogBuilder.create();
        alertDialogMobile.setCancelable(true);

        alertDialogMobile.getWindow().setBackgroundDrawableResource(android.R.color.transparent);


        LayoutInflater inflater = context.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_recharge_confiorm, null);
        alertDialogMobile.setView(dialogView);
        LinearLayout commView = dialogView.findViewById(R.id.commView);
        LinearLayout lapuView = dialogView.findViewById(R.id.lapuView);
        LinearLayout realView = dialogView.findViewById(R.id.realView);
        TextView lapuTitle = dialogView.findViewById(R.id.lapuTitle);
        // TextView lapuAmt = dialogView.findViewById(R.id.lapuAmt);
        TextView realTitle = dialogView.findViewById(R.id.realTitle);
        // TextView realAmt = dialogView.findViewById(R.id.realAmt);
        if (mCommissionDisplay != null) {
            commView.setVisibility(View.VISIBLE);
            if (isReal) {
                lapuView.setVisibility(View.GONE);
                realView.setVisibility(View.VISIBLE);
            } else {
                lapuView.setVisibility(View.VISIBLE);
                realView.setVisibility(View.GONE);
            }

            lapuTitle.setText((mCommissionDisplay.isCommType() ? "Surcharge " : "Commission ") + context.getResources().getString(R.string.rupiya) + " " + formatedAmount(mCommissionDisplay.getCommission() + ""));
            // lapuAmt.setText(context.getResources().getString(R.string.rupiya)+" "+formatedAmount(mCommissionDisplay.getCommission()+""));
            realTitle.setText((mCommissionDisplay.isrCommType() ? "Surcharge " : "Commission ") + context.getResources().getString(R.string.rupiya) + " " + formatedAmount(mCommissionDisplay.getrCommission() + ""));
            // realAmt.setText(context.getResources().getString(R.string.rupiya)+" "+formatedAmount(mCommissionDisplay.getrCommission()+""));
        } else {
            commView.setVisibility(View.GONE);
        }
        AppCompatTextView amountTv = dialogView.findViewById(R.id.amount);
        amountTv.setText(context.getResources().getString(R.string.rupiya) + " " + amount);
        final TextInputLayout til_pinPass = dialogView.findViewById(R.id.til_pinPass);
        final EditText pinPassEt = dialogView.findViewById(R.id.pinPassEt);
        if (isPinPass) {
            til_pinPass.setVisibility(View.VISIBLE);
        } else {
            til_pinPass.setVisibility(View.GONE);
        }
        AppCompatTextView operatorTv = dialogView.findViewById(R.id.operator);
        operatorTv.setText(operator);
        AppCompatTextView numberTv = dialogView.findViewById(R.id.number);
        numberTv.setText(number);
        TextView incentive = dialogView.findViewById(R.id.incentive);
        if (incentiveMap != null && incentiveMap.size() > 0 && incentiveMap.containsKey(amount)) {
            incentive.setVisibility(View.VISIBLE);
            if (incentiveMap.get(amount).isAmtType()) {
                incentive.setText("You are eligible for " + "\u20B9 " + incentiveMap.get(amount).getComm() + " Cash Back");

            } else {
                incentive.setText("You are eligible for " + incentiveMap.get(amount).getComm() + " % Cash Back");

            }
        }
        AppCompatTextView cancelTv = dialogView.findViewById(R.id.cancel);
        AppCompatTextView okTv = dialogView.findViewById(R.id.ok);
        AppCompatImageView logoIv = dialogView.findViewById(R.id.logo);
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);

        Glide.with(context)
                .load(logo)
                .apply(RequestOptions.circleCropTransform())
                .apply(RequestOptions.placeholderOf(R.drawable.circle_logo))
                .into(logoIv);

        okTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPinPass && pinPassEt.getText().toString().isEmpty()) {
                    pinPassEt.setError("Field can't be empty");
                    pinPassEt.requestFocus();
                    return;
                }
                alertDialogMobile.dismiss();
                if (mDialogCallBack != null) {
                    mDialogCallBack.onPositiveClick(pinPassEt.getText().toString());
                }
            }
        });
        cancelTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialogMobile.dismiss();
                if (mDialogCallBack != null) {
                    mDialogCallBack.onCancelClick();
                }
            }
        });

        alertDialogMobile.show();
        /*if (isFullScreen) {
            alertDialogMobile.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }*/
    }


/*
    public void rechargeConfiormDialog(Activity context, CommissionDisplay mCommissionDisplay, final boolean isReal, final boolean isPinPass, String logo, String number, String operator, String amount, final DialogCallBack mDialogCallBack) {
        if (alertDialogMobile != null && alertDialogMobile.isShowing()) {
            return;
        }
        AlertDialog.Builder dialogBuilder;
        dialogBuilder = new AlertDialog.Builder(context);

        alertDialogMobile = dialogBuilder.create();
        alertDialogMobile.setCancelable(true);

        alertDialogMobile.getWindow().setBackgroundDrawableResource(android.R.color.transparent);


        LayoutInflater inflater = context.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_recharge_confiorm, null);
        alertDialogMobile.setView(dialogView);
        LinearLayout commView = dialogView.findViewById(R.id.commView);
        LinearLayout lapuView = dialogView.findViewById(R.id.lapuView);
        LinearLayout realView = dialogView.findViewById(R.id.realView);
        TextView lapuTitle = dialogView.findViewById(R.id.lapuTitle);
        // TextView lapuAmt = dialogView.findViewById(R.id.lapuAmt);
        TextView realTitle = dialogView.findViewById(R.id.realTitle);
        // TextView realAmt = dialogView.findViewById(R.id.realAmt);
        if (mCommissionDisplay != null) {
            commView.setVisibility(View.VISIBLE);
            if (isReal) {
                lapuView.setVisibility(View.GONE);
                realView.setVisibility(View.VISIBLE);
            } else {
                lapuView.setVisibility(View.VISIBLE);
                realView.setVisibility(View.GONE);
            }

            lapuTitle.setText((mCommissionDisplay.isCommType() ? "Surcharge " : "Commission ") + context.getResources().getString(R.string.rupiya) + " " + formatedAmount(mCommissionDisplay.getCommission() + ""));
            // lapuAmt.setText(context.getResources().getString(R.string.rupiya)+" "+formatedAmount(mCommissionDisplay.getCommission()+""));
            realTitle.setText((mCommissionDisplay.isrCommType() ? "Surcharge " : "Commission ") + context.getResources().getString(R.string.rupiya) + " " + formatedAmount(mCommissionDisplay.getrCommission() + ""));
            // realAmt.setText(context.getResources().getString(R.string.rupiya)+" "+formatedAmount(mCommissionDisplay.getrCommission()+""));
        } else {
            commView.setVisibility(View.GONE);
        }
        AppCompatTextView amountTv = dialogView.findViewById(R.id.amount);
        amountTv.setText(context.getResources().getString(R.string.rupiya) + " " + amount);
        final TextInputLayout til_pinPass = dialogView.findViewById(R.id.til_pinPass);
        final EditText pinPassEt = dialogView.findViewById(R.id.pinPassEt);
        if (isPinPass) {
            til_pinPass.setVisibility(View.VISIBLE);
        } else {
            til_pinPass.setVisibility(View.GONE);
        }
        AppCompatTextView operatorTv = dialogView.findViewById(R.id.operator);
        operatorTv.setText(operator);
        AppCompatTextView numberTv = dialogView.findViewById(R.id.number);
        numberTv.setText(number);
        AppCompatTextView cancelTv = dialogView.findViewById(R.id.cancel);
        AppCompatTextView okTv = dialogView.findViewById(R.id.ok);
        AppCompatImageView logoIv = dialogView.findViewById(R.id.logo);
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);

        Glide.with(context)
                .load(logo)
                .apply(RequestOptions.circleCropTransform())
                .apply(RequestOptions.placeholderOf(R.drawable.circle_logo))
                .into(logoIv);

        okTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPinPass && pinPassEt.getText().toString().isEmpty()) {
                    pinPassEt.setError("Field can't be empty");
                    pinPassEt.requestFocus();
                    return;
                }
                alertDialogMobile.dismiss();
                if (mDialogCallBack != null) {
                    mDialogCallBack.onPositiveClick(pinPassEt.getText().toString());
                }
            }
        });
        cancelTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialogMobile.dismiss();
                if (mDialogCallBack != null) {
                    mDialogCallBack.onCancelClick();
                }
            }
        });

        alertDialogMobile.show();
        */
/*if (isFullScreen) {
            alertDialogMobile.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }*//*

    }
*/

    public void DoubleFactorOtp(final Activity context, boolean isDoubleFactor, final String otp, String reffid, final CustomLoader loader, final ApiCallBack mApiCallBack) {
        try {
            String LoginResponse = getLoginPref(context);
            LoginResponse LoginDataResponse = new Gson().fromJson(LoginResponse, LoginResponse.class);
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<DFStatusResponse> call = git.ChangeDFStatus(new DFStatusRequest(isDoubleFactor, otp, reffid,
                    LoginDataResponse.getData().getUserID() + "", LoginDataResponse.getData().getLoginTypeID() + "",
                    ApplicationConstant.INSTANCE.APP_ID,
                    getIMEI(context),
                    "", BuildConfig.VERSION_NAME, getSerialNo(context), LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));

            call.enqueue(new Callback<DFStatusResponse>() {

                @Override
                public void onResponse(Call<DFStatusResponse> call, retrofit2.Response<DFStatusResponse> response) {
                    if (loader.isShowing())
                        loader.dismiss();

                    if (response.body() != null && response.body().getStatuscode() != null) {
                        if (response.body().getStatuscode() == 1) {
                            if (mApiCallBack != null) {
                                mApiCallBack.onSucess(response.body());
                            }
                        } else if (response.body().getStatuscode() == -1) {
                            if (response.body().isVersionValid() == false) {
                                versionDialog(context);
                            } else {
                                Error(context, response.body().getMsg() + "");
                            }
                        }

                    }
                }

                @Override
                public void onFailure(Call<DFStatusResponse> call, Throwable t) {

                    if (loader.isShowing())
                        loader.dismiss();
                    if (t.getMessage().contains("No address associated with hostname")) {
                        Error(context, context.getResources().getString(R.string.network_error));
                    } else {
                        Error(context, t.getMessage());
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public interface ApiCallBackOnBoardingMethod {
        void onSuccess(Object object);
        void onError(Object object);
        void onOnBoarding(Object object);
    }
    public void CallOnboarding(final Activity context,int bioAuthType, boolean isBiometric, FragmentManager fragmentManager, final int aepsId,
                               final String otp, final String otpRefID, final String pidData, final boolean isPan,
                               boolean isFromRecharge, final boolean isOperatorRequired,
                               TextView timerTv, View resendCodeTv, Dialog mDialog, final CustomLoader mProgressDialog,
                               LoginResponse LoginDataResponse,String Lattitude,String Longitude,
                               final ApiCallBackOnBoardingMethod mApiCallBack) {
        CustomAlertDialog customPassDialog = new CustomAlertDialog(context, true);
        try {


            if (isOperatorRequired) {
                NumberListResponse NumberList = new Gson().fromJson(getNumberList(context), NumberListResponse.class);
                if (NumberList != null && NumberList.getData() != null && NumberList.getData().getOperators() != null) {
                    ArrayList<OperatorList> operatorsList = NumberList.getData().getOperators();
                    if (operatorsList != null && operatorsList.size() > 0) {
                        for (OperatorList op : operatorsList) {
                            if (op.isActive() && op.getOpType() == aepsId) { //For Aeps opType is 22
                                callOnBoardOId = op.getOid();
                                break;
                            }

                        }
                    }
                }
            } else {
                callOnBoardOId = aepsId;
            }

            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<OnboardingResponse> call = git.CallOnboarding(new OnboardingRequest(bioAuthType,isBiometric, otp, otpRefID, pidData, callOnBoardOId + "", LoginDataResponse.getData().getOutletID() + "",
                    LoginDataResponse.getData().getUserID() + "", LoginDataResponse.getData().getSessionID() + "", LoginDataResponse.getData().getSession() + "",
                    ApplicationConstant.INSTANCE.APP_ID, getDeviceId(context), getFCMRegKey(context), BuildConfig.VERSION_NAME,
                    getSerialNo(context), LoginDataResponse.getData().getLoginTypeID(),Lattitude,Longitude));

            call.enqueue(new Callback<OnboardingResponse>() {
                @Override
                public void onResponse(Call<OnboardingResponse> call, Response<OnboardingResponse> response) {


                    try {

                        if (mProgressDialog != null && mProgressDialog.isShowing()) {
                            mProgressDialog.dismiss();
                        }
                        if (response.isSuccessful()) {
                            OnboardingResponse mOnboardingResponse = response.body();
                            if (mOnboardingResponse != null) {
                                callOnBoardBioAuthType=mOnboardingResponse.getBioAuthType();
                                if (mOnboardingResponse.getOtpRefID() != null && !mOnboardingResponse.getOtpRefID().isEmpty()) {
                                    onboardingOTPReffId = mOnboardingResponse.getOtpRefID();
                                }
                                if (mOnboardingResponse.getGiurl() != null && !mOnboardingResponse.getGiurl().isEmpty()) {
                                    try {
                                        if (mDialog != null && mDialog.isShowing()) {
                                            mDialog.dismiss();
                                        }
                                        context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(ApplicationConstant.INSTANCE.baseUrl + mOnboardingResponse.getGiurl())));
                                    } catch (android.content.ActivityNotFoundException anfe) {
                                        Intent dialIntent = new Intent(Intent.ACTION_VIEW);
                                        dialIntent.setData(Uri.parse(ApplicationConstant.INSTANCE.baseUrl + mOnboardingResponse.getGiurl()));
                                        context.startActivity(dialIntent);
                                    }
                                } else if (mOnboardingResponse.isRedirectToExternal() && mOnboardingResponse.getExternalURL() != null && !mOnboardingResponse.getExternalURL().isEmpty()) {
                                    try {
                                        if (mDialog != null && mDialog.isShowing()) {
                                            mDialog.dismiss();
                                        }
                                        context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse( /*ApplicationConstant.INSTANCE.baseUrl + */mOnboardingResponse.getExternalURL())));
                                    } catch (android.content.ActivityNotFoundException anfe) {
                                        Intent dialIntent = new Intent(Intent.ACTION_VIEW);
                                        dialIntent.setData(Uri.parse(/*ApplicationConstant.INSTANCE.baseUrl + */mOnboardingResponse.getExternalURL()));
                                        context.startActivity(dialIntent);
                                    }
                                } else if (mOnboardingResponse.isBioMetricRequired()) {
                                    if (mDialog != null && mDialog.isShowing()) {
                                        mDialog.dismiss();
                                    }
                                    AEPSFingerPrintEKycDialogFragment mAEPSFingerPrintEKycDialog =
                                            new AEPSFingerPrintEKycDialogFragment();
                                    if (mOnboardingResponse.getStatuscode() == -1) {
                                        mAEPSFingerPrintEKycDialog.setOnBoardingData(context, mOnboardingResponse.getMsg(), fragmentManager,callOnBoardBioAuthType, callOnBoardOId,
                                                onboardingOTPReffId, mProgressDialog, LoginDataResponse,  mApiCallBack);

                                    } else {
                                        mAEPSFingerPrintEKycDialog.setOnBoardingData(context, "", fragmentManager,  mOnboardingResponse.getBioAuthType(),callOnBoardOId,
                                                onboardingOTPReffId, mProgressDialog, LoginDataResponse, mApiCallBack);
                                    }
                                    mAEPSFingerPrintEKycDialog.show(fragmentManager, "");
                                    isBioMetricDialogShown = true;
                                    //  context.startActivity(new Intent(context, AEPSFingerPrintEKycActivity.class));
                                } else if (mOnboardingResponse.getIsOTPRequired()) {
                                    if (mDialog != null && mDialog.isShowing()) {
                                        Successful(context, "OTP has been resend successfully");
                                       /* if (timerTv != null && resendCodeTv != null) {
                                            setTimer(timerTv, resendCodeTv);
                                        }*/
                                    } else {

                                        openOnBoardingOtpDialog(context, 7, new DialogOTPCallBack() {
                                            @Override
                                            public void onPositiveClick(EditText edMobileOtp, String otpValue, TextView timerTv, View resendCodeTv, Dialog mDialog) {
                                                mProgressDialog.show();
                                                CallOnboarding(context,callOnBoardBioAuthType ,false, fragmentManager, aepsId, otpValue, onboardingOTPReffId, pidData, isPan, isFromRecharge, isOperatorRequired, timerTv, resendCodeTv, mDialog, mProgressDialog,
                                                        LoginDataResponse,Lattitude,Longitude,  mApiCallBack);
                                            }

                                            @Override
                                            public void onResetCallback(EditText edMobileOtp, String otpValue, TextView timerTv, View resendCodeTv, Dialog mDialog) {
                                                mProgressDialog.show();
                                                CallOnboarding(context, callOnBoardBioAuthType,false, fragmentManager, aepsId, "", "0", "", isPan, isFromRecharge, isOperatorRequired, timerTv, resendCodeTv, mDialog, mProgressDialog,
                                                        LoginDataResponse,Lattitude,Longitude,  mApiCallBack);
                                            }

                                        });

                                    }

                                } else {

                                    if (mDialog != null && mDialog.isShowing()) {
                                        mDialog.dismiss();
                                    }


                                    if (!showCallOnBoardingMsgs(context, mOnboardingResponse, customPassDialog)) {
                                        if (mApiCallBack != null) {
                                            mApiCallBack.onError(mOnboardingResponse);
                                        }
                                    } else if (mOnboardingResponse.getStatuscode() == 1) {
                                        if (isFromRecharge) {
                                            if (mApiCallBack != null) {
                                                mApiCallBack.onSuccess(mOnboardingResponse);
                                            }
                                        } else if (isPan) {
                                            if (mOnboardingResponse.getPanid() != null && !mOnboardingResponse.getPanid().isEmpty()) {

                                                setPsaId(context, aepsId + "");
                                                if (mApiCallBack != null) {
                                                    mApiCallBack.onSuccess(mOnboardingResponse);
                                                }

                                            } else {
                                                Error(context, "Pan Id is not found!!");
                                            }
                                        } else {

                                            if(isBioMetricDialogShown && (mOnboardingResponse.getSdkDetail()==null&& mOnboardingResponse.getSdkType()==0)){
                                                CallOnboarding(context, callOnBoardBioAuthType,false, fragmentManager, aepsId, "", "0", "", isPan, isFromRecharge, isOperatorRequired, timerTv, resendCodeTv, mDialog, mProgressDialog,
                                                        LoginDataResponse, Lattitude,Longitude, mApiCallBack);
                                                isBioMetricDialogShown=false;
                                            }
                                            else{
                                                if (mApiCallBack != null) {
                                                    mOnboardingResponse.setoId(callOnBoardOId + "");
                                                    mApiCallBack.onSuccess(mOnboardingResponse);
                                                }
                                            }

                                        }
                                    } else {
                                        if (mOnboardingResponse.getIsVersionValid() == false) {
                                            versionDialog(context);
                                        } else {
                                            Error(context, mOnboardingResponse.getMsg() + "");
                                        }
                                    }
                                }

                            } else {
                                Error(context, context.getResources().getString(R.string.some_thing_error));
                            }
                        } else {
                            apiErrorHandle(context, response.code(), response.message());
                        }
                    } catch (Exception ex) {
                        if (mProgressDialog != null && mProgressDialog.isShowing()) {
                            mProgressDialog.dismiss();
                        }
                        Error(context, ex.getMessage() + "");
                    }


                }

                @Override
                public void onFailure(Call<OnboardingResponse> call, Throwable t) {
                    if (mProgressDialog != null && mProgressDialog.isShowing()) {
                        mProgressDialog.dismiss();
                    }

                    apiFailureError(context, t);

                }
            });
        } catch (Exception e) {
            if (mProgressDialog != null && mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
            }
            Error(context, e.getMessage() + "");
        }

    }





    public boolean showCallOnBoardingMsgs(Activity context, OnboardingResponse mOnboardingResponse, CustomAlertDialog customPassDialog) {
        if (mOnboardingResponse.getIsDown() && mOnboardingResponse.getMsg() != null) {
            customPassDialog.showMessage(context.getResources().getString(R.string.SERVICEDOWN), mOnboardingResponse.getMsg() + "", R.drawable.servicedown8, 0);
            return false;
        } else if (mOnboardingResponse.getIsWaiting() && mOnboardingResponse.getMsg() != null) {
            customPassDialog.showMessage(context.getResources().getString(R.string.UNDERSCREENING), mOnboardingResponse.getMsg() + "", R.drawable.underscreaning7, 0);
            return false;
        } else if (mOnboardingResponse.getIsUnathorized() && mOnboardingResponse.getMsg() != null) {
            customPassDialog.showMessage(context.getResources().getString(R.string.UNAUTHORISED), mOnboardingResponse.getMsg() + "", R.drawable.unauthorized6, 0);
            return false;
        } else if (mOnboardingResponse.getIsIncomplete() && mOnboardingResponse.getMsg() != null) {
            customPassDialog.showMessage(context.getResources().getString(R.string.INCOMPLETE), mOnboardingResponse.getMsg() + "", R.drawable.incomplete5, 1);
            return false;
        } else if (mOnboardingResponse.getIsRejected() & mOnboardingResponse.getMsg() != null) {
            customPassDialog.showMessage(context.getResources().getString(R.string.REJECT), mOnboardingResponse.getMsg() + "", R.drawable.reject4, 1);
            return false;
        } else if (mOnboardingResponse.getIsRedirection() & mOnboardingResponse.getMsg() != null) {
            customPassDialog.showMessage(context.getResources().getString(R.string.Redirection), mOnboardingResponse.getMsg() + "", R.drawable.incomplete5, 1);
            return false;
        }
        return true;
    }

    public interface DialogOTPCallBack {
        void onPositiveClick(EditText edMobileOtp, String otpValue, TextView timerTv, View resendCodeTv, Dialog mDialog);

        void onResetCallback(EditText edMobileOtp, String otpValue, TextView timerTv, View resendCodeTv, Dialog mDialog);
    }

    public void openOnBoardingOtpDialog(final Activity context,int otpLength, final DialogOTPCallBack mDialogCallBack) {

        if (dialogOTP != null && dialogOTP.isShowing()) {
            return;
        }
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.verifyotp, null);

        final TextInputLayout tilMobileOtp = (TextInputLayout) view.findViewById(R.id.til_mobile_otp);
        final EditText edMobileOtp = view.findViewById(R.id.ed_mobile_otp);
        final View closeIv = view.findViewById(R.id.cancelButton);
        final View okButton = view.findViewById(R.id.okButton);
        /*final TextView timerTv = view.findViewById(R.id.timer);*/
        final View resendTv = view.findViewById(R.id.resendButton);

        /*View resendView = view.findViewById(R.id.resendView);*/

        /*numberTv.setText(mobileNum.replace(mobileNum.substring(0, 7), "XXXXXXX"));*/
        dialogOTP = new Dialog(context);
        dialogOTP.setCancelable(false);
        dialogOTP.setContentView(view);

        dialogOTP.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        closeIv.setOnClickListener(v -> dialogOTP.dismiss());

        edMobileOtp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s.length() < otpLength) {
                    tilMobileOtp.setError(context.getString(R.string.err_msg_otp));
                    okButton.setEnabled(false);
                } else {
                    tilMobileOtp.setErrorEnabled(false);
                    okButton.setEnabled(true);
                }
            }
        });


        okButton.setOnClickListener(v -> {
            if (edMobileOtp.getText().length() < otpLength) {
                edMobileOtp.setError("Enter Valid OTP");
                edMobileOtp.requestFocus();
            } else {
                edMobileOtp.setError(null);
                if (mDialogCallBack != null) {
                    mDialogCallBack.onPositiveClick(edMobileOtp, edMobileOtp.getText().toString(), null, resendTv, dialogOTP);
                }
            }
        });

        resendTv.setOnClickListener(v -> {
           /* if (edMobileOtp.getText().length() != 6) {
                otpErrorTv.setText("Enter Valid OTP");
                otpErrorTv.requestFocus();
            } else {
                otpErrorTv.setText("");*/
            if (mDialogCallBack != null) {
                mDialogCallBack.onResetCallback(edMobileOtp, edMobileOtp.getText().toString(), null, resendTv, dialogOTP);
            }
            /*}*/
        });



        dialogOTP.show();
    }

    void openOtpDialog(final Activity context, final DialogCallBack mDialogCallBack) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.verifyotp, null);

        edMobileOtp = (EditText) view.findViewById(R.id.ed_mobile_otp);
        final TextInputLayout tilMobileOtp = (TextInputLayout) view.findViewById(R.id.til_mobile_otp);
        final Button okButton = (Button) view.findViewById(R.id.okButton);
        final Button resendButton = (Button) view.findViewById(R.id.resendButton);
        resendButton.setVisibility(View.VISIBLE);
        final Button cancelButton = (Button) view.findViewById(R.id.cancelButton);
        final Dialog dialog = new Dialog(context);
        dialog.setCancelable(false);
        dialog.setContentView(view);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
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

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edMobileOtp.getText() != null && edMobileOtp.getText().length() == 6) {
                    tilMobileOtp.setErrorEnabled(false);
                    if (mDialogCallBack != null) {
                        mDialogCallBack.onPositiveClick(edMobileOtp.getText().toString());
                    }

                } else {
                    tilMobileOtp.setError(context.getString(R.string.err_msg_otp));
                }
            }
        });
        resendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edMobileOtp.getText() != null && edMobileOtp.getText().length() == 6) {
                    tilMobileOtp.setErrorEnabled(false);
                    if (mDialogCallBack != null) {
                        mDialogCallBack.onResetCallback(edMobileOtp.getText().toString());
                    }

                } else {
                    tilMobileOtp.setError(context.getString(R.string.err_msg_otp));
                }
            }
        });
        dialog.show();
    }

    public void GetAppPurchageToken(final Activity context, final String TotalToken, final String oid, final String PANID, final CustomLoader loader, final ApiCallBack mApiCallBack) {
        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            String LoginResponse = getLoginPref(context);
            LoginResponse LoginDataResponse = new Gson().fromJson(LoginResponse, LoginResponse.class);
            Call<AppUserListResponse> call = git.GetAppPurchageToken(new PurchaseTokenRequest(oid,
                    PANID,
                    TotalToken,
                    LoginDataResponse.getData().getUserID(),
                    LoginDataResponse.getData().getSessionID(),
                    LoginDataResponse.getData().getSession(),
                    ApplicationConstant.INSTANCE.APP_ID,
                    getIMEI(context), "",
                    BuildConfig.VERSION_NAME,
                    getSerialNo(context),
                    LoginDataResponse.getData().getLoginTypeID(),
                    LoginDataResponse.getData().getOutletID()));

            call.enqueue(new Callback<AppUserListResponse>() {
                @Override
                public void onResponse(Call<AppUserListResponse> call, final Response<AppUserListResponse> response) {
                    try {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }

                        if (response.body() != null) {
                            if (response.body().getStatuscode() == 2) {
                                if (response.body().getMsg() != null) {
                                    if (mApiCallBack != null) {
                                        mApiCallBack.onSucess(response.body());
                                    }
                                    Successful(context, response.body().getMsg());
                                }

                            } else if (response.body().getStatuscode() == 3) {
                                if (response.body().getMsg() != null) {
                                    Error(context, response.body().getMsg());
                                }
                            } else if (response.body().getStatuscode() == 1) {
                                if (response.body().getMsg() != null) {
                                    if (mApiCallBack != null) {
                                        mApiCallBack.onSucess(response.body());
                                    }
                                    Successful(context, response.body().getMsg());
                                }
                            } else {
                                if (response.body().getMsg() != null) {
                                    Error(context, response.body().getMsg());
                                }
                            }
                        }

                    } catch (Exception e) {

                    }


                }

                @Override
                public void onFailure(Call<AppUserListResponse> call, Throwable t) {


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getlocation(final Context context) {
        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;

        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
        }

        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex) {
        }

        if (!gps_enabled && !network_enabled) {
// notify user
            new AlertDialog.Builder(context)
                    .setMessage(R.string.gps_network_not_enabled)
                    .setPositiveButton(R.string.open_location_settings, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                            context.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        }
                    }).setNegativeButton(R.string.Cancel, null)
                    .show();
        }
    }
    /*---------------------------Calling Onboarding For AEPS Details-----------------------------*/

    /*public void GetTransactionMode(final MoveToWallet context, final CustomLoader loader) {
        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            String LoginResponse = getLoginPref(context);
            LoginResponse LoginDataResponse = new Gson().fromJson(LoginResponse, LoginResponse.class);
            Call<TransactionModeResponse> call = git.GetTransactionMode(new BalanceRequest(LoginDataResponse.getData().getUserID() + "", LoginDataResponse.getData().getLoginTypeID() + "",
                    ApplicationConstant.INSTANCE.APP_ID,
                    getIMEI(context),
                    "", BuildConfig.VERSION_NAME, getSerialNo(context), LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));
            call.enqueue(new Callback<TransactionModeResponse>() {
                @Override
                public void onResponse(Call<TransactionModeResponse> call, Response<TransactionModeResponse> response) {
                    if (loader != null)
                        if (loader.isShowing())
                            loader.dismiss();
                    if (response.body() != null) {
                        if (response.body().getStatuscode().equalsIgnoreCase("1")) {

                            if (response.body().getTransactionModes() != null && response.body().getTransactionModes().size() > 0) {
                                context.gettingTransactionModeData(response.body().getTransactionModes());

                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<TransactionModeResponse> call, Throwable t) {
                    if (loader != null)
                        if (loader.isShowing())
                            loader.dismiss();

                    displayingOnFailuireMessage(context, t.getMessage());

                }
            });

        } catch (Exception ex) {
            // Log.e("Exception", "" + ex.getMessage());
            if (loader != null)
                if (loader.isShowing())
                    loader.dismiss();
            displayingOnFailuireMessage(context, ex.getMessage());
        }
    }

    public void MoveToWallet(final Activity context, String actiontype, String transMode, String amount, final CustomLoader loader) {
        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            String LoginResponse = getLoginPref(context);
            LoginResponse LoginDataResponse = new Gson().fromJson(LoginResponse, LoginResponse.class);
            Call<TransactionModeResponse> call = git.MoveToWallet(new MoveToWalletRequest(ApplicationConstant.INSTANCE.APP_ID,
                    getIMEI(context),
                    LoginDataResponse.getData().getLoginTypeID() + "", "",
                    getSerialNo(context),
                    LoginDataResponse.getData().getSession(),
                    LoginDataResponse.getData().getSessionID(),
                    LoginDataResponse.getData().getUserID() + "",
                    BuildConfig.VERSION_NAME, actiontype, transMode, amount));
            call.enqueue(new Callback<TransactionModeResponse>() {
                @Override
                public void onResponse(Call<TransactionModeResponse> call, Response<TransactionModeResponse> response) {
                    if (loader != null)
                        if (loader.isShowing())
                            loader.dismiss();
                    if (response.body() != null) {
                        if (response.body().getStatuscode().equalsIgnoreCase("1")) {
                            Successfulok(response.body().getMsg(), (MoveToWallet) context);
                        } else {
                            if (response.body().getMsg() != null) {
                                Error(context, response.body().getMsg());
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<TransactionModeResponse> call, Throwable t) {
                    if (loader != null)
                        if (loader.isShowing())
                            loader.dismiss();
                    displayingOnFailuireMessage(context, t.getMessage());
                }
            });

        } catch (Exception ex) {
            // Log.e("Exception", "" + ex.getMessage());
            if (loader != null)
                if (loader.isShowing())
                    loader.dismiss();
            displayingOnFailuireMessage(context, ex.getMessage());
        }
    }
*/
    public void WTRLogReport(final Activity context, int topRows, int criteria, String criteriaText, int status, String fromDate, String toDate, int dateType, String transactionID, final CustomLoader loader) {
        try {
            String LoginResponse = getLoginPref(context);
            LoginResponse LoginDataResponse = new Gson().fromJson(LoginResponse, LoginResponse.class);
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            RefundLogRequest mRefundLogRequest = new RefundLogRequest("0", topRows, criteria,
                    criteriaText, status, fromDate, toDate, dateType, transactionID, LoginDataResponse.getData().getUserID(),
                    LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession(),
                    ApplicationConstant.INSTANCE.APP_ID,
                    getIMEI(context),
                    "", BuildConfig.VERSION_NAME, getSerialNo(context), LoginDataResponse.getData().getLoginTypeID());
            /* String str =new Gson().toJson(mFundDCReportRequest);*/
            Call<AppUserListResponse> call = git.WTRLog(mRefundLogRequest);
            call.enqueue(new Callback<AppUserListResponse>() {
                @Override
                public void onResponse(Call<AppUserListResponse> call, final retrofit2.Response<AppUserListResponse> response) {

                    try {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        if (response.body() != null) {
                            if (response.body().getStatuscode() == 1 && response.body().getRefundLog() != null && response.body().getRefundLog().size() > 0) {
                                ActivityActivityMessage activityActivityMessage =
                                        new ActivityActivityMessage("Refund_Log", "" + new Gson().toJson(response.body()).toString());
                                GlobalBus.getBus().post(activityActivityMessage);
                            } else if (response.body().getStatuscode() == -1) {
                                if (response.body().getVersionValid() == false) {
                                    versionDialog(context);
                                } else {
                                    Error(context, response.body().getMsg() + "");
                                }
                            } else {
                                Error(context, "Report not found.");
                            }
                        }
                    } catch (Exception e) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        Error(context, e.getMessage());
                    }

                }

                @Override
                public void onFailure(Call<AppUserListResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            loader.dismiss();
                    }
//                    Error(context, context.getResources().getString(R.string.err_something_went_wrong));
                    displayingOnFailuireMessage(context, "" + t.getMessage());

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            Error(context, e.getMessage());
        }
    }

    public void MakeW2RRequest(final Activity context, String tid, String transactionID, String RightAccount,
                               final CustomLoader loader, final TextView w2r) {
        try {
            String LoginResponse = getLoginPref(context);
            LoginResponse LoginDataResponse = new Gson().fromJson(LoginResponse, LoginResponse.class);
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<RefundRequestResponse> call = git.MakeW2RRequest(
                    new W2RRequest(ApplicationConstant.INSTANCE.APP_ID,
                            getIMEI(context),
                            LoginDataResponse.getData().getLoginTypeID() + "",
                            "", getSerialNo(context) + "",
                            LoginDataResponse.getData().getSession() + "",
                            LoginDataResponse.getData().getSessionID() + "",
                            LoginDataResponse.getData().getUserID() + "",
                            BuildConfig.VERSION_NAME + "",
                            tid,
                            transactionID,
                            RightAccount));
            call.enqueue(new Callback<RefundRequestResponse>() {
                @Override
                public void onResponse(Call<RefundRequestResponse> call, Response<RefundRequestResponse> response) {

                    if (loader != null)
                        if (loader.isShowing())
                            loader.dismiss();

                    if (response.body() != null && response.body().getStatuscode() != null) {

                        if (response.body().getStatuscode() == 1) {

                            if (response.body() != null && response.body().getMsg() != null) {
                                Successful(context, response.body().getMsg() + "");
                            }
                            w2r.setVisibility(View.GONE);
                        } else if (response.body().getStatuscode() == -1) {
                            if (response.body() != null && response.body().getMsg() != null) {
                                Error(context, response.body().getMsg() + "");
                            }
                        } else {
                            if (response.body() != null && response.body().getMsg() != null) {
                                Error(context, response.body().getMsg() + "");
                            }
                        }

                    }
                }

                @Override
                public void onFailure(Call<RefundRequestResponse> call, Throwable t) {
                    if (loader != null)
                        if (loader.isShowing())
                            loader.dismiss();
                    displayingOnFailuireMessage(context, t.getMessage());
                }
            });
        } catch (Exception ex) {
            if (loader != null)
                if (loader.isShowing())
                    loader.dismiss();

            displayingOnFailuireMessage(context, ex.getMessage());
        }
    }


    public void displayingOnFailuireMessage(Activity context, String failuireMssg) {
        if (failuireMssg != null && failuireMssg.contains("No address associated with hostname")) {
            Error(context, context.getResources().getString(R.string.network_error));
        } else {
            Error(context, failuireMssg);
        }
    }

    public interface ApiCallBack {
        void onSucess(Object object);
    }

    public interface ApiActiveServiceCallBack {
        void onSucess(OpTypeResponse mOpTypeResponse, OpTypeRollIdWiseServices mOpTypeRollIdWiseServices);
    }

    public interface ApiCallBackTwoMethod {
        void onSucess(Object object);

        void onError(Object object);
    }


    public interface DialogCallBack {
        void onPositiveClick(String value);

        void onResetCallback(String value);

        void onCancelClick();
    }

    public void apiErrorHandle(Activity context, int code, String msg) {
        if (code == 401) {
            ErrorWithTitle(context, "UNAUTHENTICATED " + code, msg + "");
        } else if (code == 404) {
            ErrorWithTitle(context, "API ERROR " + code, msg + "");
        } else if (code >= 400 && code < 500) {
            ErrorWithTitle(context, "CLIENT ERROR " + code, msg + "");
        } else if (code >= 500 && code < 600) {

            ErrorWithTitle(context, "SERVER ERROR " + code, msg + "");
        } else {
            ErrorWithTitle(context, "FATAL/UNKNOWN ERROR " + code, msg + "");
        }
    }

    public void apiFailureError(Activity context, Throwable t) {
        if (t instanceof UnknownHostException || t instanceof IOException) {
            NetworkError(context, "Network Error", "Poor Internet Connectivity found!!");
        } else if (t instanceof SocketTimeoutException || t instanceof TimeoutException) {
            ErrorWithTitle(context, "TIME OUT ERROR", t.getMessage() + "");
        } else {
            if (t.getMessage() != null && !t.getMessage().isEmpty()) {
                ErrorWithTitle(context, "FATAL ERROR", t.getMessage() + "");
            } else {
                Error(context, context.getResources().getString(R.string.some_thing_error));
            }
        }
    }


    public void AutoBillingUpdateAppRequest() {

    }


    public void OrderForUPI(final Activity context, final CustomLoader loader, String amt, String upiID, final ApiCallBack apiCallBack) {
        try {
            // loader.show();
            String mLoginResponse = getLoginPref(context);
            LoginResponse LoginDataResponse = new Gson().fromJson(mLoginResponse, LoginResponse.class);
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<RechargeReportResponse> call = git.AppGenerateOrderForUPI(new GenerateOrderForUPIRequest(amt, upiID,
                    LoginDataResponse.getData().getUserID(),
                    LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession(),
                    ApplicationConstant.INSTANCE.APP_ID,
                    getIMEI(context),
                    "", BuildConfig.VERSION_NAME, getSerialNo(context), LoginDataResponse.getData().getLoginTypeID()));
            call.enqueue(new Callback<RechargeReportResponse>() {
                @Override
                public void onResponse(Call<RechargeReportResponse> call, final retrofit2.Response<RechargeReportResponse> response) {

                    try {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        if (response.body() != null && response.body().getStatuscode() != null) {
                            if (response.body().getStatuscode().equalsIgnoreCase("1")) {

                                if (apiCallBack != null) {
                                    apiCallBack.onSucess(response.body());
                                }
                              /*  upiGenrateOrderId = String.valueOf(response.body().getOrderID());
                                openUpiIntent(getUPIString(selectedUpiId, txtAccountName.getText().toString().trim(),
                                        getString(R.string.app_name).replaceAll(" ", "") + "UPITransaction", txttranferAmount.getText().toString().trim(),
                                        ApplicationConstant.INSTANCE.baseUrl));*/
                            } else {
                                if (response.body().getIsVersionValid() != null && response.body().getIsVersionValid().equalsIgnoreCase("false")) {
                                    versionDialog(context);
                                } else {
                                    Error(context, response.body().getMsg() + "");
                                }
                            }
                        }
                    } catch (Exception e) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                    }

                }

                @Override
                public void onFailure(Call<RechargeReportResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            loader.dismiss();
                    }
                    try {
                        if (t.getMessage() != null && !t.getMessage().isEmpty()) {

                            if (t.getMessage().contains("No address associated with hostname")) {
                                NetworkError(context, context.getResources().getString(R.string.err_msg_network_title),
                                        context.getResources().getString(R.string.err_msg_network));
                            } else {
                                Error(context, t.getMessage());

                            }

                        } else {
                            Error(context, context.getResources().getString(R.string.some_thing_error));

                        }
                    } catch (IllegalStateException ise) {
                        Error(context, ise.getMessage());

                    }

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isPackageInstalled(String packagename, PackageManager packageManager) {
        try {
            packageManager.getPackageInfo(packagename, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public boolean isValidString(String str) {
        if (str != null) {
            str = str.trim();
            if (str.length() > 0) {
                return true;
            }
        }

        return false;
    }
}

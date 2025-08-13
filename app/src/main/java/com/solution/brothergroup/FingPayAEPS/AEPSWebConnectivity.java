package com.solution.brothergroup.FingPayAEPS;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.solution.brothergroup.Api.Request.BalanceRequest;
import com.solution.brothergroup.Api.Response.BankListResponse;
import com.solution.brothergroup.Authentication.dto.LoginResponse;
import com.solution.brothergroup.BuildConfig;
import com.solution.brothergroup.FingPayAEPS.dto.GenerateDepositOTPRequest;
import com.solution.brothergroup.FingPayAEPS.dto.GenerateDepositOTPResponse;
import com.solution.brothergroup.FingPayAEPS.dto.GetAEPSResponse;
import com.solution.brothergroup.FingPayAEPS.dto.GetAepsRequest;
import com.solution.brothergroup.FingPayAEPS.dto.InitiateMiniBankRequest;
import com.solution.brothergroup.FingPayAEPS.dto.InitiateMiniBankResponse;
import com.solution.brothergroup.FingPayAEPS.dto.PidData;
import com.solution.brothergroup.FingPayAEPS.dto.UpdateMiniBankStatusRequest;
import com.solution.brothergroup.R;
import com.solution.brothergroup.Util.ApiClient;
import com.solution.brothergroup.Util.ApplicationConstant;
import com.solution.brothergroup.Util.UtilMethods;
import com.solution.brothergroup.usefull.CustomLoader;

import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;

public enum AEPSWebConnectivity {
    INSTANCE;

    private CountDownTimer countDownTimer;
    private Dialog dialogOTP;

    public void GetAEPSBanklist(final Activity context, final CustomLoader loader,
                                LoginResponse LoginDataResponse, final UtilMethods.ApiCallBack mApiCallBack) {
        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            if(LoginDataResponse==null) {
                String LoginResponse = UtilMethods.INSTANCE.getLoginPref(context);
                 LoginDataResponse = new Gson().fromJson(LoginResponse, LoginResponse.class);
            }
            Call<BankListResponse> call = git.GetAEPSBanks(new BalanceRequest(LoginDataResponse.getData().getUserID() + "", LoginDataResponse.getData().getLoginTypeID() + "",
                    ApplicationConstant.INSTANCE.APP_ID,
                    UtilMethods.INSTANCE.getIMEI(context),
                    "", BuildConfig.VERSION_NAME, UtilMethods.INSTANCE.getSerialNo(context), LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));
            call.enqueue(new Callback<BankListResponse>() {
                @Override
                public void onResponse(Call<BankListResponse> call, final retrofit2.Response<BankListResponse> response) {

                    try {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        if (response.isSuccessful()) {
                            if (response.body() != null) {

                                if (response.body().getStatuscode()==1) {
                                    setAEPSBankList(context, new Gson().toJson(response.body()));
                                    if (mApiCallBack != null) {
                                        mApiCallBack.onSucess(response.body());
                                    }
                                } else  {
                                    if ( !response.body().getIsVersionValid()) {
                                       UtilMethods.INSTANCE.versionDialog(context);
                                    } else {
                                        UtilMethods.INSTANCE.Error(context, response.body().getMsg() + "");
                                    }
                                }
                            }
                        } else {
                            UtilMethods.INSTANCE.apiErrorHandle(context, response.code(), response.message());
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
                        UtilMethods.INSTANCE.apiFailureError(context, t);
                    } catch (IllegalStateException ise) {
                        UtilMethods.INSTANCE.Error(context, ise.getMessage());

                    }

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void InitiateMiniBank(final Activity context, String sDKType, String oid, String amount, LoginResponse LoginDataResponse,
                                 final CustomLoader loader, UtilMethods.ApiCallBack mApiCallBack) {
        try {
            loader.show();
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<InitiateMiniBankResponse> call = git.InitiateMiniBank(new InitiateMiniBankRequest(sDKType, oid, amount,
                    LoginDataResponse.getData().getUserID() + "", LoginDataResponse.getData().getLoginTypeID() + "",
                    ApplicationConstant.INSTANCE.APP_ID,
                    UtilMethods.INSTANCE.getIMEI(context),
                    "", BuildConfig.VERSION_NAME, UtilMethods.INSTANCE.getSerialNo(context),
                    LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));

            call.enqueue(new Callback<InitiateMiniBankResponse>() {

                @Override
                public void onResponse(Call<InitiateMiniBankResponse> call, retrofit2.Response<InitiateMiniBankResponse> response) {

                    if (loader.isShowing())
                        loader.dismiss();
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            if (response.body().getStatuscode() == 1) {
                                if(mApiCallBack!=null){
                                    mApiCallBack.onSucess(response.body());
                                }

                            } else {
                                if (!response.body().isVersionValid()) {
                                   UtilMethods.INSTANCE.versionDialog(context);
                                } else {
                                    UtilMethods.INSTANCE.Error(context, response.body().getMsg() + "");
                                }
                            }

                        }
                    } else {
                        UtilMethods.INSTANCE.apiErrorHandle(context, response.code(), response.message());
                    }
                }

                @Override
                public void onFailure(Call<InitiateMiniBankResponse> call, Throwable t) {

                    if (loader.isShowing())
                        loader.dismiss();
                    UtilMethods.INSTANCE.apiFailureError(context, t);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void UpdateMiniBankStatus(final Activity context, String cardNum, String bankName, final CustomLoader loader, String tid, String vendorId, String apiStatus, String remark,
                                     LoginResponse LoginDataResponse, UtilMethods.ApiCallBack mApiCallBack) {
        try {
            loader.show();
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<InitiateMiniBankResponse> call = git.UpdateMiniBankStatus(new UpdateMiniBankStatusRequest(cardNum, bankName,tid + "", vendorId + "", apiStatus + "", remark + "",
                    LoginDataResponse.getData().getUserID() + "", LoginDataResponse.getData().getLoginTypeID() + "",
                    ApplicationConstant.INSTANCE.APP_ID,
                    UtilMethods.INSTANCE.getIMEI(context),
                    "", BuildConfig.VERSION_NAME, UtilMethods.INSTANCE.getSerialNo(context),
                    LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));

            call.enqueue(new Callback<InitiateMiniBankResponse>() {

                @Override
                public void onResponse(Call<InitiateMiniBankResponse> call, retrofit2.Response<InitiateMiniBankResponse> response) {

                    if (loader.isShowing())
                        loader.dismiss();
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            if (response.body().getStatuscode() == 1) {
                                if(mApiCallBack!=null){
                                    mApiCallBack.onSucess(response.body());
                                }
                            } else {
                                if (!response.body().isVersionValid()) {
                                   UtilMethods.INSTANCE.versionDialog(context);
                                } else {
                                    UtilMethods.INSTANCE.Error(context, response.body().getMsg() + "");
                                }
                            }

                        }
                    } else {
                        UtilMethods.INSTANCE.apiErrorHandle(context, response.code(), response.message());
                    }
                }

                @Override
                public void onFailure(Call<InitiateMiniBankResponse> call, Throwable t) {

                    if (loader.isShowing())
                        loader.dismiss();
                    UtilMethods.INSTANCE.apiFailureError(context, t);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void GetBalanceAEPS(final Activity context,String pidOption,String deviceName,int bankId,String pidXml, PidData mPidData, String aadhar, int bankIIn, int interfaceType, final CustomLoader loader,LoginResponse LoginDataResponse,String lati, String longi, final UtilMethods.ApiCallBack mApiCallBack) {
        try {
            loader.show();
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);

            Call<GetAEPSResponse> call = git.GetBalanceAEPS(new GetAepsRequest(pidOption,deviceName,bankId,pidXml,lati,longi,mPidData, aadhar, interfaceType, bankIIn, LoginDataResponse.getData().getUserID() + "", LoginDataResponse.getData().getLoginTypeID() + "",
                    ApplicationConstant.INSTANCE.APP_ID,
                    UtilMethods.INSTANCE.getIMEI(context),
                    "", BuildConfig.VERSION_NAME, UtilMethods.INSTANCE.getSerialNo(context), LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));
            call.enqueue(new Callback<GetAEPSResponse>() {
                @Override
                public void onResponse(Call<GetAEPSResponse> call, final retrofit2.Response<GetAEPSResponse> response) {

                    try {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        if (response.isSuccessful()) {
                            if (response.body() != null) {

                                if (response.body().getStatuscode() == 1) {
                                    if (mApiCallBack != null) {
                                        mApiCallBack.onSucess(response.body());
                                    }
                                } else {
                                    if (!response.body().isVersionValid()) {
                                        UtilMethods.INSTANCE.versionDialog(context);
                                    } else {
                                        UtilMethods.INSTANCE.Error(context, response.body().getMsg() + "");
                                    }
                                }
                            }
                        } else {
                            UtilMethods.INSTANCE.apiErrorHandle(context, response.code(), response.message());
                        }
                    } catch (Exception e) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                    }

                }

                @Override
                public void onFailure(Call<GetAEPSResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            loader.dismiss();
                    }
                    try {
                        UtilMethods.INSTANCE.apiFailureError(context, t);
                    } catch (IllegalStateException ise) {
                        UtilMethods.INSTANCE.Error(context, ise.getMessage());

                    }

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void GetMINIStatementAEPS(final Activity context,String pidOption,String deviceName,int bankId,String pidDataXml, PidData mPidData, String aadhar, int bankIIn,String bankName, int interfaceType, final CustomLoader loader, LoginResponse LoginDataResponse,String lati,String longi,final UtilMethods.ApiCallBack mApiCallBack) {
        try {
            loader.show();
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<GetAEPSResponse> call = git.BankMiniStatement(new GetAepsRequest(
                    pidOption,
                    deviceName,
                    bankId,
                    pidDataXml,
                    lati,
                    longi,
                    mPidData,
                    aadhar,
                    interfaceType,
                    bankIIn,
                    bankName,
                    LoginDataResponse.getData().getUserID() + "",
                    LoginDataResponse.getData().getLoginTypeID() + "",
                    ApplicationConstant.INSTANCE.APP_ID,
                    UtilMethods.INSTANCE.getIMEI(context),
                    "",
                    BuildConfig.VERSION_NAME,
                    UtilMethods.INSTANCE.getSerialNo(context),
                    LoginDataResponse.getData().getSessionID(),
                    LoginDataResponse.getData().getSession()));
            call.enqueue(new Callback<GetAEPSResponse>() {
                @Override
                public void onResponse(Call<GetAEPSResponse> call, final retrofit2.Response<GetAEPSResponse> response) {

                    try {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        if (response.isSuccessful()) {
                            if (response.body() != null) {

                                if (response.body().getStatuscode() == 1) {
                                    if(response.body().getStatements()!=null && response.body().getStatements().size()>0) {
                                        if (mApiCallBack != null) {
                                            mApiCallBack.onSucess(response.body());
                                        }
                                    }else{
                                        UtilMethods.INSTANCE.Error(context, "Mini Statements not available.");
                                    }
                                } else {
                                    if (!response.body().isVersionValid()) {
                                        UtilMethods.INSTANCE.versionDialog(context);
                                    } else {
                                        UtilMethods.INSTANCE.Error(context, response.body().getMsg() + "");
                                    }
                                }
                            }
                        } else {
                            UtilMethods.INSTANCE.apiErrorHandle(context, response.code(), response.message());
                        }
                    } catch (Exception e) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                    }

                }

                @Override
                public void onFailure(Call<GetAEPSResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            loader.dismiss();
                    }
                    try {
                        UtilMethods.INSTANCE.apiFailureError(context, t);
                    } catch (IllegalStateException ise) {
                        UtilMethods.INSTANCE.Error(context, ise.getMessage());

                    }

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public void GetWithdrawlAEPS(final Activity context,int bankId,String deviceName,String pidOption, String pidXml,PidData mPidData, String aadhar, String amount, int bankIIn, int interfaceType, final CustomLoader loader,LoginResponse LoginDataResponse,String lati, String longi, final UtilMethods.ApiCallBack mApiCallBack) {
        try {
            loader.show();
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<GetAEPSResponse> call = git.AEPSWithdrawal(new GetAepsRequest(pidOption,deviceName,bankId,pidXml,lati, longi,mPidData, aadhar, amount, interfaceType, bankIIn, LoginDataResponse.getData().getUserID() + "", LoginDataResponse.getData().getLoginTypeID() + "",
                    ApplicationConstant.INSTANCE.APP_ID,
                    UtilMethods.INSTANCE.getIMEI(context),
                    "", BuildConfig.VERSION_NAME, UtilMethods.INSTANCE.getSerialNo(context), LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));
            call.enqueue(new Callback<GetAEPSResponse>() {
                @Override
                public void onResponse(Call<GetAEPSResponse> call, final retrofit2.Response<GetAEPSResponse> response) {

                    try {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        if (response.isSuccessful()) {
                            if (response.body() != null) {

                                if (response.body().getStatuscode() == 1) {
                                    if (mApiCallBack != null) {
                                        mApiCallBack.onSucess(response.body());
                                    }
                                } else {
                                    if (!response.body().isVersionValid()) {
                                        UtilMethods.INSTANCE.versionDialog(context);
                                    } else {
                                        UtilMethods.INSTANCE.Error(context, response.body().getMsg() + "");
                                    }
                                }
                            }
                        } else {
                            UtilMethods.INSTANCE.apiErrorHandle(context, response.code(), response.message());
                        }
                    } catch (Exception e) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                    }

                }

                @Override
                public void onFailure(Call<GetAEPSResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            loader.dismiss();
                    }
                    try {
                        UtilMethods.INSTANCE.apiFailureError(context, t);
                    } catch (IllegalStateException ise) {
                        UtilMethods.INSTANCE.Error(context, ise.getMessage());

                    }

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setAEPSBankList(Activity context, String bankList) {
        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(ApplicationConstant.INSTANCE.bankAEPSListPref, bankList);
        editor.commit();
    }

    public void GenerateDepositOTP(final Activity context, String lati, String longi, String aadhar, String amount, int bankIIn, int interfaceType,
                                   final CustomLoader loader, LoginResponse LoginDataResponse, final UtilMethods.ApiCallBack mApiCallBack) {
        try {
            loader.show();
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);

            Call<GenerateDepositOTPResponse> call = git.GenerateDepositOTP(new GenerateDepositOTPRequest(lati, longi, aadhar, amount, interfaceType, bankIIn,
                    LoginDataResponse.getData().getUserID() + "", LoginDataResponse.getData().getLoginTypeID() + "",
                    ApplicationConstant.INSTANCE.APP_ID, UtilMethods.INSTANCE.getIMEI(context), "",
                    BuildConfig.VERSION_NAME, UtilMethods.INSTANCE.getSerialNo(context),
                    LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));
            call.enqueue(new Callback<GenerateDepositOTPResponse>() {
                @Override
                public void onResponse(Call<GenerateDepositOTPResponse> call, final retrofit2.Response<GenerateDepositOTPResponse> response) {

                    try {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        if (response.isSuccessful()) {
                            if (response.body() != null) {

                                if (response.body().getStatuscode() == 1) {

                                    if (mApiCallBack != null) {
                                        mApiCallBack.onSucess(response.body());
                                    }
                                } else {
                                    if (!response.body().isVersionValid()) {
                                      UtilMethods.INSTANCE.  versionDialog(context);
                                    } else {
                                        UtilMethods.INSTANCE.  Error(context, response.body().getMsg() + "");
                                    }
                                }
                            }
                        } else {
                            UtilMethods.INSTANCE.  apiErrorHandle(context, response.code(), response.message());
                        }
                    } catch (Exception e) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                    }

                }

                @Override
                public void onFailure(Call<GenerateDepositOTPResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            loader.dismiss();
                    }
                    try {
                        UtilMethods.INSTANCE.  apiFailureError(context, t);
                    } catch (IllegalStateException ise) {
                        UtilMethods.INSTANCE.  Error(context, ise.getMessage());

                    }

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void VerifyDepositOTP(final Activity context, String lati, String longi, String reff1, String reff2, String reff3, String otp, String aadhar, String amount, int bankIIn, int interfaceType,
                                 final CustomLoader loader, LoginResponse LoginDataResponse, final UtilMethods.ApiCallBack mApiCallBack) {
        try {
            loader.show();
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);

            Call<GenerateDepositOTPResponse> call = git.VerifyDepositOTP(new GenerateDepositOTPRequest(lati, longi, reff1, reff2, reff3, otp, aadhar, amount, interfaceType, bankIIn,
                    LoginDataResponse.getData().getUserID() + "", LoginDataResponse.getData().getLoginTypeID() + "",
                    ApplicationConstant.INSTANCE.APP_ID, UtilMethods.INSTANCE.getIMEI(context), "", BuildConfig.VERSION_NAME, UtilMethods.INSTANCE.getSerialNo(context),
                    LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));
            call.enqueue(new Callback<GenerateDepositOTPResponse>() {
                @Override
                public void onResponse(Call<GenerateDepositOTPResponse> call, final retrofit2.Response<GenerateDepositOTPResponse> response) {

                    try {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        if (response.isSuccessful()) {
                            if (response.body() != null) {

                                if (response.body().getStatuscode() == 1) {

                                    if (mApiCallBack != null) {
                                        mApiCallBack.onSucess(response.body());
                                    }
                                } else {
                                    if (!response.body().isVersionValid()) {
                                        UtilMethods.INSTANCE.           versionDialog(context);
                                    } else {
                                        UtilMethods.INSTANCE.     Error(context, response.body().getMsg() + "");
                                    }
                                }
                            }
                        } else {
                            UtilMethods.INSTANCE.   apiErrorHandle(context, response.code(), response.message());
                        }
                    } catch (Exception e) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                    }

                }

                @Override
                public void onFailure(Call<GenerateDepositOTPResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            loader.dismiss();
                    }
                    try {
                        UtilMethods.INSTANCE. apiFailureError(context, t);
                    } catch (IllegalStateException ise) {
                        UtilMethods.INSTANCE. Error(context, ise.getMessage());

                    }

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void DepositNow(final Activity context, String lati, String longi, String reff1, String reff2, String reff3, String otp, String aadhar, String amount, int bankIIn, int interfaceType,
                           final CustomLoader loader, LoginResponse LoginDataResponse, final UtilMethods.ApiCallBack mApiCallBack) {
        try {
            loader.show();
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);

            Call<GenerateDepositOTPResponse> call = git.DepositNow(new GenerateDepositOTPRequest(lati, longi, reff1, reff2, reff3, otp, aadhar, amount, interfaceType, bankIIn,
                    LoginDataResponse.getData().getUserID() + "", LoginDataResponse.getData().getLoginTypeID() + "",
                    ApplicationConstant.INSTANCE.APP_ID, UtilMethods.INSTANCE.getIMEI(context), "", BuildConfig.VERSION_NAME, UtilMethods.INSTANCE.getSerialNo(context),
                    LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));
            call.enqueue(new Callback<GenerateDepositOTPResponse>() {
                @Override
                public void onResponse(Call<GenerateDepositOTPResponse> call, final retrofit2.Response<GenerateDepositOTPResponse> response) {

                    try {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        if (response.isSuccessful()) {
                            if (response.body() != null) {

                                if (response.body().getStatuscode() == 1) {

                                    if (mApiCallBack != null) {
                                        mApiCallBack.onSucess(response.body());
                                    }
                                } else {
                                    if (!response.body().isVersionValid()) {
                                        UtilMethods.INSTANCE.           versionDialog(context);
                                    } else {
                                        UtilMethods.INSTANCE.        Error(context, response.body().getMsg() + "");
                                    }
                                }
                            }
                        } else {
                            UtilMethods.INSTANCE. apiErrorHandle(context, response.code(), response.message());
                        }
                    } catch (Exception e) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                    }

                }

                @Override
                public void onFailure(Call<GenerateDepositOTPResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            loader.dismiss();
                    }
                    try {
                        UtilMethods.INSTANCE. apiFailureError(context, t);
                    } catch (IllegalStateException ise) {
                        UtilMethods.INSTANCE.  Error(context, ise.getMessage());

                    }

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void GetAadharPay(final Activity context, String pidDataXML, String lati, String longi, PidData mPidData, String aadhar, String amount, int bankIIn, int interfaceType,
                             final CustomLoader loader, LoginResponse LoginDataResponse, final UtilMethods.ApiCallBack mApiCallBack) {
        try {
            loader.show();
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);

            Call<GetAEPSResponse> call = git.Aadharpay(new GetAepsRequest(lati, longi, pidDataXML, mPidData, aadhar, amount, interfaceType, bankIIn,
                    LoginDataResponse.getData().getUserID() + "", LoginDataResponse.getData().getLoginTypeID() + "",
                    ApplicationConstant.INSTANCE.APP_ID, UtilMethods.INSTANCE.getIMEI(context), "", BuildConfig.VERSION_NAME, UtilMethods.INSTANCE.getSerialNo(context),
                    LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));
            call.enqueue(new Callback<GetAEPSResponse>() {
                @Override
                public void onResponse(Call<GetAEPSResponse> call, final retrofit2.Response<GetAEPSResponse> response) {

                    try {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        if (response.isSuccessful()) {
                            if (response.body() != null) {

                                if (response.body().getStatuscode() == 1) {
                                    if (mApiCallBack != null) {
                                        mApiCallBack.onSucess(response.body());
                                    }
                                } else {
                                    if (!response.body().isVersionValid()) {
                                        UtilMethods.INSTANCE.versionDialog(context);
                                    } else {
                                        UtilMethods.INSTANCE.Error(context, response.body().getMsg() + "");
                                    }
                                }
                            }
                        } else {
                            UtilMethods.INSTANCE.apiErrorHandle(context, response.code(), response.message());
                        }
                    } catch (Exception e) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                    }

                }

                @Override
                public void onFailure(Call<GetAEPSResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            loader.dismiss();
                    }
                    try {
                        UtilMethods.INSTANCE.apiFailureError(context, t);
                    } catch (IllegalStateException ise) {
                        UtilMethods.INSTANCE.Error(context, ise.getMessage());

                    }

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void openOtpDepositDialog(final Activity context, String mobileNum, final DialogOTPCallBack mDialogCallBack) {

        if (dialogOTP != null && dialogOTP.isShowing()) {
            return;
        }
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.verifyotp, null);

        TextView numberTv = view.findViewById(R.id.number);
        EditText edMobileOtp = view.findViewById(R.id.ed_mobile_otp);
        final View btLogin = view.findViewById(R.id.okButton);
        final View cancelButton = view.findViewById(R.id.cancelButton);
        final TextView timerTv = view.findViewById(R.id.timer);
        final Button resendBtn = view.findViewById(R.id.resendButton);


        if (mobileNum != null && !mobileNum.isEmpty()) {
            numberTv.setVisibility(View.VISIBLE);
            numberTv.setText(mobileNum.replace(mobileNum.substring(0, 7), "XXXXXXX"));
        }
        dialogOTP = new Dialog(context,R.style.Theme_AppCompat_Dialog_Alert);
        dialogOTP.setCancelable(false);
        dialogOTP.setContentView(view);
        dialogOTP.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogOTP.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        setTimer(timerTv, resendBtn);

        cancelButton.setOnClickListener(v -> dialogOTP.dismiss());


        btLogin.setOnClickListener(v -> {
            if (edMobileOtp.getText().length() != 6) {
                edMobileOtp.setError("Enter Valid OTP");
                edMobileOtp.requestFocus();
            } else {
                if (mDialogCallBack != null) {
                    mDialogCallBack.onPositiveClick(edMobileOtp, edMobileOtp.getText().toString(), timerTv, resendBtn, dialogOTP);
                }
            }
        });

        resendBtn.setOnClickListener(v -> {

            if (mDialogCallBack != null) {
                mDialogCallBack.onResetCallback(edMobileOtp, edMobileOtp.getText().toString(), timerTv, resendBtn, dialogOTP);
            }

        });

        dialogOTP.setOnDismissListener(dialog1 -> {
            if (countDownTimer != null) {
                countDownTimer.cancel();
                countDownTimer = null;
            }
        });

        dialogOTP.show();
    }

    public interface DialogOTPCallBack {
        void onPositiveClick(EditText edMobileOtp, String otpValue, TextView timerTv, View resendCodeTv, Dialog mDialog);

        void onResetCallback(EditText edMobileOtp, String otpValue, TextView timerTv, View resendCodeTv, Dialog mDialog);
    }

    public void setTimer(final TextView timer, final View resendcode) {
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

}

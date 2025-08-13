package com.solution.brothergroup.FingPayAEPS;


import com.solution.brothergroup.Api.Request.BalanceRequest;
import com.solution.brothergroup.Api.Response.BankListResponse;
import com.solution.brothergroup.FingPayAEPS.dto.GenerateDepositOTPRequest;
import com.solution.brothergroup.FingPayAEPS.dto.GenerateDepositOTPResponse;
import com.solution.brothergroup.FingPayAEPS.dto.GetAEPSResponse;
import com.solution.brothergroup.FingPayAEPS.dto.GetAepsRequest;
import com.solution.brothergroup.FingPayAEPS.dto.InitiateMiniBankRequest;
import com.solution.brothergroup.FingPayAEPS.dto.InitiateMiniBankResponse;
import com.solution.brothergroup.FingPayAEPS.dto.UpdateMiniBankStatusRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface EndPointInterface {

    @Headers("Content-Type: application/json")
    @POST("App/InitiateMiniBank")
    Call<InitiateMiniBankResponse> InitiateMiniBank(@Body InitiateMiniBankRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/UpdateMiniBankStatus")
    Call<InitiateMiniBankResponse> UpdateMiniBankStatus(@Body UpdateMiniBankStatusRequest request);


    @Headers("Content-Type: application/json")
    @POST("App/GetAEPSBanks?")
    Call<BankListResponse> GetAEPSBanks(@Body BalanceRequest balanceRequest);

    @Headers("Content-Type: application/json")
    @POST("App/GetBalanceAEPS?")
    Call<GetAEPSResponse> GetBalanceAEPS(@Body GetAepsRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/BankMiniStatement?")
    Call<GetAEPSResponse> BankMiniStatement(@Body GetAepsRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/AEPSWithdrawal?")
    Call<GetAEPSResponse> AEPSWithdrawal(@Body GetAepsRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/Aadharpay")
    Call<GetAEPSResponse> Aadharpay(@Body GetAepsRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/GenerateDepositOTP")
    Call<GenerateDepositOTPResponse> GenerateDepositOTP(@Body GenerateDepositOTPRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/VerifyDepositOTP")
    Call<GenerateDepositOTPResponse> VerifyDepositOTP(@Body GenerateDepositOTPRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/DepositNow")
    Call<GenerateDepositOTPResponse> DepositNow(@Body GenerateDepositOTPRequest request);

}

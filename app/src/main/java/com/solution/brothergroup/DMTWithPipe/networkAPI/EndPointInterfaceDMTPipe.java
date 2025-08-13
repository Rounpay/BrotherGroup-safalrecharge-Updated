package com.solution.brothergroup.DMTWithPipe.networkAPI;

import com.solution.brothergroup.DMTWithPipe.dto.GetChargedAmountRequestNew;
import com.solution.brothergroup.DMTWithPipe.dto.GetSenderRequestNew;
import com.solution.brothergroup.DMTWithPipe.dto.SendMoneyRequestNew;
import com.solution.brothergroup.Api.Response.RechargeReportResponse;
import com.solution.brothergroup.Util.CreateSenderResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface EndPointInterfaceDMTPipe {

    @Headers("Content-Type: application/json")
    @POST("App/GetSenderP?")
    Call<CreateSenderResponse> GetSenderNew(@Body GetSenderRequestNew getSenderRequest);//CreateSenderResponse

    @Headers("Content-Type: application/json")
    @POST("App/CreateSenderP?")
    Call<RechargeReportResponse> CreateSenderNew(@Body GetSenderRequestNew createsenderRequest);


    @Headers("Content-Type: application/json")
    @POST("App/VerifySenderP?")
    Call<RechargeReportResponse> VerifySenderNew(@Body GetSenderRequestNew createsenderRequest);

    @Headers("Content-Type: application/json")
    @POST("App/AddBeneficiaryP?")
    Call<RechargeReportResponse> AddBeneficiaryNew(@Body GetSenderRequestNew balanceRequest);

    @Headers("Content-Type: application/json")
    @POST("App/VerifyAccountP?")
    Call<RechargeReportResponse> VerifyAccountNew(@Body GetSenderRequestNew balanceRequest);

    @Headers("Content-Type: application/json")
    @POST("App/GetBeneficiaryP?")
    Call<RechargeReportResponse> GetBeneficiaryNew(@Body GetSenderRequestNew balanceRequest);

    @Headers("Content-Type: application/json")
    @POST("App/GetChargedAmountP?")
    Call<RechargeReportResponse> GetChargedAmountNew(@Body GetChargedAmountRequestNew getChargedAmountRequeat);

    @Headers("Content-Type: application/json")
    @POST("App/SendMoneyP?")
    Call<RechargeReportResponse> SendMoneyNew(@Body SendMoneyRequestNew sendMoneyRequest);

    @Headers("Content-Type: application/json")
    @POST("App/DeleteBeneficiaryP?")
    Call<RechargeReportResponse> DeleteBeneficiaryNew(@Body GetSenderRequestNew sendMoneyRequest);

}

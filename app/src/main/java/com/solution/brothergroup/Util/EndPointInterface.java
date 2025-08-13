package com.solution.brothergroup.Util;

/*import com.solution.roundpay.BalanceCheck.dto.BalanceCheckResponse;*/

import com.google.gson.JsonObject;
import com.solution.brothergroup.AddMoney.modelClass.GatewayTransactionRequest;
import com.solution.brothergroup.AddMoney.modelClass.GatwayTransactionResponse;
import com.solution.brothergroup.AddMoney.modelClass.InitiateUPIResponse;
import com.solution.brothergroup.AddMoney.modelClass.PayTMTransactionUpdateRequest;
import com.solution.brothergroup.AddMoney.modelClass.UpdateUPIRequest;
import com.solution.brothergroup.Api.Request.ASPayCollectRequest;
import com.solution.brothergroup.Api.Request.AccountOpenListRequest;
import com.solution.brothergroup.Api.Request.AchieveTargetRequest;
import com.solution.brothergroup.Api.Request.AggrePayTransactionUpdateRequest;
import com.solution.brothergroup.Api.Request.AppGetAMRequest;
import com.solution.brothergroup.Api.Request.AppUserListRequest;
import com.solution.brothergroup.Api.Request.AppUserReffDetailRequest;
import com.solution.brothergroup.Api.Request.AppUserRegisterRequest;
import com.solution.brothergroup.Api.Request.BalanceRequest;
import com.solution.brothergroup.Api.Request.BasicRequest;
import com.solution.brothergroup.Api.Request.CallBackRequest;
import com.solution.brothergroup.Api.Request.ChangePinPasswordRequest;
import com.solution.brothergroup.Api.Request.DFStatusRequest;
import com.solution.brothergroup.Api.Request.DmrRequest;
import com.solution.brothergroup.Api.Request.FetchBillRequest;
import com.solution.brothergroup.Api.Request.FosAccStmtAndCollReportRequest;
import com.solution.brothergroup.Api.Request.FosAppUserListRequest;
import com.solution.brothergroup.Api.Request.FundDCReportRequest;
import com.solution.brothergroup.Api.Request.FundTransferRequest;
import com.solution.brothergroup.Api.Request.GetHLRLookUpRequest;
import com.solution.brothergroup.Api.Request.HeavyrefreshRequest;
import com.solution.brothergroup.Api.Request.IncentiveDetailRequest;
import com.solution.brothergroup.Api.Request.InitiateUPIRequest;
import com.solution.brothergroup.Api.Request.LapuRealCommissionRequest;
import com.solution.brothergroup.Api.Request.LogoutRequest;
import com.solution.brothergroup.Api.Request.MapQRToUserRequest;
import com.solution.brothergroup.Api.Request.MoveToBankReportRequest;
import com.solution.brothergroup.Api.Request.NewsRequest;
import com.solution.brothergroup.Api.Request.OnboardingRequest;
import com.solution.brothergroup.Api.Request.OptionalOperatorRequest;
import com.solution.brothergroup.Api.Request.PurchaseTokenRequest;
import com.solution.brothergroup.Api.Request.RealApiChangeRequest;
import com.solution.brothergroup.Api.Request.RefundLogRequest;
import com.solution.brothergroup.Api.Request.ResendOtpRequest;
import com.solution.brothergroup.Api.Request.SignupRequest;
import com.solution.brothergroup.Api.Request.SlabRangeDetailRequest;
import com.solution.brothergroup.Api.Request.SubmitSocialDetailsRequest;
import com.solution.brothergroup.Api.Request.UPIPaymentReq;
import com.solution.brothergroup.Api.Request.UpdateFcmRequest;
import com.solution.brothergroup.Api.Request.UpdateKycStatusRequest;
import com.solution.brothergroup.Api.Request.UpdateSettlementAccountRequest;
import com.solution.brothergroup.Api.Request.UpdateUserRequest;
import com.solution.brothergroup.Api.Request.UserDayBookRequest;
import com.solution.brothergroup.Api.Response.AccountOpenListResponse;
import com.solution.brothergroup.Api.Response.AppGetAMResponse;
import com.solution.brothergroup.Api.Response.AppUserListResponse;
import com.solution.brothergroup.Api.Response.AppUserReffDetailResponse;
import com.solution.brothergroup.Api.Response.BalanceResponse;
import com.solution.brothergroup.Api.Response.BankListResponse;
import com.solution.brothergroup.Api.Response.BasicResponse;
import com.solution.brothergroup.Api.Response.DMTReceiptResponse;
import com.solution.brothergroup.Api.Response.FosAccStmtAndCollReportResponse;
import com.solution.brothergroup.Api.Response.FundreqToResponse;
import com.solution.brothergroup.Api.Response.GetBankAndPaymentModeResponse;
import com.solution.brothergroup.Api.Response.GetHLRLookUPResponse;
import com.solution.brothergroup.Api.Response.GetVAResponse;
import com.solution.brothergroup.Api.Response.NumberListResponse;
import com.solution.brothergroup.Api.Response.OnboardingResponse;
import com.solution.brothergroup.Api.Response.OperatorOptionalResponse;
import com.solution.brothergroup.Api.Response.RechargeReportResponse;
import com.solution.brothergroup.Api.Response.SettlementAccountResponse;
import com.solution.brothergroup.Api.Response.SlabCommissionResponse;
import com.solution.brothergroup.Api.Response.SlabRangeDetailResponse;
import com.solution.brothergroup.Api.Response.UpdateKycResponse;
import com.solution.brothergroup.Api.Response.WalletTypeResponse;
import com.solution.brothergroup.Authentication.dto.LoginResponse;
import com.solution.brothergroup.BrowsePlan.dto.ResponsePlan;
import com.solution.brothergroup.CMS.Api.CmsApiResponse;
import com.solution.brothergroup.CMS.Api.CmsRequest;
import com.solution.brothergroup.DTH.dto.DTHSubscriptionRequest;
import com.solution.brothergroup.DTH.dto.DthSubscriptionReportRequest;
import com.solution.brothergroup.DTH.dto.DthSubscriptionReportResponse;
import com.solution.brothergroup.DTH.dto.GetDthPackageChannelRequest;
import com.solution.brothergroup.DTH.dto.GetDthPackageRequest;
import com.solution.brothergroup.DTH.dto.GetDthPackageResponse;
import com.solution.brothergroup.DTH.dto.PincodeAreaRequest;
import com.solution.brothergroup.DTH.dto.PincodeAreaResponse;
import com.solution.brothergroup.DthCustomerInfo.dto.DTHInfoResponse;
import com.solution.brothergroup.DthPlan.dto.DTHChannelPlanInfoRequest;
import com.solution.brothergroup.DthPlan.dto.DthPlanChannelAllResponse;
import com.solution.brothergroup.DthPlan.dto.DthPlanInfoAllResponse;
import com.solution.brothergroup.DthPlan.dto.DthPlanInfoResponse;
import com.solution.brothergroup.DthPlan.dto.DthPlanLanguageWiseRequest;
import com.solution.brothergroup.Fragments.DFStatusResponse;
import com.solution.brothergroup.Fragments.dto.GetSenderRequestNew;
import com.solution.brothergroup.Fragments.dto.GetUserResponse;
import com.solution.brothergroup.MoveToWallet.dto.MoveToWalletRequest;
import com.solution.brothergroup.MoveToWallet.dto.TransactionModeResponse;
import com.solution.brothergroup.NSDL.NsdlRequest;
import com.solution.brothergroup.NSDL.ReciptRequest;
import com.solution.brothergroup.NSDL.TransactionReceiptResponse;
import com.solution.brothergroup.RECHARGEANDBBPS.API.FetchBillResponse;
import com.solution.brothergroup.ROffer.dto.ROfferRequest;
import com.solution.brothergroup.ROffer.dto.RofferResponse;
import com.solution.brothergroup.UPIPayment.UI.CompanyProfileResponse;
import com.solution.brothergroup.UpgradePacakge.dto.UpgradePackageRequest;
import com.solution.brothergroup.UpgradePacakge.dto.UpgradePackageResponse;
import com.solution.brothergroup.W2RRequest.dto.W2RRequest;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by Vishnu on 20-01-2017.
 */

public interface EndPointInterface {

    @Headers("Content-Type: application/json")
    @POST("App/GetNumberList")
    Call<NumberListResponse> GetNumberList(@Body NunberListRequest appInfo);

    @FormUrlEncoded
    @POST("PGCallback/CashFreeStatusCheck")
    Call<InitiateUPIResponse> CashFreeStatusCheck(@Field("TID") String tid);

    @Headers("Content-Type: application/json")
    @POST("App/Login")
    Call<LoginResponse> secureLogin(@Body LoginRequest appInfo);


    @Headers("Content-Type: application/json")
    @POST("App/AppUserSignup")
    Call<LoginResponse> AppUserSignup(@Body SignupRequest appInfo);

    @Headers("Content-Type: application/json")
    @POST("App/GetAvailablePackages")
    Call<UpgradePackageResponse> GetAvailablePackages(@Body BalanceRequest balanceRequest);

    @Headers("Content-Type: application/json")
    @POST("App/UpgradePackage")
    Call<UpgradePackageResponse> UpgradePackage(@Body UpgradePackageRequest upgradePackageRequest);

    @Headers("Content-Type: application/json")
    @POST("App/AppFOSRetailerList")
    Call<AppUserListResponse> FOSRetailerList(@Body FosAppUserListRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/GetRoleForReferral")
    Call<GetRoleForReferralResponse> GetRoleForReferral(@Body NunberListRequest appInfo);

    @Headers("Content-Type: application/json")
    @POST("App/ValidateOTP")
    Call<LoginResponse> ValidateOTP(@Body OtpRequest appInfo);

    @Headers("Content-Type: application/json")
    @POST("App/ValidateGAuthPIN")
    Call<LoginResponse> ValidateGAuthPIN(@Body OtpRequest appInfo);

    @Headers("Content-Type: application/json")
    @POST("App/GetBalance")
    Call<BalanceResponse> Balancecheck(@Body BalanceRequest appInfo);

    @Headers("Content-Type: application/json")
    @POST("App/GetOpTypes")
    Call<OpTypeResponse> GetOpTypes(@Body BalanceRequest appInfo);


    @Headers("Content-Type: application/json")
    @POST("App/Transaction")
    Call<RechargeCResponse> Recharge(@Body RechargeRequest rechargeRequest);

    @Headers("Content-Type: application/json")
    @POST("App/Logout")
    Call<RechargeCResponse> Logout(@Body LogoutRequest request);


    @Headers("Content-Type: application/json")
    @POST("App/AEPSReport")
    Call<RechargeReportResponse> AEPSReport(@Body RechargeReportRequest rechargeReportRequest);

    @Headers("Content-Type: application/json")
    @POST("App/DTHCustomerInfo")
    Call<DTHInfoResponse> DTHCustomerInfo(@Body ROfferRequest dthInfoRequest);

    @Headers("Content-Type: application/json")
    @POST("App/GetRNPDTHCustInfo")
    Call<DTHInfoResponse> GetRNPDTHCustInfo(@Body ROfferRequest dthInfoRequest);

    @Headers("Content-Type: application/json")
    @POST("App/GetOperatorOptionals")
    Call<OperatorOptionalResponse> GetOperatorOptionals(@Body OptionalOperatorRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/GetTargetAchieved")
    Call<AppUserListResponse> GetTargetAchieved(@Body AchieveTargetRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/UserDaybook")
    Call<AppUserListResponse> UserDaybook(@Body UserDayBookRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/UserDaybookDMR")
    Call<AppUserListResponse> UserDaybookDmt(@Body UserDayBookRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/DTHSimplePlanInfo")
    Call<DthPlanInfoResponse> DTHSimplePlanInfo(@Body ROfferRequest dthInfoRequest);

    @Headers("Content-Type: application/json")
    @POST("App/GetDTHSimplePlan")
    Call<DthPlanInfoAllResponse> GetDTHSimplePlanInfo(@Body ROfferRequest dthInfoRequest);

    @Headers("Content-Type: application/json")
    @POST("App/GetDTHChannelList")
    Call<DthPlanChannelAllResponse> GetDTHChannelList(@Body DTHChannelPlanInfoRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/GetDTHPlanListByLanguage")
    Call<DthPlanInfoResponse> GetDTHPlanListByLanguage(@Body DthPlanLanguageWiseRequest dthInfoRequest);

    @Headers("Content-Type: application/json")
    @POST("App/GetDTHPlanByLang")
    Call<DthPlanInfoAllResponse> GetDTHPlanByLang(@Body DthPlanLanguageWiseRequest dthInfoRequest);

    @Headers("Content-Type: application/json")
    @POST("/App/DTHChannelPlanInfo")
    Call<DthPlanInfoResponse> DTHChannelPlanInfo(@Body DTHChannelPlanInfoRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/ROffer")
    Call<RofferResponse> ROffer(@Body ROfferRequest rOfferRequest);

    @Headers("Content-Type: application/json")
    @POST("App/GetRNPRoffer")
    Call<RofferResponse> GetRNPRoffer(@Body ROfferRequest rOfferRequest);

    @Headers("Content-Type: application/json")
    @POST("App/AppGenerateOrderForUPI")
    Call<RechargeReportResponse> AppGenerateOrderForUPI(@Body GenerateOrderForUPIRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/SimplePlan")
    Call<ResponsePlan> Rechageplans(@Body PlanRequest rOfferRequest);

    @Headers("Content-Type: application/json")
    @POST("App/RechSimplePlan")
    Call<ResponsePlan> RechagePlansUpdated(@Body PlanRequest rOfferRequest);

    @Headers("Content-Type: application/json")
    @POST("App/RechargeReport")
    Call<RechargeReportResponse> RechargeReport(@Body RechargeReportRequest rechargeReportRequest);

    @Headers("Content-Type: application/json")
    @POST("App/IncentiveDetail")
    Call<AppUserListResponse> IncentiveDetail(@Body IncentiveDetailRequest incentiveDetailRequest);

    @Headers("Content-Type: application/json")
    @POST("App/ResendOTP")
    Call<BasicResponse> ResendOTP(@Body ResendOtpRequest resendOtpRequest);


    @Headers("Content-Type: application/json")
    @POST("App/LastRechargeReport")
    Call<RechargeReportResponse> LastRechargeReport(@Body RechargeReportRequest rechargeReportRequest);

    /*@Headers("Content-Type: application/json")
    @POST("App/RecentRecharge")
    Call<RechargeReportResponse> LastRechargeReport(@Body RecentRechargeRequest rechargeReportRequest);*/


    @Headers("Content-Type: application/json")
    @POST("App/FundOrderReport")
    Call<RechargeReportResponse> FundOrderReport(@Body LedgerReportRequest rechargeReportRequest);


    @Headers("Content-Type: application/json")
    @POST("App/LedgerReport")
    Call<RechargeReportResponse> LedgerReport(@Body LedgerReportRequest rechargeReportRequest);

    @Headers("Content-Type: application/json")
    @POST("App/FundDCReport")
    Call<RechargeReportResponse> FundDCReport(@Body FundDCReportRequest fundDCReportRequest);

    @Headers("Content-Type: application/json")
    @POST("App/GetWalletType")
    Call<WalletTypeResponse> GetWalletType(@Body BalanceRequest AppFundOrder);


    @Headers("Content-Type: application/json")
    @POST("App/RefundLog")
    Call<AppUserListResponse> RefundLog(@Body RefundLogRequest fundDCReportRequest);

    @Headers("Content-Type: application/json")
    @POST("App/FundRequestTo")
    Call<FundreqToResponse> FundRequestTo(@Body BalanceRequest fundDCReportRequest);


    @Headers("Content-Type: application/json")
    @POST("App/GetBankAndPaymentMode")
    Call<GetBankAndPaymentModeResponse> GetBankAndPaymentMode(@Body BalanceRequest fundDCReportRequest);

    @Headers("Content-Type: application/json")
    @POST("App/AppFundOrder")
    Call<GetBankAndPaymentModeResponse> AppFundOrder(@Body BalanceRequest AppFundOrder);

    @Multipart
    @POST("App/AppFundOrder")
    Call<GetBankAndPaymentModeResponse> AppFundOrder(@Part MultipartBody.Part file, @Part("UserFundRequest") RequestBody userRequest);

    @Headers("Content-Type: application/json")
    @POST("App/MyCommission")
    Call<RechargeReportResponse> MyCommission(@Body BalanceRequest AppFundOrder);

    @Headers("Content-Type: application/json")
    @POST("App/DisplayCommission")
    Call<SlabCommissionResponse> DisplayCommission(@Body BalanceRequest AppFundOrder);


    @Headers("Content-Type: application/json")
    @POST("App/GetSender")
    Call<CreateSenderResponse> GetSender(@Body GetSenderRequest getSenderRequest);

    @Headers("Content-Type: application/json")
    @POST("App/GetSenderP")
    Call<CreateSenderResponse> GetSenderNew(@Body GetSenderRequestNew getSenderRequest);//CreateSenderResponse

    @Headers("Content-Type: application/json")
    @POST("App/CreateSenderP")
    Call<RechargeReportResponse> CreateSenderNew(@Body GetSenderRequestNew createsenderRequest);

    @Headers("Content-Type: application/json")
    @POST("App/VerifySenderP")
    Call<RechargeReportResponse> VerifySenderNew(@Body GetSenderRequestNew createsenderRequest);

    @Headers("Content-Type: application/json")
    @POST("App/GetBeneficiaryP")
    Call<RechargeReportResponse> GetBeneficiaryNew(@Body GetSenderRequestNew balanceRequest);

    @Headers("Content-Type: application/json")
    @POST("App/VerifyAccountP")
    Call<RechargeReportResponse> VerifyAccountNew(@Body GetSenderRequestNew balanceRequest);

    @Headers("Content-Type: application/json")
    @POST("App/AddBeneficiaryP")
    Call<RechargeReportResponse> AddBeneficiaryNew(@Body GetSenderRequestNew balanceRequest);

    @Headers("Content-Type: application/json")
    @POST("App/GetChargedAmountP")
    Call<RechargeReportResponse> GetChargedAmountNew(@Body GetChargedAmountRequeat getChargedAmountRequeat);

    @Headers("Content-Type: application/json")
    @POST("App/SendMoneyP")
    Call<RechargeReportResponse> SendMoneyNew(@Body SendMoneyRequest sendMoneyRequest);

    @Headers("Content-Type: application/json")
    @POST("App/DeleteBeneficiaryP")
    Call<RechargeReportResponse> DeleteBeneficiaryNew(@Body GetSenderRequestNew sendMoneyRequest);

    @Headers("Content-Type: application/json")
    @POST("App/GetCallMeUserReq")
    Call<BasicResponse> GetCallMeUserReq(@Body CallBackRequest getSenderRequest);

    @Headers("Content-Type: application/json")
    @POST("App/CreateSender")
    Call<RechargeReportResponse> CreateSender(@Body GetSenderRequest createsenderRequest);

    @Headers("Content-Type: application/json")
    @POST("App/VerifySender")
    Call<RechargeReportResponse> VerifySender(@Body GetSenderRequest createsenderRequest);

    @Headers("Content-Type: application/json")
    @POST("App/AppUserReffDetail")
    Call<AppUserReffDetailResponse> AppUserReffDetail(@Body AppUserReffDetailRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/AppUserRegistraion")
    Call<BasicResponse> AppUserRegistraion(@Body AppUserRegisterRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/GetBankList")
    Call<BankListResponse> GetBankList(@Body BalanceRequest balanceRequest);

    @Headers("Content-Type: application/json")
    @POST("App/AddBeneficiary")
    Call<RechargeReportResponse> AddBeneficiary(@Body GetSenderRequest balanceRequest);

    @Headers("Content-Type: application/json")
    @POST("App/VerifyAccount")
    Call<RechargeReportResponse> VerifyAccount(@Body GetSenderRequest balanceRequest);

    @Headers("Content-Type: application/json")
    @POST("App/GetRealLapuCommission")
    Call<AppUserListResponse> GetRealLapuCommission(@Body LapuRealCommissionRequest balanceRequest);

    @Headers("Content-Type: application/json")
    @POST("App/GetCalculatedCommission")
    Call<AppUserListResponse> GetCalculatedCommission(@Body LapuRealCommissionRequest balanceRequest);

    @Headers("Content-Type: application/json")
    @POST("App/GetBeneficiary")
    Call<RechargeReportResponse> GetBeneficiary(@Body GetSenderRequest balanceRequest);

    @Headers("Content-Type: application/json")
    @POST("App/GetChargedAmount")
    Call<RechargeReportResponse> GetChargedAmount(@Body GetChargedAmountRequeat getChargedAmountRequeat);

    @Headers("Content-Type: application/json")
    @POST("App/SendMoney")
    Call<RechargeReportResponse> SendMoney(@Body SendMoneyRequest sendMoneyRequest);

    @Headers("Content-Type: application/json")
    @POST("App/DeleteBeneficiary")
    Call<RechargeReportResponse> DeleteBeneficiary(@Body GetSenderRequest sendMoneyRequest);


    @Headers("Content-Type: application/json")
    @POST("App/GetDMTReceipt")
    Call<DMTReceiptResponse> GetDMTReceipt(@Body GetDMTReceiptRequest sendMoneyRequest);

    @Headers("Content-Type: application/json")
    @POST("App/DMTReport")
    Call<RechargeReportResponse> DMTReport(@Body DmrRequest dmrRequest);


    @Headers("Content-Type: application/json")
    @POST("App/AppUserChildRoles")
    Call<AppUserListResponse> AppUserChildRoles(@Body AppUserListRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/FundOrderPending")
    Call<AppUserListResponse> FundOrderPending(@Body AppUserListRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/AppFundTransfer")
    Call<AppUserListResponse> AppFundTransfer(@Body FundTransferRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/AppFundReject")
    Call<AppUserListResponse> AppFundReject(@Body FundTransferRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/ChangeUserStatus")
    Call<AppUserListResponse> ChangeUserStatus(@Body FundTransferRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/AppUserList")
    Call<AppUserListResponse> AppUserList(@Body AppUserListRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/PGatewayTransaction")
    Call<GatwayTransactionResponse> GatewayTransaction(@Body GatewayTransactionRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/UPIPaymentUpdate")
    Call<InitiateUPIResponse> UPIPaymentUpdate(@Body UpdateUPIRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/PayTMTransactionUpdate")
    Call<BasicResponse> PayTMTransactionUpdate(@Body PayTMTransactionUpdateRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/AggrePayTransactionUpdate")
    Call<BasicResponse> AggrePayTransactionUpdate(@Body AggrePayTransactionUpdateRequest request);


    @Headers("Content-Type: application/json")
    @POST("App/GetAppNews")
    Call<AppUserListResponse> GetAppNews(@Body NewsRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/GetAppBanner")
    Call<AppUserListResponse> GetAppBanner(@Body BasicRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/GetAppRefferalContent")
    Call<AppUserListResponse> GetAppRefferalContent(@Body BasicRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/ChoosePaymentGateway")
    Call<AppUserListResponse> ChoosePaymentGateway(@Body BasicRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/GetProfile")
    Call<GetUserResponse> GetProfile(@Body BasicRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/UpdateProfile")
    Call<BasicResponse> UpdateProfile(@Body UpdateUserRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/AppDocumentDetails")
    Call<UpdateKycResponse> UpdateKycApi(@Body BasicRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/UpdateFCMID")
    Call<BasicResponse> UpdateFCMID(@Body UpdateFcmRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/ForgetPassword")
    Call<BasicResponse> ForgetPassword(@Body LoginRequest request);


    @Headers("Content-Type: application/json")
    @POST("App/UpdateKYCStatus")
    Call<UpdateKycResponse> UpdateKYCStatus(@Body UpdateKycStatusRequest request);

    /*@Headers("Content-Type: application/json")
    @POST("App/UploadDocs")
    Call<UpdateKycResponse> UploadDocs(@Field("userRequest") String userRequest,
                                       @Field("file") String UserId);*/

    @Multipart
    @POST("App/UploadDocs")
    Call<BasicResponse> UploadDocs(@Part MultipartBody.Part file, @Part("userRequest") RequestBody userRequest);


    @Headers("Content-Type: application/json")
    @POST("App/DisplayCommissionLvl")
    Call<SlabCommissionResponse> DisplayCommissionLvl(@Body BasicRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/GetAppNotification")
    Call<AppUserListResponse> GetAppNotification(@Body BasicRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/GetActiveSerive")
    Call<AppUserListResponse> GetActiveSerive(@Body BasicRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/GetCompanyProfile")
    Call<AppUserListResponse> GetCompanyProfile(@Body BasicRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/FetchBill")
    Call<FetchBillResponse> FetchBill(@Body FetchBillRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/ChangePinOrPassword")
    Call<RechargeReportResponse> ChangePinOrPassword(@Body ChangePinPasswordRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/RefundRequest")
    Call<RefundRequestResponse> RefundRequest(@Body RefundRequestRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/SummaryDashboard")
    Call<Summary> SummaryDashboard(@Body BalanceRequest appInfo);

    @POST("App/ChangeDFStatus")
    Call<DFStatusResponse> ChangeDFStatus(@Body DFStatusRequest createsenderRequest);

    @Headers("Content-Type: application/json")
    @POST("/App/CallOnboarding")
    Call<OnboardingResponse> CallOnboarding(@Body OnboardingRequest onboardingRequest);


    @Headers("Content-Type: application/json")
    @POST("App/GetPopupAfterLogin")
    Call<BalanceResponse> GetPopupAfterLogin(@Body BalanceRequest appInfo);


    @Headers("Content-Type: application/json")
    @POST("App/PSATransaction")
    Call<AppUserListResponse> GetAppPurchageToken(@Body PurchaseTokenRequest request);

    @POST("App/GetTransactionMode")
    Call<TransactionModeResponse> GetTransactionMode(@Body BalanceRequest balanceRequest);

    @POST("App/MoveToWallet")
    Call<TransactionModeResponse> MoveToWallet(@Body MoveToWalletRequest moveToWalletRequest);

    @POST("App/MakeW2RRequest")
    Call<RefundRequestResponse> MakeW2RRequest(@Body W2RRequest w2RRequest);

    @POST("App/WTRLog")
    Call<AppUserListResponse> WTRLog(@Body RefundLogRequest wTRLogRequest);

    @Headers("Content-Type: application/json")
    @POST("App/GetHLRLookUp")
    Call<GetHLRLookUPResponse> GetHLRLookUp(@Body GetHLRLookUpRequest rechargeReportRequest);

    @Headers("Content-Type: application/json")
    @POST("App/GetBank")
    Call<GetBankAndPaymentModeResponse> GetBank(@Body BalanceRequest AppFundOrder);

    @Headers("Content-Type: application/json")
    @POST("App/DTHHeavyRefresh")
    Call<DthPlanInfoResponse> DTHHeavyRefresh(@Body HeavyrefreshRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/GetRNPDTHHeavyRefresh")
    Call<DthPlanInfoResponse> GetRNPDTHHeavyRefresh(@Body HeavyrefreshRequest request);


    //Auto Billing

    @Headers("Content-Type: application/json")
    @POST("App/AutoBillingUpdateApp")
    Call<AutoBillingUpdateAppResponse> AutoBillingUpdateApp(@Body AutoBillingUpdateAppRequest request);


    @Headers("Content-Type: application/json")
    @POST("App/intiateUPI")
    Call<InitiateUPIResponse> intiateUPI(@Body InitiateUPIRequest request);


    @Headers("Content-Type: application/json")
    @POST("App/GetVADetail")
    Call<GetVAResponse> GetVADetail(@Body BasicRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/RSlabRangDetail")
    Call<SlabRangeDetailResponse> SlabRangDetail(@Body SlabRangeDetailRequest AppFundOrder);


    @Headers("Content-Type: application/json")
    @POST("App/CheckFlagsEmail")
    Call<BasicResponse> CheckFlagsEmail(@Body BasicRequest appInfo);

    @Headers("Content-Type: application/json")
    @POST("App/SendEmailVerification")
    Call<BasicResponse> SendEmailVerification(@Body BasicRequest appInfo);

    @Headers("Content-Type: application/json")
    @POST("App/SaveSocialAlertSetting")
    Call<BasicResponse> SaveSocialAlertSetting(@Body SubmitSocialDetailsRequest appInfo);

    @Headers("Content-Type: application/json")
    @POST("App/ASPayCollect")
    Call<AppUserListResponse> ASPayCollect(@Body ASPayCollectRequest request);


    @Headers("Content-Type: application/json")
    @POST("App/GetASCollectBank")
    Call<BankListResponse> GetASCollectBank(@Body BalanceRequest balanceRequest);


    @Headers("Content-Type: application/json")
    @POST("App/AccStmtAndColl")
    Call<FosAccStmtAndCollReportResponse> AccStmtAndColl(@Body FosAccStmtAndCollReportRequest FosAccStmtAndCollReportRequest);

    @Headers("Content-Type: application/json")
    @POST("App/GeUserCommissionRate")
    Call<BasicResponse> GeUserCommissionRate(@Body BasicRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/AppGetAM")
    Call<AppGetAMResponse> AppGetAM(@Body AppGetAMRequest request);

    @Multipart
    @POST("App/UploadProfile")
    Call<BasicResponse> UploadProfile(@Part MultipartBody.Part file, @Part("userRequest") RequestBody userRequest);

    @Headers("Content-Type: application/json")
    @POST("App/AppUpdateBank")
    Call<BasicResponse> UpdateBank(@Body UpdateUserRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/GetAccountOpeningBanner")
    Call<AccountOpenListResponse> GetAccountOpeningList(@Body AccountOpenListRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/MapQRToUser")
    Call<BasicResponse> MapQRToUser(@Body MapQRToUserRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/ChangeRealAPIStatus")
    Call<BasicResponse> ChangeRealAPIStatus(@Body RealApiChangeRequest createsenderRequest);


    @Headers("Content-Type: application/json")
    @POST("App/GetASSumm")
    Call<FosAccStmtAndCollReportResponse> GetASSumm(@Body FosAccStmtAndCollReportRequest request);


    //Dth subscription

    @Headers("Content-Type: application/json")
    @POST("/App/GetDTHPackage")
    Call<GetDthPackageResponse> GetDTHPackage(@Body GetDthPackageRequest onboardingRequest);

    @Headers("Content-Type: application/json")
    @POST("/App/DTHChannelByPackageID")
    Call<GetDthPackageResponse> DTHChannelByPackageID(@Body GetDthPackageChannelRequest request);


    @Headers("Content-Type: application/json")
    @POST("App/DTHSubscription")
    Call<RechargeCResponse> DTHSubscription(@Body DTHSubscriptionRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/GetPincodeArea")
    Call<PincodeAreaResponse> GetPincodeArea(@Body PincodeAreaRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/DTHSubscriptionReport")
    Call<DthSubscriptionReportResponse> DTHSubscriptionReport(@Body DthSubscriptionReportRequest rechargeReportRequest);


    @Headers("Content-Type: application/json")
    @POST("App/DoUPIPayment")
    Call<RechargeReportResponse> doUPIPayment(@Body UPIPaymentReq upiPaymentReq);

    @Headers("Content-Type: application/json")
    @POST("App/GetSettlementAccount")
    Call<SettlementAccountResponse> GetSettlementAccount(@Body BasicRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/UpdateSettlementAccount")
    Call<BasicResponse> UpdateSettlementAccount(@Body UpdateSettlementAccountRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/DeleteSettlementAcount")
    Call<BasicResponse> DeleteSettlementAcount(@Body UpdateSettlementAccountRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/ToggleDefaulSettlementAcount")
    Call<BasicResponse> ToggleDefaulSettlementAcount(@Body UpdateSettlementAccountRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/VerifySettlementAccountOfUser")
    Call<BasicResponse> VerifySettlementAccountOfUser(@Body UpdateSettlementAccountRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/UpdateUTRByUser")
    Call<BasicResponse> UpdateUTRByUser(@Body UpdateSettlementAccountRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/MoveToBankReport")
    Call<RechargeReportResponse> MoveToBankReport(@Body MoveToBankReportRequest dmrRequest);

    @Headers("Content-Type: application/json")
    @POST("App/RealTimeCommission")
    Call<SlabRangeDetailResponse> RealTimeCommission(@Body SlabRangeDetailRequest AppFundOrder);

    @Headers("Content-Type: application/json")
    @POST("App/CashFreeTransactionUpdate")
    Call<BasicResponse> CashFreeTransactionUpdate(@Body PayTMTransactionUpdateRequest request);

    @Headers("Content-Type: application/json")
    @POST("/App/NSDLInitiate")
    Call<JsonObject> GetNsdlPanMitra(@Body NsdlRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/TransactionReceipt")
    Call<TransactionReceiptResponse> getTransactionReceipt(@Body ReciptRequest request);

    @Headers("Content-Type: application/json")
    @POST("App/TransactionReceipt")
    Call<CmsApiResponse> CMSTransactionReceipt(@Body CmsRequest cmsRequestPojo);

    @Headers("Content-Type: application/json")
    @POST("App/GetCompanyProfile")
    Call<CompanyProfileResponse> getCompanyProfile(@Body BalanceRequest appInfo);
}

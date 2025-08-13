package com.solution.brothergroup.Util;

/**
 * Created by Vishnu on 18-01-2017.
 */
public enum ApplicationConstant {

    INSTANCE;
    /*https://www.shantilex.in/
    public String Domain = "srecharge.net";*/

    public String Domain = "thebrothergroup.com";
    public String baseUrl = "http://" + Domain + "/";
    public String inviteUrl = baseUrl +"InviteApp/";
    public String baseProfilePicUrl = baseUrl + "Image/Profile/";
    public String baseIconUrl = baseUrl + "/Image/operator/";
    public String pgIconUrl = baseUrl + "/Image/PG/";
    public String baseAppIconUrl = baseUrl + "Image/Website/";
    public String basebankLogoUrl = baseUrl + "/image/BankLogo/";
    public String basebankBannerUrl = baseUrl + "/Image/BannerBank/";

    public String baseQrLogoUrl = baseUrl + "/image/BankQR/";
    public String psaUrl = "https://www.psaonline.utiitsl.com/psaonline/";

    public String APP_ID = "ROUNDPAYAPPID13APR20191351";
    public String regNotification = "notification";

    public String dmtOperatorListPref = "dmtOperatorListPref";
    public String isDMTWithPipePref = "isDMTWithPipePref";

    public String baseQrImageUrl = baseUrl + "/App/qrforupi.png?&amount=0&userID=";

    public String IsUserReferralDataPref = "IsUserReferralDataPref";
    public String prefRefferalDataPref = "prefRefferalDataPref";
    public String operatorListPref = "operatorList";
    public String beneficiaryListPref = "beneficiaryList";
    public String senderInfoPref = "senderInfo";
    public String senderNumberPref = "senderNumber";
    public String senderNamePref = "senderName";
    public String bankListPref = "bankList";
    public String bankListFOSPref = "bankListFOSPref";
    public String commList = "commList";
    public String walletType = "walletType";
    public String notificationListPref = "notificationListPref";
    public String slabListPref = "slabList";
    public String regFCMKeyPref = "regFCMkey";
    public String regKeyIdPref = "regkeyid";
    public String regKeyStatusPref = "regkeystatus";
    public String servicesPref = "servicesPref";
    public String bankDetailListPref = "bankDetailListPref";
    public String OTP = "OTP_PREF";
    public String SessionID = "SESSION_ID_PREF";
    public String UMail = "UMAIL_PREF";
    public String UMobile = "UMOBILE_PREF";
    public String UPassword = "UPASSWORD_PREF";
    public String UName = "UNAME_PREF";
    public String IsExist = "ISEXIST_PREF";
    public String PinPasscode = "PIN_PASSCODE";
    public String PrepaidWallet = "PREPAID_WALLET";
    public String UtilityWallet = "UTILITY_WALLET";
    public String Key = "KEY";
    public String RoleId = "RoleId";
    public String BusinessModuleID = "BusinessModuleID";
    public String supportEmail = "supportEmail";
    public String supportNumber = "supportNumber";
    public String prefapi = "prefapi";
    public String icon = "icon";
    public String IsLogin = "IsLogin";
    public String PinPasscodeFlag = "PIN_PASSCODE_FLAG";
    public String prefNotificationPref = "notificationPref";
    //services_pref/////////////////////////////////////
    public String Services_Postpaid = "Services_Postpaid";
    public String Services_Prepaid = "Services_Prepaid";
    public String Services_Landline = "Services_Landline";
    public String Services_DTH_Datacard = "Services_DTH_Datacard";
    public String Services_Electricity = "Services_Electricitya";
    public String Services_DMR = "Services_DMR";
    public String Services_Hotel = "Services_Hotel";
    public String Services_Flight = "Services_Flight";
    public String Services_InsurancePremium = "Services_InsurancePremium";
    public String Services_Gas = "Services_Gas";
    public String Services_WaterBills = "Services_WaterBills";
    public String Services_DTHConnections = "Services_DTHConnections";
    public String Notification = "Notification";
    public String Version = "Version";
    //AEPS 2

    public String USERID = "USERID";
    public String MERCHANTID = "MERCHANTID";
    public String USER_OUTLETID = "USER_OUTLETID";
    public String OutletRegistartionres = "OutletRegistartionres";
    public String setRemaining = "setRemaining";
    public String senderBalance = "senderBalance";
    ///
    public String IsLoginPref = "IS_LOGIN_PREF";
    public String LoginPref = "LOGIN_PREF";
    public String UserDetailPref = "USER_DETAIL_PREF";
    public String OpTypePref = "OPTYPE_PREF";
    public String balancePref = "balanceCheck";
    public String activeServicePref = "activeServicePref";
    public String isUpi = "isUpiPref";
    public String isUpiQR = "isUpiQRPref";
    public String isECollectEnable = "isECollectEnablePref";
    public String isPaymentGatway = "isPaymentGatwayPref";
    public String contactUsPref = "contactUsCompanyPref";
    public String FundreqTo = "FundreqTo";
    public String companyPref = "companyPref";

    public String prefNameLoginPref = "roundPayPrefLogin";
    public String numberListPref = "numberListPref";
    public String prefNamePref = "roundPayPref";
    public String regRecentLoginPref = "regRecentLoginPref";

    public String DoubleFactorPref = "DOUBLE_FACTOR_PREF";
    public String PinRequiredPref = "PIN_REQUIRED_PREF";
    public String psaIdPref = "psaIdPref";
    public String isLookUpFromAPIPref = "isLookUpFromAPIPref";
    public String isDTHInfoAutoCallPref = "isDTHInfoAutoCallPref";
    public String isFlatCommissionPref = "isFlatCommissionPref";
    public String isHeavyRefreshPref = "isHeavyRefreshPref";
    public String isAutoBillingPref = "isAutoBillingPref";
    public String isROfferPref = "isROfferPref";
    public String isDTHInfoPref = "isDTHInfoPref";
    public String isShowPDFPlanPref = "isShowPDFPlanPref";
    public String isRealAPIPerTransactionPref = "isRealAPIPerTransactionPref";
    public String dayBookDataPref = "dayBookDataPref";
    public String totalTargetDataPref = "totalTargetDataPref";
    public String bannerDataPref = "bannerDataPref";
    public String newsDataPref = "newsDataPref";
    public String balanceLowTimePref = "balanceLowTimePref";
    public String isTargetShowPref = "isTargetShowPref";
    public String UserReferralPref = "UserReferralPref";
    public String bankAEPSListPref = "bankAEPSListPref";
    public String appLogoUrlPref = "appLogoUrlPref";
    public String Paynear_USB = "Paynear_USB";
    public String SELECTED_AEPS_DEVICE_PREF = "SELECTED_AEPS_DEVICE_PREF";
    public String isEmailVerifiedPref = "isEmailVerifiedPref";
    public String isSocialLinkSavedPref = "isSocialLinkSavedPref";
    public String SocialorEmailDialogTimePref = "SocialorEmailDialogTimePref";
    public String ascReportPref = "ascReportPref";
    public String areaListPref = "areaListPref";
    public String IsRealApiPref = "IsRealApiPref";
    public String accountOpenListPref = "accountOpenListPref";
    public String voiceEnablePref = "voiceEnablePref";
    public final String CFStage = "PROD";
    public String prefNonRemovalPref = "prefNonRemovalPref";
    public String operatorListDataPref = "operatorListDataPref";
    public String WidPref = "WidPref";
}

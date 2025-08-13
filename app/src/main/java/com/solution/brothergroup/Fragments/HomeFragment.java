package com.solution.brothergroup.Fragments;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;
import static com.solution.brothergroup.usefull.DB.Table.MasterData.name;

import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.gson.Gson;
import com.paysprint.onboardinglib.activities.HostActivity;
import com.roundpay.emoneylib.EMoneyLoginActivity;
import com.roundpay.emoneylib.Object.MiniStatements;
import com.roundpay.emoneylib.Utils.KeyConstant;
import com.roundpay.shoppinglib.Shopping.Activity.ShopDashboardActivity;
import com.roundpay.shoppinglib.Util.KeyContants;
import com.solution.brothergroup.Activities.AccountOpenActivity;
import com.solution.brothergroup.Activities.AchievedTargetActivity;
import com.solution.brothergroup.Activities.CreateUserActivity;
import com.solution.brothergroup.Activities.DMRReport;
import com.solution.brothergroup.Activities.DisputeReport;
import com.solution.brothergroup.Activities.FundOrderPendingActivity;
import com.solution.brothergroup.Activities.FundRecReport;
import com.solution.brothergroup.Activities.FundReqReport;
import com.solution.brothergroup.Activities.LedgerReport;
import com.solution.brothergroup.Activities.MainActivity;
import com.solution.brothergroup.Activities.MoveToBankReportActivity;
import com.solution.brothergroup.Activities.PaymentRequest;
import com.solution.brothergroup.Activities.QrBankActivity;
import com.solution.brothergroup.Activities.SpecificRechargeReportActivity;
import com.solution.brothergroup.Activities.UserDayBookActivity;
import com.solution.brothergroup.AddMoney.AddMoneyActivity;
import com.solution.brothergroup.Aeps.UI.AEPSMiniStatementRPActivity;
import com.solution.brothergroup.Aeps.UI.AEPSReportActivity;
import com.solution.brothergroup.Aeps.UI.AEPSStatusRPActivity;
import com.solution.brothergroup.Aeps.dto.SdkDetail;
import com.solution.brothergroup.Api.Object.AssignedOpType;
import com.solution.brothergroup.Api.Object.Banners;
import com.solution.brothergroup.Api.Object.BcResponse;
import com.solution.brothergroup.Api.Object.OpTypeRollIdWiseServices;
import com.solution.brothergroup.Api.Object.OperatorList;
import com.solution.brothergroup.Api.Object.TargetAchieved;
import com.solution.brothergroup.Api.Object.UserDaybookReport;
import com.solution.brothergroup.Api.Request.BasicRequest;
import com.solution.brothergroup.Api.Response.AppGetAMResponse;
import com.solution.brothergroup.Api.Response.AppUserListResponse;
import com.solution.brothergroup.Api.Response.BalanceResponse;
import com.solution.brothergroup.Api.Response.OnboardingResponse;
import com.solution.brothergroup.AppUser.Activity.AccountStatementReportActivity;
import com.solution.brothergroup.AppUser.Activity.AppUserListActivity;
import com.solution.brothergroup.AppUser.Activity.ChannelReportActivity;
import com.solution.brothergroup.AppUser.Activity.FosAreaReportActivity;
import com.solution.brothergroup.AppUser.Activity.FosReportActivity;
import com.solution.brothergroup.AppUser.Activity.FosUserListActivity;
import com.solution.brothergroup.Authentication.dto.LoginResponse;
import com.solution.brothergroup.BuildConfig;
import com.solution.brothergroup.CMS.Activity.CmsReceiptActivity;
import com.solution.brothergroup.CommissionSlab.ui.CommissionScreen;
import com.solution.brothergroup.DMRNEW.ui.DMRLogin;
import com.solution.brothergroup.DMTWithPipe.customView.DMTCustomAlertDialog;
import com.solution.brothergroup.DMTWithPipe.networkAPI.UtilsMethodDMTPipe;
import com.solution.brothergroup.DMTWithPipe.ui.DMRLoginNew;
import com.solution.brothergroup.FingPayAEPS.AEPSWebConnectivity;
import com.solution.brothergroup.FingPayAEPS.FingPayAEPSDashBoardActivity;
import com.solution.brothergroup.FingPayAEPS.dto.InitiateMiniBankResponse;
import com.solution.brothergroup.Fragments.Adapter.HomeOptionListAdapter;
import com.solution.brothergroup.Fragments.Adapter.UpgradePackage;
import com.solution.brothergroup.Fragments.Adapter.WalletBalanceAdapter;
import com.solution.brothergroup.Fragments.dto.BalanceType;
import com.solution.brothergroup.Fragments.interfaces.RefreshBalanceCallBack;
import com.solution.brothergroup.Fragments.interfaces.RefreshCallBack;
import com.solution.brothergroup.MicroAtm.Activity.MicroATMStatusActivity;
import com.solution.brothergroup.MicroAtm.Activity.MicroAtmActivity;
import com.solution.brothergroup.MicroAtm.Activity.MiniAtmRecriptActivity;
import com.solution.brothergroup.MoveToWallet.ui.MoveToWalletNew;
import com.solution.brothergroup.NSDL.NSDLActivity;
import com.solution.brothergroup.PSA.UI.PanApplicationActivity;
import com.solution.brothergroup.Paynear.Activity.PaynearActivationActivity;
import com.solution.brothergroup.QRScan.Activity.QRScanActivity;
import com.solution.brothergroup.QRScan.Activity.UPIPayActivity;
import com.solution.brothergroup.R;
import com.solution.brothergroup.RECHARGEANDBBPS.UI.RechargeActivity;
import com.solution.brothergroup.RECHARGEANDBBPS.UI.RechargeHistory;
import com.solution.brothergroup.RECHARGEANDBBPS.UI.RechargeProviderActivity;
import com.solution.brothergroup.Util.ApiClient;
import com.solution.brothergroup.Util.ApplicationConstant;
import com.solution.brothergroup.Util.ChangePassUtils;
import com.solution.brothergroup.Util.CustomAlertDialog;
import com.solution.brothergroup.Util.EndPointInterface;
import com.solution.brothergroup.Util.GetLocation;
import com.solution.brothergroup.Util.OpTypeResponse;
import com.solution.brothergroup.Util.UtilMethods;
import com.solution.brothergroup.W2RRequest.report.W2RHistory;
import com.solution.brothergroup.usefull.CustomLoader;
import com.tapits.ubercms_bc_sdk.LoginScreen;
import com.tapits.ubercms_bc_sdk.utils.Constants;

import org.egram.aepslib.DashboardActivity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;

public class HomeFragment extends Fragment implements RefreshCallBack, RefreshBalanceCallBack {

    public static TextView[] mDotsText;
    public ArrayList<Banners> bannerList = new ArrayList<>();
    CustomLoader loader;
    BalanceResponse balanceCheckResponse;
    List<AssignedOpType> mAssignedOpTypesActiveService = new ArrayList<>();
    List<AssignedOpType> mAssignedOpTypesReportService = new ArrayList<>();

    ChangePassUtils mChangePassUtils;
    boolean isScreenOpen;
    TextView fosMsg, moveBalanceTv;
    HomeOptionListAdapter mDashboardOptionListAdapter;
    RecyclerView rechargeRecyclerView;
    TextView newsWeb;
    View newsCard;
    ImageView Share, playstorelink;
    ViewPager mViewPager;
    View pagerContainer;
    CustomPagerAdapter mCustomPagerAdapter;
    Handler handler;
    Integer mDotsCount;
    CardView card_ll_fos;
    LinearLayout ll_fosUserlist, ll_FosFundReport, ll_fos;
    LinearLayout dotsCount;
    ArrayList<BalanceType> mBalanceTypes = new ArrayList<>();
    TextView titleTv, successAmountTv, failedAmountTv, pendingAmountTv, commissionAmountTv, todaySalesTv, salesTargetTv, remainTargetTv;
    private CustomAlertDialog customAlertDialog;
    private LoginResponse loginPrefResponse;
    String userRollId;
    private boolean isRechargeViewEnable;
    private int notificationCount;
    private int INTENT_NOTIFICATIONS = 538;
    private Runnable mRunnable;
    private boolean isDmtAvailable = false;
    View balanceView, serviceOptionView;
    boolean isLoaderShouldShow;
    private int pagerTop, pagerleft;
    private WalletBalanceAdapter mWalletBalanceProfileAdapter;
    private String fromDate, toDate;
    View tragetView;
    LinearLayout summaryDashboard;
    private DMTCustomAlertDialog dmtCustomAlertDialog;
    private boolean isDMTWithPipe;

    private boolean isAddMoneyEnable, isPaymentGatway, isUPI;

    int USER_ID, OUTLET_ID, PARTNER_ID = 0, SDK_TYPE;
    String PIN = "";
    private SdkDetail sdkDetail;

    private ArrayList<MiniStatements> miniStatements;

    private boolean isECollectEnable;
    private int sDKType;
    private String matmOID;
    private int INTENT_MINI_ATM = 2531;
    private final Integer INTENT_MINI_ATM_RP = 5480;
    private final Integer INTENT_AEPS_RP = 2907;
    private int INTENT_CODE_APES = 520;
    private String aPIStatus = "";
    private SharedPreferences myPreferences;
    private AppGetAMResponse mAppGetAMResponse;
    private OpTypeRollIdWiseServices mActiveServiceData;
    private AppUserListResponse bannerData;
    private AppUserListResponse dayBookData;
    private AppUserListResponse achieveTargetData;
    private String forWhat = "";
    public GetLocation mGetLocation;
    private final Integer INTENT_CODE_CMS = 2908;
    private int sdkType;
    private SdkDetail mSdkDetail;
    private int INTENT_AEPS_PAYS_PRINT = 1002;
    private int INTENT_MINI_ATM_PAYS_PRINT = 1001;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for getActivity() fragment

        View v = inflater.inflate(R.layout.fragment_home, container, false);
        dmtCustomAlertDialog = new DMTCustomAlertDialog(getActivity(), true);

        UtilMethods.INSTANCE.setDashboardStatus(getActivity(), true);

        myPreferences = getActivity().getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);

        try {
            customAlertDialog = ((MainActivity) getActivity()).customAlertDialog;
            if (customAlertDialog == null) {
                customAlertDialog = new CustomAlertDialog(getActivity(), true);
            }
        } catch (NullPointerException npe) {
            customAlertDialog = new CustomAlertDialog(getActivity(), true);
        } catch (Exception e) {
            customAlertDialog = new CustomAlertDialog(getActivity(), true);
        }

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
            loginPrefResponse = ((MainActivity) getActivity()).LoginPrefResponse;
            if (loginPrefResponse == null) {
                loginPrefResponse = new Gson().fromJson(UtilMethods.INSTANCE.getLoginPref(getActivity()), LoginResponse.class);
            }
        } catch (NullPointerException npe) {
            loginPrefResponse = new Gson().fromJson(UtilMethods.INSTANCE.getLoginPref(getActivity()), LoginResponse.class);
        } catch (Exception e) {
            loginPrefResponse = new Gson().fromJson(UtilMethods.INSTANCE.getLoginPref(getActivity()), LoginResponse.class);
        }
        userRollId = loginPrefResponse.getData().getRoleID();


        isDMTWithPipe = UtilsMethodDMTPipe.INSTANCE.getIsDMTWithPipe(getActivity());

        isUPI = myPreferences.getBoolean(ApplicationConstant.INSTANCE.isUpi, false);

        isECollectEnable = myPreferences.getBoolean(ApplicationConstant.INSTANCE.isECollectEnable, false);

        isPaymentGatway = myPreferences.getBoolean(ApplicationConstant.INSTANCE.isPaymentGatway, false);


        pagerTop = (int) getActivity().getResources().getDimension(R.dimen._5sdp);
        pagerleft = (int) getActivity().getResources().getDimension(R.dimen._6sdp);
        getID(v);

        //getMemorySize();
        UtilMethods.INSTANCE.AppPopup(getActivity(), null);
        return v;

    }

    @Override
    public void onStart() {
        super.onStart();
        if (!isScreenOpen) {
            isScreenOpen = true;

            if (userRollId.equalsIgnoreCase("3") || userRollId.equalsIgnoreCase("2")) {

                isRechargeViewEnable = true;
                titleTv.setText("Recharge & Pay Bills");
            } else {
                titleTv.setText("Reports");
                isRechargeViewEnable = false;
            }
            try {
                mActiveServiceData = ((MainActivity) getActivity()).mActiveServiceData;
                if (mActiveServiceData == null) {
                    mActiveServiceData = new Gson().fromJson(UtilMethods.INSTANCE.getActiveService(getActivity()), OpTypeRollIdWiseServices.class);
                }
            } catch (NullPointerException npe) {
                mActiveServiceData = new Gson().fromJson(UtilMethods.INSTANCE.getActiveService(getActivity()), OpTypeRollIdWiseServices.class);
            } catch (Exception e) {
                mActiveServiceData = new Gson().fromJson(UtilMethods.INSTANCE.getActiveService(getActivity()), OpTypeRollIdWiseServices.class);
            }
            setDashboardData(mActiveServiceData);

            SimpleDateFormat sdfToday = new SimpleDateFormat("dd MMM yyyy", Locale.US);
            fromDate = sdfToday.format(new Date());
            toDate = fromDate;

            mGetLocation = new GetLocation(getActivity(), loader);
            if (UtilMethods.INSTANCE.getLattitude == 0 || UtilMethods.INSTANCE.getLongitude == 0) {
                mGetLocation.startLocationUpdatesIfSettingEnable((lattitude, longitude) -> {
                    UtilMethods.INSTANCE.getLattitude = lattitude;
                    UtilMethods.INSTANCE.getLongitude = longitude;
                });
            }
            AppUserListResponse newsData = new Gson().fromJson(UtilMethods.INSTANCE.getNewsData(getActivity()), AppUserListResponse.class);
            if (newsData != null && newsData.getNewsContent() != null && newsData.getNewsContent().getNewsDetail() != null && !newsData.getNewsContent().getNewsDetail().isEmpty()) {
                newsWeb.setText(Html.fromHtml(newsData.getNewsContent().getNewsDetail() + ""));
                newsCard.setVisibility(View.VISIBLE);
            } else {
                newsCard.setVisibility(View.GONE);
            }
            try {
                bannerData = ((MainActivity) getActivity()).bannerData;
                if (bannerData == null) {
                    bannerData = new Gson().fromJson(UtilMethods.INSTANCE.getBannerData(getActivity()), AppUserListResponse.class);
                }
            } catch (NullPointerException npe) {
                bannerData = new Gson().fromJson(UtilMethods.INSTANCE.getBannerData(getActivity()), AppUserListResponse.class);
            } catch (Exception e) {
                bannerData = new Gson().fromJson(UtilMethods.INSTANCE.getBannerData(getActivity()), AppUserListResponse.class);
            }

            setBannerData(bannerData);

            try {
                dayBookData = ((MainActivity) getActivity()).dayBookData;
                if (dayBookData == null) {
                    dayBookData = new Gson().fromJson(UtilMethods.INSTANCE.getDayBookData(getActivity()), AppUserListResponse.class);
                }
            } catch (NullPointerException npe) {
                dayBookData = new Gson().fromJson(UtilMethods.INSTANCE.getDayBookData(getActivity()), AppUserListResponse.class);
            } catch (Exception e) {
                dayBookData = new Gson().fromJson(UtilMethods.INSTANCE.getDayBookData(getActivity()), AppUserListResponse.class);
            }

            dataDayBookParse(dayBookData);

            if (tragetView.getVisibility() == View.GONE) {
                try {
                    achieveTargetData = ((MainActivity) getActivity()).achieveTargetData;
                    if (achieveTargetData == null) {
                        achieveTargetData = new Gson().fromJson(UtilMethods.INSTANCE.getTotalTargetData(getActivity()), AppUserListResponse.class);
                    }
                } catch (NullPointerException npe) {
                    achieveTargetData = new Gson().fromJson(UtilMethods.INSTANCE.getTotalTargetData(getActivity()), AppUserListResponse.class);
                } catch (Exception e) {
                    achieveTargetData = new Gson().fromJson(UtilMethods.INSTANCE.getTotalTargetData(getActivity()), AppUserListResponse.class);
                }
                achieveTargetParse(achieveTargetData);
            }
            DashboardApi();
        }
    }

    @Override
    public void onBalanceRefresh(Object object) {
        balanceCheckResponse = (BalanceResponse) object;
        showBalanceData();
    }

   /* public String getMemorySize() {
        Context context = getActivity();

        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);

        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();

        activityManager.getMemoryInfo(memoryInfo);

        DecimalFormat twoDecimalForm = new DecimalFormat("#.##");

        String finalValue = "";
        long totalMemory = memoryInfo.totalMem;

        double kb = totalMemory / 1024.0;
        double mb = totalMemory / 1048576.0;
        double gb = totalMemory / 1073741824.0;
        double tb = totalMemory / 1099511627776.0;

        if (tb > 1) {
            finalValue = twoDecimalForm.format(tb);
        } else if (gb > 1) {
            finalValue = twoDecimalForm.format(gb);
        } else if (mb > 1) {
            finalValue = twoDecimalForm.format(mb);
        } else if (kb > 1) {
            finalValue = twoDecimalForm.format(mb);
        } else {
            finalValue = twoDecimalForm.format(totalMemory);
        }


        callBanner(finalValue);
//Toast.makeText(context, "Your Ram is"+finalValue, Toast.LENGTH_LONG).show();
        return finalValue;


    }*/

   /* private void callBanner(String ramSize) {

        float fixedSize = 2.0f;
        float size = Float.parseFloat(ramSize);
        int compare = Float.compare(size, fixedSize);
//Toast.makeText(getActivity(), "yo"+ ramSize, Toast.LENGTH_LONG).show();
        if (compare > 0) {
            BannerApi();
        }
    }*/

    private void showBalanceData() {
        mBalanceTypes.clear();
        if (balanceCheckResponse != null && balanceCheckResponse.getBalanceData() != null) {
            balanceView.setVisibility(View.VISIBLE);
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
            if (loginPrefResponse.isAccountStatement()) {
                mBalanceTypes.add(new BalanceType("Outstanding Wallet", balanceCheckResponse.getBalanceData().getOsBalance() + ""));
            }
            if (mBalanceTypes.size() > 1) {
                moveBalanceTv.setVisibility(View.VISIBLE);
            } else {
                moveBalanceTv.setVisibility(View.GONE);
            }
            mWalletBalanceProfileAdapter.notifyDataSetChanged();
        } else {

            String balanceResponse = myPreferences.getString(ApplicationConstant.INSTANCE.balancePref, "");
            balanceCheckResponse = new Gson().fromJson(balanceResponse, BalanceResponse.class);
            if (balanceCheckResponse != null && balanceCheckResponse.getBalanceData() != null) {
                showBalanceData();
            } else {
                UtilMethods.INSTANCE.Balancecheck(getActivity(), null, new UtilMethods.ApiCallBack() {
                    @Override
                    public void onSucess(Object object) {
                        balanceCheckResponse = (BalanceResponse) object;

                        if (balanceCheckResponse != null && balanceCheckResponse.getBalanceData() != null) {
                            showBalanceData();
                        }
                    }
                });
            }

        }

    }

    private void getID(View v) {
        loader = new CustomLoader(getActivity(), android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        Share = v.findViewById(R.id.Share);
        playstorelink = v.findViewById(R.id.playstorelink);
        titleTv = v.findViewById(R.id.title);
        newsWeb = v.findViewById(R.id.News);
        balanceView = v.findViewById(R.id.balanceView);
        serviceOptionView = v.findViewById(R.id.serviceOptionView);
        newsWeb.setSelected(true);
        fosMsg = v.findViewById(R.id.fosMsg);
        tragetView = v.findViewById(R.id.tragetView);
        if (UtilMethods.INSTANCE.getIsTargetShow(getActivity())) {
            tragetView.setVisibility(View.GONE);
        } else {
            tragetView.setVisibility(View.GONE);
        }
        ll_fos = v.findViewById(R.id.ll_fos);
        card_ll_fos = v.findViewById(R.id.card_ll_fos);
        ll_FosFundReport = v.findViewById(R.id.ll_FosFundReport);
        ll_fosUserlist = v.findViewById(R.id.ll_fosUserlist);
        moveBalanceTv = v.findViewById(R.id.moveBalanceTv);
        newsCard = v.findViewById(R.id.newsCard);
        summaryDashboard = v.findViewById(R.id.summaryDashboard);
        successAmountTv = v.findViewById(R.id.successAmount);
        failedAmountTv = v.findViewById(R.id.failedAmount);
        pendingAmountTv = v.findViewById(R.id.pendingAmount);
        commissionAmountTv = v.findViewById(R.id.commissionAmount);
        todaySalesTv = v.findViewById(R.id.todaySales);
        salesTargetTv = v.findViewById(R.id.salesTarget);
        remainTargetTv = v.findViewById(R.id.remainTarget);
        rechargeRecyclerView = v.findViewById(R.id.rechargeRecyclerView);
        rechargeRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        mViewPager = v.findViewById(R.id.pager);
        pagerContainer = v.findViewById(R.id.pagerContainer);
        dotsCount = v.findViewById(R.id.image_count);

        final ImageView refreshOperator = v.findViewById(R.id.refreshOperator);
        refreshOperator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UtilMethods.INSTANCE.isNetworkAvialable(getActivity())) {
                    RotateAnimation rotate = new RotateAnimation(0, 180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                    rotate.setDuration(500);
                    rotate.setRepeatCount(Animation.INFINITE);
                    rotate.setInterpolator(new LinearInterpolator());
                    refreshOperator.startAnimation(rotate);
                    UtilMethods.INSTANCE.NumberList(getActivity(), refreshOperator, null);
                } else {
                    UtilMethods.INSTANCE.NetworkError(getActivity(), getResources().getString(R.string.err_msg_network_title),
                            getResources().getString(R.string.err_msg_network));
                }
            }
        });
        Share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareIt();
            }
        });
        playstorelink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + BuildConfig.APPLICATION_ID)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID)));
                }
            }
        });

        ll_fosUserlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), FosUserListActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                getActivity().startActivity(i);
            }
        });
        ll_FosFundReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LedgerReport.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });


        moveBalanceTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MoveToWalletNew.class)
                        .putExtra("items", mBalanceTypes)
                        .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
            }
        });
        v.findViewById(R.id.viewDetailTarget).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AchievedTargetActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (mDotsText != null && mDotsText.length > 0) {
                    for (int i = 0; i < mDotsCount; i++) {
                        mDotsText[i].setTextColor(getResources().getColor(R.color.light_grey));
                    }
                    mDotsText[position].setTextColor(getResources().getColor(R.color.colorPrimary));
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        RecyclerView balanceRecyclerView = v.findViewById(R.id.balanceRecyclerView);
        balanceRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mWalletBalanceProfileAdapter = new WalletBalanceAdapter(getActivity(), mBalanceTypes);
        balanceRecyclerView.setAdapter(mWalletBalanceProfileAdapter);

        try {
            balanceCheckResponse = ((MainActivity) getActivity()).balanceCheckResponse;
        } catch (NullPointerException npe) {

        }
        showBalanceData();
    }


    private void shareIt() {
        if (Build.VERSION.SDK_INT >= 24) {
            try {
                Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                m.invoke(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            AppUserListResponse companyData = new Gson().fromJson(UtilMethods.INSTANCE.getCompanyProfile(getActivity()), AppUserListResponse.class);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.app_name));
//                String shareMessage = companyData.getCompanyProfile().getAddress() + "\n\nLet me recommend you this application\n\n";
//               /* String shareMessage = " MIEUX INFRACON LTD \n" +
//                        " B-720, Gopal Palace, 7th floor \n" +
//                        " Nr Nehrunagar BRTS Stand \n" +
//                        " S M Road, Nehrunagar, Ahmedabad - 380015\n" +
//                        " Ahmedabad,Gujarat\n\nLet me recommend you this application\n\n";*/
//                shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n\n";
//                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
//                startActivity(Intent.createChooser(shareIntent, "choose one"));

                String shareMessage = "";

                if (companyData.getCompanyProfile().getAddress() != null) {
                    shareMessage = companyData.getCompanyProfile().getAddress() + "\n\nLet me recommend you this application\n\n";
                }
               /* shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n\n";
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                startActivity(Intent.createChooser(shareIntent, "Choose one"));*/

                shareMessage = getActivity().getResources().getString(R.string.refer_str) + "\n" + shareMessage + ApplicationConstant.INSTANCE.inviteUrl + loginPrefResponse.getData().getUserID() + "\n\n";
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                startActivity(Intent.createChooser(shareIntent, "choose one"));


            } else {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.app_name));

                String shareMessage = "";
                if (companyData.getCompanyProfile().getAddress() != null) {
                    shareMessage = companyData.getCompanyProfile().getAddress() + "\n\nLet me recommend you this application\n\n";
                }

              /*  String shareMessage = " MIEUX INFRACON LTD \n" +
                        " B-720, Gopal Palace, 7th floor \n" +
                        " Nr Nehrunagar BRTS Stand \n" +
                        " S M Road, Nehrunagar, Ahmedabad - 380015\n" +
                        " Ahmedabad,Gujarat\n\nLet me recommend you this application\n\n";*/
//                shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n\n";
//                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
//                startActivity(Intent.createChooser(shareIntent, "choose one"));


                shareMessage = shareMessage + ApplicationConstant.INSTANCE.inviteUrl + loginPrefResponse.getData().getUserID() + "\n\n";
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                startActivity(Intent.createChooser(shareIntent, "choose one"));


            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setDashboardData(final OpTypeRollIdWiseServices mActiveServiceData) {


        if (userRollId == null || userRollId.isEmpty()) {
            userRollId = loginPrefResponse.getData().getRoleID();
        }
        if (userRollId.equalsIgnoreCase("8")) {

            /*fosMsg.setVisibility(View.GONE);
            ll_fos.setVisibility(View.VISIBLE);
            card_ll_fos.setVisibility(View.VISIBLE);
            rechargeRecyclerView.setVisibility(View.GONE);
            summaryDashboard.setVisibility(View.GONE);
            serviceOptionView.setVisibility(View.VISIBLE);*/

            if (mActiveServiceData != null && mActiveServiceData.getGetShowableFOSSerive() != null &&
                    mActiveServiceData.getGetShowableFOSSerive().size() > 0) {
                fosMsg.setVisibility(View.GONE);
                ll_fos.setVisibility(View.GONE);
                card_ll_fos.setVisibility(View.GONE);

                serviceOptionView.setVisibility(View.VISIBLE);
                rechargeRecyclerView.setVisibility(View.VISIBLE);
                setDasboardListData(mActiveServiceData.getGetShowableFOSSerive());
            } else {
                if (UtilMethods.INSTANCE.isNetworkAvialable(getActivity())) {

                    getActiveService();

                } else {
                    UtilMethods.INSTANCE.NetworkError(getActivity());
                }
            }
        } else {
           /* fosMsg.setVisibility(View.GONE);
            rechargeRecyclerView.setVisibility(View.VISIBLE);*/
            fosMsg.setVisibility(View.GONE);
            ll_fos.setVisibility(View.GONE);
            card_ll_fos.setVisibility(View.GONE);
            serviceOptionView.setVisibility(View.VISIBLE);
            rechargeRecyclerView.setVisibility(View.VISIBLE);

            if (mActiveServiceData != null && (
                    mActiveServiceData.getGetShowableActiveSerive() != null &&
                            mActiveServiceData.getGetShowableActiveSerive().size() > 0 ||
                            mActiveServiceData.getGetShowableOtherReportSerive() != null &&
                                    mActiveServiceData.getGetShowableOtherReportSerive().size() > 0)) {

                setDasboardListData(isRechargeViewEnable ? mActiveServiceData.getGetShowableActiveSerive() : mActiveServiceData.getGetShowableOtherReportSerive());
            } else {
                if (UtilMethods.INSTANCE.isNetworkAvialable(getActivity())) {

                    getActiveService();

                } else {
                    UtilMethods.INSTANCE.NetworkError(getActivity());
                }
            }
            /*if(mActiveServiceData != null)
            {
                isAddMoneyEnable=mActiveServiceData.getAddMoneyEnable();
                isPaymentGatway=mActiveServiceData.getPaymentGatway();
                isUPI=mActiveServiceData.getUPI();
            }
            if (mActiveServiceData != null && mActiveServiceData.getData() != null && mActiveServiceData.getData().getAssignedOpTypes() != null && mActiveServiceData.getData().getAssignedOpTypes().size() > 0) {

                isAddMoneyEnable=mActiveServiceData.getAddMoneyEnable();
                isPaymentGatway=mActiveServiceData.getPaymentGatway();
                isUPI=mActiveServiceData.getUPI();


                serviceOptionView.setVisibility(View.VISIBLE);
                isDMTWithPipe=mActiveServiceData.isDMTWithPipe();
                mAssignedOpTypesActiveService = mActiveServiceData.getData().getShowableActiveSerive(mActiveServiceData.isAddMoneyEnable());

               // mAssignedOpTypesActiveService = mActiveServiceData.getData().getShowableActiveSerive(isAddMoneyEnable);
                for (int i = 0; i < mAssignedOpTypesActiveService.size(); i++) {
                    if (mAssignedOpTypesActiveService.get(i).getServiceID() == 50 *//*|| mAssignedOpTypesActiveService.get(i).getName().equalsIgnoreCase("DMT")*//*) {

                        mAssignedOpTypesActiveService.remove(i);
                    }}

           *//* Collections.sort(mAssignedOpTypesActiveService, new Comparator() {
                @Override
                public int compare(Object o1, Object o2) {
                    AssignedOpType p1 = (AssignedOpType) o1;
                    AssignedOpType p2 = (AssignedOpType) o2;
                    return p1.getServiceID().compareTo(p2.getServiceID());
                }
            });*//*
                mAssignedOpTypesReportService = mActiveServiceData.getData().getShowableOtherReportSerive(mActiveServiceData.isAddMoneyEnable());

              setDasboardListData(mAssignedOpTypesReportService);
            } else {
                if (UtilMethods.INSTANCE.isNetworkAvialable(getActivity())) {

                    getActiveService();

                } else {
                    UtilMethods.INSTANCE.NetworkError(getActivity(), getResources().getString(R.string.err_msg_network_title),
                            getResources().getString(R.string.err_msg_network));
                }
            }*/
        }
    }


    void setDasboardListData(List<AssignedOpType> operatorList) {
        mDashboardOptionListAdapter = new HomeOptionListAdapter(operatorList, getActivity(), new HomeOptionListAdapter.ClickView() {
            @Override
            public void onClick(AssignedOpType opType) {
                if (opType.getSubOpTypeList() != null && opType.getSubOpTypeList().size() > 0) {
                    customAlertDialog.serviceListDialog(opType.getParentID(), opType.getService(),
                            opType.getSubOpTypeList(), new CustomAlertDialog.DialogServiceListCallBack() {
                                @Override
                                public void onIconClick(int serviceId, String name) {
                                    if (UtilMethods.INSTANCE.getLattitude > 0 && UtilMethods.INSTANCE.getLongitude > 0) {
                                        openNewScreen(serviceId);
                                    } else {
                                        if (mGetLocation == null) {
                                            mGetLocation = new GetLocation(getActivity(), loader);
                                        }
                                        mGetLocation.startLocationUpdates((lattitude, longitude) -> {
                                            UtilMethods.INSTANCE.getLattitude = lattitude;
                                            UtilMethods.INSTANCE.getLongitude = longitude;
                                            openNewScreen(serviceId);
                                        });
                                    }
                                }

                                @Override
                                public void onUpgradePackage() {
                                    startActivity(new Intent(getActivity(), UpgradePackage.class)
                                            .putExtra("UID", loginPrefResponse.getData().getUserID() + "")
                                            .putExtra("BENE_NAME", loginPrefResponse.getData().getName() + " (" + loginPrefResponse.getData().getRoleName() + ")")
                                            .putExtra("BENE_MOBILE", loginPrefResponse.getData().getMobileNo() + "")
                                            .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
                                }
                            });
                } else {
                    if (UtilMethods.INSTANCE.getLattitude > 0 && UtilMethods.INSTANCE.getLongitude > 0) {
                        openNewScreen(opType.getServiceID());
                    } else {
                        if (mGetLocation == null) {
                            mGetLocation = new GetLocation(getActivity(), loader);
                        }
                        mGetLocation.startLocationUpdates((lattitude, longitude) -> {
                            UtilMethods.INSTANCE.getLattitude = lattitude;
                            UtilMethods.INSTANCE.getLongitude = longitude;
                            openNewScreen(opType.getServiceID());
                        });
                    }

                }
            }

            @Override
            public void onPackageUpgradeClick() {
                startActivity(new Intent(getActivity(), UpgradePackage.class)
                        .putExtra("UID", loginPrefResponse.getData().getUserID() + "")
                        .putExtra("BENE_NAME", loginPrefResponse.getData().getName() + " (" + loginPrefResponse.getData().getRoleName() + ")")
                        .putExtra("BENE_MOBILE", loginPrefResponse.getData().getMobileNo() + "")
                        .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
            }
        }, R.layout.adapter_dashboard_option_grid);
        rechargeRecyclerView.setAdapter(mDashboardOptionListAdapter);
    }


    void getActiveService() {

        try {
            UtilMethods.INSTANCE.GetActiveService(getActivity(), new UtilMethods.ApiActiveServiceCallBack() {
                @Override
                public void onSucess(OpTypeResponse mOpTypeResponse, OpTypeRollIdWiseServices mOpTypeRollIdWiseServices) {
                  /*  if (mOpTypeResponse != null && mOpTypeResponse.getData() != null && mOpTypeResponse.getData().getAssignedOpTypes() != null
                            && mOpTypeResponse.getData().getAssignedOpTypes().size() > 0) {
                        setDashboardData(mOpTypeResponse);
                    }*/
                    mActiveServiceData = mOpTypeRollIdWiseServices;
                    isECollectEnable = mOpTypeResponse.getECollectEnable();
                    isAddMoneyEnable = mOpTypeResponse.getAddMoneyEnable();
                    isPaymentGatway = mOpTypeResponse.getPaymentGatway();
                    isUPI = mOpTypeResponse.getUPI();
                    try {
                        ((MainActivity) getActivity()).mActiveServiceData = mActiveServiceData;
                        ((MainActivity) getActivity()).isECollectEnable = isECollectEnable;
                        ((MainActivity) getActivity()).isUpiQR = mOpTypeResponse.getUPIQR();
                        ((MainActivity) getActivity()).isAddMoneyEnable = isAddMoneyEnable;
                        ((MainActivity) getActivity()).isPaymentGatway = isPaymentGatway;
                        ((MainActivity) getActivity()).isUPI = isUPI;

                    } catch (NullPointerException npe) {

                    }

                    if (mOpTypeRollIdWiseServices != null) {
                        setDashboardData(mOpTypeRollIdWiseServices);
                    }
                }


            });
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    void openNewScreen(int id) {
        if (id == 1) {
            if (!userRollId.equalsIgnoreCase("2")) {
                Intent clickIntent = new Intent(getActivity(), RechargeActivity.class);
                clickIntent.putExtra("from", "Prepaid");
                clickIntent.putExtra("fromId", id);
                clickIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(clickIntent);
                /*Intent i = new Intent(getActivity(), RechargeProviderActivity.class);
                i.putExtra("from", "Prepaid"); i.putExtra("fromId", id);
                i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(i);*/
            } else {
                UtilMethods.INSTANCE.Error(getActivity(), getResources().getString(R.string.NotAuthorized));
            }
        }
        if (id == 2) {
            if (!userRollId.equalsIgnoreCase("2")) {
                Intent clickIntent = new Intent(getActivity(), RechargeActivity.class);
                clickIntent.putExtra("from", "Postpaid");
                clickIntent.putExtra("fromId", id);
                clickIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(clickIntent);

               /* Intent i = new Intent(getActivity(), RechargeProviderActivity.class);
                i.putExtra("from", "Postpaid");
                i.putExtra("fromId", id);
                i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(i);*/
                /*Bundle bundle = new Bundle();
                bundle.putString("Recharge", "Postpaid");
                RechargeFragment fragment = new RechargeFragment();
                fragment.setArguments(bundle);
                ChangeFragmemt(fragment, "Postpaid");*/
            } else {
                UtilMethods.INSTANCE.Error(getActivity(), getResources().getString(R.string.NotAuthorized));
            }
        }
        if (id == 3) {
            if (!userRollId.equalsIgnoreCase("2")) {
                Intent i = new Intent(getActivity(), RechargeProviderActivity.class);
                i.putExtra("from", "DTH");
                i.putExtra("fromId", id);
                i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(i);
            } else {
                UtilMethods.INSTANCE.Error(getActivity(), getResources().getString(R.string.NotAuthorized));
            }
        }
        if (id == 4) {
            if (!userRollId.equalsIgnoreCase("2")) {
                Intent i = new Intent(getActivity(), RechargeProviderActivity.class);
                i.putExtra("from", "Landline");
                i.putExtra("fromId", id);
                i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(i);
            } else {
                UtilMethods.INSTANCE.Error(getActivity(), getResources().getString(R.string.NotAuthorized));
            }
        }
        if (id == 5) {
            if (!userRollId.equalsIgnoreCase("2")) {
                Intent i = new Intent(getActivity(), RechargeProviderActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                i.putExtra("fromId", id);
                i.putExtra("from", "Electricity");
                startActivity(i);
            } else {
                UtilMethods.INSTANCE.Error(getActivity(), getResources().getString(R.string.NotAuthorized));
            }
        }
        if (id == 6) {
            if (!userRollId.equalsIgnoreCase("2")) {
                Intent i = new Intent(getActivity(), RechargeProviderActivity.class);
                i.putExtra("from", "Gas");
                i.putExtra("fromId", id);
                i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(i);
            } else {
                UtilMethods.INSTANCE.Error(getActivity(), getResources().getString(R.string.NotAuthorized));
            }
        }
        if (id == 7) {

        }
        if (id == 8) {

        }
        if (id == 9) {

        }
        if (id == 10) {

        }
        if (id == 11) {

        }
        if (id == 12) {

        }
        if (id == 13) {
            openMPOS();
        }
        if (id == 14) {

            if (isDMTWithPipe) {
                ArrayList<OperatorList> operatorsList = UtilsMethodDMTPipe.INSTANCE.getDMTOperatorList(getActivity());
                if (operatorsList != null && operatorsList.size() > 0) {
                    if (operatorsList.size() > 1) {
                        dmtCustomAlertDialog.dmtListDialog("Select DMT", operatorsList, new DMTCustomAlertDialog.DialogDMTListCallBack() {
                            @Override
                            public void onIconClick(OperatorList mOperatorList) {
                                Intent i = new Intent(HomeFragment.this.getActivity(), DMRLoginNew.class);
                                i.putExtra("OpType", mOperatorList.getOpType());
                                i.putExtra("OID", mOperatorList.getOid() + "");
                                i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                HomeFragment.this.startActivity(i);
                            }
                        });
                    } else {
                        Intent i = new Intent(getActivity(), DMRLoginNew.class);
                        i.putExtra("OpType", operatorsList.get(0).getOpType());
                        i.putExtra("OID", operatorsList.get(0).getOid() + "");
                        i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(i);
                    }
                } else {
                    Intent i = new Intent(getActivity(), DMRLoginNew.class);
                    i.putExtra("OpType", 0);
                    i.putExtra("OID", "0");
                    i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(i);
                }

            } else {
                Intent i = new Intent(getActivity(), DMRLogin.class);
                i.putExtra("from", "Dmr");
                i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(i);
            }

        }
        if (id == 15) {

        }
        if (id == 16) {
            if (!userRollId.equalsIgnoreCase("2")) {
                Intent i = new Intent(getActivity(), RechargeProviderActivity.class);
                i.putExtra("from", "Broadband");
                i.putExtra("fromId", id);
                i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(i);
            } else {
                UtilMethods.INSTANCE.Error(getActivity(), getResources().getString(R.string.NotAuthorized));
            }
        }
        if (id == 17) {
            if (!userRollId.equalsIgnoreCase("2")) {
                Intent i = new Intent(getActivity(), RechargeProviderActivity.class);
                i.putExtra("from", "Water");
                i.putExtra("fromId", id);
                i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(i);
            } else {
                UtilMethods.INSTANCE.Error(getActivity(), getResources().getString(R.string.NotAuthorized));
            }
        }
        if (id == 18) {

        }
        if (id == 19) {

        }
        if (id == 20) {

        }
        if (id == 21) {

        }
        if (id == 22) {
//aeps
            if (!userRollId.equalsIgnoreCase("2")) {
                if (UtilMethods.INSTANCE.isNetworkAvialable(getActivity())) {
                    UtilMethods.INSTANCE.CallOnboarding(getActivity(), 0, false, getActivity().getSupportFragmentManager(),
                            id, "", "0", "", false, false, true, null, null, null, loader, loginPrefResponse, UtilMethods.INSTANCE.getLattitude + "", UtilMethods.INSTANCE.getLongitude + "", new UtilMethods.ApiCallBackOnBoardingMethod() {
                                @Override
                                public void onSuccess(Object object) {
                                    if (object != null && object instanceof OnboardingResponse) {

                                        OnboardingResponse onboardingResponse = (OnboardingResponse) object;
                                        sdkType = onboardingResponse.getSdkType();
                                        mSdkDetail = onboardingResponse.getSdkDetail();
                                        handleAEPSResponse(onboardingResponse);
                                    }
                                }

                                @Override
                                public void onError(Object object) {

                                }

                                @Override
                                public void onOnBoarding(Object object) {
                                    if (object != null && object instanceof OnboardingResponse) {
                                        OnboardingResponse response = (OnboardingResponse) object;
                                        sdkType = response.getSdkType();

                                        boolean isAEPS = id == 22 ? true : false;
                                        if (sdkType == 7 && response.getSdkDetail() != null) {
                                            mSdkDetail = response.getSdkDetail();
                                            getLocation(mSdkDetail, isAEPS);
                                        }
                                    }
                                }
                            });
                } else {
                    UtilMethods.INSTANCE.NetworkError(getActivity(), "Network Error!", getActivity().getResources().getString(R.string.network_error));
                }
            } else {
                UtilMethods.INSTANCE.Error(getActivity(), getResources().getString(R.string.NotAuthorized));
            }
        }
        if (id == 23) {

        }
        if (id == 24) {
//pan
            if (!userRollId.equalsIgnoreCase("2")) {
                if (UtilMethods.INSTANCE.isNetworkAvialable(getActivity())) {
                    UtilMethods.INSTANCE.CallOnboarding(getActivity(), 0, false, getActivity().getSupportFragmentManager(),
                            id, "", "0", "", true, false, true, null, null, null, loader, loginPrefResponse, UtilMethods.INSTANCE.getLattitude + "", UtilMethods.INSTANCE.getLongitude + "", new UtilMethods.ApiCallBackOnBoardingMethod() {
                                @Override
                                public void onSuccess(Object object) {
                                    if (object != null) {
                                        OnboardingResponse mOnboardingResponse = (OnboardingResponse) object;
                                        if (mOnboardingResponse != null) {
                                            Intent i = new Intent(getActivity(), PanApplicationActivity.class);
                                            i.putExtra("PANID", mOnboardingResponse.getPanid());
                                            i.putExtra("outletId", loginPrefResponse.getData().getOutletID() + "");
                                            i.putExtra("userId", loginPrefResponse.getData().getOutletID() + "");
                                            i.putExtra("emailId", mOnboardingResponse.getEmailID());
                                            i.putExtra("mobileNo", mOnboardingResponse.getMobileNo());
                                            // i.putExtra("panList", new Gson().toJson(mOnboardingResponse).toString());
                                            startActivity(i);
                                        }
                                    }
                                }

                                @Override
                                public void onError(Object object) {

                                }

                                @Override
                                public void onOnBoarding(Object object) {

                                }
                            });
                } else {
                    UtilMethods.INSTANCE.NetworkError(getActivity(), "Network Error!", getActivity().getResources().getString(R.string.network_error));
                }
            } else {
                UtilMethods.INSTANCE.Error(getActivity(), getResources().getString(R.string.NotAuthorized));
            }

        }


        if (id == 25) {
            if (!userRollId.equalsIgnoreCase("2")) {
                Intent i = new Intent(getActivity(), RechargeProviderActivity.class);
                i.putExtra("from", "Loan Repayment");
                i.putExtra("fromId", id);
                i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(i);
            } else {
                UtilMethods.INSTANCE.Error(getActivity(), getResources().getString(R.string.NotAuthorized));
            }
        }
        if (id == 26) {
            if (!userRollId.equalsIgnoreCase("2")) {
                Intent i = new Intent(getActivity(), RechargeProviderActivity.class);
                i.putExtra("from", "Gas cylinder Booking");
                i.putExtra("fromId", id);
                i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(i);
            } else {
                UtilMethods.INSTANCE.Error(getActivity(), getResources().getString(R.string.NotAuthorized));
            }
        }
        if (id == 27) {
            if (!userRollId.equalsIgnoreCase("2")) {
                Intent i = new Intent(getActivity(), RechargeProviderActivity.class);
                i.putExtra("from", "LifeInsurancePremium");
                i.putExtra("fromId", id);
                i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(i);
            } else {
                UtilMethods.INSTANCE.Error(getActivity(), getResources().getString(R.string.NotAuthorized));
            }
        }
        if (id == 28) {

            if (!userRollId.equalsIgnoreCase("2")) {
                Intent i = new Intent(getActivity(), RechargeProviderActivity.class);
                i.putExtra("from", "Bike Insurance");
                i.putExtra("fromId", id);
                i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(i);
            } else {
                UtilMethods.INSTANCE.Error(getActivity(), getResources().getString(R.string.NotAuthorized));
            }
        }
        if (id == 29) {

            if (!userRollId.equalsIgnoreCase("2")) {
                Intent i = new Intent(getActivity(), RechargeProviderActivity.class);
                i.putExtra("from", "Four Wheeler Insurance");
                i.putExtra("fromId", id);
                i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(i);
            } else {
                UtilMethods.INSTANCE.Error(getActivity(), getResources().getString(R.string.NotAuthorized));
            }
        }
        if (id == 32) {
            if (!userRollId.equalsIgnoreCase("2")) {
                Intent clickIntent = new Intent(getActivity(), RechargeActivity.class);
                clickIntent.putExtra("from", "FRC Prepaid");
                clickIntent.putExtra("fromId", id);
                clickIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(clickIntent);
                /*Intent i = new Intent(getActivity(), RechargeProviderActivity.class);
                i.putExtra("from", "Prepaid"); i.putExtra("fromId", id);
                i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(i);*/
            } else {
                UtilMethods.INSTANCE.Error(getActivity(), getResources().getString(R.string.NotAuthorized));
            }
        }
        if (id == 35) {
            if (!userRollId.equalsIgnoreCase("2")) {
                Intent i = new Intent(getActivity(), RechargeProviderActivity.class/*RechargeProviderActivity.class*/);
                i.putExtra("from", "HD BOX");
                i.putExtra("fromId", id);
                i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(i);
            } else {
                UtilMethods.INSTANCE.Error(getActivity(), getResources().getString(R.string.NotAuthorized));
            }
        }
        if (id == 36) {
            if (!userRollId.equalsIgnoreCase("2")) {
                Intent i = new Intent(getActivity(), RechargeProviderActivity.class/*RechargeProviderActivity.class*/);
                i.putExtra("from", "SD BOX");
                i.putExtra("fromId", id);
                i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(i);
            } else {
                UtilMethods.INSTANCE.Error(getActivity(), getResources().getString(R.string.NotAuthorized));
            }
        }
        if (id == 44) {
//mini atm
            if (!userRollId.equalsIgnoreCase("2")) {
                if (UtilMethods.INSTANCE.isNetworkAvialable(getActivity())) {
                    UtilMethods.INSTANCE.CallOnboarding(getActivity(), 0, false, getActivity().getSupportFragmentManager(),
                            id, "", "0", "", false, false, true, null,
                            null, null, loader, loginPrefResponse, UtilMethods.INSTANCE.getLattitude + "", UtilMethods.INSTANCE.getLongitude + "", new UtilMethods.ApiCallBackOnBoardingMethod() {
                                @Override
                                public void onSuccess(Object object) {
                                    if (object != null && object instanceof OnboardingResponse) {

                                        OnboardingResponse onboardingResponse = (OnboardingResponse) object;
                                        handleMiniAtmResponse(onboardingResponse);
                                    }
                                }


                                @Override
                                public void onError(Object object) {

                                }

                                @Override
                                public void onOnBoarding(Object object) {

                                }
                            });
                } else {
                    UtilMethods.INSTANCE.NetworkError(getActivity(), "Network Error!", getActivity().getResources().getString(R.string.network_error));
                }
            } else {
                UtilMethods.INSTANCE.Error(getActivity(), getResources().getString(R.string.NotAuthorized));
            }
        }
        if (id == 45) {
            if (!userRollId.equalsIgnoreCase("2")) {
                try {
                    startActivity(new Intent(getActivity(), ShopDashboardActivity.class)
                            .putExtra(KeyContants.APP_ID, ApplicationConstant.INSTANCE.APP_ID)
                            .putExtra(KeyContants.IMEI, UtilMethods.INSTANCE.getIMEI(getActivity()) + "")
                            .putExtra(KeyContants.LOGIN_TYPE_ID, Integer.parseInt(loginPrefResponse.getData().getLoginTypeID()))//Integer value
                            .putExtra(KeyContants.REG_KEY, UtilMethods.INSTANCE.getFCMRegKey(getActivity()) + "")
                            .putExtra(KeyContants.SERIAL_NO, UtilMethods.INSTANCE.getSerialNo(getActivity()) + "")
                            .putExtra(KeyContants.SESSION, loginPrefResponse.getData().getSession() + "")
                            .putExtra(KeyContants.SESSION_ID, loginPrefResponse.getData().getSessionID() + "")
                            .putExtra(KeyContants.USER_ID, loginPrefResponse.getData().getUserID() + "")
                            .putExtra(KeyContants.VERSION_NAME, BuildConfig.VERSION_NAME)
                            .putExtra(KeyContants.BASE_URL, ApplicationConstant.INSTANCE.baseUrl) //eg. https://roundpay.net/

                    );
                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else {
                UtilMethods.INSTANCE.Error(getActivity(), getResources().getString(R.string.NotAuthorized));
            }
        }
        if (id == 46) {
            if (!userRollId.equalsIgnoreCase("2")) {
                Intent i = new Intent(getActivity(), RechargeProviderActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                i.putExtra("fromId", id);
                i.putExtra("from", "Municipal Taxes");
                startActivity(i);
            } else {
                UtilMethods.INSTANCE.Error(getActivity(), getResources().getString(R.string.NotAuthorized));
            }
        }


        if (id == 47) {
            if (!userRollId.equalsIgnoreCase("2")) {
                Intent i = new Intent(getActivity(), RechargeProviderActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                i.putExtra("fromId", id);
                i.putExtra("from", "Education Fees");
                startActivity(i);
            } else {
                UtilMethods.INSTANCE.Error(getActivity(), getResources().getString(R.string.NotAuthorized));
            }
        }


        if (id == 48) {
            if (!userRollId.equalsIgnoreCase("2")) {
                Intent i = new Intent(getActivity(), RechargeProviderActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                i.putExtra("fromId", id);
                i.putExtra("from", "Housing Society");
                startActivity(i);
            } else {
                UtilMethods.INSTANCE.Error(getActivity(), getResources().getString(R.string.NotAuthorized));
            }
        }


        if (id == 49) {
            if (!userRollId.equalsIgnoreCase("2")) {
                Intent i = new Intent(getActivity(), RechargeProviderActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                i.putExtra("fromId", id);
                i.putExtra("from", "Health Insurance");
                startActivity(i);
            } else {
                UtilMethods.INSTANCE.Error(getActivity(), getResources().getString(R.string.NotAuthorized));
            }
        }


        if (id == 52) {
            if (!userRollId.equalsIgnoreCase("2")) {
                Intent i = new Intent(getActivity(), RechargeProviderActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                i.putExtra("fromId", id);
                i.putExtra("from", "Hospital");
                startActivity(i);
            } else {
                UtilMethods.INSTANCE.Error(getActivity(), getResources().getString(R.string.NotAuthorized));
            }
        }
        if (id == 62) {
            startActivity(new Intent(getActivity(), UPIPayActivity.class)
                    .putExtra("BalanceData", balanceCheckResponse)
                    .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));

        }

        if (id == 63) {
            startActivity(new Intent(getActivity(), QRScanActivity.class)
                    .putExtra("FROM_SCANPAY", true)
                    .putExtra("BalanceData", balanceCheckResponse)
                    .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            );
        }

        if (id == 38) {
            if (!userRollId.equalsIgnoreCase("2")) {
                Intent i = new Intent(getActivity(), RechargeProviderActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                i.putExtra("fromId", id);
                i.putExtra("from", "FASTag");
                startActivity(i);
            } else {
                UtilMethods.INSTANCE.Error(getActivity(), getResources().getString(R.string.NotAuthorized));
            }
        }


        if (id == 39) {
            if (!userRollId.equalsIgnoreCase("2")) {
                Intent i = new Intent(getActivity(), RechargeProviderActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                i.putExtra("fromId", id);
                i.putExtra("from", "Cable TV");
                startActivity(i);
            } else {
                UtilMethods.INSTANCE.Error(getActivity(), getResources().getString(R.string.NotAuthorized));
            }
        }

        if (id == 33) {
            if (!userRollId.equalsIgnoreCase("2")) {
                Intent i = new Intent(getActivity(), RechargeProviderActivity.class);
                i.putExtra("from", "FRC DTH");
                i.putExtra("fromId", id);
                i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(i);
            } else {
                UtilMethods.INSTANCE.Error(getActivity(), getResources().getString(R.string.NotAuthorized));
            }
        }

        if (id == 37) {
                /* Intent i = new Intent(getActivity(), AddMoneyActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(i);*/

            Intent i = new Intent(getActivity(), AddMoneyActivity.class);
            i.putExtra("isPayment", isPaymentGatway);
            i.putExtra("isUPI", isUPI);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);

        }
        if (id == 74) {
            if (!userRollId.equalsIgnoreCase("2")) {
                Intent i = new Intent(getActivity(), AccountOpenActivity.class);
                i.putExtra("SERVICE_ID", id);
                i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(i);
            } else {
                UtilMethods.INSTANCE.Error(getActivity(), getResources().getString(R.string.NotAuthorized));
            }
        }

       /* if (id == 50) {
            Intent i = new Intent(getActivity(), AddMoneyActivity.class*//*RechargeProviderActivity.class*//*);
            i.putExtra("isPayment", isPaymentGatway);
            i.putExtra("isUPI", isUPI);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
        }*/


        if (id == 100 || id == 110) {
            Intent intent = new Intent(getActivity(), PaymentRequest.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        } else if (id == 101) {
            if (!userRollId.equalsIgnoreCase("2")) {
                if (UtilMethods.INSTANCE.isNetworkAvialable(getActivity())) {
                    UtilMethods.INSTANCE.CallOnboarding(getActivity(), 0, false, getActivity().getSupportFragmentManager(),
                            id, "", "0", "", false, false, true, null,
                            null, null, loader, loginPrefResponse, UtilMethods.INSTANCE.getLattitude + "", UtilMethods.INSTANCE.getLongitude + "", new UtilMethods.ApiCallBackOnBoardingMethod() {
                                @Override
                                public void onSuccess(Object object) {
                                    if (object != null && object instanceof OnboardingResponse) {
                                        //CMS


                                        OnboardingResponse onboardingResponse = (OnboardingResponse) object;
//                                        handleMiniAtmResponse(onboardingResponse);
                                        handelCMS(onboardingResponse);
                                    }
                                }


                                @Override
                                public void onError(Object object) {

                                }

                                @Override
                                public void onOnBoarding(Object object) {

                                }
                            });

                } else {
                    UtilMethods.INSTANCE.NetworkError(getActivity(), "Network Error!", getActivity().getResources().getString(R.string.network_error));
                }
            } else {
                UtilMethods.INSTANCE.Error(getActivity(), getResources().getString(R.string.NotAuthorized));
            }
        } else if (id == 104) {
            if (!userRollId.equalsIgnoreCase("2")) {
                Intent i = new Intent(getActivity(), NSDLActivity.class);
                i.putExtra("from", name);
                i.putExtra("fromId", id);
                i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(i);
            } else {
                UtilMethods.INSTANCE.Error(getActivity(), getResources().getString(R.string.NotAuthorized));
            }
        }
        if (id == 1001) {
            Intent intent = new Intent(getActivity(), RechargeHistory.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        }
        if (id == 102) {
            Intent intent = new Intent(getActivity(), LedgerReport.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        }
        if (id == 103) {
            Intent i = new Intent(getActivity(), FundReqReport.class);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
        }
        if (id == 1004) {
            Intent n = new Intent(getActivity(), DisputeReport.class);
            n.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(n);
        }
        if (id == 105) {
            Intent i = new Intent(getActivity(), DMRReport.class);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);

        }
        if (id == 106) {
            // Fund Transfer Report

        }
        if (id == 107) {
            Intent i = new Intent(getActivity(), FundRecReport.class);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);

        }
        if (id == 108) {
            Intent i = new Intent(getActivity(), UserDayBookActivity.class);
            i.putExtra("Type", "UserDayBook");
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);

        }
        if (id == 109) {
            Intent i = new Intent(getActivity(), FundOrderPendingActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            getActivity().startActivity(i);
        }

        if (id == 111) {
            Intent i = new Intent(getActivity(), CreateUserActivity.class);
            i.putExtra("KeyFor", "User");
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            getActivity().startActivity(i);
        }
        if (id == 112) {
            if (userRollId.equalsIgnoreCase("8")) {
                Intent i = new Intent(getActivity(), FosUserListActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                getActivity().startActivity(i);
            } else {
                Intent i = new Intent(getActivity(), AppUserListActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                getActivity().startActivity(i);
            }
        }
        if (id == 113) {
            Intent n = new Intent(getActivity(), CommissionScreen.class);
            n.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(n);
        }
        if (id == 114) {
            Intent n = new Intent(getActivity(), W2RHistory.class);
            n.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(n);
        }
        if (id == 115) {
            Intent i = new Intent(getActivity(), UserDayBookActivity.class);
            i.putExtra("Type", "UserDayBookDMT");
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);

        }
        if (id == 116) {
            //Call back request
            callBack();
        }
        if (id == 117) {
            Intent n = new Intent(getActivity(), QrBankActivity.class);
            n.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(n);
        }
        if (id == 118) {
            Intent i = new Intent(getActivity(), SpecificRechargeReportActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);

        }
        /*if (id == 111) {
            Intent i = new Intent(getActivity(), CreateUserActivity.class);
            i.putExtra("KeyFor", "FOS");
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            getActivity().startActivity(i);
        }*/
        if (id == 120) {
            Intent i = new Intent(getActivity(), AEPSReportActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);

        }
        if (id == 121) {
            // UtilMethods.INSTANCE.GetArealist(getActivity(), loader, loginPrefResponse, null);
            Intent intent = new Intent(getActivity(), AccountStatementReportActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        }

        if (id == 122) {

            // customAlertDialog.channelFosListDialog(getActivity());
            Intent intent = new Intent(getActivity(), FosReportActivity.class);
            startActivity(intent);
        }
        if (id == 123) {


            if (loginPrefResponse.isAreaMaster()) {
                if (mAppGetAMResponse == null || mAppGetAMResponse.getAreaMaster() == null) {
                    mAppGetAMResponse = new Gson().fromJson(UtilMethods.INSTANCE.getAreaListPref(getActivity()), AppGetAMResponse.class);
                    if (mAppGetAMResponse == null || mAppGetAMResponse.getAreaMaster() == null) {
                        if (UtilMethods.INSTANCE.isNetworkAvialable(getActivity())) {
                            loader.show();
                            UtilMethods.INSTANCE.GetArealist(getActivity(), loader, loginPrefResponse,
                                    new UtilMethods.ApiCallBack() {
                                        @Override
                                        public void onSucess(Object object) {
                                            mAppGetAMResponse = (AppGetAMResponse) object;
                                            if (mAppGetAMResponse != null && mAppGetAMResponse.getAreaMaster() != null && mAppGetAMResponse.getAreaMaster().size() > 0) {
                                                customAlertDialog.channelAreaListDialog(getActivity(), mAppGetAMResponse.getAreaMaster());
                                            }
                                        }


                                    });


                        } else {
                            UtilMethods.INSTANCE.NetworkError(getActivity());
                        }
                    } else {
                        customAlertDialog.channelAreaListDialog(getActivity(), mAppGetAMResponse.getAreaMaster());
                    }
                } else {
                    customAlertDialog.channelAreaListDialog(getActivity(), mAppGetAMResponse.getAreaMaster());
                }

            } else {
                Intent intent = new Intent(getActivity(), ChannelReportActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        }
        if (id == 125) {

            customAlertDialog.channelFosListDialog(getActivity(), loader, loginPrefResponse);

        }
        if (id == 128) {

            // UtilMethods.INSTANCE.GetArealist(getActivity(), loader, loginPrefResponse, null);
            Intent intent = new Intent(getActivity(), FosAreaReportActivity.class);
            intent.putExtra("ISFromFOS", true);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

            startActivity(intent);
        }
        if (id == 135) {
            Intent i = new Intent(getActivity(), MoveToBankReportActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);

        }
       /* if (id == 119) {
            Intent i = new Intent(getActivity(), AddMoneyActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);

        }*/
    }

    private void getLocation(SdkDetail sdkDetail, boolean isAEPS) {
        if (UtilMethods.INSTANCE.getLattitude != 0 && UtilMethods.INSTANCE.getLongitude != 0) {

            onBoardingSubmit(isAEPS, sdkDetail, UtilMethods.INSTANCE.getLattitude, UtilMethods.INSTANCE.getLongitude);
        } else {
            if (mGetLocation != null) {
                mGetLocation.startLocationUpdates((lattitude, longitude) -> {
                    UtilMethods.INSTANCE.getLattitude = lattitude;
                    UtilMethods.INSTANCE.getLongitude = longitude;
                    onBoardingSubmit(isAEPS, sdkDetail, lattitude, longitude);
                });
            } else {
                mGetLocation = new GetLocation(getActivity(), loader);
                mGetLocation.startLocationUpdates((lattitude, longitude) -> {
                    UtilMethods.INSTANCE.getLattitude = lattitude;
                    UtilMethods.INSTANCE.getLongitude = longitude;
                    onBoardingSubmit(isAEPS, sdkDetail, lattitude, longitude);
                });
            }
        }
    }

    private void onBoardingSubmit(boolean isAEPSOnBoard, SdkDetail sdkDetail, double lattitude, double longitude) {
        String pId = "", pApiKey = "", mCode = "", mobileNo = "", firm = "", email = "";
        mobileNo = loginPrefResponse.getData().getMobileNo();
        try {
            pId = sdkDetail.getApiPartnerID();
        } catch (NumberFormatException nfe) {
            UtilMethods.INSTANCE.Error(getActivity(), "Invalid partner Id ");
        }

        try {
            pApiKey = sdkDetail.getServiceOutletPIN();
        } catch (NumberFormatException nfe) {
            UtilMethods.INSTANCE.Error(getActivity(), "Invalid API Key provided in credential");
        }

        try {
            mCode = sdkDetail.getApiOutletID();
        } catch (NumberFormatException nfe) {
            UtilMethods.INSTANCE.Error(getActivity(), "Invalid API OutletID");
        }

        try {
            if (sdkDetail.getApiOutletMob() != null && !sdkDetail.getApiOutletMob().isEmpty())
                mobileNo = sdkDetail.getApiOutletMob();
        } catch (NumberFormatException nfe) {
            UtilMethods.INSTANCE.Error(getActivity(), "Invalid Mobile Number");
        }

        try {
            firm = sdkDetail.getOutletName();
        } catch (NumberFormatException nfe) {
            UtilMethods.INSTANCE.Error(getActivity(), "Invalid Outlet Name");
        }

        try {
            email = sdkDetail.getEmailID();
        } catch (NumberFormatException nfe) {
            UtilMethods.INSTANCE.Error(getActivity(), "Invalid Email Id");
        }


        if (pId != null && pId.isEmpty()) {
            UtilMethods.INSTANCE.Error(getActivity(), "Invalid or null partner Id");
            return;
        } else if (pApiKey != null && pApiKey.isEmpty()) {
            UtilMethods.INSTANCE.Error(getActivity(), "Invalid or null ApiKey");
            return;
        } else if (mCode != null && mCode.isEmpty()) {
            UtilMethods.INSTANCE.Error(getActivity(), "Invalid or null Merchant Code");
            return;
        }

        startActivityForResult(new Intent(getActivity(), HostActivity.class)
                .putExtra("pId", pId)//partner Id provided in credential
                .putExtra("pApiKey", pApiKey)//JWT API Key provided in credential
                .putExtra("mCode", mCode)  //Merchant Code
                .putExtra("mobile", mobileNo) // merchant mobile number
                .putExtra("lat", lattitude + "")
                .putExtra("lng", longitude + "")
                .putExtra("firm", firm)
                .putExtra("email", email)
                .addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION), isAEPSOnBoard ? INTENT_AEPS_PAYS_PRINT : INTENT_MINI_ATM_PAYS_PRINT);
    }


    private void handelCMS(OnboardingResponse response) {
        String secretKey = "";
        String mobileNumber = "";
        String merchantId = "";
        String superMerchantId = "";
        SdkDetail sdkDetail = response.getSdkDetail();
        if (sdkDetail != null) {
            merchantId = sdkDetail.getApiOutletID();
            secretKey = sdkDetail.getSecretKey();
            superMerchantId = sdkDetail.getApiPartnerID();
            mobileNumber = sdkDetail.getApiOutletMob();


            if (merchantId != null && merchantId.isEmpty()) {
                UtilMethods.INSTANCE.Error(getActivity(), "Invalid or null merchant id");
                return;
            } else if (secretKey != null && secretKey.isEmpty()) {
                UtilMethods.INSTANCE.Error(getActivity(), "Invalid or null secret key");
                return;
            } else if (superMerchantId != null && superMerchantId.isEmpty()) {
                UtilMethods.INSTANCE.Error(getActivity(), "Invalid or null super merchant id");
                return;
            } else if (mobileNumber == null || mobileNumber.isEmpty()) {
                mobileNumber = loginPrefResponse.getData().getMobileNo();
            }

            Intent intent = new Intent(getActivity(), LoginScreen.class);
            intent.putExtra(Constants.MERCHANT_ID, merchantId);
            intent.putExtra(Constants.SECRET_KEY, secretKey);
            //  If Biller
            intent.putExtra(Constants.TYPE_REF, Constants.BILLERS); /*- 101*/
            //  intent.putExtra(Constants.TYPE_REF, Constants.REF_ID);/*- 100*/
            intent.putExtra(Constants.MOBILE_NUMBER, mobileNumber);
            intent.putExtra(Constants.SUPER_MERCHANTID, superMerchantId);
            intent.putExtra(Constants.IMEI, UtilMethods.INSTANCE.getIMEI(getActivity()));
            intent.putExtra(Constants.REFERENCE_ID, Constants.REF_ID); //you can pass this value if you want this value in the sdk to be uneditable

            if (UtilMethods.INSTANCE.getLattitude != 0.0 && UtilMethods.INSTANCE.getLongitude != 0.0) {
                intent.putExtra(Constants.LATITUDE, UtilMethods.INSTANCE.getLattitude);
                intent.putExtra(Constants.LONGITUDE, UtilMethods.INSTANCE.getLongitude);
            } else {
                if (mGetLocation != null) {
                    mGetLocation.startLocationUpdates((lattitude, longitude) -> {
                        UtilMethods.INSTANCE.getLattitude = lattitude;
                        UtilMethods.INSTANCE.getLongitude = longitude;
                        intent.putExtra(Constants.LATITUDE, UtilMethods.INSTANCE.getLattitude);
                        intent.putExtra(Constants.LONGITUDE, UtilMethods.INSTANCE.getLongitude);

                    });
                } else {
                    mGetLocation = new GetLocation(getActivity(), loader);
                    mGetLocation.startLocationUpdates((lattitude, longitude) -> {
                        UtilMethods.INSTANCE.getLattitude = lattitude;
                        UtilMethods.INSTANCE.getLongitude = longitude;
                        intent.putExtra(Constants.LATITUDE, UtilMethods.INSTANCE.getLattitude);
                        intent.putExtra(Constants.LONGITUDE, UtilMethods.INSTANCE.getLongitude);
                    });
                }
            }
            startActivityForResult(intent, INTENT_CODE_CMS);
        } else {
            UtilMethods.INSTANCE.Error(getActivity(), " Required CMS Data not found !! ");
        }
    }


    void callBack() {
        if (customAlertDialog != null) {
            customAlertDialog.sendReportDialog(3, loginPrefResponse.getData().getMobileNo(), new CustomAlertDialog.DialogSingleCallBack() {
                @Override
                public void onPositiveClick(String mobile, String emailId) {
                    UtilMethods.INSTANCE.GetCallMeUserReq(getActivity(), mobile, loader);
                }
            });
        }
    }

    private void openMPOS() {
        Intent intent = new Intent(getActivity(), PaynearActivationActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);

    }

    private void handleAEPSResponse(OnboardingResponse response) {
        int sDKType = response.getSdkType();

        if (sDKType == 1 || sDKType == 7) //Fing Pay
        {
            startActivity(new Intent(getActivity(), FingPayAEPSDashBoardActivity.class)
                    .putExtra("SDKDetails", response.getSdkDetail())
                    .putExtra("InterfaceType", sDKType)
                    .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
            /*Toast.makeText(getActivity(), "SDK Type - 1/n SDK not available", Toast.LENGTH_SHORT).show();*/
        } else if ((sDKType == 2 || sDKType == 4) && response.getBcResponse() != null) {// mahagram
            if (response.getBcResponse().size() > 0) {
                if (response.getBcResponse().get(0).getErrorCode() == 0) {

                    showBCDetail(response.getBcResponse().get(0));
                } else {
                    UtilMethods.INSTANCE.Error(getActivity(), "Something went wrong!!");
                }
            } else {
                UtilMethods.INSTANCE.Error(getActivity(), "BcDetails Data not found!!");
            }
        } else if ((sDKType == 2 || sDKType == 4)
                && response.getSdkDetail() != null && response.getSdkDetail().getBcResponse() != null) { //mahagram
            if (response.getSdkDetail().getBcResponse().size() > 0) {
                if (response.getSdkDetail().getBcResponse().get(0).getErrorCode() == 0) {

                    showBCDetail(response.getSdkDetail().getBcResponse().get(0));
                } else {
                    UtilMethods.INSTANCE.Error(getActivity(), "Something went wrong!!");
                }
            } else {
                UtilMethods.INSTANCE.Error(getActivity(), "BcDetails Data not found!!");
            }
        } else if (sDKType == 3 && response.getSdkDetail() != null)//RP Fintech
        {
            SdkDetail sdkDetail = response.getSdkDetail();
            if (sdkDetail != null) {
                int partnerid = 0;
                int outletId = 0;
                try {
                    partnerid = Integer.parseInt(response.getSdkDetail().getApiPartnerID());
                } catch (NumberFormatException nfe) {
                    UtilMethods.INSTANCE.Error(getActivity(), "Invalid partner id its should be integer value");
                }

                try {
                    outletId = Integer.parseInt(response.getSdkDetail().getApiOutletID());
                } catch (NumberFormatException nfe) {
                    UtilMethods.INSTANCE.Error(getActivity(), "Invalid outlat id its should be integer value");
                }

                startActivityForResult(new Intent(getActivity(), EMoneyLoginActivity.class)
                        .putExtra(KeyConstant.USER_ID, partnerid)
                        .putExtra(KeyConstant.OUTLET_ID, outletId)
                        .putExtra(KeyConstant.PARTNER_ID, 0)
                        .putExtra(KeyConstant.LATITUDE,UtilMethods.INSTANCE.getLattitude)
                        .putExtra(KeyConstant.LONGITUDE,UtilMethods.INSTANCE.getLongitude)
                        .putExtra(KeyConstant.PIN, response.getSdkDetail().getApiOutletPassword())
                        .putExtra(KeyConstant.SERVICE_TYPE, KeyConstant.AEPS), INTENT_AEPS_RP);
            } else {
                UtilMethods.INSTANCE.Error(getActivity(), " Required Data not found !! ");
            }
        } else {
            UtilMethods.INSTANCE.Error(getActivity(), "SDK Type Error !! ");
        }
    }

    private void handleMiniAtmResponse(OnboardingResponse response) {
        sDKType = response.getSdkType();
        matmOID = response.getoId();
        if (sDKType == 1 && response.getSdkDetail() != null) {
            Intent i = new Intent(getActivity(), MicroAtmActivity.class);
            i.putExtra("SDKType", sDKType + "");
            i.putExtra("OID", matmOID + "");
            i.putExtra("SDKDetails", response.getSdkDetail());
            startActivity(i);
        } else if ((sDKType == 2 || sDKType == 4) &&
                response.getBcResponse() != null) {
            if (response.getBcResponse().size() > 0) {
                if (response.getBcResponse().get(0).getErrorCode() == 0) {
                    showMicroATMBCDetail(response.getBcResponse().get(0));
                } else {
                    UtilMethods.INSTANCE.Error(getActivity(), "Something went wrong!!");
                }
            } else {
                UtilMethods.INSTANCE.Error(getActivity(), "BcDetails Data not found!!");
            }

        } else if ((sDKType == 2 || sDKType == 4)
                && response.getSdkDetail() != null && response.getSdkDetail().getBcResponse() != null) {
            if (response.getSdkDetail().getBcResponse().size() > 0) {
                if (response.getSdkDetail().getBcResponse().get(0).getErrorCode() == 0) {
                    showMicroATMBCDetail(response.getSdkDetail().getBcResponse().get(0));
                } else {
                    UtilMethods.INSTANCE.Error(getActivity(), "Something went wrong!!");
                }
            } else {
                UtilMethods.INSTANCE.Error(getActivity(), "BcDetails Data not found!!");
            }

        } else if (sDKType == 3 && response.getSdkDetail() != null) {
            int partnerid = 0;
            int outletId = 0;
            try {
                partnerid = Integer.parseInt(response.getSdkDetail().getApiPartnerID());
            } catch (NumberFormatException nfe) {
                UtilMethods.INSTANCE.Error(getActivity(), "Invalid partner id its should be integer value");
            }

            try {
                outletId = Integer.parseInt(response.getSdkDetail().getApiOutletID());
            } catch (NumberFormatException nfe) {
                UtilMethods.INSTANCE.Error(getActivity(), "Invalid outlat id its should be integer value");
            }

            startActivityForResult(new Intent(getActivity(), EMoneyLoginActivity.class)
                    .putExtra(KeyConstant.USER_ID, partnerid)
                    .putExtra(KeyConstant.OUTLET_ID, outletId)
                    .putExtra(KeyConstant.PARTNER_ID, 0)
                    .putExtra(KeyConstant.LATITUDE,UtilMethods.INSTANCE.getLattitude)
                    .putExtra(KeyConstant.LONGITUDE,UtilMethods.INSTANCE.getLongitude)
                    .putExtra(KeyConstant.PIN, response.getSdkDetail().getApiOutletPassword())
                    .putExtra(KeyConstant.SERVICE_TYPE, KeyConstant.MICRO_ATM), INTENT_MINI_ATM_RP);
        } else {
            UtilMethods.INSTANCE.Error(getActivity(), "Merchent details is not found or SDK not integrated");
        }

    }

    private void showMicroATMBCDetail(BcResponse bcDetail) {
        try {
            String mobileno = bcDetail.getMobileno();
            String secretKey = bcDetail.getSecretKey();
            String saltKey = bcDetail.getSaltKey();
            String bcid = bcDetail.getBcid();
            String userId = bcDetail.getUserId();
            String cpid = bcDetail.getCpid() != null && bcDetail.getCpid().length() > 0 ? bcDetail.getCpid() : "";
            String emailId = bcDetail.getEmailId();
            String aepsOutletId = bcDetail.getAepsOutletId();
            String password = bcDetail.getPassword();
            String merchantId = bcDetail.getMerchantId();

            if (bcid != null && bcid.length() > 0) {
                PackageManager packageManager = getActivity().getPackageManager();
                if (UtilMethods.INSTANCE.isPackageInstalled("org.egram.microatm", packageManager)) {
                    Intent intent = new Intent();
                    intent.setComponent(new
                            ComponentName("org.egram.microatm", "org.egram.microatm.BluetoothMacSearchActivity"));
                    intent.putExtra("saltkey", saltKey);
                    intent.putExtra("secretkey", secretKey);
                    intent.putExtra("bcid", bcid);
                    intent.putExtra("userid", userId);
                    intent.putExtra("bcemailid", emailId);
                    intent.putExtra("phone1", mobileno);
                    intent.putExtra("clientrefid", cpid);
                    intent.putExtra("vendorid", "");
                    intent.putExtra("udf1", "");
                    intent.putExtra("udf2", "");
                    intent.putExtra("udf3", "");
                    intent.putExtra("udf4", "");
                    startActivityForResult(intent, INTENT_MINI_ATM);

                } else {
                    customAlertDialog.WarningWithCallBack("MicroATM Service not installed. Click OK to download.", "Get Service", "Download", false, new CustomAlertDialog.DialogCallBack() {
                        @Override
                        public void onPositiveClick() {
                            try {
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=org.egram.microatm")));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onNegativeClick() {

                        }
                    });
                }

            } else {
                UtilMethods.INSTANCE.Error(getActivity(), getResources().getString(R.string.microATMApproved));
            }


        } catch (Exception ex) {
            UtilMethods.INSTANCE.Error(getActivity(), getString(R.string.some_thing_error) + "due to " + ex.getMessage());
        }
    }

    private void showBCDetail(BcResponse bcDetail) {
        try {
            String mobileno = bcDetail.getMobileno();
            String secretKey = bcDetail.getSecretKey();
            String saltKey = bcDetail.getSaltKey();
            String bcid = bcDetail.getBcid();
            String userId = bcDetail.getUserId();
            String cpid = bcDetail.getCpid() != null && bcDetail.getCpid().length() > 0 ? bcDetail.getCpid() : "";
            String emailId = bcDetail.getEmailId();
            String aepsOutletId = bcDetail.getAepsOutletId();
            String password = bcDetail.getPassword();
            String merchantId = bcDetail.getMerchantId();


            if (bcid != null && bcid.length() > 0) {
                Intent intent = new Intent(getActivity(), DashboardActivity.class);
                intent.putExtra("saltKey", saltKey);
                intent.putExtra("secretKey", secretKey);
                intent.putExtra("BcId", bcid);
                intent.putExtra("UserId", userId);
                intent.putExtra("bcEmailId", emailId);
                intent.putExtra("Phone1", mobileno);
                intent.putExtra("cpid", cpid);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivityForResult(intent, INTENT_CODE_APES);
            } else {
                UtilMethods.INSTANCE.Error(getActivity(), getResources().getString(R.string.AepsNotApproved));
            }

        } catch (Exception ex) {
            UtilMethods.INSTANCE.Error(getActivity(), getActivity().getResources().getString(R.string.some_thing_error) + "due to " + ex.getMessage());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (UtilMethods.INSTANCE.isNetworkAvialable(getActivity())) {
            //UtilMethods.INSTANCE.BalancecheckNew(getActivity(), loader, customAlertDialog, mChangePassUtils);
            UtilMethods.INSTANCE.NewsApi(getActivity(), false, newsWeb, newsCard);
        }

    }

    @Override
    public void onPause() {

        super.onPause();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == INTENT_CODE_APES && resultCode == RESULT_OK) {
            if (resultCode == RESULT_OK) {
                String TransactionType = data.getStringExtra("TransactionType"); //to get transaction name
                data.getStringExtra("Response"); //to get response
                String s = data.getStringExtra("StatusCode"); //to get status code
                data.getStringExtra("Message"); //to get response message

                //  Toast.makeText(getActivity(), data.getStringExtra("Message") + "\n" + data.getStringExtra("TransactionType") + "\n" + data.getStringExtra("Response"), Toast.LENGTH_LONG).show();

                String response = data.getStringExtra("Response");

                JSONArray jsonarray = null;
                try {
                    jsonarray = new JSONArray(response);
                    for (int i = 0; i < jsonarray.length(); i++) {
                        JSONObject jsonobject = jsonarray.getJSONObject(i);
                        String Message = jsonobject.getString("Message");
                        String StatusCode = jsonobject.getString("StatusCode");
                        String RRN = jsonobject.getString("RRN");
                        String CustNo = jsonobject.getString("CustNo");
                        String bankmessage = jsonobject.getString("bankmessage");
                        String Stan_no = jsonobject.getString("Stan_no");
                        String Balance_Details = "";
                        String Amount = "";

                        if (StatusCode.equalsIgnoreCase("001")) {
                            if (TransactionType.equalsIgnoreCase("BalanceInquiryActivity")) {
                                if (jsonobject.has("Balance_Details")) {
                                    Balance_Details = jsonobject.getString("Balance_Details");
                                    new AlertDialog.Builder(getActivity())
                                            .setTitle(Message)
                                            .setMessage("RRN :" + RRN + "\n" +
                                                    "CustNo :" + CustNo + "\n" +
                                                    "Balance :" + Balance_Details + "\n" +
                                                    "Stan_no :" + Stan_no + "\n" +
                                                    "bankmessage :" + bankmessage)
                                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    dialogInterface.dismiss();

                                                }
                                            })
                                            .show();
                                   /* new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE)
                                            .setContentText("RRN :" + RRN + "\n" +
                                                    "CustNo :" + CustNo + "\n" +
                                                    "Balance :" + Balance_Details + "\n" +
                                                    "Stan_no :" + Stan_no + "\n" +
                                                    "bankmessage :" + bankmessage)
                                            .setTitleText(Message)
                                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                @Override
                                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                    sweetAlertDialog.dismiss();
                                                }
                                            })
                                            .show();*/
                                }
                            } else {
                                if (jsonobject.has("Amount")) {
                                    Amount = jsonobject.getString("Amount");
                                    new AlertDialog.Builder(getActivity())
                                            .setTitle(Message)
                                            .setMessage("RRN :" + RRN + "\n" +
                                                    "CustNo :" + CustNo + "\n" +
                                                    "Amount :" + Amount + "\n" +
                                                    "Stan_no :" + Stan_no + "\n" +
                                                    "bankmessage :" + bankmessage)
                                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    dialogInterface.dismiss();

                                                }
                                            })
                                            .show();
                                   /* new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE)
                                            .setContentText("RRN :" + RRN + "\n" +
                                                    "CustNo :" + CustNo + "\n" +
                                                    "Amount :" + Amount + "\n" +
                                                    "Stan_no :" + Stan_no + "\n" +
                                                    "bankmessage :" + bankmessage)
                                            .setTitleText(Message)
                                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                @Override
                                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                    sweetAlertDialog.dismiss();
                                                }
                                            })
                                            .show();*/
                                }
                            }
                        } else if (StatusCode.equalsIgnoreCase("009")) {
                            new AlertDialog.Builder(getActivity())
                                    .setTitle(Message)
                                    .setMessage("RRN :" + RRN + "\n" +
                                            "CustNo :" + CustNo + "\n" +
                                            "Stan_no :" + Stan_no + "\n" +
                                            "bankmessage :" + bankmessage)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.dismiss();

                                        }
                                    })
                                    .show();
                           /* new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                                    .setContentText("RRN :" + RRN + "\n" +
                                            "CustNo :" + CustNo + "\n" +
                                            "Stan_no :" + Stan_no + "\n" +
                                            "bankmessage :" + bankmessage)
                                    .setTitleText(Message)
                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                                            sweetAlertDialog.dismiss();
                                        }
                                    })
                                    .show();*/
                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                data.getStringExtra("StatusCode"); //to get status code
                data.getStringExtra("Message"); //to get response message

                // Log.e("datamessage", data.getStringExtra("StatusCode") + " , " + data.getStringExtra("Message"));

                //   Toast.makeText(getActivity(), data.getStringExtra("Message"), Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == INTENT_MINI_ATM_PAYS_PRINT) {
            if (resultCode == RESULT_OK && data != null) {

                boolean status = data.getBooleanExtra("status", false);
                int status_code = data.getIntExtra("response", 0);
                String status_msg = data.getStringExtra("message");

                String msg = "Status " + status + " Status code " + status_code + " Message " + status_msg;


                if (status && status_msg != null) {
                    // handleMiniATMPaySprintSDK();
                }
                if (!status && status_msg != null) {
                    UtilMethods.INSTANCE.Error(getActivity(), "StatusCode - " + status_code + "\nMessage- " + status_msg);
                } else {
                    UtilMethods.INSTANCE.Error(getActivity(), "Canceled");
                }
            } else {
                UtilMethods.INSTANCE.Error(getActivity(), "Failed");
            }
        } else if (requestCode == INTENT_AEPS_PAYS_PRINT) {
            if (resultCode == RESULT_OK && data != null) {

                boolean status = data.getBooleanExtra("status", false);
                int status_code = data.getIntExtra("response", 0);
                String status_msg = data.getStringExtra("message");

                String msg = "Status " + status + " Status code " + status_code + " Message " + status_msg;


                if (status && status_msg != null) {
                    handleAepsPaySprintSDK();
                }
                if (!status && status_msg != null) {
                    UtilMethods.INSTANCE.Error(getActivity(), "StatusCode - " + status_code + "\nMessage- " + status_msg);
                } else {
                    UtilMethods.INSTANCE.Error(getActivity(), "Canceled");
                }
            } else {
                UtilMethods.INSTANCE.Error(getActivity(), "Failed");
            }
        } else if (requestCode == INTENT_MINI_ATM) {
            if (data != null) {
                String respCode = data.getStringExtra("respcode");
                String refStan = data.getStringExtra("refstan");// Mahagram Stan Number
                String txnAmount = data.getStringExtra("txnamount");//Transaction amount (0 in case of balance inquiry and transaction amount in cash withdrawal)
                String cardNo = data.getStringExtra("cardno");//Atm card number
                String bankRemarks = data.getStringExtra("msg");//Bank Message
                if (resultCode == RESULT_OK) {
                    startActivity(new Intent(getActivity(), MiniAtmRecriptActivity.class)
                            .putExtras(data));
                    if (respCode != null && respCode.equals("00")) //Success
                    {
                        aPIStatus = "2";
                    } else if (respCode != null && respCode.equals("999")) //Pending
                    {
                        aPIStatus = "1";
                    } else {
                        aPIStatus = "3";
                    }
                    AEPSWebConnectivity.INSTANCE.InitiateMiniBank(getActivity(), sDKType + "", matmOID + "", txnAmount + "",
                            loginPrefResponse, loader, object -> {
                                InitiateMiniBankResponse miniBankResponse = (InitiateMiniBankResponse) object;
                                AEPSWebConnectivity.INSTANCE.UpdateMiniBankStatus(getActivity(), cardNo, "", loader, miniBankResponse.getTid() + "",
                                        refStan, aPIStatus, bankRemarks, loginPrefResponse, new UtilMethods.ApiCallBack() {
                                            @Override
                                            public void onSucess(Object object) {

                                            }
                                        });
                            });
                } else {
                    String statuscode = data.getStringExtra("statuscode ");//to get status code
                    String message = data.getStringExtra("message");//to get response message
                    if (statuscode.equals("111")) {
                        try {
                            startActivity(new Intent(Intent.ACTION_VIEW,
                                    Uri.parse("https://play.google.com/store/apps/details?id=org.egram.microatm")));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        UtilMethods.INSTANCE.Error(getActivity(), "" + message);
                    }
                    aPIStatus = "1";
                    AEPSWebConnectivity.INSTANCE.InitiateMiniBank(getActivity(), sDKType + "", matmOID + "", txnAmount != null ? txnAmount : "0",
                            loginPrefResponse, loader, object -> {
                                InitiateMiniBankResponse miniBankResponse = (InitiateMiniBankResponse) object;
                                AEPSWebConnectivity.INSTANCE.UpdateMiniBankStatus(getActivity(), "", "", loader, miniBankResponse.getTid() + "",
                                        refStan != null ? refStan : "NA", aPIStatus, message, loginPrefResponse, new UtilMethods.ApiCallBack() {
                                            @Override
                                            public void onSucess(Object object) {

                                            }
                                        });
                            });
                    UtilMethods.INSTANCE.Error(getActivity(), "StatusCode - " + statuscode + " Message- " + message);
                }
            } else {
                UtilMethods.INSTANCE.Error(getActivity(), "Sorry, data not found");
            }
        } else if (requestCode == INTENT_MINI_ATM_RP) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    boolean status = data.getBooleanExtra(KeyConstant.TRANS_STATUS, false);
                    String message = data.getStringExtra(KeyConstant.MESSAGE);
                    if (!status && message.equalsIgnoreCase("Canceled")) {
                        UtilMethods.INSTANCE.Error(getActivity(), "Canceled");
                    } else {
                        startActivity(new Intent(getActivity(), MicroATMStatusActivity.class)
                                .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                                .putExtras(data));
                    }
                }


            } else {
                if (data != null) {
                    String status_code = data.getStringExtra(KeyConstant.STATUS_CODE);
                    int error_code = data.getIntExtra(KeyConstant.ERROR_CODE, 0);
                    String error_msg = data.getStringExtra(KeyConstant.ERROR_MSG);
                    if (error_msg != null) {
                        UtilMethods.INSTANCE.Error(getActivity(), "StatusCode - " + error_code + "\nMessage- " + error_msg);
                    } else {
                        UtilMethods.INSTANCE.Error(getActivity(), "Canceled");
                    }
                } else {
                    UtilMethods.INSTANCE.Error(getActivity(), "Canceled");
                }
            }
        } else if (requestCode == INTENT_AEPS_RP) {

            if (resultCode == RESULT_OK && data != null) {

                int error_code = data.getIntExtra(KeyConstant.ERROR_CODE, 0);
                String error_msg = data.getStringExtra(KeyConstant.ERROR_MSG);
                String aadharNum = data.getStringExtra(KeyConstant.AADHAR_NUM);
                String bankName = data.getStringExtra(KeyConstant.BANK_NAME);
                String deviceName = data.getStringExtra(KeyConstant.DEVICE_NAME);
                int type = data.getIntExtra(KeyConstant.TYPE, 0);
                double balAmount = data.getDoubleExtra(KeyConstant.BALANCE_AMOUNT, 0);
                ArrayList<MiniStatements> miniStatements = (ArrayList<MiniStatements>) data.getSerializableExtra(KeyConstant.MINI_STATEMENT_LIST);
                boolean status = data.getBooleanExtra(KeyConstant.TRANS_STATUS, false);
                if (status) {
                    if (type == KeyConstant.MINI_STATEMENT && miniStatements != null && miniStatements.size() > 0) {
                        Intent intent = new Intent(getActivity(), AEPSMiniStatementRPActivity.class);
                        intent.putExtra("OP_NAME", bankName + "");
                        intent.putExtra("BALANCE", "\u20B9 " + UtilMethods.INSTANCE.formatedAmount(balAmount + ""));
                        intent.putExtra("MINI_STATEMENT", miniStatements);
                        intent.putExtra("NUMBER", aadharNum + "");
                        intent.putExtra("Device_NAME", deviceName + "");
                        startActivity(intent);

                    } else if (type != 0) {
                        Intent intent = new Intent(getActivity(), AEPSStatusRPActivity.class);
                        intent.putExtras(data);
                        startActivity(intent);
                    } else {
                        UtilMethods.INSTANCE.Error(getActivity(), "StatusCode - " + error_code + "\nMessage- " + error_msg);

                    }
                } else {
                    UtilMethods.INSTANCE.Error(getActivity(), "StatusCode - " + error_code + "\nMessage- " + error_msg);
                }


            } else {
                if (data != null) {
                    String status_code = data.getStringExtra(KeyConstant.STATUS_CODE);
                    int error_code = data.getIntExtra(KeyConstant.ERROR_CODE, 0);
                    String error_msg = data.getStringExtra(KeyConstant.ERROR_MSG);
                    if (error_msg != null) {
                        UtilMethods.INSTANCE.Error(getActivity(), "StatusCode - " + error_code + "\nMessage- " + error_msg);
                    } else {
                        UtilMethods.INSTANCE.Error(getActivity(), "Canceled");
                    }
                } else {
                    UtilMethods.INSTANCE.Error(getActivity(), "Canceled");
                }

            }
        } else if (requestCode == INTENT_CODE_CMS) {
            if (resultCode == RESULT_OK) {
                if (data != null && data.getStringExtra(Constants.TXN_ID) != null) {
                    String txnId = data.getStringExtra(Constants.TXN_ID);
                    Intent intent = new Intent(getContext(), CmsReceiptActivity.class);
                    intent.putExtra("cmsTId", txnId);
                    startActivity(intent);
                }
            } else {
                Log.d("CMSCallback", "error box");
            }
        }
        else {
            if (mGetLocation != null) {
                mGetLocation.onActivityResult(requestCode, resultCode, data);
            }
        }


    }

    private void handleAepsPaySprintSDK() {
        startActivity(new Intent(getActivity(), FingPayAEPSDashBoardActivity.class)
                .putExtra("SDKDetails", mSdkDetail)
                .putExtra("InterfaceType", sDKType)
                .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
    }

    public void BannerApi() {
        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);

            Call<AppUserListResponse> call = git.GetAppBanner(new BasicRequest(
                    loginPrefResponse.getData().getUserID(), loginPrefResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID,
                    UtilMethods.INSTANCE.getIMEI(getActivity()),
                    "", BuildConfig.VERSION_NAME, UtilMethods.INSTANCE.getSerialNo(getActivity()),
                    loginPrefResponse.getData().getSessionID(), loginPrefResponse.getData().getSession()));

            call.enqueue(new Callback<AppUserListResponse>() {
                @Override
                public void onResponse(Call<AppUserListResponse> call, final retrofit2.Response<AppUserListResponse> response) {

                    try {

                        if (response.body() != null && response.body().getStatuscode() == 1) {
                            UtilMethods.INSTANCE.setAppLogoUrl(getActivity(), response.body().getAppLogoUrl() + "");
                            if (response.body().getBanners() != null && response.body().getBanners().size() > 0) {
                                try {
                                    bannerData = response.body();
                                    ((MainActivity) getActivity()).bannerData = bannerData;
                                } catch (NullPointerException npe) {

                                }
                                UtilMethods.INSTANCE.setBannerData(getActivity(), new Gson().toJson(response.body()));
                                setBannerData(response.body());
                            } else {
                                pagerContainer.setVisibility(View.GONE);
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


    void setBannerData(AppUserListResponse response) {
        try {
            if (response != null && response.getBanners() != null && response.getBanners().size() > 0) {
                bannerList.clear();
                bannerList.addAll(response.getBanners());
                if (bannerList != null && bannerList.size() > 0) {
                    pagerContainer.setVisibility(View.VISIBLE);
                    mCustomPagerAdapter = new CustomPagerAdapter(bannerList, getActivity(), 1);
                    mViewPager.setAdapter(mCustomPagerAdapter);
                    mViewPager.setOffscreenPageLimit(mCustomPagerAdapter.getCount());
                    mViewPager.setPageMargin(pagerleft);
                    mViewPager.setPadding(pagerleft, pagerTop, pagerleft, pagerTop);
                    mViewPager.setClipToPadding(false);
                    mDotsCount = mViewPager.getAdapter().getCount();
                    mDotsText = new TextView[mDotsCount];
                    //here we set the dots
                    for (int i = 0; i < mDotsCount; i++) {
                        mDotsText[i] = new TextView(getActivity());
                        mDotsText[i].setText(".");
                        mDotsText[i].setTextSize(50);
                        mDotsText[i].setTypeface(null, Typeface.BOLD);
                        mDotsText[i].setTextColor(getResources().getColor(R.color.light_grey));
                        dotsCount.addView(mDotsText[i]);
                    }

                                    /*mViewPager.setOnTouchListener(new View.OnTouchListener() {
                                        @Override
                                        public boolean onTouch(View v, MotionEvent event) {
                                            return false;
                                        }
                                    });*/


                    postDelayedScrollNext();
                } else {
                    pagerContainer.setVisibility(View.GONE);
                }
            } else {
                pagerContainer.setVisibility(View.GONE);
            }
        } catch (IllegalStateException ils) {

        } catch (Exception e) {

        }
    }

    private void postDelayedScrollNext() {
        try {
            handler = new Handler();
            mRunnable = new Runnable() {
                public void run() {

                    if (mViewPager.getAdapter() != null) {
                        if (mViewPager.getCurrentItem() == mViewPager.getAdapter().getCount() - 1) {
                            mViewPager.setCurrentItem(0);
                            postDelayedScrollNext();
                            return;
                        }
                        mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
                        // postDelayedScrollNext(position+1);
                        postDelayedScrollNext();
                    }

                    // onKeyDown(KeyEvent.KEYCODE_DPAD_RIGHT, null);
                }
            };
            handler.postDelayed(mRunnable, 2500);
        } catch (IllegalStateException ise) {

        } catch (Exception e) {

        }
    }


    public void DashboardApi() {
        if (UtilMethods.INSTANCE.isNetworkAvialable(getActivity())) {
            try {
                HitDayReportApi();
                BannerApi();

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            UtilMethods.INSTANCE.NetworkError(getActivity(), getResources().getString(R.string.err_msg_network_title),
                    getResources().getString(R.string.err_msg_network));
        }
    }

    private void HitDayReportApi() {
        if (UtilMethods.INSTANCE.isNetworkAvialable(getActivity())) {

            if (userRollId == null || userRollId.isEmpty()) {
                userRollId = loginPrefResponse.getData().getRoleID();
            }
            String childNumber;
            if (userRollId.equalsIgnoreCase("3") || userRollId.equalsIgnoreCase("2")) {
                childNumber = "";
            } else {
                childNumber = loginPrefResponse.getData().getMobileNo();
            }
            //UtilMethods.INSTANCE.FundDCReport(this, ServiceID, "0", "",mobileNo, fromdate, todate, "", "", "false", loader);

            if (tragetView.getVisibility() == View.GONE) {
                UtilMethods.INSTANCE.UserAchieveTarget(getActivity(), true, null, new UtilMethods.ApiCallBack() {
                    @Override
                    public void onSucess(Object object) {
                        achieveTargetData = (AppUserListResponse) object;
                        try {
                            ((MainActivity) getActivity()).achieveTargetData = achieveTargetData;
                        } catch (NullPointerException npe) {

                        }
                        achieveTargetParse(achieveTargetData);
                    }
                });
            }
            if (isLoaderShouldShow) {
                loader.show();
            }
            UtilMethods.INSTANCE.UserDayBook(getActivity(), childNumber, fromDate, toDate, isLoaderShouldShow ? loader : null, new UtilMethods.ApiCallBack() {
                @Override
                public void onSucess(Object object) {
                    dayBookData = (AppUserListResponse) object;
                    try {
                        ((MainActivity) getActivity()).dayBookData = dayBookData;
                    } catch (NullPointerException npe) {

                    }
                    dataDayBookParse(dayBookData);
                }
            });

        } else {
            UtilMethods.INSTANCE.NetworkError(getActivity(), getResources().getString(R.string.err_msg_network_title),
                    getResources().getString(R.string.err_msg_network));
        }
    }

    public void achieveTargetParse(AppUserListResponse response) {
        try {
            if (response != null) {

                List<TargetAchieved> transactionsObjects = response.getTargetAchieveds();
                if (transactionsObjects != null && transactionsObjects.size() > 0) {

                    int remainigTarget = (int) (transactionsObjects.get(0).getTarget() - transactionsObjects.get(0).getTargetTillDate());
                    if (remainigTarget < 0) {
                        remainTargetTv.setTextColor(getResources().getColor(R.color.darkGreen));
                    }
                    remainTargetTv.setText(getString(R.string.rupiya) + " " + remainigTarget);
                    salesTargetTv.setText(getString(R.string.rupiya) + " " + (int) transactionsObjects.get(0).getTarget());
                    todaySalesTv.setText(getString(R.string.rupiya) + " " + (int) transactionsObjects.get(0).getTodaySale());
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dataDayBookParse(AppUserListResponse response) {
        try {
            if (response != null) {
                double successAmount = 0, pendingAmount = 0, failedAmount = 0, commissionAmount = 0;
                int successHit = 0, pendingHit = 0, failedHit = 0, commissionHit = 0;
                List<UserDaybookReport> transactionsObjects = response.getUserDaybookReport();
                if (transactionsObjects != null && transactionsObjects.size() > 0) {

                    for (UserDaybookReport mUserDaybookReport : transactionsObjects) {
                        try {
                            successAmount = successAmount + mUserDaybookReport.getSuccessAmount();
                        } catch (NumberFormatException nfe) {

                        }

                        try {
                            successHit = successHit + mUserDaybookReport.getSuccessHits();
                        } catch (NumberFormatException nfe) {

                        }
                        try {
                            if (userRollId.equalsIgnoreCase("2") || userRollId.equalsIgnoreCase("3")) {
                                commissionAmount = commissionAmount + mUserDaybookReport.getCommission();
                            } else {
                                commissionAmount = commissionAmount + mUserDaybookReport.getSelfCommission();
                            }

                        } catch (NumberFormatException nfe) {

                        }

                        try {
                            commissionHit = commissionHit + mUserDaybookReport.getTotalHits();
                        } catch (NumberFormatException nfe) {
                        }
                        try {
                            pendingAmount = pendingAmount + mUserDaybookReport.getPendingAmount();
                        } catch (NumberFormatException nfe) {

                        }
                        try {
                            pendingHit = pendingHit + mUserDaybookReport.getPendingHits();
                        } catch (NumberFormatException nfe) {
                        }
                        try {
                            failedAmount = failedAmount + mUserDaybookReport.getFailedAmount();
                        } catch (NumberFormatException nfe) {


                        }
                        try {
                            failedHit = failedHit + mUserDaybookReport.getFailedHits();
                        } catch (NumberFormatException nfe) {

                        }
                    }
                }
                //  int todaySales = (int) (successAmount + pendingAmount);
                // todaySalesTv.setText(getString(R.string.rupiya) + " " + todaySales);
                successAmountTv.setText(getString(R.string.rupiya) + " " + UtilMethods.INSTANCE.formatedAmount(String.format("%.2f", successAmount)) + " (" + successHit + ")");
                failedAmountTv.setText(getString(R.string.rupiya) + " " + UtilMethods.INSTANCE.formatedAmount(String.format("%.2f", failedAmount)) + " (" + failedHit + ")");
                pendingAmountTv.setText(getString(R.string.rupiya) + " " + UtilMethods.INSTANCE.formatedAmount(String.format("%.2f", pendingAmount)) + " (" + pendingHit + ")");
                commissionAmountTv.setText(getString(R.string.rupiya) + " " + UtilMethods.INSTANCE.formatedAmount(String.format("%.2f", commissionAmount)) + " (" + commissionHit + ")");
            } else {
                isLoaderShouldShow = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((MainActivity) context).mRefreshCallBack = this;
        ((MainActivity) context).mBalanceRefreshCallBack = this;
    }

    @Override
    public void onRefresh(Object object) {
        if (userRollId == null || userRollId.isEmpty()) {
            userRollId = loginPrefResponse.getData().getRoleID();
        }
        if (userRollId.equalsIgnoreCase("3") || userRollId.equalsIgnoreCase("2")) {

            isRechargeViewEnable = true;
            titleTv.setText("Recharge & Pay Bills");

        } else {
            titleTv.setText("Reports");
            isRechargeViewEnable = false;
        }
        BannerApi();
        mActiveServiceData = (OpTypeRollIdWiseServices) object;
        try {
            isECollectEnable = ((MainActivity) getActivity()).isECollectEnable;
            isAddMoneyEnable = ((MainActivity) getActivity()).isAddMoneyEnable;
            isPaymentGatway = ((MainActivity) getActivity()).isPaymentGatway;
            isUPI = ((MainActivity) getActivity()).isUPI;

        } catch (NullPointerException npe) {

        }
        setDashboardData(mActiveServiceData);
        UtilMethods.INSTANCE.NewsApi(getActivity(), false, newsWeb, newsCard);

    }
}

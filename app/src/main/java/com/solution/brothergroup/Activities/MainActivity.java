package com.solution.brothergroup.Activities;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.core.widget.ImageViewCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.ListPopupWindow;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.google.gson.Gson;
import com.solution.brothergroup.Api.Object.OpTypeRollIdWiseServices;
import com.solution.brothergroup.Api.Response.AppUserListResponse;
import com.solution.brothergroup.Api.Response.BalanceResponse;
import com.solution.brothergroup.Authentication.dto.LoginResponse;
import com.solution.brothergroup.BuildConfig;
import com.solution.brothergroup.Fragments.HomeFragment;
import com.solution.brothergroup.Fragments.ProfileFragment;
import com.solution.brothergroup.Fragments.ReportFragment;
import com.solution.brothergroup.Fragments.SupportFragment;
import com.solution.brothergroup.Fragments.dto.BalanceType;
import com.solution.brothergroup.Fragments.dto.GetUserResponse;
import com.solution.brothergroup.Fragments.interfaces.RefreshBalanceCallBack;
import com.solution.brothergroup.Fragments.interfaces.RefreshCallBack;
import com.solution.brothergroup.Notification.NotificationListActivity;
import com.solution.brothergroup.R;
import com.solution.brothergroup.UPIPayment.UI.UPIQRCodeActivity;
import com.solution.brothergroup.Util.ActivityActivityMessage;
import com.solution.brothergroup.Util.ApplicationConstant;
import com.solution.brothergroup.Util.ChangePassUtils;
import com.solution.brothergroup.Util.CustomAlertDialog;
import com.solution.brothergroup.Util.ListPopupWindowAdapter;
import com.solution.brothergroup.Util.OpTypeResponse;
import com.solution.brothergroup.Util.UtilMethods;
import com.solution.brothergroup.usefull.CustomLoader;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public RefreshCallBack mRefreshCallBack;
    public RefreshBalanceCallBack mBalanceRefreshCallBack;
    public View bottomlayout;
    public AppUserListResponse bannerData;
    public AppUserListResponse dayBookData;
    public AppUserListResponse achieveTargetData;
    public GetUserResponse mGetUserResponse;
    CustomLoader loader;
    LinearLayout ll_Supportlayout, amountView;
    ImageView fab;
    LinearLayout ll_home, ll_Report, ll_profile, ll_logout, ll_more;
    ImageView refresh, qrCode;
    int selectBottomMenuIndex;
    String versionName = "";
    int versionCode = -1;
    TextView balance;
    TextView utility;
    TextView tv_userdetail;
    // Handler handler;
    SharedPreferences myPrefs;
    View notificationView;
    TextView badges_Notification;
    public BalanceResponse balanceCheckResponse;
    ImageView homeIv, profileIv, reportIv, moreIv, logoutIv;
    TextView homeTv, profileTv, reportTv, moreTv, logoutTv;
    public ChangePassUtils mChangePassUtils;
    boolean isBankWalletActive = false;
    private int notificationCount;
    private int INTENT_NOTIFICATIONS = 538;
    private boolean isProfileClick;
    public int loginTypeId;
    private boolean isEnabled=false;
    TextToSpeech tts;
    private String FragmentTag="Home";
    private String version = "";
    public CustomAlertDialog customAlertDialog;
    private BroadcastReceiver mNewNotificationReciver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            UtilMethods.INSTANCE.GetNotifications(MainActivity.this, new UtilMethods.ApiCallBack() {
                @Override
                public void onSucess(Object object) {
                    notificationCount = ((int) object);
                    setNotificationCount();
                }
            });
        }
    };

    public boolean isDMTWithPipe;
    public boolean isECollectEnable;
    public boolean isUpiQR;
    private SharedPreferences myPreferences;
    public LoginResponse LoginPrefResponse;
    private boolean isBulkQRGeneration, isQRMappedToUser;
    public boolean isAddMoneyEnable, isPaymentGatway, isUPI;
    public OpTypeRollIdWiseServices mActiveServiceData;
    public AppUserListResponse companyProfileData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
            CheckNmberlist();
            UtilMethods.INSTANCE.setDashboardStatus(this, true);
            customAlertDialog = new CustomAlertDialog(this, true);
            mChangePassUtils = new ChangePassUtils(this);
            getID();
            ChangeFragment(new HomeFragment(), FragmentTag);
            myPreferences = getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
            myPrefs = getSharedPreferences(ApplicationConstant.INSTANCE.prefNameLoginPref, MODE_PRIVATE);
            String getLoginPref = myPrefs.getString(ApplicationConstant.INSTANCE.LoginPref, "");
            LoginPrefResponse = new Gson().fromJson(getLoginPref, LoginResponse.class);
            tv_userdetail.setText(LoginPrefResponse.getData().getName() + " (" + LoginPrefResponse.getData().getRoleName() + ")");

            if (LoginPrefResponse.getData().getRoleID().equalsIgnoreCase("3") || LoginPrefResponse.getData().getRoleID().equalsIgnoreCase("2")) {
                ll_Report.setVisibility(View.VISIBLE);
                ll_home.setVisibility(View.VISIBLE);
            } else {
                ll_Report.setVisibility(View.GONE);
                ll_home.setVisibility(View.VISIBLE);
            }
            DashboardApi(false);
            LocalBroadcastManager.getInstance(this).registerReceiver(mNewNotificationReciver, new IntentFilter("New_Notification_Detect"));
            isUpiQR = myPreferences.getBoolean(ApplicationConstant.INSTANCE.isUpiQR, false);
            isECollectEnable = myPreferences.getBoolean(ApplicationConstant.INSTANCE.isECollectEnable, false);
            if (isUpiQR) {
                qrCode.setVisibility(View.VISIBLE);
            } else {
                qrCode.setVisibility(View.GONE);
            }

//        new Handler(Looper.getMainLooper()).post(() -> {

//        });

    }

    private void CheckNmberlist() {
        if (UtilMethods.INSTANCE.getNumberList(MainActivity.this) != null
                && !UtilMethods.INSTANCE.getNumberList(MainActivity.this).isEmpty()) {

        } else {
            if (UtilMethods.INSTANCE.isNetworkAvialable(MainActivity.this)) {
                loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
                loader.isShowing();
                loader.setCancelable(false);
                UtilMethods.INSTANCE.NumberList(MainActivity.this, loader, 0);
            } else {
                UtilMethods.INSTANCE.NetworkError(MainActivity.this, getResources().getString(R.string.err_msg_network_title),
                        getResources().getString(R.string.err_msg_network));
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        UtilMethods.INSTANCE.BalancecheckNew(this, customAlertDialog, mChangePassUtils, null);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onPause() {
        EventBus.getDefault().unregister(this);
        super.onPause();
    }

    public void ChangeFragment(Fragment targetFragment, String name) {
        if (!name.equalsIgnoreCase("Home")) {
            UtilMethods.INSTANCE.BalancecheckNew(this, customAlertDialog, mChangePassUtils, null);
        }
//        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//        fragmentTransaction.replace(R.id.frame, fragment).addToBackStack("a");
//        fragmentTransaction.commit();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame, targetFragment, name)
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();

    }
/*    public void ChangeFragment(Fragment targetFragment, String name) {
        if (!name.equalsIgnoreCase("Home")) {
            UtilMethods.INSTANCE.BalancecheckNew(this, customAlertDialog, mChangePassUtils, null);
        }
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame, targetFragment, name)
                .addToBackStack("a")
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }*/


    private void getID() {
        loader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        balance = findViewById(R.id.balance);
        bottomlayout = findViewById(R.id.bottomlayout);
        notificationView = findViewById(R.id.notificationView);
        badges_Notification = findViewById(R.id.badgesNotification);
        tv_userdetail = findViewById(R.id.tv_userdetail);
        utility = findViewById(R.id.utility);
        refresh = findViewById(R.id.refresh);
        qrCode = findViewById(R.id.qrCodee);
        ll_more = findViewById(R.id.ll_more);
        ll_Supportlayout = findViewById(R.id.ll_Supportlayout);
        amountView = findViewById(R.id.amountView);
        ll_home = findViewById(R.id.homeButton);
        ll_Report = findViewById(R.id.ll_Report);
        ll_logout = findViewById(R.id.ll_logout);
        ll_profile = findViewById(R.id.profiles);
        fab = findViewById(R.id.fab);
        homeIv = findViewById(R.id.homeIv);
        profileIv = findViewById(R.id.profileIv);
        reportIv = findViewById(R.id.reportIv);
        moreIv = findViewById(R.id.moreIv);
        logoutIv = findViewById(R.id.logoutIv);
        homeTv = findViewById(R.id.homeTv);
        profileTv = findViewById(R.id.profileTv);
        reportTv = findViewById(R.id.reportTv);
        moreTv = findViewById(R.id.moreTv);
        logoutTv = findViewById(R.id.logoutTv);
        setLisener();
    }

    private void setLisener() {
        refresh.setOnClickListener(this);
        qrCode.setOnClickListener(this);
        fab.setOnClickListener(this);
        ll_home.setOnClickListener(this);
        ll_Report.setOnClickListener(this);
        ll_profile.setOnClickListener(this);
        ll_logout.setOnClickListener(this);
        ll_more.setOnClickListener(this);
        notificationView.setOnClickListener(this);
        amountView.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (UtilMethods.INSTANCE.isOnDashboard(this)) {
            CustomAlertDialog customAlertDialog = new CustomAlertDialog(this, true);
            customAlertDialog.ExitWithCallBack("Do you really want to Exist?", MainActivity.this);
        } else {
            // super.onBackPressed();
            ImageViewCompat.setImageTintList(homeIv, AppCompatResources.getColorStateList(this, R.color.colorPrimary));
            homeTv.setTextColor(getResources().getColor(R.color.colorPrimary));
            List<Fragment> fragments = getSupportFragmentManager().getFragments();
            for (Fragment f : fragments) {
                if (f instanceof ProfileFragment) {
                    ImageViewCompat.setImageTintList(profileIv, AppCompatResources.getColorStateList(this, R.color.light_grey));
                    profileTv.setTextColor(getResources().getColor(R.color.light_grey));
                }
                if (f instanceof ReportFragment) {
                    ImageViewCompat.setImageTintList(reportIv, AppCompatResources.getColorStateList(this, R.color.light_grey));
                    reportTv.setTextColor(getResources().getColor(R.color.light_grey));
                }
                if (f instanceof SupportFragment) {
                    ImageViewCompat.setImageTintList(moreIv, AppCompatResources.getColorStateList(this, R.color.light_grey));
                    moreTv.setTextColor(getResources().getColor(R.color.light_grey));
                }
            }

            //To delete all entries from back stack immediately one by one.
            int backStackEntry = getSupportFragmentManager().getBackStackEntryCount();
            for (int i = 0; i < backStackEntry; i++) {
                getSupportFragmentManager().popBackStack();
            }
            FragmentTag = "Home";
            ChangeFragment(new HomeFragment(), FragmentTag);
        }
    }

    @Override
    public void onClick(View v) {
        if (v == notificationView) {
            Intent i = new Intent(this, NotificationListActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivityForResult(i, INTENT_NOTIFICATIONS);
        }
        /*if (v == qrCode) {
            GetQRBank("1");
        }*/
        if (v == qrCode) {
            Intent intent = new Intent(MainActivity.this, UPIQRCodeActivity.class);
            intent.putExtra("isECollectEnable", isECollectEnable);
            intent.putExtra("isQRMappedToUser", isQRMappedToUser);
            intent.putExtra("isBulkQRGeneration", isBulkQRGeneration);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        }
        if (v == refresh) {
            RotateAnimation rotate = new RotateAnimation(0, 180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotate.setDuration(500);
            rotate.setRepeatCount(Animation.INFINITE);
            rotate.setInterpolator(new LinearInterpolator());
            refresh.startAnimation(rotate);
            if (!LoginPrefResponse.getData().getRoleID().equalsIgnoreCase("3") && !LoginPrefResponse.getData().getRoleID().equalsIgnoreCase("2")) {
                UtilMethods.INSTANCE.GetArealist(this, loader, LoginPrefResponse, null);
            }

            DashboardApi(true);
            UtilMethods.INSTANCE.NumberList(this, refresh, null);
           /* handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    refresh.clearAnimation();
                }
            }, 2000);*/
        }
        if (v == amountView) {
            showWalletListPopupWindow(v);
        }
        if (v == fab) {
            if (ll_Supportlayout.getVisibility() == View.GONE) {
                Animation animFadeIn = AnimationUtils.loadAnimation(getApplicationContext(),
                        R.anim.right_to_left_slide);
                ll_Supportlayout.startAnimation(animFadeIn);
                ll_Supportlayout.setVisibility(View.VISIBLE);
            } else {
                Animation animFadeIn = AnimationUtils.loadAnimation(getApplicationContext(),
                        R.anim.fade_out);
                ll_Supportlayout.startAnimation(animFadeIn);
                ll_Supportlayout.setVisibility(View.GONE);
            }
        }
        if (v == ll_home) {
            FragmentTag = "Home";
            ChangeFragment(new HomeFragment(), FragmentTag);
            setBottomClickView(ll_home);
            if(isProfileClick && loginTypeId!=3)
            {
                if(UtilMethods.INSTANCE.isVoiceEnable(this)){
                    tts=new TextToSpeech(this,null);
                }
                else
                {
                    tts=null;
                }
            }
            isProfileClick=false;

        }
        if (v == ll_profile) {
            FragmentTag="Profile";
                ChangeFragment(new ProfileFragment(),FragmentTag);
                setBottomClickView(ll_profile);
                isProfileClick=true;

        }
        if (v == ll_Report) {
            FragmentTag="Report";
            ChangeFragment(new ReportFragment(),FragmentTag);
            setBottomClickView(ll_Report);
        }
        if (v == ll_more) {

            FragmentTag="More";
            ChangeFragment(new SupportFragment(),FragmentTag);
            setBottomClickView(ll_more);
        }

        if (v == ll_logout) {
            CustomAlertDialog customAlertDialog = new CustomAlertDialog(this, true);
            customAlertDialog.Successfullogout("Do you really want to Logout?", MainActivity.this);

        }
    }

    public void setBottomClickView(View clickedView) {
        //Following code will set the icon of the bottom navigation to active
        final LinearLayout mBottomNav = findViewById(R.id.bottomlayout);
        /*View homeItem = mBottomNav.getChildAt(0);*/
        for (int setId = 0; setId< mBottomNav.getChildCount(); setId++) {
            if(mBottomNav.getChildAt(setId)==clickedView){
                //homeItem.setBackgroundResource(R.drawable.rounded_primary_border_fill);
                ViewGroup viewGroup= (ViewGroup)mBottomNav.getChildAt(setId);
                if(viewGroup.getChildAt(0) instanceof ImageView)
                {
                    ImageViewCompat.setImageTintList((ImageView) viewGroup.getChildAt(0), AppCompatResources.getColorStateList(this, R.color.colorPrimary));
                }if(viewGroup.getChildAt(1) instanceof TextView)
                {
                    ((TextView) viewGroup.getChildAt(1)).setTextColor(getResources().getColor(R.color.colorPrimary));
                }
            }else {
                ViewGroup viewGroup= (ViewGroup)mBottomNav.getChildAt(setId);
                if(viewGroup.getChildAt(0) instanceof ImageView)
                {
                    ImageViewCompat.setImageTintList((ImageView) viewGroup.getChildAt(0), AppCompatResources.getColorStateList(this, R.color.light_grey));

                }if(viewGroup.getChildAt(1) instanceof TextView)
                {
                    ((TextView) viewGroup.getChildAt(1)).setTextColor(getResources().getColor(R.color.light_grey));
                }
            }
        }
    }

    private void showWalletListPopupWindow(View anchor) {
        ArrayList<BalanceType> mBalanceTypes = new ArrayList<>();
        if (balanceCheckResponse != null) {
            isBulkQRGeneration = balanceCheckResponse.isBulkQRGeneration();
            isQRMappedToUser = balanceCheckResponse.getBalanceData().isQRMappedToUser();
            if (balanceCheckResponse.getBalanceData().isBalance()) {
                mBalanceTypes.add(new BalanceType("Prepaid Wallet", balanceCheckResponse.getBalanceData().getBalance() + ""));
            }
            if (balanceCheckResponse.getBalanceData().isUBalance()) {
                mBalanceTypes.add(new BalanceType("Utility Wallet", balanceCheckResponse.getBalanceData().getuBalance() + ""));

            }
            if (balanceCheckResponse.getBalanceData().isBBalance()) {
                mBalanceTypes.add(new BalanceType("Bank Wallet", balanceCheckResponse.getBalanceData().getbBalance() + ""));
                isBankWalletActive = true;
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
        } else {
            String balanceResponse = myPreferences.getString(ApplicationConstant.INSTANCE.balancePref, "");
            balanceCheckResponse = new Gson().fromJson(balanceResponse, BalanceResponse.class);
            showWalletListPopupWindow(anchor);
            return;
        }
        final ListPopupWindow listPopupWindow =
                createListPopupWindow(anchor, ViewGroup.LayoutParams.WRAP_CONTENT, mBalanceTypes);

        listPopupWindow.show();
    }

    private ListPopupWindow createListPopupWindow(View anchor, int width, ArrayList<BalanceType> items) {
        final ListPopupWindow popup = new ListPopupWindow(this);
        ListAdapter adapter = new ListPopupWindowAdapter(items, this, isBankWalletActive, R.layout.item_list_popup, null);
        popup.setWidth((int) getResources().getDimension(R.dimen._200sdp));
        popup.setAnchorView(anchor);
        popup.setAdapter(adapter);
        return popup;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == INTENT_NOTIFICATIONS) {
            if (resultCode == RESULT_OK) {
                notificationCount = notificationCount - data.getIntExtra("Notification_Count", 0);
                setNotificationCount();
            }
        }
        else {
            Fragment myFragment = getSupportFragmentManager().findFragmentByTag("Home");
            if (myFragment != null && myFragment.isVisible()) {
                HomeFragment fragment = (HomeFragment) myFragment;
                if (fragment.mGetLocation != null) {
                    fragment.mGetLocation.onActivityResult(requestCode, resultCode, data);
                }
            }
        }
    }

    void setNotificationCount() {
        if (notificationCount != 0) {
            badges_Notification.setVisibility(View.VISIBLE);

            if (notificationCount > 99) {
                badges_Notification.setText("99+");

            } else {
                badges_Notification.setText(notificationCount + "");

            }
        } else {
            badges_Notification.setVisibility(View.GONE);

        }
    }

    public void DashboardApi(boolean isRefresh) {
        if (UtilMethods.INSTANCE.isNetworkAvialable(this)) {
            try {
                UtilMethods.INSTANCE.WalletType(this, null);
                UtilMethods.INSTANCE.GetNotifications(this, new UtilMethods.ApiCallBack() {
                    @Override
                    public void onSucess(Object object) {
                        notificationCount = ((int) object);
                        setNotificationCount();
                    }
                });

                if (isRefresh) {
                    UtilMethods.INSTANCE.GetCompanyProfile(this, new UtilMethods.ApiCallBack() {
                        @Override
                        public void onSucess(Object object) {
                            companyProfileData = (AppUserListResponse) object;
                        }
                    });
                    UtilMethods.INSTANCE.GetActiveService(this, new UtilMethods.ApiActiveServiceCallBack() {
                        @Override
                        public void onSucess(OpTypeResponse mOpTypeResponse, OpTypeRollIdWiseServices mOpTypeRollIdWiseServices) {
                            mActiveServiceData = mOpTypeRollIdWiseServices;
                            isUpiQR = mOpTypeResponse.getUPIQR();
                            isECollectEnable = mOpTypeResponse.getECollectEnable();
                            isAddMoneyEnable = mOpTypeResponse.getAddMoneyEnable();
                            isPaymentGatway = mOpTypeResponse.getPaymentGatway();
                            isUPI = mOpTypeResponse.getUPI();
                            if (isUpiQR) {
                                qrCode.setVisibility(View.VISIBLE);
                            } else {
                                qrCode.setVisibility(View.GONE);
                            }
                            if (mRefreshCallBack != null) {
                                mRefreshCallBack.onRefresh(mOpTypeRollIdWiseServices);
                            }
                        }


                    });
                }


            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            UtilMethods.INSTANCE.NetworkError(this, getResources().getString(R.string.err_msg_network_title),
                    getResources().getString(R.string.err_msg_network));
        }
    }

    @Override
    public void onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mNewNotificationReciver);
        super.onDestroy();
    }

    @Subscribe
    public void onActivityActivityMessage(ActivityActivityMessage activityActivityMessage) {
        if (activityActivityMessage.getFrom().equalsIgnoreCase("balanceUpdate")) {
            SetBalance(activityActivityMessage.getMessage());

        }

    }

    public void SetBalance(String balanceData) {
        try {

            balanceCheckResponse = new Gson().fromJson(balanceData, BalanceResponse.class);
            String prepaidWallet = UtilMethods.INSTANCE.formatedAmount(balanceCheckResponse.getBalanceData().getBalance() + "");
            isBulkQRGeneration = balanceCheckResponse.isBulkQRGeneration();
            isQRMappedToUser = balanceCheckResponse.getBalanceData().isQRMappedToUser();
            balance.setText(" " + getResources().getString(R.string.rupiya) + ". " + prepaidWallet);
            if (mBalanceRefreshCallBack != null) {
                mBalanceRefreshCallBack.onBalanceRefresh(balanceCheckResponse);
            }
        } catch (Exception e) {

        }

    }

    private void goToVersionUpdate() {

        try {
            startActivityForResult(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=" + BuildConfig.APPLICATION_ID)), 41);
        } catch (android.content.ActivityNotFoundException anfe) {
            startActivityForResult(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=" +
                            BuildConfig.APPLICATION_ID)), 41);
        }
        // finish();
    }


}

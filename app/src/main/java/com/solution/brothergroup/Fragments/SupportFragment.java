package com.solution.brothergroup.Fragments;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import retrofit2.Call;
import retrofit2.Callback;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.solution.brothergroup.Activities.BankDetailActivity;
import com.solution.brothergroup.Activities.DthSupportActivity;
import com.solution.brothergroup.Activities.MainActivity;
import com.solution.brothergroup.Api.Object.Banners;
import com.solution.brothergroup.Api.Request.BasicRequest;
import com.solution.brothergroup.Api.Response.AppUserListResponse;
import com.solution.brothergroup.Authentication.PrivacyPolicy;
import com.solution.brothergroup.Authentication.dto.LoginResponse;
import com.solution.brothergroup.BuildConfig;
import com.solution.brothergroup.Fragments.Adapter.SupportDataListAdapter;
import com.solution.brothergroup.Fragments.dto.SupportDataItem;
import com.solution.brothergroup.R;
import com.solution.brothergroup.Util.ApiClient;
import com.solution.brothergroup.Util.ApplicationConstant;
import com.solution.brothergroup.Util.EndPointInterface;
import com.solution.brothergroup.Util.UtilMethods;
import com.solution.brothergroup.usefull.CustomLoader;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SupportFragment extends Fragment {
    View view;
    View Mobiletollfree, DTHtollfree, bankView, ll_contactus;
    TextView currentVersion;
    ImageView Share, playstorelink;

    private LinearLayout customerCareView, custCareWhatsAppView;
    private LinearLayout custCareCallUsView;
    private LinearLayout custCareEmailView;
    private LinearLayout paymentInqueryView;
    private LinearLayout paymentInqueryCallUsView;
    private LinearLayout paymentInqueryEmailView, paymentInqueryWhatsAppView;
    private LinearLayout facebookView;
    private TextView facebbokLink;
    private LinearLayout instagramView;
    private TextView instaLink;
    private LinearLayout twitterView;
    private TextView twitterLink;
    private LinearLayout addressView;
    private TextView address;
    private LinearLayout websiteView;
    private TextView website;
    private LoginResponse LoginDataResponse;
    RecyclerView customerCareNumberRecyclerView, customerCareWhatsappRecyclerView, customerCareEmailRecyclerView, paymentInqueryNumberRecyclerView, paymentInqueryWhatsappRecyclerView, paymentInqueryEmailRecyclerView;
    private String websiteLink, fbLink, instagramLink, twiLink;

    LinearLayout image_count;
    View bannerView, shareBtn;
    ViewPager pager;
    TextView shareContent;
    private Runnable runnable;
    private Handler handler;
    private CustomLoader loader;
    private AppUserListResponse companyProfileData;
    // ArrayList<SupportDataItem> mSupportDataItems = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_support, container, false);
        loader = new CustomLoader(getActivity(), android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        UtilMethods.INSTANCE.setDashboardStatus(getActivity(), false);
        getIds();

        try {
            companyProfileData = ((MainActivity) getActivity()).companyProfileData;
            if (companyProfileData == null) {
                companyProfileData = new Gson().fromJson(UtilMethods.INSTANCE.getCompanyProfile(getActivity()), AppUserListResponse.class);
            }
        } catch (NullPointerException npe) {
            companyProfileData = new Gson().fromJson(UtilMethods.INSTANCE.getCompanyProfile(getActivity()), AppUserListResponse.class);
        } catch (Exception e) {
            companyProfileData = new Gson().fromJson(UtilMethods.INSTANCE.getCompanyProfile(getActivity()), AppUserListResponse.class);
        }
        setContactData(companyProfileData);

        try {
            LoginDataResponse = ((MainActivity) getActivity()).LoginPrefResponse;
            if (LoginDataResponse == null) {
                LoginDataResponse = new Gson().fromJson(UtilMethods.INSTANCE.getLoginPref(getActivity()), LoginResponse.class);
            }
        } catch (NullPointerException npe) {
            LoginDataResponse = new Gson().fromJson(UtilMethods.INSTANCE.getLoginPref(getActivity()), LoginResponse.class);
        } catch (Exception e) {
            LoginDataResponse = new Gson().fromJson(UtilMethods.INSTANCE.getLoginPref(getActivity()), LoginResponse.class);
        }
        getDetail();
        if (LoginDataResponse.isReferral()) {
            Share.setVisibility(View.GONE);
            ShareConentApi();
        } else {
            bannerView.setVisibility(View.GONE);
            Share.setVisibility(View.VISIBLE);
        }
        return view;
    }

    private void getIds() {
        image_count = view.findViewById(R.id.image_count);
        bannerView = view.findViewById(R.id.bannerView);
        shareBtn = view.findViewById(R.id.shareBtn);
        pager = view.findViewById(R.id.pager);
        shareContent = view.findViewById(R.id.shareContent);
        Mobiletollfree = view.findViewById(R.id.Mobiletollfree);
        playstorelink = view.findViewById(R.id.playstorelink);
        DTHtollfree = view.findViewById(R.id.DTHtollfree);
        bankView = view.findViewById(R.id.bankView);
        ll_contactus = view.findViewById(R.id.ll_contactus);
        currentVersion = view.findViewById(R.id.currentVersion);
        currentVersion.setText("Current Version : " + BuildConfig.VERSION_NAME);

        Share = view.findViewById(R.id.Share);


        customerCareNumberRecyclerView = view.findViewById(R.id.customerCareNumberRecyclerView);
        customerCareNumberRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        customerCareWhatsappRecyclerView = view.findViewById(R.id.customerCareWhatsappRecyclerView);
        customerCareWhatsappRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        customerCareEmailRecyclerView = view.findViewById(R.id.customerCareEmailRecyclerView);
        customerCareEmailRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        paymentInqueryNumberRecyclerView = view.findViewById(R.id.paymentInqueryNumberRecyclerView);
        paymentInqueryNumberRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        paymentInqueryWhatsappRecyclerView = view.findViewById(R.id.paymentInqueryWhatsappRecyclerView);
        paymentInqueryWhatsappRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        paymentInqueryEmailRecyclerView = view.findViewById(R.id.paymentInqueryEmailRecyclerView);
        paymentInqueryEmailRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        customerCareView = view.findViewById(R.id.customerCareView);
        custCareWhatsAppView = view.findViewById(R.id.custCareWhatsAppView);
        custCareCallUsView = view.findViewById(R.id.custCareCallUsView);
        custCareEmailView = view.findViewById(R.id.custCareEmailView);
        paymentInqueryView = view.findViewById(R.id.paymentInqueryView);
        paymentInqueryCallUsView = view.findViewById(R.id.paymentInqueryCallUsView);
        paymentInqueryEmailView = view.findViewById(R.id.paymentInqueryEmailView);
        paymentInqueryWhatsAppView = view.findViewById(R.id.paymentInqueryWhatsAppView);
        facebookView = view.findViewById(R.id.facebookView);
        facebbokLink = view.findViewById(R.id.facebbokLink);
        instagramView = view.findViewById(R.id.instagramView);
        instaLink = view.findViewById(R.id.instaLink);
        twitterView = view.findViewById(R.id.twitterView);
        twitterLink = view.findViewById(R.id.twitterLink);
        addressView = view.findViewById(R.id.addressView);
        address = view.findViewById(R.id.address);
        websiteView = view.findViewById(R.id.websiteView);
        website = view.findViewById(R.id.website);

        TextView termAndPrivacyTxt = view.findViewById(R.id.term_and_privacy_txt);
//        termAndPrivacyTxt.setMovementMethod(LinkMovementMethod.getInstance());
        termAndPrivacyTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PrivacyPolicy.class);
                startActivity(intent);
            }
        });
        TextView termTxt = view.findViewById(R.id.term_txt);
//        termAndPrivacyTxt.setMovementMethod(LinkMovementMethod.getInstance());
        termTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TermAndCondition.class);
                startActivity(intent);
            }
        });

        websiteView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                browseLink(websiteLink);
            }
        });
        twitterView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                browseLink(twiLink);
            }
        });
        instagramView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                browseLink(instagramLink);
            }
        });
        facebookView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                browseLink(fbLink);
            }
        });
        Mobiletollfree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), DthSupportActivity.class);
                i.putExtra("From", "Prepaid");
                i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(i);


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
        DTHtollfree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), DthSupportActivity.class);
                i.putExtra("From", "DTH");
                i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(i);
            }
        });
        bankView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), BankDetailActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(i);
            }
        });
    }


    void browseLink(String mLink) {
        try {
            Intent dialIntent = new Intent(Intent.ACTION_VIEW);
            dialIntent.setData(Uri.parse(mLink + ""));
            startActivity(dialIntent);
        } catch (Exception e) {
            Intent dialIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mLink + ""));
            startActivity(dialIntent);
        }
    }

    void getDetail() {
        if (UtilMethods.INSTANCE.isNetworkAvialable(getActivity())) {
            try {
                UtilMethods.INSTANCE.GetCompanyProfile(getActivity(), new UtilMethods.ApiCallBack() {
                    @Override
                    public void onSucess(Object object) {
                        companyProfileData = (AppUserListResponse) object;
                        setContactData(companyProfileData);
                    }
                });


            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            UtilMethods.INSTANCE.NetworkError(getActivity(), getResources().getString(R.string.err_msg_network_title),
                    getResources().getString(R.string.err_msg_network));
        }
    }

    private ArrayList<SupportDataItem> getListData(int type, String editableData) {
        int icon = R.drawable.ic_arrow_right;
        if (type == 1) {
            icon = R.drawable.ic_smartphone;
        }
        if (type == 2) {
            icon = R.drawable.ic_telephone;
        }
        if (type == 3) {
            icon = R.drawable.ic_whatsapp;
        }
        if (type == 4) {
            icon = R.drawable.ic_email;
        }
        ArrayList<SupportDataItem> mSupportDataItems = new ArrayList<>();
        if (editableData.contains(",")) {
            String[] emailArray = editableData.split(",");
            if (emailArray.length > 0) {
                for (String value : emailArray) {
                    mSupportDataItems.add(new SupportDataItem(type, value.trim(), icon));
                }
            }
        } else {
            mSupportDataItems.add(new SupportDataItem(type, editableData.trim(), icon));
        }
        return mSupportDataItems;
    }

    void setContactData(AppUserListResponse mContactData) {
        if (mContactData != null && mContactData.getCompanyProfile() != null) {
            ll_contactus.setVisibility(View.VISIBLE);
            ArrayList<SupportDataItem> mSupportDataCustomerCallItems = new ArrayList<>();

            if (mContactData.getCompanyProfile().getCustomerCareMobileNos() != null && !mContactData.getCompanyProfile().getCustomerCareMobileNos().isEmpty()) {
                mSupportDataCustomerCallItems.addAll(getListData(1, mContactData.getCompanyProfile().getCustomerCareMobileNos()));
            }

            if (mContactData.getCompanyProfile().getCustomerPhoneNos() != null && !mContactData.getCompanyProfile().getCustomerPhoneNos().isEmpty()) {
                mSupportDataCustomerCallItems.addAll(getListData(2, mContactData.getCompanyProfile().getCustomerPhoneNos()));
            }

            if (mSupportDataCustomerCallItems.size() > 0) {
                customerCareView.setVisibility(View.VISIBLE);
                custCareCallUsView.setVisibility(View.VISIBLE);
                SupportDataListAdapter mSupportDataListAdapter = new SupportDataListAdapter(getActivity(), mSupportDataCustomerCallItems);
                customerCareNumberRecyclerView.setAdapter(mSupportDataListAdapter);
            }


            if (mContactData.getCompanyProfile().getCustomerWhatsAppNos() != null && !mContactData.getCompanyProfile().getCustomerWhatsAppNos().isEmpty()) {
                customerCareView.setVisibility(View.VISIBLE);
                custCareWhatsAppView.setVisibility(View.VISIBLE);

                ArrayList<SupportDataItem> mSupportDataItems = getListData(3, mContactData.getCompanyProfile().getCustomerWhatsAppNos());
                if (mSupportDataItems.size() > 0) {
                    SupportDataListAdapter mSupportDataListAdapter = new SupportDataListAdapter(getActivity(), mSupportDataItems);
                    customerCareWhatsappRecyclerView.setAdapter(mSupportDataListAdapter);
                }

            }

            if (mContactData.getCompanyProfile().getCustomerCareEmailIds() != null && !mContactData.getCompanyProfile().getCustomerCareEmailIds().isEmpty()) {
                customerCareView.setVisibility(View.VISIBLE);
                custCareEmailView.setVisibility(View.VISIBLE);

                ArrayList<SupportDataItem> mSupportDataItems = getListData(4, mContactData.getCompanyProfile().getCustomerCareEmailIds());
                if (mSupportDataItems.size() > 0) {
                    SupportDataListAdapter mSupportDataListAdapter = new SupportDataListAdapter(getActivity(), mSupportDataItems);
                    customerCareEmailRecyclerView.setAdapter(mSupportDataListAdapter);
                }
            }

            ArrayList<SupportDataItem> mSupportDataAccountCallItems = new ArrayList<>();
            if (mContactData.getCompanyProfile().getAccountMobileNo() != null && !mContactData.getCompanyProfile().getAccountMobileNo().isEmpty()) {
                mSupportDataAccountCallItems.addAll(getListData(1, mContactData.getCompanyProfile().getAccountMobileNo()));
            }

            if (mContactData.getCompanyProfile().getAccountPhoneNos() != null && !mContactData.getCompanyProfile().getAccountPhoneNos().isEmpty()) {
                mSupportDataAccountCallItems.addAll(getListData(2, mContactData.getCompanyProfile().getAccountPhoneNos()));
            }

            if (mSupportDataAccountCallItems.size() > 0) {
                paymentInqueryView.setVisibility(View.VISIBLE);
                paymentInqueryCallUsView.setVisibility(View.VISIBLE);
                SupportDataListAdapter mSupportDataListAdapter = new SupportDataListAdapter(getActivity(), mSupportDataAccountCallItems);
                paymentInqueryNumberRecyclerView.setAdapter(mSupportDataListAdapter);
            }


            if (mContactData.getCompanyProfile().getAccountWhatsAppNos() != null && !mContactData.getCompanyProfile().getAccountWhatsAppNos().isEmpty()) {
                paymentInqueryView.setVisibility(View.VISIBLE);
                paymentInqueryWhatsAppView.setVisibility(View.VISIBLE);

                ArrayList<SupportDataItem> mSupportDataItems = getListData(3, mContactData.getCompanyProfile().getAccountWhatsAppNos());
                if (mSupportDataItems.size() > 0) {
                    SupportDataListAdapter mSupportDataListAdapter = new SupportDataListAdapter(getActivity(), mSupportDataItems);
                    paymentInqueryWhatsappRecyclerView.setAdapter(mSupportDataListAdapter);
                }

            }
            if (mContactData.getCompanyProfile().getAccountEmailId() != null && !mContactData.getCompanyProfile().getAccountEmailId().isEmpty()) {
                paymentInqueryView.setVisibility(View.VISIBLE);
                paymentInqueryEmailView.setVisibility(View.VISIBLE);

                ArrayList<SupportDataItem> mSupportDataItems = getListData(4, mContactData.getCompanyProfile().getAccountEmailId());
                if (mSupportDataItems.size() > 0) {
                    SupportDataListAdapter mSupportDataListAdapter = new SupportDataListAdapter(getActivity(), mSupportDataItems);
                    paymentInqueryEmailRecyclerView.setAdapter(mSupportDataListAdapter);
                }

            }


            if (mContactData.getCompanyProfile().getAddress() != null && !mContactData.getCompanyProfile().getAddress().isEmpty()) {
                addressView.setVisibility(View.VISIBLE);
                address.setText(Html.fromHtml(mContactData.getCompanyProfile().getAddress()));
            }

            if (mContactData.getCompanyProfile().getWebsite() != null && !mContactData.getCompanyProfile().getWebsite().isEmpty()) {
                websiteView.setVisibility(View.VISIBLE);
                websiteLink = mContactData.getCompanyProfile().getWebsite();
                UtilMethods.INSTANCE.setTextViewHTML(getActivity(), website, "<a href=" + mContactData.getCompanyProfile().getWebsite() + ">Open Website</a>");
            }
            if (mContactData.getCompanyProfile().getFacebook() != null && !mContactData.getCompanyProfile().getFacebook().isEmpty()) {
                facebookView.setVisibility(View.VISIBLE);
                fbLink = mContactData.getCompanyProfile().getFacebook();
                UtilMethods.INSTANCE.setTextViewHTML(getActivity(), facebbokLink, "<a href=" + mContactData.getCompanyProfile().getFacebook() + ">Follow Us</a>");
            }
            if (mContactData.getCompanyProfile().getTwitter() != null && !mContactData.getCompanyProfile().getTwitter().isEmpty()) {
                twitterView.setVisibility(View.VISIBLE);
                twiLink = mContactData.getCompanyProfile().getTwitter();
                UtilMethods.INSTANCE.setTextViewHTML(getActivity(), twitterLink, "<a href=" + mContactData.getCompanyProfile().getTwitter() + ">Follow Us</a>");
            }
            if (mContactData.getCompanyProfile().getInstagram() != null && !mContactData.getCompanyProfile().getInstagram().isEmpty()) {
                instagramView.setVisibility(View.VISIBLE);
                instagramLink = mContactData.getCompanyProfile().getInstagram();
                UtilMethods.INSTANCE.setTextViewHTML(getActivity(), instaLink, "<a href=" + mContactData.getCompanyProfile().getInstagram() + ">Follow Us</a>");
            }
        }
    }

    public void OpenMobileDialog() {
        //before inflating the custom alert dialog layout, we will get the current activity viewgroup
        ViewGroup viewGroup = view.findViewById(android.R.id.content);

        //then we will inflate the custom alert dialog xml that we created
        View dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.mobile_tollfree, viewGroup, false);


        //Now we need an AlertDialog.Builder object
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);

        //finally creating the alert dialog and displaying it
        AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();
    }

    public void OpenDTHDialog() {
        //before inflating the custom alert dialog layout, we will get the current activity viewgroup
        ViewGroup viewGroup = view.findViewById(android.R.id.content);

        //then we will inflate the custom alert dialog xml that we created
        View dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.dth_tollfree, viewGroup, false);


        //Now we need an AlertDialog.Builder object
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);

        //finally creating the alert dialog and displaying it
        AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();
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

                shareMessage = getActivity().getResources().getString(R.string.refer_str) + "\n" + shareMessage + ApplicationConstant.INSTANCE.inviteUrl + LoginDataResponse.getData().getUserID() + "\n\n";
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


                shareMessage = shareMessage + ApplicationConstant.INSTANCE.inviteUrl + LoginDataResponse.getData().getUserID() + "\n\n";
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                startActivity(Intent.createChooser(shareIntent, "choose one"));


            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void ShareConentApi() {
        try {
            loader.show();
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<AppUserListResponse> call = git.GetAppRefferalContent(new BasicRequest(
                    LoginDataResponse.getData().getUserID(), LoginDataResponse.getData().getLoginTypeID(),
                    ApplicationConstant.INSTANCE.APP_ID,
                    UtilMethods.INSTANCE.getIMEI(getActivity()),
                    "", BuildConfig.VERSION_NAME, UtilMethods.INSTANCE.getSerialNo(getActivity()),
                    LoginDataResponse.getData().getSessionID(), LoginDataResponse.getData().getSession()));

            call.enqueue(new Callback<AppUserListResponse>() {
                @Override
                public void onResponse(Call<AppUserListResponse> call, final retrofit2.Response<AppUserListResponse> response) {
                    //Log.e("banner", "is : " + new Gson().toJson(response.body()).toString());
                    try {
                        loader.dismiss();
                        if (response.body() != null && response.body().getStatuscode() == 1) {
                            ArrayList<Banners> bannerList = response.body().getRefferalImage();
                            if (bannerList != null && bannerList.size() > 0) {

                                bannerView.setVisibility(View.VISIBLE);
                                shareContent.setText(Html.fromHtml(formatedContent(response.body().getRefferalContent() + "")));

                                CustomPagerAdapter mCustomPagerAdapter = new CustomPagerAdapter(bannerList, getActivity(), 2);
                                pager.setAdapter(mCustomPagerAdapter);
                                pager.setOffscreenPageLimit(mCustomPagerAdapter.getCount());
                                if (bannerList.size() > 1) {
                                    int mDotsCount = pager.getAdapter().getCount();
                                    TextView[] mDotsText = new TextView[mDotsCount];
                                    //here we set the dots
                                    for (int i = 0; i < mDotsCount; i++) {
                                        mDotsText[i] = new TextView(getActivity());
                                        mDotsText[i].setText(".");
                                        mDotsText[i].setTextSize(50);
                                        mDotsText[i].setTypeface(null, Typeface.BOLD);
                                        mDotsText[i].setTextColor(getResources().getColor(R.color.grey));
                                        image_count.addView(mDotsText[i]);
                                    }


                                    pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                                        @Override
                                        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                                            for (int i = 0; i < mDotsCount; i++) {
                                                mDotsText[i].setTextColor(getResources().getColor(R.color.grey));
                                            }
                                            mDotsText[position].setTextColor(getResources().getColor(R.color.colorPrimary));
                                        }

                                        @Override
                                        public void onPageSelected(int position) {

                                        }

                                        @Override
                                        public void onPageScrollStateChanged(int state) {

                                        }
                                    });

                                    postDelayedScrollNext();
                                }

                            } else {
                                bannerView.setVisibility(View.GONE);
                                Share.setVisibility(View.VISIBLE);
                            }

                        }
                    } catch (Exception e) {
                        loader.dismiss();
                        bannerView.setVisibility(View.GONE);
                        Share.setVisibility(View.VISIBLE);
                    }

                }

                @Override
                public void onFailure(Call<AppUserListResponse> call, Throwable t) {
                    bannerView.setVisibility(View.GONE);
                    Share.setVisibility(View.VISIBLE);
                    loader.dismiss();
                }
            });

        } catch (Exception e) {
            loader.dismiss();
            bannerView.setVisibility(View.GONE);
            Share.setVisibility(View.VISIBLE);
            e.printStackTrace();
        }
    }

    String formatedContent(String value) {

        if (value.contains("*")) {
            Pattern p = Pattern.compile("\\*([^\\*]*)\\*");
            Matcher m = p.matcher(value);
            while (m.find()) {
                value = value.replace("*" + m.group(1) + "*", "<b>" + m.group(1) + "</b>");

            }
        }
        if (value.contains("_")) {
            Pattern p = Pattern.compile("\\_([^\\_]*)\\_");
            Matcher m = p.matcher(value);
            while (m.find()) {
                value = value.replace("_" + m.group(1) + "_", "<i>" + m.group(1) + "</i>");

            }
        }

        if (value.contains("~")) {
            Pattern p = Pattern.compile("\\~([^\\~]*)\\~");
            Matcher m = p.matcher(value);
            while (m.find()) {
                value = value.replace("~" + m.group(1) + "~", "<s>" + m.group(1) + "</s>");

            }
        }
        if (value.contains("```")) {
            Pattern p = Pattern.compile("\\```([^\\```]*)\\```");
            Matcher m = p.matcher(value);
            while (m.find()) {
                value = value.replace("```" + m.group(1) + "```", "<tt>" + m.group(1) + "</tt>");

            }
        }
        return value;
    }

    private void postDelayedScrollNext() {
        handler = new Handler();
        runnable = new Runnable() {
            public void run() {

                if (pager.getAdapter() != null) {
                    if (pager.getCurrentItem() == pager.getAdapter().getCount() - 1) {
                        pager.setCurrentItem(0);
                        postDelayedScrollNext();
                        return;
                    }
                    pager.setCurrentItem(pager.getCurrentItem() + 1);
                    // postDelayedScrollNext(position+1);
                    postDelayedScrollNext();
                }

                // onKeyDown(KeyEvent.KEYCODE_DPAD_RIGHT, null);
            }
        };
        handler.postDelayed(runnable, 2000);

    }

    @Override
    public void onDestroy() {
        if (handler != null && runnable != null) {
            handler.removeCallbacks(runnable);
        }
        super.onDestroy();
    }

}

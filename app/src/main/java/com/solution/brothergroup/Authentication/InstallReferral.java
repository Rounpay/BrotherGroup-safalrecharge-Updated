package com.solution.brothergroup.Authentication;

import android.app.Activity;
import android.os.RemoteException;

import com.android.installreferrer.api.InstallReferrerClient;
import com.android.installreferrer.api.InstallReferrerStateListener;
import com.android.installreferrer.api.ReferrerDetails;
import com.solution.brothergroup.Util.UtilMethods;

public class InstallReferral {

    InstallReferrerClient referrerClient;
    Activity mActivity;

    public InstallReferral(Activity mActivity) {
        this.mActivity = mActivity;
    }

    public void InstllReferralData() {
        referrerClient = InstallReferrerClient.newBuilder(mActivity).build();
        referrerClient.startConnection(new InstallReferrerStateListener() {
            @Override
            public void onInstallReferrerSetupFinished(int responseCode) {
                switch (responseCode) {
                    case InstallReferrerClient.InstallReferrerResponse.OK:
                        ReferrerDetails response = null;

                        try {
                            response = referrerClient.getInstallReferrer();
                            if (response != null) {
                                String referrerUrl = response.getInstallReferrer();
                                // long referrerClickTime = response.getReferrerClickTimestampSeconds();
                                //long appInstallTime = response.getInstallBeginTimestampSeconds();
                                //  boolean instantExperienceLaunched = response.getGooglePlayInstantParam();

                                if (referrerUrl != null && !referrerUrl.isEmpty() & !referrerUrl.contains("utm") && !referrerUrl.contains("google") && !referrerUrl.contains("chrome")) {

                                    try {
                                        int referrerCode = Integer.parseInt(referrerUrl.trim().replaceAll(" ", ""));
                                        UtilMethods.INSTANCE.setReferrerId(mActivity, referrerCode + "");
                                    } catch (NumberFormatException nfe) {

                                    }

                                }
                            }
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        UtilMethods.INSTANCE.setReferrerIdSetData(mActivity, true);
                        referrerClient.endConnection();
                        break;
                    case InstallReferrerClient.InstallReferrerResponse.FEATURE_NOT_SUPPORTED:
                        // API not available on the current Play Store app.
                        UtilMethods.INSTANCE.setReferrerIdSetData(mActivity, true);
                        break;
                    case InstallReferrerClient.InstallReferrerResponse.SERVICE_UNAVAILABLE:
                        // Connection couldn't be established.
                        break;
                }
            }

            @Override
            public void onInstallReferrerServiceDisconnected() {
                // Try to restart the connection on the next request to
                // Google Play by calling the startConnection() method.
            }
        });
    }
}

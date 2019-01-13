package com.example.nguyentrungkien.drumpadelectronic;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.google.ads.consent.ConsentInformation;
import com.google.ads.consent.ConsentStatus;
import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.doubleclick.PublisherAdView;

public class Ads {

    public static void initNative(final String idAdsGG, final LinearLayout lnNative, final Activity activity) {
        try {
            if (!MySetting.isRemoveAds(activity)) {
                lnNative.setVisibility(View.GONE);
                try {
                    final PublisherAdView adView = new PublisherAdView(activity);
                    adView.setAdSizes(AdSize.FLUID);
                    adView.setAdUnitId(idAdsGG);
                    PublisherAdRequest publisherAdRequest = null;

                    if (ConsentInformation.getInstance(activity).getConsentStatus().toString().equals(ConsentStatus.PERSONALIZED) ||
                            !ConsentInformation.getInstance(activity).isRequestLocationInEeaOrUnknown()) {
                        publisherAdRequest = new PublisherAdRequest.Builder().build();
                    } else {
                        publisherAdRequest = new PublisherAdRequest.Builder()
                                .addNetworkExtrasBundle(AdMobAdapter.class, getNonPersonalizedAdsBundle())
                                .build();
                    }
                    adView.loadAd(publisherAdRequest);

                    adView.setAdListener(new AdListener() {
                        @Override
                        public void onAdLoaded() {
                            try {
                                super.onAdLoaded();
                                lnNative.removeAllViews();
                                lnNative.addView(adView);
                                lnNative.setVisibility(View.VISIBLE);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onAdFailedToLoad(int i) {
                            super.onAdFailedToLoad(i);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Bundle getNonPersonalizedAdsBundle() {
        Bundle extras = new Bundle();
        extras.putString("npa", "1");
        return extras;
    }

}

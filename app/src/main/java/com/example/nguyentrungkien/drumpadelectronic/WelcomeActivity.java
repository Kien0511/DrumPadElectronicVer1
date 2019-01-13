package com.example.nguyentrungkien.drumpadelectronic;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.google.ads.consent.ConsentInformation;
import com.google.ads.consent.ConsentStatus;
import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.doubleclick.PublisherInterstitialAd;

public class WelcomeActivity extends AppCompatActivity {

    PublisherInterstitialAd ggInterstitialAd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_wellcom);

        initInterstitialAd();

        CountDownTimer timer = new CountDownTimer(3000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                Intent intent = new Intent(WelcomeActivity.this,MainActivity.class);
                startActivity(intent);
                showInterstitial();
                finish();
                overridePendingTransition(R.anim.activity_in_right,R.anim.activity_out_left);
            }
        }.start();
        
    }

    private void initInterstitialAd() {
        try {
            ggInterstitialAd = new PublisherInterstitialAd(this);
            ggInterstitialAd.setAdUnitId(getString(R.string.IIIIIIII_A));
            ggInterstitialAd.setAdListener(new AdListener() {
                @Override
                public void onAdClosed() { requestNewInterstitial();
                }

                @Override
                public void onAdLoaded() {
                    super.onAdLoaded();
                }

                @Override
                public void onAdFailedToLoad(int i) {
                    super.onAdFailedToLoad(i);

                }
            });
            requestNewInterstitial();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void requestNewInterstitial() {
        try {
            if (!MySetting.isRemoveAds(this)) {
                PublisherAdRequest adRequest = null;
                if (ConsentInformation.getInstance(this).getConsentStatus().toString().equals(ConsentStatus.PERSONALIZED) ||
                        !ConsentInformation.getInstance(this).isRequestLocationInEeaOrUnknown()) {
                    adRequest = new PublisherAdRequest.Builder().build();
                } else {
                    adRequest = new PublisherAdRequest.Builder()
                            .addNetworkExtrasBundle(AdMobAdapter.class, Ads.getNonPersonalizedAdsBundle())
                            .build();
                }
                ggInterstitialAd.loadAd(adRequest);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showInterstitial() {
        try {
            if (ggInterstitialAd != null && ggInterstitialAd.isLoaded()) {
                ggInterstitialAd.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

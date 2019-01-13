package com.example.nguyentrungkien.drumpadelectronic;

import android.Manifest;
import android.app.ActivityManager;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;
import com.example.nguyentrungkien.drumpadelectronic.DAO.PitchDAO;
import com.example.nguyentrungkien.drumpadelectronic.DAO.SoundPackageDAO;
import com.example.nguyentrungkien.drumpadelectronic.DTO.PresetDTO;
import com.example.nguyentrungkien.drumpadelectronic.DTO.SoundPackageDTO;
import com.google.ads.consent.ConsentInformation;
import com.google.ads.consent.ConsentStatus;
import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.doubleclick.PublisherInterstitialAd;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity implements View.OnTouchListener,
        View.OnClickListener, NavigationView.OnNavigationItemSelectedListener, RewardedVideoAdListener,
        BillingProcessor.IBillingHandler{

    private ImageButton ibtn_a1, ibtn_a2, ibtn_a3, ibtn_a4, ibtn_a5, ibtn_a6, ibtn_a7, ibtn_a8, ibtn_a9, ibtn_a10, ibtn_a11, ibtn_a12;
    private ImageButton ibtn_b1, ibtn_b2, ibtn_b3, ibtn_b4, ibtn_b5, ibtn_b6, ibtn_b7, ibtn_b8, ibtn_b9, ibtn_b10, ibtn_b11, ibtn_b12;
    private ImageButton ibtnMenu, ibtnPitchadjust, ibtnRecord, ibtnRecordList, ibtnSwitch;
    private ViewFlipper vfp;
    private boolean check = false, checkRecord = false;
    private SoundPool sp;
    private int sounda1, sounda2, sounda3, sounda4, sounda5, sounda6, sounda7, sounda8, sounda9, sounda10, sounda11, sounda12;
    private int soundb1, soundb2, soundb3, soundb4, soundb5, soundb6, soundb7, soundb8, soundb9, soundb10, soundb11, soundb12;
    private AudioAttributes attributes;
    private boolean loaded;
    private int id;
    private float rate_a1, rate_a2, rate_a3, rate_a4, rate_a5, rate_a6, rate_a7, rate_a8, rate_a9, rate_a10, rate_a11, rate_a12;
    private float rate_b1, rate_b2, rate_b3, rate_b4, rate_b5, rate_b6, rate_b7, rate_b8, rate_b9, rate_b10, rate_b11, rate_b12;
    private SharedPreferences sharedPreferences;
    private MediaRecorder recorder;
    private String outputFile;
    private File directory;
    private TextView txtTimeRecordName, txtTimeRecord;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private View headerView;
    private ImageButton ibtnBack;
    private Timer t;
    private int seconds = 0, minute = 0, hour = 0;
    private String presetName = "default";

    private PitchDAO pitchDAO;
    private PresetDTO presetDTO;
    private String timeFM;
    private String str;
    private String[] strings;

    private int[] a, b, color;
    private Random random;
    private LinearLayout llbackground;


    final int REQUEST_PERMISSION_CODE = 1000;

    private ImageButton ibtnMusic;
    private boolean checkMusic = false;
    private MediaPlayer mediaPlayer;

    private ImageButton ibtnLoop;

    public static ProgressDialog dialog;

    private ImageButton ibtnPrev, ibtnNext;
    private TextView txtPresetName;

    private int soundPackage;

    private ProgressDialog soundProgessDialog;

    private int soundpackage_amount;
    private SoundPackageDAO soundPackageDAO;

    private AdView mAdView;

    private RewardedVideoAd mRewardedVideoAd;

    private int checkAds = 0;

    private int current = 1546804548;

    private int threeday = 259200;

    int soundPackageId;
    private int before = 0;

    private BillingProcessor bp;
    private boolean readyToPurchase = false;

    PublisherInterstitialAd ggInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        initInterstitialAd();
        showInterstitial();

        try {
            bp = BillingProcessor.newBillingProcessor(this, Constant.BASE64, this);
            bp.initialize(); // binds
        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.e("aaaaaaaaaaaaaaaaaaaaaa",((System.currentTimeMillis()/1000) - current) +"");

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

       MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713");

        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
        mRewardedVideoAd.setRewardedVideoAdListener(this);

        loadRewardedVideoAd();

        str = "thangdh@zonmob.com,anhlx@zonmob.com,quanpna@zonmob.com,cuongtv@zonmob.com,vuquynhanh1995@gmail.com,mynt@zonmob.com,nghiadt@zonmob.com,hiepdd@zonmob.com,huongntm@zonmob.com,"
                + "lynhk@zonmob.com,alphosez96@gmail.com,daicntt1995@gmail.com,cuongdxph04626@fpt.edu.vn,kimdungkb97@gmail.com,kienbom11@gmail.com,viet.dinhle.kma@gmail.com";
        strings = str.split(",");

        a = new int[]{R.drawable.blue_click, R.drawable.green_click, R.drawable.purple_click, R.drawable.red_click, R.drawable.yellow_click, R.drawable.purple_click, R.drawable.green_click, R.drawable.purple_click, R.drawable.purple_click, R.drawable.yellow_click, R.drawable.purple_click, R.drawable.red_click};
        b = new int[]{R.drawable.blue_click, R.drawable.green_click, R.drawable.purple_click, R.drawable.red_click, R.drawable.yellow_click, R.drawable.purple_click, R.drawable.green_click, R.drawable.purple_click, R.drawable.purple_click, R.drawable.yellow_click, R.drawable.purple_click, R.drawable.red_click};


        color = new int[]{R.drawable.color_glow_1, R.drawable.color_glow_2, R.drawable.color_glow_3, R.drawable.color_glow_4, R.drawable.color_glow_5};
        random = new Random();

        pitchDAO = new PitchDAO(MainActivity.this);
        pitchDAO.open();

        soundPackageDAO = new SoundPackageDAO(MainActivity.this);
        soundPackageDAO.open();

        soundpackage_amount = soundPackageDAO.countSoundPackage();

        rate_a1 = rate_a2 = rate_a3 = rate_a4 = rate_a5 = rate_a6 = rate_a7 = rate_a8 = rate_a9 = rate_a10 = rate_a11 = rate_a12 = 1;
        rate_b1 = rate_b2 = rate_b3 = rate_b4 = rate_b5 = rate_b6 = rate_b7 = rate_b8 = rate_b9 = rate_b10 = rate_b11 = rate_b12 = 1;
        ibtnSwitch = findViewById(R.id.ibtnSwitch);
        vfp = findViewById(R.id.vfp);
        ibtnRecord = findViewById(R.id.ibtnRecord);
        ibtnMenu = findViewById(R.id.ibtnMenu);
        txtTimeRecordName = findViewById(R.id.txtTimeRecordName);
        txtTimeRecord = findViewById(R.id.txtTimeRecord);
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);
        headerView = navigationView.getHeaderView(0);
        ibtnBack = headerView.findViewById(R.id.ibtnBack);
        llbackground = findViewById(R.id.llbackground);
        ibtnLoop = findViewById(R.id.ibtnLoop);
        ibtnMusic = findViewById(R.id.ibtnMusic);
        ibtnPrev = findViewById(R.id.ibtnPrev);
        ibtnNext = findViewById(R.id.ibtnNext);
        txtPresetName = findViewById(R.id.txtPresetName);

        navigationView.bringToFront();

        navigationView.setNavigationItemSelectedListener(this);

        Ads.initNative(getString(R.string.NNNNNNNN_L_A), (LinearLayout)headerView.findViewById(R.id.lnNative), this);


        ibtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.closeDrawers();
            }
        });


        ibtnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        sharedPreferences = MainActivity.this.getSharedPreferences("DrumPadRate", Context.MODE_PRIVATE);

        //record

        ibtnRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (checkPermissionFromDevice()) {
                    checkRecord = !checkRecord;
                    if (checkRecord) {
                        ibtnMenu.setEnabled(false);
                        ibtnLoop.setEnabled(false);
                        ibtnMusic.setEnabled(false);
                        seconds = minute = hour = 0;
                        ibtnRecord.setImageResource(R.drawable.ic_recording);
                        txtTimeRecordName.setText("Recording:");
                        txtTimeRecord.setVisibility(View.VISIBLE);
                        txtTimeRecord.setText("00 : 00 : 00");

                        t = new Timer();


                        t.schedule(new TimerTask() {
                            @Override
                            public void run() {

                                txtTimeRecord.post(new Runnable() {

                                    public void run() {
                                        seconds++;
                                        if (seconds == 60) {
                                            seconds = 0;
                                            minute++;
                                        }
                                        if (minute == 60) {
                                            minute = 0;
                                            hour++;
                                        }
                                        txtTimeRecord.setText(""
                                                + (hour > 9 ? hour : ("0" + hour)) + " : "
                                                + (minute > 9 ? minute : ("0" + minute))
                                                + " : "
                                                + (seconds > 9 ? seconds : "0" + seconds));

                                    }
                                });
                            }
                        }, 1000, 1000);
                        Long date = new Date().getTime();
                        Date current_time = new Date(Long.valueOf(date));
                        if (Environment.getExternalStorageState() == null) {
                            directory = new File(Environment.getDataDirectory().getAbsolutePath());

                            if (!directory.exists()) {
                                directory.mkdir();
                            }
                        } else if (Environment.getExternalStorageState() != null) {
                            directory = new File(Environment.getExternalStorageDirectory().getAbsolutePath());

                            if (!directory.exists()) {
                                directory.mkdir();
                            }
                        }

                        SimpleDateFormat sdf = new SimpleDateFormat("yyyyddMM-HHmmSSS");
                        timeFM = sdf.format(current_time);
                        Log.e("aaaaaaaaaaa", timeFM);

                        outputFile = directory.getAbsolutePath() + "/" + timeFM + "-record.3gp";
                        Log.e("assssssssssssssssss", Environment.getExternalStorageDirectory().getAbsolutePath());


                        recorder = new MediaRecorder();
                        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                        recorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);

                        recorder.setOutputFile(outputFile);
                        try {
                            recorder.prepare();
                            recorder.start();
                            Toast.makeText(MainActivity.this, "Đang ghi âm", Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {


                        t.cancel();
                        ibtnMusic.setEnabled(true);
                        ibtnMenu.setEnabled(true);
                        ibtnLoop.setEnabled(true);
                        txtTimeRecordName.setText("No record now");
                        txtTimeRecord.setVisibility(View.GONE);
                        ibtnRecord.setImageResource(R.drawable.ic_record_waiting);
                        recorder.stop();
                        recorder.release();
                        Toast.makeText(MainActivity.this, "Ghi âm thành công", Toast.LENGTH_SHORT).show();

                        presetDTO = new PresetDTO();
                        presetDTO.setRecord_name(timeFM + "-record.3gp");
                        presetDTO.setPreset_name(presetName);

                        pitchDAO.addRecordPresetName(presetDTO);
                    }
                } else {
                    requestPermission();
                }
            }
        });



        //btn_a
        ibtn_a1 = findViewById(R.id.ibtn_a1);
        ibtn_a2 = findViewById(R.id.ibtn_a2);
        ibtn_a3 = findViewById(R.id.ibtn_a3);
        ibtn_a4 = findViewById(R.id.ibtn_a4);
        ibtn_a5 = findViewById(R.id.ibtn_a5);
        ibtn_a6 = findViewById(R.id.ibtn_a6);
        ibtn_a7 = findViewById(R.id.ibtn_a7);
        ibtn_a8 = findViewById(R.id.ibtn_a8);
        ibtn_a9 = findViewById(R.id.ibtn_a9);
        ibtn_a10 = findViewById(R.id.ibtn_a10);
        ibtn_a11 = findViewById(R.id.ibtn_a11);
        ibtn_a12 = findViewById(R.id.ibtn_a12);

        //btn_b
        ibtn_b1 = findViewById(R.id.ibtn_b1);
        ibtn_b2 = findViewById(R.id.ibtn_b2);
        ibtn_b3 = findViewById(R.id.ibtn_b3);
        ibtn_b4 = findViewById(R.id.ibtn_b4);
        ibtn_b5 = findViewById(R.id.ibtn_b5);
        ibtn_b6 = findViewById(R.id.ibtn_b6);
        ibtn_b7 = findViewById(R.id.ibtn_b7);
        ibtn_b8 = findViewById(R.id.ibtn_b8);
        ibtn_b9 = findViewById(R.id.ibtn_b9);
        ibtn_b10 = findViewById(R.id.ibtn_b10);
        ibtn_b11 = findViewById(R.id.ibtn_b11);
        ibtn_b12 = findViewById(R.id.ibtn_b12);


        if (Build.VERSION.SDK_INT >= 21) {
            attributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();

            sp = new SoundPool.Builder()
                    .setAudioAttributes(attributes)
                    .setMaxStreams(5)
                    .build();
        } else {
            sp = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        }

        sp.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int i, int i1) {
                Log.d("sssssssss", ""+i);
                if(i % 24 == 0)
                {
                    if (soundProgessDialog != null)soundProgessDialog.dismiss();
                }
                loaded = true;
            }
        });



        if (getIntent() != null) {

            Log.e("ccccccccccc",soundPackage+"");

            //rate a
            rate_a1 = sharedPreferences.getFloat("rate_a1", 1);
            rate_a2 = sharedPreferences.getFloat("rate_a2", 1);
            rate_a3 = sharedPreferences.getFloat("rate_a3", 1);
            rate_a4 = sharedPreferences.getFloat("rate_a4", 1);
            rate_a5 = sharedPreferences.getFloat("rate_a5", 1);
            rate_a6 = sharedPreferences.getFloat("rate_a6", 1);
            rate_a7 = sharedPreferences.getFloat("rate_a7", 1);
            rate_a8 = sharedPreferences.getFloat("rate_a8", 1);
            rate_a9 = sharedPreferences.getFloat("rate_a9", 1);
            rate_a10 = sharedPreferences.getFloat("rate_a10", 1);
            rate_a11 = sharedPreferences.getFloat("rate_a11", 1);
            rate_a12 = sharedPreferences.getFloat("rate_a12", 1);

            //rate b
            rate_b1 = sharedPreferences.getFloat("rate_b1", 1);
            rate_b2 = sharedPreferences.getFloat("rate_b2", 1);
            rate_b3 = sharedPreferences.getFloat("rate_b3", 1);
            rate_b4 = sharedPreferences.getFloat("rate_b4", 1);
            rate_b5 = sharedPreferences.getFloat("rate_b5", 1);
            rate_b6 = sharedPreferences.getFloat("rate_b6", 1);
            rate_b7 = sharedPreferences.getFloat("rate_b7", 1);
            rate_b8 = sharedPreferences.getFloat("rate_b8", 1);
            rate_b9 = sharedPreferences.getFloat("rate_b9", 1);
            rate_b10 = sharedPreferences.getFloat("rate_b10", 1);
            rate_b11 = sharedPreferences.getFloat("rate_b11", 1);
            rate_b12 = sharedPreferences.getFloat("rate_b12", 1);

            presetName = sharedPreferences.getString("preset", "default");

            Log.e("aaaaaaaaa", rate_a1 + " ");
        }
        for (int i = 1; i < soundPackageDAO.countSoundPackage(); i++)
        {
            if((int)(System.currentTimeMillis()/1000) - sharedPreferences.getInt("timecurrent"+i, (int) (System.currentTimeMillis()/1000)) > 60)
            {
                soundPackageDAO.updateSoundPackage(i,"no");
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("soundpackage",soundPackage);
                editor.commit();
            }
        }

        loadSoundPool();
        //soundProgessDialog.hide();


        setOnclickAndOnTouch();

        ibtnSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check = !check;
                if (check) {
                    ibtnSwitch.setImageResource(R.drawable.ic_switchb);
                    vfp.setInAnimation(MainActivity.this, R.anim.activity_in_right);
                    vfp.setOutAnimation(MainActivity.this, R.anim.activity_out_left);
                    vfp.showNext();
                } else {
                    ibtnSwitch.setImageResource(R.drawable.ic_switcha);
                    vfp.setInAnimation(MainActivity.this, R.anim.activity_in_left);
                    vfp.setOutAnimation(MainActivity.this, R.anim.activity_out_right);
                    vfp.showPrevious();
                }

            }
        });


        ibtnLoop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isMyServiceRunning(MediaServices.class))
                {
                    Intent intentService = new Intent(MainActivity.this,MediaServices.class);

                    stopService(intentService);
                }
                Intent intent = new Intent(MainActivity.this,LoopActivity.class);

                startActivity(intent);
                overridePendingTransition(R.anim.activity_in_right,R.anim.activity_out_left);

            }
        });

        ibtnMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkPermissionFromDevice())
                {
                    if(!isMyServiceRunning(MediaServices.class))
                    {
                        Intent intent = new Intent(MainActivity.this,ListMusicActivity.class);

                        startActivity(intent);
                        overridePendingTransition(R.anim.activity_in_right,R.anim.activity_out_left);

                        dialog = new ProgressDialog(MainActivity.this);
                        dialog.setMessage("Loading.......");
                        dialog.show();

                    }
                    else
                    {
                        ibtnMusic.setImageResource(R.drawable.ic_music);
                        Intent intent = new Intent(MainActivity.this,MediaServices.class);

                        stopService(intent);
                    }
                }
                else
                {
                    requestPermission();
                }
            }
        });

        if(isMyServiceRunning(MediaServices.class))
        {
            ibtnMusic.setImageResource(R.drawable.music_pause);
        }
        else
        {
            ibtnMusic.setImageResource(R.drawable.ic_music);
        }

        ibtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                before = soundPackage;
                if(soundPackage == (soundpackage_amount-1))
                {
                    soundPackage = 0;
                }
                else
                {
                    soundPackage++;
                }

                llbackground.setVisibility(View.VISIBLE);

                final Dialog dialog = new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.sound_package_detais_dialog);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                TextView txtSoundPackageName = dialog.findViewById(R.id.txtSoundPackageName);
                TextView txtSoundPackageContent = dialog.findViewById(R.id.txtSoundPackageContent);
                final Button btnGet = dialog.findViewById(R.id.btnGet);
                Button btnTrial = dialog.findViewById(R.id.btnTrial);
                ImageButton ibtnCancel = dialog.findViewById(R.id.ibtnCancel);

                SoundPackageDTO soundPackageDTO  = soundPackageDAO.getSoundPackageDTO(soundPackage);

                txtSoundPackageName.setText(soundPackageDTO.getSoundpackage_name());
                txtSoundPackageContent.setText(soundPackageDTO.getSoundpackage_content());
                if(soundPackageDTO.getSoundpackage_unlock().equals("no"))
                {
                    btnTrial.setVisibility(View.VISIBLE);
                }
                else
                {
                    btnTrial.setVisibility(View.GONE);
                    btnGet.setText("GET");
                }

                ibtnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                        if(soundPackage == 0)
//                        {
//                            soundPackage = (soundpackage_amount-1);
//                        }
//                        else
//                        {
//                            soundPackage--;
//                        }

//                        SharedPreferences.Editor editor = sharedPreferences.edit();
//                        editor.putInt("soundpackage",soundPackage);
//                        editor.commit();
//                        loadSoundPool();
                        llbackground.setVisibility(View.GONE);

                        Log.e("bbbbbbbbbbbbbb",soundPackage+"");
                        dialog.dismiss();
                    }
                });

                btnGet.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(btnGet.getText().toString().toUpperCase().equals("GET"))
                        {

                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putInt("soundpackage",soundPackage);
                            editor.commit();
                            loadSoundPool();

                            llbackground.setVisibility(View.GONE);

                            dialog.dismiss();
                        }
                        else
                        {

                        }
                    }
                });

                btnTrial.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if(mRewardedVideoAd.isLoaded())
                        {
                            Log.e("aaaaaaaaaa","aaa");
                            mRewardedVideoAd.show();

                            dialog.dismiss();
                        }else
                        {
                            Log.e("aaaaaaaaaa","vvvvvv");

                        }
                    }
                });

                dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        if(soundPackage == 0)
                        {
                            soundPackage = (soundpackage_amount-1);
                        }
                        else
                        {
                            soundPackage--;
                        }

//                        SharedPreferences.Editor editor = sharedPreferences.edit();
//                        editor.putInt("soundpackage",soundPackage);
//                        editor.commit();
//                        loadSoundPool();
                        llbackground.setVisibility(View.GONE);
                        Log.e("bbbbbbbbbbbbbb",soundPackage+"");

                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });

        ibtnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                before = soundPackage;

                if(soundPackage == 0)
                {
                    soundPackage = (soundpackage_amount-1);

                }
                else
                {
                    soundPackage--;
                }

                llbackground.setVisibility(View.VISIBLE);
                final Dialog dialog = new Dialog(MainActivity.this);

                dialog.setContentView(R.layout.sound_package_detais_dialog);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                TextView txtSoundPackageName = dialog.findViewById(R.id.txtSoundPackageName);
                TextView txtSoundPackageContent = dialog.findViewById(R.id.txtSoundPackageContent);
                final Button btnGet = dialog.findViewById(R.id.btnGet);
                ImageButton ibtnCancel = dialog.findViewById(R.id.ibtnCancel);
                Button btnTrial = dialog.findViewById(R.id.btnTrial);

                SoundPackageDTO soundPackageDTO  = soundPackageDAO.getSoundPackageDTO(soundPackage);

                txtSoundPackageName.setText(soundPackageDTO.getSoundpackage_name());
                txtSoundPackageContent.setText(soundPackageDTO.getSoundpackage_content());

                if(soundPackageDTO.getSoundpackage_unlock().equals("no"))
                {
                    btnTrial.setVisibility(View.VISIBLE);
                }
                else
                {
                    btnTrial.setVisibility(View.GONE);
                    btnGet.setText("GET");
                }

                ibtnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                        if(soundPackage == (soundpackage_amount - 1))
//                        {
//                            soundPackage = 0;
//                        }
//                        else
//                        {
//                            soundPackage++;
//                        }

//                        SharedPreferences.Editor editor = sharedPreferences.edit();
//                        editor.putInt("soundpackage",soundPackage);
//                        editor.commit();
//                        loadSoundPool();
                        llbackground.setVisibility(View.GONE);
                        dialog.dismiss();
                    }
                });

                btnGet.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(btnGet.getText().toString().toUpperCase().equals("GET"))
                        {
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putInt("soundpackage",soundPackage);
                            editor.commit();
                            loadSoundPool();

                            llbackground.setVisibility(View.GONE);
                            dialog.dismiss();
                        }
                        else
                        {

                        }
                    }
                });

                btnTrial.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if(mRewardedVideoAd.isLoaded())
                        {
                            Log.e("aaaaaaaaaa","aaa");
                            mRewardedVideoAd.show();

                            dialog.dismiss();
                        }else
                        {
                            Log.e("aaaaaaaaaa","vvvvvv");

                        }
                    }
                });

                dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        if(soundPackage == (soundpackage_amount - 1))
                        {
                            soundPackage = 0;
                        }
                        else
                        {
                            soundPackage++;
                        }

//                        SharedPreferences.Editor editor = sharedPreferences.edit();
//                        editor.putInt("soundpackage",soundPackage);
//                        editor.commit();
//                        loadSoundPool();
                        llbackground.setVisibility(View.GONE);
                        dialog.dismiss();
                    }
                });
                dialog.show();

            }
        });
    }

    private void setOnclickAndOnTouch() {
        //ibtn_a setOnClick
        ibtn_a1.setOnClickListener(this);
        ibtn_a2.setOnClickListener(this);
        ibtn_a3.setOnClickListener(this);
        ibtn_a4.setOnClickListener(this);
        ibtn_a5.setOnClickListener(this);
        ibtn_a6.setOnClickListener(this);
        ibtn_a7.setOnClickListener(this);
        ibtn_a8.setOnClickListener(this);
        ibtn_a9.setOnClickListener(this);
        ibtn_a10.setOnClickListener(this);
        ibtn_a11.setOnClickListener(this);
        ibtn_a12.setOnClickListener(this);


        //ibtn_a setOnTouch
        ibtn_a1.setOnTouchListener(this);
        ibtn_a2.setOnTouchListener(this);
        ibtn_a3.setOnTouchListener(this);
        ibtn_a4.setOnTouchListener(this);
        ibtn_a5.setOnTouchListener(this);
        ibtn_a6.setOnTouchListener(this);
        ibtn_a7.setOnTouchListener(this);
        ibtn_a8.setOnTouchListener(this);
        ibtn_a9.setOnTouchListener(this);
        ibtn_a10.setOnTouchListener(this);
        ibtn_a11.setOnTouchListener(this);
        ibtn_a12.setOnTouchListener(this);

        //ibtn_b setOnClick
        ibtn_b1.setOnClickListener(this);
        ibtn_b2.setOnClickListener(this);
        ibtn_b3.setOnClickListener(this);
        ibtn_b4.setOnClickListener(this);
        ibtn_b5.setOnClickListener(this);
        ibtn_b6.setOnClickListener(this);
        ibtn_b7.setOnClickListener(this);
        ibtn_b8.setOnClickListener(this);
        ibtn_b9.setOnClickListener(this);
        ibtn_b10.setOnClickListener(this);
        ibtn_b11.setOnClickListener(this);
        ibtn_b12.setOnClickListener(this);

        //ibtn_b setOnTouch
        ibtn_b1.setOnTouchListener(this);
        ibtn_b2.setOnTouchListener(this);
        ibtn_b3.setOnTouchListener(this);
        ibtn_b4.setOnTouchListener(this);
        ibtn_b5.setOnTouchListener(this);
        ibtn_b6.setOnTouchListener(this);
        ibtn_b7.setOnTouchListener(this);
        ibtn_b8.setOnTouchListener(this);
        ibtn_b9.setOnTouchListener(this);
        ibtn_b10.setOnTouchListener(this);
        ibtn_b11.setOnTouchListener(this);
        ibtn_b12.setOnTouchListener(this);
    }

    private void loadSoundPool() {
        soundPackage = sharedPreferences.getInt("soundpackage",0);
        Log.e("aaaaaaaaaaaaaaaa",soundPackage+"");
        soundProgessDialog = new ProgressDialog(MainActivity.this);
        soundProgessDialog.setMessage("Loading.......");
        soundProgessDialog.show();
        if(soundPackage == 0)
        {
            txtPresetName.setText("Sound Package 1");
            //sound_a
            sounda1 = sp.load(this, R.raw.dubstep_club_a_bass_a1, 1);
            sounda2 = sp.load(this, R.raw.dubstep_club_a_bass_a2, 1);
            sounda3 = sp.load(this, R.raw.dubstep_club_a_bassfill_a3, 1);
            sounda4 = sp.load(this, R.raw.dubstep_club_a_bass_a4, 1);
            sounda5 = sp.load(this, R.raw.dubstep_club_a_bass_a5, 1);
            sounda6 = sp.load(this, R.raw.dubstep_club_a_stab_a6, 1);
            sounda7 = sp.load(this, R.raw.dubstep_club_a_arp_a7, 1);
            sounda8 = sp.load(this, R.raw.dubstep_club_a_piano_a8, 1);
            sounda9 = sp.load(this, R.raw.dubstep_club_a_vox_a9, 1);
            sounda10 = sp.load(this, R.raw.dubstep_club_a_kick_a10, 1);
            sounda11 = sp.load(this, R.raw.dubstep_club_a_hat_a11, 1);
            sounda12 = sp.load(this, R.raw.dubstep_club_a_snare_a12, 1);

            //sound_b
            soundb1 = sp.load(this, R.raw.dubstep_club_b_bass_b1, 1);
            soundb2 = sp.load(this, R.raw.dubstep_club_b_bass_b2, 1);
            soundb3 = sp.load(this, R.raw.dubstep_club_b_bassfill_b3, 1);
            soundb4 = sp.load(this, R.raw.dubstep_club_b_bass_b4, 1);
            soundb5 = sp.load(this, R.raw.dubstep_club_b_bass_b5, 1);
            soundb6 = sp.load(this, R.raw.dubstep_club_b_lead_b6, 1);
            soundb7 = sp.load(this, R.raw.dubstep_club_b_arp_b7, 1);
            soundb8 = sp.load(this, R.raw.dubstep_club_b_stab_b8, 1);
            soundb9 = sp.load(this, R.raw.dubstep_club_b_stab_b9, 1);
            soundb10 = sp.load(this, R.raw.dubstep_club_b_kick_b10, 1);
            soundb11 = sp.load(this, R.raw.dubstep_club_b_hat_b11, 1);
            soundb12 = sp.load(this, R.raw.dubstep_club_b_snare_b12, 1);
        }
        else if(soundPackage == 1)
        {
            txtPresetName.setText("Sound Package 2");

            sounda1 = sp.load(this, R.raw.nhp_01_bandcut_a, 1);
            sounda2 = sp.load(this, R.raw.nhp_02_bandcut_a, 1);
            sounda3 = sp.load(this, R.raw.nhp_03_bandcut_a, 1);
            sounda4 = sp.load(this, R.raw.nhp_04_strings_a, 1);
            sounda5 = sp.load(this, R.raw.nhp_05_strings_a, 1);
            sounda6 = sp.load(this, R.raw.nhp_06_vocal_a, 1);
            sounda7 = sp.load(this, R.raw.nhp_07_chords_a, 1);
            sounda8 = sp.load(this, R.raw.nhp_08_chords_a, 1);
            sounda9 = sp.load(this, R.raw.nhp_09_fill_a, 1);
            sounda10 = sp.load(this, R.raw.nhp_10_kick_a, 1);
            sounda11 = sp.load(this, R.raw.nhp_11_clsdhat_a, 1);
            sounda12 = sp.load(this, R.raw.nhp_12_snare_a, 1);

            //sound_b
            soundb1 = sp.load(this, R.raw.nhp_01_bandcut_b, 1);
            soundb2 = sp.load(this, R.raw.nhp_02_bandcut_b, 1);
            soundb3 = sp.load(this, R.raw.nhp_03_bandcut_b, 1);
            soundb4 = sp.load(this, R.raw.nhp_04_strings_b, 1);
            soundb5 = sp.load(this, R.raw.nhp_05_strings_b, 1);
            soundb6 = sp.load(this, R.raw.nhp_06_vocal_b, 1);
            soundb7 = sp.load(this, R.raw.nhp_07_chords_b, 1);
            soundb8 = sp.load(this, R.raw.nhp_08_chords_b, 1);
            soundb9 = sp.load(this, R.raw.nhp_09_fill_b, 1);
            soundb10 = sp.load(this, R.raw.nhp_10_kick_b, 1);
            soundb11 = sp.load(this, R.raw.nhp_11_clsdhat_b, 1);
            soundb12 = sp.load(this, R.raw.nhp_12_snare_b, 1);

        }
        else if(soundPackage == 2)
        {
            txtPresetName.setText("Sound Package 3");

            sounda1 = sp.load(this, R.raw.edp_01_chords_a, 1);
            sounda2 = sp.load(this, R.raw.edp_02_chords_a, 1);
            sounda3 = sp.load(this, R.raw.edp_03_chords_a, 1);
            sounda4 = sp.load(this, R.raw.edp_04_chords_a, 1);
            sounda5 = sp.load(this, R.raw.edp_05_chords_a, 1);
            sounda6 = sp.load(this, R.raw.edp_06_pluck_a, 1);
            sounda7 = sp.load(this, R.raw.edp_07_bass_a, 1);
            sounda8 = sp.load(this, R.raw.edp_08_fx_a, 1);
            sounda9 = sp.load(this, R.raw.edp_09_vocals_a, 1);
            sounda10 = sp.load(this, R.raw.edp_10_kick_a, 1);
            sounda11 = sp.load(this, R.raw.edp_11_hat_a, 1);
            sounda12 = sp.load(this, R.raw.edp_12_clap_a, 1);

            soundb1 = sp.load(this, R.raw.edp_01_chords_b, 1);
            soundb2 = sp.load(this, R.raw.edp_02_chords_b, 1);
            soundb3 = sp.load(this, R.raw.edp_03_chords_b, 1);
            soundb4 = sp.load(this, R.raw.edp_04_chords_b, 1);
            soundb5 = sp.load(this, R.raw.edp_05_chords_b, 1);
            soundb6 = sp.load(this, R.raw.edp_06_pluck_b, 1);
            soundb7 = sp.load(this, R.raw.edp_07_bass_b, 1);
            soundb8 = sp.load(this, R.raw.edp_08_fx_b, 1);
            soundb9 = sp.load(this, R.raw.edp_09_vocals_b, 1);
            soundb10 = sp.load(this, R.raw.edp_10_kick_b, 1);
            soundb11 = sp.load(this, R.raw.edp_11_hat_b, 1);
            soundb12 = sp.load(this, R.raw.edp_12_clap_b, 1);
        }
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.MODIFY_AUDIO_SETTINGS
        }, REQUEST_PERMISSION_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSION_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                    finishActivity(requestCode);
                }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ibtn_a1:
                break;
            case R.id.ibtn_a2:
                break;
            case R.id.ibtn_a3:
                break;
            case R.id.ibtn_a4:
                break;
            case R.id.ibtn_a5:
                break;
            case R.id.ibtn_a6:
                break;
            case R.id.ibtn_a7:
                break;
            case R.id.ibtn_a8:
                break;
            case R.id.ibtn_a9:
                break;
            case R.id.ibtn_a10:
                break;
            case R.id.ibtn_a11:
                break;
            case R.id.ibtn_a12:
                break;
            case R.id.ibtn_b1:
                break;
            case R.id.ibtn_b2:
                break;
            case R.id.ibtn_b3:
                break;
            case R.id.ibtn_b4:
                break;
            case R.id.ibtn_b5:
                break;
            case R.id.ibtn_b6:
                break;
            case R.id.ibtn_b7:
                break;
            case R.id.ibtn_b8:
                break;
            case R.id.ibtn_b9:
                break;
            case R.id.ibtn_b10:
                break;
            case R.id.ibtn_b11:
                break;
            case R.id.ibtn_b12:
                break;
        }
    }

    public void playSoundsPool(MotionEvent motionEvent, int soundId, int leftVolume, int rightVolume, int priority, int loop, float rate) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            llbackground.setVisibility(View.VISIBLE);
            if (loaded) {
                sp.play(soundId, leftVolume, rightVolume, priority, loop, rate);
            }
        } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
            llbackground.setVisibility(View.GONE);
        }
    }

    public void playSoundsPool(int soundId, int leftVolume, int rightVolume, int priority, int loop, float rate) {
        if (loaded) {
            sp.play(soundId, leftVolume, rightVolume, priority, loop, rate);
        }
    }


    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (view.getId()) {
            case R.id.ibtn_a1:
                playSoundsPool(motionEvent, sounda1, 1, 1, 0, 0, rate_a1);
                randomA1(motionEvent);
                break;
            case R.id.ibtn_a2:
                playSoundsPool(motionEvent, sounda2, 1, 1, 0, 0, rate_a2);
                randomA2(motionEvent);
                break;
            case R.id.ibtn_a3:
                playSoundsPool(motionEvent, sounda3, 1, 1, 0, 0, rate_a3);
                randomA3(motionEvent);

                break;
            case R.id.ibtn_a4:
                playSoundsPool(motionEvent, sounda4, 1, 1, 0, 0, rate_a4);
                randomA4(motionEvent);

                break;
            case R.id.ibtn_a5:
                playSoundsPool(motionEvent, sounda5, 1, 1, 0, 0, rate_a5);
                randomA5(motionEvent);

                break;
            case R.id.ibtn_a6:
                playSoundsPool(motionEvent, sounda6, 1, 1, 0, 0, rate_a6);
                randomA6(motionEvent);

                break;
            case R.id.ibtn_a7:
                playSoundsPool(motionEvent, sounda7, 1, 1, 0, 0, rate_a7);
                randomA7(motionEvent);

                break;
            case R.id.ibtn_a8:
                playSoundsPool(motionEvent, sounda8, 1, 1, 0, 0, rate_a8);
                randomA8(motionEvent);

                break;
            case R.id.ibtn_a9:
                playSoundsPool(motionEvent, sounda9, 1, 1, 0, 0, rate_a9);
                randomA9(motionEvent);

                break;
            case R.id.ibtn_a10:
                playSoundsPool(motionEvent, sounda10, 1, 1, 0, 0, rate_a10);
                randomA10(motionEvent);
                break;
            case R.id.ibtn_a11:
                playSoundsPool(motionEvent, sounda11, 1, 1, 0, 0, rate_a11);
                randomA11(motionEvent);

                break;
            case R.id.ibtn_a12:
                playSoundsPool(motionEvent, sounda12, 1, 1, 0, 0, rate_a12);
                randomA12(motionEvent);
                break;

            case R.id.ibtn_b1:
                playSoundsPool(motionEvent, soundb1, 1, 1, 0, 0, rate_b1);
                randomB1(motionEvent);
                break;
            case R.id.ibtn_b2:
                playSoundsPool(motionEvent, soundb2, 1, 1, 0, 0, rate_b2);
                randomB2(motionEvent);

                break;
            case R.id.ibtn_b3:
                playSoundsPool(motionEvent, soundb3, 1, 1, 0, 0, rate_b3);
                randomB3(motionEvent);

                break;
            case R.id.ibtn_b4:
                playSoundsPool(motionEvent, soundb4, 1, 1, 0, 0, rate_b4);
                randomB4(motionEvent);

                break;
            case R.id.ibtn_b5:
                playSoundsPool(motionEvent, soundb5, 1, 1, 0, 0, rate_b5);
                randomB5(motionEvent);

                break;
            case R.id.ibtn_b6:
                playSoundsPool(motionEvent, soundb6, 1, 1, 0, 0, rate_b6);
                randomB6(motionEvent);

                break;
            case R.id.ibtn_b7:
                playSoundsPool(motionEvent, soundb7, 1, 1, 0, 0, rate_b7);
                randomB7(motionEvent);

                break;
            case R.id.ibtn_b8:
                playSoundsPool(motionEvent, soundb8, 1, 1, 0, 0, rate_b8);
                randomB8(motionEvent);

                break;
            case R.id.ibtn_b9:
                playSoundsPool(motionEvent, soundb9, 1, 1, 0, 0, rate_b9);
                randomB9(motionEvent);

                break;
            case R.id.ibtn_b10:
                playSoundsPool(motionEvent, soundb10, 1, 1, 0, 0, rate_b10);
                randomB10(motionEvent);

                break;
            case R.id.ibtn_b11:
                playSoundsPool(motionEvent, soundb11, 1, 1, 0, 0, rate_b11);
                randomB11(motionEvent);

                break;
            case R.id.ibtn_b12:
                playSoundsPool(motionEvent, soundb12, 1, 1, 0, 0, rate_b12);
                randomB12(motionEvent);

                break;


        }
        return false;
    }


    public void runNine(final ImageButton im1, final int a1, final ImageButton im2, final int a2,
                        final ImageButton im3, final int a3, final ImageButton im4, final int a4,
                        final ImageButton im5, final int a5, final ImageButton im6, final int a6,
                        final ImageButton im7, final int a7, final ImageButton im8, final int a8,
                        final ImageButton im9, final int a9, final int color) {
        final Handler handler = new Handler();


        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                im1.setBackgroundResource(color);

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        im1.setBackgroundResource(a1);

                        im2.setBackgroundResource(color);
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                im2.setBackgroundResource(a2);

                                im3.setBackgroundResource(color);


                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        im3.setBackgroundResource(a3);

                                        im4.setBackgroundResource(color);

                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                im4.setBackgroundResource(a4);

                                                im5.setBackgroundResource(color);

                                                handler.postDelayed(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        im5.setBackgroundResource(a5);

                                                        im6.setBackgroundResource(color);

                                                        handler.postDelayed(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                im6.setBackgroundResource(a6);

                                                                im7.setBackgroundResource(color);

                                                                handler.postDelayed(new Runnable() {
                                                                    @Override
                                                                    public void run() {
                                                                        im7.setBackgroundResource(a7);

                                                                        im8.setBackgroundResource(color);
                                                                        im8.setImageResource(android.R.color.transparent);

                                                                        handler.postDelayed(new Runnable() {
                                                                            @Override
                                                                            public void run() {
                                                                                im8.setBackgroundResource(a8);

                                                                                im9.setBackgroundResource(color);

                                                                                handler.postDelayed(new Runnable() {
                                                                                    @Override
                                                                                    public void run() {
                                                                                        im9.setBackgroundResource(a9);
                                                                                    }
                                                                                }, 100);
                                                                            }
                                                                        }, 100);
                                                                    }
                                                                }, 100);
                                                            }
                                                        }, 100);
                                                    }
                                                }, 100);
                                            }
                                        }, 100);
                                    }
                                }, 100);
                            }
                        }, 100);
                    }
                }, 100);
            }
        }, 100);


    }


    public void runTwoWays(final ImageButton im1, final int a1, final ImageButton im2, final int a2,
                           final ImageButton im3, final int a3, final ImageButton im4, final int a4,
                           final ImageButton im5, final int a5, final ImageButton im6, final int a6,
                           final ImageButton im7, final int a7, final ImageButton im8, final int a8,
                           final ImageButton im9, final int a9, final int color) {
        final Handler handler = new Handler();

        im1.setBackgroundResource(color);
        im2.setBackgroundResource(color);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                im1.setBackgroundResource(a1);

                im2.setBackgroundResource(a2);

                im3.setBackgroundResource(color);

                im4.setBackgroundResource(color);

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        im3.setBackgroundResource(a3);

                        im4.setBackgroundResource(a4);

                        im5.setBackgroundResource(color);

                        im6.setBackgroundResource(color);

                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                im5.setBackgroundResource(a5);

                                im6.setBackgroundResource(a6);

                                im7.setBackgroundResource(color);

                                im8.setBackgroundResource(color);

                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        im7.setBackgroundResource(a7);

                                        im8.setBackgroundResource(a8);

                                        im9.setBackgroundResource(color);

                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                im9.setBackgroundResource(a9);
                                            }
                                        }, 100);
                                    }
                                }, 100);
                            }
                        }, 100);
                    }
                }, 100);
            }
        }, 100);
    }

    public void runFourRow(final ImageButton im1, final int a1, final ImageButton im2, final int a2,
                           final ImageButton im3, final int a3, final ImageButton im4, final int a4,
                           final ImageButton im5, final int a5, final ImageButton im6, final int a6,
                           final ImageButton im7, final int a7, final ImageButton im8, final int a8,
                           final ImageButton im9, final int a9, final ImageButton im10, final int a10,
                           final ImageButton im11, final int a11, final ImageButton im12, final int a12,
                           final int color) {
        final Handler handler = new Handler();
        im1.setBackgroundResource(color);

        im2.setBackgroundResource(color);

        im3.setBackgroundResource(color);

        im4.setBackgroundResource(color);


        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                im1.setBackgroundResource(a1);

                im2.setBackgroundResource(a2);

                im3.setBackgroundResource(a3);

                im4.setBackgroundResource(a4);

                im5.setBackgroundResource(color);

                im6.setBackgroundResource(color);

                im7.setBackgroundResource(color);

                im8.setBackgroundResource(color);


                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        im5.setBackgroundResource(a5);

                        im6.setBackgroundResource(a6);

                        im7.setBackgroundResource(a7);

                        im8.setBackgroundResource(a8);

                        im9.setBackgroundResource(color);

                        im10.setBackgroundResource(color);

                        im11.setBackgroundResource(color);

                        im12.setBackgroundResource(color);

                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                im9.setBackgroundResource(a9);

                                im10.setBackgroundResource(a10);

                                im11.setBackgroundResource(a11);

                                im12.setBackgroundResource(a12);

                            }
                        }, 100);
                    }
                }, 100);
            }
        }, 100);
    }

    public void runThreeColumns(final ImageButton im1, final int a1, final ImageButton im2, final int a2,
                                final ImageButton im3, final int a3, final ImageButton im4, final int a4,
                                final ImageButton im5, final int a5, final ImageButton im6, final int a6,
                                final ImageButton im7, final int a7, final ImageButton im8, final int a8,
                                final ImageButton im9, final int a9, final ImageButton im10, final int a10,
                                final ImageButton im11, final int a11, final ImageButton im12, final int a12,
                                final int color) {
        final Handler handler = new Handler();
        im1.setBackgroundResource(color);

        im2.setBackgroundResource(color);

        im3.setBackgroundResource(color);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                im1.setBackgroundResource(a1);

                im2.setBackgroundResource(a2);

                im3.setBackgroundResource(a3);

                im4.setBackgroundResource(color);

                im5.setBackgroundResource(color);

                im6.setBackgroundResource(color);

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        im4.setBackgroundResource(a4);

                        im5.setBackgroundResource(a5);

                        im6.setBackgroundResource(a6);

                        im7.setBackgroundResource(color);

                        im8.setBackgroundResource(color);

                        im9.setBackgroundResource(color);

                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                im7.setBackgroundResource(a7);

                                im8.setBackgroundResource(a8);

                                im9.setBackgroundResource(a9);

                                im10.setBackgroundResource(color);

                                im11.setBackgroundResource(color);

                                im12.setBackgroundResource(color);

                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        im10.setBackgroundResource(a10);

                                        im11.setBackgroundResource(a11);

                                        im12.setBackgroundResource(a12);

                                    }
                                }, 100);
                            }
                        }, 100);
                    }
                }, 100);
            }
        }, 100);
    }

    public void runTwo(final ImageButton im1, final int a1, final ImageButton im2, final int a2, int color) {
        final Handler handler = new Handler();
        im1.setBackgroundResource(color);

        im2.setBackgroundResource(color);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                im1.setBackgroundResource(a1);

                im2.setBackgroundResource(a2);

            }
        }, 100);
    }

    public void runThree(final ImageButton im1, final int a1, final ImageButton im2, final int a2, final ImageButton im3, final int a3, int color) {
        final Handler handler = new Handler();
        im1.setBackgroundResource(color);

        im2.setBackgroundResource(color);

        im3.setBackgroundResource(color);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                im1.setBackgroundResource(a1);

                im2.setBackgroundResource(a2);

                im3.setBackgroundResource(a3);

            }
        }, 100);
    }

    public void runSeven(final ImageButton im1, final int a1, final ImageButton im2, final int a2,
                         final ImageButton im3, final int a3, final ImageButton im4, final int a4,
                         final ImageButton im5, final int a5, final ImageButton im6, final int a6,
                         final ImageButton im7, final int a7, final int color) {
        final Handler handler = new Handler();
        im1.setBackgroundResource(color);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                im1.setBackgroundResource(a1);

                im2.setBackgroundResource(color);

                im3.setBackgroundResource(color);

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        im2.setBackgroundResource(a2);

                        im3.setBackgroundResource(a3);

                        im4.setBackgroundResource(color);

                        im5.setBackgroundResource(color);

                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                im4.setBackgroundResource(a4);

                                im5.setBackgroundResource(a5);

                                im6.setBackgroundResource(color);

                                im7.setBackgroundResource(color);

                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        im6.setBackgroundResource(a6);

                                        im7.setBackgroundResource(a7);

                                    }
                                }, 100);
                            }
                        }, 100);
                    }
                }, 100);
            }
        }, 100);
    }

    public void runFour(final ImageButton im1, final int a1,
                        final ImageButton im2, final int a2,
                        final ImageButton im3, final int a3,
                        final ImageButton im4, final int a4, int color) {
        final Handler handler = new Handler();
        im1.setBackgroundResource(color);

        im2.setBackgroundResource(color);

        im3.setBackgroundResource(color);

        im4.setBackgroundResource(color);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                im1.setBackgroundResource(a1);

                im2.setBackgroundResource(a2);

                im3.setBackgroundResource(a3);

                im4.setBackgroundResource(a4);

            }
        }, 100);
    }

    public void runEight(final ImageButton im1, final int a1, final ImageButton im2, final int a2,
                         final ImageButton im3, final int a3, final ImageButton im4, final int a4,
                         final ImageButton im5, final int a5, final ImageButton im6, final int a6,
                         final ImageButton im7, final int a7, final ImageButton im8, final int a8, final int color) {
        final Handler handler = new Handler();
        im1.setBackgroundResource(color);

        im2.setBackgroundResource(color);

        im3.setBackgroundResource(color);

        im4.setBackgroundResource(color);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                im1.setBackgroundResource(a1);

                im2.setBackgroundResource(a2);

                im3.setBackgroundResource(a3);

                im4.setBackgroundResource(a4);

                im5.setBackgroundResource(color);

                im6.setBackgroundResource(color);

                im7.setBackgroundResource(color);

                im8.setBackgroundResource(color);

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        im5.setBackgroundResource(a5);

                        im6.setBackgroundResource(a6);

                        im7.setBackgroundResource(a7);

                        im8.setBackgroundResource(a8);

                    }
                }, 100);
            }
        }, 100);
    }

    public void randomA1(MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            int i = random.nextInt(8);
            int j = random.nextInt(color.length - 1);
            if (i == 0) {
                runNine(ibtn_a2, a[1], ibtn_a3, a[2], ibtn_a6, a[5], ibtn_a9, a[8], ibtn_a12, a[11], ibtn_a11, a[10], ibtn_a10, a[9], ibtn_a7, a[6], ibtn_a4, a[3], color[j]);
            } else if (i == 1) {
                runThreeColumns(ibtn_a1, a[0], ibtn_a2, a[1], ibtn_a3, a[2], ibtn_a4, a[3], ibtn_a5, a[4], ibtn_a6, a[5], ibtn_a7, a[6], ibtn_a8, a[7], ibtn_a9, a[8], ibtn_a10, a[9], ibtn_a11, a[10], ibtn_a12, a[11], color[j]);
            } else if (i == 2) {
                runFourRow(ibtn_a1, a[0], ibtn_a4, a[3], ibtn_a7, a[6], ibtn_a10, a[9], ibtn_a2, a[1], ibtn_a5, a[4], ibtn_a8, a[7], ibtn_a11, a[10], ibtn_a3, a[2], ibtn_a6, a[5], ibtn_a9, a[8], ibtn_a12, a[11], color[j]);
            } else if (i == 3) {
                runTwo(ibtn_a2, a[1], ibtn_a4, a[3], color[j]);
            } else if (i == 4) {
                runTwoWays(ibtn_a2, a[1], ibtn_a4, a[3], ibtn_a3, a[2], ibtn_a7, a[6], ibtn_a6, a[5], ibtn_a10, a[9], ibtn_a9, a[8], ibtn_a11, a[10], ibtn_a12, a[11], color[j]);
            } else if (i == 5) {
                runThree(ibtn_a4, a[3], ibtn_a2, a[1], ibtn_a5, a[4], color[j]);
            } else if (i == 6) {
                runTwo(ibtn_a4, a[3], ibtn_a7, a[6], color[j]);
            } else if (i == 7) {
                runThree(ibtn_a2, a[1], ibtn_a4, a[3], ibtn_a5, a[4], color[j]);
            }
        }
    }

    public void randomA3(MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            int i = random.nextInt(8);
            int j = random.nextInt(color.length - 1);
            if (i == 0) {
                runNine(ibtn_a6, a[5], ibtn_a9, a[8], ibtn_a12, a[11], ibtn_a11, a[10], ibtn_a10, a[9], ibtn_a7, a[6], ibtn_a4, a[3], ibtn_a1, a[0], ibtn_a2, a[1], color[j]);
            } else if (i == 1) {
                runThreeColumns(ibtn_a1, a[0], ibtn_a2, a[1], ibtn_a3, a[2], ibtn_a4, a[3], ibtn_a5, a[4], ibtn_a6, a[5], ibtn_a7, a[6], ibtn_a8, a[7], ibtn_a9, a[8], ibtn_a10, a[9], ibtn_a11, a[10], ibtn_a12, a[11], color[j]);
            } else if (i == 2) {
                runFourRow(ibtn_a1, a[0], ibtn_a4, a[3], ibtn_a7, a[6], ibtn_a10, a[9], ibtn_a2, a[1], ibtn_a5, a[4], ibtn_a8, a[7], ibtn_a11, a[10], ibtn_a3, a[2], ibtn_a6, a[5], ibtn_a9, a[8], ibtn_a12, a[11], color[j]);
            } else if (i == 3) {
                runTwo(ibtn_a2, a[1], ibtn_a6, a[5], color[j]);
            } else if (i == 4) {
                runTwoWays(ibtn_a2, a[1], ibtn_a6, a[5], ibtn_a1, a[0], ibtn_a9, a[8], ibtn_a4, a[3], ibtn_a12, a[11], ibtn_a7, a[6], ibtn_a11, a[10], ibtn_a10, a[9], color[j]);
            } else if (i == 5) {
                runThree(ibtn_a6, a[5], ibtn_a2, a[1], ibtn_a5, a[4], color[j]);
            } else if (i == 6) {
                runTwo(ibtn_a6, a[5], ibtn_a9, a[8], color[j]);
            } else if (i == 7) {
                runThree(ibtn_a2, a[1], ibtn_a6, a[5], ibtn_a5, a[4], color[j]);
            }
        }
    }

    public void randomA10(MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            int i = random.nextInt(8);
            int j = random.nextInt(color.length - 1);
            if (i == 0) {
                runNine(ibtn_a7, a[6], ibtn_a4, a[3], ibtn_a1, a[0], ibtn_a2, a[1], ibtn_a3, a[2], ibtn_a6, a[5], ibtn_a9, a[8], ibtn_a12, a[11], ibtn_a11, a[10], color[j]);
            } else if (i == 1) {
                runThreeColumns(ibtn_a1, a[0], ibtn_a2, a[1], ibtn_a3, a[2], ibtn_a4, a[3], ibtn_a5, a[4], ibtn_a6, a[5], ibtn_a7, a[6], ibtn_a8, a[7], ibtn_a9, a[8], ibtn_a10, a[9], ibtn_a11, a[10], ibtn_a12, a[11], color[j]);
            } else if (i == 2) {
                runFourRow(ibtn_a1, a[0], ibtn_a4, a[3], ibtn_a7, a[6], ibtn_a10, a[9], ibtn_a2, a[1], ibtn_a5, a[4], ibtn_a8, a[7], ibtn_a11, a[10], ibtn_a3, a[2], ibtn_a6, a[5], ibtn_a9, a[8], ibtn_a12, a[11], color[j]);
            } else if (i == 3) {
                runTwo(ibtn_a7, a[6], ibtn_a11, a[10], color[j]);
            } else if (i == 4) {
                runTwoWays(ibtn_a7, a[6], ibtn_a11, a[10], ibtn_a4, a[3], ibtn_a12, a[11], ibtn_a1, a[0], ibtn_a9, a[8], ibtn_a2, a[1], ibtn_a6, a[5], ibtn_a3, a[2], color[j]);
            } else if (i == 5) {
                runThree(ibtn_a7, a[6], ibtn_a5, a[4], ibtn_a11, a[10], color[j]);
            } else if (i == 6) {
                runTwo(ibtn_a7, a[6], ibtn_a4, a[3], color[j]);
            } else if (i == 7) {
                runThree(ibtn_a7, a[6], ibtn_a8, a[7], ibtn_a11, a[10], color[j]);
            }
        }
    }

    public void randomA12(MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            int i = random.nextInt(8);
            int j = random.nextInt(color.length - 1);
            if (i == 0) {
                runNine(ibtn_a11, a[10], ibtn_a10, a[9], ibtn_a7, a[6], ibtn_a4, a[3], ibtn_a1, a[0], ibtn_a2, a[1], ibtn_a3, a[2], ibtn_a6, a[5], ibtn_a9, a[8], color[j]);
            } else if (i == 1) {
                runThreeColumns(ibtn_a1, a[0], ibtn_a2, a[1], ibtn_a3, a[2], ibtn_a4, a[3], ibtn_a5, a[4], ibtn_a6, a[5], ibtn_a7, a[6], ibtn_a8, a[7], ibtn_a9, a[8], ibtn_a10, a[9], ibtn_a11, a[10], ibtn_a12, a[11], color[j]);
            } else if (i == 2) {
                runFourRow(ibtn_a1, a[0], ibtn_a4, a[3], ibtn_a7, a[6], ibtn_a10, a[9], ibtn_a2, a[1], ibtn_a5, a[4], ibtn_a8, a[7], ibtn_a11, a[10], ibtn_a3, a[2], ibtn_a6, a[5], ibtn_a9, a[8], ibtn_a12, a[11], color[j]);
            } else if (i == 3) {
                runTwo(ibtn_a9, a[8], ibtn_a11, a[10], color[j]);
            } else if (i == 4) {
                runTwoWays(ibtn_a11, a[10], ibtn_a9, a[8], ibtn_a10, a[9], ibtn_a6, a[5], ibtn_a7, a[6], ibtn_a3, a[2], ibtn_a4, a[3], ibtn_a2, a[1], ibtn_a1, a[0], color[j]);
            } else if (i == 5) {
                runThree(ibtn_a9, a[8], ibtn_a5, a[4], ibtn_a11, a[10], color[j]);
            } else if (i == 6) {
                runTwo(ibtn_a9, a[8], ibtn_a6, a[5], color[j]);
            } else if (i == 7) {
                runThree(ibtn_a9, a[8], ibtn_a11, a[10], ibtn_a8, a[7], color[j]);
            }
        }
    }

    public void randomA8(MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            int i = random.nextInt(8);
            int j = random.nextInt(color.length - 1);
            if (i == 0) {
                runEight(ibtn_a5, a[4], ibtn_a7, a[6], ibtn_a9, a[8], ibtn_a11, a[10], ibtn_a4, a[3], ibtn_a6, a[5], ibtn_a10, a[9], ibtn_a12, a[11], color[j]);
            } else if (i == 1) {
                runFourRow(ibtn_a1, a[0], ibtn_a4, a[3], ibtn_a7, a[6], ibtn_a10, a[9], ibtn_a2, a[1], ibtn_a5, a[4], ibtn_a8, a[7], ibtn_a11, a[10], ibtn_a3, a[2], ibtn_a6, a[5], ibtn_a9, a[8], ibtn_a12, a[11], color[j]);
            } else if (i == 2) {
                runThreeColumns(ibtn_a1, a[0], ibtn_a2, a[1], ibtn_a3, a[2], ibtn_a4, a[3], ibtn_a5, a[4], ibtn_a6, a[5], ibtn_a7, a[6], ibtn_a8, a[7], ibtn_a9, a[8], ibtn_a10, a[9], ibtn_a11, a[10], ibtn_a12, a[11], color[j]);
            } else if (i == 3) {
                runTwo(ibtn_a5, a[4], ibtn_a2, a[1], color[j]);
            } else if (i == 4) {
                runNine(ibtn_a8, a[7], ibtn_a5, a[4], ibtn_a6, a[5], ibtn_a9, a[8], ibtn_a12, a[11], ibtn_a11, a[10], ibtn_a10, a[9], ibtn_a7, a[6], ibtn_a4, a[3], color[j]);
            } else if (i == 5) {
                runFour(ibtn_a5, a[4], ibtn_a9, a[8], ibtn_a7, a[6], ibtn_a11, a[10], color[j]);
            } else if (i == 6) {
                runEight(ibtn_a4, a[3], ibtn_a6, a[5], ibtn_a10, a[9], ibtn_a12, a[11], ibtn_a5, a[4], ibtn_a7, a[6], ibtn_a9, a[8], ibtn_a11, a[10], color[j]);
            } else if (i == 7) {
                runSeven(ibtn_a9, a[8], ibtn_a6, a[5], ibtn_a12, a[11], ibtn_a5, a[4], ibtn_a11, a[10], ibtn_a4, a[3], ibtn_a10, a[9], color[j]);
            }

        }
    }

    public void randomA5(MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            int i = random.nextInt(8);
            int j = random.nextInt(color.length - 1);
            if (i == 0) {
                runEight(ibtn_a2, a[1], ibtn_a4, a[3], ibtn_a6, a[5], ibtn_a8, a[7], ibtn_a1, a[0], ibtn_a3, a[2], ibtn_a7, a[6], ibtn_a9, a[8], color[j]);
            } else if (i == 1) {
                runFourRow(ibtn_a1, a[0], ibtn_a4, a[3], ibtn_a7, a[6], ibtn_a10, a[9], ibtn_a2, a[1], ibtn_a5, a[4], ibtn_a8, a[7], ibtn_a11, a[10], ibtn_a3, a[2], ibtn_a6, a[5], ibtn_a9, a[8], ibtn_a12, a[11], color[j]);
            } else if (i == 2) {
                runThreeColumns(ibtn_a1, a[0], ibtn_a2, a[1], ibtn_a3, a[2], ibtn_a4, a[3], ibtn_a5, a[4], ibtn_a6, a[5], ibtn_a7, a[6], ibtn_a8, a[7], ibtn_a9, a[8], ibtn_a10, a[9], ibtn_a11, a[10], ibtn_a12, a[11], color[j]);
            } else if (i == 3) {
                runTwo(ibtn_a8, a[7], ibtn_a11, a[10], color[j]);
            } else if (i == 4) {
                runNine(ibtn_a5, a[4], ibtn_a2, a[1], ibtn_a3, a[2], ibtn_a6, a[5], ibtn_a9, a[8], ibtn_a8, a[7], ibtn_a7, a[6], ibtn_a4, a[3], ibtn_a1, a[0], color[j]);
            } else if (i == 5) {
                runFour(ibtn_a2, a[1], ibtn_a6, a[5], ibtn_a4, a[3], ibtn_a8, a[7], color[j]);
            } else if (i == 6) {
                runEight(ibtn_a1, a[0], ibtn_a3, a[2], ibtn_a7, a[6], ibtn_a9, a[8], ibtn_a2, a[1], ibtn_a4, a[3], ibtn_a6, a[5], ibtn_a8, a[7], color[j]);
            } else if (i == 7) {
                runSeven(ibtn_a6, a[5], ibtn_a3, a[2], ibtn_a9, a[8], ibtn_a2, a[1], ibtn_a8, a[7], ibtn_a1, a[0], ibtn_a7, a[6], color[j]);
            }

        }
    }

    public void randomA2(MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            int i = random.nextInt(8);
            int j = random.nextInt(color.length - 1);
            {
                if (i == 0) {
                    runFourRow(ibtn_a1, a[0], ibtn_a4, a[3], ibtn_a7, a[6], ibtn_a10, a[9], ibtn_a2, a[1], ibtn_a5, a[4], ibtn_a8, a[7], ibtn_a11, a[10], ibtn_a3, a[2], ibtn_a6, a[5], ibtn_a9, a[8], ibtn_a12, a[11], color[j]);
                } else if (i == 1) {
                    runThreeColumns(ibtn_a1, a[0], ibtn_a2, a[1], ibtn_a3, a[2], ibtn_a4, a[3], ibtn_a5, a[4], ibtn_a6, a[5], ibtn_a7, a[6], ibtn_a8, a[7], ibtn_a9, a[8], ibtn_a10, a[9], ibtn_a11, a[10], ibtn_a12, a[11], color[j]);
                } else if (i == 2) {
                    runNine(ibtn_a2, a[1], ibtn_a5, a[4], ibtn_a6, a[5], ibtn_a9, a[8], ibtn_a8, a[7], ibtn_a7, a[6], ibtn_a10, a[9], ibtn_a11, a[10], ibtn_a12, a[11], color[j]);
                } else if (i == 3) {
                    runNine(ibtn_a3, a[2], ibtn_a6, a[5], ibtn_a9, a[8], ibtn_a12, a[11], ibtn_a11, a[10], ibtn_a10, a[9], ibtn_a7, a[6], ibtn_a4, a[3], ibtn_a1, a[0], color[j]);
                } else if (i == 4) {
                    runTwo(ibtn_a5, a[4], ibtn_a8, a[7], color[j]);
                } else if (i == 5) {
                    runThree(ibtn_a1, a[0], ibtn_a3, a[2], ibtn_a5, a[4], color[j]);
                } else if (i == 6) {
                    runTwoWays(ibtn_a1, a[0], ibtn_a3, a[2], ibtn_a4, a[3], ibtn_a6, a[5], ibtn_a7, a[6], ibtn_a9, a[8], ibtn_a10, a[9], ibtn_a12, a[11], ibtn_a11, a[10], color[j]);
                } else if (i == 7) {
                    runThree(ibtn_a7, a[6], ibtn_a5, a[4], ibtn_a9, a[8], color[j]);
                }
            }
        }
    }

    public void randomA11(MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            int i = random.nextInt(8);
            int j = random.nextInt(color.length - 1);
            {
                if (i == 0) {
                    runFourRow(ibtn_a1, a[0], ibtn_a4, a[3], ibtn_a7, a[6], ibtn_a10, a[9], ibtn_a2, a[1], ibtn_a5, a[4], ibtn_a8, a[7], ibtn_a11, a[10], ibtn_a3, a[2], ibtn_a6, a[5], ibtn_a9, a[8], ibtn_a12, a[11], color[j]);
                } else if (i == 1) {
                    runThreeColumns(ibtn_a1, a[0], ibtn_a2, a[1], ibtn_a3, a[2], ibtn_a4, a[3], ibtn_a5, a[4], ibtn_a6, a[5], ibtn_a7, a[6], ibtn_a8, a[7], ibtn_a9, a[8], ibtn_a10, a[9], ibtn_a11, a[10], ibtn_a12, a[11], color[j]);
                } else if (i == 2) {
                    runNine(ibtn_a11, a[10], ibtn_a8, a[7], ibtn_a7, a[6], ibtn_a4, a[3], ibtn_a5, a[4], ibtn_a6, a[5], ibtn_a3, a[2], ibtn_a2, a[1], ibtn_a1, a[0], color[j]);
                } else if (i == 3) {
                    runNine(ibtn_a10, a[9], ibtn_a7, a[6], ibtn_a4, a[3], ibtn_a1, a[0], ibtn_a2, a[1], ibtn_a3, a[2], ibtn_a6, a[5], ibtn_a9, a[8], ibtn_a12, a[11], color[j]);
                } else if (i == 4) {
                    runTwo(ibtn_a5, a[4], ibtn_a8, a[7], color[j]);
                } else if (i == 5) {
                    runThree(ibtn_a10, a[9], ibtn_a8, a[7], ibtn_a12, a[11], color[j]);
                } else if (i == 6) {
                    runTwoWays(ibtn_a10, a[9], ibtn_a12, a[11], ibtn_a7, a[6], ibtn_a9, a[8], ibtn_a4, a[3], ibtn_a6, a[5], ibtn_a1, a[0], ibtn_a3, a[2], ibtn_a2, a[1], color[j]);
                } else if (i == 7) {
                    runThree(ibtn_a4, a[3], ibtn_a8, a[7], ibtn_a6, a[5], color[j]);
                }
            }
        }
    }

    public void randomA4(MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            int i = random.nextInt(8);
            int j = random.nextInt(color.length - 1);
            if (i == 0) {
                runFourRow(ibtn_a1, a[0], ibtn_a4, a[3], ibtn_a7, a[6], ibtn_a10, a[9], ibtn_a2, a[1], ibtn_a5, a[4], ibtn_a8, a[7], ibtn_a11, a[10], ibtn_a3, a[2], ibtn_a6, a[5], ibtn_a9, a[8], ibtn_a12, a[11], color[j]);
            } else if (i == 1) {
                runThreeColumns(ibtn_a1, a[0], ibtn_a2, a[1], ibtn_a3, a[2], ibtn_a4, a[3], ibtn_a5, a[4], ibtn_a6, a[5], ibtn_a7, a[6], ibtn_a8, a[7], ibtn_a9, a[8], ibtn_a10, a[9], ibtn_a11, a[10], ibtn_a12, a[11], color[j]);
            } else if (i == 2) {
                runNine(ibtn_a1, a[0], ibtn_a2, a[1], ibtn_a3, a[2], ibtn_a6, a[5], ibtn_a9, a[8], ibtn_a12, a[11], ibtn_a11, a[10], ibtn_a10, a[9], ibtn_a7, a[6], color[j]);
            } else if (i == 3) {
                runNine(ibtn_a4, a[3], ibtn_a5, a[4], ibtn_a2, a[1], ibtn_a3, a[2], ibtn_a6, a[5], ibtn_a9, a[8], ibtn_a12, a[11], ibtn_a11, a[10], ibtn_a10, a[9], color[j]);
            } else if (i == 4) {
                runThree(ibtn_a1, a[0], ibtn_a5, a[4], ibtn_a7, a[6], color[j]);
            } else if (i == 5) {
                runThree(ibtn_a5, a[4], ibtn_a3, a[2], ibtn_a9, a[8], color[j]);
            } else if (i == 6) {
                runNine(ibtn_a4, a[3], ibtn_a5, a[4], ibtn_a6, a[5], ibtn_a9, a[8], ibtn_a8, a[7], ibtn_a7, a[6], ibtn_a10, a[9], ibtn_a11, a[10], ibtn_a12, a[11], color[j]);
            } else if (i == 7) {
                runTwo(ibtn_a5, a[4], ibtn_a6, a[5], color[j]);
            }
        }
    }

    public void randomA6(MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            int i = random.nextInt(8);
            int j = random.nextInt(color.length - 1);
            if (i == 0) {
                runFourRow(ibtn_a1, a[0], ibtn_a4, a[3], ibtn_a7, a[6], ibtn_a10, a[9], ibtn_a2, a[1], ibtn_a5, a[4], ibtn_a8, a[7], ibtn_a11, a[10], ibtn_a3, a[2], ibtn_a6, a[5], ibtn_a9, a[8], ibtn_a12, a[11], color[j]);
            } else if (i == 1) {
                runThreeColumns(ibtn_a1, a[0], ibtn_a2, a[1], ibtn_a3, a[2], ibtn_a4, a[3], ibtn_a5, a[4], ibtn_a6, a[5], ibtn_a7, a[6], ibtn_a8, a[7], ibtn_a9, a[8], ibtn_a10, a[9], ibtn_a11, a[10], ibtn_a12, a[11], color[j]);
            } else if (i == 2) {
                runNine(ibtn_a9, a[8], ibtn_a12, a[11], ibtn_a11, a[10], ibtn_a10, a[9], ibtn_a7, a[6], ibtn_a4, a[3], ibtn_a1, a[0], ibtn_a2, a[1], ibtn_a3, a[2], color[j]);
            } else if (i == 3) {
                runNine(ibtn_a6, a[5], ibtn_a5, a[4], ibtn_a2, a[1], ibtn_a1, a[0], ibtn_a4, a[3], ibtn_a7, a[6], ibtn_a10, a[9], ibtn_a11, a[10], ibtn_a12, a[11], color[j]);
            } else if (i == 4) {
                runThree(ibtn_a1, a[0], ibtn_a5, a[4], ibtn_a7, a[6], color[j]);
            } else if (i == 5) {
                runThree(ibtn_a5, a[4], ibtn_a3, a[2], ibtn_a9, a[8], color[j]);
            } else if (i == 6) {
                runNine(ibtn_a6, a[5], ibtn_a5, a[4], ibtn_a4, a[3], ibtn_a7, a[6], ibtn_a8, a[7], ibtn_a9, a[8], ibtn_a12, a[11], ibtn_a11, a[10], ibtn_a10, a[9], color[j]);
            } else if (i == 7) {
                runTwo(ibtn_a5, a[4], ibtn_a4, a[3], color[j]);
            }
        }
    }

    public void randomA7(MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            int i = random.nextInt(8);
            int j = random.nextInt(color.length - 1);
            if (i == 0) {
                runFourRow(ibtn_a1, a[0], ibtn_a4, a[3], ibtn_a7, a[6], ibtn_a10, a[9], ibtn_a2, a[1], ibtn_a5, a[4], ibtn_a8, a[7], ibtn_a11, a[10], ibtn_a3, a[2], ibtn_a6, a[5], ibtn_a9, a[8], ibtn_a12, a[11], color[j]);
            } else if (i == 1) {
                runThreeColumns(ibtn_a1, a[0], ibtn_a2, a[1], ibtn_a3, a[2], ibtn_a4, a[3], ibtn_a5, a[4], ibtn_a6, a[5], ibtn_a7, a[6], ibtn_a8, a[7], ibtn_a9, a[8], ibtn_a10, a[9], ibtn_a11, a[10], ibtn_a12, a[11], color[j]);
            } else if (i == 2) {
                runThree(ibtn_a6, a[5], ibtn_a8, a[7], ibtn_a12, a[11], color[j]);
            } else if (i == 3) {
                runThree(ibtn_a4, a[3], ibtn_a8, a[7], ibtn_a10, a[9], color[j]);
            } else if (i == 4) {
                runNine(ibtn_a7, a[6], ibtn_a8, a[7], ibtn_a9, a[8], ibtn_a6, a[5], ibtn_a5, a[4], ibtn_a4, a[3], ibtn_a1, a[0], ibtn_a2, a[1], ibtn_a3, a[2], color[j]);
            } else if (i == 5) {
                runNine(ibtn_a4, a[3], ibtn_a1, a[0], ibtn_a2, a[1], ibtn_a3, a[2], ibtn_a6, a[5], ibtn_a9, a[8], ibtn_a12, a[11], ibtn_a11, a[10], ibtn_a10, a[9], color[j]);
            } else if (i == 6) {
                runTwo(ibtn_a4, a[3], ibtn_a1, a[0], color[j]);
            } else if (i == 7) {
                runNine(ibtn_a7, a[6], ibtn_a10, a[9], ibtn_a11, a[10], ibtn_a8, a[7], ibtn_a5, a[4], ibtn_a4, a[3], ibtn_a7, a[6], ibtn_a8, a[7], ibtn_a9, a[8], color[j]);
            }
        }
    }

    public void randomA9(MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            int i = random.nextInt(8);
            int j = random.nextInt(color.length - 1);
            if (i == 0) {
                runFourRow(ibtn_a1, a[0], ibtn_a4, a[3], ibtn_a7, a[6], ibtn_a10, a[9], ibtn_a2, a[1], ibtn_a5, a[4], ibtn_a8, a[7], ibtn_a11, a[10], ibtn_a3, a[2], ibtn_a6, a[5], ibtn_a9, a[8], ibtn_a12, a[11], color[j]);
            } else if (i == 1) {
                runThreeColumns(ibtn_a1, a[0], ibtn_a2, a[1], ibtn_a3, a[2], ibtn_a4, a[3], ibtn_a5, a[4], ibtn_a6, a[5], ibtn_a7, a[6], ibtn_a8, a[7], ibtn_a9, a[8], ibtn_a10, a[9], ibtn_a11, a[10], ibtn_a12, a[11], color[j]);
            } else if (i == 2) {
                runThree(ibtn_a6, a[5], ibtn_a8, a[7], ibtn_a12, a[11], color[j]);
            } else if (i == 3) {
                runThree(ibtn_a4, a[3], ibtn_a8, a[7], ibtn_a10, a[9], color[j]);
            } else if (i == 4) {
                runNine(ibtn_a9, a[8], ibtn_a8, a[7], ibtn_a7, a[6], ibtn_a4, a[3], ibtn_a5, a[4], ibtn_a6, a[5], ibtn_a3, a[2], ibtn_a2, a[1], ibtn_a1, a[0], color[j]);
            } else if (i == 5) {
                runNine(ibtn_a12, a[11], ibtn_a11, a[10], ibtn_a10, a[9], ibtn_a7, a[6], ibtn_a4, a[3], ibtn_a1, a[0], ibtn_a2, a[1], ibtn_a3, a[2], ibtn_a6, a[5], color[j]);
            } else if (i == 6) {
                runTwo(ibtn_a6, a[5], ibtn_a3, a[2], color[j]);
            } else if (i == 7) {
                runNine(ibtn_a9, a[8], ibtn_a6, a[5], ibtn_a5, a[4], ibtn_a8, a[7], ibtn_a11, a[10], ibtn_a12, a[11], ibtn_a9, a[8], ibtn_a8, a[7], ibtn_a7, a[6], color[j]);
            }
        }
    }

    public void randomB1(MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            int i = random.nextInt(8);
            int j = random.nextInt(color.length - 1);
            if (i == 0) {
                runNine(ibtn_b2, b[1], ibtn_b3, b[2], ibtn_b6, b[5], ibtn_b9, b[8], ibtn_b12, b[11], ibtn_b11, b[10], ibtn_b10, b[9], ibtn_b7, b[6], ibtn_b4, b[3], color[j]);
            } else if (i == 1) {
                runThreeColumns(ibtn_b1, b[0], ibtn_b2, b[1], ibtn_b3, b[2], ibtn_b4, b[3], ibtn_b5, b[4], ibtn_b6, b[5], ibtn_b7, b[6], ibtn_b8, b[7], ibtn_b9, b[8], ibtn_b10, b[9], ibtn_b11, b[10], ibtn_b12, b[11], color[j]);
            } else if (i == 2) {
                runFourRow(ibtn_b1, b[0], ibtn_b4, b[3], ibtn_b7, b[6], ibtn_b10, b[9], ibtn_b2, b[1], ibtn_b5, b[4], ibtn_b8, b[7], ibtn_b11, b[10], ibtn_b3, b[2], ibtn_b6, b[5], ibtn_b9, b[8], ibtn_b12, b[11], color[j]);
            } else if (i == 3) {
                runTwo(ibtn_b2, b[1], ibtn_b4, b[3], color[j]);
            } else if (i == 4) {
                runTwoWays(ibtn_b2, b[1], ibtn_b4, b[3], ibtn_b3, b[2], ibtn_b7, b[6], ibtn_b6, b[5], ibtn_b10, b[9], ibtn_b9, b[8], ibtn_b11, b[10], ibtn_b12, b[11], color[j]);
            } else if (i == 5) {
                runThree(ibtn_b4, b[3], ibtn_b2, b[1], ibtn_b5, b[4], color[j]);
            } else if (i == 6) {
                runTwo(ibtn_b4, b[3], ibtn_b7, b[6], color[j]);
            } else if (i == 7) {
                runThree(ibtn_b2, b[1], ibtn_b4, b[3], ibtn_b5, b[4], color[j]);
            }
        }
    }

    public void randomB3(MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            int i = random.nextInt(8);
            int j = random.nextInt(color.length - 1);
            if (i == 0) {
                runNine(ibtn_b6, b[5], ibtn_b9, b[8], ibtn_b12, b[11], ibtn_b11, b[10], ibtn_b10, b[9], ibtn_b7, b[6], ibtn_b4, b[3], ibtn_b1, b[0], ibtn_b2, b[1], color[j]);
            } else if (i == 1) {
                runThreeColumns(ibtn_b1, b[0], ibtn_b2, b[1], ibtn_b3, b[2], ibtn_b4, b[3], ibtn_b5, b[4], ibtn_b6, b[5], ibtn_b7, b[6], ibtn_b8, b[7], ibtn_b9, b[8], ibtn_b10, b[9], ibtn_b11, b[10], ibtn_b12, b[11], color[j]);
            } else if (i == 2) {
                runFourRow(ibtn_b1, b[0], ibtn_b4, b[3], ibtn_b7, b[6], ibtn_b10, b[9], ibtn_b2, b[1], ibtn_b5, b[4], ibtn_b8, b[7], ibtn_b11, b[10], ibtn_b3, b[2], ibtn_b6, b[5], ibtn_b9, b[8], ibtn_b12, b[11], color[j]);
            } else if (i == 3) {
                runTwo(ibtn_b2, b[1], ibtn_b6, b[5], color[j]);
            } else if (i == 4) {
                runTwoWays(ibtn_b2, b[1], ibtn_b6, b[5], ibtn_b1, b[0], ibtn_b9, b[8], ibtn_b4, b[3], ibtn_b12, b[11], ibtn_b7, b[6], ibtn_b11, b[10], ibtn_b10, b[9], color[j]);
            } else if (i == 5) {
                runThree(ibtn_b6, b[5], ibtn_b2, b[1], ibtn_b5, b[4], color[j]);
            } else if (i == 6) {
                runTwo(ibtn_b6, b[5], ibtn_b9, b[8], color[j]);
            } else if (i == 7) {
                runThree(ibtn_b2, b[1], ibtn_b6, b[5], ibtn_b5, b[4], color[j]);
            }
        }
    }

    public void randomB10(MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            int i = random.nextInt(8);
            int j = random.nextInt(color.length - 1);
            if (i == 0) {
                runNine(ibtn_b7, b[6], ibtn_b4, b[3], ibtn_b1, b[0], ibtn_b2, b[1], ibtn_b3, b[2], ibtn_b6, b[5], ibtn_b9, b[8], ibtn_b12, b[11], ibtn_b11, b[10], color[j]);
            } else if (i == 1) {
                runThreeColumns(ibtn_b1, b[0], ibtn_b2, b[1], ibtn_b3, b[2], ibtn_b4, b[3], ibtn_b5, b[4], ibtn_b6, b[5], ibtn_b7, b[6], ibtn_b8, b[7], ibtn_b9, b[8], ibtn_b10, b[9], ibtn_b11, b[10], ibtn_b12, b[11], color[j]);
            } else if (i == 2) {
                runFourRow(ibtn_b1, b[0], ibtn_b4, b[3], ibtn_b7, b[6], ibtn_b10, b[9], ibtn_b2, b[1], ibtn_b5, b[4], ibtn_b8, b[7], ibtn_b11, b[10], ibtn_b3, b[2], ibtn_b6, b[5], ibtn_b9, b[8], ibtn_b12, b[11], color[j]);
            } else if (i == 3) {
                runTwo(ibtn_b7, b[6], ibtn_b11, b[10], color[j]);
            } else if (i == 4) {
                runTwoWays(ibtn_b7, b[6], ibtn_b11, b[10], ibtn_b4, b[3], ibtn_b12, b[11], ibtn_b1, b[0], ibtn_b9, b[8], ibtn_b2, b[1], ibtn_b6, b[5], ibtn_b3, b[2], color[j]);
            } else if (i == 5) {
                runThree(ibtn_b7, b[6], ibtn_b5, b[4], ibtn_b11, b[10], color[j]);
            } else if (i == 6) {
                runTwo(ibtn_b7, b[6], ibtn_b4, b[3], color[j]);
            } else if (i == 7) {
                runThree(ibtn_b7, b[6], ibtn_b8, b[7], ibtn_b11, b[10], color[j]);
            }
        }
    }

    public void randomB12(MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            int i = random.nextInt(8);
            int j = random.nextInt(color.length - 1);
            if (i == 0) {
                runNine(ibtn_b11, b[10], ibtn_b10, b[9], ibtn_b7, b[6], ibtn_b4, b[3], ibtn_b1, b[0], ibtn_b2, b[1], ibtn_b3, b[2], ibtn_b6, b[5], ibtn_b9, b[8], color[j]);
            } else if (i == 1) {
                runThreeColumns(ibtn_b1, b[0], ibtn_b2, b[1], ibtn_b3, b[2], ibtn_b4, b[3], ibtn_b5, b[4], ibtn_b6, b[5], ibtn_b7, b[6], ibtn_b8, b[7], ibtn_b9, b[8], ibtn_b10, b[9], ibtn_b11, b[10], ibtn_b12, b[11], color[j]);
            } else if (i == 2) {
                runFourRow(ibtn_b1, b[0], ibtn_b4, b[3], ibtn_b7, b[6], ibtn_b10, b[9], ibtn_b2, b[1], ibtn_b5, b[4], ibtn_b8, b[7], ibtn_b11, b[10], ibtn_b3, b[2], ibtn_b6, b[5], ibtn_b9, b[8], ibtn_b12, b[11], color[j]);
            } else if (i == 3) {
                runTwo(ibtn_b9, b[8], ibtn_b11, b[10], color[j]);
            } else if (i == 4) {
                runTwoWays(ibtn_b11, b[10], ibtn_b9, b[8], ibtn_b10, b[9], ibtn_b6, b[5], ibtn_b7, b[6], ibtn_b3, b[2], ibtn_b4, b[3], ibtn_b2, b[1], ibtn_b1, b[0], color[j]);
            } else if (i == 5) {
                runThree(ibtn_b9, b[8], ibtn_b5, b[4], ibtn_b11, b[10], color[j]);
            } else if (i == 6) {
                runTwo(ibtn_b9, b[8], ibtn_b6, b[5], color[j]);
            } else if (i == 7) {
                runThree(ibtn_b9, b[8], ibtn_b11, b[10], ibtn_b8, b[7], color[j]);
            }
        }
    }

    public void randomB8(MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            int i = random.nextInt(8);
            int j = random.nextInt(color.length - 1);
            if (i == 0) {
                runEight(ibtn_b5, b[4], ibtn_b7, b[6], ibtn_b9, b[8], ibtn_b11, b[10], ibtn_b4, b[3], ibtn_b6, b[5], ibtn_b10, b[9], ibtn_b12, b[11], color[j]);
            } else if (i == 1) {
                runFourRow(ibtn_b1, b[0], ibtn_b4, b[3], ibtn_b7, b[6], ibtn_b10, b[9], ibtn_b2, b[1], ibtn_b5, b[4], ibtn_b8, b[7], ibtn_b11, b[10], ibtn_b3, b[2], ibtn_b6, b[5], ibtn_b9, b[8], ibtn_b12, b[11], color[j]);
            } else if (i == 2) {
                runThreeColumns(ibtn_b1, b[0], ibtn_b2, b[1], ibtn_b3, b[2], ibtn_b4, b[3], ibtn_b5, b[4], ibtn_b6, b[5], ibtn_b7, b[6], ibtn_b8, b[7], ibtn_b9, b[8], ibtn_b10, b[9], ibtn_b11, b[10], ibtn_b12, b[11], color[j]);
            } else if (i == 3) {
                runTwo(ibtn_b5, b[4], ibtn_b2, b[1], color[j]);
            } else if (i == 4) {
                runNine(ibtn_b8, b[7], ibtn_b5, b[4], ibtn_b6, b[5], ibtn_b9, b[8], ibtn_b12, b[11], ibtn_b11, b[10], ibtn_b10, b[9], ibtn_b7, b[6], ibtn_b4, b[3], color[j]);
            } else if (i == 5) {
                runFour(ibtn_b5, b[4], ibtn_b9, b[8], ibtn_b7, b[6], ibtn_b11, b[10], color[j]);
            } else if (i == 6) {
                runEight(ibtn_b4, b[3], ibtn_b6, b[5], ibtn_b10, b[9], ibtn_b12, b[11], ibtn_b5, b[4], ibtn_b7, b[6], ibtn_b9, b[8], ibtn_b11, b[10], color[j]);
            } else if (i == 7) {
                runSeven(ibtn_b9, b[8], ibtn_b6, b[5], ibtn_b12, b[11], ibtn_b5, b[4], ibtn_b11, b[10], ibtn_b4, b[3], ibtn_b10, b[9], color[j]);
            }

        }
    }

    public void randomB5(MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            int i = random.nextInt(8);
            int j = random.nextInt(color.length - 1);
            if (i == 0) {
                runEight(ibtn_b2, b[1], ibtn_b4, b[3], ibtn_b6, b[5], ibtn_b8, b[7], ibtn_b1, b[0], ibtn_b3, b[2], ibtn_b7, b[6], ibtn_b9, b[8], color[j]);
            } else if (i == 1) {
                runFourRow(ibtn_b1, b[0], ibtn_b4, b[3], ibtn_b7, b[6], ibtn_b10, b[9], ibtn_b2, b[1], ibtn_b5, b[4], ibtn_b8, b[7], ibtn_b11, b[10], ibtn_b3, b[2], ibtn_b6, b[5], ibtn_b9, b[8], ibtn_b12, b[11], color[j]);
            } else if (i == 2) {
                runThreeColumns(ibtn_b1, b[0], ibtn_b2, b[1], ibtn_b3, b[2], ibtn_b4, b[3], ibtn_b5, b[4], ibtn_b6, b[5], ibtn_b7, b[6], ibtn_b8, b[7], ibtn_b9, b[8], ibtn_b10, b[9], ibtn_b11, b[10], ibtn_b12, b[11], color[j]);
            } else if (i == 3) {
                runTwo(ibtn_b8, b[7], ibtn_b11, b[10], color[j]);
            } else if (i == 4) {
                runNine(ibtn_b5, b[4], ibtn_b2, b[1], ibtn_b3, b[2], ibtn_b6, b[5], ibtn_b9, b[8], ibtn_b8, b[7], ibtn_b7, b[6], ibtn_b4, b[3], ibtn_b1, b[0], color[j]);
            } else if (i == 5) {
                runFour(ibtn_b2, b[1], ibtn_b6, b[5], ibtn_b4, b[3], ibtn_b8, b[7], color[j]);
            } else if (i == 6) {
                runEight(ibtn_b1, b[0], ibtn_b3, b[2], ibtn_b7, b[6], ibtn_b9, b[8], ibtn_b2, b[1], ibtn_b4, b[3], ibtn_b6, b[5], ibtn_b8, b[7], color[j]);
            } else if (i == 7) {
                runSeven(ibtn_b6, b[5], ibtn_b3, b[2], ibtn_b9, b[8], ibtn_b2, b[1], ibtn_b8, b[7], ibtn_b1, b[0], ibtn_b7, b[6], color[j]);
            }

        }
    }

    public void randomB2(MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            int i = random.nextInt(8);
            int j = random.nextInt(color.length - 1);
            {
                if (i == 0) {
                    runFourRow(ibtn_b1, b[0], ibtn_b4, b[3], ibtn_b7, b[6], ibtn_b10, b[9], ibtn_b2, b[1], ibtn_b5, b[4], ibtn_b8, b[7], ibtn_b11, b[10], ibtn_b3, b[2], ibtn_b6, b[5], ibtn_b9, b[8], ibtn_b12, b[11], color[j]);
                } else if (i == 1) {
                    runThreeColumns(ibtn_b1, b[0], ibtn_b2, b[1], ibtn_b3, b[2], ibtn_b4, b[3], ibtn_b5, b[4], ibtn_b6, b[5], ibtn_b7, b[6], ibtn_b8, b[7], ibtn_b9, b[8], ibtn_b10, b[9], ibtn_b11, b[10], ibtn_b12, b[11], color[j]);
                } else if (i == 2) {
                    runNine(ibtn_b2, b[1], ibtn_b5, b[4], ibtn_b6, b[5], ibtn_b9, b[8], ibtn_b8, b[7], ibtn_b7, b[6], ibtn_b10, b[9], ibtn_b11, b[10], ibtn_b12, b[11], color[j]);
                } else if (i == 3) {
                    runNine(ibtn_b3, b[2], ibtn_b6, b[5], ibtn_b9, b[8], ibtn_b12, b[11], ibtn_b11, b[10], ibtn_b10, b[9], ibtn_b7, b[6], ibtn_b4, b[3], ibtn_b1, b[0], color[j]);
                } else if (i == 4) {
                    runTwo(ibtn_b5, b[4], ibtn_b8, b[7], color[j]);
                } else if (i == 5) {
                    runThree(ibtn_b1, b[0], ibtn_b3, b[2], ibtn_b5, b[4], color[j]);
                } else if (i == 6) {
                    runTwoWays(ibtn_b1, b[0], ibtn_b3, b[2], ibtn_b4, b[3], ibtn_b6, b[5], ibtn_b7, b[6], ibtn_b9, b[8], ibtn_b10, b[9], ibtn_b12, b[11], ibtn_b11, b[10], color[j]);
                } else if (i == 7) {
                    runThree(ibtn_b7, b[6], ibtn_b5, b[4], ibtn_b9, b[8], color[j]);
                }
            }
        }
    }

    public void randomB11(MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            int i = random.nextInt(8);
            int j = random.nextInt(color.length - 1);
            {
                if (i == 0) {
                    runFourRow(ibtn_b1, b[0], ibtn_b4, b[3], ibtn_b7, b[6], ibtn_b10, b[9], ibtn_b2, b[1], ibtn_b5, b[4], ibtn_b8, b[7], ibtn_b11, b[10], ibtn_b3, b[2], ibtn_b6, b[5], ibtn_b9, b[8], ibtn_b12, b[11], color[j]);
                } else if (i == 1) {
                    runThreeColumns(ibtn_b1, b[0], ibtn_b2, b[1], ibtn_b3, b[2], ibtn_b4, b[3], ibtn_b5, b[4], ibtn_b6, b[5], ibtn_b7, b[6], ibtn_b8, b[7], ibtn_b9, b[8], ibtn_b10, b[9], ibtn_b11, b[10], ibtn_b12, b[11], color[j]);
                } else if (i == 2) {
                    runNine(ibtn_b11, b[10], ibtn_b8, b[7], ibtn_b7, b[6], ibtn_b4, b[3], ibtn_b5, b[4], ibtn_b6, b[5], ibtn_b3, b[2], ibtn_b2, b[1], ibtn_b1, b[0], color[j]);
                } else if (i == 3) {
                    runNine(ibtn_b10, b[9], ibtn_b7, b[6], ibtn_b4, b[3], ibtn_b1, b[0], ibtn_b2, b[1], ibtn_b3, b[2], ibtn_b6, b[5], ibtn_b9, b[8], ibtn_b12, b[11], color[j]);
                } else if (i == 4) {
                    runTwo(ibtn_b5, b[4], ibtn_b8, b[7], color[j]);
                } else if (i == 5) {
                    runThree(ibtn_b10, b[9], ibtn_b8, b[7], ibtn_b12, b[11], color[j]);
                } else if (i == 6) {
                    runTwoWays(ibtn_b10, b[9], ibtn_b12, b[11], ibtn_b7, b[6], ibtn_b9, b[8], ibtn_b4, b[3], ibtn_b6, b[5], ibtn_b1, b[0], ibtn_b3, b[2], ibtn_b2, b[1], color[j]);
                } else if (i == 7) {
                    runThree(ibtn_b4, b[3], ibtn_b8, b[7], ibtn_b6, b[5], color[j]);
                }
            }
        }
    }

    public void randomB4(MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            int i = random.nextInt(8);
            int j = random.nextInt(color.length - 1);
            if (i == 0) {
                runFourRow(ibtn_b1, b[0], ibtn_b4, b[3], ibtn_b7, b[6], ibtn_b10, b[9], ibtn_b2, b[1], ibtn_b5, b[4], ibtn_b8, b[7], ibtn_b11, b[10], ibtn_b3, b[2], ibtn_b6, b[5], ibtn_b9, b[8], ibtn_b12, b[11], color[j]);
            } else if (i == 1) {
                runThreeColumns(ibtn_b1, b[0], ibtn_b2, b[1], ibtn_b3, b[2], ibtn_b4, b[3], ibtn_b5, b[4], ibtn_b6, b[5], ibtn_b7, b[6], ibtn_b8, b[7], ibtn_b9, b[8], ibtn_b10, b[9], ibtn_b11, b[10], ibtn_b12, b[11], color[j]);
            } else if (i == 2) {
                runNine(ibtn_b1, b[0], ibtn_b2, b[1], ibtn_b3, b[2], ibtn_b6, b[5], ibtn_b9, b[8], ibtn_b12, b[11], ibtn_b11, b[10], ibtn_b10, b[9], ibtn_b7, b[6], color[j]);
            } else if (i == 3) {
                runNine(ibtn_b4, b[3], ibtn_b5, b[4], ibtn_b2, b[1], ibtn_b3, b[2], ibtn_b6, b[5], ibtn_b9, b[8], ibtn_b12, b[11], ibtn_b11, b[10], ibtn_b10, b[9], color[j]);
            } else if (i == 4) {
                runThree(ibtn_b1, b[0], ibtn_b5, b[4], ibtn_b7, b[6], color[j]);
            } else if (i == 5) {
                runThree(ibtn_b5, b[4], ibtn_b3, b[2], ibtn_b9, b[8], color[j]);
            } else if (i == 6) {
                runNine(ibtn_b4, b[3], ibtn_b5, b[4], ibtn_b6, b[5], ibtn_b9, b[8], ibtn_b8, b[7], ibtn_b7, b[6], ibtn_b10, b[9], ibtn_b11, b[10], ibtn_b12, b[11], color[j]);
            } else if (i == 7) {
                runTwo(ibtn_b5, b[4], ibtn_b6, b[5], color[j]);
            }
        }
    }

    public void randomB6(MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            int i = random.nextInt(8);
            int j = random.nextInt(color.length - 1);
            if (i == 0) {
                runFourRow(ibtn_b1, b[0], ibtn_b4, b[3], ibtn_b7, b[6], ibtn_b10, b[9], ibtn_b2, b[1], ibtn_b5, b[4], ibtn_b8, b[7], ibtn_b11, b[10], ibtn_b3, b[2], ibtn_b6, b[5], ibtn_b9, b[8], ibtn_b12, b[11], color[j]);
            } else if (i == 1) {
                runThreeColumns(ibtn_b1, b[0], ibtn_b2, b[1], ibtn_b3, b[2], ibtn_b4, b[3], ibtn_b5, b[4], ibtn_b6, b[5], ibtn_b7, b[6], ibtn_b8, b[7], ibtn_b9, b[8], ibtn_b10, b[9], ibtn_b11, b[10], ibtn_b12, b[11], color[j]);
            } else if (i == 2) {
                runNine(ibtn_b9, b[8], ibtn_b12, b[11], ibtn_b11, b[10], ibtn_b10, b[9], ibtn_b7, b[6], ibtn_b4, b[3], ibtn_b1, b[0], ibtn_b2, b[1], ibtn_b3, b[2], color[j]);
            } else if (i == 3) {
                runNine(ibtn_b6, b[5], ibtn_b5, b[4], ibtn_b2, b[1], ibtn_b1, b[0], ibtn_b4, b[3], ibtn_b7, b[6], ibtn_b10, b[9], ibtn_b11, b[10], ibtn_b12, b[11], color[j]);
            } else if (i == 4) {
                runThree(ibtn_b1, b[0], ibtn_b5, b[4], ibtn_b7, b[6], color[j]);
            } else if (i == 5) {
                runThree(ibtn_b5, b[4], ibtn_b3, b[2], ibtn_b9, b[8], color[j]);
            } else if (i == 6) {
                runNine(ibtn_b6, b[5], ibtn_b5, b[4], ibtn_b4, b[3], ibtn_b7, b[6], ibtn_b8, b[7], ibtn_b9, b[8], ibtn_b12, b[11], ibtn_b11, b[10], ibtn_b10, b[9], color[j]);
            } else if (i == 7) {
                runTwo(ibtn_b5, b[4], ibtn_b4, b[3], color[j]);
            }
        }
    }

    public void randomB7(MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            int i = random.nextInt(8);
            int j = random.nextInt(color.length - 1);
            if (i == 0) {
                runFourRow(ibtn_b1, b[0], ibtn_b4, b[3], ibtn_b7, b[6], ibtn_b10, b[9], ibtn_b2, b[1], ibtn_b5, b[4], ibtn_b8, b[7], ibtn_b11, b[10], ibtn_b3, b[2], ibtn_b6, b[5], ibtn_b9, b[8], ibtn_b12, b[11], color[j]);
            } else if (i == 1) {
                runThreeColumns(ibtn_b1, b[0], ibtn_b2, b[1], ibtn_b3, b[2], ibtn_b4, b[3], ibtn_b5, b[4], ibtn_b6, b[5], ibtn_b7, b[6], ibtn_b8, b[7], ibtn_b9, b[8], ibtn_b10, b[9], ibtn_b11, b[10], ibtn_b12, b[11], color[j]);
            } else if (i == 2) {
                runThree(ibtn_b6, b[5], ibtn_b8, b[7], ibtn_b12, b[11], color[j]);
            } else if (i == 3) {
                runThree(ibtn_b4, b[3], ibtn_b8, b[7], ibtn_b10, b[9], color[j]);
            } else if (i == 4) {
                runNine(ibtn_b7, b[6], ibtn_b8, b[7], ibtn_b9, b[8], ibtn_b6, b[5], ibtn_b5, b[4], ibtn_b4, b[3], ibtn_b1, b[0], ibtn_b2, b[1], ibtn_b3, b[2], color[j]);
            } else if (i == 5) {
                runNine(ibtn_b4, b[3], ibtn_b1, b[0], ibtn_b2, b[1], ibtn_b3, b[2], ibtn_b6, b[5], ibtn_b9, b[8], ibtn_b12, b[11], ibtn_b11, b[10], ibtn_b10, b[9], color[j]);
            } else if (i == 6) {
                runTwo(ibtn_b4, b[3], ibtn_b1, b[0], color[j]);
            } else if (i == 7) {
                runNine(ibtn_b7, b[6], ibtn_b10, b[9], ibtn_b11, b[10], ibtn_b8, b[7], ibtn_b5, b[4], ibtn_b4, b[3], ibtn_b7, b[6], ibtn_b8, b[7], ibtn_b9, b[8], color[j]);
            }
        }
    }

    public void randomB9(MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            int i = random.nextInt(8);
            int j = random.nextInt(color.length - 1);
            if (i == 0) {
                runFourRow(ibtn_b1, b[0], ibtn_b4, b[3], ibtn_b7, b[6], ibtn_b10, b[9], ibtn_b2, b[1], ibtn_b5, b[4], ibtn_b8, b[7], ibtn_b11, b[10], ibtn_b3, b[2], ibtn_b6, b[5], ibtn_b9, b[8], ibtn_b12, b[11], color[j]);
            } else if (i == 1) {
                runThreeColumns(ibtn_b1, b[0], ibtn_b2, b[1], ibtn_b3, b[2], ibtn_b4, b[3], ibtn_b5, b[4], ibtn_b6, b[5], ibtn_b7, b[6], ibtn_b8, b[7], ibtn_b9, b[8], ibtn_b10, b[9], ibtn_b11, b[10], ibtn_b12, b[11], color[j]);
            } else if (i == 2) {
                runThree(ibtn_b6, b[5], ibtn_b8, b[7], ibtn_b12, b[11], color[j]);
            } else if (i == 3) {
                runThree(ibtn_b4, b[3], ibtn_b8, b[7], ibtn_b10, b[9], color[j]);
            } else if (i == 4) {
                runNine(ibtn_b9, b[8], ibtn_b8, b[7], ibtn_b7, b[6], ibtn_b4, b[3], ibtn_b5, b[4], ibtn_b6, b[5], ibtn_b3, b[2], ibtn_b2, b[1], ibtn_b1, b[0], color[j]);
            } else if (i == 5) {
                runNine(ibtn_b12, b[11], ibtn_b11, b[10], ibtn_b10, b[9], ibtn_b7, b[6], ibtn_b4, b[3], ibtn_b1, b[0], ibtn_b2, b[1], ibtn_b3, b[2], ibtn_b6, b[5], color[j]);
            } else if (i == 6) {
                runTwo(ibtn_b6, b[5], ibtn_b3, b[2], color[j]);
            } else if (i == 7) {
                runNine(ibtn_b9, b[8], ibtn_b6, b[5], ibtn_b5, b[4], ibtn_b8, b[7], ibtn_b11, b[10], ibtn_b12, b[11], ibtn_b9, b[8], ibtn_b8, b[7], ibtn_b7, b[6], color[j]);
            }
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemFeedback:
                Intent intentFeedback = new Intent(Intent.ACTION_SEND);
                intentFeedback.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                intentFeedback.setType("message/rfc822");
                intentFeedback.putExtra(Intent.EXTRA_EMAIL, strings);
                intentFeedback.putExtra(Intent.EXTRA_SUBJECT, "Feedback");
                intentFeedback.setPackage("com.google.android.gm");
                if (intentFeedback.resolveActivity(getPackageManager()) != null)
                    startActivity(intentFeedback);
                else
                    Toast.makeText(this, "Gmail App is not installed", Toast.LENGTH_SHORT).show();
                break;

            case R.id.itemShareApp:
                Intent intentShare = new Intent(Intent.ACTION_SEND);
                intentShare.setType("text/plain");
                intentShare.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                intentShare.putExtra(Intent.EXTRA_TEXT, "http://play.google.com/store/apps/details?id=" + getPackageName());
                startActivity(Intent.createChooser(intentShare, "Share link!"));
                break;

            case R.id.itemRateApp:
                final Dialog dialog = new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.dialog_ratingbar);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                RatingBar ratingBar = dialog.findViewById(R.id.ratingBar);

                ImageButton ibtnRate = dialog.findViewById(R.id.ibtnRate);
                ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                    @Override
                    public void onRatingChanged(RatingBar ratingBar, float numberRating, boolean b) {
                        if (numberRating <= (float) 3.5) {
                            Intent intentFeedback = new Intent(Intent.ACTION_SEND);
                            intentFeedback.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                            intentFeedback.setType("message/rfc822");
                            intentFeedback.putExtra(Intent.EXTRA_EMAIL, strings);
                            intentFeedback.putExtra(Intent.EXTRA_SUBJECT, "Feedback");
                            intentFeedback.setPackage("com.google.android.gm");
                            intentFeedback.putExtra(Intent.EXTRA_TEXT, "Rate app: " + numberRating);
                            if (intentFeedback.resolveActivity(getPackageManager()) != null)
                                startActivity(intentFeedback);
                            else
                                Toast.makeText(MainActivity.this, "Gmail App is not installed", Toast.LENGTH_SHORT).show();
                        } else {
                            try {
                                Uri uri = Uri.parse("market://details?id=" + getPackageName());

                                Intent intentRate = new Intent(Intent.ACTION_VIEW, uri);
                                startActivity(intentRate);

                            } catch (ActivityNotFoundException ex) {
                                Uri uri = Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName());

                                Intent intentRate = new Intent(Intent.ACTION_VIEW, uri);
                                startActivity(intentRate);
                            }
                        }
                    }
                });

                ibtnRate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
                drawerLayout.closeDrawers();
                break;

            case R.id.itemListRecord:
                if (checkPermissionFromDevice()) {
                    if(isMyServiceRunning(MediaServices.class))
                    {
                        Intent intentService = new Intent(MainActivity.this,MediaServices.class);

                        stopService(intentService);
                    }

                    Intent intent = new Intent(MainActivity.this, ListRecord.class);

                    startActivity(intent);
                    overridePendingTransition(R.anim.activity_in_right, R.anim.activity_out_left);
                    finish();

                } else {
                    requestPermission();
                }
                break;

            case R.id.itemPitchadjust:
                Intent intent = new Intent(MainActivity.this, CaoDo.class);

                SharedPreferences.Editor editor = sharedPreferences.edit();

                //put rate a
                editor.putFloat("rate_a1", rate_a1);
                editor.putFloat("rate_a2", rate_a2);
                editor.putFloat("rate_a3", rate_a3);
                editor.putFloat("rate_a4", rate_a4);
                editor.putFloat("rate_a5", rate_a5);
                editor.putFloat("rate_a6", rate_a6);
                editor.putFloat("rate_a7", rate_a7);
                editor.putFloat("rate_a8", rate_a8);
                editor.putFloat("rate_a9", rate_a9);
                editor.putFloat("rate_a10", rate_a10);
                editor.putFloat("rate_a11", rate_a11);
                editor.putFloat("rate_a12", rate_a12);

                //put rate b
                editor.putFloat("rate_b1", rate_b1);
                editor.putFloat("rate_b2", rate_b2);
                editor.putFloat("rate_b3", rate_b3);
                editor.putFloat("rate_b4", rate_b4);
                editor.putFloat("rate_b5", rate_b5);
                editor.putFloat("rate_b6", rate_b6);
                editor.putFloat("rate_b7", rate_b7);
                editor.putFloat("rate_b8", rate_b8);
                editor.putFloat("rate_b9", rate_b9);
                editor.putFloat("rate_b10", rate_b10);
                editor.putFloat("rate_b11", rate_b11);
                editor.putFloat("rate_b12", rate_b12);

                editor.commit();

                startActivity(intent);
                overridePendingTransition(R.anim.activity_in_right, R.anim.activity_out_left);
                finish();
                showInterstitial();

                break;

            case R.id.itemRemoveAds:
                removeAds();
                break;
        }
        return true;
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(event.getAction() == KeyEvent.ACTION_DOWN)
        {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                if(!drawerLayout.isDrawerOpen(GravityCompat.START))
                {
                    Log.e("aaaaaaaaaaaaaaaaaa","ccccccccccccccccc");
                    final Dialog dialog = new Dialog(MainActivity.this);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.setContentView(R.layout.custom_dialog_out);

                    TextView txtYes, txtNo;

                    txtYes = dialog.findViewById(R.id.txtYes);
                    txtNo = dialog.findViewById(R.id.txtNo);

                    txtYes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();

                            Intent intent = new Intent(MainActivity.this,GoodbyeActivity.class);

                            startActivity(intent);
                            showInterstitial();

                            finish();

                            overridePendingTransition(R.anim.activity_in_right,R.anim.activity_out_left);
                        }
                    });

                    txtNo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });



                    dialog.show();

                }
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private boolean checkPermissionFromDevice() {
        int write_external_storage_result = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int read_external_storage_result = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int record_audio_result = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.RECORD_AUDIO);
        int modify_audio_result = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.MODIFY_AUDIO_SETTINGS);
        return write_external_storage_result == PackageManager.PERMISSION_GRANTED &&
                read_external_storage_result == PackageManager.PERMISSION_GRANTED &&
                record_audio_result == PackageManager.PERMISSION_GRANTED &&
                modify_audio_result == PackageManager.PERMISSION_GRANTED;
    }

    public boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onRewardedVideoAdLoaded() {
        Log.e("aaaaaaaaaaaaaaa","aaaaaa");
        Toast.makeText(this, "cccccccccccccccccc", Toast.LENGTH_SHORT).show();
}

    @Override
    public void onRewardedVideoAdOpened() {

    }

    @Override
    public void onRewardedVideoStarted() {
        checkAds = 1;
    }

    @Override
    public void onRewardedVideoAdClosed() {
        if (checkAds != 2){
            Toast.makeText(this, "Why are you not watching full ad", Toast.LENGTH_LONG).show();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            Log.e("aaaaaaaaaaaaaaaaaaaaa",before+"");
            editor.putInt("soundpackage",before);
            editor.commit();

            llbackground.setVisibility(View.GONE);
            loadSoundPool();

        } else {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("timecurrent"+soundPackage, (int) (System.currentTimeMillis()/1000));
            editor.putInt("soundpackage",soundPackage);

            soundPackageDAO.updateSoundPackage(soundPackage,"yes");

            editor.commit();
            llbackground.setVisibility(View.GONE);

            loadSoundPool();
        }

        loadRewardedVideoAd();
    }

    @Override
    public void onRewarded(RewardItem rewardItem) {
        checkAds = 2;
    }

    @Override
    public void onRewardedVideoAdLeftApplication() {

    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {
        Toast.makeText(this, "cannot load video", Toast.LENGTH_SHORT).show();
        loadRewardedVideoAd();
    }

    @Override
    public void onRewardedVideoCompleted() {

    }

    private void loadRewardedVideoAd() {
        mRewardedVideoAd.loadAd("ca-app-pub-3940256099942544/5224354917",
                new AdRequest.Builder().build());
    }

    @Override
    public void onResume() {
        super.onResume();

        if(isMyServiceRunning(MediaServices.class))
        {
            ibtnMusic.setImageResource(R.drawable.music_pause);
        }
        else
        {
            ibtnMusic.setImageResource(R.drawable.ic_music);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (bp != null) {
            bp.release();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!bp.handleActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void removeAds() {
        if (readyToPurchase) {
            bp.subscribe(this, Constant.ID_REMOVE_ADS);
        } else {
            Toast.makeText(this, R.string.billing_not_initialized, Toast.LENGTH_SHORT).show();
        }
    }

    private void checkRemoveAds() {
        try {
            if (bp.isSubscribed(Constant.ID_REMOVE_ADS)) {
                MySetting.putRemoveAds(this, true);
            } else {
                MySetting.putRemoveAds(this, false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onProductPurchased(String productId, TransactionDetails details) {
        Toast.makeText(this, R.string.thank_you_purchased, Toast.LENGTH_SHORT).show();
        checkRemoveAds();
    }

    @Override
    public void onPurchaseHistoryRestored() {

    }

    @Override
    public void onBillingError(int errorCode, Throwable error) {
        Toast.makeText(this, R.string.purchase_cancel, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBillingInitialized() {
        checkRemoveAds();
        readyToPurchase = true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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

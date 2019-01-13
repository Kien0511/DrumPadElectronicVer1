package com.example.nguyentrungkien.drumpadelectronic;


import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaRecorder;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nguyentrungkien.drumpadelectronic.Adapter.LoopAdapter;
import com.example.nguyentrungkien.drumpadelectronic.DAO.PitchDAO;
import com.example.nguyentrungkien.drumpadelectronic.DTO.LoopDTO;
import com.example.nguyentrungkien.drumpadelectronic.DTO.PresetDTO;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;


public class LoopActivity extends AppCompatActivity implements View.OnClickListener,View.OnTouchListener {
    private RecyclerView rcvLoop1, rcvLoop2, rcvLoop3, rcvLoop4, rcvLoop5, rcvLoop6, rcvLoop7, rcvLoop8, rcvLoop9, rcvLoop10, rcvLoop11, rcvLoop12;
    private RecyclerView rcvLoop13, rcvLoop14, rcvLoop15, rcvLoop16, rcvLoop17, rcvLoop18, rcvLoop19, rcvLoop20, rcvLoop21, rcvLoop22, rcvLoop23, rcvLoop24;
    private ImageButton ibtn_a1, ibtn_a2, ibtn_a3, ibtn_a4, ibtn_a5, ibtn_a6, ibtn_a7, ibtn_a8, ibtn_a9, ibtn_a10, ibtn_a11, ibtn_a12;
    private ImageButton ibtn_b1, ibtn_b2, ibtn_b3, ibtn_b4, ibtn_b5, ibtn_b6, ibtn_b7, ibtn_b8, ibtn_b9, ibtn_b10, ibtn_b11, ibtn_b12;
    private List<LoopDTO> list;
    private ImageButton ibtnBack;
    private AudioAttributes attributes;
    private SoundPool sp;
    private boolean loaded = false;
    private int sounda1, sounda2, sounda3, sounda4, sounda5, sounda6, sounda7, sounda8, sounda9, sounda10, sounda11, sounda12;
    private int soundb1, soundb2, soundb3, soundb4, soundb5, soundb6, soundb7, soundb8, soundb9, soundb10, soundb11, soundb12;
    LoopAdapter adapter1, adapter2, adapter3, adapter4, adapter5, adapter6, adapter7, adapter8;
    LoopAdapter adapter9, adapter10, adapter11, adapter12, adapter13, adapter14, adapter15, adapter16;
    LoopAdapter adapter17, adapter18, adapter19, adapter20, adapter21, adapter22, adapter23, adapter24;
    private int soundPackage = 0;
    SharedPreferences sharedPreferences;
    private float rate_a1,rate_a2,rate_a3,rate_a4,rate_a5,rate_a6,rate_a7,rate_a8,rate_a9,rate_a10,rate_a11,rate_a12;
    private float rate_b1,rate_b2,rate_b3,rate_b4,rate_b5,rate_b6,rate_b7,rate_b8,rate_b9,rate_b10,rate_b11,rate_b12;
    private int[] color,a,b;
    private Random random;
    private ImageButton ibtnDelete, ibtnRecord;
    private boolean checkRecord = false;
    final int REQUEST_PERMISSION_CODE = 1000;
    private int seconds = 0, minute = 0, hour = 0;
    private String timeFM;
    private String outputFile;
    private String presetName = "default";
    private PitchDAO pitchDAO;

    public static TextView txtCurrentProgress;
    public static SeekBar skbAuto;

    Handler handler;
    private File directory;
    private MediaRecorder recorder;
    private PresetDTO presetDTO;

    private int recordState = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_loop);



        a = new int[]{R.drawable.blue_click, R.drawable.green_click, R.drawable.purple_click, R.drawable.red_click, R.drawable.yellow_click, R.drawable.purple_click, R.drawable.green_click, R.drawable.purple_click, R.drawable.purple_click, R.drawable.yellow_click, R.drawable.purple_click, R.drawable.red_click};
        b = new int[]{R.drawable.blue_click, R.drawable.green_click, R.drawable.purple_click, R.drawable.red_click, R.drawable.yellow_click, R.drawable.purple_click, R.drawable.green_click, R.drawable.purple_click, R.drawable.purple_click, R.drawable.yellow_click, R.drawable.purple_click, R.drawable.red_click};

        color = new int[]{R.drawable.color_glow_1, R.drawable.color_glow_2, R.drawable.color_glow_3, R.drawable.color_glow_4, R.drawable.color_glow_5};
        random = new Random();
        txtCurrentProgress = findViewById(R.id.txtCurrentProgress);
        skbAuto = findViewById(R.id.skbAuto);

        txtCurrentProgress.setText(""+(skbAuto.getProgress()+10));
        pitchDAO = new PitchDAO(LoopActivity.this);
        pitchDAO.open();

        ibtnDelete = findViewById(R.id.ibtnDelete);
        ibtnRecord = findViewById(R.id.ibtnRecord);


        rcvLoop1 = findViewById(R.id.rcvLoop1);
        rcvLoop2 = findViewById(R.id.rcvLoop2);
        rcvLoop3 = findViewById(R.id.rcvLoop3);
        rcvLoop4 = findViewById(R.id.rcvLoop4);
        rcvLoop5 = findViewById(R.id.rcvLoop5);
        rcvLoop6 = findViewById(R.id.rcvLoop6);
        rcvLoop7 = findViewById(R.id.rcvLoop7);
        rcvLoop8 = findViewById(R.id.rcvLoop8);
        rcvLoop9 = findViewById(R.id.rcvLoop9);
        rcvLoop10 = findViewById(R.id.rcvLoop10);
        rcvLoop11 = findViewById(R.id.rcvLoop11);
        rcvLoop12 = findViewById(R.id.rcvLoop12);

        rcvLoop13 = findViewById(R.id.rcvLoop13);
        rcvLoop14 = findViewById(R.id.rcvLoop14);
        rcvLoop15 = findViewById(R.id.rcvLoop15);
        rcvLoop16 = findViewById(R.id.rcvLoop16);
        rcvLoop17 = findViewById(R.id.rcvLoop17);
        rcvLoop18 = findViewById(R.id.rcvLoop18);
        rcvLoop19 = findViewById(R.id.rcvLoop19);
        rcvLoop20 = findViewById(R.id.rcvLoop20);
        rcvLoop21 = findViewById(R.id.rcvLoop21);
        rcvLoop22 = findViewById(R.id.rcvLoop22);
        rcvLoop23 = findViewById(R.id.rcvLoop23);
        rcvLoop24 = findViewById(R.id.rcvLoop24);

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

        ibtnBack = findViewById(R.id.ibtnBack);

        ibtn_a1.setTag(R.drawable.blue_click);
        ibtn_a2.setTag(R.drawable.green_click);
        ibtn_a3.setTag(R.drawable.purple_click);
        ibtn_a4.setTag(R.drawable.red_click);
        ibtn_a5.setTag(R.drawable.yellow_click);
        ibtn_a6.setTag(R.drawable.purple_click);
        ibtn_a7.setTag(R.drawable.green_click);
        ibtn_a8.setTag(R.drawable.purple_click);
        ibtn_a9.setTag(R.drawable.purple_click);
        ibtn_a10.setTag(R.drawable.yellow_click);
        ibtn_a11.setTag(R.drawable.purple_click);
        ibtn_a12.setTag(R.drawable.red_click);

        ibtn_b1.setTag(R.drawable.blue_click);
        ibtn_b2.setTag(R.drawable.green_click);
        ibtn_b3.setTag(R.drawable.purple_click);
        ibtn_b4.setTag(R.drawable.red_click);
        ibtn_b5.setTag(R.drawable.yellow_click);
        ibtn_b6.setTag(R.drawable.purple_click);
        ibtn_b7.setTag(R.drawable.green_click);
        ibtn_b8.setTag(R.drawable.purple_click);
        ibtn_b9.setTag(R.drawable.purple_click);
        ibtn_b10.setTag(R.drawable.yellow_click);
        ibtn_b11.setTag(R.drawable.purple_click);
        ibtn_b12.setTag(R.drawable.red_click);

        sharedPreferences = LoopActivity.this.getSharedPreferences("DrumPadRate", MODE_PRIVATE);

        presetName = sharedPreferences.getString("preset", "default");

        soundPackage = sharedPreferences.getInt("soundpackage", 0);
        list = new ArrayList<>();

        list.add(new LoopDTO(R.drawable.base));


        setOnClickAndOnTouch();

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
                loaded = true;
            }
        });

        loadSoundPool(sp);

        ibtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (adapter1 != null) {
                    adapter1.removeHandler();
                    adapter2.removeHandler();
                    adapter3.removeHandler();
                    adapter4.removeHandler();
                    adapter5.removeHandler();
                    adapter6.removeHandler();
                    adapter7.removeHandler();
                    adapter8.removeHandler();
                    adapter9.removeHandler();
                    adapter10.removeHandler();
                    adapter11.removeHandler();
                    adapter12.removeHandler();

                    adapter13.removeHandler();
                    adapter14.removeHandler();
                    adapter15.removeHandler();
                    adapter16.removeHandler();
                    adapter17.removeHandler();
                    adapter18.removeHandler();
                    adapter19.removeHandler();
                    adapter20.removeHandler();
                    adapter21.removeHandler();
                    adapter22.removeHandler();
                    adapter23.removeHandler();
                    adapter24.removeHandler();
                }

                if(recordState == 1)
                {
                    recordState = 0;
                    recorder.stop();
                    recorder.release();
                    Toast.makeText(LoopActivity.this, "Record success", Toast.LENGTH_SHORT).show();
                }


                finish();
                overridePendingTransition(R.anim.activity_in_left, R.anim.activity_out_right);
            }
        });

        setAdapter();

        skbAuto.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                handler = new Handler();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        txtCurrentProgress.setText((skbAuto.getProgress()+10)+"");
                    }
                });
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        ibtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list = new ArrayList<>();

                list.add(new LoopDTO(R.drawable.base));

                adapter1.setList(list);
                adapter2.setList(list);
                adapter3.setList(list);
                adapter4.setList(list);
                adapter5.setList(list);
                adapter6.setList(list);
                adapter7.setList(list);
                adapter8.setList(list);
                adapter9.setList(list);
                adapter10.setList(list);
                adapter11.setList(list);
                adapter12.setList(list);
                adapter13.setList(list);
                adapter14.setList(list);
                adapter15.setList(list);
                adapter16.setList(list);
                adapter17.setList(list);
                adapter18.setList(list);
                adapter19.setList(list);
                adapter20.setList(list);
                adapter21.setList(list);
                adapter22.setList(list);
                adapter23.setList(list);
                adapter24.setList(list);

                ibtnDelete.setImageResource(R.drawable.delete_click);
                Handler handler = new Handler();

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ibtnDelete.setImageResource(R.drawable.delete);
                    }
                }, 200);
            }
        });

        ibtnRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (checkPermissionFromDevice()) {
                    checkRecord = !checkRecord;
                    if (checkRecord) {
                        recordState = 1;
                        seconds = minute = hour = 0;
                        ibtnRecord.setImageResource(R.drawable.recording);
                        
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
                            Toast.makeText(LoopActivity.this, "Recording", Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {

                        recordState = 0;
                        ibtnRecord.setImageResource(R.drawable.record);
                        recorder.stop();
                        recorder.release();
                        Toast.makeText(LoopActivity.this, "Record success", Toast.LENGTH_SHORT).show();

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

    public void setOnClickAndOnTouch() {
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

    public void setAdapter() {
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        LinearLayoutManager layoutManager3 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        LinearLayoutManager layoutManager4 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        LinearLayoutManager layoutManager5 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        LinearLayoutManager layoutManager6 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        LinearLayoutManager layoutManager7 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        LinearLayoutManager layoutManager8 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        LinearLayoutManager layoutManager9 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        LinearLayoutManager layoutManager10 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        LinearLayoutManager layoutManager11 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        LinearLayoutManager layoutManager12 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        LinearLayoutManager layoutManager13 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        LinearLayoutManager layoutManager14 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        LinearLayoutManager layoutManager15 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        LinearLayoutManager layoutManager16 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        LinearLayoutManager layoutManager17 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        LinearLayoutManager layoutManager18 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        LinearLayoutManager layoutManager19 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        LinearLayoutManager layoutManager20 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        LinearLayoutManager layoutManager21 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        LinearLayoutManager layoutManager22 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        LinearLayoutManager layoutManager23 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        LinearLayoutManager layoutManager24 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        rcvLoop1.setLayoutManager(layoutManager1);
        rcvLoop2.setLayoutManager(layoutManager2);
        rcvLoop3.setLayoutManager(layoutManager3);
        rcvLoop4.setLayoutManager(layoutManager4);
        rcvLoop5.setLayoutManager(layoutManager5);
        rcvLoop6.setLayoutManager(layoutManager6);
        rcvLoop7.setLayoutManager(layoutManager7);
        rcvLoop8.setLayoutManager(layoutManager8);
        rcvLoop9.setLayoutManager(layoutManager9);
        rcvLoop10.setLayoutManager(layoutManager10);
        rcvLoop11.setLayoutManager(layoutManager11);
        rcvLoop12.setLayoutManager(layoutManager12);

        rcvLoop13.setLayoutManager(layoutManager13);
        rcvLoop14.setLayoutManager(layoutManager14);
        rcvLoop15.setLayoutManager(layoutManager15);
        rcvLoop16.setLayoutManager(layoutManager16);
        rcvLoop17.setLayoutManager(layoutManager17);
        rcvLoop18.setLayoutManager(layoutManager18);
        rcvLoop19.setLayoutManager(layoutManager19);
        rcvLoop20.setLayoutManager(layoutManager20);
        rcvLoop21.setLayoutManager(layoutManager21);
        rcvLoop22.setLayoutManager(layoutManager22);
        rcvLoop23.setLayoutManager(layoutManager23);
        rcvLoop24.setLayoutManager(layoutManager24);

        adapter1 = new LoopAdapter(this, list, sp, sounda1,ibtn_a1,R.drawable.blue_looped_click,R.drawable.blue_click);
        adapter2 = new LoopAdapter(this, list, sp, sounda2,ibtn_a2,R.drawable.green_looped_click,R.drawable.green_click);
        adapter3 = new LoopAdapter(this, list, sp, sounda3,ibtn_a3,R.drawable.purple_looped_click,R.drawable.purple_click);
        adapter4 = new LoopAdapter(this, list, sp, sounda4,ibtn_a4,R.drawable.red_looped_click,R.drawable.red_click);
        adapter5 = new LoopAdapter(this, list, sp, sounda5,ibtn_a5,R.drawable.yellow_looped_click,R.drawable.yellow_click);
        adapter6 = new LoopAdapter(this, list, sp, sounda6,ibtn_a6,R.drawable.purple_looped_click,R.drawable.purple_click);
        adapter7 = new LoopAdapter(this, list, sp, sounda7,ibtn_a7,R.drawable.green_looped_click,R.drawable.green_click);
        adapter8 = new LoopAdapter(this, list, sp, sounda8,ibtn_a8,R.drawable.purple_looped_click,R.drawable.purple_click);
        adapter9 = new LoopAdapter(this, list, sp, sounda9,ibtn_a9,R.drawable.purple_looped_click,R.drawable.purple_click);
        adapter10 = new LoopAdapter(this, list, sp, sounda10,ibtn_a10,R.drawable.yellow_looped_click,R.drawable.yellow_click);
        adapter11 = new LoopAdapter(this, list, sp, sounda11,ibtn_a11,R.drawable.purple_looped_click,R.drawable.purple_click);
        adapter12 = new LoopAdapter(this, list, sp, sounda12,ibtn_a12,R.drawable.red_looped_click,R.drawable.red_click);

        adapter13 = new LoopAdapter(this, list, sp, soundb1,ibtn_b1,R.drawable.blue_looped_click,R.drawable.blue_click);
        adapter14 = new LoopAdapter(this, list, sp, soundb2,ibtn_b2,R.drawable.green_looped_click,R.drawable.green_click);
        adapter15 = new LoopAdapter(this, list, sp, soundb3,ibtn_b3,R.drawable.purple_looped_click,R.drawable.purple_click);
        adapter16 = new LoopAdapter(this, list, sp, soundb4,ibtn_b4,R.drawable.red_looped_click,R.drawable.red_click);
        adapter17 = new LoopAdapter(this, list, sp, soundb5,ibtn_b5,R.drawable.yellow_looped_click,R.drawable.yellow_click);
        adapter18 = new LoopAdapter(this, list, sp, soundb6,ibtn_b6,R.drawable.purple_looped_click,R.drawable.purple_click);
        adapter19 = new LoopAdapter(this, list, sp, soundb7,ibtn_b7,R.drawable.green_looped_click,R.drawable.green_click);
        adapter20 = new LoopAdapter(this, list, sp, soundb8,ibtn_b8,R.drawable.purple_looped_click,R.drawable.purple_click);
        adapter21 = new LoopAdapter(this, list, sp, soundb9,ibtn_b9,R.drawable.purple_looped_click,R.drawable.purple_click);
        adapter22 = new LoopAdapter(this, list, sp, soundb10,ibtn_b10,R.drawable.yellow_looped_click,R.drawable.yellow_click);
        adapter23 = new LoopAdapter(this, list, sp, soundb11,ibtn_b11,R.drawable.purple_looped_click,R.drawable.purple_click);
        adapter24 = new LoopAdapter(this, list, sp, soundb12,ibtn_b12,R.drawable.red_looped_click,R.drawable.red_click);

        rcvLoop1.setAdapter(adapter1);
        rcvLoop2.setAdapter(adapter2);
        rcvLoop3.setAdapter(adapter3);
        rcvLoop4.setAdapter(adapter4);
        rcvLoop5.setAdapter(adapter5);
        rcvLoop6.setAdapter(adapter6);
        rcvLoop7.setAdapter(adapter7);
        rcvLoop8.setAdapter(adapter8);
        rcvLoop9.setAdapter(adapter9);
        rcvLoop10.setAdapter(adapter10);
        rcvLoop11.setAdapter(adapter11);
        rcvLoop12.setAdapter(adapter12);

        rcvLoop13.setAdapter(adapter13);
        rcvLoop14.setAdapter(adapter14);
        rcvLoop15.setAdapter(adapter15);
        rcvLoop16.setAdapter(adapter16);
        rcvLoop17.setAdapter(adapter17);
        rcvLoop18.setAdapter(adapter18);
        rcvLoop19.setAdapter(adapter19);
        rcvLoop20.setAdapter(adapter20);
        rcvLoop21.setAdapter(adapter21);
        rcvLoop22.setAdapter(adapter22);
        rcvLoop23.setAdapter(adapter23);
        rcvLoop24.setAdapter(adapter24);
    }

    public void loadSoundPool(SoundPool sp) {
        if (soundPackage == 0) {
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
        } else if (soundPackage == 1) {
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK)
        {
            if (adapter1 != null) {
                adapter1.removeHandler();
                adapter2.removeHandler();
                adapter3.removeHandler();
                adapter4.removeHandler();
                adapter5.removeHandler();
                adapter6.removeHandler();
                adapter7.removeHandler();
                adapter8.removeHandler();
                adapter9.removeHandler();
                adapter10.removeHandler();
                adapter11.removeHandler();
                adapter12.removeHandler();

                adapter13.removeHandler();
                adapter14.removeHandler();
                adapter15.removeHandler();
                adapter16.removeHandler();
                adapter17.removeHandler();
                adapter18.removeHandler();
                adapter19.removeHandler();
                adapter20.removeHandler();
                adapter21.removeHandler();
                adapter22.removeHandler();
                adapter23.removeHandler();
                adapter24.removeHandler();
            }

            if(recordState == 1)
            {
                recordState = 0;
                recorder.stop();
                recorder.release();
                Toast.makeText(LoopActivity.this, "Record success", Toast.LENGTH_SHORT).show();
            }
            finish();
            overridePendingTransition(R.anim.activity_in_left, R.anim.activity_out_right);
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (view.getId()) {
            case R.id.ibtn_a1:
                setRcv(rcvLoop1,rcvLoop2,rcvLoop3,rcvLoop4,rcvLoop5,rcvLoop6,rcvLoop7,rcvLoop8,
                        rcvLoop9,rcvLoop10,rcvLoop11,rcvLoop12,rcvLoop13,rcvLoop14,rcvLoop15,rcvLoop16,
                        rcvLoop17,rcvLoop18,rcvLoop19,rcvLoop20,rcvLoop21,rcvLoop22,rcvLoop23,rcvLoop24);

                setLoopClick(motionEvent,ibtn_a1,(int) R.drawable.blue_looped_click,(int) R.drawable.blue_paused_click,R.drawable.blue_click,sounda1,rate_a1);
                setImageRes(ibtn_a1,ibtn_a2,ibtn_a3,ibtn_a4,ibtn_a5,ibtn_a6,ibtn_a7,ibtn_a8,ibtn_a9,ibtn_a10,ibtn_a11,ibtn_a12,
                        ibtn_b1,ibtn_b2,ibtn_b3,ibtn_b4,ibtn_b5,ibtn_b6,ibtn_b7,ibtn_b8,ibtn_b9,ibtn_b10,ibtn_b11,ibtn_b12,R.drawable.border_white);

                break;
            case R.id.ibtn_a2:
                setRcv(rcvLoop2,rcvLoop1,rcvLoop3,rcvLoop4,rcvLoop5,rcvLoop6,rcvLoop7,rcvLoop8,
                        rcvLoop9,rcvLoop10,rcvLoop11,rcvLoop12,rcvLoop13,rcvLoop14,rcvLoop15,rcvLoop16,
                        rcvLoop17,rcvLoop18,rcvLoop19,rcvLoop20,rcvLoop21,rcvLoop22,rcvLoop23,rcvLoop24);
                setImageRes(ibtn_a2,ibtn_a1,ibtn_a3,ibtn_a4,ibtn_a5,ibtn_a6,ibtn_a7,ibtn_a8,ibtn_a9,ibtn_a10,ibtn_a11,ibtn_a12,
                        ibtn_b1,ibtn_b2,ibtn_b3,ibtn_b4,ibtn_b5,ibtn_b6,ibtn_b7,ibtn_b8,ibtn_b9,ibtn_b10,ibtn_b11,ibtn_b12,R.drawable.border_white);
                setLoopClick(motionEvent,ibtn_a2,(int) R.drawable.green_looped_click,(int) R.drawable.green_paused_click,R.drawable.green_click,sounda2,rate_a2);

                break;
            case R.id.ibtn_a3:
                setRcv(rcvLoop3,rcvLoop2,rcvLoop1,rcvLoop4,rcvLoop5,rcvLoop6,rcvLoop7,rcvLoop8,
                        rcvLoop9,rcvLoop10,rcvLoop11,rcvLoop12,rcvLoop13,rcvLoop14,rcvLoop15,rcvLoop16,
                        rcvLoop17,rcvLoop18,rcvLoop19,rcvLoop20,rcvLoop21,rcvLoop22,rcvLoop23,rcvLoop24);
                setImageRes(ibtn_a3,ibtn_a2,ibtn_a1,ibtn_a4,ibtn_a5,ibtn_a6,ibtn_a7,ibtn_a8,ibtn_a9,ibtn_a10,ibtn_a11,ibtn_a12,
                        ibtn_b1,ibtn_b2,ibtn_b3,ibtn_b4,ibtn_b5,ibtn_b6,ibtn_b7,ibtn_b8,ibtn_b9,ibtn_b10,ibtn_b11,ibtn_b12,R.drawable.border_white);
                setLoopClick(motionEvent,ibtn_a3,(int) R.drawable.purple_looped_click,(int) R.drawable.purple_paused_click,R.drawable.purple_click,sounda3,rate_a3);


                break;
            case R.id.ibtn_a4:
                setRcv(rcvLoop4,rcvLoop2,rcvLoop3,rcvLoop1,rcvLoop5,rcvLoop6,rcvLoop7,rcvLoop8,
                        rcvLoop9,rcvLoop10,rcvLoop11,rcvLoop12,rcvLoop13,rcvLoop14,rcvLoop15,rcvLoop16,
                        rcvLoop17,rcvLoop18,rcvLoop19,rcvLoop20,rcvLoop21,rcvLoop22,rcvLoop23,rcvLoop24);
                setLoopClick(motionEvent,ibtn_a4,(int) R.drawable.red_looped_click,(int) R.drawable.red_paused_click,R.drawable.red_click,sounda4,rate_a4);

                setImageRes(ibtn_a4,ibtn_a2,ibtn_a3,ibtn_a1,ibtn_a5,ibtn_a6,ibtn_a7,ibtn_a8,ibtn_a9,ibtn_a10,ibtn_a11,ibtn_a12,
                        ibtn_b1,ibtn_b2,ibtn_b3,ibtn_b4,ibtn_b5,ibtn_b6,ibtn_b7,ibtn_b8,ibtn_b9,ibtn_b10,ibtn_b11,ibtn_b12,R.drawable.border_white);
                break;
            case R.id.ibtn_a5:
                setRcv(rcvLoop5,rcvLoop2,rcvLoop3,rcvLoop4,rcvLoop1,rcvLoop6,rcvLoop7,rcvLoop8,
                        rcvLoop9,rcvLoop10,rcvLoop11,rcvLoop12,rcvLoop13,rcvLoop14,rcvLoop15,rcvLoop16,
                        rcvLoop17,rcvLoop18,rcvLoop19,rcvLoop20,rcvLoop21,rcvLoop22,rcvLoop23,rcvLoop24);

                setImageRes(ibtn_a5,ibtn_a2,ibtn_a3,ibtn_a4,ibtn_a1,ibtn_a6,ibtn_a7,ibtn_a8,ibtn_a9,ibtn_a10,ibtn_a11,ibtn_a12,
                        ibtn_b1,ibtn_b2,ibtn_b3,ibtn_b4,ibtn_b5,ibtn_b6,ibtn_b7,ibtn_b8,ibtn_b9,ibtn_b10,ibtn_b11,ibtn_b12,R.drawable.border_white);
                setLoopClick(motionEvent,ibtn_a5,(int) R.drawable.yellow_looped_click,(int) R.drawable.yellow_paused_click,R.drawable.yellow_click,sounda5,rate_a5);


                break;
            case R.id.ibtn_a6:
                setRcv(rcvLoop6,rcvLoop2,rcvLoop3,rcvLoop4,rcvLoop5,rcvLoop1,rcvLoop7,rcvLoop8,
                        rcvLoop9,rcvLoop10,rcvLoop11,rcvLoop12,rcvLoop13,rcvLoop14,rcvLoop15,rcvLoop16,
                        rcvLoop17,rcvLoop18,rcvLoop19,rcvLoop20,rcvLoop21,rcvLoop22,rcvLoop23,rcvLoop24);
                setImageRes(ibtn_a6,ibtn_a2,ibtn_a3,ibtn_a4,ibtn_a5,ibtn_a1,ibtn_a7,ibtn_a8,ibtn_a9,ibtn_a10,ibtn_a11,ibtn_a12,
                        ibtn_b1,ibtn_b2,ibtn_b3,ibtn_b4,ibtn_b5,ibtn_b6,ibtn_b7,ibtn_b8,ibtn_b9,ibtn_b10,ibtn_b11,ibtn_b12,R.drawable.border_white);
                setLoopClick(motionEvent,ibtn_a6,(int) R.drawable.purple_looped_click,(int) R.drawable.purple_paused_click,R.drawable.purple_click,sounda6,rate_a6);


                break;
            case R.id.ibtn_a7:
                setRcv(rcvLoop7,rcvLoop2,rcvLoop3,rcvLoop4,rcvLoop5,rcvLoop6,rcvLoop1,rcvLoop8,
                        rcvLoop9,rcvLoop10,rcvLoop11,rcvLoop12,rcvLoop13,rcvLoop14,rcvLoop15,rcvLoop16,
                        rcvLoop17,rcvLoop18,rcvLoop19,rcvLoop20,rcvLoop21,rcvLoop22,rcvLoop23,rcvLoop24);

                setImageRes(ibtn_a7,ibtn_a2,ibtn_a3,ibtn_a4,ibtn_a5,ibtn_a6,ibtn_a1,ibtn_a8,ibtn_a9,ibtn_a10,ibtn_a11,ibtn_a12,
                        ibtn_b1,ibtn_b2,ibtn_b3,ibtn_b4,ibtn_b5,ibtn_b6,ibtn_b7,ibtn_b8,ibtn_b9,ibtn_b10,ibtn_b11,ibtn_b12,R.drawable.border_white);
                setLoopClick(motionEvent,ibtn_a7,(int) R.drawable.green_looped_click,(int) R.drawable.green_paused_click,R.drawable.green_click,sounda7,rate_a7);


                break;
            case R.id.ibtn_a8:
                setRcv(rcvLoop8,rcvLoop2,rcvLoop3,rcvLoop4,rcvLoop5,rcvLoop6,rcvLoop7,rcvLoop1,
                        rcvLoop9,rcvLoop10,rcvLoop11,rcvLoop12,rcvLoop13,rcvLoop14,rcvLoop15,rcvLoop16,
                        rcvLoop17,rcvLoop18,rcvLoop19,rcvLoop20,rcvLoop21,rcvLoop22,rcvLoop23,rcvLoop24);

                setImageRes(ibtn_a8,ibtn_a2,ibtn_a3,ibtn_a4,ibtn_a5,ibtn_a6,ibtn_a7,ibtn_a1,ibtn_a9,ibtn_a10,ibtn_a11,ibtn_a12,
                        ibtn_b1,ibtn_b2,ibtn_b3,ibtn_b4,ibtn_b5,ibtn_b6,ibtn_b7,ibtn_b8,ibtn_b9,ibtn_b10,ibtn_b11,ibtn_b12,R.drawable.border_white);
                setLoopClick(motionEvent,ibtn_a8,(int) R.drawable.purple_looped_click,(int) R.drawable.purple_paused_click,R.drawable.purple_click,sounda8,rate_a8);


                break;
            case R.id.ibtn_a9:
                setRcv(rcvLoop9,rcvLoop2,rcvLoop3,rcvLoop4,rcvLoop5,rcvLoop6,rcvLoop7,rcvLoop8,
                        rcvLoop1,rcvLoop10,rcvLoop11,rcvLoop12,rcvLoop13,rcvLoop14,rcvLoop15,rcvLoop16,
                        rcvLoop17,rcvLoop18,rcvLoop19,rcvLoop20,rcvLoop21,rcvLoop22,rcvLoop23,rcvLoop24);

                setImageRes(ibtn_a9,ibtn_a2,ibtn_a3,ibtn_a4,ibtn_a5,ibtn_a6,ibtn_a7,ibtn_a8,ibtn_a1,ibtn_a10,ibtn_a11,ibtn_a12,
                        ibtn_b1,ibtn_b2,ibtn_b3,ibtn_b4,ibtn_b5,ibtn_b6,ibtn_b7,ibtn_b8,ibtn_b9,ibtn_b10,ibtn_b11,ibtn_b12,R.drawable.border_white);
                setLoopClick(motionEvent,ibtn_a9,(int) R.drawable.purple_looped_click,(int) R.drawable.purple_paused_click,R.drawable.purple_click,sounda9,rate_a9);


                break;
            case R.id.ibtn_a10:
                setRcv(rcvLoop10,rcvLoop2,rcvLoop3,rcvLoop4,rcvLoop5,rcvLoop6,rcvLoop7,rcvLoop8,
                        rcvLoop9,rcvLoop1,rcvLoop11,rcvLoop12,rcvLoop13,rcvLoop14,rcvLoop15,rcvLoop16,
                        rcvLoop17,rcvLoop18,rcvLoop19,rcvLoop20,rcvLoop21,rcvLoop22,rcvLoop23,rcvLoop24);

                setImageRes(ibtn_a10,ibtn_a2,ibtn_a3,ibtn_a4,ibtn_a5,ibtn_a6,ibtn_a7,ibtn_a8,ibtn_a9,ibtn_a1,ibtn_a11,ibtn_a12,
                        ibtn_b1,ibtn_b2,ibtn_b3,ibtn_b4,ibtn_b5,ibtn_b6,ibtn_b7,ibtn_b8,ibtn_b9,ibtn_b10,ibtn_b11,ibtn_b12,R.drawable.border_white);
                setLoopClick(motionEvent,ibtn_a10,(int) R.drawable.yellow_looped_click,(int) R.drawable.yellow_paused_click,R.drawable.yellow_click,sounda10,rate_a10);

                break;
            case R.id.ibtn_a11:
                setRcv(rcvLoop11,rcvLoop2,rcvLoop3,rcvLoop4,rcvLoop5,rcvLoop6,rcvLoop7,rcvLoop8,
                        rcvLoop9,rcvLoop10,rcvLoop1,rcvLoop12,rcvLoop13,rcvLoop14,rcvLoop15,rcvLoop16,
                        rcvLoop17,rcvLoop18,rcvLoop19,rcvLoop20,rcvLoop21,rcvLoop22,rcvLoop23,rcvLoop24);

                setImageRes(ibtn_a11,ibtn_a2,ibtn_a3,ibtn_a4,ibtn_a5,ibtn_a6,ibtn_a7,ibtn_a8,ibtn_a9,ibtn_a10,ibtn_a1,ibtn_a12,
                        ibtn_b1,ibtn_b2,ibtn_b3,ibtn_b4,ibtn_b5,ibtn_b6,ibtn_b7,ibtn_b8,ibtn_b9,ibtn_b10,ibtn_b11,ibtn_b12,R.drawable.border_white);
                setLoopClick(motionEvent,ibtn_a11,(int) R.drawable.purple_looped_click,(int) R.drawable.purple_paused_click,R.drawable.purple_click,sounda11,rate_a11);


                break;
            case R.id.ibtn_a12:
                setRcv(rcvLoop12,rcvLoop2,rcvLoop3,rcvLoop4,rcvLoop5,rcvLoop6,rcvLoop7,rcvLoop8,
                        rcvLoop9,rcvLoop10,rcvLoop11,rcvLoop1,rcvLoop13,rcvLoop14,rcvLoop15,rcvLoop16,
                        rcvLoop17,rcvLoop18,rcvLoop19,rcvLoop20,rcvLoop21,rcvLoop22,rcvLoop23,rcvLoop24);

                setImageRes(ibtn_a12,ibtn_a2,ibtn_a3,ibtn_a4,ibtn_a5,ibtn_a6,ibtn_a7,ibtn_a8,ibtn_a9,ibtn_a10,ibtn_a11,ibtn_a1,
                        ibtn_b1,ibtn_b2,ibtn_b3,ibtn_b4,ibtn_b5,ibtn_b6,ibtn_b7,ibtn_b8,ibtn_b9,ibtn_b10,ibtn_b11,ibtn_b12,R.drawable.border_white);
                setLoopClick(motionEvent,ibtn_a12,(int) R.drawable.red_looped_click,(int) R.drawable.red_paused_click,R.drawable.red_click,sounda12,rate_a12);

                break;

            case R.id.ibtn_b1:
                setRcv(rcvLoop13,rcvLoop2,rcvLoop3,rcvLoop4,rcvLoop5,rcvLoop6,rcvLoop7,rcvLoop8,
                        rcvLoop9,rcvLoop10,rcvLoop11,rcvLoop12,rcvLoop1,rcvLoop14,rcvLoop15,rcvLoop16,
                        rcvLoop17,rcvLoop18,rcvLoop19,rcvLoop20,rcvLoop21,rcvLoop22,rcvLoop23,rcvLoop24);

                setImageRes(ibtn_b1,ibtn_a2,ibtn_a3,ibtn_a4,ibtn_a5,ibtn_a6,ibtn_a7,ibtn_a8,ibtn_a9,ibtn_a10,ibtn_a11,ibtn_a12,
                        ibtn_a1,ibtn_b2,ibtn_b3,ibtn_b4,ibtn_b5,ibtn_b6,ibtn_b7,ibtn_b8,ibtn_b9,ibtn_b10,ibtn_b11,ibtn_b12,R.drawable.border_white);
                setLoopClick(motionEvent,ibtn_b1,(int) R.drawable.blue_looped_click,(int) R.drawable.blue_paused_click,R.drawable.blue_click,soundb1,rate_b1);

                break;
            case R.id.ibtn_b2:
                setRcv(rcvLoop14,rcvLoop2,rcvLoop3,rcvLoop4,rcvLoop5,rcvLoop6,rcvLoop7,rcvLoop8,
                        rcvLoop9,rcvLoop10,rcvLoop11,rcvLoop12,rcvLoop13,rcvLoop1,rcvLoop15,rcvLoop16,
                        rcvLoop17,rcvLoop18,rcvLoop19,rcvLoop20,rcvLoop21,rcvLoop22,rcvLoop23,rcvLoop24);

                setImageRes(ibtn_b2,ibtn_a2,ibtn_a3,ibtn_a4,ibtn_a5,ibtn_a6,ibtn_a7,ibtn_a8,ibtn_a9,ibtn_a10,ibtn_a11,ibtn_a12,
                        ibtn_b1,ibtn_a1,ibtn_b3,ibtn_b4,ibtn_b5,ibtn_b6,ibtn_b7,ibtn_b8,ibtn_b9,ibtn_b10,ibtn_b11,ibtn_b12,R.drawable.border_white);
                setLoopClick(motionEvent,ibtn_b2,(int) R.drawable.green_looped_click,(int) R.drawable.green_paused_click,R.drawable.green_click,soundb2,rate_b2);


                break;
            case R.id.ibtn_b3:
                setRcv(rcvLoop15,rcvLoop2,rcvLoop3,rcvLoop4,rcvLoop5,rcvLoop6,rcvLoop7,rcvLoop8,
                        rcvLoop9,rcvLoop10,rcvLoop11,rcvLoop12,rcvLoop13,rcvLoop14,rcvLoop1,rcvLoop16,
                        rcvLoop17,rcvLoop18,rcvLoop19,rcvLoop20,rcvLoop21,rcvLoop22,rcvLoop23,rcvLoop24);

                setImageRes(ibtn_b3,ibtn_a2,ibtn_a3,ibtn_a4,ibtn_a5,ibtn_a6,ibtn_a7,ibtn_a8,ibtn_a9,ibtn_a10,ibtn_a11,ibtn_a12,
                        ibtn_b1,ibtn_b2,ibtn_a1,ibtn_b4,ibtn_b5,ibtn_b6,ibtn_b7,ibtn_b8,ibtn_b9,ibtn_b10,ibtn_b11,ibtn_b12,R.drawable.border_white);
                setLoopClick(motionEvent,ibtn_b3,(int) R.drawable.purple_looped_click,(int) R.drawable.purple_paused_click,R.drawable.purple_click,soundb3,rate_b3);


                break;
            case R.id.ibtn_b4:
                setRcv(rcvLoop16,rcvLoop2,rcvLoop3,rcvLoop4,rcvLoop5,rcvLoop6,rcvLoop7,rcvLoop8,
                        rcvLoop9,rcvLoop10,rcvLoop11,rcvLoop12,rcvLoop13,rcvLoop14,rcvLoop15,rcvLoop1,
                        rcvLoop17,rcvLoop18,rcvLoop19,rcvLoop20,rcvLoop21,rcvLoop22,rcvLoop23,rcvLoop24);

                setImageRes(ibtn_b4,ibtn_a2,ibtn_a3,ibtn_a4,ibtn_a5,ibtn_a6,ibtn_a7,ibtn_a8,ibtn_a9,ibtn_a10,ibtn_a11,ibtn_a12,
                        ibtn_b1,ibtn_b2,ibtn_b3,ibtn_a1,ibtn_b5,ibtn_b6,ibtn_b7,ibtn_b8,ibtn_b9,ibtn_b10,ibtn_b11,ibtn_b12,R.drawable.border_white);
                setLoopClick(motionEvent,ibtn_b4,(int) R.drawable.red_looped_click,(int) R.drawable.red_paused_click,R.drawable.red_click,soundb4,rate_b4);


                break;
            case R.id.ibtn_b5:
                setRcv(rcvLoop17,rcvLoop2,rcvLoop3,rcvLoop4,rcvLoop5,rcvLoop6,rcvLoop7,rcvLoop8,
                        rcvLoop9,rcvLoop10,rcvLoop11,rcvLoop12,rcvLoop13,rcvLoop14,rcvLoop15,rcvLoop16,
                        rcvLoop1,rcvLoop18,rcvLoop19,rcvLoop20,rcvLoop21,rcvLoop22,rcvLoop23,rcvLoop24);

                setImageRes(ibtn_b5,ibtn_a2,ibtn_a3,ibtn_a4,ibtn_a5,ibtn_a6,ibtn_a7,ibtn_a8,ibtn_a9,ibtn_a10,ibtn_a11,ibtn_a12,
                        ibtn_b1,ibtn_b2,ibtn_b3,ibtn_b4,ibtn_a1,ibtn_b6,ibtn_b7,ibtn_b8,ibtn_b9,ibtn_b10,ibtn_b11,ibtn_b12,R.drawable.border_white);
                setLoopClick(motionEvent,ibtn_b5,(int) R.drawable.yellow_looped_click,(int) R.drawable.yellow_paused_click,R.drawable.yellow_click,soundb5,rate_b5);


                break;
            case R.id.ibtn_b6:
                setRcv(rcvLoop18,rcvLoop2,rcvLoop3,rcvLoop4,rcvLoop5,rcvLoop6,rcvLoop7,rcvLoop8,
                        rcvLoop9,rcvLoop10,rcvLoop11,rcvLoop12,rcvLoop13,rcvLoop14,rcvLoop15,rcvLoop16,
                        rcvLoop17,rcvLoop1,rcvLoop19,rcvLoop20,rcvLoop21,rcvLoop22,rcvLoop23,rcvLoop24);

                setImageRes(ibtn_b6,ibtn_a2,ibtn_a3,ibtn_a4,ibtn_a5,ibtn_a6,ibtn_a7,ibtn_a8,ibtn_a9,ibtn_a10,ibtn_a11,ibtn_a12,
                        ibtn_b1,ibtn_b2,ibtn_b3,ibtn_b4,ibtn_b5,ibtn_a1,ibtn_b7,ibtn_b8,ibtn_b9,ibtn_b10,ibtn_b11,ibtn_b12,R.drawable.border_white);
                setLoopClick(motionEvent,ibtn_b6,(int) R.drawable.purple_looped_click,(int) R.drawable.purple_paused_click,R.drawable.purple_click,soundb6,rate_b6);


                break;
            case R.id.ibtn_b7:
                setRcv(rcvLoop19,rcvLoop2,rcvLoop3,rcvLoop4,rcvLoop5,rcvLoop6,rcvLoop7,rcvLoop8,
                        rcvLoop9,rcvLoop10,rcvLoop11,rcvLoop12,rcvLoop13,rcvLoop14,rcvLoop15,rcvLoop16,
                        rcvLoop17,rcvLoop18,rcvLoop1,rcvLoop20,rcvLoop21,rcvLoop22,rcvLoop23,rcvLoop24);

                setImageRes(ibtn_b7,ibtn_a2,ibtn_a3,ibtn_a4,ibtn_a5,ibtn_a6,ibtn_a7,ibtn_a8,ibtn_a9,ibtn_a10,ibtn_a11,ibtn_a12,
                        ibtn_b1,ibtn_b2,ibtn_b3,ibtn_b4,ibtn_b5,ibtn_b6,ibtn_a1,ibtn_b8,ibtn_b9,ibtn_b10,ibtn_b11,ibtn_b12,R.drawable.border_white);
                setLoopClick(motionEvent,ibtn_b7,(int) R.drawable.green_looped_click,(int) R.drawable.green_paused_click,R.drawable.green_click,soundb7,rate_b7);


                break;
            case R.id.ibtn_b8:
                setRcv(rcvLoop20,rcvLoop2,rcvLoop3,rcvLoop4,rcvLoop5,rcvLoop6,rcvLoop7,rcvLoop8,
                        rcvLoop9,rcvLoop10,rcvLoop11,rcvLoop12,rcvLoop13,rcvLoop14,rcvLoop15,rcvLoop16,
                        rcvLoop17,rcvLoop18,rcvLoop19,rcvLoop1,rcvLoop21,rcvLoop22,rcvLoop23,rcvLoop24);

                setImageRes(ibtn_b8,ibtn_a2,ibtn_a3,ibtn_a4,ibtn_a5,ibtn_a6,ibtn_a7,ibtn_a8,ibtn_a9,ibtn_a10,ibtn_a11,ibtn_a12,
                        ibtn_b1,ibtn_b2,ibtn_b3,ibtn_b4,ibtn_b5,ibtn_b6,ibtn_b7,ibtn_a1,ibtn_b9,ibtn_b10,ibtn_b11,ibtn_b12,R.drawable.border_white);
                setLoopClick(motionEvent,ibtn_b8,(int) R.drawable.purple_looped_click,(int) R.drawable.purple_paused_click,R.drawable.purple_click,soundb8,rate_b8);


                break;
            case R.id.ibtn_b9:
                setRcv(rcvLoop21,rcvLoop2,rcvLoop3,rcvLoop4,rcvLoop5,rcvLoop6,rcvLoop7,rcvLoop8,
                        rcvLoop9,rcvLoop10,rcvLoop11,rcvLoop12,rcvLoop13,rcvLoop14,rcvLoop15,rcvLoop16,
                        rcvLoop17,rcvLoop18,rcvLoop19,rcvLoop20,rcvLoop1,rcvLoop22,rcvLoop23,rcvLoop24);

                setImageRes(ibtn_b9,ibtn_a2,ibtn_a3,ibtn_a4,ibtn_a5,ibtn_a6,ibtn_a7,ibtn_a8,ibtn_a9,ibtn_a10,ibtn_a11,ibtn_a12,
                        ibtn_b1,ibtn_b2,ibtn_b3,ibtn_b4,ibtn_b5,ibtn_b6,ibtn_b7,ibtn_b8,ibtn_a1,ibtn_b10,ibtn_b11,ibtn_b12,R.drawable.border_white);
                setLoopClick(motionEvent,ibtn_b9,(int) R.drawable.purple_looped_click,(int) R.drawable.purple_paused_click,R.drawable.purple_click,soundb9,rate_b9);


                break;
            case R.id.ibtn_b10:
                setRcv(rcvLoop22,rcvLoop2,rcvLoop3,rcvLoop4,rcvLoop5,rcvLoop6,rcvLoop7,rcvLoop8,
                        rcvLoop9,rcvLoop10,rcvLoop11,rcvLoop12,rcvLoop13,rcvLoop14,rcvLoop15,rcvLoop16,
                        rcvLoop17,rcvLoop18,rcvLoop19,rcvLoop20,rcvLoop21,rcvLoop1,rcvLoop23,rcvLoop24);

                setImageRes(ibtn_b10,ibtn_a2,ibtn_a3,ibtn_a4,ibtn_a5,ibtn_a6,ibtn_a7,ibtn_a8,ibtn_a9,ibtn_a10,ibtn_a11,ibtn_a12,
                        ibtn_b1,ibtn_b2,ibtn_b3,ibtn_b4,ibtn_b5,ibtn_b6,ibtn_b7,ibtn_b8,ibtn_b9,ibtn_a1,ibtn_b11,ibtn_b12,R.drawable.border_white);
                setLoopClick(motionEvent,ibtn_b10,(int) R.drawable.yellow_looped_click,(int) R.drawable.yellow_paused_click,R.drawable.yellow_click,soundb10,rate_b10);


                break;
            case R.id.ibtn_b11:
                setRcv(rcvLoop23,rcvLoop2,rcvLoop3,rcvLoop4,rcvLoop5,rcvLoop6,rcvLoop7,rcvLoop8,
                        rcvLoop9,rcvLoop10,rcvLoop11,rcvLoop12,rcvLoop13,rcvLoop14,rcvLoop15,rcvLoop16,
                        rcvLoop17,rcvLoop18,rcvLoop19,rcvLoop20,rcvLoop21,rcvLoop22,rcvLoop1,rcvLoop24);

                setImageRes(ibtn_b11,ibtn_a2,ibtn_a3,ibtn_a4,ibtn_a5,ibtn_a6,ibtn_a7,ibtn_a8,ibtn_a9,ibtn_a10,ibtn_a11,ibtn_a12,
                        ibtn_b1,ibtn_b2,ibtn_b3,ibtn_b4,ibtn_b5,ibtn_b6,ibtn_b7,ibtn_b8,ibtn_b9,ibtn_b10,ibtn_a1,ibtn_b12,R.drawable.border_white);
                setLoopClick(motionEvent,ibtn_b11,(int) R.drawable.purple_looped_click,(int) R.drawable.purple_paused_click,R.drawable.purple_click,soundb11,rate_b11);


                break;
            case R.id.ibtn_b12:
                setRcv(rcvLoop24,rcvLoop2,rcvLoop3,rcvLoop4,rcvLoop5,rcvLoop6,rcvLoop7,rcvLoop8,
                        rcvLoop9,rcvLoop10,rcvLoop11,rcvLoop12,rcvLoop13,rcvLoop14,rcvLoop15,rcvLoop16,
                        rcvLoop17,rcvLoop18,rcvLoop19,rcvLoop20,rcvLoop21,rcvLoop22,rcvLoop23,rcvLoop1);

                setImageRes(ibtn_b12,ibtn_a2,ibtn_a3,ibtn_a4,ibtn_a5,ibtn_a6,ibtn_a7,ibtn_a8,ibtn_a9,ibtn_a10,ibtn_a11,ibtn_a12,
                        ibtn_b1,ibtn_b2,ibtn_b3,ibtn_b4,ibtn_b5,ibtn_b6,ibtn_b7,ibtn_b8,ibtn_b9,ibtn_b10,ibtn_b11,ibtn_a1,R.drawable.border_white);
                setLoopClick(motionEvent,ibtn_b12,(int) R.drawable.red_looped_click,(int) R.drawable.red_paused_click,R.drawable.red_click,soundb12,rate_b12);


                break;


        }
        return false;
    }

    public void playSoundsPool(MotionEvent motionEvent, int soundId, float rate) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            if (loaded) {
                sp.play(soundId, 1, 1, 0, 0, rate);
            }
        }
    }

    public void loadRate()
    {
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
    }

    public void setImageRes(ImageButton im1,ImageButton im2,ImageButton im3,ImageButton im4,ImageButton im5,ImageButton im6,
                            ImageButton im7,ImageButton im8,ImageButton im9,ImageButton im10,ImageButton im11,ImageButton im12,
                            ImageButton im13,ImageButton im14,ImageButton im15,ImageButton im16,ImageButton im17,ImageButton im18,
                            ImageButton im19,ImageButton im20,ImageButton im21,ImageButton im22,ImageButton im23,ImageButton im24,
                            int img)
    {
        im1.setImageResource(img);
        im2.setImageResource(android.R.color.transparent);
        im3.setImageResource(android.R.color.transparent);
        im4.setImageResource(android.R.color.transparent);
        im5.setImageResource(android.R.color.transparent);
        im6.setImageResource(android.R.color.transparent);
        im7.setImageResource(android.R.color.transparent);
        im8.setImageResource(android.R.color.transparent);
        im9.setImageResource(android.R.color.transparent);
        im10.setImageResource(android.R.color.transparent);
        im11.setImageResource(android.R.color.transparent);
        im12.setImageResource(android.R.color.transparent);
        im13.setImageResource(android.R.color.transparent);
        im14.setImageResource(android.R.color.transparent);
        im15.setImageResource(android.R.color.transparent);
        im16.setImageResource(android.R.color.transparent);
        im17.setImageResource(android.R.color.transparent);
        im18.setImageResource(android.R.color.transparent);
        im19.setImageResource(android.R.color.transparent);
        im20.setImageResource(android.R.color.transparent);
        im21.setImageResource(android.R.color.transparent);
        im22.setImageResource(android.R.color.transparent);
        im23.setImageResource(android.R.color.transparent);
        im24.setImageResource(android.R.color.transparent);
    }

    public void setRcv(RecyclerView rcv1,RecyclerView rcv2,RecyclerView rcv3,RecyclerView rcv4,
                       RecyclerView rcv5,RecyclerView rcv6,RecyclerView rcv7,RecyclerView rcv8,
                       RecyclerView rcv9,RecyclerView rcv10,RecyclerView rcv11,RecyclerView rcv12,
                       RecyclerView rcv13,RecyclerView rcv14,RecyclerView rcv15,RecyclerView rcv16,
                       RecyclerView rcv17,RecyclerView rcv18,RecyclerView rcv19,RecyclerView rcv20,
                       RecyclerView rcv21,RecyclerView rcv22,RecyclerView rcv23,RecyclerView rcv24)
    {
        rcv1.setVisibility(View.VISIBLE);
        rcv2.setVisibility(View.INVISIBLE);
        rcv3.setVisibility(View.INVISIBLE);
        rcv4.setVisibility(View.INVISIBLE);
        rcv5.setVisibility(View.INVISIBLE);
        rcv6.setVisibility(View.INVISIBLE);
        rcv7.setVisibility(View.INVISIBLE);
        rcv8.setVisibility(View.INVISIBLE);
        rcv9.setVisibility(View.INVISIBLE);
        rcv10.setVisibility(View.INVISIBLE);
        rcv11.setVisibility(View.INVISIBLE);
        rcv12.setVisibility(View.INVISIBLE);

        rcv13.setVisibility(View.INVISIBLE);
        rcv14.setVisibility(View.INVISIBLE);
        rcv15.setVisibility(View.INVISIBLE);
        rcv16.setVisibility(View.INVISIBLE);
        rcv17.setVisibility(View.INVISIBLE);
        rcv18.setVisibility(View.INVISIBLE);
        rcv19.setVisibility(View.INVISIBLE);
        rcv20.setVisibility(View.INVISIBLE);
        rcv21.setVisibility(View.INVISIBLE);
        rcv22.setVisibility(View.INVISIBLE);
        rcv23.setVisibility(View.INVISIBLE);
        rcv24.setVisibility(View.INVISIBLE);
    }

    public void setLoopClick(MotionEvent motionEvent, ImageButton ibtn,int tagPause, int tagPlay, int resNormal, int soundId, float rate)
    {
        if(motionEvent.getAction() == MotionEvent.ACTION_DOWN)
        {
            if((int) ibtn.getTag() == tagPause)
            {
                Log.e("aaaaaaaa","TagPause");
                ibtn.setBackgroundResource(tagPlay);
                ibtn.setTag(tagPlay);
            }
            else if((int) ibtn.getTag() == tagPlay)
            {
                Log.e("aaaaaaaa","TagPlay");
                playSoundsPool(motionEvent, soundId, rate);
                ibtn.setBackgroundResource(tagPause);
                ibtn.setTag(tagPause);

            }
            else
            {
                Log.e("aaaaaaaa","TagNormal");

                playSoundsPool(motionEvent, soundId, rate);
                ibtn.setTag(resNormal);
            }

        }
    }

    private boolean checkPermissionFromDevice() {
        int write_external_storage_result = ContextCompat.checkSelfPermission(LoopActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int read_external_storage_result = ContextCompat.checkSelfPermission(LoopActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int record_audio_result = ContextCompat.checkSelfPermission(LoopActivity.this, Manifest.permission.RECORD_AUDIO);
        int modify_audio_result = ContextCompat.checkSelfPermission(LoopActivity.this, Manifest.permission.MODIFY_AUDIO_SETTINGS);
        return write_external_storage_result == PackageManager.PERMISSION_GRANTED &&
                read_external_storage_result == PackageManager.PERMISSION_GRANTED &&
                record_audio_result == PackageManager.PERMISSION_GRANTED &&
                modify_audio_result == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(LoopActivity.this, new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.MODIFY_AUDIO_SETTINGS
        }, REQUEST_PERMISSION_CODE);
    }
}

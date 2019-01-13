package com.example.nguyentrungkien.drumpadelectronic;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.nguyentrungkien.drumpadelectronic.DAO.PitchDAO;
import com.example.nguyentrungkien.drumpadelectronic.DTO.PitchDTO;

import java.util.ArrayList;
import java.util.List;

public class CaoDo extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener {
    private ImageButton ibtn_a1, ibtn_a2, ibtn_a3, ibtn_a4, ibtn_a5, ibtn_a6, ibtn_a7, ibtn_a8, ibtn_a9, ibtn_a10, ibtn_a11, ibtn_a12;
    private ImageButton ibtn_b1, ibtn_b2, ibtn_b3, ibtn_b4, ibtn_b5, ibtn_b6, ibtn_b7, ibtn_b8, ibtn_b9, ibtn_b10, ibtn_b11, ibtn_b12;
    private ViewFlipper vfp;
    private boolean check = false;
    private MediaPlayer mp;
    private SoundPool sp;
    private int sounda1, sounda2, sounda3, sounda4, sounda5, sounda6, sounda7, sounda8, sounda9, sounda10, sounda11, sounda12;
    private int soundb1, soundb2, soundb3, soundb4, soundb5, soundb6, soundb7, soundb8, soundb9, soundb10, soundb11, soundb12;
    private AudioAttributes attributes;
    private boolean loaded;
    private int id;
    private float rate_a1, rate_a2, rate_a3, rate_a4, rate_a5, rate_a6, rate_a7, rate_a8, rate_a9, rate_a10, rate_a11, rate_a12;
    private float rate_b1, rate_b2, rate_b3, rate_b4, rate_b5, rate_b6, rate_b7, rate_b8, rate_b9, rate_b10, rate_b11, rate_b12;

    private TextView txt_a1, txt_a2, txt_a3, txt_a4, txt_a5, txt_a6, txt_a7, txt_a8, txt_a9, txt_a10, txt_a11, txt_a12;
    private TextView txt_b1, txt_b2, txt_b3, txt_b4, txt_b5, txt_b6, txt_b7, txt_b8, txt_b9, txt_b10, txt_b11, txt_b12;
    private SharedPreferences sharedPreferences;

    private ImageButton ibtnDrumPadA, ibtnDrumPadB, ibtnBack, ibtnSave, ibtnReset;
    private LinearLayout llBehindDialog;

    private PitchDAO pitchDAO;
    private List<PitchDTO> list;

    private Spinner spnPresetName;

    private List<String> listPresetName;

    private ArrayAdapter<String> spinnerAdapter;
    private int soundPackage = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_cao_do);



        pitchDAO = new PitchDAO(this);
        pitchDAO.open();

        list = new ArrayList<>();
        listPresetName = new ArrayList<>();

        list = pitchDAO.GetListPreset();

        sharedPreferences = CaoDo.this.getSharedPreferences("DrumPadRate", MODE_PRIVATE);

        init();

        setPresetNameForSpinner(list);

        Log.e("liaaaaaaa",listPresetName.size()+"");

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

        soundPackage = sharedPreferences.getInt("soundpackage",0);
        loadSoundpool(sp);

        getRate();

        setTextByRate();



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

        ibtnDrumPadA.setTag(R.drawable.ic_pitchadjustment_presseda);
        ibtnDrumPadB.setTag(R.drawable.ic_pitchadjustment_unpressedb);

        ibtnDrumPadA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((int) ibtnDrumPadA.getTag() != R.drawable.ic_pitchadjustment_presseda)
                {
                    ibtnDrumPadB.setImageResource(R.drawable.ic_pitchadjustment_unpressedb);
                    ibtnDrumPadA.setImageResource(R.drawable.ic_pitchadjustment_presseda);

                    ibtnDrumPadA.setTag(R.drawable.ic_pitchadjustment_presseda);
                    ibtnDrumPadB.setTag(R.drawable.ic_pitchadjustment_unpressedb);

                    vfp.setInAnimation(CaoDo.this, R.anim.activity_in_left);
                    vfp.setOutAnimation(CaoDo.this, R.anim.activity_out_right);
                    vfp.showPrevious();
                }

            }
        });

        ibtnDrumPadB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((int) ibtnDrumPadB.getTag() != R.drawable.ic_pitchadjustment_pressedb) {
                    ibtnDrumPadB.setImageResource(R.drawable.ic_pitchadjustment_pressedb);
                    ibtnDrumPadA.setImageResource(R.drawable.ic_pitchadjustment_unpresseda);

                    ibtnDrumPadA.setTag(R.drawable.ic_pitchadjustment_unpresseda);
                    ibtnDrumPadB.setTag(R.drawable.ic_pitchadjustment_pressedb);
                    vfp.setInAnimation(CaoDo.this, R.anim.activity_in_right);
                    vfp.setOutAnimation(CaoDo.this, R.anim.activity_out_left);
                    vfp.showNext();
                }
            }
        });

        ibtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkBack())
                {
                    showBackDialog();

                }
                else
                {
                    Intent intent = new Intent(CaoDo.this,MainActivity.class);
                    PutSharedPreference();

                    startActivity(intent);
                    overridePendingTransition(R.anim.activity_in_left,R.anim.activity_out_right);
                    finish();
                }
            }
        });

        ibtnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showResetDialog();
            }
        });

        ibtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSaveDialog();
            }
        });

        spnPresetName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                PitchDTO pitchDTO = pitchDAO.CurrentPitch(spnPresetName.getItemAtPosition(i).toString());

                txt_a1.setText(pitchDTO.getPicht_a1());
                txt_a2.setText(pitchDTO.getPicht_a2());
                txt_a3.setText(pitchDTO.getPicht_a3());
                txt_a4.setText(pitchDTO.getPicht_a4());
                txt_a5.setText(pitchDTO.getPicht_a5());
                txt_a6.setText(pitchDTO.getPicht_a6());
                txt_a7.setText(pitchDTO.getPicht_a7());
                txt_a8.setText(pitchDTO.getPicht_a8());
                txt_a9.setText(pitchDTO.getPicht_a9());
                txt_a10.setText(pitchDTO.getPicht_a10());
                txt_a11.setText(pitchDTO.getPicht_a11());
                txt_a12.setText(pitchDTO.getPicht_a12());

                txt_b1.setText(pitchDTO.getPicht_b1());
                txt_b2.setText(pitchDTO.getPicht_b2());
                txt_b3.setText(pitchDTO.getPicht_b3());
                txt_b4.setText(pitchDTO.getPicht_b4());
                txt_b5.setText(pitchDTO.getPicht_b5());
                txt_b6.setText(pitchDTO.getPicht_b6());
                txt_b7.setText(pitchDTO.getPicht_b7());
                txt_b8.setText(pitchDTO.getPicht_b8());
                txt_b9.setText(pitchDTO.getPicht_b9());
                txt_b10.setText(pitchDTO.getPicht_b10());
                txt_b11.setText(pitchDTO.getPicht_b11());
                txt_b12.setText(pitchDTO.getPicht_b12());

                PutSharedPreference();
                checkBack();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void setTextByRate()
    {
        //set text a
        txt_a1.setText(setText(rate_a1));
        txt_a2.setText(setText(rate_a2));
        txt_a3.setText(setText(rate_a3));
        txt_a4.setText(setText(rate_a4));
        txt_a5.setText(setText(rate_a5));
        txt_a6.setText(setText(rate_a6));
        txt_a7.setText(setText(rate_a7));
        txt_a8.setText(setText(rate_a8));
        txt_a9.setText(setText(rate_a9));
        txt_a10.setText(setText(rate_a10));
        txt_a11.setText(setText(rate_a11));
        txt_a12.setText(setText(rate_a12));

        //set text b
        txt_b1.setText(setText(rate_b1));
        txt_b2.setText(setText(rate_b2));
        txt_b3.setText(setText(rate_b3));
        txt_b4.setText(setText(rate_b4));
        txt_b5.setText(setText(rate_b5));
        txt_b6.setText(setText(rate_b6));
        txt_b7.setText(setText(rate_b7));
        txt_b8.setText(setText(rate_b8));
        txt_b9.setText(setText(rate_b9));
        txt_b10.setText(setText(rate_b10));
        txt_b11.setText(setText(rate_b11));
        txt_b12.setText(setText(rate_b12));

        PitchDTO pitchDTO = UpdateDefault();

        String name = pitchDAO.GetSpinnerListRecordName(pitchDTO);

        int pos = 0;
        for (int i = 0; i < listPresetName.size(); i++)
        {
            if(listPresetName.get(i).equals(name))
            {
                pos = i;
            }
        }

        spnPresetName.setSelection(pos);
    }

    private void showSaveDialogInBackDialog()
    {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.custom_dialog_save);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        llBehindDialog.setVisibility(View.VISIBLE);

        TextView txtSavePreset, txtCancel;
        final EditText edtPresetName;

        txtSavePreset = dialog.findViewById(R.id.txtSavePreset);
        txtCancel = dialog.findViewById(R.id.txtCancel);
        edtPresetName = dialog.findViewById(R.id.edtPresetName);

        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                dialog.dismiss();
                llBehindDialog.setVisibility(View.GONE);
            }
        });

        txtCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                llBehindDialog.setVisibility(View.GONE);
            }
        });



        txtSavePreset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstText = edtPresetName.getText().toString().substring(0,1);
                String endText = edtPresetName.getText().toString().substring(1,edtPresetName.getText().length());
                String name = firstText.toUpperCase()+endText;

                if(edtPresetName.getText().toString().isEmpty())
                {
                    Toast.makeText(CaoDo.this, "Please import your preset name", Toast.LENGTH_SHORT).show();
                }
                else if(pitchDAO.CheckPresetName(name))
                {
                    if(pitchDAO.CheckAmountRecord())
                    {
                        Toast.makeText(CaoDo.this, "This storage is full. Please choose the name of preset has been exists", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        AddPreset(name);
                        list = pitchDAO.GetListPreset();

                        setPresetNameForSpinner(list);

                        int pos = 0;

                        for (int i = 0; i < listPresetName.size(); i++)
                        {
                            if(listPresetName.get(i).equals(name))
                            {
                                pos = i;
                            }
                        }

                        if(pos > 0)
                        {
                            spnPresetName.setSelection(pos);
                        }
                        PutSharedPreference();
                        checkBack();

                        dialog.dismiss();

                        llBehindDialog.setVisibility(View.GONE);

                        Intent intent = new Intent(CaoDo.this,MainActivity.class);
                        PutSharedPreference();

                        startActivity(intent);
                        overridePendingTransition(R.anim.activity_in_left,R.anim.activity_out_right);
                        finish();
                    }
                }
                else
                {
                    if(name.toLowerCase().equals("default")) {
                        Toast.makeText(CaoDo.this, "Cannot replace default preset", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        UpdatePreset(name);
                        int pos = 0;

                        for (int i = 0; i < listPresetName.size(); i++)
                        {
                            if(listPresetName.get(i).equals(name))
                            {
                                pos = i;
                            }
                        }

                        if(pos > 0)
                        {
                            spnPresetName.setSelection(pos);
                        }
                        PutSharedPreference();
                        checkBack();
                        dialog.dismiss();
                        llBehindDialog.setVisibility(View.GONE);

                        Intent intent = new Intent(CaoDo.this,MainActivity.class);
                        PutSharedPreference();

                        startActivity(intent);
                        overridePendingTransition(R.anim.activity_in_left,R.anim.activity_out_right);
                        finish();
                    }
                }
            }
        });
        dialog.show();
    }
    private void showBackDialog()
    {
        final Dialog dialog = new Dialog(CaoDo.this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.custom_dialog_back);

        TextView txtYes, txtNo;

        txtYes = dialog.findViewById(R.id.txtYes);
        txtNo = dialog.findViewById(R.id.txtNo);

        llBehindDialog.setVisibility(View.VISIBLE);
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                llBehindDialog.setVisibility(View.GONE);
            }
        });


        txtYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                llBehindDialog.setVisibility(View.GONE);
                showSaveDialogInBackDialog();
            }
        });

        txtNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                llBehindDialog.setVisibility(View.GONE);
                Intent intent = new Intent(CaoDo.this,MainActivity.class);
                PutSharedPreference();

                startActivity(intent);
                overridePendingTransition(R.anim.activity_in_left,R.anim.activity_out_right);
                finish();

            }
        });
        dialog.show();
    }
    private void showSaveDialog()
    {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.custom_dialog_save);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        llBehindDialog.setVisibility(View.VISIBLE);

        TextView txtSavePreset, txtCancel;
        final EditText edtPresetName;

        txtSavePreset = dialog.findViewById(R.id.txtSavePreset);
        txtCancel = dialog.findViewById(R.id.txtCancel);
        edtPresetName = dialog.findViewById(R.id.edtPresetName);


        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                dialog.dismiss();
                llBehindDialog.setVisibility(View.GONE);
            }
        });

        txtCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                llBehindDialog.setVisibility(View.GONE);
            }
        });

        txtSavePreset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstText = edtPresetName.getText().toString().substring(0,1);
                String endText = edtPresetName.getText().toString().substring(1,edtPresetName.getText().length());
                String name = firstText.toUpperCase()+endText;

                if(edtPresetName.getText().toString().isEmpty())
                {
                    Toast.makeText(CaoDo.this, "Please import your preset name", Toast.LENGTH_SHORT).show();
                }
                else if(pitchDAO.CheckPresetName(name))
                {
                    if(pitchDAO.CheckAmountRecord())
                    {
                        Toast.makeText(CaoDo.this, "This storage is full. Please choose the name of preset has been exists", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        AddPreset(name);

                        list = pitchDAO.GetListPreset();

                        setPresetNameForSpinner(list);

                        int pos = 0;

                        for (int i = 0; i < listPresetName.size(); i++)
                        {
                            if(listPresetName.get(i).equals(name))
                            {
                                pos = i;
                            }
                        }

                        if(pos > 0)
                        {
                            spnPresetName.setSelection(pos);
                        }
                        PutSharedPreference();
                        checkBack();

                        dialog.dismiss();

                        llBehindDialog.setVisibility(View.GONE);
                    }
                }
                else
                {
                    if(name.toLowerCase().equals("default")) {
                        Toast.makeText(CaoDo.this, "Cannot replace default preset", Toast.LENGTH_SHORT).show();
                    }
                    else 
                    {

                        UpdatePreset(name);
                        int pos = 0;

                        for (int i = 0; i < listPresetName.size(); i++)
                        {
                            if(listPresetName.get(i).equals(name))
                            {
                                pos = i;
                            }
                        }

                        if(pos > 0)
                        {
                            spnPresetName.setSelection(pos);
                        }
                        PutSharedPreference();
                        checkBack();
                        dialog.dismiss();
                        llBehindDialog.setVisibility(View.GONE);
                    }
                }
            }
        });
        dialog.show();
    }
    private void showResetDialog()
    {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.custom_dialog_reset);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        llBehindDialog.setVisibility(View.VISIBLE);
        TextView txtResetAll, txtCancel;

        txtResetAll = dialog.findViewById(R.id.txtResetAll);
        txtCancel = dialog.findViewById(R.id.txtCancel);

        txtResetAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Reset();
                llBehindDialog.setVisibility(View.GONE);
                dialog.dismiss();
            }
        });

        txtCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                llBehindDialog.setVisibility(View.GONE);
                dialog.dismiss();
            }
        });

        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                dialog.dismiss();
                llBehindDialog.setVisibility(View.GONE);
            }
        });
        dialog.show();
    }
    private void Reset()
    {
        txt_a1.setText("0");
        txt_a2.setText("0");
        txt_a3.setText("0");
        txt_a4.setText("0");
        txt_a5.setText("0");
        txt_a6.setText("0");
        txt_a7.setText("0");
        txt_a8.setText("0");
        txt_a9.setText("0");
        txt_a10.setText("0");
        txt_a11.setText("0");
        txt_a12.setText("0");

        txt_b1.setText("0");
        txt_b2.setText("0");
        txt_b3.setText("0");
        txt_b4.setText("0");
        txt_b5.setText("0");
        txt_b6.setText("0");
        txt_b7.setText("0");
        txt_b8.setText("0");
        txt_b9.setText("0");
        txt_b10.setText("0");
        txt_b11.setText("0");
        txt_b12.setText("0");
    }

    private void AddPreset(String name)
    {
        PitchDTO pitchDTO = new PitchDTO();

        pitchDTO.setPicht_name(name);

        pitchDTO.setPicht_a1(txt_a1.getText().toString());
        pitchDTO.setPicht_a2(txt_a2.getText().toString());
        pitchDTO.setPicht_a3(txt_a3.getText().toString());
        pitchDTO.setPicht_a4(txt_a4.getText().toString());
        pitchDTO.setPicht_a5(txt_a5.getText().toString());
        pitchDTO.setPicht_a6(txt_a6.getText().toString());
        pitchDTO.setPicht_a7(txt_a7.getText().toString());
        pitchDTO.setPicht_a8(txt_a8.getText().toString());
        pitchDTO.setPicht_a9(txt_a9.getText().toString());
        pitchDTO.setPicht_a10(txt_a10.getText().toString());
        pitchDTO.setPicht_a11(txt_a11.getText().toString());
        pitchDTO.setPicht_a12(txt_a12.getText().toString());

        pitchDTO.setPicht_b1(txt_b1.getText().toString());
        pitchDTO.setPicht_b2(txt_b2.getText().toString());
        pitchDTO.setPicht_b3(txt_b3.getText().toString());
        pitchDTO.setPicht_b4(txt_b4.getText().toString());
        pitchDTO.setPicht_b5(txt_b5.getText().toString());
        pitchDTO.setPicht_b6(txt_b6.getText().toString());
        pitchDTO.setPicht_b7(txt_b7.getText().toString());
        pitchDTO.setPicht_b8(txt_b8.getText().toString());
        pitchDTO.setPicht_b9(txt_b9.getText().toString());
        pitchDTO.setPicht_b10(txt_b10.getText().toString());
        pitchDTO.setPicht_b11(txt_b11.getText().toString());
        pitchDTO.setPicht_b12(txt_b12.getText().toString());

        boolean check = pitchDAO.AddPreset(pitchDTO);

        Log.e("checcccccccccc",check+"");

        if(check == true)
        {
            Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();
        }
    }

    private void UpdatePreset(String name)
    {
        PitchDTO pitchDTO = new PitchDTO();

        pitchDTO.setPicht_name(name);

        Log.e("ccccccccccc",name);

        pitchDTO.setPicht_a1(txt_a1.getText().toString());
        pitchDTO.setPicht_a2(txt_a2.getText().toString());
        pitchDTO.setPicht_a3(txt_a3.getText().toString());
        pitchDTO.setPicht_a4(txt_a4.getText().toString());
        pitchDTO.setPicht_a5(txt_a5.getText().toString());
        pitchDTO.setPicht_a6(txt_a6.getText().toString());
        pitchDTO.setPicht_a7(txt_a7.getText().toString());
        pitchDTO.setPicht_a8(txt_a8.getText().toString());
        pitchDTO.setPicht_a9(txt_a9.getText().toString());
        pitchDTO.setPicht_a10(txt_a10.getText().toString());
        pitchDTO.setPicht_a11(txt_a11.getText().toString());
        pitchDTO.setPicht_a12(txt_a12.getText().toString());

        pitchDTO.setPicht_b1(txt_b1.getText().toString());
        pitchDTO.setPicht_b2(txt_b2.getText().toString());
        pitchDTO.setPicht_b3(txt_b3.getText().toString());
        pitchDTO.setPicht_b4(txt_b4.getText().toString());
        pitchDTO.setPicht_b5(txt_b5.getText().toString());
        pitchDTO.setPicht_b6(txt_b6.getText().toString());
        pitchDTO.setPicht_b7(txt_b7.getText().toString());
        pitchDTO.setPicht_b8(txt_b8.getText().toString());
        pitchDTO.setPicht_b9(txt_b9.getText().toString());
        pitchDTO.setPicht_b10(txt_b10.getText().toString());
        pitchDTO.setPicht_b11(txt_b11.getText().toString());
        pitchDTO.setPicht_b12(txt_b12.getText().toString());

        boolean check = pitchDAO.UpdatePreset(pitchDTO);

        Log.e("checcccccccccc",check+"");

        if(check == true)
        {
            Toast.makeText(this, "Update Success", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this, "Update Failed", Toast.LENGTH_SHORT).show();
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

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (view.getId()) {
            case R.id.ibtn_a1:
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    if (loaded) {
                        if (Integer.parseInt(txt_a1.getText().toString()) < 10 && Integer.parseInt(txt_a1.getText().toString()) >= -5) {
                            txt_a1.setText((Integer.parseInt(txt_a1.getText().toString()) + 1) + "");
                        } else if (Integer.parseInt(txt_a1.getText().toString()) == 10) {
                            txt_a1.setText("-5");
                        }
                        sp.play(sounda1, 1, 1, 0, 0, setRate(txt_a1.getText().toString()));

                    }
                }
                break;
            case R.id.ibtn_a2:
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    if (loaded) {
                        if (Integer.parseInt(txt_a2.getText().toString()) < 10 && Integer.parseInt(txt_a2.getText().toString()) >= -5) {
                            txt_a2.setText((Integer.parseInt(txt_a2.getText().toString()) + 1) + "");
                        } else if (Integer.parseInt(txt_a2.getText().toString()) == 10) {
                            txt_a2.setText("-5");
                        }
                        sp.play(sounda2, 1, 1, 0, 0, setRate(txt_a2.getText().toString()));

                    }
                }
                break;
            case R.id.ibtn_a3:
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    if (loaded) {
                        if (Integer.parseInt(txt_a3.getText().toString()) < 10 && Integer.parseInt(txt_a3.getText().toString()) >= -5) {
                            txt_a3.setText((Integer.parseInt(txt_a3.getText().toString()) + 1) + "");
                        } else if (Integer.parseInt(txt_a3.getText().toString()) == 10) {
                            txt_a3.setText("-5");
                        }
                        sp.play(sounda3, 1, 1, 0, 0, setRate(txt_a3.getText().toString()));

                    }
                }
                break;
            case R.id.ibtn_a4:
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    if (loaded) {
                        if (Integer.parseInt(txt_a4.getText().toString()) < 10 && Integer.parseInt(txt_a4.getText().toString()) >= -5) {
                            txt_a4.setText((Integer.parseInt(txt_a4.getText().toString()) + 1) + "");
                        } else if (Integer.parseInt(txt_a4.getText().toString()) == 10) {
                            txt_a4.setText("-5");
                        }
                        sp.play(sounda4, 1, 1, 0, 0, setRate(txt_a4.getText().toString()));

                    }
                }
                break;
            case R.id.ibtn_a5:
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    if (loaded) {
                        if (Integer.parseInt(txt_a5.getText().toString()) < 10 && Integer.parseInt(txt_a5.getText().toString()) >= -5) {
                            txt_a5.setText((Integer.parseInt(txt_a5.getText().toString()) + 1) + "");
                        } else if (Integer.parseInt(txt_a5.getText().toString()) == 10) {
                            txt_a5.setText("-5");
                        }
                        sp.play(sounda5, 1, 1, 0, 0, setRate(txt_a5.getText().toString()));

                    }
                }
                break;
            case R.id.ibtn_a6:
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    if (loaded) {
                        if (Integer.parseInt(txt_a6.getText().toString()) < 10 && Integer.parseInt(txt_a6.getText().toString()) >= -5) {
                            txt_a6.setText((Integer.parseInt(txt_a6.getText().toString()) + 1) + "");
                        } else if (Integer.parseInt(txt_a6.getText().toString()) == 10) {
                            txt_a6.setText("-5");
                        }
                        sp.play(sounda6, 1, 1, 0, 0, setRate(txt_a6.getText().toString()));

                    }
                }
                break;
            case R.id.ibtn_a7:
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    if (loaded) {
                        if (Integer.parseInt(txt_a7.getText().toString()) < 10 && Integer.parseInt(txt_a7.getText().toString()) >= -5) {
                            txt_a7.setText((Integer.parseInt(txt_a7.getText().toString()) + 1) + "");
                        } else if (Integer.parseInt(txt_a7.getText().toString()) == 10) {
                            txt_a7.setText("-5");
                        }
                        id = sp.play(sounda7, 1, 1, 0, -1, setRate(txt_a7.getText().toString()));
                        sp.setLoop(id, -1);
                    }
                    Log.e("touchmode", ibtn_a7.isInTouchMode() + "");

                }

                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    sp.stop(id);
                    Log.e("bbbbbbbbbbbb", "bbbbbbbbbbbb");
                }

                break;
            case R.id.ibtn_a8:
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    if (loaded) {
                        if (Integer.parseInt(txt_a8.getText().toString()) < 10 && Integer.parseInt(txt_a8.getText().toString()) >= -5) {
                            txt_a8.setText((Integer.parseInt(txt_a8.getText().toString()) + 1) + "");
                        } else if (Integer.parseInt(txt_a8.getText().toString()) == 10) {
                            txt_a8.setText("-5");
                        }
                        sp.play(sounda8, 1, 1, 0, 0, setRate(txt_a8.getText().toString()));

                    }
                }
                break;
            case R.id.ibtn_a9:
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    if (loaded) {
                        if (Integer.parseInt(txt_a9.getText().toString()) < 10 && Integer.parseInt(txt_a9.getText().toString()) >= -5) {
                            txt_a9.setText((Integer.parseInt(txt_a9.getText().toString()) + 1) + "");
                        } else if (Integer.parseInt(txt_a9.getText().toString()) == 10) {
                            txt_a9.setText("-5");
                        }
                        sp.play(sounda9, 1, 1, 0, 0, setRate(txt_a9.getText().toString()));

                    }
                }
                break;
            case R.id.ibtn_a10:
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    if (loaded) {
                        if (Integer.parseInt(txt_a10.getText().toString()) < 10 && Integer.parseInt(txt_a10.getText().toString()) >= -5) {
                            txt_a10.setText((Integer.parseInt(txt_a10.getText().toString()) + 1) + "");
                        } else if (Integer.parseInt(txt_a10.getText().toString()) == 10) {
                            txt_a10.setText("-5");
                        }
                        sp.play(sounda10, 1, 1, 0, 0, setRate(txt_a10.getText().toString()));

                    }
                }
                break;
            case R.id.ibtn_a11:
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    if (loaded) {
                        if (Integer.parseInt(txt_a11.getText().toString()) < 10 && Integer.parseInt(txt_a11.getText().toString()) >= -5) {
                            txt_a11.setText((Integer.parseInt(txt_a11.getText().toString()) + 1) + "");
                        } else if (Integer.parseInt(txt_a11.getText().toString()) == 10) {
                            txt_a11.setText("-5");
                        }
                        sp.play(sounda11, 1, 1, 0, 0, setRate(txt_a11.getText().toString()));

                    }
                }
                break;
            case R.id.ibtn_a12:
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    if (loaded) {
                        if (Integer.parseInt(txt_a12.getText().toString()) < 10 && Integer.parseInt(txt_a12.getText().toString()) >= -5) {
                            txt_a12.setText((Integer.parseInt(txt_a12.getText().toString()) + 1) + "");
                        } else if (Integer.parseInt(txt_a12.getText().toString()) == 10) {
                            txt_a12.setText("-5");
                        }
                        sp.play(sounda12, 1, 1, 0, 0, setRate(txt_a12.getText().toString()));

                    }
                }
                break;

            case R.id.ibtn_b1:
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    if (loaded) {
                        if (Integer.parseInt(txt_b1.getText().toString()) < 10 && Integer.parseInt(txt_b1.getText().toString()) >= -5) {
                            txt_b1.setText((Integer.parseInt(txt_b1.getText().toString()) + 1) + "");
                        } else if (Integer.parseInt(txt_b1.getText().toString()) == 10) {
                            txt_b1.setText("-5");
                        }
                        sp.play(soundb1, 1, 1, 0, 0, setRate(txt_b1.getText().toString()));

                    }
                }
                break;
            case R.id.ibtn_b2:
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    if (loaded) {
                        if (Integer.parseInt(txt_b2.getText().toString()) < 10 && Integer.parseInt(txt_b2.getText().toString()) >= -5) {
                            txt_b2.setText((Integer.parseInt(txt_b2.getText().toString()) + 1) + "");
                        } else if (Integer.parseInt(txt_b2.getText().toString()) == 10) {
                            txt_b2.setText("-5");
                        }
                        sp.play(soundb2, 1, 1, 0, 0, setRate(txt_b2.getText().toString()));

                    }
                }
                break;
            case R.id.ibtn_b3:
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    if (loaded) {
                        if (Integer.parseInt(txt_b3.getText().toString()) < 10 && Integer.parseInt(txt_b3.getText().toString()) >= -5) {
                            txt_b3.setText((Integer.parseInt(txt_b3.getText().toString()) + 1) + "");
                        } else if (Integer.parseInt(txt_b3.getText().toString()) == 10) {
                            txt_b3.setText("-5");
                        }
                        sp.play(soundb3, 1, 1, 0, 0, setRate(txt_b3.getText().toString()));

                    }
                }
                break;
            case R.id.ibtn_b4:
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    if (loaded) {
                        if (Integer.parseInt(txt_b4.getText().toString()) < 10 && Integer.parseInt(txt_b4.getText().toString()) >= -5) {
                            txt_b4.setText((Integer.parseInt(txt_b4.getText().toString()) + 1) + "");
                        } else if (Integer.parseInt(txt_b4.getText().toString()) == 10) {
                            txt_b4.setText("-5");
                        }
                        sp.play(soundb4, 1, 1, 0, 0, setRate(txt_b4.getText().toString()));

                    }
                }
                break;
            case R.id.ibtn_b5:
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    if (loaded) {
                        if (Integer.parseInt(txt_b5.getText().toString()) < 10 && Integer.parseInt(txt_b5.getText().toString()) >= -5) {
                            txt_b5.setText((Integer.parseInt(txt_b5.getText().toString()) + 1) + "");
                        } else if (Integer.parseInt(txt_b5.getText().toString()) == 10) {
                            txt_b5.setText("-5");
                        }
                        sp.play(soundb5, 1, 1, 0, 0, setRate(txt_b5.getText().toString()));

                    }
                }
                break;
            case R.id.ibtn_b6:
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    if (loaded) {
                        if (Integer.parseInt(txt_b6.getText().toString()) < 10 && Integer.parseInt(txt_b6.getText().toString()) >= -5) {
                            txt_b6.setText((Integer.parseInt(txt_b6.getText().toString()) + 1) + "");
                        } else if (Integer.parseInt(txt_b6.getText().toString()) == 10) {
                            txt_b6.setText("-5");
                        }
                        sp.play(soundb6, 1, 1, 0, 0, setRate(txt_b6.getText().toString()));

                    }
                }
                break;
            case R.id.ibtn_b7:
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    if (loaded) {
                        if (Integer.parseInt(txt_b7.getText().toString()) < 10 && Integer.parseInt(txt_b7.getText().toString()) >= -5) {
                            txt_b7.setText((Integer.parseInt(txt_b7.getText().toString()) + 1) + "");
                        } else if (Integer.parseInt(txt_b7.getText().toString()) == 10) {
                            txt_b7.setText("-5");
                        }

                        id = sp.play(soundb7, 1, 1, 0, -1, setRate(txt_b7.getText().toString()));
                        sp.setLoop(id, -1);
                    }
                    Log.e("touchmode", ibtn_b7.isInTouchMode() + "");

                }

                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    sp.stop(id);
                    Log.e("bbbbbbbbbbbb", "bbbbbbbbbbbb");
                }

                break;
            case R.id.ibtn_b8:
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    if (loaded) {
                        if (Integer.parseInt(txt_b8.getText().toString()) < 10 && Integer.parseInt(txt_b8.getText().toString()) >= -5) {
                            txt_b8.setText((Integer.parseInt(txt_b8.getText().toString()) + 1) + "");
                        } else if (Integer.parseInt(txt_b8.getText().toString()) == 10) {
                            txt_b8.setText("-5");
                        }
                        sp.play(soundb8, 1, 1, 0, 0, setRate(txt_b8.getText().toString()));

                    }
                }
                break;
            case R.id.ibtn_b9:
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    if (loaded) {
                        if (Integer.parseInt(txt_b9.getText().toString()) < 10 && Integer.parseInt(txt_b9.getText().toString()) >= -5) {
                            txt_b9.setText((Integer.parseInt(txt_b9.getText().toString()) + 1) + "");
                        } else if (Integer.parseInt(txt_b9.getText().toString()) == 10) {
                            txt_b9.setText("-5");
                        }
                        sp.play(soundb9, 1, 1, 0, 0, setRate(txt_b9.getText().toString()));

                    }
                }
                break;
            case R.id.ibtn_b10:
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    if (loaded) {
                        if (Integer.parseInt(txt_b10.getText().toString()) < 10 && Integer.parseInt(txt_b10.getText().toString()) >= -5) {
                            txt_b10.setText((Integer.parseInt(txt_b10.getText().toString()) + 1) + "");
                        } else if (Integer.parseInt(txt_b10.getText().toString()) == 10) {
                            txt_b10.setText("-5");
                        }
                        sp.play(soundb10, 1, 1, 0, 0, setRate(txt_b10.getText().toString()));

                    }
                }
                break;
            case R.id.ibtn_b11:
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    if (loaded) {
                        if (Integer.parseInt(txt_b11.getText().toString()) < 10 && Integer.parseInt(txt_b11.getText().toString()) >= -5) {
                            txt_b11.setText((Integer.parseInt(txt_b11.getText().toString()) + 1) + "");
                        } else if (Integer.parseInt(txt_b11.getText().toString()) == 10) {
                            txt_b11.setText("-5");
                        }
                        sp.play(soundb11, 1, 1, 0, 0, setRate(txt_b11.getText().toString()));

                    }
                }
                break;
            case R.id.ibtn_b12:
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    if (loaded) {
                        if (Integer.parseInt(txt_b12.getText().toString()) < 10 && Integer.parseInt(txt_b12.getText().toString()) >= -5) {
                            txt_b12.setText((Integer.parseInt(txt_b12.getText().toString()) + 1) + "");
                        } else if (Integer.parseInt(txt_b12.getText().toString()) == 10) {
                            txt_b12.setText("-5");
                        }
                        sp.play(soundb12, 1, 1, 0, 0, setRate(txt_b12.getText().toString()));

                    }
                }
                break;


        }
        return false;
    }

    private void init()
    {
        spnPresetName = findViewById(R.id.spnPreset);
        ibtnDrumPadA = findViewById(R.id.ibtnDrumPadA);
        vfp = findViewById(R.id.vfp);
        ibtnDrumPadB = findViewById(R.id.ibtnDrumPadB);
        ibtnBack = findViewById(R.id.ibtnBack);
        ibtnSave = findViewById(R.id.ibtnSave);
        ibtnReset = findViewById(R.id.ibtnReset);
        llBehindDialog = findViewById(R.id.llBehindDialog);

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

        //txt_a
        txt_a1 = findViewById(R.id.txta1);
        txt_a2 = findViewById(R.id.txta2);
        txt_a3 = findViewById(R.id.txta3);
        txt_a4 = findViewById(R.id.txta4);
        txt_a5 = findViewById(R.id.txta5);
        txt_a6 = findViewById(R.id.txta6);
        txt_a7 = findViewById(R.id.txta7);
        txt_a8 = findViewById(R.id.txta8);
        txt_a9 = findViewById(R.id.txta9);
        txt_a10 = findViewById(R.id.txta10);
        txt_a11 = findViewById(R.id.txta11);
        txt_a12 = findViewById(R.id.txta12);

        //txtb
        txt_b1 = findViewById(R.id.txtb1);
        txt_b2 = findViewById(R.id.txtb2);
        txt_b3 = findViewById(R.id.txtb3);
        txt_b4 = findViewById(R.id.txtb4);
        txt_b5 = findViewById(R.id.txtb5);
        txt_b6 = findViewById(R.id.txtb6);
        txt_b7 = findViewById(R.id.txtb7);
        txt_b8 = findViewById(R.id.txtb8);
        txt_b9 = findViewById(R.id.txtb9);
        txt_b10 = findViewById(R.id.txtb10);
        txt_b11 = findViewById(R.id.txtb11);
        txt_b12 = findViewById(R.id.txtb12);
    }

    private void loadSoundpool(SoundPool sp)
    {
        if(soundPackage == 0)
        {
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
    private String setText(float x) {
        String text = "";
        if (x == (float) 0.5) {
            text = "-5";
        } else if (x == (float) 0.6) {
            text = "-4";

        } else if (x == (float) 0.7) {
            text = "-3";

        } else if (x == (float) 0.8) {
            text = "-2";

        } else if (x == (float) 0.9) {
            text = "-1";

        } else if (x == (float) 1.0) {
            text = "0";

        } else if (x == (float) 1.1) {
            text = "1";

        } else if (x == (float) 1.2) {
            text = "2";

        } else if (x == (float) 1.3) {
            text = "3";

        } else if (x == (float) 1.4) {
            text = "4";

        } else if (x == (float) 1.5) {
            text = "5";

        } else if (x == (float) 1.6) {
            text = "6";

        } else if (x == (float) 1.7) {
            text = "7";

        } else if (x == (float) 1.8) {
            text = "8";

        } else if (x == (float) 1.9) {
            text = "9";
        } else if (x == (float) 2.0) {
            text = "10";

        }

        return text;
    }

    private void getRate() {
        // rate a
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

        Log.e("a_1", rate_a1 + "");
    }

    private boolean checkBack()
    {
        if(rate_a1 != setRate(txt_a1.getText().toString()) ||
                rate_a2 != setRate(txt_a2.getText().toString()) ||
                rate_a3 != setRate(txt_a3.getText().toString()) ||
                rate_a4 != setRate(txt_a4.getText().toString()) ||
                rate_a5 != setRate(txt_a5.getText().toString()) ||
                rate_a6 != setRate(txt_a6.getText().toString()) ||
                rate_a7 != setRate(txt_a7.getText().toString()) ||
                rate_a8 != setRate(txt_a8.getText().toString()) ||
                rate_a9 != setRate(txt_a9.getText().toString()) ||
                rate_a10 != setRate(txt_a10.getText().toString()) ||
                rate_a11 != setRate(txt_a11.getText().toString()) ||
                rate_a12 != setRate(txt_a12.getText().toString()) ||
                rate_b1 != setRate(txt_b1.getText().toString()) ||
                rate_b2 != setRate(txt_b2.getText().toString()) ||
                rate_b3 != setRate(txt_b3.getText().toString()) ||
                rate_b4 != setRate(txt_b4.getText().toString()) ||
                rate_b5 != setRate(txt_b5.getText().toString()) ||
                rate_b6 != setRate(txt_b6.getText().toString()) ||
                rate_b7 != setRate(txt_b7.getText().toString()) ||
                rate_b8 != setRate(txt_b8.getText().toString()) ||
                rate_b9 != setRate(txt_b9.getText().toString()) ||
                rate_b10 != setRate(txt_b10.getText().toString()) ||
                rate_b11 != setRate(txt_b11.getText().toString()) ||
                rate_b12 != setRate(txt_b12.getText().toString())
                )
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    private float setRate(String text) {
        float rate = 1;

        if (text.equals("-5")) {
            rate = (float) 0.5;
        } else if (text.equals("-4")) {
            rate = (float) 0.6;
        } else if (text.equals("-3")) {
            rate = (float) 0.7;
        } else if (text.equals("-2")) {
            rate = (float) 0.8;
        } else if (text.equals("-1")) {
            rate = (float) 0.9;
        } else if (text.equals("0")) {
            rate = (float) 1.0;
        } else if (text.equals("1")) {
            rate = (float) 1.1;
        } else if (text.equals("2")) {
            rate = (float) 1.2;
        } else if (text.equals("3")) {
            rate = (float) 1.3;
        } else if (text.equals("4")) {
            rate = (float) 1.4;
        } else if (text.equals("5")) {
            rate = (float) 1.5;
        } else if (text.equals("6")) {
            rate = (float) 1.6;
        } else if (text.equals("7")) {
            rate = (float) 1.7;
        } else if (text.equals("8")) {
            rate = (float) 1.8;
        } else if (text.equals("9")) {
            rate = (float) 1.9;
        } else if (text.equals("10")) {
            rate = (float) 2.0;
        }

        return rate;
    }

    private List<String> getPresetNameList(List<PitchDTO> list)
    {
        List<String> listPresetName = new ArrayList<>();

        for (int i = 0; i < list.size(); i++)
        {
            listPresetName.add(list.get(i).getPicht_name());
        }

        return listPresetName;
    }

    private void setPresetNameForSpinner(List<PitchDTO> list)
    {
        listPresetName = getPresetNameList(list);

        spinnerAdapter = new ArrayAdapter<>(this,R.layout.item_record_spinner,listPresetName);

        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);

        spnPresetName.setAdapter(spinnerAdapter);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {


            if(checkBack())
            {
                showBackDialog();

            }
            else
            {
                Intent intent = new Intent(CaoDo.this,MainActivity.class);
                PutSharedPreference();

                startActivity(intent);
                overridePendingTransition(R.anim.activity_in_left,R.anim.activity_out_right);
                finish();
            }
        }
        return super.onKeyDown(keyCode, event);

    }

    private void PutSharedPreference()
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();

        //set rate a
        rate_a1 = setRate(txt_a1.getText().toString());
        rate_a2 = setRate(txt_a2.getText().toString());
        rate_a3 = setRate(txt_a3.getText().toString());
        rate_a4 = setRate(txt_a4.getText().toString());
        rate_a5 = setRate(txt_a5.getText().toString());
        rate_a6 = setRate(txt_a6.getText().toString());
        rate_a7 = setRate(txt_a7.getText().toString());
        rate_a8 = setRate(txt_a8.getText().toString());
        rate_a9 = setRate(txt_a9.getText().toString());
        rate_a10 = setRate(txt_a10.getText().toString());
        rate_a11 = setRate(txt_a11.getText().toString());
        rate_a12 = setRate(txt_a12.getText().toString());


        //set rate b
        rate_b1 = setRate(txt_b1.getText().toString());
        rate_b2 = setRate(txt_b2.getText().toString());
        rate_b3 = setRate(txt_b3.getText().toString());
        rate_b4 = setRate(txt_b4.getText().toString());
        rate_b5 = setRate(txt_b5.getText().toString());
        rate_b6 = setRate(txt_b6.getText().toString());
        rate_b7 = setRate(txt_b7.getText().toString());
        rate_b8 = setRate(txt_b8.getText().toString());
        rate_b9 = setRate(txt_b9.getText().toString());
        rate_b10 = setRate(txt_b10.getText().toString());
        rate_b11 = setRate(txt_b11.getText().toString());
        rate_b12 = setRate(txt_b12.getText().toString());

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

        editor.putString("preset",spnPresetName.getSelectedItem().toString());

        Log.e("rate_a1", rate_a1 + "abc");
        editor.commit();
    }

    private PitchDTO UpdateDefault()
    {
        PitchDTO pitchDTO = new PitchDTO();

        pitchDTO.setPicht_name("default");

        pitchDTO.setPicht_a1(txt_a1.getText().toString());
        pitchDTO.setPicht_a2(txt_a2.getText().toString());
        pitchDTO.setPicht_a3(txt_a3.getText().toString());
        pitchDTO.setPicht_a4(txt_a4.getText().toString());
        pitchDTO.setPicht_a5(txt_a5.getText().toString());
        pitchDTO.setPicht_a6(txt_a6.getText().toString());
        pitchDTO.setPicht_a7(txt_a7.getText().toString());
        pitchDTO.setPicht_a8(txt_a8.getText().toString());
        pitchDTO.setPicht_a9(txt_a9.getText().toString());
        pitchDTO.setPicht_a10(txt_a10.getText().toString());
        pitchDTO.setPicht_a11(txt_a11.getText().toString());
        pitchDTO.setPicht_a12(txt_a12.getText().toString());

        pitchDTO.setPicht_b1(txt_b1.getText().toString());
        pitchDTO.setPicht_b2(txt_b2.getText().toString());
        pitchDTO.setPicht_b3(txt_b3.getText().toString());
        pitchDTO.setPicht_b4(txt_b4.getText().toString());
        pitchDTO.setPicht_b5(txt_b5.getText().toString());
        pitchDTO.setPicht_b6(txt_b6.getText().toString());
        pitchDTO.setPicht_b7(txt_b7.getText().toString());
        pitchDTO.setPicht_b8(txt_b8.getText().toString());
        pitchDTO.setPicht_b9(txt_b9.getText().toString());
        pitchDTO.setPicht_b10(txt_b10.getText().toString());
        pitchDTO.setPicht_b11(txt_b11.getText().toString());
        pitchDTO.setPicht_b12(txt_b12.getText().toString());


        return pitchDTO;
    }



}

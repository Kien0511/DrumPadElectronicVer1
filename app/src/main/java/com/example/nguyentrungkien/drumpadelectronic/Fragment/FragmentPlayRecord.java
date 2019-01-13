package com.example.nguyentrungkien.drumpadelectronic.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.nguyentrungkien.drumpadelectronic.DAO.PitchDAO;
import com.example.nguyentrungkien.drumpadelectronic.DTO.RecordDTO;
import com.example.nguyentrungkien.drumpadelectronic.ListRecord;
import com.example.nguyentrungkien.drumpadelectronic.PlayRecordActivity;
import com.example.nguyentrungkien.drumpadelectronic.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FragmentPlayRecord extends Fragment implements View.OnClickListener {
    File directory;

    private List<RecordDTO> list = new ArrayList<>();
    private TextView txtFileName, txtTimeTotal, txtTimePresent, txtPresetName, txtQuantity;
    private SeekBar skbDrumPad;
    private ImageButton ibtnPlay_Pause, ibtnReplay, ibtnNext, ibtnPrev, ibtnShuffle;
    public static MediaPlayer mediaPlayer;
    private boolean checkReplay = false;
    private PitchDAO pitchDAO;
    Handler handler;
    int i = 0;
    boolean checkShuffle = false;
    int count = 0;
    public static String playName = "";

    PlayRecordActivity activity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_play_record, container, false);

        pitchDAO = new PitchDAO(getContext());
        pitchDAO.open();
        list = PlayRecordActivity.list;
        txtFileName = view.findViewById(R.id.txtFileName);
        txtTimeTotal = view.findViewById(R.id.txtTimeTotal);
        txtTimePresent = view.findViewById(R.id.txtTimePresent);
        skbDrumPad = view.findViewById(R.id.skbDrumPad);
        ibtnPlay_Pause = view.findViewById(R.id.ibtnMusic);
        ibtnReplay = view.findViewById(R.id.ibtnReplay);
        txtPresetName = view.findViewById(R.id.txtPresetName);
        txtQuantity = view.findViewById(R.id.txtQuantity);
        ibtnNext = view.findViewById(R.id.ibtnNext);
        ibtnPrev = view.findViewById(R.id.ibtnPrev);
        ibtnShuffle = view.findViewById(R.id.ibtnShuffle);

        Log.e("ccccccccccc", "cccccccccc");
        PlayAtI(i);
        ibtnPlay_Pause.setOnClickListener(this);
        ibtnReplay.setOnClickListener(this);
        ibtnNext.setOnClickListener(this);
        ibtnPrev.setOnClickListener(this);
        ibtnShuffle.setOnClickListener(this);
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    private void PlayAtI(int i) {
        if (i == list.size()) {
            i = 0;
        } else {
            ibtnPlay_Pause.setImageResource(R.drawable.ic_pause);
            txtFileName.setText(list.get(i).getName());
            txtTimeTotal.setText(setTimeTotal(Long.parseLong(list.get(i).getDuration())));

            txtPresetName.setText("Preset: " + pitchDAO.GetPresetName(list.get(i).getName()));
            txtQuantity.setText("Quantity: " + setSize(Long.parseLong(list.get(i).getSize())));

            playName = txtFileName.getText().toString();
            mediaPlayer = new MediaPlayer();
            if (Environment.getExternalStorageState() == null) {
                directory = new File(Environment.getDataDirectory().getAbsolutePath());

            } else if (Environment.getExternalStorageState() != null) {
                directory = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
            }

            try {
                mediaPlayer.setDataSource(directory + "/" + list.get(i).getName());
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mediaPlayer.prepare();
                mediaPlayer.start();

                skbDrumPad.setMax(mediaPlayer.getDuration());


                handler = new Handler();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (mediaPlayer != null) {
                            skbDrumPad.setProgress(mediaPlayer.getCurrentPosition());
                            txtTimePresent.setText(setTimeTotal((long) mediaPlayer.getCurrentPosition()));
                            handler.post(this);
                        }
                    }
                });

                skbDrumPad.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        ibtnPlay_Pause.setImageResource(R.drawable.ic_pause);
                        if (mediaPlayer.isPlaying()) {
                            mediaPlayer.pause();
                        }
                        mediaPlayer.seekTo(skbDrumPad.getProgress());
                        mediaPlayer.start();
                    }
                });

                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        ibtnPlay_Pause.setImageResource(R.drawable.ic_play);

                        if (list.size() > 1) {

                            try {
                                Thread.sleep(1000);
                                ibtnNext.performClick();


                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                });


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String setTimeTotal(long duration) {
        String timeTotal = "";
        SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");


        long dur = duration;

        String seconds = String.valueOf((dur % 60000) / 1000);


        Log.v("seconds", seconds);
        String minutes = String.valueOf(dur / 60000);

        if (minutes.length() == 1) {
            if (seconds.length() == 1) {
                timeTotal = "0" + minutes + ":0" + seconds;
            } else {
                timeTotal = "0" + minutes + ":" + seconds;
            }
        } else {
            if (seconds.length() == 1) {
                timeTotal = minutes + ":0" + seconds;
            } else {
                timeTotal = minutes + ":" + seconds;
            }
        }

        return timeTotal;
    }

    private String setSize(long fileSize) {
        String file = "";
        long size = fileSize;

        if ((size / 1024) > 1024) {
            file = (((size / 1024) / 1024) + "MB");
        } else {
            file = ((size / 1024) + "KB");
        }

        return file;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ibtnMusic:
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    ibtnPlay_Pause.setImageResource(R.drawable.ic_play);
                } else {
                    mediaPlayer.start();
                    ibtnPlay_Pause.setImageResource(R.drawable.ic_pause);
                }
                break;

            case R.id.ibtnReplay:
                checkReplay = !checkReplay;
                if (checkReplay) {
                    ibtnReplay.setImageResource(R.drawable.ic_replay);
                    mediaPlayer.setLooping(true);
                } else {
                    ibtnReplay.setImageResource(R.drawable.ic_replay_off);
                    mediaPlayer.setLooping(false);
                }
                break;

            case R.id.ibtnNext:

                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                }
                if (checkShuffle) {
                    Random random = new Random();
                    int r = random.nextInt(list.size() - 1);
                    if (r == i) {
                        r = random.nextInt(list.size() - 1);
                    }
                    i = r;
                } else {
                    if (i < list.size() - 1) {
                        i++;
                    } else {
                        i = 0;
                    }
                }
                Log.e("caccccccccc", i + "");
                PlayAtI(i);
                ibtnPlay_Pause.setImageResource(R.drawable.ic_pause);

                activity = (PlayRecordActivity) getContext();
                activity.reloadFragmentList();

                Log.e("ccccccccccccccccccccc", i + "");
                break;

            case R.id.ibtnPrev:

                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                }
                if (checkShuffle) {
                    Random random = new Random();
                    int r = random.nextInt(list.size() - 1);
                    if (r == i) {
                        r = random.nextInt(list.size() - 1);
                    }
                    i = r;
                } else {
                    if (i == 0) {
                        i = list.size() - 1;
                    } else {
                        i--;
                    }
                }
                Log.e("aaaaaaaaa", "bbbbbbbbbb");
                PlayAtI(i);
                ibtnPlay_Pause.setImageResource(R.drawable.ic_pause);

                activity = (PlayRecordActivity) getContext();
                activity.reloadFragmentList();
                break;

            case R.id.ibtnShuffle:
                checkShuffle = !checkShuffle;

                if (checkShuffle) {
                    ibtnShuffle.setImageResource(R.drawable.ic_shuffle);
                } else {
                    ibtnShuffle.setImageResource(R.drawable.ic_shuffle_off);
                }

                break;
        }
    }

    public void removeRecord() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }

        if (Environment.getExternalStorageState() == null) {
            directory = new File(Environment.getDataDirectory().getAbsolutePath());

        } else if (Environment.getExternalStorageState() != null) {
            directory = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
        }
        File[] files = directory.listFiles();
        int pos = -1;
        for (int i = 0; i < files.length; i++) {
            if (files[i].getName().equals(playName)) {
                pos = i;
            }
        }

        int listpos = -1;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getName().equals(playName)) {
                listpos = i;
            }
        }


        if (listpos == list.size() - 1) {
            Log.e("listpos ", listpos + "");
            Log.e("listposaaaa ", list.size() - 1 + "");
            i = 0;
        }

        if (listpos != -1) {
            PlayRecordActivity.list.remove(listpos);
            list = PlayRecordActivity.list;
        }
        if (pos != -1) {
            files[pos].delete();
        }

        PlayRecordActivity activity = (PlayRecordActivity) getContext();
        activity.reloadListFragmentList();
        if (list.size() != 0) {
            PlayAtI(i);
        } else {
            Intent intent = new Intent(getContext(), ListRecord.class);

            getContext().startActivity(intent);
            ((Activity) getContext()).overridePendingTransition(R.anim.activity_in_left, R.anim.activity_out_right);
            ((PlayRecordActivity) getContext()).finish();
        }

        Log.e("accccccccccc ", i + "");
    }

    public void playRecord(String name) {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }

        int pos = -1;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getName().equals(name)) {
                pos = i;
            }
        }

        i = pos;
        if (pos != -1) {
            PlayAtI(i);
            activity = (PlayRecordActivity) getContext();
            activity.reloadFragmentList();
        }
    }
}

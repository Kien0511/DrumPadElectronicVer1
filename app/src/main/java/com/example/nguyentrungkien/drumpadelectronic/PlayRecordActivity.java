package com.example.nguyentrungkien.drumpadelectronic;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.nguyentrungkien.drumpadelectronic.Adapter.PlayRecordAdapter;
import com.example.nguyentrungkien.drumpadelectronic.DTO.RecordDTO;
import com.example.nguyentrungkien.drumpadelectronic.Fragment.FragmentListRecord;
import com.example.nguyentrungkien.drumpadelectronic.Fragment.FragmentPlayRecord;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PlayRecordActivity extends AppCompatActivity implements View.OnClickListener{
    private ImageButton ibtnBack, ibtnOption;
    private ViewPager vpgPlayRecord;
    public static RecordDTO recordDTO = null;
    public static List<RecordDTO> list = new ArrayList<>();
    private int check = -1;

    FragmentListRecord fragmentListRecord;
    FragmentPlayRecord fragmentPlayRecord;

    PlayRecordAdapter adapter;

    File directory;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.custom_record_play);



        ibtnBack = findViewById(R.id.ibtnBack);
        ibtnOption = findViewById(R.id.ibtnOption);
        vpgPlayRecord = findViewById(R.id.vpgPlayRecord);

        fragmentListRecord = new FragmentListRecord();
        fragmentPlayRecord = new FragmentPlayRecord();

        setupViewPager(vpgPlayRecord);

        if(getIntent().getExtras() != null)
        {
            this.recordDTO = new RecordDTO();
            this.recordDTO.setName(getIntent().getExtras().getString("filename"));
            this.recordDTO.setDuration(getIntent().getExtras().getString("fileduration"));
            this.recordDTO.setSize(getIntent().getExtras().getString("filesize"));

            for (int i = 0; i < list.size(); i++)
            {
                if (list.get(i).getName().equals(getIntent().getExtras().getString("filename")))
                {
                    check = i;
                }
            }

            if(check != -1)
            {
                this.list.remove(check);
            }
            this.list.add(0,this.recordDTO);
        }

        ibtnBack.setOnClickListener(this);
        ibtnOption.setOnClickListener(this);


    }

    public void setupViewPager(ViewPager viewPager)
    {
        adapter = new PlayRecordAdapter(getSupportFragmentManager());

        adapter.addFragment(fragmentListRecord);
        adapter.addFragment(fragmentPlayRecord);

        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(1);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.ibtnBack:
                if(FragmentPlayRecord.mediaPlayer.isPlaying())
                {
                    FragmentPlayRecord.mediaPlayer.stop();
                }
                Intent intent = new Intent(PlayRecordActivity.this,ListRecord.class);

                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.activity_in_left,R.anim.activity_out_right);
                break;

            case R.id.ibtnOption:
                PopupMenu popupMenu = new PopupMenu(PlayRecordActivity.this,ibtnOption);
                popupMenu.getMenuInflater().inflate(R.menu.custom_popup_option_play_record,popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId())
                        {
                            case R.id.itemDelete:
                                delete();
                                break;

                            case R.id.itemShare:
                                shareFile();
                                break;
                        }
                        return true;
                    }
                });
                popupMenu.show();
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK)
        {
            if(FragmentPlayRecord.mediaPlayer.isPlaying())
            {
                FragmentPlayRecord.mediaPlayer.stop();
            }

            Intent intent = new Intent(PlayRecordActivity.this,ListRecord.class);

            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.activity_in_left,R.anim.activity_out_right);
        }
        return super.onKeyDown(keyCode, event);
    }

    public void reloadFragmentList()
    {
        fragmentListRecord = (FragmentListRecord) adapter.getList().get(0);
        fragmentListRecord.reload();
    }

    public void reloadListFragmentList()
    {
        fragmentListRecord = (FragmentListRecord) adapter.getList().get(0);
        fragmentListRecord.reloadList();
    }

    public void delete()
    {
        fragmentPlayRecord = (FragmentPlayRecord) adapter.getList().get(1);
        fragmentPlayRecord.removeRecord();
    }

    public void playRecord(String name)
    {
        fragmentPlayRecord = (FragmentPlayRecord) adapter.getList().get(1);
        fragmentPlayRecord.playRecord(name);

    }

    public void shareFile()
    {
        if (Environment.getExternalStorageState() == null) {
            directory = new File(Environment.getDataDirectory().getAbsolutePath());

        } else if (Environment.getExternalStorageState() != null) {
            directory = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
        }
        File[] files = directory.listFiles();
        int pos = -1;
        for (int i = 0; i < files.length; i++)
        {
            if(files[i].getName().equals(FragmentPlayRecord.playName))
            {
                pos = i;
            }
        }
        if(pos != -1)
        {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(files[pos].getAbsoluteFile()));
            intent.setType("text/plain");
            startActivity(Intent.createChooser(intent, "Share File"));
        }

    }
}

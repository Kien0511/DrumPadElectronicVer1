package com.example.nguyentrungkien.drumpadelectronic;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;

import com.example.nguyentrungkien.drumpadelectronic.Adapter.ListMusicAdapter;
import com.example.nguyentrungkien.drumpadelectronic.Fragment.FragmentListDefault;
import com.example.nguyentrungkien.drumpadelectronic.Fragment.FragmentListMP3;

public class ListMusicActivity extends AppCompatActivity {

    private TabLayout tabLayoutListMusic;
    private ViewPager vpgListMusic;

    FragmentListMP3 fragmentListMP3;
    FragmentListDefault fragmentListDefault;
    ListMusicAdapter adapter;
    private ImageButton ibtnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_list_music);


        ibtnBack = findViewById(R.id.ibtnBack);
        tabLayoutListMusic = findViewById(R.id.tabLayoutListMusic);
        vpgListMusic = findViewById(R.id.vpgListMusic);

        fragmentListMP3 = new FragmentListMP3();
        fragmentListDefault = new FragmentListDefault();

        setupViewPager(vpgListMusic);
        tabLayoutListMusic.setupWithViewPager(vpgListMusic);

//        MainActivity.dialog.hide();
        ibtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.dialog.dismiss();

                finish();
                overridePendingTransition(R.anim.activity_in_left,R.anim.activity_out_right);
            }
        });
    }

    public void setupViewPager(ViewPager viewPager)
    {
        adapter = new ListMusicAdapter(getSupportFragmentManager());

        adapter.addFragment(fragmentListMP3,"Your Music");
        adapter.addFragment(fragmentListDefault,"Default Music");

        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK)
        {
            MainActivity.dialog.dismiss();


            finish();
            overridePendingTransition(R.anim.activity_in_left,R.anim.activity_out_right);
        }
        return super.onKeyDown(keyCode, event);
    }
}

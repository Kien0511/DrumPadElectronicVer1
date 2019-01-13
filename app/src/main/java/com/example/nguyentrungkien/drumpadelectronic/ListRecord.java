package com.example.nguyentrungkien.drumpadelectronic;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.nguyentrungkien.drumpadelectronic.Adapter.RecordAdapter;
import com.example.nguyentrungkien.drumpadelectronic.DTO.RecordDTO;
import com.google.ads.consent.ConsentInformation;
import com.google.ads.consent.ConsentStatus;
import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.doubleclick.PublisherInterstitialAd;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ListRecord extends AppCompatActivity {
    private RecyclerView rcvListRecord;
    private List<RecordDTO> list;
    private File file;
    private String css;
    private ImageButton ibtnBack, ibtnOption, ibtnDelete, ibtnCheckAll;
    private RecordAdapter adapter;

    private boolean checkSelectAll = false;

    private LinearLayout llRecords;

    private List<String> listChecked;

    private File[] files;

    private LinearLayout llBehindDialog;

    PublisherInterstitialAd ggInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_list_record);

        initInterstitialAd();

        llBehindDialog = findViewById(R.id.llBehindDialog);
        llRecords = findViewById(R.id.llRecords);
        ibtnDelete = findViewById(R.id.ibtnDelete);
        ibtnCheckAll = findViewById(R.id.ibtnCheckAll);
        ibtnBack = findViewById(R.id.ibtnBack);
        ibtnOption = findViewById(R.id.ibtnOption);
        rcvListRecord = findViewById(R.id.rcvListRecord);
        list = new ArrayList<>();
        listChecked = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rcvListRecord.setLayoutManager(layoutManager);

        if(Environment.getExternalStorageState() == null)
        {
            file = new File(Environment.getDataDirectory().getAbsolutePath());
        }
        else if(Environment.getExternalStorageState() != null)
        {
            file = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
        }

        files = file.listFiles();
        for (int i = 0;i < files.length; i++)
        {
            if(files[i].getName().length() > 4)
            {
                css = files[i].getName().substring(files[i].getName().length()-4,files[i].getName().length());
            }
            else
            {
                css = files[i].getName();
            }
            if(css.equals(".3gp"))
            {
                MediaMetadataRetriever metaRetriever = new MediaMetadataRetriever();
                metaRetriever.setDataSource(files[i].getAbsolutePath());

                String duration = metaRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
                String size = files[i].length()+"";

                RecordDTO recordDTO = new RecordDTO();
                recordDTO.setName(files[i].getName());
                recordDTO.setDuration(duration);
                recordDTO.setSize(size);
                list.add(recordDTO);

                metaRetriever.release();
            }
        }

        adapter = new RecordAdapter(this,list);
        rcvListRecord.setAdapter(adapter);

        ibtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListRecord.this,MainActivity.class);

                startActivity(intent);
                overridePendingTransition(R.anim.activity_in_left,R.anim.activity_out_right);
                finish();
                showInterstitial();

            }
        });

        ibtnOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(ListRecord.this,ibtnOption);
                popupMenu.getMenuInflater().inflate(R.menu.custom_popup_option_list_record,popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId())
                        {
                            case R.id.itemDelete:
                                ibtnDelete.setVisibility(View.VISIBLE);
                                ibtnCheckAll.setVisibility(View.VISIBLE);
                                ibtnOption.setVisibility(View.GONE);
                                adapter.setCheck(true);
                                rcvListRecord.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                                break;

                            case R.id.itemPlayAll:
                                if(list.size() > 0)
                                {
                                    Intent intent = new Intent(ListRecord.this,PlayRecordActivity.class);

                                    PlayRecordActivity.list = list;
                                    startActivity(intent);
                                    overridePendingTransition(R.anim.activity_in_right, R.anim.activity_out_left);
                                }
                                break;
                        }
                        return true;
                    }
                });
                popupMenu.show();
            }
        });

        ibtnCheckAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkSelectAll = !checkSelectAll;
                if(checkSelectAll)
                {
                    ibtnCheckAll.setImageResource(R.drawable.ic_selectall_on);
                    for (int i = 0; i < list.size(); i++)
                    {
                        listChecked.add(list.get(i).getName());
                    }

                    Log.e("aaaaaaaaaaaaaaaaaaaa",listChecked.size()+"");
                    adapter.setCheckAll(true);
                    adapter.setListChecked(listChecked);
                    rcvListRecord.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
                else
                {
                    ibtnCheckAll.setImageResource(R.drawable.ic_selectall);
                    listChecked.clear();
                    Log.e("aaaaaaaaaaaaaaaaaaaa",listChecked.size()+"");

                    adapter.setCheckAll(false);
                    adapter.setListChecked(listChecked);
                    rcvListRecord.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }
        });

        llRecords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ibtnOption.getVisibility() == View.GONE)
                {
                    ibtnOption.setVisibility(View.VISIBLE);
                    ibtnCheckAll.setVisibility(View.GONE);
                    ibtnDelete.setVisibility(View.GONE);

                    ibtnCheckAll.setImageResource(R.drawable.ic_selectall);
                    adapter.setCheck(false);
                    adapter.setCheckAll(false);
                    adapter.clearListChecked();
                    rcvListRecord.setAdapter(adapter);
                    listChecked = adapter.getListChecked();
                    adapter.notifyDataSetChanged();
                }
            }
        });

        ibtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listChecked = adapter.getListChecked();
                if(listChecked.size() > 0) {
                    showDeleteDialog();
                }

                Log.e("aaaaaaaaaaaaaa",listChecked.size()+"");
            }
        });
    }


    private void showDeleteDialog()
    {

        llBehindDialog.setVisibility(View.VISIBLE);
        final Dialog dialog = new Dialog(ListRecord.this);
        dialog.setContentView(R.layout.custom_dialog_delete);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView txtDelete, txtCancel;

        txtDelete = dialog.findViewById(R.id.txtDelete);
        txtCancel = dialog.findViewById(R.id.txtCancel);

        txtDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listChecked = adapter.getListChecked();

                for (int i = 0; i < listChecked.size(); i++)
                {
                    int pos = -1;
                    int filePos = -1;
                    for (int j = 0; j < list.size(); j++)
                    {
                        if(list.get(j).getName().equals(listChecked.get(i)))
                        {
                            pos = j;
                        }
                    }

                    if(pos > -1)
                    {
                        list.remove(pos);
                        adapter.notifyItemRemoved(pos);
                        adapter.notifyDataSetChanged();
                    }

                    for (int k = 0; k < files.length; k++)
                    {
                        if(files[k].getName().equals(listChecked.get(i)))
                        {
                            filePos = k;
                        }
                    }

                    if(filePos > -1)
                    {
                        files[filePos].delete();
                    }


                }
                listChecked.clear();

                if(list.size() == 0)
                {
                    ibtnCheckAll.setVisibility(View.GONE);
                    ibtnDelete.setVisibility(View.GONE);
                    ibtnOption.setVisibility(View.VISIBLE);
                }

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
                llBehindDialog.setVisibility(View.GONE);
                dialog.dismiss();
            }
        });

        dialog.show();
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK)
        {
            if(ibtnOption.getVisibility() != View.GONE)
            {
                Intent intent = new Intent(ListRecord.this,MainActivity.class);

                startActivity(intent);
                overridePendingTransition(R.anim.activity_in_left,R.anim.activity_out_right);
                finish();
                showInterstitial();
            }

        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        if(ibtnOption.getVisibility() == View.GONE)
        {
            ibtnOption.setVisibility(View.VISIBLE);
            ibtnCheckAll.setVisibility(View.GONE);
            ibtnDelete.setVisibility(View.GONE);

            ibtnCheckAll.setImageResource(R.drawable.ic_selectall);
            adapter.setCheck(false);
            adapter.setCheckAll(false);
            rcvListRecord.setAdapter(adapter);
            adapter.clearListChecked();
            adapter.notifyDataSetChanged();
            listChecked = adapter.getListChecked();
        }
        else {
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

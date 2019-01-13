package com.example.nguyentrungkien.drumpadelectronic.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.nguyentrungkien.drumpadelectronic.PlayRecordActivity;
import com.example.nguyentrungkien.drumpadelectronic.DTO.RecordDTO;
import com.example.nguyentrungkien.drumpadelectronic.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.ViewHolder>{

    Context context;
    List<RecordDTO> list;

    File file;
    MediaPlayer mediaPlayer;
    List<String> listChecked = new ArrayList<>();
    ;

    public List<String> getListChecked() {
        return listChecked;
    }

    public void setListChecked(List<String> listChecked) {
        this.listChecked = listChecked;
    }




    boolean check = false;

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public RecordAdapter(Context context, List<RecordDTO> list) {
        this.context = context;
        this.list = list;
    }

    boolean checkDelete = false;
    boolean checkAll = false;

    public boolean isCheckAll() {
        return checkAll;
    }

    public void setCheckAll(boolean checkAll) {
        this.checkAll = checkAll;
    }

    int vitri = -1;
    int posShare = -1;
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_record,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.txtFileName.setText(list.get(position).getName());


        if(isCheck())
        {
            holder.ibtnCheckDelete.setVisibility(View.VISIBLE);
            holder.ibtnOption.setVisibility(View.GONE);

            if(isCheckAll())
            {
                holder.ibtnCheckDelete.setImageResource(R.drawable.ic_checkbox_checked);
                holder.ibtnCheckDelete.setTag(R.drawable.ic_checkbox_checked);

                setCheckDelete(holder,position);
            }
            else
            {
                holder.ibtnCheckDelete.setImageResource(R.drawable.ic_checkbox_uncheck);
                holder.ibtnCheckDelete.setTag(R.drawable.ic_checkbox_uncheck);

                setCheckDelete(holder,position);
            }


        }
        else
        {
            holder.ibtnCheckDelete.setVisibility(View.GONE);
            holder.ibtnOption.setVisibility(View.VISIBLE);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, PlayRecordActivity.class);

                    intent.putExtra("filename",list.get(position).getName());
                    intent.putExtra("fileduration",list.get(position).getDuration());
                    intent.putExtra("filesize",list.get(position).getSize());

                    context.startActivity(intent);

                    ((Activity) context).overridePendingTransition(R.anim.activity_in_right, R.anim.activity_out_left);


                }
            });
        }





        if(Environment.getExternalStorageState() == null)
        {
            file = new File(Environment.getDataDirectory().getAbsolutePath());

        }
        else if(Environment.getExternalStorageState() != null)
        {
            file = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
        }

        holder.ibtnOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                PopupMenu popupMenu = new PopupMenu(context,holder.ibtnOption);
                popupMenu.getMenuInflater().inflate(R.menu.custom_popup_option_item_record,popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId())
                        {
                            case R.id.itemDelete:

                                File[] files = file.listFiles();

                                for (int i = 0; i < files.length; i++)
                                {
                                    if(list.get(position).getName().compareTo(files[i].getName()) == 0)
                                    {
                                        vitri = i;
                                    }
                                }

                                if(vitri > -1)
                                {
                                    files[vitri].delete();
                                    list.remove(position);
                                    notifyDataSetChanged();
                                    notifyItemRemoved(position);
                                }
                                break;

                            case R.id.itemPlay:
                                Intent intent = new Intent(context, PlayRecordActivity.class);

                                intent.putExtra("filename",list.get(position).getName());
                                intent.putExtra("fileduration",list.get(position).getDuration());
                                intent.putExtra("filesize",list.get(position).getSize());

                                context.startActivity(intent);

                                ((Activity) context).overridePendingTransition(R.anim.activity_in_right, R.anim.activity_out_left);
                                break;

                            case R.id.itemShare:
                                File[] files1 = file.listFiles();

                                for (int i = 0; i < files1.length; i++)
                                {
                                    if(list.get(position).getName().compareTo(files1[i].getName()) == 0)
                                    {
                                        posShare = i;
                                    }
                                }

                                if(posShare != -1)
                                {
                                    Intent intentShare = new Intent(Intent.ACTION_SEND);
                                    intentShare.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(files1[posShare].getAbsoluteFile()));
                                    intentShare.setType("text/plain");
                                    context.startActivity(Intent.createChooser(intentShare, "Share File"));
                                }

                                break;
                        }
                        return true;
                    }
                });
                popupMenu.show();
            }
        });

        SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");


        long dur = Long.parseLong(list.get(position).getDuration());

        String seconds = String.valueOf((dur % 60000) / 1000);


        Log.v("seconds", seconds);
        String minutes = String.valueOf(dur / 60000);

        if(minutes.length() == 1)
        {
            if (seconds.length() == 1) {
                holder.txtDuration.setText("Duration: " + "0" + minutes + ":0" + seconds);
            }else {
                holder.txtDuration.setText("Duration: "+ "0" + minutes + ":" + seconds);
            }
        }
        else
        {
            if (seconds.length() == 1) {
                holder.txtDuration.setText("Duration: " + minutes + ":0" + seconds);
            }else {
                holder.txtDuration.setText("Duration: " + minutes + ":" + seconds);
            }
        }

        long size = Long.parseLong(list.get(position).getSize());

        if((size/1024)>1024)
        {
            holder.txtSize.setText(((size/1024)/1024)+"MB");
        }
        else {
            holder.txtSize.setText((size / 1024) + "KB");
        }
    }

    private void setCheckDelete(final ViewHolder holder, final int position)
    {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer integer = (Integer) holder.ibtnCheckDelete.getTag();
                if(integer == R.drawable.ic_checkbox_uncheck)
                {
                    holder.ibtnCheckDelete.setImageResource(R.drawable.ic_checkbox_checked);
                    holder.ibtnCheckDelete.setTag(R.drawable.ic_checkbox_checked);

                    listChecked.add(list.get(position).getName());
                    Log.e("aaaaaaaaaaaaaaaaaaa",list.get(position).getName());

                }
                else
                {
                    holder.ibtnCheckDelete.setImageResource(R.drawable.ic_checkbox_uncheck);
                    holder.ibtnCheckDelete.setTag(R.drawable.ic_checkbox_uncheck);
                    int removeAt = -1;
                    for(int i = 0; i < listChecked.size(); i++)
                    {
                        if(listChecked.get(i).compareTo(list.get(position).getName()) == 0)
                        {
                            removeAt = i;
                        }
                    }

                    if(removeAt > -1)
                    {
                        listChecked.remove(removeAt);
                    }
                }

            }
        });

        holder.ibtnCheckDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer integer = (Integer) holder.ibtnCheckDelete.getTag();

                if(integer == R.drawable.ic_checkbox_uncheck)
                {
                    holder.ibtnCheckDelete.setImageResource(R.drawable.ic_checkbox_checked);
                    holder.ibtnCheckDelete.setTag(R.drawable.ic_checkbox_checked);

                    listChecked.add(list.get(position).getName());
                }
                else
                {
                    holder.ibtnCheckDelete.setImageResource(R.drawable.ic_checkbox_uncheck);
                    holder.ibtnCheckDelete.setTag(R.drawable.ic_checkbox_uncheck);

                    int removeAt = -1;
                    for(int i = 0; i < listChecked.size(); i++)
                    {
                        if(listChecked.get(i).compareTo(list.get(position).getName()) == 0)
                        {
                            removeAt = i;
                        }
                    }

                    if(removeAt > -1)
                    {
                        listChecked.remove(removeAt);
                    }
                }

            }
        });
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView txtFileName,txtDuration, txtSize;
        ImageButton ibtnOption, ibtnCheckDelete;
        public ViewHolder(View itemView) {
            super(itemView);
            txtFileName = itemView.findViewById(R.id.txtFileName);
            ibtnOption = itemView.findViewById(R.id.ibtnOption);
            txtDuration = itemView.findViewById(R.id.txtDuration);
            txtSize = itemView.findViewById(R.id.txtSize);
            ibtnCheckDelete = itemView.findViewById(R.id.ibtnCheckDelete);
        }
    }

    public void clearListChecked()
    {
        listChecked.clear();
    }
}

package com.example.nguyentrungkien.drumpadelectronic.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nguyentrungkien.drumpadelectronic.DTO.RecordDTO;
import com.example.nguyentrungkien.drumpadelectronic.Fragment.FragmentPlayRecord;
import com.example.nguyentrungkien.drumpadelectronic.PlayRecordActivity;
import com.example.nguyentrungkien.drumpadelectronic.R;

import java.text.SimpleDateFormat;
import java.util.List;

public class ListRecordAdapter extends RecyclerView.Adapter<ListRecordAdapter.ViewHolder> {

    Context context;
    List<RecordDTO> list;

    public ListRecordAdapter(Context context, List<RecordDTO> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_record_list_play,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.txtFileName.setText(list.get(position).getName());
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

        if(list.get(position).getName().equals(FragmentPlayRecord.playName))
        {
            holder.itemView.setSelected(true);
        }
        else
        {
            holder.itemView.setSelected(false);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PlayRecordActivity activity = (PlayRecordActivity) context;
                activity.playRecord(list.get(position).getName());
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView txtFileName,txtDuration, txtSize;
        public ViewHolder(View itemView) {
            super(itemView);

            txtFileName = itemView.findViewById(R.id.txtFileName);
            txtDuration = itemView.findViewById(R.id.txtDuration);
            txtSize = itemView.findViewById(R.id.txtSize);
        }
    }

}
